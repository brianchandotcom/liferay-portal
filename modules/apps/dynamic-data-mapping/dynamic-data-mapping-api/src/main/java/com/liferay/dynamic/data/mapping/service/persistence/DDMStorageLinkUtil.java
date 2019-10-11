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

package com.liferay.dynamic.data.mapping.service.persistence;

import com.liferay.dynamic.data.mapping.model.DDMStorageLink;
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
 * The persistence utility for the ddm storage link service. This utility wraps <code>com.liferay.dynamic.data.mapping.service.persistence.impl.DDMStorageLinkPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStorageLinkPersistence
 * @generated
 */
public class DDMStorageLinkUtil {

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
	public static void clearCache(DDMStorageLink ddmStorageLink) {
		getPersistence().clearCache(ddmStorageLink);
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
	public static Map<Serializable, DDMStorageLink> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DDMStorageLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DDMStorageLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DDMStorageLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DDMStorageLink update(DDMStorageLink ddmStorageLink) {
		return getPersistence().update(ddmStorageLink);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DDMStorageLink update(
		DDMStorageLink ddmStorageLink, ServiceContext serviceContext) {

		return getPersistence().update(ddmStorageLink, serviceContext);
	}

	/**
	 * Returns all the ddm storage links where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching ddm storage links
	 */
	public static List<DDMStorageLink> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the ddm storage links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm storage links
	 * @param end the upper bound of the range of ddm storage links (not inclusive)
	 * @return the range of matching ddm storage links
	 */
	public static List<DDMStorageLink> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm storage links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm storage links
	 * @param end the upper bound of the range of ddm storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm storage links
	 */
	public static List<DDMStorageLink> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm storage links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm storage links
	 * @param end the upper bound of the range of ddm storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm storage links
	 */
	public static List<DDMStorageLink> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm storage link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm storage link
	 * @throws NoSuchStorageLinkException if a matching ddm storage link could not be found
	 */
	public static DDMStorageLink findByUuid_First(
			String uuid, OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStorageLinkException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first ddm storage link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm storage link, or <code>null</code> if a matching ddm storage link could not be found
	 */
	public static DDMStorageLink fetchByUuid_First(
		String uuid, OrderByComparator<DDMStorageLink> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last ddm storage link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm storage link
	 * @throws NoSuchStorageLinkException if a matching ddm storage link could not be found
	 */
	public static DDMStorageLink findByUuid_Last(
			String uuid, OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStorageLinkException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last ddm storage link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm storage link, or <code>null</code> if a matching ddm storage link could not be found
	 */
	public static DDMStorageLink fetchByUuid_Last(
		String uuid, OrderByComparator<DDMStorageLink> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the ddm storage links before and after the current ddm storage link in the ordered set where uuid = &#63;.
	 *
	 * @param storageLinkId the primary key of the current ddm storage link
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm storage link
	 * @throws NoSuchStorageLinkException if a ddm storage link with the primary key could not be found
	 */
	public static DDMStorageLink[] findByUuid_PrevAndNext(
			long storageLinkId, String uuid,
			OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStorageLinkException {

		return getPersistence().findByUuid_PrevAndNext(
			storageLinkId, uuid, orderByComparator);
	}

	/**
	 * Removes all the ddm storage links where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of ddm storage links where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching ddm storage links
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the ddm storage links where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching ddm storage links
	 */
	public static List<DDMStorageLink> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the ddm storage links where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm storage links
	 * @param end the upper bound of the range of ddm storage links (not inclusive)
	 * @return the range of matching ddm storage links
	 */
	public static List<DDMStorageLink> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm storage links where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm storage links
	 * @param end the upper bound of the range of ddm storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm storage links
	 */
	public static List<DDMStorageLink> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm storage links where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm storage links
	 * @param end the upper bound of the range of ddm storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm storage links
	 */
	public static List<DDMStorageLink> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm storage link
	 * @throws NoSuchStorageLinkException if a matching ddm storage link could not be found
	 */
	public static DDMStorageLink findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStorageLinkException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first ddm storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm storage link, or <code>null</code> if a matching ddm storage link could not be found
	 */
	public static DDMStorageLink fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DDMStorageLink> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last ddm storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm storage link
	 * @throws NoSuchStorageLinkException if a matching ddm storage link could not be found
	 */
	public static DDMStorageLink findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStorageLinkException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last ddm storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm storage link, or <code>null</code> if a matching ddm storage link could not be found
	 */
	public static DDMStorageLink fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DDMStorageLink> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the ddm storage links before and after the current ddm storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param storageLinkId the primary key of the current ddm storage link
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm storage link
	 * @throws NoSuchStorageLinkException if a ddm storage link with the primary key could not be found
	 */
	public static DDMStorageLink[] findByUuid_C_PrevAndNext(
			long storageLinkId, String uuid, long companyId,
			OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStorageLinkException {

		return getPersistence().findByUuid_C_PrevAndNext(
			storageLinkId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the ddm storage links where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of ddm storage links where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching ddm storage links
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the ddm storage link where classPK = &#63; or throws a <code>NoSuchStorageLinkException</code> if it could not be found.
	 *
	 * @param classPK the class pk
	 * @return the matching ddm storage link
	 * @throws NoSuchStorageLinkException if a matching ddm storage link could not be found
	 */
	public static DDMStorageLink findByClassPK(long classPK)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStorageLinkException {

		return getPersistence().findByClassPK(classPK);
	}

	/**
	 * Returns the ddm storage link where classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classPK the class pk
	 * @return the matching ddm storage link, or <code>null</code> if a matching ddm storage link could not be found
	 */
	public static DDMStorageLink fetchByClassPK(long classPK) {
		return getPersistence().fetchByClassPK(classPK);
	}

	/**
	 * Returns the ddm storage link where classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm storage link, or <code>null</code> if a matching ddm storage link could not be found
	 */
	public static DDMStorageLink fetchByClassPK(
		long classPK, boolean useFinderCache) {

		return getPersistence().fetchByClassPK(classPK, useFinderCache);
	}

	/**
	 * Removes the ddm storage link where classPK = &#63; from the database.
	 *
	 * @param classPK the class pk
	 * @return the ddm storage link that was removed
	 */
	public static DDMStorageLink removeByClassPK(long classPK)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStorageLinkException {

		return getPersistence().removeByClassPK(classPK);
	}

	/**
	 * Returns the number of ddm storage links where classPK = &#63;.
	 *
	 * @param classPK the class pk
	 * @return the number of matching ddm storage links
	 */
	public static int countByClassPK(long classPK) {
		return getPersistence().countByClassPK(classPK);
	}

	/**
	 * Returns all the ddm storage links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the matching ddm storage links
	 */
	public static List<DDMStorageLink> findByStructureId(long structureId) {
		return getPersistence().findByStructureId(structureId);
	}

	/**
	 * Returns a range of all the ddm storage links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm storage links
	 * @param end the upper bound of the range of ddm storage links (not inclusive)
	 * @return the range of matching ddm storage links
	 */
	public static List<DDMStorageLink> findByStructureId(
		long structureId, int start, int end) {

		return getPersistence().findByStructureId(structureId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm storage links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm storage links
	 * @param end the upper bound of the range of ddm storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm storage links
	 */
	public static List<DDMStorageLink> findByStructureId(
		long structureId, int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator) {

		return getPersistence().findByStructureId(
			structureId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm storage links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of ddm storage links
	 * @param end the upper bound of the range of ddm storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm storage links
	 */
	public static List<DDMStorageLink> findByStructureId(
		long structureId, int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByStructureId(
			structureId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm storage link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm storage link
	 * @throws NoSuchStorageLinkException if a matching ddm storage link could not be found
	 */
	public static DDMStorageLink findByStructureId_First(
			long structureId,
			OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStorageLinkException {

		return getPersistence().findByStructureId_First(
			structureId, orderByComparator);
	}

	/**
	 * Returns the first ddm storage link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm storage link, or <code>null</code> if a matching ddm storage link could not be found
	 */
	public static DDMStorageLink fetchByStructureId_First(
		long structureId, OrderByComparator<DDMStorageLink> orderByComparator) {

		return getPersistence().fetchByStructureId_First(
			structureId, orderByComparator);
	}

	/**
	 * Returns the last ddm storage link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm storage link
	 * @throws NoSuchStorageLinkException if a matching ddm storage link could not be found
	 */
	public static DDMStorageLink findByStructureId_Last(
			long structureId,
			OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStorageLinkException {

		return getPersistence().findByStructureId_Last(
			structureId, orderByComparator);
	}

	/**
	 * Returns the last ddm storage link in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm storage link, or <code>null</code> if a matching ddm storage link could not be found
	 */
	public static DDMStorageLink fetchByStructureId_Last(
		long structureId, OrderByComparator<DDMStorageLink> orderByComparator) {

		return getPersistence().fetchByStructureId_Last(
			structureId, orderByComparator);
	}

	/**
	 * Returns the ddm storage links before and after the current ddm storage link in the ordered set where structureId = &#63;.
	 *
	 * @param storageLinkId the primary key of the current ddm storage link
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm storage link
	 * @throws NoSuchStorageLinkException if a ddm storage link with the primary key could not be found
	 */
	public static DDMStorageLink[] findByStructureId_PrevAndNext(
			long storageLinkId, long structureId,
			OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStorageLinkException {

		return getPersistence().findByStructureId_PrevAndNext(
			storageLinkId, structureId, orderByComparator);
	}

	/**
	 * Removes all the ddm storage links where structureId = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 */
	public static void removeByStructureId(long structureId) {
		getPersistence().removeByStructureId(structureId);
	}

	/**
	 * Returns the number of ddm storage links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the number of matching ddm storage links
	 */
	public static int countByStructureId(long structureId) {
		return getPersistence().countByStructureId(structureId);
	}

	/**
	 * Returns all the ddm storage links where structureVersionId = &#63;.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the matching ddm storage links
	 */
	public static List<DDMStorageLink> findByStructureVersionId(
		long structureVersionId) {

		return getPersistence().findByStructureVersionId(structureVersionId);
	}

	/**
	 * Returns a range of all the ddm storage links where structureVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureVersionId the structure version ID
	 * @param start the lower bound of the range of ddm storage links
	 * @param end the upper bound of the range of ddm storage links (not inclusive)
	 * @return the range of matching ddm storage links
	 */
	public static List<DDMStorageLink> findByStructureVersionId(
		long structureVersionId, int start, int end) {

		return getPersistence().findByStructureVersionId(
			structureVersionId, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm storage links where structureVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureVersionId the structure version ID
	 * @param start the lower bound of the range of ddm storage links
	 * @param end the upper bound of the range of ddm storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm storage links
	 */
	public static List<DDMStorageLink> findByStructureVersionId(
		long structureVersionId, int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator) {

		return getPersistence().findByStructureVersionId(
			structureVersionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm storage links where structureVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureVersionId the structure version ID
	 * @param start the lower bound of the range of ddm storage links
	 * @param end the upper bound of the range of ddm storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm storage links
	 */
	public static List<DDMStorageLink> findByStructureVersionId(
		long structureVersionId, int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByStructureVersionId(
			structureVersionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ddm storage link in the ordered set where structureVersionId = &#63;.
	 *
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm storage link
	 * @throws NoSuchStorageLinkException if a matching ddm storage link could not be found
	 */
	public static DDMStorageLink findByStructureVersionId_First(
			long structureVersionId,
			OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStorageLinkException {

		return getPersistence().findByStructureVersionId_First(
			structureVersionId, orderByComparator);
	}

	/**
	 * Returns the first ddm storage link in the ordered set where structureVersionId = &#63;.
	 *
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm storage link, or <code>null</code> if a matching ddm storage link could not be found
	 */
	public static DDMStorageLink fetchByStructureVersionId_First(
		long structureVersionId,
		OrderByComparator<DDMStorageLink> orderByComparator) {

		return getPersistence().fetchByStructureVersionId_First(
			structureVersionId, orderByComparator);
	}

	/**
	 * Returns the last ddm storage link in the ordered set where structureVersionId = &#63;.
	 *
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm storage link
	 * @throws NoSuchStorageLinkException if a matching ddm storage link could not be found
	 */
	public static DDMStorageLink findByStructureVersionId_Last(
			long structureVersionId,
			OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStorageLinkException {

		return getPersistence().findByStructureVersionId_Last(
			structureVersionId, orderByComparator);
	}

	/**
	 * Returns the last ddm storage link in the ordered set where structureVersionId = &#63;.
	 *
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm storage link, or <code>null</code> if a matching ddm storage link could not be found
	 */
	public static DDMStorageLink fetchByStructureVersionId_Last(
		long structureVersionId,
		OrderByComparator<DDMStorageLink> orderByComparator) {

		return getPersistence().fetchByStructureVersionId_Last(
			structureVersionId, orderByComparator);
	}

	/**
	 * Returns the ddm storage links before and after the current ddm storage link in the ordered set where structureVersionId = &#63;.
	 *
	 * @param storageLinkId the primary key of the current ddm storage link
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm storage link
	 * @throws NoSuchStorageLinkException if a ddm storage link with the primary key could not be found
	 */
	public static DDMStorageLink[] findByStructureVersionId_PrevAndNext(
			long storageLinkId, long structureVersionId,
			OrderByComparator<DDMStorageLink> orderByComparator)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStorageLinkException {

		return getPersistence().findByStructureVersionId_PrevAndNext(
			storageLinkId, structureVersionId, orderByComparator);
	}

	/**
	 * Returns all the ddm storage links where structureVersionId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureVersionIds the structure version IDs
	 * @return the matching ddm storage links
	 */
	public static List<DDMStorageLink> findByStructureVersionId(
		long[] structureVersionIds) {

		return getPersistence().findByStructureVersionId(structureVersionIds);
	}

	/**
	 * Returns a range of all the ddm storage links where structureVersionId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureVersionIds the structure version IDs
	 * @param start the lower bound of the range of ddm storage links
	 * @param end the upper bound of the range of ddm storage links (not inclusive)
	 * @return the range of matching ddm storage links
	 */
	public static List<DDMStorageLink> findByStructureVersionId(
		long[] structureVersionIds, int start, int end) {

		return getPersistence().findByStructureVersionId(
			structureVersionIds, start, end);
	}

	/**
	 * Returns an ordered range of all the ddm storage links where structureVersionId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureVersionIds the structure version IDs
	 * @param start the lower bound of the range of ddm storage links
	 * @param end the upper bound of the range of ddm storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm storage links
	 */
	public static List<DDMStorageLink> findByStructureVersionId(
		long[] structureVersionIds, int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator) {

		return getPersistence().findByStructureVersionId(
			structureVersionIds, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm storage links where structureVersionId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param structureVersionId the structure version ID
	 * @param start the lower bound of the range of ddm storage links
	 * @param end the upper bound of the range of ddm storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm storage links
	 */
	public static List<DDMStorageLink> findByStructureVersionId(
		long[] structureVersionIds, int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByStructureVersionId(
			structureVersionIds, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ddm storage links where structureVersionId = &#63; from the database.
	 *
	 * @param structureVersionId the structure version ID
	 */
	public static void removeByStructureVersionId(long structureVersionId) {
		getPersistence().removeByStructureVersionId(structureVersionId);
	}

	/**
	 * Returns the number of ddm storage links where structureVersionId = &#63;.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the number of matching ddm storage links
	 */
	public static int countByStructureVersionId(long structureVersionId) {
		return getPersistence().countByStructureVersionId(structureVersionId);
	}

	/**
	 * Returns the number of ddm storage links where structureVersionId = any &#63;.
	 *
	 * @param structureVersionIds the structure version IDs
	 * @return the number of matching ddm storage links
	 */
	public static int countByStructureVersionId(long[] structureVersionIds) {
		return getPersistence().countByStructureVersionId(structureVersionIds);
	}

	/**
	 * Caches the ddm storage link in the entity cache if it is enabled.
	 *
	 * @param ddmStorageLink the ddm storage link
	 */
	public static void cacheResult(DDMStorageLink ddmStorageLink) {
		getPersistence().cacheResult(ddmStorageLink);
	}

	/**
	 * Caches the ddm storage links in the entity cache if it is enabled.
	 *
	 * @param ddmStorageLinks the ddm storage links
	 */
	public static void cacheResult(List<DDMStorageLink> ddmStorageLinks) {
		getPersistence().cacheResult(ddmStorageLinks);
	}

	/**
	 * Creates a new ddm storage link with the primary key. Does not add the ddm storage link to the database.
	 *
	 * @param storageLinkId the primary key for the new ddm storage link
	 * @return the new ddm storage link
	 */
	public static DDMStorageLink create(long storageLinkId) {
		return getPersistence().create(storageLinkId);
	}

	/**
	 * Removes the ddm storage link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param storageLinkId the primary key of the ddm storage link
	 * @return the ddm storage link that was removed
	 * @throws NoSuchStorageLinkException if a ddm storage link with the primary key could not be found
	 */
	public static DDMStorageLink remove(long storageLinkId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStorageLinkException {

		return getPersistence().remove(storageLinkId);
	}

	public static DDMStorageLink updateImpl(DDMStorageLink ddmStorageLink) {
		return getPersistence().updateImpl(ddmStorageLink);
	}

	/**
	 * Returns the ddm storage link with the primary key or throws a <code>NoSuchStorageLinkException</code> if it could not be found.
	 *
	 * @param storageLinkId the primary key of the ddm storage link
	 * @return the ddm storage link
	 * @throws NoSuchStorageLinkException if a ddm storage link with the primary key could not be found
	 */
	public static DDMStorageLink findByPrimaryKey(long storageLinkId)
		throws com.liferay.dynamic.data.mapping.exception.
			NoSuchStorageLinkException {

		return getPersistence().findByPrimaryKey(storageLinkId);
	}

	/**
	 * Returns the ddm storage link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param storageLinkId the primary key of the ddm storage link
	 * @return the ddm storage link, or <code>null</code> if a ddm storage link with the primary key could not be found
	 */
	public static DDMStorageLink fetchByPrimaryKey(long storageLinkId) {
		return getPersistence().fetchByPrimaryKey(storageLinkId);
	}

	/**
	 * Returns all the ddm storage links.
	 *
	 * @return the ddm storage links
	 */
	public static List<DDMStorageLink> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ddm storage links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm storage links
	 * @param end the upper bound of the range of ddm storage links (not inclusive)
	 * @return the range of ddm storage links
	 */
	public static List<DDMStorageLink> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ddm storage links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm storage links
	 * @param end the upper bound of the range of ddm storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm storage links
	 */
	public static List<DDMStorageLink> findAll(
		int start, int end,
		OrderByComparator<DDMStorageLink> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ddm storage links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStorageLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm storage links
	 * @param end the upper bound of the range of ddm storage links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm storage links
	 */
	public static List<DDMStorageLink> findAll(
		int start, int end, OrderByComparator<DDMStorageLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ddm storage links from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ddm storage links.
	 *
	 * @return the number of ddm storage links
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DDMStorageLinkPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DDMStorageLinkPersistence, DDMStorageLinkPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DDMStorageLinkPersistence.class);

		ServiceTracker<DDMStorageLinkPersistence, DDMStorageLinkPersistence>
			serviceTracker =
				new ServiceTracker
					<DDMStorageLinkPersistence, DDMStorageLinkPersistence>(
						bundle.getBundleContext(),
						DDMStorageLinkPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}