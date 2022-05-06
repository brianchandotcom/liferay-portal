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

package com.liferay.notifications.admin.service.persistence.impl;

import com.liferay.notifications.admin.model.NotificationsQueueEntryTable;
import com.liferay.notifications.admin.model.impl.NotificationsQueueEntryImpl;
import com.liferay.notifications.admin.model.impl.NotificationsQueueEntryModelImpl;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from NotificationsQueueEntry.
 *
 * @author Gabriel Albuquerque
 * @generated
 */
@Component(
	immediate = true,
	service = {
		NotificationsQueueEntryModelArgumentsResolver.class,
		ArgumentsResolver.class
	}
)
public class NotificationsQueueEntryModelArgumentsResolver
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

		NotificationsQueueEntryModelImpl notificationsQueueEntryModelImpl =
			(NotificationsQueueEntryModelImpl)baseModel;

		long columnBitmask =
			notificationsQueueEntryModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(
				notificationsQueueEntryModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					notificationsQueueEntryModelImpl.getColumnBitmask(
						columnName);
			}

			if (finderPath.isBaseModelResult() &&
				(NotificationsQueueEntryPersistenceImpl.
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION ==
						finderPath.getCacheName())) {

				finderPathColumnBitmask |= _ORDER_BY_COLUMNS_BITMASK;
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(
				notificationsQueueEntryModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return NotificationsQueueEntryImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return NotificationsQueueEntryTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		NotificationsQueueEntryModelImpl notificationsQueueEntryModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] =
					notificationsQueueEntryModelImpl.getColumnOriginalValue(
						columnName);
			}
			else {
				arguments[i] = notificationsQueueEntryModelImpl.getColumnValue(
					columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

	private static final long _ORDER_BY_COLUMNS_BITMASK;

	static {
		long orderByColumnsBitmask = 0;

		orderByColumnsBitmask |=
			NotificationsQueueEntryModelImpl.getColumnBitmask("priority");

		_ORDER_BY_COLUMNS_BITMASK = orderByColumnsBitmask;
	}

}