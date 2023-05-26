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
 * Provides a wrapper for {@link RedundantIndexEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see RedundantIndexEntryLocalService
 * @generated
 */
public class RedundantIndexEntryLocalServiceWrapper
	implements RedundantIndexEntryLocalService,
			   ServiceWrapper<RedundantIndexEntryLocalService> {

	public RedundantIndexEntryLocalServiceWrapper() {
		this(null);
	}

	public RedundantIndexEntryLocalServiceWrapper(
		RedundantIndexEntryLocalService redundantIndexEntryLocalService) {

		_redundantIndexEntryLocalService = redundantIndexEntryLocalService;
	}

	/**
	 * Adds the redundant index entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RedundantIndexEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param redundantIndexEntry the redundant index entry
	 * @return the redundant index entry that was added
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.RedundantIndexEntry
			addRedundantIndexEntry(
				com.liferay.portal.tools.service.builder.test.model.
					RedundantIndexEntry redundantIndexEntry) {

		return _redundantIndexEntryLocalService.addRedundantIndexEntry(
			redundantIndexEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _redundantIndexEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new redundant index entry with the primary key. Does not add the redundant index entry to the database.
	 *
	 * @param redundantIndexEntryId the primary key for the new redundant index entry
	 * @return the new redundant index entry
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.RedundantIndexEntry
			createRedundantIndexEntry(long redundantIndexEntryId) {

		return _redundantIndexEntryLocalService.createRedundantIndexEntry(
			redundantIndexEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _redundantIndexEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the redundant index entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RedundantIndexEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param redundantIndexEntryId the primary key of the redundant index entry
	 * @return the redundant index entry that was removed
	 * @throws PortalException if a redundant index entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.RedundantIndexEntry
				deleteRedundantIndexEntry(long redundantIndexEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _redundantIndexEntryLocalService.deleteRedundantIndexEntry(
			redundantIndexEntryId);
	}

	/**
	 * Deletes the redundant index entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RedundantIndexEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param redundantIndexEntry the redundant index entry
	 * @return the redundant index entry that was removed
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.RedundantIndexEntry
			deleteRedundantIndexEntry(
				com.liferay.portal.tools.service.builder.test.model.
					RedundantIndexEntry redundantIndexEntry) {

		return _redundantIndexEntryLocalService.deleteRedundantIndexEntry(
			redundantIndexEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _redundantIndexEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _redundantIndexEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _redundantIndexEntryLocalService.dynamicQuery();
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

		return _redundantIndexEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.RedundantIndexEntryModelImpl</code>.
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

		return _redundantIndexEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.RedundantIndexEntryModelImpl</code>.
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

		return _redundantIndexEntryLocalService.dynamicQuery(
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

		return _redundantIndexEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _redundantIndexEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.RedundantIndexEntry
			fetchRedundantIndexEntry(long redundantIndexEntryId) {

		return _redundantIndexEntryLocalService.fetchRedundantIndexEntry(
			redundantIndexEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _redundantIndexEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _redundantIndexEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _redundantIndexEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _redundantIndexEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Returns a range of all the redundant index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.RedundantIndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of redundant index entries
	 * @param end the upper bound of the range of redundant index entries (not inclusive)
	 * @return the range of redundant index entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.
			RedundantIndexEntry> getRedundantIndexEntries(int start, int end) {

		return _redundantIndexEntryLocalService.getRedundantIndexEntries(
			start, end);
	}

	/**
	 * Returns the number of redundant index entries.
	 *
	 * @return the number of redundant index entries
	 */
	@Override
	public int getRedundantIndexEntriesCount() {
		return _redundantIndexEntryLocalService.getRedundantIndexEntriesCount();
	}

	/**
	 * Returns the redundant index entry with the primary key.
	 *
	 * @param redundantIndexEntryId the primary key of the redundant index entry
	 * @return the redundant index entry
	 * @throws PortalException if a redundant index entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.RedundantIndexEntry
				getRedundantIndexEntry(long redundantIndexEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _redundantIndexEntryLocalService.getRedundantIndexEntry(
			redundantIndexEntryId);
	}

	/**
	 * Updates the redundant index entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RedundantIndexEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param redundantIndexEntry the redundant index entry
	 * @return the redundant index entry that was updated
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.RedundantIndexEntry
			updateRedundantIndexEntry(
				com.liferay.portal.tools.service.builder.test.model.
					RedundantIndexEntry redundantIndexEntry) {

		return _redundantIndexEntryLocalService.updateRedundantIndexEntry(
			redundantIndexEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _redundantIndexEntryLocalService.getBasePersistence();
	}

	@Override
	public RedundantIndexEntryLocalService getWrappedService() {
		return _redundantIndexEntryLocalService;
	}

	@Override
	public void setWrappedService(
		RedundantIndexEntryLocalService redundantIndexEntryLocalService) {

		_redundantIndexEntryLocalService = redundantIndexEntryLocalService;
	}

	private RedundantIndexEntryLocalService _redundantIndexEntryLocalService;

}