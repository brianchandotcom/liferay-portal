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

package com.liferay.petra.sql.dsl.expression;

import com.liferay.petra.function.UnsafeSupplier;

/**
 * @author Preston Crary
 */
public interface Predicate extends Expression<Boolean> {

	public static Predicate and(
		Predicate leftPredicate, Predicate rightPredicate) {

		if (leftPredicate == null) {
			return rightPredicate;
		}

		return leftPredicate.and(rightPredicate);
	}

	public static Predicate or(
		Predicate leftPredicate, Predicate rightPredicate) {

		if (leftPredicate == null) {
			return rightPredicate;
		}

		return leftPredicate.or(rightPredicate);
	}

	public static Predicate withParentheses(Predicate predicate) {
		if (predicate == null) {
			return null;
		}

		return predicate.withParentheses();
	}

	public Predicate and(Expression<Boolean> expression);

	public default <T extends Throwable> Predicate and(
			UnsafeSupplier<Expression<Boolean>, T> unsafeSupplier)
		throws T {

		return and(unsafeSupplier.get());
	}

	public Predicate or(Expression<Boolean> expression);

	public default <T extends Throwable> Predicate or(
			UnsafeSupplier<Expression<Boolean>, T> unsafeSupplier)
		throws T {

		return or(unsafeSupplier.get());
	}

	public Predicate withParentheses();

}