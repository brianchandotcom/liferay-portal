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

package com.liferay.headless.collaboration.internal.resource.v1_0;

import static com.liferay.portal.vulcan.util.LocalDateTimeUtil.toLocalDateTime;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.collaboration.dto.v1_0.BlogPosting;
import com.liferay.headless.collaboration.dto.v1_0.Categories;
import com.liferay.headless.collaboration.dto.v1_0.Image;
import com.liferay.headless.collaboration.internal.dto.v1_0.BlogPostingImpl;
import com.liferay.headless.collaboration.internal.dto.v1_0.CategoriesImpl;
import com.liferay.headless.collaboration.internal.dto.v1_0.ImageImpl;
import com.liferay.headless.collaboration.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.collaboration.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingResource;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/blog-posting.properties",
	scope = ServiceScope.PROTOTYPE, service = BlogPostingResource.class
)
public class BlogPostingResourceImpl extends BaseBlogPostingResourceImpl {

	@Override
	public boolean deleteBlogPosting(Long blogPostingId) throws Exception {
		_blogsEntryService.deleteEntry(blogPostingId);

		return true;
	}

	@Override
	public BlogPosting getBlogPosting(Long blogPostingId) throws Exception {
		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogPostingId);

		return _toBlogPosting(blogsEntry);
	}

	@Override
	public Page<BlogPosting> getContentSpaceBlogPostingsPage(
		Long parentId, Pagination pagination) {

		return Page.of(
			transform(
				_blogsEntryService.getGroupEntries(
					parentId, WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toBlogPosting),
			pagination,
			_blogsEntryService.getGroupEntriesCount(
				parentId, WorkflowConstants.STATUS_APPROVED));
	}

	@Override
	public BlogPosting postContentSpaceBlogPosting(
			Long contentSpaceId, BlogPosting blogPosting)
		throws Exception {

		LocalDateTime localDateTime = toLocalDateTime(
			blogPosting.getDatePublished());

		return _toBlogPosting(
			_blogsEntryService.addEntry(
				blogPosting.getHeadline(), blogPosting.getAlternativeHeadline(),
				blogPosting.getFriendlyUrlPath(), blogPosting.getDescription(),
				blogPosting.getArticleBody(), localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), true, true,
				new String[0], blogPosting.getCaption(),
				_getImageSelector(blogPosting), null,
				_createServiceContext(contentSpaceId, blogPosting)));
	}

	@Override
	public BlogPosting putBlogPosting(
			Long blogPostingId, BlogPosting blogPosting)
		throws Exception {

		LocalDateTime localDateTime = toLocalDateTime(
			blogPosting.getDatePublished());

		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogPostingId);

		return _toBlogPosting(
			_blogsEntryService.updateEntry(
				blogPostingId, blogPosting.getHeadline(),
				blogPosting.getAlternativeHeadline(),
				blogPosting.getFriendlyUrlPath(), blogPosting.getDescription(),
				blogPosting.getArticleBody(), localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), true, true,
				new String[0], blogPosting.getCaption(),
				_getImageSelector(blogPosting), null,
				_createServiceContext(blogsEntry.getGroupId(), blogPosting)));
	}

	private ServiceContext _createServiceContext(
		long groupId, BlogPosting blogPosting) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Long[] categoryIds = blogPosting.getCategoryIds();

		if (ArrayUtil.isNotEmpty(categoryIds)) {
			serviceContext.setAssetCategoryIds(ArrayUtil.toArray(categoryIds));
		}

		String[] keywords = blogPosting.getKeywords();

		if (ArrayUtil.isNotEmpty(keywords)) {
			serviceContext.setAssetTagNames(keywords);
		}

		serviceContext.setScopeGroupId(groupId);

		return serviceContext;
	}

	private String[] _getAssetTagNames(BlogsEntry blogsEntry) {
		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			BlogsEntry.class.getName(), blogsEntry.getEntryId());

		return ListUtil.toArray(assetTags, AssetTag.NAME_ACCESSOR);
	}

	private Categories[] _getCategories(BlogsEntry blogsEntry) {
		List<AssetCategory> assetCategories =
			_assetCategoryLocalService.getCategories(
				BlogsEntry.class.getName(), blogsEntry.getEntryId());

		Stream<AssetCategory> stream = assetCategories.stream();

		return stream.map(
			assetCategory -> new CategoriesImpl() {
				{
					setCategoryId(assetCategory.getCategoryId());
					setCategoryName(assetCategory.getName());
				}
			}
		).toArray(
			Categories[]::new
		);
	}

	private Long[] _getCategoryIds(BlogsEntry blogsEntry) {
		return ArrayUtil.toArray(
			_assetCategoryLocalService.getCategoryIds(
				BlogsEntry.class.getName(), blogsEntry.getEntryId()));
	}

	private Image _getImage(BlogsEntry blogsEntry) throws Exception {
		long coverImageFileEntryId = blogsEntry.getCoverImageFileEntryId();

		if (coverImageFileEntryId == 0) {
			return null;
		}

		FileEntry fileEntry = _dlAppService.getFileEntry(coverImageFileEntryId);

		return new ImageImpl() {
			{
				contentUrl = _dlURLHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null, "", false,
					false);
				imageId = coverImageFileEntryId;
				name = blogsEntry.getCoverImageCaption();
			}
		};
	}

	private ImageSelector _getImageSelector(BlogPosting blogPosting) {
		Long imageId = blogPosting.getImageId();

		if (Objects.equals(imageId, 0L)) {
			return null;
		}

		try {
			FileEntry fileEntry = _dlAppService.getFileEntry(imageId);

			return new ImageSelector(
				FileUtil.getBytes(fileEntry.getContentStream()),
				fileEntry.getFileName(), fileEntry.getMimeType(),
				"{\"height\": 0, \"width\": 0, \"x\": 0, \"y\": 0}");
		}
		catch (Exception e) {
			throw new BadRequestException(
				"Unable to get file entry " + imageId, e);
		}
	}

	private BlogPosting _toBlogPosting(BlogsEntry blogsEntry) throws Exception {
		return new BlogPostingImpl() {
			{
				alternativeHeadline = blogsEntry.getSubtitle();
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						BlogsEntry.class.getName(), blogsEntry.getEntryId()));
				articleBody = blogsEntry.getContent();
				caption = blogsEntry.getCoverImageCaption();
				categories = _getCategories(blogsEntry);
				categoryIds = _getCategoryIds(blogsEntry);
				contentSpace = blogsEntry.getGroupId();
				creator = CreatorUtil.toCreator(
					_portal, _userLocalService.getUser(blogsEntry.getUserId()));
				dateCreated = blogsEntry.getCreateDate();
				dateModified = blogsEntry.getModifiedDate();
				datePublished = blogsEntry.getDisplayDate();
				description = blogsEntry.getDescription();
				encodingFormat = "text/html";
				friendlyUrlPath = blogsEntry.getUrlTitle();
				headline = blogsEntry.getTitle();
				id = blogsEntry.getEntryId();
				image = _getImage(blogsEntry);
				imageId = blogsEntry.getCoverImageFileEntryId();
				keywords = _getAssetTagNames(blogsEntry);
			}
		};
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private UserLocalService _userLocalService;

}