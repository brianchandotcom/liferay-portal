/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.internal.upgrade.v1_0_1;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.reports.engine.console.internal.upgrade.v1_0_1.util.EntryTable;

/**
 * @author Inácio Nery
 */
public class UpgradeReportEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			EntryTable.class,
			new AlterColumnType("reportParameters", "TEXT null"),
			new AlterColumnType("errorMessage", "STRING null"));
	}

}