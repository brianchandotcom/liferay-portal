/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.ERCVersionedEntryVersion;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the erc versioned entry version service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.ERCVersionedEntryVersionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCVersionedEntryVersionPersistence
 * @generated
 */
public class ERCVersionedEntryVersionUtil {

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
	public static void clearCache(
		ERCVersionedEntryVersion ercVersionedEntryVersion) {

		getPersistence().clearCache(ercVersionedEntryVersion);
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
	public static Map<Serializable, ERCVersionedEntryVersion>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ERCVersionedEntryVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ERCVersionedEntryVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ERCVersionedEntryVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ERCVersionedEntryVersion update(
		ERCVersionedEntryVersion ercVersionedEntryVersion) {

		return getPersistence().update(ercVersionedEntryVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ERCVersionedEntryVersion update(
		ERCVersionedEntryVersion ercVersionedEntryVersion,
		ServiceContext serviceContext) {

		return getPersistence().update(
			ercVersionedEntryVersion, serviceContext);
	}

	/**
	 * Returns all the erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @return the matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByErcVersionedEntryId(
		long ercVersionedEntryId) {

		return getPersistence().findByErcVersionedEntryId(ercVersionedEntryId);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByErcVersionedEntryId(
		long ercVersionedEntryId, int start, int end) {

		return getPersistence().findByErcVersionedEntryId(
			ercVersionedEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByErcVersionedEntryId(
		long ercVersionedEntryId, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().findByErcVersionedEntryId(
			ercVersionedEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByErcVersionedEntryId(
		long ercVersionedEntryId, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByErcVersionedEntryId(
			ercVersionedEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion findByErcVersionedEntryId_First(
			long ercVersionedEntryId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByErcVersionedEntryId_First(
			ercVersionedEntryId, orderByComparator);
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion fetchByErcVersionedEntryId_First(
		long ercVersionedEntryId,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().fetchByErcVersionedEntryId_First(
			ercVersionedEntryId, orderByComparator);
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion findByErcVersionedEntryId_Last(
			long ercVersionedEntryId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByErcVersionedEntryId_Last(
			ercVersionedEntryId, orderByComparator);
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion fetchByErcVersionedEntryId_Last(
		long ercVersionedEntryId,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().fetchByErcVersionedEntryId_Last(
			ercVersionedEntryId, orderByComparator);
	}

	/**
	 * Returns the erc versioned entry versions before and after the current erc versioned entry version in the ordered set where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the current erc versioned entry version
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	public static ERCVersionedEntryVersion[]
			findByErcVersionedEntryId_PrevAndNext(
				long ercVersionedEntryVersionId, long ercVersionedEntryId,
				OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByErcVersionedEntryId_PrevAndNext(
			ercVersionedEntryVersionId, ercVersionedEntryId, orderByComparator);
	}

	/**
	 * Removes all the erc versioned entry versions where ercVersionedEntryId = &#63; from the database.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 */
	public static void removeByErcVersionedEntryId(long ercVersionedEntryId) {
		getPersistence().removeByErcVersionedEntryId(ercVersionedEntryId);
	}

	/**
	 * Returns the number of erc versioned entry versions where ercVersionedEntryId = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @return the number of matching erc versioned entry versions
	 */
	public static int countByErcVersionedEntryId(long ercVersionedEntryId) {
		return getPersistence().countByErcVersionedEntryId(ercVersionedEntryId);
	}

	/**
	 * Returns the erc versioned entry version where ercVersionedEntryId = &#63; and version = &#63; or throws a <code>NoSuchERCVersionedEntryVersionException</code> if it could not be found.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @return the matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion findByErcVersionedEntryId_Version(
			long ercVersionedEntryId, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByErcVersionedEntryId_Version(
			ercVersionedEntryId, version);
	}

	/**
	 * Returns the erc versioned entry version where ercVersionedEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @return the matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion fetchByErcVersionedEntryId_Version(
		long ercVersionedEntryId, int version) {

		return getPersistence().fetchByErcVersionedEntryId_Version(
			ercVersionedEntryId, version);
	}

	/**
	 * Returns the erc versioned entry version where ercVersionedEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion fetchByErcVersionedEntryId_Version(
		long ercVersionedEntryId, int version, boolean useFinderCache) {

		return getPersistence().fetchByErcVersionedEntryId_Version(
			ercVersionedEntryId, version, useFinderCache);
	}

	/**
	 * Removes the erc versioned entry version where ercVersionedEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @return the erc versioned entry version that was removed
	 */
	public static ERCVersionedEntryVersion removeByErcVersionedEntryId_Version(
			long ercVersionedEntryId, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().removeByErcVersionedEntryId_Version(
			ercVersionedEntryId, version);
	}

	/**
	 * Returns the number of erc versioned entry versions where ercVersionedEntryId = &#63; and version = &#63;.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID
	 * @param version the version
	 * @return the number of matching erc versioned entry versions
	 */
	public static int countByErcVersionedEntryId_Version(
		long ercVersionedEntryId, int version) {

		return getPersistence().countByErcVersionedEntryId_Version(
			ercVersionedEntryId, version);
	}

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion findByUuid_First(
			String uuid,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion fetchByUuid_First(
		String uuid,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion findByUuid_Last(
			String uuid,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion fetchByUuid_Last(
		String uuid,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the erc versioned entry versions before and after the current erc versioned entry version in the ordered set where uuid = &#63;.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the current erc versioned entry version
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	public static ERCVersionedEntryVersion[] findByUuid_PrevAndNext(
			long ercVersionedEntryVersionId, String uuid,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByUuid_PrevAndNext(
			ercVersionedEntryVersionId, uuid, orderByComparator);
	}

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching erc versioned entry versions
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUuid_Version(
		String uuid, int version) {

		return getPersistence().findByUuid_Version(uuid, version);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end) {

		return getPersistence().findByUuid_Version(uuid, version, start, end);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().findByUuid_Version(
			uuid, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_Version(
			uuid, version, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion findByUuid_Version_First(
			String uuid, int version,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByUuid_Version_First(
			uuid, version, orderByComparator);
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion fetchByUuid_Version_First(
		String uuid, int version,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().fetchByUuid_Version_First(
			uuid, version, orderByComparator);
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion findByUuid_Version_Last(
			String uuid, int version,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByUuid_Version_Last(
			uuid, version, orderByComparator);
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion fetchByUuid_Version_Last(
		String uuid, int version,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().fetchByUuid_Version_Last(
			uuid, version, orderByComparator);
	}

	/**
	 * Returns the erc versioned entry versions before and after the current erc versioned entry version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the current erc versioned entry version
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	public static ERCVersionedEntryVersion[] findByUuid_Version_PrevAndNext(
			long ercVersionedEntryVersionId, String uuid, int version,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByUuid_Version_PrevAndNext(
			ercVersionedEntryVersionId, uuid, version, orderByComparator);
	}

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 */
	public static void removeByUuid_Version(String uuid, int version) {
		getPersistence().removeByUuid_Version(uuid, version);
	}

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the number of matching erc versioned entry versions
	 */
	public static int countByUuid_Version(String uuid, int version) {
		return getPersistence().countByUuid_Version(uuid, version);
	}

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUUID_G(
		String uuid, long groupId) {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end) {

		return getPersistence().findByUUID_G(uuid, groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().findByUUID_G(
			uuid, groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUUID_G(
			uuid, groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion findByUUID_G_First(
			String uuid, long groupId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByUUID_G_First(
			uuid, groupId, orderByComparator);
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion fetchByUUID_G_First(
		String uuid, long groupId,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().fetchByUUID_G_First(
			uuid, groupId, orderByComparator);
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion findByUUID_G_Last(
			String uuid, long groupId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByUUID_G_Last(
			uuid, groupId, orderByComparator);
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion fetchByUUID_G_Last(
		String uuid, long groupId,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().fetchByUUID_G_Last(
			uuid, groupId, orderByComparator);
	}

	/**
	 * Returns the erc versioned entry versions before and after the current erc versioned entry version in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the current erc versioned entry version
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	public static ERCVersionedEntryVersion[] findByUUID_G_PrevAndNext(
			long ercVersionedEntryVersionId, String uuid, long groupId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByUUID_G_PrevAndNext(
			ercVersionedEntryVersionId, uuid, groupId, orderByComparator);
	}

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 */
	public static void removeByUUID_G(String uuid, long groupId) {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching erc versioned entry versions
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the erc versioned entry version where uuid = &#63; and groupId = &#63; and version = &#63; or throws a <code>NoSuchERCVersionedEntryVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion findByUUID_G_Version(
			String uuid, long groupId, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByUUID_G_Version(uuid, groupId, version);
	}

	/**
	 * Returns the erc versioned entry version where uuid = &#63; and groupId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion fetchByUUID_G_Version(
		String uuid, long groupId, int version) {

		return getPersistence().fetchByUUID_G_Version(uuid, groupId, version);
	}

	/**
	 * Returns the erc versioned entry version where uuid = &#63; and groupId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion fetchByUUID_G_Version(
		String uuid, long groupId, int version, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G_Version(
			uuid, groupId, version, useFinderCache);
	}

	/**
	 * Removes the erc versioned entry version where uuid = &#63; and groupId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the erc versioned entry version that was removed
	 */
	public static ERCVersionedEntryVersion removeByUUID_G_Version(
			String uuid, long groupId, int version)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().removeByUUID_G_Version(uuid, groupId, version);
	}

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63; and groupId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching erc versioned entry versions
	 */
	public static int countByUUID_G_Version(
		String uuid, long groupId, int version) {

		return getPersistence().countByUUID_G_Version(uuid, groupId, version);
	}

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the erc versioned entry versions before and after the current erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the current erc versioned entry version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	public static ERCVersionedEntryVersion[] findByUuid_C_PrevAndNext(
			long ercVersionedEntryVersionId, String uuid, long companyId,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByUuid_C_PrevAndNext(
			ercVersionedEntryVersionId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of erc versioned entry versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching erc versioned entry versions
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version) {

		return getPersistence().findByUuid_C_Version(uuid, companyId, version);
	}

	/**
	 * Returns a range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end) {

		return getPersistence().findByUuid_C_Version(
			uuid, companyId, version, start, end);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().findByUuid_C_Version(
			uuid, companyId, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C_Version(
			uuid, companyId, version, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion findByUuid_C_Version_First(
			String uuid, long companyId, int version,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByUuid_C_Version_First(
			uuid, companyId, version, orderByComparator);
	}

	/**
	 * Returns the first erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion fetchByUuid_C_Version_First(
		String uuid, long companyId, int version,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().fetchByUuid_C_Version_First(
			uuid, companyId, version, orderByComparator);
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion findByUuid_C_Version_Last(
			String uuid, long companyId, int version,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByUuid_C_Version_Last(
			uuid, companyId, version, orderByComparator);
	}

	/**
	 * Returns the last erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching erc versioned entry version, or <code>null</code> if a matching erc versioned entry version could not be found
	 */
	public static ERCVersionedEntryVersion fetchByUuid_C_Version_Last(
		String uuid, long companyId, int version,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().fetchByUuid_C_Version_Last(
			uuid, companyId, version, orderByComparator);
	}

	/**
	 * Returns the erc versioned entry versions before and after the current erc versioned entry version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the current erc versioned entry version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	public static ERCVersionedEntryVersion[] findByUuid_C_Version_PrevAndNext(
			long ercVersionedEntryVersionId, String uuid, long companyId,
			int version,
			OrderByComparator<ERCVersionedEntryVersion> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByUuid_C_Version_PrevAndNext(
			ercVersionedEntryVersionId, uuid, companyId, version,
			orderByComparator);
	}

	/**
	 * Removes all the erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63; from the database.
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
	 * Returns the number of erc versioned entry versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the number of matching erc versioned entry versions
	 */
	public static int countByUuid_C_Version(
		String uuid, long companyId, int version) {

		return getPersistence().countByUuid_C_Version(uuid, companyId, version);
	}

	/**
	 * Caches the erc versioned entry version in the entity cache if it is enabled.
	 *
	 * @param ercVersionedEntryVersion the erc versioned entry version
	 */
	public static void cacheResult(
		ERCVersionedEntryVersion ercVersionedEntryVersion) {

		getPersistence().cacheResult(ercVersionedEntryVersion);
	}

	/**
	 * Caches the erc versioned entry versions in the entity cache if it is enabled.
	 *
	 * @param ercVersionedEntryVersions the erc versioned entry versions
	 */
	public static void cacheResult(
		List<ERCVersionedEntryVersion> ercVersionedEntryVersions) {

		getPersistence().cacheResult(ercVersionedEntryVersions);
	}

	/**
	 * Creates a new erc versioned entry version with the primary key. Does not add the erc versioned entry version to the database.
	 *
	 * @param ercVersionedEntryVersionId the primary key for the new erc versioned entry version
	 * @return the new erc versioned entry version
	 */
	public static ERCVersionedEntryVersion create(
		long ercVersionedEntryVersionId) {

		return getPersistence().create(ercVersionedEntryVersionId);
	}

	/**
	 * Removes the erc versioned entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the erc versioned entry version
	 * @return the erc versioned entry version that was removed
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	public static ERCVersionedEntryVersion remove(
			long ercVersionedEntryVersionId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().remove(ercVersionedEntryVersionId);
	}

	public static ERCVersionedEntryVersion updateImpl(
		ERCVersionedEntryVersion ercVersionedEntryVersion) {

		return getPersistence().updateImpl(ercVersionedEntryVersion);
	}

	/**
	 * Returns the erc versioned entry version with the primary key or throws a <code>NoSuchERCVersionedEntryVersionException</code> if it could not be found.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the erc versioned entry version
	 * @return the erc versioned entry version
	 * @throws NoSuchERCVersionedEntryVersionException if a erc versioned entry version with the primary key could not be found
	 */
	public static ERCVersionedEntryVersion findByPrimaryKey(
			long ercVersionedEntryVersionId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCVersionedEntryVersionException {

		return getPersistence().findByPrimaryKey(ercVersionedEntryVersionId);
	}

	/**
	 * Returns the erc versioned entry version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ercVersionedEntryVersionId the primary key of the erc versioned entry version
	 * @return the erc versioned entry version, or <code>null</code> if a erc versioned entry version with the primary key could not be found
	 */
	public static ERCVersionedEntryVersion fetchByPrimaryKey(
		long ercVersionedEntryVersionId) {

		return getPersistence().fetchByPrimaryKey(ercVersionedEntryVersionId);
	}

	/**
	 * Returns all the erc versioned entry versions.
	 *
	 * @return the erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the erc versioned entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @return the range of erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findAll(
		int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the erc versioned entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCVersionedEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc versioned entry versions
	 * @param end the upper bound of the range of erc versioned entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of erc versioned entry versions
	 */
	public static List<ERCVersionedEntryVersion> findAll(
		int start, int end,
		OrderByComparator<ERCVersionedEntryVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the erc versioned entry versions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of erc versioned entry versions.
	 *
	 * @return the number of erc versioned entry versions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ERCVersionedEntryVersionPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		ERCVersionedEntryVersionPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile ERCVersionedEntryVersionPersistence _persistence;

}