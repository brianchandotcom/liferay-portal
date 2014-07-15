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
public class LayoutDisplayContext {

	public LayoutDisplayContext(
			HttpServletRequest request,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		_request = request;
		_liferayPortletResponse = liferayPortletResponse;

		_tabs1 = ParamUtil.getString(request, "tabs1", "public-pages");

		_privateLayout = false;

		if (_tabs1.equals("my-dashboard") || _tabs1.equals("private-pages")) {
			_privateLayout = true;
		}

		if (getSelLayout() != null) {
			_privateLayout = getSelLayout().isPrivateLayout();
		}

		if (getLiveGroup().isUser() && !isPublicLayoutsModifiable() &&
			isPrivateLayoutsModifiable() && !_privateLayout) {

			_tabs1 = "my-dashboard";

			_privateLayout = true;
		}

		if (getSelGroup().isLayoutSetPrototype()) {
			_privateLayout = true;
		}
	}

	public String getBackURL() {
		if (_backURL != null) {
			return _backURL;
		}

		_backURL = ParamUtil.getString(_request, "backURL", getRedirect());

		return _backURL;
	}

	public Group getGroup() {
		if (_group != null) {
			return _group;
		}

		if (getStagingGroup() != null) {
			_group = getStagingGroup();
		}
		else {
			_group = getLiveGroup();
		}

		return _group;
	}

	public Long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = getLiveGroup().getGroupId();

		if (getGroup() != null) {
			_groupId = getGroup().getGroupId();
		}

		return _groupId;
	}

	public UnicodeProperties getGroupTypeSettings() {
		if (_groupTypeSettings != null) {
			return _groupTypeSettings;
		}

		if (getGroup() != null) {
			_groupTypeSettings = getGroup().getTypeSettingsProperties();
		}
		else {
			_groupTypeSettings = new UnicodeProperties();
		}

		return _groupTypeSettings;
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

		if (getSelLayout() != null) {
			_layoutId = getSelLayout().getLayoutId();
		}

		return _layoutId;
	}

	public Group getLiveGroup() {
		if (_liveGroup != null) {
			return _liveGroup;
		}

		if (getSelGroup().isStagingGroup()) {
			_liveGroup = getSelGroup().getLiveGroup();
		}
		else {
			_liveGroup = getSelGroup();
		}

		return _liveGroup;
	}

	public Long getLiveGroupId() {
		if (_liveGroupId != null) {
			return _liveGroupId;
		}

		_liveGroupId = getLiveGroup().getGroupId();

		return _liveGroupId;
	}

	public Organization getOrganization() {
		if (_organization != null) {
			return _organization;
		}

		if (getLiveGroup().isOrganization()) {
			_organization = OrganizationLocalServiceUtil.fetchOrganization(
				getLiveGroup().getOrganizationId());
		}

		return _organization;
	}

	public String getPagesName() {
		if (_pagesName != null) {
			return _pagesName;
		}

		if (getLiveGroup().isLayoutPrototype() ||
			getLiveGroup().isLayoutSetPrototype() ||
			getLiveGroup().isUserGroup()) {

			_pagesName = "pages";
		}
		else if (isPrivateLayout()) {
			if (getLiveGroup().isUser()) {
				_pagesName = "my-dashboard";
			}
			else {
				_pagesName = "private-pages";
			}
		}
		else {
			if (getLiveGroup().isUser()) {
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

		_rootNodeName = getLiveGroup().getLayoutRootNodeName(
			isPrivateLayout(), themeDisplay.getLocale());

		return _rootNodeName;
	}

	public Group getSelGroup() {
		if (_selGroup != null) {
			return _selGroup;
		}

		_selGroup = (Group)_request.getAttribute(WebKeys.GROUP);

		return _selGroup;
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

		if (getLiveGroup().isUser()) {
			_selUser = UserLocalServiceUtil.fetchUser(
				getLiveGroup().getClassPK());
		}

		return _selUser;
	}

	public Group getStagingGroup() {
		if (_stagingGroup != null) {
			return _stagingGroup;
		}

		Group selGroup = getSelGroup();

		if (selGroup.isStagingGroup()) {
			_stagingGroup = selGroup;
		}
		else {
			if (selGroup.isStaged()) {
				if (selGroup.isStagedRemotely()) {
					_stagingGroup = selGroup;
				}
				else {
					_stagingGroup = selGroup.getStagingGroup();
				}
			}
		}

		return _stagingGroup;
	}

	public Long getStagingGroupId() {
		if (_stagingGroupId != null) {
			return _stagingGroupId;
		}

		_stagingGroupId = 0L;

		if (getStagingGroup() != null) {
			_stagingGroupId = getStagingGroup().getGroupId();
		}

		return _stagingGroupId;
	}

	public String getTabs1() {
		return _tabs1;
	}

	public String getTabs1Names() throws PortalException {
		if (_tabs1Names != null) {
			return _tabs1Names;
		}

		_tabs1Names = "public-pages,private-pages";

		if (getLiveGroup().isUser()) {
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

		if (getLiveGroup().isUserGroup()) {
			_userGroup = UserGroupLocalServiceUtil.fetchUserGroup(
				getLiveGroup().getClassPK());
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

	protected boolean hasPowerUserRole() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay) _request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return RoleLocalServiceUtil.hasUserRole(
			getSelUser().getUserId(), themeDisplay.getCompanyId(),
			RoleConstants.POWER_USER, true);
	}

	protected boolean isPrivateLayoutsModifiable() throws PortalException {
		if ((!PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_POWER_USER_REQUIRED ||
			 hasPowerUserRole()) &&
			PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED) {

			return true;
		}

		return false;
	}

	protected boolean isPublicLayoutsModifiable() throws PortalException {
		if ((!PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_POWER_USER_REQUIRED ||
			 hasPowerUserRole()) &&
			PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED) {

			return true;
		}

		return false;
	}

	private String _backURL;
	private Group _group;
	private Long _groupId;
	private UnicodeProperties _groupTypeSettings;
	private List<LayoutDescription> _layoutDescriptions;
	private Long _layoutId;
	private LiferayPortletResponse _liferayPortletResponse;
	private Group _liveGroup;
	private Long _liveGroupId;
	private Organization _organization;
	private String _pagesName;
	private boolean _privateLayout;
	private String _redirect;
	private HttpServletRequest _request;
	private String _rootNodeName;
	private Group _selGroup;
	private Layout _selLayout;
	private LayoutSet _selLayoutSet;
	private Long _selPlid;
	private User _selUser;
	private Group _stagingGroup;
	private Long _stagingGroupId;
	private String _tabs1;
	private String _tabs1Names;
	private UserGroup _userGroup;

}