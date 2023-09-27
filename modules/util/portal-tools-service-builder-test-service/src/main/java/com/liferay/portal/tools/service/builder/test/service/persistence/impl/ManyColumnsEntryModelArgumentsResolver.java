/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.ManyColumnsEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.ManyColumnsEntryModelImpl;

import java.util.Objects;

/**
 * The arguments resolver class for retrieving value from ManyColumnsEntry.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@OSGiBeanProperties(
	property = {
		"class.name=com.liferay.portal.tools.service.builder.test.model.impl.ManyColumnsEntryImpl",
		"table.name=ManyColumnsEntry"
	},
	service = ArgumentsResolver.class
)
public class ManyColumnsEntryModelArgumentsResolver
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

		ManyColumnsEntryModelImpl manyColumnsEntryModelImpl =
			(ManyColumnsEntryModelImpl)baseModel;

		if (!checkColumn ||
			_hasModifiedColumns(manyColumnsEntryModelImpl, columnNames)) {

			return _getValue(manyColumnsEntryModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return ManyColumnsEntryImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return ManyColumnsEntryTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		ManyColumnsEntryModelImpl manyColumnsEntryModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] = manyColumnsEntryModelImpl.getColumnOriginalValue(
					columnName);
			}
			else {
				arguments[i] = manyColumnsEntryModelImpl.getColumnValue(
					columnName);
			}
		}

		return arguments;
	}

	private static boolean _hasModifiedColumns(
		ManyColumnsEntryModelImpl manyColumnsEntryModelImpl,
		String[] columnNames) {

		if (columnNames.length == 0) {
			return false;
		}

		for (String columnName : columnNames) {
			if (!Objects.equals(
					manyColumnsEntryModelImpl.getColumnOriginalValue(
						columnName),
					manyColumnsEntryModelImpl.getColumnValue(columnName))) {

				return true;
			}
		}

		return false;
	}

}