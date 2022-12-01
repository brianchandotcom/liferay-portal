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
 * Provides a wrapper for {@link LoadFinderCacheEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LoadFinderCacheEntryLocalService
 * @generated
 */
public class LoadFinderCacheEntryLocalServiceWrapper
	implements LoadFinderCacheEntryLocalService,
			   ServiceWrapper<LoadFinderCacheEntryLocalService> {

	public LoadFinderCacheEntryLocalServiceWrapper() {
		this(null);
	}

	public LoadFinderCacheEntryLocalServiceWrapper(
		LoadFinderCacheEntryLocalService loadFinderCacheEntryLocalService) {

		_loadFinderCacheEntryLocalService = loadFinderCacheEntryLocalService;
	}

	/**
	 * Adds the load finder cache entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LoadFinderCacheEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param loadFinderCacheEntry the load finder cache entry
	 * @return the load finder cache entry that was added
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.LoadFinderCacheEntry
			addLoadFinderCacheEntry(
				com.liferay.portal.tools.service.builder.test.model.
					LoadFinderCacheEntry loadFinderCacheEntry) {

		return _loadFinderCacheEntryLocalService.addLoadFinderCacheEntry(
			loadFinderCacheEntry);
	}

	/**
	 * Creates a new load finder cache entry with the primary key. Does not add the load finder cache entry to the database.
	 *
	 * @param loadFinderCacheEntryId the primary key for the new load finder cache entry
	 * @return the new load finder cache entry
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.LoadFinderCacheEntry
			createLoadFinderCacheEntry(long loadFinderCacheEntryId) {

		return _loadFinderCacheEntryLocalService.createLoadFinderCacheEntry(
			loadFinderCacheEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _loadFinderCacheEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the load finder cache entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LoadFinderCacheEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param loadFinderCacheEntry the load finder cache entry
	 * @return the load finder cache entry that was removed
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.LoadFinderCacheEntry
			deleteLoadFinderCacheEntry(
				com.liferay.portal.tools.service.builder.test.model.
					LoadFinderCacheEntry loadFinderCacheEntry) {

		return _loadFinderCacheEntryLocalService.deleteLoadFinderCacheEntry(
			loadFinderCacheEntry);
	}

	/**
	 * Deletes the load finder cache entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LoadFinderCacheEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param loadFinderCacheEntryId the primary key of the load finder cache entry
	 * @return the load finder cache entry that was removed
	 * @throws PortalException if a load finder cache entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.LoadFinderCacheEntry
				deleteLoadFinderCacheEntry(long loadFinderCacheEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _loadFinderCacheEntryLocalService.deleteLoadFinderCacheEntry(
			loadFinderCacheEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _loadFinderCacheEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _loadFinderCacheEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _loadFinderCacheEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _loadFinderCacheEntryLocalService.dynamicQuery();
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

		return _loadFinderCacheEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.LoadFinderCacheEntryModelImpl</code>.
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

		return _loadFinderCacheEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.LoadFinderCacheEntryModelImpl</code>.
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

		return _loadFinderCacheEntryLocalService.dynamicQuery(
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

		return _loadFinderCacheEntryLocalService.dynamicQueryCount(
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

		return _loadFinderCacheEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.LoadFinderCacheEntry
			fetchLoadFinderCacheEntry(long loadFinderCacheEntryId) {

		return _loadFinderCacheEntryLocalService.fetchLoadFinderCacheEntry(
			loadFinderCacheEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _loadFinderCacheEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _loadFinderCacheEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the load finder cache entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.LoadFinderCacheEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of load finder cache entries
	 * @param end the upper bound of the range of load finder cache entries (not inclusive)
	 * @return the range of load finder cache entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.
			LoadFinderCacheEntry> getLoadFinderCacheEntries(
				int start, int end) {

		return _loadFinderCacheEntryLocalService.getLoadFinderCacheEntries(
			start, end);
	}

	/**
	 * Returns the number of load finder cache entries.
	 *
	 * @return the number of load finder cache entries
	 */
	@Override
	public int getLoadFinderCacheEntriesCount() {
		return _loadFinderCacheEntryLocalService.
			getLoadFinderCacheEntriesCount();
	}

	/**
	 * Returns the load finder cache entry with the primary key.
	 *
	 * @param loadFinderCacheEntryId the primary key of the load finder cache entry
	 * @return the load finder cache entry
	 * @throws PortalException if a load finder cache entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.LoadFinderCacheEntry
				getLoadFinderCacheEntry(long loadFinderCacheEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _loadFinderCacheEntryLocalService.getLoadFinderCacheEntry(
			loadFinderCacheEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _loadFinderCacheEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _loadFinderCacheEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the load finder cache entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LoadFinderCacheEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param loadFinderCacheEntry the load finder cache entry
	 * @return the load finder cache entry that was updated
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.LoadFinderCacheEntry
			updateLoadFinderCacheEntry(
				com.liferay.portal.tools.service.builder.test.model.
					LoadFinderCacheEntry loadFinderCacheEntry) {

		return _loadFinderCacheEntryLocalService.updateLoadFinderCacheEntry(
			loadFinderCacheEntry);
	}

	@Override
	public LoadFinderCacheEntryLocalService getWrappedService() {
		return _loadFinderCacheEntryLocalService;
	}

	@Override
	public void setWrappedService(
		LoadFinderCacheEntryLocalService loadFinderCacheEntryLocalService) {

		_loadFinderCacheEntryLocalService = loadFinderCacheEntryLocalService;
	}

	private LoadFinderCacheEntryLocalService _loadFinderCacheEntryLocalService;

}