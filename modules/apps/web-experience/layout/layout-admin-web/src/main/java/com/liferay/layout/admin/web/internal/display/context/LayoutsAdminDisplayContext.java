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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.util.LayoutDescription;
import com.liferay.portal.util.LayoutListUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.layoutsadmin.display.context.GroupDisplayContextHelper;

import java.util.List;

import javax.portlet.PortletURL;

/**
 * @author Eudaldo Alonso
 */
public class LayoutsAdminDisplayContext extends BaseLayoutDisplayContext {

	public LayoutsAdminDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		super(liferayPortletRequest, liferayPortletResponse);

		_groupDisplayContextHelper = new GroupDisplayContextHelper(
			PortalUtil.getHttpServletRequest(liferayPortletRequest));

		this.liferayPortletRequest.setAttribute(
			com.liferay.portal.kernel.util.WebKeys.LAYOUT_DESCRIPTIONS,
			getLayoutDescriptions());
	}

	@Override
	public PortletURL getAddLayoutURL(long selPlid, Boolean privateLayout) {
		PortletURL addLayoutURL = super.getAddLayoutURL(selPlid, privateLayout);

		addLayoutURL.setParameter(
			"backURL",
			PortalUtil.getCurrentURL(
				PortalUtil.getHttpServletRequest(liferayPortletRequest)));

		return addLayoutURL;
	}

	public PortletURL getEditLayoutURL() {
		PortletURL editLayoutURL = super.getEditLayoutURL(
			getSelPlid(), isPrivateLayout());

		editLayoutURL.setParameter("redirect", getRedirect());

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
			themeDisplay.getLocale());

		return _layoutDescriptions;
	}

	@Override
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

	@Override
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

		_redirect = ParamUtil.getString(liferayPortletRequest, "redirect");

		return _redirect;
	}

	public PortletURL getRedirectURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter("groupId", String.valueOf(getSelGroupId()));

		return portletURL;
	}

	@Override
	public Group getSelGroup() {
		return _groupDisplayContextHelper.getSelGroup();
	}

	@Override
	public long getSelGroupId() {
		Group selGroup = getSelGroup();

		if (selGroup != null) {
			return selGroup.getGroupId();
		}

		return 0;
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

	@Override
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

	protected boolean hasPowerUserRole() {
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

	private final GroupDisplayContextHelper _groupDisplayContextHelper;
	private List<LayoutDescription> _layoutDescriptions;
	private Long _layoutId;
	private Organization _organization;
	private String _pagesName;
	private String _redirect;
	private User _selUser;
	private UserGroup _userGroup;

}