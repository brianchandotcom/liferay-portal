/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.builder.settings;

import com.liferay.dynamic.data.mapping.model.DDMForm;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public class DDMFormBuilderSettingsRequest {

	public static DDMFormBuilderSettingsRequest with(
		long companyId, long scopeGroupId, long fieldSetClassNameId,
		DDMForm ddmForm, Locale locale) {

		DDMFormBuilderSettingsRequest ddmFormBuilderSettingsRequest =
			new DDMFormBuilderSettingsRequest();

		ddmFormBuilderSettingsRequest.setCompanyId(companyId);
		ddmFormBuilderSettingsRequest.setScopeGroupId(scopeGroupId);
		ddmFormBuilderSettingsRequest.setFieldSetClassNameId(
			fieldSetClassNameId);
		ddmFormBuilderSettingsRequest.setDDMForm(ddmForm);
		ddmFormBuilderSettingsRequest.setLocale(locale);

		return ddmFormBuilderSettingsRequest;
	}

	public void addProperty(String key, Object value) {
		_properties.put(key, value);
	}

	public long getCompanyId() {
		return getProperty("companyId");
	}

	public DDMForm getDDMForm() {
		return getProperty("ddmForm");
	}

	public long getFieldSetClassNameId() {
		return getProperty("fieldSetClassNameId");
	}

	public Locale getLocale() {
		return getProperty("locale");
	}

	public <T> T getProperty(String name) {
		return (T)_properties.get(name);
	}

	public long getScopeGroupId() {
		return getProperty("scopeGroupId");
	}

	public void setCompanyId(long companyId) {
		addProperty("companyId", companyId);
	}

	public void setDDMForm(DDMForm ddmForm) {
		addProperty("ddmForm", ddmForm);
	}

	public void setFieldSetClassNameId(long fieldSetClassNameId) {
		addProperty("fieldSetClassNameId", fieldSetClassNameId);
	}

	public void setLocale(Locale locale) {
		addProperty("locale", locale);
	}

	public void setScopeGroupId(long scopeGroupId) {
		addProperty("scopeGroupId", scopeGroupId);
	}

	private DDMFormBuilderSettingsRequest() {
	}

	private final Map<String, Object> _properties = new HashMap<>();

}