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

package com.liferay.headless.collaboration.internal.dto.v1_0.util;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.collaboration.dto.v1_0.BlogPosting;
import com.liferay.headless.collaboration.dto.v1_0.Image;
import com.liferay.headless.collaboration.dto.v1_0.TaxonomyCategory;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;

/**
 * @author Rubén Pulido
 * @author Víctor Galán
 */
public class BlogPostingUtil {

	public static BlogPosting toBlogPosting(
			AssetCategoryLocalService assetCategoryLocalService,
			AssetTagLocalService assetTagLocalService, BlogsEntry blogsEntry,
			CommentManager commentManager, DLAppService dlAppService,
			DLURLHelper dlURLHelper, Portal portal,
			RatingsStatsLocalService ratingsStatsLocalService,
			UserLocalService userLocalService)
		throws Exception {

		return new BlogPosting() {
			{
				alternativeHeadline = blogsEntry.getSubtitle();
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					ratingsStatsLocalService.fetchStats(
						BlogsEntry.class.getName(), blogsEntry.getEntryId()));
				articleBody = blogsEntry.getContent();
				contentSpaceId = blogsEntry.getGroupId();
				creator = CreatorUtil.toCreator(
					portal, userLocalService.getUser(blogsEntry.getUserId()));
				dateCreated = blogsEntry.getCreateDate();
				dateModified = blogsEntry.getModifiedDate();
				datePublished = blogsEntry.getDisplayDate();
				description = blogsEntry.getDescription();
				encodingFormat = "text/html";
				friendlyUrlPath = blogsEntry.getUrlTitle();
				headline = blogsEntry.getTitle();
				id = blogsEntry.getEntryId();
				image = _getImage(blogsEntry, dlAppService, dlURLHelper);
				keywords = ListUtil.toArray(
					assetTagLocalService.getTags(
						BlogsEntry.class.getName(), blogsEntry.getEntryId()),
					AssetTag.NAME_ACCESSOR);
				numberOfComments = commentManager.getCommentsCount(
					BlogsEntry.class.getName(), blogsEntry.getEntryId());
				taxonomyCategories = TransformUtil.transformToArray(
					assetCategoryLocalService.getCategories(
						BlogsEntry.class.getName(), blogsEntry.getEntryId()),
					assetCategory -> new TaxonomyCategory() {
						{
							taxonomyCategoryId = assetCategory.getCategoryId();
							taxonomyCategoryName = assetCategory.getName();
						}
					},
					TaxonomyCategory.class);
			}
		};
	}

	private static Image _getImage(
			BlogsEntry blogsEntry, DLAppService dlAppService,
			DLURLHelper dlURLHelper)
		throws Exception {

		long coverImageFileEntryId = blogsEntry.getCoverImageFileEntryId();

		if (coverImageFileEntryId == 0) {
			return null;
		}

		FileEntry fileEntry = dlAppService.getFileEntry(coverImageFileEntryId);

		return new Image() {
			{
				caption = blogsEntry.getCoverImageCaption();
				contentUrl = dlURLHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null, "", false,
					false);
				imageId = coverImageFileEntryId;
			}
		};
	}

}