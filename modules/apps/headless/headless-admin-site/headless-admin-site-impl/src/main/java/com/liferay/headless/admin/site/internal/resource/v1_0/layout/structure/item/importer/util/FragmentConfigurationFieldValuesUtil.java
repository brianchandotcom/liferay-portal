/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.util;

import com.liferay.fragment.util.configuration.FragmentConfigurationField;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParserUtil;
import com.liferay.headless.admin.site.dto.v1_0.CheckboxFragmentConfigurationFieldValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentConfigurationFieldValue;
import com.liferay.headless.admin.site.dto.v1_0.LengthFragmentConfigurationFieldValue;
import com.liferay.headless.admin.site.dto.v1_0.SelectFragmentConfigurationFieldValue;
import com.liferay.headless.admin.site.dto.v1_0.TextFragmentConfigurationFieldValue;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.FragmentConfigurationFieldValueTypeUtil;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.LocalizedValueUtil;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Map;
import java.util.Objects;

/**
 * @author Lourdes Fernández Besada
 */
public class FragmentConfigurationFieldValuesUtil {

	public static JSONObject getFreeMarkerFragmentEntryProcessorJSONObject(
			String configuration,
			Map<String, FragmentConfigurationFieldValue>
				fragmentConfigurationFieldValuesMap)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (fragmentConfigurationFieldValuesMap == null) {
			return jsonObject;
		}

		JSONObject configurationJSONObject = JSONFactoryUtil.createJSONObject(
			configuration);

		for (FragmentConfigurationField fragmentConfigurationField :
				FragmentEntryConfigurationParserUtil.
					getFragmentConfigurationFields(configurationJSONObject)) {

			FragmentConfigurationFieldValue fragmentConfigurationFieldValue =
				fragmentConfigurationFieldValuesMap.get(
					fragmentConfigurationField.getName());

			if (fragmentConfigurationFieldValue == null) {
				continue;
			}

			if (!Objects.equals(
					fragmentConfigurationFieldValue.getType(),
					FragmentConfigurationFieldValueTypeUtil.toExternalType(
						fragmentConfigurationField.getType()))) {

				throw new UnsupportedOperationException();
			}

			jsonObject.put(
				fragmentConfigurationField.getName(),
				_fromFragmentConfigurationFieldValue(
					fragmentConfigurationFieldValue,
					fragmentConfigurationField));
		}

		return jsonObject;
	}

	private static Object _fromFragmentConfigurationFieldValue(
			FragmentConfigurationFieldValue fragmentConfigurationFieldValue,
			FragmentConfigurationField fragmentConfigurationField)
		throws Exception {

		if (Objects.equals(
				fragmentConfigurationFieldValue.getType(),
				FragmentConfigurationFieldValue.Type.CHECKBOX)) {

			CheckboxFragmentConfigurationFieldValue
				checkboxFragmentConfigurationFieldValue =
					(CheckboxFragmentConfigurationFieldValue)
						fragmentConfigurationFieldValue;

			return _getConfigurationObject(
				fragmentConfigurationField.isLocalizable(),
				checkboxFragmentConfigurationFieldValue.getValue(),
				checkboxFragmentConfigurationFieldValue.getValue_i18n());
		}

		if (Objects.equals(
				fragmentConfigurationFieldValue.getType(),
				FragmentConfigurationFieldValue.Type.LENGTH)) {

			LengthFragmentConfigurationFieldValue
				lengthFragmentConfigurationFieldValue =
					(LengthFragmentConfigurationFieldValue)
						fragmentConfigurationFieldValue;

			return _getConfigurationObject(
				fragmentConfigurationField.isLocalizable(),
				lengthFragmentConfigurationFieldValue.getValue(),
				lengthFragmentConfigurationFieldValue.getValue_i18n());
		}

		if (Objects.equals(
				fragmentConfigurationFieldValue.getType(),
				FragmentConfigurationFieldValue.Type.SELECT)) {

			SelectFragmentConfigurationFieldValue
				selectFragmentConfigurationFieldValue =
					(SelectFragmentConfigurationFieldValue)
						fragmentConfigurationFieldValue;

			JSONObject typeOptionsJSONObject =
				fragmentConfigurationField.getTypeOptionsJSONObject();

			JSONArray validValuesJSONArray = typeOptionsJSONObject.getJSONArray(
				"validValues");

			return _getConfigurationObject(
				fragmentConfigurationField.isLocalizable(),
				value -> {
					if (_isValidValue(validValuesJSONArray, value)) {
						return value;
					}

					throw new UnsupportedOperationException();
				},
				selectFragmentConfigurationFieldValue.getValue(),
				selectFragmentConfigurationFieldValue.getValue_i18n());
		}

		if (Objects.equals(
				fragmentConfigurationFieldValue.getType(),
				FragmentConfigurationFieldValue.Type.TEXT)) {

			TextFragmentConfigurationFieldValue
				textFragmentConfigurationFieldValue =
					(TextFragmentConfigurationFieldValue)
						fragmentConfigurationFieldValue;

			return _getConfigurationObject(
				fragmentConfigurationField.isLocalizable(),
				textFragmentConfigurationFieldValue.getValue(),
				textFragmentConfigurationFieldValue.getValue_i18n());
		}

		return null;
	}

	private static <T> Object _getConfigurationObject(
			boolean localizable, T value, Map<String, T> valuesMap)
		throws Exception {

		return _getConfigurationObject(
			localizable, curValue -> curValue, value, valuesMap);
	}

	private static <T, R> Object _getConfigurationObject(
			boolean localizable, UnsafeFunction<T, R, Exception> unsafeFunction,
			T value, Map<String, T> valuesMap)
		throws Exception {

		if (!localizable) {
			return unsafeFunction.apply(value);
		}

		return LocalizedValueUtil.toJSONObject(valuesMap, unsafeFunction);
	}

	private static boolean _isValidValue(
		JSONArray validValuesJSONArray, String value) {

		for (int i = 0; i < validValuesJSONArray.length(); i++) {
			JSONObject jsonObject = validValuesJSONArray.getJSONObject(i);

			if (Objects.equals(jsonObject.getString("value"), value)) {
				return true;
			}
		}

		return false;
	}

}