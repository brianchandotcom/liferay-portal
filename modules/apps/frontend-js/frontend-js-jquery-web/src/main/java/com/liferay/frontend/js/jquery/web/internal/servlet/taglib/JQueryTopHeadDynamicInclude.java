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

import com.liferay.frontend.js.jquery.web.configuration.JSJQueryConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julien Castelain
 */
@Component(
	configurationPid = "com.liferay.frontend.js.jquery.web.configuration.JSJQueryConfiguration",
	immediate = true, service = DynamicInclude.class
)
public class JQueryTopHeadDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response,
			String key)
		throws IOException {

		if (!_jsJQueryConfiguration.enableJQuery()) {
			return;
		}

		PrintWriter printWriter = response.getWriter();

		AbsolutePortalURLBuilder absolutePortalURLBuilder =
			_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
				request);

		String context = request.getContextPath();

		for (String fileName : _FILE_NAMES) {
			printWriter.print("<script data-senna-track=\"permanent\" src=\"");

			printWriter.print(
				absolutePortalURLBuilder.forResource(
					context + "/o/frontend-js-jquery-web/" + fileName
				).build());

			printWriter.println("\" type=\"text/javascript\"></script>");
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#pre");
	}

	@Activate
	@Modified
	protected void activate(
			ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		_jsJQueryConfiguration = ConfigurableUtil.createConfigurable(
			JSJQueryConfiguration.class, properties);
	}

	private static final String[] _FILE_NAMES = {
		"jquery/jquery.js", "jquery/fm.js", "jquery/form.js"
	};

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	private volatile JSJQueryConfiguration _jsJQueryConfiguration;

}