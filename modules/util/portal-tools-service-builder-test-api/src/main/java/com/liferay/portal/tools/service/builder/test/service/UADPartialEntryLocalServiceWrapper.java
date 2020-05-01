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

/**
 * Provides a wrapper for {@link UADPartialEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see UADPartialEntryLocalService
 * @generated
 */
public class UADPartialEntryLocalServiceWrapper
	implements ServiceWrapper<UADPartialEntryLocalService>,
			   UADPartialEntryLocalService {

	public UADPartialEntryLocalServiceWrapper(
		UADPartialEntryLocalService uadPartialEntryLocalService) {

		_uadPartialEntryLocalService = uadPartialEntryLocalService;
	}

	/**
	 * Adds the uad partial entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param uadPartialEntry the uad partial entry
	 * @return the uad partial entry that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.UADPartialEntry
		addUADPartialEntry(
			com.liferay.portal.tools.service.builder.test.model.UADPartialEntry
				uadPartialEntry) {

		return _uadPartialEntryLocalService.addUADPartialEntry(uadPartialEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _uadPartialEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new uad partial entry with the primary key. Does not add the uad partial entry to the database.
	 *
	 * @param uadPartialEntryId the primary key for the new uad partial entry
	 * @return the new uad partial entry
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.UADPartialEntry
		createUADPartialEntry(long uadPartialEntryId) {

		return _uadPartialEntryLocalService.createUADPartialEntry(
			uadPartialEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _uadPartialEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the uad partial entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param uadPartialEntryId the primary key of the uad partial entry
	 * @return the uad partial entry that was removed
	 * @throws PortalException if a uad partial entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.UADPartialEntry
			deleteUADPartialEntry(long uadPartialEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _uadPartialEntryLocalService.deleteUADPartialEntry(
			uadPartialEntryId);
	}

	/**
	 * Deletes the uad partial entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param uadPartialEntry the uad partial entry
	 * @return the uad partial entry that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.UADPartialEntry
		deleteUADPartialEntry(
			com.liferay.portal.tools.service.builder.test.model.UADPartialEntry
				uadPartialEntry) {

		return _uadPartialEntryLocalService.deleteUADPartialEntry(
			uadPartialEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _uadPartialEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _uadPartialEntryLocalService.dynamicQuery();
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

		return _uadPartialEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.UADPartialEntryModelImpl</code>.
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

		return _uadPartialEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.UADPartialEntryModelImpl</code>.
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

		return _uadPartialEntryLocalService.dynamicQuery(
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

		return _uadPartialEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _uadPartialEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.UADPartialEntry
		fetchUADPartialEntry(long uadPartialEntryId) {

		return _uadPartialEntryLocalService.fetchUADPartialEntry(
			uadPartialEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _uadPartialEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _uadPartialEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _uadPartialEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _uadPartialEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the uad partial entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.UADPartialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of uad partial entries
	 * @param end the upper bound of the range of uad partial entries (not inclusive)
	 * @return the range of uad partial entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.UADPartialEntry>
			getUADPartialEntries(int start, int end) {

		return _uadPartialEntryLocalService.getUADPartialEntries(start, end);
	}

	/**
	 * Returns the number of uad partial entries.
	 *
	 * @return the number of uad partial entries
	 */
	@Override
	public int getUADPartialEntriesCount() {
		return _uadPartialEntryLocalService.getUADPartialEntriesCount();
	}

	/**
	 * Returns the uad partial entry with the primary key.
	 *
	 * @param uadPartialEntryId the primary key of the uad partial entry
	 * @return the uad partial entry
	 * @throws PortalException if a uad partial entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.UADPartialEntry
			getUADPartialEntry(long uadPartialEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _uadPartialEntryLocalService.getUADPartialEntry(
			uadPartialEntryId);
	}

	/**
	 * Updates the uad partial entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param uadPartialEntry the uad partial entry
	 * @return the uad partial entry that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.UADPartialEntry
		updateUADPartialEntry(
			com.liferay.portal.tools.service.builder.test.model.UADPartialEntry
				uadPartialEntry) {

		return _uadPartialEntryLocalService.updateUADPartialEntry(
			uadPartialEntry);
	}

	@Override
	public UADPartialEntryLocalService getWrappedService() {
		return _uadPartialEntryLocalService;
	}

	@Override
	public void setWrappedService(
		UADPartialEntryLocalService uadPartialEntryLocalService) {

		_uadPartialEntryLocalService = uadPartialEntryLocalService;
	}

	private UADPartialEntryLocalService _uadPartialEntryLocalService;

}