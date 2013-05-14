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

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.BaseAssetRenderer;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Michael C. Han
 * @author Sergio González
 */
public class UserAssetRenderer extends BaseAssetRenderer {

	public UserAssetRenderer(User user) {
		_user = user;
	}

	@Override
	public String getClassName() {
		return User.class.getName();
	}

	@Override
	public long getClassPK() {
		return _user.getPrimaryKey();
	}

	@Override
	public String getDiscussionPath() {
		return null;
	}

	@Override
	public long getGroupId() {
		return 0;
	}

	@Override
	public String getSummary(Locale locale) {
		return _user.getComments();
	}

	@Override
	public String getTitle(Locale locale) {
		return _user.getFullName();
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			getControlPanelPlid(liferayPortletRequest), PortletKeys.USERS_ADMIN,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/users_admin/edit_user");
		portletURL.setParameter("p_u_i_d", String.valueOf(_user.getUserId()));

		return portletURL;
	}

	@Override
	public String getUrlTitle() {
		return _user.getScreenName();
	}

	@Override
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		return getURLViewInContext(
			liferayPortletRequest, noSuchEntryRedirect, "/directory/find_user",
			"p_u_i_d", _user.getUserId());
	}

	@Override
	public long getUserId() {
		return _user.getUserId();
	}

	@Override
	public String getUserName() {
		return _user.getFullName();
	}

	@Override
	public String getUuid() {
		return _user.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker) {
		return UserPermissionUtil.contains(
			permissionChecker, _user.getUserId(), ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		return UserPermissionUtil.contains(
			permissionChecker, _user.getUserId(), ActionKeys.VIEW);
	}

	@Override
	public boolean isPrintable() {
		return false;
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			renderRequest.setAttribute(WebKeys.USER, _user);

			return "/html/portlet/directory/asset/abstract.jsp";
		}
		else {
			return null;
		}
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/user_icon.png";
	}

	private User _user;

}