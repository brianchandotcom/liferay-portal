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
 * Provides a wrapper for {@link DSLQueryEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DSLQueryEntryLocalService
 * @generated
 */
public class DSLQueryEntryLocalServiceWrapper
	implements DSLQueryEntryLocalService,
			   ServiceWrapper<DSLQueryEntryLocalService> {

	public DSLQueryEntryLocalServiceWrapper() {
		this(null);
	}

	public DSLQueryEntryLocalServiceWrapper(
		DSLQueryEntryLocalService dslQueryEntryLocalService) {

		_dslQueryEntryLocalService = dslQueryEntryLocalService;
	}

	/**
	 * Adds the dsl query entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DSLQueryEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dslQueryEntry the dsl query entry
	 * @return the dsl query entry that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.DSLQueryEntry
		addDSLQueryEntry(
			com.liferay.portal.tools.service.builder.test.model.DSLQueryEntry
				dslQueryEntry) {

		return _dslQueryEntryLocalService.addDSLQueryEntry(dslQueryEntry);
	}

	/**
	 * Creates a new dsl query entry with the primary key. Does not add the dsl query entry to the database.
	 *
	 * @param dslQueryEntryId the primary key for the new dsl query entry
	 * @return the new dsl query entry
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.DSLQueryEntry
		createDSLQueryEntry(long dslQueryEntryId) {

		return _dslQueryEntryLocalService.createDSLQueryEntry(dslQueryEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dslQueryEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the dsl query entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DSLQueryEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dslQueryEntry the dsl query entry
	 * @return the dsl query entry that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.DSLQueryEntry
		deleteDSLQueryEntry(
			com.liferay.portal.tools.service.builder.test.model.DSLQueryEntry
				dslQueryEntry) {

		return _dslQueryEntryLocalService.deleteDSLQueryEntry(dslQueryEntry);
	}

	/**
	 * Deletes the dsl query entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DSLQueryEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dslQueryEntryId the primary key of the dsl query entry
	 * @return the dsl query entry that was removed
	 * @throws PortalException if a dsl query entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.DSLQueryEntry
			deleteDSLQueryEntry(long dslQueryEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dslQueryEntryLocalService.deleteDSLQueryEntry(dslQueryEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dslQueryEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _dslQueryEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _dslQueryEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _dslQueryEntryLocalService.dynamicQuery();
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

		return _dslQueryEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DSLQueryEntryModelImpl</code>.
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

		return _dslQueryEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DSLQueryEntryModelImpl</code>.
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

		return _dslQueryEntryLocalService.dynamicQuery(
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

		return _dslQueryEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _dslQueryEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.DSLQueryEntry
		fetchDSLQueryEntry(long dslQueryEntryId) {

		return _dslQueryEntryLocalService.fetchDSLQueryEntry(dslQueryEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _dslQueryEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the dsl query entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DSLQueryEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dsl query entries
	 * @param end the upper bound of the range of dsl query entries (not inclusive)
	 * @return the range of dsl query entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.DSLQueryEntry>
			getDSLQueryEntries(int start, int end) {

		return _dslQueryEntryLocalService.getDSLQueryEntries(start, end);
	}

	/**
	 * Returns the number of dsl query entries.
	 *
	 * @return the number of dsl query entries
	 */
	@Override
	public int getDSLQueryEntriesCount() {
		return _dslQueryEntryLocalService.getDSLQueryEntriesCount();
	}

	/**
	 * Returns the dsl query entry with the primary key.
	 *
	 * @param dslQueryEntryId the primary key of the dsl query entry
	 * @return the dsl query entry
	 * @throws PortalException if a dsl query entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.DSLQueryEntry
			getDSLQueryEntry(long dslQueryEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dslQueryEntryLocalService.getDSLQueryEntry(dslQueryEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _dslQueryEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _dslQueryEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dslQueryEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the dsl query entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DSLQueryEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dslQueryEntry the dsl query entry
	 * @return the dsl query entry that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.DSLQueryEntry
		updateDSLQueryEntry(
			com.liferay.portal.tools.service.builder.test.model.DSLQueryEntry
				dslQueryEntry) {

		return _dslQueryEntryLocalService.updateDSLQueryEntry(dslQueryEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _dslQueryEntryLocalService.getBasePersistence();
	}

	@Override
	public DSLQueryEntryLocalService getWrappedService() {
		return _dslQueryEntryLocalService;
	}

	@Override
	public void setWrappedService(
		DSLQueryEntryLocalService dslQueryEntryLocalService) {

		_dslQueryEntryLocalService = dslQueryEntryLocalService;
	}

	private DSLQueryEntryLocalService _dslQueryEntryLocalService;

}