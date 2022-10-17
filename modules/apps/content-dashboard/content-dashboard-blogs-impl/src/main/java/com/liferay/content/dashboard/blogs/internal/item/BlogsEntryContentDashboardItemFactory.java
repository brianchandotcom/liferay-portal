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

package com.liferay.content.dashboard.blogs.internal.item;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.content.dashboard.item.ContentDashboardItem;
import com.liferay.content.dashboard.item.ContentDashboardItemFactory;
import com.liferay.content.dashboard.item.action.ContentDashboardItemActionProviderTracker;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtypeFactory;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtypeFactoryTracker;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.Portal;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemFactory.class)
public class BlogsEntryContentDashboardItemFactory
	implements ContentDashboardItemFactory<BlogsEntry> {

	@Override
	public ContentDashboardItem<BlogsEntry> create(long classPK)
		throws PortalException {

		BlogsEntry blogsEntry = _blogsEntryLocalService.getBlogsEntry(classPK);

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			BlogsEntry.class.getName(), blogsEntry.getEntryId());

		if (assetEntry == null) {
			throw new NoSuchModelException(
				"Unable to find an asset entry for blogs entry " +
					blogsEntry.getEntryId());
		}

		return new BlogsEntryContentDashboardItem(
			assetEntry.getCategories(), assetEntry.getTags(), blogsEntry,
			_contentDashboardItemActionProviderTracker,
			_groupLocalService.fetchGroup(blogsEntry.getGroupId()), _language,
			_portal);
	}

	@Override
	public Optional<ContentDashboardItemSubtypeFactory>
		getContentDashboardItemSubtypeFactoryOptional() {

		return _contentDashboardItemSubtypeFactoryTracker.
			getContentDashboardItemSubtypeFactoryOptional(
				BlogsEntry.class.getName());
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private ContentDashboardItemActionProviderTracker
		_contentDashboardItemActionProviderTracker;

	@Reference
	private ContentDashboardItemSubtypeFactoryTracker
		_contentDashboardItemSubtypeFactoryTracker;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}