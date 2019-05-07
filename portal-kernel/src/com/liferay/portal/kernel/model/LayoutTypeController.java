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

package com.liferay.portal.kernel.model;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 */
public interface LayoutTypeController extends Serializable {

	public String[] getConfigurationActionDelete();

	public String[] getConfigurationActionUpdate();

	public String getType();

	public String getURL();

	public String includeEditContent(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Layout layout)
		throws Exception;

	public boolean includeLayoutContent(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Layout layout)
		throws Exception;

	public boolean isBrowsable();

	public boolean isCheckLayoutViewPermission();

	public boolean isFirstPageable();

	public boolean isFullPageDisplayable();

	public boolean isInstanceable();

	public boolean isParentable();

	public default boolean isPrimaryType() {
		return false;
	}

	public boolean isSitemapable();

	public boolean isURLFriendliable();

	public default boolean isWorkflowEnabled() {
		return true;
	}

	public boolean matches(
		HttpServletRequest httpServletRequest, String friendlyURL,
		Layout layout);

}