/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeFormatter;
import com.liferay.portal.tools.servicebuilder.ServiceBuilder;
import com.liferay.portal.util.InitUtil;

import jargs.gnu.CmdLineParser;

import java.io.File;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class SeleneseToJavaBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new SeleneseToJavaBuilder(args);
	}

	public SeleneseToJavaBuilder(String[] args) throws Exception {
		CmdLineParser cmdLineParser = new CmdLineParser();

		CmdLineParser.Option basedirOption = cmdLineParser.addStringOption(
			"basedir");
		CmdLineParser.Option minimizeOption = cmdLineParser.addStringOption(
			"minimize");
		CmdLineParser.Option reportDuplicatesOption =
			cmdLineParser.addStringOption("reportDuplicates");

		cmdLineParser.parse(args);

		_basedir = (String)cmdLineParser.getOptionValue(basedirOption);

		String minimizeTestFileName = (String)cmdLineParser.getOptionValue(
			minimizeOption);

		minimizeTestFileName = normalizeFileName(minimizeTestFileName);

		String minimizeTestContent = getNormalizedContent(minimizeTestFileName);

		_reportDuplicates = GetterUtil.getBoolean(
			(String)cmdLineParser.getOptionValue(reportDuplicatesOption));

		int testHtmlCount = 0;

		Map<String, ObjectValuePair<String, IntegerWrapper>> testHtmlMap =
			new HashMap<String, ObjectValuePair<String, IntegerWrapper>>();

		Set<String> fileNames = getFileNames();

		for (String fileName : fileNames) {
			if (fileName.length() > 161) {
				System.out.println(
					"Exceeds 177 characters: portal-web/test/" + fileName);
			}

			if (fileName.endsWith("Test.html")) {
				testHtmlCount++;

				String content = getNormalizedContent(fileName);

				if ((content != null) && content.equals(minimizeTestContent)) {
					minimizeTestCase(fileName, minimizeTestFileName);
				}

				ObjectValuePair<String, IntegerWrapper> testHtmlOVP =
					testHtmlMap.get(content);

				if (testHtmlOVP == null) {
					testHtmlOVP = new ObjectValuePair<String, IntegerWrapper>(
						fileName, new IntegerWrapper());

					testHtmlMap.put(content, testHtmlOVP);
				}
				else {
					IntegerWrapper integerWrapper = testHtmlOVP.getValue();

					integerWrapper.increment();
				}

				translateTestCase(fileName);
			}
			else if (fileName.endsWith("Tests.html")) {
				translateTestSuite(fileName);
			}
			else if (fileName.endsWith("Test.java") ||
					 fileName.endsWith("Tests.java")) {

				if (!fileNames.contains(
						fileName.substring(0, fileName.length() - 5) +
							".html")) {

					System.out.println("Unused: " + fileName);
				}
			}
		}

		List<ObjectValuePair<String, IntegerWrapper>> testHtmlOVPs =
			new ArrayList<ObjectValuePair<String, IntegerWrapper>>();

		int duplicateTestHtmlCount = 0;

		for (Map.Entry<String, ObjectValuePair<String, IntegerWrapper>> entry :
				testHtmlMap.entrySet()) {

			ObjectValuePair<String, IntegerWrapper> testHtmlOVP =
				entry.getValue();

			testHtmlOVPs.add(testHtmlOVP);

			IntegerWrapper integerWrapper = testHtmlOVP.getValue();

			duplicateTestHtmlCount += integerWrapper.getValue();
		}

		Collections.sort(testHtmlOVPs, new TestHtmlCountComparator());

		StringBundler sb = new StringBundler();

		for (ObjectValuePair<String, IntegerWrapper> testHtmlOVP :
				testHtmlOVPs) {

			String fileName = testHtmlOVP.getKey();
			IntegerWrapper integerWrapper = testHtmlOVP.getValue();

			if (integerWrapper.getValue() > 0) {
				sb.append(fileName);
				sb.append(",");
				sb.append(integerWrapper.getValue());
				sb.append("\n");
			}
		}

		if (_reportDuplicates && (sb.index() > 0)) {
			System.out.println(
				"There are " + duplicateTestHtmlCount +
					" duplicate tests out of " + testHtmlCount +
						". See duplicate_selenium_tests.txt.");

			FileUtil.write("duplicate_selenium_tests.txt", sb.toString());
		}
	}

	protected String fixParam(String param) {
		StringBuilder sb = new StringBuilder();

		char[] array = param.toCharArray();

		for (int i = 0; i < array.length; ++i) {
			char c = array[i];

			if (c == CharPool.BACK_SLASH) {
				sb.append("\\\\");
			}
			else if (c == CharPool.QUOTE) {
				sb.append("\\\"");
			}
			else if (Character.isWhitespace(c)) {
				sb.append(c);
			}
			else if ((c < 0x0020) || (c > 0x007e)) {
				sb.append("\\u");
				sb.append(UnicodeFormatter.charToHex(c));
			}
			else {
				sb.append(c);
			}
		}

		return StringUtil.replace(
			sb.toString(), _FIX_PARAM_OLD_SUBS, _FIX_PARAM_NEW_SUBS);
	}

	protected String formatTestSuite(String fileName, String oldContent)
		throws Exception {

		if (!oldContent.contains("..")) {
			return oldContent;
		}

		String newContent = oldContent;

		int x = 0;
		int y = 0;

		while (oldContent.indexOf("<a href=\"", x) != -1) {
			x = oldContent.indexOf("<a href=\"", x) + 9;
			y = oldContent.indexOf("\">", x);

			String testCaseName = oldContent.substring(x, y);

			if (!testCaseName.contains("..")) {
				continue;
			}

			int z = fileName.lastIndexOf(StringPool.SLASH);

			String importClassName = fileName.substring(0, z);

			if (!FileUtil.exists(
					_basedir + "/" + importClassName + "/" + testCaseName)) {

				throw new IllegalArgumentException(
					fileName + " has improper relative path");
			}

			if (testCaseName.contains("../portalweb/")) {
				continue;
			}

			int count = StringUtil.count(testCaseName, "..");

			for (int i = 0; i < count; i++) {
				z = importClassName.lastIndexOf(StringPool.SLASH);

				importClassName = fileName.substring(0, z);
			}

			z = testCaseName.lastIndexOf("../", z);

			importClassName += testCaseName.substring(z + 2);

			count = StringUtil.count(fileName, "/") - 2;

			String relativePath = "" ;

			for (int i = 0; i < count; i++) {
				relativePath += "../";
			}

			importClassName = StringUtil.replace(
				importClassName, "com/liferay/", relativePath);

			newContent = StringUtil.replace(
				newContent, testCaseName, importClassName);
		}

		if (!oldContent.equals(newContent)) {
			writeFile(fileName, newContent, false);
		}

		return newContent;
	}

	protected Set<String> getFileNames() throws Exception {
		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(_basedir);
		directoryScanner.setExcludes(
			new String[] {
				"**\\EvaluateLogTest.java", "**\\EvaluateUserCSVFileTest.java",
				"**\\IterateThemeTest.java", "**\\StopSeleniumTest.java",
				"**\\WaitForSystemShutdownTest.java"
			});
		directoryScanner.setIncludes(
			new String[] {
				"**\\*Test.html", "**\\*Test.java", "**\\*Tests.html",
				"**\\*Tests.java", "**\\*TestSuite.java"
			});

		directoryScanner.scan();

		Set<String> fileNames = new TreeSet<String>(
			new StringComparator() {

				@Override
				public int compare(String s1, String s2) {
					if (s1.endsWith("Test.html") && s2.contains("Tests.html")) {
						return -1;
					}

					if (s1.endsWith("Tests.html") && s2.contains("Test.html")) {
						return 1;
					}

					if (s1.endsWith(".html") && s2.contains(".java")) {
						return -1;
					}

					if (s1.endsWith(".java") && s2.contains(".html")) {
						return 1;
					}

					return super.compare(s1, s2);
				}

			});

		for (String fileName : directoryScanner.getIncludedFiles()) {
			fileName = normalizeFileName(fileName);

			fileNames.add(fileName);
		}

		if (false) {
			StringBundler sb = new StringBundler();

			for (String fileName : fileNames) {
				sb.append(fileName);
				sb.append("\n");
			}

			writeFile("selenium_included_files.txt", sb.toString(), false);
		}

		return fileNames;
	}

	protected String getNormalizedContent(String fileName) throws Exception {
		String content = readFile(fileName);

		if (content != null) {
			content = content.trim();
			content = StringUtil.replace(content, "\n", "");
			content = StringUtil.replace(content, "\r\n", "");
		}

		return content;
	}

	protected String[] getParams(String step) throws Exception {
		String[] params = new String[3];

		int x = 0;
		int y = 0;

		for (int i = 0; i < 3; i++) {
			x = step.indexOf("<td>", x) + 4;
			y = step.indexOf("\n", x);
			y = step.lastIndexOf("</td>", y);

			params[i] = step.substring(x, y);
		}

		return params;
	}

	protected void minimizeTestCase(
			String fileName, String minimizeTestFileName)
		throws Exception {

		int x = fileName.lastIndexOf(StringPool.SLASH);

		String dirName = fileName.substring(0, x);

		x = minimizeTestFileName.lastIndexOf(StringPool.SLASH);

		String minimizeTestDirName = minimizeTestFileName.substring(0, x);

		if (dirName.equals(minimizeTestDirName)) {
			return;
		}

		String minimizeTestName = minimizeTestFileName.substring(x + 1);

		x = fileName.indexOf("portalweb");

		int count = StringUtil.count(fileName.substring(x), StringPool.SLASH);

		String relativeMinimizeTestFileName = "";

		while (count > 0) {
			relativeMinimizeTestFileName += "../";

			count--;
		}

		relativeMinimizeTestFileName += minimizeTestFileName.substring(
			minimizeTestFileName.lastIndexOf("/", x) + 1);

		File minimizeTestFile = new File(
			_basedir + "/" + dirName + "/" + relativeMinimizeTestFileName);

		if (!minimizeTestFile.exists()) {
			throw new IllegalArgumentException(
				minimizeTestFile.toString() + " does not exist");
		}

		String[] subfileNames = FileUtil.listFiles(_basedir + "/" + dirName);

		for (String subfileName : subfileNames) {
			if (!subfileName.endsWith("Tests.html")) {
				continue;
			}

			File subfile = new File(
				_basedir + "/" + dirName + "/" + subfileName);

			String content = FileUtil.read(subfile);

			content = StringUtil.replace(
				content, "\"" + minimizeTestName + "\"",
				"\"" + relativeMinimizeTestFileName + "\"");

			FileUtil.write(subfile, content);
		}

		FileUtil.delete(_basedir + "/" + fileName);
		FileUtil.delete(
			_basedir + "/" + fileName.substring(0, fileName.length() - 5) +
				".java");
	}

	protected String normalizeFileName(String fileName) {
		return StringUtil.replace(
			fileName, StringPool.BACK_SLASH, StringPool.SLASH);
	}

	protected String readFile(String fileName) throws Exception {
		return FileUtil.read(_basedir + "/" + fileName);
	}

	protected void translateTestCase(String fileName) throws Exception {
		if (!FileUtil.exists(_basedir + "/" + fileName)) {
			return;
		}

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String testPackagePath = StringUtil.replace(
			fileName.substring(0, x), StringPool.SLASH, StringPool.PERIOD);
		String testName = fileName.substring(x + 1, y);
		String testMethodName =
			"test" + testName.substring(0, testName.length() - 4);
		String testFileName = fileName.substring(0, y) + ".java";

		StringBundler sb = new StringBundler();

		sb.append("package ");
		sb.append(testPackagePath);
		sb.append(";\n");

		sb.append("import com.liferay.portal.kernel.util.FileUtil;\n");
		sb.append("import com.liferay.portal.kernel.util.StringPool;\n");
		sb.append("import com.liferay.portalweb.portal.BaseTestCase;\n");
		sb.append(
			"import com.liferay.portalweb.portal.util.BrowserCommands;\n");
		sb.append(
			"import com.liferay.portalweb.portal.util.RuntimeVariables;\n");

		sb.append("public class ");
		sb.append(testName);
		sb.append(" extends BaseTestCase {");

		sb.append("public void ");
		sb.append(testMethodName);
		sb.append("() throws Exception {");

		String content = readFile(fileName);

		if (!content.contains("<title>" + testName + "</title>") ||
			!content.contains("colspan=\"3\">" + testName + "</td>")) {

			System.out.println(testName + " has an invalid test name");
		}

		if (content.contains("&amp;")) {
			content = StringUtil.replace(content, "&amp;", "&");

			writeFile(fileName, content, false);
		}

		if (content.contains("&gt;")) {
			content = StringUtil.replace(content, "&gt;", ">");

			writeFile(fileName, content, false);
		}

		if (content.contains("&lt;")) {
			content = StringUtil.replace(content, "&lt;", "<");

			writeFile(fileName, content, false);
		}

		if (content.contains("&quot;")) {
			content = StringUtil.replace(content, "&quot;", "\"");

			writeFile(fileName, content, false);
		}

		x = content.indexOf("<tbody>");
		y = content.indexOf("</tbody>");

		content = content.substring(x, y + 8);

		Map<String, String> labels = new HashMap<String, String>();

		int labelCount = 1;

		Map<Integer, Boolean> takeScreenShots = new HashMap<Integer, Boolean>();

		x = 0;
		y = 0;

		while (true) {
			x = content.indexOf("<tr>", y);
			y = content.indexOf("\n</tr>", x);

			if ((x == -1) || (y == -1)) {
				break;
			}

			x += 6;
			y++;

			String step = content.substring(x, y);

			String[] params = getParams(step);

			String param1 = params[0];
			String param2 = fixParam(params[1]);

			if (param1.equals("assertConfirmation")) {
				int previousX = x - 6;

				previousX = content.lastIndexOf("<tr>", previousX - 1);
				previousX += 6;

				takeScreenShots.put(previousX, Boolean.FALSE);
			}
			else if (param1.equals("label")) {
				String label = labels.get(param2);

				if (label == null) {
					labelCount++;

					label = labels.put(param2, String.valueOf(labelCount));
				}
			}
		}

		if (labels.size() > 0) {
			sb.append("int label = 1;");

			sb.append("while (label >= 1) {");
			sb.append("switch (label) {");
			sb.append("case 1:");
		}

		x = 0;
		y = 0;

		sb.append("selenium.selectWindow(\"null\");");
		sb.append("selenium.selectFrame(\"relative=top\");");

		while (true) {
			x = content.indexOf("<tr>", y);
			y = content.indexOf("\n</tr>", x);

			if ((x == -1) || (y == -1)) {
				break;
			}

			x += 6;
			y++;

			String step = content.substring(x, y);

			String[] params = getParams(step);

			String param1 = params[0];
			String param2 = fixParam(params[1]);
			String param3 = fixParam(params[2]);

			if (param1.equals("addSelection") || param1.equals("clickAt") ||
				param1.equals("doubleClickAt") || param1.equals("keyDown") ||
				param1.equals("keyPress") || param1.equals("keyUp") ||
				param1.equals("mouseMoveAt") || param1.equals("openWindow") ||
				param1.equals("select") || param1.equals("sendKeys") ||
				param1.equals("type") || param1.equals("typeFrame") ||
				param1.equals("typeKeys") ||
				param1.equals("uploadCommonFile") ||
				param1.equals("uploadFile") ||
				param1.equals("uploadTempFile") ||
				param1.equals("waitForPopUp")) {

				sb.append("selenium.");
				sb.append(param1);
				sb.append(StringPool.OPEN_PARENTHESIS);

				if (param2.startsWith("${")) {
					sb.append("RuntimeVariables.getValue(\"");

					String text = param2.substring(2, param2.length() - 1);

					sb.append(text);
					sb.append("\")");
				}
				else {
					sb.append("\"");
					sb.append(param2);
					sb.append("\"");
				}

				sb.append(", RuntimeVariables.replace(");

				if (param3.startsWith("${")) {
					sb.append("RuntimeVariables.getValue(\"");

					String text = param3.substring(2, param3.length() - 1);

					sb.append(text);
					sb.append("\")");
				}
				else if (param3.startsWith("value=${")) {
					sb.append("\"value=\" + RuntimeVariables.getValue(\"");

					String text = param3.substring(8, param3.length() - 1);

					sb.append(text);
					sb.append("\")");
				}
				else {
					sb.append("\"");
					sb.append(param3);
					sb.append("\"");
				}

				sb.append("));");
			}
			else if (param1.equals("assertAlert") ||
					 param1.equals("assertNotAlert")) {

				if (param1.equals("assertAlert")) {
					sb.append("assertEquals");
				}
				else if (param1.equals("assertNotAlert")) {
					sb.append("assertNotEquals");
				}

				sb.append("(\"");
				sb.append(param2);
				sb.append("\", selenium.getAlert());");
			}
			else if (param1.equals("assertChecked") ||
					 param1.equals("assertNotChecked")) {

				if (param1.equals("assertChecked")) {
					sb.append("assertTrue");
				}
				else if (param1.equals("assertNotChecked")) {
					sb.append("assertFalse");
				}

				sb.append("(selenium.isChecked(\"");
				sb.append(param2);
				sb.append("\"));");
			}
			else if (param1.equals("assertConfirmation")) {
				param2 = StringUtil.replace(param2, "?", "[\\\\s\\\\S]");

				sb.append("assertTrue(selenium.getConfirmation().matches(\"^");
				sb.append(param2);
				sb.append("$\"));");
			}
			else if (param1.equals("assertLocation") ||
					 param1.equals("assertNotLocation")) {

				if (param1.equals("assertLocation")) {
					sb.append("assertEquals");
				}
				else if (param1.equals("assertNotLocation")) {
					sb.append("assertNotEquals");
				}

				sb.append("(RuntimeVariables.replace(\"");
				sb.append(param2);
				sb.append("\"), selenium.getLocation());");
			}
			else if (param1.equals("assertElementNotPresent") ||
					 param1.equals("assertElementPresent")) {

				sb.append("assertTrue(selenium.");

				if (param1.equals("assertElementNotPresent")) {
					sb.append("isElementNotPresent");
				}
				else if (param1.equals("assertElementPresent")) {
					sb.append("isElementPresent");
				}

				sb.append("(\"");
				sb.append(param2);
				sb.append("\"));");
			}
			else if (param1.equals("assertNotPartialText") ||
					 param1.equals("assertPartialText")) {

				if (param1.equals("assertNotPartialText")) {
					sb.append("assertFalse");
				}
				else if (param1.equals("assertPartialText")) {
					sb.append("assertTrue");
				}

				sb.append("(selenium.isPartialText(\"");
				sb.append(param2);
				sb.append("\", ");

				if (param3.startsWith("${")) {
					sb.append("RuntimeVariables.getValue(\"");

					String text = param3.substring(2, param3.length() - 1);

					sb.append(text);
					sb.append("\")");
				}
				else {
					sb.append("\"");
					sb.append(param3);
					sb.append("\"");
				}

				sb.append("));");
			}
			else if (param1.equals("assertNotSelectedLabel") ||
					 param1.equals("assertSelectedLabel")) {

				if (param1.equals("assertNotSelectedLabel")) {
					sb.append("assertNotEquals");
				}
				else if (param1.equals("assertSelectedLabel")) {
					sb.append("assertEquals");
				}

				sb.append(StringPool.OPEN_PARENTHESIS);

				if (param3.startsWith("${")) {
					sb.append("RuntimeVariables.getValue(\"");

					String text = param3.substring(2, param3.length() - 1);

					sb.append(text);
					sb.append("\")");
				}
				else {
					sb.append("\"");
					sb.append(param3);
					sb.append("\"");
				}

				sb.append(", selenium.getSelectedLabel(\"");
				sb.append(param2);
				sb.append("\"));");
			}
			else if (param1.equals("assertNotSelectedLabels") ||
					 param1.equals("assertSelectedLabels")) {

				if (param1.equals("assertNotSelectedLabels")) {
					sb.append("assertNotEquals");
				}
				else if (param1.equals("assertSelectedLabels")) {
					sb.append("assertEquals");
				}

				sb.append("(\"");
				sb.append(param3);
				sb.append("\", join(selenium.getSelectedLabels(\"");
				sb.append(param2);
				sb.append("\"), \',\'));");
			}
			else if (param1.equals("assertNotText") ||
					 param1.equals("assertText")) {

				if (param1.equals("assertNotText")) {
					sb.append("assertNotEquals");
				}
				else if (param1.equals("assertText")) {
					sb.append("assertEquals");
				}

				sb.append("(RuntimeVariables.replace(\"");
				sb.append(param3);
				sb.append("\"), selenium.getText(\"");
				sb.append(param2);
				sb.append("\"));");
			}
			else if (param1.equals("assertNotValue") ||
					 param1.equals("assertValue")) {

				if (param1.equals("assertNotValue")) {
					sb.append("assertNotEquals");
				}
				else if (param1.equals("assertValue")) {
					sb.append("assertEquals");
				}

				sb.append("(\"");
				sb.append(param3);
				sb.append("\", selenium.getValue(\"");
				sb.append(param2);
				sb.append("\"));");
			}
			else if (param1.equals("assertNotVisible") ||
					 param1.equals("assertVisible")) {

				if (param1.equals("assertNotVisible")) {
					sb.append("assertFalse");
				}
				else if (param1.equals("assertVisible")) {
					sb.append("assertTrue");
				}

				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append("selenium.isVisible(\"");
				sb.append(param2);
				sb.append("\"));");
			}
			else if (param1.equals("assertSelectOptions")) {
				String[] expectedArray = StringUtil.split(param3);

				sb.append("String[] actualArray = ");
				sb.append("selenium.getSelectOptions(\"");
				sb.append(param2);
				sb.append("\");");

				sb.append("assertEquals(");
				sb.append(expectedArray.length);
				sb.append(", actualArray.length);");

				for (int i = 0; i < expectedArray.length; i++) {
					sb.append("assertEquals(\"");
					sb.append(expectedArray[i]);
					sb.append("\", actualArray[");
					sb.append(i);
					sb.append("]);");
				}
			}
			else if (param1.equals("assertTextNotPresent") ||
					 param1.equals("assertTextPresent")) {

				if (param1.equals("assertTextNotPresent")) {
					sb.append("assertFalse");
				}
				else if (param1.equals("assertTextPresent")) {
					sb.append("assertTrue");
				}

				sb.append("(selenium.isTextPresent(\"");
				sb.append(param2);
				sb.append("\"));");
			}
			else if (param1.equals("assertXMLTagNotPresent") ||
					 param1.equals("assertXMLTagPresent")) {

				if (param1.equals("assertXMLTagNotPresent")) {
					sb.append("assertFalse");
				}
				else if (param1.equals("assertXMLTagPresent")) {
					sb.append("assertTrue");
				}

				sb.append("(selenium.isXMLTagPresent(\"");
				sb.append(param2);
				sb.append("\", ");

				if (param3.startsWith("${")) {
					sb.append("RuntimeVariables.getValue(\"");

					String text = param3.substring(2, param3.length() - 1);

					sb.append(text);
					sb.append("\")");
				}
				else {
					sb.append("\"");
					sb.append(param3);
					sb.append("\"");
				}

				sb.append("));");
			}
			else if (param1.equals("captureEntirePageScreenshot")) {
				int pos = param2.lastIndexOf("\\");

				String dirName = param2.substring(0, pos + 1);

				sb.append("FileUtil.mkdirs(RuntimeVariables.replace(\"");
				sb.append(dirName);
				sb.append("\"));");
				sb.append("selenium.captureEntirePageScreenshot(");
				sb.append("RuntimeVariables.replace(\"");
				sb.append(param2);
				sb.append("\"), \"\");");
			}
			else if (param1.equals("check") || param1.equals("click") ||
					 param1.equals("doubleClick") ||
					 param1.equals("downloadTempFile") ||
					 param1.equals("makeVisible") ||
					 param1.equals("mouseDown") || param1.equals("mouseMove") ||
					 param1.equals("mouseOver") || param1.equals("mouseUp") ||
					 param1.equals("open") || param1.equals("runScript") ||
					 param1.equals("selectFrame") ||
					 param1.equals("selectPopUp") ||
					 param1.equals("selectWindow") ||
					 param1.equals("setTimeout") ||
					 param1.equals("setTimeoutImplicit") ||
					 param1.equals("uncheck") ||
					 param1.equals("waitForConfirmation") ||
					 param1.equals("waitForElementPresent") ||
					 param1.equals("waitForElementNotPresent") ||
					 param1.equals("waitForNotVisible") ||
					 param1.equals("waitForTextNotPresent") ||
					 param1.equals("waitForTextPresent") ||
					 param1.equals("waitForVisible")) {

				if (param1.equals("downloadTempFile")) {
					sb.append("BrowserCommands.");
				}
				else {
					sb.append("selenium.");
				}

				sb.append(param1);
				sb.append(StringPool.OPEN_PARENTHESIS);

				if (param2.startsWith("${")) {
					sb.append("RuntimeVariables.getValue(\"");

					String text = param2.substring(2, param2.length() - 1);

					sb.append(text);
					sb.append("\")");
				}
				else {
					sb.append("\"");
					sb.append(param2);
					sb.append("\"");
				}

				sb.append(");");
			}
			else if (param1.equals("clickAndWait")) {
				sb.append("selenium.click(RuntimeVariables.replace(\"");
				sb.append(param2);
				sb.append("\"));");
				sb.append("selenium.waitForPageToLoad(\"30000\");");
			}
			else if (param1.equals("clickAtAndWait") ||
					 param1.equals("keyDownAndWait") ||
					 param1.equals("keyPressAndWait") ||
					 param1.equals("keyUpAndWait") ||
					 param1.equals("selectAndWait")) {

				sb.append("selenium.");

				String text = param1.substring(0, param1.length() - 7);

				sb.append(text);
				sb.append("(\"");
				sb.append(param2);
				sb.append("\", RuntimeVariables.replace(\"");
				sb.append(param3);
				sb.append("\"));");
				sb.append("selenium.waitForPageToLoad(\"30000\");");
			}
			else if (param1.equals("close") || param1.equals("goBack") ||
					 param1.equals("refresh") ||
					 param1.equals("setBrowserOption") ||
					 param1.equals("setDefaultTimeout") ||
					 param1.equals("setDefaultTimeoutImplicit") ||
					 param1.equals("windowFocus") ||
					 param1.equals("windowMaximize")) {

				if (param1.equals("setBrowserOption")) {
					sb.append("BrowserCommands.");
				}
				else {
					sb.append("selenium.");
				}

				sb.append(param1);
				sb.append("();");
			}
			else if (param1.equals("dragAndDropToObject")) {
				sb.append("selenium.");
				sb.append("dragAndDropToObject(\"");
				sb.append(param2);
				sb.append("\", \"");
				sb.append(param3);
				sb.append("\");");
			}
			else if (param1.equals("echo")) {
				sb.append("System.out.println(");

				if (param2.startsWith("${")) {
					sb.append("RuntimeVariables.getValue(\"");

					String text = param2.substring(2, param2.length() - 1);

					sb.append(text);
					sb.append("\")");
				}
				else {
					sb.append("\"");
					sb.append(param2);
					sb.append("\"");
				}

				sb.append(");");
			}
			else if (param1.equals("goBackAndWait") ||
					 param1.equals("refreshAndWait") ||
					 param1.equals("windowMaximizeAndWait")) {

				String text = param1.substring(0, param1.length() - 7);

				sb.append("selenium.");
				sb.append(text);
				sb.append("();");
				sb.append("selenium.waitForPageToLoad(\"30000\");");
			}
			else if (param1.equals("gotoIf")) {
				String conditional = StringUtil.replace(
					param2, new String[] {"${", "}"}, new String[] {"", ""});

				sb.append("if (");
				sb.append(conditional);
				sb.append(") {");
				sb.append("label =");
				sb.append(labels.get(param3));
				sb.append(";");
				sb.append("continue;");
				sb.append("}");
			}
			else if (param1.equals("label")) {
				String label = labels.get(param2);

				sb.append("case ");
				sb.append(label);
				sb.append(":");
			}
			else if (param1.equals("pause")) {
				sb.append("Thread.sleep(");
				sb.append(param2);
				sb.append(");");
			}
			else if (param1.equals("store")) {
				sb.append("boolean ");
				sb.append(param3);
				sb.append(" = ");

				if (param2.startsWith("eval(")) {
					String eval = param2.substring(5, param2.length() - 1);

					eval = StringUtil.replace(eval, "'", "\"");

					sb.append(eval);
				}

				sb.append(";");
			}
			else if (param1.equals("storeAttribute")) {
				sb.append("String ");
				sb.append(param3);
				sb.append(" = selenium.getAttribute(\"");
				sb.append(param2);
				sb.append("\");");

				sb.append("RuntimeVariables.setValue(\"");
				sb.append(param3);
				sb.append("\", ");
				sb.append(param3);
				sb.append(");");
			}
			else if (param1.equals("storeChecked")) {
				sb.append("boolean ");
				sb.append(param3);
				sb.append(" = selenium.isChecked(\"");
				sb.append(param2);
				sb.append("\");");
			}
			else if (param1.equals("storeCurrentDay")) {
				sb.append("String ");
				sb.append(param2);
				sb.append(" = selenium.getCurrentDay();");

				sb.append("RuntimeVariables.setValue(\"");
				sb.append(param2);
				sb.append("\", ");
				sb.append(param2);
				sb.append(");");
			}
			else if (param1.equals("storeCurrentMonth")) {
				sb.append("String ");
				sb.append(param2);
				sb.append(" = selenium.getCurrentMonth();");

				sb.append("RuntimeVariables.setValue(\"");
				sb.append(param2);
				sb.append("\", ");
				sb.append(param2);
				sb.append(");");
			}
			else if (param1.equals("storeCurrentYear")) {
				sb.append("String ");
				sb.append(param2);
				sb.append(" = selenium.getCurrentYear();");

				sb.append("RuntimeVariables.setValue(\"");
				sb.append(param2);
				sb.append("\", ");
				sb.append(param2);
				sb.append(");");
			}
			else if (param1.equals("storeElementPresent")) {
				sb.append("boolean ");
				sb.append(param3);
				sb.append(" = selenium.isElementPresent(\"");
				sb.append(param2);
				sb.append("\");");
			}
			else if (param1.equals("storeFirstNumber")) {
				sb.append("String ");
				sb.append(param3);
				sb.append(" = selenium.getFirstNumber(\"");
				sb.append(param2);
				sb.append("\");");

				sb.append("RuntimeVariables.setValue(\"");
				sb.append(param3);
				sb.append("\", ");
				sb.append(param3);
				sb.append(");");
			}
			else if (param1.equals("storeFirstNumberIncrement")) {
				sb.append("String ");
				sb.append(param3);
				sb.append(" = selenium.getFirstNumberIncrement(\"");
				sb.append(param2);
				sb.append("\");");

				sb.append("RuntimeVariables.setValue(\"");
				sb.append(param3);
				sb.append("\", ");
				sb.append(param3);
				sb.append(");");
			}
			else if (param1.equals("storeLocation")) {
				sb.append("String ");
				sb.append(param2);
				sb.append(" = selenium.getLocation();");

				sb.append("RuntimeVariables.setValue(\"");
				sb.append(param2);
				sb.append("\", ");
				sb.append(param2);
				sb.append(");");
			}
			else if (param1.equals("storeNumberIncrement")) {
				sb.append("String ");
				sb.append(param3);
				sb.append(" = selenium.getNumberIncrement(");
				sb.append("RuntimeVariables.getValue(\"");

				String expression = param2.substring(2, param2.length() - 1);

				sb.append(expression);
				sb.append("\"));");

				sb.append("RuntimeVariables.setValue(\"");
				sb.append(param3);
				sb.append("\", ");
				sb.append(param3);
				sb.append(");");
			}
			else if (param1.equals("storeNumberDecrement")) {
				sb.append("String ");
				sb.append(param3);
				sb.append(" = selenium.getNumberDecrement(");
				sb.append("RuntimeVariables.getValue(\"");

				String expression = param2.substring(2, param2.length() - 1);

				sb.append(expression);
				sb.append("\"));");

				sb.append("RuntimeVariables.setValue(\"");
				sb.append(param3);
				sb.append("\", ");
				sb.append(param3);
				sb.append(");");
			}
			else if (param1.equals("storeText")) {
				sb.append("String ");
				sb.append(param3);
				sb.append(" = selenium.getText(\"");
				sb.append(param2);
				sb.append("\");");

				sb.append("RuntimeVariables.setValue(\"");
				sb.append(param3);
				sb.append("\", ");
				sb.append(param3);
				sb.append(");");
			}
			else if (param1.equals("storeValue")) {
				sb.append("String ");
				sb.append(param3);
				sb.append(" = selenium.getValue(\"");
				sb.append(param2);
				sb.append("\");");

				sb.append("RuntimeVariables.setValue(\"");
				sb.append(param3);
				sb.append("\", ");
				sb.append(param3);
				sb.append(");");
			}
			else if (param1.equals("storeVisible")) {
				sb.append("boolean ");
				sb.append(param3);
				sb.append(" = selenium.isVisible(\"");
				sb.append(param2);
				sb.append("\");");
			}
			else if (param1.equals("verifyElementNotPresent") ||
					 param1.equals("verifyElementPresent")) {

				if (param1.equals("verifyElementNotPresent")) {
					sb.append("verifyFalse");
				}
				else if (param1.equals("verifyElementPresent")) {
					sb.append("verifyTrue");
				}

				sb.append("(selenium.isElementPresent(\"");
				sb.append(param2);
				sb.append("\"));");
			}
			else if (param1.equals("verifyTextNotPresent") ||
					 param1.equals("verifyTextPresent")) {

				if (param1.equals("verifyTextNotPresent")) {
					sb.append("verifyFalse");
				}
				else if (param1.equals("verifyTextPresent")) {
					sb.append("verifyTrue");
				}

				sb.append("(selenium.isTextPresent(\"");
				sb.append(param2);
				sb.append("\"));");
			}
			else if (param1.equals("verifyTitle")) {
				sb.append("verifyEquals(\"");
				sb.append(param2);
				sb.append("\", selenium.getTitle());");
			}
			else if (param1.equals("waitForNotPartialText") ||
					 param1.equals("waitForNotSelectedLabel") ||
					 param1.equals("waitForNotText") ||
					 param1.equals("waitForNotValue") ||
					 param1.equals("waitForPartialText") ||
					 param1.equals("waitForSelectedLabel") ||
					 param1.equals("waitForText") ||
					 param1.equals("waitForValue")) {

				sb.append("selenium.");
				sb.append(param1);
				sb.append("(\"");
				sb.append(param2);
				sb.append("\",");

				if (param3.startsWith("${")) {
					sb.append("RuntimeVariables.getValue(\"");

					String text = param3.substring(2, param3.length() - 1);

					sb.append(text);
					sb.append("\")");
				}
				else {
					sb.append("\"");
					sb.append(param3);
					sb.append("\"");
				}

				sb.append(");");
			}
			else {
				System.out.println(
					testFileName + " has an unknown command " + param1);
			}
		}

		if (labels.size() > 0) {
			sb.append("case 100:");
			sb.append("label = -1;");
			sb.append("}");
			sb.append("}");
		}

		sb.append("}");
		sb.append("}");

		writeFile(testFileName, sb.toString(), true);
	}

	protected void translateTestSuite(String fileName) throws Exception {
		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(StringPool.PERIOD);

		String testPackagePath = StringUtil.replace(
			fileName.substring(0, x), StringPool.SLASH, StringPool.PERIOD);
		String testName = fileName.substring(x + 1, y);
		String testFileName = fileName.substring(0, y) + ".java";

		StringBundler sb = new StringBundler();

		sb.append("package ");
		sb.append(testPackagePath);
		sb.append(";\n");

		sb.append("import com.liferay.portalweb.portal.BaseTestSuite;\n");
		sb.append("import com.liferay.portalweb.portal.StopSeleniumTest;\n");

		String content = readFile(fileName);

		content = formatTestSuite(fileName, content);

		x = 0;
		y = 0;

		while (content.indexOf("<a href=\"", x) != -1) {
			x = content.indexOf("<a href=\"", x) + 9;
			y = content.indexOf("\">", x);

			String testCaseName = content.substring(x, y);

			if (!testCaseName.contains("..")) {
				continue;
			}

			if (!testCaseName.contains("../portalweb/")) {
				throw new IllegalArgumentException(
					fileName + " has improper relative path");
			}

			testCaseName = StringUtil.replace(
				testCaseName, "../../portalweb/", "../");

			int z = fileName.lastIndexOf(StringPool.SLASH);

			String importClassName = fileName.substring(0, z);

			int count = StringUtil.count(testCaseName, "..");

			for (int i = 0; i < count; i++) {
				z = importClassName.lastIndexOf(StringPool.SLASH);

				importClassName = fileName.substring(0, z);
			}

			z = testCaseName.lastIndexOf("../", z);

			importClassName += testCaseName.substring(
				z + 2, testCaseName.length() -5);
			importClassName = StringUtil.replace(
				importClassName, StringPool.SLASH, StringPool.PERIOD);

			sb.append("import ");
			sb.append(importClassName);
			sb.append(";\n");
		}

		sb.append("import junit.framework.Test;\n");
		sb.append("import junit.framework.TestSuite;\n");

		sb.append("public class ");
		sb.append(testName);
		sb.append(" extends BaseTestSuite {");

		sb.append("public static Test suite() {");

		sb.append("TestSuite testSuite = new TestSuite();");

		x = content.indexOf("</b></td></tr>");
		y = content.indexOf("</tbody>");

		content = content.substring(x + 15, y);

		x = 0;
		y = 0;

		while (true) {
			x = content.indexOf("\">", x);
			y = content.indexOf("</a>", x);

			if ((x == -1) || (y == -1)) {
				break;
			}

			String className = content.substring(x + 2, y);

			x += className.length();

			sb.append("testSuite.addTestSuite(");
			sb.append(className);
			sb.append(".class);");
		}

		sb.append("return testSuite;");
		sb.append("}");
		sb.append("}");

		writeFile(testFileName, sb.toString(), true);
	}

	protected void writeFile(String fileName, String content, boolean format)
		throws Exception {

		File file = new File(_basedir + "/" + fileName);

		if (format) {
			ServiceBuilder.writeFile(file, content);
		}
		else {
			System.out.println("Writing " + file);

			FileUtil.write(file, content);
		}
	}

	private static final String[] _FIX_PARAM_NEW_SUBS = {"\\n", "\\n"};

	private static final String[] _FIX_PARAM_OLD_SUBS = {"\\\\n", "<br />"};

	private String _basedir;
	private boolean _reportDuplicates;

	private class TestHtmlCountComparator
		implements Comparator<ObjectValuePair<String, IntegerWrapper>> {

		@Override
		public int compare(
			ObjectValuePair<String, IntegerWrapper> object1,
			ObjectValuePair<String, IntegerWrapper> object2) {

			IntegerWrapper integerWrapper1 = object1.getValue();
			IntegerWrapper integerWrapper2 = object2.getValue();

			if (integerWrapper1.getValue() > integerWrapper2.getValue()) {
				return -1;
			}
			else if (integerWrapper1.getValue() < integerWrapper2.getValue()) {
				return 1;
			}
			else {
				return 0;
			}
		}

	}

}