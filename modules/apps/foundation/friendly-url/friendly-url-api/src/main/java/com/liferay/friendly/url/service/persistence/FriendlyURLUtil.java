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

package com.liferay.friendly.url.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.friendly.url.model.FriendlyURL;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the friendly url service. This utility wraps {@link com.liferay.friendly.url.service.persistence.impl.FriendlyURLPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLPersistence
 * @see com.liferay.friendly.url.service.persistence.impl.FriendlyURLPersistenceImpl
 * @generated
 */
@ProviderType
public class FriendlyURLUtil {
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
	public static void clearCache(FriendlyURL friendlyURL) {
		getPersistence().clearCache(friendlyURL);
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
	public static List<FriendlyURL> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<FriendlyURL> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<FriendlyURL> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<FriendlyURL> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static FriendlyURL update(FriendlyURL friendlyURL) {
		return getPersistence().update(friendlyURL);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static FriendlyURL update(FriendlyURL friendlyURL,
		ServiceContext serviceContext) {
		return getPersistence().update(friendlyURL, serviceContext);
	}

	/**
	* Returns all the friendly urls where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching friendly urls
	*/
	public static List<FriendlyURL> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the friendly urls where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @return the range of matching friendly urls
	*/
	public static List<FriendlyURL> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the friendly urls where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching friendly urls
	*/
	public static List<FriendlyURL> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<FriendlyURL> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the friendly urls where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching friendly urls
	*/
	public static List<FriendlyURL> findByUuid(java.lang.String uuid,
		int start, int end, OrderByComparator<FriendlyURL> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public static FriendlyURL findByUuid_First(java.lang.String uuid,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public static FriendlyURL fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<FriendlyURL> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public static FriendlyURL findByUuid_Last(java.lang.String uuid,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public static FriendlyURL fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<FriendlyURL> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the friendly urls before and after the current friendly url in the ordered set where uuid = &#63;.
	*
	* @param friendlyURLId the primary key of the current friendly url
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next friendly url
	* @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	*/
	public static FriendlyURL[] findByUuid_PrevAndNext(long friendlyURLId,
		java.lang.String uuid, OrderByComparator<FriendlyURL> orderByComparator)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence()
				   .findByUuid_PrevAndNext(friendlyURLId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the friendly urls where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of friendly urls where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching friendly urls
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the friendly url where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public static FriendlyURL findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public static FriendlyURL fetchByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public static FriendlyURL fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the friendly url where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the friendly url that was removed
	*/
	public static FriendlyURL removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of friendly urls where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching friendly urls
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching friendly urls
	*/
	public static List<FriendlyURL> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @return the range of matching friendly urls
	*/
	public static List<FriendlyURL> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching friendly urls
	*/
	public static List<FriendlyURL> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<FriendlyURL> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching friendly urls
	*/
	public static List<FriendlyURL> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<FriendlyURL> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public static FriendlyURL findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<FriendlyURL> orderByComparator)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public static FriendlyURL fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<FriendlyURL> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public static FriendlyURL findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<FriendlyURL> orderByComparator)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public static FriendlyURL fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<FriendlyURL> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the friendly urls before and after the current friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param friendlyURLId the primary key of the current friendly url
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next friendly url
	* @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	*/
	public static FriendlyURL[] findByUuid_C_PrevAndNext(long friendlyURLId,
		java.lang.String uuid, long companyId,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(friendlyURLId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the friendly urls where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching friendly urls
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the friendly urls where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching friendly urls
	*/
	public static List<FriendlyURL> findByG_C(long groupId, long classNameId) {
		return getPersistence().findByG_C(groupId, classNameId);
	}

	/**
	* Returns a range of all the friendly urls where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @return the range of matching friendly urls
	*/
	public static List<FriendlyURL> findByG_C(long groupId, long classNameId,
		int start, int end) {
		return getPersistence().findByG_C(groupId, classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the friendly urls where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching friendly urls
	*/
	public static List<FriendlyURL> findByG_C(long groupId, long classNameId,
		int start, int end, OrderByComparator<FriendlyURL> orderByComparator) {
		return getPersistence()
				   .findByG_C(groupId, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the friendly urls where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching friendly urls
	*/
	public static List<FriendlyURL> findByG_C(long groupId, long classNameId,
		int start, int end, OrderByComparator<FriendlyURL> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C(groupId, classNameId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first friendly url in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public static FriendlyURL findByG_C_First(long groupId, long classNameId,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence()
				   .findByG_C_First(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the first friendly url in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public static FriendlyURL fetchByG_C_First(long groupId, long classNameId,
		OrderByComparator<FriendlyURL> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_First(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the last friendly url in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public static FriendlyURL findByG_C_Last(long groupId, long classNameId,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence()
				   .findByG_C_Last(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the last friendly url in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public static FriendlyURL fetchByG_C_Last(long groupId, long classNameId,
		OrderByComparator<FriendlyURL> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_Last(groupId, classNameId, orderByComparator);
	}

	/**
	* Returns the friendly urls before and after the current friendly url in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param friendlyURLId the primary key of the current friendly url
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next friendly url
	* @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	*/
	public static FriendlyURL[] findByG_C_PrevAndNext(long friendlyURLId,
		long groupId, long classNameId,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence()
				   .findByG_C_PrevAndNext(friendlyURLId, groupId, classNameId,
			orderByComparator);
	}

	/**
	* Removes all the friendly urls where groupId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	*/
	public static void removeByG_C(long groupId, long classNameId) {
		getPersistence().removeByG_C(groupId, classNameId);
	}

	/**
	* Returns the number of friendly urls where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching friendly urls
	*/
	public static int countByG_C(long groupId, long classNameId) {
		return getPersistence().countByG_C(groupId, classNameId);
	}

	/**
	* Returns all the friendly urls where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching friendly urls
	*/
	public static List<FriendlyURL> findByG_C_C_C(long groupId, long companyId,
		long classNameId, long classPK) {
		return getPersistence()
				   .findByG_C_C_C(groupId, companyId, classNameId, classPK);
	}

	/**
	* Returns a range of all the friendly urls where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @return the range of matching friendly urls
	*/
	public static List<FriendlyURL> findByG_C_C_C(long groupId, long companyId,
		long classNameId, long classPK, int start, int end) {
		return getPersistence()
				   .findByG_C_C_C(groupId, companyId, classNameId, classPK,
			start, end);
	}

	/**
	* Returns an ordered range of all the friendly urls where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching friendly urls
	*/
	public static List<FriendlyURL> findByG_C_C_C(long groupId, long companyId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<FriendlyURL> orderByComparator) {
		return getPersistence()
				   .findByG_C_C_C(groupId, companyId, classNameId, classPK,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the friendly urls where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching friendly urls
	*/
	public static List<FriendlyURL> findByG_C_C_C(long groupId, long companyId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator<FriendlyURL> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_C_C(groupId, companyId, classNameId, classPK,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first friendly url in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public static FriendlyURL findByG_C_C_C_First(long groupId, long companyId,
		long classNameId, long classPK,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence()
				   .findByG_C_C_C_First(groupId, companyId, classNameId,
			classPK, orderByComparator);
	}

	/**
	* Returns the first friendly url in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public static FriendlyURL fetchByG_C_C_C_First(long groupId,
		long companyId, long classNameId, long classPK,
		OrderByComparator<FriendlyURL> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_C_First(groupId, companyId, classNameId,
			classPK, orderByComparator);
	}

	/**
	* Returns the last friendly url in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public static FriendlyURL findByG_C_C_C_Last(long groupId, long companyId,
		long classNameId, long classPK,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence()
				   .findByG_C_C_C_Last(groupId, companyId, classNameId,
			classPK, orderByComparator);
	}

	/**
	* Returns the last friendly url in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public static FriendlyURL fetchByG_C_C_C_Last(long groupId, long companyId,
		long classNameId, long classPK,
		OrderByComparator<FriendlyURL> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_C_C_Last(groupId, companyId, classNameId,
			classPK, orderByComparator);
	}

	/**
	* Returns the friendly urls before and after the current friendly url in the ordered set where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param friendlyURLId the primary key of the current friendly url
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next friendly url
	* @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	*/
	public static FriendlyURL[] findByG_C_C_C_PrevAndNext(long friendlyURLId,
		long groupId, long companyId, long classNameId, long classPK,
		OrderByComparator<FriendlyURL> orderByComparator)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence()
				   .findByG_C_C_C_PrevAndNext(friendlyURLId, groupId,
			companyId, classNameId, classPK, orderByComparator);
	}

	/**
	* Removes all the friendly urls where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public static void removeByG_C_C_C(long groupId, long companyId,
		long classNameId, long classPK) {
		getPersistence()
			.removeByG_C_C_C(groupId, companyId, classNameId, classPK);
	}

	/**
	* Returns the number of friendly urls where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching friendly urls
	*/
	public static int countByG_C_C_C(long groupId, long companyId,
		long classNameId, long classPK) {
		return getPersistence()
				   .countByG_C_C_C(groupId, companyId, classNameId, classPK);
	}

	/**
	* Returns the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and urlTitle = &#63; or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @return the matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public static FriendlyURL findByG_C_C_U(long groupId, long companyId,
		long classNameId, java.lang.String urlTitle)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence()
				   .findByG_C_C_U(groupId, companyId, classNameId, urlTitle);
	}

	/**
	* Returns the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public static FriendlyURL fetchByG_C_C_U(long groupId, long companyId,
		long classNameId, java.lang.String urlTitle) {
		return getPersistence()
				   .fetchByG_C_C_U(groupId, companyId, classNameId, urlTitle);
	}

	/**
	* Returns the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public static FriendlyURL fetchByG_C_C_U(long groupId, long companyId,
		long classNameId, java.lang.String urlTitle, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_C_C_U(groupId, companyId, classNameId, urlTitle,
			retrieveFromCache);
	}

	/**
	* Removes the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and urlTitle = &#63; from the database.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @return the friendly url that was removed
	*/
	public static FriendlyURL removeByG_C_C_U(long groupId, long companyId,
		long classNameId, java.lang.String urlTitle)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence()
				   .removeByG_C_C_U(groupId, companyId, classNameId, urlTitle);
	}

	/**
	* Returns the number of friendly urls where groupId = &#63; and companyId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @return the number of matching friendly urls
	*/
	public static int countByG_C_C_U(long groupId, long companyId,
		long classNameId, java.lang.String urlTitle) {
		return getPersistence()
				   .countByG_C_C_U(groupId, companyId, classNameId, urlTitle);
	}

	/**
	* Returns the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param urlTitle the url title
	* @return the matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public static FriendlyURL findByG_C_C_C_U(long groupId, long companyId,
		long classNameId, long classPK, java.lang.String urlTitle)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence()
				   .findByG_C_C_C_U(groupId, companyId, classNameId, classPK,
			urlTitle);
	}

	/**
	* Returns the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param urlTitle the url title
	* @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public static FriendlyURL fetchByG_C_C_C_U(long groupId, long companyId,
		long classNameId, long classPK, java.lang.String urlTitle) {
		return getPersistence()
				   .fetchByG_C_C_C_U(groupId, companyId, classNameId, classPK,
			urlTitle);
	}

	/**
	* Returns the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param urlTitle the url title
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public static FriendlyURL fetchByG_C_C_C_U(long groupId, long companyId,
		long classNameId, long classPK, java.lang.String urlTitle,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_C_C_C_U(groupId, companyId, classNameId, classPK,
			urlTitle, retrieveFromCache);
	}

	/**
	* Removes the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; from the database.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param urlTitle the url title
	* @return the friendly url that was removed
	*/
	public static FriendlyURL removeByG_C_C_C_U(long groupId, long companyId,
		long classNameId, long classPK, java.lang.String urlTitle)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence()
				   .removeByG_C_C_C_U(groupId, companyId, classNameId, classPK,
			urlTitle);
	}

	/**
	* Returns the number of friendly urls where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param urlTitle the url title
	* @return the number of matching friendly urls
	*/
	public static int countByG_C_C_C_U(long groupId, long companyId,
		long classNameId, long classPK, java.lang.String urlTitle) {
		return getPersistence()
				   .countByG_C_C_C_U(groupId, companyId, classNameId, classPK,
			urlTitle);
	}

	/**
	* Returns the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @return the matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public static FriendlyURL findByG_C_C_C_M(long groupId, long companyId,
		long classNameId, long classPK, boolean main)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence()
				   .findByG_C_C_C_M(groupId, companyId, classNameId, classPK,
			main);
	}

	/**
	* Returns the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public static FriendlyURL fetchByG_C_C_C_M(long groupId, long companyId,
		long classNameId, long classPK, boolean main) {
		return getPersistence()
				   .fetchByG_C_C_C_M(groupId, companyId, classNameId, classPK,
			main);
	}

	/**
	* Returns the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public static FriendlyURL fetchByG_C_C_C_M(long groupId, long companyId,
		long classNameId, long classPK, boolean main, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_C_C_C_M(groupId, companyId, classNameId, classPK,
			main, retrieveFromCache);
	}

	/**
	* Removes the friendly url where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; from the database.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @return the friendly url that was removed
	*/
	public static FriendlyURL removeByG_C_C_C_M(long groupId, long companyId,
		long classNameId, long classPK, boolean main)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence()
				   .removeByG_C_C_C_M(groupId, companyId, classNameId, classPK,
			main);
	}

	/**
	* Returns the number of friendly urls where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @return the number of matching friendly urls
	*/
	public static int countByG_C_C_C_M(long groupId, long companyId,
		long classNameId, long classPK, boolean main) {
		return getPersistence()
				   .countByG_C_C_C_M(groupId, companyId, classNameId, classPK,
			main);
	}

	/**
	* Caches the friendly url in the entity cache if it is enabled.
	*
	* @param friendlyURL the friendly url
	*/
	public static void cacheResult(FriendlyURL friendlyURL) {
		getPersistence().cacheResult(friendlyURL);
	}

	/**
	* Caches the friendly urls in the entity cache if it is enabled.
	*
	* @param friendlyURLs the friendly urls
	*/
	public static void cacheResult(List<FriendlyURL> friendlyURLs) {
		getPersistence().cacheResult(friendlyURLs);
	}

	/**
	* Creates a new friendly url with the primary key. Does not add the friendly url to the database.
	*
	* @param friendlyURLId the primary key for the new friendly url
	* @return the new friendly url
	*/
	public static FriendlyURL create(long friendlyURLId) {
		return getPersistence().create(friendlyURLId);
	}

	/**
	* Removes the friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLId the primary key of the friendly url
	* @return the friendly url that was removed
	* @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	*/
	public static FriendlyURL remove(long friendlyURLId)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence().remove(friendlyURLId);
	}

	public static FriendlyURL updateImpl(FriendlyURL friendlyURL) {
		return getPersistence().updateImpl(friendlyURL);
	}

	/**
	* Returns the friendly url with the primary key or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	*
	* @param friendlyURLId the primary key of the friendly url
	* @return the friendly url
	* @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	*/
	public static FriendlyURL findByPrimaryKey(long friendlyURLId)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		return getPersistence().findByPrimaryKey(friendlyURLId);
	}

	/**
	* Returns the friendly url with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param friendlyURLId the primary key of the friendly url
	* @return the friendly url, or <code>null</code> if a friendly url with the primary key could not be found
	*/
	public static FriendlyURL fetchByPrimaryKey(long friendlyURLId) {
		return getPersistence().fetchByPrimaryKey(friendlyURLId);
	}

	public static java.util.Map<java.io.Serializable, FriendlyURL> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the friendly urls.
	*
	* @return the friendly urls
	*/
	public static List<FriendlyURL> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @return the range of friendly urls
	*/
	public static List<FriendlyURL> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of friendly urls
	*/
	public static List<FriendlyURL> findAll(int start, int end,
		OrderByComparator<FriendlyURL> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of friendly urls
	*/
	public static List<FriendlyURL> findAll(int start, int end,
		OrderByComparator<FriendlyURL> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the friendly urls from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of friendly urls.
	*
	* @return the number of friendly urls
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static FriendlyURLPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<FriendlyURLPersistence, FriendlyURLPersistence> _serviceTracker =
		ServiceTrackerFactory.open(FriendlyURLPersistence.class);
}