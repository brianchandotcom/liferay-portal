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
import com.liferay.portal.kernel.model.CompanyConstants;
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
public class PreupgradeVerifyStoreFileSystemStructure
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

		Path rootDirPath = _getRootDirPath();

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				rootDirPath)) {

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
						rootDirPath.toString(), " is invalid"));
			}
		}

		if (!companyIds.isEmpty()) {
			throw new VerifyException(
				StringBundler.concat(
					"Missing directories in ", rootDirPath.toString(),
					" for companies: ", companyIds.toString()));
		}
	}

	@Override
	protected boolean isSkipDBPartitions() {
		return true;
	}

	private Path _getRootDirPath() throws Exception {
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
				"Unable to get root directory", exception);
		}

		if ((rootDir == null) || !rootDir.exists()) {
			throw new VerifyException(
				"Root directory does not exist: " + rootDir);
		}

		return rootDir.toPath();
	}

	private boolean _hasAdvancedFileSystemStructureCompanyIdPath(
		Path companyIdPath) {

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				companyIdPath)) {

			for (Path repositoryIdPath : directoryStream) {
				if (_isSystemCompanyRepositoryIdPath(repositoryIdPath)) {
					continue;
				}

				if (!Files.isDirectory(repositoryIdPath)) {
					_log.error(
						"Found file in advanced file system structure " +
							"directory when only directories are expected: " +
								repositoryIdPath);

					return false;
				}

				if (!_hasAdvancedFileSystemStructureRepositoryIdPath(
						repositoryIdPath)) {

					return false;
				}
			}

			return true;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to verify advanced file system structure in: " +
						companyIdPath,
					exception);
			}

			return false;
		}
	}

	private boolean _hasAdvancedFileSystemStructureRepositoryIdPath(
		Path repositoryIdPath) {

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				repositoryIdPath)) {

			for (Path fileNamePath : directoryStream) {
				if (!Files.isDirectory(fileNamePath)) {
					_log.error(
						"Found file in advanced file system structure " +
							"directory when only directories are expected: " +
								fileNamePath);

					return false;
				}

				String fileName = String.valueOf(fileNamePath.getFileName());

				if (fileName.equals("DLFE")) {
					if (!_hasAdvancedFileSystemStructureRepositoryIdPath(
							fileNamePath)) {

						return false;
					}
				}
				else if ((fileName.length() > 2) &&
						 Validator.isNull(FileUtil.getExtension(fileName))) {

					_log.error(
						StringBundler.concat(
							"Found file name directory with name longer than ",
							"2 characters without an extension in advanced ",
							"file system structure directory: ",
							fileNamePath.toString()));

					return false;
				}
			}

			return true;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to verify advanced file system structure in: " +
						repositoryIdPath,
					exception);
			}

			return false;
		}
	}

	private boolean _hasFileSystemStructureCompanyIdPath(Path companyIdPath) {
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				companyIdPath)) {

			for (Path repositoryIdPath : directoryStream) {
				if (_isSystemCompanyRepositoryIdPath(repositoryIdPath)) {
					continue;
				}

				if (!Files.isDirectory(repositoryIdPath)) {
					_log.error(
						"Found file in file system structure directory when " +
							"only directories are expected: " +
								repositoryIdPath);

					return false;
				}

				if (!_hasFileSystemStructureRepositoryIdPath(
						repositoryIdPath)) {

					return false;
				}
			}

			return true;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to verify file system structure in: " +
						companyIdPath,
					exception);
			}

			return false;
		}
	}

	private boolean _hasFileSystemStructureRepositoryIdPath(
		Path repositoryIdPath) {

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				repositoryIdPath)) {

			for (Path fileNamePath : directoryStream) {
				if (!Files.isDirectory(fileNamePath)) {
					_log.error(
						"Found file in file system structure directory when " +
							"only directories are expected: " + fileNamePath);

					return false;
				}

				if (StringUtil.contains(
						String.valueOf(fileNamePath.getFileName()),
						StringPool.PERIOD, StringPool.BLANK)) {

					_log.error(
						StringBundler.concat(
							"Found file name directory with extension in file ",
							"system structure when no extensions are ",
							"expected: ", fileNamePath.toString()));

					return false;
				}

				if (!_hasVersionLabelFile(fileNamePath)) {
					_log.error(
						StringBundler.concat(
							"File name directory does not contain valid ",
							"version label files as expected in file system ",
							"structure: ", fileNamePath.toString()));

					return false;
				}
			}

			return true;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to verify file system structure in: " +
						repositoryIdPath,
					exception);
			}

			return false;
		}
	}

	private boolean _hasVersionLabelFile(Path fileNamePath) {
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				fileNamePath)) {

			for (Path versionLabelPath : directoryStream) {
				if (Files.isDirectory(versionLabelPath)) {
					continue;
				}

				String versionLabelName = String.valueOf(
					versionLabelPath.getFileName());

				if (StringUtil.equals(
						versionLabelName,
						DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION)) {

					continue;
				}

				if (!versionLabelName.matches("\\d+\\.\\d+.*")) {
					_log.error(
						"Found file that does not match version label " +
							"structure: " + versionLabelPath);

					return false;
				}
			}

			return true;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to verify file entry version label in: " +
						fileNamePath,
					exception);
			}

			return false;
		}
	}

	private boolean _isSystemCompanyRepositoryIdPath(Path repositoryIdPath) {
		return StringUtil.equals(
			String.valueOf(repositoryIdPath.getFileName()),
			CompanyConstants.SYSTEM_STRING);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PreupgradeVerifyStoreFileSystemStructure.class);

}