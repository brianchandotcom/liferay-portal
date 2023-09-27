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
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.io.File;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public class DLValidatorUtil {

	public static String fixName(String name) {
		return _dlValidator.fixName(name);
	}

	public static long getMaxAllowableSize(long groupId, String mimeType) {
		return _dlValidator.getMaxAllowableSize(groupId, mimeType);
	}

	public static boolean isValidName(String name) {
		return _dlValidator.isValidName(name);
	}

	public static void validateDirectoryName(String directoryName)
		throws FolderNameException {

		_dlValidator.validateDirectoryName(directoryName);
	}

	public static void validateFileExtension(String fileName)
		throws FileExtensionException {

		_dlValidator.validateFileExtension(fileName);
	}

	public static void validateFileName(String fileName)
		throws FileNameException {

		_dlValidator.validateFileName(fileName);
	}

	public static void validateFileSize(
			long groupId, String fileName, String mimeType, byte[] bytes)
		throws FileSizeException {

		_dlValidator.validateFileSize(groupId, fileName, mimeType, bytes);
	}

	public static void validateFileSize(
			long groupId, String fileName, String mimeType, File file)
		throws FileSizeException {

		_dlValidator.validateFileSize(groupId, fileName, mimeType, file);
	}

	public static void validateFileSize(
			long groupId, String fileName, String mimeType,
			InputStream inputStream)
		throws FileSizeException {

		_dlValidator.validateFileSize(groupId, fileName, mimeType, inputStream);
	}

	public static void validateFileSize(
			long groupId, String fileName, String mimeType, long size)
		throws FileSizeException {

		_dlValidator.validateFileSize(groupId, fileName, mimeType, size);
	}

	public static void validateSourceFileExtension(
			String fileExtension, String sourceFileName)
		throws SourceFileNameException {

		_dlValidator.validateSourceFileExtension(fileExtension, sourceFileName);
	}

	public static void validateVersionLabel(String versionLabel)
		throws InvalidFileVersionException {

		_dlValidator.validateVersionLabel(versionLabel);
	}

	private static volatile DLValidator _dlValidator =
		ServiceProxyFactory.newServiceTrackedInstance(
			DLValidator.class, DLValidatorUtil.class, "_dlValidator", false);

}