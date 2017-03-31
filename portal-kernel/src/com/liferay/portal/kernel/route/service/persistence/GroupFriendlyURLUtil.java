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

package com.liferay.portal.kernel.route.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.route.model.GroupFriendlyURL;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.util.List;

/**
 * The persistence utility for the group friendly url service. This utility wraps {@link com.liferay.portal.route.service.persistence.impl.GroupFriendlyURLPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GroupFriendlyURLPersistence
 * @see com.liferay.portal.route.service.persistence.impl.GroupFriendlyURLPersistenceImpl
 * @generated
 */
@ProviderType
public class GroupFriendlyURLUtil {
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
	public static void clearCache(GroupFriendlyURL groupFriendlyURL) {
		getPersistence().clearCache(groupFriendlyURL);
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
	public static List<GroupFriendlyURL> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<GroupFriendlyURL> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<GroupFriendlyURL> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<GroupFriendlyURL> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static GroupFriendlyURL update(GroupFriendlyURL groupFriendlyURL) {
		return getPersistence().update(groupFriendlyURL);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static GroupFriendlyURL update(GroupFriendlyURL groupFriendlyURL,
		ServiceContext serviceContext) {
		return getPersistence().update(groupFriendlyURL, serviceContext);
	}

	/**
	* Returns all the group friendly urls where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching group friendly urls
	*/
	public static List<GroupFriendlyURL> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the group friendly urls where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @return the range of matching group friendly urls
	*/
	public static List<GroupFriendlyURL> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the group friendly urls where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching group friendly urls
	*/
	public static List<GroupFriendlyURL> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<GroupFriendlyURL> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the group friendly urls where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching group friendly urls
	*/
	public static List<GroupFriendlyURL> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<GroupFriendlyURL> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first group friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL findByUuid_First(java.lang.String uuid,
		OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first group friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<GroupFriendlyURL> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last group friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL findByUuid_Last(java.lang.String uuid,
		OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last group friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<GroupFriendlyURL> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the group friendly urls before and after the current group friendly url in the ordered set where uuid = &#63;.
	*
	* @param groupFriendlyURLId the primary key of the current group friendly url
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group friendly url
	* @throws NoSuchGroupFriendlyURLException if a group friendly url with the primary key could not be found
	*/
	public static GroupFriendlyURL[] findByUuid_PrevAndNext(
		long groupFriendlyURLId, java.lang.String uuid,
		OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException {
		return getPersistence()
				   .findByUuid_PrevAndNext(groupFriendlyURLId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the group friendly urls where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of group friendly urls where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching group friendly urls
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the group friendly url where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchGroupFriendlyURLException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the group friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the group friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the group friendly url where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the group friendly url that was removed
	*/
	public static GroupFriendlyURL removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of group friendly urls where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching group friendly urls
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the group friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching group friendly urls
	*/
	public static List<GroupFriendlyURL> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the group friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @return the range of matching group friendly urls
	*/
	public static List<GroupFriendlyURL> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the group friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching group friendly urls
	*/
	public static List<GroupFriendlyURL> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<GroupFriendlyURL> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the group friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching group friendly urls
	*/
	public static List<GroupFriendlyURL> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<GroupFriendlyURL> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first group friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first group friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<GroupFriendlyURL> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last group friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last group friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<GroupFriendlyURL> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the group friendly urls before and after the current group friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param groupFriendlyURLId the primary key of the current group friendly url
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group friendly url
	* @throws NoSuchGroupFriendlyURLException if a group friendly url with the primary key could not be found
	*/
	public static GroupFriendlyURL[] findByUuid_C_PrevAndNext(
		long groupFriendlyURLId, java.lang.String uuid, long companyId,
		OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(groupFriendlyURLId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the group friendly urls where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of group friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching group friendly urls
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the group friendly urls where companyId = &#63; and groupId = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @return the matching group friendly urls
	*/
	public static List<GroupFriendlyURL> findByC_G(long companyId, long groupId) {
		return getPersistence().findByC_G(companyId, groupId);
	}

	/**
	* Returns a range of all the group friendly urls where companyId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @return the range of matching group friendly urls
	*/
	public static List<GroupFriendlyURL> findByC_G(long companyId,
		long groupId, int start, int end) {
		return getPersistence().findByC_G(companyId, groupId, start, end);
	}

	/**
	* Returns an ordered range of all the group friendly urls where companyId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching group friendly urls
	*/
	public static List<GroupFriendlyURL> findByC_G(long companyId,
		long groupId, int start, int end,
		OrderByComparator<GroupFriendlyURL> orderByComparator) {
		return getPersistence()
				   .findByC_G(companyId, groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the group friendly urls where companyId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching group friendly urls
	*/
	public static List<GroupFriendlyURL> findByC_G(long companyId,
		long groupId, int start, int end,
		OrderByComparator<GroupFriendlyURL> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_G(companyId, groupId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first group friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL findByC_G_First(long companyId,
		long groupId, OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException {
		return getPersistence()
				   .findByC_G_First(companyId, groupId, orderByComparator);
	}

	/**
	* Returns the first group friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL fetchByC_G_First(long companyId,
		long groupId, OrderByComparator<GroupFriendlyURL> orderByComparator) {
		return getPersistence()
				   .fetchByC_G_First(companyId, groupId, orderByComparator);
	}

	/**
	* Returns the last group friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL findByC_G_Last(long companyId, long groupId,
		OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException {
		return getPersistence()
				   .findByC_G_Last(companyId, groupId, orderByComparator);
	}

	/**
	* Returns the last group friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL fetchByC_G_Last(long companyId,
		long groupId, OrderByComparator<GroupFriendlyURL> orderByComparator) {
		return getPersistence()
				   .fetchByC_G_Last(companyId, groupId, orderByComparator);
	}

	/**
	* Returns the group friendly urls before and after the current group friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	*
	* @param groupFriendlyURLId the primary key of the current group friendly url
	* @param companyId the company ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group friendly url
	* @throws NoSuchGroupFriendlyURLException if a group friendly url with the primary key could not be found
	*/
	public static GroupFriendlyURL[] findByC_G_PrevAndNext(
		long groupFriendlyURLId, long companyId, long groupId,
		OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException {
		return getPersistence()
				   .findByC_G_PrevAndNext(groupFriendlyURLId, companyId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the group friendly urls where companyId = &#63; and groupId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	*/
	public static void removeByC_G(long companyId, long groupId) {
		getPersistence().removeByC_G(companyId, groupId);
	}

	/**
	* Returns the number of group friendly urls where companyId = &#63; and groupId = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @return the number of matching group friendly urls
	*/
	public static int countByC_G(long companyId, long groupId) {
		return getPersistence().countByC_G(companyId, groupId);
	}

	/**
	* Returns the group friendly url where companyId = &#63; and friendlyURL = &#63; or throws a {@link NoSuchGroupFriendlyURLException} if it could not be found.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @return the matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL findByC_F(long companyId,
		java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException {
		return getPersistence().findByC_F(companyId, friendlyURL);
	}

	/**
	* Returns the group friendly url where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL fetchByC_F(long companyId,
		java.lang.String friendlyURL) {
		return getPersistence().fetchByC_F(companyId, friendlyURL);
	}

	/**
	* Returns the group friendly url where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL fetchByC_F(long companyId,
		java.lang.String friendlyURL, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_F(companyId, friendlyURL, retrieveFromCache);
	}

	/**
	* Removes the group friendly url where companyId = &#63; and friendlyURL = &#63; from the database.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @return the group friendly url that was removed
	*/
	public static GroupFriendlyURL removeByC_F(long companyId,
		java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException {
		return getPersistence().removeByC_F(companyId, friendlyURL);
	}

	/**
	* Returns the number of group friendly urls where companyId = &#63; and friendlyURL = &#63;.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @return the number of matching group friendly urls
	*/
	public static int countByC_F(long companyId, java.lang.String friendlyURL) {
		return getPersistence().countByC_F(companyId, friendlyURL);
	}

	/**
	* Returns the group friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; or throws a {@link NoSuchGroupFriendlyURLException} if it could not be found.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param languageId the language ID
	* @return the matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL findByC_G_L(long companyId, long groupId,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException {
		return getPersistence().findByC_G_L(companyId, groupId, languageId);
	}

	/**
	* Returns the group friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param languageId the language ID
	* @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL fetchByC_G_L(long companyId, long groupId,
		java.lang.String languageId) {
		return getPersistence().fetchByC_G_L(companyId, groupId, languageId);
	}

	/**
	* Returns the group friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL fetchByC_G_L(long companyId, long groupId,
		java.lang.String languageId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_G_L(companyId, groupId, languageId,
			retrieveFromCache);
	}

	/**
	* Removes the group friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param languageId the language ID
	* @return the group friendly url that was removed
	*/
	public static GroupFriendlyURL removeByC_G_L(long companyId, long groupId,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException {
		return getPersistence().removeByC_G_L(companyId, groupId, languageId);
	}

	/**
	* Returns the number of group friendly urls where companyId = &#63; and groupId = &#63; and languageId = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param languageId the language ID
	* @return the number of matching group friendly urls
	*/
	public static int countByC_G_L(long companyId, long groupId,
		java.lang.String languageId) {
		return getPersistence().countByC_G_L(companyId, groupId, languageId);
	}

	/**
	* Returns the group friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or throws a {@link NoSuchGroupFriendlyURLException} if it could not be found.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @param languageId the language ID
	* @return the matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL findByC_F_L(long companyId,
		java.lang.String friendlyURL, java.lang.String languageId)
		throws com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException {
		return getPersistence().findByC_F_L(companyId, friendlyURL, languageId);
	}

	/**
	* Returns the group friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @param languageId the language ID
	* @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL fetchByC_F_L(long companyId,
		java.lang.String friendlyURL, java.lang.String languageId) {
		return getPersistence().fetchByC_F_L(companyId, friendlyURL, languageId);
	}

	/**
	* Returns the group friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public static GroupFriendlyURL fetchByC_F_L(long companyId,
		java.lang.String friendlyURL, java.lang.String languageId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_F_L(companyId, friendlyURL, languageId,
			retrieveFromCache);
	}

	/**
	* Removes the group friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @param languageId the language ID
	* @return the group friendly url that was removed
	*/
	public static GroupFriendlyURL removeByC_F_L(long companyId,
		java.lang.String friendlyURL, java.lang.String languageId)
		throws com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException {
		return getPersistence().removeByC_F_L(companyId, friendlyURL, languageId);
	}

	/**
	* Returns the number of group friendly urls where companyId = &#63; and friendlyURL = &#63; and languageId = &#63;.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @param languageId the language ID
	* @return the number of matching group friendly urls
	*/
	public static int countByC_F_L(long companyId,
		java.lang.String friendlyURL, java.lang.String languageId) {
		return getPersistence().countByC_F_L(companyId, friendlyURL, languageId);
	}

	/**
	* Caches the group friendly url in the entity cache if it is enabled.
	*
	* @param groupFriendlyURL the group friendly url
	*/
	public static void cacheResult(GroupFriendlyURL groupFriendlyURL) {
		getPersistence().cacheResult(groupFriendlyURL);
	}

	/**
	* Caches the group friendly urls in the entity cache if it is enabled.
	*
	* @param groupFriendlyURLs the group friendly urls
	*/
	public static void cacheResult(List<GroupFriendlyURL> groupFriendlyURLs) {
		getPersistence().cacheResult(groupFriendlyURLs);
	}

	/**
	* Creates a new group friendly url with the primary key. Does not add the group friendly url to the database.
	*
	* @param groupFriendlyURLId the primary key for the new group friendly url
	* @return the new group friendly url
	*/
	public static GroupFriendlyURL create(long groupFriendlyURLId) {
		return getPersistence().create(groupFriendlyURLId);
	}

	/**
	* Removes the group friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param groupFriendlyURLId the primary key of the group friendly url
	* @return the group friendly url that was removed
	* @throws NoSuchGroupFriendlyURLException if a group friendly url with the primary key could not be found
	*/
	public static GroupFriendlyURL remove(long groupFriendlyURLId)
		throws com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException {
		return getPersistence().remove(groupFriendlyURLId);
	}

	public static GroupFriendlyURL updateImpl(GroupFriendlyURL groupFriendlyURL) {
		return getPersistence().updateImpl(groupFriendlyURL);
	}

	/**
	* Returns the group friendly url with the primary key or throws a {@link NoSuchGroupFriendlyURLException} if it could not be found.
	*
	* @param groupFriendlyURLId the primary key of the group friendly url
	* @return the group friendly url
	* @throws NoSuchGroupFriendlyURLException if a group friendly url with the primary key could not be found
	*/
	public static GroupFriendlyURL findByPrimaryKey(long groupFriendlyURLId)
		throws com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException {
		return getPersistence().findByPrimaryKey(groupFriendlyURLId);
	}

	/**
	* Returns the group friendly url with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param groupFriendlyURLId the primary key of the group friendly url
	* @return the group friendly url, or <code>null</code> if a group friendly url with the primary key could not be found
	*/
	public static GroupFriendlyURL fetchByPrimaryKey(long groupFriendlyURLId) {
		return getPersistence().fetchByPrimaryKey(groupFriendlyURLId);
	}

	public static java.util.Map<java.io.Serializable, GroupFriendlyURL> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the group friendly urls.
	*
	* @return the group friendly urls
	*/
	public static List<GroupFriendlyURL> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the group friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @return the range of group friendly urls
	*/
	public static List<GroupFriendlyURL> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the group friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of group friendly urls
	*/
	public static List<GroupFriendlyURL> findAll(int start, int end,
		OrderByComparator<GroupFriendlyURL> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the group friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of group friendly urls
	*/
	public static List<GroupFriendlyURL> findAll(int start, int end,
		OrderByComparator<GroupFriendlyURL> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the group friendly urls from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of group friendly urls.
	*
	* @return the number of group friendly urls
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static GroupFriendlyURLPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (GroupFriendlyURLPersistence)PortalBeanLocatorUtil.locate(GroupFriendlyURLPersistence.class.getName());

			ReferenceRegistry.registerReference(GroupFriendlyURLUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static GroupFriendlyURLPersistence _persistence;
}