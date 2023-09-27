/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Before;

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
		PortalClassLoaderUtil.setClassLoader(Mockito.mock(ClassLoader.class));
	}

	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = Mockito.mock(Portal.class);

		ResourceBundle resourceBundle = Mockito.mock(ResourceBundle.class);

		Mockito.when(
			portal.getResourceBundle(Mockito.any(Locale.class))
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
			resourceBundleLoader.loadResourceBundle(Mockito.any(Locale.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);

		ResourceBundle mockResourceBundle = Mockito.mock(ResourceBundle.class);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(Mockito.eq(LocaleUtil.US))
		).thenReturn(
			mockResourceBundle
		);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(
				Mockito.eq(LocaleUtil.BRAZIL))
		).thenReturn(
			mockResourceBundle
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

		actualDDMFormLayoutRelatedObjects.forEach(
			function.apply(expectedDDMFormLayoutRelatedObjects));

		Assert.assertEquals(
			expectedDDMFormLayoutRelatedObjects.toString(), 0,
			expectedDDMFormLayoutRelatedObjects.size());

		if (runnable != null) {
			runnable.run();
		}
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

}