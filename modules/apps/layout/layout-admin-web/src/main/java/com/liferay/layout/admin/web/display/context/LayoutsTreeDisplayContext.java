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

import com.liferay.layout.admin.web.constants.LayoutAdminPortletKeys;
import com.liferay.portal.exception.NoSuchLayoutSetBranchException;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.exportimport.background.task.BackgroundTaskExecutorNames;
import com.liferay.portlet.exportimport.staging.StagingUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Julio Camarero
 */
public class LayoutsTreeDisplayContext extends BaseLayoutDisplayContext {

	public LayoutsTreeDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		super(liferayPortletRequest, liferayPortletResponse);
	}

	public Long getCurSelPlid() {
		Long curSelPlid = null;

		if (getSelPlid() != LayoutConstants.DEFAULT_PLID) {
			curSelPlid = getSelPlid();
		}
		else {
			Layout layout = themeDisplay.getLayout();

			if (!layout.isTypeControlPanel()) {
				curSelPlid = layout.getPlid();
			}
		}

		return curSelPlid;
	}

	public PortletURL getEmptyLayoutSetURL(boolean privateLayout) {
		PortletURL emptyLayoutSetURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		emptyLayoutSetURL.setParameter("mvcPath", "/empty_layout_set.jsp");

		emptyLayoutSetURL.setParameter(
			"selPlid", String.valueOf(LayoutConstants.DEFAULT_PLID));

		Group liveGroup = getSelGroup();

		emptyLayoutSetURL.setParameter(
			"groupId", String.valueOf(liveGroup.getGroupId()));
		emptyLayoutSetURL.setParameter(
			"privateLayout", String.valueOf(privateLayout));

		return emptyLayoutSetURL;
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
			PortalUtil.getHttpServletRequest(liferayPortletRequest),
			layoutSetBranch.getName());
	}

	public String getLayoutSetBranchURL(LayoutSetBranch layoutSetBranch)
		throws PortalException {

		if (isLayoutSetBranchSelected(layoutSetBranch)) {
			return null;
		}

		PortletURL layoutSetBranchURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
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
			PortletURL portletURL = getEmptyLayoutSetURL(true);

			return portletURL.toString();
		}

		return selGroup.getDisplayURL(themeDisplay, true);
	}

	public String getPublicLayoutsURL() {
		Group selGroup = getSelGroup();

		if (!selGroup.hasPublicLayouts()) {
			PortletURL portletURL = getEmptyLayoutSetURL(false);

			return portletURL.toString();
		}

		return selGroup.getDisplayURL(themeDisplay, false);
	}

	public boolean isShowAddLayoutButton() throws PortalException {
		return LayoutPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), getSelLayout(),
			ActionKeys.ADD_LAYOUT);
	}

	public boolean isShowEditLayoutSetButton() throws PortalException {
		return GroupPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), getSelGroup(),
			ActionKeys.MANAGE_LAYOUTS);
	}

	public boolean isShowLayoutSetBranchesSelector() {
		Group stagingGroup = getStagingGroup();

		if ((stagingGroup == null) || !stagingGroup.isStaged() ||
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

	public boolean isShowPrivateLayoutsTree() throws PortalException {
		Group selGroup = getSelGroup();

		if (!selGroup.hasPrivateLayouts() && !isShowAddRootLayoutButton()) {
			return false;
		}

		return true;
	}

	public boolean isShowPublicLayoutsTree() throws PortalException {
		Group selGroup = getSelGroup();

		if (selGroup.isLayoutSetPrototype() || selGroup.isLayoutPrototype()) {
			return false;
		}

		if (!selGroup.hasPublicLayouts() && !isShowAddRootLayoutButton()) {
			return false;
		}

		return true;
	}

	public boolean isShowStagingProcessMessage() throws PortalException {
		Group group = getSelGroup();

		if (!GroupPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), group,
				ActionKeys.PUBLISH_STAGING)) {

			return false;
		}

		Group backgroundTaskExcutorGroup = null;

		if (group.isStagingGroup()) {
			backgroundTaskExcutorGroup = getLiveGroup();
		}
		else {
			backgroundTaskExcutorGroup = getStagingGroup();
		}

		int incompleteBackgroundTaskCount =
			BackgroundTaskManagerUtil.getBackgroundTasksCount(
				backgroundTaskExcutorGroup.getGroupId(),
				BackgroundTaskExecutorNames.
					LAYOUT_STAGING_BACKGROUND_TASK_EXECUTOR,
				false);

		if (incompleteBackgroundTaskCount > 0) {
			return true;
		}

		return false;
	}

	protected LayoutSetBranch getLayoutSetBranch() throws PortalException {
		if (_layoutSetBranch != null) {
			return _layoutSetBranch;
		}

		long layoutSetBranchId = ParamUtil.getLong(
			liferayPortletRequest, "layoutSetBranchId");

		if (layoutSetBranchId <= 0) {
			LayoutSet selLayoutSet = getSelLayoutSet();

			layoutSetBranchId = StagingUtil.getRecentLayoutSetBranchId(
				themeDisplay.getUser(), selLayoutSet.getLayoutSetId());
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

}