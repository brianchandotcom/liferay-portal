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
import com.liferay.headless.form.internal.dto.v1_0.FormImpl;
import com.liferay.headless.form.resource.v1_0.FormResource;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseFormResourceImpl implements FormResource {

	@Override
	@GET
	@Path("/content-spaces/{content-space-id}/form")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Page<Form> getContentSpaceFormsPage(
	@PathParam("content-space-id") Long contentSpaceId,@Context Pagination pagination)
			throws Exception {

				return Page.of(Collections.emptyList());
	}
	@Override
	@GET
	@Path("/forms/{form-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Form getForm(
	@PathParam("form-id") Long formId)
			throws Exception {

				return new FormImpl();
	}
	@Override
	@Consumes("application/json")
	@POST
	@Path("/forms/{form-id}/evaluate-context")
	@Produces("application/json")
	@RequiresScope("everything.write")
	public Form postFormEvaluateContext(
	@PathParam("form-id") Long formId,Form form)
			throws Exception {

				return new FormImpl();
	}
	@Override
	@GET
	@Path("/forms/{form-id}/fetch-latest-draft")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public Form getFormFetchLatestDraft(
	@PathParam("form-id") Long formId)
			throws Exception {

				return new FormImpl();
	}
	@Override
	@Consumes("application/json")
	@POST
	@Path("/forms/{form-id}/upload-file")
	@Produces("application/json")
	@RequiresScope("everything.write")
	public Form postFormUploadFile(
	@PathParam("form-id") Long formId,Form form)
			throws Exception {

				return new FormImpl();
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected <T, R> List<R> transform(List<T> list, UnsafeFunction<T, R, Throwable> unsafeFunction) {
		return TransformUtil.transform(list, unsafeFunction);
	}

	@Context
	protected AcceptLanguage contextAcceptLanguage;

	@Context
	protected Company contextCompany;

}