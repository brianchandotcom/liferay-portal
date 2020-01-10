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
import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeListener;
import com.liferay.portal.kernel.dao.model.dsl.base.BaseASTNode;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class Alias<T> extends BaseASTNode implements Expression<T> {

	public Alias(Expression<T> expression, String name) {
		_expression = Objects.requireNonNull(expression);
		_name = Objects.requireNonNull(name);
	}

	@Override
	public String getAlias() {
		return _name;
	}

	@Override
	public Expression<T> unwrapAlias() {
		return _expression;
	}

	@Override
	protected void doToSQL(StringBundler sb, ASTNodeListener astNodeListener) {
		sb.append(_name);
	}

	private final Expression<T> _expression;
	private final String _name;

}