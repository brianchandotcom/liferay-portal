/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.filter.expression;

/**
 * Represents a binary expression node in the expression tree.
 *
 * @author Cristina González
 * @review
 */
public interface BinaryExpression extends Expression {

	/**
	 * Returns an {@link Expression} subtree of the left operation.
	 *
	 * @return Expression sub tree of the left operation
	 * @review
	 */
	public Expression getLeftOperationExpression();

	/**
	 * Returns the binary expression's operation.
	 *
	 * @return the operation of the binary Expression
	 * @review
	 */
	public Operation getOperation();

	/**
	 * Returns an {@link Expression} subtree of the right operation.
	 *
	 * @return the expression subtree
	 * @review
	 */
	public Expression getRightOperationExpression();

	public static enum Operation {

		AND, EQ, GE, GT, LE, LT, NE, OR, SUB

	}

}