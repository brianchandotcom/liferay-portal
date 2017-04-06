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
		return getDLValidator().fixName(name);
	}

	public static DLValidator getDLValidator() {
		return _dlValidator;
	}

	public static long getMaxAllowableSize() {
		return getDLValidator().getMaxAllowableSize();
	}

	public static boolean isValidName(String name) {
		return getDLValidator().isValidName(name);
	}

	public static final void validateDirectoryName(String directoryName)
		throws FolderNameException {

		getDLValidator().validateDirectoryName(directoryName);
	}

	public static void validateFileExtension(String fileName)
		throws FileExtensionException {

		getDLValidator().validateFileExtension(fileName);
	}

	public static void validateFileName(String fileName)
		throws FileNameException {

		getDLValidator().validateFileName(fileName);
	}

	public static void validateFileSize(String fileName, byte[] bytes)
		throws FileSizeException {

		getDLValidator().validateFileSize(fileName, bytes);
	}

	public static void validateFileSize(String fileName, File file)
		throws FileSizeException {

		getDLValidator().validateFileSize(fileName, file);
	}

	public static void validateFileSize(String fileName, InputStream is)
		throws FileSizeException {

		getDLValidator().validateFileSize(fileName, is);
	}

	public static void validateFileSize(String fileName, long size)
		throws FileSizeException {

		getDLValidator().validateFileSize(fileName, size);
	}

	public static void validateSourceFileExtension(
			String fileExtension, String sourceFileName)
		throws SourceFileNameException {

		getDLValidator().validateSourceFileExtension(
			fileExtension, sourceFileName);
	}

	public static void validateVersionLabel(String versionLabel)
		throws InvalidFileVersionException {

		getDLValidator().validateVersionLabel(versionLabel);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public void setDLValidator(DLValidator dlValidator) {
	}

	private static volatile DLValidator _dlValidator =
		ServiceProxyFactory.newServiceTrackedInstance(
			DLValidator.class, DLValidatorUtil.class, "_dlValidator", false);

}