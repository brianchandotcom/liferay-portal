/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class DLSizeLimitConfigurationModelListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_setUpResourceBundleLoader();
		_setUpLanguage();
	}

	@Test
	public void testEmptyConfigurationValue() throws Exception {
		_dlSizeLimitConfigurationModelListener.onBeforeSave(
			RandomTestUtil.randomString(),
			HashMapDictionaryBuilder.<String, Object>put(
				"mimeTypeSizeLimit", new String[0]
			).build());
	}

	@Test(expected = ConfigurationModelListenerException.class)
	public void testInvalidMimeType() throws Exception {
		_dlSizeLimitConfigurationModelListener.onBeforeSave(
			RandomTestUtil.randomString(),
			HashMapDictionaryBuilder.<String, Object>put(
				"mimeTypeSizeLimit", new String[] {" type : 12345 "}
			).build());
	}

	@Test
	public void testNullConfigurationValue() throws Exception {
		_dlSizeLimitConfigurationModelListener.onBeforeSave(
			RandomTestUtil.randomString(), new HashMapDictionary<>());
	}

	@Test
	public void testValidMimeType() throws Exception {
		_dlSizeLimitConfigurationModelListener.onBeforeSave(
			RandomTestUtil.randomString(),
			HashMapDictionaryBuilder.<String, Object>put(
				"mimeTypeSizeLimit", new String[] {"image/png:12345"}
			).build());
	}

	@Test
	public void testValidMimeTypeWithBlanks() throws Exception {
		_dlSizeLimitConfigurationModelListener.onBeforeSave(
			RandomTestUtil.randomString(),
			HashMapDictionaryBuilder.<String, Object>put(
				"mimeTypeSizeLimit", new String[] {" image/png : 12345 "}
			).build());
	}

	@Test
	public void testValidSimpleMimeType() throws Exception {
		_dlSizeLimitConfigurationModelListener.onBeforeSave(
			RandomTestUtil.randomString(),
			HashMapDictionaryBuilder.<String, Object>put(
				"mimeTypeSizeLimit", new String[] {"image/png:12345"}
			).build());
	}

	private void _setUpLanguage() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
	}

	private void _setUpResourceBundleLoader() {
		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			resourceBundleLoader);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(
				Mockito.nullable(Locale.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	private final DLSizeLimitConfigurationModelListener
		_dlSizeLimitConfigurationModelListener =
			new DLSizeLimitConfigurationModelListener();
	private final Language _language = Mockito.mock(Language.class);

}