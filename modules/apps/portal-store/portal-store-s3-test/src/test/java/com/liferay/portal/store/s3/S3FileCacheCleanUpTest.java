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

package com.liferay.portal.store.s3;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.FileImpl;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Eric Yan
 */
public class S3FileCacheCleanUpTest {

	@Test
	public void testExpiredCacheDirWithSingleFile() throws IOException {
		Path cacheDirPath = Files.createTempDirectory("cacheDir");

		Path cachedFilePath = Files.createTempFile(
			cacheDirPath, "cachedFile", null);

		long expirationTime = _getLastModifiedTimeInMillis(cacheDirPath) + 1;

		_cleanUpCacheFiles(cacheDirPath, expirationTime);

		Assert.assertFalse(Files.exists(cacheDirPath));

		Assert.assertFalse(Files.exists(cachedFilePath));
	}

	@Test
	public void testExpiredCacheDirWithSingleFileAndExpiredEmptySubdirectory()
		throws IOException {

		Path cacheDirPath = Files.createTempDirectory("cacheDir");

		Path cachedFilePath = Files.createTempFile(
			cacheDirPath, "cachedFile", null);

		Path subdirPath = Files.createTempDirectory(cacheDirPath, "subdir");

		long expirationTime = _getLastModifiedTimeInMillis(subdirPath) + 1;

		_cleanUpCacheFiles(cacheDirPath, expirationTime);

		Assert.assertTrue(Files.exists(cacheDirPath));
		Assert.assertTrue(Files.exists(cachedFilePath));
		Assert.assertFalse(Files.exists(subdirPath));
	}

	@Test
	public void testExpiredCacheDirWithSingleFileAndExpiredEmptySubdirectoryAndExpireCacheDirAgain()
		throws Exception {

		Path cacheDirPath = Files.createTempDirectory("cacheDir");

		Path cachedFilePath = Files.createTempFile(
			cacheDirPath, "cachedFile", null);

		Path subdirPath = Files.createTempDirectory(cacheDirPath, "subdir");

		// Simulate that we created the cacheDir and cachedFile 2 secs ago,
		// before creating the subdir (Needed to account for Unit test,
		// creating and deleting files too fast)

		long timeInMillis = System.currentTimeMillis();

		Files.setLastModifiedTime(
			cacheDirPath, FileTime.fromMillis(timeInMillis - 2000));
		Files.setLastModifiedTime(
			cachedFilePath, FileTime.fromMillis(timeInMillis - 2000));
		Files.setLastModifiedTime(
			subdirPath, FileTime.fromMillis(timeInMillis));

		long expirationTime = timeInMillis + 1;

		_cleanUpCacheFiles(cacheDirPath, expirationTime);

		Assert.assertTrue(Files.exists(cacheDirPath));
		Assert.assertTrue(Files.exists(cachedFilePath));
		Assert.assertFalse(Files.exists(subdirPath));

		// Simulate that the cacheDir's lastModifiedTime has been incremented
		// by 1 second, due to the deletion of the subdir (Needed to account
		// for Unit test, creating and deleting files too fast)

		Files.setLastModifiedTime(
			cacheDirPath, FileTime.fromMillis(timeInMillis + 1000));

		expirationTime = _getLastModifiedTimeInMillis(cacheDirPath) + 1;

		_cleanUpCacheFiles(cacheDirPath, expirationTime);

		Assert.assertFalse(Files.exists(cacheDirPath));
		Assert.assertFalse(Files.exists(cachedFilePath));
		Assert.assertFalse(Files.exists(subdirPath));
	}

	@Test
	public void testExpiredCacheDirWithSingleFileAndExpiredSubdirectoryWithSingleFile()
		throws IOException {

		Path cacheDirPath = Files.createTempDirectory("cacheDir");

		Path cachedFilePath = Files.createTempFile(
			cacheDirPath, "cachedFile", null);

		Path subdirPath = Files.createTempDirectory(cacheDirPath, "subdir");

		Path subdirCachedFilePath = Files.createTempFile(
			subdirPath, "subdirCachedFile", null);

		long expirationTime = _getLastModifiedTimeInMillis(subdirPath) + 1;

		_cleanUpCacheFiles(cacheDirPath, expirationTime);

		Assert.assertTrue(Files.exists(cacheDirPath));
		Assert.assertTrue(Files.exists(cachedFilePath));
		Assert.assertFalse(Files.exists(subdirPath));
		Assert.assertFalse(Files.exists(subdirCachedFilePath));
	}

