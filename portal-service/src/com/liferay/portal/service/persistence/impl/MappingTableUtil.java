/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.persistence.BasePersistence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class MappingTableUtil {

	public static <L extends BaseModel<L>, R extends BaseModel<R>>
		MappingTable<L, R> getMappingTable(
			String mappingTableName, String leftColumnName,
			String rightColumnName, BasePersistence<L> leftPersistence,
			BasePersistence<R> rightPersistence) {

		MappingTable<?, ?> mappingTable = mappingTables.get(mappingTableName);

		if (mappingTable == null) {
			MappingTableImpl<L, R> mappingTableImpl =
				new MappingTableImpl<L, R>(
					mappingTableName, leftColumnName, rightColumnName,
					leftPersistence, rightPersistence);

			mappingTableImpl.setReverseMappingTable(
				new ReverseMappingTable<R, L>(mappingTableImpl));

			mappingTable = mappingTableImpl;

			mappingTables.put(mappingTableName, mappingTable);
		}
		else if (!mappingTable.matches(leftColumnName, rightColumnName)) {
			mappingTable = mappingTable.getReverseMappingTable();
		}

		return (MappingTable<L, R>)mappingTable;
	}

	protected static Map<String, MappingTable<?, ?>> mappingTables =
		new ConcurrentHashMap<String, MappingTable<?, ?>>();

}