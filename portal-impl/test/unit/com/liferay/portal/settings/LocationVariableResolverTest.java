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

package com.liferay.portal.settings;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.FileImpl;

import java.io.File;
import java.io.IOException;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Ivan Zaera
 */
public class LocationVariableResolverTest {

	@Before
	public void setUp() {
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());
	}

	@Test
	public void testIsLocationVariableWithFile() {
		LocationVariableResolver locationVariableResolver =
			new LocationVariableResolver(null);

		Assert.assertTrue(
			locationVariableResolver.isLocationVariable(
				"${file:///template.ftl}"));
	}

	@Test
	public void testIsLocationVariableWithNonVariable() {
		LocationVariableResolver locationVariableResolver =
			new LocationVariableResolver(null);

		Assert.assertFalse(
			locationVariableResolver.isLocationVariable(
				"this is obviously not a location variable"));
	}

	@Test
	public void testIsLocationVariableWithResource() {
		LocationVariableResolver locationVariableResolver =
			new LocationVariableResolver(null);

		Assert.assertTrue(
			locationVariableResolver.isLocationVariable(
				"${resource:template.ftl}"));
	}

	@Test
	public void testResolveVariableWithFile() throws IOException {
		String expectedValue = "En un lugar de la Mancha...";

		File file = File.createTempFile("testResolveVariableForFile", "txt");

		file.deleteOnExit();

		FileUtil.write(file, expectedValue.getBytes());

		LocationVariableResolver locationVariableResolver =
			new LocationVariableResolver(null);

		String value = locationVariableResolver.resolve(
			"${file://" + file.getAbsolutePath() + "}");

		Assert.assertEquals(expectedValue, value);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testResolveVariableWithInvalidFile() {
		LocationVariableResolver locationVariableResolver =
			new LocationVariableResolver(null);

		locationVariableResolver.resolve(
			"${file:bad_file_uri_without_slashes.txt}");
	}

	@Test
	public void testResolveVariableWithResource() {
		String expectedValue = "Lorem ipsum";

		MockResourceManager mockResourceManager = new MockResourceManager(
			expectedValue);

		LocationVariableResolver locationVariableResolver =
			new LocationVariableResolver(mockResourceManager);

		String value = locationVariableResolver.resolve(
			"${resource:template.ftl}");

		Assert.assertEquals(expectedValue, value);

		List<String> requestedLocations =
			mockResourceManager.getRequestedLocations();

		Assert.assertEquals(1, requestedLocations.size());
		Assert.assertEquals("template.ftl", requestedLocations.get(0));
	}

}