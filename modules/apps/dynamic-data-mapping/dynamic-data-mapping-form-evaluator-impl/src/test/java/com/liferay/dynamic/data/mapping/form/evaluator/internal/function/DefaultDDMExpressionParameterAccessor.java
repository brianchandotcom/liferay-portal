/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionParameterAccessor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Rafael Praxedes
 */
public class DefaultDDMExpressionParameterAccessor
	implements DDMExpressionParameterAccessor {

	@Override
	public long getCompanyId() {
		return _getCompanyIdSupplier.get();
	}

	@Override
	public String getGooglePlacesAPIKey() {
		return _getGooglePlacesAPIKeySupplier.get();
	}

	@Override
	public long getGroupId() {
		return _getGroupIdSupplier.get();
	}

	@Override
	public Locale getLocale() {
		return _getLocaleSupplier.get();
	}

	@Override
	public JSONArray getObjectFieldsJSONArray() {
		return _getObjectFieldsJSONArraySupplier.get();
	}

	@Override
	public Map<String, Object> getObjectFieldsOldValues() {
		return _getObjectFieldsOldValuesSupplier.get();
	}

	@Override
	public String getTimeZoneId() {
		return _getTimeZoneIdSupplier.get();
	}

	@Override
	public long getUserId() {
		return _getUserIdSupplier.get();
	}

	protected void setGetCompanyIdSupplier(Supplier<Long> supplier) {
		_getCompanyIdSupplier = supplier;
	}

	protected void setGetGooglePlacesAPIKeySupplier(Supplier<String> supplier) {
		_getGooglePlacesAPIKeySupplier = supplier;
	}

	protected void setGetGroupIdSupplier(Supplier<Long> supplier) {
		_getGroupIdSupplier = supplier;
	}

	protected void setGetLocaleSupplier(Supplier<Locale> supplier) {
		_getLocaleSupplier = supplier;
	}

	protected void setGetObjectFieldsOldValuesSupplier(
		Supplier<Map<String, Object>> getObjectFieldsOldValuesSupplier) {

		_getObjectFieldsOldValuesSupplier = getObjectFieldsOldValuesSupplier;
	}

	protected void setGetUserIdSupplier(Supplier<Long> supplier) {
		_getUserIdSupplier = supplier;
	}

	private Supplier<Long> _getCompanyIdSupplier = () -> 0L;
	private Supplier<String> _getGooglePlacesAPIKeySupplier =
		() -> StringPool.BLANK;
	private Supplier<Long> _getGroupIdSupplier = () -> 0L;
	private Supplier<Locale> _getLocaleSupplier = () -> new Locale("pt", "BR");
	private final Supplier<JSONArray> _getObjectFieldsJSONArraySupplier =
		JSONFactoryUtil::createJSONArray;
	private Supplier<Map<String, Object>> _getObjectFieldsOldValuesSupplier =
		Collections::emptyMap;
	private final Supplier<String> _getTimeZoneIdSupplier = () -> "UTC";
	private Supplier<Long> _getUserIdSupplier = () -> 0L;

}