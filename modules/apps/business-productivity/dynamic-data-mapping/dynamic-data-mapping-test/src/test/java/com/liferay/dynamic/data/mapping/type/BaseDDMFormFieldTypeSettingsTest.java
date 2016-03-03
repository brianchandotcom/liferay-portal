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

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

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
public abstract class BaseDDMFormFieldTypeSettingsTest extends PowerMockito {

	@Before
	public void setUp() {
		setUpLanguageUtil();
		setUpPortalClassLoaderUtil();
		setUpResourceBundleUtil();
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

	@Mock
	private ClassLoader _classLoader;

	@Mock
	private ResourceBundle _resourceBundle;

}