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
 * Provides a wrapper for {@link CacheFieldEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CacheFieldEntryLocalService
 * @generated
 */
public class CacheFieldEntryLocalServiceWrapper
	implements CacheFieldEntryLocalService,
			   ServiceWrapper<CacheFieldEntryLocalService> {

	public CacheFieldEntryLocalServiceWrapper() {
		this(null);
	}

	public CacheFieldEntryLocalServiceWrapper(
		CacheFieldEntryLocalService cacheFieldEntryLocalService) {

		_cacheFieldEntryLocalService = cacheFieldEntryLocalService;
	}

	/**
	 * Adds the cache field entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CacheFieldEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cacheFieldEntry the cache field entry
	 * @return the cache field entry that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.CacheFieldEntry
		addCacheFieldEntry(
			com.liferay.portal.tools.service.builder.test.model.CacheFieldEntry
				cacheFieldEntry) {

		return _cacheFieldEntryLocalService.addCacheFieldEntry(cacheFieldEntry);
	}

	/**
	 * Creates a new cache field entry with the primary key. Does not add the cache field entry to the database.
	 *
	 * @param cacheFieldEntryId the primary key for the new cache field entry
	 * @return the new cache field entry
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.CacheFieldEntry
		createCacheFieldEntry(long cacheFieldEntryId) {

		return _cacheFieldEntryLocalService.createCacheFieldEntry(
			cacheFieldEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheFieldEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the cache field entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CacheFieldEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cacheFieldEntry the cache field entry
	 * @return the cache field entry that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.CacheFieldEntry
		deleteCacheFieldEntry(
			com.liferay.portal.tools.service.builder.test.model.CacheFieldEntry
				cacheFieldEntry) {

		return _cacheFieldEntryLocalService.deleteCacheFieldEntry(
			cacheFieldEntry);
	}

	/**
	 * Deletes the cache field entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CacheFieldEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cacheFieldEntryId the primary key of the cache field entry
	 * @return the cache field entry that was removed
	 * @throws PortalException if a cache field entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.CacheFieldEntry
			deleteCacheFieldEntry(long cacheFieldEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheFieldEntryLocalService.deleteCacheFieldEntry(
			cacheFieldEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheFieldEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _cacheFieldEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _cacheFieldEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cacheFieldEntryLocalService.dynamicQuery();
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

		return _cacheFieldEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.CacheFieldEntryModelImpl</code>.
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

		return _cacheFieldEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.CacheFieldEntryModelImpl</code>.
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

		return _cacheFieldEntryLocalService.dynamicQuery(
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

		return _cacheFieldEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _cacheFieldEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.CacheFieldEntry
		fetchCacheFieldEntry(long cacheFieldEntryId) {

		return _cacheFieldEntryLocalService.fetchCacheFieldEntry(
			cacheFieldEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _cacheFieldEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the cache field entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.CacheFieldEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache field entries
	 * @param end the upper bound of the range of cache field entries (not inclusive)
	 * @return the range of cache field entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.CacheFieldEntry>
			getCacheFieldEntries(int start, int end) {

		return _cacheFieldEntryLocalService.getCacheFieldEntries(start, end);
	}

	/**
	 * Returns the number of cache field entries.
	 *
	 * @return the number of cache field entries
	 */
	@Override
	public int getCacheFieldEntriesCount() {
		return _cacheFieldEntryLocalService.getCacheFieldEntriesCount();
	}

	/**
	 * Returns the cache field entry with the primary key.
	 *
	 * @param cacheFieldEntryId the primary key of the cache field entry
	 * @return the cache field entry
	 * @throws PortalException if a cache field entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.CacheFieldEntry
			getCacheFieldEntry(long cacheFieldEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheFieldEntryLocalService.getCacheFieldEntry(
			cacheFieldEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _cacheFieldEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cacheFieldEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheFieldEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the cache field entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CacheFieldEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cacheFieldEntry the cache field entry
	 * @return the cache field entry that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.CacheFieldEntry
		updateCacheFieldEntry(
			com.liferay.portal.tools.service.builder.test.model.CacheFieldEntry
				cacheFieldEntry) {

		return _cacheFieldEntryLocalService.updateCacheFieldEntry(
			cacheFieldEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cacheFieldEntryLocalService.getBasePersistence();
	}

	@Override
	public CacheFieldEntryLocalService getWrappedService() {
		return _cacheFieldEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CacheFieldEntryLocalService cacheFieldEntryLocalService) {

		_cacheFieldEntryLocalService = cacheFieldEntryLocalService;
	}

	private CacheFieldEntryLocalService _cacheFieldEntryLocalService;

}