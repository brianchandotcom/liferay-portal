/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl;

import com.liferay.petra.sql.dsl.ast.ASTNode;

import java.util.Collection;

/**
 * @author Preston Crary
 */
public interface Table<T extends Table<T>> extends ASTNode {

	public T as(String alias);

	public Column<T, ?> getColumn(String name);

	public <C> Column<T, C> getColumn(String name, Class<C> clazz);

	public Collection<Column<T, ?>> getColumns();

	public String getName();

	public String getTableName();

}