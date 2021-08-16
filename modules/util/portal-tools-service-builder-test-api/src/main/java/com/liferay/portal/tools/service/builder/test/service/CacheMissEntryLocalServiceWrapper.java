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

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.tools.service.builder.test.model.CacheMissEntry;

/**
 * Provides a wrapper for {@link CacheMissEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CacheMissEntryLocalService
 * @generated
 */
public class CacheMissEntryLocalServiceWrapper
	implements CacheMissEntryLocalService,
			   ServiceWrapper<CacheMissEntryLocalService> {

	public CacheMissEntryLocalServiceWrapper(
		CacheMissEntryLocalService cacheMissEntryLocalService) {

		_cacheMissEntryLocalService = cacheMissEntryLocalService;
	}

	/**
	 * Adds the cache miss entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CacheMissEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cacheMissEntry the cache miss entry
	 * @return the cache miss entry that was added
	 */
	@Override
	public CacheMissEntry addCacheMissEntry(CacheMissEntry cacheMissEntry) {
		return _cacheMissEntryLocalService.addCacheMissEntry(cacheMissEntry);
	}

	/**
	 * Creates a new cache miss entry with the primary key. Does not add the cache miss entry to the database.
	 *
	 * @param cacheMissEntryId the primary key for the new cache miss entry
	 * @return the new cache miss entry
	 */
	@Override
	public CacheMissEntry createCacheMissEntry(long cacheMissEntryId) {
		return _cacheMissEntryLocalService.createCacheMissEntry(
			cacheMissEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheMissEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the cache miss entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CacheMissEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cacheMissEntry the cache miss entry
	 * @return the cache miss entry that was removed
	 */
	@Override
	public CacheMissEntry deleteCacheMissEntry(CacheMissEntry cacheMissEntry) {
		return _cacheMissEntryLocalService.deleteCacheMissEntry(cacheMissEntry);
	}

	/**
	 * Deletes the cache miss entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CacheMissEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cacheMissEntryId the primary key of the cache miss entry
	 * @return the cache miss entry that was removed
	 * @throws PortalException if a cache miss entry with the primary key could not be found
	 */
	@Override
	public CacheMissEntry deleteCacheMissEntry(long cacheMissEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheMissEntryLocalService.deleteCacheMissEntry(
			cacheMissEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheMissEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _cacheMissEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _cacheMissEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cacheMissEntryLocalService.dynamicQuery();
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

		return _cacheMissEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.CacheMissEntryModelImpl</code>.
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

		return _cacheMissEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.CacheMissEntryModelImpl</code>.
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

		return _cacheMissEntryLocalService.dynamicQuery(
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

		return _cacheMissEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _cacheMissEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public CacheMissEntry fetchCacheMissEntry(long cacheMissEntryId) {
		return _cacheMissEntryLocalService.fetchCacheMissEntry(
			cacheMissEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _cacheMissEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the cache miss entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.CacheMissEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache miss entries
	 * @param end the upper bound of the range of cache miss entries (not inclusive)
	 * @return the range of cache miss entries
	 */
	@Override
	public java.util.List<CacheMissEntry> getCacheMissEntries(
		int start, int end) {

		return _cacheMissEntryLocalService.getCacheMissEntries(start, end);
	}

	/**
	 * Returns the number of cache miss entries.
	 *
	 * @return the number of cache miss entries
	 */
	@Override
	public int getCacheMissEntriesCount() {
		return _cacheMissEntryLocalService.getCacheMissEntriesCount();
	}

	/**
	 * Returns the cache miss entry with the primary key.
	 *
	 * @param cacheMissEntryId the primary key of the cache miss entry
	 * @return the cache miss entry
	 * @throws PortalException if a cache miss entry with the primary key could not be found
	 */
	@Override
	public CacheMissEntry getCacheMissEntry(long cacheMissEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheMissEntryLocalService.getCacheMissEntry(cacheMissEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _cacheMissEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cacheMissEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheMissEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the cache miss entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CacheMissEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cacheMissEntry the cache miss entry
	 * @return the cache miss entry that was updated
	 */
	@Override
	public CacheMissEntry updateCacheMissEntry(CacheMissEntry cacheMissEntry) {
		return _cacheMissEntryLocalService.updateCacheMissEntry(cacheMissEntry);
	}

	@Override
	public CTPersistence<CacheMissEntry> getCTPersistence() {
		return _cacheMissEntryLocalService.getCTPersistence();
	}

	@Override
	public Class<CacheMissEntry> getModelClass() {
		return _cacheMissEntryLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<CacheMissEntry>, R, E>
				updateUnsafeFunction)
		throws E {

		return _cacheMissEntryLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public CacheMissEntryLocalService getWrappedService() {
		return _cacheMissEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CacheMissEntryLocalService cacheMissEntryLocalService) {

		_cacheMissEntryLocalService = cacheMissEntryLocalService;
	}

	private CacheMissEntryLocalService _cacheMissEntryLocalService;

}