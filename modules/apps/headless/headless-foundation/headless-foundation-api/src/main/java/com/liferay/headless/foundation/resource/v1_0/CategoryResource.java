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

package com.liferay.headless.foundation.resource.v1_0;

import com.liferay.headless.foundation.dto.v1_0.Category;
import com.liferay.headless.foundation.dto.v1_0.Email;
import com.liferay.headless.foundation.dto.v1_0.Keyword;
import com.liferay.headless.foundation.dto.v1_0.Organization;
import com.liferay.headless.foundation.dto.v1_0.Phone;
import com.liferay.headless.foundation.dto.v1_0.PostalAddress;
import com.liferay.headless.foundation.dto.v1_0.Role;
import com.liferay.headless.foundation.dto.v1_0.UserAccount;
import com.liferay.headless.foundation.dto.v1_0.Vocabulary;
import com.liferay.headless.foundation.dto.v1_0.WebUrl;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Date;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PATCH;
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
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-foundation/v1.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public interface CategoryResource {

	@DELETE
	@Path("/categories/{category-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Response deleteCategories( @PathParam("category-id") Long categoryId ) throws Exception;

	@GET
	@Path("/categories/{category-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Category getCategories( @PathParam("category-id") Long categoryId ) throws Exception;

	@Consumes("application/json")
	@PUT
	@Path("/categories/{category-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Category putCategories( @PathParam("category-id") Long categoryId , Category category ) throws Exception;

	@GET
	@Path("/categories/{category-id}/categories")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<Category> getCategoriesCategoriesPage( @PathParam("category-id") Long categoryId , @Context Pagination pagination ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/categories/{category-id}/categories")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Category postCategoriesCategories( @PathParam("category-id") Long categoryId , Category category ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/categories/{category-id}/categories/batch-create")
	@Produces("application/json")
	@RequiresScope("everything.write")
	public Category postCategoriesCategoriesBatchCreate( @PathParam("category-id") Long categoryId , Category category ) throws Exception;

	@GET
	@Path("/vocabularies/{vocabulary-id}/categories")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<Category> getVocabulariesCategoriesPage( @PathParam("vocabulary-id") Long vocabularyId , @Context Pagination pagination ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/vocabularies/{vocabulary-id}/categories")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Category postVocabulariesCategories( @PathParam("vocabulary-id") Long vocabularyId , Category category ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/vocabularies/{vocabulary-id}/categories/batch-create")
	@Produces("application/json")
	@RequiresScope("everything.write")
	public Category postVocabulariesCategoriesBatchCreate( @PathParam("vocabulary-id") Long vocabularyId , Category category ) throws Exception;

}