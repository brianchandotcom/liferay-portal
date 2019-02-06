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

package com.liferay.headless.form.resource;

import com.liferay.headless.form.dto.Creator;
import com.liferay.headless.form.dto.Form;
import com.liferay.headless.form.dto.FormDocument;
import com.liferay.headless.form.dto.FormRecord;
import com.liferay.headless.form.dto.FormStructure;
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
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-form/1.0.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@Path("/1.0.0")
public interface FormRecordResource {

	@GET
	@Path("/form-record/{form-record-id}")
	@Produces("application/json")
	@RequiresScope("headless-form-application.read")
	public FormRecord getFormRecord( @PathParam("form-record-id") Long formRecordId ) throws Exception;

	@Consumes("application/json")
	@PUT
	@Path("/form-record/{form-record-id}")
	@Produces("application/json")
	@RequiresScope("headless-form-application.read")
	public FormRecord putFormRecord( @PathParam("form-record-id") Long formRecordId ) throws Exception;

	@GET
	@Path("/form/{form-id}/form-record")
	@Produces("application/json")
	@RequiresScope("headless-form-application.read")
	public Page<FormRecord> getFormFormRecordPage( @PathParam("form-id") Long formId , @Context Pagination pagination ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/form/{form-id}/form-record")
	@Produces("application/json")
	@RequiresScope("headless-form-application.read")
	public FormRecord postFormFormRecord( @PathParam("form-id") Long formId ) throws Exception;

	@Consumes("application/json")
	@POST
	@Path("/form/{form-id}/form-record/batch-create")
	@Produces("application/json")
	@RequiresScope("headless-form-application.write")
	public FormRecord postFormFormRecordBatchCreate( @PathParam("form-id") Long formId ) throws Exception;

}