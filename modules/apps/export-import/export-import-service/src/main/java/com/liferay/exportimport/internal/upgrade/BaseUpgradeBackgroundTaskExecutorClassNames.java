/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

/**
 * @author Jonathan McCann
 */
public abstract class BaseUpgradeBackgroundTaskExecutorClassNames
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String[][] renameTaskExecutorClassNamesArray =
			getRenameTaskExecutorClassNames();

		for (String[] renameTaskExecutorClassName :
				renameTaskExecutorClassNamesArray) {

			try (LoggingTimer loggingTimer = new LoggingTimer(
					renameTaskExecutorClassName[0])) {

				runSQL(
					StringBundler.concat(
						"update BackgroundTask set taskExecutorClassName = '",
						renameTaskExecutorClassName[1],
						"' where taskExecutorClassName = '",
						renameTaskExecutorClassName[0], "'"));
			}
		}
	}

	protected abstract String[][] getRenameTaskExecutorClassNames();

}