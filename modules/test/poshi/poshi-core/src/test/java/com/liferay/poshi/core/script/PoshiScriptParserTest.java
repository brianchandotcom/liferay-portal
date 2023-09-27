/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.script;

import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.util.FileUtil;
import com.liferay.poshi.core.util.PropsUtil;
import com.liferay.poshi.core.util.TestUtil;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kenji Heigel
 */
public class PoshiScriptParserTest {

	@After
	public void tearDown() {
		PoshiContext.clear();
		PoshiScriptParserException.clear();
		PropsUtil.clear();
	}

	@Test
	public void testInvalidSyntax() {
		String fileName = "InvalidSyntax.macro";

		_preparePoshiContext(fileName);

		List<PoshiScriptParserException> actualExceptions = new ArrayList<>(
			PoshiScriptParserException.getExceptions());

		PoshiScriptParserException.clear();

		_throwPoshiScriptParserException(
			"Invalid variable assignment syntax, please use triple quotes " +
				"(''') to wrap a multiline string instead of double quotes",
			4, _getFilePath(fileName));

		_throwPoshiScriptParserException(
			"Unescaped quotes in list value: \"item 1, item2\"", 7,
			_getFilePath(fileName));

		_assertExceptions(
			PoshiScriptParserException.getExceptions(), actualExceptions);
	}

	@Test
	public void testMissingSemicolonMacro() {
		String fileName = "MissingSemicolon.macro";

		_preparePoshiContext(fileName);

		List<PoshiScriptParserException> actualExceptions = new ArrayList<>(
			PoshiScriptParserException.getExceptions());

		PoshiScriptParserException.clear();

		_throwPoshiScriptParserException(
			"Missing semicolon", 6, _getFilePath(fileName));
		_throwPoshiScriptParserException(
			"Missing semicolon", 14, _getFilePath(fileName));
		_throwPoshiScriptParserException(
			"Missing semicolon", 22, _getFilePath(fileName));
		_throwPoshiScriptParserException(
			"Missing semicolon", 30, _getFilePath(fileName));
		_throwPoshiScriptParserException(
			"Missing semicolon", 38, _getFilePath(fileName));
		_throwPoshiScriptParserException(
			"Missing semicolon", 46, _getFilePath(fileName));
		_throwPoshiScriptParserException(
			"Missing semicolon", 55, _getFilePath(fileName));
		_throwPoshiScriptParserException(
			"Missing semicolon", 63, _getFilePath(fileName));
		_throwPoshiScriptParserException(
			"Missing semicolon", 71, _getFilePath(fileName));
		_throwPoshiScriptParserException(
			"Missing semicolon", 79, _getFilePath(fileName));
		_throwPoshiScriptParserException(
			"Missing semicolon", 89, _getFilePath(fileName));
		_throwPoshiScriptParserException(
			"Missing semicolon", 97, _getFilePath(fileName));
		_throwPoshiScriptParserException(
			"Missing semicolon", 105, _getFilePath(fileName));
		_throwPoshiScriptParserException(
			"Missing semicolon", 109, _getFilePath(fileName));
		_throwPoshiScriptParserException(
			"Missing semicolon", 115, _getFilePath(fileName));

		_assertExceptions(
			PoshiScriptParserException.getExceptions(), actualExceptions);
	}

	@Test
	public void testMissingSemicolonTestcase() {
		String fileName = "MissingSemicolon.testcase";

		_preparePoshiContext(fileName);

		List<PoshiScriptParserException> actualExceptions = new ArrayList<>(
			PoshiScriptParserException.getExceptions());

		PoshiScriptParserException.clear();

		_throwPoshiScriptParserException(
			"Missing semicolon", 2, _getFilePath(fileName));
		_throwPoshiScriptParserException(
			"Missing semicolon", 9, _getFilePath(fileName));

		_assertExceptions(
			PoshiScriptParserException.getExceptions(), actualExceptions);
	}

	private void _assertContains(String s, String text) {
		if (!s.contains(text)) {
			TestUtil.printDiffs(null, s, null, text);
		}

		Assert.assertTrue(s.contains(text));
	}

	private void _assertExceptions(
		List<PoshiScriptParserException> expectedExceptions,
		List<PoshiScriptParserException> actualExceptions) {

		if (expectedExceptions == actualExceptions) {
			throw new RuntimeException(
				"Exception lists should not reference the same object");
		}

		for (int i = 0; i < expectedExceptions.size(); i++) {
			PoshiScriptParserException expectedPoshiScriptParserException =
				expectedExceptions.get(i);

			PoshiScriptParserException actualPoshiScriptParserException =
				actualExceptions.get(i);

			_assertContains(
				actualPoshiScriptParserException.getMessage(),
				expectedPoshiScriptParserException.getMessage());
			Assert.assertEquals(
				expectedPoshiScriptParserException.getErrorLineNumber(),
				actualPoshiScriptParserException.getErrorLineNumber());
			Assert.assertEquals(
				expectedPoshiScriptParserException.getFilePath(),
				actualPoshiScriptParserException.getFilePath());
		}
	}

	private URL _getFilePath(String fileName) {
		String filePath = FileUtil.getCanonicalPath(
			_BASE_POSHI_FILE_DIR + "/" + fileName);

		try {
			return new URL("file://" + filePath);
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private void _preparePoshiContext(String dirName) {
		String poshiFileDir = _BASE_POSHI_FILE_DIR + "/" + dirName;

		PropsUtil.set("test.base.dir.name", poshiFileDir);

		try {
			PoshiContext.readFiles();
		}
		catch (Exception exception) {
			String message = exception.getMessage();

			if (!message.contains("Found Poshi script syntax errors")) {
				throw new RuntimeException(exception);
			}
		}
	}

	private void _throwPoshiScriptParserException(
		String message, int lineNumber, URL filePath) {

		try {
			throw new PoshiScriptParserException(
				message, lineNumber, null, filePath);
		}
		catch (PoshiScriptParserException poshiScriptParserException) {
		}
	}

	private static final String _BASE_POSHI_FILE_DIR =
		"src/test/resources/com/liferay/poshi/core/dependencies/script";

}