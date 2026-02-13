/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.upgrade.v3_0_0;

import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectDefinitionSetting;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.upgrade.v7_0_0.UpgradeAsset;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jhosseph Gonzalez
 */
public class AssetListEntryUsageUpgradeProcess extends UpgradeAsset {

	public AssetListEntryUsageUpgradeProcess(
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectDefinitionSettingLocalService
			objectDefinitionSettingLocalService) {

		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectDefinitionSettingLocalService =
			objectDefinitionSettingLocalService;
	}

	@Override
	protected void doUpgrade() {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			processConcurrently(
				StringBundler.concat(
					"select assetListEntryUsageId, companyId, key_ from ",
					"AssetListEntryUsage where (key_ like '%",
					_INFO_COLLECTION_PROVIDER_CLASS_NAME_PREFIX,
					"OneToManyObjectRelationshipRelatedInfoCollection",
					"Provider%' or key_ like '%",
					_INFO_COLLECTION_PROVIDER_CLASS_NAME_PREFIX,
					"ManyToManyObjectRelationshipRelatedInfoCollection",
					"Provider%')"),
				"update AssetListEntryUsage set key_ =" +
					"? where assetListEntryUsageId = ? and companyId = ?",
				resultSet -> new Object[] {
					resultSet.getLong("assetListEntryUsageId"),
					resultSet.getLong("companyId"),
					GetterUtil.getString(resultSet.getString("key_"))
				},
				(values, preparedStatement) -> {
					String key_ = (String)values[2];

					if (key_.isEmpty() ||
						!StringUtil.startsWith(
							key_,
							_INFO_COLLECTION_PROVIDER_CLASS_NAME_PREFIX)) {

						return;
					}

					Matcher
						objectRelationshipRelatedInfoCollectionProviderKeyMatcher =
							_manyToManyObjectRelationshipRelatedInfoCollectionProviderKeyPattern.
								matcher(key_);

					if (!objectRelationshipRelatedInfoCollectionProviderKeyMatcher.
							matches()) {

						objectRelationshipRelatedInfoCollectionProviderKeyMatcher =
							_oneToManyObjectRelationshipRelatedInfoCollectionProviderKeyPattern.
								matcher(key_);
					}

					if (!objectRelationshipRelatedInfoCollectionProviderKeyMatcher.
							matches()) {

						return;
					}

					ObjectDefinition objectDefinition = _getObjectDefinition(
						GetterUtil.getLong(values[1]),
						objectRelationshipRelatedInfoCollectionProviderKeyMatcher);

					if (objectDefinition == null) {
						return;
					}

					String newKey = _getKey(
						objectRelationshipRelatedInfoCollectionProviderKeyMatcher,
						objectDefinition);

					if (Objects.equals(newKey, key_)) {
						return;
					}

					preparedStatement.setString(1, newKey);

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
		long companyId,
		Matcher objectRelationshipRelatedInfoCollectionProviderKeyMatcher) {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				companyId,
				objectRelationshipRelatedInfoCollectionProviderKeyMatcher.group(
					3));

		if (objectDefinition != null) {
			return objectDefinition;
		}

		objectDefinition = _objectDefinitionLocalService.fetchObjectDefinition(
			GetterUtil.getLong(
				objectRelationshipRelatedInfoCollectionProviderKeyMatcher.group(
					2)),
			objectRelationshipRelatedInfoCollectionProviderKeyMatcher.group(3));

		if (objectDefinition == null) {
			return null;
		}

		ObjectDefinitionSetting objectDefinitionSetting =
			_objectDefinitionSettingLocalService.fetchObjectDefinitionSetting(
				companyId, ObjectDefinitionSettingConstants.NAME_OLD_CLASS_NAME,
				objectDefinition.getClassName());

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

	private static final Pattern
		_manyToManyObjectRelationshipRelatedInfoCollectionProviderKeyPattern =
			Pattern.compile(
				StringBundler.concat(
					"^(", _INFO_COLLECTION_PROVIDER_CLASS_NAME_PREFIX_REGEX,
					"ManyToManyObjectRelationshipRelatedInfoCollectionProvider",
					"_)(\\d+)_(C_[^_]+)_([A-Za-z0-9_]+)$"));
	private static final Pattern
		_oneToManyObjectRelationshipRelatedInfoCollectionProviderKeyPattern =
			Pattern.compile(
				StringBundler.concat(
					"^(", _INFO_COLLECTION_PROVIDER_CLASS_NAME_PREFIX_REGEX,
					"OneToManyObjectRelationshipRelatedInfoCollectionProvider",
					"_)(\\d+)_(C_[^_]+)_([A-Za-z0-9_]+)$"));

	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectDefinitionSettingLocalService
		_objectDefinitionSettingLocalService;

}