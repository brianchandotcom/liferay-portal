/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.expression;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;

import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class ScalarList<T> extends BaseASTNode implements DefaultExpression<T> {

	public ScalarList(T[] values) {
		if (values.length == 0) {
			throw new IllegalArgumentException("Values is empty");
		}

		_values = values;
	}

	public T[] getValues() {
		return _values;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept("(?");

		for (int i = 1; i < _values.length; i++) {
			consumer.accept(", ?");
		}

		consumer.accept(")");
	}

	private final T[] _values;

}