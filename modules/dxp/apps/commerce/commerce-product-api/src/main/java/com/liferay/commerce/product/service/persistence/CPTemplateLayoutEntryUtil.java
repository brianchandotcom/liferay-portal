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

import com.liferay.commerce.product.model.CPTemplateLayoutEntry;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp template layout entry service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPTemplateLayoutEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPTemplateLayoutEntryPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPTemplateLayoutEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class CPTemplateLayoutEntryUtil {
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
	public static void clearCache(CPTemplateLayoutEntry cpTemplateLayoutEntry) {
		getPersistence().clearCache(cpTemplateLayoutEntry);
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
	public static List<CPTemplateLayoutEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPTemplateLayoutEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPTemplateLayoutEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPTemplateLayoutEntry update(
		CPTemplateLayoutEntry cpTemplateLayoutEntry) {
		return getPersistence().update(cpTemplateLayoutEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPTemplateLayoutEntry update(
		CPTemplateLayoutEntry cpTemplateLayoutEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(cpTemplateLayoutEntry, serviceContext);
	}

	/**
	* Returns all the cp template layout entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp template layout entries
	*/
	public static List<CPTemplateLayoutEntry> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cp template layout entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @return the range of matching cp template layout entries
	*/
	public static List<CPTemplateLayoutEntry> findByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cp template layout entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp template layout entries
	*/
	public static List<CPTemplateLayoutEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp template layout entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp template layout entries
	*/
	public static List<CPTemplateLayoutEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp template layout entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp template layout entry
	* @throws NoSuchCPTemplateLayoutEntryException if a matching cp template layout entry could not be found
	*/
	public static CPTemplateLayoutEntry findByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPTemplateLayoutEntryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp template layout entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	*/
	public static CPTemplateLayoutEntry fetchByUuid_First(
		java.lang.String uuid,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp template layout entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp template layout entry
	* @throws NoSuchCPTemplateLayoutEntryException if a matching cp template layout entry could not be found
	*/
	public static CPTemplateLayoutEntry findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPTemplateLayoutEntryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp template layout entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	*/
	public static CPTemplateLayoutEntry fetchByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp template layout entries before and after the current cp template layout entry in the ordered set where uuid = &#63;.
	*
	* @param CPFriendlyURLEntryId the primary key of the current cp template layout entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp template layout entry
	* @throws NoSuchCPTemplateLayoutEntryException if a cp template layout entry with the primary key could not be found
	*/
	public static CPTemplateLayoutEntry[] findByUuid_PrevAndNext(
		long CPFriendlyURLEntryId, java.lang.String uuid,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPTemplateLayoutEntryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPFriendlyURLEntryId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the cp template layout entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp template layout entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp template layout entries
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp template layout entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPTemplateLayoutEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp template layout entry
	* @throws NoSuchCPTemplateLayoutEntryException if a matching cp template layout entry could not be found
	*/
	public static CPTemplateLayoutEntry findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPTemplateLayoutEntryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp template layout entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	*/
	public static CPTemplateLayoutEntry fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp template layout entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	*/
	public static CPTemplateLayoutEntry fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp template layout entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp template layout entry that was removed
	*/
	public static CPTemplateLayoutEntry removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPTemplateLayoutEntryException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp template layout entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp template layout entries
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp template layout entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp template layout entries
	*/
	public static List<CPTemplateLayoutEntry> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cp template layout entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @return the range of matching cp template layout entries
	*/
	public static List<CPTemplateLayoutEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp template layout entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp template layout entries
	*/
	public static List<CPTemplateLayoutEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp template layout entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp template layout entries
	*/
	public static List<CPTemplateLayoutEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp template layout entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp template layout entry
	* @throws NoSuchCPTemplateLayoutEntryException if a matching cp template layout entry could not be found
	*/
	public static CPTemplateLayoutEntry findByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPTemplateLayoutEntryException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp template layout entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	*/
	public static CPTemplateLayoutEntry fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp template layout entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp template layout entry
	* @throws NoSuchCPTemplateLayoutEntryException if a matching cp template layout entry could not be found
	*/
	public static CPTemplateLayoutEntry findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPTemplateLayoutEntryException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp template layout entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	*/
	public static CPTemplateLayoutEntry fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cp template layout entries before and after the current cp template layout entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPFriendlyURLEntryId the primary key of the current cp template layout entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp template layout entry
	* @throws NoSuchCPTemplateLayoutEntryException if a cp template layout entry with the primary key could not be found
	*/
	public static CPTemplateLayoutEntry[] findByUuid_C_PrevAndNext(
		long CPFriendlyURLEntryId, java.lang.String uuid, long companyId,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPTemplateLayoutEntryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPFriendlyURLEntryId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the cp template layout entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp template layout entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp template layout entries
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns the cp template layout entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchCPTemplateLayoutEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching cp template layout entry
	* @throws NoSuchCPTemplateLayoutEntryException if a matching cp template layout entry could not be found
	*/
	public static CPTemplateLayoutEntry findByG_C_C(long groupId,
		long classNameId, long classPK)
		throws com.liferay.commerce.product.exception.NoSuchCPTemplateLayoutEntryException {
		return getPersistence().findByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the cp template layout entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	*/
	public static CPTemplateLayoutEntry fetchByG_C_C(long groupId,
		long classNameId, long classPK) {
		return getPersistence().fetchByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the cp template layout entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	*/
	public static CPTemplateLayoutEntry fetchByG_C_C(long groupId,
		long classNameId, long classPK, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_C_C(groupId, classNameId, classPK,
			retrieveFromCache);
	}

	/**
	* Removes the cp template layout entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the cp template layout entry that was removed
	*/
	public static CPTemplateLayoutEntry removeByG_C_C(long groupId,
		long classNameId, long classPK)
		throws com.liferay.commerce.product.exception.NoSuchCPTemplateLayoutEntryException {
		return getPersistence().removeByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the number of cp template layout entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching cp template layout entries
	*/
	public static int countByG_C_C(long groupId, long classNameId, long classPK) {
		return getPersistence().countByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Caches the cp template layout entry in the entity cache if it is enabled.
	*
	* @param cpTemplateLayoutEntry the cp template layout entry
	*/
	public static void cacheResult(CPTemplateLayoutEntry cpTemplateLayoutEntry) {
		getPersistence().cacheResult(cpTemplateLayoutEntry);
	}

	/**
	* Caches the cp template layout entries in the entity cache if it is enabled.
	*
	* @param cpTemplateLayoutEntries the cp template layout entries
	*/
	public static void cacheResult(
		List<CPTemplateLayoutEntry> cpTemplateLayoutEntries) {
		getPersistence().cacheResult(cpTemplateLayoutEntries);
	}

	/**
	* Creates a new cp template layout entry with the primary key. Does not add the cp template layout entry to the database.
	*
	* @param CPFriendlyURLEntryId the primary key for the new cp template layout entry
	* @return the new cp template layout entry
	*/
	public static CPTemplateLayoutEntry create(long CPFriendlyURLEntryId) {
		return getPersistence().create(CPFriendlyURLEntryId);
	}

	/**
	* Removes the cp template layout entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPFriendlyURLEntryId the primary key of the cp template layout entry
	* @return the cp template layout entry that was removed
	* @throws NoSuchCPTemplateLayoutEntryException if a cp template layout entry with the primary key could not be found
	*/
	public static CPTemplateLayoutEntry remove(long CPFriendlyURLEntryId)
		throws com.liferay.commerce.product.exception.NoSuchCPTemplateLayoutEntryException {
		return getPersistence().remove(CPFriendlyURLEntryId);
	}

	public static CPTemplateLayoutEntry updateImpl(
		CPTemplateLayoutEntry cpTemplateLayoutEntry) {
		return getPersistence().updateImpl(cpTemplateLayoutEntry);
	}

	/**
	* Returns the cp template layout entry with the primary key or throws a {@link NoSuchCPTemplateLayoutEntryException} if it could not be found.
	*
	* @param CPFriendlyURLEntryId the primary key of the cp template layout entry
	* @return the cp template layout entry
	* @throws NoSuchCPTemplateLayoutEntryException if a cp template layout entry with the primary key could not be found
	*/
	public static CPTemplateLayoutEntry findByPrimaryKey(
		long CPFriendlyURLEntryId)
		throws com.liferay.commerce.product.exception.NoSuchCPTemplateLayoutEntryException {
		return getPersistence().findByPrimaryKey(CPFriendlyURLEntryId);
	}

	/**
	* Returns the cp template layout entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPFriendlyURLEntryId the primary key of the cp template layout entry
	* @return the cp template layout entry, or <code>null</code> if a cp template layout entry with the primary key could not be found
	*/
	public static CPTemplateLayoutEntry fetchByPrimaryKey(
		long CPFriendlyURLEntryId) {
		return getPersistence().fetchByPrimaryKey(CPFriendlyURLEntryId);
	}

	public static java.util.Map<java.io.Serializable, CPTemplateLayoutEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp template layout entries.
	*
	* @return the cp template layout entries
	*/
	public static List<CPTemplateLayoutEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp template layout entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @return the range of cp template layout entries
	*/
	public static List<CPTemplateLayoutEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp template layout entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp template layout entries
	*/
	public static List<CPTemplateLayoutEntry> findAll(int start, int end,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp template layout entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp template layout entries
	* @param end the upper bound of the range of cp template layout entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp template layout entries
	*/
	public static List<CPTemplateLayoutEntry> findAll(int start, int end,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp template layout entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp template layout entries.
	*
	* @return the number of cp template layout entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPTemplateLayoutEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPTemplateLayoutEntryPersistence, CPTemplateLayoutEntryPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CPTemplateLayoutEntryPersistence.class);
}