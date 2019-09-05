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

package com.liferay.portal.kernel.service.persistence.impl;

import com.liferay.portal.kernel.internal.service.persistence.CachelessTableMapperImpl;
import com.liferay.portal.kernel.internal.service.persistence.ReverseTableMapper;
import com.liferay.portal.kernel.internal.service.persistence.TableMapperImpl;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class TableMapperFactory {

	public static <L extends BaseModel<L>, R extends BaseModel<R>>
		TableMapper<L, R> getTableMapper(
			String tableName, String companyColumnName, String leftColumnName,
			String rightColumnName, BasePersistence<L> leftPersistence,
			BasePersistence<R> rightPersistence) {

		return _getTableMapper(
			tableName, tableName, companyColumnName, leftColumnName,
			rightColumnName, leftPersistence, rightPersistence);
	}

	/**
	 * Creates a left side only TableMapper
	 */
	public static <L extends BaseModel<L>, R extends BaseModel<R>>
		TableMapper<L, R> getTableMapper(
			String tableMapperKey, String tableName, String companyColumnName,
			String leftColumnName, String rightColumnName,
			BasePersistence<L> leftPersistence, Class<R> rightModelClass) {

		return _getTableMapper(
			tableMapperKey, tableName, companyColumnName, leftColumnName,
			rightColumnName, leftPersistence,
			new RejectingBasePersistenceImpl<>(rightModelClass));
	}

	public static void removeTableMapper(String tableMapperKey) {
		TableMapper<?, ?> tableMapper = _tableMappers.remove(tableMapperKey);

		if (tableMapper != null) {
			tableMapper.destroy();
		}
	}

	private static <L extends BaseModel<L>, R extends BaseModel<R>>
		TableMapper<L, R> _getTableMapper(
			String tableMapperKey, String tableName, String companyColumnName,
			String leftColumnName, String rightColumnName,
			BasePersistence<L> leftPersistence,
			BasePersistence<R> rightPersistence) {

		TableMapper<?, ?> tableMapper = _tableMappers.get(tableMapperKey);

		if (tableMapper == null) {
			TableMapperImpl<L, R> tableMapperImpl = null;

			if (_cachelessMappingTableNames.contains(tableName)) {
				tableMapperImpl = new CachelessTableMapperImpl<>(
					tableName, companyColumnName, leftColumnName,
					rightColumnName, leftPersistence.getModelClass(),
					rightPersistence.getModelClass(), leftPersistence,
					rightPersistence);
			}
			else {
				tableMapperImpl = new TableMapperImpl<>(
					tableName, companyColumnName, leftColumnName,
					rightColumnName, leftPersistence.getModelClass(),
					rightPersistence.getModelClass(), leftPersistence,
					rightPersistence);
			}

			tableMapperImpl.setReverseTableMapper(
				new ReverseTableMapper<>(tableMapperImpl));

			tableMapper = tableMapperImpl;

			_tableMappers.put(tableMapperKey, tableMapper);
		}
		else if (!tableMapper.matches(leftColumnName, rightColumnName)) {
			tableMapper = tableMapper.getReverseTableMapper();
		}

		return (TableMapper<L, R>)tableMapper;
	}

	private static final Set<String> _cachelessMappingTableNames =
		SetUtil.fromArray(
			PropsUtil.getArray(
				PropsKeys.TABLE_MAPPER_CACHELESS_MAPPING_TABLE_NAMES));
	private static final Map<String, TableMapper<?, ?>> _tableMappers =
		new ConcurrentHashMap<>();

	private static class RejectingBasePersistenceImpl<T extends BaseModel<T>>
		extends BasePersistenceImpl<T> {

		@Override
		public T findByPrimaryKey(Serializable primaryKey) {
			throw new UnsupportedOperationException(
				"The TableMapper only supports BaseModel queries on one side");
		}

		private RejectingBasePersistenceImpl(Class<T> modelClass) {
			setModelClass(modelClass);
		}

	}

}