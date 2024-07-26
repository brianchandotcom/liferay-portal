/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.expression;

/**
 * @author Preston Crary
 */
public interface Alias<T> extends Expression<T> {

	public Expression<T> getExpression();

	public String getName();

}