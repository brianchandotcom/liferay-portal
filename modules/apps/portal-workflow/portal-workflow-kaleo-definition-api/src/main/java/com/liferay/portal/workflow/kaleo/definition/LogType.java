/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition;

/**
 * @author Michael C. Han
 */
public enum LogType {

	ACTION_EXECUTION, INSTANCE_END, INSTANCE_FAIL, INSTANCE_START, NODE_ENTRY,
	NODE_EXIT, NODE_USAGE_METADATA, TASK_ASSIGNMENT, TASK_COMPLETION,
	TASK_UPDATE

}