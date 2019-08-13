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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DDMDataProviderTestUtil {

	public static DDMDataProviderInstance createDDMRestDataProviderInstance(
			Group group,
			List<DDMDataProviderInputParametersSettings> inputParameterSettings,
			List<DDMDataProviderOutputParametersSettings>
				outputParameterSettings)
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		DDMDataProvider[] ddmDataProviders = registry.getServices(
			"com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider",
			"(ddm.data.provider.type=rest)");

		DDMDataProvider restDDMDataProvider = ddmDataProviders[0];

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
				"http://localhost:8080/api/jsonws/country/get-countries"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"username", "test@liferay.com"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"timeout", "1000"));

		if (inputParameterSettings != null) {
			for (DDMDataProviderInputParametersSettings inputParameterSetting :
					inputParameterSettings) {

				ddmFormValues.addDDMFormFieldValue(
					createInputParameter(inputParameterSetting));
			}
		}

		if (outputParameterSettings != null) {
			for (DDMDataProviderOutputParametersSettings
					outputParameterSetting : outputParameterSettings) {

				ddmFormValues.addDDMFormFieldValue(
					createOutputParameter(outputParameterSetting));
			}
		}

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getSiteDefault(), "Data provider");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		return DDMDataProviderInstanceLocalServiceUtil.addDataProviderInstance(
			TestPropsValues.getUserId(), group.getGroupId(), nameMap, nameMap,
			ddmFormValues, "rest", serviceContext);
	}

	protected static DDMFormFieldValue createInputParameter(
		DDMDataProviderInputParametersSettings inputParameterSetting) {

		DDMFormFieldValue inputParameters =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"inputParameters", null);

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterLabel",
				inputParameterSetting.inputParameterLabel()));

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterName",
				inputParameterSetting.inputParameterName()));

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterType",
				inputParameterSetting.inputParameterType()));

		return inputParameters;
	}

	protected static DDMFormFieldValue createOutputParameter(
		DDMDataProviderOutputParametersSettings outputParameterSetting) {

		DDMFormFieldValue outputParameters =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"outputParameters", null);

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterName",
				outputParameterSetting.outputParameterName()));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterPath",
				outputParameterSetting.outputParameterPath()));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterType",
				outputParameterSetting.outputParameterType()));

		return outputParameters;
	}

}