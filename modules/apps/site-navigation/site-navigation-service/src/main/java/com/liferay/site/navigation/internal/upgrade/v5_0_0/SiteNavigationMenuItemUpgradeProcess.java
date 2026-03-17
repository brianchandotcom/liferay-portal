/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.internal.upgrade.v5_0_0;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Objects;

/**
 * @author Gislayne Vitorino
 */
public class SiteNavigationMenuItemUpgradeProcess extends UpgradeProcess {

	public SiteNavigationMenuItemUpgradeProcess(
		GroupLocalService groupLocalService,
		JournalArticleLocalService journalArticleLocalService,
		KBArticleLocalService kbArticleLocalService) {

		_groupLocalService = groupLocalService;
		_journalArticleLocalService = journalArticleLocalService;
		_kbArticleLocalService = kbArticleLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select ctCollectionId, siteNavigationMenuId, " +
					"siteNavigationMenuItemId, type_, typeSettings from " +
						"SiteNavigationMenuItem");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SiteNavigationMenuItem set typeSettings = ? " +
						"where ctCollectionId = ? and " +
							"siteNavigationMenuItemId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				_upgradeSiteNavigationMenuItem(preparedStatement2, resultSet);
			}

			preparedStatement2.executeBatch();
		}
	}

	private PersistedModel _getPersistedModel(
			String className, String type,
			UnicodeProperties typeSettingsUnicodeProperties)
		throws Exception {

		PersistedModel persistedModel = null;

		if (Objects.equals(type, JournalArticle.class.getName())) {
			persistedModel = _journalArticleLocalService.getLatestArticle(
				GetterUtil.getLong(
					typeSettingsUnicodeProperties.getProperty("classPK")));
		}
		else if (Objects.equals(type, KBArticle.class.getName())) {
			persistedModel = _kbArticleLocalService.getLatestKBArticle(
				GetterUtil.getLong(
					typeSettingsUnicodeProperties.getProperty("classPK")));
		}
		else {
			if (className.equals(FileEntry.class.getName())) {
				className = DLFileEntry.class.getName();
			}
			else if (className.contains(ObjectDefinition.class.getName())) {
				className = ObjectEntry.class.getName();
			}

			PersistedModelLocalService persistedModelLocalService =
				PersistedModelLocalServiceRegistryUtil.
					getPersistedModelLocalService(className);

			persistedModel = persistedModelLocalService.getPersistedModel(
				GetterUtil.getLong(
					typeSettingsUnicodeProperties.getProperty("classPK")));
		}

		return persistedModel;
	}

	private String _getScopeExternalReferenceCode(
		long groupId, long siteNavigationMenuGroupId) {

		if (groupId == siteNavigationMenuGroupId) {
			return null;
		}

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return null;
		}

		return group.getExternalReferenceCode();
	}

	private void _upgradeSiteNavigationMenuItem(
			PreparedStatement preparedStatement2, ResultSet resultSet)
		throws Exception {

		String type = resultSet.getString("type_");

		if (Objects.equals(type, SiteNavigationMenuItemTypeConstants.NODE) ||
			Objects.equals(type, SiteNavigationMenuItemTypeConstants.URL)) {

			return;
		}

		String scopeExternalReferenceCode = null;

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				resultSet.getString("typeSettings")
			).build();

		long siteNavigationMenuGroupId = 0;

		try (PreparedStatement preparedStatement3 = connection.prepareStatement(
				"select groupId from SiteNavigationMenu where " +
					"siteNavigationMenuId = ?")) {

			preparedStatement3.setLong(
				1, resultSet.getLong("siteNavigationMenuId"));

			try (ResultSet resultSet3 = preparedStatement3.executeQuery()) {
				if (resultSet3.next()) {
					siteNavigationMenuGroupId = resultSet3.getLong("groupId");
				}
			}
		}

		if (Objects.equals(
				type, SiteNavigationMenuItemTypeConstants.ASSET_VOCABULARY)) {

			long groupId = GetterUtil.getLong(
				typeSettingsUnicodeProperties.remove("groupId"));

			scopeExternalReferenceCode = _getScopeExternalReferenceCode(
				groupId, siteNavigationMenuGroupId);
		}
		else if (Objects.equals(
					type, SiteNavigationMenuItemTypeConstants.LAYOUT)) {

			typeSettingsUnicodeProperties.remove("groupId");
		}
		else {
			String className = typeSettingsUnicodeProperties.getProperty(
				"className");

			if (className == null) {
				return;
			}

			PersistedModel persistedModel = _getPersistedModel(
				className, type, typeSettingsUnicodeProperties);

			if (persistedModel == null) {
				return;
			}

			if (Objects.equals(
					className,
					"com.liferay.commerce.product.model.CPDefinition")) {

				String sql = StringBundler.concat(
					"select CProduct.groupId from CProduct inner join ",
					"CPDefinition on CProduct.CProductId = CPDefinition.",
					"CProductId where CPDefinition.cpDefinitionId = ",
					GetterUtil.getLong(
						typeSettingsUnicodeProperties.getProperty("classPK")));

				try (PreparedStatement preparedStatement4 =
						connection.prepareStatement(sql);

					ResultSet resultSet4 = preparedStatement4.executeQuery()) {

					if (resultSet4.next()) {
						long groupId = resultSet4.getLong("groupId");

						scopeExternalReferenceCode =
							_getScopeExternalReferenceCode(
								groupId, siteNavigationMenuGroupId);
					}
				}
			}
			else if (persistedModel instanceof GroupedModel) {
				GroupedModel groupedModel = (GroupedModel)persistedModel;

				scopeExternalReferenceCode = _getScopeExternalReferenceCode(
					groupedModel.getGroupId(), siteNavigationMenuGroupId);
			}

			if (scopeExternalReferenceCode == null) {
				return;
			}
		}

		typeSettingsUnicodeProperties.setProperty(
			"scopeExternalReferenceCode", scopeExternalReferenceCode);

		preparedStatement2.setString(
			1, typeSettingsUnicodeProperties.toString());
		preparedStatement2.setLong(2, resultSet.getLong("ctCollectionId"));
		preparedStatement2.setLong(
			3, resultSet.getLong("siteNavigationMenuItemId"));

		preparedStatement2.addBatch();
	}

	private final GroupLocalService _groupLocalService;
	private final JournalArticleLocalService _journalArticleLocalService;
	private final KBArticleLocalService _kbArticleLocalService;

}