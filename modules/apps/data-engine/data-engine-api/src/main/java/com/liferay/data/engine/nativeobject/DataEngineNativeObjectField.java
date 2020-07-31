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

package com.liferay.data.engine.nativeobject;

import com.liferay.petra.sql.dsl.Column;

/**
 * @author Jeyvison Nascimento
 */
public class DataEngineNativeObjectField {

	/**
	 * @param      column
	 * @param      customType
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public DataEngineNativeObjectField(Column<?, ?> column, String customType) {
		_column = column;
		_customName = null;
		_customType = customType;
	}

	public DataEngineNativeObjectField(
		Column<?, ?> column, String customName, String customType) {

		_column = column;
		_customName = customName;
		_customType = customType;
	}

	public Column<?, ?> getColumn() {
		return _column;
	}

	public String getCustomName() {
		return _customName;
	}

	public String getCustomType() {
		return _customType;
	}

	private final Column<?, ?> _column;
	private final String _customName;
	private final String _customType;

}