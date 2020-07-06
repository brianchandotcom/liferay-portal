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

package com.liferay.content.dashboard.web.internal.item;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.info.display.url.provider.InfoEditURLProviderTracker;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemFactory.class)
public class JournalArticleContentDashboardItemFactory
	implements ContentDashboardItemFactory<JournalArticle> {

	@Override
	public ContentDashboardItem<JournalArticle> create(long classPK)
		throws PortalException {

		JournalArticle journalArticle =
			_journalArticleLocalService.getLatestArticle(
				classPK, WorkflowConstants.STATUS_ANY, false);

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		return new JournalArticleContentDashboardItem(
			assetEntry.getCategories(), _assetDisplayPageFriendlyURLProvider,
			_groupLocalService.fetchGroup(journalArticle.getGroupId()),
			_infoEditURLProviderTracker.getInfoEditURLProvider(
				JournalArticle.class.getName()),
			journalArticle, _language, _modelResourcePermission);
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoEditURLProviderTracker _infoEditURLProviderTracker;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private Language _language;

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalArticle)"
	)
	private ModelResourcePermission<JournalArticle> _modelResourcePermission;

}