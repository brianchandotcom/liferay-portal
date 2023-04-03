/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.service.persistence.impl;

import com.liferay.osb.faro.model.FaroProjectEmailAddressDomainTable;
import com.liferay.osb.faro.model.impl.FaroProjectEmailAddressDomainImpl;
import com.liferay.osb.faro.model.impl.FaroProjectEmailAddressDomainModelImpl;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The arguments resolver class for retrieving value from FaroProjectEmailAddressDomain.
 *
 * @author Matthew Kong
 * @generated
 */
@OSGiBeanProperties(
	property = {
		"class.name=com.liferay.osb.faro.model.impl.FaroProjectEmailAddressDomainImpl",
		"table.name=OSBFaro_FaroProjectEmailAddressDomain"
	},
	service = ArgumentsResolver.class
)
public class FaroProjectEmailAddressDomainModelArgumentsResolver
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

		FaroProjectEmailAddressDomainModelImpl
			faroProjectEmailAddressDomainModelImpl =
				(FaroProjectEmailAddressDomainModelImpl)baseModel;

		long columnBitmask =
			faroProjectEmailAddressDomainModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(
				faroProjectEmailAddressDomainModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					faroProjectEmailAddressDomainModelImpl.getColumnBitmask(
						columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(
				faroProjectEmailAddressDomainModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return FaroProjectEmailAddressDomainImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return FaroProjectEmailAddressDomainTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		FaroProjectEmailAddressDomainModelImpl
			faroProjectEmailAddressDomainModelImpl,
		String[] columnNames, boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] =
					faroProjectEmailAddressDomainModelImpl.
						getColumnOriginalValue(columnName);
			}
			else {
				arguments[i] =
					faroProjectEmailAddressDomainModelImpl.getColumnValue(
						columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}