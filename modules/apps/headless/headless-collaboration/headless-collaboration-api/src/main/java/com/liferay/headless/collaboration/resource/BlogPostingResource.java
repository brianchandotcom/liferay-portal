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

import com.liferay.headless.collaboration.dto.AggregateRating;
import com.liferay.headless.collaboration.dto.BlogPosting;
import com.liferay.headless.collaboration.dto.Comment;
import com.liferay.headless.collaboration.dto.ImageObject;
import com.liferay.headless.collaboration.dto.ImageObjectRepository;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import java.util.Date;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

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

	@DELETE
	@Path("/blog-posting/{blog-posting-id}")
	@Produces("application/json")
	@RequiresScope("headless-collaboration-application.read")
	public Response deleteBlogPosting( @PathParam("blog-posting-id") Long blogPostingId ) throws Exception;

	@GET
	@Path("/blog-posting/{blog-posting-id}")
	@Produces("application/json")
	@RequiresScope("headless-collaboration-application.read")
	public BlogPosting getBlogPosting( @PathParam("blog-posting-id") Long blogPostingId ) throws Exception;

	@Consumes("application/json")
	@PUT
	@Path("/blog-posting/{blog-posting-id}")
	@Produces("application/json")
	@RequiresScope("headless-collaboration-application.read")
	public BlogPosting putBlogPosting( @PathParam("blog-posting-id") Long blogPostingId ) throws Exception;

	@GET
	@Path("/content-space/{content-space-id}/blog-posting")
	@Produces("application/json")
	@RequiresScope("headless-collaboration-application.read")
	public Page<BlogPosting> getContentSpaceBlogPostingPage( @PathParam("content-space-id") Long contentSpaceId , @Context Pagination pagination ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/content-space/{content-space-id}/blog-posting")
	@Produces("application/json")
	@RequiresScope("headless-collaboration-application.read")
	public BlogPosting postContentSpaceBlogPosting( @PathParam("content-space-id") Long contentSpaceId , BlogPosting blogPosting ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/content-space/{content-space-id}/blog-posting/batch-create")
	@Produces("application/json")
	@RequiresScope("headless-collaboration-application.write")
	public BlogPosting postContentSpaceBlogPostingBatchCreate( @PathParam("content-space-id") Long contentSpaceId ) throws Exception;

}