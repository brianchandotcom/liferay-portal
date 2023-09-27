/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;
import java.util.Set;

/**
 * @author Leonardo Barros
 */
public class ContainsFunction
	implements DDMExpressionFunction.Function2<Object, Object, Boolean> {

	public static final String NAME = "contains";

	@Override
	public Boolean apply(Object object1, Object object2) {
		if ((object1 == null) || (object2 == null)) {
			return false;
		}

		if ((object1 instanceof JSONObjectImpl) &&
			(object2 instanceof JSONObjectImpl)) {

			return apply((JSONObjectImpl)object1, (JSONObjectImpl)object2);
		}

		if ((object1 instanceof JSONArray) && (object2 instanceof String)) {
			return apply(object1.toString(), object2);
		}

		return apply(object1.toString(), object2.toString());
	}

	@Override
	public String getName() {
		return NAME;
	}

	protected Boolean apply(JSONObject jsonObject1, JSONObject jsonObject2) {
		if ((jsonObject1 == null) || (jsonObject2 == null)) {
			return false;
		}

		Set<String> keys = jsonObject2.keySet();

		for (String key : keys) {
			String value = jsonObject1.getString(key);

			if ((value == null) ||
				!Objects.equals(value, jsonObject2.get(key))) {

				return false;
			}
		}

		return true;
	}

	protected Boolean apply(String string1, String string2) {
		if (Validator.isNull(string1) || Validator.isNull(string2)) {
			return false;
		}

		string1 = StringUtil.toLowerCase(string1);
		string2 = StringUtil.toLowerCase(string2);

		return string1.contains(string2);
	}

}