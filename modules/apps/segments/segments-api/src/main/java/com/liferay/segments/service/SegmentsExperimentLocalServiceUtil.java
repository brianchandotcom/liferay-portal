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
 * Provides the local service utility for SegmentsExperiment. This utility wraps
 * <code>com.liferay.segments.service.impl.SegmentsExperimentLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperimentLocalService
 * @generated
 */
public class SegmentsExperimentLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.segments.service.impl.SegmentsExperimentLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SegmentsExperimentLocalServiceUtil} to access the segments experiment local service. Add custom service methods to <code>com.liferay.segments.service.impl.SegmentsExperimentLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
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

	/**
	 * Adds the segments experiment to the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsExperiment the segments experiment
	 * @return the segments experiment that was added
	 */
	public static com.liferay.segments.model.SegmentsExperiment
		addSegmentsExperiment(
			com.liferay.segments.model.SegmentsExperiment segmentsExperiment) {

		return getService().addSegmentsExperiment(segmentsExperiment);
	}

	/**
	 * Creates a new segments experiment with the primary key. Does not add the segments experiment to the database.
	 *
	 * @param segmentsExperimentId the primary key for the new segments experiment
	 * @return the new segments experiment
	 */
	public static com.liferay.segments.model.SegmentsExperiment
		createSegmentsExperiment(long segmentsExperimentId) {

		return getService().createSegmentsExperiment(segmentsExperimentId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the segments experiment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsExperimentId the primary key of the segments experiment
	 * @return the segments experiment that was removed
	 * @throws PortalException if a segments experiment with the primary key could not be found
	 */
	public static com.liferay.segments.model.SegmentsExperiment
			deleteSegmentsExperiment(long segmentsExperimentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteSegmentsExperiment(segmentsExperimentId);
	}

	/**
	 * Deletes the segments experiment from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsExperiment the segments experiment
	 * @return the segments experiment that was removed
	 * @throws PortalException
	 */
	public static com.liferay.segments.model.SegmentsExperiment
			deleteSegmentsExperiment(
				com.liferay.segments.model.SegmentsExperiment
					segmentsExperiment)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteSegmentsExperiment(segmentsExperiment);
	}

	public static com.liferay.segments.model.SegmentsExperiment
			deleteSegmentsExperiment(
				com.liferay.segments.model.SegmentsExperiment
					segmentsExperiment,
				boolean force)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteSegmentsExperiment(segmentsExperiment, force);
	}

	public static void deleteSegmentsExperiments(
			long segmentsExperienceId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteSegmentsExperiments(
			segmentsExperienceId, classNameId, classPK);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.segments.model.SegmentsExperiment
		fetchSegmentsExperiment(long segmentsExperimentId) {

		return getService().fetchSegmentsExperiment(segmentsExperimentId);
	}

	public static com.liferay.segments.model.SegmentsExperiment
		fetchSegmentsExperiment(
			long segmentsExperienceId, long classNameId, long classPK,
			int[] statuses) {

		return getService().fetchSegmentsExperiment(
			segmentsExperienceId, classNameId, classPK, statuses);
	}

	public static com.liferay.segments.model.SegmentsExperiment
		fetchSegmentsExperiment(long groupId, String segmentsExperimentKey) {

		return getService().fetchSegmentsExperiment(
			groupId, segmentsExperimentKey);
	}

	/**
	 * Returns the segments experiment matching the UUID and group.
	 *
	 * @param uuid the segments experiment's UUID
	 * @param groupId the primary key of the group
	 * @return the matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	public static com.liferay.segments.model.SegmentsExperiment
		fetchSegmentsExperimentByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchSegmentsExperimentByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.List<com.liferay.segments.model.SegmentsExperiment>
		getSegmentsEntrySegmentsExperiments(long segmentsEntryId) {

		return getService().getSegmentsEntrySegmentsExperiments(
			segmentsEntryId);
	}

	public static java.util.List<com.liferay.segments.model.SegmentsExperiment>
		getSegmentsExperienceSegmentsExperiments(
			long segmentsExperienceId, long classNameId, long classPK) {

		return getService().getSegmentsExperienceSegmentsExperiments(
			segmentsExperienceId, classNameId, classPK);
	}

	public static java.util.List<com.liferay.segments.model.SegmentsExperiment>
		getSegmentsExperienceSegmentsExperiments(
			long[] segmentsExperienceIds, long classNameId, long classPK,
			int[] statuses, int start, int end) {

		return getService().getSegmentsExperienceSegmentsExperiments(
			segmentsExperienceIds, classNameId, classPK, statuses, start, end);
	}

	/**
	 * Returns the segments experiment with the primary key.
	 *
	 * @param segmentsExperimentId the primary key of the segments experiment
	 * @return the segments experiment
	 * @throws PortalException if a segments experiment with the primary key could not be found
	 */
	public static com.liferay.segments.model.SegmentsExperiment
			getSegmentsExperiment(long segmentsExperimentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSegmentsExperiment(segmentsExperimentId);
	}

	public static com.liferay.segments.model.SegmentsExperiment
			getSegmentsExperiment(String segmentsExperimentKey)
		throws com.liferay.segments.exception.NoSuchExperimentException {

		return getService().getSegmentsExperiment(segmentsExperimentKey);
	}

	/**
	 * Returns the segments experiment matching the UUID and group.
	 *
	 * @param uuid the segments experiment's UUID
	 * @param groupId the primary key of the group
	 * @return the matching segments experiment
	 * @throws PortalException if a matching segments experiment could not be found
	 */
	public static com.liferay.segments.model.SegmentsExperiment
			getSegmentsExperimentByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSegmentsExperimentByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the segments experiments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of segments experiments
	 */
	public static java.util.List<com.liferay.segments.model.SegmentsExperiment>
		getSegmentsExperiments(int start, int end) {

		return getService().getSegmentsExperiments(start, end);
	}

	public static java.util.List<com.liferay.segments.model.SegmentsExperiment>
		getSegmentsExperiments(long groupId, long classNameId, long classPK) {

		return getService().getSegmentsExperiments(
			groupId, classNameId, classPK);
	}

	public static java.util.List<com.liferay.segments.model.SegmentsExperiment>
		getSegmentsExperiments(
			long segmentsExperienceId, long classNameId, long classPK,
			int[] statuses,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.segments.model.SegmentsExperiment>
					orderByComparator) {

		return getService().getSegmentsExperiments(
			segmentsExperienceId, classNameId, classPK, statuses,
			orderByComparator);
	}

	/**
	 * Returns all the segments experiments matching the UUID and company.
	 *
	 * @param uuid the UUID of the segments experiments
	 * @param companyId the primary key of the company
	 * @return the matching segments experiments, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.segments.model.SegmentsExperiment>
		getSegmentsExperimentsByUuidAndCompanyId(String uuid, long companyId) {

		return getService().getSegmentsExperimentsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of segments experiments matching the UUID and company.
	 *
	 * @param uuid the UUID of the segments experiments
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching segments experiments, or an empty list if no matches were found
	 */
	public static java.util.List<com.liferay.segments.model.SegmentsExperiment>
		getSegmentsExperimentsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.segments.model.SegmentsExperiment>
					orderByComparator) {

		return getService().getSegmentsExperimentsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of segments experiments.
	 *
	 * @return the number of segments experiments
	 */
	public static int getSegmentsExperimentsCount() {
		return getService().getSegmentsExperimentsCount();
	}

	public static boolean hasSegmentsExperiment(
		long segmentsExperienceId, long classNameId, long classPK,
		int[] statuses) {

		return getService().hasSegmentsExperiment(
			segmentsExperienceId, classNameId, classPK, statuses);
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
			updateSegmentsExperiment(
				long segmentsExperimentId, String name, String description,
				String goal, String goalTarget)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateSegmentsExperiment(
			segmentsExperimentId, name, description, goal, goalTarget);
	}

	/**
	 * Updates the segments experiment in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsExperiment the segments experiment
	 * @return the segments experiment that was updated
	 */
	public static com.liferay.segments.model.SegmentsExperiment
		updateSegmentsExperiment(
			com.liferay.segments.model.SegmentsExperiment segmentsExperiment) {

		return getService().updateSegmentsExperiment(segmentsExperiment);
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

	public static SegmentsExperimentLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SegmentsExperimentLocalService, SegmentsExperimentLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			SegmentsExperimentLocalService.class);

		ServiceTracker
			<SegmentsExperimentLocalService, SegmentsExperimentLocalService>
				serviceTracker =
					new ServiceTracker
						<SegmentsExperimentLocalService,
						 SegmentsExperimentLocalService>(
							 bundle.getBundleContext(),
							 SegmentsExperimentLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}