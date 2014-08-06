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

package com.liferay.portlet.layoutsadmin.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
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
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.LayoutDescription;
import com.liferay.portal.util.LayoutListUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;

import java.util.List;

import javax.portlet.PortletConfig;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutsAdminDisplayContext {

	public LayoutsAdminDisplayContext(
		HttpServletRequest request,
		LiferayPortletResponse liferayPortletResponse) {

		_request = request;
		_liferayPortletResponse = liferayPortletResponse;

		_groupDisplayContextHelper = new GroupDisplayContextHelper(request);

		_tabs1 = ParamUtil.getString(request, "tabs1", "public-pages");

		_privateLayout = false;

		if (_tabs1.equals("my-dashboard") || _tabs1.equals("private-pages")) {
			_privateLayout = true;
		}

		Layout selLayout = getSelLayout();

		if (selLayout != null) {
			_privateLayout = selLayout.isPrivateLayout();
		}

		Group liveGroup = getLiveGroup();

		if (liveGroup.isUser() && !isPublicLayoutsModifiable() &&
			isPrivateLayoutsModifiable() && !_privateLayout) {

			_tabs1 = "my-dashboard";

			_privateLayout = true;
		}

		Group selGroup = getSelGroup();

		if (selGroup.isLayoutSetPrototype()) {
			_privateLayout = true;
		}

		String portletName = getPortletName();

		if (portletName.equals(PortletKeys.LAYOUTS_ADMIN) ||
			portletName.equals(PortletKeys.MY_ACCOUNT)) {

			ThemeDisplay themeDisplay = (ThemeDisplay) _request.getAttribute(
				WebKeys.THEME_DISPLAY);

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			portletDisplay.setURLBack(getBackURL());
		}

		_request.setAttribute(
			com.liferay.portal.util.WebKeys.LAYOUT_DESCRIPTIONS,
			getLayoutDescriptions());
	}

	public String getBackURL() {
		if (_backURL != null) {
			return _backURL;
		}

		_backURL = ParamUtil.getString(_request, "backURL", getRedirect());

		return _backURL;
	}

	public PortletURL getEditLayoutURL() {
		String closeRedirect = ParamUtil.getString(_request, "closeRedirect");

		PortletURL editLayoutURL = _liferayPortletResponse.createRenderURL();

		editLayoutURL.setParameter(
			"struts_action", "/layouts_admin/edit_layouts");
		editLayoutURL.setParameter("tabs1", getTabs1());
		editLayoutURL.setParameter("redirect", getRedirect());
		editLayoutURL.setParameter("closeRedirect", closeRedirect);

		String portletName = getPortletName();

		if (portletName.equals(PortletKeys.LAYOUTS_ADMIN) ||
			portletName.equals(PortletKeys.MY_ACCOUNT) ||
			portletName.equals(PortletKeys.USERS_ADMIN)) {

			editLayoutURL.setParameter("backURL", getBackURL());
		}

		editLayoutURL.setParameter("groupId", String.valueOf(getLiveGroupId()));
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

		ThemeDisplay themeDisplay = (ThemeDisplay) _request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_layoutDescriptions = LayoutListUtil.getLayoutDescriptions(
			getGroupId(), isPrivateLayout(), getRootNodeName(),
			themeDisplay.getLocale());

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

		portletURL.setParameter("struts_action", "/layouts_admin/edit_layouts");
		portletURL.setParameter("tabs1", getTabs1());
		portletURL.setParameter("redirect", getRedirect());

		String portletName = getPortletName();

		if (portletName.equals(PortletKeys.LAYOUTS_ADMIN) ||
			portletName.equals(PortletKeys.MY_ACCOUNT) ||
			portletName.equals(PortletKeys.USERS_ADMIN)) {

			portletURL.setParameter("backURL", getBackURL());
		}

		portletURL.setParameter("groupId", String.valueOf(getLiveGroupId()));

		return portletURL;
	}

	public String getRootNodeName() {
		if (_rootNodeName != null) {
			return _rootNodeName;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay) _request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group liveGroup = getLiveGroup();

		_rootNodeName = liveGroup.getLayoutRootNodeName(
			isPrivateLayout(), themeDisplay.getLocale());

		return _rootNodeName;
	}

	public Group getSelGroup() {
		return _groupDisplayContextHelper.getSelGroup();
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

	public String getTabs1() {
		return _tabs1;
	}

	public String getTabs1Names() {
		if (_tabs1Names != null) {
			return _tabs1Names;
		}

		_tabs1Names = "public-pages,private-pages";

		Group liveGroup = getLiveGroup();

		if (liveGroup.isUser()) {
			if (isPrivateLayoutsModifiable() && isPublicLayoutsModifiable()) {
				_tabs1Names = "my-profile,my-dashboard";
			}
			else if (isPrivateLayoutsModifiable()) {
				_tabs1Names = "my-dashboard";
			}
			else if (isPublicLayoutsModifiable()) {
				_tabs1Names = "my-profile";
			}
		}

		return _tabs1Names;
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

	protected String getPortletName() {
		PortletConfig portletConfig = (PortletConfig)_request.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		if (portletConfig == null) {
			return StringPool.BLANK;
		}

		return portletConfig.getPortletName();
	}

	protected boolean hasPowerUserRole() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			User selUser = getSelUser();

			return RoleLocalServiceUtil.hasUserRole(
				selUser.getUserId(), themeDisplay.getCompanyId(),
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

	private String _backURL;
	private GroupDisplayContextHelper _groupDisplayContextHelper;
	private List<LayoutDescription> _layoutDescriptions;
	private Long _layoutId;
	private LiferayPortletResponse _liferayPortletResponse;
	private Organization _organization;
	private String _pagesName;
	private boolean _privateLayout;
	private String _redirect;
	private HttpServletRequest _request;
	private String _rootNodeName;
	private Layout _selLayout;
	private LayoutSet _selLayoutSet;
	private Long _selPlid;
	private User _selUser;
	private String _tabs1;
	private String _tabs1Names;
	private UserGroup _userGroup;

}