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

import com.liferay.headless.form.dto.v1_0.FormRecord;
import com.liferay.headless.form.internal.dto.v1_0.FormRecordImpl;
import com.liferay.headless.form.resource.v1_0.FormRecordResource;
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
import javax.ws.rs.PUT;
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
public abstract class BaseFormRecordResourceImpl implements FormRecordResource {

	@GET
	@Path("/form-records/{form-record-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	@Override
	public FormRecord getFormRecord( @PathParam("form-record-id") Long formRecordId ) throws Exception {
			return new FormRecordImpl();

	}
	@Consumes("application/json")
	@PUT
	@Path("/form-records/{form-record-id}")
	@Produces("application/json")
	@RequiresScope("everything.read")
	@Override
	public FormRecord putFormRecord( @PathParam("form-record-id") Long formRecordId , FormRecord formRecord ) throws Exception {
			return new FormRecordImpl();

	}
	@GET
	@Path("/forms/{form-id}/form-records")
	@Produces("application/json")
	@RequiresScope("everything.read")
	@Override
	public Page<FormRecord> getFormFormRecordsPage( @PathParam("form-id") Long formId , @Context Pagination pagination ) throws Exception {
			return Page.of(Collections.emptyList());

	}
	@Consumes("application/json")
	@POST
	@Path("/forms/{form-id}/form-records")
	@Produces("application/json")
	@RequiresScope("everything.read")
	@Override
	public FormRecord postFormFormRecord( @PathParam("form-id") Long formId , FormRecord formRecord ) throws Exception {
			return new FormRecordImpl();

	}

	protected <T, R> List<R> transform(List<T> list, UnsafeFunction<T, R, Throwable> unsafeFunction) {
		return TransformUtil.transform(list, unsafeFunction);
	}

	@Context
	protected AcceptLanguage acceptLanguage;

	@Context
	protected Company company;

}