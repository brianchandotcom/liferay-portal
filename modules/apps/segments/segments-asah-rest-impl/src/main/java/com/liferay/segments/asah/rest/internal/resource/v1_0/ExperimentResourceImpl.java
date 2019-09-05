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

package com.liferay.segments.asah.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.segments.asah.rest.resource.v1_0.ExperimentResource;
import com.liferay.segments.service.SegmentsExperimentService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/experiment.properties",
	scope = ServiceScope.PROTOTYPE, service = ExperimentResource.class
)
public class ExperimentResourceImpl extends BaseExperimentResourceImpl {

	@Override
	public void deleteExperiment(String experimentId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.popServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		serviceContext.setAttribute("updateAsah", Boolean.FALSE);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		_segmentsExperimentService.deleteSegmentsExperiment(experimentId);
	}

	@Reference
	private SegmentsExperimentService _segmentsExperimentService;

}