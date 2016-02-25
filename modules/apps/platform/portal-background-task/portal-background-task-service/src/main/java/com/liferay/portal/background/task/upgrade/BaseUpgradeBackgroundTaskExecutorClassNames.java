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

package com.liferay.portal.background.task.upgrade;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Daniel Kocsis
 */
public class BaseUpgradeBackgroundTaskExecutorClassNames
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String[][] renameTaskExecutorClassNamesArray =
			getRenameTaskExecutorClassNames();

		for (String[] renameTaskExecutorClassName :
				renameTaskExecutorClassNamesArray) {

			String oldTaskExecutorClassName = renameTaskExecutorClassName[0];
			String newTaskExecutorClassName = renameTaskExecutorClassName[1];

			updateTaskExecutorClassName(
				oldTaskExecutorClassName, newTaskExecutorClassName);
		}
	}

	protected String[][] getRenameTaskExecutorClassNames() {
		return new String[0][0];
	}

	protected void updateTaskExecutorClassName(
		String oldTaskExecutorClassName, String newTaskExecutorClassName) {

		StringBundler sb = new StringBundler(5);

		sb.append("update BackgroundTask set taskExecutorClassName = '");
		sb.append(newTaskExecutorClassName);
		sb.append("' where taskExecutorClassName = '");
		sb.append(oldTaskExecutorClassName);
		sb.append("'");

		try {
			runSQL(sb.toString());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseUpgradeBackgroundTaskExecutorClassNames.class);

}