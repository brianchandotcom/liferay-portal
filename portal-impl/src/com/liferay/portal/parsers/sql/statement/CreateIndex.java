/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.parsers.sql.statement;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import net.sf.jsqlparser.statement.create.table.Index;
import net.sf.jsqlparser.statement.select.PlainSelect;

/**
 * @author Zsolt Berentey
 */
public class CreateIndex extends BaseStatement {

	public Index getIndex() {
		return _index;
	}

	public String getTableName() {
		return _tableName;
	}

	public void setIndex(Index index) {
		_index = index;
	}

	public void setTableName(String tableName) {
		_tableName = tableName;
	}

	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("CREATE ");

		if (_index.getType() != null) {
			sb.append(_index.getType());
			sb.append(StringPool.SPACE);
		}

		sb.append("INDEX ");
		sb.append(_index.getName());
		sb.append(" ON ");
		sb.append(_tableName);
		sb.append(StringPool.BLANK);
		sb.append(
			PlainSelect.getStringList(_index.getColumnsNames(), true, true));

		return sb.toString();
	}

	private Index _index = null;
	private String _tableName = null;

}