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

package com.liferay.portal.model;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 */
public interface LayoutTypeController extends Serializable {

	public String getBaseLayoutType();

	public String[] getConfigurationActionDelete();

	public String[] getConfigurationActionUpdate();

	public String getURL();

	public String includeEditContent(
			HttpServletRequest request, HttpServletResponse response,
			Layout layout)
		throws Exception;

	public boolean includeLayoutContent(
			HttpServletRequest request, HttpServletResponse response,
			Layout layout)
		throws Exception;

	public boolean isBrowsable();

	public boolean isFirstPageable();

	public boolean isParentable();

	public boolean isSitemapable();

	public boolean isURLFriendliable();

	public boolean matches(
		HttpServletRequest request, String friendlyURL, Layout layout);

}