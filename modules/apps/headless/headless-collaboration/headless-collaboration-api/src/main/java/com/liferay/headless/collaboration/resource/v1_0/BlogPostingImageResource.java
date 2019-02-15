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

package com.liferay.headless.collaboration.resource.v1_0;

import com.liferay.headless.collaboration.dto.v1_0.BlogPostingImage;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-collaboration/v1.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public interface BlogPostingImageResource {

	@GET
	@Path("/content-spaces/{content-space-id}/blog-posting-images")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<BlogPostingImage> getContentSpaceBlogPostingImagesPage( @PathParam("content-space-id") Long contentSpaceId , @Context Pagination pagination ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/content-spaces/{content-space-id}/blog-posting-images")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public BlogPostingImage postContentSpaceBlogPostingImage( @PathParam("content-space-id") Long contentSpaceId , BlogPostingImage blogPostingImage ) throws Exception;

	@DELETE
	@Path("/blog-posting-images/{image-object-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Response deleteImageObject( @PathParam("image-object-id") Long imageObjectId ) throws Exception;

	@GET
	@Path("/blog-posting-images/{image-object-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public BlogPostingImage getImageObject( @PathParam("image-object-id") Long imageObjectId ) throws Exception;

}