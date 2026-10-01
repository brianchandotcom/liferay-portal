/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.processor;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.java.parser.JavaParser;
import com.liferay.source.formatter.SourceFormatterArgs;

import java.io.File;
import java.io.FileNotFoundException;

import java.net.URL;

import org.apache.commons.io.IOUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Hugo Huijser
 */
public class JavaParserTest {

	@Test
	public void testJavaAnnotation() throws Exception {
		_test("JavaAnnotation");
	}

	@Test
	public void testJavaArray() throws Exception {
		_test("JavaArray");
	}

	@Test
	public void testJavaLogVariableDefinition() throws Exception {
		test("JavaLogVariableDefinition.testjava");
	}

	@Test
	public void testJavaModifierStrictfp() throws Exception {
		_test("JavaModifierStrictfp");
	}

	@Test
	public void testJavaNestedSwitchExpressions() throws Exception {
		_test("JavaNestedSwitchExpressions");
	}

	@Test
	public void testJavaPatternMatchingForInstanceof() throws Exception {
		_test("JavaPatternMatchingForInstanceof");
	}

	@Test
	public void testJavaPatternMatchingForSwitch() throws Exception {
		_test("JavaPatternMatchingForSwitch");
	}

	@Test
	public void testJavaRecordPatterns() throws Exception {
		_test("JavaRecordPatterns");
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		URL url = classLoader.getResource(fileName);

		if (url == null) {
			throw new FileNotFoundException(fileName);
		}

		return StringUtil.replace(
			IOUtils.toString(url, StringPool.UTF8), StringPool.RETURN_NEW_LINE,
			StringPool.NEW_LINE);
	}

	private void _test(String className) throws Exception {
		String content = _read(
			StringBundler.concat(_DIR_NAME, "/", className, ".testjava"));

		Assert.assertEquals(
			_read(
				StringBundler.concat(
					_DIR_NAME, "/expected/", className, ".testjava")),
			JavaParser.parse(
				new File(_DIR_NAME, className + ".java"), content,
				SourceFormatterArgs.MAX_LINE_LENGTH, false));
	}

	private static final String _DIR_NAME =
		"com/liferay/source/formatter/dependencies";

}