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

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.petra.string.StringBundler;
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

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			processConcurrently(
				"select distinct groupId, resourcePrimKey, companyId, " +
					"articleId from JournalArticle",
				resultSet -> new Object[] {
					resultSet.getLong("resourcePrimKey"),
					GetterUtil.getString(resultSet.getString("articleId")),
					resultSet.getLong("groupId"), resultSet.getLong("companyId")
				},
				values -> {
					long resourcePrimKey = (Long)values[0];

					if (_layoutClassedModelUsageLocalService.
							hasDefaultLayoutClassedModelUsage(
								journalArticleClassNameId, resourcePrimKey)) {

						return;
					}

					String articleId = (String)values[1];
					long groupId = (Long)values[2];

					_addJournalContentSearchLayoutClassedModelUsages(
						articleId, resourcePrimKey, groupId,
						journalArticleClassNameId, portletClassNameId,
						serviceContext);

					String assetEntryClassUuid = _getAssetEntryClassUuid(
						journalArticleClassNameId, resourcePrimKey);

					if (Validator.isNull(assetEntryClassUuid)) {
						return;
					}

					long companyId = (Long)values[3];

					_addAssetPublisherPortletPreferencesLayoutClassedModelUsages(
						assetEntryClassUuid, resourcePrimKey, groupId,
						journalArticleClassNameId, portletClassNameId,
						_portletPreferencesLocalService.getPortletPreferences(
							companyId, groupId,
							PortletKeys.PREFS_OWNER_ID_DEFAULT,
							PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
							AssetPublisherPortletKeys.ASSET_PUBLISHER, true),
						serviceContext);

					_addAssetPublisherPortletPreferencesLayoutClassedModelUsages(
						assetEntryClassUuid, resourcePrimKey, groupId,
						journalArticleClassNameId, portletClassNameId,
						_portletPreferencesLocalService.getPortletPreferences(
							companyId, groupId,
							PortletKeys.PREFS_OWNER_ID_DEFAULT,
							PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
							AssetPublisherPortletKeys.ASSET_PUBLISHER, false),
						serviceContext);

					_layoutClassedModelUsageLocalService.
						addDefaultLayoutClassedModelUsage(
							groupId, journalArticleClassNameId, resourcePrimKey,
							serviceContext);
				},
				"Unable to create journal articles layout classed model " +
					"usages");
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

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			processConcurrently(
				StringBundler.concat(
					"select privateLayout, layoutId, portletId from ",
					"JournalContentSearch where groupId = ", groupId,
					" and articleId = '", articleId, StringPool.APOSTROPHE),
				resultSet -> new Object[] {
					resultSet.getBoolean("privateLayout"),
					resultSet.getLong("layoutId"),
					GetterUtil.getString(resultSet.getString("portletId"))
				},
				values -> {
					boolean privateLayout = (Boolean)values[0];
					long layoutId = (Long)values[1];

					Layout layout = _layoutLocalService.fetchLayout(
						groupId, privateLayout, layoutId);

					if (layout == null) {
						return;
					}

					String portletId = (String)values[2];

					LayoutClassedModelUsage layoutClassedModelUsage =
						_layoutClassedModelUsageLocalService.
							fetchLayoutClassedModelUsage(
								journalArticleClassNameId, classPK, portletId,
								portletClassNameId, layout.getPlid());

					if (layoutClassedModelUsage != null) {
						return;
					}

					_layoutClassedModelUsageLocalService.
						addLayoutClassedModelUsage(
							groupId, journalArticleClassNameId, classPK,
							portletId, portletClassNameId, layout.getPlid(),
							serviceContext);
				},
				"Unable to create journal content search layout classed " +
					"model usages");
		}
	}

	private String _getAssetEntryClassUuid(long classNameId, long classPK)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select classUuid from AssetEntry where classNameId = ? and " +
					"classPK = ?")) {

			preparedStatement.setLong(1, classNameId);
			preparedStatement.setLong(2, classPK);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getString("classUuid");
				}
			}
		}

		return null;
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