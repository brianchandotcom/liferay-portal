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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.asah.rest.dto.v1_0.Experiment;
import com.liferay.segments.asah.rest.resource.v1_0.ExperimentResource;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperimentService;

import javax.ws.rs.ClientErrorException;

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
	public Experiment patchExperiment(Long segmentsExperimentKey, String status)
		throws Exception {

		return _toExperiment(
			_segmentsExperimentService.updateSegmentsExperiment(
				String.valueOf(segmentsExperimentKey), _parseStatus(status)));
	}

	private int _parseStatus(String status) {
		SegmentsExperimentConstants.Status segmentsExperimentConstantsStatus =
			SegmentsExperimentConstants.Status.parse(
				StringUtil.upperCase(status));

		if (segmentsExperimentConstantsStatus == null) {
			throw new ClientErrorException("Experiment status is invalid", 422);
		}

		return segmentsExperimentConstantsStatus.getValue();
	}

	private Experiment _toExperiment(SegmentsExperiment segmentsExperiment) {
		SegmentsExperimentConstants.Status segmentsExperimentConstantsStatus =
			SegmentsExperimentConstants.Status.parse(
				segmentsExperiment.getStatus());

		return new Experiment() {
			{
				id = segmentsExperiment.getSegmentsExperimentKey();
				description = segmentsExperiment.getDescription();
				dateCreated = segmentsExperiment.getCreateDate();
				dateModified = segmentsExperiment.getModifiedDate();
				name = segmentsExperiment.getName();
				siteId = segmentsExperiment.getGroupId();
				status = segmentsExperimentConstantsStatus.toString();
			}
		};
	}

	@Reference
	private SegmentsExperimentService _segmentsExperimentService;

}