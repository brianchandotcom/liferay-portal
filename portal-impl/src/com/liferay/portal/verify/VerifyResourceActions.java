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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Michael Bowerman
 */
public class VerifyResourceActions extends VerifyProcess {

	protected void deleteDuplicateBitwiseValuesOnResource() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select name, bitwiseValue, min(resourceActionId) as " +
					"minResourceActionId from ResourceAction group by name, " +
						"bitwiseValue having count(resourceActionId) > 1");
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"select resourceActionId, actionId from ResourceAction where " +
					"name = ? and bitwiseValue = ? and resourceActionId != ?");
			ResultSet resultSet1 = preparedStatement1.executeQuery()) {

			while (resultSet1.next()) {
				String name = resultSet1.getString("name");
				long bitwiseValue = resultSet1.getLong("bitwiseValue");
				long minResourceActionId = resultSet1.getLong(
					"minResourceActionId");

				preparedStatement2.setString(1, name);
				preparedStatement2.setLong(2, bitwiseValue);
				preparedStatement2.setLong(3, minResourceActionId);

				try (ResultSet resultSet2 = preparedStatement2.executeQuery()) {
					while (resultSet2.next()) {
						if (_log.isInfoEnabled()) {
							StringBundler sb = new StringBundler(7);

							sb.append("Deleting resource action ");
							sb.append(resultSet2.getString("actionId"));
							sb.append(" from resource ");
							sb.append(name);
							sb.append(" because its bitwise value is the ");
							sb.append("same as another resource action on ");
							sb.append("the same resource");

							_log.info(sb.toString());
						}

						ResourceActionLocalServiceUtil.deleteResourceAction(
							resultSet2.getLong("resourceActionId"));
					}
				}
			}
		}
	}

	@Override
	protected void doVerify() throws Exception {
		deleteDuplicateBitwiseValuesOnResource();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyResourceActions.class);

}