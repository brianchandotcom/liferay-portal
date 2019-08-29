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

package com.liferay.dynamic.data.mapping.render;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDMFieldsCounter;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marcellus Tavares
 */
public class DDMFormRendererUtil {

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public static DDMFormRenderer getDDMFormRenderer() {
		return null;
	}

	public static String render(
			DDMForm ddmForm,
			DDMFormFieldRenderingContext ddmFormFieldRenderingContext)
		throws PortalException {

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		StringBundler sb = new StringBundler(ddmFormFields.size());

		for (DDMFormField ddmFormField : ddmFormFields) {
			if (_isDDMFormFieldSkippable(
					ddmFormField, ddmFormFieldRenderingContext)) {

				continue;
			}

			DDMFormFieldRenderer ddmFormFieldRenderer =
				DDMFormFieldRendererRegistryUtil.getDDMFormFieldRenderer(
					ddmFormField.getType());

			sb.append(
				ddmFormFieldRenderer.render(
					ddmFormField, ddmFormFieldRenderingContext));
		}

		_clearDDMFieldsCounter(ddmFormFieldRenderingContext);

		return sb.toString();
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public void setDDMFormRenderer(DDMFormRenderer ddmFormRenderer) {
	}

	private static void _clearDDMFieldsCounter(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		HttpServletRequest httpServletRequest =
			ddmFormFieldRenderingContext.getHttpServletRequest();

		String fieldsCounterKey =
			ddmFormFieldRenderingContext.getPortletNamespace() +
				ddmFormFieldRenderingContext.getNamespace() + "fieldsCount";

		DDMFieldsCounter ddmFieldsCounter =
			(DDMFieldsCounter)httpServletRequest.getAttribute(fieldsCounterKey);

		if (ddmFieldsCounter != null) {
			ddmFieldsCounter.clear();
		}
	}

	private static boolean _isDDMFormFieldSkippable(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		if (!ddmFormFieldRenderingContext.isReadOnly() ||
			ddmFormFieldRenderingContext.isShowEmptyFieldLabel()) {

			return false;
		}

		Fields fields = ddmFormFieldRenderingContext.getFields();

		if (fields.contains(ddmFormField.getName())) {
			return false;
		}

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			if (!_isDDMFormFieldSkippable(
					nestedDDMFormField, ddmFormFieldRenderingContext)) {

				return false;
			}
		}

		return true;
	}

}