/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.upgrade.v3_2_7;

import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.upgrade.BaseViewActionResourcePermissionUpgradeProcess;

/**
 * @author Adolfo Pérez
 */
public class DownloadViewActionResourcePermissionUpgradeProcess
	extends BaseViewActionResourcePermissionUpgradeProcess {

	@Override
	protected String getActionId() {
		return ActionKeys.DOWNLOAD;
	}

	@Override
	protected String getClassName() {
		return "com.liferay.document.library.kernel.model.DLFileEntry";
	}

	@Override
	protected String getExceptionMessage() {
		return "Unable to assign download permission to file entry with view " +
			"permission";
	}

}