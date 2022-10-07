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

package com.liferay.dynamic.data.mapping.form.field.type.internal.grid;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRequestParameterRetriever;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Queiroz
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.GRID,
	service = DDMFormFieldValueRequestParameterRetriever.class
)
public class GridDDMFormFieldValueRequestParameterRetriever
	implements DDMFormFieldValueRequestParameterRetriever {

	@Override
	public String get(
		HttpServletRequest httpServletRequest, String ddmFormFieldParameterName,
		String defaultDDMFormFieldParameterValue) {

		JSONObject jsonObject = jsonFactory.createJSONObject();

		String[] parameterValues = httpServletRequest.getParameterValues(
			ddmFormFieldParameterName);

		if (ArrayUtil.isEmpty(parameterValues)) {
			return jsonObject.toString();
		}

		if (parameterValues.length == 1) {
			jsonObject = Optional.ofNullable(
				getJSONObject(_log, parameterValues[0])
			).orElse(
				jsonObject
			);
		}

		for (String parameterValue : parameterValues) {
			if (parameterValue.isEmpty() ||
				!parameterValue.contains(StringPool.SEMICOLON)) {

				continue;
			}

			String[] parameterValueParts = parameterValue.split(
				StringPool.SEMICOLON);

			jsonObject.put(parameterValueParts[0], parameterValueParts[1]);
		}

		return jsonObject.toString();
	}

	@Reference
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		GridDDMFormFieldValueRequestParameterRetriever.class);

}