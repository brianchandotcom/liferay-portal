/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.expression;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.expression.Alias;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class DefaultAlias<T>
	extends BaseASTNode implements Alias<T>, DefaultExpression<T> {

	public DefaultAlias(Expression<T> expression, String name) {
		_expression = Objects.requireNonNull(expression);
		_name = Objects.requireNonNull(name);
	}

	@Override
	public Expression<T> getExpression() {
		return _expression;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept(_name);
	}

	private final Expression<T> _expression;
	private final String _name;

}