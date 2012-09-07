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
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetTag;

import java.util.List;
import java.util.Set;

import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Garcia
 * @author Jorge Ferrer
 */
public class AssetUtil {

	public static final char[] INVALID_CHARACTERS = new char[] {
		CharPool.AMPERSAND, CharPool.APOSTROPHE, CharPool.AT,
		CharPool.BACK_SLASH, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.COLON, CharPool.COMMA, CharPool.EQUAL, CharPool.GREATER_THAN,
		CharPool.FORWARD_SLASH, CharPool.LESS_THAN, CharPool.NEW_LINE,
		CharPool.OPEN_BRACKET, CharPool.OPEN_CURLY_BRACE, CharPool.PERCENT,
		CharPool.PIPE, CharPool.PLUS, CharPool.POUND, CharPool.QUESTION,
		CharPool.QUOTE, CharPool.RETURN, CharPool.SEMICOLON, CharPool.SLASH,
		CharPool.STAR, CharPool.TILDE
	};

	public static Set<String> addLayoutTags(
			HttpServletRequest request, List<AssetTag> tags) {
		return getAsset().addLayoutTags(request, tags);
	}

	public static void addPortletBreadcrumbEntries(
			long assetCategoryId, HttpServletRequest request,
			PortletURL portletURL)
		throws Exception {

		getAsset().addPortletBreadcrumbEntries(
			assetCategoryId, request, portletURL);
	}

	public static void addPortletBreadcrumbEntry(
			HttpServletRequest request, String title, String url)
		throws Exception {

		getAsset().addPortletBreadcrumbEntry(request, title, url);
	}

	public static Asset getAsset() {
		PortalRuntimePermission.checkGetBeanProperty(Asset.class);

		return _asset;
	}

	public static String getAssetKeywords(String className, long classPK)
		throws SystemException {

		return getAsset().getAssetKeywords(className, classPK);
	}

	public static Set<String> getLayoutTagNames(HttpServletRequest request) {
		return getAsset().getLayoutTagNames(request);
	}

	public static PortletURL getURLEdit(
			AssetRenderer assetRenderer,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			boolean checkPermissions, PortletURL redirectURL,
			WindowState windowState)
		throws Exception {

		return getAsset().getURLEdit(
			assetRenderer, liferayPortletRequest, liferayPortletResponse,
			checkPermissions, redirectURL, windowState);
	}

	public static boolean isValidWord(String word) {
		return getAsset().isValidWord(word);
	}

	public static String substituteCategoryPropertyVariables(
			long groupId, long categoryId, String s)
		throws PortalException, SystemException {

		return getAsset().substituteCategoryPropertyVariables(
			groupId, categoryId, s);
	}

	public static String substituteTagPropertyVariables(
			long groupId, String tagName, String s)
		throws PortalException, SystemException {

		return getAsset().substituteTagPropertyVariables(groupId, tagName, s);
	}

	public static String toWord(String text) {
		return getAsset().toWord(text);
	}

	public void setAsset(Asset asset) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_asset = asset;
	}

	private static Asset _asset;

}