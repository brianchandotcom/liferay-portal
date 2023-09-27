/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service.persistence.impl;

import com.liferay.notification.model.NotificationRecipientTable;
import com.liferay.notification.model.impl.NotificationRecipientImpl;
import com.liferay.notification.model.impl.NotificationRecipientModelImpl;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from NotificationRecipient.
 *
 * @author Gabriel Albuquerque
 * @generated
 */
@Component(
	property = {
		"class.name=com.liferay.notification.model.impl.NotificationRecipientImpl",
		"table.name=NotificationRecipient"
	},
	service = ArgumentsResolver.class
)
public class NotificationRecipientModelArgumentsResolver
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

		NotificationRecipientModelImpl notificationRecipientModelImpl =
			(NotificationRecipientModelImpl)baseModel;

		long columnBitmask = notificationRecipientModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(
				notificationRecipientModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					notificationRecipientModelImpl.getColumnBitmask(columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(
				notificationRecipientModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return NotificationRecipientImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return NotificationRecipientTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		NotificationRecipientModelImpl notificationRecipientModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] =
					notificationRecipientModelImpl.getColumnOriginalValue(
						columnName);
			}
			else {
				arguments[i] = notificationRecipientModelImpl.getColumnValue(
					columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}