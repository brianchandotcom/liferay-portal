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

package com.liferay.exportimport.internal.upgrade.v1_0_0;

import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

/**
 * @author Daniel Kocsis
 */
public class UpgradeBackgroundTaskExecutorClassNames extends UpgradeProcess {

	protected void deleteBackgroundTasks() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL(
				"delete from BackgroundTask where taskExecutorClassName = '" +
					"com.liferay.portal.lar.backgroundtask." +
						"StagingIndexingBackgroundTaskExecutor'");
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		String[][] renameTaskExecutorClassNamesArray =
			getRenameTaskExecutorClassNames();

		for (String[] renameTaskExecutorClassName :
				renameTaskExecutorClassNamesArray) {

			StringBundler sb = new StringBundler(5);

			sb.append("update BackgroundTask set taskExecutorClassName = '");
			sb.append(renameTaskExecutorClassName[1]);
			sb.append("' where taskExecutorClassName = '");
			sb.append(renameTaskExecutorClassName[0]);
			sb.append("'");

			runSQL(sb.toString());
		}

		deleteBackgroundTasks();
	}

	protected String[][] getRenameTaskExecutorClassNames() {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			return new String[][] {
				{
					"com.liferay.portal.lar.backgroundtask." +
						"LayoutExportBackgroundTaskExecutor",
					BackgroundTaskExecutorNames.
						LAYOUT_EXPORT_BACKGROUND_TASK_EXECUTOR
				},
				{
					"com.liferay.portal.lar.backgroundtask." +
						"LayoutImportBackgroundTaskExecutor",
					BackgroundTaskExecutorNames.
						LAYOUT_IMPORT_BACKGROUND_TASK_EXECUTOR
				},
				{
					"com.liferay.portal.lar.backgroundtask." +
						"LayoutRemoteStagingBackgroundTaskExecutor",
					BackgroundTaskExecutorNames.
						LAYOUT_REMOTE_STAGING_BACKGROUND_TASK_EXECUTOR
				},
				{
					"com.liferay.portal.lar.backgroundtask." +
						"LayoutStagingBackgroundTaskExecutor",
					BackgroundTaskExecutorNames.
						LAYOUT_STAGING_BACKGROUND_TASK_EXECUTOR
				},
				{
					"com.liferay.portal.lar.backgroundtask." +
						"PortletExportBackgroundTaskExecutor",
					BackgroundTaskExecutorNames.
						PORTLET_EXPORT_BACKGROUND_TASK_EXECUTOR
				},
				{
					"com.liferay.portal.lar.backgroundtask." +
						"PortletImportBackgroundTaskExecutor",
					BackgroundTaskExecutorNames.
						PORTLET_IMPORT_BACKGROUND_TASK_EXECUTOR
				},
				{
					"com.liferay.portal.lar.backgroundtask." +
						"PortletStagingBackgroundTaskExecutor",
					BackgroundTaskExecutorNames.
						PORTLET_STAGING_BACKGROUND_TASK_EXECUTOR
				}
			};
		}
	}

}