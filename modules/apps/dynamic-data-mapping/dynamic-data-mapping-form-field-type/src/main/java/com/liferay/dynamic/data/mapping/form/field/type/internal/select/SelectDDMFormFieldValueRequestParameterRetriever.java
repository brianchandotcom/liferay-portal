/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.select;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRequestParameterRetriever;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.SELECT,
	service = DDMFormFieldValueRequestParameterRetriever.class
)
public class SelectDDMFormFieldValueRequestParameterRetriever
	implements DDMFormFieldValueRequestParameterRetriever {

	@Override
	public String get(
		HttpServletRequest httpServletRequest, String ddmFormFieldParameterName,
		String defaultDDMFormFieldParameterValue) {

		String ddmFormFieldParameterValue = httpServletRequest.getParameter(
			ddmFormFieldParameterName);

		if (ddmFormFieldParameterValue == null) {
			return jsonFactory.serialize(
				_getDefaultDDMFormFieldParameterValues(
					defaultDDMFormFieldParameterValue,
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY)));
		}

		try {
			return String.valueOf(
				jsonFactory.createJSONArray(ddmFormFieldParameterValue));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return "[]";
		}
	}

	@Reference
	protected JSONFactory jsonFactory;

	private String[] _getDefaultDDMFormFieldParameterValues(
		String defaultDDMFormFieldParameterValue, ThemeDisplay themeDisplay) {

		if (themeDisplay.isLifecycleAction() ||
			Validator.isNull(defaultDDMFormFieldParameterValue) ||
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

	private static final Log _log = LogFactoryUtil.getLog(
		SelectDDMFormFieldValueRequestParameterRetriever.class);

}