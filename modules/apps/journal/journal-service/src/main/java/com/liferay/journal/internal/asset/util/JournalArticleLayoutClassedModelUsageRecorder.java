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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
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

		InfoDisplayContributor infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(
				_portal.getClassName(classNameId));

		InfoDisplayObjectProvider infoDisplayObjectProvider =
			infoDisplayContributor.getInfoDisplayObjectProvider(classPK);

		_recordJournalContentSearches(infoDisplayObjectProvider);
		_recordPortletPreferences(infoDisplayObjectProvider, true);
		_recordPortletPreferences(infoDisplayObjectProvider, false);

		_layoutClassedModelUsageLocalService.addDefaultLayoutClassedModelUsage(
			infoDisplayObjectProvider.getGroupId(), classNameId, classPK,
			ServiceContextThreadLocal.getServiceContext());
	}

	private void _recordJournalContentSearches(
		InfoDisplayObjectProvider infoDisplayObjectProvider) {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		JournalArticle article =
			(JournalArticle)infoDisplayObjectProvider.getDisplayObject();

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
						infoDisplayObjectProvider.getClassNameId(),
						infoDisplayObjectProvider.getClassPK(),
						contentSearch.getPortletId(),
						_portal.getClassNameId(Portlet.class),
						layout.getPlid());

			if (layoutClassedModelUsage != null) {
				continue;
			}

			_layoutClassedModelUsageLocalService.addLayoutClassedModelUsage(
				contentSearch.getGroupId(),
				infoDisplayObjectProvider.getClassNameId(),
				infoDisplayObjectProvider.getClassPK(),
				contentSearch.getPortletId(),
				_portal.getClassNameId(Portlet.class), layout.getPlid(),
				serviceContext);
		}
	}

	private void _recordPortletPreferences(
		InfoDisplayObjectProvider infoDisplayObjectProvider,
		boolean privateLayout) {

		JournalArticle article =
			(JournalArticle)infoDisplayObjectProvider.getDisplayObject();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		List<PortletPreferences> portletPreferencesList =
			_portletPreferencesLocalService.getPortletPreferences(
				article.getCompanyId(), article.getGroupId(),
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

			AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
				infoDisplayObjectProvider.getClassNameId(),
				infoDisplayObjectProvider.getClassPK());

			if ((assetEntry == null) ||
				!assetEntryXml.contains(assetEntry.getClassUuid())) {

				continue;
			}

			LayoutClassedModelUsage layoutClassedModelUsage =
				_layoutClassedModelUsageLocalService.
					fetchLayoutClassedModelUsage(
						infoDisplayObjectProvider.getClassNameId(),
						infoDisplayObjectProvider.getClassPK(),
						portletPreferences.getPortletId(),
						_portal.getClassNameId(Portlet.class),
						portletPreferences.getPlid());

			if (layoutClassedModelUsage != null) {
				continue;
			}

			_layoutClassedModelUsageLocalService.addLayoutClassedModelUsage(
				infoDisplayObjectProvider.getGroupId(),
				infoDisplayObjectProvider.getClassNameId(),
				infoDisplayObjectProvider.getClassPK(),
				portletPreferences.getPortletId(),
				_portal.getClassNameId(Portlet.class),
				portletPreferences.getPlid(), serviceContext);
		}
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

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

}