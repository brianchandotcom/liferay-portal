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

package com.liferay.batch.engine.service.persistence.impl;

import com.liferay.batch.engine.model.BatchEngineImportTaskErrorTable;
import com.liferay.batch.engine.model.impl.BatchEngineImportTaskErrorImpl;
import com.liferay.batch.engine.model.impl.BatchEngineImportTaskErrorModelImpl;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from BatchEngineImportTaskError.
 *
 * @author Shuyang Zhou
 * @generated
 */
@Component(
	immediate = true,
	service = {
		BatchEngineImportTaskErrorModelArgumentsResolver.class,
		ArgumentsResolver.class
	}
)
public class BatchEngineImportTaskErrorModelArgumentsResolver
	implements ArgumentsResolver {

	@Override
	public Object[] getArguments(
		FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
		boolean original) {

		String[] columnNames = finderPath.getColumnNames();

		if ((columnNames == null) || (columnNames.length == 0)) {
			if (baseModel.isNew()) {
				return new Object[0];
			}

			return null;
		}

		BatchEngineImportTaskErrorModelImpl
			batchEngineImportTaskErrorModelImpl =
				(BatchEngineImportTaskErrorModelImpl)baseModel;

		long columnBitmask =
			batchEngineImportTaskErrorModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(
				batchEngineImportTaskErrorModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					batchEngineImportTaskErrorModelImpl.getColumnBitmask(
						columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(
				batchEngineImportTaskErrorModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return BatchEngineImportTaskErrorImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return BatchEngineImportTaskErrorTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		BatchEngineImportTaskErrorModelImpl batchEngineImportTaskErrorModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] =
					batchEngineImportTaskErrorModelImpl.getColumnOriginalValue(
						columnName);
			}
			else {
				arguments[i] =
					batchEngineImportTaskErrorModelImpl.getColumnValue(
						columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}