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

import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.remote.web.component.admin.web.internal.util.LastModified;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	property = "service.ranking:Integer=1000", service = DynamicInclude.class
)
public class RemoteWebComponentTopHeadDynamicInclude
	extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.append("<link href=\"");
		printWriter.append(_servletContext.getContextPath());
		printWriter.append("/css/rwc.css?ts=");
		printWriter.append(LastModified.getString());
		printWriter.append("\" rel=\"stylesheet\" type=\"text/css\" />");
		printWriter.println();
		printWriter.append("<script src=\"");
		printWriter.append(_servletContext.getContextPath());
		printWriter.append("/remote-web-component-admin-web.js?ts=");
		printWriter.append(LastModified.getString());
		printWriter.append("\" type=\"text/javascript\"");
		printWriter.append("data-senna-track=\"permanent\"></script>");

		printWriter.flush();
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.remote.web.component.admin.web)"
	)
	private ServletContext _servletContext;

}