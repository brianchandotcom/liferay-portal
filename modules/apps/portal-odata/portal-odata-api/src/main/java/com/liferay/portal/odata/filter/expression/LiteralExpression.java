/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.filter.expression;

/**
 * Represents a literal expression node in the expression tree. This expression
 * is not validated by default.
 *
 * @author Cristina González
 * @review
 */
public interface LiteralExpression extends Expression {

	/**
	 * Returns the literal expression's text value.
	 *
	 * @return the text value
	 * @review
	 */
	public String getText();

	/**
	 * Returns the literal expression's type.
	 *
	 * @return the type
	 * @review
	 */
	public Type getType();

	public static enum Type {

		BOOLEAN, DATE, DATE_TIME, DOUBLE, DURATION, INTEGER, NULL, STRING

	}

}