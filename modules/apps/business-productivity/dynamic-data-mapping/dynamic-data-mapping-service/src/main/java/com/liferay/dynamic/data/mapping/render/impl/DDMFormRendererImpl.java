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

package com.liferay.dynamic.data.mapping.render.impl;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRendererRegistryUtil;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.render.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.impl.DDMFieldsCounter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marcellus Tavares
 */
public class DDMFormRendererImpl implements DDMFormRenderer {

	@Override
	public String render(
			DDMForm ddmForm,
			DDMFormFieldRenderingContext ddmFormFieldRenderingContext)
		throws PortalException {

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		StringBundler sb = new StringBundler(ddmFormFields.size());

		for (DDMFormField ddmFormField : ddmFormFields) {
			if (isDDMFormFieldSkippable(
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

		clearDDMFieldsCounter(ddmFormFieldRenderingContext);

		return sb.toString();
	}

	protected void clearDDMFieldsCounter(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		HttpServletRequest request =
			ddmFormFieldRenderingContext.getHttpServletRequest();

		String fieldsCounterKey =
			ddmFormFieldRenderingContext.getPortletNamespace() +
				ddmFormFieldRenderingContext.getNamespace() + "fieldsCount";

		DDMFieldsCounter ddmFieldsCounter =
			(DDMFieldsCounter)request.getAttribute(fieldsCounterKey);

		if (ddmFieldsCounter != null) {
			ddmFieldsCounter.clear();
		}
	}

	protected boolean isDDMFormFieldSkippable(
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

			if (!isDDMFormFieldSkippable(
					nestedDDMFormField, ddmFormFieldRenderingContext)) {

				return false;
			}
		}

		return true;
	}

}