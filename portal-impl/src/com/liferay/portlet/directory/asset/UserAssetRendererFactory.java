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

package com.liferay.portlet.directory.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;

import javax.portlet.PortletURL;

/**
 * @author Michael C. Han
 */
public class UserAssetRendererFactory extends BaseAssetRendererFactory {

	public static final String TYPE = "user";

	@Override
	public AssetRenderer getAssetRenderer(long classPK, int type)
		throws PortalException, SystemException {

		User user = UserLocalServiceUtil.getUserById(classPK);

		UserAssetRenderer userAssetRenderer = new UserAssetRenderer(user);

		userAssetRenderer.setAssetRendererType(type);

		return userAssetRenderer;
	}

	@Override
	public AssetRenderer getAssetRenderer(long groupId, String urlTitle)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		User user = UserLocalServiceUtil.getUserByScreenName(
			group.getCompanyId(), urlTitle);

		return new UserAssetRenderer(user);
	}

	@Override
	public String getClassName() {
		return User.class.getName();
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLAdd(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		return null;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return UserPermissionUtil.contains(
			permissionChecker, classPK, actionId);
	}

	@Override
	public boolean isSelectable() {
		return _SELECTABLE;
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/user_icon.png";
	}

	private static final boolean _SELECTABLE = false;

}