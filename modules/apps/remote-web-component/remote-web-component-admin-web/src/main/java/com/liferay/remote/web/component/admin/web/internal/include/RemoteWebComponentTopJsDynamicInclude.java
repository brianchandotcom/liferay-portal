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

package com.liferay.remote.web.component.admin.web.internal.include;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.remote.web.component.admin.web.internal.configuration.RemoteWebComponentConfiguration;
import com.liferay.remote.web.component.admin.web.internal.util.Timestamp;

import java.io.IOException;
import java.io.PrintWriter;

import java.time.Instant;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.remote.web.component.admin.web.internal.configuration.RemoteWebComponentConfiguration",
	factory = "remote.web.component.top.js.dynamic.include",
	service = DynamicInclude.class
)
public class RemoteWebComponentTopJsDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		Instant now = Instant.now();

		String[] webComponentHeadJsUrls = Timestamp.append(
			_remoteWebComponentConfiguration.webComponentTopJsUrl(), now);

		if (webComponentHeadJsUrls.length > 0) {
			PrintWriter printWriter = httpServletResponse.getWriter();

			for (String url : webComponentHeadJsUrls) {
				printWriter.append("<script src=\"");
				printWriter.append(url);
				printWriter.append("\" type=\"text/javascript\"></script>");
				printWriter.println();
			}

			printWriter.flush();
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_js.jspf#resources");
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_remoteWebComponentConfiguration = ConfigurableUtil.createConfigurable(
			RemoteWebComponentConfiguration.class, properties);
	}

	private volatile RemoteWebComponentConfiguration
		_remoteWebComponentConfiguration;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.remote.web.component.admin.web)"
	)
	private ServletContext _servletContext;

}