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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.persistence.BasePersistence;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class TableMapperFactory {

	public static
		<L extends BaseModel<L>, R extends BaseModel<R>> TableMapper<L, R>
			getTableMapper(
				String tableName, String leftColumnName, String rightColumnName,
				BasePersistence<L> leftPersistence,
				BasePersistence<R> rightPersistence,
				CompanyProviderHolder companyProviderHolder) {

		TableMapper<?, ?> tableMapper = tableMappers.get(tableName);

		if (tableMapper == null) {
			TableMapperImpl<L, R> tableMapperImpl = null;

			if (cacheMappingTableNames.contains(tableName)) {
				tableMapperImpl = new TableMapperImpl<>(
					tableName, leftColumnName, rightColumnName, leftPersistence,
					rightPersistence, companyProviderHolder);
			}
			else {
				tableMapperImpl = new CachelessTableMapperImpl<>(
					tableName, leftColumnName, rightColumnName, leftPersistence,
					rightPersistence, companyProviderHolder);
			}

			tableMapperImpl.setReverseTableMapper(
				new ReverseTableMapper<>(tableMapperImpl));

			tableMapper = tableMapperImpl;

			tableMappers.put(tableName, tableMapper);
		}
		else if (!tableMapper.matches(leftColumnName, rightColumnName)) {
			tableMapper = tableMapper.getReverseTableMapper();
		}

		return (TableMapper<L, R>)tableMapper;
	}

	public static void removeTableMapper(String tableName) {
		TableMapper<?, ?> tableMapper = tableMappers.remove(tableName);

		if (tableMapper != null) {
			tableMapper.destroy();
		}
	}

	protected static final Set<String> cacheMappingTableNames =
		SetUtil.fromArray(
			PropsUtil.getArray(
				PropsKeys.TABLE_MAPPER_CACHE_MAPPING_TABLE_NAMES));
	protected static final Map<String, TableMapper<?, ?>> tableMappers =
		new ConcurrentHashMap<>();

}