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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.FolderNameException;
import com.liferay.portlet.documentlibrary.InvalidFileVersionException;
import com.liferay.portlet.documentlibrary.SourceFileNameException;

import java.io.File;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public interface DLValidator {

	public boolean isValidName(String name);

	public void validateDirectoryName(String directoryName)
		throws FolderNameException;

	public void validateFileExtension(String fileName)
		throws FileExtensionException, SystemException;

	public void validateFileName(String fileName) throws FileNameException;

	public void validateFileSize(String fileName, byte[] bytes)
		throws FileSizeException, SystemException;

	public void validateFileSize(String fileName, File file)
		throws FileSizeException, SystemException;

	public void validateFileSize(String fileName, InputStream is)
		throws FileSizeException, SystemException;

	public void validateSourceFileExtension(
			String fileExtension, String sourceFileName)
		throws SourceFileNameException;

	public void validateVersionLabel(String versionLabel)
		throws InvalidFileVersionException;

}