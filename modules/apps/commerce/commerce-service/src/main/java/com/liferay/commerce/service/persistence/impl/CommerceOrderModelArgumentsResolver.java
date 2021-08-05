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

package com.liferay.commerce.service.persistence.impl;

import com.liferay.commerce.model.CommerceOrderTable;
import com.liferay.commerce.model.impl.CommerceOrderImpl;
import com.liferay.commerce.model.impl.CommerceOrderModelImpl;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The arguments resolver class for retrieving value from CommerceOrder.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceOrderModelArgumentsResolver implements ArgumentsResolver {

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

		CommerceOrderModelImpl commerceOrderModelImpl =
			(CommerceOrderModelImpl)baseModel;

		if (!checkColumn ||
			_hasModifiedColumns(commerceOrderModelImpl, columnNames) ||
			_hasModifiedColumns(commerceOrderModelImpl, _ORDER_BY_COLUMNS)) {

			return _getValue(commerceOrderModelImpl, columnNames, original);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return CommerceOrderImpl.class.getName();
	}

	@Override
	public String getTableName() {
		return CommerceOrderTable.INSTANCE.getTableName();
	}

	private static Object[] _getValue(
		CommerceOrderModelImpl commerceOrderModelImpl, String[] columnNames,
		boolean original) {

		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] = commerceOrderModelImpl.getColumnOriginalValue(
					columnName);
			}
			else {
				arguments[i] = commerceOrderModelImpl.getColumnValue(
					columnName);
			}
		}

		return arguments;
	}

	private static boolean _hasModifiedColumns(
		CommerceOrderModelImpl commerceOrderModelImpl, String[] columnNames) {

		if (columnNames.length == 0) {
			return false;
		}

		for (String columnName : columnNames) {
			if (!Objects.equals(
					commerceOrderModelImpl.getColumnOriginalValue(columnName),
					commerceOrderModelImpl.getColumnValue(columnName))) {

				return true;
			}
		}

		return false;
	}

	private static final String[] _ORDER_BY_COLUMNS;

	static {
		List<String> orderByColumns = new ArrayList<String>();

		orderByColumns.add("createDate");

		_ORDER_BY_COLUMNS = orderByColumns.toArray(new String[0]);
	}

}