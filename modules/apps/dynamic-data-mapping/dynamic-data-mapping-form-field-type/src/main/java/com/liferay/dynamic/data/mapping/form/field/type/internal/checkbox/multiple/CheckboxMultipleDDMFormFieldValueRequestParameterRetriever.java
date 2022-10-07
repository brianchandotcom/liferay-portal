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

package com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox.multiple;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRequestParameterRetriever;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE,
	service = DDMFormFieldValueRequestParameterRetriever.class
)
public class CheckboxMultipleDDMFormFieldValueRequestParameterRetriever
	implements DDMFormFieldValueRequestParameterRetriever {

	@Override
	public String get(
		HttpServletRequest httpServletRequest, String ddmFormFieldParameterName,
		String defaultDDMFormFieldParameterValue) {

		return jsonFactory.serialize(
			_getParameterValues(
				httpServletRequest, ddmFormFieldParameterName,
				_getDefaultDDMFormFieldParameterValues(
					defaultDDMFormFieldParameterValue)));
	}

	@Reference
	protected JSONFactory jsonFactory;

	private String[] _getDefaultDDMFormFieldParameterValues(
		String defaultDDMFormFieldParameterValue) {

		if (Validator.isNull(defaultDDMFormFieldParameterValue) ||
			Objects.equals(defaultDDMFormFieldParameterValue, "[]")) {

			return GetterUtil.DEFAULT_STRING_VALUES;
		}

		try {
			return jsonFactory.looseDeserialize(
				defaultDDMFormFieldParameterValue, String[].class);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return StringUtil.split(defaultDDMFormFieldParameterValue);
		}
	}

	private String[] _getParameterValues(
		HttpServletRequest httpServletRequest, String ddmFormFieldParameterName,
		String[] defaultDDMFormFieldParameterValues) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay.isLifecycleAction()) {
			return ParamUtil.getParameterValues(
				httpServletRequest, ddmFormFieldParameterName);
		}

		return ParamUtil.getParameterValues(
			httpServletRequest, ddmFormFieldParameterName,
			defaultDDMFormFieldParameterValues);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CheckboxMultipleDDMFormFieldValueRequestParameterRetriever.class);

}