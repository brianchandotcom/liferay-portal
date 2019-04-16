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

package com.liferay.bulk.rest.internal.resource.v1_0;

import com.liferay.bulk.rest.dto.v1_0.DocumentBulkSelection;
import com.liferay.bulk.rest.dto.v1_0.Keyword;
import com.liferay.bulk.rest.dto.v1_0.KeywordBulkSelection;
import com.liferay.bulk.rest.resource.v1_0.KeywordResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseKeywordResourceImpl implements KeywordResource {

	@Override
	@Consumes("application/json")
	@PATCH
	@Path("/keywords/batch")
	@Tags(value = {@Tag(name = "Keyword")})
	public void patchKeywordBatch(KeywordBulkSelection keywordBulkSelection)
		throws Exception {
	}

	@Override
	@Consumes("application/json")
	@PUT
	@Path("/keywords/batch")
	@Tags(value = {@Tag(name = "Keyword")})
	public void putKeywordBatch(KeywordBulkSelection keywordBulkSelection)
		throws Exception {
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path("/keywords/common")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Keyword")})
	public Page<Keyword> postKeywordsCommonPage(
			DocumentBulkSelection documentBulkSelection)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(Keyword keyword, Keyword existingKeyword) {
	}

	protected <T, R> List<R> transform(
		Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	@Context
	protected AcceptLanguage contextAcceptLanguage;

	@Context
	protected Company contextCompany;

	@Context
	protected UriInfo contextUriInfo;

}