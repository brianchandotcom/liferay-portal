/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.fragment.util.configuration.FragmentConfigurationField;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParserUtil;
import com.liferay.headless.admin.site.client.dto.v1_0.CheckboxFragmentConfigurationFieldValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentConfigurationFieldValue;
import com.liferay.headless.admin.site.client.dto.v1_0.LengthFragmentConfigurationFieldValue;
import com.liferay.headless.admin.site.client.dto.v1_0.SelectFragmentConfigurationFieldValue;
import com.liferay.headless.admin.site.client.dto.v1_0.TextFragmentConfigurationFieldValue;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Lourdes Fernández Besada
 */
public class FragmentConfigurationFieldValueTestUtil {

	public static Map<String, FragmentConfigurationFieldValue>
		getFragmentConfigurationFieldValuesMap(
			JSONObject configurationJSONObject,
			Map<String, Object> objectsMap) {

		Map<String, FragmentConfigurationFieldValue> map = new HashMap<>();

		if (configurationJSONObject == null) {
			return map;
		}

		for (FragmentConfigurationField fragmentConfigurationField :
				FragmentEntryConfigurationParserUtil.
					getFragmentConfigurationFields(configurationJSONObject)) {

			Object object = objectsMap.get(
				fragmentConfigurationField.getName());

			if (object == null) {
				continue;
			}

			map.put(
				fragmentConfigurationField.getName(),
				_getFragmentConfigurationFieldValue(
					fragmentConfigurationField, object));
		}

		return map;
	}

	private static FragmentConfigurationFieldValue
		_getCheckboxFragmentConfigurationFieldValue(
			boolean localizable, Object object) {

		CheckboxFragmentConfigurationFieldValue
			checkboxFragmentConfigurationFieldValue =
				new CheckboxFragmentConfigurationFieldValue() {
					{
						setType(() -> Type.CHECKBOX);
					}
				};

		if (localizable) {
			checkboxFragmentConfigurationFieldValue.setValue_i18n(
				HashMapBuilder.put(
					LocaleUtil.toLanguageId(LocaleUtil.getDefault()),
					GetterUtil.getBoolean(object)
				).build());
		}
		else {
			checkboxFragmentConfigurationFieldValue.setValue(
				GetterUtil.getBoolean(object));
		}

		return checkboxFragmentConfigurationFieldValue;
	}

	private static FragmentConfigurationFieldValue
		_getFragmentConfigurationFieldValue(
			FragmentConfigurationField fragmentConfigurationField,
			Object value) {

		String type = fragmentConfigurationField.getType();

		if (Objects.equals(type, "checkbox")) {
			return _getCheckboxFragmentConfigurationFieldValue(
				fragmentConfigurationField.isLocalizable(), value);
		}

		if (Objects.equals(type, "length")) {
			return _getLengthFragmentConfigurationFieldValue(
				fragmentConfigurationField.isLocalizable(), value);
		}

		if (Objects.equals(type, "select")) {
			return _getSelectFragmentConfigurationFieldValue(
				fragmentConfigurationField.isLocalizable(), value);
		}

		if (Objects.equals(type, "text")) {
			return _getTextFragmentConfigurationFieldValue(
				fragmentConfigurationField.isLocalizable(), value);
		}

		return null;
	}

	private static FragmentConfigurationFieldValue
		_getLengthFragmentConfigurationFieldValue(
			boolean localizable, Object object) {

		LengthFragmentConfigurationFieldValue
			lengthFragmentConfigurationFieldValue =
				new LengthFragmentConfigurationFieldValue() {
					{
						setType(() -> Type.LENGTH);
					}
				};

		if (localizable) {
			lengthFragmentConfigurationFieldValue.setValue_i18n(
				HashMapBuilder.put(
					LocaleUtil.toLanguageId(LocaleUtil.getDefault()),
					GetterUtil.getString(object)
				).build());
		}
		else {
			lengthFragmentConfigurationFieldValue.setValue(
				GetterUtil.getString(object));
		}

		return lengthFragmentConfigurationFieldValue;
	}

	private static FragmentConfigurationFieldValue
		_getSelectFragmentConfigurationFieldValue(
			boolean localizable, Object object) {

		SelectFragmentConfigurationFieldValue
			selectFragmentConfigurationFieldValue =
				new SelectFragmentConfigurationFieldValue() {
					{
						setType(() -> Type.SELECT);
					}
				};

		if (localizable) {
			selectFragmentConfigurationFieldValue.setValue_i18n(
				HashMapBuilder.put(
					LocaleUtil.toLanguageId(LocaleUtil.getDefault()),
					GetterUtil.getString(object)
				).build());
		}
		else {
			selectFragmentConfigurationFieldValue.setValue(
				GetterUtil.getString(object));
		}

		return selectFragmentConfigurationFieldValue;
	}

	private static FragmentConfigurationFieldValue
		_getTextFragmentConfigurationFieldValue(
			boolean localizable, Object object) {

		TextFragmentConfigurationFieldValue
			textFragmentConfigurationFieldValue =
				new TextFragmentConfigurationFieldValue() {
					{
						setType(() -> Type.TEXT);
					}
				};

		if (localizable) {
			textFragmentConfigurationFieldValue.setValue_i18n(
				HashMapBuilder.put(
					LocaleUtil.toLanguageId(LocaleUtil.getDefault()),
					GetterUtil.getString(object)
				).build());
		}
		else {
			textFragmentConfigurationFieldValue.setValue(
				GetterUtil.getString(object));
		}

		return textFragmentConfigurationFieldValue;
	}

}