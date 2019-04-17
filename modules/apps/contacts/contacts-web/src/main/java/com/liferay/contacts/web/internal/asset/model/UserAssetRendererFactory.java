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

package com.liferay.contacts.web.internal.asset.model;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.contacts.constants.ContactsPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + ContactsPortletKeys.CONCTACTS_CENTER,
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
		userAssetRenderer.setServletContext(_servletContext);

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
		return "user";
	}

	@Override
	public String getPortletId() {
		return ContactsPortletKeys.CONCTACTS_CENTER;
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

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.contacts.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
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
	private ServletContext _servletContext;
	private UserLocalService _userLocalService;

}