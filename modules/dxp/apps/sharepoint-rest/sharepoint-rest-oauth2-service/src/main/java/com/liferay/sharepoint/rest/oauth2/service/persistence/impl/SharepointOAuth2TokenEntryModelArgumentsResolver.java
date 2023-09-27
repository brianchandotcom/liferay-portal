/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.oauth2.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntryTable;
import com.liferay.sharepoint.rest.oauth2.model.impl.SharepointOAuth2TokenEntryImpl;
import com.liferay.sharepoint.rest.oauth2.model.impl.SharepointOAuth2TokenEntryModelImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from SharepointOAuth2TokenEntry.
 *
 * @author Adolfo Pérez
 * @generated
 */
@Component(
	property = {
		"class.name=com.liferay.sharepoint.rest.oauth2.model.impl.SharepointOAuth2TokenEntryImpl",
		"table.name=SharepointOAuth2TokenEntry"
	},
	service = ArgumentsResolver.class
)
public class SharepointOAuth2TokenEntryModelArgumentsResolver
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

		SharepointOAuth2TokenEntryModelImpl
			sharepointOAuth2TokenEntryModelImpl =
				(SharepointOAuth2TokenEntryModelImpl)baseModel;

		long columnBitmask =
			sharepointOAuth2TokenEntryModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(
				sharepointOAuth2TokenEntryModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					sharepointOAuth2TokenEntryModelImpl.getColumnBitmask(
						columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(
				sharepointOAuth2TokenEntryModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return SharepointOAuth2TokenEntryImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return SharepointOAuth2TokenEntryTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		SharepointOAuth2TokenEntryModelImpl sharepointOAuth2TokenEntryModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] =
					sharepointOAuth2TokenEntryModelImpl.getColumnOriginalValue(
						columnName);
			}
			else {
				arguments[i] =
					sharepointOAuth2TokenEntryModelImpl.getColumnValue(
						columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}