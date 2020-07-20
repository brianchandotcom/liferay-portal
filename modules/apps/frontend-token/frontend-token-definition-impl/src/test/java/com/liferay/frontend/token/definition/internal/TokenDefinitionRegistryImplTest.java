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

package com.liferay.frontend.token.definition.internal;

import com.liferay.frontend.token.definition.TokenDefinition;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortalImpl;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.Bundle;

/**
 * @author Iván Zaera
 */
public class TokenDefinitionRegistryImplTest {

	@Test
	public void testGetServletContextName() {
		TokenDefinitionRegistryImpl tokenDefinitionRegistryImpl =
			new TokenDefinitionRegistryImpl();

		Bundle bundle = Mockito.mock(Bundle.class);

		HashMapDictionary<String, String> headers = new HashMapDictionary<>();

		headers.put("Web-ContextPath", "/my-theme");

		Mockito.when(
			bundle.getHeaders(Mockito.anyString())
		).thenReturn(
			headers
		);

		Assert.assertEquals(
			"my-theme",
			tokenDefinitionRegistryImpl.getServletContextName(bundle));
	}

	@Test
	public void testGetThemeIdWithNontheme() {
		TokenDefinitionRegistryImpl tokenDefinitionRegistryImpl =
			new TokenDefinitionRegistryImpl();

		Bundle bundle = Mockito.mock(Bundle.class);

		Assert.assertNull(tokenDefinitionRegistryImpl.getThemeId(bundle));
	}

	@Test
	public void testGetThemeIdWithoutServletContext() {
		TokenDefinitionRegistryImpl tokenDefinitionRegistryImpl =
			new TokenDefinitionRegistryImpl();

		Bundle bundle = Mockito.mock(Bundle.class);

		Mockito.when(
			bundle.getHeaders(Mockito.anyString())
		).thenReturn(
			new HashMapDictionary<>()
		);

		Mockito.when(
			bundle.getEntry("WEB-INF/liferay-look-and-feel.xml")
		).thenReturn(
			_liferayLookAndFeelXMLURL
		);

		tokenDefinitionRegistryImpl.portal = new PortalImpl();

		Assert.assertEquals(
			"classic", tokenDefinitionRegistryImpl.getThemeId(bundle));
	}

	@Test
	public void testGetThemeIdWithServletContext() {
		TokenDefinitionRegistryImpl tokenDefinitionRegistryImpl =
			new TokenDefinitionRegistryImpl();

		Bundle bundle = Mockito.mock(Bundle.class);

		HashMapDictionary<String, String> headers = new HashMapDictionary<>();

		headers.put("Web-ContextPath", "/classic-theme");

		Mockito.when(
			bundle.getHeaders(Mockito.anyString())
		).thenReturn(
			headers
		);

		Mockito.when(
			bundle.getEntry("WEB-INF/liferay-look-and-feel.xml")
		).thenReturn(
			_liferayLookAndFeelXMLURL
		);

		tokenDefinitionRegistryImpl.portal = new PortalImpl();

		Assert.assertEquals(
			"classic_WAR_classictheme",
			tokenDefinitionRegistryImpl.getThemeId(bundle));
	}

	@Test
	public void testGetTokenDefinitionJSON() throws IOException {
		TokenDefinitionRegistryImpl tokenDefinitionRegistryImpl =
			new TokenDefinitionRegistryImpl();

		Bundle bundle = Mockito.mock(Bundle.class);

		Mockito.when(
			bundle.getHeaders(Mockito.anyString())
		).thenReturn(
			new HashMapDictionary<>()
		);

		Mockito.when(
			bundle.getEntry("META-INF/token-definition.json")
		).thenReturn(
			_tokenDefinitionJSONURL
		);

		TokenDefinition tokenDefinition =
			tokenDefinitionRegistryImpl.getTokenDefinitionImpl(bundle);

		try (InputStream inputStream = _tokenDefinitionJSONURL.openStream()) {
			Assert.assertEquals(
				StringUtil.read(inputStream),
				tokenDefinition.getTokenDefinitionJSON());
		}
	}

	@Test
	public void testGetTokenDefinitionPathWithManifestHeader() {
		TokenDefinitionRegistryImpl tokenDefinitionRegistryImpl =
			new TokenDefinitionRegistryImpl();

		Bundle bundle = Mockito.mock(Bundle.class);

		Dictionary<String, String> headers = new HashMapDictionary<>();

		headers.put("Token-Definition-Path", "WEB-INF/token-definition.json");

		Mockito.when(
			bundle.getHeaders(Mockito.anyString())
		).thenReturn(
			headers
		);

		String tokenDefinitionPath =
			tokenDefinitionRegistryImpl.getTokenDefinitionPath(bundle);

		Assert.assertEquals(
			"WEB-INF/token-definition.json", tokenDefinitionPath);
	}

	@Test
	public void testGetTokenDefinitionPathWithoutManifestHeader() {
		TokenDefinitionRegistryImpl tokenDefinitionRegistryImpl =
			new TokenDefinitionRegistryImpl();

		Bundle bundle = Mockito.mock(Bundle.class);

		Mockito.when(
			bundle.getHeaders(Mockito.anyString())
		).thenReturn(
			new HashMapDictionary<>()
		);

		String tokenDefinitionPath =
			tokenDefinitionRegistryImpl.getTokenDefinitionPath(bundle);

		Assert.assertEquals(
			"META-INF/token-definition.json", tokenDefinitionPath);
	}

	private static final URL _liferayLookAndFeelXMLURL =
		TokenDefinitionRegistryImplTest.class.getResource(
			"dependencies/liferay-look-and-feel.xml");
	private static final URL _tokenDefinitionJSONURL =
		TokenDefinitionRegistryImplTest.class.getResource(
			"dependencies/token-definition.json");

}