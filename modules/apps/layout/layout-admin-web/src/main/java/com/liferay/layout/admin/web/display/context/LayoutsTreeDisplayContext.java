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

import com.liferay.application.list.util.LatentGroupManagerUtil;
import com.liferay.layout.admin.web.constants.LayoutAdminPortletKeys;
import com.liferay.portal.NoSuchLayoutSetBranchException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.exportimport.staging.StagingUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Julio Camarero
 */
public class LayoutsTreeDisplayContext {

	public LayoutsTreeDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public PortletURL getAddLayoutURL(long selPlid, Boolean privateLayout) {
		PortletURL addPagesURL = PortalUtil.getControlPanelPortletURL(
			_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		addPagesURL.setParameter("mvcPath", "/add_layout.jsp");

		if (selPlid >= LayoutConstants.DEFAULT_PLID) {
			addPagesURL.setParameter("selPlid", String.valueOf(selPlid));
		}

		addPagesURL.setParameter("groupId", String.valueOf(getSelGroupId()));

		if (privateLayout != null) {
			addPagesURL.setParameter(
				"privateLayout", String.valueOf(privateLayout));
		}

		return addPagesURL;
	}

	public Long getCurSelPlid() {
		Long curSelPlid = null;

		if (getSelPlid() != LayoutConstants.DEFAULT_PLID) {
			curSelPlid = getSelPlid();
		}
		else {
			Layout layout = _themeDisplay.getLayout();

			if (!layout.isTypeControlPanel()) {
				curSelPlid = layout.getPlid();
			}
		}

		return curSelPlid;
	}

	public PortletURL getEditLayoutURL(long selPlid, Boolean privateLayout) {
		PortletURL editPublicLayoutURL = PortalUtil.getControlPanelPortletURL(
			_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		if (selPlid >= LayoutConstants.DEFAULT_PLID) {
			editPublicLayoutURL.setParameter(
				"selPlid", String.valueOf(selPlid));
		}

		if (privateLayout != null) {
			editPublicLayoutURL.setParameter(
				"privateLayout", String.valueOf(privateLayout));
		}

		Group liveGroup = getLiveGroup();

		editPublicLayoutURL.setParameter(
			"groupId", String.valueOf(liveGroup.getGroupId()));
		editPublicLayoutURL.setParameter("viewLayout", Boolean.TRUE.toString());

		return editPublicLayoutURL;
	}

	public String getJSSafeEditLayoutTitle() {
		String value = UnicodeLanguageUtil.format(
			getHttpServletRequest(), "edit-x", "{label}", false);

		return StringUtil.replace(
			value, UnicodeLanguageUtil.get(getHttpServletRequest(), "{label}"),
			"{label}");
	}

	public String getLayoutSetBranchCssClass(LayoutSetBranch layoutSetBranch)
		throws PortalException {

		if (isLayoutSetBranchSelected(layoutSetBranch)) {
			return "disabled";
		}

		return StringPool.BLANK;
	}

	public List<LayoutSetBranch> getLayoutSetBranches() {
		if (_layoutSetBranches != null) {
			return _layoutSetBranches;
		}

		Group stagingGroup = getStagingGroup();

		_layoutSetBranches =
			LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(
				stagingGroup.getGroupId(), isPrivateLayout());

		return _layoutSetBranches;
	}

	public String getLayoutSetBranchName() throws PortalException {
		LayoutSetBranch layoutSetBranch = getLayoutSetBranch();

		return LanguageUtil.get(
			getHttpServletRequest(), layoutSetBranch.getName());
	}

	public String getLayoutSetBranchURL(LayoutSetBranch layoutSetBranch)
		throws PortalException {

		if (isLayoutSetBranchSelected(layoutSetBranch)) {
			return null;
		}

		PortletURL layoutSetBranchURL = PortalUtil.getControlPanelPortletURL(
			_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		layoutSetBranchURL.setParameter("mvcPath", "/view.jsp");
		layoutSetBranchURL.setParameter(
			"groupId", String.valueOf(layoutSetBranch.getGroupId()));
		layoutSetBranchURL.setParameter(
			"privateLayout", String.valueOf(isPrivateLayout()));
		layoutSetBranchURL.setParameter(
			"layoutSetBranchId",
			String.valueOf(layoutSetBranch.getLayoutSetBranchId()));

		return layoutSetBranchURL.toString();
	}

	public Map<String, Object> getLayoutSetBranchURLData() {
		Map<String, Object> data = new HashMap<>();

		data.put("navigation", Boolean.TRUE.toString());

		return data;
	}

	public String getLayoutSetName(boolean privateLayout) {
		Group liveGroup = getLiveGroup();

		return liveGroup.getLayoutRootNodeName(
			privateLayout, _themeDisplay.getLocale());
	}

	public Map<String, PortletURL> getPortletURLs() {
		Map<String, PortletURL> portletURLs = new HashMap<>();

		portletURLs.put(
			"addLayoutURL",
			getAddLayoutURL(LayoutConstants.DEFAULT_PLID, null));
		portletURLs.put(
			"editLayoutURL",
			getEditLayoutURL(LayoutConstants.DEFAULT_PLID, null));

		return portletURLs;
	}

	public String getPrivateLayoutsURL() {
		Group selGroup = getSelGroup();

		if (!selGroup.hasPrivateLayouts()) {
			return null;
		}

		return selGroup.getDisplayURL(_themeDisplay, true);
	}

	public String getPublicLayoutsURL() {
		Group selGroup = getSelGroup();

		if (!selGroup.hasPublicLayouts()) {
			return null;
		}

		return selGroup.getDisplayURL(_themeDisplay, false);
	}

	public long getSelGroupId() {
		Group selGroup = getSelGroup();

		if (selGroup != null) {
			return selGroup.getGroupId();
		}

		return 0;
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

		Layout layout = _themeDisplay.getLayout();

		if (!layout.isTypeControlPanel()) {
			_privateLayout = layout.isPrivateLayout();

			return _privateLayout;
		}

		_privateLayout = ParamUtil.getBoolean(
			_liferayPortletRequest, "privateLayout");

		return _privateLayout;
	}

	public boolean isShowAddLayoutButton() throws PortalException {
		return LayoutPermissionUtil.contains(
			_themeDisplay.getPermissionChecker(), getSelLayout(),
			ActionKeys.ADD_LAYOUT);
	}

	public boolean isShowAddRootLayoutButton() throws PortalException {
		return GroupPermissionUtil.contains(
			_themeDisplay.getPermissionChecker(), getSelGroup(),
			ActionKeys.ADD_LAYOUT);
	}

	public boolean isShowEditLayoutSetButton() throws PortalException {
		return GroupPermissionUtil.contains(
			_themeDisplay.getPermissionChecker(), getSelGroup(),
			ActionKeys.MANAGE_LAYOUTS);
	}

	public boolean isShowLayoutSetBranchesSelector() {
		Group stagingGroup = getStagingGroup();

		if (!stagingGroup.isStaged() ||
			(getSelGroupId() != stagingGroup.getGroupId())) {

			return false;
		}

		List<LayoutSetBranch> layoutSetBranches = getLayoutSetBranches();

		if (layoutSetBranches.size() < 2) {
			return false;
		}

		return true;
	}

	public boolean isShowLayoutTabs() {
		Group selGroup = getSelGroup();

		if (selGroup.isLayoutPrototype()) {
			return false;
		}

		return true;
	}

	public boolean isShowPublicLayoutsTree() {
		Group selGroup = getSelGroup();

		if (selGroup.isLayoutSetPrototype() || selGroup.isLayoutPrototype()) {
			return false;
		}

		return true;
	}

	protected HttpServletRequest getHttpServletRequest() {
		if (_httpServletRequest != null) {
			return _httpServletRequest;
		}

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			_liferayPortletRequest);

		return _httpServletRequest;
	}

	protected HttpSession getHttpSession() {
		HttpServletRequest httpServletRequest = getHttpServletRequest();

		return httpServletRequest.getSession();
	}

	protected LayoutSetBranch getLayoutSetBranch() throws PortalException {
		if (_layoutSetBranch != null) {
			return _layoutSetBranch;
		}

		long layoutSetBranchId = ParamUtil.getLong(
			_liferayPortletRequest, "layoutSetBranchId");

		if (layoutSetBranchId <= 0) {
			LayoutSet selLayoutSet = getSelLayoutSet();

			layoutSetBranchId = StagingUtil.getRecentLayoutSetBranchId(
				_themeDisplay.getUser(), selLayoutSet.getLayoutSetId());
		}

		if (layoutSetBranchId > 0) {
			_layoutSetBranch =
				LayoutSetBranchLocalServiceUtil.fetchLayoutSetBranch(
					layoutSetBranchId);
		}

		if (_layoutSetBranch == null) {
			try {
				Group stagingGroup = getStagingGroup();

				_layoutSetBranch =
					LayoutSetBranchLocalServiceUtil.getMasterLayoutSetBranch(
						stagingGroup.getGroupId(), isPrivateLayout());
			}
			catch (NoSuchLayoutSetBranchException nslsbe) {
			}
		}

		return _layoutSetBranch;
	}

	protected Group getLiveGroup() {
		if (_liveGroup != null) {
			return _liveGroup;
		}

		_liveGroup = StagingUtil.getLiveGroup(getSelGroupId());

		return _liveGroup;
	}

	protected Group getSelGroup() {
		if (_selGroup != null) {
			return _selGroup;
		}

		_selGroup = _themeDisplay.getScopeGroup();

		if (_selGroup.isControlPanel()) {
			_selGroup = LatentGroupManagerUtil.getLatentGroup(getHttpSession());
		}

		return _selGroup;
	}

	protected Layout getSelLayout() {
		if (_selLayout != null) {
			return _selLayout;
		}

		if (getSelPlid() != LayoutConstants.DEFAULT_PLID) {
			_selLayout = LayoutLocalServiceUtil.fetchLayout(getSelPlid());
		}

		return _selLayout;
	}

	protected LayoutSet getSelLayoutSet() throws PortalException {
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

	protected Long getSelPlid() {
		if (_selPlid != null) {
			return _selPlid;
		}

		_selPlid = ParamUtil.getLong(
			_liferayPortletRequest, "selPlid", LayoutConstants.DEFAULT_PLID);

		return _selPlid;
	}

	protected Group getStagingGroup() {
		if (_stagingGroup != null) {
			return _stagingGroup;
		}

		_stagingGroup = StagingUtil.getStagingGroup(getSelGroupId());

		return _stagingGroup;
	}

	protected boolean isLayoutSetBranchSelected(LayoutSetBranch layoutSetBranch)
		throws PortalException {

		LayoutSetBranch curLayoutSetBranch = getLayoutSetBranch();

		if (curLayoutSetBranch == null) {
			return false;
		}

		if (curLayoutSetBranch.getLayoutSetBranchId() ==
				layoutSetBranch.getLayoutSetBranchId()) {

			return true;
		}

		return false;
	}

	private HttpServletRequest _httpServletRequest;
	private LayoutSetBranch _layoutSetBranch;
	private List<LayoutSetBranch> _layoutSetBranches;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private Group _liveGroup;
	private Boolean _privateLayout;
	private Group _selGroup;
	private Layout _selLayout;
	private LayoutSet _selLayoutSet;
	private Long _selPlid;
	private Group _stagingGroup;
	private final ThemeDisplay _themeDisplay;

}