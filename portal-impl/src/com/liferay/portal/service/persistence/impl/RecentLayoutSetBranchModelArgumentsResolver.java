/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.RecentLayoutSetBranchTable;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.model.impl.RecentLayoutSetBranchImpl;
import com.liferay.portal.model.impl.RecentLayoutSetBranchModelImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The arguments resolver class for retrieving value from RecentLayoutSetBranch.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@OSGiBeanProperties(
	property = {
		"class.name=com.liferay.portal.model.impl.RecentLayoutSetBranchImpl",
		"table.name=RecentLayoutSetBranch"
	},
	service = ArgumentsResolver.class
)
public class RecentLayoutSetBranchModelArgumentsResolver
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

		RecentLayoutSetBranchModelImpl recentLayoutSetBranchModelImpl =
			(RecentLayoutSetBranchModelImpl)baseModel;

		long columnBitmask = recentLayoutSetBranchModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(
				recentLayoutSetBranchModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					recentLayoutSetBranchModelImpl.getColumnBitmask(columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(
				recentLayoutSetBranchModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return RecentLayoutSetBranchImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return RecentLayoutSetBranchTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		RecentLayoutSetBranchModelImpl recentLayoutSetBranchModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] =
					recentLayoutSetBranchModelImpl.getColumnOriginalValue(
						columnName);
			}
			else {
				arguments[i] = recentLayoutSetBranchModelImpl.getColumnValue(
					columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}