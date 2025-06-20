/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.verify.util.PreupgradeFileSystemStoreVerifyUtil;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Set;

/**
 * @author István András Dézsi
 */
public class PreupgradeVerifyFileSystemStoreStructure
	extends PreupgradeVerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		if (PropsValues.UPGRADE_DATABASE_DL_STORAGE_CHECK_DISABLED ||
			!PreupgradeFileSystemStoreVerifyUtil.isFileSystemStore() ||
			StartupHelperUtil.isDBNew()) {

			return;
		}

		boolean advancedFileSystemStore = StringUtil.equals(
			PropsValues.DL_STORE_IMPL, _ADVANCED_FILE_SYSTEM_STORE);
		Set<Long> companyIds = SetUtil.fromArray(
			PortalInstancePool.getCompanyIds());
		boolean fileSystemStore = StringUtil.equals(
			PropsValues.DL_STORE_IMPL, _FILE_SYSTEM_STORE);

		Path fileSystemStoreRootDirPath =
			PreupgradeFileSystemStoreVerifyUtil.getFileSystemStoreRootDirPath();

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

	private static final String _ADVANCED_FILE_SYSTEM_STORE =
		"com.liferay.portal.store.file.system.AdvancedFileSystemStore";

	private static final String _FILE_SYSTEM_STORE =
		"com.liferay.portal.store.file.system.FileSystemStore";

	private static final Log _log = LogFactoryUtil.getLog(
		PreupgradeVerifyFileSystemStoreStructure.class);

}