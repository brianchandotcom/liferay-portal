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

package com.liferay.dynamic.data.mapping.form.field.type.internal.numeric.input.mask;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Carolina Barbosa
 */
@RunWith(PowerMockRunner.class)
public class NumericInputMaskDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_setUpJSONFactory();
		_setUpJSONFactoryUtil();
		_setUpLanguageUtil();
		_setUpPortal();
	}

	@Test
	public void testGetParameters() {
		Map<String, Object> parameters =
			_numericInputMaskDDMFormFieldTemplateContextContributor.
				getParameters(
					new DDMFormField("field", "numeric"),
					_createDDMFormFieldRenderingContext());

		Assert.assertEquals("$", parameters.get("append"));
		Assert.assertEquals("prefix", parameters.get("appendType"));
		Assert.assertEquals(2, parameters.get("decimalPlaces"));
		Assert.assertEquals(",", parameters.get("decimalSymbol"));

		List<Object> decimalSymbols = (List<Object>)parameters.get(
			"decimalSymbols");

		Assert.assertEquals(
			decimalSymbols.toString(), 2, decimalSymbols.size());
		Assert.assertEquals(
			HashMapBuilder.put(
				"label", "0.00"
			).put(
				"reference", "."
			).put(
				"value", "."
			).build(),
			decimalSymbols.get(0));
		Assert.assertEquals(
			HashMapBuilder.put(
				"label", "0,00"
			).put(
				"reference", ","
			).put(
				"value", ","
			).build(),
			decimalSymbols.get(1));

		Assert.assertEquals("\'", parameters.get("thousandsSeparator"));

		List<Object> thousandsSeparators = (List<Object>)parameters.get(
			"thousandsSeparators");

		Assert.assertEquals(
			thousandsSeparators.toString(), 5, thousandsSeparators.size());
		Assert.assertEquals(
			HashMapBuilder.<String, Object>put(
				"label",
				HashMapBuilder.put(
					"en_US", "None"
				).build()
			).put(
				"reference", "none"
			).put(
				"value", "none"
			).build(),
			thousandsSeparators.get(0));
		Assert.assertEquals(
			HashMapBuilder.put(
				"label", "1,000"
			).put(
				"reference", ","
			).put(
				"value", ","
			).build(),
			thousandsSeparators.get(1));
		Assert.assertEquals(
			HashMapBuilder.put(
				"label", "1.000"
			).put(
				"reference", "."
			).put(
				"value", "."
			).build(),
			thousandsSeparators.get(2));
		Assert.assertEquals(
			HashMapBuilder.put(
				"label", "1 000"
			).put(
				"reference", " "
			).put(
				"value", " "
			).build(),
			thousandsSeparators.get(3));
		Assert.assertEquals(
			HashMapBuilder.put(
				"label", "1\'000"
			).put(
				"reference", "\'"
			).put(
				"value", "\'"
			).build(),
			thousandsSeparators.get(4));
	}

	private DDMFormFieldRenderingContext _createDDMFormFieldRenderingContext() {
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);
		ddmFormFieldRenderingContext.setValue(
			JSONUtil.put(
				"append", "$"
			).put(
				"appendType", "prefix"
			).put(
				"decimalPlaces", 2
			).put(
				"symbols",
				JSONUtil.put(
					"decimalSymbol", ","
				).put(
					"thousandsSeparator", "\'"
				)
			).toString());

		return ddmFormFieldRenderingContext;
	}

	private void _setUpJSONFactory() throws Exception {
		PowerMockito.field(
			NumericInputMaskDDMFormFieldTemplateContextContributor.class,
			"_jsonFactory"
		).set(
			_numericInputMaskDDMFormFieldTemplateContextContributor,
			new JSONFactoryImpl()
		);
	}

	private void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = mock(Language.class);

		when(
			language.get(
				Matchers.any(ResourceBundle.class), Matchers.eq("none"))
		).thenReturn(
			"None"
		);

		when(
			language.getAvailableLocales()
		).thenReturn(
			SetUtil.fromArray(new Locale[] {LocaleUtil.US})
		);

		when(
			language.getLanguageId(LocaleUtil.US)
		).thenReturn(
			"en_US"
		);

		languageUtil.setLanguage(language);
	}

	private void _setUpPortal() throws Exception {
		Portal portal = mock(Portal.class);

		PowerMockito.field(
			NumericInputMaskDDMFormFieldTemplateContextContributor.class,
			"_portal"
		).set(
			_numericInputMaskDDMFormFieldTemplateContextContributor, portal
		);
	}

	private final NumericInputMaskDDMFormFieldTemplateContextContributor
		_numericInputMaskDDMFormFieldTemplateContextContributor =
			new NumericInputMaskDDMFormFieldTemplateContextContributor();

}