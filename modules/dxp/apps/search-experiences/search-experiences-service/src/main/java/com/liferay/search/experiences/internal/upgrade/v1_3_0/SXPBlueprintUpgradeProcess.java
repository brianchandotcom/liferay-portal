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

package com.liferay.search.experiences.internal.upgrade.v1_3_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.model.impl.SXPElementImpl;
import com.liferay.search.experiences.rest.dto.v1_0.ElementDefinition;
import com.liferay.search.experiences.rest.dto.v1_0.ElementInstance;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.util.ElementInstanceUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Wade Cao
 */
public class SXPBlueprintUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeSXPElement();

		_upgradeSXPBlueprint();
	}

	private com.liferay.search.experiences.model.SXPElement _fetchSXPElement(
			long sxpElementId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select sxpElementId, externalReferenceCode, readOnly, " +
					"version from SXPElement where sxpElementId = " +
						sxpElementId);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				com.liferay.search.experiences.model.SXPElement sxpElement =
					new SXPElementImpl();

				sxpElement.setSXPElementId(resultSet.getLong("sxpElementId"));
				sxpElement.setExternalReferenceCode(
					resultSet.getString("externalReferenceCode"));
				sxpElement.setReadOnly(resultSet.getBoolean("readOnly"));
				sxpElement.setVersion(resultSet.getString("version"));

				return sxpElement;
			}
		}

		return null;
	}

	private String _getElementInstancesJSON(String elementInstancesJSON)
		throws Exception {

		ElementInstance[] elementInstances =
			ElementInstanceUtil.toElementInstances(elementInstancesJSON);

		if (ArrayUtil.isEmpty(elementInstances)) {
			return "{}";
		}

		for (ElementInstance elementInstance : elementInstances) {
			SXPElement sxpElement = elementInstance.getSxpElement();

			com.liferay.search.experiences.model.SXPElement
				serviceBuilderSXPElement = _fetchSXPElement(sxpElement.getId());

			if (serviceBuilderSXPElement == null) {
				_log.error(
					"No search experiences element exists with ID " +
						sxpElement.getId());

				continue;
			}

			if (serviceBuilderSXPElement.isReadOnly()) {
				Map<String, String> description_i18n =
					sxpElement.getDescription_i18n();

				description_i18n.put(
					"en-US", _renameDescription(description_i18n.get("en-US")));

				Map<String, String> title_i18n = sxpElement.getTitle_i18n();

				sxpElement.setElementDefinition(
					ElementDefinition.unsafeToDTO(
						_renameElementDefinitionJSON(
							String.valueOf(
								sxpElement.getElementDefinition()))));

				title_i18n.put("en-US", _renameTitle(title_i18n.get("en-US")));
			}

			sxpElement.setExternalReferenceCode(
				serviceBuilderSXPElement.getExternalReferenceCode());
			sxpElement.setVersion(serviceBuilderSXPElement.getVersion());
		}

		return Arrays.toString(elementInstances);
	}

	private String _renameDescription(String description) {
		return StringUtil.replace(
			description,
			"Boost contents in a category for users belonging to a given " +
				"user segment",
			"Boost contents in a category for users belonging to the given " +
				"user segments");
	}

	private String _renameElementDefinitionJSON(String elementDefinitionJSON) {
		return StringUtil.replace(
			elementDefinitionJSON,
			new String[] {
				"Create Date: From", "Create Date: To", "Morning (4am - 12am)"
			},
			new String[] {"Date: From", "Date: To", "Morning (4am - 12pm)"});
	}

	private String _renameTitle(String title) {
		return StringUtil.replace(
			title,
			new String[] {
				"Boost Contents in a Category for a User Segment",
				"Search with the Lucene Syntax"
			},
			new String[] {
				"Boost Contents in a Category for User Segments",
				"Search with Query String Syntax"
			});
	}

	private void _upgradeSXPBlueprint() throws Exception {
		alterTableDropColumn("SXPBlueprint", "key_");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select sxpBlueprintId, elementInstancesJSON, version from " +
					"SXPBlueprint");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SXPBlueprint set elementInstancesJSON = ?, " +
						"version = ? where sxpBlueprintId = ?")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					preparedStatement2.setString(
						1,
						_getElementInstancesJSON(
							resultSet.getString("elementInstancesJSON")));

					String version = resultSet.getString("version");

					if (Validator.isNull(version)) {
						version = "1.0";
					}

					preparedStatement2.setString(2, version);

					preparedStatement2.setLong(
						3, resultSet.getLong("sxpBlueprintId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private void _upgradeSXPElement() throws Exception {
		alterTableDropColumn("SXPElement", "key_");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select sxpElementId, description, elementDefinitionJSON, " +
					"readOnly, title, version from SXPElement");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"update SXPElement set description = ?, ",
						"elementDefinitionJSON = ?, title = ?, version = ? ",
						"where sxpElementId = ?"))) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					String description = resultSet.getString("description");
					String elementDefinitionJSON = resultSet.getString(
						"elementDefinitionJSON");
					String title = resultSet.getString("title");

					if (resultSet.getBoolean("readOnly")) {
						description = _renameDescription(description);
						elementDefinitionJSON = _renameElementDefinitionJSON(
							elementDefinitionJSON);
						title = _renameTitle(title);
					}

					preparedStatement2.setString(1, description);
					preparedStatement2.setString(2, elementDefinitionJSON);
					preparedStatement2.setString(3, title);

					String version = resultSet.getString("version");

					if (Validator.isNull(version)) {
						version = "1.0";
					}

					preparedStatement2.setString(4, version);

					preparedStatement2.setLong(
						5, resultSet.getLong("sxpElementId"));
					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintUpgradeProcess.class);

}