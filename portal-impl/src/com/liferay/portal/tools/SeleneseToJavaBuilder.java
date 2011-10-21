/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeFormatter;
import com.liferay.portal.tools.servicebuilder.ServiceBuilder;
import com.liferay.portal.util.InitUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class SeleneseToJavaBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		if (args.length == 1) {
			new SeleneseToJavaBuilder(args[0]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public SeleneseToJavaBuilder(String basedir) throws Exception {
		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(basedir);
		ds.setExcludes(
			new String[] {
				"**\\IterateThemeTest.java", "**\\StopSeleniumTest.java"
			});
		ds.setIncludes(new String[] {"**\\*Test.html", "**\\*Test.java"});
		/*ds.setIncludes(
			new String[] {
				"**\\liferay\\portalweb\\portal\\login\\*Test.html",
				"**\\liferay\\portalweb\\portal\\login\\*Test.java"
			});*/

		ds.scan();

		Set<String> fileNames = SetUtil.fromArray(ds.getIncludedFiles());

		for (String fileName : fileNames) {

			// I would have preferred to use XlateHtmlSeleneseToJava, but it
			// is horribly out of sync with Selenium IDE and generates incorrect
			// code.

			/*String input = StringUtil.replace(
				basedir + "/" + fileName, "\\", "/");

			XlateHtmlSeleneseToJava.main(
				new String[] {
					"test", "-silent", input
				}
			);*/

			if (fileName.endsWith("Test.html")) {
				translate(basedir, fileName);
			}
			else if (fileName.endsWith("Test.java")) {
				if (!fileNames.contains(
						fileName.substring(0, fileName.length() - 5) +
							".html")) {

					System.out.println("unused: " + fileName);
				}
			}
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

	protected void translate(String basedir, String fileName) throws Exception {
		fileName = StringUtil.replace(
			fileName, StringPool.BACK_SLASH, StringPool.SLASH);

		int x = fileName.lastIndexOf(StringPool.SLASH);
		int y = fileName.indexOf(CharPool.PERIOD);

		String testPackagePath = StringUtil.replace(
			fileName.substring(0, x), StringPool.SLASH, StringPool.PERIOD);
		String testName = fileName.substring(x + 1, y);
		String testMethodName =
			"test" + testName.substring(0, testName.length() - 4);
		String testFileName =
			basedir + "/" + fileName.substring(0, y) + ".java";

		StringBundler sb = new StringBundler();

		sb.append("package ");
		sb.append(testPackagePath);
		sb.append(";\n\n");

		sb.append("import com.liferay.portal.kernel.util.FileUtil;\n");
		sb.append("import com.liferay.portal.kernel.util.StringPool;\n");
		sb.append("import com.liferay.portalweb.portal.BaseTestCase;\n\n");
		sb.append(
			"import com.liferay.portalweb.portal.util.RuntimeVariables;\n\n");

		sb.append("public class ");
		sb.append(testName);
		sb.append(" extends BaseTestCase {");

		sb.append("public void ");
		sb.append(testMethodName);
		sb.append("() throws Exception {");

		String xml = FileUtil.read(basedir + "/" + fileName);

		if ((xml.indexOf("<title>" + testName + "</title>") == -1) ||
			(xml.indexOf("colspan=\"3\">" + testName + "</td>") == -1)) {

			System.out.println(testName + " has an invalid test name");
		}

		if (xml.indexOf("&gt;") != -1) {
			xml = StringUtil.replace(xml, "&gt;", ">");

			FileUtil.write(basedir + "/" + fileName, xml);
		}

		if (xml.indexOf("&lt;") != -1) {
			xml = StringUtil.replace(xml, "&lt;", "<");

			FileUtil.write(basedir + "/" + fileName, xml);
		}

		if (xml.indexOf("&quot;") != -1) {
			xml = StringUtil.replace(xml, "&quot;", "\"");

			FileUtil.write(basedir + "/" + fileName, xml);
		}

		x = xml.indexOf("<tbody>");
		y = xml.indexOf("</tbody>");

		xml = xml.substring(x, y + 8);

		Map<String, String> labels = new HashMap<String, String>();

		int labelCount = 1;

		Map<Integer, Boolean> takeScreenShots = new HashMap<Integer, Boolean>();

		x = 0;
		y = 0;

		while (true) {
			x = xml.indexOf("<tr>", x);
			y = xml.indexOf("\n</tr>", x);

			if ((x == -1) || (y == -1)) {
				break;
			}

			x += 6;
			y++;

			String step = xml.substring(x, y);

			String[] params = getParams(step);

			String param1 = params[0];
			String param2 = fixParam(params[1]);

			if (param1.equals("assertConfirmation")) {
				int previousX = x - 6;

				previousX = xml.lastIndexOf("<tr>", previousX - 1);
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

		while (true) {
			x = xml.indexOf("<tr>", x);
			y = xml.indexOf("\n</tr>", x);

			if ((x == -1) || (y == -1)) {
				break;
			}

			x += 6;
			y++;

			String step = xml.substring(x, y);

			String[] params = getParams(step);

			String param1 = params[0];
			String param2 = fixParam(params[1]);
			String param3 = fixParam(params[2]);

			if (param1.equals("addSelection") || param1.equals("clickAt") ||
				param1.equals("doubleClickAt") || param1.equals("keyPress") ||
				param1.equals("mouseMoveAt") || param1.equals("openWindow") ||
				param1.equals("select") || param1.equals("type") ||
				param1.equals("typeKeys") || param1.equals("uploadFile") ||
				param1.equals("waitForPopUp")) {

				sb.append("selenium.");
				sb.append(param1);
				sb.append("(");

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

				if (param1.equals("assertElementNotPresent")) {
					sb.append("assertFalse");
				}
				else if (param1.equals("assertElementPresent")) {
					sb.append("assertTrue");
				}

				sb.append("(selenium.isElementPresent(\"");
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

				sb.append("(");

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

				sb.append("(");
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
					 param1.equals("downloadFile") ||
					 param1.equals("mouseDown") || param1.equals("mouseMove") ||
					 param1.equals("mouseOver") || param1.equals("mouseUp") ||
					 param1.equals("open") || param1.equals("selectFrame") ||
					 param1.equals("selectPopUp") ||
					 param1.equals("selectWindow") ||
					 param1.equals("setTimeout") || param1.equals("uncheck")) {

				sb.append("selenium.");
				sb.append(param1);
				sb.append("(");

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
					 param1.equals("keyPressAndWait") ||
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
			else if (param1.equals("close") || param1.equals("refresh") ||
					 param1.equals("setBrowserOption") ||
					 param1.equals("windowFocus") ||
					 param1.equals("windowMaximize")) {

				sb.append("selenium.");
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
				sb.append("System.out.println(\"");
				sb.append(param2);
				sb.append("\");");
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
			else if (param1.equals("refreshAndWait") ||
					 param1.equals("windowMaximizeAndWait")) {

				String text = param1.substring(0, param1.length() - 7);

				sb.append("selenium.");
				sb.append(text);
				sb.append("();");
				sb.append("selenium.waitForPageToLoad(\"30000\");");
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
			else if (param1.equals("waitForConfirmation") ||
					 param1.equals("waitForElementNotPresent") ||
					 param1.equals("waitForElementPresent") ||
					 param1.equals("waitForNotPartialText") ||
					 param1.equals("waitForNotSelectedLabel") ||
					 param1.equals("waitForNotTable") ||
					 param1.equals("waitForNotText") ||
					 param1.equals("waitForNotValue") ||
					 param1.equals("waitForNotVisible") ||
					 param1.equals("waitForPartialText") ||
					 param1.equals("waitForSelectedLabel") ||
					 param1.equals("waitForTable") ||
					 param1.equals("waitForText") ||
					 param1.equals("waitForTextNotPresent") ||
					 param1.equals("waitForTextPresent") ||
					 param1.equals("waitForValue") ||
					 param1.equals("waitForVisible")) {

				sb.append("for (int second = 0;; second++) {");
				sb.append("if (second >= 90) {");
				sb.append("fail(\"timeout\");");
				sb.append("}");

				sb.append("try {");
				sb.append("if (");

				if (param1.equals("waitForElementNotPresent") ||
					param1.equals("waitForNotPartialText") ||
					param1.equals("waitForNotSelectedLabel") ||
					param1.equals("waitForNotTable") ||
					param1.equals("waitForNotText") ||
					param1.equals("waitForNotValue") ||
					param1.equals("waitForNotVisible") ||
					param1.equals("waitForTextNotPresent")) {

					sb.append("!");
				}

				if (param1.equals("waitForConfirmation")) {
					sb.append("\"");
					sb.append(param2);
					sb.append("\".equals(selenium.getConfirmation())");
				}
				else if (param1.equals("waitForElementNotPresent") ||
					param1.equals("waitForElementPresent")) {

					sb.append("selenium.isElementPresent");
					sb.append("(\"");
					sb.append(param2);
					sb.append("\")");
				}
				else if (param1.equals("waitForNotPartialText") ||
						 param1.equals("waitForPartialText")) {

					sb.append("selenium.isPartialText(\"");
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

					sb.append(")");
				}
				else if (param1.equals("waitForNotSelectedLabel") ||
						 param1.equals("waitForSelectedLabel"))
				{

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

					sb.append(".equals(selenium.getSelectedLabel(\"");
					sb.append(param2);
					sb.append("\"))");
				}
				else if (param1.equals("waitForNotTable") ||
						 param1.equals("waitForTable")) {

					sb.append("StringPool.BLANK.equals(selenium.getTable(\"");
					sb.append(param2);
					sb.append("\"))");
				}
				else if (param1.equals("waitForNotText") ||
						 param1.equals("waitForText")) {

					sb.append("RuntimeVariables.replace(\"");
					sb.append(param3);
					sb.append("\").equals(selenium.getText(\"");
					sb.append(param2);
					sb.append("\"))");
				}
				else if (param1.equals("waitForNotValue") ||
						 param1.equals("waitForValue")) {

					sb.append("RuntimeVariables.replace(\"");
					sb.append(param3);
					sb.append("\").equals(selenium.getValue(\"");
					sb.append(param2);
					sb.append("\"))");
				}
				else if (param1.equals("waitForNotVisible") ||
						 param1.equals("waitForVisible")) {

					sb.append("selenium.isVisible");
					sb.append("(\"");
					sb.append(param2);
					sb.append("\")");
				}
				else if (param1.equals("waitForTextNotPresent") ||
						 param1.equals("waitForTextPresent")) {

					sb.append("selenium.isTextPresent");
					sb.append("(\"");
					sb.append(param2);
					sb.append("\")");
				}

				sb.append(") {");
				sb.append("break;");
				sb.append("}");
				sb.append("}");
				sb.append("catch (Exception e) {");
				sb.append("}");

				sb.append("Thread.sleep(1000);");
				sb.append("}");
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

		String content = sb.toString();

		ServiceBuilder.writeFile(new File(testFileName), content);
	}

	private static final String[] _FIX_PARAM_OLD_SUBS = new String[] {
		"\\\\n", "<br />"
	};

	private static final String[] _FIX_PARAM_NEW_SUBS = new String[] {
		"\\n", "\\n"
	};

}