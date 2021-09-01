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
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;

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
import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@PrepareForTest({PortalClassLoaderUtil.class, ResourceBundleUtil.class})
@RunWith(PowerMockRunner.class)
public abstract class BaseDDMFormFieldTypeSettingsTestCase
	extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpJSONFactoryUtil();
		setUpLanguageUtil();
		setUpPortalClassLoaderUtil();
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
		Set<Locale> availableLocales = SetUtil.fromArray(
			new Locale[] {LocaleUtil.US});

		when(
			language.getAvailableLocales()
		).thenReturn(
			availableLocales
		);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(language);
	}

	protected void setUpPortalClassLoaderUtil() {
		mockStatic(PortalClassLoaderUtil.class);

		when(
			PortalClassLoaderUtil.getClassLoader()
		).thenReturn(
			_classLoader
		);
	}

	protected void setUpResourceBundleUtil() {
		mockStatic(ResourceBundleUtil.class);

		when(
			ResourceBundleUtil.getBundle(
				"content.Language", LocaleUtil.BRAZIL, _classLoader)
		).thenReturn(
			_resourceBundle
		);

		when(
			ResourceBundleUtil.getBundle(
				"content.Language", LocaleUtil.US, _classLoader)
		).thenReturn(
			_resourceBundle
		);
	}

	@Mock
	protected Language language;

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

	@Mock
	private ClassLoader _classLoader;

	@Mock
	private ResourceBundle _resourceBundle;

}