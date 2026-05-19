/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.test.util;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInputParametersSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderOutputParametersSettings;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DDMDataProviderTestUtil {

	public static DDMDataProviderInstance createDDMRestDataProviderInstance(
			DDMDataProvider restDDMDataProvider, Group group,
			List<DDMDataProviderInputParametersSettings>
				inputParametersSettingsList,
			List<DDMDataProviderOutputParametersSettings>
				outputParametersSettingsList)
		throws Exception {

		DDMForm ddmForm = DDMFormFactory.create(
			restDDMDataProvider.getSettings());

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"cacheable", Boolean.FALSE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterable", Boolean.FALSE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterParameterName", StringPool.BLANK));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"pagination", Boolean.TRUE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"password", "test"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"paginationEndParameterName", "end"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"paginationStartParameterName", "start"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"url",
				"http://localhost:" + PortalUtil.getPortalServerPort(false) +
					"/api/jsonws/country/get-countries"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"username", "test@liferay.com"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"timeout", "30000"));

		if (inputParametersSettingsList != null) {
			for (DDMDataProviderInputParametersSettings
					inputParametersSettings : inputParametersSettingsList) {

				ddmFormValues.addDDMFormFieldValue(
					createInputParameter(inputParametersSettings));
			}
		}

		if (outputParametersSettingsList != null) {
			for (DDMDataProviderOutputParametersSettings
					outputParametersSettings : outputParametersSettingsList) {

				ddmFormValues.addDDMFormFieldValue(
					createOutputParameter(outputParametersSettings));
			}
		}

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.getSiteDefault(), "Data provider"
		).build();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		return DDMDataProviderInstanceLocalServiceUtil.addDataProviderInstance(
			TestPropsValues.getUserId(), group.getGroupId(), nameMap, nameMap,
			ddmFormValues, "rest", serviceContext);
	}

	protected static DDMFormFieldValue createInputParameter(
		DDMDataProviderInputParametersSettings inputParametersSettings) {

		DDMFormFieldValue inputParameters =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"inputParameters", null);

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterLabel",
				inputParametersSettings.inputParameterLabel()));

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterName",
				inputParametersSettings.inputParameterName()));

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterType",
				inputParametersSettings.inputParameterType()));

		return inputParameters;
	}

	protected static DDMFormFieldValue createOutputParameter(
		DDMDataProviderOutputParametersSettings outputParametersSettings) {

		DDMFormFieldValue outputParameters =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"outputParameters", null);

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterName",
				outputParametersSettings.outputParameterName()));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterPath",
				outputParametersSettings.outputParameterPath()));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterType",
				outputParametersSettings.outputParameterType()));

		return outputParameters;
	}

}