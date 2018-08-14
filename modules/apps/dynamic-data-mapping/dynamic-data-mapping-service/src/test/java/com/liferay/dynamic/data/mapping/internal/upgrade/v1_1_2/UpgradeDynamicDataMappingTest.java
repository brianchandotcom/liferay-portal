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

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_2;

import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;

import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.kernel.json.JSONFactory;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Jeyvison Nascimento
 */
@RunWith(MockitoJUnitRunner.class)
public class UpgradeDynamicDataMappingTest {

	@Test
	public void testTransformRadioDDMFormFieldValues() throws Exception {
		UpgradeDynamicDataMapping.RadioDDMFormFieldValueTransformer
			radioDDMFormFieldValueTransformer =
				_getRadioDDMFormFieldValueTransformer();

		Mockito.when(
			_jsonFactory.createJSONArray(Matchers.any(String.class))
		).thenReturn(
			new JSONArrayImpl()
		);

		Mockito.when(
			_ddmFormFieldValue.getValue()
		).thenReturn(
			_value
		);

		Mockito.when(
			_value.getAvailableLocales()
		).thenReturn(
			_getAvailableLocales()
		);

		Mockito.when(
			_value.getString(Mockito.any(Locale.class))
		).thenReturn(
			"value"
		);

		radioDDMFormFieldValueTransformer.transform(_ddmFormFieldValue);

		Mockito.verify(
			_value, atLeastOnce()
		).addString(
			Matchers.any(Locale.class), Matchers.anyString()
		);
	}

	@Test
	public void testTransformRadioDDMFormFieldValuesWithNullValue()
		throws Exception {

		UpgradeDynamicDataMapping.RadioDDMFormFieldValueTransformer
			radioDDMFormFieldValueTransformer =
				_getRadioDDMFormFieldValueTransformer();

		Mockito.when(
			_ddmFormFieldValue.getValue()
		).thenReturn(
			_value
		);

		radioDDMFormFieldValueTransformer.transform(_ddmFormFieldValue);

		Mockito.verify(
			_value, Mockito.never()
		).getString(
			Matchers.any(Locale.class)
		);
	}

	@Test
	public void testTransformSelectDDMFormFieldValues() throws Exception {
		UpgradeDynamicDataMapping.SelectDDMFormFieldValueTransformer
			selectDDMFormFieldValueTransformer =
				_getSelectDDMFormFieldValueTransformer();

		Mockito.when(
			_jsonFactory.createJSONArray(Matchers.any(String.class))
		).thenReturn(
			new JSONArrayImpl()
		);

		Mockito.when(
			_ddmFormFieldValue.getValue()
		).thenReturn(
			_value
		);

		Mockito.when(
			_value.getAvailableLocales()
		).thenReturn(
			_getAvailableLocales()
		);

		Mockito.when(
			_value.getString(Matchers.any(Locale.class))
		).thenReturn(
			"value"
		);

		selectDDMFormFieldValueTransformer.transform(_ddmFormFieldValue);

		Mockito.verify(
			_value, atLeastOnce()
		).addString(
			Matchers.any(Locale.class), Matchers.anyString()
		);
	}

	@Test
	public void testTransformSelectDDMFormFieldValuesWithNullValue()
		throws Exception {

		UpgradeDynamicDataMapping.SelectDDMFormFieldValueTransformer
			selectDDMFormFieldValueTransformer =
				_getSelectDDMFormFieldValueTransformer();

		Mockito.when(
			_ddmFormFieldValue.getValue()
		).thenReturn(
			null
		);

		selectDDMFormFieldValueTransformer.transform(_ddmFormFieldValue);

		Mockito.verify(
			_value, Mockito.never()
		).getString(
			Matchers.any(Locale.class)
		);
	}

	private Set<Locale> _getAvailableLocales() {
		Locale locale = new Locale("pt-BR");

		Set<Locale> locales = new HashSet<>();

		locales.add(locale);

		return locales;
	}

	private UpgradeDynamicDataMapping.RadioDDMFormFieldValueTransformer
		_getRadioDDMFormFieldValueTransformer() {

		UpgradeDynamicDataMapping upgradeDynamicDataMapping =
			_getUpgradeDynamicDataMapping();

		return upgradeDynamicDataMapping.
			new RadioDDMFormFieldValueTransformer();
	}

	private UpgradeDynamicDataMapping.SelectDDMFormFieldValueTransformer
		_getSelectDDMFormFieldValueTransformer() {

		UpgradeDynamicDataMapping upgradeDynamicDataMapping =
			_getUpgradeDynamicDataMapping();

		return upgradeDynamicDataMapping.
			new SelectDDMFormFieldValueTransformer();
	}

	private UpgradeDynamicDataMapping _getUpgradeDynamicDataMapping() {
		return new UpgradeDynamicDataMapping(
			null, null, null, null, _jsonFactory);
	}

	@Mock
	private DDMFormFieldValue _ddmFormFieldValue;

	@Mock
	private JSONFactory _jsonFactory;

	@Mock
	private Value _value;

}