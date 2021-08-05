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

package com.liferay.saml.persistence.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.saml.persistence.model.SamlPeerBindingTable;
import com.liferay.saml.persistence.model.impl.SamlPeerBindingImpl;
import com.liferay.saml.persistence.model.impl.SamlPeerBindingModelImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * The arguments resolver class for retrieving value from SamlPeerBinding.
 *
 * @author Mika Koivisto
 * @generated
 */
@Component(
	immediate = true,
	service = {
		SamlPeerBindingModelArgumentsResolver.class, ArgumentsResolver.class
	}
)
public class SamlPeerBindingModelArgumentsResolver
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

		SamlPeerBindingModelImpl samlPeerBindingModelImpl =
			(SamlPeerBindingModelImpl)baseModel;

		long columnBitmask = samlPeerBindingModelImpl.getColumnBitmask();

		if (!checkColumn || (columnBitmask == 0)) {
			return _getValue(samlPeerBindingModelImpl, columnNames, original);
		}

		Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
			finderPath);

		if (finderPathColumnBitmask == null) {
			finderPathColumnBitmask = 0L;

			for (String columnName : columnNames) {
				finderPathColumnBitmask |=
					samlPeerBindingModelImpl.getColumnBitmask(columnName);
			}

			_finderPathColumnBitmasksCache.put(
				finderPath, finderPathColumnBitmask);
		}

		if ((columnBitmask & finderPathColumnBitmask) != 0) {
			return _getValue(samlPeerBindingModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return SamlPeerBindingImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return SamlPeerBindingTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		SamlPeerBindingModelImpl samlPeerBindingModelImpl, String[] columnNames,
		boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] = samlPeerBindingModelImpl.getColumnOriginalValue(
					columnName);
			}
			else {
				arguments[i] = samlPeerBindingModelImpl.getColumnValue(
					columnName);
			}
		}

		return arguments;
	}

	private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache =
		new ConcurrentHashMap<>();

}