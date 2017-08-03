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

package com.liferay.vulcan.sample.liferay.portal.internal.resource;

import com.liferay.blogs.kernel.exception.NoSuchEntryException;
import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.kernel.service.BlogsEntryService;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.vulcan.filter.QueryParamFilterType;
import com.liferay.vulcan.liferay.portal.filter.GroupIdFilter;
import com.liferay.vulcan.liferay.portal.filter.provider.ClassNameClassPKFilterProvider;
import com.liferay.vulcan.liferay.portal.filter.provider.GroupIdFilterProvider;
import com.liferay.vulcan.liferay.portal.identifier.ClassNameClassPKIdentifier;
import com.liferay.vulcan.pagination.PageItems;
import com.liferay.vulcan.pagination.Pagination;
import com.liferay.vulcan.resource.Resource;
import com.liferay.vulcan.resource.Routes;
import com.liferay.vulcan.resource.builder.RepresentorBuilder;
import com.liferay.vulcan.resource.builder.RoutesBuilder;
import com.liferay.vulcan.sample.liferay.portal.rating.AggregateRating;
import com.liferay.vulcan.sample.liferay.portal.rating.AggregateRatingService;

import java.text.DateFormat;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides all the necessary information to expose <a
 * href="http://schema.org/BlogPosting">BlogPosting</a> resources through a web
 * API.
 *
 * The resources are mapped from the internal {@link BlogsEntry} model.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class BlogPostingResource implements Resource<BlogsEntry> {

	@Override
	public void buildRepresentor(
		RepresentorBuilder<BlogsEntry> representorBuilder) {

		Function<Date, Object> formatFunction = date -> {
			if (date == null) {
				return null;
			}

			DateFormat dateFormat = DateUtil.getISO8601Format();

			return dateFormat.format(date);
		};

		representorBuilder.identifier(
			blogsEntry -> String.valueOf(blogsEntry.getEntryId())
		).addBidirectionalModel(
			"group", "blogs", Group.class, this::_getGroupOptional,
			this::_getGroupIdFilter
		).addEmbeddedModel(
			"aggregateRating", AggregateRating.class,
			this::_getAggregateRatingOptional
		).addEmbeddedModel(
			"creator", User.class, this::_getUserOptional
		).addField(
			"alternativeHeadline", BlogsEntry::getSubtitle
		).addField(
			"articleBody", BlogsEntry::getContent
		).addField(
			"createDate",
			blogsEntry -> formatFunction.apply(blogsEntry.getCreateDate())
		).addField(
			"fileFormat", blogsEntry -> "text/html"
		).addField(
			"headline", BlogsEntry::getTitle
		).addField(
			"modifiedDate",
			blogsEntry -> formatFunction.apply(blogsEntry.getModifiedDate())
		).addField(
			"publishedDate",
			blogsEntry -> formatFunction.apply(blogsEntry.getLastPublishDate())
		).addLinkedModel(
			"author", User.class, this::_getUserOptional
		).addLink(
			"license", "https://creativecommons.org/licenses/by/4.0"
		).addRelatedCollection(
			"comment", Comment.class, this::_getClassNameClassPKFilter
		).addType(
			"BlogPosting"
		);
	}

	@Override
	public String getPath() {
		return "blogs";
	}

	@Override
	public Routes<BlogsEntry> routes(RoutesBuilder<BlogsEntry> routesBuilder) {
		return routesBuilder.filteredCollectionPage(
			this::_getPageItems, GroupIdFilter.class
		).collectionItem(
			this::_getBlogsEntry, Long.class
		).build();
	}

	private Optional<AggregateRating> _getAggregateRatingOptional(
		BlogsEntry blogsEntry) {

		ClassNameClassPKIdentifier identifier = new ClassNameClassPKIdentifier(
			BlogsEntry.class.getName(), blogsEntry.getEntryId());

		return Optional.of(
			_aggregateRatingService.getAggregateRating(identifier));
	}

	private BlogsEntry _getBlogsEntry(Long id) {
		try {
			return _blogsService.getEntry(id);
		}
		catch (NoSuchEntryException | PrincipalException e) {
			throw new NotFoundException(e);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private QueryParamFilterType _getClassNameClassPKFilter(
		BlogsEntry blogsEntry) {

		String className = BlogsEntry.class.getName();

		return _classNameClassPKFilterProvider.create(
			className, blogsEntry.getEntryId());
	}

	private GroupIdFilter _getGroupIdFilter(Group group) {
		return _groupIdFilterProvider.create(group.getGroupId());
	}

	private Optional<Group> _getGroupOptional(BlogsEntry blogsEntry) {
		try {
			return Optional.of(
				_groupLocalService.getGroup(blogsEntry.getGroupId()));
		}
		catch (NoSuchGroupException nsge) {
			throw new NotFoundException(nsge);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<BlogsEntry> _getPageItems(
		GroupIdFilter groupIdFilter, Pagination pagination) {

		Long groupId = groupIdFilter.getId();

		List<BlogsEntry> blogsEntries = _blogsService.getGroupEntries(
			groupId, 0, pagination.getStartPosition(),
			pagination.getEndPosition());
		int count = _blogsService.getGroupEntriesCount(groupId, 0);

		return new PageItems<>(blogsEntries, count);
	}

	private Optional<User> _getUserOptional(BlogsEntry blogsEntry) {
		try {
			return Optional.ofNullable(
				_userService.getUserById(blogsEntry.getUserId()));
		}
		catch (NoSuchUserException | PrincipalException e) {
			throw new NotFoundException(e);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	@Reference
	private AggregateRatingService _aggregateRatingService;

	@Reference
	private BlogsEntryService _blogsService;

	@Reference
	private ClassNameClassPKFilterProvider _classNameClassPKFilterProvider;

	@Reference
	private GroupIdFilterProvider _groupIdFilterProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private UserService _userService;

}