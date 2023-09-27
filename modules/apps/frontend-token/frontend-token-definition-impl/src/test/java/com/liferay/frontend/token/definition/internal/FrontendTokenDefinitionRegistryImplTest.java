/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.internal;

import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PortalImpl;

import java.io.InputStream;

import java.net.URL;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.Bundle;

/**
 * @author Iván Zaera
 */
public class FrontendTokenDefinitionRegistryImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetJSON() throws Exception {
		FrontendTokenDefinitionRegistryImpl
			frontendTokenDefinitionRegistryImpl =
				new FrontendTokenDefinitionRegistryImpl();

		frontendTokenDefinitionRegistryImpl.jsonFactory = new JSONFactoryImpl();

		Bundle bundle = Mockito.mock(Bundle.class);

		Mockito.when(
			bundle.getEntry("WEB-INF/frontend-token-definition.json")
		).thenReturn(
			_frontendTokenDefinitionJSONURL
		);

		Mockito.when(
			bundle.getHeaders(Mockito.anyString())
		).thenReturn(
			new HashMapDictionary<>()
		);

		Mockito.when(
			bundle.getSymbolicName()
		).thenReturn(
			StringPool.BLANK
		);

		FrontendTokenDefinition frontendTokenDefinition =
			frontendTokenDefinitionRegistryImpl.getFrontendTokenDefinitionImpl(
				bundle);

		try (InputStream inputStream =
				_frontendTokenDefinitionJSONURL.openStream()) {

			JSONFactory jsonFactory = new JSONFactoryImpl();

			JSONObject expectJSONObject = jsonFactory.createJSONObject(
				StringUtil.read(inputStream));

			JSONObject actualJSONObject = frontendTokenDefinition.getJSONObject(
				LocaleUtil.ENGLISH);

			Assert.assertEquals(
				expectJSONObject.toMap(), actualJSONObject.toMap());
		}
	}

	@Test
	public void testGetServletContextName() {
		FrontendTokenDefinitionRegistryImpl
			frontendTokenDefinitionRegistryImpl =
				new FrontendTokenDefinitionRegistryImpl();

		Bundle bundle = Mockito.mock(Bundle.class);

		Dictionary<String, String> headers = HashMapDictionaryBuilder.put(
			"Web-ContextPath", "/my-theme"
		).build();

		Mockito.when(
			bundle.getHeaders(Mockito.anyString())
		).thenReturn(
			headers
		);

		Assert.assertEquals(
			"my-theme",
			frontendTokenDefinitionRegistryImpl.getServletContextName(bundle));
	}

	@Test
	public void testGetThemeIdWithNontheme() {
		FrontendTokenDefinitionRegistryImpl
			frontendTokenDefinitionRegistryImpl =
				new FrontendTokenDefinitionRegistryImpl();

		Bundle bundle = Mockito.mock(Bundle.class);

		Assert.assertNull(
			frontendTokenDefinitionRegistryImpl.getThemeId(bundle));
	}

	@Test
	public void testGetThemeIdWithoutServletContext() {
		FrontendTokenDefinitionRegistryImpl
			frontendTokenDefinitionRegistryImpl =
				new FrontendTokenDefinitionRegistryImpl();

		Bundle bundle = Mockito.mock(Bundle.class);

		Mockito.when(
			bundle.getEntry("WEB-INF/liferay-look-and-feel.xml")
		).thenReturn(
			_liferayLookAndFeelXMLURL
		);

		Mockito.when(
			bundle.getHeaders(Mockito.anyString())
		).thenReturn(
			new HashMapDictionary<>()
		);

		frontendTokenDefinitionRegistryImpl.portal = new PortalImpl();

		Assert.assertEquals(
			"classic", frontendTokenDefinitionRegistryImpl.getThemeId(bundle));
	}

	@Test
	public void testGetThemeIdWithServletContext() {
		FrontendTokenDefinitionRegistryImpl
			frontendTokenDefinitionRegistryImpl =
				new FrontendTokenDefinitionRegistryImpl();

		Bundle bundle = Mockito.mock(Bundle.class);

		Mockito.when(
			bundle.getEntry("WEB-INF/liferay-look-and-feel.xml")
		).thenReturn(
			_liferayLookAndFeelXMLURL
		);

		Dictionary<String, String> headers = HashMapDictionaryBuilder.put(
			"Web-ContextPath", "/classic-theme"
		).build();

		Mockito.when(
			bundle.getHeaders(Mockito.anyString())
		).thenReturn(
			headers
		);

		frontendTokenDefinitionRegistryImpl.portal = new PortalImpl();

		Assert.assertEquals(
			"classic_WAR_classictheme",
			frontendTokenDefinitionRegistryImpl.getThemeId(bundle));
	}

	private static final URL _frontendTokenDefinitionJSONURL =
		FrontendTokenDefinitionRegistryImplTest.class.getResource(
			"dependencies/frontend-token-definition.json");
	private static final URL _liferayLookAndFeelXMLURL =
		FrontendTokenDefinitionRegistryImplTest.class.getResource(
			"dependencies/liferay-look-and-feel.xml");

}