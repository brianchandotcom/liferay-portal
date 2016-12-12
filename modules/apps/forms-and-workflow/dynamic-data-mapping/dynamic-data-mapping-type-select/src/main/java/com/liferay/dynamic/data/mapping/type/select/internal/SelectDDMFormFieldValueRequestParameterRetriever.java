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

package com.liferay.dynamic.data.mapping.type.select.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRequestParameterRetriever;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, property = "ddm.form.field.type.name=select")
public class SelectDDMFormFieldValueRequestParameterRetriever
	implements DDMFormFieldValueRequestParameterRetriever {

	@Override
	public String get(
		HttpServletRequest httpServletRequest, String ddmFormFieldParameterName,
		String defaultDDMFormFieldParameterValue) {

		String[] defaultDDMFormFieldParameterValues =
			getDefaultDDMFormFieldParameterValues(
				defaultDDMFormFieldParameterValue);

		String[] parameterValues = ParamUtil.getParameterValues(
			httpServletRequest, ddmFormFieldParameterName,
			defaultDDMFormFieldParameterValues);

		return jsonFactory.serialize(parameterValues);
	}

	protected String[] getDefaultDDMFormFieldParameterValues(
		String defaultDDMFormFieldParameterValue) {

		if (Validator.isNull(defaultDDMFormFieldParameterValue)) {
			return GetterUtil.DEFAULT_STRING_VALUES;
		}

		return jsonFactory.looseDeserialize(
			defaultDDMFormFieldParameterValue, String[].class);
	}

	@Reference
	protected JSONFactory jsonFactory;

}