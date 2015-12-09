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

package com.liferay.frontend.map.google.web;

import com.liferay.frontend.map.api.BaseJSPMapProvider;
import com.liferay.frontend.map.api.MapProvider;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = MapProvider.class)
public class GoogleMapProvider extends BaseJSPMapProvider {

	@Override
	public String getConfigurationJspPath() {
		return "/configuration.jsp";
	}

	@Override
	public String getHelpMessage() {
		return null;
	}

	@Override
	public String getJspPath() {
		return "/view.jsp";
	}

	@Override
	public String getKey() {
		return "Google";
	}

	@Override
	public String getLabel() {
		return "google-maps";
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.map.google.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

}