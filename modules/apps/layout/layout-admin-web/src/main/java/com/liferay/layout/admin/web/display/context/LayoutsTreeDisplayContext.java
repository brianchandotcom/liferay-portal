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
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HttpUtil;
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

	public PortletURL getAddLayoutURL() {
		PortletURL addPagesURL = getCommonAddLayoutURL();

		addPagesURL.setParameter("selPlid", String.valueOf(getCurSelPlid()));
		addPagesURL.setParameter(
			"privateLayout", String.valueOf(isPrivateLayout()));

		return addPagesURL;
	}

	public Map<String, Object> getAddLayoutURLData() {
		Map<String, Object> data = new HashMap<>();

		Layout selLayout = getSelLayout();

		data.put(
			"selPlid", (selLayout != null) ?
				String.valueOf(selLayout.getPlid()) : StringPool.BLANK);
		data.put("privateLayout", String.valueOf(isPrivateLayout()));

		PortletURL genericAddLayoutURL = getCommonAddLayoutURL();

		data.put(
			"url",
			StringUtil.replace(
				genericAddLayoutURL.toString(),
				new String[] {
					HttpUtil.encodePath("{selPlid}"),
					HttpUtil.encodePath("{privateLayout}")
				},
				new String[] {"{selPlid}", "{privateLayout}"}));

		return data;
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

	public PortletURL getEditLayoutURL(boolean privateLayout) {
		PortletURL editPublicLayoutURL = PortalUtil.getControlPanelPortletURL(
			_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		editPublicLayoutURL.setParameter(
			"privateLayout", String.valueOf(privateLayout));

		Group liveGroup = getLiveGroup();

		editPublicLayoutURL.setParameter(
			"groupId", String.valueOf(liveGroup.getGroupId()));
		editPublicLayoutURL.setParameter("viewLayout", Boolean.TRUE.toString());

		return editPublicLayoutURL;
	}

	public String getLayoutRootNodeName(boolean privateLayout) {
		Group liveGroup = getLiveGroup();

		return liveGroup.getLayoutRootNodeName(
			privateLayout, _themeDisplay.getLocale());
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

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(_liferayPortletRequest);

		return LanguageUtil.get(httpServletRequest, layoutSetBranch.getName());
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

	public Group getSelGroup() {
		if (_selGroup != null) {
			return _selGroup;
		}

		_selGroup = _themeDisplay.getScopeGroup();

		if (_selGroup.isControlPanel()) {
			HttpServletRequest httpServletRequest =
				PortalUtil.getHttpServletRequest(_liferayPortletRequest);

			_selGroup = LatentGroupManagerUtil.getLatentGroup(
				httpServletRequest.getSession());
		}

		return _selGroup;
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
		if (getSelLayout() == null) {
			return GroupPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), getSelGroup(),
				ActionKeys.ADD_LAYOUT);
		}
		else {
			return LayoutPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), getSelLayout(),
				ActionKeys.ADD_LAYOUT);
		}
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

	public boolean isShowPublicLayoutsTree() {
		Group selGroup = getSelGroup();

		if (selGroup.isLayoutSetPrototype() || selGroup.isLayoutPrototype()) {
			return false;
		}

		return true;
	}

	protected PortletURL getCommonAddLayoutURL() {
		PortletURL addPagesURL = PortalUtil.getControlPanelPortletURL(
			_liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		addPagesURL.setParameter("mvcPath", "/add_layout.jsp");
		addPagesURL.setParameter("groupId", String.valueOf(getSelGroupId()));
		addPagesURL.setParameter("selPlid", "{selPlid}");
		addPagesURL.setParameter("privateLayout", "{privateLayout}");

		return addPagesURL;
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

	protected long getSelGroupId() {
		Group selGroup = getSelGroup();

		if (selGroup != null) {
			return selGroup.getGroupId();
		}

		return 0;
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