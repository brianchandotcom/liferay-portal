/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.service.persistence.impl;

import com.liferay.layout.seo.model.LayoutSEOEntryCustomMetaTagTable;
import com.liferay.layout.seo.model.impl.LayoutSEOEntryCustomMetaTagImpl;
import com.liferay.layout.seo.model.impl.LayoutSEOEntryCustomMetaTagModelImpl;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from LayoutSEOEntryCustomMetaTag.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	property = {
		"class.name=com.liferay.layout.seo.model.impl.LayoutSEOEntryCustomMetaTagImpl",
		"table.name=LayoutSEOEntryCustomMetaTag"
	},
	service = ArgumentsResolver.class
)
public class LayoutSEOEntryCustomMetaTagModelArgumentsResolver
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

		LayoutSEOEntryCustomMetaTagModelImpl
			layoutSEOEntryCustomMetaTagModelImpl =
				(LayoutSEOEntryCustomMetaTagModelImpl)baseModel;

		long columnBitmask =
			layoutSEOEntryCustomMetaTagModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(
				layoutSEOEntryCustomMetaTagModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					layoutSEOEntryCustomMetaTagModelImpl.getColumnBitmask(
						columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(
				layoutSEOEntryCustomMetaTagModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return LayoutSEOEntryCustomMetaTagImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return LayoutSEOEntryCustomMetaTagTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		LayoutSEOEntryCustomMetaTagModelImpl
			layoutSEOEntryCustomMetaTagModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] =
					layoutSEOEntryCustomMetaTagModelImpl.getColumnOriginalValue(
						columnName);
			}
			else {
				arguments[i] =
					layoutSEOEntryCustomMetaTagModelImpl.getColumnValue(
						columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}