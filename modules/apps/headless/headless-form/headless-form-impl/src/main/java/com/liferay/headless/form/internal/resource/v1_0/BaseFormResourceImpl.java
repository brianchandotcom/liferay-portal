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

package com.liferay.headless.form.internal.resource.v1_0;

import com.liferay.headless.form.dto.v1_0.Form;
import com.liferay.headless.form.resource.v1_0.FormResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseFormResourceImpl implements FormResource {

	@Override
	@GET
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.QUERY, name = "page"),
			@Parameter(in = ParameterIn.QUERY, name = "pageSize")
		}
	)
	@Path("/content-spaces/{content-space-id}/forms")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Form")})
	public Page<Form> getContentSpaceFormsPage(
			@NotNull @PathParam("content-space-id") Long contentSpaceId,
			@Context Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@GET
	@Path("/forms/{form-id}")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Form")})
	public Form getForm(@NotNull @PathParam("form-id") Long formId)
		throws Exception {

		return new Form();
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path("/forms/{form-id}/evaluate-context")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Form")})
	public Form postFormEvaluateContext(
			@NotNull @PathParam("form-id") Long formId, Form form)
		throws Exception {

		return new Form();
	}

	@Override
	@GET
	@Path("/forms/{form-id}/fetch-latest-draft")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Form")})
	public Form getFormFetchLatestDraft(
			@NotNull @PathParam("form-id") Long formId)
		throws Exception {

		return new Form();
	}

	@Override
	@Consumes("application/json")
	@POST
	@Path("/forms/{form-id}/upload-file")
	@Produces("application/json")
	@Tags(value = {@Tag(name = "Form")})
	public Form postFormUploadFile(
			@NotNull @PathParam("form-id") Long formId, Form form)
		throws Exception {

		return new Form();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(Form form) {
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