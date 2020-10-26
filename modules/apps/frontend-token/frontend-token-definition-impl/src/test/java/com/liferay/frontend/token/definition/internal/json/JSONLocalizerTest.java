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

package com.liferay.frontend.token.definition.internal.json;

import com.liferay.frontend.token.definition.internal.FrontendTokenDefinitionImplTest;
import com.liferay.frontend.token.definition.internal.FrontendTokenDefinitionRegistryImplTest;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Iván Zaera
 */
public class JSONLocalizerTest {

	@Test
	public void testGetTranslatedToExistingLocale() {
		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		Package pkg = FrontendTokenDefinitionImplTest.class.getPackage();

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(LocaleUtil.ENGLISH)
		).thenReturn(
			ResourceBundle.getBundle(
				pkg.getName() + ".dependencies.Language", LocaleUtil.ENGLISH)
		);

		JSONLocalizer jsonLocalizer = new JSONLocalizer(
			_FRONTEND_TOKEN_DEFINITION_JSON, new JSONFactoryImpl(),
			resourceBundleLoader, "theme_id");

		Assert.assertEquals(
			_TRANSLATED_FRONTEND_TOKEN_DEFINITION_JSON,
			jsonLocalizer.getJSON(LocaleUtil.ENGLISH));
	}

	@Test
	public void testGetTranslatedToMissingLocale() {
		JSONLocalizer jsonLocalizer = new JSONLocalizer(
			_FRONTEND_TOKEN_DEFINITION_JSON, new JSONFactoryImpl(),
			Mockito.mock(ResourceBundleLoader.class), "theme_id");

		Assert.assertEquals(
			_FRONTEND_TOKEN_DEFINITION_JSON,
			jsonLocalizer.getJSON(LocaleUtil.SPAIN));
	}

	@Test
	public void testGetUnstranslated() {
		JSONLocalizer jsonLocalizer = new JSONLocalizer(
			_FRONTEND_TOKEN_DEFINITION_JSON, new JSONFactoryImpl(),
			Mockito.mock(ResourceBundleLoader.class), "theme_id");

		Assert.assertEquals(
			_FRONTEND_TOKEN_DEFINITION_JSON, jsonLocalizer.getJSON(null));
	}

	@Test
	public void testReturnsAreCached() {
		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		Package pkg = FrontendTokenDefinitionImplTest.class.getPackage();

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(LocaleUtil.ENGLISH)
		).thenReturn(
			ResourceBundle.getBundle(
				pkg.getName() + ".dependencies.Language", LocaleUtil.ENGLISH)
		);

		JSONLocalizer jsonLocalizer = new JSONLocalizer(
			_FRONTEND_TOKEN_DEFINITION_JSON, new JSONFactoryImpl(),
			resourceBundleLoader, "theme_id");

		Assert.assertSame(
			jsonLocalizer.getJSON(LocaleUtil.ENGLISH),
			jsonLocalizer.getJSON(LocaleUtil.ENGLISH));
	}

	private static final String _FRONTEND_TOKEN_DEFINITION_JSON;

	private static final String _TRANSLATED_FRONTEND_TOKEN_DEFINITION_JSON;

	static {
		JSONFactory jsonFactory = new JSONFactoryImpl();

		URL url = FrontendTokenDefinitionRegistryImplTest.class.getResource(
			"dependencies/frontend-token-definition.json");

		try (InputStream inputStream = url.openStream()) {
			_FRONTEND_TOKEN_DEFINITION_JSON = jsonFactory.looseSerializeDeep(
				jsonFactory.createJSONObject(StringUtil.read(inputStream)));
		}
		catch (IOException | JSONException exception) {
			throw new RuntimeException(exception);
		}

		url = FrontendTokenDefinitionRegistryImplTest.class.getResource(
			"dependencies/translated-frontend-token-definition.json");

		try (InputStream inputStream = url.openStream()) {
			_TRANSLATED_FRONTEND_TOKEN_DEFINITION_JSON =
				jsonFactory.looseSerializeDeep(
					jsonFactory.createJSONObject(StringUtil.read(inputStream)));
		}
		catch (IOException | JSONException exception) {
			throw new RuntimeException(exception);
		}
	}

}