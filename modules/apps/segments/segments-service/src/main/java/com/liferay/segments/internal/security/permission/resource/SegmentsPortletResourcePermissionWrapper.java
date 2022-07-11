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

package com.liferay.segments.internal.security.permission.resource;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.BasePortletResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionLogic;
import com.liferay.segments.constants.SegmentsActionKeys;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.constants.SegmentsPortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "resource.name=" + SegmentsConstants.RESOURCE_NAME,
	service = PortletResourcePermission.class
)
public class SegmentsPortletResourcePermissionWrapper
	extends BasePortletResourcePermissionWrapper {

	@Override
	protected PortletResourcePermission doGetPortletResourcePermission() {
		return PortletResourcePermissionFactory.create(
			SegmentsConstants.RESOURCE_NAME,
			new StagedPortletPermissionLogic(
				_stagingPermission, SegmentsPortletKeys.SEGMENTS));
	}

	@Reference
	private StagingPermission _stagingPermission;

	private static class StagedPortletPermissionLogic
		implements PortletResourcePermissionLogic {

		@Override
		public Boolean contains(
			PermissionChecker permissionChecker, String name, Group group,
			String actionId) {

			if (SegmentsActionKeys.SIMULATE_SEGMENTS_ENTRIES.equals(actionId)) {
				return null;
			}

			long groupId = 0;

			if (group != null) {
				groupId = group.getGroupId();
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