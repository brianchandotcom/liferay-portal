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

package com.liferay.fragment.internal.security.permission.resource;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.BasePortletResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionLogic;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	property = "resource.name=" + FragmentConstants.RESOURCE_NAME,
	service = PortletResourcePermission.class
)
public class FragmentPortletResourcePermissionWrapper
	extends BasePortletResourcePermissionWrapper {

	@Override
	protected PortletResourcePermission doGetPortletResourcePermission() {
		return PortletResourcePermissionFactory.create(
			FragmentConstants.RESOURCE_NAME,
			new StagedPortletPermissionLogic(
				_stagingPermission, FragmentPortletKeys.FRAGMENT));
	}

	@Reference
	private StagingPermission _stagingPermission;

	private static class StagedPortletPermissionLogic
		implements PortletResourcePermissionLogic {

		@Override
		public Boolean contains(
			PermissionChecker permissionChecker, String name, Group group,
			String actionId) {

			long groupId = 0;

			if (group != null) {
				groupId = group.getGroupId();
			}
			else {
				User user = permissionChecker.getUser();

				group = user.getGroup();
				groupId = user.getGroupId();
			}

			return _stagingPermission.hasPermission(
				permissionChecker, group, name, groupId, _portletId, actionId);
		}

		private StagedPortletPermissionLogic(
			StagingPermission stagingPermission, String portletId) {

			_stagingPermission = stagingPermission;
			_portletId = portletId;
		}

		private final String _portletId;
		private final StagingPermission _stagingPermission;

	}

}