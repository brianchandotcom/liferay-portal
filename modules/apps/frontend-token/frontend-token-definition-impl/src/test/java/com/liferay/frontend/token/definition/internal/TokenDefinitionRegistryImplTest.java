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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.Bundle;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Iván Zaera
 */
public class TokenDefinitionRegistryImplTest {

	@Test
	public void testGetTokenDefinition() throws IOException {
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
			_tokenDefinitionJsonURL
		);

		TokenDefinition tokenDefinition =
			tokenDefinitionRegistryImpl.getTokenDefinition(bundle);

		try (InputStream inputStream = _tokenDefinitionJsonURL.openStream()) {
			Assert.assertEquals(
				StringUtil.read(inputStream),
				tokenDefinition.getRawDefinition());
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

	@Test
	public void testGetTokenDefinitions() {
		TokenDefinitionRegistryImpl tokenDefinitionRegistryImpl =
			new TokenDefinitionRegistryImpl();

		BundleTracker<AtomicReference<TokenDefinition>> bundleTracker =
			Mockito.mock(BundleTracker.class);

		TokenDefinition tokenDefinition1 = new TokenDefinitionImpl("1");
		TokenDefinition tokenDefinition2 = new TokenDefinitionImpl("2");

		Map<Bundle, AtomicReference<TokenDefinition>> map =
			HashMapBuilder.<Bundle, AtomicReference<TokenDefinition>>put(
				Mockito.mock(Bundle.class),
				new AtomicReference<>(tokenDefinition1)
			).put(
				Mockito.mock(Bundle.class), new AtomicReference<>(null)
			).put(
				Mockito.mock(Bundle.class),
				new AtomicReference<>(tokenDefinition2)
			).build();

		Mockito.when(
			bundleTracker.getTracked()
		).thenReturn(
			map
		);

		tokenDefinitionRegistryImpl.bundleTracker = bundleTracker;

		Collection<TokenDefinition> tokenDefinitions =
			tokenDefinitionRegistryImpl.getTokenDefinitions();

		Assert.assertEquals(
			"tokenDefinitions contains 2 items", 2, tokenDefinitions.size());

		Assert.assertTrue(
			"tokenDefinitions contains tokenDefinition1",
			tokenDefinitions.contains(tokenDefinition1));
		Assert.assertTrue(
			"tokenDefinitions contains tokenDefinition2",
			tokenDefinitions.contains(tokenDefinition2));
	}

	private static final URL _tokenDefinitionJsonURL =
		TokenDefinitionRegistryImplTest.class.getResource(
			"token-definition.json");

}