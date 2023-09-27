/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.expression;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;
import com.liferay.petra.string.StringPool;

import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class Scalar<T> extends BaseASTNode implements DefaultExpression<T> {

	public Scalar(T value) {
		_value = value;
	}

	public T getValue() {
		return _value;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept(StringPool.QUESTION);
	}

	private final T _value;

}