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

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_1_5;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Objects;

/**
 * @author István András Dézsi
 */
public class DDMStructureLayoutUpgradeProcess extends UpgradeProcess {

	public DDMStructureLayoutUpgradeProcess(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDMStructureLayout.structureLayoutId, ",
					"DDMStructureLayout.definition from DDMStructureLayout ",
					"inner join DDMStructureVersion on ",
					"DDMStructureLayout.structureVersionId = ",
					"DDMStructureVersion.structureVersionId inner join ",
					"DDMStructure on DDMStructure.structureId = ",
					"DDMStructureVersion.structureId where ",
					"DDMStructure.classNameId = ?"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureLayout set definition = ? where " +
						"structureLayoutId = ?")) {

			preparedStatement1.setLong(
				1, PortalUtil.getClassNameId(DDMFormInstance.class.getName()));

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					JSONObject jsonObject = _jsonFactory.createJSONObject(
						resultSet.getString("definition"));

					if (!Objects.equals(
							jsonObject.getString("paginationMode"),
							"paginated")) {

						continue;
					}

					jsonObject.put("paginationMode", DDMFormLayout.MULTI_PAGES);

					preparedStatement2.setString(1, jsonObject.toString());
					preparedStatement2.setLong(
						2, resultSet.getLong("structureLayoutId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private final JSONFactory _jsonFactory;

}