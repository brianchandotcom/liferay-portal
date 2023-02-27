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

package com.liferay.journal.internal.upgrade.v4_4_3;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferenceValueLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Lourdes Fernández Besada
 */
public class JournalArticleLayoutClassedModelUsageUpgradeProcess
	extends UpgradeProcess {

	public JournalArticleLayoutClassedModelUsageUpgradeProcess(
		AssetEntryLocalService assetEntryLocalService,
		ClassNameLocalService classNameLocalService,
		LayoutLocalService layoutLocalService,
		LayoutClassedModelUsageLocalService layoutClassedModelUsageLocalService,
		PortletPreferencesLocalService portletPreferencesLocalService,
		PortletPreferenceValueLocalService portletPreferenceValueLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
		_classNameLocalService = classNameLocalService;
		_layoutLocalService = layoutLocalService;
		_layoutClassedModelUsageLocalService =
			layoutClassedModelUsageLocalService;
		_portletPreferencesLocalService = portletPreferencesLocalService;
		_portletPreferenceValueLocalService =
			portletPreferenceValueLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		long journalArticleClassNameId = _classNameLocalService.getClassNameId(
			JournalArticle.class.getName());
		long portletClassNameId = _classNameLocalService.getClassNameId(
			Portlet.class.getName());

		ServiceContext serviceContext = new ServiceContext();

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					"select groupId, resourcePrimKey, articleId from " +
						"JournalArticle")) {

			try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long resourcePrimKey = resultSet.getLong("resourcePrimKey");

					if (_layoutClassedModelUsageLocalService.
							hasDefaultLayoutClassedModelUsage(
								journalArticleClassNameId, resourcePrimKey)) {

						continue;
					}

					String articleId = GetterUtil.getString(
						resultSet.getString("articleId"));
					long groupId = resultSet.getLong("groupId");

					_addJournalContentSearchLayoutClassedModelUsages(
						articleId, resourcePrimKey, groupId,
						journalArticleClassNameId, portletClassNameId,
						serviceContext);

					AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
						journalArticleClassNameId, resourcePrimKey);

					if ((assetEntry == null) ||
						Validator.isNull(assetEntry.getClassUuid())) {

						continue;
					}

					_addAssetPublisherPortletPreferencesLayoutClassedModelUsages(
						assetEntry.getClassUuid(), resourcePrimKey, groupId,
						journalArticleClassNameId, portletClassNameId,
						_portletPreferencesLocalService.getPortletPreferences(
							assetEntry.getCompanyId(), groupId,
							PortletKeys.PREFS_OWNER_ID_DEFAULT,
							PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
							AssetPublisherPortletKeys.ASSET_PUBLISHER, true),
						serviceContext);

					_addAssetPublisherPortletPreferencesLayoutClassedModelUsages(
						assetEntry.getClassUuid(), resourcePrimKey, groupId,
						journalArticleClassNameId, portletClassNameId,
						_portletPreferencesLocalService.getPortletPreferences(
							assetEntry.getCompanyId(), groupId,
							PortletKeys.PREFS_OWNER_ID_DEFAULT,
							PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
							AssetPublisherPortletKeys.ASSET_PUBLISHER, false),
						serviceContext);

					_layoutClassedModelUsageLocalService.
						addDefaultLayoutClassedModelUsage(
							groupId, journalArticleClassNameId, resourcePrimKey,
							serviceContext);
				}
			}
		}
	}

	private void _addAssetPublisherPortletPreferencesLayoutClassedModelUsages(
		String assetEntryClassUuid, long classPK, long groupId,
		long journalArticleClassNameId, long portletClassNameId,
		List<PortletPreferences> portletPreferencesList,
		ServiceContext serviceContext) {

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			for (PortletPreferences portletPreferences :
					portletPreferencesList) {

				javax.portlet.PortletPreferences jxPortletPreferences =
					_portletPreferenceValueLocalService.getPreferences(
						portletPreferences);

				String selectionStyle = jxPortletPreferences.getValue(
					"selectionStyle", "dynamic");

				if (!StringUtil.equals(selectionStyle, "manual")) {
					continue;
				}

				String assetEntryXml = jxPortletPreferences.getValue(
					"assetEntryXml", StringPool.BLANK);

				if (!assetEntryXml.contains(assetEntryClassUuid)) {
					continue;
				}

				LayoutClassedModelUsage layoutClassedModelUsage =
					_layoutClassedModelUsageLocalService.
						fetchLayoutClassedModelUsage(
							journalArticleClassNameId, classPK,
							portletPreferences.getPortletId(),
							portletClassNameId, portletPreferences.getPlid());

				if (layoutClassedModelUsage != null) {
					continue;
				}

				_layoutClassedModelUsageLocalService.addLayoutClassedModelUsage(
					groupId, journalArticleClassNameId, classPK,
					portletPreferences.getPortletId(), portletClassNameId,
					portletPreferences.getPlid(), serviceContext);
			}
		}
	}

	private void _addJournalContentSearchLayoutClassedModelUsages(
			String articleId, long classPK, long groupId,
			long journalArticleClassNameId, long portletClassNameId,
			ServiceContext serviceContext)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select privateLayout, layoutId, portletId from " +
					"JournalContentSearch where groupId = ? and articleId = " +
						"?")) {

			preparedStatement.setLong(1, groupId);
			preparedStatement.setString(2, articleId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Layout layout = _layoutLocalService.fetchLayout(
						groupId, resultSet.getBoolean("privateLayout"),
						resultSet.getLong("layoutId"));

					if (layout == null) {
						continue;
					}

					String portletId = GetterUtil.getString(
						resultSet.getString("portletId"));

					LayoutClassedModelUsage layoutClassedModelUsage =
						_layoutClassedModelUsageLocalService.
							fetchLayoutClassedModelUsage(
								journalArticleClassNameId, classPK, portletId,
								portletClassNameId, layout.getPlid());

					if (layoutClassedModelUsage != null) {
						continue;
					}

					_layoutClassedModelUsageLocalService.
						addLayoutClassedModelUsage(
							groupId, journalArticleClassNameId, classPK,
							portletId, portletClassNameId, layout.getPlid(),
							serviceContext);
				}
			}
		}
	}

	private final AssetEntryLocalService _assetEntryLocalService;
	private final ClassNameLocalService _classNameLocalService;
	private final LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;
	private final LayoutLocalService _layoutLocalService;
	private final PortletPreferencesLocalService
		_portletPreferencesLocalService;
	private final PortletPreferenceValueLocalService
		_portletPreferenceValueLocalService;

}