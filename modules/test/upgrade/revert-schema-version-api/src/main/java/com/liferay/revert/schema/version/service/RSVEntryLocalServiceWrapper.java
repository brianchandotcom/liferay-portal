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

package com.liferay.revert.schema.version.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link RSVEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see RSVEntryLocalService
 * @generated
 */
public class RSVEntryLocalServiceWrapper
	implements RSVEntryLocalService, ServiceWrapper<RSVEntryLocalService> {

	public RSVEntryLocalServiceWrapper(
		RSVEntryLocalService rsvEntryLocalService) {

		_rsvEntryLocalService = rsvEntryLocalService;
	}

	/**
	 * Adds the rsv entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RSVEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param rsvEntry the rsv entry
	 * @return the rsv entry that was added
	 */
	@Override
	public com.liferay.revert.schema.version.model.RSVEntry addRSVEntry(
		com.liferay.revert.schema.version.model.RSVEntry rsvEntry) {

		return _rsvEntryLocalService.addRSVEntry(rsvEntry);
	}

	/**
	 * Creates a new rsv entry with the primary key. Does not add the rsv entry to the database.
	 *
	 * @param rsvEntryId the primary key for the new rsv entry
	 * @return the new rsv entry
	 */
	@Override
	public com.liferay.revert.schema.version.model.RSVEntry createRSVEntry(
		long rsvEntryId) {

		return _rsvEntryLocalService.createRSVEntry(rsvEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _rsvEntryLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the rsv entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RSVEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param rsvEntryId the primary key of the rsv entry
	 * @return the rsv entry that was removed
	 * @throws PortalException if a rsv entry with the primary key could not be found
	 */
	@Override
	public com.liferay.revert.schema.version.model.RSVEntry deleteRSVEntry(
			long rsvEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _rsvEntryLocalService.deleteRSVEntry(rsvEntryId);
	}

	/**
	 * Deletes the rsv entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RSVEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param rsvEntry the rsv entry
	 * @return the rsv entry that was removed
	 */
	@Override
	public com.liferay.revert.schema.version.model.RSVEntry deleteRSVEntry(
		com.liferay.revert.schema.version.model.RSVEntry rsvEntry) {

		return _rsvEntryLocalService.deleteRSVEntry(rsvEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _rsvEntryLocalService.dynamicQuery();
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

		return _rsvEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.revert.schema.version.model.impl.RSVEntryModelImpl</code>.
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

		return _rsvEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.revert.schema.version.model.impl.RSVEntryModelImpl</code>.
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

		return _rsvEntryLocalService.dynamicQuery(
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

		return _rsvEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _rsvEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.revert.schema.version.model.RSVEntry fetchRSVEntry(
		long rsvEntryId) {

		return _rsvEntryLocalService.fetchRSVEntry(rsvEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _rsvEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _rsvEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _rsvEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _rsvEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the rsv entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.revert.schema.version.model.impl.RSVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rsv entries
	 * @param end the upper bound of the range of rsv entries (not inclusive)
	 * @return the range of rsv entries
	 */
	@Override
	public java.util.List<com.liferay.revert.schema.version.model.RSVEntry>
		getRSVEntries(int start, int end) {

		return _rsvEntryLocalService.getRSVEntries(start, end);
	}

	/**
	 * Returns the number of rsv entries.
	 *
	 * @return the number of rsv entries
	 */
	@Override
	public int getRSVEntriesCount() {
		return _rsvEntryLocalService.getRSVEntriesCount();
	}

	/**
	 * Returns the rsv entry with the primary key.
	 *
	 * @param rsvEntryId the primary key of the rsv entry
	 * @return the rsv entry
	 * @throws PortalException if a rsv entry with the primary key could not be found
	 */
	@Override
	public com.liferay.revert.schema.version.model.RSVEntry getRSVEntry(
			long rsvEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _rsvEntryLocalService.getRSVEntry(rsvEntryId);
	}

	/**
	 * Updates the rsv entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RSVEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param rsvEntry the rsv entry
	 * @return the rsv entry that was updated
	 */
	@Override
	public com.liferay.revert.schema.version.model.RSVEntry updateRSVEntry(
		com.liferay.revert.schema.version.model.RSVEntry rsvEntry) {

		return _rsvEntryLocalService.updateRSVEntry(rsvEntry);
	}

	@Override
	public RSVEntryLocalService getWrappedService() {
		return _rsvEntryLocalService;
	}

	@Override
	public void setWrappedService(RSVEntryLocalService rsvEntryLocalService) {
		_rsvEntryLocalService = rsvEntryLocalService;
	}

	private RSVEntryLocalService _rsvEntryLocalService;

}