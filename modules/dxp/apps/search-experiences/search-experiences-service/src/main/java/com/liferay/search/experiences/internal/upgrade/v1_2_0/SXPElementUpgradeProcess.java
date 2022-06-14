/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.upgrade.v1_2_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Wade Cao
 */
public class SXPElementUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn("SXPElement", "key_")) {
			alterTableDropColumn("SXPElement", "key_");
		}

		if (!hasColumn("SXPElement", "externalReferenceCode")) {
			alterTableAddColumn(
				"SXPElement", "externalReferenceCode", "VARCHAR(75)");
		}

		StringBundler selectSB = new StringBundler(3);

		selectSB.append("select externalReferenceCode, description, ");
		selectSB.append("elementDefinitionJSON, readOnly, title, ");
		selectSB.append("sxpElementId, version from SXPElement");

		StringBundler updateSB = new StringBundler(3);

		updateSB.append("update SXPElement externalReferenceCode = ?, set ");
		updateSB.append("description = ?, elementDefinitionJSON = ?, title = ");
		updateSB.append("?, version = ? where sxpElementId = ?");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				selectSB.toString());
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, updateSB.toString())) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					String description = resultSet.getString("description");
					String elementDefinitionJSON = resultSet.getString(
						"elementDefinitionJSON");
					String externalReferenceCode = resultSet.getString(
						"externalReferenceCode");

					boolean readOnly = resultSet.getBoolean("readOnly");

					long sxpElementId = resultSet.getLong("sxpElementId");

					String title = resultSet.getString("title");
					String version = resultSet.getString("version");

					if (Validator.isNull(externalReferenceCode)) {
						externalReferenceCode = String.valueOf(sxpElementId);
					}

					if (readOnly) {
						description = _renameDescription(description);
						elementDefinitionJSON = _renameElementDefinitionJSON(
							elementDefinitionJSON, title);
						title = _renameTitle(title);
					}

					if (Validator.isNull(version)) {
						version = "1.0";
					}

					preparedStatement2.setString(1, externalReferenceCode);
					preparedStatement2.setString(2, description);
					preparedStatement2.setString(3, elementDefinitionJSON);
					preparedStatement2.setString(4, title);
					preparedStatement2.setString(5, version);

					preparedStatement2.setLong(6, sxpElementId);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private String _renameDescription(String currentDescription) {
		String oldDescription =
			"Boost contents in a category for users belonging to a given " +
				"user segment";

		if (currentDescription.indexOf(oldDescription) > 0) {
			return StringUtil.replace(
				currentDescription, oldDescription,
				"Boost contents in a category for users belonging to the " +
					"given user segments");
		}

		return currentDescription;
	}

	private String _renameElementDefinitionJSON(
		String currentElementDefinition, String title) {

		String currentTitle =
			"Boost Contents in a Category for a Period of Time";

		if (title.indexOf(currentTitle) > 0) {
			currentElementDefinition = StringUtil.replace(
				currentElementDefinition, "Create Date: From", "Date: From");
			currentElementDefinition = StringUtil.replace(
				currentElementDefinition, "Creat Date: To", "Date: To");
		}

		currentTitle = "Boost Contents in a Category for the Time of Day";

		if (title.indexOf(currentTitle) > 0) {
			currentElementDefinition = StringUtil.replace(
				currentElementDefinition, "Morning (4am - 12am)",
				"Morning (4am - 12pm)");
		}

		return currentElementDefinition;
	}

	private String _renameTitle(String currentTitle) {
		String oldTitle = "Search with the Lucene Syntax";

		if (currentTitle.indexOf(oldTitle) > 0) {
			return StringUtil.replace(
				currentTitle, oldTitle, "Search with Query String Syntax");
		}

		return currentTitle;
	}

}