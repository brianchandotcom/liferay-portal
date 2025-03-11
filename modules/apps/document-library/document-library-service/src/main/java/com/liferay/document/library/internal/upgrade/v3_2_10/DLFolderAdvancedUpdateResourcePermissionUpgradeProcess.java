/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.upgrade.v3_2_10;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.upgrade.BaseAdvancedUpdateResourcePermissionUpgradeProcess;

/**
 * @author Jürgen Kappler
 */
public class DLFolderAdvancedUpdateResourcePermissionUpgradeProcess
	extends BaseAdvancedUpdateResourcePermissionUpgradeProcess {

	public DLFolderAdvancedUpdateResourcePermissionUpgradeProcess(
		ResourceActionLocalService resourceActionLocalService) {

		this.resourceActionLocalService = resourceActionLocalService;
	}

	@Override
	public String getResourcePermissionName() {
		return DLFolder.class.getName();
	}

}