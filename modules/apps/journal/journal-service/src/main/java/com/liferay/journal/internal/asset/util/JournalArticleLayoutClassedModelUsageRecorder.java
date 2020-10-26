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

package com.liferay.journal.internal.asset.util;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalContentSearch;
import com.liferay.journal.service.JournalContentSearchLocalService;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.layout.util.LayoutClassedModelUsageRecorder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferenceValueLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.journal.model.JournalArticle",
	service = LayoutClassedModelUsageRecorder.class
)
public class JournalArticleLayoutClassedModelUsageRecorder
	implements LayoutClassedModelUsageRecorder {

	@Override
	public void record(long classNameId, long classPK) throws PortalException {
		if (_layoutClassedModelUsageLocalService.
				hasDefaultLayoutClassedModelUsage(classNameId, classPK)) {

			return;
		}

		InfoItemObjectProvider<JournalArticle> infoItemObjectProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemObjectProvider.class,
				_portal.getClassName(classNameId));

		JournalArticle article = infoItemObjectProvider.getInfoItem(
			new ClassPKInfoItemIdentifier(classPK));

		AssetEntry assetEntry = _getAssetEntry(article);

		_recordJournalContentSearches(article, assetEntry);
		_recordPortletPreferences(article, assetEntry, true);
		_recordPortletPreferences(article, assetEntry, false);

		_layoutClassedModelUsageLocalService.addDefaultLayoutClassedModelUsage(
			article.getGroupId(), classNameId, classPK,
			ServiceContextThreadLocal.getServiceContext());
	}

	private AssetEntry _getAssetEntry(JournalArticle journalArticle)
		throws PortalException {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				JournalArticle.class.getName());

		return assetRendererFactory.getAssetEntry(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());
	}

	private void _recordJournalContentSearches(
		JournalArticle article, AssetEntry assetEntry) {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		List<JournalContentSearch> contentSearches =
			_journalContentSearchLocalService.getArticleContentSearches(
				article.getArticleId());

		for (JournalContentSearch contentSearch : contentSearches) {
			Layout layout = _layoutLocalService.fetchLayout(
				contentSearch.getGroupId(), contentSearch.isPrivateLayout(),
				contentSearch.getLayoutId());

			LayoutClassedModelUsage layoutClassedModelUsage =
				_layoutClassedModelUsageLocalService.
					fetchLayoutClassedModelUsage(
						assetEntry.getClassNameId(),
						article.getResourcePrimKey(),
						contentSearch.getPortletId(),
						_portal.getClassNameId(Portlet.class),
						layout.getPlid());

			if (layoutClassedModelUsage != null) {
				continue;
			}

			_layoutClassedModelUsageLocalService.addLayoutClassedModelUsage(
				contentSearch.getGroupId(), assetEntry.getClassNameId(),
				article.getResourcePrimKey(), contentSearch.getPortletId(),
				_portal.getClassNameId(Portlet.class), layout.getPlid(),
				serviceContext);
		}
	}

	private void _recordPortletPreferences(
		JournalArticle article, AssetEntry assetEntry, boolean privateLayout) {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		List<PortletPreferences> portletPreferencesList =
			_portletPreferencesLocalService.getPortletPreferences(
				article.getCompanyId(), article.getGroupId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
				AssetPublisherPortletKeys.ASSET_PUBLISHER, privateLayout);

		for (PortletPreferences portletPreferences : portletPreferencesList) {
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

			if ((assetEntry == null) ||
				!assetEntryXml.contains(assetEntry.getClassUuid())) {

				continue;
			}

			LayoutClassedModelUsage layoutClassedModelUsage =
				_layoutClassedModelUsageLocalService.
					fetchLayoutClassedModelUsage(
						assetEntry.getClassNameId(),
						article.getResourcePrimKey(),
						portletPreferences.getPortletId(),
						_portal.getClassNameId(Portlet.class),
						portletPreferences.getPlid());

			if (layoutClassedModelUsage != null) {
				continue;
			}

			_layoutClassedModelUsageLocalService.addLayoutClassedModelUsage(
				article.getGroupId(), assetEntry.getClassNameId(),
				article.getResourcePrimKey(), portletPreferences.getPortletId(),
				_portal.getClassNameId(Portlet.class),
				portletPreferences.getPlid(), serviceContext);
		}
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private JournalContentSearchLocalService _journalContentSearchLocalService;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private PortletPreferenceValueLocalService
		_portletPreferenceValueLocalService;

}