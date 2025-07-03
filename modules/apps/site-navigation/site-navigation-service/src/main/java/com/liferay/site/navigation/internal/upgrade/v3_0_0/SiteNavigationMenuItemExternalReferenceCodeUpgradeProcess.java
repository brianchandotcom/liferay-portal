/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.internal.upgrade.v3_0_0;

import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.ExternalReferenceCodeModel;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Objects;

/**
 * @author Joao Victor Alves
 */
public class SiteNavigationMenuItemExternalReferenceCodeUpgradeProcess
	extends UpgradeProcess {

	public SiteNavigationMenuItemExternalReferenceCodeUpgradeProcess(
		AssetVocabularyLocalService assetVocabularyLocalService,
		JournalArticleLocalService journalArticleLocalService,
		KBArticleLocalService kbArticleLocalService,
		LayoutLocalService layoutLocalService) {

		_assetVocabularyLocalService = assetVocabularyLocalService;
		_journalArticleLocalService = journalArticleLocalService;
		_kbArticleLocalService = kbArticleLocalService;
		_layoutLocalService = layoutLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select ctCollectionId, siteNavigationMenuItemId, type_, " +
					"typeSettings from SiteNavigationMenuItem");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SiteNavigationMenuItem set typeSettings = ? " +
						"where ctCollectionId = ? and " +
							"siteNavigationMenuItemId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				String navigationMenuItemType = resultSet.getString("type_");

				PersistedModel model = null;

				UnicodeProperties typeSettingsUnicodeProperties =
					UnicodePropertiesBuilder.fastLoad(
						resultSet.getString("typeSettings")
					).build();

				String className = typeSettingsUnicodeProperties.getProperty(
					"className");

				if (Objects.equals(
						navigationMenuItemType,
						SiteNavigationMenuItemTypeConstants.ASSET_VOCABULARY)) {

					model =
						_assetVocabularyLocalService.
							fetchAssetVocabularyByUuidAndGroupId(
								typeSettingsUnicodeProperties.getProperty(
									"uuid"),
								GetterUtil.getLong(
									typeSettingsUnicodeProperties.getProperty(
										"groupId")));
				}
				else if (Objects.equals(
							navigationMenuItemType,
							JournalArticle.class.getName())) {

					model = _journalArticleLocalService.getLatestArticle(
						GetterUtil.getLong(
							typeSettingsUnicodeProperties.getProperty(
								"classPK")));
				}
				else if (Objects.equals(
							navigationMenuItemType,
							KBArticle.class.getName())) {

					model = _kbArticleLocalService.getLatestKBArticle(
						GetterUtil.getLong(
							typeSettingsUnicodeProperties.getProperty(
								"classPK")));
				}
				else if (Objects.equals(
							navigationMenuItemType,
							SiteNavigationMenuItemTypeConstants.LAYOUT)) {

					model = _layoutLocalService.fetchLayoutByUuidAndGroupId(
						typeSettingsUnicodeProperties.getProperty("layoutUuid"),
						GetterUtil.getLong(
							typeSettingsUnicodeProperties.getProperty(
								"groupId")),
						GetterUtil.getBoolean(
							typeSettingsUnicodeProperties.getProperty(
								"privateLayout")));
				}
				else {
					if (className == null) {
						continue;
					}

					if (className.equals(FileEntry.class.getName())) {
						className = DLFileEntry.class.getName();
					}
					else if (className.contains(
								ObjectDefinition.class.getName())) {

						className = ObjectEntry.class.getName();
					}

					PersistedModelLocalService persistedModelLocalService =
						PersistedModelLocalServiceRegistryUtil.
							getPersistedModelLocalService(className);

					model = persistedModelLocalService.getPersistedModel(
						GetterUtil.getLong(
							typeSettingsUnicodeProperties.getProperty(
								"classPK")));
				}

				if (model == null) {
					continue;
				}

				String externalReferenceCode = null;

				if (Objects.equals(className, _CLASS_NAME_CPDEFINITION)) {
					try (PreparedStatement preparedStatement3 =
							connection.prepareStatement(
								StringBundler.concat(
									"select CProduct.externalReferenceCode ",
									"from CProduct inner join CPDefinition on ",
									"CProduct.CProductId = CPDefinition.",
									"CProductId where CPDefinition.",
									"cpDefinitionId = ",
									GetterUtil.getLong(
										typeSettingsUnicodeProperties.
											getProperty("classPK"))));
						ResultSet resultSet3 =
							preparedStatement3.executeQuery()) {

						if (resultSet3.next()) {
							externalReferenceCode = resultSet3.getString(
								"externalReferenceCode");
						}
					}
				}
				else if (model instanceof ExternalReferenceCodeModel) {
					ExternalReferenceCodeModel externalReferenceCodeModel =
						(ExternalReferenceCodeModel)model;

					externalReferenceCode =
						externalReferenceCodeModel.getExternalReferenceCode();
				}

				if (Validator.isNull(externalReferenceCode)) {
					continue;
				}

				typeSettingsUnicodeProperties.setProperty(
					"externalReferenceCode", externalReferenceCode);

				preparedStatement2.setString(
					1, typeSettingsUnicodeProperties.toString());
				preparedStatement2.setLong(
					2, resultSet.getLong("ctCollectionId"));
				preparedStatement2.setLong(
					3, resultSet.getLong("siteNavigationMenuItemId"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private static final String _CLASS_NAME_CPDEFINITION =
		"com.liferay.commerce.product.model.CPDefinition";

	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private final JournalArticleLocalService _journalArticleLocalService;
	private final KBArticleLocalService _kbArticleLocalService;
	private final LayoutLocalService _layoutLocalService;

}