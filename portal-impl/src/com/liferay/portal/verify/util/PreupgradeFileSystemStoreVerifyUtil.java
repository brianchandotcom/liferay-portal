/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify.util;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.nio.file.Path;

import java.util.Collection;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author István András Dézsi
 */
public class PreupgradeFileSystemStoreVerifyUtil {

	public static Path getFileSystemStoreRootDir() {
		File rootDir = null;

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		try {
			Collection<ServiceReference<Store>> serviceReferences =
				bundleContext.getServiceReferences(
					Store.class,
					"(store.type=" + PropsValues.DL_STORE_IMPL + ")");

			for (ServiceReference<Store> serviceReference : serviceReferences) {
				rootDir = (File)serviceReference.getProperty("rootDir");

				break;
			}
		}
		catch (Exception exception) {
			_log.error(
				"Unable to get file system store root directory", exception);
		}

		if (rootDir == null) {
			return null;
		}

		if (rootDir.exists()) {
			return rootDir.toPath();
		}

		_log.error(
			"File system store root directory does not exist: " + rootDir);

		return null;
	}

	public static boolean isFileSystemStore() {
		if (StringUtil.equals(
				PropsValues.DL_STORE_IMPL,
				"com.liferay.portal.store.file.system." +
					"AdvancedFileSystemStore") ||
			StringUtil.equals(
				PropsValues.DL_STORE_IMPL,
				"com.liferay.portal.store.file.system.FileSystemStore")) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PreupgradeFileSystemStoreVerifyUtil.class);

}