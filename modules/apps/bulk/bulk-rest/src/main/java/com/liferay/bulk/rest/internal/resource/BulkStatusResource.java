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

import com.liferay.bulk.rest.internal.model.BulkStatusModel;
import com.liferay.bulk.selection.BulkSelectionRunner;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ContentTypes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=bulk-application)",
		"osgi.jaxrs.resource=true"
	},
	service = BulkStatusResource.class
)
public class BulkStatusResource {

	@GET
	@Path("/status")
	@Produces(ContentTypes.APPLICATION_JSON)
	public BulkStatusModel getBulkStatus(@Context User user) {
		return new BulkStatusModel(
			_bulkSelectionRunner.isBusy(user.getUserId()));
	}

	@Reference
	private BulkSelectionRunner _bulkSelectionRunner;

}