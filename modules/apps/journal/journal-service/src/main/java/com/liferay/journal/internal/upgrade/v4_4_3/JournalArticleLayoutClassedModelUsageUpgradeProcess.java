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
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferenceValueLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortletKeys;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

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
			Map<Long, Long> resourcePrimKeysMap = new HashMap<>();

			processConcurrently(
				StringBundler.concat(
					"select distinct JournalArticle.resourcePrimKey, ",
					"JournalArticle.groupId, JournalArticle.companyId, ",
					"JournalArticle.articleId from JournalArticle where not ",
					"exists (select 1 from LayoutClassedModelUsage where ",
					"LayoutClassedModelUsage.classPK = ",
					"JournalArticle.resourcePrimKey and ",
					"LayoutClassedModelUsage.classNameId = ",
					journalArticleClassNameId,
					" and LayoutClassedModelUsage.containerKey is null and ",
					"LayoutClassedModelUsage.containerType = 0 and ",
					"LayoutClassedModelUsage.plid = 0 )"),
				resultSet -> new Object[] {
					GetterUtil.getString(resultSet.getString("articleId")),
					resultSet.getLong("resourcePrimKey"),
					resultSet.getLong("groupId"), resultSet.getLong("companyId")
				},
				values -> {
					String articleId = (String)values[0];
					long resourcePrimKey = (Long)values[1];
					long groupId = (Long)values[2];

					_addJournalContentSearchLayoutClassedModelUsages(
						articleId, resourcePrimKey, groupId,
						journalArticleClassNameId, portletClassNameId,
						serviceContext);

					resourcePrimKeysMap.put(resourcePrimKey, groupId);
				},
				"Unable to create journal articles layout classed model " +
					"usages");

			_addAssetPublisherPortletPreferencesLayoutClassedModelUsages(
				journalArticleClassNameId, portletClassNameId,
				resourcePrimKeysMap, serviceContext);

			for (Map.Entry<Long, Long> entry : resourcePrimKeysMap.entrySet()) {
				_layoutClassedModelUsageLocalService.
					addDefaultLayoutClassedModelUsage(
						entry.getValue(), journalArticleClassNameId,
						entry.getKey(), serviceContext);
			}
		}
	}

	private void _addAssetPublisherPortletPreferencesLayoutClassedModelUsages(
			long journalArticleClassNameId, long portletClassNameId,
			Map<Long, Long> resourcePrimKeysMap, ServiceContext serviceContext)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String sql = StringBundler.concat(
				"select distinct AssetEntry.groupId, AssetEntry.classPK, ",
				"PortletPreferences.plid, PortletPreferences.portletId from ",
				"PortletPreferences inner join AssetEntry as AssetEntry on ",
				"AssetEntry.classNameId = ", journalArticleClassNameId,
				" and AssetEntry.visible = 1 and AssetEntry.classUuid is not ",
				"null and CAST_TEXT(AssetEntry.classUuid) != '' inner join ",
				"PortletPreferenceValue as PortletPreferenceValue1 on ",
				"PortletPreferenceValue1.portletPreferencesId = ",
				"PortletPreferences.portletPreferencesId and ",
				"PortletPreferenceValue1.name = 'selectionStyle' and ",
				"(PortletPreferenceValue1.smallValue = 'manual' or ",
				"PortletPreferenceValue1.largeValue = 'manual') inner join ",
				"PortletPreferenceValue as PortletPreferenceValue2 on ",
				"PortletPreferenceValue2.portletPreferencesId = ",
				"PortletPreferences.portletPreferencesId and ",
				"PortletPreferenceValue2.name = 'assetEntryXml' and ",
				"(PortletPreferenceValue2.smallValue like CONCAT('%', ",
				"AssetEntry.classUuid, '%') or ",
				"PortletPreferenceValue2.largeValue like CONCAT('%', ",
				"AssetEntry.classUuid, '%')) where ",
				"PortletPreferences.companyId = AssetEntry.companyId and ",
				"PortletPreferences.ownerId = ",
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				" and PortletPreferences.ownerType = ",
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
				" and PortletPreferences.portletId like '",
				AssetPublisherPortletKeys.ASSET_PUBLISHER,
				"%' and not exists (select 1 from LayoutClassedModelUsage ",
				"where LayoutClassedModelUsage.classPK = AssetEntry.classPK ",
				"and LayoutClassedModelUsage.classNameId = ",
				journalArticleClassNameId,
				" and LayoutClassedModelUsage.containerKey = ",
				"PortletPreferences.portletId and ",
				"LayoutClassedModelUsage.containerType = ", portletClassNameId,
				" and LayoutClassedModelUsage.plid = PortletPreferences.plid) ",
				"and not exists (select 1 from LayoutClassedModelUsage where ",
				"LayoutClassedModelUsage.classPK = AssetEntry.classPK and ",
				"LayoutClassedModelUsage.classNameId = ",
				journalArticleClassNameId,
				" and LayoutClassedModelUsage.containerKey is null and ",
				"LayoutClassedModelUsage.containerType = 0 and ",
				"LayoutClassedModelUsage.plid = 0 )");

			processConcurrently(
				SQLTransformer.transform(sql),
				resultSet -> new Object[] {
					resultSet.getLong("groupId"), resultSet.getLong("classPK"),
					resultSet.getLong("plid"),
					GetterUtil.getString(resultSet.getString("portletId"))
				},
				values -> {
					long groupId = (Long)values[0];
					long classPK = (Long)values[1];
					long plid = (Long)values[2];
					String portletId = (String)values[3];

					_layoutClassedModelUsageLocalService.
						addLayoutClassedModelUsage(
							groupId, journalArticleClassNameId, classPK,
							portletId, portletClassNameId, plid,
							serviceContext);

					resourcePrimKeysMap.computeIfAbsent(
						classPK, key -> groupId);
				},
				"Unable to create manual selection asset publisher layout " +
					"classed model usages");
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