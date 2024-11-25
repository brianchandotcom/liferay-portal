/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.internal;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.URLUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PortalImpl;

import java.net.URL;

import java.util.Dictionary;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
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

	@Before
	public void setUp() {
		_resourceBundleLoaderUtilMockedStatic = Mockito.mockStatic(
			ResourceBundleLoaderUtil.class);

		_resourceBundleLoaderUtilMockedStatic.when(
			() ->
				ResourceBundleLoaderUtil.
					getResourceBundleLoaderByBundleSymbolicName(
						Mockito.anyString())
		).thenReturn(
			Mockito.mock(ResourceBundleLoader.class)
		);
	}

	@After
	public void tearDown() {
		_resourceBundleLoaderUtilMockedStatic.close();
	}

	@Test
	public void testGetJSON() throws Exception {
		FrontendTokenDefinitionRegistryImpl
			frontendTokenDefinitionRegistryImpl =
				new FrontendTokenDefinitionRegistryImpl();

		frontendTokenDefinitionRegistryImpl.jsonFactory = new JSONFactoryImpl();
		frontendTokenDefinitionRegistryImpl.portal = new PortalImpl();

		Bundle bundle = Mockito.mock(Bundle.class);

		Mockito.when(
			bundle.getEntry("WEB-INF/frontend-token-definition.json")
		).thenReturn(
			_frontendTokenDefinitionJSONURL
		);

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

		List<FrontendTokenDefinitionImpl> frontendTokenDefinitionImpls =
			frontendTokenDefinitionRegistryImpl.getFrontendTokenDefinitionImpls(
				bundle);

		Assert.assertEquals(
			frontendTokenDefinitionImpls.toString(), 2,
			frontendTokenDefinitionImpls.size());

		JSONFactory jsonFactory = new JSONFactoryImpl();

		JSONObject expectJSONObject = jsonFactory.createJSONObject(
			URLUtil.toString(_frontendTokenDefinitionJSONURL));

		for (FrontendTokenDefinitionImpl frontendTokenDefinitionImpl :
				frontendTokenDefinitionImpls) {

			JSONObject actualJSONObject =
				frontendTokenDefinitionImpl.getJSONObject(LocaleUtil.ENGLISH);

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
	public void testGetThemeIdsWithNontheme() {
		FrontendTokenDefinitionRegistryImpl
			frontendTokenDefinitionRegistryImpl =
				new FrontendTokenDefinitionRegistryImpl();

		Bundle bundle = Mockito.mock(Bundle.class);

		List<String> themeIds = frontendTokenDefinitionRegistryImpl.getThemeIds(
			bundle);

		Assert.assertTrue(themeIds.isEmpty());
	}

	@Test
	public void testGetThemeIdsWithoutServletContext() {
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

		List<String> themeIds = frontendTokenDefinitionRegistryImpl.getThemeIds(
			bundle);

		Assert.assertEquals(themeIds.toString(), 2, themeIds.size());
		Assert.assertEquals("classic", themeIds.get(0));
		Assert.assertEquals("modern", themeIds.get(1));
	}

	@Test
	public void testGetThemeIdsWithServletContext() {
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
			"Web-ContextPath", "/two-themes"
		).build();

		Mockito.when(
			bundle.getHeaders(Mockito.anyString())
		).thenReturn(
			headers
		);

		frontendTokenDefinitionRegistryImpl.portal = new PortalImpl();

		List<String> themeIds = frontendTokenDefinitionRegistryImpl.getThemeIds(
			bundle);

		Assert.assertEquals(themeIds.toString(), 2, themeIds.size());

		Assert.assertEquals("classic_WAR_twothemes", themeIds.get(0));
		Assert.assertEquals("modern_WAR_twothemes", themeIds.get(1));
	}

	private static final URL _frontendTokenDefinitionJSONURL =
		FrontendTokenDefinitionRegistryImplTest.class.getResource(
			"dependencies/frontend-token-definition.json");
	private static final URL _liferayLookAndFeelXMLURL =
		FrontendTokenDefinitionRegistryImplTest.class.getResource(
			"dependencies/liferay-look-and-feel.xml");

	private MockedStatic<ResourceBundleLoaderUtil>
		_resourceBundleLoaderUtilMockedStatic;

}