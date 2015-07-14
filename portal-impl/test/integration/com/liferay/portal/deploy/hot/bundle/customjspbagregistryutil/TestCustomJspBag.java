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

package com.liferay.portal.deploy.hot.bundle.customjspbagregistryutil;

import com.liferay.portal.deploy.hot.CustomJspBag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {"service.ranking:Integer=" + Integer.MAX_VALUE},
	service = CustomJspBag.class
)
public class TestCustomJspBag implements CustomJspBag {

	@Override
	public String getCustomJspDir() {
		return "/WEB-INF/custom_jsps";
	}

	@Override
	public List<String> getCustomJsps() {
		_customJsps.add("/html/portlet/nothing.jsp");
		return _customJsps;
	}

	@Override
	public String getPluginPackageName() {
		return "Sample OSGI JSP Overwrite";
	}

	@Override
	public ServletContext getServletContext() {
		return null;
	}

	@Override
	public String getServletContextName() {
		return "test-jsp-bag";
	}

	@Override
	public boolean isCustomJspGlobal() {
		return false;
	}

	private final List<String> _customJsps = new ArrayList<>();

}