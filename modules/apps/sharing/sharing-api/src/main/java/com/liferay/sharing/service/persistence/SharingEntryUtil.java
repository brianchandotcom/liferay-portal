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

package com.liferay.sharing.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.sharing.model.SharingEntry;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the sharing entry service. This utility wraps {@link com.liferay.sharing.service.persistence.impl.SharingEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SharingEntryPersistence
 * @see com.liferay.sharing.service.persistence.impl.SharingEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class SharingEntryUtil {
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
	public static void clearCache(SharingEntry sharingEntry) {
		getPersistence().clearCache(sharingEntry);
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
	public static List<SharingEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SharingEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SharingEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SharingEntry update(SharingEntry sharingEntry) {
		return getPersistence().update(sharingEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SharingEntry update(SharingEntry sharingEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(sharingEntry, serviceContext);
	}

	/**
	* Returns all the sharing entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching sharing entries
	*/
	public static List<SharingEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the sharing entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @return the range of matching sharing entries
	*/
	public static List<SharingEntry> findByUuid(String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the sharing entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sharing entries
	*/
	public static List<SharingEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the sharing entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching sharing entries
	*/
	public static List<SharingEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first sharing entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public static SharingEntry findByUuid_First(String uuid,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first sharing entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByUuid_First(String uuid,
		OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last sharing entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public static SharingEntry findByUuid_Last(String uuid,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last sharing entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByUuid_Last(String uuid,
		OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the sharing entries before and after the current sharing entry in the ordered set where uuid = &#63;.
	*
	* @param sharingEntryId the primary key of the current sharing entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sharing entry
	* @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	*/
	public static SharingEntry[] findByUuid_PrevAndNext(long sharingEntryId,
		String uuid, OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(sharingEntryId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the sharing entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of sharing entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching sharing entries
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the sharing entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public static SharingEntry findByUUID_G(String uuid, long groupId)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the sharing entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the sharing entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the sharing entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the sharing entry that was removed
	*/
	public static SharingEntry removeByUUID_G(String uuid, long groupId)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of sharing entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching sharing entries
	*/
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the sharing entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching sharing entries
	*/
	public static List<SharingEntry> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the sharing entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @return the range of matching sharing entries
	*/
	public static List<SharingEntry> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the sharing entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sharing entries
	*/
	public static List<SharingEntry> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the sharing entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching sharing entries
	*/
	public static List<SharingEntry> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public static SharingEntry findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public static SharingEntry findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the sharing entries before and after the current sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param sharingEntryId the primary key of the current sharing entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sharing entry
	* @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	*/
	public static SharingEntry[] findByUuid_C_PrevAndNext(long sharingEntryId,
		String uuid, long companyId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(sharingEntryId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the sharing entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of sharing entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching sharing entries
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the sharing entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching sharing entries
	*/
	public static List<SharingEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the sharing entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @return the range of matching sharing entries
	*/
	public static List<SharingEntry> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the sharing entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sharing entries
	*/
	public static List<SharingEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the sharing entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching sharing entries
	*/
	public static List<SharingEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first sharing entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public static SharingEntry findByGroupId_First(long groupId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first sharing entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByGroupId_First(long groupId,
		OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last sharing entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public static SharingEntry findByGroupId_Last(long groupId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last sharing entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the sharing entries before and after the current sharing entry in the ordered set where groupId = &#63;.
	*
	* @param sharingEntryId the primary key of the current sharing entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sharing entry
	* @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	*/
	public static SharingEntry[] findByGroupId_PrevAndNext(
		long sharingEntryId, long groupId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(sharingEntryId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the sharing entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of sharing entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching sharing entries
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the sharing entries where fromUserId = &#63;.
	*
	* @param fromUserId the from user ID
	* @return the matching sharing entries
	*/
	public static List<SharingEntry> findByFromUserId(long fromUserId) {
		return getPersistence().findByFromUserId(fromUserId);
	}

	/**
	* Returns a range of all the sharing entries where fromUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fromUserId the from user ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @return the range of matching sharing entries
	*/
	public static List<SharingEntry> findByFromUserId(long fromUserId,
		int start, int end) {
		return getPersistence().findByFromUserId(fromUserId, start, end);
	}

	/**
	* Returns an ordered range of all the sharing entries where fromUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fromUserId the from user ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sharing entries
	*/
	public static List<SharingEntry> findByFromUserId(long fromUserId,
		int start, int end, OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence()
				   .findByFromUserId(fromUserId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the sharing entries where fromUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fromUserId the from user ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching sharing entries
	*/
	public static List<SharingEntry> findByFromUserId(long fromUserId,
		int start, int end, OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByFromUserId(fromUserId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first sharing entry in the ordered set where fromUserId = &#63;.
	*
	* @param fromUserId the from user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public static SharingEntry findByFromUserId_First(long fromUserId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .findByFromUserId_First(fromUserId, orderByComparator);
	}

	/**
	* Returns the first sharing entry in the ordered set where fromUserId = &#63;.
	*
	* @param fromUserId the from user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByFromUserId_First(long fromUserId,
		OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence()
				   .fetchByFromUserId_First(fromUserId, orderByComparator);
	}

	/**
	* Returns the last sharing entry in the ordered set where fromUserId = &#63;.
	*
	* @param fromUserId the from user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public static SharingEntry findByFromUserId_Last(long fromUserId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .findByFromUserId_Last(fromUserId, orderByComparator);
	}

	/**
	* Returns the last sharing entry in the ordered set where fromUserId = &#63;.
	*
	* @param fromUserId the from user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByFromUserId_Last(long fromUserId,
		OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence()
				   .fetchByFromUserId_Last(fromUserId, orderByComparator);
	}

	/**
	* Returns the sharing entries before and after the current sharing entry in the ordered set where fromUserId = &#63;.
	*
	* @param sharingEntryId the primary key of the current sharing entry
	* @param fromUserId the from user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sharing entry
	* @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	*/
	public static SharingEntry[] findByFromUserId_PrevAndNext(
		long sharingEntryId, long fromUserId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .findByFromUserId_PrevAndNext(sharingEntryId, fromUserId,
			orderByComparator);
	}

	/**
	* Removes all the sharing entries where fromUserId = &#63; from the database.
	*
	* @param fromUserId the from user ID
	*/
	public static void removeByFromUserId(long fromUserId) {
		getPersistence().removeByFromUserId(fromUserId);
	}

	/**
	* Returns the number of sharing entries where fromUserId = &#63;.
	*
	* @param fromUserId the from user ID
	* @return the number of matching sharing entries
	*/
	public static int countByFromUserId(long fromUserId) {
		return getPersistence().countByFromUserId(fromUserId);
	}

	/**
	* Returns all the sharing entries where toUserId = &#63;.
	*
	* @param toUserId the to user ID
	* @return the matching sharing entries
	*/
	public static List<SharingEntry> findByToUserId(long toUserId) {
		return getPersistence().findByToUserId(toUserId);
	}

	/**
	* Returns a range of all the sharing entries where toUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toUserId the to user ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @return the range of matching sharing entries
	*/
	public static List<SharingEntry> findByToUserId(long toUserId, int start,
		int end) {
		return getPersistence().findByToUserId(toUserId, start, end);
	}

	/**
	* Returns an ordered range of all the sharing entries where toUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toUserId the to user ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sharing entries
	*/
	public static List<SharingEntry> findByToUserId(long toUserId, int start,
		int end, OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence()
				   .findByToUserId(toUserId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the sharing entries where toUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toUserId the to user ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching sharing entries
	*/
	public static List<SharingEntry> findByToUserId(long toUserId, int start,
		int end, OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByToUserId(toUserId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first sharing entry in the ordered set where toUserId = &#63;.
	*
	* @param toUserId the to user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public static SharingEntry findByToUserId_First(long toUserId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence().findByToUserId_First(toUserId, orderByComparator);
	}

	/**
	* Returns the first sharing entry in the ordered set where toUserId = &#63;.
	*
	* @param toUserId the to user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByToUserId_First(long toUserId,
		OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence()
				   .fetchByToUserId_First(toUserId, orderByComparator);
	}

	/**
	* Returns the last sharing entry in the ordered set where toUserId = &#63;.
	*
	* @param toUserId the to user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public static SharingEntry findByToUserId_Last(long toUserId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence().findByToUserId_Last(toUserId, orderByComparator);
	}

	/**
	* Returns the last sharing entry in the ordered set where toUserId = &#63;.
	*
	* @param toUserId the to user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByToUserId_Last(long toUserId,
		OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence().fetchByToUserId_Last(toUserId, orderByComparator);
	}

	/**
	* Returns the sharing entries before and after the current sharing entry in the ordered set where toUserId = &#63;.
	*
	* @param sharingEntryId the primary key of the current sharing entry
	* @param toUserId the to user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sharing entry
	* @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	*/
	public static SharingEntry[] findByToUserId_PrevAndNext(
		long sharingEntryId, long toUserId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .findByToUserId_PrevAndNext(sharingEntryId, toUserId,
			orderByComparator);
	}

	/**
	* Removes all the sharing entries where toUserId = &#63; from the database.
	*
	* @param toUserId the to user ID
	*/
	public static void removeByToUserId(long toUserId) {
		getPersistence().removeByToUserId(toUserId);
	}

	/**
	* Returns the number of sharing entries where toUserId = &#63;.
	*
	* @param toUserId the to user ID
	* @return the number of matching sharing entries
	*/
	public static int countByToUserId(long toUserId) {
		return getPersistence().countByToUserId(toUserId);
	}

	/**
	* Returns all the sharing entries where toUserId = &#63; and classNameId = &#63;.
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @return the matching sharing entries
	*/
	public static List<SharingEntry> findByTU_C(long toUserId, long classNameId) {
		return getPersistence().findByTU_C(toUserId, classNameId);
	}

	/**
	* Returns a range of all the sharing entries where toUserId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @return the range of matching sharing entries
	*/
	public static List<SharingEntry> findByTU_C(long toUserId,
		long classNameId, int start, int end) {
		return getPersistence().findByTU_C(toUserId, classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the sharing entries where toUserId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sharing entries
	*/
	public static List<SharingEntry> findByTU_C(long toUserId,
		long classNameId, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence()
				   .findByTU_C(toUserId, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the sharing entries where toUserId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching sharing entries
	*/
	public static List<SharingEntry> findByTU_C(long toUserId,
		long classNameId, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByTU_C(toUserId, classNameId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63;.
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public static SharingEntry findByTU_C_First(long toUserId,
		long classNameId, OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .findByTU_C_First(toUserId, classNameId, orderByComparator);
	}

	/**
	* Returns the first sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63;.
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByTU_C_First(long toUserId,
		long classNameId, OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence()
				   .fetchByTU_C_First(toUserId, classNameId, orderByComparator);
	}

	/**
	* Returns the last sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63;.
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public static SharingEntry findByTU_C_Last(long toUserId, long classNameId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .findByTU_C_Last(toUserId, classNameId, orderByComparator);
	}

	/**
	* Returns the last sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63;.
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByTU_C_Last(long toUserId,
		long classNameId, OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence()
				   .fetchByTU_C_Last(toUserId, classNameId, orderByComparator);
	}

	/**
	* Returns the sharing entries before and after the current sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63;.
	*
	* @param sharingEntryId the primary key of the current sharing entry
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sharing entry
	* @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	*/
	public static SharingEntry[] findByTU_C_PrevAndNext(long sharingEntryId,
		long toUserId, long classNameId,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .findByTU_C_PrevAndNext(sharingEntryId, toUserId,
			classNameId, orderByComparator);
	}

	/**
	* Removes all the sharing entries where toUserId = &#63; and classNameId = &#63; from the database.
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	*/
	public static void removeByTU_C(long toUserId, long classNameId) {
		getPersistence().removeByTU_C(toUserId, classNameId);
	}

	/**
	* Returns the number of sharing entries where toUserId = &#63; and classNameId = &#63;.
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @return the number of matching sharing entries
	*/
	public static int countByTU_C(long toUserId, long classNameId) {
		return getPersistence().countByTU_C(toUserId, classNameId);
	}

	/**
	* Returns all the sharing entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching sharing entries
	*/
	public static List<SharingEntry> findByC_C(long classNameId, long classPK) {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns a range of all the sharing entries where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @return the range of matching sharing entries
	*/
	public static List<SharingEntry> findByC_C(long classNameId, long classPK,
		int start, int end) {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the sharing entries where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sharing entries
	*/
	public static List<SharingEntry> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the sharing entries where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching sharing entries
	*/
	public static List<SharingEntry> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public static SharingEntry findByC_C_First(long classNameId, long classPK,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .findByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByC_C_First(long classNameId, long classPK,
		OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public static SharingEntry findByC_C_Last(long classNameId, long classPK,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .findByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByC_C_Last(long classNameId, long classPK,
		OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the sharing entries before and after the current sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param sharingEntryId the primary key of the current sharing entry
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sharing entry
	* @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	*/
	public static SharingEntry[] findByC_C_PrevAndNext(long sharingEntryId,
		long classNameId, long classPK,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .findByC_C_PrevAndNext(sharingEntryId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Removes all the sharing entries where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of sharing entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching sharing entries
	*/
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Returns all the sharing entries where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching sharing entries
	*/
	public static List<SharingEntry> findByTU_C_C(long toUserId,
		long classNameId, long classPK) {
		return getPersistence().findByTU_C_C(toUserId, classNameId, classPK);
	}

	/**
	* Returns a range of all the sharing entries where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @return the range of matching sharing entries
	*/
	public static List<SharingEntry> findByTU_C_C(long toUserId,
		long classNameId, long classPK, int start, int end) {
		return getPersistence()
				   .findByTU_C_C(toUserId, classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the sharing entries where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sharing entries
	*/
	public static List<SharingEntry> findByTU_C_C(long toUserId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence()
				   .findByTU_C_C(toUserId, classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the sharing entries where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching sharing entries
	*/
	public static List<SharingEntry> findByTU_C_C(long toUserId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByTU_C_C(toUserId, classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public static SharingEntry findByTU_C_C_First(long toUserId,
		long classNameId, long classPK,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .findByTU_C_C_First(toUserId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the first sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByTU_C_C_First(long toUserId,
		long classNameId, long classPK,
		OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence()
				   .fetchByTU_C_C_First(toUserId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public static SharingEntry findByTU_C_C_Last(long toUserId,
		long classNameId, long classPK,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .findByTU_C_C_Last(toUserId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByTU_C_C_Last(long toUserId,
		long classNameId, long classPK,
		OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence()
				   .fetchByTU_C_C_Last(toUserId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the sharing entries before and after the current sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param sharingEntryId the primary key of the current sharing entry
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sharing entry
	* @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	*/
	public static SharingEntry[] findByTU_C_C_PrevAndNext(long sharingEntryId,
		long toUserId, long classNameId, long classPK,
		OrderByComparator<SharingEntry> orderByComparator)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .findByTU_C_C_PrevAndNext(sharingEntryId, toUserId,
			classNameId, classPK, orderByComparator);
	}

	/**
	* Removes all the sharing entries where toUserId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public static void removeByTU_C_C(long toUserId, long classNameId,
		long classPK) {
		getPersistence().removeByTU_C_C(toUserId, classNameId, classPK);
	}

	/**
	* Returns the number of sharing entries where toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching sharing entries
	*/
	public static int countByTU_C_C(long toUserId, long classNameId,
		long classPK) {
		return getPersistence().countByTU_C_C(toUserId, classNameId, classPK);
	}

	/**
	* Returns the sharing entry where fromUserId = &#63; and toUserId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param fromUserId the from user ID
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public static SharingEntry findByFU_TU_C_C(long fromUserId, long toUserId,
		long classNameId, long classPK)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .findByFU_TU_C_C(fromUserId, toUserId, classNameId, classPK);
	}

	/**
	* Returns the sharing entry where fromUserId = &#63; and toUserId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param fromUserId the from user ID
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByFU_TU_C_C(long fromUserId, long toUserId,
		long classNameId, long classPK) {
		return getPersistence()
				   .fetchByFU_TU_C_C(fromUserId, toUserId, classNameId, classPK);
	}

	/**
	* Returns the sharing entry where fromUserId = &#63; and toUserId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param fromUserId the from user ID
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static SharingEntry fetchByFU_TU_C_C(long fromUserId, long toUserId,
		long classNameId, long classPK, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByFU_TU_C_C(fromUserId, toUserId, classNameId,
			classPK, retrieveFromCache);
	}

	/**
	* Removes the sharing entry where fromUserId = &#63; and toUserId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param fromUserId the from user ID
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the sharing entry that was removed
	*/
	public static SharingEntry removeByFU_TU_C_C(long fromUserId,
		long toUserId, long classNameId, long classPK)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence()
				   .removeByFU_TU_C_C(fromUserId, toUserId, classNameId, classPK);
	}

	/**
	* Returns the number of sharing entries where fromUserId = &#63; and toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param fromUserId the from user ID
	* @param toUserId the to user ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching sharing entries
	*/
	public static int countByFU_TU_C_C(long fromUserId, long toUserId,
		long classNameId, long classPK) {
		return getPersistence()
				   .countByFU_TU_C_C(fromUserId, toUserId, classNameId, classPK);
	}

	/**
	* Caches the sharing entry in the entity cache if it is enabled.
	*
	* @param sharingEntry the sharing entry
	*/
	public static void cacheResult(SharingEntry sharingEntry) {
		getPersistence().cacheResult(sharingEntry);
	}

	/**
	* Caches the sharing entries in the entity cache if it is enabled.
	*
	* @param sharingEntries the sharing entries
	*/
	public static void cacheResult(List<SharingEntry> sharingEntries) {
		getPersistence().cacheResult(sharingEntries);
	}

	/**
	* Creates a new sharing entry with the primary key. Does not add the sharing entry to the database.
	*
	* @param sharingEntryId the primary key for the new sharing entry
	* @return the new sharing entry
	*/
	public static SharingEntry create(long sharingEntryId) {
		return getPersistence().create(sharingEntryId);
	}

	/**
	* Removes the sharing entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sharingEntryId the primary key of the sharing entry
	* @return the sharing entry that was removed
	* @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	*/
	public static SharingEntry remove(long sharingEntryId)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence().remove(sharingEntryId);
	}

	public static SharingEntry updateImpl(SharingEntry sharingEntry) {
		return getPersistence().updateImpl(sharingEntry);
	}

	/**
	* Returns the sharing entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param sharingEntryId the primary key of the sharing entry
	* @return the sharing entry
	* @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	*/
	public static SharingEntry findByPrimaryKey(long sharingEntryId)
		throws com.liferay.sharing.exception.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(sharingEntryId);
	}

	/**
	* Returns the sharing entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param sharingEntryId the primary key of the sharing entry
	* @return the sharing entry, or <code>null</code> if a sharing entry with the primary key could not be found
	*/
	public static SharingEntry fetchByPrimaryKey(long sharingEntryId) {
		return getPersistence().fetchByPrimaryKey(sharingEntryId);
	}

	public static java.util.Map<java.io.Serializable, SharingEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the sharing entries.
	*
	* @return the sharing entries
	*/
	public static List<SharingEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the sharing entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @return the range of sharing entries
	*/
	public static List<SharingEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the sharing entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of sharing entries
	*/
	public static List<SharingEntry> findAll(int start, int end,
		OrderByComparator<SharingEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the sharing entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of sharing entries
	*/
	public static List<SharingEntry> findAll(int start, int end,
		OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the sharing entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of sharing entries.
	*
	* @return the number of sharing entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static SharingEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SharingEntryPersistence, SharingEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SharingEntryPersistence.class);

		ServiceTracker<SharingEntryPersistence, SharingEntryPersistence> serviceTracker =
			new ServiceTracker<SharingEntryPersistence, SharingEntryPersistence>(bundle.getBundleContext(),
				SharingEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}