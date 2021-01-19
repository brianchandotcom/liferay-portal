/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.frontend.js.svg4everybody.web.internal.servlet.taglib;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryce Osterhaus
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE,
	service = DynamicInclude.class
)
public class SVG4EverybodyTopHeadDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		boolean cdnDynamicResourcesEnabled = true;

		try {
			cdnDynamicResourcesEnabled = _portal.isCDNDynamicResourcesEnabled(
				httpServletRequest);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to gather property from configuration",
					portalException);
			}
		}

		boolean cdnHostEnabled = false;

		try {
			String cdnHost = _portal.getCDNHost(httpServletRequest);

			cdnHostEnabled = !cdnHost.isEmpty();
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to gather property from configuration",
					portalException);
			}
		}

		if (cdnHostEnabled) {
			PrintWriter printWriter = httpServletResponse.getWriter();

			AbsolutePortalURLBuilder absolutePortalURLBuilder =
				_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
					httpServletRequest);

			if (!cdnDynamicResourcesEnabled) {
				absolutePortalURLBuilder.ignoreCDNHost();
			}

			for (String jsFileName : _JS_FILE_NAMES) {
				printWriter.print(
					"<script data-senna-track=\"permanent\" src=\"");

				printWriter.print(
					absolutePortalURLBuilder.forModule(
						_bundleContext.getBundle(), jsFileName
					).build());

				printWriter.println("\" type=\"text/javascript\"></script>");
			}
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#pre");
	}

	@Activate
	@Modified
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private static final String[] _JS_FILE_NAMES = {"/index.js"};

	private static final Log _log = LogFactoryUtil.getLog(
		SVG4EverybodyTopHeadDynamicInclude.class);

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	private BundleContext _bundleContext;

	@Reference
	private Portal _portal;

}