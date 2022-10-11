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