/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.upgrade.v4_7_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Marco Galluzzi
 */
public class KBFolderUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("KBFolder", "status")) {
			alterTableAddColumn("KBFolder", "status", "INTEGER");

			runSQL(
				"update KBFolder set status = " +
					WorkflowConstants.STATUS_APPROVED +
						" where status is null");
		}

		if (!hasColumn("KBFolder", "statusByUserId")) {
			alterTableAddColumn("KBFolder", "statusByUserId", "LONG");

			runSQL(
				"update KBFolder set statusByUserId = userId where " +
					"statusByUserId is null");
		}

		if (!hasColumn("KBFolder", "statusByUserName")) {
			alterTableAddColumn("KBFolder", "statusByUserName", "VARCHAR(75)");

			runSQL(
				"update KBFolder set statusByUserName = userName where " +
					"statusByUserName is null");
		}

		if (!hasColumn("KBFolder", "statusDate")) {
			alterTableAddColumn("KBFolder", "statusDate", "DATE");

			runSQL(
				"update KBFolder set statusDate = modifiedDate where " +
					"statusDate is null");
		}
	}

}