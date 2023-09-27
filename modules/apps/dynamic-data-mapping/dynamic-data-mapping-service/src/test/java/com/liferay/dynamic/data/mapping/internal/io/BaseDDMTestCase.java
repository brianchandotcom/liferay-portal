/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.io;

import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.junit.Before;

import org.mockito.Mockito;

/**
 * @author Marcellus Tavares
 */
public abstract class BaseDDMTestCase {

	@Before
	public void setUp() throws Exception {
		_setUpJSONFactoryUtil();
		_setUpLanguageUtil();
		_setUpPortalClassLoaderUtil();
		_setUpResourceBundleUtil();
	}

	protected DDMFormLayoutColumn createDDMFormLayoutColumn(
		int size, String... fieldNames) {

		return new DDMFormLayoutColumn(size, fieldNames);
	}

	protected List<DDMFormLayoutColumn> createDDMFormLayoutColumns(
		String... fieldNames) {

		List<DDMFormLayoutColumn> ddmFormLayoutColumns = new ArrayList<>();

		int size = 12 / fieldNames.length;

		for (String fieldName : fieldNames) {
			ddmFormLayoutColumns.add(new DDMFormLayoutColumn(size, fieldName));
		}

		return ddmFormLayoutColumns;
	}

	protected DDMFormLayoutRow createDDMFormLayoutRow(
		DDMFormLayoutColumn... ddmFormLayoutColumns) {

		return createDDMFormLayoutRow(Arrays.asList(ddmFormLayoutColumns));
	}

	protected DDMFormLayoutRow createDDMFormLayoutRow(
		List<DDMFormLayoutColumn> ddmFormLayoutColumns) {

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		ddmFormLayoutRow.setDDMFormLayoutColumns(ddmFormLayoutColumns);

		return ddmFormLayoutRow;
	}

	protected String read(String fileName) throws IOException {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	protected Language language = Mockito.mock(Language.class);

	private void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private void _setUpLanguageUtil() {
		Set<Locale> availableLocales = SetUtil.fromArray(
			LocaleUtil.BRAZIL, LocaleUtil.US);

		_whenLanguageGetAvailableLocalesThen(availableLocales);

		_whenLanguageIsAvailableLocale(LocaleUtil.BRAZIL);
		_whenLanguageIsAvailableLocale(LocaleUtil.US);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(language);
	}

	private void _setUpPortalClassLoaderUtil() {
		PortalClassLoaderUtil.setClassLoader(_classLoader);
	}

	private void _setUpResourceBundleUtil() {
		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			resourceBundleLoader);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(LocaleUtil.BRAZIL)
		).thenReturn(
			_resourceBundle
		);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(LocaleUtil.US)
		).thenReturn(
			_resourceBundle
		);
	}

	private void _whenLanguageGetAvailableLocalesThen(
		Set<Locale> availableLocales) {

		Mockito.when(
			language.getAvailableLocales()
		).thenReturn(
			availableLocales
		);
	}

	private void _whenLanguageIsAvailableLocale(Locale locale) {
		Mockito.when(
			language.isAvailableLocale(
				Mockito.eq(LocaleUtil.toLanguageId(locale)))
		).thenReturn(
			true
		);

		Mockito.when(
			language.isAvailableLocale(Mockito.eq(locale))
		).thenReturn(
			true
		);
	}

	private final ClassLoader _classLoader = Mockito.mock(ClassLoader.class);
	private final ResourceBundle _resourceBundle = Mockito.mock(
		ResourceBundle.class);

}