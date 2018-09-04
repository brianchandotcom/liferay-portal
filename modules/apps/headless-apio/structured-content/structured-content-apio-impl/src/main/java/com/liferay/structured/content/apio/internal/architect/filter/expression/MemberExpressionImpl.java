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

package com.liferay.structured.content.apio.internal.architect.filter.expression;

import com.liferay.structured.content.apio.architect.filter.expression.ExpressionVisitException;
import com.liferay.structured.content.apio.architect.filter.expression.ExpressionVisitor;
import com.liferay.structured.content.apio.architect.filter.expression.MemberExpression;

import java.util.Collections;
import java.util.List;

/**
 * @author Cristina González
 */
public class MemberExpressionImpl implements MemberExpression {

	public MemberExpressionImpl(List<String> resourcePath) {
		if (resourcePath == null) {
			_resourcePath = Collections.emptyList();
		}
		else {
			_resourcePath = Collections.unmodifiableList(resourcePath);
		}
	}

	@Override
	public <T> T accept(ExpressionVisitor<T> expressionVisitor)
		throws ExpressionVisitException {

		return expressionVisitor.visitMemberExpression(this);
	}

	@Override
	public List<String> getResourcePath() {
		return _resourcePath;
	}

	@Override
	public String toString() {
		return _resourcePath.toString();
	}

	private final List<String > _resourcePath;

}