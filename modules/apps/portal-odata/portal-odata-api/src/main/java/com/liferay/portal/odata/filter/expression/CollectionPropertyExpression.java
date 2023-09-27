/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.filter.expression;

/**
 * Models a collection {@code PropertyExpression}.
 *
 * @author Rubén Pulido
 * @review
 */
public interface CollectionPropertyExpression extends PropertyExpression {

	/**
	 * Gets the lambda function expression.
	 *
	 * @return the lambda function expression
	 * @review
	 */
	public LambdaFunctionExpression getLambdaFunctionExpression();

	/**
	 * Gets the property expression.
	 *
	 * @return the property expression
	 * @review
	 */
	public PropertyExpression getPropertyExpression();

}