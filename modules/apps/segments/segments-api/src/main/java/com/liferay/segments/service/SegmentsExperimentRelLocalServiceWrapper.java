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
 * Provides a wrapper for {@link SegmentsExperimentRelLocalService}.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperimentRelLocalService
 * @generated
 */
public class SegmentsExperimentRelLocalServiceWrapper
	implements SegmentsExperimentRelLocalService,
			   ServiceWrapper<SegmentsExperimentRelLocalService> {

	public SegmentsExperimentRelLocalServiceWrapper(
		SegmentsExperimentRelLocalService segmentsExperimentRelLocalService) {

		_segmentsExperimentRelLocalService = segmentsExperimentRelLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SegmentsExperimentRelLocalServiceUtil} to access the segments experiment rel local service. Add custom service methods to <code>com.liferay.segments.service.impl.SegmentsExperimentRelLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.segments.model.SegmentsExperimentRel
			addSegmentsExperimentRel(
				long segmentsExperimentId, long segmentsExperienceId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentRelLocalService.addSegmentsExperimentRel(
			segmentsExperimentId, segmentsExperienceId, serviceContext);
	}

	/**
	 * Adds the segments experiment rel to the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsExperimentRel the segments experiment rel
	 * @return the segments experiment rel that was added
	 */
	@Override
	public com.liferay.segments.model.SegmentsExperimentRel
		addSegmentsExperimentRel(
			com.liferay.segments.model.SegmentsExperimentRel
				segmentsExperimentRel) {

		return _segmentsExperimentRelLocalService.addSegmentsExperimentRel(
			segmentsExperimentRel);
	}

	/**
	 * Creates a new segments experiment rel with the primary key. Does not add the segments experiment rel to the database.
	 *
	 * @param segmentsExperimentRelId the primary key for the new segments experiment rel
	 * @return the new segments experiment rel
	 */
	@Override
	public com.liferay.segments.model.SegmentsExperimentRel
		createSegmentsExperimentRel(long segmentsExperimentRelId) {

		return _segmentsExperimentRelLocalService.createSegmentsExperimentRel(
			segmentsExperimentRelId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentRelLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the segments experiment rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsExperimentRelId the primary key of the segments experiment rel
	 * @return the segments experiment rel that was removed
	 * @throws PortalException if a segments experiment rel with the primary key could not be found
	 */
	@Override
	public com.liferay.segments.model.SegmentsExperimentRel
			deleteSegmentsExperimentRel(long segmentsExperimentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentRelLocalService.deleteSegmentsExperimentRel(
			segmentsExperimentRelId);
	}

	/**
	 * Deletes the segments experiment rel from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsExperimentRel the segments experiment rel
	 * @return the segments experiment rel that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.segments.model.SegmentsExperimentRel
			deleteSegmentsExperimentRel(
				com.liferay.segments.model.SegmentsExperimentRel
					segmentsExperimentRel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentRelLocalService.deleteSegmentsExperimentRel(
			segmentsExperimentRel);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperimentRel
			deleteSegmentsExperimentRel(
				com.liferay.segments.model.SegmentsExperimentRel
					segmentsExperimentRel,
				boolean force)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentRelLocalService.deleteSegmentsExperimentRel(
			segmentsExperimentRel, force);
	}

	@Override
	public void deleteSegmentsExperimentRels(long segmentsExperimentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_segmentsExperimentRelLocalService.deleteSegmentsExperimentRels(
			segmentsExperimentId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _segmentsExperimentRelLocalService.dynamicQuery();
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

		return _segmentsExperimentRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsExperimentRelModelImpl</code>.
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

		return _segmentsExperimentRelLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsExperimentRelModelImpl</code>.
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

		return _segmentsExperimentRelLocalService.dynamicQuery(
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

		return _segmentsExperimentRelLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _segmentsExperimentRelLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperimentRel
		fetchSegmentsExperimentRel(long segmentsExperimentRelId) {

		return _segmentsExperimentRelLocalService.fetchSegmentsExperimentRel(
			segmentsExperimentRelId);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperimentRel
			fetchSegmentsExperimentRel(
				long segmentsExperimentId, long segmentsExperienceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentRelLocalService.fetchSegmentsExperimentRel(
			segmentsExperimentId, segmentsExperienceId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _segmentsExperimentRelLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _segmentsExperimentRelLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _segmentsExperimentRelLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentRelLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Returns the segments experiment rel with the primary key.
	 *
	 * @param segmentsExperimentRelId the primary key of the segments experiment rel
	 * @return the segments experiment rel
	 * @throws PortalException if a segments experiment rel with the primary key could not be found
	 */
	@Override
	public com.liferay.segments.model.SegmentsExperimentRel
			getSegmentsExperimentRel(long segmentsExperimentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentRelLocalService.getSegmentsExperimentRel(
			segmentsExperimentRelId);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperimentRel
			getSegmentsExperimentRel(
				long segmentsExperimentId, long segmentsExperienceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentRelLocalService.getSegmentsExperimentRel(
			segmentsExperimentId, segmentsExperienceId);
	}

	/**
	 * Returns a range of all the segments experiment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsExperimentRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments experiment rels
	 * @param end the upper bound of the range of segments experiment rels (not inclusive)
	 * @return the range of segments experiment rels
	 */
	@Override
	public java.util.List<com.liferay.segments.model.SegmentsExperimentRel>
		getSegmentsExperimentRels(int start, int end) {

		return _segmentsExperimentRelLocalService.getSegmentsExperimentRels(
			start, end);
	}

	@Override
	public java.util.List<com.liferay.segments.model.SegmentsExperimentRel>
		getSegmentsExperimentRels(long segmentsExperimentId) {

		return _segmentsExperimentRelLocalService.getSegmentsExperimentRels(
			segmentsExperimentId);
	}

	/**
	 * Returns the number of segments experiment rels.
	 *
	 * @return the number of segments experiment rels
	 */
	@Override
	public int getSegmentsExperimentRelsCount() {
		return _segmentsExperimentRelLocalService.
			getSegmentsExperimentRelsCount();
	}

	@Override
	public com.liferay.segments.model.SegmentsExperimentRel
			updateSegmentsExperimentRel(
				long segmentsExperimentRelId, double split)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentRelLocalService.updateSegmentsExperimentRel(
			segmentsExperimentRelId, split);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperimentRel
			updateSegmentsExperimentRel(
				long segmentsExperimentId, long segmentsExperienceId,
				double split)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentRelLocalService.updateSegmentsExperimentRel(
			segmentsExperimentId, segmentsExperienceId, split);
	}

	@Override
	public com.liferay.segments.model.SegmentsExperimentRel
			updateSegmentsExperimentRel(
				long segmentsExperimentRelId, String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsExperimentRelLocalService.updateSegmentsExperimentRel(
			segmentsExperimentRelId, name, serviceContext);
	}

	/**
	 * Updates the segments experiment rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsExperimentRel the segments experiment rel
	 * @return the segments experiment rel that was updated
	 */
	@Override
	public com.liferay.segments.model.SegmentsExperimentRel
		updateSegmentsExperimentRel(
			com.liferay.segments.model.SegmentsExperimentRel
				segmentsExperimentRel) {

		return _segmentsExperimentRelLocalService.updateSegmentsExperimentRel(
			segmentsExperimentRel);
	}

	@Override
	public SegmentsExperimentRelLocalService getWrappedService() {
		return _segmentsExperimentRelLocalService;
	}

	@Override
	public void setWrappedService(
		SegmentsExperimentRelLocalService segmentsExperimentRelLocalService) {

		_segmentsExperimentRelLocalService = segmentsExperimentRelLocalService;
	}

	private SegmentsExperimentRelLocalService
		_segmentsExperimentRelLocalService;

}