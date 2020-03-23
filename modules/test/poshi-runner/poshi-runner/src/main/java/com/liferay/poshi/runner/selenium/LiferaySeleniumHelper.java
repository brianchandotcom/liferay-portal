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

package com.liferay.poshi.runner.selenium;

import com.liferay.poshi.runner.exception.PoshiRunnerWarningException;
import com.liferay.poshi.runner.util.EmailCommands;
import com.liferay.poshi.runner.util.FileUtil;
import com.liferay.poshi.runner.util.GetterUtil;
import com.liferay.poshi.runner.util.OSDetector;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.Validator;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import junit.framework.TestCase;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.desktop.DesktopKeyboard;

/**
 * @author Brian Wing Shun Chan
 */
@SuppressWarnings("deprecation")
public class LiferaySeleniumHelper {

	public static void addToJavaScriptExceptions(Exception exception) {
		_javaScriptExceptions.add(exception);
	}

	public static void addToJavaScriptExceptions(List<Exception> exceptions) {
		_javaScriptExceptions.addAll(exceptions);
	}

	public static void addToLiferayExceptions(Exception exception) {
		_liferayExceptions.add(exception);
	}

	public static void addToLiferayExceptions(List<Exception> exceptions) {
		_liferayExceptions.addAll(exceptions);
	}

	public static void assertConsoleErrors() throws Exception {
		if (!PropsValues.TEST_ASSERT_CONSOLE_ERRORS) {
			return;
		}

		String content = getTestConsoleLogFileContent();

		if (content.equals("")) {
			return;
		}

		SAXReader saxReader = new SAXReader();

		content = "<log4j>" + content + "</log4j>";
		content = content.replaceAll("log4j:", "");

		InputStream inputStream = new ByteArrayInputStream(
			content.getBytes("UTF-8"));

		Document document = saxReader.read(inputStream);

		Element rootElement = document.getRootElement();

		List<Element> eventElements = rootElement.elements("event");

		List<Exception> exceptions = new ArrayList<>();

		for (Element eventElement : eventElements) {
			String level = eventElement.attributeValue("level");

			if (level.equals("ERROR") || level.equals("FATAL") ||
				level.equals("WARN")) {

				String timestamp = eventElement.attributeValue("timestamp");

				if (_errorTimestamps.contains(timestamp)) {
					continue;
				}

				_errorTimestamps.add(timestamp);

				Element messageElement = eventElement.element("message");

				String messageText = messageElement.getText();

				if (isIgnorableErrorLine(messageText)) {
					continue;
				}

				StringBuilder sb = new StringBuilder();

				sb.append("LIFERAY_ERROR: ");
				sb.append(messageText);

				System.out.println(sb.toString());

				exceptions.add(new PoshiRunnerWarningException(sb.toString()));
			}
		}

		if (!exceptions.isEmpty()) {
			addToLiferayExceptions(exceptions);

			throw exceptions.get(0);
		}
	}

	public static void assertLocation(
			LiferaySelenium liferaySelenium, String pattern)
		throws Exception {

		TestCase.assertEquals(pattern, liferaySelenium.getLocation());
	}

	public static void assertNoJavaScriptExceptions() throws Exception {
		if (!_javaScriptExceptions.isEmpty()) {
			StringBuilder sb = new StringBuilder();

			sb.append(_javaScriptExceptions.size());
			sb.append(" Javascript Exception");

			if (_javaScriptExceptions.size() > 1) {
				sb.append("s were");
			}
			else {
				sb.append(" was");
			}

			sb.append(" thrown");

			System.out.println();
			System.out.println("##");
			System.out.println("## " + sb.toString());
			System.out.println("##");

			for (int i = 0; i < _javaScriptExceptions.size(); i++) {
				Exception exception = _javaScriptExceptions.get(i);

				System.out.println();
				System.out.println("##");
				System.out.println("## Javascript Exception #" + (i + 1));
				System.out.println("##");
				System.out.println();
				System.out.println(exception.getMessage());
				System.out.println();
			}

			throw new Exception(sb.toString());
		}
	}

