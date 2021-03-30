/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
 * <p>
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */
package com.liferay.portal.store.azure.test;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Josef Sustacek
 */
public class TestFiles {
	private TestFiles() {}

	public static final TestFile SMALL_PDF_FILE = new TestFile("fw4.pdf");


	public static class TestFile {
		private final URL url;
		private final long sizeBytes;

		public InputStream openStream() throws UncheckedIOException {
			try {
				return url.openStream();
			}
			catch (IOException ioe) {
				throw new UncheckedIOException("Cannot load test file " + url, ioe);
			}
		}

		public TestFile(String classpathPath) {
			this.url = _getResourceMandatory(Objects.requireNonNull(classpathPath));

			try {
				this.sizeBytes = IOUtils.toByteArray(url).length;
			} catch (IOException ioe) {
				throw new UncheckedIOException("Cannot load test file " + url, ioe);
			}
		}

		/**
		 * If resource of given path is found (if relative paath is given, then relative
		 * to the current class), return URL of that resource. If not found, raise an exception.
		 *
		 * This way, missing resource won't be silently ignored, but reported right away.
		 *
		 * @param path
		 * @return
		 */
		private static URL _getResourceMandatory(String path) {
			Class contextClass = TestFiles.class;

			return Optional.ofNullable(
				contextClass.getResource(
					Objects.requireNonNull(path, "path to resource must be provided")))
				.orElseThrow(() -> new RuntimeException(
					"Cannot find test file on classpath for " + contextClass.getName() + ": " + path));
		}

		public long getSizeBytes() {
			return sizeBytes;
		}
	}
}
