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

package com.liferay.portal.kernel.settings;

import com.liferay.portal.kernel.util.FileUtil;
//import com.liferay.portal.util.FileImpl;

import java.io.File;
import java.io.IOException;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Iván Zaera
 */
public class LocationVariableResolverTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new MockFile());

		_mockResourceManager = new MockResourceManager(
			"En un lugar de la Mancha...");

		_mockSettingsFactory = mock(SettingsFactory.class);

		_locationVariableResolver = new LocationVariableResolver(
			_mockResourceManager, _mockSettingsFactory);
	}

	@Test
	public void testIsLocationVariableWithNonVariable() {
		Assert.assertFalse(
			_locationVariableResolver.isLocationVariable(
				"this is obviously not a location variable"));
	}

	@Test
	public void testIsLocationVariableWithVariable() {
		Assert.assertTrue(
			_locationVariableResolver.isLocationVariable(
				"${protocol:location}"));
	}

	@Test
	public void testResolveVariableWithFile() throws IOException {
		String expectedValue = "En un lugar de la Mancha...";

		File file = File.createTempFile("testResolveVariableForFile", "txt");

		file.deleteOnExit();

		FileUtil.write(file, expectedValue.getBytes());

		String value = _locationVariableResolver.resolve(
			"${file://" + file.getAbsolutePath() + "}");

		Assert.assertEquals(expectedValue, value);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testResolveVariableWithInvalidFile() {
		_locationVariableResolver.resolve(
			"${file:bad_file_uri_without_slashes.txt}");
	}

	@Test
	public void testResolveVariableWithResource() {
		String value = _locationVariableResolver.resolve(
			"${resource:template.ftl}");

		Assert.assertEquals(_mockResourceManager.getContent(), value);

		List<String> requestedLocations =
			_mockResourceManager.getRequestedLocations();

		Assert.assertEquals(
			requestedLocations.toString(), 1, requestedLocations.size());
		Assert.assertEquals("template.ftl", requestedLocations.get(0));
	}

	@Test
	public void testResolveVariableWithServerProperty() {
		Settings mockSettings = mock(Settings.class);

		final String expectedValue = "test@liferay.com";

		when(
			mockSettings.getValue(
				Matchers.eq("admin.email.from.address"), Matchers.anyString())
		).thenReturn(
			expectedValue
		);

		when(
			_mockSettingsFactory.getServerSettings("com.liferay.portal")
		).thenReturn(
			mockSettings
		);

		Assert.assertEquals(
			expectedValue,
			_locationVariableResolver.resolve(
				"${server-property://com.liferay.portal" +
					"/admin.email.from.address}"));
	}

	private LocationVariableResolver _locationVariableResolver;
	private MockResourceManager _mockResourceManager;
	private SettingsFactory _mockSettingsFactory;

}