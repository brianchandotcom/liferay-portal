/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.internal.filter.expression;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.NavigationPropertyExpression;

/**
 * @author Rubén Pulido
 */
public class NavigationPropertyExpressionImpl
	implements NavigationPropertyExpression {

	public NavigationPropertyExpressionImpl(
		String name, NavigationPropertyExpression.Type type) {

		_name = name;
		_type = type;
	}

	@Override
	public <T> T accept(ExpressionVisitor<T> expressionVisitor)
		throws ExpressionVisitException {

		return expressionVisitor.visitNavigationPropertyExpression(this);
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public String toString() {
		if (_type == Type.COUNT) {
			return StringBundler.concat(_name, "/", _type);
		}

		return _name;
	}

	private final String _name;
	private final Type _type;

}