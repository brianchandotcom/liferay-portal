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

package com.liferay.frontend.js.jquery.web.internal.servlet.taglib;

import com.liferay.frontend.js.jquery.web.internal.configuration.JSJQueryConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julien Castelain
 */
@Component(
	configurationPid = "com.liferay.frontend.js.jquery.web.internal.configuration.JSJQueryConfiguration",
	immediate = true, service = DynamicInclude.class
)
public class JQueryTopHeadDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		if (!_jsJQueryConfiguration.enableJQuery()) {
			return;
		}

		PrintWriter printWriter = httpServletResponse.getWriter();

		StringBundler sb = new StringBundler();

		AbsolutePortalURLBuilder absolutePortalURLBuilder =
			_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
				httpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay.isThemeJsFastLoad()) {
			absolutePortalURLBuilder.ignoreCDNHost();

			sb.append("<script data-senna-track=\"permanent\" src=\"");
			sb.append(
				_portal.getStaticResourceURL(
					httpServletRequest, _portal.getPathContext() + "/combo",
					"minifierType=js", _lastModified));

			for (String fileName : _FILE_NAMES) {
				sb.append("&");
				sb.append(
					absolutePortalURLBuilder.forModule(
						_bundleContext.getBundle(), fileName
					).build());
			}

			sb.append("\" type=\"text/javascript\"></script>");
		}
		else {
			for (String fileName : _FILE_NAMES) {
				sb.append("<script data-senna-track=\"permanent\" src=\"");
				sb.append(
					absolutePortalURLBuilder.forModule(
						_bundleContext.getBundle(), fileName
					).build());
				sb.append("\" type=\"text/javascript\"></script>");
			}
		}

		printWriter.println(sb.toString());
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#pre");
	}

	@Activate
	@Modified
	protected void activate(
			BundleContext bundleContext, ComponentContext componentContext,
			Map<String, Object> properties)
		throws Exception {

		_bundleContext = bundleContext;

		_lastModified = System.currentTimeMillis();

		_jsJQueryConfiguration = ConfigurableUtil.createConfigurable(
			JSJQueryConfiguration.class, properties);
	}

	private static final String[] _FILE_NAMES = {
		"/jquery/jquery.min.js", "/jquery/bootstrap.bundle.min.js",
		"/jquery/collapsible_search.js", "/jquery/fm.js", "/jquery/form.js",
		"/jquery/popper.min.js", "/jquery/side_navigation.js"
	};

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	private BundleContext _bundleContext;
	private volatile JSJQueryConfiguration _jsJQueryConfiguration;
	private long _lastModified;

	@Reference
	private Portal _portal;

}