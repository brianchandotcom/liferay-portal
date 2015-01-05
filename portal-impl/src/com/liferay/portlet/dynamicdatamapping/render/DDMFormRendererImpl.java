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

package com.liferay.portlet.dynamicdatamapping.render;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;

import java.util.List;

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
			DDMFormFieldRenderer ddmFormFieldRenderer =
				DDMFormFieldRendererRegistryUtil.getDDMFormFieldRenderer(
					ddmFormField.getType());

			sb.append(
				ddmFormFieldRenderer.render(
					ddmFormField, ddmFormFieldRenderingContext));
		}

		return sb.toString();
	}

}