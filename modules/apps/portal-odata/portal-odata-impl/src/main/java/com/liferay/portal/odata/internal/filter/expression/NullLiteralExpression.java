/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.internal.filter.expression;

/**
 * @author Cristina González
 */
public class NullLiteralExpression extends LiteralExpressionImpl {

	public NullLiteralExpression() {
		super(null, null);
	}

	@Override
	public String toString() {
		return Type.NULL.toString();
	}

}