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

package com.liferay.site.memberships.web.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.exportimport.staging.StagingUtil;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SiteMembershipsDisplayContext {

	public SiteMembershipsDisplayContext(
		HttpServletRequest request,
		LiferayPortletResponse liferayPortletResponse) {

		_request = request;
		_liferayPortletResponse = liferayPortletResponse;
	}

	public int getCur() {
		if (_cur != null) {
			return _cur;
		}

		_cur = ParamUtil.getInteger(
			_request, SearchContainer.DEFAULT_CUR_PARAM);

		return _cur;
	}

	public Group getGroup() throws PortalException {
		if (_group != null) {
			return _group;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(
			_request, "groupId", themeDisplay.getSiteGroupId());

		_group = GroupLocalServiceUtil.getGroup(groupId);

		if (_group != null) {
			_group = StagingUtil.getLiveGroup(_group.getGroupId());
		}

		return _group;
	}

	public long getGroupId() throws PortalException {
		Group group = getGroup();

		return group.getGroupId();
	}

	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter("tabs1", getTabs1());
		portletURL.setParameter("groupId", String.valueOf(getGroupId()));

		return portletURL;
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_request, "redirect");

		if (Validator.isNull(_redirect)) {
			PortletURL portletURL = _liferayPortletResponse.createRenderURL();

			_redirect = portletURL.toString();
		}

		return _redirect;
	}

	public User getSelUser() throws PortalException {
		if (_selUser != null) {
			return _selUser;
		}

		_selUser = PortalUtil.getSelectedUser(_request, false);

		return _selUser;
	}

	public String getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(_request, "tabs1", "users");

		return _tabs1;
	}

	public UserGroup getUserGroup() throws PortalException {
		if (_userGroup != null) {
			return _userGroup;
		}

		_userGroup = UserGroupLocalServiceUtil.getUserGroup(getUserGroupId());

		return _userGroup;
	}

	public long getUserGroupId() {
		if (_userGroupId != null) {
			return _userGroupId;
		}

		_userGroupId = ParamUtil.getLong(_request, "userGroupId");

		return _userGroupId;
	}

	public long getUserId() throws PortalException {
		User selUser = getSelUser();

		if (selUser != null) {
			return selUser.getUserId();
		}

		return 0;
	}

	private Integer _cur;
	private Group _group;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _redirect;
	private final HttpServletRequest _request;
	private User _selUser;
	private String _tabs1;
	private UserGroup _userGroup;
	private Long _userGroupId;

}