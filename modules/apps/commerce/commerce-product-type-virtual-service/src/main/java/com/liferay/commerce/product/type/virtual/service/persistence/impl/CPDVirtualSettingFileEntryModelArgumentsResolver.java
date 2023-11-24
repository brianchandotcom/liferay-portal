/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.service.persistence.impl;

import com.liferay.commerce.product.type.virtual.model.CPDVirtualSettingFileEntryTable;
import com.liferay.commerce.product.type.virtual.model.impl.CPDVirtualSettingFileEntryImpl;
import com.liferay.commerce.product.type.virtual.model.impl.CPDVirtualSettingFileEntryModelImpl;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from CPDVirtualSettingFileEntry.
 *
 * @author Marco Leo
 * @generated
 */
@Component(
	property = {
		"class.name=com.liferay.commerce.product.type.virtual.model.impl.CPDVirtualSettingFileEntryImpl",
		"table.name=CPDVirtualSettingFileEntry"
	},
	service = ArgumentsResolver.class
)
public class CPDVirtualSettingFileEntryModelArgumentsResolver
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

		CPDVirtualSettingFileEntryModelImpl
			cpdVirtualSettingFileEntryModelImpl =
				(CPDVirtualSettingFileEntryModelImpl)baseModel;

		long columnBitmask =
			cpdVirtualSettingFileEntryModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(
				cpdVirtualSettingFileEntryModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					cpdVirtualSettingFileEntryModelImpl.getColumnBitmask(
						columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(
				cpdVirtualSettingFileEntryModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return CPDVirtualSettingFileEntryImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return CPDVirtualSettingFileEntryTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		CPDVirtualSettingFileEntryModelImpl cpdVirtualSettingFileEntryModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] =
					cpdVirtualSettingFileEntryModelImpl.getColumnOriginalValue(
						columnName);
			}
			else {
				arguments[i] =
					cpdVirtualSettingFileEntryModelImpl.getColumnValue(
						columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}