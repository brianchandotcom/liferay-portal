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

import com.liferay.headless.foundation.dto.v1_0.TaxonomyCategory;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import javax.annotation.Generated;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

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
public interface TaxonomyCategoryResource {

	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sorts")
		}
	)
	@Path("/taxonomy-categories/{parentTaxonomyCategoryId}/taxonomy-categories")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public Page<TaxonomyCategory> getTaxonomyCategoryTaxonomyCategoriesPage(
			@NotNull @PathParam("parentTaxonomyCategoryId") Long
				parentTaxonomyCategoryId,
			@QueryParam("search") String search, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/taxonomy-categories/{parentTaxonomyCategoryId}/taxonomy-categories")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory postTaxonomyCategoryTaxonomyCategory(
			@NotNull @PathParam("parentTaxonomyCategoryId") Long
				parentTaxonomyCategoryId,
			TaxonomyCategory taxonomyCategory)
		throws Exception;

	@DELETE
	@Path("/taxonomy-categories/{taxonomyCategoryId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public void deleteTaxonomyCategory(
			@NotNull @PathParam("taxonomyCategoryId") Long taxonomyCategoryId)
		throws Exception;

	@GET
	@Path("/taxonomy-categories/{taxonomyCategoryId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory getTaxonomyCategory(
			@NotNull @PathParam("taxonomyCategoryId") Long taxonomyCategoryId)
		throws Exception;

	@Consumes("application/json")
	@PATCH
	@Path("/taxonomy-categories/{taxonomyCategoryId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory patchTaxonomyCategory(
			@NotNull @PathParam("taxonomyCategoryId") Long taxonomyCategoryId,
			TaxonomyCategory taxonomyCategory)
		throws Exception;

	@Consumes("application/json")
	@PUT
	@Path("/taxonomy-categories/{taxonomyCategoryId}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory putTaxonomyCategory(
			@NotNull @PathParam("taxonomyCategoryId") Long taxonomyCategoryId,
			TaxonomyCategory taxonomyCategory)
		throws Exception;

	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "filter"),
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize"),
			@Parameter(in = ParameterIn.QUERY, name = "sorts")
		}
	)
	@Path("/taxonomy-vocabularies/{taxonomyVocabularyId}/taxonomy-categories")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public Page<TaxonomyCategory> getTaxonomyVocabularyTaxonomyCategoriesPage(
			@NotNull @PathParam("taxonomyVocabularyId") Long
				taxonomyVocabularyId,
			@QueryParam("search") String search, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts)
		throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/taxonomy-vocabularies/{taxonomyVocabularyId}/taxonomy-categories")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "TaxonomyCategory")})
	public TaxonomyCategory postTaxonomyVocabularyTaxonomyCategory(
			@NotNull @PathParam("taxonomyVocabularyId") Long
				taxonomyVocabularyId,
			TaxonomyCategory taxonomyCategory)
		throws Exception;

	@Context
	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage);

	@Context
	public void setContextCompany(Company contextCompany);

	@Context
	public void setContextUriInfo(UriInfo contextUriInfo);

}