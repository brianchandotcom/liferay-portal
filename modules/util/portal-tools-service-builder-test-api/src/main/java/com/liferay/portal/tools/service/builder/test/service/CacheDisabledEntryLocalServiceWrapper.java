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
 * Provides a wrapper for {@link CacheDisabledEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CacheDisabledEntryLocalService
 * @generated
 */
public class CacheDisabledEntryLocalServiceWrapper
	implements CacheDisabledEntryLocalService,
			   ServiceWrapper<CacheDisabledEntryLocalService> {

	public CacheDisabledEntryLocalServiceWrapper() {
		this(null);
	}

	public CacheDisabledEntryLocalServiceWrapper(
		CacheDisabledEntryLocalService cacheDisabledEntryLocalService) {

		_cacheDisabledEntryLocalService = cacheDisabledEntryLocalService;
	}

	/**
	 * Adds the cache disabled entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CacheDisabledEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cacheDisabledEntry the cache disabled entry
	 * @return the cache disabled entry that was added
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.CacheDisabledEntry
			addCacheDisabledEntry(
				com.liferay.portal.tools.service.builder.test.model.
					CacheDisabledEntry cacheDisabledEntry) {

		return _cacheDisabledEntryLocalService.addCacheDisabledEntry(
			cacheDisabledEntry);
	}

	/**
	 * Creates a new cache disabled entry with the primary key. Does not add the cache disabled entry to the database.
	 *
	 * @param cacheDisabledEntryId the primary key for the new cache disabled entry
	 * @return the new cache disabled entry
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.CacheDisabledEntry
			createCacheDisabledEntry(long cacheDisabledEntryId) {

		return _cacheDisabledEntryLocalService.createCacheDisabledEntry(
			cacheDisabledEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheDisabledEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the cache disabled entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CacheDisabledEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cacheDisabledEntry the cache disabled entry
	 * @return the cache disabled entry that was removed
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.CacheDisabledEntry
			deleteCacheDisabledEntry(
				com.liferay.portal.tools.service.builder.test.model.
					CacheDisabledEntry cacheDisabledEntry) {

		return _cacheDisabledEntryLocalService.deleteCacheDisabledEntry(
			cacheDisabledEntry);
	}

	/**
	 * Deletes the cache disabled entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CacheDisabledEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cacheDisabledEntryId the primary key of the cache disabled entry
	 * @return the cache disabled entry that was removed
	 * @throws PortalException if a cache disabled entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.CacheDisabledEntry
				deleteCacheDisabledEntry(long cacheDisabledEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheDisabledEntryLocalService.deleteCacheDisabledEntry(
			cacheDisabledEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheDisabledEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _cacheDisabledEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _cacheDisabledEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cacheDisabledEntryLocalService.dynamicQuery();
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

		return _cacheDisabledEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.CacheDisabledEntryModelImpl</code>.
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

		return _cacheDisabledEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.CacheDisabledEntryModelImpl</code>.
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

		return _cacheDisabledEntryLocalService.dynamicQuery(
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

		return _cacheDisabledEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _cacheDisabledEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.CacheDisabledEntry
			fetchCacheDisabledEntry(long cacheDisabledEntryId) {

		return _cacheDisabledEntryLocalService.fetchCacheDisabledEntry(
			cacheDisabledEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _cacheDisabledEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the cache disabled entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.CacheDisabledEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache disabled entries
	 * @param end the upper bound of the range of cache disabled entries (not inclusive)
	 * @return the range of cache disabled entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.CacheDisabledEntry>
			getCacheDisabledEntries(int start, int end) {

		return _cacheDisabledEntryLocalService.getCacheDisabledEntries(
			start, end);
	}

	/**
	 * Returns the number of cache disabled entries.
	 *
	 * @return the number of cache disabled entries
	 */
	@Override
	public int getCacheDisabledEntriesCount() {
		return _cacheDisabledEntryLocalService.getCacheDisabledEntriesCount();
	}

	/**
	 * Returns the cache disabled entry with the primary key.
	 *
	 * @param cacheDisabledEntryId the primary key of the cache disabled entry
	 * @return the cache disabled entry
	 * @throws PortalException if a cache disabled entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.CacheDisabledEntry
				getCacheDisabledEntry(long cacheDisabledEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheDisabledEntryLocalService.getCacheDisabledEntry(
			cacheDisabledEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _cacheDisabledEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cacheDisabledEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheDisabledEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the cache disabled entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CacheDisabledEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cacheDisabledEntry the cache disabled entry
	 * @return the cache disabled entry that was updated
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.CacheDisabledEntry
			updateCacheDisabledEntry(
				com.liferay.portal.tools.service.builder.test.model.
					CacheDisabledEntry cacheDisabledEntry) {

		return _cacheDisabledEntryLocalService.updateCacheDisabledEntry(
			cacheDisabledEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cacheDisabledEntryLocalService.getBasePersistence();
	}

	@Override
	public CacheDisabledEntryLocalService getWrappedService() {
		return _cacheDisabledEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CacheDisabledEntryLocalService cacheDisabledEntryLocalService) {

		_cacheDisabledEntryLocalService = cacheDisabledEntryLocalService;
	}

	private CacheDisabledEntryLocalService _cacheDisabledEntryLocalService;

}