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

package com.liferay.segments.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.segments.model.SegmentsExperiment;

import java.util.List;
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
			long segmentsExperienceId, long classNameId, long classPK,
			String name, String description, String goal, String goalTarget,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addSegmentsExperiment(
			segmentsExperienceId, classNameId, classPK, name, description, goal,
			goalTarget, serviceContext);
	}

	public static SegmentsExperiment deleteSegmentsExperiment(
			long segmentsExperimentId)
		throws PortalException {

		return getService().deleteSegmentsExperiment(segmentsExperimentId);
	}

	public static SegmentsExperiment deleteSegmentsExperiment(
			String segmentsExperimentKey)
		throws PortalException {

		return getService().deleteSegmentsExperiment(segmentsExperimentKey);
	}

	public static SegmentsExperiment fetchSegmentsExperiment(
			long segmentsExperienceId, long classNameId, long classPK,
			int[] statuses)
		throws PortalException {

		return getService().fetchSegmentsExperiment(
			segmentsExperienceId, classNameId, classPK, statuses);
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

	public static List<SegmentsExperiment>
			getSegmentsExperienceSegmentsExperiments(
				long[] segmentsExperienceIds, long classNameId, long classPK,
				int[] statuses, int start, int end)
		throws PortalException {

		return getService().getSegmentsExperienceSegmentsExperiments(
			segmentsExperienceIds, classNameId, classPK, statuses, start, end);
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

	public static List<SegmentsExperiment> getSegmentsExperiments(
		long groupId, long classNameId, long classPK) {

		return getService().getSegmentsExperiments(
			groupId, classNameId, classPK);
	}

	public static List<SegmentsExperiment> getSegmentsExperiments(
		long segmentsExperienceId, long classNameId, long classPK,
		int[] statuses,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		return getService().getSegmentsExperiments(
			segmentsExperienceId, classNameId, classPK, statuses,
			orderByComparator);
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