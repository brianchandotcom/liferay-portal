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

package com.liferay.commerce.product.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPFriendlyURLEntry;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp friendly url entry service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPFriendlyURLEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPFriendlyURLEntryPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPFriendlyURLEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class CPFriendlyURLEntryUtil {
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
	public static void clearCache(CPFriendlyURLEntry cpFriendlyURLEntry) {
		getPersistence().clearCache(cpFriendlyURLEntry);
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
	public static List<CPFriendlyURLEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPFriendlyURLEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPFriendlyURLEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPFriendlyURLEntry update(
		CPFriendlyURLEntry cpFriendlyURLEntry) {
		return getPersistence().update(cpFriendlyURLEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPFriendlyURLEntry update(
		CPFriendlyURLEntry cpFriendlyURLEntry, ServiceContext serviceContext) {
		return getPersistence().update(cpFriendlyURLEntry, serviceContext);
	}

	/**
	* Returns all the cp friendly url entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cp friendly url entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @return the range of matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByUuid(String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cp friendly url entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp friendly url entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<CPFriendlyURLEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp friendly url entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry findByUuid_First(String uuid,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp friendly url entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry fetchByUuid_First(String uuid,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp friendly url entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry findByUuid_Last(String uuid,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp friendly url entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry fetchByUuid_Last(String uuid,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp friendly url entries before and after the current cp friendly url entry in the ordered set where uuid = &#63;.
	*
	* @param CPFriendlyURLEntryId the primary key of the current cp friendly url entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	*/
	public static CPFriendlyURLEntry[] findByUuid_PrevAndNext(
		long CPFriendlyURLEntryId, String uuid,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPFriendlyURLEntryId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the cp friendly url entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp friendly url entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp friendly url entries
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp friendly url entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPFriendlyURLEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry findByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp friendly url entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp friendly url entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp friendly url entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp friendly url entry that was removed
	*/
	public static CPFriendlyURLEntry removeByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp friendly url entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp friendly url entries
	*/
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp friendly url entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByUuid_C(String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cp friendly url entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @return the range of matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp friendly url entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp friendly url entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry findByUuid_C_First(String uuid,
		long companyId, OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry fetchByUuid_C_First(String uuid,
		long companyId, OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry findByUuid_C_Last(String uuid,
		long companyId, OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry fetchByUuid_C_Last(String uuid,
		long companyId, OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cp friendly url entries before and after the current cp friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPFriendlyURLEntryId the primary key of the current cp friendly url entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	*/
	public static CPFriendlyURLEntry[] findByUuid_C_PrevAndNext(
		long CPFriendlyURLEntryId, String uuid, long companyId,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPFriendlyURLEntryId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the cp friendly url entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp friendly url entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp friendly url entries
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByG_C_C(long groupId,
		long classNameId, long classPK) {
		return getPersistence().findByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns a range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @return the range of matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end) {
		return getPersistence()
				   .findByG_C_C(groupId, classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return getPersistence()
				   .findByG_C_C(groupId, classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_C(groupId, classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry findByG_C_C_First(long groupId,
		long classNameId, long classPK,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence()
				   .findByG_C_C_First(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the first cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry fetchByG_C_C_First(long groupId,
		long classNameId, long classPK,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_First(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry findByG_C_C_Last(long groupId,
		long classNameId, long classPK,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence()
				   .findByG_C_C_Last(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry fetchByG_C_C_Last(long groupId,
		long classNameId, long classPK,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_Last(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the cp friendly url entries before and after the current cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param CPFriendlyURLEntryId the primary key of the current cp friendly url entry
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	*/
	public static CPFriendlyURLEntry[] findByG_C_C_PrevAndNext(
		long CPFriendlyURLEntryId, long groupId, long classNameId,
		long classPK, OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence()
				   .findByG_C_C_PrevAndNext(CPFriendlyURLEntryId, groupId,
			classNameId, classPK, orderByComparator);
	}

	/**
	* Removes all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public static void removeByG_C_C(long groupId, long classNameId,
		long classPK) {
		getPersistence().removeByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the number of cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching cp friendly url entries
	*/
	public static int countByG_C_C(long groupId, long classNameId, long classPK) {
		return getPersistence().countByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @return the matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByG_C_U(long groupId,
		long classNameId, String urlTitle) {
		return getPersistence().findByG_C_U(groupId, classNameId, urlTitle);
	}

	/**
	* Returns a range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @return the range of matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByG_C_U(long groupId,
		long classNameId, String urlTitle, int start, int end) {
		return getPersistence()
				   .findByG_C_U(groupId, classNameId, urlTitle, start, end);
	}

	/**
	* Returns an ordered range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByG_C_U(long groupId,
		long classNameId, String urlTitle, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return getPersistence()
				   .findByG_C_U(groupId, classNameId, urlTitle, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByG_C_U(long groupId,
		long classNameId, String urlTitle, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_U(groupId, classNameId, urlTitle, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry findByG_C_U_First(long groupId,
		long classNameId, String urlTitle,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence()
				   .findByG_C_U_First(groupId, classNameId, urlTitle,
			orderByComparator);
	}

	/**
	* Returns the first cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry fetchByG_C_U_First(long groupId,
		long classNameId, String urlTitle,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_U_First(groupId, classNameId, urlTitle,
			orderByComparator);
	}

	/**
	* Returns the last cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry findByG_C_U_Last(long groupId,
		long classNameId, String urlTitle,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence()
				   .findByG_C_U_Last(groupId, classNameId, urlTitle,
			orderByComparator);
	}

	/**
	* Returns the last cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry fetchByG_C_U_Last(long groupId,
		long classNameId, String urlTitle,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_U_Last(groupId, classNameId, urlTitle,
			orderByComparator);
	}

	/**
	* Returns the cp friendly url entries before and after the current cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	*
	* @param CPFriendlyURLEntryId the primary key of the current cp friendly url entry
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	*/
	public static CPFriendlyURLEntry[] findByG_C_U_PrevAndNext(
		long CPFriendlyURLEntryId, long groupId, long classNameId,
		String urlTitle, OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence()
				   .findByG_C_U_PrevAndNext(CPFriendlyURLEntryId, groupId,
			classNameId, urlTitle, orderByComparator);
	}

	/**
	* Removes all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	*/
	public static void removeByG_C_U(long groupId, long classNameId,
		String urlTitle) {
		getPersistence().removeByG_C_U(groupId, classNameId, urlTitle);
	}

	/**
	* Returns the number of cp friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @return the number of matching cp friendly url entries
	*/
	public static int countByG_C_U(long groupId, long classNameId,
		String urlTitle) {
		return getPersistence().countByG_C_U(groupId, classNameId, urlTitle);
	}

	/**
	* Returns all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @return the matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByG_C_C_M(long groupId,
		long classNameId, long classPK, boolean main) {
		return getPersistence()
				   .findByG_C_C_M(groupId, classNameId, classPK, main);
	}

	/**
	* Returns a range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @return the range of matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByG_C_C_M(long groupId,
		long classNameId, long classPK, boolean main, int start, int end) {
		return getPersistence()
				   .findByG_C_C_M(groupId, classNameId, classPK, main, start,
			end);
	}

	/**
	* Returns an ordered range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByG_C_C_M(long groupId,
		long classNameId, long classPK, boolean main, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return getPersistence()
				   .findByG_C_C_M(groupId, classNameId, classPK, main, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findByG_C_C_M(long groupId,
		long classNameId, long classPK, boolean main, int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_C_M(groupId, classNameId, classPK, main, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry findByG_C_C_M_First(long groupId,
		long classNameId, long classPK, boolean main,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence()
				   .findByG_C_C_M_First(groupId, classNameId, classPK, main,
			orderByComparator);
	}

	/**
	* Returns the first cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry fetchByG_C_C_M_First(long groupId,
		long classNameId, long classPK, boolean main,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_M_First(groupId, classNameId, classPK, main,
			orderByComparator);
	}

	/**
	* Returns the last cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry findByG_C_C_M_Last(long groupId,
		long classNameId, long classPK, boolean main,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence()
				   .findByG_C_C_M_Last(groupId, classNameId, classPK, main,
			orderByComparator);
	}

	/**
	* Returns the last cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry fetchByG_C_C_M_Last(long groupId,
		long classNameId, long classPK, boolean main,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_M_Last(groupId, classNameId, classPK, main,
			orderByComparator);
	}

	/**
	* Returns the cp friendly url entries before and after the current cp friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	*
	* @param CPFriendlyURLEntryId the primary key of the current cp friendly url entry
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	*/
	public static CPFriendlyURLEntry[] findByG_C_C_M_PrevAndNext(
		long CPFriendlyURLEntryId, long groupId, long classNameId,
		long classPK, boolean main,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence()
				   .findByG_C_C_M_PrevAndNext(CPFriendlyURLEntryId, groupId,
			classNameId, classPK, main, orderByComparator);
	}

	/**
	* Removes all the cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	*/
	public static void removeByG_C_C_M(long groupId, long classNameId,
		long classPK, boolean main) {
		getPersistence().removeByG_C_C_M(groupId, classNameId, classPK, main);
	}

	/**
	* Returns the number of cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @return the number of matching cp friendly url entries
	*/
	public static int countByG_C_C_M(long groupId, long classNameId,
		long classPK, boolean main) {
		return getPersistence()
				   .countByG_C_C_M(groupId, classNameId, classPK, main);
	}

	/**
	* Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and languageId = &#63; and urlTitle = &#63; or throws a {@link NoSuchCPFriendlyURLEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param languageId the language ID
	* @param urlTitle the url title
	* @return the matching cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry findByG_C_L_U(long groupId,
		long classNameId, String languageId, String urlTitle)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence()
				   .findByG_C_L_U(groupId, classNameId, languageId, urlTitle);
	}

	/**
	* Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and languageId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param languageId the language ID
	* @param urlTitle the url title
	* @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry fetchByG_C_L_U(long groupId,
		long classNameId, String languageId, String urlTitle) {
		return getPersistence()
				   .fetchByG_C_L_U(groupId, classNameId, languageId, urlTitle);
	}

	/**
	* Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and languageId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param languageId the language ID
	* @param urlTitle the url title
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry fetchByG_C_L_U(long groupId,
		long classNameId, String languageId, String urlTitle,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_C_L_U(groupId, classNameId, languageId, urlTitle,
			retrieveFromCache);
	}

	/**
	* Removes the cp friendly url entry where groupId = &#63; and classNameId = &#63; and languageId = &#63; and urlTitle = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param languageId the language ID
	* @param urlTitle the url title
	* @return the cp friendly url entry that was removed
	*/
	public static CPFriendlyURLEntry removeByG_C_L_U(long groupId,
		long classNameId, String languageId, String urlTitle)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence()
				   .removeByG_C_L_U(groupId, classNameId, languageId, urlTitle);
	}

	/**
	* Returns the number of cp friendly url entries where groupId = &#63; and classNameId = &#63; and languageId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param languageId the language ID
	* @param urlTitle the url title
	* @return the number of matching cp friendly url entries
	*/
	public static int countByG_C_L_U(long groupId, long classNameId,
		String languageId, String urlTitle) {
		return getPersistence()
				   .countByG_C_L_U(groupId, classNameId, languageId, urlTitle);
	}

	/**
	* Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and urlTitle = &#63; or throws a {@link NoSuchCPFriendlyURLEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param languageId the language ID
	* @param urlTitle the url title
	* @return the matching cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry findByG_C_C_L_U(long groupId,
		long classNameId, long classPK, String languageId, String urlTitle)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence()
				   .findByG_C_C_L_U(groupId, classNameId, classPK, languageId,
			urlTitle);
	}

	/**
	* Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param languageId the language ID
	* @param urlTitle the url title
	* @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry fetchByG_C_C_L_U(long groupId,
		long classNameId, long classPK, String languageId, String urlTitle) {
		return getPersistence()
				   .fetchByG_C_C_L_U(groupId, classNameId, classPK, languageId,
			urlTitle);
	}

	/**
	* Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param languageId the language ID
	* @param urlTitle the url title
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry fetchByG_C_C_L_U(long groupId,
		long classNameId, long classPK, String languageId, String urlTitle,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_C_C_L_U(groupId, classNameId, classPK, languageId,
			urlTitle, retrieveFromCache);
	}

	/**
	* Removes the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and urlTitle = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param languageId the language ID
	* @param urlTitle the url title
	* @return the cp friendly url entry that was removed
	*/
	public static CPFriendlyURLEntry removeByG_C_C_L_U(long groupId,
		long classNameId, long classPK, String languageId, String urlTitle)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence()
				   .removeByG_C_C_L_U(groupId, classNameId, classPK,
			languageId, urlTitle);
	}

	/**
	* Returns the number of cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param languageId the language ID
	* @param urlTitle the url title
	* @return the number of matching cp friendly url entries
	*/
	public static int countByG_C_C_L_U(long groupId, long classNameId,
		long classPK, String languageId, String urlTitle) {
		return getPersistence()
				   .countByG_C_C_L_U(groupId, classNameId, classPK, languageId,
			urlTitle);
	}

	/**
	* Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and main = &#63; or throws a {@link NoSuchCPFriendlyURLEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param languageId the language ID
	* @param main the main
	* @return the matching cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry findByG_C_C_L_M(long groupId,
		long classNameId, long classPK, String languageId, boolean main)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence()
				   .findByG_C_C_L_M(groupId, classNameId, classPK, languageId,
			main);
	}

	/**
	* Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and main = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param languageId the language ID
	* @param main the main
	* @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry fetchByG_C_C_L_M(long groupId,
		long classNameId, long classPK, String languageId, boolean main) {
		return getPersistence()
				   .fetchByG_C_C_L_M(groupId, classNameId, classPK, languageId,
			main);
	}

	/**
	* Returns the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and main = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param languageId the language ID
	* @param main the main
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyURLEntry fetchByG_C_C_L_M(long groupId,
		long classNameId, long classPK, String languageId, boolean main,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_C_C_L_M(groupId, classNameId, classPK, languageId,
			main, retrieveFromCache);
	}

	/**
	* Removes the cp friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and main = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param languageId the language ID
	* @param main the main
	* @return the cp friendly url entry that was removed
	*/
	public static CPFriendlyURLEntry removeByG_C_C_L_M(long groupId,
		long classNameId, long classPK, String languageId, boolean main)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence()
				   .removeByG_C_C_L_M(groupId, classNameId, classPK,
			languageId, main);
	}

	/**
	* Returns the number of cp friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and languageId = &#63; and main = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param languageId the language ID
	* @param main the main
	* @return the number of matching cp friendly url entries
	*/
	public static int countByG_C_C_L_M(long groupId, long classNameId,
		long classPK, String languageId, boolean main) {
		return getPersistence()
				   .countByG_C_C_L_M(groupId, classNameId, classPK, languageId,
			main);
	}

	/**
	* Caches the cp friendly url entry in the entity cache if it is enabled.
	*
	* @param cpFriendlyURLEntry the cp friendly url entry
	*/
	public static void cacheResult(CPFriendlyURLEntry cpFriendlyURLEntry) {
		getPersistence().cacheResult(cpFriendlyURLEntry);
	}

	/**
	* Caches the cp friendly url entries in the entity cache if it is enabled.
	*
	* @param cpFriendlyURLEntries the cp friendly url entries
	*/
	public static void cacheResult(
		List<CPFriendlyURLEntry> cpFriendlyURLEntries) {
		getPersistence().cacheResult(cpFriendlyURLEntries);
	}

	/**
	* Creates a new cp friendly url entry with the primary key. Does not add the cp friendly url entry to the database.
	*
	* @param CPFriendlyURLEntryId the primary key for the new cp friendly url entry
	* @return the new cp friendly url entry
	*/
	public static CPFriendlyURLEntry create(long CPFriendlyURLEntryId) {
		return getPersistence().create(CPFriendlyURLEntryId);
	}

	/**
	* Removes the cp friendly url entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPFriendlyURLEntryId the primary key of the cp friendly url entry
	* @return the cp friendly url entry that was removed
	* @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	*/
	public static CPFriendlyURLEntry remove(long CPFriendlyURLEntryId)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence().remove(CPFriendlyURLEntryId);
	}

	public static CPFriendlyURLEntry updateImpl(
		CPFriendlyURLEntry cpFriendlyURLEntry) {
		return getPersistence().updateImpl(cpFriendlyURLEntry);
	}

	/**
	* Returns the cp friendly url entry with the primary key or throws a {@link NoSuchCPFriendlyURLEntryException} if it could not be found.
	*
	* @param CPFriendlyURLEntryId the primary key of the cp friendly url entry
	* @return the cp friendly url entry
	* @throws NoSuchCPFriendlyURLEntryException if a cp friendly url entry with the primary key could not be found
	*/
	public static CPFriendlyURLEntry findByPrimaryKey(long CPFriendlyURLEntryId)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException {
		return getPersistence().findByPrimaryKey(CPFriendlyURLEntryId);
	}

	/**
	* Returns the cp friendly url entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPFriendlyURLEntryId the primary key of the cp friendly url entry
	* @return the cp friendly url entry, or <code>null</code> if a cp friendly url entry with the primary key could not be found
	*/
	public static CPFriendlyURLEntry fetchByPrimaryKey(
		long CPFriendlyURLEntryId) {
		return getPersistence().fetchByPrimaryKey(CPFriendlyURLEntryId);
	}

	public static java.util.Map<java.io.Serializable, CPFriendlyURLEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp friendly url entries.
	*
	* @return the cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp friendly url entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @return the range of cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp friendly url entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findAll(int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp friendly url entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp friendly url entries
	*/
	public static List<CPFriendlyURLEntry> findAll(int start, int end,
		OrderByComparator<CPFriendlyURLEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp friendly url entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp friendly url entries.
	*
	* @return the number of cp friendly url entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPFriendlyURLEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPFriendlyURLEntryPersistence, CPFriendlyURLEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPFriendlyURLEntryPersistence.class);

		ServiceTracker<CPFriendlyURLEntryPersistence, CPFriendlyURLEntryPersistence> serviceTracker =
			new ServiceTracker<CPFriendlyURLEntryPersistence, CPFriendlyURLEntryPersistence>(bundle.getBundleContext(),
				CPFriendlyURLEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}