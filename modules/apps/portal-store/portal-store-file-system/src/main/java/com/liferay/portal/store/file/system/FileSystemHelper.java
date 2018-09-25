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
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

/**
 * @author Adolfo Pérez
 */
public abstract class FileSystemHelper {

	public static FileSystemHelper createBasicFileSystemHelper() {
		return new BasicFileSystemHelper();
	}

	public static FileSystemHelper createHardLinkFileSystemHelper(
		File rootDir) {

		File sourceFile = null;
		File destinationFile = null;

		try {
			sourceFile = _getTemporaryFile(rootDir);

			if (sourceFile == null) {
				return createBasicFileSystemHelper();
			}

			FileUtil.touch(sourceFile);

			destinationFile = _getTemporaryFile(rootDir);

			if (destinationFile == null) {
				return createBasicFileSystemHelper();
			}

			Files.createLink(destinationFile.toPath(), sourceFile.toPath());

			return new HardLinkFileSystemHelper();
		}
		catch (IOException ioe) {
			return createBasicFileSystemHelper();
		}
		finally {
			if (sourceFile != null) {
				sourceFile.delete();
			}

			if (destinationFile != null) {
				destinationFile.delete();
			}
		}
	}

	public abstract void copy(File source, File destination) throws IOException;

	public abstract void move(File source, File destination);

	private static File _getTemporaryFile(File rootDir) {
		File tempFile = new File(rootDir, StringUtil.randomString(5));

		int tries = 0;

		while ((tries < _MAX_TRIES) && tempFile.exists()) {
			tempFile = new File(rootDir, StringUtil.randomString(5));

			tries++;
		}

		if (tries >= _MAX_TRIES) {
			return null;
		}

		return tempFile;
	}

	private static final int _MAX_TRIES = 10;

	private static final class BasicFileSystemHelper extends FileSystemHelper {

		@Override
		public void copy(File source, File destination) throws IOException {
			destination.createNewFile();

			FileUtil.copyFile(source, destination);
		}

		@Override
		public void move(File source, File destination) {
			boolean renamed = FileUtil.move(source, destination);

			if (!renamed) {
				throw new SystemException(
					StringBundler.concat(
						"File name was not renamed from ", source.getPath(),
						" to ", destination.getPath()));
			}
		}

	}

	private static final class HardLinkFileSystemHelper
		extends FileSystemHelper {

		@Override
		public void copy(File source, File destination) throws IOException {
			Files.createLink(source.toPath(), destination.toPath());
		}

		@Override
		public void move(File source, File destination) {
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

	}

}