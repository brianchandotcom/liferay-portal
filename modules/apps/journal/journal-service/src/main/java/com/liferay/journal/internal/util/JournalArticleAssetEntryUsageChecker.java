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
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.portlet.PortletPreferences;

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

		JournalArticle article = _journalArticleLocalService.fetchLatestArticle(
			assetEntry.getClassPK());

		List<JournalContentSearch> journalContentSearchList =
			_journalContentSearchLocalService.getArticleContentSearches(
				article.getArticleId());

		ServiceContext serviceContext = Optional.ofNullable(
			ServiceContextThreadLocal.getServiceContext()
		).orElse(
			new ServiceContext()
		);

		for (JournalContentSearch journalContentSearch :
				journalContentSearchList) {

			Layout layout = _layoutLocalService.getLayout(
				journalContentSearch.getGroupId(),
				journalContentSearch.isPrivateLayout(),
				journalContentSearch.getLayoutId());

			_assetEntryUsageLocalService.addAssetEntryUsage(
				article.getUserId(), journalContentSearch.getGroupId(),
				assetEntry.getEntryId(), _portal.getClassNameId(Layout.class),
				layout.getPlid(), journalContentSearch.getPortletId(),
				serviceContext);
		}

		for (boolean privateLayout : Arrays.asList(false, true)) {
			List<Layout> layouts = _layoutLocalService.getLayouts(
				assetEntry.getGroupId(), privateLayout,
				LayoutConstants.TYPE_PORTLET);

			for (Layout layout : layouts) {
				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				if (!layoutTypePortlet.hasPortletId(
						AssetPublisherPortletKeys.ASSET_PUBLISHER)) {

					continue;
				}

				_checkPortlets(
					assetEntry, layoutTypePortlet.getPortletIds(), layout,
					serviceContext);
			}
		}
	}

	private void _checkPortlets(
			AssetEntry assetEntry, List<String> portletIds, Layout layout,
			ServiceContext serviceContext)
		throws PortalException {

		for (String portletId : portletIds) {
			if (!StringUtil.startsWith(
					portletId, AssetPublisherPortletKeys.ASSET_PUBLISHER)) {

				continue;
			}

			PortletPreferences portletPreferences =
				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					layout, portletId);

			String selectionStyle = portletPreferences.getValue(
				"selectionStyle", "dynamic");

			if (!StringUtil.equals(selectionStyle, "manual")) {
				continue;
			}

			String assetEntryXml = portletPreferences.getValue(
				"assetEntryXml", StringPool.BLANK);

			if (!assetEntryXml.contains(assetEntry.getClassUuid())) {
				continue;
			}

			_assetEntryUsageLocalService.addAssetEntryUsage(
				assetEntry.getUserId(), assetEntry.getGroupId(),
				assetEntry.getEntryId(), _portal.getClassNameId(Layout.class),
				layout.getPlid(), portletId, serviceContext);
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

}