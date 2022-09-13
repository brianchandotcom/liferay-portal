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

package com.liferay.poshi.runner.var.type;

import java.util.List;

/**
 * @author Calum Ragan
 */
public interface Table {

	public List<String> getColumnByIndex(int index);

	public List<String> getColumnByName(String columnName);

	public List<String> getRowByIndex(int index);

	public List<String> getRowByName(String rowName);

	public int getTableRowWidth(List<List<String>> rawTable);

	public int getTableSize();

	public Table getTransposedTable(List<List<String>> rawTable);

}