/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.view.table;

import java.util.Map;

/**
 * @author Marco Leo
 */
public class FDSTableSchema {

	public Map<String, FDSTableSchemaField> getFDSTableSchemaFieldsMap() {
		return _fdsTableSchemaFieldsMap;
	}

	public void setFDSTableSchemaFieldsMap(
		Map<String, FDSTableSchemaField> fdsTableSchemaFieldsMap) {

		_fdsTableSchemaFieldsMap = fdsTableSchemaFieldsMap;
	}

	private Map<String, FDSTableSchemaField> _fdsTableSchemaFieldsMap;

}