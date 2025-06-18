/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.verify.util.PreupgradeFileSystemStoreVerifyUtil;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author István András Dézsi
 */
public class PreupgradeVerifyFileSystemStoreWritePermission
	extends PreupgradeVerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		if (PropsValues.UPGRADE_DATABASE_DL_STORAGE_CHECK_DISABLED ||
			!PreupgradeFileSystemStoreVerifyUtil.isFileSystemStore()) {

			return;
		}

		Path fileSystemStoreRootDir =
			PreupgradeFileSystemStoreVerifyUtil.getFileSystemStoreRootDir(
				connection);

		if (fileSystemStoreRootDir == null) {
			throw new VerifyException(
				"File system store root directory does not exist");
		}

		if (!Files.isDirectory(fileSystemStoreRootDir)) {
			throw new VerifyException(
				"File system store path is not a directory: " +
					fileSystemStoreRootDir.toString());
		}

		Path testFile = fileSystemStoreRootDir.resolve(
			StringBundler.concat(
				"liferay_upgrade_test_",
				String.valueOf(System.currentTimeMillis()), ".tmp"));

		try {
			Files.createFile(testFile);
			Files.delete(testFile);
		}
		catch (Exception exception) {
			throw new VerifyException(
				StringBundler.concat(
					"Cannot write to file system store directory: ",
					fileSystemStoreRootDir.toString(), ". ",
					exception.getClass(
					).getName(),
					": ", exception.getMessage()));
		}
	}

	@Override
	protected boolean isSkipDBPartitions() {
		return true;
	}

}