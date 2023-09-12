/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.segments.model.SegmentsExperiment;

import java.util.Map;

/**
 * Provides the remote service utility for SegmentsExperiment. This utility wraps
 * <code>com.liferay.segments.service.impl.SegmentsExperimentServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperimentService
 * @generated
 */
public class SegmentsExperimentServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.segments.service.impl.SegmentsExperimentServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static SegmentsExperiment addSegmentsExperiment(
			long segmentsExperienceId, long plid, String name,
			String description, String goal, String goalTarget,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addSegmentsExperiment(
			segmentsExperienceId, plid, name, description, goal, goalTarget,
			serviceContext);
	}

	public static SegmentsExperiment deleteSegmentsExperiment(
			long segmentsExperimentId)
		throws PortalException {

		return getService().deleteSegmentsExperiment(segmentsExperimentId);
	}

	public static SegmentsExperiment deleteSegmentsExperiment(
			SegmentsExperiment segmentsExperiment, boolean force)
		throws PortalException {

		return getService().deleteSegmentsExperiment(segmentsExperiment, force);
	}

	public static SegmentsExperiment deleteSegmentsExperiment(
			String segmentsExperimentKey)
		throws PortalException {

		return getService().deleteSegmentsExperiment(segmentsExperimentKey);
	}

	public static SegmentsExperiment fetchSegmentsExperiment(
			long groupId, long segmentsExperienceId, long plid)
		throws PortalException {

		return getService().fetchSegmentsExperiment(
			groupId, segmentsExperienceId, plid);
	}

	public static SegmentsExperiment fetchSegmentsExperiment(
			long groupId, String segmentsExperimentKey)
		throws PortalException {

		return getService().fetchSegmentsExperiment(
			groupId, segmentsExperimentKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static SegmentsExperiment getSegmentsExperiment(
			long segmentsExperimentId)
		throws PortalException {

		return getService().getSegmentsExperiment(segmentsExperimentId);
	}

	public static SegmentsExperiment getSegmentsExperiment(
			String segmentsExperimentKey)
		throws PortalException {

		return getService().getSegmentsExperiment(segmentsExperimentKey);
	}

	public static SegmentsExperiment runSegmentsExperiment(
			long segmentsExperimentId, double confidenceLevel,
			Map<Long, Double> segmentsExperienceIdSplitMap)
		throws PortalException {

		return getService().runSegmentsExperiment(
			segmentsExperimentId, confidenceLevel,
			segmentsExperienceIdSplitMap);
	}

	public static SegmentsExperiment runSegmentsExperiment(
			String segmentsExperimentKey, double confidenceLevel,
			Map<String, Double> segmentsExperienceKeySplitMap)
		throws PortalException {

		return getService().runSegmentsExperiment(
			segmentsExperimentKey, confidenceLevel,
			segmentsExperienceKeySplitMap);
	}

	public static SegmentsExperiment updateSegmentsExperiment(
			long segmentsExperimentId, String name, String description,
			String goal, String goalTarget)
		throws PortalException {

		return getService().updateSegmentsExperiment(
			segmentsExperimentId, name, description, goal, goalTarget);
	}

	public static SegmentsExperiment updateSegmentsExperimentStatus(
			long segmentsExperimentId, int status)
		throws PortalException {

		return getService().updateSegmentsExperimentStatus(
			segmentsExperimentId, status);
	}

	public static SegmentsExperiment updateSegmentsExperimentStatus(
			long segmentsExperimentId, long winnerSegmentsExperienceId,
			int status)
		throws PortalException {

		return getService().updateSegmentsExperimentStatus(
			segmentsExperimentId, winnerSegmentsExperienceId, status);
	}

	public static SegmentsExperiment updateSegmentsExperimentStatus(
			String segmentsExperimentKey, int status)
		throws PortalException {

		return getService().updateSegmentsExperimentStatus(
			segmentsExperimentKey, status);
	}

	public static SegmentsExperiment updateSegmentsExperimentStatus(
			String segmentsExperimentKey, String winnerSegmentsExperienceKey,
			int status)
		throws PortalException {

		return getService().updateSegmentsExperimentStatus(
			segmentsExperimentKey, winnerSegmentsExperienceKey, status);
	}

	public static SegmentsExperimentService getService() {
		return _service;
	}

	public static void setService(SegmentsExperimentService service) {
		_service = service;
	}

	private static volatile SegmentsExperimentService _service;

}