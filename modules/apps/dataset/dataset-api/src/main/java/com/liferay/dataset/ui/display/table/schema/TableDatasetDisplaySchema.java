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

package com.liferay.dataset.ui.display.table.schema;

import java.util.Map;

/**
 * @author Iván Zaera
 */
public class TableDatasetDisplaySchema {

	public Map<String, TableDatasetDisplaySchemaField>
		getTableDatasetDisplaySchemaFieldsMap() {

		return _tableDatasetDisplaySchemaFieldsMap;
	}

	public void setTableDatasetDisplaySchemaFieldsMap(
		Map<String, TableDatasetDisplaySchemaField>
			tableDatasetDisplaySchemaFieldsMap) {

		_tableDatasetDisplaySchemaFieldsMap =
			tableDatasetDisplaySchemaFieldsMap;
	}

	private Map<String, TableDatasetDisplaySchemaField>
		_tableDatasetDisplaySchemaFieldsMap;

}