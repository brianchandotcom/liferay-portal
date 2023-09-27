/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.search.experiences.model.SXPBlueprintTable;
import com.liferay.search.experiences.model.impl.SXPBlueprintImpl;
import com.liferay.search.experiences.model.impl.SXPBlueprintModelImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from SXPBlueprint.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	property = {
		"class.name=com.liferay.search.experiences.model.impl.SXPBlueprintImpl",
		"table.name=SXPBlueprint"
	},
	service = ArgumentsResolver.class
)
public class SXPBlueprintModelArgumentsResolver implements ArgumentsResolver {

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

		SXPBlueprintModelImpl sxpBlueprintModelImpl =
			(SXPBlueprintModelImpl)baseModel;

		long columnBitmask = sxpBlueprintModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(sxpBlueprintModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					sxpBlueprintModelImpl.getColumnBitmask(columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(sxpBlueprintModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return SXPBlueprintImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return SXPBlueprintTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		SXPBlueprintModelImpl sxpBlueprintModelImpl, String[] columnNames,
		boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] = sxpBlueprintModelImpl.getColumnOriginalValue(
					columnName);
			}
			else {
				arguments[i] = sxpBlueprintModelImpl.getColumnValue(columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}