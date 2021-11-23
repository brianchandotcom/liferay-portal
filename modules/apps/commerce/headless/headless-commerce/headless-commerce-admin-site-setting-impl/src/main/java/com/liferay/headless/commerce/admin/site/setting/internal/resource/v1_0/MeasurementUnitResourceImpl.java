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

package com.liferay.headless.commerce.admin.site.setting.internal.resource.v1_0;

import com.liferay.headless.commerce.admin.site.setting.dto.v1_0.MeasurementUnit;
import com.liferay.headless.commerce.admin.site.setting.internal.helper.v1_0.MeasurementUnitHelper;
import com.liferay.headless.commerce.admin.site.setting.resource.v1_0.MeasurementUnitResource;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltán Takács
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/measurement-unit.properties",
	scope = ServiceScope.PROTOTYPE, service = MeasurementUnitResource.class
)
public class MeasurementUnitResourceImpl
	extends BaseMeasurementUnitResourceImpl {

	@Override
	public Response deleteMeasurementUnit(Long id) throws Exception {
		_measurementUnitHelper.deleteMeasurementUnit(id);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public MeasurementUnit getMeasurementUnit(Long id) throws Exception {
		return _measurementUnitHelper.getMeasurementUnit(id);
	}

	@Reference
	private MeasurementUnitHelper _measurementUnitHelper;

}