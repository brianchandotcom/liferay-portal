/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.expression;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.expression.ScalarDSLQueryAlias;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;

import java.util.function.Consumer;

/**
 * @author Marco Leo
 */
public class DefaultScalarDSLQueryAlias<T>
	extends BaseASTNode
	implements DefaultExpression<T>, ScalarDSLQueryAlias<T> {

	public DefaultScalarDSLQueryAlias(
		DSLQuery dslQuery, Class<T> javaType, String name, int sqlType) {

		_dslQuery = dslQuery;
		_javaType = javaType;
		_name = name;
		_sqlType = sqlType;
	}

	@Override
	public DSLQuery getDSLQuery() {
		return _dslQuery;
	}

	@Override
	public Class<T> getJavaType() {
		return _javaType;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public int getSQLType() {
		return _sqlType;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept("(");
		consumer.accept(_dslQuery.toSQL(astNodeListener));
		consumer.accept(") as " + _name);
	}

	private final DSLQuery _dslQuery;
	private final Class<T> _javaType;
	private final String _name;
	private final int _sqlType;

}