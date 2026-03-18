/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v6_0_0;

import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jhosseph Gonzalez
 */
public class LayoutPageTemplateStructureRelUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select ctCollectionId, lPageTemplateStructureRelId, ",
					"companyId, data_ from LayoutPageTemplateStructureRel ",
					"where (data_ like '%",
					"com.liferay.object.internal.info.collection.provider.",
					"OneToManyObjectRelationshipRelatedInfoCollection",
					"Provider%' or data_ like '%",
					"com.liferay.object.internal.info.collection.provider.",
					"ManyToManyObjectRelationshipRelatedInfoCollection",
					"Provider%')"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"update LayoutPageTemplateStructureRel set data_ = ? ",
						"where ctCollectionId = ? and ",
						"lPageTemplateStructureRelId = ? and companyId = ?"));
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				LayoutStructure layoutStructure = LayoutStructure.of(
					GetterUtil.getString(resultSet.getString("data_")));

				for (CollectionStyledLayoutStructureItem
						collectionStyledLayoutStructureItem :
							layoutStructure.
								getCollectionStyledLayoutStructureItems()) {

					JSONObject collectionJSONObject =
						collectionStyledLayoutStructureItem.
							getCollectionJSONObject();

					if (collectionJSONObject == null) {
						continue;
					}

					String key = collectionJSONObject.getString("key");

					if (Validator.isNull(key)) {
						continue;
					}

					Matcher
						objectRelationshipRelatedInfoCollectionProviderKeyMatcher =
							_objectRelationshipRelatedInfoCollectionProviderKeyPattern.
								matcher(key);

					if (!objectRelationshipRelatedInfoCollectionProviderKeyMatcher.
							matches()) {

						continue;
					}

					String sourceItemType = collectionJSONObject.getString(
						"sourceItemType");

					if (Validator.isNull(sourceItemType)) {
						sourceItemType = collectionJSONObject.getString(
							"itemType");
					}

					if (Validator.isNull(sourceItemType)) {
						continue;
					}

					Matcher objectDefinitionClassNameMatcher =
						_objectDefinitionClassNamePattern.matcher(
							sourceItemType);

					if (!objectDefinitionClassNameMatcher.matches()) {
						continue;
					}

					String className = _fetchObjectDefinitionClassName(
						sourceItemType, resultSet.getLong("companyId"));

					if (Validator.isNull(className)) {
						continue;
					}

					collectionJSONObject.put(
						"key",
						StringBundler.concat(
							objectRelationshipRelatedInfoCollectionProviderKeyMatcher.
								group(1),
							className,
							objectRelationshipRelatedInfoCollectionProviderKeyMatcher.
								group(4)));
				}

				preparedStatement2.setString(
					1, String.valueOf(layoutStructure.toJSONObject()));
				preparedStatement2.setLong(
					2, resultSet.getLong("ctCollectionId"));
				preparedStatement2.setLong(
					3, resultSet.getLong("lPageTemplateStructureRelId"));
				preparedStatement2.setLong(4, resultSet.getLong("companyId"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private String _fetchObjectDefinitionClassName(
			String className, long companyId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select className from ObjectDefinition where companyId = ? " +
					"and className = ?")) {

			preparedStatement.setLong(1, companyId);
			preparedStatement.setString(2, className);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getString("className");
				}
			}
		}

		return _fetchObjectDefinitionClassNameByOldClassName(
			className, companyId);
	}

	private String _fetchObjectDefinitionClassNameByOldClassName(
			String className, long companyId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select ObjectDefinition.className from ObjectDefinition ",
					"inner join ObjectDefinitionSetting on ",
					"ObjectDefinition.objectDefinitionId = ",
					"ObjectDefinitionSetting.objectDefinitionId where ",
					"ObjectDefinitionSetting.companyId = ? and ",
					"ObjectDefinitionSetting.name = ? and ",
					"ObjectDefinitionSetting.value = ?"))) {

			preparedStatement.setLong(1, companyId);
			preparedStatement.setString(
				2, ObjectDefinitionSettingConstants.NAME_OLD_CLASS_NAME);
			preparedStatement.setString(3, className);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getString("className");
				}
			}
		}

		return null;
	}

	private static final Pattern _objectDefinitionClassNamePattern =
		Pattern.compile(
			StringBundler.concat(
				"^com\\.liferay\\.object\\.model\\.ObjectDefinition#",
				"[A-Za-z]\\d[A-Za-z]\\d$"));
	private static final Pattern
		_objectRelationshipRelatedInfoCollectionProviderKeyPattern =
			Pattern.compile(
				StringBundler.concat(
					"^(com\\.liferay\\.object\\.internal\\.info\\.",
					"collection\\.provider\\.",
					"[A-Za-z]+ObjectRelationshipRelatedInfoCollectionProvider",
					"_)(\\d+)_(.+)(_[A-Za-z0-9]+)$"));

}