/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.filter.expression;

import java.util.List;

/**
 * Represents a list expression node in the expression tree.
 *
 * @author Cristina González
 * @review
 */
public interface ListExpression extends Expression {

	/**
	 * Returns an {@link Expression} subtree of the left operation.
	 *
	 * @return Expression sub tree of the left operation
	 * @review
	 */
	public Expression getLeftOperationExpression();

	/**
	 * Returns the list expression's operation.
	 *
	 * @return the operation of the list Expression
	 * @review
	 */
	public Operation getOperation();

	/**
	 * Returns a list of the right {@link Expression} operations.
	 *
	 * @return the expression list
	 * @review
	 */
	public List<Expression> getRightOperationExpressions();

	public static enum Operation {

		IN

	}

}