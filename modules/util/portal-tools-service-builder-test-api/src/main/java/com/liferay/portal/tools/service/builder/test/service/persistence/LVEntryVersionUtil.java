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

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.LVEntryVersion;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the lv entry version service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.LVEntryVersionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryVersionPersistence
 * @generated
 */
public class LVEntryVersionUtil {

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
	public static void clearCache(LVEntryVersion lvEntryVersion) {
		getPersistence().clearCache(lvEntryVersion);
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
	public static Map<Serializable, LVEntryVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LVEntryVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LVEntryVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LVEntryVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LVEntryVersion update(LVEntryVersion lvEntryVersion) {
		return getPersistence().update(lvEntryVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LVEntryVersion update(
		LVEntryVersion lvEntryVersion, ServiceContext serviceContext) {

		return getPersistence().update(lvEntryVersion, serviceContext);
	}

	/**
	 * Returns all the lv entry versions where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @return the matching lv entry versions
	 */
	public static List<LVEntryVersion> findByLvEntryId(long lvEntryId) {
		return getPersistence().findByLvEntryId(lvEntryId);
	}

	/**
	 * Returns a range of all the lv entry versions where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByLvEntryId(
		long lvEntryId, int start, int end) {

		return getPersistence().findByLvEntryId(lvEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByLvEntryId(
		long lvEntryId, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().findByLvEntryId(
			lvEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByLvEntryId(
		long lvEntryId, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLvEntryId(
			lvEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first lv entry version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByLvEntryId_First(
			long lvEntryId, OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByLvEntryId_First(
			lvEntryId, orderByComparator);
	}

	/**
	 * Returns the first lv entry version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByLvEntryId_First(
		long lvEntryId, OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().fetchByLvEntryId_First(
			lvEntryId, orderByComparator);
	}

	/**
	 * Returns the last lv entry version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByLvEntryId_Last(
			long lvEntryId, OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByLvEntryId_Last(
			lvEntryId, orderByComparator);
	}

	/**
	 * Returns the last lv entry version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByLvEntryId_Last(
		long lvEntryId, OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().fetchByLvEntryId_Last(
			lvEntryId, orderByComparator);
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	public static LVEntryVersion[] findByLvEntryId_PrevAndNext(
			long lvEntryVersionId, long lvEntryId,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByLvEntryId_PrevAndNext(
			lvEntryVersionId, lvEntryId, orderByComparator);
	}

	/**
	 * Removes all the lv entry versions where lvEntryId = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 */
	public static void removeByLvEntryId(long lvEntryId) {
		getPersistence().removeByLvEntryId(lvEntryId);
	}

	/**
	 * Returns the number of lv entry versions where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @return the number of matching lv entry versions
	 */
	public static int countByLvEntryId(long lvEntryId) {
		return getPersistence().countByLvEntryId(lvEntryId);
	}

	/**
	 * Returns the lv entry version where lvEntryId = &#63; and version = &#63; or throws a <code>NoSuchLVEntryVersionException</code> if it could not be found.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @return the matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByLvEntryId_Version(
			long lvEntryId, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByLvEntryId_Version(lvEntryId, version);
	}

	/**
	 * Returns the lv entry version where lvEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @return the matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByLvEntryId_Version(
		long lvEntryId, int version) {

		return getPersistence().fetchByLvEntryId_Version(lvEntryId, version);
	}

	/**
	 * Returns the lv entry version where lvEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByLvEntryId_Version(
		long lvEntryId, int version, boolean useFinderCache) {

		return getPersistence().fetchByLvEntryId_Version(
			lvEntryId, version, useFinderCache);
	}

	/**
	 * Removes the lv entry version where lvEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @return the lv entry version that was removed
	 */
	public static LVEntryVersion removeByLvEntryId_Version(
			long lvEntryId, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().removeByLvEntryId_Version(lvEntryId, version);
	}

	/**
	 * Returns the number of lv entry versions where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @return the number of matching lv entry versions
	 */
	public static int countByLvEntryId_Version(long lvEntryId, int version) {
		return getPersistence().countByLvEntryId_Version(lvEntryId, version);
	}

	/**
	 * Returns all the lv entry versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the lv entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first lv entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByUuid_First(
			String uuid, OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first lv entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByUuid_First(
		String uuid, OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByUuid_Last(
			String uuid, OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByUuid_Last(
		String uuid, OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where uuid = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	public static LVEntryVersion[] findByUuid_PrevAndNext(
			long lvEntryVersionId, String uuid,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByUuid_PrevAndNext(
			lvEntryVersionId, uuid, orderByComparator);
	}

	/**
	 * Removes all the lv entry versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of lv entry versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching lv entry versions
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the lv entry versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUuid_Version(
		String uuid, int version) {

		return getPersistence().findByUuid_Version(uuid, version);
	}

	/**
	 * Returns a range of all the lv entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end) {

		return getPersistence().findByUuid_Version(uuid, version, start, end);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().findByUuid_Version(
			uuid, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_Version(
			uuid, version, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first lv entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByUuid_Version_First(
			String uuid, int version,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByUuid_Version_First(
			uuid, version, orderByComparator);
	}

	/**
	 * Returns the first lv entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByUuid_Version_First(
		String uuid, int version,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().fetchByUuid_Version_First(
			uuid, version, orderByComparator);
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByUuid_Version_Last(
			String uuid, int version,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByUuid_Version_Last(
			uuid, version, orderByComparator);
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByUuid_Version_Last(
		String uuid, int version,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().fetchByUuid_Version_Last(
			uuid, version, orderByComparator);
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	public static LVEntryVersion[] findByUuid_Version_PrevAndNext(
			long lvEntryVersionId, String uuid, int version,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByUuid_Version_PrevAndNext(
			lvEntryVersionId, uuid, version, orderByComparator);
	}

	/**
	 * Removes all the lv entry versions where uuid = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 */
	public static void removeByUuid_Version(String uuid, int version) {
		getPersistence().removeByUuid_Version(uuid, version);
	}

	/**
	 * Returns the number of lv entry versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the number of matching lv entry versions
	 */
	public static int countByUuid_Version(String uuid, int version) {
		return getPersistence().countByUuid_Version(uuid, version);
	}

	/**
	 * Returns all the lv entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUUID_G(String uuid, long groupId) {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the lv entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end) {

		return getPersistence().findByUUID_G(uuid, groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().findByUUID_G(
			uuid, groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUUID_G(
			uuid, groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first lv entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByUUID_G_First(
			String uuid, long groupId,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByUUID_G_First(
			uuid, groupId, orderByComparator);
	}

	/**
	 * Returns the first lv entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByUUID_G_First(
		String uuid, long groupId,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().fetchByUUID_G_First(
			uuid, groupId, orderByComparator);
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByUUID_G_Last(
			String uuid, long groupId,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByUUID_G_Last(
			uuid, groupId, orderByComparator);
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByUUID_G_Last(
		String uuid, long groupId,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().fetchByUUID_G_Last(
			uuid, groupId, orderByComparator);
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	public static LVEntryVersion[] findByUUID_G_PrevAndNext(
			long lvEntryVersionId, String uuid, long groupId,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByUUID_G_PrevAndNext(
			lvEntryVersionId, uuid, groupId, orderByComparator);
	}

	/**
	 * Removes all the lv entry versions where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 */
	public static void removeByUUID_G(String uuid, long groupId) {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of lv entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching lv entry versions
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the lv entry version where uuid = &#63; and groupId = &#63; and version = &#63; or throws a <code>NoSuchLVEntryVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByUUID_G_Version(
			String uuid, long groupId, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByUUID_G_Version(uuid, groupId, version);
	}

	/**
	 * Returns the lv entry version where uuid = &#63; and groupId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByUUID_G_Version(
		String uuid, long groupId, int version) {

		return getPersistence().fetchByUUID_G_Version(uuid, groupId, version);
	}

	/**
	 * Returns the lv entry version where uuid = &#63; and groupId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByUUID_G_Version(
		String uuid, long groupId, int version, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G_Version(
			uuid, groupId, version, useFinderCache);
	}

	/**
	 * Removes the lv entry version where uuid = &#63; and groupId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the lv entry version that was removed
	 */
	public static LVEntryVersion removeByUUID_G_Version(
			String uuid, long groupId, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().removeByUUID_G_Version(uuid, groupId, version);
	}

	/**
	 * Returns the number of lv entry versions where uuid = &#63; and groupId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching lv entry versions
	 */
	public static int countByUUID_G_Version(
		String uuid, long groupId, int version) {

		return getPersistence().countByUUID_G_Version(uuid, groupId, version);
	}

	/**
	 * Returns all the lv entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the lv entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first lv entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first lv entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	public static LVEntryVersion[] findByUuid_C_PrevAndNext(
			long lvEntryVersionId, String uuid, long companyId,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByUuid_C_PrevAndNext(
			lvEntryVersionId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the lv entry versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of lv entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching lv entry versions
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the lv entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version) {

		return getPersistence().findByUuid_C_Version(uuid, companyId, version);
	}

	/**
	 * Returns a range of all the lv entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end) {

		return getPersistence().findByUuid_C_Version(
			uuid, companyId, version, start, end);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().findByUuid_C_Version(
			uuid, companyId, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C_Version(
			uuid, companyId, version, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first lv entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByUuid_C_Version_First(
			String uuid, long companyId, int version,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByUuid_C_Version_First(
			uuid, companyId, version, orderByComparator);
	}

	/**
	 * Returns the first lv entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByUuid_C_Version_First(
		String uuid, long companyId, int version,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().fetchByUuid_C_Version_First(
			uuid, companyId, version, orderByComparator);
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByUuid_C_Version_Last(
			String uuid, long companyId, int version,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByUuid_C_Version_Last(
			uuid, companyId, version, orderByComparator);
	}

	/**
	 * Returns the last lv entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByUuid_C_Version_Last(
		String uuid, long companyId, int version,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().fetchByUuid_C_Version_Last(
			uuid, companyId, version, orderByComparator);
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	public static LVEntryVersion[] findByUuid_C_Version_PrevAndNext(
			long lvEntryVersionId, String uuid, long companyId, int version,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByUuid_C_Version_PrevAndNext(
			lvEntryVersionId, uuid, companyId, version, orderByComparator);
	}

	/**
	 * Removes all the lv entry versions where uuid = &#63; and companyId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 */
	public static void removeByUuid_C_Version(
		String uuid, long companyId, int version) {

		getPersistence().removeByUuid_C_Version(uuid, companyId, version);
	}

	/**
	 * Returns the number of lv entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the number of matching lv entry versions
	 */
	public static int countByUuid_C_Version(
		String uuid, long companyId, int version) {

		return getPersistence().countByUuid_C_Version(uuid, companyId, version);
	}

	/**
	 * Returns all the lv entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching lv entry versions
	 */
	public static List<LVEntryVersion> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the lv entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first lv entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByGroupId_First(
			long groupId, OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first lv entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByGroupId_First(
		long groupId, OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last lv entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByGroupId_Last(
			long groupId, OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last lv entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByGroupId_Last(
		long groupId, OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where groupId = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	public static LVEntryVersion[] findByGroupId_PrevAndNext(
			long lvEntryVersionId, long groupId,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByGroupId_PrevAndNext(
			lvEntryVersionId, groupId, orderByComparator);
	}

	/**
	 * Removes all the lv entry versions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of lv entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching lv entry versions
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the lv entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching lv entry versions
	 */
	public static List<LVEntryVersion> findByGroupId_Version(
		long groupId, int version) {

		return getPersistence().findByGroupId_Version(groupId, version);
	}

	/**
	 * Returns a range of all the lv entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end) {

		return getPersistence().findByGroupId_Version(
			groupId, version, start, end);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().findByGroupId_Version(
			groupId, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId_Version(
			groupId, version, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByGroupId_Version_First(
			long groupId, int version,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByGroupId_Version_First(
			groupId, version, orderByComparator);
	}

	/**
	 * Returns the first lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByGroupId_Version_First(
		long groupId, int version,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().fetchByGroupId_Version_First(
			groupId, version, orderByComparator);
	}

	/**
	 * Returns the last lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByGroupId_Version_Last(
			long groupId, int version,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByGroupId_Version_Last(
			groupId, version, orderByComparator);
	}

	/**
	 * Returns the last lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByGroupId_Version_Last(
		long groupId, int version,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().fetchByGroupId_Version_Last(
			groupId, version, orderByComparator);
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	public static LVEntryVersion[] findByGroupId_Version_PrevAndNext(
			long lvEntryVersionId, long groupId, int version,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByGroupId_Version_PrevAndNext(
			lvEntryVersionId, groupId, version, orderByComparator);
	}

	/**
	 * Removes all the lv entry versions where groupId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 */
	public static void removeByGroupId_Version(long groupId, int version) {
		getPersistence().removeByGroupId_Version(groupId, version);
	}

	/**
	 * Returns the number of lv entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching lv entry versions
	 */
	public static int countByGroupId_Version(long groupId, int version) {
		return getPersistence().countByGroupId_Version(groupId, version);
	}

	/**
	 * Returns all the lv entry versions where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @return the matching lv entry versions
	 */
	public static List<LVEntryVersion> findByG_UGK(
		long groupId, String uniqueGroupKey) {

		return getPersistence().findByG_UGK(groupId, uniqueGroupKey);
	}

	/**
	 * Returns a range of all the lv entry versions where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByG_UGK(
		long groupId, String uniqueGroupKey, int start, int end) {

		return getPersistence().findByG_UGK(
			groupId, uniqueGroupKey, start, end);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByG_UGK(
		long groupId, String uniqueGroupKey, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().findByG_UGK(
			groupId, uniqueGroupKey, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	public static List<LVEntryVersion> findByG_UGK(
		long groupId, String uniqueGroupKey, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_UGK(
			groupId, uniqueGroupKey, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first lv entry version in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByG_UGK_First(
			long groupId, String uniqueGroupKey,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByG_UGK_First(
			groupId, uniqueGroupKey, orderByComparator);
	}

	/**
	 * Returns the first lv entry version in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByG_UGK_First(
		long groupId, String uniqueGroupKey,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().fetchByG_UGK_First(
			groupId, uniqueGroupKey, orderByComparator);
	}

	/**
	 * Returns the last lv entry version in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByG_UGK_Last(
			long groupId, String uniqueGroupKey,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByG_UGK_Last(
			groupId, uniqueGroupKey, orderByComparator);
	}

	/**
	 * Returns the last lv entry version in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByG_UGK_Last(
		long groupId, String uniqueGroupKey,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().fetchByG_UGK_Last(
			groupId, uniqueGroupKey, orderByComparator);
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	public static LVEntryVersion[] findByG_UGK_PrevAndNext(
			long lvEntryVersionId, long groupId, String uniqueGroupKey,
			OrderByComparator<LVEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByG_UGK_PrevAndNext(
			lvEntryVersionId, groupId, uniqueGroupKey, orderByComparator);
	}

	/**
	 * Removes all the lv entry versions where groupId = &#63; and uniqueGroupKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 */
	public static void removeByG_UGK(long groupId, String uniqueGroupKey) {
		getPersistence().removeByG_UGK(groupId, uniqueGroupKey);
	}

	/**
	 * Returns the number of lv entry versions where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @return the number of matching lv entry versions
	 */
	public static int countByG_UGK(long groupId, String uniqueGroupKey) {
		return getPersistence().countByG_UGK(groupId, uniqueGroupKey);
	}

	/**
	 * Returns the lv entry version where groupId = &#63; and uniqueGroupKey = &#63; and version = &#63; or throws a <code>NoSuchLVEntryVersionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param version the version
	 * @return the matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	public static LVEntryVersion findByG_UGK_Version(
			long groupId, String uniqueGroupKey, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByG_UGK_Version(
			groupId, uniqueGroupKey, version);
	}

	/**
	 * Returns the lv entry version where groupId = &#63; and uniqueGroupKey = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param version the version
	 * @return the matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByG_UGK_Version(
		long groupId, String uniqueGroupKey, int version) {

		return getPersistence().fetchByG_UGK_Version(
			groupId, uniqueGroupKey, version);
	}

	/**
	 * Returns the lv entry version where groupId = &#63; and uniqueGroupKey = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	public static LVEntryVersion fetchByG_UGK_Version(
		long groupId, String uniqueGroupKey, int version,
		boolean useFinderCache) {

		return getPersistence().fetchByG_UGK_Version(
			groupId, uniqueGroupKey, version, useFinderCache);
	}

	/**
	 * Removes the lv entry version where groupId = &#63; and uniqueGroupKey = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param version the version
	 * @return the lv entry version that was removed
	 */
	public static LVEntryVersion removeByG_UGK_Version(
			long groupId, String uniqueGroupKey, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().removeByG_UGK_Version(
			groupId, uniqueGroupKey, version);
	}

	/**
	 * Returns the number of lv entry versions where groupId = &#63; and uniqueGroupKey = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param version the version
	 * @return the number of matching lv entry versions
	 */
	public static int countByG_UGK_Version(
		long groupId, String uniqueGroupKey, int version) {

		return getPersistence().countByG_UGK_Version(
			groupId, uniqueGroupKey, version);
	}

	/**
	 * Caches the lv entry version in the entity cache if it is enabled.
	 *
	 * @param lvEntryVersion the lv entry version
	 */
	public static void cacheResult(LVEntryVersion lvEntryVersion) {
		getPersistence().cacheResult(lvEntryVersion);
	}

	/**
	 * Caches the lv entry versions in the entity cache if it is enabled.
	 *
	 * @param lvEntryVersions the lv entry versions
	 */
	public static void cacheResult(List<LVEntryVersion> lvEntryVersions) {
		getPersistence().cacheResult(lvEntryVersions);
	}

	/**
	 * Creates a new lv entry version with the primary key. Does not add the lv entry version to the database.
	 *
	 * @param lvEntryVersionId the primary key for the new lv entry version
	 * @return the new lv entry version
	 */
	public static LVEntryVersion create(long lvEntryVersionId) {
		return getPersistence().create(lvEntryVersionId);
	}

	/**
	 * Removes the lv entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lvEntryVersionId the primary key of the lv entry version
	 * @return the lv entry version that was removed
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	public static LVEntryVersion remove(long lvEntryVersionId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().remove(lvEntryVersionId);
	}

	public static LVEntryVersion updateImpl(LVEntryVersion lvEntryVersion) {
		return getPersistence().updateImpl(lvEntryVersion);
	}

	/**
	 * Returns the lv entry version with the primary key or throws a <code>NoSuchLVEntryVersionException</code> if it could not be found.
	 *
	 * @param lvEntryVersionId the primary key of the lv entry version
	 * @return the lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	public static LVEntryVersion findByPrimaryKey(long lvEntryVersionId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryVersionException {

		return getPersistence().findByPrimaryKey(lvEntryVersionId);
	}

	/**
	 * Returns the lv entry version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param lvEntryVersionId the primary key of the lv entry version
	 * @return the lv entry version, or <code>null</code> if a lv entry version with the primary key could not be found
	 */
	public static LVEntryVersion fetchByPrimaryKey(long lvEntryVersionId) {
		return getPersistence().fetchByPrimaryKey(lvEntryVersionId);
	}

	/**
	 * Returns all the lv entry versions.
	 *
	 * @return the lv entry versions
	 */
	public static List<LVEntryVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the lv entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of lv entry versions
	 */
	public static List<LVEntryVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the lv entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lv entry versions
	 */
	public static List<LVEntryVersion> findAll(
		int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the lv entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of lv entry versions
	 */
	public static List<LVEntryVersion> findAll(
		int start, int end, OrderByComparator<LVEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the lv entry versions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of lv entry versions.
	 *
	 * @return the number of lv entry versions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	 * Returns the primaryKeys of big decimal entries associated with the lv entry version.
	 *
	 * @param pk the primary key of the lv entry version
	 * @return long[] of the primaryKeys of big decimal entries associated with the lv entry version
	 */
	public static long[] getBigDecimalEntryPrimaryKeys(long pk) {
		return getPersistence().getBigDecimalEntryPrimaryKeys(pk);
	}

	/**
	 * Returns all the big decimal entries associated with the lv entry version.
	 *
	 * @param pk the primary key of the lv entry version
	 * @return the big decimal entries associated with the lv entry version
	 */
	public static List
		<com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry>
			getBigDecimalEntries(long pk) {

		return getPersistence().getBigDecimalEntries(pk);
	}

	/**
	 * Returns a range of all the big decimal entries associated with the lv entry version.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the lv entry version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of big decimal entries associated with the lv entry version
	 */
	public static List
		<com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry>
			getBigDecimalEntries(long pk, int start, int end) {

		return getPersistence().getBigDecimalEntries(pk, start, end);
	}

	/**
	 * Returns an ordered range of all the big decimal entries associated with the lv entry version.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the lv entry version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of big decimal entries associated with the lv entry version
	 */
	public static List
		<com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry>
			getBigDecimalEntries(
				long pk, int start, int end,
				OrderByComparator
					<com.liferay.portal.tools.service.builder.test.model.
						BigDecimalEntry> orderByComparator) {

		return getPersistence().getBigDecimalEntries(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of big decimal entries associated with the lv entry version.
	 *
	 * @param pk the primary key of the lv entry version
	 * @return the number of big decimal entries associated with the lv entry version
	 */
	public static int getBigDecimalEntriesSize(long pk) {
		return getPersistence().getBigDecimalEntriesSize(pk);
	}

	/**
	 * Returns <code>true</code> if the big decimal entry is associated with the lv entry version.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntryPK the primary key of the big decimal entry
	 * @return <code>true</code> if the big decimal entry is associated with the lv entry version; <code>false</code> otherwise
	 */
	public static boolean containsBigDecimalEntry(
		long pk, long bigDecimalEntryPK) {

		return getPersistence().containsBigDecimalEntry(pk, bigDecimalEntryPK);
	}

	/**
	 * Returns <code>true</code> if the lv entry version has any big decimal entries associated with it.
	 *
	 * @param pk the primary key of the lv entry version to check for associations with big decimal entries
	 * @return <code>true</code> if the lv entry version has any big decimal entries associated with it; <code>false</code> otherwise
	 */
	public static boolean containsBigDecimalEntries(long pk) {
		return getPersistence().containsBigDecimalEntries(pk);
	}

	/**
	 * Adds an association between the lv entry version and the big decimal entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntryPK the primary key of the big decimal entry
	 */
	public static void addBigDecimalEntry(long pk, long bigDecimalEntryPK) {
		getPersistence().addBigDecimalEntry(pk, bigDecimalEntryPK);
	}

	/**
	 * Adds an association between the lv entry version and the big decimal entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntry the big decimal entry
	 */
	public static void addBigDecimalEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry
			bigDecimalEntry) {

		getPersistence().addBigDecimalEntry(pk, bigDecimalEntry);
	}

	/**
	 * Adds an association between the lv entry version and the big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntryPKs the primary keys of the big decimal entries
	 */
	public static void addBigDecimalEntries(
		long pk, long[] bigDecimalEntryPKs) {

		getPersistence().addBigDecimalEntries(pk, bigDecimalEntryPKs);
	}

	/**
	 * Adds an association between the lv entry version and the big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntries the big decimal entries
	 */
	public static void addBigDecimalEntries(
		long pk,
		List
			<com.liferay.portal.tools.service.builder.test.model.
				BigDecimalEntry> bigDecimalEntries) {

		getPersistence().addBigDecimalEntries(pk, bigDecimalEntries);
	}

	/**
	 * Clears all associations between the lv entry version and its big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version to clear the associated big decimal entries from
	 */
	public static void clearBigDecimalEntries(long pk) {
		getPersistence().clearBigDecimalEntries(pk);
	}

	/**
	 * Removes the association between the lv entry version and the big decimal entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntryPK the primary key of the big decimal entry
	 */
	public static void removeBigDecimalEntry(long pk, long bigDecimalEntryPK) {
		getPersistence().removeBigDecimalEntry(pk, bigDecimalEntryPK);
	}

	/**
	 * Removes the association between the lv entry version and the big decimal entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntry the big decimal entry
	 */
	public static void removeBigDecimalEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry
			bigDecimalEntry) {

		getPersistence().removeBigDecimalEntry(pk, bigDecimalEntry);
	}

	/**
	 * Removes the association between the lv entry version and the big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntryPKs the primary keys of the big decimal entries
	 */
	public static void removeBigDecimalEntries(
		long pk, long[] bigDecimalEntryPKs) {

		getPersistence().removeBigDecimalEntries(pk, bigDecimalEntryPKs);
	}

	/**
	 * Removes the association between the lv entry version and the big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntries the big decimal entries
	 */
	public static void removeBigDecimalEntries(
		long pk,
		List
			<com.liferay.portal.tools.service.builder.test.model.
				BigDecimalEntry> bigDecimalEntries) {

		getPersistence().removeBigDecimalEntries(pk, bigDecimalEntries);
	}

	/**
	 * Sets the big decimal entries associated with the lv entry version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntryPKs the primary keys of the big decimal entries to be associated with the lv entry version
	 */
	public static void setBigDecimalEntries(
		long pk, long[] bigDecimalEntryPKs) {

		getPersistence().setBigDecimalEntries(pk, bigDecimalEntryPKs);
	}

	/**
	 * Sets the big decimal entries associated with the lv entry version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry version
	 * @param bigDecimalEntries the big decimal entries to be associated with the lv entry version
	 */
	public static void setBigDecimalEntries(
		long pk,
		List
			<com.liferay.portal.tools.service.builder.test.model.
				BigDecimalEntry> bigDecimalEntries) {

		getPersistence().setBigDecimalEntries(pk, bigDecimalEntries);
	}

	public static LVEntryVersionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LVEntryVersionPersistence, LVEntryVersionPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LVEntryVersionPersistence.class);

		ServiceTracker<LVEntryVersionPersistence, LVEntryVersionPersistence>
			serviceTracker =
				new ServiceTracker
					<LVEntryVersionPersistence, LVEntryVersionPersistence>(
						bundle.getBundleContext(),
						LVEntryVersionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}