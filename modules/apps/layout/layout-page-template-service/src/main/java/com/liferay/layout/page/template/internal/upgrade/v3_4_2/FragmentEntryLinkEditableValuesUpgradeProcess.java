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

package com.liferay.layout.page.template.internal.upgrade.v3_4_2;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Rubén Pulido
 */
public class FragmentEntryLinkEditableValuesUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select fragmentEntryLinkId,editableValues,rendererKey from " +
					"FragmentEntryLink where rendererKey = " +
						"'BASIC_COMPONENT-separator'");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update FragmentEntryLink set editableValues = ? " +
							"where fragmentEntryLinkId = ?"));
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				JSONObject editablesJSONObject =
					JSONFactoryUtil.createJSONObject(
						resultSet.getString("editableValues"));

				JSONObject configurationJSONObject =
					editablesJSONObject.getJSONObject(
						"com.liferay.fragment.entry.processor.freemarker." +
							"FreeMarkerFragmentEntryProcessor");

				if (configurationJSONObject == null) {
					continue;
				}

				if (configurationJSONObject.has("verticalSpace")) {
					configurationJSONObject.put(
						"bottomSpacing",
						configurationJSONObject.remove("verticalSpace"));
				}

				preparedStatement2.setString(1, editablesJSONObject.toString());
				preparedStatement2.setLong(
					2, resultSet.getLong("fragmentEntryLinkId"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}