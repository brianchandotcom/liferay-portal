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

package com.liferay.portal.security.pacl.util;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * @author Zsolt Berentey
 */
public class StatementInfo {

	public StatementInfo() {
	}

	public StatementInfo(
		String operation, String objectType, boolean parseTables,
		boolean mainTable) {

		_mainTable = mainTable;
		_objectType = objectType;
		_operation = operation;
		_parseTables = parseTables;
	}

	public BitSet getLiteralBitSet() {
		return _literalBitSet;
	}

	public String getMainTable() {
		if (hasMainTable() && !_tableNames.isEmpty()) {
			return _tableNames.get(0);
		}

		return null;
	}

	public String getObjectType() {
		return _objectType;
	}

	public String getOperation() {
		return _operation;
	}

	public List<String> getReadTables() {
		if (_tableNames.isEmpty()) {
			return _tableNames;
		}

		if (hasMainTable()) {
			return _tableNames.subList(1, _tableNames.size());
		}
		else {
			return _tableNames;
		}
	}

	public boolean hasMainTable() {
		return _mainTable;
	}

	public boolean hasObjectType(String objectType) {
		if (_objectType == null) {
			return false;
		}

		return _objectType.contains(objectType);
	}

	public boolean hasReadTables() {
		if (hasMainTable()) {
			return _tableNames.size() > 1;
		}
		else {
			return !_tableNames.isEmpty();
		}
	}

	public boolean isParseTables() {
		return _parseTables;
	}

	public void setLiteralBitSet(BitSet bitSet) {
		_literalBitSet = bitSet;
	}

	public void setMainTable(boolean mainTable) {
		_mainTable = mainTable;
	}

	public void setObjectType(String objectType) {
		_objectType = objectType;
	}

	public void setOperation(String operation) {
		_operation = operation;
	}

	public void setParseTables(boolean parseTables) {
		_parseTables = parseTables;
	}

	public void setTableNames(List<String> tableNames) {
		_tableNames = tableNames;
	}

	private BitSet _literalBitSet;
	private boolean _mainTable;
	private String _objectType;
	private String _operation;
	private boolean _parseTables;
	private List<String> _tableNames = new ArrayList<String>();

}