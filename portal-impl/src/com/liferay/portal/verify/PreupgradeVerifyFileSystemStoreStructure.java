/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

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

import java.util.Collection;
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

			for (Path path : directoryStream) {
				String companyIdString = String.valueOf(path.getFileName());

				long companyId = GetterUtil.getLong(companyIdString);

				if (!companyIds.remove(companyId)) {
					continue;
				}

				if (!Files.isDirectory(path)) {
					throw new VerifyException(path + " is not a directory");
				}

				if (advancedFileSystemStore &&
					_hasAdvancedFileSystemStructure(path)) {

					continue;
				}

				if (fileSystemStore && _hasBasicFileSystemStructure(path)) {
					continue;
				}

				String expectedType =
					advancedFileSystemStore ? "an advanced file system" :
						"a file system";

				throw new VerifyException(
					StringBundler.concat(
						"File system store directory structure mismatch. ",
						"Expected ", expectedType, " structure, but found an ",
						"invalid structure in: ",
						fileSystemStoreRootDirPath.toString()));
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
			throw new VerifyException(
				"Unable to get file system store root directory", exception);
		}

		if ((rootDir == null) || !rootDir.exists()) {
			throw new VerifyException(
				"File system store root directory does not exist: " + rootDir);
		}

		return rootDir.toPath();
	}

	private boolean _hasAdvancedFileSystemStructure(Path companyIdDirectory) {
		try (DirectoryStream<Path> companyIdDirectoryStream =
				Files.newDirectoryStream(companyIdDirectory)) {

			for (Path folderIdDirectory : companyIdDirectoryStream) {
				String folderIdDirectoryName = folderIdDirectory.getFileName(
				).toString();

				if (StringUtil.equals(
						folderIdDirectoryName, _ADAPTIVE_MEDIA_FOLDER_NAME)) {

					continue;
				}

				if (!Files.isDirectory(folderIdDirectory)) {
					_log.error(
						"Found file in advanced file system structure " +
							"directory (only directories expected): " +
								folderIdDirectory);

					return false;
				}

				if (!_validateAdvancedFileSystemSubdirectories(
						folderIdDirectory)) {

					return false;
				}
			}

			return true;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to check advanced file system structure in: " +
						companyIdDirectory,
					exception);
			}

			return false;
		}
	}

	private boolean _hasBasicFileSystemStructure(Path companyIdDirectory) {
		try (DirectoryStream<Path> companyIdDirectoryStream =
				Files.newDirectoryStream(companyIdDirectory)) {

			for (Path folderIdDirectory : companyIdDirectoryStream) {
				String folderIdDirectoryName = folderIdDirectory.getFileName(
				).toString();

				if (StringUtil.equals(
						folderIdDirectoryName, _ADAPTIVE_MEDIA_FOLDER_NAME)) {

					continue;
				}

				if (!Files.isDirectory(folderIdDirectory)) {
					_log.error(
						"Found file in basic file system structure directory " +
							"(only directories expected): " +
								folderIdDirectory);

					return false;
				}

				try (DirectoryStream<Path> folderIdDirectoryStream =
						Files.newDirectoryStream(folderIdDirectory)) {

					for (Path numericFileEntryNameDirectory :
							folderIdDirectoryStream) {

						if (!Files.isDirectory(numericFileEntryNameDirectory)) {
							_log.error(
								"Found file in basic file system structure " +
									"directory (only directories expected): " +
										numericFileEntryNameDirectory);

							return false;
						}

						String numericFileEntryNameDirectoryName =
							numericFileEntryNameDirectory.getFileName(
							).toString();

						if (numericFileEntryNameDirectoryName.contains(
								StringPool.PERIOD)) {

							_log.error(
								StringBundler.concat(
									"Found directory with extension in basic ",
									"file system structure (no extensions ",
									"expected): ",
									numericFileEntryNameDirectory.toString()));

							return false;
						}

						if (!_hasVersionFile(numericFileEntryNameDirectory)) {
							_log.error(
								StringBundler.concat(
									"Directory does not contain valid version ",
									"files as expected in basic file system ",
									"structure: ",
									numericFileEntryNameDirectory.toString()));

							return false;
						}
					}
				}
			}

			return true;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to check basic file system structure in: " +
						companyIdDirectory,
					exception);
			}

			return false;
		}
	}

	private boolean _hasVersionFile(Path numericFileEntryNameDirectory) {
		try (DirectoryStream<Path> numericFileEntryNameDirectoryStream =
				Files.newDirectoryStream(numericFileEntryNameDirectory)) {

			for (Path versionNumberFile : numericFileEntryNameDirectoryStream) {
				if (!Files.isDirectory(versionNumberFile)) {
					String versionNumberFileName =
						versionNumberFile.getFileName(
						).toString();

					if (!versionNumberFileName.matches("\\d+\\.\\d+.*")) {
						_log.error(
							"Found file that does not match version " +
								"structure (expected \\d+\\.\\d+.*): " +
									versionNumberFile);

						return false;
					}
				}
			}

			return true;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to check for version file in: " +
						numericFileEntryNameDirectory,
					exception);
			}

			return false;
		}
	}

	private boolean _validateAdvancedFileSystemSubdirectories(
		Path folderIdDirectory) {

		try (DirectoryStream<Path> folderIdDirectoryStream =
				Files.newDirectoryStream(folderIdDirectory)) {

			for (Path numericFileEntryNameDirectory : folderIdDirectoryStream) {
				if (!Files.isDirectory(numericFileEntryNameDirectory)) {
					_log.error(
						"Found file in advanced file system structure " +
							"directory (only directories expected): " +
								numericFileEntryNameDirectory);

					return false;
				}

				String numericFileEntryNameDirectoryName =
					numericFileEntryNameDirectory.getFileName(
					).toString();

				if (numericFileEntryNameDirectoryName.equals("DLFE")) {
					if (!_validateAdvancedFileSystemSubdirectories(
							numericFileEntryNameDirectory)) {

						return false;
					}
				}
				else if ((numericFileEntryNameDirectoryName.length() > 2) &&
						 Validator.isNull(
							 FileUtil.getExtension(
								 numericFileEntryNameDirectoryName))) {

					_log.error(
						StringBundler.concat(
							"Found directory with name longer than 2 without ",
							"extension in advanced file system structure ",
							"directory: ",
							numericFileEntryNameDirectory.toString()));

					return false;
				}
			}

			return true;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to validate subdirectories in: " +
						folderIdDirectory,
					exception);
			}

			return false;
		}
	}

	private static final String _ADAPTIVE_MEDIA_FOLDER_NAME = "0";

	private static final Log _log = LogFactoryUtil.getLog(
		PreupgradeVerifyFileSystemStoreStructure.class);

}