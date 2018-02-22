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
		catch (Exception e) {
			return null;
		}
	}

	public String getTableName() {
		return _tableName;
	}

	private final String[] _entityNames = new String[3];
	private final String _tableName;

}