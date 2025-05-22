/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.upgrade.v5_0_0;

import com.liferay.portal.kernel.upgrade.BaseJakartaUpgradeProcess;

/**
 * @author Luis Ortiz
 */
public class KaleoJakartaUpgradeProcess extends BaseJakartaUpgradeProcess {

	@Override
	protected String[][] getTableAndColumnNames() {
		return new String[][] {
			{"KaleoAction", "script"}, {"KaleoCondition", "script"},
			{"KaleoDefinition", "content"},
			{"KaleoDefinitionVersion", "content"},
			{"KaleoInstance", "workflowContext"},
			{"KaleoLog", "workflowContext"}, {"KaleoNotification", "template"},
			{"KaleoTaskAssignment", "assigneeScript"},
			{"KaleoTaskInstanceToken", "workflowContext"}
		};
	}

}