/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.release.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.net.URI;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.Properties;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class ResourceUtilTest extends ResourceUtil {

	@BeforeClass
	public static void setUpClass() throws Exception {
		Path tempDirectoryPath = Files.createTempDirectory("ResourceUtilTest_");

		_tempDirectory = tempDirectoryPath.toFile();

		_tempDirectory.deleteOnExit();

		_newFile = new File(_tempDirectory, "newFile.txt");

		Files.createFile(_newFile.toPath());

		_nontexistentFile = new File(_tempDirectory, "nonexistentFile.txt");

		_oldFile = new File(_tempDirectory, "oldFile.txt");

		Files.createFile(_oldFile.toPath());

		Instant instant = Instant.now();

		Files.setLastModifiedTime(
			_oldFile.toPath(),
			FileTime.from(instant.minus(_OLD_FILE_AGE, ChronoUnit.DAYS)));
	}

	@Test
	public void testGetClassLoaderResolver() throws Exception {
		_testResolve(
			getClassLoaderResolver(
				"/com/liferay/release/util/dependencies/foo.txt"));
		_testResolve(
			getClassLoaderResolver(
				ResourceUtilTest.class, "dependencies/foo.txt"));

		_testResolveThrows(
			exception -> Assert.assertEquals(
				"Unable to get resource from class path: nonexistent",
				exception.getMessage()),
			NullPointerException.class, getClassLoaderResolver("nonexistent"));
	}

	@Test
	public void testGetLocalFileResolver() throws Exception {
		int maxAge = _OLD_FILE_AGE - 1;

		_testResolve(getLocalFileResolver(_newFile));
		_testResolve(getLocalFileResolver(_newFile, maxAge, ChronoUnit.DAYS));
		_testResolve(getLocalFileResolver(_oldFile));

		_testResolveThrows(
			exception -> Assert.assertEquals(
				String.format(
					"Unable to get resource from local file: %s",
					_nontexistentFile.getAbsolutePath()),
				exception.getMessage()),
			FileNotFoundException.class,
			getLocalFileResolver(_nontexistentFile));
		_testResolveThrows(
			exception -> Assert.assertEquals(
				String.format(
					"Cached file %s is older than max age of 2 Days",
					_oldFile.getAbsolutePath()),
				exception.getMessage()),
			Exception.class,
			getLocalFileResolver(_oldFile, maxAge, ChronoUnit.DAYS));
	}

	@Test
	public void testGetURIResolver() throws Exception {
		_testResolve(getURIResolver(_tempDirectory, _newFile.toURI()));
		_testResolve(getURIResolver(_tempDirectory, new URI(_VALID_URL)));
		_testResolve(
			getURIResolver(_tempDirectory, 10 * 1000, new URI(_VALID_URL)));

		String invalidDomain = "invalid.releases.liferay.com";

		String invalidURL = "https://" + invalidDomain;

		_testResolveThrows(
			exception -> {
				String message = exception.getMessage();

				Assert.assertTrue(
					message.startsWith(
						String.format(
							"Unable to get resource from URL %s: %s",
							invalidURL, invalidDomain)));
				Assert.assertTrue(message.endsWith("not known"));
			},
			Exception.class,
			getURIResolver(_tempDirectory, new URI(invalidURL)));

		_testResolveThrows(
			exception -> {
				String message = exception.getMessage();

				Assert.assertTrue(
					message.startsWith(
						"Unable to get resource from URL " + _VALID_URL));
				Assert.assertTrue(message.contains("timed out"));
			},
			Exception.class,
			getURIResolver(_tempDirectory, 1, new URI(_VALID_URL)));
	}

	@Test
	public void testGetURLResolver() throws Exception {
		URI uri = _newFile.toURI();

		_testResolve(getURLResolver(_tempDirectory, uri.toString()));

		_testResolve(getURLResolver(_tempDirectory, _VALID_URL));
	}

	@Test
	public void testReadJSON() {
		Model model = readJSON(
			Model.class, _nullResolver,
			getClassLoaderResolver("dependencies/read-json.json"));

		Assert.assertEquals("bar", model.getFoo());

		Assert.assertNull(readJSON(Model.class, _nullResolver));

		try {
			readJSON(
				Model.class,
				getClassLoaderResolver("dependencies/read-json-bad.json"));

			Assert.fail();
		}
		catch (RuntimeException runtimeException) {
			Throwable throwable = runtimeException.getCause();

			Assert.assertEquals(JsonParseException.class, throwable.getClass());
		}
	}

	@Test
	public void testReadProperties() {
		Properties properties = readProperties(
			_nullResolver,
			getClassLoaderResolver("dependencies/read-properties.properties"));

		Assert.assertEquals("bar", properties.getProperty("foo"));

		Assert.assertNull(readProperties(_nullResolver));
	}

	@Test
	public void testReadString() {
		String string = readString(
			_nullResolver,
			getClassLoaderResolver("dependencies/read-string.txt"));

		Assert.assertEquals("foo\nbar", string);

		Assert.assertNull(readProperties(_nullResolver));
	}

	private void _testResolve(Resolver resolver) throws Exception {
		try (InputStream inputStream = resolver.resolve()) {
			Assert.assertNotNull(inputStream);
		}
	}

	private void _testResolveThrows(
		Consumer<Exception> consumer, Class<?> exceptionClass,
		Resolver resolver) {

		try (InputStream ignoredInputStream = resolver.resolve()) {
			resolver.resolve();

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertEquals(exceptionClass, exception.getClass());

			if (consumer != null) {
				consumer.accept(exception);
			}
		}
	}

	private static final int _OLD_FILE_AGE = 3;

	private static final String _VALID_URL =
		"https://releases.liferay.com/releases.json";

	private static File _newFile;
	private static File _nontexistentFile;
	private static File _oldFile;
	private static File _tempDirectory;

	private final Resolver _nullResolver = () -> null;

	private static class Model {

		public String getFoo() {
			return _foo;
		}

		@JsonProperty("foo")
		private String _foo;

	}

}