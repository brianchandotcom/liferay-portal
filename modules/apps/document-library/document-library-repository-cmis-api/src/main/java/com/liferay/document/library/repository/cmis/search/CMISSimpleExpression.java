/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.cmis.search;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

/**
 * @author Mika Koivisto
 */
public class CMISSimpleExpression implements CMISCriterion {

	public CMISSimpleExpression(
		String field, String value,
		CMISSimpleExpressionOperator cmisSimpleExpressionOperator) {

		_field = field;
		_value = value;
		_cmisSimpleExpressionOperator = cmisSimpleExpressionOperator;
	}

	@Override
	public String toQueryFragment() {
		return StringBundler.concat(
			_field, StringPool.SPACE, _cmisSimpleExpressionOperator,
			StringPool.SPACE, StringPool.APOSTROPHE, _value,
			StringPool.APOSTROPHE);
	}

	private final CMISSimpleExpressionOperator _cmisSimpleExpressionOperator;
	private final String _field;
	private final String _value;

}