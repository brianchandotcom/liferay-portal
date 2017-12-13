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

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.identifier.LongIdentifier;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.resource.ScopedCollectionResource;
import com.liferay.apio.architect.routes.Routes;
import com.liferay.apio.architect.sample.liferay.portal.identifier.AggregateRatingIdentifier;
import com.liferay.apio.architect.sample.liferay.portal.identifier.CommentableIdentifier;
import com.liferay.apio.architect.sample.liferay.portal.rating.AggregateRating;
import com.liferay.apio.architect.sample.liferay.portal.rating.AggregateRatingService;
import com.liferay.apio.architect.sample.liferay.portal.website.WebSite;
import com.liferay.apio.architect.sample.liferay.portal.website.WebSiteService;
import com.liferay.blogs.kernel.exception.NoSuchEntryException;
import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.kernel.service.BlogsEntryService;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import javax.ws.rs.BadRequestException;
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
@Component(immediate = true, service = CollectionResource.class)
public class BlogPostingScopedCollectionResource
	implements ScopedCollectionResource<BlogsEntry, LongIdentifier> {

	@Override
	public String getName() {
		return "blog-postings";
	}

	@Override
	public Representor<BlogsEntry, LongIdentifier> representor(
		Representor.Builder<BlogsEntry, LongIdentifier> builder) {

		return builder.types(
			"BlogPosting"
		).identifier(
			blogsEntry -> blogsEntry::getEntryId
		).addBidirectionalModel(
			"webSite", "blogs", WebSite.class, this::_getWebSiteOptional,
			WebSite::getWebSiteLongIdentifier
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
			"aggregateRating", AggregateRating.class,
			this::_getAggregateRatingOptional
		).addLinkedModel(
			"creator", User.class, this::_getUserOptional
		).addLinkedModel(
			"author", User.class, this::_getUserOptional
		).addRelatedCollection(
			"comment", Comment.class, CommentableIdentifier::create
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

	@Override
	public Routes<BlogsEntry> routes(
		Routes.Builder<BlogsEntry, LongIdentifier> builder) {

		return builder.addCollectionPageGetter(
			this::_getPageItems, LongIdentifier.class
		).addCollectionPageItemCreator(
			this::_addBlogsEntry, LongIdentifier.class
		).addCollectionPageItemGetter(
			this::_getBlogsEntry
		).addCollectionPageItemRemover(
			this::_deleteBlogsEntry
		).addCollectionPageItemUpdater(
			this::_updateBlogsEntry
		).build();
	}

	private BlogsEntry _addBlogsEntry(
		LongIdentifier groupLongIdentifier, Map<String, Object> body) {

		String title = (String)body.get("headline");
		String subtitle = (String)body.get("alternativeHeadline");
		String description = (String)body.get("description");
		String content = (String)body.get("articleBody");
		String displayDateString = (String)body.get("displayDate");

		Supplier<BadRequestException> invalidBodyExceptionSupplier =
			() -> new BadRequestException("Invalid body");

		if (Validator.isNull(title) || Validator.isNull(subtitle) ||
			Validator.isNull(description) || Validator.isNull(content) ||
			Validator.isNull(displayDateString)) {

			throw invalidBodyExceptionSupplier.get();
		}

		Calendar calendar = Calendar.getInstance();

		Try<DateFormat> dateFormatTry = Try.success(
			DateUtil.getISO8601Format());

		Date displayDate = dateFormatTry.map(
			dateFormat -> dateFormat.parse(displayDateString)
		).mapFailMatching(
			ParseException.class, invalidBodyExceptionSupplier
		).getUnchecked();

		calendar.setTime(displayDate);

		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int year = calendar.get(Calendar.YEAR);
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupLongIdentifier.getId());

		Try<BlogsEntry> blogsEntryTry = Try.fromFallible(
			() -> _blogsService.addEntry(
				title, subtitle, description, content, month, day, year, hour,
				minute, false, false, null, null, null, null, serviceContext));

		return blogsEntryTry.getUnchecked();
	}

	private void _deleteBlogsEntry(LongIdentifier blogsEntryIdLongIdentifier) {
		try {
			_blogsService.deleteEntry(blogsEntryIdLongIdentifier.getId());
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private Optional<AggregateRating> _getAggregateRatingOptional(
		BlogsEntry blogsEntry) {

		AggregateRatingIdentifier aggregateRatingIdentifier =
			AggregateRatingIdentifier.create(blogsEntry);

		return Optional.of(
			_aggregateRatingService.getAggregateRating(
				aggregateRatingIdentifier));
	}

	private BlogsEntry _getBlogsEntry(
		LongIdentifier blogsEntryIdLongIdentifier) {

		try {
			return _blogsService.getEntry(blogsEntryIdLongIdentifier.getId());
		}
		catch (NoSuchEntryException | PrincipalException e) {
			throw new NotFoundException(
				"Unable to get blogs entry " +
					blogsEntryIdLongIdentifier.getId(),
				e);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<BlogsEntry> _getPageItems(
		Pagination pagination, LongIdentifier groupIdLongIdentifier) {

		List<BlogsEntry> blogsEntries = _blogsService.getGroupEntries(
			groupIdLongIdentifier.getId(), 0, pagination.getStartPosition(),
			pagination.getEndPosition());
		int count = _blogsService.getGroupEntriesCount(
			groupIdLongIdentifier.getId(), 0);

		return new PageItems<>(blogsEntries, count);
	}

	private Optional<User> _getUserOptional(BlogsEntry blogsEntry) {
		try {
			return Optional.ofNullable(
				_userService.getUserById(blogsEntry.getUserId()));
		}
		catch (NoSuchUserException | PrincipalException e) {
			throw new NotFoundException(
				"Unable to get user " + blogsEntry.getUserId(), e);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private Optional<WebSite> _getWebSiteOptional(BlogsEntry blogsEntry) {
		return _webSiteService.getWebSite(blogsEntry.getGroupId());
	}

	private BlogsEntry _updateBlogsEntry(
		LongIdentifier blogsEntryIdLongIdentifier, Map<String, Object> body) {

		String title = (String)body.get("headline");
		String subtitle = (String)body.get("alternativeHeadline");
		String description = (String)body.get("description");
		String content = (String)body.get("articleBody");
		String displayDateString = (String)body.get("displayDate");

		Supplier<BadRequestException> invalidBodyExceptionSupplier =
			() -> new BadRequestException("Invalid body");

		if (Validator.isNull(title) || Validator.isNull(subtitle) ||
			Validator.isNull(description) || Validator.isNull(content) ||
			Validator.isNull(displayDateString)) {

			throw invalidBodyExceptionSupplier.get();
		}

		Calendar calendar = Calendar.getInstance();

		Try<DateFormat> dateFormatTry = Try.success(
			DateUtil.getISO8601Format());

		Date displayDate = dateFormatTry.map(
			dateFormat -> dateFormat.parse(displayDateString)
		).mapFailMatching(
			ParseException.class, invalidBodyExceptionSupplier
		).getUnchecked();

		calendar.setTime(displayDate);

		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int year = calendar.get(Calendar.YEAR);
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		BlogsEntry blogsEntry = _getBlogsEntry(blogsEntryIdLongIdentifier);

		serviceContext.setScopeGroupId(blogsEntry.getGroupId());

		Try<BlogsEntry> blogsEntryTry = Try.fromFallible(
			() -> _blogsService.updateEntry(
				blogsEntryIdLongIdentifier.getId(), title, subtitle,
				description, content, month, day, year, hour, minute, false,
				false, null, null, null, null, serviceContext));

		return blogsEntryTry.getUnchecked();
	}

	@Reference
	private AggregateRatingService _aggregateRatingService;

	@Reference
	private BlogsEntryService _blogsService;

	@Reference
	private UserService _userService;

	@Reference
	private WebSiteService _webSiteService;

}