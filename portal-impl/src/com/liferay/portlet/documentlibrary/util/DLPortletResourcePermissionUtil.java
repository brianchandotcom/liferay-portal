/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

/**
 * @author Hayden Zheng
 */
public class DLPortletResourcePermissionUtil {

	public static PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermissionSnapshot.get();
	}

	private static final Snapshot<PortletResourcePermission>
		_portletResourcePermissionSnapshot = new Snapshot<>(
			DLPortletResourcePermissionUtil.class,
			PortletResourcePermission.class,
			"(resource.name=" + DLConstants.RESOURCE_NAME + ")");

}