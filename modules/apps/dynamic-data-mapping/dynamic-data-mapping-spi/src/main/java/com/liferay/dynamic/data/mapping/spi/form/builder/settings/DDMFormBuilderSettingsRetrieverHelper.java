/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.spi.form.builder.settings;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.portal.kernel.json.JSONArray;

import java.util.Locale;

/**
 * @author Gabriel Albuquerque
 */
public interface DDMFormBuilderSettingsRetrieverHelper {

	public String getDDMDataProviderInstanceParameterSettingsURL();

	public String getDDMDataProviderInstancesURL();

	public String getDDMFieldSetDefinitionURL();

	public String getDDMFieldSettingsDDMFormContextURL();

	public String getDDMFormContextProviderURL();

	public String getDDMFunctionsURL();

	public JSONArray getFieldSetsMetadataJSONArray(
		long companyId, long scopeGroupId, long fieldSetClassNameId,
		Locale locale);

	public String getRolesURL();

	public String getSerializedDDMExpressionFunctionsMetadata(Locale locale);

	public String getSerializedDDMFormRules(DDMForm ddmForm);

}