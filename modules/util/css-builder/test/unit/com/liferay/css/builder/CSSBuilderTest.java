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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.net.URL;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Arrays;

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
		URL url = CSSBuilderTest.class.getResource("dependencies");

		Path path = Paths.get(url.toURI());

		_docrootDirName = path.toString();
	}

	@After
	public void tearDown() throws Exception {
		Files.walkFileTree(
			Paths.get(_docrootDirName + "/css/.sass-cache"),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Files.delete(path);

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(
						Path path, IOException ioe)
					throws IOException {

					Files.delete(path);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	@Test
	public void testJniSassToCssBuilder() throws Exception {
		_testSassToCssBuilder("jni");
	}

	@Test
	public void testRubySassToCssBuilder() throws Exception {
		_testSassToCssBuilder("ruby");
	}

	private String _read(String fileName) throws Exception {
		Path path = Paths.get(fileName);

		String s = new String(Files.readAllBytes(path), StringPool.UTF8);

		return StringUtil.replace(
			s, StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);
	}

	private void _testSassToCssBuilder(String compiler) throws Exception {
		CSSBuilder cssBuilder = new CSSBuilder(
			_docrootDirName, "../../../portal-web/docroot/html/css/common",
			new String[0], compiler);

		cssBuilder.execute(Arrays.asList(new String[] {"/css"}));

		String expectedCacheContent = _read(
			_docrootDirName + "/expected/test.css");
		String actualCacheContent = _read(
			_docrootDirName + "/css/.sass-cache/test.css");

		Assert.assertEquals(expectedCacheContent, actualCacheContent);

		String expectedRtlCacheContent = _read(
			_docrootDirName + "/expected/test_rtl.css");
		String actualRtlCacheContent = _read(
			_docrootDirName + "/css/.sass-cache/test_rtl.css");

		Assert.assertEquals(expectedRtlCacheContent, actualRtlCacheContent);
	}

	private static String _docrootDirName;

}