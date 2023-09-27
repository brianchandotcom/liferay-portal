/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service.persistence.impl;

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceTable;
import com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceModelImpl;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from DDMDataProviderInstance.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	property = {
		"class.name=com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceImpl",
		"table.name=DDMDataProviderInstance"
	},
	service = ArgumentsResolver.class
)
public class DDMDataProviderInstanceModelArgumentsResolver
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

		DDMDataProviderInstanceModelImpl ddmDataProviderInstanceModelImpl =
			(DDMDataProviderInstanceModelImpl)baseModel;

		long columnBitmask =
			ddmDataProviderInstanceModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(
				ddmDataProviderInstanceModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					ddmDataProviderInstanceModelImpl.getColumnBitmask(
						columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(
				ddmDataProviderInstanceModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return DDMDataProviderInstanceImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return DDMDataProviderInstanceTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		DDMDataProviderInstanceModelImpl ddmDataProviderInstanceModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] =
					ddmDataProviderInstanceModelImpl.getColumnOriginalValue(
						columnName);
			}
			else {
				arguments[i] = ddmDataProviderInstanceModelImpl.getColumnValue(
					columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}