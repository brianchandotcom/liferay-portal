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

package com.liferay.css.builder;

import java.io.File;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;

import org.apache.commons.io.FileUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Eduardo Garcia
 * @author David Truong
 */
public class CSSBuilderTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		Class clazz = CSSBuilderTest.class;

		URL url = clazz.getResource("dependencies");

		_DOCROOT_DIR_NAME = url.getFile();
	}

	@After
	public void tearDown() throws Exception {
		FileUtils.deleteDirectory(
			new File(_DOCROOT_DIR_NAME + _DIR_NAME + _SASS_CACHE_DIR_NAME));
	}

	@Test
	public void testSassToCssBuilder() throws Exception {
		CSSBuilder cssBuilder = new CSSBuilder(
			_DOCROOT_DIR_NAME, "../../../portal-web/docroot/html/css/common",
			"jni");

		cssBuilder.execute(Arrays.asList(new String[] {_DIR_NAME}));

		String expectedCacheContent = _read(
			_DOCROOT_DIR_NAME + _EXPECTED_DIR_NAME + _FILE_NAME);
		String actualCacheContent = _read(
			_DOCROOT_DIR_NAME + _DIR_NAME + _SASS_CACHE_DIR_NAME + _FILE_NAME);

		Assert.assertEquals(expectedCacheContent, actualCacheContent);

		String expectedRtlCacheContent = _read(
			_DOCROOT_DIR_NAME + _EXPECTED_DIR_NAME + _RTL_FILE_NAME);
		String actualRtlCacheContent = _read(
			_DOCROOT_DIR_NAME + _DIR_NAME + _SASS_CACHE_DIR_NAME +
				_RTL_FILE_NAME);

		Assert.assertEquals(expectedRtlCacheContent, actualRtlCacheContent);
	}

	private String _read(String fileName) throws Exception {
		Path path = Paths.get(fileName);

		return new String(Files.readAllBytes(path));
	}

	private static final String _DIR_NAME = "/css";

	private static String _DOCROOT_DIR_NAME;

	private static final String _EXPECTED_DIR_NAME = "/expected";

	private static final String _FILE_NAME = "/test.css";

	private static final String _RTL_FILE_NAME = "/test_rtl.css";

	private static final String _SASS_CACHE_DIR_NAME = "/.sass-cache";

}