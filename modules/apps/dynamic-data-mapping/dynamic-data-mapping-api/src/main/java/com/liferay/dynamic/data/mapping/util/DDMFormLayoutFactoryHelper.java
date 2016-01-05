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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Leonardo Barros
 * @author Marcellus Tavares
 */
public class DDMFormLayoutFactoryHelper {

	public DDMFormLayoutFactoryHelper(Class<?> clazz) {
		_clazz = clazz;
		_ddmFormLayout = clazz.getAnnotation(DDMFormLayout.class);

		setDefaultLocale();
	}

	public com.liferay.dynamic.data.mapping.model.DDMFormLayout
		createDDMFormLayout() {

		com.liferay.dynamic.data.mapping.model.DDMFormLayout ddmFormLayout =
			new com.liferay.dynamic.data.mapping.model.DDMFormLayout();

		ddmFormLayout.setDefaultLocale(_defaultLocale);

		for (DDMFormLayoutPage ddmFormLayoutPage : _ddmFormLayout.value()) {
			ddmFormLayout.addDDMFormLayoutPage(
				createDDMFormLayoutPage(ddmFormLayoutPage));
		}

		return ddmFormLayout;
	}

	protected void collectResourceBundles(
		Class<?> clazz, List<ResourceBundle> resourceBundles, Locale locale) {

		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			collectResourceBundles(interfaceClass, resourceBundles, locale);
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, clazz.getClassLoader());

		if (resourceBundle != null) {
			resourceBundles.add(resourceBundle);
		}
	}

	protected com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn
		createDDMFormLayoutColumn(DDMFormLayoutColumn ddmFormLayoutColumnAnnotation) {

		com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn
			ddmFormLayoutColumn = new com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn();

		ddmFormLayoutColumn.setDDMFormFieldNames(
			ListUtil.fromArray(ddmFormLayoutColumnAnnotation.value()));
		ddmFormLayoutColumn.setSize(ddmFormLayoutColumnAnnotation.size());

		return ddmFormLayoutColumn;
	}

	protected com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage
		createDDMFormLayoutPage(DDMFormLayoutPage ddmFormLayoutPageAnnotation) {

		com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage
			ddmFormLayoutPage = new com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage();

		LocalizedValue title = createDDMFormLayoutPageTitle(
			ddmFormLayoutPageAnnotation.title());

		ddmFormLayoutPage.setTitle(title);

		for (DDMFormLayoutRow ddmFormLayoutRow :
				ddmFormLayoutPageAnnotation.value()) {

			ddmFormLayoutPage.addDDMFormLayoutRow(
				createDDMFormLayoutRow(ddmFormLayoutRow));
		}

		return ddmFormLayoutPage;
	}

	protected LocalizedValue createDDMFormLayoutPageTitle(String title) {
		LocalizedValue localizedValue = new LocalizedValue(_defaultLocale);

		if (Validator.isNull(title)) {
			return localizedValue;
		}

		if (isLocalizableValue(title)) {
			String languageKey = extractLanguageKey(title);

			localizedValue.addString(
				_defaultLocale, getLocalizedValue(_defaultLocale, languageKey));
		}
		else {
			localizedValue.addString(_defaultLocale, title);
		}

		return localizedValue;
	}

	protected com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow
		createDDMFormLayoutRow(DDMFormLayoutRow ddmFormLayoutRowAnnotation) {

		com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow
			ddmFormLayoutRow = new com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow();

		for (DDMFormLayoutColumn ddmFormLayoutColumn :
				ddmFormLayoutRowAnnotation.value()) {

			ddmFormLayoutRow.addDDMFormLayoutColumn(
				createDDMFormLayoutColumn(ddmFormLayoutColumn));
		}

		return ddmFormLayoutRow;
	}

	protected String extractLanguageKey(String value) {
		return StringUtil.extractLast(value, StringPool.PERCENT);
	}

	protected LocalizedValue getDDMFormLayoutPageTitle(String title) {
		Locale defaultLocale = LocaleUtil.getDefault();

		LocalizedValue localizedValue = new LocalizedValue(defaultLocale);

		if (Validator.isNull(title)) {
			return localizedValue;
		}

		if (isLocalizableValue(title)) {
			String languageKey = extractLanguageKey(title);

			localizedValue.addString(
				defaultLocale, getLocalizedValue(defaultLocale, languageKey));
		}
		else {
			localizedValue.addString(defaultLocale, title);
		}

		return localizedValue;
	}

	protected String getLocalizedValue(Locale locale, String value) {
		ResourceBundle resourceBundle = getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, value);
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundle portalResourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, PortalClassLoaderUtil.getClassLoader());

		List<ResourceBundle> resourceBundles = new ArrayList<>();

		resourceBundles.add(portalResourceBundle);

		collectResourceBundles(_clazz, resourceBundles, locale);

		ResourceBundle[] resourceBundleArray = resourceBundles.toArray(
			new ResourceBundle[resourceBundles.size()]);

		return new AggregateResourceBundle(resourceBundleArray);
	}

	protected boolean isLocalizableValue(String value) {
		if (StringUtil.startsWith(value, StringPool.PERCENT)) {
			return true;
		}

		return false;
	}

	protected void setDefaultLocale() {
		Locale defaultLocale = LocaleThreadLocal.getThemeDisplayLocale();

		if (defaultLocale == null) {
			defaultLocale = LocaleUtil.getDefault();
		}

		_defaultLocale = defaultLocale;
	}

	private final Class<?> _clazz;
	private final DDMFormLayout _ddmFormLayout;
	private Locale _defaultLocale;

}