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

package com.liferay.control.menu;

import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Julio Camarero
 */
public interface ControlMenuEntry {

	public Map<String, Object> getData(HttpServletRequest request);

	public String getIconCssClass(HttpServletRequest request);

	public String getKey();

	public String getLabel(Locale locale);

	public String getLinkCssClass(HttpServletRequest request);

	public String getURL(HttpServletRequest request);

	public boolean hasAccessPermission(HttpServletRequest request)
		throws PortalException;

	public boolean include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException;

	public boolean isUseDialog();

}