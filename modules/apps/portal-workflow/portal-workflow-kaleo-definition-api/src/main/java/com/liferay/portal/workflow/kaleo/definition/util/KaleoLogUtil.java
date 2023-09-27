/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition.util;

import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.workflow.kaleo.definition.LogType;

/**
 * @author Michael C. Han
 */
public class KaleoLogUtil {

	public static String convert(int type) {
		if (type == WorkflowLog.NODE_ENTRY) {
			return LogType.NODE_ENTRY.name();
		}
		else if (type == WorkflowLog.TASK_ASSIGN) {
			return LogType.TASK_ASSIGNMENT.name();
		}
		else if (type == WorkflowLog.TASK_COMPLETION) {
			return LogType.TASK_COMPLETION.name();
		}
		else if (type == WorkflowLog.TASK_UPDATE) {
			return LogType.TASK_UPDATE.name();
		}
		else if (type == WorkflowLog.TRANSITION) {
			return LogType.NODE_EXIT.name();
		}

		return null;
	}

	public static int convert(String type) {
		LogType logType = LogType.valueOf(type);

		if (logType.equals(LogType.NODE_ENTRY)) {
			return WorkflowLog.NODE_ENTRY;
		}
		else if (logType.equals(LogType.NODE_EXIT)) {
			return WorkflowLog.TRANSITION;
		}
		else if (logType.equals(LogType.TASK_ASSIGNMENT)) {
			return WorkflowLog.TASK_ASSIGN;
		}
		else if (logType.equals(LogType.TASK_COMPLETION)) {
			return WorkflowLog.TASK_COMPLETION;
		}
		else if (logType.equals(LogType.TASK_UPDATE)) {
			return WorkflowLog.TASK_UPDATE;
		}

		return -1;
	}

}