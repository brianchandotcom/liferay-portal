/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.dynamic.data.mapping.expression;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Paulo Albuquerque
 */
public class ObjectEntryDDMExpressionParameterAccessor
	implements DDMExpressionParameterAccessor {

	public ObjectEntryDDMExpressionParameterAccessor(
		Map<String, Object> oldValues) {

		_oldValues = oldValues;
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
		return null;
	}

	@Override
	public JSONArray getObjectFieldsJSONArray() {
		return JSONFactoryUtil.createJSONArray();
	}

	@Override
	public Map<String, Object> getObjectFieldsOldValues() {
		return _oldValues;
	}

	@Override
	public String getTimeZoneId() {
		return StringPool.BLANK;
	}

	@Override
	public long getUserId() {
		return 0L;
	}

	private final Map<String, Object> _oldValues;

}