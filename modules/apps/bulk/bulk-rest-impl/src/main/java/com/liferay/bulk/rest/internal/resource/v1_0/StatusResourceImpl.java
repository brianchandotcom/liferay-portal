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

import com.liferay.bulk.rest.dto.v1_0.Status;
import com.liferay.bulk.rest.resource.v1_0.StatusResource;
import com.liferay.bulk.selection.BulkSelectionRunner;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alejandro Tardín
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/status.properties",
	scope = ServiceScope.PROTOTYPE, service = StatusResource.class
)
public class StatusResourceImpl extends BaseStatusResourceImpl {

	@Override
	public Status getStatus() {
		return new Status() {
			{
				actionInProgress = _bulkSelectionRunner.isBusy(contextUser);
			}
		};
	}

	@Reference
	private BulkSelectionRunner _bulkSelectionRunner;

}