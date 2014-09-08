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

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.util.TranslationUpdater;
import com.liferay.portal.model.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseUpgradeTranslations extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		long classNameId = getClassNameId(Company.class);

		Iterable<Long[]> companyAndGroupIds = getCompanyAndGroupIds(
			classNameId);

		for (Long[] companyAndGroupId : companyAndGroupIds) {
			Long companyId = companyAndGroupId[0];
			Long groupId = companyAndGroupId[1];

			for (TranslationUpdater translationUpdater :
					getTranslationUpdaters()) {

				translationUpdater.update(companyId, groupId);
			}
		}
	}

	protected long getClassNameId(Class<?> modelClass) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DataAccess.getUpgradeOptimizedConnection();

			preparedStatement = connection.prepareStatement(
				"select classNameId from ClassName_ where value = ?");

			preparedStatement.setString(1, modelClass.getName());

			resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				throw new IllegalStateException(
					String.format(
						"No row found in table ClassName_ for value %s",
						modelClass.getName()));
			}

			return resultSet.getLong(1);
		}
		finally {
			DataAccess.cleanUp(connection, preparedStatement, resultSet);
		}
	}

	protected Iterable<Long[]> getCompanyAndGroupIds(long classNameId)
		throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DataAccess.getUpgradeOptimizedConnection();
			preparedStatement = connection.prepareStatement(
				"select companyId, groupId from Group_ where classNameId = ?");

			preparedStatement.setLong(1, classNameId);
			resultSet = preparedStatement.executeQuery();

			Collection<Long[]> companyAndGroupIds = new ArrayList<Long[]>();

			while (resultSet.next()) {
				Long[] companyIdAndGroupId = new Long[2];

				companyIdAndGroupId[0] = resultSet.getLong(1);
				companyIdAndGroupId[1] = resultSet.getLong(2);

				companyAndGroupIds.add(companyIdAndGroupId);
			}

			return companyAndGroupIds;
		}
		finally {
			DataAccess.cleanUp(connection, preparedStatement, resultSet);
		}
	}

	protected abstract Collection<TranslationUpdater> getTranslationUpdaters();

}