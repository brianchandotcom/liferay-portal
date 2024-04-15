/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.defaultpermissions.asset;

import java.util.List;

/**
 * @author Stefano Motta
 */
public interface PortalDefaultPermissionsAssetRegistry {

	public PortalDefaultPermissionsAsset getPortalDefaultPermissionsAsset(
		String key);

	public List<PortalDefaultPermissionsAsset>
		getPortalDefaultPermissionsAssets();

}