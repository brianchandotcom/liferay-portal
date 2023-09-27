/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.filter.expression;

/**
 * Provides a common abstraction for expression evaluation.
 *
 * @author Cristina González
 * @review
 */
public interface Expression {

	/**
	 * Called when traversing the expression tree.
	 *
	 * @param  expressionVisitor the {@link ExpressionVisitor}
	 * @return the object of type {@code T}, which should be passed to the
	 *         processing algorithm of the parent expression node
	 * @review
	 */
	public <T> T accept(ExpressionVisitor<T> expressionVisitor)
		throws ExpressionVisitException;

}