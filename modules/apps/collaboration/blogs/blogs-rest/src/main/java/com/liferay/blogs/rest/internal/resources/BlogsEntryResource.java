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

package com.liferay.blogs.rest.internal.resources;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.rest.internal.repr.BlogsEntryRepr;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author Alejandro Hernández
 */
public class BlogsEntryResource {

	public BlogsEntryResource(BlogsEntry entry) {
		_entry = entry;
	}

	@GET
	@Path("/")
	@Produces("application/json")
	public BlogsEntryRepr getEntry() {
		return new BlogsEntryRepr(_entry);
	}

	private final BlogsEntry _entry;

}