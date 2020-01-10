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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeListener;
import com.liferay.portal.kernel.dao.model.dsl.base.BaseASTNode;

import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class Scalar<T> extends BaseASTNode implements Expression<T> {

	public static boolean isScalarValue(Object value) {
		if (value instanceof Integer || value instanceof Long ||
			(value == null)) {

			return false;
		}

		return true;
	}

	public Scalar(T value) {
		_value = value;
	}

	public T getValue() {
		return _value;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		if (isScalarValue(_value)) {
			consumer.accept(StringPool.QUESTION);
		}
		else {
			consumer.accept(String.valueOf(_value));
		}
	}

	private final T _value;

}