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

package com.liferay.vocabulary.apio.internal.architect.router;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.blog.apio.architect.identifier.BlogPostingIdentifier;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.vocabulary.apio.architect.identifier.VocabularyIdentifier;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose {@code Vocabulary} resources
 * associated to a {@code BlogsEntry}. The resources are mapped from the
 * internal model {@code AssetVocabulary}.
 *
 * @author Javier Gamarra
 * @review
 */
@Component
public class BlogPostingVocabularyNestedCollectionRouter implements
	NestedCollectionRouter<AssetVocabulary, Long, VocabularyIdentifier, Long,
		BlogPostingIdentifier> {

	@Override
	public NestedCollectionRoutes<AssetVocabulary, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<AssetVocabulary, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageEntries
		).build();
	}

	private PageItems _getPageEntries(Pagination pagination, Long blogsEntryId)
		throws PortalException {

		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogsEntryId);

		List<AssetVocabulary> groupsVocabularies =
			_assetVocabularySevice.getGroupsVocabularies(
				new long[] {blogsEntry.getGroupId()},
				BlogsEntry.class.getName(), blogsEntryId);

		return new PageItems<>(groupsVocabularies, groupsVocabularies.size());
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularySevice;

	@Reference
	private BlogsEntryService _blogsEntryService;

}