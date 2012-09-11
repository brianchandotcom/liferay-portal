/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetTag;

import java.util.List;
import java.util.Set;

import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo Garcia
 */
public interface Asset {

	public Set<String> addLayoutTags(
		HttpServletRequest request, List<AssetTag> tags);

	public void addPortletBreadcrumbEntries(
			long assetCategoryId, HttpServletRequest request,
			PortletURL portletURL)
		throws Exception;

	public void addPortletBreadcrumbEntry(
			HttpServletRequest request, String title, String url)
		throws Exception;

	public String getAssetKeywords(String className, long classPK)
		throws SystemException;

	public Set<String> getLayoutTagNames(HttpServletRequest request);

	public PortletURL getURLEdit(
			AssetRenderer assetRenderer,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			boolean checkPermissions, PortletURL redirectURL,
			WindowState windowState)
		throws Exception;

	public boolean isValidWord(String word);

	public String substituteCategoryPropertyVariables(
			long groupId, long categoryId, String s)
		throws PortalException, SystemException;

	public String substituteTagPropertyVariables(
			long groupId, String tagName, String s)
		throws PortalException, SystemException;

	public String toWord(String text);

}