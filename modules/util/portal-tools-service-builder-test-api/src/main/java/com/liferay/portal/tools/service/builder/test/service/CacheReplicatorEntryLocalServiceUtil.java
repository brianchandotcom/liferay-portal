/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.CacheReplicatorEntry;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for CacheReplicatorEntry. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.service.impl.CacheReplicatorEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see CacheReplicatorEntryLocalService
 * @generated
 */
public class CacheReplicatorEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.service.impl.CacheReplicatorEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static CacheReplicatorEntry addCacheReplicatorEntry(
		CacheReplicatorEntry cacheReplicatorEntry) {

		return getService().addCacheReplicatorEntry(cacheReplicatorEntry);
	}

	public static CacheReplicatorEntry addCacheReplicatorEntry(
		long companyId, String name) {

		return getService().addCacheReplicatorEntry(companyId, name);
	}

	/**
	 * Creates a new cache replicator entry with the primary key. Does not add the cache replicator entry to the database.
	 *
	 * @param cacheReplicatorEntryId the primary key for the new cache replicator entry
	 * @return the new cache replicator entry
	 */
	public static CacheReplicatorEntry createCacheReplicatorEntry(
		long cacheReplicatorEntryId) {

		return getService().createCacheReplicatorEntry(cacheReplicatorEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
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
	public static CacheReplicatorEntry deleteCacheReplicatorEntry(
		CacheReplicatorEntry cacheReplicatorEntry) {

		return getService().deleteCacheReplicatorEntry(cacheReplicatorEntry);
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
	public static CacheReplicatorEntry deleteCacheReplicatorEntry(
			long cacheReplicatorEntryId)
		throws PortalException {

		return getService().deleteCacheReplicatorEntry(cacheReplicatorEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static CacheReplicatorEntry fetchCacheReplicatorEntry(
		long cacheReplicatorEntryId) {

		return getService().fetchCacheReplicatorEntry(cacheReplicatorEntryId);
	}

	public static CacheReplicatorEntry fetchCacheReplicatorEntry(String name) {
		return getService().fetchCacheReplicatorEntry(name);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
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
	public static List<CacheReplicatorEntry> getCacheReplicatorEntries(
		int start, int end) {

		return getService().getCacheReplicatorEntries(start, end);
	}

	public static List<CacheReplicatorEntry> getCacheReplicatorEntries(
		long companyId) {

		return getService().getCacheReplicatorEntries(companyId);
	}

	public static List<CacheReplicatorEntry> getCacheReplicatorEntries(
		long companyId, int start, int end) {

		return getService().getCacheReplicatorEntries(companyId, start, end);
	}

	/**
	 * Returns the number of cache replicator entries.
	 *
	 * @return the number of cache replicator entries
	 */
	public static int getCacheReplicatorEntriesCount() {
		return getService().getCacheReplicatorEntriesCount();
	}

	public static int getCacheReplicatorEntriesCountByCompanyId(
		long companyId) {

		return getService().getCacheReplicatorEntriesCountByCompanyId(
			companyId);
	}

	/**
	 * Returns the cache replicator entry with the primary key.
	 *
	 * @param cacheReplicatorEntryId the primary key of the cache replicator entry
	 * @return the cache replicator entry
	 * @throws PortalException if a cache replicator entry with the primary key could not be found
	 */
	public static CacheReplicatorEntry getCacheReplicatorEntry(
			long cacheReplicatorEntryId)
		throws PortalException {

		return getService().getCacheReplicatorEntry(cacheReplicatorEntryId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
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
	public static CacheReplicatorEntry updateCacheReplicatorEntry(
		CacheReplicatorEntry cacheReplicatorEntry) {

		return getService().updateCacheReplicatorEntry(cacheReplicatorEntry);
	}

	public static CacheReplicatorEntryLocalService getService() {
		return _service;
	}

	public static void setService(CacheReplicatorEntryLocalService service) {
		_service = service;
	}

	private static volatile CacheReplicatorEntryLocalService _service;

}
// LIFERAY-SERVICE-BUILDER-HASH:1887424096