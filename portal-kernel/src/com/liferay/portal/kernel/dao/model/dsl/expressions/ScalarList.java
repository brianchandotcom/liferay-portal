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

package com.liferay.portal.kernel.dao.model.dsl.expressions;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeListener;
import com.liferay.portal.kernel.dao.model.dsl.base.BaseASTNode;

/**
 * @author Preston Crary
 */
public class ScalarList<T> extends BaseASTNode implements Expression<T> {

	public ScalarList(T[] values) {
		if (values.length == 0) {
			throw new IllegalArgumentException();
		}

		_values = values;
	}

	public T[] getValues() {
		return _values;
	}

	@Override
	protected void doToSQL(StringBundler sb, ASTNodeListener astNodeListener) {
		sb.append("(");

		for (int i = 0; i < _values.length; i++) {
			if (Scalar.isScalarValue(_values[i])) {
				sb.append(StringPool.QUESTION);
			}
			else {
				sb.append(_values[i]);
			}

			if (i < (_values.length - 1)) {
				sb.append(", ");
			}
		}

		sb.append(")");
	}

	private final T[] _values;

}