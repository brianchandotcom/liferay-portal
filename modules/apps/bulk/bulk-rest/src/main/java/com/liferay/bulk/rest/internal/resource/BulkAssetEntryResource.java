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

package com.liferay.bulk.rest.internal.resource;

import com.liferay.bulk.rest.internal.model.BulkActionResponseModel;
import com.liferay.bulk.rest.internal.model.BulkAssetEntryCommonTagsModel;
import com.liferay.bulk.rest.internal.model.BulkAssetEntryUpdateTagsActionModel;
import com.liferay.portal.kernel.util.ContentTypes;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=bulk-application)",
		"osgi.jaxrs.resource=true"
	},
	service = BulkAssetEntryResource.class
)
@Path("/asset")
public class BulkAssetEntryResource {

	@Consumes(ContentTypes.APPLICATION_JSON)
	@Path("/tags/{classNameId}/common")
	@POST
	@Produces(ContentTypes.APPLICATION_JSON)
	public BulkAssetEntryCommonTagsModel getAssetEntryCommonTags(
		@PathParam("classNameId") long classNameId,
		BulkAssetEntryUpdateTagsActionModel
			bulkAssetEntryUpdateTagsActionModel) {

		return new BulkAssetEntryCommonTagsModel();
	}

	@Consumes(ContentTypes.APPLICATION_JSON)
	@Path("/tags/{classNameId}")
	@POST
	@Produces(ContentTypes.APPLICATION_JSON)
	public BulkActionResponseModel updateAssetEntryTags(
		@PathParam("classNameId") long classNameId,
		BulkAssetEntryUpdateTagsActionModel
			bulkAssetEntryUpdateTagsActionModel) {

		return new BulkActionResponseModel();
	}

}