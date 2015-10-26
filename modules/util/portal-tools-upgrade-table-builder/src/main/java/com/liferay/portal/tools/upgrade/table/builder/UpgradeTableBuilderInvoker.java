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

package com.liferay.portal.tools.upgrade.table.builder;

import java.io.File;

/**
 * @author Andrea Di Giorgi
 */
public class UpgradeTableBuilderInvoker {

	public static UpgradeTableBuilder invoke(
			File baseDir, UpgradeTableBuilderArgs upgradeTableBuilderArgs)
		throws Exception {

		return new UpgradeTableBuilder(
			_getAbsolutePath(baseDir, upgradeTableBuilderArgs.getBaseDirName()),
			upgradeTableBuilderArgs.isOsgiModule(),
			_getAbsolutePath(
				baseDir, upgradeTableBuilderArgs.getUpgradeTableDirName()));
	}

	private static String _getAbsolutePath(File baseDir, String fileName) {
		File file = new File(baseDir, fileName);

		return file.getAbsolutePath();
	}

}