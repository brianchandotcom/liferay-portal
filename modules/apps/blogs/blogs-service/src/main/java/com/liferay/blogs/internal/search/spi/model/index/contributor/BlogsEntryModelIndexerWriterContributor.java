/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.internal.search.spi.model.index.contributor;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.IndexerWriterMode;

/**
 * @author Luan Maoski
 */
public class BlogsEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<BlogsEntry> {

	public BlogsEntryModelIndexerWriterContributor(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _blogsEntryLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public IndexerWriterMode getIndexerWriterMode(BlogsEntry blogsEntry) {
		if (blogsEntry.isApproved() || blogsEntry.isDraft() ||
			blogsEntry.isInTrash() || blogsEntry.isPending() ||
			blogsEntry.isScheduled()) {

			return IndexerWriterMode.UPDATE;
		}

		if (!blogsEntry.isApproved() && !blogsEntry.isInTrash()) {
			return IndexerWriterMode.SKIP;
		}

		return IndexerWriterMode.DELETE;
	}

	private final BlogsEntryLocalService _blogsEntryLocalService;

}