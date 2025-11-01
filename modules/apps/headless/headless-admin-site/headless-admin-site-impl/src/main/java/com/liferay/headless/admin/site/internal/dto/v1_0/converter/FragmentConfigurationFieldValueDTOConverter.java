/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.fragment.util.configuration.FragmentConfigurationField;
import com.liferay.headless.admin.site.dto.v1_0.CheckboxFragmentConfigurationFieldValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentConfigurationFieldValue;
import com.liferay.headless.admin.site.dto.v1_0.LengthFragmentConfigurationFieldValue;
import com.liferay.headless.admin.site.dto.v1_0.SelectFragmentConfigurationFieldValue;
import com.liferay.headless.admin.site.dto.v1_0.TextFragmentConfigurationFieldValue;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.FragmentConfigurationFieldValueTypeUtil;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.LocalizedValueUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "dto.class.name=com.liferay.fragment.util.configuration.FragmentConfigurationField",
	service = DTOConverter.class
)
public class FragmentConfigurationFieldValueDTOConverter
	implements DTOConverter
		<FragmentConfigurationField, FragmentConfigurationFieldValue> {

	@Override
	public String getContentType() {
		return FragmentConfigurationFieldValue.class.getSimpleName();
	}

	@Override
	public FragmentConfigurationFieldValue toDTO(
			DTOConverterContext dtoConverterContext,
			FragmentConfigurationField fragmentConfigurationField)
		throws Exception {

		if (dtoConverterContext == null) {
			return null;
		}

		Object fragmentFragmentConfigurationFieldValue =
			dtoConverterContext.getAttribute(
				"fragmentFragmentConfigurationFieldValue");

		if (fragmentFragmentConfigurationFieldValue == null) {
			return null;
		}

		FragmentConfigurationFieldValue.Type type =
			FragmentConfigurationFieldValueTypeUtil.toExternalType(
				fragmentConfigurationField.getType());

		if (Objects.equals(
				type, FragmentConfigurationFieldValue.Type.CHECKBOX)) {

			return _getCheckboxFragmentConfigurationFieldValue(
				fragmentConfigurationField,
				fragmentFragmentConfigurationFieldValue);
		}

		if (Objects.equals(type, FragmentConfigurationFieldValue.Type.LENGTH)) {
			return _getLengthFragmentConfigurationFieldValue(
				fragmentConfigurationField,
				fragmentFragmentConfigurationFieldValue);
		}

		if (Objects.equals(type, FragmentConfigurationFieldValue.Type.TEXT)) {
			return _getTextFragmentConfigurationFieldValue(
				fragmentConfigurationField,
				fragmentFragmentConfigurationFieldValue);
		}

		if (Objects.equals(type, FragmentConfigurationFieldValue.Type.SELECT)) {
			return _getSelectFragmentConfigurationFieldValue(
				fragmentConfigurationField,
				fragmentFragmentConfigurationFieldValue);
		}

		return null;
	}

	private FragmentConfigurationFieldValue
		_getCheckboxFragmentConfigurationFieldValue(
			FragmentConfigurationField fragmentConfigurationField,
			Object fragmentFragmentConfigurationFieldValue) {

		CheckboxFragmentConfigurationFieldValue
			checkboxFragmentConfigurationFieldValue =
				new CheckboxFragmentConfigurationFieldValue();

		if (fragmentConfigurationField.isLocalizable()) {
			JSONObject jsonObject =
				(JSONObject)fragmentFragmentConfigurationFieldValue;

			checkboxFragmentConfigurationFieldValue.setValue_i18n(
				() -> LocalizedValueUtil.toLocalizedValues(
					jsonObject, key -> jsonObject.getBoolean(key)));
		}
		else {
			checkboxFragmentConfigurationFieldValue.setValue(
				() -> GetterUtil.getBoolean(
					fragmentFragmentConfigurationFieldValue));
		}

		return checkboxFragmentConfigurationFieldValue;
	}

	private FragmentConfigurationFieldValue
		_getLengthFragmentConfigurationFieldValue(
			FragmentConfigurationField fragmentConfigurationField,
			Object fragmentFragmentConfigurationFieldValue) {

		LengthFragmentConfigurationFieldValue
			lengthFragmentConfigurationFieldValue =
				new LengthFragmentConfigurationFieldValue() {
					{
						setType(Type.LENGTH);
					}
				};

		if (fragmentConfigurationField.isLocalizable()) {
			JSONObject jsonObject =
				(JSONObject)fragmentFragmentConfigurationFieldValue;

			lengthFragmentConfigurationFieldValue.setValue_i18n(
				() -> LocalizedValueUtil.toLocalizedValues(jsonObject));
		}
		else {
			lengthFragmentConfigurationFieldValue.setValue(
				() -> GetterUtil.getString(
					fragmentFragmentConfigurationFieldValue));
		}

		return lengthFragmentConfigurationFieldValue;
	}

	private FragmentConfigurationFieldValue
		_getSelectFragmentConfigurationFieldValue(
			FragmentConfigurationField fragmentConfigurationField,
			Object fragmentFragmentConfigurationFieldValue) {

		SelectFragmentConfigurationFieldValue
			selectFragmentConfigurationFieldValue =
				new SelectFragmentConfigurationFieldValue() {
					{
						setType(Type.SELECT);
					}
				};

		if (fragmentConfigurationField.isLocalizable()) {
			JSONObject jsonObject =
				(JSONObject)fragmentFragmentConfigurationFieldValue;

			selectFragmentConfigurationFieldValue.setValue_i18n(
				() -> LocalizedValueUtil.toLocalizedValues(jsonObject));
		}
		else {
			selectFragmentConfigurationFieldValue.setValue(
				() -> GetterUtil.getString(
					fragmentFragmentConfigurationFieldValue));
		}

		return selectFragmentConfigurationFieldValue;
	}

	private FragmentConfigurationFieldValue
		_getTextFragmentConfigurationFieldValue(
			FragmentConfigurationField fragmentConfigurationField,
			Object fragmentFragmentConfigurationFieldValue) {

		TextFragmentConfigurationFieldValue
			textFragmentConfigurationFieldValue =
				new TextFragmentConfigurationFieldValue() {
					{
						setType(Type.TEXT);
					}
				};

		if (fragmentConfigurationField.isLocalizable()) {
			JSONObject jsonObject =
				(JSONObject)fragmentFragmentConfigurationFieldValue;

			textFragmentConfigurationFieldValue.setValue_i18n(
				() -> LocalizedValueUtil.toLocalizedValues(jsonObject));
		}
		else {
			textFragmentConfigurationFieldValue.setValue(
				() -> GetterUtil.getString(
					fragmentFragmentConfigurationFieldValue));
		}

		return textFragmentConfigurationFieldValue;
	}

}