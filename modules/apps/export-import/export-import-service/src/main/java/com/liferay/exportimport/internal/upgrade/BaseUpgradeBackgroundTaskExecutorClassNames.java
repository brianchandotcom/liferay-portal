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

package com.liferay.exportimport.internal.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

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

			StringBundler sb = new StringBundler(5);

			sb.append("update BackgroundTask set taskExecutorClassName = '");
			sb.append(renameTaskExecutorClassName[1]);
			sb.append("' where taskExecutorClassName = '");
			sb.append(renameTaskExecutorClassName[0]);
			sb.append("'");

			runSQL(sb.toString());
		}
	}

	protected abstract String[][] getRenameTaskExecutorClassNames();

}