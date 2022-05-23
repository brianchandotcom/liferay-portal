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

package com.liferay.object.internal.upgrade.v3_8_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Guilherme Camacho
 */
public class ObjectEntryExternalReferenceCodeUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		processConcurrently(
			"select objectEntryId from ObjectEntry where " +
				"externalReferenceCode is null or externalReferenceCode = ''",
			resultSet -> new Object[] {resultSet.getLong("objectEntryId")},
			columns -> {
				long objectEntryId = (long)columns[0];

				String sql =
					"update ObjectEntry set externalReferenceCode = ? where " +
						"objectEntryId = ?";

				try (PreparedStatement updatePreparedStatement =
						connection.prepareStatement(sql)) {

					updatePreparedStatement.setString(
						1, String.valueOf(objectEntryId));
					updatePreparedStatement.setLong(2, objectEntryId);

					updatePreparedStatement.executeUpdate();
				}
			},
			"Unable to update external reference code for object entries");
	}

}