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
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import javax.annotation.Generated;

import javax.ws.rs.GET;
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
public interface AggregateRatingResource {

	@GET
	@Path("/aggregate-rating/{id}")
	@Produces({"*/*"})
	@RequiresScope("headless-collaboration-application.read")
	public AggregateRating getAggregateRating(@PathParam("id") Integer id)
		throws Exception;

	@GET
	@Path("/blog-posting/{parent-id}/comment")
	@Produces({"*/*"})
	@RequiresScope("headless-collaboration-application.read")
	public Page<Comment> getBlogPostingCommentPage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/comment/{parent-id}/comment")
	@Produces({"*/*"})
	@RequiresScope("headless-collaboration-application.read")
	public Page<Comment> getCommentCommentPage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/content-space/{parent-id}/blog-posting")
	@Produces({"*/*"})
	@RequiresScope("headless-collaboration-application.read")
	public Page<BlogPosting> getContentSpaceBlogPostingPage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

	@GET
	@Path("/image-object-repository/{parent-id}/image-object")
	@Produces({"*/*"})
	@RequiresScope("headless-collaboration-application.read")
	public Page<ImageObject> getImageObjectRepositoryImageObjectPage(
			@PathParam("parent-id") Integer parentId,
			@Context Pagination pagination)
		throws Exception;

}