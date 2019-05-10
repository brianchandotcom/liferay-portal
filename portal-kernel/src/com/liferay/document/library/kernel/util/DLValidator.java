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

import java.io.File;
import java.io.InputStream;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Adolfo Pérez
 */
@ProviderType
public interface DLValidator {

	public String fixName(String name);

	public long getMaxAllowableSize();

	public boolean isValidName(String name);

	public void validateDirectoryName(String directoryName)
		throws FolderNameException;

	public void validateFileExtension(String fileName)
		throws FileExtensionException;

	public void validateFileName(String fileName) throws FileNameException;

	public void validateFileSize(String fileName, byte[] bytes)
		throws FileSizeException;

	public void validateFileSize(String fileName, File file)
		throws FileSizeException;

	public void validateFileSize(String fileName, InputStream is)
		throws FileSizeException;

	public void validateFileSize(String fileName, long size)
		throws FileSizeException;

	public void validateSourceFileExtension(
			String fileExtension, String sourceFileName)
		throws SourceFileNameException;

	public void validateVersionLabel(String versionLabel)
		throws InvalidFileVersionException;

}