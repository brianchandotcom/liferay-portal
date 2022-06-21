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

package com.liferay.portal.osgi.web.wab.generator.internal.artifact;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.File;
import java.io.IOException;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import java.util.Objects;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import org.osgi.framework.Constants;

/**
 * @author Gregory Amerson
 */
public class ArtifactURLUtilTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static TemporaryFolder temporaryFolder = new TemporaryFolder();

	@BeforeClass
	public static void setUpClass() {
		URL.setURLStreamHandlerFactory(
			protocol -> {
				if (!Objects.equals(protocol, "webbundle")) {
					return null;
				}

				return new URLStreamHandler() {

					protected URLConnection openConnection(URL url)
						throws IOException {

						return new URLConnection(url) {

							public void connect() throws IOException {
							}

						};
					}

				};
			});
	}

	@Test
	public void testClientExtensionURLWithoutVersionContainsExpectedSymbolicName()
		throws Exception {

		File file = temporaryFolder.newFile("clientextension.zip");

		URI uri = file.toURI();

		URL url = ArtifactURLUtil.transform(uri.toURL());

		String query = url.getQuery();

		Assert.assertTrue(
			query.contains(Constants.BUNDLE_SYMBOLICNAME + "=clientextension"));
	}

	@Test
	public void testClientExtensionURLWithVersionContainsExpectedSymbolicName()
		throws Exception {

		File file = temporaryFolder.newFile("clientextension-1.0.0.zip");

		URI uri = file.toURI();

		URL url = ArtifactURLUtil.transform(uri.toURL());

		String query = url.getQuery();

		Assert.assertFalse(
			query.contains(
				Constants.BUNDLE_SYMBOLICNAME + "=clientextension-1.0.0"));
		Assert.assertTrue(
			query.contains(Constants.BUNDLE_SYMBOLICNAME + "=clientextension"));
	}

	@Test
	public void testWarURLContainsExpectedSymbolicName() throws Exception {
		String uriString = _getURIString(
			"dependencies/classic-theme.autodeployed.war");

		URI uri = new URI(uriString);

		URL url = ArtifactURLUtil.transform(uri.toURL());

		String query = url.getQuery();

		Assert.assertTrue(
			query.contains(
				Constants.BUNDLE_SYMBOLICNAME + "=classic-theme.autodeployed"));
	}

	private String _getURIString(String fileName) throws Exception {
		URL url = ArtifactURLUtilTest.class.getResource(fileName);

		URI uri = url.toURI();

		return uri.toASCIIString();
	}

}