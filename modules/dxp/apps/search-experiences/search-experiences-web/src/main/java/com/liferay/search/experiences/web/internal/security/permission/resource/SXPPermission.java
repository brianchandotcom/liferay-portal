/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.web.internal.security.permission.resource;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.search.experiences.constants.SXPConstants;

/**
 * @author Petteri Karttunen
 */
public class SXPPermission {

	public static boolean contains(
		PermissionChecker permissionChecker, long groupId, String actionKey) {

		PortletResourcePermission portletResourcePermission =
			_portletResourcePermissionSnapshot.get();

		return portletResourcePermission.contains(
			permissionChecker, groupId, actionKey);
	}

	private static final Snapshot<PortletResourcePermission>
		_portletResourcePermissionSnapshot = new Snapshot<>(
			SXPPermission.class, PortletResourcePermission.class,
			"(resource.name=" + SXPConstants.RESOURCE_NAME + ")");

}