	public static void assertNoLiferayExceptions() throws Exception {
		if (!_liferayExceptions.isEmpty()) {
			StringBuilder sb = new StringBuilder();

			sb.append(_liferayExceptions.size());
			sb.append(" Liferay Exception");

			if (_liferayExceptions.size() > 1) {
				sb.append("s were");
			}
			else {
				sb.append(" was");
			}

			sb.append(" thrown");

			System.out.println();
			System.out.println("##");
			System.out.println("## " + sb.toString());
			System.out.println("##");

			for (int i = 0; i < _liferayExceptions.size(); i++) {
				Exception exception = _liferayExceptions.get(i);

				System.out.println();
				System.out.println("##");
				System.out.println("## Liferay Exception #" + (i + 1));
				System.out.println("##");
				System.out.println();
				System.out.println(exception.getMessage());
				System.out.println();
			}

			throw new Exception(sb.toString());
		}
	}

	public static void assertNoPoshiWarnings() throws Exception {
		if (!PropsValues.TEST_ASSERT_WARNING_EXCEPTIONS) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		if (!_javaScriptExceptions.isEmpty()) {
			sb.append("\n");
			sb.append("##\n");

			sb.append("## ");
			sb.append(_javaScriptExceptions.size());
			sb.append(" Javascript Exception");

			if (_javaScriptExceptions.size() > 1) {
				sb.append("s were");
			}
			else {
				sb.append(" was");
			}

			sb.append(" thrown\n");

			sb.append("##\n");
			sb.append("\n");

			for (Exception exception : _javaScriptExceptions) {
				sb.append(exception.getMessage());

				sb.append("\n");
			}

			sb.append("\n");
		}

		if (!_liferayExceptions.isEmpty()) {
			sb.append("\n");
			sb.append("##\n");

			sb.append("## ");
			sb.append(_liferayExceptions.size());
			sb.append(" Liferay Exception");

			if (_liferayExceptions.size() > 1) {
				sb.append("s were");
			}
			else {
				sb.append(" was");
			}

			sb.append(" thrown\n");

			sb.append("##\n");
			sb.append("\n");

			for (Exception exception : _liferayExceptions) {
				sb.append(exception.getMessage());

				sb.append("\n");
			}

			sb.append("\n");
		}

		if (Validator.isNotNull(sb.toString())) {
			throw new Exception(sb.toString());
		}
	}

	public static void captureScreen(String fileName) throws Exception {
		if (!PropsValues.SAVE_SCREENSHOT) {
			return;
		}

		File file = new File(fileName);

		file.mkdirs();

		Robot robot = new Robot();

		Toolkit toolkit = Toolkit.getDefaultToolkit();

		Rectangle rectangle = new Rectangle(toolkit.getScreenSize());

		BufferedImage bufferedImage = robot.createScreenCapture(rectangle);

		ImageIO.write(bufferedImage, "jpg", file);
	}

	public static void connectToEmailAccount(
			String emailAddress, String emailPassword)
		throws Exception {

		EmailCommands.connectToEmailAccount(emailAddress, emailPassword);
	}

	public static void deleteAllEmails() throws Exception {
		EmailCommands.deleteAllEmails();
	}

	public static void echo(String message) {
		System.out.println(message);
	}

	public static void fail(String message) {
		TestCase.fail(message);
	}

	public static String getEmailBody(String index) throws Exception {
		return EmailCommands.getEmailBody(GetterUtil.getInteger(index));
	}

	public static String getEmailSubject(String index) throws Exception {
		return EmailCommands.getEmailSubject(GetterUtil.getInteger(index));
	}

	public static ImageTarget getImageTarget(
			LiferaySelenium liferaySelenium, String image)
		throws Exception {

		String filePath =
			FileUtil.getSeparator() + liferaySelenium.getSikuliImagesDirName() +
				image;

		File file = new File(getSourceDirFilePath(filePath));

		return new ImageTarget(file);
	}

	public static String getNumberDecrement(String value) {
		return String.valueOf(GetterUtil.getInteger(value) - 1);
	}

	public static String getNumberIncrement(String value) {
		return String.valueOf(GetterUtil.getInteger(value) + 1);
	}

