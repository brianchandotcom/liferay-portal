/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.model.BaseModel;

import java.sql.PreparedStatement;

import java.util.function.Function;

/**
 * @author Shuyang Zhou
 */
public class LongPKRemoveFunction<T extends BaseModel<T>>
	implements Function<T, T> {

	public LongPKRemoveFunction(
		BasePersistence<T> basePersistence, String tableName,
		String pkFieldName) {

		_basePersistence = basePersistence;

		_deleteSQL = StringBundler.concat(
			"delete from ", tableName, " where ", pkFieldName,
			" = ? and ctCollectionId = ?");
	}

	@Override
	public T apply(T baseModel) {
		Session session = _basePersistence.openSession();

		try {
			session.apply(
				connection -> {
					try (PreparedStatement preparedStatement =
							connection.prepareStatement(_deleteSQL)) {

						preparedStatement.setLong(
							1, (long)baseModel.getPrimaryKeyObj());
						preparedStatement.setLong(
							2, CTCollectionThreadLocal.getCTCollectionId());

						preparedStatement.executeUpdate();
					}
				});
		}
		finally {
			_basePersistence.closeSession(session);
		}

		return baseModel;
	}

	private final BasePersistence<T> _basePersistence;
	private final String _deleteSQL;

}