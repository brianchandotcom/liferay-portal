/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.factory;

import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.FromStep;

/**
 * @author Preston Crary
 */
public interface DSLQueryFactory {

	public FromStep count();

	public FromStep countDistinct(Expression<?> expression);

	public <T> Expression<T> scalarSubDSLQuery(
		DSLQuery dslQuery, Class<T> javaType, String name, int sqlType);

	public FromStep select();

	public FromStep select(Expression<?>... expressions);

	public <T extends Table<T>> FromStep select(Table<T> table);

	public FromStep selectDistinct(Expression<?>... expressions);

	public <T extends Table<T>> FromStep selectDistinct(Table<T> table);

}