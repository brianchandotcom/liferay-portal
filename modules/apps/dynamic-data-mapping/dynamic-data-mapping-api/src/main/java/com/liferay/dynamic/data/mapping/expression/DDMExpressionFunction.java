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

/**
 * @author Leonardo Barros
 */
public interface DDMExpressionFunction {

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link Function0}, {@link
	 *             Function1}, {@link Function2}, {@link Function3}, {@link
	 *             Function4}
	 */
	@Deprecated
	public default Object evaluate(Object... parameters) {
		if (parameters.length == 0) {
			DDMExpressionFunction.Function0 function0 =
				(DDMExpressionFunction.Function0)this;

			return function0.apply();
		}
		else if (parameters.length == 1) {
			DDMExpressionFunction.Function1 function1 =
				(DDMExpressionFunction.Function1)this;

			return function1.apply(parameters[0]);
		}
		else if (parameters.length == 2) {
			DDMExpressionFunction.Function2 function2 =
				(DDMExpressionFunction.Function2)this;

			return function2.apply(parameters[0], parameters[1]);
		}
		else if (parameters.length == 3) {
			DDMExpressionFunction.Function3 function3 =
				(DDMExpressionFunction.Function3)this;

			return function3.apply(parameters[0], parameters[1], parameters[2]);
		}
		else if (parameters.length == 4) {
			DDMExpressionFunction.Function4 function4 =
				(DDMExpressionFunction.Function4)this;

			return function4.apply(
				parameters[0], parameters[1], parameters[2], parameters[3]);
		}

		return null;
	}

	public String getName();

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