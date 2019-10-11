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
 * Provides a wrapper for {@link SegmentsEntryRelLocalService}.
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryRelLocalService
 * @generated
 */
public class SegmentsEntryRelLocalServiceWrapper
	implements SegmentsEntryRelLocalService,
			   ServiceWrapper<SegmentsEntryRelLocalService> {

	public SegmentsEntryRelLocalServiceWrapper(
		SegmentsEntryRelLocalService segmentsEntryRelLocalService) {

		_segmentsEntryRelLocalService = segmentsEntryRelLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SegmentsEntryRelLocalServiceUtil} to access the segments entry rel local service. Add custom service methods to <code>com.liferay.segments.service.impl.SegmentsEntryRelLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.segments.model.SegmentsEntryRel addSegmentsEntryRel(
			long segmentsEntryId, long classNameId, long classPK,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsEntryRelLocalService.addSegmentsEntryRel(
			segmentsEntryId, classNameId, classPK, serviceContext);
	}

	/**
	 * Adds the segments entry rel to the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntryRel the segments entry rel
	 * @return the segments entry rel that was added
	 */
	@Override
	public com.liferay.segments.model.SegmentsEntryRel addSegmentsEntryRel(
		com.liferay.segments.model.SegmentsEntryRel segmentsEntryRel) {

		return _segmentsEntryRelLocalService.addSegmentsEntryRel(
			segmentsEntryRel);
	}

	/**
	 * Creates a new segments entry rel with the primary key. Does not add the segments entry rel to the database.
	 *
	 * @param segmentsEntryRelId the primary key for the new segments entry rel
	 * @return the new segments entry rel
	 */
	@Override
	public com.liferay.segments.model.SegmentsEntryRel createSegmentsEntryRel(
		long segmentsEntryRelId) {

		return _segmentsEntryRelLocalService.createSegmentsEntryRel(
			segmentsEntryRelId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsEntryRelLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the segments entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntryRelId the primary key of the segments entry rel
	 * @return the segments entry rel that was removed
	 * @throws PortalException if a segments entry rel with the primary key could not be found
	 */
	@Override
	public com.liferay.segments.model.SegmentsEntryRel deleteSegmentsEntryRel(
			long segmentsEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsEntryRelLocalService.deleteSegmentsEntryRel(
			segmentsEntryRelId);
	}

	@Override
	public void deleteSegmentsEntryRel(
			long segmentsEntryId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		_segmentsEntryRelLocalService.deleteSegmentsEntryRel(
			segmentsEntryId, classNameId, classPK);
	}

	/**
	 * Deletes the segments entry rel from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntryRel the segments entry rel
	 * @return the segments entry rel that was removed
	 */
	@Override
	public com.liferay.segments.model.SegmentsEntryRel deleteSegmentsEntryRel(
		com.liferay.segments.model.SegmentsEntryRel segmentsEntryRel) {

		return _segmentsEntryRelLocalService.deleteSegmentsEntryRel(
			segmentsEntryRel);
	}

	@Override
	public void deleteSegmentsEntryRels(long segmentsEntryId) {
		_segmentsEntryRelLocalService.deleteSegmentsEntryRels(segmentsEntryId);
	}

	@Override
	public void deleteSegmentsEntryRels(long classNameId, long classPK) {
		_segmentsEntryRelLocalService.deleteSegmentsEntryRels(
			classNameId, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _segmentsEntryRelLocalService.dynamicQuery();
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

		return _segmentsEntryRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsEntryRelModelImpl</code>.
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

		return _segmentsEntryRelLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsEntryRelModelImpl</code>.
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

		return _segmentsEntryRelLocalService.dynamicQuery(
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

		return _segmentsEntryRelLocalService.dynamicQueryCount(dynamicQuery);
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

		return _segmentsEntryRelLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.segments.model.SegmentsEntryRel fetchSegmentsEntryRel(
		long segmentsEntryRelId) {

		return _segmentsEntryRelLocalService.fetchSegmentsEntryRel(
			segmentsEntryRelId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _segmentsEntryRelLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _segmentsEntryRelLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _segmentsEntryRelLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsEntryRelLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the segments entry rel with the primary key.
	 *
	 * @param segmentsEntryRelId the primary key of the segments entry rel
	 * @return the segments entry rel
	 * @throws PortalException if a segments entry rel with the primary key could not be found
	 */
	@Override
	public com.liferay.segments.model.SegmentsEntryRel getSegmentsEntryRel(
			long segmentsEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _segmentsEntryRelLocalService.getSegmentsEntryRel(
			segmentsEntryRelId);
	}

	/**
	 * Returns a range of all the segments entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments entry rels
	 * @param end the upper bound of the range of segments entry rels (not inclusive)
	 * @return the range of segments entry rels
	 */
	@Override
	public java.util.List<com.liferay.segments.model.SegmentsEntryRel>
		getSegmentsEntryRels(int start, int end) {

		return _segmentsEntryRelLocalService.getSegmentsEntryRels(start, end);
	}

	@Override
	public java.util.List<com.liferay.segments.model.SegmentsEntryRel>
		getSegmentsEntryRels(long segmentsEntryId) {

		return _segmentsEntryRelLocalService.getSegmentsEntryRels(
			segmentsEntryId);
	}

	@Override
	public java.util.List<com.liferay.segments.model.SegmentsEntryRel>
		getSegmentsEntryRels(
			long segmentsEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.segments.model.SegmentsEntryRel>
					orderByComparator) {

		return _segmentsEntryRelLocalService.getSegmentsEntryRels(
			segmentsEntryId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.segments.model.SegmentsEntryRel>
		getSegmentsEntryRels(long classNameId, long classPK) {

		return _segmentsEntryRelLocalService.getSegmentsEntryRels(
			classNameId, classPK);
	}

	@Override
	public java.util.List<com.liferay.segments.model.SegmentsEntryRel>
		getSegmentsEntryRels(long groupId, long classNameId, long classPK) {

		return _segmentsEntryRelLocalService.getSegmentsEntryRels(
			groupId, classNameId, classPK);
	}

	/**
	 * Returns the number of segments entry rels.
	 *
	 * @return the number of segments entry rels
	 */
	@Override
	public int getSegmentsEntryRelsCount() {
		return _segmentsEntryRelLocalService.getSegmentsEntryRelsCount();
	}

	@Override
	public int getSegmentsEntryRelsCount(long segmentsEntryId) {
		return _segmentsEntryRelLocalService.getSegmentsEntryRelsCount(
			segmentsEntryId);
	}

	@Override
	public int getSegmentsEntryRelsCount(long classNameId, long classPK) {
		return _segmentsEntryRelLocalService.getSegmentsEntryRelsCount(
			classNameId, classPK);
	}

	@Override
	public int getSegmentsEntryRelsCount(
		long groupId, long classNameId, long classPK) {

		return _segmentsEntryRelLocalService.getSegmentsEntryRelsCount(
			groupId, classNameId, classPK);
	}

	@Override
	public boolean hasSegmentsEntryRel(
		long segmentsEntryId, long classNameId, long classPK) {

		return _segmentsEntryRelLocalService.hasSegmentsEntryRel(
			segmentsEntryId, classNameId, classPK);
	}

	/**
	 * Updates the segments entry rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsEntryRel the segments entry rel
	 * @return the segments entry rel that was updated
	 */
	@Override
	public com.liferay.segments.model.SegmentsEntryRel updateSegmentsEntryRel(
		com.liferay.segments.model.SegmentsEntryRel segmentsEntryRel) {

		return _segmentsEntryRelLocalService.updateSegmentsEntryRel(
			segmentsEntryRel);
	}

	@Override
	public SegmentsEntryRelLocalService getWrappedService() {
		return _segmentsEntryRelLocalService;
	}

	@Override
	public void setWrappedService(
		SegmentsEntryRelLocalService segmentsEntryRelLocalService) {

		_segmentsEntryRelLocalService = segmentsEntryRelLocalService;
	}

	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

}