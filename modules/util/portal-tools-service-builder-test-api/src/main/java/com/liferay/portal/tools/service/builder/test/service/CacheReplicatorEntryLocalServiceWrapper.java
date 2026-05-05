/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link CacheReplicatorEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CacheReplicatorEntryLocalService
 * @generated
 */
public class CacheReplicatorEntryLocalServiceWrapper
	implements CacheReplicatorEntryLocalService,
			   ServiceWrapper<CacheReplicatorEntryLocalService> {

	public CacheReplicatorEntryLocalServiceWrapper() {
		this(null);
	}

	public CacheReplicatorEntryLocalServiceWrapper(
		CacheReplicatorEntryLocalService cacheReplicatorEntryLocalService) {

		_cacheReplicatorEntryLocalService = cacheReplicatorEntryLocalService;
	}

	/**
	 * Adds the cache replicator entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CacheReplicatorEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cacheReplicatorEntry the cache replicator entry
	 * @return the cache replicator entry that was added
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.CacheReplicatorEntry
			addCacheReplicatorEntry(
				com.liferay.portal.tools.service.builder.test.model.
					CacheReplicatorEntry cacheReplicatorEntry) {

		return _cacheReplicatorEntryLocalService.addCacheReplicatorEntry(
			cacheReplicatorEntry);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.CacheReplicatorEntry
			addCacheReplicatorEntry(long companyId, String name) {

		return _cacheReplicatorEntryLocalService.addCacheReplicatorEntry(
			companyId, name);
	}

	/**
	 * Creates a new cache replicator entry with the primary key. Does not add the cache replicator entry to the database.
	 *
	 * @param cacheReplicatorEntryId the primary key for the new cache replicator entry
	 * @return the new cache replicator entry
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.CacheReplicatorEntry
			createCacheReplicatorEntry(long cacheReplicatorEntryId) {

		return _cacheReplicatorEntryLocalService.createCacheReplicatorEntry(
			cacheReplicatorEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheReplicatorEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the cache replicator entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CacheReplicatorEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cacheReplicatorEntry the cache replicator entry
	 * @return the cache replicator entry that was removed
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.CacheReplicatorEntry
			deleteCacheReplicatorEntry(
				com.liferay.portal.tools.service.builder.test.model.
					CacheReplicatorEntry cacheReplicatorEntry) {

		return _cacheReplicatorEntryLocalService.deleteCacheReplicatorEntry(
			cacheReplicatorEntry);
	}

	/**
	 * Deletes the cache replicator entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CacheReplicatorEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cacheReplicatorEntryId the primary key of the cache replicator entry
	 * @return the cache replicator entry that was removed
	 * @throws PortalException if a cache replicator entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.CacheReplicatorEntry
				deleteCacheReplicatorEntry(long cacheReplicatorEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheReplicatorEntryLocalService.deleteCacheReplicatorEntry(
			cacheReplicatorEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheReplicatorEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _cacheReplicatorEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _cacheReplicatorEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cacheReplicatorEntryLocalService.dynamicQuery();
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

		return _cacheReplicatorEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.CacheReplicatorEntryModelImpl</code>.
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

		return _cacheReplicatorEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.CacheReplicatorEntryModelImpl</code>.
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

		return _cacheReplicatorEntryLocalService.dynamicQuery(
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

		return _cacheReplicatorEntryLocalService.dynamicQueryCount(
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

		return _cacheReplicatorEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.CacheReplicatorEntry
			fetchCacheReplicatorEntry(long cacheReplicatorEntryId) {

		return _cacheReplicatorEntryLocalService.fetchCacheReplicatorEntry(
			cacheReplicatorEntryId);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.CacheReplicatorEntry
			fetchCacheReplicatorEntryByName(String name) {

		return _cacheReplicatorEntryLocalService.
			fetchCacheReplicatorEntryByName(name);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _cacheReplicatorEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the cache replicator entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.CacheReplicatorEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cache replicator entries
	 * @param end the upper bound of the range of cache replicator entries (not inclusive)
	 * @return the range of cache replicator entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.
			CacheReplicatorEntry> getCacheReplicatorEntries(
				int start, int end) {

		return _cacheReplicatorEntryLocalService.getCacheReplicatorEntries(
			start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.
			CacheReplicatorEntry> getCacheReplicatorEntriesByCompanyId(
				long companyId) {

		return _cacheReplicatorEntryLocalService.
			getCacheReplicatorEntriesByCompanyId(companyId);
	}

	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.
			CacheReplicatorEntry> getCacheReplicatorEntriesByCompanyId(
				long companyId, int start, int end) {

		return _cacheReplicatorEntryLocalService.
			getCacheReplicatorEntriesByCompanyId(companyId, start, end);
	}

	/**
	 * Returns the number of cache replicator entries.
	 *
	 * @return the number of cache replicator entries
	 */
	@Override
	public int getCacheReplicatorEntriesCount() {
		return _cacheReplicatorEntryLocalService.
			getCacheReplicatorEntriesCount();
	}

	@Override
	public int getCacheReplicatorEntriesCountByCompanyId(long companyId) {
		return _cacheReplicatorEntryLocalService.
			getCacheReplicatorEntriesCountByCompanyId(companyId);
	}

	/**
	 * Returns the cache replicator entry with the primary key.
	 *
	 * @param cacheReplicatorEntryId the primary key of the cache replicator entry
	 * @return the cache replicator entry
	 * @throws PortalException if a cache replicator entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.CacheReplicatorEntry
				getCacheReplicatorEntry(long cacheReplicatorEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheReplicatorEntryLocalService.getCacheReplicatorEntry(
			cacheReplicatorEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _cacheReplicatorEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cacheReplicatorEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cacheReplicatorEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the cache replicator entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CacheReplicatorEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cacheReplicatorEntry the cache replicator entry
	 * @return the cache replicator entry that was updated
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.model.CacheReplicatorEntry
			updateCacheReplicatorEntry(
				com.liferay.portal.tools.service.builder.test.model.
					CacheReplicatorEntry cacheReplicatorEntry) {

		return _cacheReplicatorEntryLocalService.updateCacheReplicatorEntry(
			cacheReplicatorEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cacheReplicatorEntryLocalService.getBasePersistence();
	}

	@Override
	public CacheReplicatorEntryLocalService getWrappedService() {
		return _cacheReplicatorEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CacheReplicatorEntryLocalService cacheReplicatorEntryLocalService) {

		_cacheReplicatorEntryLocalService = cacheReplicatorEntryLocalService;
	}

	private CacheReplicatorEntryLocalService _cacheReplicatorEntryLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:1582792236