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

package com.liferay.upload.web.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.FileImpl;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
public class DefaultUniqueFileNameProviderTest {

	@Before
	public void setUp() {
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());
	}

	@Test
	public void testAppendsAParentheticalSuffixIfTheFileExists()
		throws Exception {

		String originalFileName = "filename.extension";

		for (int i = 1; i <= 50; i++) {
			String uniqueFileName = _defaultUniqueFileNameProvider.provide(
				originalFileName, _existsUntil(i));

			Assert.assertEquals(
				"filename (" + i + ").extension", uniqueFileName);
		}
	}

	@Test
	public void testAppendsAParentheticalSuffixIfTheFileExistsWithNoExtension()
		throws Exception {

		String originalFileName = "filename";

		String uniqueFileName = _defaultUniqueFileNameProvider.provide(
			originalFileName, _existsUntil(1));

		Assert.assertEquals("filename (1)", uniqueFileName);
	}

	@Test(expected = PortalException.class)
	public void testGivesUpIfTheFileExists51Times() throws Exception {
		_defaultUniqueFileNameProvider.provide(
			"filename.extension", _existsUntil(51));
	}

	@Test
	public void testModifiesOnlyTheLastExistingParentheticalSuffix()
		throws Exception {

		String originalFileName = "filename (1) (2) (3) (4) (1).extension";

		String uniqueFileName = _defaultUniqueFileNameProvider.provide(
			originalFileName, _existsUntil(2));

		Assert.assertEquals(
			"filename (1) (2) (3) (4) (2).extension", uniqueFileName);
	}

	@Test
	public void testModifiesTheExistingParentheticalSuffix() throws Exception {
		String originalFileName = "filename (1).extension";

		String uniqueFileName = _defaultUniqueFileNameProvider.provide(
			originalFileName, _existsUntil(2));

		Assert.assertEquals("filename (2).extension", uniqueFileName);
	}

	@Test
	public void testModifiesTheExistingParentheticalSuffixWithNoExtension()
		throws Exception {

		String originalFileName = "filename (1)";

		String uniqueFileName = _defaultUniqueFileNameProvider.provide(
			originalFileName, _existsUntil(2));

		Assert.assertEquals("filename (2)", uniqueFileName);
	}

	@Test
	public void testReturnsTheSameFileNameIfTheFileDoesNotExist()
		throws Exception {

		String originalFileName = "filename.extension";

		String uniqueFileName = _defaultUniqueFileNameProvider.provide(
			originalFileName, fileName -> false);

		Assert.assertEquals(originalFileName, uniqueFileName);
	}

	private Predicate<String> _existsUntil(int n) {
		AtomicInteger atomicInteger = new AtomicInteger(0);

		return fileName -> atomicInteger.addAndGet(1) <= n;
	}

	private final DefaultUniqueFileNameProvider _defaultUniqueFileNameProvider =
		new DefaultUniqueFileNameProvider();

}