/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.storage;

import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.FieldRenderer;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.JavaDetector;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class GeolocationFieldRendererTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_setUpLanguageUtil();
	}

	@Test
	public void testRenderedValuesFollowLocaleConventions() {
		FieldRenderer fieldRenderer = new GeolocationFieldRenderer(
			new JSONFactoryImpl(), _language);

		if (JavaDetector.isJDK8()) {
			Assert.assertEquals(
				"Latitud: 9,877, Longitud: 1,234",
				fieldRenderer.render(_createField(), LocaleUtil.SPAIN));
		}
		else {
			Assert.assertEquals(
				"Latitud: 9,876, Longitud: 1,234",
				fieldRenderer.render(_createField(), LocaleUtil.SPAIN));
		}
	}

	@Test
	public void testRenderedValuesShouldHave3DecimalPlaces() {
		FieldRenderer fieldRenderer = new GeolocationFieldRenderer(
			new JSONFactoryImpl(), _language);

		if (JavaDetector.isJDK8()) {
			Assert.assertEquals(
				"Latitude: 9.877, Longitude: 1.234",
				fieldRenderer.render(_createField(), LocaleUtil.US));
		}
		else {
			Assert.assertEquals(
				"Latitude: 9.876, Longitude: 1.234",
				fieldRenderer.render(_createField(), LocaleUtil.US));
		}
	}

	private static void _setUpLanguageUtil() {
		_whenLanguageGet(LocaleUtil.SPAIN, "latitude", "Latitud");
		_whenLanguageGet(LocaleUtil.SPAIN, "longitude", "Longitud");
		_whenLanguageGet(LocaleUtil.US, "latitude", "Latitude");
		_whenLanguageGet(LocaleUtil.US, "longitude", "Longitude");

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
	}

	private static void _whenLanguageGet(
		Locale locale, String key, String returnValue) {

		Mockito.when(
			_language.get(Mockito.eq(locale), Mockito.eq(key))
		).thenReturn(
			returnValue
		);
	}

	private Field _createField() {
		return new Field("field", "{latitude: 9.8765, longitude: 1.2345}");
	}

	private static final Language _language = Mockito.mock(Language.class);

}