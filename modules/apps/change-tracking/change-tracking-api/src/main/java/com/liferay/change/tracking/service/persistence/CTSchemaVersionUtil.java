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

package com.liferay.change.tracking.service.persistence;

import com.liferay.change.tracking.model.CTSchemaVersion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the ct schema version service. This utility wraps <code>com.liferay.change.tracking.service.persistence.impl.CTSchemaVersionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTSchemaVersionPersistence
 * @generated
 */
public class CTSchemaVersionUtil {

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
	public static void clearCache(CTSchemaVersion ctSchemaVersion) {
		getPersistence().clearCache(ctSchemaVersion);
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
	public static Map<Serializable, CTSchemaVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CTSchemaVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CTSchemaVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CTSchemaVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CTSchemaVersion> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CTSchemaVersion update(CTSchemaVersion ctSchemaVersion) {
		return getPersistence().update(ctSchemaVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CTSchemaVersion update(
		CTSchemaVersion ctSchemaVersion, ServiceContext serviceContext) {

		return getPersistence().update(ctSchemaVersion, serviceContext);
	}

	/**
	 * Returns all the ct schema versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct schema versions
	 */
	public static List<CTSchemaVersion> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the ct schema versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @return the range of matching ct schema versions
	 */
	public static List<CTSchemaVersion> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the ct schema versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct schema versions
	 */
	public static List<CTSchemaVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTSchemaVersion> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct schema versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct schema versions
	 */
	public static List<CTSchemaVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTSchemaVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ct schema version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct schema version
	 * @throws NoSuchSchemaVersionException if a matching ct schema version could not be found
	 */
	public static CTSchemaVersion findByCompanyId_First(
			long companyId,
			OrderByComparator<CTSchemaVersion> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchSchemaVersionException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first ct schema version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct schema version, or <code>null</code> if a matching ct schema version could not be found
	 */
	public static CTSchemaVersion fetchByCompanyId_First(
		long companyId, OrderByComparator<CTSchemaVersion> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last ct schema version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct schema version
	 * @throws NoSuchSchemaVersionException if a matching ct schema version could not be found
	 */
	public static CTSchemaVersion findByCompanyId_Last(
			long companyId,
			OrderByComparator<CTSchemaVersion> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchSchemaVersionException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last ct schema version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct schema version, or <code>null</code> if a matching ct schema version could not be found
	 */
	public static CTSchemaVersion fetchByCompanyId_Last(
		long companyId, OrderByComparator<CTSchemaVersion> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the ct schema versions before and after the current ct schema version in the ordered set where companyId = &#63;.
	 *
	 * @param schemaVersionId the primary key of the current ct schema version
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct schema version
	 * @throws NoSuchSchemaVersionException if a ct schema version with the primary key could not be found
	 */
	public static CTSchemaVersion[] findByCompanyId_PrevAndNext(
			long schemaVersionId, long companyId,
			OrderByComparator<CTSchemaVersion> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchSchemaVersionException {

		return getPersistence().findByCompanyId_PrevAndNext(
			schemaVersionId, companyId, orderByComparator);
	}

	/**
	 * Removes all the ct schema versions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of ct schema versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct schema versions
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Caches the ct schema version in the entity cache if it is enabled.
	 *
	 * @param ctSchemaVersion the ct schema version
	 */
	public static void cacheResult(CTSchemaVersion ctSchemaVersion) {
		getPersistence().cacheResult(ctSchemaVersion);
	}

	/**
	 * Caches the ct schema versions in the entity cache if it is enabled.
	 *
	 * @param ctSchemaVersions the ct schema versions
	 */
	public static void cacheResult(List<CTSchemaVersion> ctSchemaVersions) {
		getPersistence().cacheResult(ctSchemaVersions);
	}

	/**
	 * Creates a new ct schema version with the primary key. Does not add the ct schema version to the database.
	 *
	 * @param schemaVersionId the primary key for the new ct schema version
	 * @return the new ct schema version
	 */
	public static CTSchemaVersion create(long schemaVersionId) {
		return getPersistence().create(schemaVersionId);
	}

	/**
	 * Removes the ct schema version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param schemaVersionId the primary key of the ct schema version
	 * @return the ct schema version that was removed
	 * @throws NoSuchSchemaVersionException if a ct schema version with the primary key could not be found
	 */
	public static CTSchemaVersion remove(long schemaVersionId)
		throws com.liferay.change.tracking.exception.
			NoSuchSchemaVersionException {

		return getPersistence().remove(schemaVersionId);
	}

	public static CTSchemaVersion updateImpl(CTSchemaVersion ctSchemaVersion) {
		return getPersistence().updateImpl(ctSchemaVersion);
	}

	/**
	 * Returns the ct schema version with the primary key or throws a <code>NoSuchSchemaVersionException</code> if it could not be found.
	 *
	 * @param schemaVersionId the primary key of the ct schema version
	 * @return the ct schema version
	 * @throws NoSuchSchemaVersionException if a ct schema version with the primary key could not be found
	 */
	public static CTSchemaVersion findByPrimaryKey(long schemaVersionId)
		throws com.liferay.change.tracking.exception.
			NoSuchSchemaVersionException {

		return getPersistence().findByPrimaryKey(schemaVersionId);
	}

	/**
	 * Returns the ct schema version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param schemaVersionId the primary key of the ct schema version
	 * @return the ct schema version, or <code>null</code> if a ct schema version with the primary key could not be found
	 */
	public static CTSchemaVersion fetchByPrimaryKey(long schemaVersionId) {
		return getPersistence().fetchByPrimaryKey(schemaVersionId);
	}

	/**
	 * Returns all the ct schema versions.
	 *
	 * @return the ct schema versions
	 */
	public static List<CTSchemaVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ct schema versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @return the range of ct schema versions
	 */
	public static List<CTSchemaVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ct schema versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct schema versions
	 */
	public static List<CTSchemaVersion> findAll(
		int start, int end,
		OrderByComparator<CTSchemaVersion> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct schema versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct schema versions
	 */
	public static List<CTSchemaVersion> findAll(
		int start, int end,
		OrderByComparator<CTSchemaVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ct schema versions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ct schema versions.
	 *
	 * @return the number of ct schema versions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CTSchemaVersionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CTSchemaVersionPersistence, CTSchemaVersionPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CTSchemaVersionPersistence.class);

		ServiceTracker<CTSchemaVersionPersistence, CTSchemaVersionPersistence>
			serviceTracker =
				new ServiceTracker
					<CTSchemaVersionPersistence, CTSchemaVersionPersistence>(
						bundle.getBundleContext(),
						CTSchemaVersionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}