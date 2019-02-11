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

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.headless.collaboration.dto.v1_0.BlogPosting;
import com.liferay.headless.collaboration.dto.v1_0.ImageObject;
import com.liferay.headless.collaboration.resource.v1_0.BlogPostingResource;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import java.sql.Timestamp;

import java.time.LocalDateTime;

import java.util.Date;
import java.util.Objects;

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

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public BlogPosting getBlogPosting(Long blogPostingId) throws Exception {
		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogPostingId);

		return _toBlogPosting(blogsEntry);
	}

	@Override
	public Page<BlogPosting> getContentSpaceBlogPostingPage(
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

		LocalDateTime localDateTime = _getLocalDateTime(
			blogPosting.getDatePublished());

		BlogsEntry blogsEntry = _blogsEntryService.addEntry(
			blogPosting.getHeadline(), blogPosting.getAlternativeHeadline(),
			blogPosting.getFriendlyUrlPath(), blogPosting.getDescription(),
			blogPosting.getArticleBody(), localDateTime.getMonthValue() - 1,
			localDateTime.getDayOfMonth(), localDateTime.getYear(),
			localDateTime.getHour(), localDateTime.getMinute(), true, true,
			new String[0], blogPosting.getCaption(),
			_getImageSelector(blogPosting), null,
			_createServiceContext(contentSpaceId, blogPosting));

		return _toBlogPosting(blogsEntry);
	}

	@Override
	public BlogPosting putBlogPosting(
			Long blogPostingId, BlogPosting blogPosting)
		throws Exception {

		LocalDateTime localDateTime = _getLocalDateTime(
			blogPosting.getDatePublished());

		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogPostingId);

		BlogsEntry updatedBlogsEntry = _blogsEntryService.updateEntry(
			blogPostingId, blogPosting.getHeadline(),
			blogPosting.getAlternativeHeadline(),
			blogPosting.getFriendlyUrlPath(), blogPosting.getDescription(),
			blogPosting.getArticleBody(), localDateTime.getMonthValue() - 1,
			localDateTime.getDayOfMonth(), localDateTime.getYear(),
			localDateTime.getHour(), localDateTime.getMinute(), true, true,
			new String[0], blogPosting.getCaption(),
			_getImageSelector(blogPosting), null,
			_createServiceContext(blogsEntry.getGroupId(), blogPosting));

		return _toBlogPosting(updatedBlogsEntry);
	}

	private ServiceContext _createServiceContext(
		long groupId, BlogPosting blogPosting) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		String[] keywords = blogPosting.getKeywords();

		if (ArrayUtil.isNotEmpty(keywords)) {
			serviceContext.setAssetTagNames(keywords);
		}

		serviceContext.setScopeGroupId(groupId);

		return serviceContext;
	}

	private ImageSelector _getImageSelector(BlogPosting blogPosting) {
		ImageObject imageObject = blogPosting.getImage();

		Long imageId = imageObject.getId();

		if (Objects.equals(imageId, 0L)) {
			return null;
		}

		try {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(imageId);

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

	private LocalDateTime _getLocalDateTime(Date date) {
		Timestamp timestamp = null;

		if (date != null) {
			timestamp = new Timestamp(date.getTime());
		}
		else {
			timestamp = new Timestamp(System.currentTimeMillis());
		}

		return timestamp.toLocalDateTime();
	}

	private BlogPosting _toBlogPosting(BlogsEntry blogsEntry) {
		return new BlogPosting() {
			{
				setAlternativeHeadline(blogsEntry.getSubtitle());
				setArticleBody(blogsEntry.getContent());
				setCaption(blogsEntry.getCoverImageCaption());
				setDescription(blogsEntry.getDescription());
				setFriendlyUrlPath(blogsEntry.getUrlTitle());
				setHeadline(blogsEntry.getTitle());
				setId(blogsEntry.getEntryId());
			}
		};
	}

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

}