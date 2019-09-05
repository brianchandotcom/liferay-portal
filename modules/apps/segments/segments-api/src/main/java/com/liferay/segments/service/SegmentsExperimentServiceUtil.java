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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

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
	public static com.liferay.segments.model.SegmentsExperiment
			addSegmentsExperiment(
				long segmentsExperienceId, long classNameId, long classPK,
				String name, String description, String goal, String goalTarget,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addSegmentsExperiment(
			segmentsExperienceId, classNameId, classPK, name, description, goal,
			goalTarget, serviceContext);
	}

	public static com.liferay.segments.model.SegmentsExperiment
			deleteSegmentsExperiment(long segmentsExperimentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteSegmentsExperiment(segmentsExperimentId);
	}

	public static com.liferay.segments.model.SegmentsExperiment
			deleteSegmentsExperiment(String segmentsExperimentKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteSegmentsExperiment(segmentsExperimentKey);
	}

	public static com.liferay.segments.model.SegmentsExperiment
			fetchSegmentsExperiment(
				long segmentsExperienceId, long classNameId, long classPK,
				int[] statuses)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchSegmentsExperiment(
			segmentsExperienceId, classNameId, classPK, statuses);
	}

	public static com.liferay.segments.model.SegmentsExperiment
			fetchSegmentsExperiment(long groupId, String segmentsExperimentKey)
		throws com.liferay.portal.kernel.exception.PortalException {

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

	public static java.util.List<com.liferay.segments.model.SegmentsExperiment>
			getSegmentsExperienceSegmentsExperiments(
				long[] segmentsExperienceIds, long classNameId, long classPK,
				int[] statuses, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSegmentsExperienceSegmentsExperiments(
			segmentsExperienceIds, classNameId, classPK, statuses, start, end);
	}

	public static com.liferay.segments.model.SegmentsExperiment
			getSegmentsExperiment(long segmentsExperimentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSegmentsExperiment(segmentsExperimentId);
	}

	public static java.util.List<com.liferay.segments.model.SegmentsExperiment>
		getSegmentsExperiments(long groupId, long classNameId, long classPK) {

		return getService().getSegmentsExperiments(
			groupId, classNameId, classPK);
	}

	public static com.liferay.segments.model.SegmentsExperiment
			runSegmentsExperiment(
				long segmentsExperimentId, double confidenceLevel,
				java.util.Map<Long, Double> segmentsExperienceIdSplitMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().runSegmentsExperiment(
			segmentsExperimentId, confidenceLevel,
			segmentsExperienceIdSplitMap);
	}

	public static com.liferay.segments.model.SegmentsExperiment
			runSegmentsExperiment(
				String segmentsExperimentKey, double confidenceLevel,
				java.util.Map<String, Double> segmentsExperienceKeySplitMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().runSegmentsExperiment(
			segmentsExperimentKey, confidenceLevel,
			segmentsExperienceKeySplitMap);
	}

	public static com.liferay.segments.model.SegmentsExperiment
			updateSegmentsExperiment(
				long segmentsExperimentId, String name, String description,
				String goal, String goalTarget)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateSegmentsExperiment(
			segmentsExperimentId, name, description, goal, goalTarget);
	}

	public static com.liferay.segments.model.SegmentsExperiment
			updateSegmentsExperimentStatus(
				long segmentsExperimentId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateSegmentsExperimentStatus(
			segmentsExperimentId, status);
	}

	public static com.liferay.segments.model.SegmentsExperiment
			updateSegmentsExperimentStatus(
				long segmentsExperimentId, long winnerSegmentsExperienceId,
				int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateSegmentsExperimentStatus(
			segmentsExperimentId, winnerSegmentsExperienceId, status);
	}

	public static com.liferay.segments.model.SegmentsExperiment
			updateSegmentsExperimentStatus(
				String segmentsExperimentKey, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateSegmentsExperimentStatus(
			segmentsExperimentKey, status);
	}

	public static com.liferay.segments.model.SegmentsExperiment
			updateSegmentsExperimentStatus(
				String segmentsExperimentKey,
				String winnerSegmentsExperienceKey, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateSegmentsExperimentStatus(
			segmentsExperimentKey, winnerSegmentsExperienceKey, status);
	}

	public static SegmentsExperimentService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SegmentsExperimentService, SegmentsExperimentService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			SegmentsExperimentService.class);

		ServiceTracker<SegmentsExperimentService, SegmentsExperimentService>
			serviceTracker =
				new ServiceTracker
					<SegmentsExperimentService, SegmentsExperimentService>(
						bundle.getBundleContext(),
						SegmentsExperimentService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}