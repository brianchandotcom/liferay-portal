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

package com.liferay.journal.internal.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.service.AssetEntryUsageLocalService;
import com.liferay.asset.util.AssetEntryUsageChecker;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalContentSearch;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalContentSearchLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.journal.model.JournalArticle",
	service = AssetEntryUsageChecker.class
)
public class JournalArticleAssetEntryUsageChecker
	implements AssetEntryUsageChecker {

	@Override
	public void checkAssetEntryUsages(AssetEntry assetEntry)
		throws PortalException {

		_checkAssetPublisherUsages(assetEntry, true);

		_checkAssetPublisherUsages(assetEntry, false);

		_checkWebContentUsages(assetEntry);
	}

	private void _checkAssetPublisherUsages(
			AssetEntry assetEntry, boolean privateLayout)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		List<PortletPreferences> portletPreferencesList =
			_portletPreferencesLocalService.getPortletPreferences(
				assetEntry.getCompanyId(), assetEntry.getGroupId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
				AssetPublisherPortletKeys.ASSET_PUBLISHER, privateLayout);

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			String preferencesXML = portletPreferences.getPreferences();

			if (Validator.isNull(preferencesXML)) {
				continue;
			}

			javax.portlet.PortletPreferences jxPortletPreferences =
				PortletPreferencesFactoryUtil.fromDefaultXML(preferencesXML);

			String selectionStyle = jxPortletPreferences.getValue(
				"selectionStyle", "dynamic");

			if (!StringUtil.equals(selectionStyle, "manual")) {
				continue;
			}

			String assetEntryXml = jxPortletPreferences.getValue(
				"assetEntryXml", StringPool.BLANK);

			if (!assetEntryXml.contains(assetEntry.getClassUuid())) {
				continue;
			}

			AssetEntryUsage assetEntryUsage =
				_assetEntryUsageLocalService.fetchAssetEntryUsage(
					assetEntry.getEntryId(),
					_portal.getClassNameId(Layout.class),
					portletPreferences.getPlid(),
					portletPreferences.getPortletId());

			if (assetEntryUsage != null) {
				continue;
			}

			_assetEntryUsageLocalService.addAssetEntryUsage(
				assetEntry.getUserId(), assetEntry.getGroupId(),
				assetEntry.getEntryId(), _portal.getClassNameId(Layout.class),
				portletPreferences.getPlid(), portletPreferences.getPortletId(),
				serviceContext);
		}
	}

	private void _checkWebContentUsages(AssetEntry assetEntry)
		throws PortalException {

		JournalArticle article = _journalArticleLocalService.fetchLatestArticle(
			assetEntry.getClassPK());

		List<JournalContentSearch> contentSearches =
			_journalContentSearchLocalService.getArticleContentSearches(
				article.getArticleId());

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		for (JournalContentSearch contentSearch : contentSearches) {
			Layout layout = _layoutLocalService.fetchLayout(
				contentSearch.getGroupId(), contentSearch.isPrivateLayout(),
				contentSearch.getLayoutId());

			AssetEntryUsage assetEntryUsage =
				_assetEntryUsageLocalService.fetchAssetEntryUsage(
					assetEntry.getEntryId(),
					_portal.getClassNameId(Layout.class), layout.getPlid(),
					contentSearch.getPortletId());

			if (assetEntryUsage != null) {
				continue;
			}

			_assetEntryUsageLocalService.addAssetEntryUsage(
				article.getUserId(), contentSearch.getGroupId(),
				assetEntry.getEntryId(), _portal.getClassNameId(Layout.class),
				layout.getPlid(), contentSearch.getPortletId(), serviceContext);
		}
	}

	@Reference
	private AssetEntryUsageLocalService _assetEntryUsageLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalContentSearchLocalService _journalContentSearchLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}