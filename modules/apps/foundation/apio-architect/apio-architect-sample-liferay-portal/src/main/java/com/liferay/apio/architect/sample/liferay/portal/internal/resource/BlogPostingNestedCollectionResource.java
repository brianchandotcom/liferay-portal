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

package com.liferay.apio.architect.sample.liferay.portal.internal.resource;

import static com.liferay.apio.architect.sample.liferay.portal.internal.idempotent.Idempotent.idempotent;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.sample.liferay.portal.internal.form.BlogPostingForm;
import com.liferay.apio.architect.sample.liferay.portal.internal.identifier.BlogPostingIdentifier;
import com.liferay.apio.architect.sample.liferay.portal.internal.identifier.WebSiteIdentifier;
import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.kernel.model.BlogsEntryModel;
import com.liferay.blogs.kernel.service.BlogsEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

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
			this::_addBlogsEntry, (credentials, groupId) -> true,
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
			(credentials, entryId) -> true
		).addUpdater(
			this::_updateBlogsEntry, (credentials, entryId) -> true,
			BlogPostingForm::buildForm
		).build();
	}

	@Override
	public Representor<BlogsEntry, Long> representor(
		Representor.Builder<BlogsEntry, Long> builder) {

		return builder.types(
			"BlogPosting"
		).identifier(
			BlogsEntry::getEntryId
		).addBidirectionalModel(
			"interactionService", "blogPosts", WebSiteIdentifier.class,
			BlogsEntryModel::getGroupId
		).addDate(
			"dateCreated", BlogsEntry::getCreateDate
		).addDate(
			"dateModified", BlogsEntry::getModifiedDate
		).addDate(
			"datePublished", BlogsEntry::getLastPublishDate
		).addLink(
			"license", "https://creativecommons.org/licenses/by/4.0"
		).addString(
			"alternativeHeadline", BlogsEntry::getSubtitle
		).addString(
			"articleBody", BlogsEntry::getContent
		).addString(
			"description", BlogsEntry::getDescription
		).addString(
			"fileFormat", __ -> "text/html"
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
			groupId, 0, pagination.getStartPosition(),
			pagination.getEndPosition());
		int count = _blogsService.getGroupEntriesCount(groupId, 0);

		return new PageItems<>(blogsEntries, count);
	}

	private BlogsEntry _updateBlogsEntry(
			Long entryId, BlogPostingForm blogPostingForm)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		BlogsEntry blogsEntry = _blogsService.getEntry(entryId);

		serviceContext.setScopeGroupId(blogsEntry.getGroupId());

		return _blogsService.updateEntry(
			entryId, blogPostingForm.getHeadline(),
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

}