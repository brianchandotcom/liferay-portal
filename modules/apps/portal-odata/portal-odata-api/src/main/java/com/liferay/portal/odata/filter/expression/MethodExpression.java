/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.filter.expression;

import java.util.List;

/**
 * Represents a method expression in the expression tree
 *
 * @author Cristina González
 * @review
 */
public interface MethodExpression extends Expression {

	/**
	 * @return The list of expression tree which form the actual method
	 *         parameters
	 * @review
	 */
	public List<Expression> getExpressions();

	/**
	 * Returns the method type
	 *
	 * @return The type
	 * @review
	 */
	public Type getType();

	public static enum Type {

		CONTAINS, NOW, STARTS_WITH

	}

}