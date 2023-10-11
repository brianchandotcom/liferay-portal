/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.util;

import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.exception.FolderNameException;
import com.liferay.document.library.kernel.exception.InvalidFileVersionException;
import com.liferay.document.library.kernel.exception.SourceFileNameException;
import com.liferay.portal.kernel.module.service.Snapshot;

import java.io.File;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public class DLValidatorUtil {

	public static String fixName(String name) {
		DLValidator dlValidator = _dlValidatorSnapshot.get();

		return dlValidator.fixName(name);
	}

	public static long getMaxAllowableSize(long groupId, String mimeType) {
		DLValidator dlValidator = _dlValidatorSnapshot.get();

		return dlValidator.getMaxAllowableSize(groupId, mimeType);
	}

	public static long getMaxAllowableSize(
		long groupId, String mimeType, long limit) {

		DLValidator dlValidator = _dlValidatorSnapshot.get();

		return dlValidator.getMaxAllowableSize(groupId, mimeType, limit);
	}

	public static boolean isValidName(String name) {
		DLValidator dlValidator = _dlValidatorSnapshot.get();

		return dlValidator.isValidName(name);
	}

	public static void validateDirectoryName(String directoryName)
		throws FolderNameException {

		DLValidator dlValidator = _dlValidatorSnapshot.get();

		dlValidator.validateDirectoryName(directoryName);
	}

	public static void validateFileExtension(String fileName)
		throws FileExtensionException {

		DLValidator dlValidator = _dlValidatorSnapshot.get();

		dlValidator.validateFileExtension(fileName);
	}

	public static void validateFileName(String fileName)
		throws FileNameException {

		DLValidator dlValidator = _dlValidatorSnapshot.get();

		dlValidator.validateFileName(fileName);
	}

	public static void validateFileSize(
			long groupId, String fileName, String mimeType, byte[] bytes)
		throws FileSizeException {

		DLValidator dlValidator = _dlValidatorSnapshot.get();

		dlValidator.validateFileSize(groupId, fileName, mimeType, bytes);
	}

	public static void validateFileSize(
			long groupId, String fileName, String mimeType, File file)
		throws FileSizeException {

		DLValidator dlValidator = _dlValidatorSnapshot.get();

		dlValidator.validateFileSize(groupId, fileName, mimeType, file);
	}

	public static void validateFileSize(
			long groupId, String fileName, String mimeType,
			InputStream inputStream)
		throws FileSizeException {

		DLValidator dlValidator = _dlValidatorSnapshot.get();

		dlValidator.validateFileSize(groupId, fileName, mimeType, inputStream);
	}

	public static void validateFileSize(
			long groupId, String fileName, String mimeType, long size)
		throws FileSizeException {

		DLValidator dlValidator = _dlValidatorSnapshot.get();

		dlValidator.validateFileSize(groupId, fileName, mimeType, size);
	}

	public static void validateSourceFileExtension(
			String fileExtension, String sourceFileName)
		throws SourceFileNameException {

		DLValidator dlValidator = _dlValidatorSnapshot.get();

		dlValidator.validateSourceFileExtension(fileExtension, sourceFileName);
	}

	public static void validateVersionLabel(String versionLabel)
		throws InvalidFileVersionException {

		DLValidator dlValidator = _dlValidatorSnapshot.get();

		dlValidator.validateVersionLabel(versionLabel);
	}

	private static final Snapshot<DLValidator> _dlValidatorSnapshot =
		new Snapshot<>(DLValidatorUtil.class, DLValidator.class);

}