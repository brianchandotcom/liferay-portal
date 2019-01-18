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

import com.liferay.apio.architect.annotation.Body;
import com.liferay.apio.architect.annotation.Id;
import com.liferay.apio.architect.annotation.ParentId;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.ActionRouter;
import com.liferay.headless.blog.dto.BlogPosting;
import com.liferay.headless.blog.resource.BlogPostingResource;
import com.liferay.headless.content.space.dto.ContentSpace;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = ActionRouter.class)
public class BlogPostingResourceImpl implements BlogPostingResource {

	@Override
	public BlogPosting createBlogPosting(
			@ParentId(ContentSpace.class) long groupId,
			@Body BlogPosting blogPosting)
		throws PortalException {

		return null;
	}

	@Override
	public void deleteBlogPosting(@Id long blogPostingId)
		throws PortalException {
	}

	@Override
	public BlogPosting getBlogPosting(long blogPostingId)
		throws PortalException {

		return null;
	}

	@Override
	public PageItems<BlogPosting> getPageItems(
		Pagination pagination,
		@ParentId(ContentSpace.class) long contentSpaceId) {

		return new PageItems<>(Collections.emptyList(), 0);
	}

	@Override
	public BlogPosting replaceBlogPosting(
			@ParentId(ContentSpace.class) long blogsEntryId,
			@Body BlogPosting blogPosting)
		throws PortalException {

		return null;
	}

}