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

package com.liferay.asset.kernel.model;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jorge Ferrer
 * @author Juan Fernández
 */
@ProviderType
public interface AssetRenderer<T> extends Renderer {

	public static final String TEMPLATE_ABSTRACT = "abstract";

	public static final String TEMPLATE_FULL_CONTENT = "full_content";

	public static final String TEMPLATE_PREVIEW = "preview";

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	public String getAddToPagePortletId() throws Exception;

	public T getAssetObject();

	public default T getAssetObject(long versionClassPK) {
		return getAssetObject();
	}

	public AssetRendererFactory<T> getAssetRendererFactory();

	public int getAssetRendererType();

	public String[] getAvailableLanguageIds() throws Exception;

	public DDMFormValuesReader getDDMFormValuesReader();

	public default String getDefaultLanguageId() throws Exception {
		String[] availableLanguageIds = getAvailableLanguageIds();

		String siteDefaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		if (ArrayUtil.isNotEmpty(availableLanguageIds) &&
			!ArrayUtil.contains(availableLanguageIds, siteDefaultLanguageId)) {

			return availableLanguageIds[0];
		}

		return siteDefaultLanguageId;
	}

	public String getDiscussionPath();

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public Date getDisplayDate();

	public long getGroupId();

	public String getNewName(String oldName, String token);

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	public String getPreviewPath(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception;

	public String getSearchSummary(Locale locale);

	public int getStatus();

	public String getSummary();

	public String[] getSupportedConversions();

	public String getThumbnailPath(PortletRequest portletRequest)
		throws Exception;

	public String getURLDownload(ThemeDisplay themeDisplay);

	public default PortletURL getURLEdit(HttpServletRequest httpServletRequest)
		throws Exception {

		return null;
	}

	public default PortletURL getURLEdit(
			HttpServletRequest httpServletRequest, WindowState windowState,
			PortletURL redirectURL)
		throws Exception {

		return null;
	}

	public default PortletURL getURLEdit(
			HttpServletRequest httpServletRequest, WindowState windowState,
			String redirect)
		throws Exception {

		return null;
	}

	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception;

	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState, PortletURL redirectURL)
		throws Exception;

	public default PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState, String redirect)
		throws Exception {

		return null;
	}

	public PortletURL getURLExport(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception;

	public String getURLImagePreview(PortletRequest portletRequest)
		throws Exception;

	public String getUrlTitle();

	public String getUrlTitle(Locale locale);

	public String getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception;

	public PortletURL getURLViewDiffs(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception;

	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception;

	public default String getURLViewUsages(
			HttpServletRequest httpServletRequest)
		throws Exception {

		return StringPool.BLANK;
	}

	public long getUserId();

	public String getUserName();

	public String getUuid();

	public String getViewInContextMessage();

	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException;

	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException;

	public default boolean isCategorizable(long groupId) {
		return true;
	}

	public boolean isCommentable();

	public boolean isConvertible();

	public boolean isDisplayable();

	public boolean isLocalizable();

	public boolean isPreviewInContext();

	public boolean isPrintable();

	public boolean isRatable();

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	public void setAddToPagePreferences(
			PortletPreferences portletPreferences, String portletId,
			ThemeDisplay themeDisplay)
		throws Exception;

}