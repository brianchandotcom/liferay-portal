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

package com.liferay.portal.workflow.kaleo.internal.upgrade.v1_2_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Amadea Fejes
 */
public class UpdateKaleoService extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateKaleoAction();
		updateKaleoCondition();
		updateKaleoTaskAssignment();
	}

	protected void updateKaleoAction() throws Exception {
		if (!hasColumn("KaleoAction", "scriptRequiredContexts")) {
			runSQL(
				"alter table KaleoAction add scriptRequiredContexts STRING null");
		}
	}

	protected void updateKaleoCondition() throws Exception {
		if (!hasColumn("KaleoCondition", "scriptRequiredContexts")) {
			runSQL(
				"alter table KaleoCondition add scriptRequiredContexts STRING null");
		}
	}

	protected void updateKaleoTaskAssignment() throws Exception {
		if (!hasColumn(
				"KaleoTaskAssignment", "assigneeScriptRequiredContexts")) {

			runSQL(
				"alter table KaleoTaskAssignment add assigneeScriptRequiredContexts STRING null");
		}
	}

}