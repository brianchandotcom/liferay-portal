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

import com.liferay.commerce.product.model.CPGroup;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp group service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPGroupPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPGroupPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPGroupPersistenceImpl
 * @generated
 */
@ProviderType
public class CPGroupUtil {
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
	public static void clearCache(CPGroup cpGroup) {
		getPersistence().clearCache(cpGroup);
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
	public static List<CPGroup> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPGroup> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPGroup> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPGroup> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPGroup update(CPGroup cpGroup) {
		return getPersistence().update(cpGroup);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPGroup update(CPGroup cpGroup, ServiceContext serviceContext) {
		return getPersistence().update(cpGroup, serviceContext);
	}

	/**
	* Returns all the cp groups where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp groups
	*/
	public static List<CPGroup> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cp groups where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @return the range of matching cp groups
	*/
	public static List<CPGroup> findByUuid(java.lang.String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cp groups where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp groups
	*/
	public static List<CPGroup> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<CPGroup> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp groups where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp groups
	*/
	public static List<CPGroup> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<CPGroup> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp group
	* @throws NoSuchCPGroupException if a matching cp group could not be found
	*/
	public static CPGroup findByUuid_First(java.lang.String uuid,
		OrderByComparator<CPGroup> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPGroupException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp group, or <code>null</code> if a matching cp group could not be found
	*/
	public static CPGroup fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<CPGroup> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp group
	* @throws NoSuchCPGroupException if a matching cp group could not be found
	*/
	public static CPGroup findByUuid_Last(java.lang.String uuid,
		OrderByComparator<CPGroup> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPGroupException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp group, or <code>null</code> if a matching cp group could not be found
	*/
	public static CPGroup fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<CPGroup> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp groups before and after the current cp group in the ordered set where uuid = &#63;.
	*
	* @param CPGroupId the primary key of the current cp group
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp group
	* @throws NoSuchCPGroupException if a cp group with the primary key could not be found
	*/
	public static CPGroup[] findByUuid_PrevAndNext(long CPGroupId,
		java.lang.String uuid, OrderByComparator<CPGroup> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPGroupException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPGroupId, uuid, orderByComparator);
	}

	/**
	* Removes all the cp groups where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp groups where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp groups
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp group where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPGroupException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp group
	* @throws NoSuchCPGroupException if a matching cp group could not be found
	*/
	public static CPGroup findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPGroupException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp group where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp group, or <code>null</code> if a matching cp group could not be found
	*/
	public static CPGroup fetchByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp group where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp group, or <code>null</code> if a matching cp group could not be found
	*/
	public static CPGroup fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp group where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp group that was removed
	*/
	public static CPGroup removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPGroupException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp groups where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp groups
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp groups where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp groups
	*/
	public static List<CPGroup> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cp groups where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @return the range of matching cp groups
	*/
	public static List<CPGroup> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp groups where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp groups
	*/
	public static List<CPGroup> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPGroup> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp groups where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp groups
	*/
	public static List<CPGroup> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPGroup> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp group
	* @throws NoSuchCPGroupException if a matching cp group could not be found
	*/
	public static CPGroup findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CPGroup> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPGroupException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp group, or <code>null</code> if a matching cp group could not be found
	*/
	public static CPGroup fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<CPGroup> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp group
	* @throws NoSuchCPGroupException if a matching cp group could not be found
	*/
	public static CPGroup findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CPGroup> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPGroupException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp group, or <code>null</code> if a matching cp group could not be found
	*/
	public static CPGroup fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<CPGroup> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cp groups before and after the current cp group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPGroupId the primary key of the current cp group
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp group
	* @throws NoSuchCPGroupException if a cp group with the primary key could not be found
	*/
	public static CPGroup[] findByUuid_C_PrevAndNext(long CPGroupId,
		java.lang.String uuid, long companyId,
		OrderByComparator<CPGroup> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPGroupException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPGroupId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the cp groups where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp groups where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp groups
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns the cp group where groupId = &#63; or throws a {@link NoSuchCPGroupException} if it could not be found.
	*
	* @param groupId the group ID
	* @return the matching cp group
	* @throws NoSuchCPGroupException if a matching cp group could not be found
	*/
	public static CPGroup findByGroupId(long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPGroupException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns the cp group where groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @return the matching cp group, or <code>null</code> if a matching cp group could not be found
	*/
	public static CPGroup fetchByGroupId(long groupId) {
		return getPersistence().fetchByGroupId(groupId);
	}

	/**
	* Returns the cp group where groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp group, or <code>null</code> if a matching cp group could not be found
	*/
	public static CPGroup fetchByGroupId(long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByGroupId(groupId, retrieveFromCache);
	}

	/**
	* Removes the cp group where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @return the cp group that was removed
	*/
	public static CPGroup removeByGroupId(long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPGroupException {
		return getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of cp groups where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching cp groups
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Caches the cp group in the entity cache if it is enabled.
	*
	* @param cpGroup the cp group
	*/
	public static void cacheResult(CPGroup cpGroup) {
		getPersistence().cacheResult(cpGroup);
	}

	/**
	* Caches the cp groups in the entity cache if it is enabled.
	*
	* @param cpGroups the cp groups
	*/
	public static void cacheResult(List<CPGroup> cpGroups) {
		getPersistence().cacheResult(cpGroups);
	}

	/**
	* Creates a new cp group with the primary key. Does not add the cp group to the database.
	*
	* @param CPGroupId the primary key for the new cp group
	* @return the new cp group
	*/
	public static CPGroup create(long CPGroupId) {
		return getPersistence().create(CPGroupId);
	}

	/**
	* Removes the cp group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPGroupId the primary key of the cp group
	* @return the cp group that was removed
	* @throws NoSuchCPGroupException if a cp group with the primary key could not be found
	*/
	public static CPGroup remove(long CPGroupId)
		throws com.liferay.commerce.product.exception.NoSuchCPGroupException {
		return getPersistence().remove(CPGroupId);
	}

	public static CPGroup updateImpl(CPGroup cpGroup) {
		return getPersistence().updateImpl(cpGroup);
	}

	/**
	* Returns the cp group with the primary key or throws a {@link NoSuchCPGroupException} if it could not be found.
	*
	* @param CPGroupId the primary key of the cp group
	* @return the cp group
	* @throws NoSuchCPGroupException if a cp group with the primary key could not be found
	*/
	public static CPGroup findByPrimaryKey(long CPGroupId)
		throws com.liferay.commerce.product.exception.NoSuchCPGroupException {
		return getPersistence().findByPrimaryKey(CPGroupId);
	}

	/**
	* Returns the cp group with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPGroupId the primary key of the cp group
	* @return the cp group, or <code>null</code> if a cp group with the primary key could not be found
	*/
	public static CPGroup fetchByPrimaryKey(long CPGroupId) {
		return getPersistence().fetchByPrimaryKey(CPGroupId);
	}

	public static java.util.Map<java.io.Serializable, CPGroup> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp groups.
	*
	* @return the cp groups
	*/
	public static List<CPGroup> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @return the range of cp groups
	*/
	public static List<CPGroup> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp groups
	*/
	public static List<CPGroup> findAll(int start, int end,
		OrderByComparator<CPGroup> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp groups
	*/
	public static List<CPGroup> findAll(int start, int end,
		OrderByComparator<CPGroup> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp groups from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp groups.
	*
	* @return the number of cp groups
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPGroupPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPGroupPersistence, CPGroupPersistence> _serviceTracker =
		ServiceTrackerFactory.open(CPGroupPersistence.class);
}