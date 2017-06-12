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

import com.liferay.commerce.product.model.CPFriendlyUrlEntry;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp friendly url entry service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPFriendlyUrlEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPFriendlyUrlEntryPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPFriendlyUrlEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class CPFriendlyUrlEntryUtil {
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
	public static void clearCache(CPFriendlyUrlEntry cpFriendlyUrlEntry) {
		getPersistence().clearCache(cpFriendlyUrlEntry);
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
	public static List<CPFriendlyUrlEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPFriendlyUrlEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPFriendlyUrlEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPFriendlyUrlEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPFriendlyUrlEntry update(
		CPFriendlyUrlEntry cpFriendlyUrlEntry) {
		return getPersistence().update(cpFriendlyUrlEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPFriendlyUrlEntry update(
		CPFriendlyUrlEntry cpFriendlyUrlEntry, ServiceContext serviceContext) {
		return getPersistence().update(cpFriendlyUrlEntry, serviceContext);
	}

	/**
	* Returns all the cp friendly url entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp friendly url entries
	*/
	public static List<CPFriendlyUrlEntry> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cp friendly url entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyUrlEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @return the range of matching cp friendly url entries
	*/
	public static List<CPFriendlyUrlEntry> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cp friendly url entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyUrlEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp friendly url entries
	*/
	public static List<CPFriendlyUrlEntry> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CPFriendlyUrlEntry> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp friendly url entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyUrlEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp friendly url entries
	*/
	public static List<CPFriendlyUrlEntry> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<CPFriendlyUrlEntry> orderByComparator,
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
	* @throws NoSuchCPFriendlyUrlEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyUrlEntry findByUuid_First(java.lang.String uuid,
		OrderByComparator<CPFriendlyUrlEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyUrlEntryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp friendly url entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyUrlEntry fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<CPFriendlyUrlEntry> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp friendly url entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp friendly url entry
	* @throws NoSuchCPFriendlyUrlEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyUrlEntry findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CPFriendlyUrlEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyUrlEntryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp friendly url entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyUrlEntry fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<CPFriendlyUrlEntry> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp friendly url entries before and after the current cp friendly url entry in the ordered set where uuid = &#63;.
	*
	* @param CPFriendlyUrlEntryId the primary key of the current cp friendly url entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp friendly url entry
	* @throws NoSuchCPFriendlyUrlEntryException if a cp friendly url entry with the primary key could not be found
	*/
	public static CPFriendlyUrlEntry[] findByUuid_PrevAndNext(
		long CPFriendlyUrlEntryId, java.lang.String uuid,
		OrderByComparator<CPFriendlyUrlEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyUrlEntryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPFriendlyUrlEntryId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the cp friendly url entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp friendly url entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp friendly url entries
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp friendly url entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPFriendlyUrlEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp friendly url entry
	* @throws NoSuchCPFriendlyUrlEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyUrlEntry findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyUrlEntryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp friendly url entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyUrlEntry fetchByUUID_G(java.lang.String uuid,
		long groupId) {
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
	public static CPFriendlyUrlEntry fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp friendly url entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp friendly url entry that was removed
	*/
	public static CPFriendlyUrlEntry removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyUrlEntryException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp friendly url entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp friendly url entries
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp friendly url entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp friendly url entries
	*/
	public static List<CPFriendlyUrlEntry> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cp friendly url entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyUrlEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @return the range of matching cp friendly url entries
	*/
	public static List<CPFriendlyUrlEntry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp friendly url entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyUrlEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp friendly url entries
	*/
	public static List<CPFriendlyUrlEntry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPFriendlyUrlEntry> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp friendly url entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyUrlEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static List<CPFriendlyUrlEntry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPFriendlyUrlEntry> orderByComparator,
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
	* @throws NoSuchCPFriendlyUrlEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyUrlEntry findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CPFriendlyUrlEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyUrlEntryException {
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
	public static CPFriendlyUrlEntry fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPFriendlyUrlEntry> orderByComparator) {
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
	* @throws NoSuchCPFriendlyUrlEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyUrlEntry findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CPFriendlyUrlEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyUrlEntryException {
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
	public static CPFriendlyUrlEntry fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CPFriendlyUrlEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cp friendly url entries before and after the current cp friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPFriendlyUrlEntryId the primary key of the current cp friendly url entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp friendly url entry
	* @throws NoSuchCPFriendlyUrlEntryException if a cp friendly url entry with the primary key could not be found
	*/
	public static CPFriendlyUrlEntry[] findByUuid_C_PrevAndNext(
		long CPFriendlyUrlEntryId, java.lang.String uuid, long companyId,
		OrderByComparator<CPFriendlyUrlEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyUrlEntryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPFriendlyUrlEntryId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the cp friendly url entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp friendly url entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp friendly url entries
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns the cp friendly url entry where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or throws a {@link NoSuchCPFriendlyUrlEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @return the matching cp friendly url entry
	* @throws NoSuchCPFriendlyUrlEntryException if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyUrlEntry findByG_U_L(long groupId,
		java.lang.String urlTitle, java.lang.String languageId)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyUrlEntryException {
		return getPersistence().findByG_U_L(groupId, urlTitle, languageId);
	}

	/**
	* Returns the cp friendly url entry where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyUrlEntry fetchByG_U_L(long groupId,
		java.lang.String urlTitle, java.lang.String languageId) {
		return getPersistence().fetchByG_U_L(groupId, urlTitle, languageId);
	}

	/**
	* Returns the cp friendly url entry where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp friendly url entry, or <code>null</code> if a matching cp friendly url entry could not be found
	*/
	public static CPFriendlyUrlEntry fetchByG_U_L(long groupId,
		java.lang.String urlTitle, java.lang.String languageId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_U_L(groupId, urlTitle, languageId,
			retrieveFromCache);
	}

	/**
	* Removes the cp friendly url entry where groupId = &#63; and urlTitle = &#63; and languageId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @return the cp friendly url entry that was removed
	*/
	public static CPFriendlyUrlEntry removeByG_U_L(long groupId,
		java.lang.String urlTitle, java.lang.String languageId)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyUrlEntryException {
		return getPersistence().removeByG_U_L(groupId, urlTitle, languageId);
	}

	/**
	* Returns the number of cp friendly url entries where groupId = &#63; and urlTitle = &#63; and languageId = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @return the number of matching cp friendly url entries
	*/
	public static int countByG_U_L(long groupId, java.lang.String urlTitle,
		java.lang.String languageId) {
		return getPersistence().countByG_U_L(groupId, urlTitle, languageId);
	}

	/**
	* Caches the cp friendly url entry in the entity cache if it is enabled.
	*
	* @param cpFriendlyUrlEntry the cp friendly url entry
	*/
	public static void cacheResult(CPFriendlyUrlEntry cpFriendlyUrlEntry) {
		getPersistence().cacheResult(cpFriendlyUrlEntry);
	}

	/**
	* Caches the cp friendly url entries in the entity cache if it is enabled.
	*
	* @param cpFriendlyUrlEntries the cp friendly url entries
	*/
	public static void cacheResult(
		List<CPFriendlyUrlEntry> cpFriendlyUrlEntries) {
		getPersistence().cacheResult(cpFriendlyUrlEntries);
	}

	/**
	* Creates a new cp friendly url entry with the primary key. Does not add the cp friendly url entry to the database.
	*
	* @param CPFriendlyUrlEntryId the primary key for the new cp friendly url entry
	* @return the new cp friendly url entry
	*/
	public static CPFriendlyUrlEntry create(long CPFriendlyUrlEntryId) {
		return getPersistence().create(CPFriendlyUrlEntryId);
	}

	/**
	* Removes the cp friendly url entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPFriendlyUrlEntryId the primary key of the cp friendly url entry
	* @return the cp friendly url entry that was removed
	* @throws NoSuchCPFriendlyUrlEntryException if a cp friendly url entry with the primary key could not be found
	*/
	public static CPFriendlyUrlEntry remove(long CPFriendlyUrlEntryId)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyUrlEntryException {
		return getPersistence().remove(CPFriendlyUrlEntryId);
	}

	public static CPFriendlyUrlEntry updateImpl(
		CPFriendlyUrlEntry cpFriendlyUrlEntry) {
		return getPersistence().updateImpl(cpFriendlyUrlEntry);
	}

	/**
	* Returns the cp friendly url entry with the primary key or throws a {@link NoSuchCPFriendlyUrlEntryException} if it could not be found.
	*
	* @param CPFriendlyUrlEntryId the primary key of the cp friendly url entry
	* @return the cp friendly url entry
	* @throws NoSuchCPFriendlyUrlEntryException if a cp friendly url entry with the primary key could not be found
	*/
	public static CPFriendlyUrlEntry findByPrimaryKey(long CPFriendlyUrlEntryId)
		throws com.liferay.commerce.product.exception.NoSuchCPFriendlyUrlEntryException {
		return getPersistence().findByPrimaryKey(CPFriendlyUrlEntryId);
	}

	/**
	* Returns the cp friendly url entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPFriendlyUrlEntryId the primary key of the cp friendly url entry
	* @return the cp friendly url entry, or <code>null</code> if a cp friendly url entry with the primary key could not be found
	*/
	public static CPFriendlyUrlEntry fetchByPrimaryKey(
		long CPFriendlyUrlEntryId) {
		return getPersistence().fetchByPrimaryKey(CPFriendlyUrlEntryId);
	}

	public static java.util.Map<java.io.Serializable, CPFriendlyUrlEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp friendly url entries.
	*
	* @return the cp friendly url entries
	*/
	public static List<CPFriendlyUrlEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp friendly url entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyUrlEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @return the range of cp friendly url entries
	*/
	public static List<CPFriendlyUrlEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp friendly url entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyUrlEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp friendly url entries
	*/
	public static List<CPFriendlyUrlEntry> findAll(int start, int end,
		OrderByComparator<CPFriendlyUrlEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp friendly url entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPFriendlyUrlEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp friendly url entries
	* @param end the upper bound of the range of cp friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp friendly url entries
	*/
	public static List<CPFriendlyUrlEntry> findAll(int start, int end,
		OrderByComparator<CPFriendlyUrlEntry> orderByComparator,
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

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPFriendlyUrlEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPFriendlyUrlEntryPersistence, CPFriendlyUrlEntryPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CPFriendlyUrlEntryPersistence.class);
}