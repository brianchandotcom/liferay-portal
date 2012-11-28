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

package com.liferay.portlet.asset.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;

import java.util.Locale;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

/**
 * @author Jorge Ferrer
 * @author Sergio González
 */
public abstract class BaseAssetRenderer implements AssetRenderer {

	public AssetRendererFactory getAssetRendererFactory() {
		if (_assetRendererFactory != null) {
			return _assetRendererFactory;
		}

		_assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getAssetRendererFactoryClassName());

		return _assetRendererFactory;
	}

	public String[] getAvailableLocales() {
		return _AVAILABLE_LOCALES;
	}

	public String getDiscussionPath() {
		return null;
	}

	public String getIconPath(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return getIconPath(themeDisplay);
	}

	public String getSearchSummary(Locale locale) {
		return getSummary(locale);
	}

	public String getURLDownload(ThemeDisplay themeDisplay) {
		return null;
	}

	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		return null;
	}

	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState, PortletURL redirectURL)
		throws Exception {

		LiferayPortletURL editPortletURL =
			(LiferayPortletURL)getURLEdit(
				liferayPortletRequest, liferayPortletResponse);

		if (editPortletURL == null) {
			return null;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		if (group.isLayout()) {
			Layout layout = themeDisplay.getLayout();

			group = layout.getGroup();
		}

		if (group.hasStagingGroup()) {
			return null;
		}

		editPortletURL.setDoAsGroupId(getGroupId());

		editPortletURL.setParameter("redirect", redirectURL.toString());
		editPortletURL.setParameter("originalRedirect", redirectURL.toString());

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletResource = ParamUtil.getString(
			liferayPortletRequest, "portletResource", portletDisplay.getId());

		if (Validator.isNotNull(portletResource)) {
			editPortletURL.setParameter(
				"referringPortletResource", portletResource);
		}
		else {
			editPortletURL.setParameter(
				"referringPortletResource", portletDisplay.getId());
		}

		editPortletURL.setPortletMode(PortletMode.VIEW);
		editPortletURL.setRefererPlid(themeDisplay.getPlid());
		editPortletURL.setWindowState(windowState);

		return editPortletURL;
	}

	public PortletURL getURLExport(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		return null;
	}

	public String getUrlTitle() {
		return null;
	}

	public PortletURL getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception {

		return null;
	}

	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception {

		return null;
	}

	public String getViewInContextMessage() {
		return "view-in-context";
	}

	@SuppressWarnings("unused")
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		return false;
	}

	@SuppressWarnings("unused")
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		return true;
	}

	public boolean isConvertible() {
		return false;
	}

	public boolean isDisplayable() {
		return true;
	}

	public boolean isLocalizable() {
		return false;
	}

	public boolean isPreviewInContext() {
		return false;
	}

	public boolean isPrintable() {
		return false;
	}

	public String renderActions(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		return null;
	}

	protected long getControlPanelPlid(
			LiferayPortletRequest liferayPortletRequest)
		throws PortalException, SystemException {

		return PortalUtil.getControlPanelPlid(liferayPortletRequest);
	}

	protected long getControlPanelPlid(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return PortalUtil.getControlPanelPlid(themeDisplay.getCompanyId());
	}

	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/page.png";
	}

	protected String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest, String noSuchEntryRedirect,
		String path, String primaryKeyParameterName,
		long primaryKeyParameterValue) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		StringBundler sb = new StringBundler(11);

		sb.append(themeDisplay.getPortalURL());
		sb.append(themeDisplay.getPathMain());
		sb.append(path);
		sb.append("?p_l_id=");
		sb.append(themeDisplay.getPlid());
		sb.append("&noSuchEntryRedirect=");
		sb.append(HttpUtil.encodeURL(noSuchEntryRedirect));
		sb.append(StringPool.AMPERSAND);
		sb.append(primaryKeyParameterName);
		sb.append(StringPool.EQUAL);
		sb.append(primaryKeyParameterValue);

		return sb.toString();
	}

	private static final String[] _AVAILABLE_LOCALES = new String[0];

	private AssetRendererFactory _assetRendererFactory;

}