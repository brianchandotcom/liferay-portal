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

package com.liferay.site.memberships.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SiteMembershipsDisplayContext {

	public SiteMembershipsDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
	}

	public Group getGroup() {
		if (_group != null) {
			return _group;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(
			_httpServletRequest, "groupId",
			themeDisplay.getSiteGroupIdOrLiveGroupId());

		_group = GroupLocalServiceUtil.fetchGroup(groupId);

		return _group;
	}

	public long getGroupId() {
		Group group = getGroup();

		if (group == null) {
			return 0;
		}

		return group.getGroupId();
	}

	public List<NavigationItem> getInfoPanelNavigationItems() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(themeDisplay.getURLCurrent());
						navigationItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "details"));
					});
			}
		};
	}

	public PortletURL getPortletURL() {
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

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

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

		_selUser = PortalUtil.getSelectedUser(_httpServletRequest, false);

		return _selUser;
	}

	public String getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(_httpServletRequest, "tabs1", "users");

		return _tabs1;
	}

	public long getUserGroupId() {
		if (_userGroupId != null) {
			return _userGroupId;
		}

		_userGroupId = ParamUtil.getLong(_httpServletRequest, "userGroupId");

		return _userGroupId;
	}

	public long getUserId() throws PortalException {
		User selUser = getSelUser();

		if (selUser != null) {
			return selUser.getUserId();
		}

		return 0;
	}

	public List<NavigationItem> getViewNavigationItems() {
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(
							Objects.equals(getTabs1(), "users"));
						navigationItem.setHref(
							getPortletURL(), "tabs1", "users");
						navigationItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "users"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(
							Objects.equals(getTabs1(), "organizations"));
						navigationItem.setHref(
							getPortletURL(), "tabs1", "organizations");
						navigationItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "organizations"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(
							Objects.equals(getTabs1(), "user-groups"));
						navigationItem.setHref(
							getPortletURL(), "tabs1", "user-groups");
						navigationItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "user-groups"));
					});
			}
		};
	}

	private Group _group;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _redirect;
	private User _selUser;
	private String _tabs1;
	private Long _userGroupId;

}