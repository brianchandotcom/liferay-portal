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

package com.liferay.blogs.rest.internal.resource;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.rest.internal.model.BlogsEntryModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author Alejandro Hernández
 */
public class BlogsEntryResource {

	public BlogsEntryResource(BlogsEntry blogsEntry) {
		_blogsEntry = blogsEntry;
	}

	@GET
	@Path("/")
	@Produces("application/json")
	public BlogsEntryModel getBlogsEntryModel() {
		return new BlogsEntryModel(_blogsEntry);
	}

	private final BlogsEntry _blogsEntry;

}