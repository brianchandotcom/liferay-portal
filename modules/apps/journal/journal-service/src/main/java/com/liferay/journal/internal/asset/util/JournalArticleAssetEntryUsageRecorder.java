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
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.service.AssetEntryUsageLocalService;
import com.liferay.asset.util.AssetEntryUsageRecorder;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalContentSearch;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalContentSearchLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
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
	service = AssetEntryUsageRecorder.class
)
public class JournalArticleAssetEntryUsageRecorder
	implements AssetEntryUsageRecorder {

	@Override
	public void record(AssetEntry assetEntry) throws PortalException {
		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetEntry.getClassName());

		AssetRenderer<JournalArticle> assetRenderer =
			assetRendererFactory.getAssetRenderer(assetEntry.getClassPK());

		JournalArticle article = assetRenderer.getAssetObject();

		if (!article.isApproved()) {
			assetEntry = _assetEntryLocalService.fetchEntry(
				_portal.getClassNameId(JournalArticle.class),
				article.getResourcePrimKey());
		}

		if (_assetEntryUsageLocalService.hasDefaultAssetEntryUsage(
				assetEntry.getEntryId())) {

			return;
		}

		_recordJournalContentSearches(assetEntry);
		_recordPortletPreferences(assetEntry, true);
		_recordPortletPreferences(assetEntry, false);

		_assetEntryUsageLocalService.addDefaultAssetEntryUsage(
			assetEntry.getGroupId(), assetEntry.getEntryId(),
			ServiceContextThreadLocal.getServiceContext());
	}

	private void _recordJournalContentSearches(AssetEntry assetEntry)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetEntry.getClassName());

		AssetRenderer<JournalArticle> assetRenderer =
			assetRendererFactory.getAssetRenderer(assetEntry.getClassPK());

		JournalArticle article = assetRenderer.getAssetObject();

		List<JournalContentSearch> contentSearches =
			_journalContentSearchLocalService.getArticleContentSearches(
				article.getArticleId());

		for (JournalContentSearch contentSearch : contentSearches) {
			Layout layout = _layoutLocalService.fetchLayout(
				contentSearch.getGroupId(), contentSearch.isPrivateLayout(),
				contentSearch.getLayoutId());

			AssetEntryUsage assetEntryUsage =
				_assetEntryUsageLocalService.fetchAssetEntryUsage(
					assetEntry.getEntryId(),
					_portal.getClassNameId(Portlet.class),
					contentSearch.getPortletId(), layout.getPlid());

			if (assetEntryUsage != null) {
				continue;
			}

			_assetEntryUsageLocalService.addAssetEntryUsage(
				contentSearch.getGroupId(), assetEntry.getEntryId(),
				_portal.getClassNameId(Portlet.class),
				contentSearch.getPortletId(), layout.getPlid(), serviceContext);
		}
	}

	private void _recordPortletPreferences(
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
					_portal.getClassNameId(Portlet.class),
					portletPreferences.getPortletId(),
					portletPreferences.getPlid());

			if (assetEntryUsage != null) {
				continue;
			}

			_assetEntryUsageLocalService.addAssetEntryUsage(
				assetEntry.getGroupId(), assetEntry.getEntryId(),
				_portal.getClassNameId(Portlet.class),
				portletPreferences.getPortletId(), portletPreferences.getPlid(),
				serviceContext);
		}
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

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