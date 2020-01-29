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

package com.liferay.petra.sql.dsl.spi.expression.step;

import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.step.ElseEndStep;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;

/**
 * @author Preston Crary
 */
public interface DefaultElseEndStep<T> extends ElseEndStep<T> {

	@Override
	public default Expression<T> elseEnd(Expression<T> expression) {
		return new ElseEnd<>(this, expression);
	}

	@Override
	public default Expression<T> elseEnd(T value) {
		return elseEnd(new Scalar<>(value));
	}

}