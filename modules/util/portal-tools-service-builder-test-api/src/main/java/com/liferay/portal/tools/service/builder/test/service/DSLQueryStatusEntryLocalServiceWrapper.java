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
 * Provides a wrapper for {@link DSLQueryStatusEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DSLQueryStatusEntryLocalService
 * @generated
 */
public class DSLQueryStatusEntryLocalServiceWrapper
	implements DSLQueryStatusEntryLocalService,
			   ServiceWrapper<DSLQueryStatusEntryLocalService> {

	public DSLQueryStatusEntryLocalServiceWrapper() {
		this(null);
	}

	public DSLQueryStatusEntryLocalServiceWrapper(
		DSLQueryStatusEntryLocalService dslQueryStatusEntryLocalService) {

		_dslQueryStatusEntryLocalService = dslQueryStatusEntryLocalService;
	}

	/**
	 * Adds the dsl query status entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DSLQueryStatusEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dslQueryStatusEntry the dsl query status entry
	 * @return the dsl query status entry that was added
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.DSLQueryStatusEntry
			addDSLQueryStatusEntry(
				com.liferay.portal.tools.service.builder.test.model.
					DSLQueryStatusEntry dslQueryStatusEntry) {

		return _dslQueryStatusEntryLocalService.addDSLQueryStatusEntry(
			dslQueryStatusEntry);
	}

	/**
	 * Creates a new dsl query status entry with the primary key. Does not add the dsl query status entry to the database.
	 *
	 * @param dslQueryStatusEntryId the primary key for the new dsl query status entry
	 * @return the new dsl query status entry
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.DSLQueryStatusEntry
			createDSLQueryStatusEntry(long dslQueryStatusEntryId) {

		return _dslQueryStatusEntryLocalService.createDSLQueryStatusEntry(
			dslQueryStatusEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dslQueryStatusEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the dsl query status entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DSLQueryStatusEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dslQueryStatusEntry the dsl query status entry
	 * @return the dsl query status entry that was removed
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.DSLQueryStatusEntry
			deleteDSLQueryStatusEntry(
				com.liferay.portal.tools.service.builder.test.model.
					DSLQueryStatusEntry dslQueryStatusEntry) {

		return _dslQueryStatusEntryLocalService.deleteDSLQueryStatusEntry(
			dslQueryStatusEntry);
	}

	/**
	 * Deletes the dsl query status entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DSLQueryStatusEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dslQueryStatusEntryId the primary key of the dsl query status entry
	 * @return the dsl query status entry that was removed
	 * @throws PortalException if a dsl query status entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.DSLQueryStatusEntry
				deleteDSLQueryStatusEntry(long dslQueryStatusEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _dslQueryStatusEntryLocalService.deleteDSLQueryStatusEntry(
			dslQueryStatusEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dslQueryStatusEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _dslQueryStatusEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _dslQueryStatusEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _dslQueryStatusEntryLocalService.dynamicQuery();
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

		return _dslQueryStatusEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DSLQueryStatusEntryModelImpl</code>.
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

		return _dslQueryStatusEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DSLQueryStatusEntryModelImpl</code>.
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

		return _dslQueryStatusEntryLocalService.dynamicQuery(
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

		return _dslQueryStatusEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _dslQueryStatusEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.DSLQueryStatusEntry
			fetchDSLQueryStatusEntry(long dslQueryStatusEntryId) {

		return _dslQueryStatusEntryLocalService.fetchDSLQueryStatusEntry(
			dslQueryStatusEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _dslQueryStatusEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the dsl query status entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DSLQueryStatusEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dsl query status entries
	 * @param end the upper bound of the range of dsl query status entries (not inclusive)
	 * @return the range of dsl query status entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.
			DSLQueryStatusEntry> getDSLQueryStatusEntries(int start, int end) {

		return _dslQueryStatusEntryLocalService.getDSLQueryStatusEntries(
			start, end);
	}

	/**
	 * Returns the number of dsl query status entries.
	 *
	 * @return the number of dsl query status entries
	 */
	@Override
	public int getDSLQueryStatusEntriesCount() {
		return _dslQueryStatusEntryLocalService.getDSLQueryStatusEntriesCount();
	}

	/**
	 * Returns the dsl query status entry with the primary key.
	 *
	 * @param dslQueryStatusEntryId the primary key of the dsl query status entry
	 * @return the dsl query status entry
	 * @throws PortalException if a dsl query status entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.DSLQueryStatusEntry
				getDSLQueryStatusEntry(long dslQueryStatusEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _dslQueryStatusEntryLocalService.getDSLQueryStatusEntry(
			dslQueryStatusEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _dslQueryStatusEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _dslQueryStatusEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dslQueryStatusEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the dsl query status entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DSLQueryStatusEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param dslQueryStatusEntry the dsl query status entry
	 * @return the dsl query status entry that was updated
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.DSLQueryStatusEntry
			updateDSLQueryStatusEntry(
				com.liferay.portal.tools.service.builder.test.model.
					DSLQueryStatusEntry dslQueryStatusEntry) {

		return _dslQueryStatusEntryLocalService.updateDSLQueryStatusEntry(
			dslQueryStatusEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _dslQueryStatusEntryLocalService.getBasePersistence();
	}

	@Override
	public DSLQueryStatusEntryLocalService getWrappedService() {
		return _dslQueryStatusEntryLocalService;
	}

	@Override
	public void setWrappedService(
		DSLQueryStatusEntryLocalService dslQueryStatusEntryLocalService) {

		_dslQueryStatusEntryLocalService = dslQueryStatusEntryLocalService;
	}

	private DSLQueryStatusEntryLocalService _dslQueryStatusEntryLocalService;

}