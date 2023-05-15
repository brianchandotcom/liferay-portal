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

package com.liferay.document.library.service.persistence;

import com.liferay.document.library.model.DLStorageQuota;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the dl storage quota service. This utility wraps <code>com.liferay.document.library.service.persistence.impl.DLStorageQuotaPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLStorageQuotaPersistence
 * @generated
 */
public class DLStorageQuotaUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(DLStorageQuota dlStorageQuota) {
		getPersistence().clearCache(dlStorageQuota);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, DLStorageQuota> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DLStorageQuota> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLStorageQuota> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DLStorageQuota> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DLStorageQuota> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DLStorageQuota update(DLStorageQuota dlStorageQuota) {
		return getPersistence().update(dlStorageQuota);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DLStorageQuota update(
		DLStorageQuota dlStorageQuota, ServiceContext serviceContext) {

		return getPersistence().update(dlStorageQuota, serviceContext);
	}

	/**
	 * Returns the dl storage quota where companyId = &#63; or throws a <code>NoSuchStorageQuotaException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @return the matching dl storage quota
	 * @throws NoSuchStorageQuotaException if a matching dl storage quota could not be found
	 */
	public static DLStorageQuota findByCompanyId(long companyId)
		throws com.liferay.document.library.exception.
			NoSuchStorageQuotaException {

		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns the dl storage quota where companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @return the matching dl storage quota, or <code>null</code> if a matching dl storage quota could not be found
	 */
	public static DLStorageQuota fetchByCompanyId(long companyId) {
		return getPersistence().fetchByCompanyId(companyId);
	}

	/**
	 * Returns the dl storage quota where companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching dl storage quota, or <code>null</code> if a matching dl storage quota could not be found
	 */
	public static DLStorageQuota fetchByCompanyId(
		long companyId, boolean useFinderCache) {

		return getPersistence().fetchByCompanyId(companyId, useFinderCache);
	}

	/**
	 * Removes the dl storage quota where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @return the dl storage quota that was removed
	 */
	public static DLStorageQuota removeByCompanyId(long companyId)
		throws com.liferay.document.library.exception.
			NoSuchStorageQuotaException {

		return getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of dl storage quotas where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching dl storage quotas
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Caches the dl storage quota in the entity cache if it is enabled.
	 *
	 * @param dlStorageQuota the dl storage quota
	 */
	public static void cacheResult(DLStorageQuota dlStorageQuota) {
		getPersistence().cacheResult(dlStorageQuota);
	}

	/**
	 * Caches the dl storage quotas in the entity cache if it is enabled.
	 *
	 * @param dlStorageQuotas the dl storage quotas
	 */
	public static void cacheResult(List<DLStorageQuota> dlStorageQuotas) {
		getPersistence().cacheResult(dlStorageQuotas);
	}

	/**
	 * Creates a new dl storage quota with the primary key. Does not add the dl storage quota to the database.
	 *
	 * @param dlStorageQuotaId the primary key for the new dl storage quota
	 * @return the new dl storage quota
	 */
	public static DLStorageQuota create(long dlStorageQuotaId) {
		return getPersistence().create(dlStorageQuotaId);
	}

	/**
	 * Removes the dl storage quota with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlStorageQuotaId the primary key of the dl storage quota
	 * @return the dl storage quota that was removed
	 * @throws NoSuchStorageQuotaException if a dl storage quota with the primary key could not be found
	 */
	public static DLStorageQuota remove(long dlStorageQuotaId)
		throws com.liferay.document.library.exception.
			NoSuchStorageQuotaException {

		return getPersistence().remove(dlStorageQuotaId);
	}

	public static DLStorageQuota updateImpl(DLStorageQuota dlStorageQuota) {
		return getPersistence().updateImpl(dlStorageQuota);
	}

	/**
	 * Returns the dl storage quota with the primary key or throws a <code>NoSuchStorageQuotaException</code> if it could not be found.
	 *
	 * @param dlStorageQuotaId the primary key of the dl storage quota
	 * @return the dl storage quota
	 * @throws NoSuchStorageQuotaException if a dl storage quota with the primary key could not be found
	 */
	public static DLStorageQuota findByPrimaryKey(long dlStorageQuotaId)
		throws com.liferay.document.library.exception.
			NoSuchStorageQuotaException {

		return getPersistence().findByPrimaryKey(dlStorageQuotaId);
	}

	/**
	 * Returns the dl storage quota with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dlStorageQuotaId the primary key of the dl storage quota
	 * @return the dl storage quota, or <code>null</code> if a dl storage quota with the primary key could not be found
	 */
	public static DLStorageQuota fetchByPrimaryKey(long dlStorageQuotaId) {
		return getPersistence().fetchByPrimaryKey(dlStorageQuotaId);
	}

	/**
	 * Returns all the dl storage quotas.
	 *
	 * @return the dl storage quotas
	 */
	public static List<DLStorageQuota> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the dl storage quotas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLStorageQuotaModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl storage quotas
	 * @param end the upper bound of the range of dl storage quotas (not inclusive)
	 * @return the range of dl storage quotas
	 */
	public static List<DLStorageQuota> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the dl storage quotas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLStorageQuotaModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl storage quotas
	 * @param end the upper bound of the range of dl storage quotas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dl storage quotas
	 */
	public static List<DLStorageQuota> findAll(
		int start, int end,
		OrderByComparator<DLStorageQuota> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the dl storage quotas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLStorageQuotaModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl storage quotas
	 * @param end the upper bound of the range of dl storage quotas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dl storage quotas
	 */
	public static List<DLStorageQuota> findAll(
		int start, int end, OrderByComparator<DLStorageQuota> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the dl storage quotas from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of dl storage quotas.
	 *
	 * @return the number of dl storage quotas
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DLStorageQuotaPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(DLStorageQuotaPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile DLStorageQuotaPersistence _persistence;

}