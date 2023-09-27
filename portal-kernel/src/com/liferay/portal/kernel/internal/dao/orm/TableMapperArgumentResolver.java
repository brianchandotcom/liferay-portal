/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.internal.dao.orm;

import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;

/**
 * @author Preston Crary
 */
public class TableMapperArgumentResolver implements ArgumentsResolver {

	public TableMapperArgumentResolver(String tableName) {
		_tableName = tableName;
	}

	@Override
	public Object[] getArguments(
		FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
		boolean original) {

		return null;
	}

	@Override
	public String getClassName() {
		return _tableName;
	}

	@Override
	public String getTableName() {
		return _tableName;
	}

	private final String _tableName;

}