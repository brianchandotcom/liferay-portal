/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.factory;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.sql.dsl.factory.ColumnFactory;
import com.liferay.petra.sql.dsl.spi.DefaultColumn;

/**
 * @author Preston Crary
 */
public class DefaultColumnFactory implements ColumnFactory {

	@Override
	public <T extends BaseTable<T>, C> Column<T, C> createColumn(
		T table, String name, Class<C> javaType, int sqlType, int flags) {

		return new DefaultColumn<>(table, name, javaType, sqlType, flags);
	}

}