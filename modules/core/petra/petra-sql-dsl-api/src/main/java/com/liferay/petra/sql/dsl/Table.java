/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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