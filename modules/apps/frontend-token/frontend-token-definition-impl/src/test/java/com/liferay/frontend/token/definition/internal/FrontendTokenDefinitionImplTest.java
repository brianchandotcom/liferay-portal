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

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Iván Zaera
 */
public class FrontendTokenDefinitionImplTest {

	@Test
	public void testTranslateJSON() {
		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		Package aPackage = FrontendTokenDefinitionImplTest.class.getPackage();

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(Locale.ENGLISH)
		).thenReturn(
			ResourceBundle.getBundle(
				aPackage.getName() + ".dependencies.Language", Locale.ENGLISH)
		);

		FrontendTokenDefinitionImpl frontendTokenDefinitionImpl =
			new FrontendTokenDefinitionImpl(
				_frontendTokenDefinitionJSON, new JSONFactoryImpl(),
				resourceBundleLoader, "theme_id");

		String json = frontendTokenDefinitionImpl.translateJSON(Locale.ENGLISH);

		Assert.assertEquals(_translatedFrontendTokenDefinitionJSON, json);
	}

	private static final String _frontendTokenDefinitionJSON;
	private static final String _translatedFrontendTokenDefinitionJSON;

	static {
		URL url = FrontendTokenDefinitionRegistryImplTest.class.getResource(
			"dependencies/frontend-token-definition.json");

		try (InputStream inputStream = url.openStream()) {
			_frontendTokenDefinitionJSON = StringUtil.read(inputStream);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		url = FrontendTokenDefinitionRegistryImplTest.class.getResource(
			"dependencies/translated-frontend-token-definition.json");

		try (InputStream inputStream = url.openStream()) {
			JSONFactory jsonFactory = new JSONFactoryImpl();

			_translatedFrontendTokenDefinitionJSON =
				jsonFactory.looseSerializeDeep(
					jsonFactory.createJSONObject(StringUtil.read(inputStream)));
		}
		catch (IOException | JSONException exception) {
			throw new RuntimeException(exception);
		}
	}

}