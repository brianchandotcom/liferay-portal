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

package com.liferay.exportimport.staging;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.staging.LayoutStaging;
import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutSetStagingHandler;
import com.liferay.portal.kernel.model.LayoutStagingHandler;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.lang.reflect.InvocationHandler;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(immediate = true)
@DoPrivileged
public class LayoutStagingImpl implements LayoutStaging {

	@Override
	public LayoutRevision getLayoutRevision(Layout layout) {
		LayoutStagingHandler layoutStagingHandler = getLayoutStagingHandler(
			layout);

		if (layoutStagingHandler == null) {
			return null;
		}

		return layoutStagingHandler.getLayoutRevision();
	}

	@Override
	public LayoutSetBranch getLayoutSetBranch(LayoutSet layoutSet) {
		LayoutSetStagingHandler layoutSetStagingHandler =
			getLayoutSetStagingHandler(layoutSet);

		if (layoutSetStagingHandler == null) {
			return null;
		}

		return layoutSetStagingHandler.getLayoutSetBranch();
	}

	@Override
	public LayoutSetStagingHandler getLayoutSetStagingHandler(
		LayoutSet layoutSet) {

		if (!ProxyUtil.isProxyClass(layoutSet.getClass())) {
			return null;
		}

		InvocationHandler invocationHandler = ProxyUtil.getInvocationHandler(
			layoutSet);

		if (!(invocationHandler instanceof LayoutSetStagingHandler)) {
			return null;
		}

		return (LayoutSetStagingHandler)invocationHandler;
	}

	@Override
	public LayoutStagingHandler getLayoutStagingHandler(Layout layout) {
		if (layout == null) {
			return null;
		}

		if (!ProxyUtil.isProxyClass(layout.getClass())) {
			return null;
		}

		InvocationHandler invocationHandler = ProxyUtil.getInvocationHandler(
			layout);

		if (!(invocationHandler instanceof LayoutStagingHandler)) {
			return null;
		}

		return (LayoutStagingHandler)invocationHandler;
	}

	@Override
	public boolean isBranchingLayout(Layout layout) {
		try {
			return isBranchingLayoutSet(
				layout.getGroup(), layout.isPrivateLayout());
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean isBranchingLayoutSet(Group group, boolean privateLayout) {
		boolean isStagingGroup = false;

		if (group.isStagingGroup() && !group.isStagedRemotely()) {
			isStagingGroup = true;

			group = group.getLiveGroup();
		}

		UnicodeProperties typeSettingsProperties =
			group.getTypeSettingsProperties();

		if (typeSettingsProperties.isEmpty()) {
			return false;
		}

		boolean branchingEnabled = false;

		if (privateLayout) {
			branchingEnabled = GetterUtil.getBoolean(
				typeSettingsProperties.getProperty("branchingPrivate"));
		}
		else {
			branchingEnabled = GetterUtil.getBoolean(
				typeSettingsProperties.getProperty("branchingPublic"));
		}

		if (!branchingEnabled || !group.isStaged() ||
			(!group.isStagedRemotely() && !isStagingGroup)) {

			return false;
		}

		Group stagingGroup = group;

		if (isStagingGroup) {
			stagingGroup = group.getStagingGroup();
		}

		try {
			_layoutSetBranchLocalService.getMasterLayoutSetBranch(
				stagingGroup.getGroupId(), privateLayout);

			return true;
		}
		catch (PortalException pe) {
			return false;
		}
	}

	public boolean prepareLayoutStagingHandler(
		PortletDataContext portletDataContext, Layout layout) {

		boolean exportLAR = MapUtil.getBoolean(
			portletDataContext.getParameterMap(), "exportLAR");

		if (exportLAR || !LayoutStagingUtil.isBranchingLayout(layout)) {
			return true;
		}

		long layoutSetBranchId = MapUtil.getLong(
			portletDataContext.getParameterMap(), "layoutSetBranchId");

		if (layoutSetBranchId <= 0) {
			return false;
		}

		LayoutRevision layoutRevision =
			_layoutRevisionLocalService.fetchLayoutRevision(
				layoutSetBranchId, true, layout.getPlid());

		if (layoutRevision == null) {
			return false;
		}

		LayoutStagingHandler layoutStagingHandler =
			LayoutStagingUtil.getLayoutStagingHandler(layout);

		layoutStagingHandler.setLayoutRevision(layoutRevision);

		return true;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetBranchLocalService(
		LayoutSetBranchLocalService layoutSetBranchLocalService) {

		_layoutSetBranchLocalService = layoutSetBranchLocalService;
	}

	@Reference
	private LayoutRevisionLocalService _layoutRevisionLocalService;

	private LayoutSetBranchLocalService _layoutSetBranchLocalService;

}