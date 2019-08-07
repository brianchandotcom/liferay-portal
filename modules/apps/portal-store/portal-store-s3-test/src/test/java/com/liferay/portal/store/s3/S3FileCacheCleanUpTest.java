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

import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Eric Yan
 */
public class S3FileCacheCleanUpTest {

	@Before
	public void setUp() throws IOException {
		_cacheDirPath = Files.createTempDirectory("cacheDir");
	}

	@After
	public void tearDown() throws IOException {
		if (Files.notExists(_cacheDirPath)) {
			return;
		}

		Files.walkFileTree(
			_cacheDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path dirPath, IOException ioException)
					throws IOException {

					Files.delete(dirPath);

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Files.delete(filePath);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	@Test
	public void testExpiredCacheDirWithSingleFile() throws IOException {
		Path cachedFilePath = Files.createTempFile(
			_cacheDirPath, "cachedFile", null);

		long expirationTime = _getLastModifiedTimeInMillis(_cacheDirPath) + 1;

		_cleanUpCacheFiles(_cacheDirPath, expirationTime);

		Assert.assertFalse(Files.exists(_cacheDirPath));

		Assert.assertFalse(Files.exists(cachedFilePath));
	}

	@Test
	public void testExpiredCacheDirWithSingleFileAndExpiredEmptySubdirectory()
		throws IOException {

		Path cachedFilePath = Files.createTempFile(
			_cacheDirPath, "cachedFile", null);

		Path subdirPath = Files.createTempDirectory(_cacheDirPath, "subdir");

		long currentTime = System.currentTimeMillis();

		Files.setLastModifiedTime(
			_cacheDirPath, FileTime.fromMillis(currentTime - 5000));
		Files.setLastModifiedTime(
			cachedFilePath, FileTime.fromMillis(currentTime - 5000));
		Files.setLastModifiedTime(
			subdirPath, FileTime.fromMillis(currentTime - 3000));

		long expirationTime = _getLastModifiedTimeInMillis(subdirPath) + 1;

		_cleanUpCacheFiles(_cacheDirPath, expirationTime);

		Assert.assertFalse(Files.exists(_cacheDirPath));
		Assert.assertFalse(Files.exists(cachedFilePath));
		Assert.assertFalse(Files.exists(subdirPath));
	}

	@Test
	public void testExpiredCacheDirWithSingleFileAndExpiredSubdirectoryWithSingleFile()
		throws IOException {

		Path cachedFilePath = Files.createTempFile(
			_cacheDirPath, "cachedFile", null);

		Path subdirPath = Files.createTempDirectory(_cacheDirPath, "subdir");

		Path subdirCachedFilePath = Files.createTempFile(
			subdirPath, "subdirCachedFile", null);

		long currentTime = System.currentTimeMillis();

		Files.setLastModifiedTime(
			_cacheDirPath, FileTime.fromMillis(currentTime - 5000));
		Files.setLastModifiedTime(
			cachedFilePath, FileTime.fromMillis(currentTime - 5000));
		Files.setLastModifiedTime(
			subdirPath, FileTime.fromMillis(currentTime - 3000));
		Files.setLastModifiedTime(
			subdirCachedFilePath, FileTime.fromMillis(currentTime - 3000));

		long expirationTime = _getLastModifiedTimeInMillis(subdirPath) + 1;

		_cleanUpCacheFiles(_cacheDirPath, expirationTime);

		Assert.assertFalse(Files.exists(_cacheDirPath));
		Assert.assertFalse(Files.exists(cachedFilePath));
		Assert.assertFalse(Files.exists(subdirPath));
		Assert.assertFalse(Files.exists(subdirCachedFilePath));
	}

	@Test
	public void testExpiredCacheDirWithSingleFileAndValidEmptySubdirectory()
		throws IOException {

		Path cachedFilePath = Files.createTempFile(
			_cacheDirPath, "cachedFile", null);

		Path subdirPath = Files.createTempDirectory(_cacheDirPath, "subdir");

		long currentTime = System.currentTimeMillis();

		Files.setLastModifiedTime(
			_cacheDirPath, FileTime.fromMillis(currentTime - 5000));
		Files.setLastModifiedTime(
			cachedFilePath, FileTime.fromMillis(currentTime - 5000));

		long expirationTime = _getLastModifiedTimeInMillis(subdirPath);

		_cleanUpCacheFiles(_cacheDirPath, expirationTime);

		Assert.assertFalse(Files.exists(_cacheDirPath));
		Assert.assertFalse(Files.exists(cachedFilePath));
		Assert.assertFalse(Files.exists(subdirPath));
	}

	@Test
	public void testExpiredCacheDirWithSingleFileAndValidSubdirectoryWithSingleFile()
		throws IOException {

		Path cachedFilePath = Files.createTempFile(
			_cacheDirPath, "cachedFile", null);

		Path subdirPath = Files.createTempDirectory(_cacheDirPath, "subdir");

		Path subdirCachedFilePath = Files.createTempFile(
			subdirPath, "subdirCachedFile", null);

		long currentTime = System.currentTimeMillis();

		Files.setLastModifiedTime(
			_cacheDirPath, FileTime.fromMillis(currentTime - 5000));
		Files.setLastModifiedTime(
			cachedFilePath, FileTime.fromMillis(currentTime - 5000));

		long expirationTime = _getLastModifiedTimeInMillis(subdirPath);

		_cleanUpCacheFiles(_cacheDirPath, expirationTime);

		Assert.assertTrue(Files.exists(_cacheDirPath));
		Assert.assertFalse(Files.exists(cachedFilePath));
		Assert.assertTrue(Files.exists(subdirPath));
		Assert.assertTrue(Files.exists(subdirCachedFilePath));
	}

	@Test
	public void testExpiredEmptyCacheDir() throws IOException {
		long expirationTime = _getLastModifiedTimeInMillis(_cacheDirPath) + 1;

		_cleanUpCacheFiles(_cacheDirPath, expirationTime);

		Assert.assertFalse(Files.exists(_cacheDirPath));
	}

	@Test
	public void testValidCacheDirWithSingleFile() throws IOException {
		Path cachedFilePath = Files.createTempFile(
			_cacheDirPath, "cachedFile", null);

		long expirationTime = _getLastModifiedTimeInMillis(_cacheDirPath);

		_cleanUpCacheFiles(_cacheDirPath, expirationTime);

		Assert.assertTrue(Files.exists(_cacheDirPath));

		Assert.assertTrue(Files.exists(cachedFilePath));
	}

	@Test
	public void testValidEmptyCacheDir() throws IOException {
		long expirationTime = _getLastModifiedTimeInMillis(_cacheDirPath);

		_cleanUpCacheFiles(_cacheDirPath, expirationTime);

		Assert.assertFalse(Files.exists(_cacheDirPath));
	}

	private void _cleanUpCacheFiles(Path cacheDirPath, long expirationTime) {
		ReflectionTestUtil.invoke(
			_s3FileCache, "cleanUpCacheFiles",
			new Class<?>[] {Path.class, long.class}, cacheDirPath,
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

	private Path _cacheDirPath;

}