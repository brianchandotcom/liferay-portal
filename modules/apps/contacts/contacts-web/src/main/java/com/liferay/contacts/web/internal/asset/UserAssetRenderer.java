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

package com.liferay.contacts.web.internal.asset;

import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.contacts.constants.ContactsWebKeys;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Michael C. Han
 * @author Sergio González
 */
public class UserAssetRenderer extends BaseJSPAssetRenderer<User> {

	public UserAssetRenderer(User user) {
		_user = user;
	}

	@Override
	public User getAssetObject() {
		return _user;
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
	public String getJspPath(HttpServletRequest request, String template) {
		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/asset/abstract.jsp";
		}

		return null;
	}

	@Override
	public int getStatus() {
		return _user.getStatus();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

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

		String portletId = PortletProviderUtil.getPortletId(
			User.class.getName(), PortletProvider.Action.VIEW);

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			getControlPanelPlid(liferayPortletRequest), portletId,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/users_admin/edit_user");
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

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			return _user.getDisplayURL(themeDisplay);
		}
		catch (Exception e) {
		}

		return noSuchEntryRedirect;
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
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		request.setAttribute(ContactsWebKeys.CONTACTS_USER, _user);

		return super.include(request, response, template);
	}

	@Override
	public boolean isPrintable() {
		return false;
	}

	private final User _user;

}