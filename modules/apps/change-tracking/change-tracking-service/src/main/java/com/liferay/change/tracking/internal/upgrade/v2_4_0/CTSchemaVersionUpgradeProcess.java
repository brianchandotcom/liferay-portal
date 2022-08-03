/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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