/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;

import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Jorge Ferrer
 * @author Juan Fernández
 * @author Raymond Augé
 * @author Sergio González
 */
public abstract class BaseAssetRendererFactory implements AssetRendererFactory {

	public AssetEntry getAssetEntry(long assetEntryId)
		throws PortalException, SystemException {

		return AssetEntryLocalServiceUtil.getEntry(assetEntryId);
	}

	public AssetEntry getAssetEntry(String className, long classPK)
		throws PortalException, SystemException {

		return AssetEntryLocalServiceUtil.getEntry(className, classPK);
	}

	public AssetRenderer getAssetRenderer(long classPK)
		throws PortalException, SystemException {

		return getAssetRenderer(classPK, TYPE_LATEST_APPROVED);
	}

	@SuppressWarnings("unused")
	public AssetRenderer getAssetRenderer(long groupId, String urlTitle)
		throws PortalException, SystemException {

		return null;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public Map<Long, String> getClassTypes(long[] groupId) throws Exception {
		return  null;
	}

	public String getIconPath(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return getIconPath(themeDisplay);
	}

	public String getPortletId() {
		return _portletId;
	}

	@SuppressWarnings("unused")
	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException, SystemException {

		return null;
	}

	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return _PERMISSION;
	}

	public boolean isCategorizable() {
		return true;
	}

	public boolean isLinkable() {
		return _LINKABLE;
	}

	public boolean isSelectable() {
		return _SELECTABLE;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	protected long getControlPanelPlid(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		Group controlPanelGroup = GroupLocalServiceUtil.getGroup(
			themeDisplay.getCompanyId(), GroupConstants.CONTROL_PANEL);

		return LayoutLocalServiceUtil.getDefaultPlid(
			controlPanelGroup.getGroupId(), true);
	}

	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/page.png";
	}

	private static final boolean _LINKABLE = false;

	private static final boolean _PERMISSION = true;

	private static final boolean _SELECTABLE = true;

	private long _classNameId;
	private String _portletId;

}