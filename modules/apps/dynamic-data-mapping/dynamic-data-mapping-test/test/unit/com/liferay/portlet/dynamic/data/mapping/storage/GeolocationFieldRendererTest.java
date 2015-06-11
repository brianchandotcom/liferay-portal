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

package com.liferay.portlet.dynamic.data.mapping.storage;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.FieldRenderer;
import com.liferay.portlet.dynamicdatamapping.storage.GeolocationFieldRenderer;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Adolfo Pérez
 */
@PrepareForTest({LanguageUtil.class, JSONFactoryUtil.class})
@RunWith(PowerMockRunner.class)
public class GeolocationFieldRendererTest extends PowerMockito {

	@Before
	public void setUp() {
		setUpJSONFactoryUtil();
		setUpLanguageUtil();
	}

	@Test
	public void testRenderedValuesFollowLocaleConventions() {
		FieldRenderer fieldRenderer = new GeolocationFieldRenderer();

		Assert.assertEquals(
			"Latitud: 9,876, Longitud: 1,234",
			fieldRenderer.render(createField(), LocaleUtil.SPAIN));
	}

	@Test
	public void testRenderedValuesShouldHave3DecimalPlaces() {
		FieldRenderer fieldRenderer = new GeolocationFieldRenderer();

		Assert.assertEquals(
			"Latitude: 9.876, Longitude: 1.234",
			fieldRenderer.render(createField(), LocaleUtil.US));
	}

	protected Field createField() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("latitude", 9.8765);
		jsonObject.put("longitude", 1.2345);

		return new Field("field", jsonObject.toString());
	}

	protected void setUpJSONFactoryUtil() {
		spy(JSONFactoryUtil.class);

		when(
			JSONFactoryUtil.getJSONFactory()
		).thenReturn(
			new JSONFactoryImpl()
		);
	}

	protected void setUpLanguageUtil() {
		whenLanguageGet(LocaleUtil.SPAIN, "latitude", "Latitud");
		whenLanguageGet(LocaleUtil.SPAIN, "longitude", "Longitud");
		whenLanguageGet(LocaleUtil.US, "latitude", "Latitude");
		whenLanguageGet(LocaleUtil.US, "longitude", "Longitude");

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
	}

	protected void whenLanguageGet(
		Locale locale, String key, String returnValue) {

		when(
			_language.get(Matchers.eq(locale), Matchers.eq(key))
		).thenReturn(
			returnValue
		);
	}

	@Mock
	private Language _language;

}