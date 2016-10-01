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

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

import org.apache.commons.lang.StringEscapeUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shinn Lok
 */
public class FileUtilTest {

	@Test
	public void testChecksumsEqual() {
		Assert.assertFalse(FileUtil.checksumsEqual(null, null));
		Assert.assertFalse(FileUtil.checksumsEqual("", ""));
		Assert.assertTrue(
			FileUtil.checksumsEqual(
				"da39a3ee5e6b4b0d3255bfef95601890afd80709",
				"da39a3ee5e6b4b0d3255bfef95601890afd80709"));
	}

	@Test
	public void testDeleteFile() throws Exception {
		Path filePath = Files.createTempFile("test", null);

		Assert.assertTrue(FileUtil.exists(filePath));

		FileUtil.deleteFile(filePath);

		Assert.assertTrue(FileUtil.notExists(filePath));
	}

	@Test
	public void testGetFilePath() {
		FileSystem fileSystem = FileSystems.getDefault();

		Path filePath = FileUtil.getFilePath("test", "test");

		Assert.assertEquals(
			"test" + fileSystem.getSeparator() + "test", filePath.toString());
	}

	@Test
	public void testGetFilePathName() {
		FileSystem fileSystem = FileSystems.getDefault();

		Assert.assertEquals(
			"test" + fileSystem.getSeparator() + "test",
			FileUtil.getFilePathName("test", "test"));
	}

	@Test
	public void testGetNextFilePathName() throws Exception {
		Path filePath = Files.createTempFile("test", null);

		String filePathName = filePath.toString();

		String nextFilePathName = FileUtil.getNextFilePathName(filePathName);

		Assert.assertEquals(
			filePathName.substring(0, filePathName.length() - 4) + " (1).tmp",
			nextFilePathName);
	}

	@Test
	public void testGetSanitizedFileName() {
		for (String blacklistChar : PropsValues.SYNC_FILE_BLACKLIST_CHARS) {
			String fileName = "test" + blacklistChar + "test";

			Assert.assertEquals(
				"test_test.tmp",
				FileUtil.getSanitizedFileName(fileName, "tmp"));
		}

		for (String blacklistChar :
				PropsValues.SYNC_FILE_BLACKLIST_CHARS_LAST) {

			if (blacklistChar.startsWith("\\u")) {
				blacklistChar = StringEscapeUtils.unescapeJava(blacklistChar);
			}

			String fileName = "test" + blacklistChar;

			Assert.assertEquals(
				"test", FileUtil.getSanitizedFileName(fileName, null));
		}
	}

	@Test
	public void testIsHidden() throws Exception {
		Path filePath = Files.createTempFile(".test", null);

		if (OSDetector.isWindows()) {
			Files.setAttribute(filePath, "dos:hidden", true);
		}

		Assert.assertTrue(FileUtil.isHidden(filePath));
	}

	@Test
	public void testIsIgnoredFilePath() throws Exception {
		Path filePath = Files.createTempDirectory("test");

		for (String ignoredFileName : PropsValues.SYNC_FILE_IGNORE_NAMES) {
			Path ignoredFilePath = filePath.resolve(
				StringEscapeUtils.unescapeJava(ignoredFileName));

			Assert.assertTrue(FileUtil.isIgnoredFilePath(ignoredFilePath));
		}
	}

	@Test
	public void testMoveFile() throws Exception {
		Path sourceFilePath = Files.createTempFile("test", null);

		Assert.assertTrue(FileUtil.exists(sourceFilePath));

		Path targetDirectoryFilePath = Files.createTempDirectory("test");

		Path targetFilePath = targetDirectoryFilePath.resolve(
			sourceFilePath.getFileName());

		FileUtil.moveFile(sourceFilePath, targetFilePath);

		Assert.assertTrue(FileUtil.notExists(sourceFilePath));
		Assert.assertTrue(FileUtil.exists(targetFilePath));
	}

	@Test
	public void testRenameFile() throws Exception {
		Path sourceFilePath = Files.createTempFile("test", null);

		Path parentFilePath = sourceFilePath.getParent();

		String sourceFilePathFileName = String.valueOf(
			sourceFilePath.getFileName());

		Path targetFilePath = parentFilePath.resolve(
			sourceFilePathFileName.toUpperCase());

		FileUtil.moveFile(sourceFilePath, targetFilePath);

		Path realFilePath = targetFilePath.toRealPath();

		Path realFilePathFileName = realFilePath.getFileName();

		Assert.assertFalse(sourceFilePath.endsWith(realFilePathFileName));
		Assert.assertTrue(targetFilePath.endsWith(realFilePathFileName));
	}

	@Test
	public void testSetModifiedTime() throws Exception {
		Path filePath = Files.createTempFile("test", "test");

		long modifiedTime = System.currentTimeMillis();

		FileUtil.setModifiedTime(filePath, modifiedTime);

		FileTime modifiedFileTime = Files.getLastModifiedTime(filePath);

		Assert.assertEquals(
			modifiedTime / 1000, modifiedFileTime.toMillis() / 1000);
	}

}