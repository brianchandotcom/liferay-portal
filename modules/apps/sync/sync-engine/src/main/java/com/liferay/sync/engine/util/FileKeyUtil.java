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

package com.liferay.sync.engine.util;

import ch.securityvision.xattrj.Xattrj;

import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.service.SyncFileService;

import java.io.File;
import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.UserDefinedFileAttributeView;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class FileKeyUtil {

	public static long getFileKey(Path filePath) {
		if (!FileUtil.isRealFilePath(filePath) || !FileUtil.exists(filePath)) {
			return -1;
		}

		try {
			if (OSDetector.isApple()) {
				Xattrj xattrj = getXattrj();

				if (xattrj == null) {
					return -1;
				}

				String fileKey = xattrj.readAttribute(
					filePath.toFile(), "fileKey");

				if (fileKey == null) {
					return -1;
				}

				return Long.parseLong(fileKey);
			}
			else {
				UserDefinedFileAttributeView userDefinedFileAttributeView =
					Files.getFileAttributeView(
						filePath, UserDefinedFileAttributeView.class);

				List<String> list = userDefinedFileAttributeView.list();

				if (!list.contains("fileKey")) {
					return -1;
				}

				ByteBuffer byteBuffer = ByteBuffer.allocate(20);

				userDefinedFileAttributeView.read("fileKey", byteBuffer);

				CharBuffer charBuffer = _CHARSET.decode(
					(ByteBuffer)byteBuffer.flip());

				return Long.parseLong(charBuffer.toString());
			}
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);

			return -1;
		}
	}

	public static boolean hasFileKey(Path filePath, long fileKey) {
		if (getFileKey(filePath) == fileKey) {
			return true;
		}

		return false;
	}

	public static void writeFileKey(
		final Path filePath, final String fileKey, boolean acquireFileLock) {

		if (hasFileKey(filePath, Long.parseLong(fileKey))) {
			return;
		}

		if (!acquireFileLock) {
			doWriteFileKey(filePath, fileKey);

			return;
		}

		PathCallable pathCallable = new PathCallable(filePath) {

			@Override
			public Object call() throws Exception {
				doWriteFileKey(filePath, fileKey);

				return null;
			}

		};

		FileLockRetryUtil.registerPathCallable(pathCallable);
	}

	public static void writeFileKeys(final Path filePath) throws IOException {
		Files.walkFileTree(
			filePath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					SyncFile syncFile = SyncFileService.fetchSyncFile(
						filePath.toString());

					if (syncFile == null) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					doWriteFileKey(
						filePath, String.valueOf(syncFile.getSyncFileId()));

					return super.preVisitDirectory(
						filePath, basicFileAttributes);
				}

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					SyncFile syncFile = SyncFileService.fetchSyncFile(
						filePath.toString());

					if (syncFile != null) {
						doWriteFileKey(
							filePath, String.valueOf(syncFile.getSyncFileId()));
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	protected static void doWriteFileKey(Path filePath, String fileKey) {
		if (hasFileKey(filePath, Long.parseLong(fileKey))) {
			return;
		}

		if (OSDetector.isApple()) {
			Xattrj xattrj = getXattrj();

			if (xattrj == null) {
				return;
			}

			File file = filePath.toFile();

			if (!file.canWrite()) {
				file.setWritable(true);
			}

			xattrj.writeAttribute(file, "fileKey", fileKey);
		}
		else {
			File file = filePath.toFile();

			if (!file.canWrite()) {
				file.setWritable(true);
			}

			UserDefinedFileAttributeView userDefinedFileAttributeView =
				Files.getFileAttributeView(
					filePath, UserDefinedFileAttributeView.class);

			try {
				userDefinedFileAttributeView.write(
					"fileKey", _CHARSET.encode(CharBuffer.wrap(fileKey)));
			}
			catch (Exception e) {
				_logger.error(e.getMessage(), e);
			}
		}
	}

	protected static Xattrj getXattrj() {
		if (_xattrj != null) {
			return _xattrj;
		}

		try {
			_xattrj = new Xattrj();

			return _xattrj;
		}
		catch (IOException ioe) {
			_logger.error(ioe.getMessage(), ioe);

			return null;
		}
	}

	private static final Charset _CHARSET = Charset.forName("UTF-8");

	private static final Logger _logger = LoggerFactory.getLogger(
		FileKeyUtil.class);

	private static Xattrj _xattrj;

}