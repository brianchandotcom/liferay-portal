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
import com.liferay.segments.asah.rest.dto.v1_0.Experiment;
import com.liferay.segments.asah.rest.dto.v1_0.Status;
import com.liferay.segments.asah.rest.resource.v1_0.StatusResource;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperimentService;

import java.util.Optional;

import javax.ws.rs.ClientErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/status.properties",
	scope = ServiceScope.PROTOTYPE, service = StatusResource.class
)
public class StatusResourceImpl extends BaseStatusResourceImpl {

	@Override
	public Experiment postExperimentStatus(
			Long segmentsExperimentKey, Status status)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.popServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		serviceContext.setAttribute("updateAsah", Boolean.FALSE);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		Optional<SegmentsExperimentConstants.Status> optionalStatus =
			SegmentsExperimentConstants.Status.parse(status.getStatus());

		return _toExperiment(
			_segmentsExperimentService.updateSegmentsExperimentStatus(
				String.valueOf(segmentsExperimentKey),
				status.getWinnerVariantId(),
				optionalStatus.map(
					SegmentsExperimentConstants.Status::getValue
				).orElseThrow(
					() -> new ClientErrorException(
						"Experiment status is invalid", 422)
				)));
	}

	private Experiment _toExperiment(SegmentsExperiment segmentsExperiment) {
		SegmentsExperimentConstants.Status segmentsExperimentConstantsStatus =
			SegmentsExperimentConstants.Status.valueOf(
				segmentsExperiment.getStatus());

		return new Experiment() {
			{
				dateCreated = segmentsExperiment.getCreateDate();
				dateModified = segmentsExperiment.getModifiedDate();
				description = segmentsExperiment.getDescription();
				id = segmentsExperiment.getSegmentsExperimentKey();
				name = segmentsExperiment.getName();
				siteId = segmentsExperiment.getGroupId();
				status = segmentsExperimentConstantsStatus.toString();
				winnerVariantId =
					segmentsExperiment.getWinnerSegmentsExperienceId();
			}
		};
	}

	@Reference
	private SegmentsExperimentService _segmentsExperimentService;

}