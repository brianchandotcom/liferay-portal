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

package com.liferay.headless.collaboration.resource;

import com.liferay.headless.collaboration.dto.BlogPosting;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-collaboration/1.0.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/1.0.0")
public interface BlogPostingResource {

	@GET
	@Path("/blog-posting/{id}")
	@Produces({"*/*"})
	@RequiresScope("headless-collaboration-application.read")
	public BlogPosting getBlogPosting(@PathParam("id") Integer id)
		throws Exception;

	@GET
	@Path("/content-space/{parent-id}/blog-posting")
	@Produces({"*/*"})
	@RequiresScope("headless-collaboration-application.read")
	public Page<BlogPosting> getContentSpaceBlogPostingPage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@Consumes({"*/*"})
	@Path("/content-space/{parent-id}/blog-posting")
	@POST
	@Produces({"*/*"})
	@RequiresScope("headless-collaboration-application.read")
	public BlogPosting postContentSpaceBlogPosting(
			@PathParam("parent-id") Integer parentId)
		throws Exception;

	@Consumes({"*/*"})
	@Path("/content-space/{parent-id}/blog-posting/batch-create")
	@POST
	@Produces({"*/*"})
	@RequiresScope("headless-collaboration-application.write")
	public BlogPosting postContentSpaceBlogPostingBatchCreate(
			@PathParam("parent-id") Integer parentId)
		throws Exception;

	@Consumes({"*/*"})
	@Path("/blog-posting/{id}")
	@Produces({"*/*"})
	@PUT
	@RequiresScope("headless-collaboration-application.read")
	public BlogPosting putBlogPosting(@PathParam("id") Integer id)
		throws Exception;

}