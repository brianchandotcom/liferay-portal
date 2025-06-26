/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author István András Dézsi
 */
public class PreupgradeVerifyFileSystemStoreStructure
	extends PreupgradeVerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		if (PropsValues.UPGRADE_DATABASE_DL_STORAGE_CHECK_DISABLED ||
			StartupHelperUtil.isDBNew()) {

			return;
		}

		boolean advancedFileSystemStore = StringUtil.equals(
			PropsValues.DL_STORE_IMPL,
			"com.liferay.portal.store.file.system.AdvancedFileSystemStore");
		boolean fileSystemStore = StringUtil.equals(
			PropsValues.DL_STORE_IMPL,
			"com.liferay.portal.store.file.system.FileSystemStore");

		if (!advancedFileSystemStore && !fileSystemStore) {
			return;
		}

		Set<Long> companyIds = SetUtil.fromArray(
			PortalInstancePool.getCompanyIds());

		Path fileSystemStoreRootDirPath = _getFileSystemStoreRootDirPath();

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				fileSystemStoreRootDirPath)) {

			for (Path companyIdPath : directoryStream) {
				String fileName = String.valueOf(companyIdPath.getFileName());

				long companyId = GetterUtil.getLong(fileName);

				if (!companyIds.remove(companyId)) {
					continue;
				}

				if (!Files.isDirectory(companyIdPath)) {
					throw new VerifyException(
						companyIdPath + " is not a directory");
				}

				if (advancedFileSystemStore &&
					_hasAdvancedFileSystemStructureCompanyIdPath(
						companyIdPath)) {

					continue;
				}

				if (fileSystemStore &&
					_hasFileSystemStructureCompanyIdPath(companyIdPath)) {

					continue;
				}

				throw new VerifyException(
					StringBundler.concat(
						advancedFileSystemStore ? "Advanced file" : "File",
						" system store directory structure ",
						fileSystemStoreRootDirPath.toString(), " is invalid"));
			}
		}

		if (!companyIds.isEmpty()) {
			throw new VerifyException(
				StringBundler.concat(
					"Missing directories in ",
					fileSystemStoreRootDirPath.toString(), " for companies: ",
					companyIds.toString()));
		}
	}

	@Override
	protected boolean isSkipDBPartitions() {
		return true;
	}

	private Path _getFileSystemStoreRootDirPath() throws Exception {
		File rootDir = null;

		try {
			BundleContext bundleContext = SystemBundleUtil.getBundleContext();

			for (ServiceReference<Store> serviceReference :
					bundleContext.getServiceReferences(
						Store.class,
						"(store.type=" + PropsValues.DL_STORE_IMPL + ")")) {

				rootDir = (File)serviceReference.getProperty("rootDir");

				break;
			}
		}
		catch (Exception exception) {
			throw new VerifyException(
				"Unable to get file system store root directory", exception);
		}

		if ((rootDir == null) || !rootDir.exists()) {
			throw new VerifyException(
				"File system store root directory does not exist: " + rootDir);
		}

		return rootDir.toPath();
	}

	private boolean _hasAdvancedFileSystemStructureCompanyIdPath(
		Path companyIdPath) {

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				companyIdPath)) {

			for (Path folderIdPath : directoryStream) {
				if (StringUtil.equals(
						String.valueOf(folderIdPath.getFileName()),
						_ADAPTIVE_MEDIA_FOLDER_NAME)) {

					continue;
				}

				if (!Files.isDirectory(folderIdPath)) {
					_log.error(
						"Found file in advanced file system structure " +
							"directory when only directories are expected: " +
								folderIdPath);

					return false;
				}

				if (!_hasAdvancedFileSystemStructureFolderIdPath(
						folderIdPath)) {

					return false;
				}
			}

			return true;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to check advanced file system structure in: " +
						companyIdPath,
					exception);
			}

			return false;
		}
	}

	private boolean _hasAdvancedFileSystemStructureFolderIdPath(
		Path folderIdPath) {

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				folderIdPath)) {

			for (Path fileEntryPath : directoryStream) {
				if (!Files.isDirectory(fileEntryPath)) {
					_log.error(
						"Found file in advanced file system structure " +
							"directory (only directories expected): " +
								fileEntryPath);

					return false;
				}

				String fileEntryName = String.valueOf(
					fileEntryPath.getFileName());

				if (fileEntryName.equals("DLFE")) {
					if (!_hasAdvancedFileSystemStructureFolderIdPath(
							fileEntryPath)) {

						return false;
					}
				}
				else if ((fileEntryName.length() > 2) &&
						 Validator.isNull(
							 FileUtil.getExtension(fileEntryName))) {

					_log.error(
						StringBundler.concat(
							"Found directory with name longer than 2 without ",
							"extension in advanced file system structure ",
							"directory: ", fileEntryPath.toString()));

					return false;
				}
			}

			return true;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to validate subdirectories in: " + folderIdPath,
					exception);
			}

			return false;
		}
	}

	private boolean _hasFileSystemStructureCompanyIdPath(Path companyIdPath) {
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				companyIdPath)) {

			for (Path folderIdPath : directoryStream) {
				if (StringUtil.equals(
						String.valueOf(folderIdPath.getFileName()),
						_ADAPTIVE_MEDIA_FOLDER_NAME)) {

					continue;
				}

				if (!Files.isDirectory(folderIdPath)) {
					_log.error(
						"Found file in file system structure directory when " +
							"only directories are expected: " + folderIdPath);

					return false;
				}

				if (!_hasFileSystemStructureFolderIdPath(folderIdPath)) {
					return false;
				}
			}

			return true;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to check file system structure in: " +
						companyIdPath,
					exception);
			}

			return false;
		}
	}

	private boolean _hasFileSystemStructureFolderIdPath(Path folderIdPath) {
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				folderIdPath)) {

			for (Path fileEntryPath : directoryStream) {
				if (!Files.isDirectory(fileEntryPath)) {
					_log.error(
						"Found file in file system structure directory (only " +
							"directories expected): " + fileEntryPath);

					return false;
				}

				if (StringUtil.contains(
						String.valueOf(fileEntryPath.getFileName()),
						StringPool.PERIOD, StringPool.BLANK)) {

					_log.error(
						StringBundler.concat(
							"Found directory with extension in file system ",
							"structure (no extensions expected): ",
							fileEntryPath.toString()));

					return false;
				}

				if (!_hasVersionFile(fileEntryPath)) {
					_log.error(
						StringBundler.concat(
							"Directory does not contain valid version files ",
							"as expected in file system structure: ",
							fileEntryPath.toString()));

					return false;
				}
			}

			return true;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to validate subdirectories in: " + folderIdPath,
					exception);
			}

			return false;
		}
	}

	private boolean _hasVersionFile(Path fileEntryPath) {
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				fileEntryPath)) {

			for (Path versionPath : directoryStream) {
				if (Files.isDirectory(versionPath)) {
					continue;
				}

				String versionName = String.valueOf(versionPath.getFileName());

				if (StringUtil.equals(
						versionName,
						DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION)) {

					continue;
				}

				if (!versionName.matches("\\d+\\.\\d+.*")) {
					_log.error(
						"Found file that does not match version structure " +
							"(expected \\d+\\.\\d+.*): " + versionPath);

					return false;
				}
			}

			return true;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to check for version file in: " + fileEntryPath,
					exception);
			}

			return false;
		}
	}

	private static final String _ADAPTIVE_MEDIA_FOLDER_NAME = "0";

	private static final Log _log = LogFactoryUtil.getLog(
		PreupgradeVerifyFileSystemStoreStructure.class);

}