	public static String getSourceDirFilePath(String fileName)
		throws Exception {

		List<String> filePaths = new ArrayList<>();

		List<String> baseDirNames = new ArrayList<>();

		baseDirNames.add(PropsValues.TEST_BASE_DIR_NAME);

		if (Validator.isNotNull(PropsValues.TEST_INCLUDE_DIR_NAMES)) {
			Collections.addAll(
				baseDirNames, PropsValues.TEST_INCLUDE_DIR_NAMES);
		}

		if (Validator.isNotNull(PropsValues.TEST_SUBREPO_DIRS)) {
			Collections.addAll(baseDirNames, PropsValues.TEST_SUBREPO_DIRS);
		}

		for (String baseDirName : baseDirNames) {
			String filePath = FileUtil.getCanonicalPath(
				baseDirName + FileUtil.getSeparator() + fileName);

			if (FileUtil.exists(filePath)) {
				filePaths.add(filePath);
			}
		}

		if (filePaths.size() > 1) {
			StringBuilder sb = new StringBuilder();

			sb.append("Duplicate file names found!\n");

			for (String filePath : filePaths) {
				sb.append(filePath);
				sb.append("\n");
			}

			throw new Exception(sb.toString());
		}
		else if (filePaths.isEmpty()) {
			throw new Exception("File not found " + fileName);
		}

		return filePaths.get(0);
	}

	public static String getTestConsoleLogFileContent() throws Exception {
		if (Validator.isNull(PropsValues.TEST_CONSOLE_LOG_FILE_NAME)) {
			return "";
		}

		Map<String, File> consoleLogFiles = new TreeMap<>();

		String baseDirName = PropsValues.TEST_CONSOLE_LOG_FILE_NAME;

		int x = PropsValues.TEST_CONSOLE_LOG_FILE_NAME.lastIndexOf("/");

		if (x != -1) {
			baseDirName = PropsValues.TEST_CONSOLE_LOG_FILE_NAME.substring(
				0, x);
		}

		List<URL> urls = FileUtil.getIncludedResourceURLs(
			new String[] {PropsValues.TEST_CONSOLE_LOG_FILE_NAME}, baseDirName);

		for (URL url : urls) {
			File file = new File(url.toURI());

			consoleLogFiles.put(Long.toString(file.lastModified()), file);
		}

		StringBuilder sb = new StringBuilder();

		SortedSet<String> keys = new TreeSet<>(consoleLogFiles.keySet());

		for (String key : keys) {
			File file = consoleLogFiles.get(key);

			sb.append(FileUtil.read(file));
		}

		return sb.toString();
	}

