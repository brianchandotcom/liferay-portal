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

import com.liferay.sync.engine.documentlibrary.util.FileEventUtil;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;

import java.io.IOException;
import java.io.InputStream;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class FileUtil {

	public static void checkFilePath(Path filePath) {

		// Check to see if the file or folder is still being written to. If
		// it is, wait until the process is finished before making any future
		// modifications. This is used to prevent file system interruptions.

		try {
			while (true) {
				long size1 = FileUtils.sizeOf(filePath.toFile());

				Thread.sleep(1000);

				long size2 = FileUtils.sizeOf(filePath.toFile());

				if (size1 == size2) {
					break;
				}
			}
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);
		}
	}

	public static boolean checksumsEqual(String checksum1, String checksum2) {
		if ((checksum1 == null) || (checksum2 == null) ||
			checksum1.isEmpty() || checksum2.isEmpty()) {

			return false;
		}

		return checksum1.equals(checksum2);
	}

	public static void deleteFile(Path filePath) {
		try {
			deleteFile(filePath, true);
		}
		catch (Exception e) {
		}
	}

	public static void deleteFile(final Path filePath, boolean retry)
		throws IOException {

		try {
			Files.deleteIfExists(filePath);
		}
		catch (Exception e) {
			if (!retry) {
				throw e;
			}

			PathCallable pathCallable = new PathCallable(filePath) {

				@Override
				public Object call() throws Exception {
					FileTime fileTime = Files.getLastModifiedTime(filePath);

					if (fileTime.toMillis() <= getStartTime()) {
						Files.deleteIfExists(filePath);
					}

					return null;
				}

			};

			FileLockRetryUtil.registerPathCallable(pathCallable);
		}
	}

	public static boolean exists(Path filePath) {
		try {
			Path realFilePath = filePath.toRealPath();

			String realFilePathString = realFilePath.toString();

			if (!realFilePathString.equals(filePath.toString())) {
				return false;
			}

			return Files.exists(filePath);
		}
		catch (Exception e) {
			return false;
		}
	}

	public static void fireDeleteEvents(Path filePath) throws IOException {
		long startTime = System.currentTimeMillis();

		Files.walkFileTree(
			filePath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					SyncFile syncFile = SyncFileService.fetchSyncFile(
						filePath.toString());

					if (syncFile == null) {
						syncFile = SyncFileService.fetchSyncFile(
							FileKeyUtil.getFileKey(filePath));
					}

					if (syncFile != null) {
						syncFile.setLocalSyncTime(System.currentTimeMillis());

						SyncFileService.update(syncFile);
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					SyncFile syncFile = SyncFileService.fetchSyncFile(
						filePath.toString());

					if (syncFile == null) {
						syncFile = SyncFileService.fetchSyncFile(
							FileKeyUtil.getFileKey(filePath));
					}

					if (syncFile != null) {
						syncFile.setLocalSyncTime(System.currentTimeMillis());

						SyncFileService.update(syncFile);
					}

					return FileVisitResult.CONTINUE;
				}

			}
		);

		List<SyncFile> deletedSyncFiles = SyncFileService.findSyncFiles(
			filePath.toString(), startTime);

		for (SyncFile deletedSyncFile : deletedSyncFiles) {
			if (!Files.notExists(
					Paths.get(deletedSyncFile.getFilePathName()))) {

				continue;
			}

			if (deletedSyncFile.getTypePK() == 0) {
				SyncFileService.deleteSyncFile(deletedSyncFile, false);

				continue;
			}

			if (deletedSyncFile.isFolder()) {
				FileEventUtil.deleteFolder(
					deletedSyncFile.getSyncAccountId(), deletedSyncFile);
			}
			else {
				FileEventUtil.deleteFile(
					deletedSyncFile.getSyncAccountId(), deletedSyncFile);
			}
		}
	}

	public static String getChecksum(Path filePath) throws IOException {
		if (!isValidChecksum(filePath)) {
			return "";
		}

		InputStream fileInputStream = null;

		try {
			fileInputStream = Files.newInputStream(filePath);

			byte[] bytes = DigestUtils.sha1(fileInputStream);

			return Base64.encodeBase64String(bytes);
		}
		finally {
			StreamUtil.cleanUp(fileInputStream);
		}
	}

	public static FileLock getFileLock(FileChannel fileChannel) {
		try {
			return fileChannel.tryLock();
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);

			return null;
		}
	}

	public static Path getFilePath(String first, String... more) {
		FileSystem fileSystem = FileSystems.getDefault();

		return fileSystem.getPath(first, more);
	}

	public static String getFilePathName(String first, String... more) {
		Path filePath = getFilePath(first, more);

		return filePath.toString();
	}

	public static long getLastModifiedTime(Path filePath) throws IOException {
		if (!Files.exists(filePath)) {
			return 0;
		}

		FileTime fileTime = Files.getLastModifiedTime(
			filePath, LinkOption.NOFOLLOW_LINKS);

		return fileTime.toMillis();
	}

	public static String getNextFilePathName(String filePathName) {
		Path filePath = Paths.get(filePathName);

		Path parentFilePath = filePath.getParent();

		for (int i = 0;; i++) {
			StringBuilder sb = new StringBuilder();

			sb.append(FilenameUtils.getBaseName(filePathName));

			if (i > 0) {
				sb.append(" (");
				sb.append(i);
				sb.append(")");
			}

			String extension = FilenameUtils.getExtension(filePathName);

			if (extension.length() > 0) {
				sb.append(".");
				sb.append(extension);
			}

			String tempFilePathName = FileUtil.getFilePathName(
				parentFilePath.toString(), sb.toString());

			if (SyncFileService.fetchSyncFile(tempFilePathName) == null) {
				Path tempFilePath = Paths.get(tempFilePathName);

				if (!Files.exists(tempFilePath)) {
					return tempFilePathName;
				}
			}
		}
	}

	public static String getSanitizedFileName(
		String fileName, String extension) {

		for (String blacklistChar : PropsValues.SYNC_FILE_BLACKLIST_CHARS) {
			fileName = fileName.replace(blacklistChar, "_");
		}

		for (String blacklistCharLast :
				PropsValues.SYNC_FILE_BLACKLIST_CHARS_LAST) {

			if (blacklistCharLast.startsWith("\\u")) {
				blacklistCharLast = StringEscapeUtils.unescapeJava(
					blacklistCharLast);
			}

			if (fileName.endsWith(blacklistCharLast)) {
				fileName = fileName.substring(0, fileName.length() - 1);
			}
		}

		if ((extension != null) && !extension.isEmpty()) {
			int x = fileName.lastIndexOf(".");

			if ((x == -1) ||
				!extension.equalsIgnoreCase(fileName.substring(x + 1))) {

				fileName += "." + extension;
			}
		}

		if (fileName.length() > 255) {
			int x = fileName.length() - 1;

			if ((extension != null) && !extension.isEmpty()) {
				x = fileName.lastIndexOf(".");
			}

			int y = x - (fileName.length() - 255);

			fileName = fileName.substring(0, y) + fileName.substring(x);
		}

		return fileName;
	}

	public static Path getTempFilePath(SyncFile syncFile) {
		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			syncFile.getSyncAccountId());

		if (syncAccount == null) {
			return null;
		}

		return getFilePath(
			syncAccount.getFilePathName(), ".data",
			String.valueOf(syncFile.getSyncFileId()));
	}

	public static boolean isHidden(Path filePath) {
		if (!Files.exists(filePath)) {
			return false;
		}

		try {
			return Files.isHidden(filePath);
		}
		catch (IOException ioe) {
			return false;
		}
	}

	public static boolean isIgnoredFileName(String fileName) {
		return _syncFileIgnoreNames.contains(
			StringEscapeUtils.escapeJava(fileName));
	}

	public static boolean isIgnoredFilePath(Path filePath) {
		String fileName = String.valueOf(filePath.getFileName());

		if (isIgnoredFileName(fileName) ||
			MSOfficeFileUtil.isTempCreatedFile(filePath) ||
			(PropsValues.SYNC_FILE_IGNORE_HIDDEN && isHidden(filePath)) ||
			Files.isSymbolicLink(filePath) || fileName.endsWith(".lnk")) {

			return true;
		}

		SyncFile syncFile = SyncFileService.fetchSyncFile(filePath.toString());

		if (syncFile == null) {
			return isIgnoredFilePath(filePath.getParent());
		}

		if (!syncFile.isSystem() &&
			(syncFile.getState() == SyncFile.STATE_UNSYNCED)) {

			return true;
		}

		return false;
	}

	public static boolean isModified(SyncFile syncFile) {
		if (syncFile.getFilePathName() == null) {
			return true;
		}

		Path filePath = Paths.get(syncFile.getFilePathName());

		return isModified(syncFile, filePath);
	}

	public static boolean isModified(SyncFile syncFile, Path filePath) {
		if ((filePath == null) || Files.notExists(filePath)) {
			return true;
		}

		try {
			if (MSOfficeFileUtil.isLegacyExcelFile(filePath)) {
				Date lastSavedDate = MSOfficeFileUtil.getLastSavedDate(
					filePath);

				if ((lastSavedDate != null) && (lastSavedDate.getTime() ==
						GetterUtil.getLong(
							syncFile.getLocalExtraSettingValue(
								"lastSavedDate")))) {

					return false;
				}
			}

			if (syncFile.getSize() > 0) {
				long lastModifiedTime = getLastModifiedTime(filePath);

				long modifiedTime = syncFile.getModifiedTime();

				if (OSDetector.isUnix()) {
					modifiedTime = modifiedTime / 1000 * 1000;
				}

				if (((lastModifiedTime == modifiedTime) ||
					 (lastModifiedTime ==
						 syncFile.getPreviousModifiedTime())) &&
					FileKeyUtil.hasFileKey(
						filePath, syncFile.getSyncFileId())) {

					return false;
				}
			}
		}
		catch (IOException ioe) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(ioe.getMessage(), ioe);
			}
		}

		try {
			if ((syncFile.getSize() > 0) &&
				(syncFile.getSize() != Files.size(filePath))) {

				return true;
			}
		}
		catch (IOException ioe) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(ioe.getMessage(), ioe);
			}
		}

		try {
			String checksum = getChecksum(filePath);

			return !checksumsEqual(checksum, syncFile.getChecksum());
		}
		catch (IOException ioe) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(ioe.getMessage(), ioe);
			}

			return true;
		}
	}

	public static boolean isValidChecksum(Path filePath) throws IOException {
		if (Files.notExists(filePath) ||
			(Files.size(filePath) >
				PropsValues.SYNC_FILE_CHECKSUM_THRESHOLD_SIZE)) {

			return false;
		}

		return true;
	}

	public static boolean isValidFileName(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			return false;
		}

		for (String blacklistChar : PropsValues.SYNC_FILE_BLACKLIST_CHARS) {
			if (fileName.contains(blacklistChar)) {
				return false;
			}
		}

		for (String blacklistLastChar :
				PropsValues.SYNC_FILE_BLACKLIST_CHARS_LAST) {

			if (blacklistLastChar.startsWith("\\u")) {
				blacklistLastChar = StringEscapeUtils.unescapeJava(
					blacklistLastChar);
			}

			if (fileName.endsWith(blacklistLastChar)) {
				return false;
			}
		}

		String nameWithoutExtension = FilenameUtils.removeExtension(fileName);

		for (String blacklistName : PropsValues.SYNC_FILE_BLACKLIST_NAMES) {
			if (nameWithoutExtension.equalsIgnoreCase(blacklistName)) {
				return false;
			}
		}

		return true;
	}

	public static void moveFile(Path sourceFilePath, Path targetFilePath) {
		try {
			moveFile(sourceFilePath, targetFilePath, true);
		}
		catch (Exception e) {
		}
	}

	public static void moveFile(
			final Path sourceFilePath, final Path targetFilePath, boolean retry)
		throws IOException {

		try {
			Files.move(
				sourceFilePath, targetFilePath, StandardCopyOption.ATOMIC_MOVE,
				StandardCopyOption.REPLACE_EXISTING);
		}
		catch (IOException ioe) {
			if (!retry) {
				throw ioe;
			}

			PathCallable pathCallable = new PathCallable(sourceFilePath) {

				@Override
				public Object call() throws Exception {
					FileTime fileTime = Files.getLastModifiedTime(
						targetFilePath);

					if (fileTime.toMillis() <= getStartTime()) {
						Files.move(
							sourceFilePath, targetFilePath,
							StandardCopyOption.ATOMIC_MOVE,
							StandardCopyOption.REPLACE_EXISTING);
					}
					else {
						Files.deleteIfExists(sourceFilePath);
					}

					return null;
				}

			};

			FileLockRetryUtil.registerPathCallable(pathCallable);
		}
	}

	public static void releaseFileLock(FileLock fileLock) {
		try {
			if (fileLock != null) {
				fileLock.release();
			}
		}
		catch (Exception e) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(e.getMessage(), e);
			}
		}
	}

	public static void setModifiedTime(Path filePath, long modifiedTime)
		throws IOException {

		if (!Files.exists(filePath)) {
			return;
		}

		FileTime fileTime = FileTime.fromMillis(modifiedTime);

		Files.setLastModifiedTime(filePath, fileTime);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		FileUtil.class);

	private static final Set<String> _syncFileIgnoreNames = new HashSet<>(
		Arrays.asList(PropsValues.SYNC_FILE_IGNORE_NAMES));

}