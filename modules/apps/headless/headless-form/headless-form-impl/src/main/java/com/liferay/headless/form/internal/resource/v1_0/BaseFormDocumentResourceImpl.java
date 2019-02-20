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

import com.liferay.headless.form.dto.v1_0.FormDocument;
import com.liferay.headless.form.internal.dto.v1_0.FormDocumentImpl;
import com.liferay.headless.form.resource.v1_0.FormDocumentResource;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
public abstract class BaseFormDocumentResourceImpl implements FormDocumentResource {

	@Override
	@DELETE
	@Path("/form-documents/{form-document-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public boolean deleteFormDocument(
	@PathParam("form-document-id") Long formDocumentId)
			throws Exception {

				return false;
	}
	@Override
	@GET
	@Path("/form-documents/{form-document-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	public FormDocument getFormDocument(
	@PathParam("form-document-id") Long formDocumentId)
			throws Exception {

				return new FormDocumentImpl();
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