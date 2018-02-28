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

package com.liferay.blog.apio.internal.architect.resource;

import com.liferay.aggregate.rating.apio.architect.identifier.AggregateRatingIdentifier;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.blog.apio.architect.identifier.BlogsEntryIdentifier;
import com.liferay.blog.apio.internal.architect.form.BlogPostingForm;
import com.liferay.blogs.kernel.exception.NoSuchEntryException;
import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.kernel.model.BlogsEntryModel;
import com.liferay.blogs.kernel.service.BlogsEntryService;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.apio.architect.context.auth.MockPermissions;
import com.liferay.portal.apio.architect.context.identifier.ClassNameClassPK;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.site.apio.architect.identifier.WebSiteIdentifier;

import java.util.List;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

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
	implements NestedCollectionResource<BlogsEntry, Long, BlogsEntryIdentifier,
		Long, WebSiteIdentifier> {

	@Override
	public NestedCollectionRoutes<BlogsEntry, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<BlogsEntry, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addBlogsEntry, MockPermissions::validPermission,
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
			this::_getBlogsEntry
		).addRemover(
			this::_deleteBlogsEntry, MockPermissions::validPermission
		).addUpdater(
			this::_updateBlogsEntry, MockPermissions::validPermission,
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
			"webSite", "blogs", WebSiteIdentifier.class,
			BlogsEntryModel::getGroupId
		).addDate(
			"createDate", BlogsEntry::getCreateDate
		).addDate(
			"displayDate", BlogsEntry::getDisplayDate
		).addDate(
			"modifiedDate", BlogsEntry::getModifiedDate
		).addDate(
			"publishedDate", BlogsEntry::getLastPublishDate
		).addLink(
			"license", "https://creativecommons.org/licenses/by/4.0"
		).addLinkedModel(
			"aggregateRating", AggregateRatingIdentifier.class,
			ClassNameClassPK::create
		).addLinkedModel(
			"author", PersonIdentifier.class, this::_getUserOptional
		).addLinkedModel(
			"creator", PersonIdentifier.class, this::_getUserOptional
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
		Long groupId, BlogPostingForm blogPostingForm) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		Try<BlogsEntry> blogsEntryTry = Try.fromFallible(
			() -> _blogsService.addEntry(
				blogPostingForm.getHeadline(),
				blogPostingForm.getAlternativeHeadline(),
				blogPostingForm.getDescription(),
				blogPostingForm.getArticleBody(),
				blogPostingForm.getDisplayDateMonth(),
				blogPostingForm.getDisplayDateDay(),
				blogPostingForm.getDisplayDateYear(),
				blogPostingForm.getDisplayDateHour(),
				blogPostingForm.getDisplayDateMinute(), false, false, null,
				null, null, null, serviceContext));

		return blogsEntryTry.getUnchecked();
	}

	private void _deleteBlogsEntry(Long blogsEntryId) {
		try {
			_blogsService.deleteEntry(blogsEntryId);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private BlogsEntry _getBlogsEntry(Long blogsEntryId) {
		try {
			return _blogsService.getEntry(blogsEntryId);
		}
		catch (NoSuchEntryException | PrincipalException e) {
			throw new NotFoundException(
				"Unable to get blogs entry " + blogsEntryId, e);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<BlogsEntry> _getPageItems(
		Pagination pagination, Long groupId) {

		List<BlogsEntry> blogsEntries = _blogsService.getGroupEntries(
			groupId, 0, pagination.getStartPosition(),
			pagination.getEndPosition());
		int count = _blogsService.getGroupEntriesCount(groupId, 0);

		return new PageItems<>(blogsEntries, count);
	}

	private Long _getUserOptional(BlogsEntry blogsEntry) {
		return blogsEntry.getUserId();
	}

	private BlogsEntry _updateBlogsEntry(
		Long blogsEntryId, BlogPostingForm blogPostingForm) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		BlogsEntry blogsEntry = _getBlogsEntry(blogsEntryId);

		serviceContext.setScopeGroupId(blogsEntry.getGroupId());

		Try<BlogsEntry> blogsEntryTry = Try.fromFallible(
			() -> _blogsService.updateEntry(
				blogsEntryId, blogPostingForm.getHeadline(),
				blogPostingForm.getAlternativeHeadline(),
				blogPostingForm.getDescription(),
				blogPostingForm.getArticleBody(),
				blogPostingForm.getDisplayDateMonth(),
				blogPostingForm.getDisplayDateDay(),
				blogPostingForm.getDisplayDateYear(),
				blogPostingForm.getDisplayDateHour(),
				blogPostingForm.getDisplayDateMinute(), false, false, null,
				null, null, null, serviceContext));

		return blogsEntryTry.getUnchecked();
	}

	@Reference
	private BlogsEntryService _blogsService;

}