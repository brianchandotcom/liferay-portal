/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.wiki.model.WikiPageTable;
import com.liferay.wiki.model.impl.WikiPageImpl;
import com.liferay.wiki.model.impl.WikiPageModelImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from WikiPage.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	property = {
		"class.name=com.liferay.wiki.model.impl.WikiPageImpl",
		"table.name=WikiPage"
	},
	service = ArgumentsResolver.class
)
public class WikiPageModelArgumentsResolver implements ArgumentsResolver {

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

		WikiPageModelImpl wikiPageModelImpl = (WikiPageModelImpl)baseModel;

		long columnBitmask = wikiPageModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(wikiPageModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |= wikiPageModelImpl.getColumnBitmask(
					columnName);
			}

			if (finderPath.isBaseModelResult() &&
				(WikiPagePersistenceImpl.
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION ==
						finderPath.getCacheName())) {

				finderPathColumnBitmask |= _ORDER_BY_COLUMNS_BITMASK;
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(wikiPageModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return WikiPageImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return WikiPageTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		WikiPageModelImpl wikiPageModelImpl, String[] columnNames,
		boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] = wikiPageModelImpl.getColumnOriginalValue(
					columnName);
			}
			else {
				arguments[i] = wikiPageModelImpl.getColumnValue(columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

	private static final long _ORDER_BY_COLUMNS_BITMASK;

	static {
		long orderByColumnsBitmask = 0;

		orderByColumnsBitmask |= WikiPageModelImpl.getColumnBitmask("nodeId");
		orderByColumnsBitmask |= WikiPageModelImpl.getColumnBitmask("title");
		orderByColumnsBitmask |= WikiPageModelImpl.getColumnBitmask("version");

		_ORDER_BY_COLUMNS_BITMASK = orderByColumnsBitmask;
	}

}