/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Carolina Barbosa
 */
public class HasObjectFieldFunction
	implements DDMExpressionFunction.Function1<Object, Boolean> {

	public static final String NAME = "hasObjectField";

	@Override
	public Boolean apply(Object object) {
		if (object instanceof String) {
			return _apply((String)object);
		}
		else if (object instanceof String[]) {
			return _apply((String[])object);
		}

		return false;
	}

	@Override
	public String getName() {
		return NAME;
	}

	private boolean _apply(String objectFieldName) {
		if (Validator.isNull(objectFieldName) ||
			Validator.isNull(
				objectFieldName.replaceAll("\\[|\\]|\"", StringPool.BLANK))) {

			return false;
		}

		return true;
	}

	private boolean _apply(String[] objectFieldNames) {
		if (ArrayUtil.isEmpty(objectFieldNames)) {
			return false;
		}

		return true;
	}

}