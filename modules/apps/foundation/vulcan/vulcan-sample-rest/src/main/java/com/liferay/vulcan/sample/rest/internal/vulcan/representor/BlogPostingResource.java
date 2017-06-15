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

package com.liferay.vulcan.sample.rest.internal.vulcan.representor;

import com.liferay.blogs.kernel.exception.NoSuchEntryException;
import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.kernel.service.BlogsEntryService;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.vulcan.liferay.context.CurrentGroup;
import com.liferay.vulcan.liferay.scope.GroupScoped;
import com.liferay.vulcan.pagination.PageItems;
import com.liferay.vulcan.pagination.Pagination;
import com.liferay.vulcan.representor.Resource;
import com.liferay.vulcan.representor.Routes;
import com.liferay.vulcan.representor.RoutesBuilder;
import com.liferay.vulcan.representor.builder.RepresentorBuilder;

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
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(
	immediate = true, service = {BlogPostingResource.class, Resource.class}
)
public class BlogPostingResource
	implements GroupScoped<BlogsEntry>, Resource<BlogsEntry> {

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
		).addType(
			"BlogPosting"
		);
	}

	@Override
	public long getGroupId(BlogsEntry blogsEntry) {
		return blogsEntry.getGroupId();
	}

	@Override
	public String getPath() {
		return "blogs";
	}

	@Override
	public Routes<BlogsEntry> routes(RoutesBuilder<BlogsEntry> routesBuilder) {
		return routesBuilder.collectionPage(
			this::_getPageItems, CurrentGroup.class
		).collectionItem(
			this::_getBlogsEntry, Long.class
		);
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

	private PageItems<BlogsEntry> _getPageItems(
		Pagination pagination, CurrentGroup currentGroup) {

		Group group = currentGroup.getGroup();

		List<BlogsEntry> blogsEntries = _blogsService.getGroupEntries(
			group.getGroupId(), 0, pagination.getStartPosition(),
			pagination.getEndPosition());

		int count = _blogsService.getGroupEntriesCount(group.getGroupId(), 0);

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
	private BlogsEntryService _blogsService;

	@Reference
	private UserService _userService;

}