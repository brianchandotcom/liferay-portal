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

package com.liferay.layout.portlet.category;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public interface PortletCategoryManager {

	public JSONArray getPortletsJSONArray(
			HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay)
		throws Exception;

	public void updateSortedPortletCategoryKeys(
		PortalPreferences portalPreferences,
		String[] sortedPortletCategoryKeys);

}