/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0;

import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

/**
 * @author Rafael Praxedes
 */
public class SchemaUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getTableNames() {
					return new String[] {
						"KaleoAction", "KaleoCondition", "KaleoDefinition",
						"KaleoDefinitionVersion", "KaleoInstance",
						"KaleoInstanceToken", "KaleoLog", "KaleoNode",
						"KaleoNotification", "KaleoNotificationRecipient",
						"KaleoTask", "KaleoTaskAssignment",
						"KaleoTaskAssignmentInstance", "KaleoTaskForm",
						"KaleoTaskFormInstance", "KaleoTaskInstanceToken",
						"KaleoTimer", "KaleoTimerInstanceToken",
						"KaleoTransition"
					};
				}

			});

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			alterColumnType(
				"KaleoNotification", "notificationTypes", "VARCHAR(255) null");
		}
	}

}