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

package com.liferay.portal.events.test;

import com.liferay.portal.events.ServicePreAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;

import java.io.File;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Preston Crary
 */
public class TestServicePreAction extends ServicePreAction {

	public static TestServicePreAction INSTANCE = new TestServicePreAction();

	@Override
	public void addDefaultLayoutsByLAR(long userId, long groupId, File larFile)
		throws PortalException {

		super.addDefaultLayoutsByLAR(userId, groupId, larFile);
	}

	@Override
	public void addDefaultUserPublicLayoutByProperties(
			long userId, long groupId)
		throws PortalException {

		super.addDefaultUserPublicLayoutByProperties(userId, groupId);
	}

	@Override
	public void addDefaultUserPublicLayouts(User user) throws PortalException {
		super.addDefaultUserPublicLayouts(user);
	}

	@Override
	public void deleteDefaultUserPublicLayouts(User user)
		throws PortalException {

		super.deleteDefaultUserPublicLayouts(user);
	}

	@Override
	public Object[] getDefaultLayout(
			HttpServletRequest request, User user, boolean signedIn)
		throws PortalException {

		return super.getDefaultLayout(request, user, signedIn);
	}

	@Override
	public Object[] getViewableLayouts(
			HttpServletRequest request, User user,
			PermissionChecker permissionChecker, Layout layout,
			List<Layout> layouts, long doAsGroupId, String controlPanelCategory)
		throws PortalException {

		return super.getViewableLayouts(
			request, user, permissionChecker, layout, layouts, doAsGroupId,
			controlPanelCategory);
	}

	@Override
	public boolean hasAccessPermission(
			PermissionChecker permissionChecker, Layout layout,
			long doAsGroupId, String controlPanelCategory,
			boolean checkViewableGroup)
		throws PortalException {

		return super.hasAccessPermission(
			permissionChecker, layout, doAsGroupId, controlPanelCategory,
			checkViewableGroup);
	}

	@Override
	public void initImportLARFiles() {
		super.initImportLARFiles();
	}

	@Override
	public boolean isLoginRequest(HttpServletRequest request) {
		return super.isLoginRequest(request);
	}

	@Override
	public List<Layout> mergeAdditionalLayouts(
			HttpServletRequest request, User user,
			PermissionChecker permissionChecker, Layout layout,
			List<Layout> layouts, long doAsGroupId, String controlPanelCategory)
		throws PortalException {

		return super.mergeAdditionalLayouts(
			request, user, permissionChecker, layout, layouts, doAsGroupId,
			controlPanelCategory);
	}

	@Override
	public void processControlPanelRedirects(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		super.processControlPanelRedirects(request, response);
	}

	@Override
	public void rememberVisitedGroupIds(
		HttpServletRequest request, long currentGroupId) {

		super.rememberVisitedGroupIds(request, currentGroupId);
	}

	@Override
	public void servicePre(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		super.servicePre(request, response);
	}

	@Override
	public void updateUserLayouts(User user) throws Exception {
		super.updateUserLayouts(user);
	}

}