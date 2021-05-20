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

package com.liferay.dynamic.data.mapping.expression;

import com.liferay.petra.string.StringPool;

import java.util.Locale;

/**
 * @author Leonardo Barros
 */
public interface DDMExpressionFunction {

	public default String getLabel(Locale locale) {
		return StringPool.BLANK;
	}

	public String getName();

	public default boolean isCustomDDMExpressionFunction() {
		return false;
	}

	public interface Function0<R> extends DDMExpressionFunction {

		public R apply();

	}

	public interface Function1<A, R> extends DDMExpressionFunction {

		public R apply(A a);

	}

	public interface Function2<A, B, R> extends DDMExpressionFunction {

		public R apply(A a, B b);

	}

	public interface Function3<A, B, C, R> extends DDMExpressionFunction {

		public R apply(A a, B b, C c);

	}

	public interface Function4<A, B, C, D, R> extends DDMExpressionFunction {

		public R apply(A a, B b, C c, D d);

	}

}