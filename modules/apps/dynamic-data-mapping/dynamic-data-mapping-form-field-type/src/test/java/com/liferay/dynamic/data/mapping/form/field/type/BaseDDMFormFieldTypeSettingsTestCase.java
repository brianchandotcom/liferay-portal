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

package com.liferay.dynamic.data.mapping.form.field.type;

import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Leonardo Barros
 */
public abstract class BaseDDMFormFieldTypeSettingsTestCase {

	@Before
	public void setUp() throws Exception {
		setUpJSONFactoryUtil();
		setUpLanguageUtil();
		setUpPortalClassLoaderUtil();
		setUpPortalUtil();
		setUpResourceBundleUtil();
	}

	protected void assertDDMFormLayout(
		DDMFormLayout actualDDMFormLayout,
		DDMFormLayout expectedDDMFormLayout) {

		_assertDDMFormLayoutRelatedObject(
			actualDDMFormLayout, expectedDDMFormLayout,
			_getAssertDDMFormLayoutPagesFunction(), "getDDMFormLayoutPages",
			() -> Assert.assertEquals(
				expectedDDMFormLayout.getPaginationMode(),
				actualDDMFormLayout.getPaginationMode()));
	}

	protected void setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Set<Locale> availableLocales = new HashSet<>(
			Arrays.asList(LocaleUtil.BRAZIL, LocaleUtil.US));

		Mockito.when(
			language.getAvailableLocales()
		).thenReturn(
			availableLocales
		);

		Mockito.when(
			language.getLanguageId(LocaleUtil.BRAZIL)
		).thenReturn(
			"pt_BR"
		);

		Mockito.when(
			language.getLanguageId(LocaleUtil.US)
		).thenReturn(
			"en_US"
		);

		languageUtil.setLanguage(language);
	}

	protected void setUpPortalClassLoaderUtil() {
		PortalClassLoaderUtil.setClassLoader(_classLoader);
	}

	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = Mockito.mock(Portal.class);

		ResourceBundle resourceBundle = Mockito.mock(ResourceBundle.class);

		Mockito.when(
			portal.getResourceBundle(Matchers.any(Locale.class))
		).thenReturn(
			resourceBundle
		);

		portalUtil.setPortal(portal);
	}

	protected void setUpResourceBundleUtil() {
		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			resourceBundleLoader);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(Matchers.any(Locale.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(Matchers.eq(LocaleUtil.US))
		).thenReturn(
			_resourceBundle
		);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(
				Matchers.eq(LocaleUtil.BRAZIL))
		).thenReturn(
			_resourceBundle
		);
	}

	protected Language language = Mockito.mock(Language.class);

	private void _assertDDMFormLayoutColumn(
		DDMFormLayoutColumn actualDDMFormLayoutColumn,
		DDMFormLayoutColumn expectedDDMFormLayoutColumn) {

		Assert.assertEquals(
			expectedDDMFormLayoutColumn.getDDMFormFieldNames(),
			actualDDMFormLayoutColumn.getDDMFormFieldNames());
		Assert.assertEquals(
			expectedDDMFormLayoutColumn.getSize(),
			actualDDMFormLayoutColumn.getSize());
	}

	private void _assertDDMFormLayoutRelatedObject(
		Object actualDDMFormLayoutRelatedObject,
		Object expectedDDMFormLayoutRelatedObject,
		Function<List<Object>, Consumer<Object>> function, String methodName,
		Runnable runnable) {

		List<Object> expectedDDMFormLayoutRelatedObjects =
			ReflectionTestUtil.invoke(
				expectedDDMFormLayoutRelatedObject, methodName, null);

		List<Object> actualDDMFormLayoutRelatedObjects =
			ReflectionTestUtil.invoke(
				actualDDMFormLayoutRelatedObject, methodName, null);

		Stream<Object> stream = actualDDMFormLayoutRelatedObjects.stream();

		stream.forEachOrdered(
			function.apply(expectedDDMFormLayoutRelatedObjects));

		Assert.assertEquals(
			expectedDDMFormLayoutRelatedObjects.toString(), 0,
			expectedDDMFormLayoutRelatedObjects.size());

		Optional.ofNullable(
			runnable
		).ifPresent(
			Runnable::run
		);
	}

	private Function<List<Object>, Consumer<Object>>
		_getAssertDDMFormLayoutColumnsFunction() {

		return expectedDDMFormLayoutColumns ->
			actualDDMFormLayoutColumn -> _assertDDMFormLayoutColumn(
				(DDMFormLayoutColumn)actualDDMFormLayoutColumn,
				(DDMFormLayoutColumn)expectedDDMFormLayoutColumns.remove(0));
	}

	private Function<List<Object>, Consumer<Object>>
		_getAssertDDMFormLayoutPagesFunction() {

		return expectedDDMFormLayoutPages ->
			actualDDMFormLayoutPage -> _assertDDMFormLayoutRelatedObject(
				actualDDMFormLayoutPage, expectedDDMFormLayoutPages.remove(0),
				_getAssertDDMFormLayoutRowsFunction(), "getDDMFormLayoutRows",
				null);
	}

	private Function<List<Object>, Consumer<Object>>
		_getAssertDDMFormLayoutRowsFunction() {

		return expectedDDMFormLayoutRows ->
			actualDDMFormLayoutRow -> _assertDDMFormLayoutRelatedObject(
				actualDDMFormLayoutRow, expectedDDMFormLayoutRows.remove(0),
				_getAssertDDMFormLayoutColumnsFunction(),
				"getDDMFormLayoutColumns", null);
	}

	private final ClassLoader _classLoader = Mockito.mock(ClassLoader.class);
	private final ResourceBundle _resourceBundle = Mockito.mock(
		ResourceBundle.class);

}