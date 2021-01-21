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

package com.liferay.poshi.core.prose;

import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.PoshiGetterUtil;
import com.liferay.poshi.core.util.Dom4JUtil;
import com.liferay.poshi.core.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.net.URI;

import java.nio.file.Files;
import java.nio.file.Paths;

import junit.framework.TestCase;

import org.apache.commons.lang3.ArrayUtils;

import org.dom4j.Element;
import org.dom4j.util.NodeComparator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Yi-Chen Tsai
 */
public class PoshiProseDefinitionTest extends TestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		_testBaseDir = new File(_TEST_BASE_DIR_NAME);

		if (!_testBaseDir.exists()) {
			throw new RuntimeException(
				"Test directory does not exist: " + _TEST_BASE_DIR_NAME);
		}

		String[] poshiFileNames = ArrayUtils.addAll(
			PoshiContext.POSHI_SUPPORT_FILE_INCLUDES,
			PoshiContext.POSHI_TEST_FILE_INCLUDES);

		PoshiContext.readFiles(poshiFileNames, _TEST_BASE_DIR_NAME);

		_poshiProseDefinition = new PoshiProseDefinition(
			FileUtil.getURL(new File(_testBaseDir, _POSHI_PROSE_FILE_NAME)));
	}

	@After
	@Override
	public void tearDown() throws Exception {
		PoshiContext.clear();
	}

	@Test
	public void testProseToXMLTranslation() throws Exception {
		Element actual = _poshiProseDefinition.toElement();

		File file = new File(_TEST_BASE_DIR_NAME + _POSHI_TESTCASE_FILE_NAME);

		URI uri = file.toURI();

		Element expected = PoshiGetterUtil.getRootElementFromURL(
			uri.toURL(), false);

		Dom4JUtil.removeWhiteSpaceTextNodes(expected);

		_assertEqualElements(
			actual, expected,
			"Translated actual XML Element does not match expected Element");
	}

	protected String read(File file) throws IOException {
		return new String(Files.readAllBytes(Paths.get(file.toURI())));
	}

	protected String read(File dir, String fileName) throws IOException {
		return read(new File(dir, fileName));
	}

	private void _assertEqualElements(
			Element actualElement, Element expectedElement, String errorMessage)
		throws Exception {

		NodeComparator nodeComparator = new NodeComparator();

		int compare = nodeComparator.compare(actualElement, expectedElement);

		if (compare != 0) {
			String actual = Dom4JUtil.format(actualElement);
			String expected = Dom4JUtil.format(expectedElement);

			throw new Exception(
				_getErrorMessage(actual, expected, errorMessage));
		}
	}

	private String _getErrorMessage(
			String actual, String expected, String errorMessage)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(errorMessage);
		sb.append("\n\nExpected:\n");
		sb.append(expected);
		sb.append("\n\nActual:\n");
		sb.append(actual);

		return sb.toString();
	}

	private static final String _POSHI_PROSE_FILE_NAME =
		"PoshiProseSyntax.prose";

	private static final String _POSHI_TESTCASE_FILE_NAME =
		"PoshiXMLSyntax.testcase";

	private static final String _TEST_BASE_DIR_NAME =
		"src/test/resources/com/liferay/poshi/core/dependencies/prose/";

	private PoshiProseDefinition _poshiProseDefinition;
	private File _testBaseDir;

}