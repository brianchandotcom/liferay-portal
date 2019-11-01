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

package com.liferay.layout.page.template.internal.security.permission.resource;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.StagedPortletPermissionLogic;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = "resource.name=" + LayoutPageTemplateConstants.RESOURCE_NAME,
	service = {
		LayoutPageTemplatePortletResourcePermission.class,
		PortletResourcePermission.class
	}
)
public class LayoutPageTemplatePortletResourcePermission
	implements PortletResourcePermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, Group group, String actionId)
		throws PrincipalException {

		if (!contains(
				permissionChecker, _getGroupId(group.getGroupId()), actionId)) {

			throw new PrincipalException();
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, _getGroupId(groupId), actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, Group group, String actionId) {

		return contains(
			permissionChecker, _getGroupId(group.getGroupId()), actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long groupId, String actionId) {

		return _portletResourcePermission.contains(
			permissionChecker, _getGroupId(groupId), actionId);
	}

	@Override
	public String getResourceName() {
		return LayoutPageTemplateConstants.RESOURCE_NAME;
	}

	@Activate
	protected void activate() {
		_portletResourcePermission = PortletResourcePermissionFactory.create(
			LayoutPageTemplateConstants.RESOURCE_NAME,
			new StagedPortletPermissionLogic(
				_stagingPermission,
				LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES));
	}

	private long _getGroupId(long groupId) {
		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return groupId;
		}

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if (stagingGroupHelper.isLocalLiveGroup(groupId)) {
			Group stagingGroup = group.getStagingGroup();

			return stagingGroup.getGroupId();
		}

		return groupId;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private StagingPermission _stagingPermission;

}