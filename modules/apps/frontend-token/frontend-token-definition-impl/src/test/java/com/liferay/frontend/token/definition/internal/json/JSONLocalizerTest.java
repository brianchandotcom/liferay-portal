/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.internal.json;

import com.liferay.frontend.token.definition.internal.FrontendTokenDefinitionImplTest;
import com.liferay.frontend.token.definition.internal.FrontendTokenDefinitionRegistryImplTest;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Iván Zaera
 */
public class JSONLocalizerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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

		JSONObject jsonObject = jsonLocalizer.getJSONObject(LocaleUtil.ENGLISH);

		Assert.assertEquals(
			_TRANSLATED_FRONTEND_TOKEN_DEFINITION_JSON_OBJECT.toMap(),
			jsonObject.toMap());
	}

	@Test
	public void testGetTranslatedToMissingLocale() {
		JSONLocalizer jsonLocalizer = new JSONLocalizer(
			_FRONTEND_TOKEN_DEFINITION_JSON, new JSONFactoryImpl(),
			Mockito.mock(ResourceBundleLoader.class), "theme_id");

		JSONObject jsonObject = jsonLocalizer.getJSONObject(LocaleUtil.SPAIN);

		Assert.assertEquals(
			_FRONTEND_TOKEN_DEFINITION_JSON_OBJECT.toMap(), jsonObject.toMap());
	}

	@Test
	public void testGetUnstranslated() {
		JSONLocalizer jsonLocalizer = new JSONLocalizer(
			_FRONTEND_TOKEN_DEFINITION_JSON, new JSONFactoryImpl(),
			Mockito.mock(ResourceBundleLoader.class), "theme_id");

		JSONObject jsonObject = jsonLocalizer.getJSONObject(null);

		Assert.assertEquals(
			_FRONTEND_TOKEN_DEFINITION_JSON_OBJECT.toMap(), jsonObject.toMap());
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

		JSONObject jsonObject1 = jsonLocalizer.getJSONObject(
			LocaleUtil.ENGLISH);
		JSONObject jsonObject2 = jsonLocalizer.getJSONObject(
			LocaleUtil.ENGLISH);

		Assert.assertEquals(jsonObject1.toMap(), jsonObject2.toMap());
	}

	private static final String _FRONTEND_TOKEN_DEFINITION_JSON;

	private static final JSONObject _FRONTEND_TOKEN_DEFINITION_JSON_OBJECT;

	private static final JSONObject
		_TRANSLATED_FRONTEND_TOKEN_DEFINITION_JSON_OBJECT;

	static {
		JSONFactory jsonFactory = new JSONFactoryImpl();

		URL url = FrontendTokenDefinitionRegistryImplTest.class.getResource(
			"dependencies/frontend-token-definition.json");

		try (InputStream inputStream = url.openStream()) {
			_FRONTEND_TOKEN_DEFINITION_JSON_OBJECT =
				jsonFactory.createJSONObject(StringUtil.read(inputStream));

			_FRONTEND_TOKEN_DEFINITION_JSON = jsonFactory.looseSerializeDeep(
				_FRONTEND_TOKEN_DEFINITION_JSON_OBJECT);
		}
		catch (IOException | JSONException exception) {
			throw new RuntimeException(exception);
		}

		url = FrontendTokenDefinitionRegistryImplTest.class.getResource(
			"dependencies/translated-frontend-token-definition.json");

		try (InputStream inputStream = url.openStream()) {
			_TRANSLATED_FRONTEND_TOKEN_DEFINITION_JSON_OBJECT =
				jsonFactory.createJSONObject(StringUtil.read(inputStream));
		}
		catch (IOException | JSONException exception) {
			throw new RuntimeException(exception);
		}
	}

}