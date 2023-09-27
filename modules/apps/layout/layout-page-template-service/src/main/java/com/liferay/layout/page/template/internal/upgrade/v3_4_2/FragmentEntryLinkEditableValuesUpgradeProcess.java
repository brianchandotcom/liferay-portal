/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v3_4_2;

import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
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
					connection,
					"update FragmentEntryLink set editableValues = ? where " +
						"fragmentEntryLinkId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				JSONObject editablesJSONObject =
					JSONFactoryUtil.createJSONObject(
						resultSet.getString("editableValues"));

				JSONObject configurationJSONObject =
					editablesJSONObject.getJSONObject(
						FragmentEntryProcessorConstants.
							KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

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