	public static boolean isConsoleTextPresent(String text) throws Exception {
		String content = getTestConsoleLogFileContent();

		if (content.equals("")) {
			return false;
		}

		SAXReader saxReader = new SAXReader();

		content = "<log4j>" + content + "</log4j>";
		content = content.replaceAll("log4j:", "");

		InputStream inputStream = new ByteArrayInputStream(
			content.getBytes("UTF-8"));

		Document document = saxReader.read(inputStream);

		Element rootElement = document.getRootElement();

		List<Element> eventElements = rootElement.elements("event");

		for (Element eventElement : eventElements) {
			Element messageElement = eventElement.element("message");

			String messageText = messageElement.getText();

			Pattern pattern = Pattern.compile(text);

			Matcher matcher = pattern.matcher(messageText);

			if (matcher.find()) {
				return true;
			}

			Element throwableElement = eventElement.element("throwable");

			if (throwableElement != null) {
				String throwableText = throwableElement.getText();

				matcher = pattern.matcher(throwableText);

				if (matcher.find()) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isIgnorableErrorLine(String line) throws Exception {
		if (isInIgnoreErrorsFile(line, "log")) {
			return true;
		}

		if (Objects.equals(PropsValues.LIFERAY_PORTAL_BUNDLE, "6.2.10.1") ||
			Objects.equals(PropsValues.LIFERAY_PORTAL_BUNDLE, "6.2.10.2") ||
			Objects.equals(PropsValues.LIFERAY_PORTAL_BUNDLE, "6.2.10.3") ||
			Objects.equals(PropsValues.LIFERAY_PORTAL_BUNDLE, "6.2.10.4") ||
			Objects.equals(PropsValues.LIFERAY_PORTAL_BRANCH, "ee-6.2.10")) {

			if (line.contains(
					"com.liferay.portal.kernel.search.SearchException: " +
						"java.nio.channels.ClosedByInterruptException")) {

				return true;
			}

			if (line.contains(
					"org.apache.lucene.store.AlreadyClosedException")) {

				return true;
			}
		}

		if (Validator.isNotNull(PropsValues.IGNORE_ERRORS)) {
			if (Validator.isNotNull(PropsValues.IGNORE_ERRORS_DELIMITER)) {
				String ignoreErrorsDelimiter =
					PropsValues.IGNORE_ERRORS_DELIMITER;

				if (ignoreErrorsDelimiter.equals("|")) {
					ignoreErrorsDelimiter = "\\|";
				}

				String[] ignoreErrors = PropsValues.IGNORE_ERRORS.split(
					ignoreErrorsDelimiter);

				for (String ignoreError : ignoreErrors) {
					if (line.contains(ignoreError)) {
						return true;
					}
				}
			}
			else if (line.contains(PropsValues.IGNORE_ERRORS)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isInIgnoreErrorsFile(String line, String errorType)
		throws Exception {

		if (Validator.isNotNull(PropsValues.IGNORE_ERRORS_FILE_NAME)) {
			SAXReader saxReader = new SAXReader();

			String content = FileUtil.read(PropsValues.IGNORE_ERRORS_FILE_NAME);

			InputStream inputStream = new ByteArrayInputStream(
				content.getBytes("UTF-8"));

			Document document = saxReader.read(inputStream);

			Element rootElement = document.getRootElement();

			Element errorTypeElement = rootElement.element(errorType);

			if (errorTypeElement == null) {
				return false;
			}

			List<Element> ignoreErrorElements = errorTypeElement.elements(
				"ignore-error");

			for (Element ignoreErrorElement : ignoreErrorElements) {
				Element containsElement = ignoreErrorElement.element(
					"contains");
				Element matchesElement = ignoreErrorElement.element("matches");

				String containsText = containsElement.getText();
				String matchesText = matchesElement.getText();

				if (Validator.isNotNull(containsText) &&
					Validator.isNotNull(matchesText)) {

					if (line.contains(containsText) &&
						line.matches(matchesText)) {

						return true;
					}
				}
				else if (Validator.isNotNull(containsText)) {
					if (line.contains(containsText)) {
						return true;
					}
				}
				else if (Validator.isNotNull(matchesText)) {
					if (line.matches(matchesText)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public static void pause(String waitTime) throws Exception {
		Thread.sleep(GetterUtil.getInteger(waitTime));
	}

	public static void printJavaProcessStacktrace() throws Exception {
		if (Validator.isNull(PropsValues.PRINT_JAVA_PROCESS_ON_FAIL)) {
			return;
		}

		String line = null;
		String pid = null;

		BufferedReader bufferedReader = _execute("jps");

		while ((line = bufferedReader.readLine()) != null) {
			System.out.println(line);

			if (line.contains(PropsValues.PRINT_JAVA_PROCESS_ON_FAIL)) {
				pid = line.substring(0, line.indexOf(" "));

				System.out.println(
					PropsValues.PRINT_JAVA_PROCESS_ON_FAIL + " PID: " + pid);
			}
		}

		if (Validator.isNotNull(pid)) {
			bufferedReader = _execute("jstack -l " + pid);

			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}
		}
	}

	public static void selectFieldText() {
		Keyboard keyboard = new DesktopKeyboard();

		keyboard.keyDown(KeyEvent.VK_CONTROL);

		keyboard.type("a");

		keyboard.keyUp(KeyEvent.VK_CONTROL);
	}

	public static void typeScreen(String value) {
		Keyboard keyboard = new DesktopKeyboard();

		keyboard.type(value);
	}

	public static void writePoshiWarnings() throws Exception {
		StringBuilder sb = new StringBuilder();

		if (!_javaScriptExceptions.isEmpty()) {
			for (Exception exception : _javaScriptExceptions) {
				sb.append("<value><![CDATA[");
				sb.append(exception.getMessage());
				sb.append("]]></value>\n");
			}
		}

		if (!_liferayExceptions.isEmpty()) {
			for (Exception exception : _liferayExceptions) {
				sb.append("<value><![CDATA[");
				sb.append(exception.getMessage());
				sb.append("]]></value>\n");
			}
		}

		FileUtil.write(
			PropsValues.TEST_POSHI_WARNINGS_FILE_NAME, sb.toString());
	}

	private static BufferedReader _execute(String command) throws Exception {
		String[] runCommand;

		if (!OSDetector.isWindows()) {
			runCommand = new String[] {"/bin/bash", "-c", command};
		}
		else {
			runCommand = new String[] {"cmd", "/c", command};
		}

		Runtime runtime = Runtime.getRuntime();

		Process process = runtime.exec(runCommand);

		InputStreamReader inputStreamReader = new InputStreamReader(
			process.getInputStream());

		return new BufferedReader(inputStreamReader);
	}

	private static final List<String> _errorTimestamps = new ArrayList<>();
	private static final List<Exception> _javaScriptExceptions =
		new ArrayList<>();
	private static final List<Exception> _liferayExceptions = new ArrayList<>();

}