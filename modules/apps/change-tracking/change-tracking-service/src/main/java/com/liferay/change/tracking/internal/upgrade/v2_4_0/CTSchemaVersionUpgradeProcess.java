/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.upgrade.v2_4_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Preston Crary
 */
public class CTSchemaVersionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL("update CTCollection set schemaVersionId = 0");

		runSQL(
			StringBundler.concat(
				"create table CTSchemaVersion (mvccVersion LONG default 0 not ",
				"null, schemaVersionId LONG not null primary key, companyId ",
				"LONG, schemaContext TEXT null)"));

		runSQL(
			StringBundler.concat(
				"update CTCollection set status = ",
				WorkflowConstants.STATUS_EXPIRED, " where status = ",
				WorkflowConstants.STATUS_DRAFT));

		runSQL(
			"update CTPreferences set ctCollectionId = 0, " +
				"previousCtCollectionId = 0");
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"CTCollection", "schemaVersionId LONG")
		};
	}

}