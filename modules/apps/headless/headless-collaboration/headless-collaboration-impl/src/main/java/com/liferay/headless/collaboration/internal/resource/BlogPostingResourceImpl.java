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

package com.liferay.headless.collaboration.internal.resource;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.friendly.url.exception.DuplicateFriendlyURLEntryException;
import com.liferay.headless.collaboration.dto.BlogPosting;
import com.liferay.headless.collaboration.dto.ImageObject;
import com.liferay.headless.collaboration.resource.BlogPostingResource;
import com.liferay.portal.kernel.exception.NoSuchModelException;
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
import java.util.Optional;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/blog-posting.properties",
	scope = ServiceScope.PROTOTYPE, service = BlogPostingResource.class
)
public class BlogPostingResourceImpl extends BaseBlogPostingResourceImpl {

	@Override
	public Response deleteBlogPosting(Long blogPostingId) throws Exception {
		try {
			_blogsEntryService.deleteEntry(blogPostingId);
		}
		catch (NoSuchModelException nsme) {
		}

		return null;
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

		Date publishedDate = Optional.ofNullable(
			blogPosting.getDatePublished()
		).orElseGet(
			Date::new
		);

		Timestamp timestamp = new Timestamp(publishedDate.getTime());

		LocalDateTime localDateTime = timestamp.toLocalDateTime();

		try {
			BlogsEntry blogsEntry = _blogsEntryService.addEntry(
				blogPosting.getHeadline(), blogPosting.getAlternativeHeadline(),
				blogPosting.getFriendlyUrlPath(), blogPosting.getDescription(),
				blogPosting.getArticleBody(), localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), true, true,
				new String[0], blogPosting.getCaption(),
				_getImageSelector(blogPosting), null,
				_getServiceContext(contentSpaceId, blogPosting));

			return _toBlogPosting(blogsEntry);
		}
		catch (DuplicateFriendlyURLEntryException dfurlee) {
			throw new ClientErrorException(
				"Duplicate friendly URL", 422, dfurlee);
		}
	}

	@Override
	public BlogPosting putBlogPosting(
			Long blogPostingId, BlogPosting blogPosting)
		throws Exception {

		Date publishedDate = Optional.ofNullable(
			blogPosting.getDatePublished()
		).orElseGet(
			Date::new
		);

		Timestamp timestamp = new Timestamp(publishedDate.getTime());

		LocalDateTime localDateTime = timestamp.toLocalDateTime();

		BlogsEntry blogsEntry = _blogsEntryService.getEntry(blogPostingId);

		try {
			BlogsEntry updatedBlogsEntry = _blogsEntryService.updateEntry(
				blogPostingId, blogPosting.getHeadline(),
				blogPosting.getAlternativeHeadline(),
				blogPosting.getFriendlyUrlPath(), blogPosting.getDescription(),
				blogPosting.getArticleBody(), localDateTime.getMonthValue() - 1,
				localDateTime.getDayOfMonth(), localDateTime.getYear(),
				localDateTime.getHour(), localDateTime.getMinute(), true, true,
				new String[0], blogPosting.getCaption(),
				_getImageSelector(blogPosting), null,
				_getServiceContext(blogsEntry.getGroupId(), blogPosting));

			return _toBlogPosting(updatedBlogsEntry);
		}
		catch (DuplicateFriendlyURLEntryException dfurlee) {
			throw new ClientErrorException(
				"Duplicate friendly URL", 422, dfurlee);
		}
	}

	private ImageSelector _getImageSelector(BlogPosting blogPosting) {
		ImageObject imageObject = blogPosting.getImage();

		Long imageId = imageObject.getId();

		if ((imageId == null) || imageId.equals(0L)) {
			return null;
		}

		try {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(imageId);

			return new ImageSelector(
				FileUtil.getBytes(fileEntry.getContentStream()),
				fileEntry.getFileName(), fileEntry.getMimeType(),
				"{\"height\": 0,\"width\": 0,\"x\": 0,\"y\": 0}");
		}
		catch (Exception e) {
			throw new BadRequestException(
				"Unable to find file entry with id " + imageId, e);
		}
	}

	private ServiceContext _getServiceContext(
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