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

import com.liferay.poshi.core.selenium.LiferaySelenium;
import com.liferay.poshi.core.util.FileUtil;
import com.liferay.poshi.core.util.GetterUtil;
import com.liferay.poshi.core.util.OSDetector;
import com.liferay.poshi.core.util.PropsValues;
import com.liferay.poshi.core.util.Validator;
import com.liferay.poshi.runner.exception.JavaScriptException;
import com.liferay.poshi.runner.exception.LiferayLogException;
import com.liferay.poshi.runner.exception.PoshiRunnerWarningException;
import com.liferay.poshi.runner.util.EmailCommands;

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

import java.net.URISyntaxException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import junit.framework.TestCase;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import org.openqa.selenium.By;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.desktop.DesktopKeyboard;

/**
 * @author Brian Wing Shun Chan
 */
@SuppressWarnings("deprecation")
public class LiferaySeleniumUtil {

	public static void assertConsoleErrors() throws Exception {
		if (!PropsValues.TEST_ASSERT_CONSOLE_ERRORS) {
			return;
		}

		String content = getTestConsoleLogFileContent();

		if (content.equals("")) {
			return;
		}

		LiferayLogException liferayLogException = null;

		SAXReader saxReader = new SAXReader();

		content = "<log4j>" + content + "</log4j>";
		content = content.replaceAll("log4j:", "");

		InputStream inputStream = new ByteArrayInputStream(
			content.getBytes("UTF-8"));

		Document document = saxReader.read(inputStream);

		Element rootElement = document.getRootElement();

		List<Element> eventElements = rootElement.elements("event");

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

				if (liferayLogException == null) {
					liferayLogException = new LiferayLogException(
						sb.toString());
				}
				else {
					PoshiRunnerWarningException.addException(
						new LiferayLogException(sb.toString()));
				}
			}
		}

		if (liferayLogException != null) {
			throw liferayLogException;
		}
	}

	public static void assertLocation(
			LiferaySelenium liferaySelenium, String pattern)
		throws Exception {

		TestCase.assertEquals(pattern, liferaySelenium.getLocation());
	}

	public static void assertNoJavaScriptExceptions() throws Exception {
		List<PoshiRunnerWarningException> javaScriptExceptions =
			new ArrayList<>();

		for (PoshiRunnerWarningException poshiRunnerWarningException :
				PoshiRunnerWarningException.getPoshiRunnerWarningExceptions()) {

			if (poshiRunnerWarningException instanceof JavaScriptException) {
				javaScriptExceptions.add(poshiRunnerWarningException);
			}
		}

		if (!javaScriptExceptions.isEmpty()) {
			throw new Exception(
				_getExceptionsMessage(javaScriptExceptions, "JavaScript"));
		}
	}

	public static void assertNoLiferayExceptions() throws Exception {
		List<PoshiRunnerWarningException> liferayLogExceptions =
			new ArrayList<>();

		for (PoshiRunnerWarningException poshiRunnerWarningException :
				PoshiRunnerWarningException.getPoshiRunnerWarningExceptions()) {

			if (poshiRunnerWarningException instanceof JavaScriptException) {
				liferayLogExceptions.add(poshiRunnerWarningException);
			}
		}

		if (!liferayLogExceptions.isEmpty()) {
			throw new Exception(
				_getExceptionsMessage(liferayLogExceptions, "Liferay"));
		}
	}

	public static void assertNoPoshiWarnings() throws Exception {
		if (!PropsValues.TEST_ASSERT_WARNING_EXCEPTIONS) {
			return;
		}

		List<PoshiRunnerWarningException> javaScriptExceptions =
			new ArrayList<>();
		List<PoshiRunnerWarningException> liferayLogExceptions =
			new ArrayList<>();

		for (PoshiRunnerWarningException poshiRunnerWarningException :
				PoshiRunnerWarningException.getPoshiRunnerWarningExceptions()) {

			if (poshiRunnerWarningException instanceof JavaScriptException) {
				javaScriptExceptions.add(poshiRunnerWarningException);

				continue;
			}

			if (poshiRunnerWarningException instanceof LiferayLogException) {
				liferayLogExceptions.add(poshiRunnerWarningException);
			}
		}

		StringBuilder sb = new StringBuilder();

		if (!javaScriptExceptions.isEmpty()) {
			sb.append(
				_getExceptionsMessage(javaScriptExceptions, "JavaScript"));
		}

		if (!liferayLogExceptions.isEmpty()) {
			sb.append(_getExceptionsMessage(liferayLogExceptions, "Liferay"));
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

	public static By getBy(String locator) {
		if (locator.startsWith("//")) {
			return By.xpath(locator);
		}
		else if (locator.startsWith("class=")) {
			locator = locator.substring(6);

			return By.className(locator);
		}
		else if (locator.startsWith("css=")) {
			locator = locator.substring(4);

			if (locator.contains(">>>")) {
				return LiferayByUtil.cssSelectorWithShadowRoot(locator);
			}

			return By.cssSelector(locator);
		}
		else if (locator.startsWith("link=")) {
			locator = locator.substring(5);

			return By.linkText(locator);
		}
		else if (locator.startsWith("name=")) {
			locator = locator.substring(5);

			return By.name(locator);
		}
		else if (locator.startsWith("tag=")) {
			locator = locator.substring(4);

			return By.tagName(locator);
		}
		else if (locator.startsWith("xpath=") || locator.startsWith("xPath=")) {
			locator = locator.substring(6);

			return By.xpath(locator);
		}
		else {
			return By.id(locator);
		}
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

		if (Validator.isNotNull(PropsValues.TEST_DIRS)) {
			Collections.addAll(baseDirNames, PropsValues.TEST_DIRS);
		}

		if (Validator.isNotNull(PropsValues.TEST_SUPPORT_DIRS)) {
			Collections.addAll(baseDirNames, PropsValues.TEST_SUPPORT_DIRS);
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

		String baseDirName = PropsValues.TEST_CONSOLE_LOG_FILE_NAME;

		int x = PropsValues.TEST_CONSOLE_LOG_FILE_NAME.lastIndexOf("/");

		if (x != -1) {
			baseDirName = PropsValues.TEST_CONSOLE_LOG_FILE_NAME.substring(
				0, x);
		}

		List<URL> urls = FileUtil.getIncludedResourceURLs(
			new String[] {PropsValues.TEST_CONSOLE_LOG_FILE_NAME}, baseDirName);

		try {
			urls.sort(
				(URL url1, URL url2) -> {
					try {
						File file1 = new File(url1.toURI());
						File file2 = new File(url2.toURI());

						Long file1LastModified = Long.valueOf(
							file1.lastModified());

						return file1LastModified.compareTo(
							Long.valueOf(file2.lastModified()));
					}
					catch (URISyntaxException uriSyntaxException) {
						throw new RuntimeException(uriSyntaxException);
					}
				});
		}
		catch (RuntimeException runtimeException) {
			throw new PoshiRunnerWarningException(
				"Unable to get console log file content. Please check log " +
					"file(s): " + PropsValues.TEST_CONSOLE_LOG_FILE_NAME,
				runtimeException);
		}

		StringBuilder sb = new StringBuilder();

		long consoleLogSize = 0;

		for (URL url : urls) {
			File file = new File(url.toURI());

			consoleLogSize = consoleLogSize + file.length();

			if (consoleLogSize > _BYTES_MAX_SIZE_CONSOLE_LOG) {
				String largeConsoleLogSizeMessage =
					"Console log " + PropsValues.TEST_CONSOLE_LOG_FILE_NAME +
						" exceeded " + _BYTES_MAX_SIZE_CONSOLE_LOG + " bytes";

				System.out.println(largeConsoleLogSizeMessage);

				throw new PoshiRunnerWarningException(
					largeConsoleLogSizeMessage);
			}

			sb.append(FileUtil.read(url));
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

		if ((Objects.equals(PropsValues.LIFERAY_PORTAL_BUNDLE, "6.2.10.1") ||
			 Objects.equals(PropsValues.LIFERAY_PORTAL_BUNDLE, "6.2.10.2") ||
			 Objects.equals(PropsValues.LIFERAY_PORTAL_BUNDLE, "6.2.10.3") ||
			 Objects.equals(PropsValues.LIFERAY_PORTAL_BUNDLE, "6.2.10.4") ||
			 Objects.equals(PropsValues.LIFERAY_PORTAL_BRANCH, "ee-6.2.10")) &&
			(line.contains(
				"com.liferay.portal.kernel.search.SearchException: " +
					"java.nio.channels.ClosedByInterruptException") ||
			 line.contains("org.apache.lucene.store.AlreadyClosedException"))) {

			return true;
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

	public static void pause(int duration) throws Exception {
		Thread.sleep(duration);
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

		for (PoshiRunnerWarningException poshiRunnerWarningException :
				PoshiRunnerWarningException.getPoshiRunnerWarningExceptions()) {

			sb.append("<value><![CDATA[");

			String message = poshiRunnerWarningException.getMessage();

			if (Validator.isNotNull(message) &&
				(message.length() > _CHARS_EXCEPTION_MESSAGE_SIZE_MAX)) {

				message = message.substring(
					0, _CHARS_EXCEPTION_MESSAGE_SIZE_MAX);
			}

			sb.append(message);

			sb.append("]]></value>\n");
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

	private static String _getExceptionsMessage(
		List<PoshiRunnerWarningException> poshiRunnerWarningExceptions,
		String exceptionType) {

		StringBuilder sb = new StringBuilder();

		if (!poshiRunnerWarningExceptions.isEmpty()) {
			sb.append("\n");
			sb.append("##\n");

			sb.append("## ");
			sb.append(poshiRunnerWarningExceptions.size());
			sb.append(" ");
			sb.append(exceptionType);
			sb.append(" Exception");

			if (poshiRunnerWarningExceptions.size() > 1) {
				sb.append("s were");
			}
			else {
				sb.append(" was");
			}

			sb.append(" thrown\n");

			sb.append("##\n");
			sb.append("\n");

			for (Exception exception : poshiRunnerWarningExceptions) {
				sb.append(exception.getMessage());

				sb.append("\n");
			}

			sb.append("\n");
		}

		return sb.toString();
	}

	private static final long _BYTES_MAX_SIZE_CONSOLE_LOG = 20 * 1024 * 1024;

	private static final int _CHARS_EXCEPTION_MESSAGE_SIZE_MAX = 2500;

	private static final List<String> _errorTimestamps = new ArrayList<>();

}