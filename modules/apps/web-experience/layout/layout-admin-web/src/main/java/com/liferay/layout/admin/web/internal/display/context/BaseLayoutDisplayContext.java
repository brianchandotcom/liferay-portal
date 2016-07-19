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

import com.liferay.application.list.GroupProvider;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.layout.admin.web.internal.constants.LayoutAdminPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Julio Camarero
 */
public class BaseLayoutDisplayContext {

	public BaseLayoutDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		this.liferayPortletRequest = liferayPortletRequest;
		this.liferayPortletResponse = liferayPortletResponse;

		groupProvider = (GroupProvider)liferayPortletRequest.getAttribute(
			ApplicationListWebKeys.GROUP_PROVIDER);
		themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public PortletURL getAddLayoutURL(long selPlid, Boolean privateLayout) {
		PortletURL addLayoutURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		addLayoutURL.setParameter("mvcPath", "/add_layout.jsp");

		if (selPlid >= LayoutConstants.DEFAULT_PLID) {
			addLayoutURL.setParameter("selPlid", String.valueOf(selPlid));
		}

		addLayoutURL.setParameter("groupId", String.valueOf(getSelGroupId()));

		if (privateLayout != null) {
			addLayoutURL.setParameter(
				"privateLayout", String.valueOf(privateLayout));
		}

		return addLayoutURL;
	}

	public PortletURL getEditLayoutURL(long selPlid, Boolean privateLayout) {
		PortletURL editLayoutURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		if (selPlid >= LayoutConstants.DEFAULT_PLID) {
			editLayoutURL.setParameter("selPlid", String.valueOf(selPlid));
		}

		if (privateLayout != null) {
			editLayoutURL.setParameter(
				"privateLayout", String.valueOf(privateLayout));
		}

		Group selGroup = getSelGroup();

		editLayoutURL.setParameter(
			"backURL", PortalUtil.getCurrentURL(liferayPortletRequest));
		editLayoutURL.setParameter(
			"groupId", String.valueOf(selGroup.getGroupId()));

		return editLayoutURL;
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

	public String getRootNodeName() {
		if (_rootNodeName != null) {
			return _rootNodeName;
		}

		_rootNodeName = getRootNodeName(isPrivateLayout());

		return _rootNodeName;
	}

	public String getRootNodeName(boolean privateLayout) {
		Group liveGroup = getLiveGroup();

		return liveGroup.getLayoutRootNodeName(
			privateLayout, themeDisplay.getLocale());
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

		Group group = getStagingGroup();

		if (group == null) {
			group = getLiveGroup();
		}

		_selLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			group.getGroupId(), isPrivateLayout());

		return _selLayoutSet;
	}

	public Long getSelPlid() {
		if (_selPlid != null) {
			return _selPlid;
		}

		_selPlid = ParamUtil.getLong(
			liferayPortletRequest, "selPlid", LayoutConstants.DEFAULT_PLID);

		return _selPlid;
	}

	public boolean isPrivateLayout() {
		if (_privateLayout != null) {
			return _privateLayout;
		}

		Group selGroup = getSelGroup();

		if (selGroup.isLayoutSetPrototype() ||
			selGroup.isLayoutSetPrototype()) {

			_privateLayout = true;

			return _privateLayout;
		}

		Layout selLayout = getSelLayout();

		if (getSelLayout() != null) {
			_privateLayout = selLayout.isPrivateLayout();

			return _privateLayout;
		}

		Layout layout = themeDisplay.getLayout();

		if (!layout.isTypeControlPanel()) {
			_privateLayout = layout.isPrivateLayout();

			return _privateLayout;
		}

		_privateLayout = ParamUtil.getBoolean(
			liferayPortletRequest, "privateLayout");

		return _privateLayout;
	}

	public boolean isShowAddRootLayoutButton() throws PortalException {
		return GroupPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), getSelGroup(),
			ActionKeys.ADD_LAYOUT);
	}

	protected Group getLiveGroup() {
		if (_liveGroup != null) {
			return _liveGroup;
		}

		_liveGroup = StagingUtil.getLiveGroup(getSelGroupId());

		if (_liveGroup == null) {
			_liveGroup = getSelGroup();
		}

		return _liveGroup;
	}

	protected Group getSelGroup() {
		if (_selGroup != null) {
			return _selGroup;
		}

		_selGroup = groupProvider.getGroup(
			PortalUtil.getHttpServletRequest(liferayPortletRequest));

		return _selGroup;
	}

	protected Group getStagingGroup() {
		if (_stagingGroup != null) {
			return _stagingGroup;
		}

		_stagingGroup = StagingUtil.getStagingGroup(getSelGroupId());

		if (_stagingGroup == null) {
			_stagingGroup = getSelGroup();
		}

		return _stagingGroup;
	}

	protected final GroupProvider groupProvider;
	protected final LiferayPortletRequest liferayPortletRequest;
	protected final LiferayPortletResponse liferayPortletResponse;
	protected final ThemeDisplay themeDisplay;

	private Long _layoutId;
	private Group _liveGroup;
	private Boolean _privateLayout;
	private String _rootNodeName;
	private Group _selGroup;
	private Layout _selLayout;
	private LayoutSet _selLayoutSet;
	private Long _selPlid;
	private Group _stagingGroup;

}