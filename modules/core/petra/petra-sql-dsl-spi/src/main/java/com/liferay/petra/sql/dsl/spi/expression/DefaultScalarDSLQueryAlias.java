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
	extends BaseASTNode implements ScalarDSLQueryAlias<T>, DefaultExpression<T> {

	public DefaultScalarDSLQueryAlias(DSLQuery dslQuery, Class<T> javaType, String name, int sqlType) {
		_dslQuery = dslQuery;
		_javaType = javaType;
		_name = name;
		_sqlType = sqlType;
	}

	@Override
	public Class<T> getJavaType() {
		return _javaType;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept("(");

		consumer.accept(_dslQuery.toSQL(astNodeListener));

		consumer.accept(") as " + _name);
	}

	@Override
	public int getSQLType() {
		return _sqlType;
	}

	@Override
	public DSLQuery getDSLQuery() {
		return _dslQuery;
	}

	@Override
	public String getName() {
		return _name;
	}


	private final Class<T> _javaType;
	private final int _sqlType;
	private final DSLQuery _dslQuery;
	private final String _name;

}