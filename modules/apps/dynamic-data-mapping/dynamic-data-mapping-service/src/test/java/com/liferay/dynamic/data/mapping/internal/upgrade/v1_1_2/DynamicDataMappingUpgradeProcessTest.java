/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_2;

import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

/**
 * @author Jeyvison Nascimento
 */
public class DynamicDataMappingUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testTransformRadioDDMFormFieldValues() throws Exception {
		DynamicDataMappingUpgradeProcess.RadioDDMFormFieldValueTransformer
			radioDDMFormFieldValueTransformer =
				_getRadioDDMFormFieldValueTransformer();

		Mockito.when(
			_jsonFactory.createJSONArray(Mockito.any(String.class))
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
			_value, VerificationModeFactory.atLeastOnce()
		).addString(
			Mockito.any(Locale.class), Mockito.anyString()
		);
	}

	@Test
	public void testTransformRadioDDMFormFieldValuesWithNullValue()
		throws Exception {

		DynamicDataMappingUpgradeProcess.RadioDDMFormFieldValueTransformer
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
			Mockito.any(Locale.class)
		);
	}

	@Test
	public void testTransformSelectDDMFormFieldValues() throws Exception {
		DynamicDataMappingUpgradeProcess.SelectDDMFormFieldValueTransformer
			selectDDMFormFieldValueTransformer =
				_getSelectDDMFormFieldValueTransformer();

		Mockito.when(
			_jsonFactory.createJSONArray(Mockito.any(String.class))
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

		selectDDMFormFieldValueTransformer.transform(_ddmFormFieldValue);

		Mockito.verify(
			_value, VerificationModeFactory.atLeastOnce()
		).addString(
			Mockito.any(Locale.class), Mockito.anyString()
		);
	}

	@Test
	public void testTransformSelectDDMFormFieldValuesWithNullValue()
		throws Exception {

		DynamicDataMappingUpgradeProcess.SelectDDMFormFieldValueTransformer
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
			Mockito.any(Locale.class)
		);
	}

	private Set<Locale> _getAvailableLocales() {
		Locale locale = new Locale("pt-BR");

		Set<Locale> locales = new HashSet<>();

		locales.add(locale);

		return locales;
	}

	private DynamicDataMappingUpgradeProcess
		_getDynamicDataMappingUpgradeProcess() {

		return new DynamicDataMappingUpgradeProcess(
			null, null, null, null, _jsonFactory);
	}

	private DynamicDataMappingUpgradeProcess.RadioDDMFormFieldValueTransformer
		_getRadioDDMFormFieldValueTransformer() {

		DynamicDataMappingUpgradeProcess upgradeDynamicDataMapping =
			_getDynamicDataMappingUpgradeProcess();

		return upgradeDynamicDataMapping.
			new RadioDDMFormFieldValueTransformer();
	}

	private DynamicDataMappingUpgradeProcess.SelectDDMFormFieldValueTransformer
		_getSelectDDMFormFieldValueTransformer() {

		DynamicDataMappingUpgradeProcess upgradeDynamicDataMapping =
			_getDynamicDataMappingUpgradeProcess();

		return upgradeDynamicDataMapping.
			new SelectDDMFormFieldValueTransformer();
	}

	private final DDMFormFieldValue _ddmFormFieldValue = Mockito.mock(
		DDMFormFieldValue.class);
	private final JSONFactory _jsonFactory = Mockito.mock(JSONFactory.class);
	private final Value _value = Mockito.mock(Value.class);

}