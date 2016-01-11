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

package com.liferay.directory.web.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PortletKeys.DIRECTORY,
		"javax.portlet.name=" + PortletKeys.FRIENDS_DIRECTORY,
		"javax.portlet.name=" + PortletKeys.MY_SITES_DIRECTORY,
		"javax.portlet.name=" + PortletKeys.SITE_MEMBERS_DIRECTORY
	},
	service = AssetRendererFactory.class
)
public class UserAssetRendererFactory extends BaseAssetRendererFactory<User> {

	public static final String TYPE = "user";

	public UserAssetRendererFactory() {
		setSearchable(true);
		setSelectable(false);
	}

	@Override
	public AssetRenderer<User> getAssetRenderer(long classPK, int type)
		throws PortalException {

		User user = _userLocalService.getUserById(classPK);

		UserAssetRenderer userAssetRenderer = new UserAssetRenderer(user);

		userAssetRenderer.setAssetRendererType(type);

		return userAssetRenderer;
	}

	@Override
	public AssetRenderer<User> getAssetRenderer(long groupId, String urlTitle)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		User user = _userLocalService.getUserByScreenName(
			group.getCompanyId(), urlTitle);

		return new UserAssetRenderer(user);
	}

	@Override
	public String getClassName() {
		return User.class.getName();
	}

	@Override
	public String getIconCssClass() {
		return "icon-user";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return UserPermissionUtil.contains(
			permissionChecker, classPK, actionId);
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/user_icon.png";
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private GroupLocalService _groupLocalService;
	private UserLocalService _userLocalService;

}