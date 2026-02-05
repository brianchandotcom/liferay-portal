/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v6_0_0;

import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectDefinitionSetting;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jhosseph Gonzalez
 */
public class LayoutPageTemplateStructureRelUpgradeProcess
	extends UpgradeProcess {

	public LayoutPageTemplateStructureRelUpgradeProcess(
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectDefinitionSettingLocalService
			objectDefinitionSettingLocalService) {

		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectDefinitionSettingLocalService =
			objectDefinitionSettingLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			processConcurrently(
				StringBundler.concat(
					"select ctCollectionId, lPageTemplateStructureRelId, ",
					"companyId, data_ from LayoutPageTemplateStructureRel ",
					"where (data_ like '%",
					_INFO_COLLECTION_PROVIDER_CLASS_NAME_PREFIX,
					"OneToManyObjectRelationshipRelatedInfoCollection",
					"Provider%' or data_ like '%",
					_INFO_COLLECTION_PROVIDER_CLASS_NAME_PREFIX,
					"ManyToManyObjectRelationshipRelatedInfoCollection",
					"Provider%')"),
				"update LayoutPageTemplateStructureRel set data_ =" +
					"? where ctCollectionId = ? and " +
						"lPageTemplateStructureRelId = ?",
				resultSet -> new Object[] {
					resultSet.getLong("ctCollectionId"),
					resultSet.getLong("lPageTemplateStructureRelId"),
					resultSet.getLong("companyId"),
					GetterUtil.getString(resultSet.getString("data_"))
				},
				(values, preparedStatement) -> {
					String data_ = (String)values[3];

					if (data_.isEmpty()) {
						return;
					}

					LayoutStructure layoutStructure = LayoutStructure.of(data_);

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

						if (!StringUtil.startsWith(
								key,
								_INFO_COLLECTION_PROVIDER_CLASS_NAME_PREFIX)) {

							continue;
						}

						Matcher
							objectRelationshipRelatedInfoCollectionProviderKeyMatcher =
								_manyToManyObjectRelationshipRelatedInfoCollectionProviderKeyPattern.
									matcher(key);

						if (!objectRelationshipRelatedInfoCollectionProviderKeyMatcher.
								matches()) {

							objectRelationshipRelatedInfoCollectionProviderKeyMatcher =
								_oneToManyObjectRelationshipRelatedInfoCollectionProviderKeyPattern.
									matcher(key);
						}

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

						ObjectDefinition objectDefinition =
							_getObjectDefinition(
								GetterUtil.getLong(values[2]), sourceItemType);

						if (objectDefinition == null) {
							continue;
						}

						String newKey = _getKey(
							objectRelationshipRelatedInfoCollectionProviderKeyMatcher,
							objectDefinition);

						if (Objects.equals(newKey, key)) {
							continue;
						}

						collectionJSONObject.put("key", newKey);
					}

					JSONObject jsonObject = layoutStructure.toJSONObject();

					preparedStatement.setString(1, jsonObject.toString());

					preparedStatement.setLong(2, (Long)values[0]);
					preparedStatement.setLong(3, (Long)values[1]);

					preparedStatement.addBatch();
				},
				null);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private String _getKey(Matcher matcher, ObjectDefinition objectDefinition) {
		return StringBundler.concat(
			matcher.group(1), objectDefinition.getClassName(), "_",
			matcher.group(4));
	}

	private ObjectDefinition _getObjectDefinition(
		long companyId, String objectDefinitionSettingValue) {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				companyId, objectDefinitionSettingValue);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		ObjectDefinitionSetting objectDefinitionSetting =
			_objectDefinitionSettingLocalService.fetchObjectDefinitionSetting(
				companyId, ObjectDefinitionSettingConstants.NAME_OLD_CLASS_NAME,
				objectDefinitionSettingValue);

		if (objectDefinitionSetting == null) {
			return null;
		}

		return _objectDefinitionLocalService.fetchObjectDefinition(
			objectDefinitionSetting.getObjectDefinitionId());
	}

	private static final String _INFO_COLLECTION_PROVIDER_CLASS_NAME_PREFIX =
		"com.liferay.object.internal.info.collection.provider.";

	private static final String
		_INFO_COLLECTION_PROVIDER_CLASS_NAME_PREFIX_REGEX =
			StringBundler.concat(
				"com\\.liferay\\.object\\.internal\\.info\\.collection\\.",
				"provider\\.");

	private static final String _OBJECT_DEFINITION_CLASS_NAME_REGEX =
		StringBundler.concat(
			"com\\.liferay\\.object\\.model\\.ObjectDefinition#",
			"[A-Za-z]\\d[A-Za-z]\\d");

	private static final Pattern
		_manyToManyObjectRelationshipRelatedInfoCollectionProviderKeyPattern =
			Pattern.compile(
				StringBundler.concat(
					"^(", _INFO_COLLECTION_PROVIDER_CLASS_NAME_PREFIX_REGEX,
					"ManyToManyObjectRelationshipRelatedInfoCollectionProvider",
					"_)(\\d+)_(.+)_([A-Za-z0-9_]+)$"));
	private static final Pattern _objectDefinitionClassNamePattern =
		Pattern.compile(
			StringBundler.concat(
				"^", _OBJECT_DEFINITION_CLASS_NAME_REGEX, "$"));
	private static final Pattern
		_oneToManyObjectRelationshipRelatedInfoCollectionProviderKeyPattern =
			Pattern.compile(
				StringBundler.concat(
					"^(", _INFO_COLLECTION_PROVIDER_CLASS_NAME_PREFIX_REGEX,
					"OneToManyObjectRelationshipRelatedInfoCollectionProvider",
					"_)(\\d+)_(.+)_([A-Za-z0-9_]+)$"));

	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectDefinitionSettingLocalService
		_objectDefinitionSettingLocalService;

}