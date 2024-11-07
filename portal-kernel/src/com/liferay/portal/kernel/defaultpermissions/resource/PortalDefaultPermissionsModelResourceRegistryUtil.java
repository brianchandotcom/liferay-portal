/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.defaultpermissions.resource;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Crescenzo Rega
 */
public class PortalDefaultPermissionsModelResourceRegistryUtil {

	public static PortalDefaultPermissionsModelResourceRegistry
		getPortalDefaultPermissionsModelResourceRegistry() {

		return _portalDefaultPermissionsModelResourceRegistrySnapshot.get();
	}

	private static final Snapshot<PortalDefaultPermissionsModelResourceRegistry>
		_portalDefaultPermissionsModelResourceRegistrySnapshot = new Snapshot<>(
			PortalDefaultPermissionsModelResourceRegistryUtil.class,
			PortalDefaultPermissionsModelResourceRegistry.class);

}