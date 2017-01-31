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

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import java.net.URI;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import org.junit.Assert;

/**
 * @author Peter Yoo
 */
public abstract class BaseJenkinsResultsParserTestCase {

	protected void assertSample(File sampleDir) throws Exception {
		System.out.print("Asserting sample " + sampleDir.getName() + ": ");

		File expectedMessageFile = new File(sampleDir, "expected_message.html");

		String expectedMessage = read(expectedMessageFile);

		String actualMessage = fixMessage(getMessage(sampleDir));

		boolean value = expectedMessage.equals(actualMessage);

		if (value) {
			System.out.println(" PASSED");
		}
		else {
			System.out.println(" FAILED");
			System.out.println("\nActual message: \n" + actualMessage);
			System.out.println("\nExpected message: \n" + expectedMessage);
		}

		Assert.assertTrue(value);
	}

	protected void assertSamples() throws Exception {
		File[] files = dependenciesDir.listFiles();

		for (File file : files) {
			assertSample(file);
		}
	}

	protected void deleteFile(File file) {
		if (!file.exists()) {
			return;
		}

		if (file.isFile()) {
			file.delete();
		}
		else {
			File[] files = file.listFiles();

			for (File childFile : files) {
				deleteFile(childFile);
			}

			file.delete();
		}
	}

	protected void deleteFile(String fileName) {
		deleteFile(new File(fileName));
	}

	protected abstract void downloadSample(File sampleDir, URL url)
		throws Exception;

	protected void downloadSample(String sampleKey, URL url) throws Exception {
		String sampleDirName = dependenciesDir.getPath() + "/" + sampleKey;

		File sampleDir = new File(sampleDirName);

		File expectedMessageFile = new File(sampleDir, "expected_message.html");

		if (expectedMessageFile.exists()) {
			return;
		}

		try {
			if (!sampleDir.exists()) {
				System.out.println("Downloading sample " + sampleKey);

				downloadSample(sampleDir, url);
			}

			writeExpectedMessage(sampleDir);
		}
		catch (IOException ioe) {
			deleteFile(sampleDir);

			throw ioe;
		}
	}

	protected void downloadSampleURL(File dir, URL url, String urlSuffix)
		throws Exception {

		String urlString = url + urlSuffix;

		if (urlString.endsWith("json")) {
			urlString += "?pretty";
		}

		urlSuffix = JenkinsResultsParserUtil.fixFileName(urlSuffix);

		JenkinsResultsParserUtil.write(
			new File(dir, urlSuffix),
			JenkinsResultsParserUtil.toString(
				JenkinsResultsParserUtil.getLocalURL(urlString)));
	}

	protected String fixMessage(String message) {
		if (message.contains(JenkinsResultsParserUtil.DEPENDENCIES_URL_FILE)) {
			message = message.replace(
				JenkinsResultsParserUtil.DEPENDENCIES_URL_FILE,
				"${dependencies.url}");
		}

		if (message.contains(JenkinsResultsParserUtil.DEPENDENCIES_URL_HTTP)) {
			message = message.replace(
				JenkinsResultsParserUtil.DEPENDENCIES_URL_HTTP,
				"${dependencies.url}");
		}

		return message.replaceAll("[^\\S\\r\\n]+\n", "\n");
	}

	protected String formatXML(String xml)
		throws DocumentException, IOException {

		SAXReader saxReader = new SAXReader();

		for (int i = 0; i < _XML_REPLACEMENTS.length; i++) {
			xml = xml.replace(_XML_REPLACEMENTS[i][0], _XML_REPLACEMENTS[i][1]);
		}

		Document document = null;

		try {
			document = saxReader.read(new StringReader(xml));
		}
		catch (DocumentException de) {
			DocumentException newDE = new DocumentException(
				de.getMessage() + "\n" + xml);

			newDE.setStackTrace(de.getStackTrace());

			throw newDE;
		}

		String formattedXML = Dom4JUtil.format(document.getRootElement());

		for (int i = 0; i < _XML_REPLACEMENTS.length; i++) {
			formattedXML = formattedXML.replace(
				_XML_REPLACEMENTS[i][1], _XML_REPLACEMENTS[i][0]);
		}

		return formattedXML;
	}

	protected abstract String getMessage(File sampleDir) throws Exception;

	protected String getSimpleClassName() {
		Class<?> clazz = getClass();

		return clazz.getSimpleName();
	}

	protected String read(File file) throws IOException {
		return new String(Files.readAllBytes(Paths.get(file.toURI())));
	}

	protected String read(File dir, String fileName) throws IOException {
		return read(new File(dir, fileName));
	}

	protected String replaceToken(String string, String token, String value) {
		if (string == null) {
			return string;
		}

		return string.replace("${" + token + "}", value);
	}

	protected String toURLString(File file) throws Exception {
		URI uri = file.toURI();

		URL url = uri.toURL();

		String urlString = url.toString();

		String path = dependenciesDir.getPath();

		int x =
			path.indexOf("src/test/resources/dependencies/") +
				"src/test/resources/dependencies/".length();

		path = path.substring(x);

		return urlString.replace(
			"file:" + dependenciesDir.getAbsolutePath(),
			"${dependencies.url}/" + path);
	}

	protected void writeExpectedMessage(File sampleDir) throws Exception {
		File expectedMessageFile = new File(sampleDir, "expected_message.html");

		String expectedMessage = fixMessage(getMessage(sampleDir));

		JenkinsResultsParserUtil.write(expectedMessageFile, expectedMessage);
	}

	protected File dependenciesDir = new File(
		"src/test/resources/dependencies/" + getSimpleClassName());

	private static final String[][] _XML_REPLACEMENTS = new String[][] {
		{"<pre>", "<pre><![CDATA["}, {"</pre>", "]]></pre>"},
		{"&raquo;", "[raquo]"}
	};

}