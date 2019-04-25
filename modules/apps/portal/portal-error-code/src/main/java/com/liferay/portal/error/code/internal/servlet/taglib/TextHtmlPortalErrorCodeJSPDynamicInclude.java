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

package com.liferay.portal.error.code.internal.servlet.taglib;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.IOException;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(property = "mime.type=text/html", service = DynamicInclude.class)
public class TextHtmlPortalErrorCodeJSPDynamicInclude
	extends BaseJSPDynamicInclude {

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response,
			String key)
		throws IOException {

		response.setContentType(_mimeType + _CHARSET);

		super.include(request, response, key);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/errors/code.jsp#" + _mimeType);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_mimeType = MapUtil.getString(properties, "mime.type", "text/html");
	}

	@Override
	protected String getJspPath() {
		return "/dynamic_include/text_html.jsp";
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.error.code.dynamic.include.impl)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final String _CHARSET = "; charset=UTF-8";

	private static final Log _log = LogFactoryUtil.getLog(
		TextHtmlPortalErrorCodeJSPDynamicInclude.class);

	private String _mimeType;

}