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

package com.liferay.knowledge.base.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Peter Shin
 */
public class RatingsStatsUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateRatingsStats();
	}

	protected long getClassNameId(String className) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select classNameId from ClassName_ where value = ?")) {

			preparedStatement.setString(1, className);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getLong("classNameId");
				}

				return 0;
			}
		}
	}

	private void _updateRatingsStats() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select statsId, totalScore, averageScore from RatingsStats " +
					"where classNameId = " +
						getClassNameId(_CLASS_NAME_ARTICLE));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long statsId = resultSet.getLong("statsId");
				double totalScore = resultSet.getDouble("totalScore");
				double averageScore = resultSet.getDouble("averageScore");

				runSQL(
					StringBundler.concat(
						"update RatingsStats set totalScore = ", totalScore * 2,
						", averageScore = ", averageScore * 2,
						" where statsId = ", statsId));
			}
		}
	}

	private static final String _CLASS_NAME_ARTICLE =
		"com.liferay.knowledgebase.model.Article";

}