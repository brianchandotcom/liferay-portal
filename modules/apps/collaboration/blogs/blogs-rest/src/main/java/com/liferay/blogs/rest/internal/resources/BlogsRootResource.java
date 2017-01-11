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

import com.liferay.blogs.exception.NoSuchEntryException;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.portal.kernel.exception.PortalException;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true, service = BlogsRootResource.class)
@Path("/")
public class BlogsRootResource {

	@Path("/{entryId}")
	public BlogsEntryResource getEntryResource(
			@PathParam("entryId") long entryId)
		throws PortalException {

		BlogsEntry blogsEntry = null;

		try {
			blogsEntry = _blogsEntryService.getEntry(entryId);
		}
		catch (NoSuchEntryException nsee) {
			throw new NotFoundException();
		}

		return new BlogsEntryResource(blogsEntry);
	}

	@Reference
	private BlogsEntryService _blogsEntryService;

}