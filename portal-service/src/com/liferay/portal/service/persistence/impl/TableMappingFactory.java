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
public class TableMappingFactory {

	public static <L extends BaseModel<L>, R extends BaseModel<R>>
		TableMapping<L, R> getTableMapping(
			String mappingTableName, String leftColumnName,
			String rightColumnName, BasePersistence<L> leftPersistence,
			BasePersistence<R> rightPersistence) {

		TableMapping<?, ?> tableMapping = tableMappings.get(mappingTableName);

		if (tableMapping == null) {
			TableMappingImpl<L, R> tableMappingImpl =
				new TableMappingImpl<L, R>(
					mappingTableName, leftColumnName, rightColumnName,
					leftPersistence, rightPersistence);

			tableMappingImpl.setReverseTableMapping(
				new ReverseTableMapping<R, L>(tableMappingImpl));

			tableMapping = tableMappingImpl;

			tableMappings.put(mappingTableName, tableMapping);
		}
		else if (!tableMapping.matches(leftColumnName, rightColumnName)) {
			tableMapping = tableMapping.getReverseTableMapping();
		}

		return (TableMapping<L, R>)tableMapping;
	}

	protected static Map<String, TableMapping<?, ?>> tableMappings =
		new ConcurrentHashMap<String, TableMapping<?, ?>>();

}