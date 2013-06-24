/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

/**
 * @author Jorge Ferrer
 * @author Juan Fernández
 */
public interface AssetRenderer {

	public static final String TEMPLATE_ABSTRACT = "abstract";

	public static final String TEMPLATE_FULL_CONTENT = "full_content";

	public String getAddToPagePortletId() throws Exception;

	public int getAssetRendererType();

	public String[] getAvailableLocales() throws Exception;

	public String getClassName();

	public long getClassPK();

	public String getDiscussionPath();

	public Date getDisplayDate();

	public long getGroupId();

	public String getIconPath(PortletRequest portletRequest);

	public String getNewName(String oldName, String token);

	public String getPreviewPath(
			PortletRequest portletRequest, PortletResponse PortletResponse)
		throws Exception;

	public String getSearchSummary(Locale locale);

	public String getSummary(Locale locale);

	public String getThumbnailPath(PortletRequest portletRequest)
		throws Exception;

	public String getTitle(Locale locale);

	public String getURLDownload(ThemeDisplay themeDisplay);

	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception;

	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState, PortletURL redirectURL)
		throws Exception;

	public PortletURL getURLExport(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception;

	public String getURLImagePreview(PortletRequest portletRequest)
		throws Exception;

	public String getUrlTitle();

	public PortletURL getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception;

	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception;

	public long getUserId();

	public String getUserName();

	public String getUuid();

	public String getViewInContextMessage();

	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException;

	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException;

	public boolean isConvertible();

	public boolean isDisplayable();

	public boolean isLocalizable();

	public boolean isPreviewInContext();

	public boolean isPrintable();

	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception;

	public void setAddToPagePreferences(
			PortletPreferences preferences, String portletId,
			ThemeDisplay themeDisplay)
		throws Exception;

}