	@Test
	public void testExpiredCacheDirWithSingleFileAndExpiredSubdirectoryWithSingleFileAndExpireCacheDirAgain()
		throws IOException {

		Path cacheDirPath = Files.createTempDirectory("cacheDir");

		Path cachedFilePath = Files.createTempFile(
			cacheDirPath, "cachedFile", null);

		Path subdirPath = Files.createTempDirectory(cacheDirPath, "subdir");

		Path subdirCachedFilePath = Files.createTempFile(
			subdirPath, "subdirCachedFile", null);

		// Simulate that we created the cacheDir and cachedFile 2 secs ago,
		// before creating the subdir and subdirCachedFile (Needed to account
		// for Unit test, creating and deleting files too fast)

		long timeInMillis = System.currentTimeMillis();

		Files.setLastModifiedTime(
			cacheDirPath, FileTime.fromMillis(timeInMillis - 2000));
		Files.setLastModifiedTime(
			cachedFilePath, FileTime.fromMillis(timeInMillis - 2000));
		Files.setLastModifiedTime(
			subdirPath, FileTime.fromMillis(timeInMillis));
		Files.setLastModifiedTime(
			subdirCachedFilePath, FileTime.fromMillis(timeInMillis));

		long expirationTime = timeInMillis + 1;

		_cleanUpCacheFiles(cacheDirPath, expirationTime);

		Assert.assertTrue(Files.exists(cacheDirPath));
		Assert.assertTrue(Files.exists(cachedFilePath));
		Assert.assertFalse(Files.exists(subdirPath));
		Assert.assertFalse(Files.exists(subdirCachedFilePath));

		// Simulate that the cacheDir's lastModifiedTime has been incremented
		// by 1 second, due to the deletion of the subdir (Needed to account
		// for Unit test, creating and deleting files too fast)

		Files.setLastModifiedTime(
			cacheDirPath, FileTime.fromMillis(timeInMillis + 1000));

		expirationTime = _getLastModifiedTimeInMillis(cacheDirPath) + 1;

		_cleanUpCacheFiles(cacheDirPath, expirationTime);

		Assert.assertFalse(Files.exists(cacheDirPath));
		Assert.assertFalse(Files.exists(cachedFilePath));
		Assert.assertFalse(Files.exists(subdirPath));
		Assert.assertFalse(Files.exists(subdirCachedFilePath));
	}

	@Test
	public void testExpiredCacheDirWithSingleFileAndValidEmptySubdirectory()
		throws IOException {

		Path cacheDirPath = Files.createTempDirectory("cacheDir");

		Path cachedFilePath = Files.createTempFile(
			cacheDirPath, "cachedFile", null);

		Path subdirPath = Files.createTempDirectory(cacheDirPath, "subdir");

		long expirationTime = _getLastModifiedTimeInMillis(subdirPath);

		_cleanUpCacheFiles(cacheDirPath, expirationTime);

		Assert.assertTrue(Files.exists(cacheDirPath));
		Assert.assertTrue(Files.exists(cachedFilePath));
		Assert.assertTrue(Files.exists(subdirPath));
	}

	@Test
	public void testExpiredCacheDirWithSingleFileAndValidSubdirectoryWithSingleFile()
		throws IOException {

		Path cacheDirPath = Files.createTempDirectory("cacheDir");

		Path cachedFilePath = Files.createTempFile(
			cacheDirPath, "cachedFile", null);

		Path subdirPath = Files.createTempDirectory(cacheDirPath, "subdir");

		Path subdirCachedFilePath = Files.createTempFile(
			subdirPath, "subdirCachedFile", null);

		long expirationTime = _getLastModifiedTimeInMillis(subdirPath);

		_cleanUpCacheFiles(cacheDirPath, expirationTime);

		Assert.assertTrue(Files.exists(cacheDirPath));
		Assert.assertTrue(Files.exists(cachedFilePath));
		Assert.assertTrue(Files.exists(subdirPath));
		Assert.assertTrue(Files.exists(subdirCachedFilePath));
	}

	@Test
	public void testExpiredEmptyCacheDir() throws IOException {
		Path cacheDirPath = Files.createTempDirectory("cacheDir");

		long expirationTime = _getLastModifiedTimeInMillis(cacheDirPath) + 1;

		_cleanUpCacheFiles(cacheDirPath, expirationTime);

		Assert.assertFalse(Files.exists(cacheDirPath));
	}

	@Test
	public void testValidCacheDirWithSingleFile() throws IOException {
		Path cacheDirPath = Files.createTempDirectory("cacheDir");

		Path cachedFilePath = Files.createTempFile(
			cacheDirPath, "cachedFile", null);

		long expirationTime = _getLastModifiedTimeInMillis(cacheDirPath);

		_cleanUpCacheFiles(cacheDirPath, expirationTime);

		Assert.assertTrue(Files.exists(cacheDirPath));

		Assert.assertTrue(Files.exists(cachedFilePath));
	}

	@Test
	public void testValidEmptyCacheDir() throws IOException {
		Path cacheDirPath = Files.createTempDirectory("cacheDir");

		long expirationTime = _getLastModifiedTimeInMillis(cacheDirPath);

		_cleanUpCacheFiles(cacheDirPath, expirationTime);

		Assert.assertTrue(Files.exists(cacheDirPath));
	}

	private void _cleanUpCacheFiles(Path cacheDirPath, long expirationTime) {
		ReflectionTestUtil.invoke(
			_s3FileCache, "cleanUpCacheFiles",
			new Class<?>[] {File.class, long.class}, cacheDirPath.toFile(),
			expirationTime);
	}

	private long _getLastModifiedTimeInMillis(Path path) throws IOException {
		FileTime fileTime = Files.getLastModifiedTime(path);

		return fileTime.toMillis();
	}

	private static final S3FileCache _s3FileCache = new S3FileCacheImpl();

	static {
		ReflectionTestUtil.setFieldValue(
			FileUtil.class, "_file", FileImpl.getInstance());
	}

}