/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.service.persistence.impl;

import com.liferay.exportimport.report.model.ImportReportEntryTable;
import com.liferay.exportimport.report.model.impl.ImportReportEntryImpl;
import com.liferay.exportimport.report.model.impl.ImportReportEntryModelImpl;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from ImportReportEntry.
 *
 * @author Carlos Correa
 * @generated
 */
@Component(
	property = {
		"class.name=com.liferay.exportimport.report.model.impl.ImportReportEntryImpl",
		"table.name=ImportReportEntry"
	},
	service = ArgumentsResolver.class
)
public class ImportReportEntryModelArgumentsResolver
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

		ImportReportEntryModelImpl importReportEntryModelImpl =
			(ImportReportEntryModelImpl)baseModel;

		long columnBitmask = importReportEntryModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(importReportEntryModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					importReportEntryModelImpl.getColumnBitmask(columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(importReportEntryModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return ImportReportEntryImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return ImportReportEntryTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		ImportReportEntryModelImpl importReportEntryModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] =
					importReportEntryModelImpl.getColumnOriginalValue(
						columnName);
			}
			else {
				arguments[i] = importReportEntryModelImpl.getColumnValue(
					columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}