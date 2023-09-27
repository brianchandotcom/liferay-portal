/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service.persistence.impl;

import com.liferay.notification.model.NotificationQueueEntryTable;
import com.liferay.notification.model.impl.NotificationQueueEntryImpl;
import com.liferay.notification.model.impl.NotificationQueueEntryModelImpl;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from NotificationQueueEntry.
 *
 * @author Gabriel Albuquerque
 * @generated
 */
@Component(
	property = {
		"class.name=com.liferay.notification.model.impl.NotificationQueueEntryImpl",
		"table.name=NotificationQueueEntry"
	},
	service = ArgumentsResolver.class
)
public class NotificationQueueEntryModelArgumentsResolver
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

		NotificationQueueEntryModelImpl notificationQueueEntryModelImpl =
			(NotificationQueueEntryModelImpl)baseModel;

		long columnBitmask = notificationQueueEntryModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(
				notificationQueueEntryModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					notificationQueueEntryModelImpl.getColumnBitmask(
						columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(
				notificationQueueEntryModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return NotificationQueueEntryImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return NotificationQueueEntryTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		NotificationQueueEntryModelImpl notificationQueueEntryModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] =
					notificationQueueEntryModelImpl.getColumnOriginalValue(
						columnName);
			}
			else {
				arguments[i] = notificationQueueEntryModelImpl.getColumnValue(
					columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}