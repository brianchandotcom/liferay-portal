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

package com.liferay.layout.admin.web.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.LayoutDescription;
import com.liferay.portal.util.LayoutListUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.layoutsadmin.display.context.GroupDisplayContextHelper;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutsAdminDisplayContext {

	public LayoutsAdminDisplayContext(
			HttpServletRequest request,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		_request = request;
		_liferayPortletResponse = liferayPortletResponse;

		_groupDisplayContextHelper = new GroupDisplayContextHelper(request);
		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");

		Layout selLayout = getSelLayout();

		if (selLayout != null) {
			privateLayout = selLayout.isPrivateLayout();
		}

		Group liveGroup = getLiveGroup();

		if (liveGroup.isUser() && !isPublicLayoutsModifiable() &&
			isPrivateLayoutsModifiable() && !privateLayout) {

			privateLayout = true;
		}

		Group selGroup = getSelGroup();

		if (selGroup.isLayoutSetPrototype()) {
			privateLayout = true;
		}

		_privateLayout = privateLayout;

		_request.setAttribute(
			com.liferay.portal.kernel.util.WebKeys.LAYOUT_DESCRIPTIONS,
			getLayoutDescriptions());
	}

	public PortletURL getEditLayoutURL() {
		PortletURL editLayoutURL = _liferayPortletResponse.createRenderURL();

		editLayoutURL.setParameter("redirect", getRedirect());
		editLayoutURL.setParameter("groupId", String.valueOf(getSelGroupId()));
		editLayoutURL.setParameter("viewLayout", Boolean.TRUE.toString());

		return editLayoutURL;
	}

	public Group getGroup() {
		return _groupDisplayContextHelper.getGroup();
	}

	public Long getGroupId() {
		return _groupDisplayContextHelper.getGroupId();
	}

	public UnicodeProperties getGroupTypeSettings() {
		return _groupDisplayContextHelper.getGroupTypeSettings();
	}

	public List<LayoutDescription> getLayoutDescriptions() {
		if (_layoutDescriptions != null) {
			return _layoutDescriptions;
		}

		_layoutDescriptions = LayoutListUtil.getLayoutDescriptions(
			getGroupId(), isPrivateLayout(), getRootNodeName(),
			_themeDisplay.getLocale());

		return _layoutDescriptions;
	}

	public Long getLayoutId() {
		if (_layoutId != null) {
			return _layoutId;
		}

		_layoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

		Layout selLayout = getSelLayout();

		if (selLayout != null) {
			_layoutId = selLayout.getLayoutId();
		}

		return _layoutId;
	}

	public Group getLiveGroup() {
		return _groupDisplayContextHelper.getLiveGroup();
	}

	public Long getLiveGroupId() {
		return _groupDisplayContextHelper.getLiveGroupId();
	}

	public Organization getOrganization() {
		if (_organization != null) {
			return _organization;
		}

		Group liveGroup = getLiveGroup();

		if (liveGroup.isOrganization()) {
			_organization = OrganizationLocalServiceUtil.fetchOrganization(
				liveGroup.getOrganizationId());
		}

		return _organization;
	}

	public String getPagesName() {
		if (_pagesName != null) {
			return _pagesName;
		}

		Group liveGroup = getLiveGroup();

		if (liveGroup.isLayoutPrototype() || liveGroup.isLayoutSetPrototype() ||
			liveGroup.isUserGroup()) {

			_pagesName = "pages";
		}
		else if (isPrivateLayout()) {
			if (liveGroup.isUser()) {
				_pagesName = "my-dashboard";
			}
			else {
				_pagesName = "private-pages";
			}
		}
		else {
			if (liveGroup.isUser()) {
				_pagesName = "my-profile";
			}
			else {
				_pagesName = "public-pages";
			}
		}

		return _pagesName;
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_request, "redirect");

		return _redirect;
	}

	public PortletURL getRedirectURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter("groupId", String.valueOf(getSelGroupId()));

		return portletURL;
	}

	public String getRootNodeName() {
		if (_rootNodeName != null) {
			return _rootNodeName;
		}

		Group liveGroup = getLiveGroup();

		_rootNodeName = liveGroup.getLayoutRootNodeName(
			isPrivateLayout(), _themeDisplay.getLocale());

		return _rootNodeName;
	}

	public Group getSelGroup() {
		return _groupDisplayContextHelper.getSelGroup();
	}

	public long getSelGroupId() {
		Group selGroup = getSelGroup();

		if (selGroup != null) {
			return selGroup.getGroupId();
		}

		return 0;
	}

	public Layout getSelLayout() {
		if (_selLayout != null) {
			return _selLayout;
		}

		if (getSelPlid() != LayoutConstants.DEFAULT_PLID) {
			_selLayout = LayoutLocalServiceUtil.fetchLayout(getSelPlid());
		}

		return _selLayout;
	}

	public LayoutSet getSelLayoutSet() throws PortalException {
		if (_selLayoutSet != null) {
			return _selLayoutSet;
		}

		_selLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			getGroupId(), isPrivateLayout());

		return _selLayoutSet;
	}

	public Long getSelPlid() {
		if (_selPlid != null) {
			return _selPlid;
		}

		_selPlid = ParamUtil.getLong(
			_request, "selPlid", LayoutConstants.DEFAULT_PLID);

		return _selPlid;
	}

	public User getSelUser() {
		if (_selUser != null) {
			return _selUser;
		}

		Group liveGroup = getLiveGroup();

		if (liveGroup.isUser()) {
			_selUser = UserLocalServiceUtil.fetchUser(liveGroup.getClassPK());
		}

		return _selUser;
	}

	public Group getStagingGroup() {
		return _groupDisplayContextHelper.getStagingGroup();
	}

	public Long getStagingGroupId() {
		return _groupDisplayContextHelper.getStagingGroupId();
	}

	public UserGroup getUserGroup() {
		if (_userGroup != null) {
			return _userGroup;
		}

		Group liveGroup = getLiveGroup();

		if (liveGroup.isUserGroup()) {
			_userGroup = UserGroupLocalServiceUtil.fetchUserGroup(
				liveGroup.getClassPK());
		}

		return _userGroup;
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	protected boolean hasPowerUserRole() {
		try {
			User selUser = getSelUser();

			return RoleLocalServiceUtil.hasUserRole(
				selUser.getUserId(), _themeDisplay.getCompanyId(),
				RoleConstants.POWER_USER, true);
		}
		catch (Exception e) {
		}

		return false;
	}

	protected boolean isPrivateLayoutsModifiable() {
		if ((!PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_POWER_USER_REQUIRED ||
			 hasPowerUserRole()) &&
			PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED) {

			return true;
		}

		return false;
	}

	protected boolean isPublicLayoutsModifiable() {
		if ((!PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_POWER_USER_REQUIRED ||
			 hasPowerUserRole()) &&
			PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED) {

			return true;
		}

		return false;
	}

	private final GroupDisplayContextHelper _groupDisplayContextHelper;
	private List<LayoutDescription> _layoutDescriptions;
	private Long _layoutId;
	private final LiferayPortletResponse _liferayPortletResponse;
	private Organization _organization;
	private String _pagesName;
	private final boolean _privateLayout;
	private String _redirect;
	private final HttpServletRequest _request;
	private String _rootNodeName;
	private Layout _selLayout;
	private LayoutSet _selLayoutSet;
	private Long _selPlid;
	private User _selUser;
	private final ThemeDisplay _themeDisplay;
	private UserGroup _userGroup;

}