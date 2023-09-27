/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.filter.expression;

/**
 * Represents a member expression node in the expression tree. This expression
 * is used to describe access paths to properties.
 *
 * @author Cristina González
 * @review
 */
public interface MemberExpression extends Expression {

	/**
	 * Returns the expression which forms this {@code MemberExpression}.
	 *
	 * @return the expression.
	 * @review
	 */
	public default Expression getExpression() {
		throw new UnsupportedOperationException(
			"Unsupported method getExpression");
	}

}