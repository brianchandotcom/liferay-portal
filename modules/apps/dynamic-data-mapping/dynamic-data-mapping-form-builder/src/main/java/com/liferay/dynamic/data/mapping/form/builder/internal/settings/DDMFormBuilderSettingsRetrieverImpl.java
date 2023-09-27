/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.builder.internal.settings;

import com.liferay.dynamic.data.mapping.form.builder.settings.DDMFormBuilderSettingsRequest;
import com.liferay.dynamic.data.mapping.form.builder.settings.DDMFormBuilderSettingsResponse;
import com.liferay.dynamic.data.mapping.form.builder.settings.DDMFormBuilderSettingsRetriever;
import com.liferay.dynamic.data.mapping.spi.form.builder.settings.DDMFormBuilderSettingsRetrieverHelper;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(service = DDMFormBuilderSettingsRetriever.class)
public class DDMFormBuilderSettingsRetrieverImpl
	implements DDMFormBuilderSettingsRetriever {

	@Override
	public DDMFormBuilderSettingsResponse getSettings(
		DDMFormBuilderSettingsRequest ddmFormBuilderSettingsRequest) {

		DDMFormBuilderSettingsResponse ddmFormBuilderSettings =
			new DDMFormBuilderSettingsResponse();

		ddmFormBuilderSettings.setDataProviderInstanceParameterSettingsURL(
			_ddmFormBuilderSettingsRetrieverHelper.
				getDDMDataProviderInstanceParameterSettingsURL());
		ddmFormBuilderSettings.setDataProviderInstancesURL(
			_ddmFormBuilderSettingsRetrieverHelper.
				getDDMDataProviderInstancesURL());
		ddmFormBuilderSettings.setFieldSetDefinitionURL(
			_ddmFormBuilderSettingsRetrieverHelper.
				getDDMFieldSetDefinitionURL());
		ddmFormBuilderSettings.setFieldSettingsDDMFormContextURL(
			_ddmFormBuilderSettingsRetrieverHelper.
				getDDMFieldSettingsDDMFormContextURL());
		ddmFormBuilderSettings.setFormContextProviderURL(
			_ddmFormBuilderSettingsRetrieverHelper.
				getDDMFormContextProviderURL());
		ddmFormBuilderSettings.setFunctionsURL(
			_ddmFormBuilderSettingsRetrieverHelper.getDDMFunctionsURL());
		ddmFormBuilderSettings.setRolesURL(
			_ddmFormBuilderSettingsRetrieverHelper.getRolesURL());

		Locale locale = ddmFormBuilderSettingsRequest.getLocale();

		ddmFormBuilderSettings.setFunctionsMetadata(
			_ddmFormBuilderSettingsRetrieverHelper.
				getSerializedDDMExpressionFunctionsMetadata(locale));
		ddmFormBuilderSettings.setFieldSets(
			_ddmFormBuilderSettingsRetrieverHelper.
				getFieldSetsMetadataJSONArray(
					ddmFormBuilderSettingsRequest.getCompanyId(),
					ddmFormBuilderSettingsRequest.getScopeGroupId(),
					ddmFormBuilderSettingsRequest.getFieldSetClassNameId(),
					locale));

		ddmFormBuilderSettings.setSerializedDDMFormRules(
			_ddmFormBuilderSettingsRetrieverHelper.getSerializedDDMFormRules(
				ddmFormBuilderSettingsRequest.getDDMForm()));

		return ddmFormBuilderSettings;
	}

	@Reference
	private DDMFormBuilderSettingsRetrieverHelper
		_ddmFormBuilderSettingsRetrieverHelper;

}