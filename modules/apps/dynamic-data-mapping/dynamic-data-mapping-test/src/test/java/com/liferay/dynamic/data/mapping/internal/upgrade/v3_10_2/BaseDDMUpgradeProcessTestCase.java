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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_10_2;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.internal.fieldset.FieldSetDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.internal.radio.RadioDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.internal.select.SelectDDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.internal.text.TextDDMFormFieldType;
import com.liferay.dynamic.data.mapping.internal.io.DDMFormJSONDeserializer;
import com.liferay.dynamic.data.mapping.internal.io.DDMFormJSONSerializer;
import com.liferay.dynamic.data.mapping.internal.io.DDMFormLayoutJSONDeserializer;
import com.liferay.dynamic.data.mapping.internal.io.DDMFormLayoutJSONSerializer;
import com.liferay.dynamic.data.mapping.internal.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.internal.io.DDMFormValuesJSONSerializer;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Carolina Barbosa
 */
@PrepareForTest({LocaleUtil.class, ResourceBundleUtil.class})
@RunWith(PowerMockRunner.class)
public abstract class BaseDDMUpgradeProcessTestCase extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpDDMFormFieldTypeServicesTracker();
		setUpJSONFactory();
		setUpJSONFactoryUtil();
		setUpLanguageUtil();
		setUpLocaleUtil();
		setUpPortalUtil();
		setUpResourceBundleUtil();
	}

	protected String read(String fileName) throws IOException {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	protected void setUpDDMFormFieldTypeServicesTracker() throws Exception {
		MemberMatcher.field(
			DDMFormJSONDeserializer.class, "_ddmFormFieldTypeServicesTracker"
		).set(
			ddmFormJSONDeserializer, ddmFormFieldTypeServicesTracker
		);

		MemberMatcher.field(
			DDMFormJSONSerializer.class, "_ddmFormFieldTypeServicesTracker"
		).set(
			ddmFormJSONSerializer, ddmFormFieldTypeServicesTracker
		);

		when(
			ddmFormFieldTypeServicesTracker.getDDMFormFieldType("fieldset")
		).thenReturn(
			new FieldSetDDMFormFieldType()
		);

		when(
			ddmFormFieldTypeServicesTracker.getDDMFormFieldType("radio")
		).thenReturn(
			new RadioDDMFormFieldType()
		);

		when(
			ddmFormFieldTypeServicesTracker.getDDMFormFieldType("select")
		).thenReturn(
			new SelectDDMFormFieldType()
		);

		when(
			ddmFormFieldTypeServicesTracker.getDDMFormFieldType("text")
		).thenReturn(
			new TextDDMFormFieldType()
		);
	}

	protected void setUpJSONFactory() throws Exception {
		MemberMatcher.field(
			DDMFormJSONDeserializer.class, "_jsonFactory"
		).set(
			ddmFormJSONDeserializer, jsonFactory
		);

		MemberMatcher.field(
			DDMFormJSONSerializer.class, "_jsonFactory"
		).set(
			ddmFormJSONSerializer, jsonFactory
		);

		MemberMatcher.field(
			DDMFormLayoutJSONDeserializer.class, "_jsonFactory"
		).set(
			ddmFormLayoutJSONDeserializer, jsonFactory
		);

		MemberMatcher.field(
			DDMFormLayoutJSONSerializer.class, "_jsonFactory"
		).set(
			ddmFormLayoutJSONSerializer, jsonFactory
		);

		MemberMatcher.field(
			DDMFormValuesJSONDeserializer.class, "_jsonFactory"
		).set(
			ddmFormValuesJSONDeserializer, jsonFactory
		);

		MemberMatcher.field(
			DDMFormValuesJSONSerializer.class, "_jsonFactory"
		).set(
			ddmFormValuesJSONSerializer, jsonFactory
		);
	}

	protected void setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = mock(Language.class);

		when(
			language.isAvailableLocale("en_US")
		).thenReturn(
			true
		);

		languageUtil.setLanguage(language);
	}

	protected void setUpLocaleUtil() {
		mockStatic(LocaleUtil.class);

		when(
			LocaleUtil.fromLanguageId("en_US")
		).thenReturn(
			LocaleUtil.US
		);

		when(
			LocaleUtil.fromLanguageId("en_US", true, false)
		).thenReturn(
			LocaleUtil.US
		);

		when(
			LocaleUtil.toLanguageId(LocaleUtil.US)
		).thenReturn(
			"en_US"
		);
	}

	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = mock(Portal.class);

		ResourceBundle resourceBundle = mock(ResourceBundle.class);

		when(
			portal.getResourceBundle(Matchers.any(Locale.class))
		).thenReturn(
			resourceBundle
		);

		portalUtil.setPortal(portal);
	}

	protected void setUpResourceBundleUtil() {
		mockStatic(ResourceBundleUtil.class);

		when(
			ResourceBundleUtil.getBundle(
				Matchers.anyString(), Matchers.any(Locale.class),
				Matchers.any(ClassLoader.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	@Mock
	protected DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker;

	protected final DDMFormJSONDeserializer ddmFormJSONDeserializer =
		new DDMFormJSONDeserializer();
	protected final DDMFormJSONSerializer ddmFormJSONSerializer =
		new DDMFormJSONSerializer();
	protected final DDMFormLayoutJSONDeserializer
		ddmFormLayoutJSONDeserializer = new DDMFormLayoutJSONDeserializer();
	protected final DDMFormLayoutJSONSerializer ddmFormLayoutJSONSerializer =
		new DDMFormLayoutJSONSerializer();
	protected final DDMFormValuesJSONDeserializer
		ddmFormValuesJSONDeserializer = new DDMFormValuesJSONDeserializer();
	protected final DDMFormValuesJSONSerializer ddmFormValuesJSONSerializer =
		new DDMFormValuesJSONSerializer();
	protected final JSONFactory jsonFactory = new JSONFactoryImpl();

}