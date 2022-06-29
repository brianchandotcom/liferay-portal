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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.model.impl.SXPElementImpl;
import com.liferay.search.experiences.rest.dto.v1_0.ElementDefinition;
import com.liferay.search.experiences.rest.dto.v1_0.ElementInstance;
import com.liferay.search.experiences.rest.dto.v1_0.util.ElementInstanceUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Wade Cao
 */
public class SXPBlueprintUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeSXPElement();

		_upgradeSXPBlueprint();
	}

	private String _getElementInstancesJSON(
		String elementInstancesJSON, List<SXPElement> sxpElements) {

		ElementInstance[] elementInstances =
			ElementInstanceUtil.toElementInstances(elementInstancesJSON);

		if (ArrayUtil.isEmpty(elementInstances)) {
			return StringPool.BLANK;
		}

		for (ElementInstance elementInstance : elementInstances) {
			SXPElement sxpElement = null;

			com.liferay.search.experiences.rest.dto.v1_0.SXPElement
				sxpElementDTO = elementInstance.getSxpElement();

			for (SXPElement currSXPElement : sxpElements) {
				if (Objects.equals(
						currSXPElement.getSXPElementId(),
						sxpElementDTO.getId())) {

					sxpElement = currSXPElement;

					break;
				}
			}

			if (sxpElement == null) {
				continue;
			}

			if (sxpElement.isReadOnly()) {
				Map<String, String> description_i18n =
					sxpElementDTO.getDescription_i18n();

				description_i18n.put(
					"en-US", _renameDescription(description_i18n.get("en-US")));

				sxpElementDTO.setDescription_i18n(description_i18n);

				String elementDefinitionString = String.valueOf(
					sxpElementDTO.getElementDefinition());

				Map<String, String> title_i18n = sxpElementDTO.getTitle_i18n();

				sxpElementDTO.setElementDefinition(
					ElementDefinition.unsafeToDTO(
						_renameElementDefinitionJSON(
							elementDefinitionString, title_i18n.get("en-US"))));

				title_i18n.put("en-US", _renameTitle(title_i18n.get("en-US")));

				sxpElementDTO.setTitle_i18n(title_i18n);
			}

			sxpElementDTO.setExternalReferenceCode(
				sxpElement.getExternalReferenceCode());
			sxpElementDTO.setVersion(sxpElement.getVersion());
		}

		return Arrays.toString(elementInstances);
	}

	private String _renameDescription(String currentDescription) {
		String oldDescription =
			"Boost contents in a category for users belonging to a given " +
				"user segment";

		if (currentDescription.indexOf(oldDescription) > -1) {
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

		if (title.indexOf(currentTitle) > -1) {
			currentElementDefinition = StringUtil.replace(
				currentElementDefinition, "Create Date: From", "Date: From");
			currentElementDefinition = StringUtil.replace(
				currentElementDefinition, "Creat Date: To", "Date: To");
		}

		currentTitle = "Boost Contents in a Category for the Time of Day";

		if (title.indexOf(currentTitle) > -1) {
			currentElementDefinition = StringUtil.replace(
				currentElementDefinition, "Morning (4am - 12am)",
				"Morning (4am - 12pm)");
		}

		return currentElementDefinition;
	}

	private String _renameTitle(String currentTitle) {
		String oldTitle = "Boost Contents in a Category for a User Segment";

		if (currentTitle.indexOf(oldTitle) > -1) {
			return StringUtil.replace(
				currentTitle, oldTitle,
				"Boost Contents in a Category for User Segments");
		}

		oldTitle = "Search with the Lucene Syntax";

		if (currentTitle.indexOf(oldTitle) > -1) {
			return StringUtil.replace(
				currentTitle, oldTitle, "Search with Query String Syntax");
		}

		return currentTitle;
	}

	private void _upgradeSXPBlueprint() throws Exception {
		if (hasColumn("SXPBlueprint", "key_")) {
			alterTableDropColumn("SXPBlueprint", "key_");
		}

		List<SXPElement> sxpElements = new ArrayList<>();

		StringBundler sb = new StringBundler(2);

		sb.append("select externalReferenceCode, readOnly, sxpElementId, ");
		sb.append("version from SXPElement");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				SXPElementImpl sxpElementImpl = new SXPElementImpl();

				sxpElementImpl.setExternalReferenceCode(
					resultSet.getString("externalReferenceCode"));
				sxpElementImpl.setReadOnly(resultSet.getBoolean("readOnly"));
				sxpElementImpl.setSXPElementId(
					resultSet.getLong("sxpElementId"));
				sxpElementImpl.setVersion(resultSet.getString("version"));

				sxpElements.add(sxpElementImpl);
			}
		}

		sb = new StringBundler(2);

		sb.append("select elementInstancesJSON, sxpBlueprintId, version from ");
		sb.append("SXPBlueprint");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				sb.toString());
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SXPBlueprint set elementInstancesJSON = ?, " +
						"version = ? where sxpBlueprintId = ?")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					String version = resultSet.getString("version");

					if (Validator.isNull(version)) {
						version = "1.0";
					}

					preparedStatement2.setString(
						1,
						_getElementInstancesJSON(
							resultSet.getString("elementInstancesJSON"),
							sxpElements));
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
		if (hasColumn("SXPElement", "key_")) {
			alterTableDropColumn("SXPElement", "key_");
		}

		StringBundler selectSB = new StringBundler(3);

		selectSB.append("select description, elementDefinitionJSON, ");
		selectSB.append("readOnly, title, sxpElementId, version from ");
		selectSB.append("SXPElement");

		StringBundler updateSB = new StringBundler(3);

		updateSB.append("update SXPElement set description = ?, ");
		updateSB.append("elementDefinitionJSON = ?, title = ?, version = ? ");
		updateSB.append("where sxpElementId = ?");

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

					boolean readOnly = resultSet.getBoolean("readOnly");

					long sxpElementId = resultSet.getLong("sxpElementId");

					String title = resultSet.getString("title");
					String version = resultSet.getString("version");

					if (readOnly) {
						description = _renameDescription(description);
						elementDefinitionJSON = _renameElementDefinitionJSON(
							elementDefinitionJSON, title);
						title = _renameTitle(title);
					}

					if (Validator.isNull(version)) {
						version = "1.0";
					}

					preparedStatement2.setString(1, description);
					preparedStatement2.setString(2, elementDefinitionJSON);
					preparedStatement2.setString(3, title);
					preparedStatement2.setString(4, version);

					preparedStatement2.setLong(5, sxpElementId);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

}