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

package com.liferay.portal.tools.servicebuilder;

/**
 * @author Glenn Powell
 * @author Brian Wing Shun Chan
 */
public class EntityMapping {

	public EntityMapping(String table, String entity1, String entity2) {
		_table = table;
		_entities[0] = entity1;
		_entities[1] = entity2;
	}

	public String getEntity(int index) {
		try {
			return _entities[index];
		}
		catch (Exception e) {
			return null;
		}
	}

	public String getTable() {
		return _table;
	}

	private final String[] _entities = new String[2];
	private final String _table;

}