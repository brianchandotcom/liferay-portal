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
				protected String[] getModuleTableNames() {
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
			if (!hasColumnType(
					"KaleoNotification", "notificationTypes",
					"VARCHAR(255) null")) {

				alterColumnType(
					"KaleoNotification", "notificationTypes",
					"VARCHAR(255) null");
			}
		}
	}

}