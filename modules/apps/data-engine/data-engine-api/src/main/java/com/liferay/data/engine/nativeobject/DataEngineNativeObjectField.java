/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		_customType = customType;

		_customName = null;
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