/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static Predicate not(Predicate predicate) {
		if (predicate == null) {
			return null;
		}

		return predicate.not();
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

	public Predicate not();

	public Predicate or(Expression<Boolean> expression);

	public default <T extends Throwable> Predicate or(
			UnsafeSupplier<Expression<Boolean>, T> unsafeSupplier)
		throws T {

		return or(unsafeSupplier.get());
	}

	public Predicate withParentheses();

}