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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SegmentsExperimentLocalService}.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperimentLocalService
 * @generated
 */
public class SegmentsExperimentLocalServiceWrapper
	implements SegmentsExperimentLocalService,
			   ServiceWrapper<SegmentsExperimentLocalService> {

	public SegmentsExperimentLocalServiceWrapper(
		SegmentsExperimentLocalService segmentsExperimentLocalService) {

		_segmentsExperimentLocalService = segmentsExperimentLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SegmentsExperimentLocalServiceUtil} to access the segments experiment local service. Add custom service methods to <code>com.liferay.segments.service.impl.SegmentsExperimentLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.segments.model.SegmentsExperiment addSegmentsExperiment(
			long segmentsExperienceId, long classNameId, long classPK,
			String name, String description, String goal, String goalTarget,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentLocalService.addSegmentsExperiment(
			segmentsExperienceId, classNameId, classPK, name, description, goal,
			goalTarget, serviceContext);
	}

	/**
	 * Adds the segments experiment to the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsExperiment the segments experiment
	 * @return the segments experiment that was added
	 */
	@Override
	public com.liferay.segments.model.SegmentsExperiment addSegmentsExperiment(
		com.liferay.segments.model.SegmentsExperiment segmentsExperiment) {

		return _segmentsExperimentLocalService.addSegmentsExperiment(
			segmentsExperiment);
	}

	/**
	 * Creates a new segments experiment with the primary key. Does not add the segments experiment to the database.
	 *
	 * @param segmentsExperimentId the primary key for the new segments experiment
	 * @return the new segments experiment
	 */
	@Override
	public com.liferay.segments.model.SegmentsExperiment
		createSegmentsExperiment(long segmentsExperimentId) {

		return _segmentsExperimentLocalService.createSegmentsExperiment(
			segmentsExperimentId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the segments experiment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsExperimentId the primary key of the segments experiment
	 * @return the segments experiment that was removed
	 * @throws PortalException if a segments experiment with the primary key could not be found
	 */
	@Override
	public com.liferay.segments.model.SegmentsExperiment
			deleteSegmentsExperiment(long segmentsExperimentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentLocalService.deleteSegmentsExperiment(
			segmentsExperimentId);
	}

	/**
	 * Deletes the segments experiment from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsExperiment the segments experiment
	 * @return the segments experiment that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.segments.model.SegmentsExperiment
			deleteSegmentsExperiment(
				com.liferay.segments.model.SegmentsExperiment
					segmentsExperiment)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentLocalService.deleteSegmentsExperiment(
			segmentsExperiment);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperiment
			deleteSegmentsExperiment(
				com.liferay.segments.model.SegmentsExperiment
					segmentsExperiment,
				boolean force)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentLocalService.deleteSegmentsExperiment(
			segmentsExperiment, force);
	}

	@Override
	public void deleteSegmentsExperiments(
			long segmentsExperienceId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		_segmentsExperimentLocalService.deleteSegmentsExperiments(
			segmentsExperienceId, classNameId, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _segmentsExperimentLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _segmentsExperimentLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _segmentsExperimentLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _segmentsExperimentLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _segmentsExperimentLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _segmentsExperimentLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperiment
		fetchSegmentsExperiment(long segmentsExperimentId) {

		return _segmentsExperimentLocalService.fetchSegmentsExperiment(
			segmentsExperimentId);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperiment
		fetchSegmentsExperiment(
			long segmentsExperienceId, long classNameId, long classPK,
			int[] statuses) {

		return _segmentsExperimentLocalService.fetchSegmentsExperiment(
			segmentsExperienceId, classNameId, classPK, statuses);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperiment
		fetchSegmentsExperiment(long groupId, String segmentsExperimentKey) {

		return _segmentsExperimentLocalService.fetchSegmentsExperiment(
			groupId, segmentsExperimentKey);
	}

	/**
	 * Returns the segments experiment matching the UUID and group.
	 *
	 * @param uuid the segments experiment's UUID
	 * @param groupId the primary key of the group
	 * @return the matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	@Override
	public com.liferay.segments.model.SegmentsExperiment
		fetchSegmentsExperimentByUuidAndGroupId(String uuid, long groupId) {

		return _segmentsExperimentLocalService.
			fetchSegmentsExperimentByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _segmentsExperimentLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _segmentsExperimentLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _segmentsExperimentLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _segmentsExperimentLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.segments.model.SegmentsExperiment>
		getSegmentsEntrySegmentsExperiments(long segmentsEntryId) {

		return _segmentsExperimentLocalService.
			getSegmentsEntrySegmentsExperiments(segmentsEntryId);
	}

	@Override
	public java.util.List<com.liferay.segments.model.SegmentsExperiment>
		getSegmentsExperienceSegmentsExperiments(
			long segmentsExperienceId, long classNameId, long classPK) {

		return _segmentsExperimentLocalService.
			getSegmentsExperienceSegmentsExperiments(
				segmentsExperienceId, classNameId, classPK);
	}

	@Override
	public java.util.List<com.liferay.segments.model.SegmentsExperiment>
		getSegmentsExperienceSegmentsExperiments(
			long[] segmentsExperienceIds, long classNameId, long classPK,
			int[] statuses, int start, int end) {

		return _segmentsExperimentLocalService.
			getSegmentsExperienceSegmentsExperiments(
				segmentsExperienceIds, classNameId, classPK, statuses, start,
				end);
	}

	/**
	 * Returns the segments experiment with the primary key.
	 *
	 * @param segmentsExperimentId the primary key of the segments experiment
	 * @return the segments experiment
	 * @throws PortalException if a segments experiment with the primary key could not be found
	 */
	@Override
	public com.liferay.segments.model.SegmentsExperiment getSegmentsExperiment(
			long segmentsExperimentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentLocalService.getSegmentsExperiment(
			segmentsExperimentId);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperiment getSegmentsExperiment(
			String segmentsExperimentKey)
		throws com.liferay.segments.exception.NoSuchExperimentException {

		return _segmentsExperimentLocalService.getSegmentsExperiment(
			segmentsExperimentKey);
	}

	/**
	 * Returns the segments experiment matching the UUID and group.
	 *
	 * @param uuid the segments experiment's UUID
	 * @param groupId the primary key of the group
	 * @return the matching segments experiment
	 * @throws PortalException if a matching segments experiment could not be found
	 */
	@Override
	public com.liferay.segments.model.SegmentsExperiment
			getSegmentsExperimentByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentLocalService.
			getSegmentsExperimentByUuidAndGroupId(uuid, groupId);
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
	@Override
	public java.util.List<com.liferay.segments.model.SegmentsExperiment>
		getSegmentsExperiments(int start, int end) {

		return _segmentsExperimentLocalService.getSegmentsExperiments(
			start, end);
	}

	@Override
	public java.util.List<com.liferay.segments.model.SegmentsExperiment>
		getSegmentsExperiments(long groupId, long classNameId, long classPK) {

		return _segmentsExperimentLocalService.getSegmentsExperiments(
			groupId, classNameId, classPK);
	}

	@Override
	public java.util.List<com.liferay.segments.model.SegmentsExperiment>
		getSegmentsExperiments(
			long segmentsExperienceId, long classNameId, long classPK,
			int[] statuses,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.segments.model.SegmentsExperiment>
					orderByComparator) {

		return _segmentsExperimentLocalService.getSegmentsExperiments(
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
	@Override
	public java.util.List<com.liferay.segments.model.SegmentsExperiment>
		getSegmentsExperimentsByUuidAndCompanyId(String uuid, long companyId) {

		return _segmentsExperimentLocalService.
			getSegmentsExperimentsByUuidAndCompanyId(uuid, companyId);
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
	@Override
	public java.util.List<com.liferay.segments.model.SegmentsExperiment>
		getSegmentsExperimentsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.segments.model.SegmentsExperiment>
					orderByComparator) {

		return _segmentsExperimentLocalService.
			getSegmentsExperimentsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of segments experiments.
	 *
	 * @return the number of segments experiments
	 */
	@Override
	public int getSegmentsExperimentsCount() {
		return _segmentsExperimentLocalService.getSegmentsExperimentsCount();
	}

	@Override
	public boolean hasSegmentsExperiment(
		long segmentsExperienceId, long classNameId, long classPK,
		int[] statuses) {

		return _segmentsExperimentLocalService.hasSegmentsExperiment(
			segmentsExperienceId, classNameId, classPK, statuses);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperiment runSegmentsExperiment(
			long segmentsExperimentId, double confidenceLevel,
			java.util.Map<Long, Double> segmentsExperienceIdSplitMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentLocalService.runSegmentsExperiment(
			segmentsExperimentId, confidenceLevel,
			segmentsExperienceIdSplitMap);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperiment
			updateSegmentsExperiment(
				long segmentsExperimentId, String name, String description,
				String goal, String goalTarget)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentLocalService.updateSegmentsExperiment(
			segmentsExperimentId, name, description, goal, goalTarget);
	}

	/**
	 * Updates the segments experiment in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsExperiment the segments experiment
	 * @return the segments experiment that was updated
	 */
	@Override
	public com.liferay.segments.model.SegmentsExperiment
		updateSegmentsExperiment(
			com.liferay.segments.model.SegmentsExperiment segmentsExperiment) {

		return _segmentsExperimentLocalService.updateSegmentsExperiment(
			segmentsExperiment);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperiment
			updateSegmentsExperimentStatus(
				long segmentsExperimentId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperimentId, status);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperiment
			updateSegmentsExperimentStatus(
				long segmentsExperimentId, long winnerSegmentsExperienceId,
				int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperimentId, winnerSegmentsExperienceId, status);
	}

	@Override
	public SegmentsExperimentLocalService getWrappedService() {
		return _segmentsExperimentLocalService;
	}

	@Override
	public void setWrappedService(
		SegmentsExperimentLocalService segmentsExperimentLocalService) {

		_segmentsExperimentLocalService = segmentsExperimentLocalService;
	}

	private SegmentsExperimentLocalService _segmentsExperimentLocalService;

}