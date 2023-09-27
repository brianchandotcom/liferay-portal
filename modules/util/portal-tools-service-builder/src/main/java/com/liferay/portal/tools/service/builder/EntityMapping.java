/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder;

/**
 * @author Glenn Powell
 * @author Brian Wing Shun Chan
 */
public class EntityMapping {

	public EntityMapping(
		String tableName, String entityName1, String entityName2) {

		_tableName = tableName;

		_entityNames[0] = "com.liferay.portal.Company";
		_entityNames[1] = entityName1;
		_entityNames[2] = entityName2;
	}

	public String getEntityName(int index) {
		try {
			return _entityNames[index];
		}
		catch (Exception exception) {
			return null;
		}
	}

	public String getTableName() {
		return _tableName;
	}

	private final String[] _entityNames = new String[3];
	private final String _tableName;

}