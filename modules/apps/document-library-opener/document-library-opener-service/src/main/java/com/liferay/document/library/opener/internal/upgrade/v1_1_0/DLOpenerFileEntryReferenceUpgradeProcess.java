/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.internal.upgrade.v1_1_0;

import com.liferay.document.library.opener.internal.upgrade.v1_1_0.util.DLOpenerFileEntryReferenceTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Cristina González
 */
public class DLOpenerFileEntryReferenceUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"update " + DLOpenerFileEntryReferenceTable.TABLE_NAME +
				" set referenceType = 'GoogleDrive'");
	}

}