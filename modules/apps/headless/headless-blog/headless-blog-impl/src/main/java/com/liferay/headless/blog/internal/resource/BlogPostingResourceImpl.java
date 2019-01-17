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

package com.liferay.headless.blog.internal.resource;

import com.liferay.apio.architect.annotation.Actions;
import com.liferay.apio.architect.annotation.Body;
import com.liferay.apio.architect.annotation.Id;
import com.liferay.apio.architect.annotation.ParentId;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.ActionRouter;
import com.liferay.content.space.apio.architect.model.ContentSpace;
import com.liferay.headless.blog.api.dto.BlogPosting;
import com.liferay.headless.blog.api.resource.BlogPostingResource;
import com.liferay.portal.apio.user.CurrentUser;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;

/**
 * Provides the information necessary to expose <a
 * href="http://schema.org/BlogPosting">BlogPosting </a> resources through a web
 * API. The resources are mapped from the internal model {@code BlogsEntry}.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = ActionRouter.class)
public class BlogPostingResourceImpl implements BlogPostingResource {

	@Actions.Create
	@Override
	public BlogPosting createBlogPosting(
			@ParentId(ContentSpace.class) long groupId,
			@Body BlogPosting blogPosting, CurrentUser currentUser)
		throws PortalException {

		return null;
	}

	@Actions.Remove
	@Override
	public void deleteBlogPosting(@Id long blogPostingId)
		throws PortalException {

		return null;
	}

	@Actions.Retrieve
	@Override
	public PageItems<BlogPosting> getPageItems(
		Pagination pagination,
		@ParentId(ContentSpace.class) long contentSpaceId) {

		return null;
	}

	@Actions.Replace
	@Override
	public BlogPosting replaceBlogPosting(
			@ParentId(ContentSpace.class) long blogsEntryId,
			@Body BlogPosting blogPosting, CurrentUser currentUser)
		throws PortalException {

		return null;
	}

}