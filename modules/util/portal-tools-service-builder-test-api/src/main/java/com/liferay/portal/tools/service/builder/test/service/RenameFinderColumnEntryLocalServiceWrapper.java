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

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link RenameFinderColumnEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see RenameFinderColumnEntryLocalService
 * @generated
 */
public class RenameFinderColumnEntryLocalServiceWrapper
	implements RenameFinderColumnEntryLocalService,
			   ServiceWrapper<RenameFinderColumnEntryLocalService> {

	public RenameFinderColumnEntryLocalServiceWrapper() {
		this(null);
	}

	public RenameFinderColumnEntryLocalServiceWrapper(
		RenameFinderColumnEntryLocalService
			renameFinderColumnEntryLocalService) {

		_renameFinderColumnEntryLocalService =
			renameFinderColumnEntryLocalService;
	}

	/**
	 * Adds the rename finder column entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RenameFinderColumnEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param renameFinderColumnEntry the rename finder column entry
	 * @return the rename finder column entry that was added
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			RenameFinderColumnEntry addRenameFinderColumnEntry(
				com.liferay.portal.tools.service.builder.test.model.
					RenameFinderColumnEntry renameFinderColumnEntry) {

		return _renameFinderColumnEntryLocalService.addRenameFinderColumnEntry(
			renameFinderColumnEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _renameFinderColumnEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new rename finder column entry with the primary key. Does not add the rename finder column entry to the database.
	 *
	 * @param renameFinderColumnEntryId the primary key for the new rename finder column entry
	 * @return the new rename finder column entry
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			RenameFinderColumnEntry createRenameFinderColumnEntry(
				long renameFinderColumnEntryId) {

		return _renameFinderColumnEntryLocalService.
			createRenameFinderColumnEntry(renameFinderColumnEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _renameFinderColumnEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the rename finder column entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RenameFinderColumnEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param renameFinderColumnEntryId the primary key of the rename finder column entry
	 * @return the rename finder column entry that was removed
	 * @throws PortalException if a rename finder column entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			RenameFinderColumnEntry deleteRenameFinderColumnEntry(
					long renameFinderColumnEntryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _renameFinderColumnEntryLocalService.
			deleteRenameFinderColumnEntry(renameFinderColumnEntryId);
	}

	/**
	 * Deletes the rename finder column entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RenameFinderColumnEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param renameFinderColumnEntry the rename finder column entry
	 * @return the rename finder column entry that was removed
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			RenameFinderColumnEntry deleteRenameFinderColumnEntry(
				com.liferay.portal.tools.service.builder.test.model.
					RenameFinderColumnEntry renameFinderColumnEntry) {

		return _renameFinderColumnEntryLocalService.
			deleteRenameFinderColumnEntry(renameFinderColumnEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _renameFinderColumnEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _renameFinderColumnEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _renameFinderColumnEntryLocalService.dynamicQuery();
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

		return _renameFinderColumnEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.RenameFinderColumnEntryModelImpl</code>.
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

		return _renameFinderColumnEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.RenameFinderColumnEntryModelImpl</code>.
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

		return _renameFinderColumnEntryLocalService.dynamicQuery(
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

		return _renameFinderColumnEntryLocalService.dynamicQueryCount(
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

		return _renameFinderColumnEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			RenameFinderColumnEntry fetchRenameFinderColumnEntry(
				long renameFinderColumnEntryId) {

		return _renameFinderColumnEntryLocalService.
			fetchRenameFinderColumnEntry(renameFinderColumnEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _renameFinderColumnEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _renameFinderColumnEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _renameFinderColumnEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _renameFinderColumnEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Returns a range of all the rename finder column entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.RenameFinderColumnEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of rename finder column entries
	 * @param end the upper bound of the range of rename finder column entries (not inclusive)
	 * @return the range of rename finder column entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.
			RenameFinderColumnEntry> getRenameFinderColumnEntries(
				int start, int end) {

		return _renameFinderColumnEntryLocalService.
			getRenameFinderColumnEntries(start, end);
	}

	/**
	 * Returns the number of rename finder column entries.
	 *
	 * @return the number of rename finder column entries
	 */
	@Override
	public int getRenameFinderColumnEntriesCount() {
		return _renameFinderColumnEntryLocalService.
			getRenameFinderColumnEntriesCount();
	}

	/**
	 * Returns the rename finder column entry with the primary key.
	 *
	 * @param renameFinderColumnEntryId the primary key of the rename finder column entry
	 * @return the rename finder column entry
	 * @throws PortalException if a rename finder column entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			RenameFinderColumnEntry getRenameFinderColumnEntry(
					long renameFinderColumnEntryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _renameFinderColumnEntryLocalService.getRenameFinderColumnEntry(
			renameFinderColumnEntryId);
	}

	/**
	 * Updates the rename finder column entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RenameFinderColumnEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param renameFinderColumnEntry the rename finder column entry
	 * @return the rename finder column entry that was updated
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.
			RenameFinderColumnEntry updateRenameFinderColumnEntry(
				com.liferay.portal.tools.service.builder.test.model.
					RenameFinderColumnEntry renameFinderColumnEntry) {

		return _renameFinderColumnEntryLocalService.
			updateRenameFinderColumnEntry(renameFinderColumnEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _renameFinderColumnEntryLocalService.getBasePersistence();
	}

	@Override
	public RenameFinderColumnEntryLocalService getWrappedService() {
		return _renameFinderColumnEntryLocalService;
	}

	@Override
	public void setWrappedService(
		RenameFinderColumnEntryLocalService
			renameFinderColumnEntryLocalService) {

		_renameFinderColumnEntryLocalService =
			renameFinderColumnEntryLocalService;
	}

	private RenameFinderColumnEntryLocalService
		_renameFinderColumnEntryLocalService;

}