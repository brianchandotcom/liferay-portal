/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.segments.model.SegmentsExperiment;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

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

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.segments.service.impl.SegmentsExperimentLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
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

	/**
	 * Adds the segments experiment to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SegmentsExperimentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param segmentsExperiment the segments experiment
	 * @return the segments experiment that was added
	 */
	public static SegmentsExperiment addSegmentsExperiment(
		SegmentsExperiment segmentsExperiment) {

		return getService().addSegmentsExperiment(segmentsExperiment);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new segments experiment with the primary key. Does not add the segments experiment to the database.
	 *
	 * @param segmentsExperimentId the primary key for the new segments experiment
	 * @return the new segments experiment
	 */
	public static SegmentsExperiment createSegmentsExperiment(
		long segmentsExperimentId) {

		return getService().createSegmentsExperiment(segmentsExperimentId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the segments experiment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SegmentsExperimentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param segmentsExperimentId the primary key of the segments experiment
	 * @return the segments experiment that was removed
	 * @throws PortalException if a segments experiment with the primary key could not be found
	 */
	public static SegmentsExperiment deleteSegmentsExperiment(
			long segmentsExperimentId)
		throws PortalException {

		return getService().deleteSegmentsExperiment(segmentsExperimentId);
	}

	public static SegmentsExperiment deleteSegmentsExperiment(
			long groupId, long segmentsExperienceId, long plid)
		throws PortalException {

		return getService().deleteSegmentsExperiment(
			groupId, segmentsExperienceId, plid);
	}

	/**
	 * Deletes the segments experiment from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SegmentsExperimentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param segmentsExperiment the segments experiment
	 * @return the segments experiment that was removed
	 * @throws PortalException
	 */
	public static SegmentsExperiment deleteSegmentsExperiment(
			SegmentsExperiment segmentsExperiment)
		throws PortalException {

		return getService().deleteSegmentsExperiment(segmentsExperiment);
	}

	public static SegmentsExperiment deleteSegmentsExperiment(
			SegmentsExperiment segmentsExperiment, boolean force)
		throws PortalException {

		return getService().deleteSegmentsExperiment(segmentsExperiment, force);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static SegmentsExperiment fetchSegmentsExperiment(
		long segmentsExperimentId) {

		return getService().fetchSegmentsExperiment(segmentsExperimentId);
	}

	public static SegmentsExperiment fetchSegmentsExperiment(
		long groupId, long segmentsExperienceId, long plid) {

		return getService().fetchSegmentsExperiment(
			groupId, segmentsExperienceId, plid);
	}

	public static SegmentsExperiment fetchSegmentsExperiment(
		long groupId, String segmentsExperimentKey) {

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
	public static SegmentsExperiment fetchSegmentsExperimentByUuidAndGroupId(
		String uuid, long groupId) {

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

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static List<SegmentsExperiment> getSegmentsEntrySegmentsExperiments(
		long segmentsEntryId) {

		return getService().getSegmentsEntrySegmentsExperiments(
			segmentsEntryId);
	}

	/**
	 * Returns the segments experiment with the primary key.
	 *
	 * @param segmentsExperimentId the primary key of the segments experiment
	 * @return the segments experiment
	 * @throws PortalException if a segments experiment with the primary key could not be found
	 */
	public static SegmentsExperiment getSegmentsExperiment(
			long segmentsExperimentId)
		throws PortalException {

		return getService().getSegmentsExperiment(segmentsExperimentId);
	}

	public static SegmentsExperiment getSegmentsExperiment(
			String segmentsExperimentKey)
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
	public static SegmentsExperiment getSegmentsExperimentByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

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
	public static List<SegmentsExperiment> getSegmentsExperiments(
		int start, int end) {

		return getService().getSegmentsExperiments(start, end);
	}

	/**
	 * Returns all the segments experiments matching the UUID and company.
	 *
	 * @param uuid the UUID of the segments experiments
	 * @param companyId the primary key of the company
	 * @return the matching segments experiments, or an empty list if no matches were found
	 */
	public static List<SegmentsExperiment>
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
	public static List<SegmentsExperiment>
		getSegmentsExperimentsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<SegmentsExperiment> orderByComparator) {

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

	public static SegmentsExperiment runSegmentsExperiment(
			long segmentsExperimentId, double confidenceLevel,
			Map<Long, Double> segmentsExperienceIdSplitMap)
		throws PortalException {

		return getService().runSegmentsExperiment(
			segmentsExperimentId, confidenceLevel,
			segmentsExperienceIdSplitMap);
	}

	public static SegmentsExperiment updateSegmentsExperiment(
			long segmentsExperimentId, String name, String description,
			String goal, String goalTarget)
		throws PortalException {

		return getService().updateSegmentsExperiment(
			segmentsExperimentId, name, description, goal, goalTarget);
	}

	/**
	 * Updates the segments experiment in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SegmentsExperimentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param segmentsExperiment the segments experiment
	 * @return the segments experiment that was updated
	 */
	public static SegmentsExperiment updateSegmentsExperiment(
		SegmentsExperiment segmentsExperiment) {

		return getService().updateSegmentsExperiment(segmentsExperiment);
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

	public static SegmentsExperimentLocalService getService() {
		return _service;
	}

	public static void setService(SegmentsExperimentLocalService service) {
		_service = service;
	}

	private static volatile SegmentsExperimentLocalService _service;

}