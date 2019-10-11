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

package com.liferay.portal.workflow.kaleo.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the kaleo definition version service. This utility wraps <code>com.liferay.portal.workflow.kaleo.service.persistence.impl.KaleoDefinitionVersionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoDefinitionVersionPersistence
 * @generated
 */
public class KaleoDefinitionVersionUtil {

	/**
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
	public static void clearCache(
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		getPersistence().clearCache(kaleoDefinitionVersion);
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
	public static Map<Serializable, KaleoDefinitionVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<KaleoDefinitionVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<KaleoDefinitionVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<KaleoDefinitionVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static KaleoDefinitionVersion update(
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		return getPersistence().update(kaleoDefinitionVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static KaleoDefinitionVersion update(
		KaleoDefinitionVersion kaleoDefinitionVersion,
		ServiceContext serviceContext) {

		return getPersistence().update(kaleoDefinitionVersion, serviceContext);
	}

	/**
	 * Returns all the kaleo definition versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo definition versions
	 */
	public static List<KaleoDefinitionVersion> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the kaleo definition versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @return the range of matching kaleo definition versions
	 */
	public static List<KaleoDefinitionVersion> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo definition versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo definition versions
	 */
	public static List<KaleoDefinitionVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo definition versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo definition versions
	 */
	public static List<KaleoDefinitionVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	public static KaleoDefinitionVersion findByCompanyId_First(
			long companyId,
			OrderByComparator<KaleoDefinitionVersion> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchDefinitionVersionException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	public static KaleoDefinitionVersion fetchByCompanyId_First(
		long companyId,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	public static KaleoDefinitionVersion findByCompanyId_Last(
			long companyId,
			OrderByComparator<KaleoDefinitionVersion> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchDefinitionVersionException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	public static KaleoDefinitionVersion fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the kaleo definition versions before and after the current kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the current kaleo definition version
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	public static KaleoDefinitionVersion[] findByCompanyId_PrevAndNext(
			long kaleoDefinitionVersionId, long companyId,
			OrderByComparator<KaleoDefinitionVersion> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchDefinitionVersionException {

		return getPersistence().findByCompanyId_PrevAndNext(
			kaleoDefinitionVersionId, companyId, orderByComparator);
	}

	/**
	 * Removes all the kaleo definition versions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of kaleo definition versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo definition versions
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching kaleo definition versions
	 */
	public static List<KaleoDefinitionVersion> findByC_N(
		long companyId, String name) {

		return getPersistence().findByC_N(companyId, name);
	}

	/**
	 * Returns a range of all the kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @return the range of matching kaleo definition versions
	 */
	public static List<KaleoDefinitionVersion> findByC_N(
		long companyId, String name, int start, int end) {

		return getPersistence().findByC_N(companyId, name, start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo definition versions
	 */
	public static List<KaleoDefinitionVersion> findByC_N(
		long companyId, String name, int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {

		return getPersistence().findByC_N(
			companyId, name, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo definition versions
	 */
	public static List<KaleoDefinitionVersion> findByC_N(
		long companyId, String name, int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_N(
			companyId, name, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	public static KaleoDefinitionVersion findByC_N_First(
			long companyId, String name,
			OrderByComparator<KaleoDefinitionVersion> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchDefinitionVersionException {

		return getPersistence().findByC_N_First(
			companyId, name, orderByComparator);
	}

	/**
	 * Returns the first kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	public static KaleoDefinitionVersion fetchByC_N_First(
		long companyId, String name,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {

		return getPersistence().fetchByC_N_First(
			companyId, name, orderByComparator);
	}

	/**
	 * Returns the last kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	public static KaleoDefinitionVersion findByC_N_Last(
			long companyId, String name,
			OrderByComparator<KaleoDefinitionVersion> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchDefinitionVersionException {

		return getPersistence().findByC_N_Last(
			companyId, name, orderByComparator);
	}

	/**
	 * Returns the last kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	public static KaleoDefinitionVersion fetchByC_N_Last(
		long companyId, String name,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {

		return getPersistence().fetchByC_N_Last(
			companyId, name, orderByComparator);
	}

	/**
	 * Returns the kaleo definition versions before and after the current kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the current kaleo definition version
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	public static KaleoDefinitionVersion[] findByC_N_PrevAndNext(
			long kaleoDefinitionVersionId, long companyId, String name,
			OrderByComparator<KaleoDefinitionVersion> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchDefinitionVersionException {

		return getPersistence().findByC_N_PrevAndNext(
			kaleoDefinitionVersionId, companyId, name, orderByComparator);
	}

	/**
	 * Removes all the kaleo definition versions where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 */
	public static void removeByC_N(long companyId, String name) {
		getPersistence().removeByC_N(companyId, name);
	}

	/**
	 * Returns the number of kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching kaleo definition versions
	 */
	public static int countByC_N(long companyId, String name) {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	 * Returns the kaleo definition version where companyId = &#63; and name = &#63; and version = &#63; or throws a <code>NoSuchDefinitionVersionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	public static KaleoDefinitionVersion findByC_N_V(
			long companyId, String name, String version)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchDefinitionVersionException {

		return getPersistence().findByC_N_V(companyId, name, version);
	}

	/**
	 * Returns the kaleo definition version where companyId = &#63; and name = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	public static KaleoDefinitionVersion fetchByC_N_V(
		long companyId, String name, String version) {

		return getPersistence().fetchByC_N_V(companyId, name, version);
	}

	/**
	 * Returns the kaleo definition version where companyId = &#63; and name = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	public static KaleoDefinitionVersion fetchByC_N_V(
		long companyId, String name, String version, boolean useFinderCache) {

		return getPersistence().fetchByC_N_V(
			companyId, name, version, useFinderCache);
	}

	/**
	 * Removes the kaleo definition version where companyId = &#63; and name = &#63; and version = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the kaleo definition version that was removed
	 */
	public static KaleoDefinitionVersion removeByC_N_V(
			long companyId, String name, String version)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchDefinitionVersionException {

		return getPersistence().removeByC_N_V(companyId, name, version);
	}

	/**
	 * Returns the number of kaleo definition versions where companyId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the number of matching kaleo definition versions
	 */
	public static int countByC_N_V(
		long companyId, String name, String version) {

		return getPersistence().countByC_N_V(companyId, name, version);
	}

	/**
	 * Caches the kaleo definition version in the entity cache if it is enabled.
	 *
	 * @param kaleoDefinitionVersion the kaleo definition version
	 */
	public static void cacheResult(
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		getPersistence().cacheResult(kaleoDefinitionVersion);
	}

	/**
	 * Caches the kaleo definition versions in the entity cache if it is enabled.
	 *
	 * @param kaleoDefinitionVersions the kaleo definition versions
	 */
	public static void cacheResult(
		List<KaleoDefinitionVersion> kaleoDefinitionVersions) {

		getPersistence().cacheResult(kaleoDefinitionVersions);
	}

	/**
	 * Creates a new kaleo definition version with the primary key. Does not add the kaleo definition version to the database.
	 *
	 * @param kaleoDefinitionVersionId the primary key for the new kaleo definition version
	 * @return the new kaleo definition version
	 */
	public static KaleoDefinitionVersion create(long kaleoDefinitionVersionId) {
		return getPersistence().create(kaleoDefinitionVersionId);
	}

	/**
	 * Removes the kaleo definition version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the kaleo definition version
	 * @return the kaleo definition version that was removed
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	public static KaleoDefinitionVersion remove(long kaleoDefinitionVersionId)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchDefinitionVersionException {

		return getPersistence().remove(kaleoDefinitionVersionId);
	}

	public static KaleoDefinitionVersion updateImpl(
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		return getPersistence().updateImpl(kaleoDefinitionVersion);
	}

	/**
	 * Returns the kaleo definition version with the primary key or throws a <code>NoSuchDefinitionVersionException</code> if it could not be found.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the kaleo definition version
	 * @return the kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	public static KaleoDefinitionVersion findByPrimaryKey(
			long kaleoDefinitionVersionId)
		throws com.liferay.portal.workflow.kaleo.exception.
			NoSuchDefinitionVersionException {

		return getPersistence().findByPrimaryKey(kaleoDefinitionVersionId);
	}

	/**
	 * Returns the kaleo definition version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the kaleo definition version
	 * @return the kaleo definition version, or <code>null</code> if a kaleo definition version with the primary key could not be found
	 */
	public static KaleoDefinitionVersion fetchByPrimaryKey(
		long kaleoDefinitionVersionId) {

		return getPersistence().fetchByPrimaryKey(kaleoDefinitionVersionId);
	}

	/**
	 * Returns all the kaleo definition versions.
	 *
	 * @return the kaleo definition versions
	 */
	public static List<KaleoDefinitionVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the kaleo definition versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @return the range of kaleo definition versions
	 */
	public static List<KaleoDefinitionVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the kaleo definition versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo definition versions
	 */
	public static List<KaleoDefinitionVersion> findAll(
		int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the kaleo definition versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo definition versions
	 */
	public static List<KaleoDefinitionVersion> findAll(
		int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the kaleo definition versions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of kaleo definition versions.
	 *
	 * @return the number of kaleo definition versions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static KaleoDefinitionVersionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<KaleoDefinitionVersionPersistence, KaleoDefinitionVersionPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			KaleoDefinitionVersionPersistence.class);

		ServiceTracker
			<KaleoDefinitionVersionPersistence,
			 KaleoDefinitionVersionPersistence> serviceTracker =
				new ServiceTracker
					<KaleoDefinitionVersionPersistence,
					 KaleoDefinitionVersionPersistence>(
						 bundle.getBundleContext(),
						 KaleoDefinitionVersionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}