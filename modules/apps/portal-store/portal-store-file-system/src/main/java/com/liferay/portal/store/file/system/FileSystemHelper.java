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

package com.liferay.portal.store.file.system;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Adolfo Pérez
 */
public class FileSystemHelper {

	public FileSystemHelper(boolean useHardLink, Path rootDirPath) {
		if (!useHardLink) {
			_supportHardLink = false;

			return;
		}

		boolean supportHardLink = false;

		Path sourceFilePath = null;
		Path destinationFilePath = null;

		try {
			sourceFilePath = Files.createTempFile(rootDirPath, null, null);

			Path fileNamePath = sourceFilePath.getFileName();

			destinationFilePath = sourceFilePath.resolveSibling(
				fileNamePath.toString() + "-link");

			Files.createLink(destinationFilePath, sourceFilePath);

			supportHardLink = true;
		}
		catch (IOException ioe) {
		}
		finally {
			try {
				Files.deleteIfExists(sourceFilePath);
			}
			catch (IOException ioe) {
			}

			try {
				Files.deleteIfExists(destinationFilePath);
			}
			catch (IOException ioe) {
			}
		}

		_supportHardLink = supportHardLink;
	}

	public void copy(File source, File destination) throws IOException {
		if (_supportHardLink) {
			Files.createLink(destination.toPath(), source.toPath());
		}
		else {
			destination.createNewFile();

			FileUtil.copyFile(source, destination);
		}
	}

	public void move(File source, File destination) {
		if (_supportHardLink) {
			try {
				Files.move(source.toPath(), destination.toPath());
			}
			catch (IOException ioe) {
				throw new SystemException(
					StringBundler.concat(
						"File name was not renamed from ", source.getPath(),
						" to ", destination.getPath()),
					ioe);
			}
		}
		else {
			boolean renamed = FileUtil.move(source, destination);

			if (!renamed) {
				throw new SystemException(
					StringBundler.concat(
						"File name was not renamed from ", source.getPath(),
						" to ", destination.getPath()));
			}
		}
	}

	private final boolean _supportHardLink;

}