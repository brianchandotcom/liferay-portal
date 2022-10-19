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

package com.liferay.notification.service.persistence.impl;

import com.liferay.notification.model.NotificationQueueEntryRecipientTable;
import com.liferay.notification.model.impl.NotificationQueueEntryRecipientImpl;
import com.liferay.notification.model.impl.NotificationQueueEntryRecipientModelImpl;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from NotificationQueueEntryRecipient.
 *
 * @author Gabriel Albuquerque
 * @generated
 */
@Component(service = ArgumentsResolver.class)
public class NotificationQueueEntryRecipientModelArgumentsResolver
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

		NotificationQueueEntryRecipientModelImpl
			notificationQueueEntryRecipientModelImpl =
				(NotificationQueueEntryRecipientModelImpl)baseModel;

		long columnBitmask =
			notificationQueueEntryRecipientModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(
				notificationQueueEntryRecipientModelImpl, columnNames,
				original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					notificationQueueEntryRecipientModelImpl.getColumnBitmask(
						columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(
				notificationQueueEntryRecipientModelImpl, columnNames,
				original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return NotificationQueueEntryRecipientImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return NotificationQueueEntryRecipientTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		NotificationQueueEntryRecipientModelImpl
			notificationQueueEntryRecipientModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] =
					notificationQueueEntryRecipientModelImpl.
						getColumnOriginalValue(columnName);
			}
			else {
				arguments[i] =
					notificationQueueEntryRecipientModelImpl.getColumnValue(
						columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}