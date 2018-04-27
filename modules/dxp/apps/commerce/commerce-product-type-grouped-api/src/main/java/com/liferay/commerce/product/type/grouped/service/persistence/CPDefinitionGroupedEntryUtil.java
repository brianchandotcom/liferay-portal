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

package com.liferay.commerce.product.type.grouped.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp definition grouped entry service. This utility wraps {@link com.liferay.commerce.product.type.grouped.service.persistence.impl.CPDefinitionGroupedEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CPDefinitionGroupedEntryPersistence
 * @see com.liferay.commerce.product.type.grouped.service.persistence.impl.CPDefinitionGroupedEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionGroupedEntryUtil {
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
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {
		getPersistence().clearCache(cpDefinitionGroupedEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPDefinitionGroupedEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPDefinitionGroupedEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPDefinitionGroupedEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPDefinitionGroupedEntry update(
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {
		return getPersistence().update(cpDefinitionGroupedEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPDefinitionGroupedEntry update(
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(cpDefinitionGroupedEntry, serviceContext);
	}

	/**
	* Returns all the cp definition grouped entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp definition grouped entries
	*/
	public static List<CPDefinitionGroupedEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cp definition grouped entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @return the range of matching cp definition grouped entries
	*/
	public static List<CPDefinitionGroupedEntry> findByUuid(String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition grouped entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition grouped entries
	*/
	public static List<CPDefinitionGroupedEntry> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition grouped entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition grouped entries
	*/
	public static List<CPDefinitionGroupedEntry> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp definition grouped entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	*/
	public static CPDefinitionGroupedEntry findByUuid_First(String uuid,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws com.liferay.commerce.product.type.grouped.exception.NoSuchCPDefinitionGroupedEntryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp definition grouped entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public static CPDefinitionGroupedEntry fetchByUuid_First(String uuid,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp definition grouped entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	*/
	public static CPDefinitionGroupedEntry findByUuid_Last(String uuid,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws com.liferay.commerce.product.type.grouped.exception.NoSuchCPDefinitionGroupedEntryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp definition grouped entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public static CPDefinitionGroupedEntry fetchByUuid_Last(String uuid,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp definition grouped entries before and after the current cp definition grouped entry in the ordered set where uuid = &#63;.
	*
	* @param CPDefinitionGroupedEntryId the primary key of the current cp definition grouped entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	*/
	public static CPDefinitionGroupedEntry[] findByUuid_PrevAndNext(
		long CPDefinitionGroupedEntryId, String uuid,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws com.liferay.commerce.product.type.grouped.exception.NoSuchCPDefinitionGroupedEntryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPDefinitionGroupedEntryId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the cp definition grouped entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp definition grouped entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp definition grouped entries
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp definition grouped entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionGroupedEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	*/
	public static CPDefinitionGroupedEntry findByUUID_G(String uuid,
		long groupId)
		throws com.liferay.commerce.product.type.grouped.exception.NoSuchCPDefinitionGroupedEntryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp definition grouped entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public static CPDefinitionGroupedEntry fetchByUUID_G(String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp definition grouped entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public static CPDefinitionGroupedEntry fetchByUUID_G(String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp definition grouped entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp definition grouped entry that was removed
	*/
	public static CPDefinitionGroupedEntry removeByUUID_G(String uuid,
		long groupId)
		throws com.liferay.commerce.product.type.grouped.exception.NoSuchCPDefinitionGroupedEntryException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp definition grouped entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp definition grouped entries
	*/
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp definition grouped entries
	*/
	public static List<CPDefinitionGroupedEntry> findByUuid_C(String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @return the range of matching cp definition grouped entries
	*/
	public static List<CPDefinitionGroupedEntry> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition grouped entries
	*/
	public static List<CPDefinitionGroupedEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition grouped entries
	*/
	public static List<CPDefinitionGroupedEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	*/
	public static CPDefinitionGroupedEntry findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws com.liferay.commerce.product.type.grouped.exception.NoSuchCPDefinitionGroupedEntryException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public static CPDefinitionGroupedEntry fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	*/
	public static CPDefinitionGroupedEntry findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws com.liferay.commerce.product.type.grouped.exception.NoSuchCPDefinitionGroupedEntryException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public static CPDefinitionGroupedEntry fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cp definition grouped entries before and after the current cp definition grouped entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPDefinitionGroupedEntryId the primary key of the current cp definition grouped entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	*/
	public static CPDefinitionGroupedEntry[] findByUuid_C_PrevAndNext(
		long CPDefinitionGroupedEntryId, String uuid, long companyId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws com.liferay.commerce.product.type.grouped.exception.NoSuchCPDefinitionGroupedEntryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPDefinitionGroupedEntryId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the cp definition grouped entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp definition grouped entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp definition grouped entries
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the cp definition grouped entries where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the matching cp definition grouped entries
	*/
	public static List<CPDefinitionGroupedEntry> findByCPDefinitionId(
		long CPDefinitionId) {
		return getPersistence().findByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns a range of all the cp definition grouped entries where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @return the range of matching cp definition grouped entries
	*/
	public static List<CPDefinitionGroupedEntry> findByCPDefinitionId(
		long CPDefinitionId, int start, int end) {
		return getPersistence().findByCPDefinitionId(CPDefinitionId, start, end);
	}

	/**
	* Returns an ordered range of all the cp definition grouped entries where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition grouped entries
	*/
	public static List<CPDefinitionGroupedEntry> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		return getPersistence()
				   .findByCPDefinitionId(CPDefinitionId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition grouped entries where CPDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPDefinitionId the cp definition ID
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition grouped entries
	*/
	public static List<CPDefinitionGroupedEntry> findByCPDefinitionId(
		long CPDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCPDefinitionId(CPDefinitionId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	*/
	public static CPDefinitionGroupedEntry findByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws com.liferay.commerce.product.type.grouped.exception.NoSuchCPDefinitionGroupedEntryException {
		return getPersistence()
				   .findByCPDefinitionId_First(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the first cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public static CPDefinitionGroupedEntry fetchByCPDefinitionId_First(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionId_First(CPDefinitionId,
			orderByComparator);
	}

	/**
	* Returns the last cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	*/
	public static CPDefinitionGroupedEntry findByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws com.liferay.commerce.product.type.grouped.exception.NoSuchCPDefinitionGroupedEntryException {
		return getPersistence()
				   .findByCPDefinitionId_Last(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the last cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public static CPDefinitionGroupedEntry fetchByCPDefinitionId_Last(
		long CPDefinitionId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCPDefinitionId_Last(CPDefinitionId, orderByComparator);
	}

	/**
	* Returns the cp definition grouped entries before and after the current cp definition grouped entry in the ordered set where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionGroupedEntryId the primary key of the current cp definition grouped entry
	* @param CPDefinitionId the cp definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	*/
	public static CPDefinitionGroupedEntry[] findByCPDefinitionId_PrevAndNext(
		long CPDefinitionGroupedEntryId, long CPDefinitionId,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws com.liferay.commerce.product.type.grouped.exception.NoSuchCPDefinitionGroupedEntryException {
		return getPersistence()
				   .findByCPDefinitionId_PrevAndNext(CPDefinitionGroupedEntryId,
			CPDefinitionId, orderByComparator);
	}

	/**
	* Removes all the cp definition grouped entries where CPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	*/
	public static void removeByCPDefinitionId(long CPDefinitionId) {
		getPersistence().removeByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the number of cp definition grouped entries where CPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @return the number of matching cp definition grouped entries
	*/
	public static int countByCPDefinitionId(long CPDefinitionId) {
		return getPersistence().countByCPDefinitionId(CPDefinitionId);
	}

	/**
	* Returns the cp definition grouped entry where CPDefinitionId = &#63; and entryCPDefinitionId = &#63; or throws a {@link NoSuchCPDefinitionGroupedEntryException} if it could not be found.
	*
	* @param CPDefinitionId the cp definition ID
	* @param entryCPDefinitionId the entry cp definition ID
	* @return the matching cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a matching cp definition grouped entry could not be found
	*/
	public static CPDefinitionGroupedEntry findByC_E(long CPDefinitionId,
		long entryCPDefinitionId)
		throws com.liferay.commerce.product.type.grouped.exception.NoSuchCPDefinitionGroupedEntryException {
		return getPersistence().findByC_E(CPDefinitionId, entryCPDefinitionId);
	}

	/**
	* Returns the cp definition grouped entry where CPDefinitionId = &#63; and entryCPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param entryCPDefinitionId the entry cp definition ID
	* @return the matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public static CPDefinitionGroupedEntry fetchByC_E(long CPDefinitionId,
		long entryCPDefinitionId) {
		return getPersistence().fetchByC_E(CPDefinitionId, entryCPDefinitionId);
	}

	/**
	* Returns the cp definition grouped entry where CPDefinitionId = &#63; and entryCPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPDefinitionId the cp definition ID
	* @param entryCPDefinitionId the entry cp definition ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition grouped entry, or <code>null</code> if a matching cp definition grouped entry could not be found
	*/
	public static CPDefinitionGroupedEntry fetchByC_E(long CPDefinitionId,
		long entryCPDefinitionId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_E(CPDefinitionId, entryCPDefinitionId,
			retrieveFromCache);
	}

	/**
	* Removes the cp definition grouped entry where CPDefinitionId = &#63; and entryCPDefinitionId = &#63; from the database.
	*
	* @param CPDefinitionId the cp definition ID
	* @param entryCPDefinitionId the entry cp definition ID
	* @return the cp definition grouped entry that was removed
	*/
	public static CPDefinitionGroupedEntry removeByC_E(long CPDefinitionId,
		long entryCPDefinitionId)
		throws com.liferay.commerce.product.type.grouped.exception.NoSuchCPDefinitionGroupedEntryException {
		return getPersistence().removeByC_E(CPDefinitionId, entryCPDefinitionId);
	}

	/**
	* Returns the number of cp definition grouped entries where CPDefinitionId = &#63; and entryCPDefinitionId = &#63;.
	*
	* @param CPDefinitionId the cp definition ID
	* @param entryCPDefinitionId the entry cp definition ID
	* @return the number of matching cp definition grouped entries
	*/
	public static int countByC_E(long CPDefinitionId, long entryCPDefinitionId) {
		return getPersistence().countByC_E(CPDefinitionId, entryCPDefinitionId);
	}

	/**
	* Caches the cp definition grouped entry in the entity cache if it is enabled.
	*
	* @param cpDefinitionGroupedEntry the cp definition grouped entry
	*/
	public static void cacheResult(
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {
		getPersistence().cacheResult(cpDefinitionGroupedEntry);
	}

	/**
	* Caches the cp definition grouped entries in the entity cache if it is enabled.
	*
	* @param cpDefinitionGroupedEntries the cp definition grouped entries
	*/
	public static void cacheResult(
		List<CPDefinitionGroupedEntry> cpDefinitionGroupedEntries) {
		getPersistence().cacheResult(cpDefinitionGroupedEntries);
	}

	/**
	* Creates a new cp definition grouped entry with the primary key. Does not add the cp definition grouped entry to the database.
	*
	* @param CPDefinitionGroupedEntryId the primary key for the new cp definition grouped entry
	* @return the new cp definition grouped entry
	*/
	public static CPDefinitionGroupedEntry create(
		long CPDefinitionGroupedEntryId) {
		return getPersistence().create(CPDefinitionGroupedEntryId);
	}

	/**
	* Removes the cp definition grouped entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionGroupedEntryId the primary key of the cp definition grouped entry
	* @return the cp definition grouped entry that was removed
	* @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	*/
	public static CPDefinitionGroupedEntry remove(
		long CPDefinitionGroupedEntryId)
		throws com.liferay.commerce.product.type.grouped.exception.NoSuchCPDefinitionGroupedEntryException {
		return getPersistence().remove(CPDefinitionGroupedEntryId);
	}

	public static CPDefinitionGroupedEntry updateImpl(
		CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {
		return getPersistence().updateImpl(cpDefinitionGroupedEntry);
	}

	/**
	* Returns the cp definition grouped entry with the primary key or throws a {@link NoSuchCPDefinitionGroupedEntryException} if it could not be found.
	*
	* @param CPDefinitionGroupedEntryId the primary key of the cp definition grouped entry
	* @return the cp definition grouped entry
	* @throws NoSuchCPDefinitionGroupedEntryException if a cp definition grouped entry with the primary key could not be found
	*/
	public static CPDefinitionGroupedEntry findByPrimaryKey(
		long CPDefinitionGroupedEntryId)
		throws com.liferay.commerce.product.type.grouped.exception.NoSuchCPDefinitionGroupedEntryException {
		return getPersistence().findByPrimaryKey(CPDefinitionGroupedEntryId);
	}

	/**
	* Returns the cp definition grouped entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDefinitionGroupedEntryId the primary key of the cp definition grouped entry
	* @return the cp definition grouped entry, or <code>null</code> if a cp definition grouped entry with the primary key could not be found
	*/
	public static CPDefinitionGroupedEntry fetchByPrimaryKey(
		long CPDefinitionGroupedEntryId) {
		return getPersistence().fetchByPrimaryKey(CPDefinitionGroupedEntryId);
	}

	public static java.util.Map<java.io.Serializable, CPDefinitionGroupedEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp definition grouped entries.
	*
	* @return the cp definition grouped entries
	*/
	public static List<CPDefinitionGroupedEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp definition grouped entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @return the range of cp definition grouped entries
	*/
	public static List<CPDefinitionGroupedEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp definition grouped entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp definition grouped entries
	*/
	public static List<CPDefinitionGroupedEntry> findAll(int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp definition grouped entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionGroupedEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition grouped entries
	* @param end the upper bound of the range of cp definition grouped entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp definition grouped entries
	*/
	public static List<CPDefinitionGroupedEntry> findAll(int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp definition grouped entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp definition grouped entries.
	*
	* @return the number of cp definition grouped entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPDefinitionGroupedEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionGroupedEntryPersistence, CPDefinitionGroupedEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionGroupedEntryPersistence.class);

		ServiceTracker<CPDefinitionGroupedEntryPersistence, CPDefinitionGroupedEntryPersistence> serviceTracker =
			new ServiceTracker<CPDefinitionGroupedEntryPersistence, CPDefinitionGroupedEntryPersistence>(bundle.getBundleContext(),
				CPDefinitionGroupedEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}