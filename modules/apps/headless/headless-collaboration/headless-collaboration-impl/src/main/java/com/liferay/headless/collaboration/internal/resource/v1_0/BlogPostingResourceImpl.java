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
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.headless.collaboration.dto.v1_0.BlogPosting;
import com.liferay.headless.collaboration.dto.v1_0.Category;
import com.liferay.headless.collaboration.dto.v1_0.Image;
import com.liferay.headless.collaboration.internal.dto.v1_0.AggregateRatingUtil;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingResource;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;

import java.time.LocalDateTime;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

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
	public Response deleteBlogPosting(Long blogPostingId) throws Exception {
		_blogsEntryService.deleteEntry(blogPostingId);

		return buildNoContentResponse();
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

		Category[] categories = blogPosting.getCategory();

		if (ArrayUtil.isNotEmpty(categories)) {
			Stream<Category> stream = Arrays.stream(categories);

			long[] assetCategoryIds = stream.mapToLong(
				Category::getCategoryId
			).toArray();

			serviceContext.setAssetCategoryIds(assetCategoryIds);
		}

		String[] keywords = blogPosting.getKeywords();

		if (ArrayUtil.isNotEmpty(keywords)) {
			serviceContext.setAssetTagNames(keywords);
		}

		serviceContext.setScopeGroupId(groupId);

		return serviceContext;
	}

	private Category[] _getBlogPostingCategories(BlogsEntry blogsEntry) {
		List<AssetCategory> assetCategories =
			_assetCategoryLocalService.getCategories(
				BlogsEntry.class.getName(), blogsEntry.getEntryId());

		Stream<AssetCategory> stream = assetCategories.stream();

		return stream.map(
			assetCategory -> new Category() {
				{
					setCategoryId(assetCategory.getCategoryId());
					setCategoryName(assetCategory.getName());
				}
			}
		).toArray(
			Category[]::new
		);
	}

	private Image _getBlogPostingImage(BlogsEntry blogsEntry) throws Exception {
		long coverImageFileEntryId = blogsEntry.getCoverImageFileEntryId();

		if (coverImageFileEntryId == 0) {
			return null;
		}

		FileEntry fileEntry = _dlAppService.getFileEntry(coverImageFileEntryId);

		FileVersion fileVersion = _dlAppService.getFileVersion(
			fileEntry.getFileEntryId());

		return new Image() {
			{
				setContentUrl(
					_dlurlHelper.getPreviewURL(
						fileEntry, fileVersion, null, "", false, false));
				setName(blogsEntry.getCoverImageCaption());
				setImageId(coverImageFileEntryId);
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
		Image image = _getBlogPostingImage(blogsEntry);

		Category[] categories = _getBlogPostingCategories(blogsEntry);

		return new BlogPosting() {
			{
				setAlternativeHeadline(blogsEntry.getSubtitle());
				setAggregateRating(
					AggregateRatingUtil.toAggregateRating(
						_ratingsStatsLocalService.fetchStats(
							BlogsEntry.class.getName(),
							blogsEntry.getEntryId())));
				setArticleBody(blogsEntry.getContent());
				setCaption(blogsEntry.getCoverImageCaption());
				setCategory(categories);
				setContentSpace(blogsEntry.getGroupId());
				setDateCreated(blogsEntry.getCreateDate());
				setDateModified(blogsEntry.getModifiedDate());
				setDatePublished(blogsEntry.getDisplayDate());
				setDescription(blogsEntry.getDescription());
				setEncodingFormat("text/html");
				setFriendlyUrlPath(blogsEntry.getUrlTitle());
				setHeadline(blogsEntry.getTitle());
				setId(blogsEntry.getEntryId());
				setImage(image);
			}
		};
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlurlHelper;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

}