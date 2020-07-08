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

package com.liferay.frontend.theme.token.definition.internal;

import com.liferay.portal.kernel.util.HashMapDictionary;

import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.Bundle;

/**
 * @author Iván Zaera Avellón
 */
public class ThemeBundleInspectorTest {

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsWhenNotAThemeBundle() {
		Bundle bundle = Mockito.mock(Bundle.class);

		Mockito.when(
			bundle.getSymbolicName()
		).thenReturn(
			"not-a-theme-bundle.jar"
		);

		new ThemeBundleInspector(bundle);
	}

	@Test
	public void testGetTokenDefinition() throws Exception {
		Bundle bundle = Mockito.mock(Bundle.class);

		Mockito.when(
			bundle.getHeaders(Mockito.anyString())
		).thenReturn(
			new HashMapDictionary<>()
		);

		Mockito.when(
			bundle.getEntry("WEB-INF/liferay-look-and-feel.xml")
		).thenReturn(
			new URL("file:///liferay-look-and-feel.xml")
		);

		Mockito.when(
			bundle.getEntry("WEB-INF/token-definition.json")
		).thenReturn(
			_tokenDefinitionJsonURL
		);

		ThemeBundleInspector themeBundleInspector = new ThemeBundleInspector(
			bundle);

		Assert.assertEquals("{}", themeBundleInspector.getTokenDefinition());
	}

	private static final URL _tokenDefinitionJsonURL =
		ThemeBundleInspectorTest.class.getResource("token-definition.json");

}