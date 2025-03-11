/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v6_1_5;

import com.liferay.journal.model.JournalFolder;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.upgrade.BaseAdvancedUpdateResourcePermissionUpgradeProcess;

/**
 * @author Jürgen Kappler
 */
public class JournalFolderAdvancedUpdateResourcePermissionUpgradeProcess
	extends BaseAdvancedUpdateResourcePermissionUpgradeProcess {

	public JournalFolderAdvancedUpdateResourcePermissionUpgradeProcess(
		ResourceActionLocalService resourceActionLocalService) {

		this.resourceActionLocalService = resourceActionLocalService;
	}

	@Override
	public String getResourcePermissionName() {
		return JournalFolder.class.getName();
	}

}