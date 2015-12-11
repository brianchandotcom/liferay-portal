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

import java.net.URI;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;

/**
 * @author Peter Yoo
 */
public abstract class BaseJenkinsResultsParserTestCase {

	protected void assertSample(File caseDir) throws Exception {
		System.out.print("Asserting sample " + caseDir.getName() + ": ");

		File expectedMessageFile = new File(caseDir, "expected_message.html");

		String expectedMessage = read(expectedMessageFile);

		String actualMessage = getMessage(toURLString(caseDir));

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

		if (sampleDir.exists()) {
			return;
		}

		System.out.println("Downloading sample " + sampleKey);

		try {
			downloadSample(sampleDir, url);

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

	protected abstract String getMessage(String urlString) throws Exception;

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

		return urlString.replace(System.getProperty("user.dir"), "${user.dir}");
	}

	protected void writeExpectedMessage(File sampleDir) throws Exception {
		File expectedMessageFile = new File(sampleDir, "expected_message.html");
		String expectedMessage = getMessage(toURLString(sampleDir));

		JenkinsResultsParserUtil.write(expectedMessageFile, expectedMessage);
	}

	protected File dependenciesDir = new File(
		"src/test/resources/dependencies/" + getSimpleClassName());

}