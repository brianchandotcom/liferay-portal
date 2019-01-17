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

package com.liferay.headless.blog.api.resource;

import com.liferay.apio.architect.annotation.Actions;
import com.liferay.apio.architect.annotation.Body;
import com.liferay.apio.architect.annotation.Id;
import com.liferay.apio.architect.annotation.ParentId;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.content.space.apio.architect.model.ContentSpace;
import com.liferay.headless.blog.api.dto.BlogPosting;
import com.liferay.portal.apio.user.CurrentUser;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Víctor Galán
 */
public interface BlogPostingResource {

	@Actions.Create
	public BlogPosting createBlogPosting(
			@ParentId(ContentSpace.class) long groupId,
			@Body BlogPosting blogPosting, CurrentUser currentUser)
		throws PortalException;

	@Actions.Remove
	public void deleteBlogPosting(@Id long blogPostingId)
		throws PortalException;

	@Actions.Retrieve
	public PageItems<BlogPosting> getPageItems(
		Pagination pagination,
		@ParentId(ContentSpace.class) long contentSpaceId);

	@Actions.Replace
	public BlogPosting replaceBlogPosting(
			@ParentId(ContentSpace.class) long blogsEntryId,
			@Body BlogPosting blogPosting, CurrentUser currentUser)
		throws PortalException;

}