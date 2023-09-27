/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.service.persistence.impl;

import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRelTable;
import com.liferay.asset.list.model.impl.AssetListEntrySegmentsEntryRelImpl;
import com.liferay.asset.list.model.impl.AssetListEntrySegmentsEntryRelModelImpl;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from AssetListEntrySegmentsEntryRel.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	property = {
		"class.name=com.liferay.asset.list.model.impl.AssetListEntrySegmentsEntryRelImpl",
		"table.name=AssetListEntrySegmentsEntryRel"
	},
	service = ArgumentsResolver.class
)
public class AssetListEntrySegmentsEntryRelModelArgumentsResolver
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

		AssetListEntrySegmentsEntryRelModelImpl
			assetListEntrySegmentsEntryRelModelImpl =
				(AssetListEntrySegmentsEntryRelModelImpl)baseModel;

		long columnBitmask =
			assetListEntrySegmentsEntryRelModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(
				assetListEntrySegmentsEntryRelModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					assetListEntrySegmentsEntryRelModelImpl.getColumnBitmask(
						columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(
				assetListEntrySegmentsEntryRelModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return AssetListEntrySegmentsEntryRelImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return AssetListEntrySegmentsEntryRelTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		AssetListEntrySegmentsEntryRelModelImpl
			assetListEntrySegmentsEntryRelModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] =
					assetListEntrySegmentsEntryRelModelImpl.
						getColumnOriginalValue(columnName);
			}
			else {
				arguments[i] =
					assetListEntrySegmentsEntryRelModelImpl.getColumnValue(
						columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}