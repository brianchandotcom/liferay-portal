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

package com.liferay.blog.apio.internal.resource;

import static com.liferay.portal.apio.idempotent.Idempotent.idempotent;

import com.liferay.aggregate.rating.apio.identifier.AggregateRatingIdentifier;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.blog.apio.identifier.BlogPostingIdentifier;
import com.liferay.blog.apio.internal.form.BlogPostingForm;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.media.object.apio.identifier.FileEntryIdentifier;
import com.liferay.person.apio.identifier.PersonIdentifier;
import com.liferay.portal.apio.identifier.ClassNameClassPK;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.site.apio.identifier.WebSiteIdentifier;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/BlogPosting">BlogPosting </a> resources through a web
 * API. The resources are mapped from the internal model {@code BlogsEntry}.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class BlogPostingNestedCollectionResource
	implements NestedCollectionResource<BlogsEntry, Long, BlogPostingIdentifier,
		Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<BlogsEntry, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<BlogsEntry, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addBlogsEntry,
			_hasPermission.forAddingEntries(BlogsEntry.class),
			BlogPostingForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "blog-postings";
	}

	@Override
	public ItemRoutes<BlogsEntry, Long> itemRoutes(
		ItemRoutes.Builder<BlogsEntry, Long> builder) {

		return builder.addGetter(
			_blogsService::getEntry
		).addRemover(
			idempotent(_blogsService::deleteEntry),
			_hasPermission.forDeleting(BlogsEntry.class)
		).addUpdater(
			this::_updateBlogsEntry,
			_hasPermission.forUpdating(BlogsEntry.class),
			BlogPostingForm::buildForm
		).build();
	}

	@Override
	public Representor<BlogsEntry> representor(
		Representor.Builder<BlogsEntry, Long> builder) {

		return builder.types(
			"BlogPosting"
		).identifier(
			BlogsEntry::getEntryId
		).addBidirectionalModel(
			"interactionService", "blogPosts", WebSiteIdentifier.class,
			BlogsEntry::getGroupId
		).addDate(
			"dateCreated", BlogsEntry::getCreateDate
		).addDate(
			"dateDisplayed", BlogsEntry::getDisplayDate
		).addDate(
			"dateModified", BlogsEntry::getModifiedDate
		).addDate(
			"datePublished", BlogsEntry::getLastPublishDate
		).addLinkedModel(
			"aggregateRating", AggregateRatingIdentifier.class,
			ClassNameClassPK::create
		).addLinkedModel(
			"author", PersonIdentifier.class, BlogsEntry::getUserId
		).addLinkedModel(
			"creator", PersonIdentifier.class, BlogsEntry::getUserId
		).addLinkedModel(
			"image", FileEntryIdentifier.class,
			BlogsEntry::getCoverImageFileEntryId
		).addString(
			"alternativeHeadline", BlogsEntry::getSubtitle
		).addString(
			"articleBody", BlogsEntry::getContent
		).addString(
			"description", BlogsEntry::getDescription
		).addString(
			"fileFormat", blogsEntry -> "text/html"
		).addString(
			"headline", BlogsEntry::getTitle
		).build();
	}

	private BlogsEntry _addBlogsEntry(
			Long groupId, BlogPostingForm blogPostingForm)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		return _blogsService.addEntry(
			blogPostingForm.getHeadline(),
			blogPostingForm.getAlternativeHeadline(),
			blogPostingForm.getDescription(), blogPostingForm.getArticleBody(),
			blogPostingForm.getDisplayDateMonth(),
			blogPostingForm.getDisplayDateDay(),
			blogPostingForm.getDisplayDateYear(),
			blogPostingForm.getDisplayDateHour(),
			blogPostingForm.getDisplayDateMinute(), false, false, null, null,
			null, null, serviceContext);
	}

	private PageItems<BlogsEntry> _getPageItems(
		Pagination pagination, Long groupId) {

		List<BlogsEntry> blogsEntries = _blogsService.getGroupEntries(
			groupId, WorkflowConstants.STATUS_APPROVED,
			pagination.getStartPosition(), pagination.getEndPosition());
		int count = _blogsService.getGroupEntriesCount(
			groupId, WorkflowConstants.STATUS_APPROVED);

		return new PageItems<>(blogsEntries, count);
	}

	private BlogsEntry _updateBlogsEntry(
			Long blogsEntryId, BlogPostingForm blogPostingForm)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		BlogsEntry blogsEntry = _blogsService.getEntry(blogsEntryId);

		serviceContext.setScopeGroupId(blogsEntry.getGroupId());

		return _blogsService.updateEntry(
			blogsEntryId, blogPostingForm.getHeadline(),
			blogPostingForm.getAlternativeHeadline(),
			blogPostingForm.getDescription(), blogPostingForm.getArticleBody(),
			blogPostingForm.getDisplayDateMonth(),
			blogPostingForm.getDisplayDateDay(),
			blogPostingForm.getDisplayDateYear(),
			blogPostingForm.getDisplayDateHour(),
			blogPostingForm.getDisplayDateMinute(), false, false, null, null,
			null, null, serviceContext);
	}

	@Reference
	private BlogsEntryService _blogsService;

	@Reference
	private HasPermission _hasPermission;

}