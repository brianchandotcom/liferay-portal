/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.segments.model.SegmentsExperiment;

/**
 * Provides a wrapper for {@link SegmentsExperimentService}.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperimentService
 * @generated
 */
public class SegmentsExperimentServiceWrapper
	implements SegmentsExperimentService,
			   ServiceWrapper<SegmentsExperimentService> {

	public SegmentsExperimentServiceWrapper() {
		this(null);
	}

	public SegmentsExperimentServiceWrapper(
		SegmentsExperimentService segmentsExperimentService) {

		_segmentsExperimentService = segmentsExperimentService;
	}

	@Override
	public SegmentsExperiment addSegmentsExperiment(
			long segmentsExperienceId, long plid, String name,
			String description, String goal, String goalTarget,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentService.addSegmentsExperiment(
			segmentsExperienceId, plid, name, description, goal, goalTarget,
			serviceContext);
	}

	@Override
	public SegmentsExperiment deleteSegmentsExperiment(
			long segmentsExperimentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentService.deleteSegmentsExperiment(
			segmentsExperimentId);
	}

	@Override
	public SegmentsExperiment deleteSegmentsExperiment(
			SegmentsExperiment segmentsExperiment, boolean force)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentService.deleteSegmentsExperiment(
			segmentsExperiment, force);
	}

	@Override
	public SegmentsExperiment deleteSegmentsExperiment(
			String segmentsExperimentKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentService.deleteSegmentsExperiment(
			segmentsExperimentKey);
	}

	@Override
	public SegmentsExperiment fetchSegmentsExperiment(
			long groupId, long segmentsExperienceId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentService.fetchSegmentsExperiment(
			groupId, segmentsExperienceId, plid);
	}

	@Override
	public SegmentsExperiment fetchSegmentsExperiment(
			long groupId, String segmentsExperimentKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentService.fetchSegmentsExperiment(
			groupId, segmentsExperimentKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _segmentsExperimentService.getOSGiServiceIdentifier();
	}

	@Override
	public SegmentsExperiment getSegmentsExperiment(long segmentsExperimentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentService.getSegmentsExperiment(
			segmentsExperimentId);
	}

	@Override
	public SegmentsExperiment getSegmentsExperiment(
			String segmentsExperimentKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentService.getSegmentsExperiment(
			segmentsExperimentKey);
	}

	@Override
	public SegmentsExperiment runSegmentsExperiment(
			long segmentsExperimentId, double confidenceLevel,
			java.util.Map<Long, Double> segmentsExperienceIdSplitMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentService.runSegmentsExperiment(
			segmentsExperimentId, confidenceLevel,
			segmentsExperienceIdSplitMap);
	}

	@Override
	public SegmentsExperiment runSegmentsExperiment(
			String segmentsExperimentKey, double confidenceLevel,
			java.util.Map<String, Double> segmentsExperienceKeySplitMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentService.runSegmentsExperiment(
			segmentsExperimentKey, confidenceLevel,
			segmentsExperienceKeySplitMap);
	}

	@Override
	public SegmentsExperiment updateSegmentsExperiment(
			long segmentsExperimentId, String name, String description,
			String goal, String goalTarget)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentService.updateSegmentsExperiment(
			segmentsExperimentId, name, description, goal, goalTarget);
	}

	@Override
	public SegmentsExperiment updateSegmentsExperimentStatus(
			long segmentsExperimentId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentService.updateSegmentsExperimentStatus(
			segmentsExperimentId, status);
	}

	@Override
	public SegmentsExperiment updateSegmentsExperimentStatus(
			long segmentsExperimentId, long winnerSegmentsExperienceId,
			int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentService.updateSegmentsExperimentStatus(
			segmentsExperimentId, winnerSegmentsExperienceId, status);
	}

	@Override
	public SegmentsExperiment updateSegmentsExperimentStatus(
			String segmentsExperimentKey, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentService.updateSegmentsExperimentStatus(
			segmentsExperimentKey, status);
	}

	@Override
	public SegmentsExperiment updateSegmentsExperimentStatus(
			String segmentsExperimentKey, String winnerSegmentsExperienceKey,
			int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentService.updateSegmentsExperimentStatus(
			segmentsExperimentKey, winnerSegmentsExperienceKey, status);
	}

	@Override
	public SegmentsExperimentService getWrappedService() {
		return _segmentsExperimentService;
	}

	@Override
	public void setWrappedService(
		SegmentsExperimentService segmentsExperimentService) {

		_segmentsExperimentService = segmentsExperimentService;
	}

	private SegmentsExperimentService _segmentsExperimentService;

}