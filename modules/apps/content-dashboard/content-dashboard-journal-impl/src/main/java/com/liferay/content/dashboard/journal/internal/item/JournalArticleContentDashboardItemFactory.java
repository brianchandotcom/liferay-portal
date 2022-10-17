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

package com.liferay.content.dashboard.journal.internal.item;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.content.dashboard.item.ContentDashboardItem;
import com.liferay.content.dashboard.item.ContentDashboardItemFactory;
import com.liferay.content.dashboard.item.action.ContentDashboardItemActionProviderTracker;
import com.liferay.content.dashboard.item.action.ContentDashboardItemVersionActionProviderTracker;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtypeFactory;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtypeFactoryTracker;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Optional;

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

		AssetEntry assetEntry = null;

		if (!journalArticle.isApproved() && !journalArticle.isExpired() &&
			(journalArticle.getVersion() !=
				JournalArticleConstants.VERSION_DEFAULT)) {

			assetEntry = _assetEntryLocalService.fetchEntry(
				JournalArticle.class.getName(), journalArticle.getPrimaryKey());
		}
		else {
			assetEntry = _assetEntryLocalService.fetchEntry(
				JournalArticle.class.getName(),
				journalArticle.getResourcePrimKey());
		}

		if (assetEntry == null) {
			throw new NoSuchModelException(
				"Unable to find an asset entry for journal article " +
					journalArticle.getPrimaryKey());
		}

		Optional<ContentDashboardItemSubtypeFactory>
			contentDashboardItemSubtypeFactoryOptional =
				getContentDashboardItemSubtypeFactoryOptional();

		ContentDashboardItemSubtypeFactory contentDashboardItemSubtypeFactory =
			contentDashboardItemSubtypeFactoryOptional.orElseThrow(
				NoSuchModelException::new);

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		InfoItemFieldValuesProvider<JournalArticle>
			infoItemFieldValuesProvider =
				infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFieldValuesProvider.class,
					JournalArticle.class.getName());

		JournalArticle latestApprovedJournalArticle =
			_journalArticleLocalService.fetchLatestArticle(
				classPK, WorkflowConstants.STATUS_APPROVED);

		return new JournalArticleContentDashboardItem(
			assetEntry.getCategories(), assetEntry.getTags(),
			_contentDashboardItemActionProviderTracker,
			_contentDashboardItemVersionActionProviderTracker,
			contentDashboardItemSubtypeFactory.create(
				ddmStructure.getStructureId(),
				journalArticle.getResourcePrimKey()),
			_groupLocalService.fetchGroup(journalArticle.getGroupId()),
			infoItemFieldValuesProvider, journalArticle, _journalArticleService,
			_language, latestApprovedJournalArticle, _portal);
	}

	@Override
	public Optional<ContentDashboardItemSubtypeFactory>
		getContentDashboardItemSubtypeFactoryOptional() {

		return _contentDashboardItemSubtypeFactoryTracker.
			getContentDashboardItemSubtypeFactoryOptional(
				DDMStructure.class.getName());
	}

	@Reference
	protected InfoItemServiceTracker infoItemServiceTracker;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private ContentDashboardItemActionProviderTracker
		_contentDashboardItemActionProviderTracker;

	@Reference
	private ContentDashboardItemSubtypeFactoryTracker
		_contentDashboardItemSubtypeFactoryTracker;

	@Reference
	private ContentDashboardItemVersionActionProviderTracker
		_contentDashboardItemVersionActionProviderTracker;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}