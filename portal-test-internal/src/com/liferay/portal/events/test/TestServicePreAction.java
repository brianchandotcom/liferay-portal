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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

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
	public void addDefaultLayoutsByLAR(
			long userId, long groupId, boolean privateLayout, File larFile)
		throws PortalException {

		super.addDefaultLayoutsByLAR(userId, groupId, privateLayout, larFile);
	}

	@Override
	public void addDefaultUserPrivateLayoutByProperties(
			long userId, long groupId)
		throws PortalException {

		super.addDefaultUserPublicLayoutByProperties(userId, groupId);
	}

	@Override
	public void addDefaultUserPrivateLayouts(User user) throws PortalException {
		super.addDefaultUserPrivateLayouts(user);
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
	public void deleteDefaultUserPrivateLayouts(User user)
		throws PortalException {

		super.deleteDefaultUserPrivateLayouts(user);
	}

	@Override
	public void deleteDefaultUserPublicLayouts(User user)
		throws PortalException {

		super.deleteDefaultUserPublicLayouts(user);
	}

	@Override
	public LayoutComposite getDefaultUserPersonalSiteLayoutComposite(
		User user) {

		return super.getDefaultUserPersonalSiteLayoutComposite(user);
	}

	@Override
	public LayoutComposite getDefaultUserSitesLayoutComposite(User user)
		throws PortalException {

		return super.getDefaultUserSitesLayoutComposite(user);
	}

	@Override
	public LayoutComposite getDefaultViewableLayoutComposite(
			HttpServletRequest request, User user,
			PermissionChecker permissionChecker, long doAsGroupId,
			boolean signedIn)
		throws PortalException {

		return super.getDefaultViewableLayoutComposite(
			request, user, permissionChecker, doAsGroupId, signedIn);
	}

	@Override
	public LayoutComposite getDefaultVirtualHostLayoutComposite(
			HttpServletRequest request)
		throws PortalException {

		return super.getDefaultVirtualHostLayoutComposite(request);
	}

	@Override
	public LayoutComposite getGuestSiteLayoutComposite(User user)
		throws PortalException {

		return super.getGuestSiteLayoutComposite(user);
	}

	@Override
	public LayoutComposite getViewableLayoutComposite(
			HttpServletRequest request, User user,
			PermissionChecker permissionChecker, Layout layout,
			List<Layout> layouts, long doAsGroupId)
		throws PortalException {

		return super.getViewableLayoutComposite(
			request, user, permissionChecker, layout, layouts, doAsGroupId);
	}

	@Override
	public boolean hasAccessPermission(
			PermissionChecker permissionChecker, Layout layout,
			long doAsGroupId, boolean checkViewableGroup)
		throws PortalException {

		return super.hasAccessPermission(
			permissionChecker, layout, doAsGroupId, checkViewableGroup);
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
			List<Layout> layouts, long doAsGroupId)
		throws PortalException {

		return super.mergeAdditionalLayouts(
			request, user, permissionChecker, layout, layouts, doAsGroupId);
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