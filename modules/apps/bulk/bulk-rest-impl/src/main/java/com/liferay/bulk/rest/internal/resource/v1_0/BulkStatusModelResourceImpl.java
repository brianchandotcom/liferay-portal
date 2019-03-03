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

import com.liferay.bulk.rest.dto.v1_0.BulkStatusModel;
import com.liferay.bulk.rest.resource.v1_0.BulkStatusModelResource;
import com.liferay.bulk.selection.BulkSelectionRunner;
import com.liferay.portal.kernel.model.User;

import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/Bulk-status-model.properties",
	scope = ServiceScope.PROTOTYPE, service = BulkStatusModelResource.class
)
public class BulkStatusModelResourceImpl
	extends BaseBulkStatusModelResourceImpl {

	@Override
	public BulkStatusModel getStatus(Long param) throws Exception {
		BulkStatusModel bulkStatusModel = new BulkStatusModel();

		bulkStatusModel.setStatus(_bulkSelectionRunner.isBusy(_user));

		return bulkStatusModel;
	}

	@Reference
	private BulkSelectionRunner _bulkSelectionRunner;

	@Context
	private User _user;

}