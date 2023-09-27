/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.blogs.internal.item;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.content.dashboard.item.ContentDashboardItem;
import com.liferay.content.dashboard.item.ContentDashboardItemFactory;
import com.liferay.content.dashboard.item.action.ContentDashboardItemActionProviderRegistry;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtypeFactory;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtypeFactoryRegistry;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.Portal;

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
			_contentDashboardItemActionProviderRegistry,
			_groupLocalService.fetchGroup(blogsEntry.getGroupId()), _language,
			_portal);
	}

	@Override
	public ContentDashboardItemSubtypeFactory
		getContentDashboardItemSubtypeFactory() {

		return _contentDashboardItemSubtypeFactoryRegistry.
			getContentDashboardItemSubtypeFactory(BlogsEntry.class.getName());
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private ContentDashboardItemActionProviderRegistry
		_contentDashboardItemActionProviderRegistry;

	@Reference
	private ContentDashboardItemSubtypeFactoryRegistry
		_contentDashboardItemSubtypeFactoryRegistry;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}