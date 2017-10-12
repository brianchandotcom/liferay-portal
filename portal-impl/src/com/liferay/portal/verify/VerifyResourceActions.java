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
		StringBundler sb = new StringBundler(3);

		sb.append("select name, bitwiseValue, min(resourceActionId) as ");
		sb.append("minResourceActionId from ResourceAction group by name, ");
		sb.append("bitwiseValue having count(resourceActionId) > 1");

		String sql1 = sb.toString();

		sb.setIndex(0);

		sb.append("select resourceActionId, actionId from ResourceAction ");
		sb.append("where name = ? and bitwiseValue = ? and ");
		sb.append("resourceActionId != ?");

		String sql2 = sb.toString();

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(sql1);
			PreparedStatement ps2 = connection.prepareStatement(sql2);
			ResultSet rs1 = ps1.executeQuery()) {

			while (rs1.next()) {
				String name = rs1.getString("name");
				long bitwiseValue = rs1.getLong("bitwiseValue");
				long minResourceActionId = rs1.getLong("minResourceActionId");

				ps2.setString(1, name);
				ps2.setLong(2, bitwiseValue);
				ps2.setLong(3, minResourceActionId);

				try (ResultSet rs2 = ps2.executeQuery()) {
					while (rs2.next()) {
						if (_log.isInfoEnabled()) {
							sb = new StringBundler(7);

							sb.append("Deleting resource action ");
							sb.append(rs2.getString("actionId"));
							sb.append(" from resource ");
							sb.append(name);
							sb.append(" because its bitwise value is the ");
							sb.append("same as another resource action on ");
							sb.append("the same resource");

							_log.info(sb.toString());
						}

						long resourceActionId = rs2.getLong("resourceActionId");

						ResourceActionLocalServiceUtil.deleteResourceAction(
							resourceActionId);
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