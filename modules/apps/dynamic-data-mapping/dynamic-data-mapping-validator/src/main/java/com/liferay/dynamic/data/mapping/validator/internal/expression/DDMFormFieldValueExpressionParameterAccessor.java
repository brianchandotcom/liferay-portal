/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.validator.internal.expression;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;

import java.util.Locale;

/**
 * @author Carolina Barbosa
 * @author Renato Rego
 */
public class DDMFormFieldValueExpressionParameterAccessor
	implements DDMExpressionParameterAccessor {

	public DDMFormFieldValueExpressionParameterAccessor(
		JSONFactory jsonFactory, Locale locale, String timeZoneId) {

		_jsonFactory = jsonFactory;
		_locale = locale;
		_timeZoneId = timeZoneId;
	}

	@Override
	public long getCompanyId() {
		return 0L;
	}

	@Override
	public String getGooglePlacesAPIKey() {
		return StringPool.BLANK;
	}

	@Override
	public long getGroupId() {
		return 0L;
	}

	@Override
	public Locale getLocale() {
		return _locale;
	}

	@Override
	public JSONArray getObjectFieldsJSONArray() {
		return _jsonFactory.createJSONArray();
	}

	@Override
	public String getTimeZoneId() {
		return _timeZoneId;
	}

	@Override
	public long getUserId() {
		return 0L;
	}

	private final JSONFactory _jsonFactory;
	private final Locale _locale;
	private final String _timeZoneId;

}