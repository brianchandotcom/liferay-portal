/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.service.persistence.impl;

import com.liferay.oauth2.provider.model.OAuth2ScopeGrantTable;
import com.liferay.oauth2.provider.model.impl.OAuth2ScopeGrantImpl;
import com.liferay.oauth2.provider.model.impl.OAuth2ScopeGrantModelImpl;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from OAuth2ScopeGrant.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	property = {
		"class.name=com.liferay.oauth2.provider.model.impl.OAuth2ScopeGrantImpl",
		"table.name=OAuth2ScopeGrant"
	},
	service = ArgumentsResolver.class
)
public class OAuth2ScopeGrantModelArgumentsResolver
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

		OAuth2ScopeGrantModelImpl oAuth2ScopeGrantModelImpl =
			(OAuth2ScopeGrantModelImpl)baseModel;

		long columnBitmask = oAuth2ScopeGrantModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(oAuth2ScopeGrantModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					oAuth2ScopeGrantModelImpl.getColumnBitmask(columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(oAuth2ScopeGrantModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return OAuth2ScopeGrantImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return OAuth2ScopeGrantTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		OAuth2ScopeGrantModelImpl oAuth2ScopeGrantModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] = oAuth2ScopeGrantModelImpl.getColumnOriginalValue(
					columnName);
			}
			else {
				arguments[i] = oAuth2ScopeGrantModelImpl.getColumnValue(
					columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}