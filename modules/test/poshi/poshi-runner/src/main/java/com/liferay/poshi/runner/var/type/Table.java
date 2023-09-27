/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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