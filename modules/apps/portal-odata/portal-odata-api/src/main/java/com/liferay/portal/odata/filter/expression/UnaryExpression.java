/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.filter.expression;

/**
 * Represents a unary expression node in the expression tree.
 *
 * @author Cristina González
 * @review
 */
public interface UnaryExpression extends Expression {

	/**
	 * Returns an {@link Expression} subtree of the operation.
	 *
	 * @return Expression sub tree of the operation
	 * @review
	 */
	public Expression getExpression();

	/**
	 * Returns the unary expression's operation.
	 *
	 * @return the operation of the unary Expression
	 * @review
	 */
	public UnaryExpression.Operation getOperation();

	public static enum Operation {

		NOT

	}

}