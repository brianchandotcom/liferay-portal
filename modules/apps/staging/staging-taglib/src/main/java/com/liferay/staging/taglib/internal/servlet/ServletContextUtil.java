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

package com.liferay.staging.taglib.internal.servlet;

import com.liferay.layout.util.LayoutsTree;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(service = {})
public class ServletContextUtil {

	public static String getContextPath() {
		return _servletContext.getContextPath();
	}

	public static LayoutsTree getLayoutsTree() {
		return _layoutsTree;
	}

	public static ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference(unbind = "-")
	protected void setLayoutsTree(LayoutsTree layoutsTree) {
		_layoutsTree = layoutsTree;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.staging.taglib)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static LayoutsTree _layoutsTree;
	private static ServletContext _servletContext;

}