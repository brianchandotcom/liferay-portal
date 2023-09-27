/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.segments.asah.rest.dto.v1_0.Experiment;
import com.liferay.segments.asah.rest.resource.v1_0.ExperimentResource;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.model.SegmentsExperiment;
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

	@Override
	public Experiment getExperiment(String experimentId) throws Exception {
		return _toExperiment(
			_segmentsExperimentService.getSegmentsExperiment(experimentId));
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