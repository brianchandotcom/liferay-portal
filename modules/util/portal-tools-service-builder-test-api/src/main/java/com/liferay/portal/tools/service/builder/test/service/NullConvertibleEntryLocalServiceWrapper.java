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
 * Provides a wrapper for {@link NullConvertibleEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see NullConvertibleEntryLocalService
 * @generated
 */
public class NullConvertibleEntryLocalServiceWrapper
	implements NullConvertibleEntryLocalService,
			   ServiceWrapper<NullConvertibleEntryLocalService> {

	public NullConvertibleEntryLocalServiceWrapper() {
		this(null);
	}

	public NullConvertibleEntryLocalServiceWrapper(
		NullConvertibleEntryLocalService nullConvertibleEntryLocalService) {

		_nullConvertibleEntryLocalService = nullConvertibleEntryLocalService;
	}

	/**
	 * Adds the null convertible entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NullConvertibleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param nullConvertibleEntry the null convertible entry
	 * @return the null convertible entry that was added
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.NullConvertibleEntry
			addNullConvertibleEntry(
				com.liferay.portal.tools.service.builder.test.model.
					NullConvertibleEntry nullConvertibleEntry) {

		return _nullConvertibleEntryLocalService.addNullConvertibleEntry(
			nullConvertibleEntry);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.NullConvertibleEntry
			addNullConvertibleEntry(String name) {

		return _nullConvertibleEntryLocalService.addNullConvertibleEntry(name);
	}

	/**
	 * Creates a new null convertible entry with the primary key. Does not add the null convertible entry to the database.
	 *
	 * @param nullConvertibleEntryId the primary key for the new null convertible entry
	 * @return the new null convertible entry
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.NullConvertibleEntry
			createNullConvertibleEntry(long nullConvertibleEntryId) {

		return _nullConvertibleEntryLocalService.createNullConvertibleEntry(
			nullConvertibleEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _nullConvertibleEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the null convertible entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NullConvertibleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param nullConvertibleEntryId the primary key of the null convertible entry
	 * @return the null convertible entry that was removed
	 * @throws PortalException if a null convertible entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.NullConvertibleEntry
				deleteNullConvertibleEntry(long nullConvertibleEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _nullConvertibleEntryLocalService.deleteNullConvertibleEntry(
			nullConvertibleEntryId);
	}

	/**
	 * Deletes the null convertible entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NullConvertibleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param nullConvertibleEntry the null convertible entry
	 * @return the null convertible entry that was removed
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.NullConvertibleEntry
			deleteNullConvertibleEntry(
				com.liferay.portal.tools.service.builder.test.model.
					NullConvertibleEntry nullConvertibleEntry) {

		return _nullConvertibleEntryLocalService.deleteNullConvertibleEntry(
			nullConvertibleEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _nullConvertibleEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _nullConvertibleEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _nullConvertibleEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _nullConvertibleEntryLocalService.dynamicQuery();
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

		return _nullConvertibleEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.NullConvertibleEntryModelImpl</code>.
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

		return _nullConvertibleEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.NullConvertibleEntryModelImpl</code>.
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

		return _nullConvertibleEntryLocalService.dynamicQuery(
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

		return _nullConvertibleEntryLocalService.dynamicQueryCount(
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

		return _nullConvertibleEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.NullConvertibleEntry
			fetchNullConvertibleEntry(long nullConvertibleEntryId) {

		return _nullConvertibleEntryLocalService.fetchNullConvertibleEntry(
			nullConvertibleEntryId);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.NullConvertibleEntry
			fetchNullConvertibleEntry(String name) {

		return _nullConvertibleEntryLocalService.fetchNullConvertibleEntry(
			name);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _nullConvertibleEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _nullConvertibleEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the null convertible entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.NullConvertibleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of null convertible entries
	 * @param end the upper bound of the range of null convertible entries (not inclusive)
	 * @return the range of null convertible entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.
			NullConvertibleEntry> getNullConvertibleEntries(
				int start, int end) {

		return _nullConvertibleEntryLocalService.getNullConvertibleEntries(
			start, end);
	}

	@Override
	public int getNullConvertibleEntries(String name) {
		return _nullConvertibleEntryLocalService.getNullConvertibleEntries(
			name);
	}

	/**
	 * Returns the number of null convertible entries.
	 *
	 * @return the number of null convertible entries
	 */
	@Override
	public int getNullConvertibleEntriesCount() {
		return _nullConvertibleEntryLocalService.
			getNullConvertibleEntriesCount();
	}

	/**
	 * Returns the null convertible entry with the primary key.
	 *
	 * @param nullConvertibleEntryId the primary key of the null convertible entry
	 * @return the null convertible entry
	 * @throws PortalException if a null convertible entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.NullConvertibleEntry
				getNullConvertibleEntry(long nullConvertibleEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _nullConvertibleEntryLocalService.getNullConvertibleEntry(
			nullConvertibleEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _nullConvertibleEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _nullConvertibleEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the null convertible entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NullConvertibleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param nullConvertibleEntry the null convertible entry
	 * @return the null convertible entry that was updated
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.NullConvertibleEntry
			updateNullConvertibleEntry(
				com.liferay.portal.tools.service.builder.test.model.
					NullConvertibleEntry nullConvertibleEntry) {

		return _nullConvertibleEntryLocalService.updateNullConvertibleEntry(
			nullConvertibleEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _nullConvertibleEntryLocalService.getBasePersistence();
	}

	@Override
	public NullConvertibleEntryLocalService getWrappedService() {
		return _nullConvertibleEntryLocalService;
	}

	@Override
	public void setWrappedService(
		NullConvertibleEntryLocalService nullConvertibleEntryLocalService) {

		_nullConvertibleEntryLocalService = nullConvertibleEntryLocalService;
	}

	private NullConvertibleEntryLocalService _nullConvertibleEntryLocalService;

}