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

package com.liferay.batch.engine.rest.internal.resource;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.batch.engine.BatchFileImportOperation;
import com.liferay.batch.engine.fileimport.BatchFileImportHelper;
import com.liferay.batch.engine.model.BatchFileImport;
import com.liferay.batch.engine.service.BatchFileImportLocalService;
import com.liferay.oauth2.provider.scope.RequiresScope;

import java.util.HashMap;
import java.util.Map;

import javax.activation.DataHandler;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Ivica Cardic
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Batch)",
		"osgi.jaxrs.resource=true"
	},
	scope = ServiceScope.PROTOTYPE, service = BatchFileImportResource.class
)
@Path("/file-import")
public class BatchFileImportResource {

	@DELETE
	@Path("/{version}/{domainName}")
	@RequiresScope("Batch.write")
	public Response delete(
			@PathParam("domainName") String domainName,
			@PathParam("version") String version, MultipartBody multipartBody,
			@QueryParam("callbackURL") String callbackURL)
		throws Exception {

		return _process(
			multipartBody.getRootAttachment(), domainName, version,
			BatchFileImportOperation.DELETE, callbackURL);
	}

	@GET
	@Path("/{batchFileImportId}")
	@RequiresScope("Batch.read")
	public Response getBatchExecutionStatus(
		@PathParam("batchFileImportId") long batchFileImportId) {

		Response.ResponseBuilder responseBuilder = Response.ok();

		BatchFileImport batchFileImport =
			_batchFileImportLocalService.fetchBatchFileImport(
				batchFileImportId);

		responseBuilder.entity(batchFileImport.getStatus());

		return responseBuilder.build();
	}

	@Path("/{version}/{domainName}")
	@POST
	@RequiresScope("Batch.write")
	public Response post(
			@PathParam("domainName") String domainName,
			@PathParam("version") String version, MultipartBody multipartBody,
			@QueryParam("callbackURL") String callbackURL)
		throws Exception {

		return _process(
			multipartBody.getRootAttachment(), domainName, version,
			BatchFileImportOperation.CREATE, callbackURL);
	}

	@Path("/{version}/{domainName}")
	@PUT
	@RequiresScope("Batch.write")
	public Response put(
			@PathParam("domainName") String domainName,
			@PathParam("version") String version, MultipartBody multipartBody,
			@QueryParam("callbackURL") String callbackURL)
		throws Exception {

		return _process(
			multipartBody.getRootAttachment(), domainName, version,
			BatchFileImportOperation.UPDATE, callbackURL);
	}

	private Response _process(
			Attachment attachment, String domainName, String version,
			BatchFileImportOperation operation, String callbackURL)
		throws Exception {

		DataHandler dataHandler = attachment.getDataHandler();

		BatchFileImport batchFileImport = _batchFileImportHelper.queue(
			dataHandler.getName(), dataHandler.getInputStream(), domainName,
			version, operation, callbackURL, null);

		Response.ResponseBuilder responseBuilder = Response.accepted();

		Map<Long, String> map = new HashMap<>();

		map.put(batchFileImport.getBatchFileImportId(), dataHandler.getName());

		responseBuilder.entity(_OBJECT_MAPPER.writeValueAsString(map));

		return responseBuilder.build();
	}

	private static final ObjectMapper _OBJECT_MAPPER = new ObjectMapper();

	@Reference
	private BatchFileImportHelper _batchFileImportHelper;

	@Reference
	private BatchFileImportLocalService _batchFileImportLocalService;

}