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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the group service. This utility wraps {@link com.liferay.portal.service.persistence.impl.GroupPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GroupPersistence
 * @see com.liferay.portal.service.persistence.impl.GroupPersistenceImpl
 * @generated
 */
@ProviderType
public class GroupUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(Group group) {
		getPersistence().clearCache(group);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Group> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Group> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Group> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static Group update(Group group) {
		return getPersistence().update(group);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static Group update(Group group, ServiceContext serviceContext) {
		return getPersistence().update(group, serviceContext);
	}

	/**
	* Returns all the groups where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching groups
	*/
	public static List<Group> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the groups where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public static List<Group> findByUuid(java.lang.String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the groups where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<Group> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the groups where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByUuid_First(java.lang.String uuid,
		OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByUuid_Last(java.lang.String uuid,
		OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the groups before and after the current group in the ordered set where uuid = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public static Group[] findByUuid_PrevAndNext(long groupId,
		java.lang.String uuid, OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByUuid_PrevAndNext(groupId, uuid, orderByComparator);
	}

	/**
	* Removes all the groups where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of groups where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching groups
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the group where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchGroupException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the group where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the group where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the group where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the group that was removed
	*/
	public static Group removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of groups where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching groups
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the groups where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching groups
	*/
	public static List<Group> findByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the groups where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public static List<Group> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the groups where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the groups where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the groups before and after the current group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public static Group[] findByUuid_C_PrevAndNext(long groupId,
		java.lang.String uuid, long companyId,
		OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(groupId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the groups where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of groups where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching groups
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the groups where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching groups
	*/
	public static List<Group> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the groups where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public static List<Group> findByCompanyId(long companyId, int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the groups where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the groups where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first group in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByCompanyId_First(long companyId,
		OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first group in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByCompanyId_First(long companyId,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByCompanyId_Last(long companyId,
		OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByCompanyId_Last(long companyId,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the groups before and after the current group in the ordered set where companyId = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public static Group[] findByCompanyId_PrevAndNext(long groupId,
		long companyId, OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(groupId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the groups where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of groups where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching groups
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the group where liveGroupId = &#63; or throws a {@link NoSuchGroupException} if it could not be found.
	*
	* @param liveGroupId the live group ID
	* @return the matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByLiveGroupId(long liveGroupId)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence().findByLiveGroupId(liveGroupId);
	}

	/**
	* Returns the group where liveGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param liveGroupId the live group ID
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByLiveGroupId(long liveGroupId) {
		return getPersistence().fetchByLiveGroupId(liveGroupId);
	}

	/**
	* Returns the group where liveGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param liveGroupId the live group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByLiveGroupId(long liveGroupId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByLiveGroupId(liveGroupId, retrieveFromCache);
	}

	/**
	* Removes the group where liveGroupId = &#63; from the database.
	*
	* @param liveGroupId the live group ID
	* @return the group that was removed
	*/
	public static Group removeByLiveGroupId(long liveGroupId)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence().removeByLiveGroupId(liveGroupId);
	}

	/**
	* Returns the number of groups where liveGroupId = &#63;.
	*
	* @param liveGroupId the live group ID
	* @return the number of matching groups
	*/
	public static int countByLiveGroupId(long liveGroupId) {
		return getPersistence().countByLiveGroupId(liveGroupId);
	}

	/**
	* Returns all the groups where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the matching groups
	*/
	public static List<Group> findByC_C(long companyId, long classNameId) {
		return getPersistence().findByC_C(companyId, classNameId);
	}

	/**
	* Returns a range of all the groups where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public static List<Group> findByC_C(long companyId, long classNameId,
		int start, int end) {
		return getPersistence().findByC_C(companyId, classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByC_C(long companyId, long classNameId,
		int start, int end, OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .findByC_C(companyId, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByC_C(long companyId, long classNameId,
		int start, int end, OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C(companyId, classNameId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first group in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByC_C_First(long companyId, long classNameId,
		OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_C_First(companyId, classNameId, orderByComparator);
	}

	/**
	* Returns the first group in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_C_First(long companyId, long classNameId,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_First(companyId, classNameId, orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByC_C_Last(long companyId, long classNameId,
		OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_C_Last(companyId, classNameId, orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_C_Last(long companyId, long classNameId,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_Last(companyId, classNameId, orderByComparator);
	}

	/**
	* Returns the groups before and after the current group in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public static Group[] findByC_C_PrevAndNext(long groupId, long companyId,
		long classNameId, OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_C_PrevAndNext(groupId, companyId, classNameId,
			orderByComparator);
	}

	/**
	* Removes all the groups where companyId = &#63; and classNameId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	*/
	public static void removeByC_C(long companyId, long classNameId) {
		getPersistence().removeByC_C(companyId, classNameId);
	}

	/**
	* Returns the number of groups where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @return the number of matching groups
	*/
	public static int countByC_C(long companyId, long classNameId) {
		return getPersistence().countByC_C(companyId, classNameId);
	}

	/**
	* Returns all the groups where companyId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @return the matching groups
	*/
	public static List<Group> findByC_P(long companyId, long parentGroupId) {
		return getPersistence().findByC_P(companyId, parentGroupId);
	}

	/**
	* Returns a range of all the groups where companyId = &#63; and parentGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public static List<Group> findByC_P(long companyId, long parentGroupId,
		int start, int end) {
		return getPersistence().findByC_P(companyId, parentGroupId, start, end);
	}

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByC_P(long companyId, long parentGroupId,
		int start, int end, OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .findByC_P(companyId, parentGroupId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByC_P(long companyId, long parentGroupId,
		int start, int end, OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_P(companyId, parentGroupId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByC_P_First(long companyId, long parentGroupId,
		OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_P_First(companyId, parentGroupId, orderByComparator);
	}

	/**
	* Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_P_First(long companyId, long parentGroupId,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByC_P_First(companyId, parentGroupId, orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByC_P_Last(long companyId, long parentGroupId,
		OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_P_Last(companyId, parentGroupId, orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_P_Last(long companyId, long parentGroupId,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByC_P_Last(companyId, parentGroupId, orderByComparator);
	}

	/**
	* Returns the groups before and after the current group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public static Group[] findByC_P_PrevAndNext(long groupId, long companyId,
		long parentGroupId, OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_P_PrevAndNext(groupId, companyId, parentGroupId,
			orderByComparator);
	}

	/**
	* Removes all the groups where companyId = &#63; and parentGroupId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	*/
	public static void removeByC_P(long companyId, long parentGroupId) {
		getPersistence().removeByC_P(companyId, parentGroupId);
	}

	/**
	* Returns the number of groups where companyId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @return the number of matching groups
	*/
	public static int countByC_P(long companyId, long parentGroupId) {
		return getPersistence().countByC_P(companyId, parentGroupId);
	}

	/**
	* Returns the group where companyId = &#63; and groupKey = &#63; or throws a {@link NoSuchGroupException} if it could not be found.
	*
	* @param companyId the company ID
	* @param groupKey the group key
	* @return the matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByC_GK(long companyId, java.lang.String groupKey)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence().findByC_GK(companyId, groupKey);
	}

	/**
	* Returns the group where companyId = &#63; and groupKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param groupKey the group key
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_GK(long companyId, java.lang.String groupKey) {
		return getPersistence().fetchByC_GK(companyId, groupKey);
	}

	/**
	* Returns the group where companyId = &#63; and groupKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param groupKey the group key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_GK(long companyId, java.lang.String groupKey,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_GK(companyId, groupKey, retrieveFromCache);
	}

	/**
	* Removes the group where companyId = &#63; and groupKey = &#63; from the database.
	*
	* @param companyId the company ID
	* @param groupKey the group key
	* @return the group that was removed
	*/
	public static Group removeByC_GK(long companyId, java.lang.String groupKey)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence().removeByC_GK(companyId, groupKey);
	}

	/**
	* Returns the number of groups where companyId = &#63; and groupKey = &#63;.
	*
	* @param companyId the company ID
	* @param groupKey the group key
	* @return the number of matching groups
	*/
	public static int countByC_GK(long companyId, java.lang.String groupKey) {
		return getPersistence().countByC_GK(companyId, groupKey);
	}

	/**
	* Returns the group where companyId = &#63; and friendlyURL = &#63; or throws a {@link NoSuchGroupException} if it could not be found.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @return the matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByC_F(long companyId, java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence().findByC_F(companyId, friendlyURL);
	}

	/**
	* Returns the group where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_F(long companyId, java.lang.String friendlyURL) {
		return getPersistence().fetchByC_F(companyId, friendlyURL);
	}

	/**
	* Returns the group where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_F(long companyId,
		java.lang.String friendlyURL, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_F(companyId, friendlyURL, retrieveFromCache);
	}

	/**
	* Removes the group where companyId = &#63; and friendlyURL = &#63; from the database.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @return the group that was removed
	*/
	public static Group removeByC_F(long companyId, java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence().removeByC_F(companyId, friendlyURL);
	}

	/**
	* Returns the number of groups where companyId = &#63; and friendlyURL = &#63;.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly u r l
	* @return the number of matching groups
	*/
	public static int countByC_F(long companyId, java.lang.String friendlyURL) {
		return getPersistence().countByC_F(companyId, friendlyURL);
	}

	/**
	* Returns all the groups where companyId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param site the site
	* @return the matching groups
	*/
	public static List<Group> findByC_S(long companyId, boolean site) {
		return getPersistence().findByC_S(companyId, site);
	}

	/**
	* Returns a range of all the groups where companyId = &#63; and site = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param site the site
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public static List<Group> findByC_S(long companyId, boolean site,
		int start, int end) {
		return getPersistence().findByC_S(companyId, site, start, end);
	}

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and site = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param site the site
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByC_S(long companyId, boolean site,
		int start, int end, OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .findByC_S(companyId, site, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and site = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param site the site
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByC_S(long companyId, boolean site,
		int start, int end, OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_S(companyId, site, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first group in the ordered set where companyId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByC_S_First(long companyId, boolean site,
		OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_S_First(companyId, site, orderByComparator);
	}

	/**
	* Returns the first group in the ordered set where companyId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_S_First(long companyId, boolean site,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByC_S_First(companyId, site, orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where companyId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByC_S_Last(long companyId, boolean site,
		OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_S_Last(companyId, site, orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where companyId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_S_Last(long companyId, boolean site,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByC_S_Last(companyId, site, orderByComparator);
	}

	/**
	* Returns the groups before and after the current group in the ordered set where companyId = &#63; and site = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param companyId the company ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public static Group[] findByC_S_PrevAndNext(long groupId, long companyId,
		boolean site, OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_S_PrevAndNext(groupId, companyId, site,
			orderByComparator);
	}

	/**
	* Removes all the groups where companyId = &#63; and site = &#63; from the database.
	*
	* @param companyId the company ID
	* @param site the site
	*/
	public static void removeByC_S(long companyId, boolean site) {
		getPersistence().removeByC_S(companyId, site);
	}

	/**
	* Returns the number of groups where companyId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param site the site
	* @return the number of matching groups
	*/
	public static int countByC_S(long companyId, boolean site) {
		return getPersistence().countByC_S(companyId, site);
	}

	/**
	* Returns all the groups where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @return the matching groups
	*/
	public static List<Group> findByC_A(long companyId, boolean active) {
		return getPersistence().findByC_A(companyId, active);
	}

	/**
	* Returns a range of all the groups where companyId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param active the active
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public static List<Group> findByC_A(long companyId, boolean active,
		int start, int end) {
		return getPersistence().findByC_A(companyId, active, start, end);
	}

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param active the active
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByC_A(long companyId, boolean active,
		int start, int end, OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .findByC_A(companyId, active, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param active the active
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByC_A(long companyId, boolean active,
		int start, int end, OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_A(companyId, active, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first group in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByC_A_First(long companyId, boolean active,
		OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_A_First(companyId, active, orderByComparator);
	}

	/**
	* Returns the first group in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_A_First(long companyId, boolean active,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByC_A_First(companyId, active, orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByC_A_Last(long companyId, boolean active,
		OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_A_Last(companyId, active, orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_A_Last(long companyId, boolean active,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByC_A_Last(companyId, active, orderByComparator);
	}

	/**
	* Returns the groups before and after the current group in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public static Group[] findByC_A_PrevAndNext(long groupId, long companyId,
		boolean active, OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_A_PrevAndNext(groupId, companyId, active,
			orderByComparator);
	}

	/**
	* Removes all the groups where companyId = &#63; and active = &#63; from the database.
	*
	* @param companyId the company ID
	* @param active the active
	*/
	public static void removeByC_A(long companyId, boolean active) {
		getPersistence().removeByC_A(companyId, active);
	}

	/**
	* Returns the number of groups where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @return the number of matching groups
	*/
	public static int countByC_A(long companyId, boolean active) {
		return getPersistence().countByC_A(companyId, active);
	}

	/**
	* Returns all the groups where type = &#63; and active = &#63;.
	*
	* @param type the type
	* @param active the active
	* @return the matching groups
	*/
	public static List<Group> findByT_A(int type, boolean active) {
		return getPersistence().findByT_A(type, active);
	}

	/**
	* Returns a range of all the groups where type = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param active the active
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public static List<Group> findByT_A(int type, boolean active, int start,
		int end) {
		return getPersistence().findByT_A(type, active, start, end);
	}

	/**
	* Returns an ordered range of all the groups where type = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param active the active
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByT_A(int type, boolean active, int start,
		int end, OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .findByT_A(type, active, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the groups where type = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param active the active
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByT_A(int type, boolean active, int start,
		int end, OrderByComparator<Group> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByT_A(type, active, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first group in the ordered set where type = &#63; and active = &#63;.
	*
	* @param type the type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByT_A_First(int type, boolean active,
		OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence().findByT_A_First(type, active, orderByComparator);
	}

	/**
	* Returns the first group in the ordered set where type = &#63; and active = &#63;.
	*
	* @param type the type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByT_A_First(int type, boolean active,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence().fetchByT_A_First(type, active, orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where type = &#63; and active = &#63;.
	*
	* @param type the type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByT_A_Last(int type, boolean active,
		OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence().findByT_A_Last(type, active, orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where type = &#63; and active = &#63;.
	*
	* @param type the type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByT_A_Last(int type, boolean active,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence().fetchByT_A_Last(type, active, orderByComparator);
	}

	/**
	* Returns the groups before and after the current group in the ordered set where type = &#63; and active = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param type the type
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public static Group[] findByT_A_PrevAndNext(long groupId, int type,
		boolean active, OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByT_A_PrevAndNext(groupId, type, active,
			orderByComparator);
	}

	/**
	* Removes all the groups where type = &#63; and active = &#63; from the database.
	*
	* @param type the type
	* @param active the active
	*/
	public static void removeByT_A(int type, boolean active) {
		getPersistence().removeByT_A(type, active);
	}

	/**
	* Returns the number of groups where type = &#63; and active = &#63;.
	*
	* @param type the type
	* @param active the active
	* @return the number of matching groups
	*/
	public static int countByT_A(int type, boolean active) {
		return getPersistence().countByT_A(type, active);
	}

	/**
	* Returns all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @return the matching groups
	*/
	public static List<Group> findByG_C_P(long groupId, long companyId,
		long parentGroupId) {
		return getPersistence().findByG_C_P(groupId, companyId, parentGroupId);
	}

	/**
	* Returns a range of all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public static List<Group> findByG_C_P(long groupId, long companyId,
		long parentGroupId, int start, int end) {
		return getPersistence()
				   .findByG_C_P(groupId, companyId, parentGroupId, start, end);
	}

	/**
	* Returns an ordered range of all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByG_C_P(long groupId, long companyId,
		long parentGroupId, int start, int end,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .findByG_C_P(groupId, companyId, parentGroupId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByG_C_P(long groupId, long companyId,
		long parentGroupId, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_C_P(groupId, companyId, parentGroupId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first group in the ordered set where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByG_C_P_First(long groupId, long companyId,
		long parentGroupId, OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByG_C_P_First(groupId, companyId, parentGroupId,
			orderByComparator);
	}

	/**
	* Returns the first group in the ordered set where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByG_C_P_First(long groupId, long companyId,
		long parentGroupId, OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_P_First(groupId, companyId, parentGroupId,
			orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByG_C_P_Last(long groupId, long companyId,
		long parentGroupId, OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByG_C_P_Last(groupId, companyId, parentGroupId,
			orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByG_C_P_Last(long groupId, long companyId,
		long parentGroupId, OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByG_C_P_Last(groupId, companyId, parentGroupId,
			orderByComparator);
	}

	/**
	* Removes all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	*/
	public static void removeByG_C_P(long groupId, long companyId,
		long parentGroupId) {
		getPersistence().removeByG_C_P(groupId, companyId, parentGroupId);
	}

	/**
	* Returns the number of groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	*
	* @param groupId the group ID
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @return the number of matching groups
	*/
	public static int countByG_C_P(long groupId, long companyId,
		long parentGroupId) {
		return getPersistence().countByG_C_P(groupId, companyId, parentGroupId);
	}

	/**
	* Returns the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchGroupException} if it could not be found.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByC_C_C(long companyId, long classNameId,
		long classPK) throws com.liferay.portal.NoSuchGroupException {
		return getPersistence().findByC_C_C(companyId, classNameId, classPK);
	}

	/**
	* Returns the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_C_C(long companyId, long classNameId,
		long classPK) {
		return getPersistence().fetchByC_C_C(companyId, classNameId, classPK);
	}

	/**
	* Returns the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_C_C(long companyId, long classNameId,
		long classPK, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_C_C(companyId, classNameId, classPK,
			retrieveFromCache);
	}

	/**
	* Removes the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the group that was removed
	*/
	public static Group removeByC_C_C(long companyId, long classNameId,
		long classPK) throws com.liferay.portal.NoSuchGroupException {
		return getPersistence().removeByC_C_C(companyId, classNameId, classPK);
	}

	/**
	* Returns the number of groups where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching groups
	*/
	public static int countByC_C_C(long companyId, long classNameId,
		long classPK) {
		return getPersistence().countByC_C_C(companyId, classNameId, classPK);
	}

	/**
	* Returns all the groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @return the matching groups
	*/
	public static List<Group> findByC_C_P(long companyId, long classNameId,
		long parentGroupId) {
		return getPersistence()
				   .findByC_C_P(companyId, classNameId, parentGroupId);
	}

	/**
	* Returns a range of all the groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public static List<Group> findByC_C_P(long companyId, long classNameId,
		long parentGroupId, int start, int end) {
		return getPersistence()
				   .findByC_C_P(companyId, classNameId, parentGroupId, start,
			end);
	}

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByC_C_P(long companyId, long classNameId,
		long parentGroupId, int start, int end,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .findByC_C_P(companyId, classNameId, parentGroupId, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByC_C_P(long companyId, long classNameId,
		long parentGroupId, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C_P(companyId, classNameId, parentGroupId, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first group in the ordered set where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByC_C_P_First(long companyId, long classNameId,
		long parentGroupId, OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_C_P_First(companyId, classNameId, parentGroupId,
			orderByComparator);
	}

	/**
	* Returns the first group in the ordered set where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_C_P_First(long companyId, long classNameId,
		long parentGroupId, OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_P_First(companyId, classNameId, parentGroupId,
			orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByC_C_P_Last(long companyId, long classNameId,
		long parentGroupId, OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_C_P_Last(companyId, classNameId, parentGroupId,
			orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_C_P_Last(long companyId, long classNameId,
		long parentGroupId, OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_P_Last(companyId, classNameId, parentGroupId,
			orderByComparator);
	}

	/**
	* Returns the groups before and after the current group in the ordered set where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public static Group[] findByC_C_P_PrevAndNext(long groupId, long companyId,
		long classNameId, long parentGroupId,
		OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_C_P_PrevAndNext(groupId, companyId, classNameId,
			parentGroupId, orderByComparator);
	}

	/**
	* Removes all the groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	*/
	public static void removeByC_C_P(long companyId, long classNameId,
		long parentGroupId) {
		getPersistence().removeByC_C_P(companyId, classNameId, parentGroupId);
	}

	/**
	* Returns the number of groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param parentGroupId the parent group ID
	* @return the number of matching groups
	*/
	public static int countByC_C_P(long companyId, long classNameId,
		long parentGroupId) {
		return getPersistence()
				   .countByC_C_P(companyId, classNameId, parentGroupId);
	}

	/**
	* Returns all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @return the matching groups
	*/
	public static List<Group> findByC_P_S(long companyId, long parentGroupId,
		boolean site) {
		return getPersistence().findByC_P_S(companyId, parentGroupId, site);
	}

	/**
	* Returns a range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public static List<Group> findByC_P_S(long companyId, long parentGroupId,
		boolean site, int start, int end) {
		return getPersistence()
				   .findByC_P_S(companyId, parentGroupId, site, start, end);
	}

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByC_P_S(long companyId, long parentGroupId,
		boolean site, int start, int end,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .findByC_P_S(companyId, parentGroupId, site, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByC_P_S(long companyId, long parentGroupId,
		boolean site, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_P_S(companyId, parentGroupId, site, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByC_P_S_First(long companyId, long parentGroupId,
		boolean site, OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_P_S_First(companyId, parentGroupId, site,
			orderByComparator);
	}

	/**
	* Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_P_S_First(long companyId, long parentGroupId,
		boolean site, OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByC_P_S_First(companyId, parentGroupId, site,
			orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByC_P_S_Last(long companyId, long parentGroupId,
		boolean site, OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_P_S_Last(companyId, parentGroupId, site,
			orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_P_S_Last(long companyId, long parentGroupId,
		boolean site, OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByC_P_S_Last(companyId, parentGroupId, site,
			orderByComparator);
	}

	/**
	* Returns the groups before and after the current group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public static Group[] findByC_P_S_PrevAndNext(long groupId, long companyId,
		long parentGroupId, boolean site,
		OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_P_S_PrevAndNext(groupId, companyId, parentGroupId,
			site, orderByComparator);
	}

	/**
	* Removes all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; from the database.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	*/
	public static void removeByC_P_S(long companyId, long parentGroupId,
		boolean site) {
		getPersistence().removeByC_P_S(companyId, parentGroupId, site);
	}

	/**
	* Returns the number of groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @return the number of matching groups
	*/
	public static int countByC_P_S(long companyId, long parentGroupId,
		boolean site) {
		return getPersistence().countByC_P_S(companyId, parentGroupId, site);
	}

	/**
	* Returns the group where companyId = &#63; and liveGroupId = &#63; and groupKey = &#63; or throws a {@link NoSuchGroupException} if it could not be found.
	*
	* @param companyId the company ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @return the matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByC_L_GK(long companyId, long liveGroupId,
		java.lang.String groupKey)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence().findByC_L_GK(companyId, liveGroupId, groupKey);
	}

	/**
	* Returns the group where companyId = &#63; and liveGroupId = &#63; and groupKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_L_GK(long companyId, long liveGroupId,
		java.lang.String groupKey) {
		return getPersistence().fetchByC_L_GK(companyId, liveGroupId, groupKey);
	}

	/**
	* Returns the group where companyId = &#63; and liveGroupId = &#63; and groupKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_L_GK(long companyId, long liveGroupId,
		java.lang.String groupKey, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_L_GK(companyId, liveGroupId, groupKey,
			retrieveFromCache);
	}

	/**
	* Removes the group where companyId = &#63; and liveGroupId = &#63; and groupKey = &#63; from the database.
	*
	* @param companyId the company ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @return the group that was removed
	*/
	public static Group removeByC_L_GK(long companyId, long liveGroupId,
		java.lang.String groupKey)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence().removeByC_L_GK(companyId, liveGroupId, groupKey);
	}

	/**
	* Returns the number of groups where companyId = &#63; and liveGroupId = &#63; and groupKey = &#63;.
	*
	* @param companyId the company ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @return the number of matching groups
	*/
	public static int countByC_L_GK(long companyId, long liveGroupId,
		java.lang.String groupKey) {
		return getPersistence().countByC_L_GK(companyId, liveGroupId, groupKey);
	}

	/**
	* Returns the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and groupKey = &#63; or throws a {@link NoSuchGroupException} if it could not be found.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @return the matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByC_C_L_GK(long companyId, long classNameId,
		long liveGroupId, java.lang.String groupKey)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_C_L_GK(companyId, classNameId, liveGroupId, groupKey);
	}

	/**
	* Returns the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and groupKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_C_L_GK(long companyId, long classNameId,
		long liveGroupId, java.lang.String groupKey) {
		return getPersistence()
				   .fetchByC_C_L_GK(companyId, classNameId, liveGroupId,
			groupKey);
	}

	/**
	* Returns the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and groupKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_C_L_GK(long companyId, long classNameId,
		long liveGroupId, java.lang.String groupKey, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_C_L_GK(companyId, classNameId, liveGroupId,
			groupKey, retrieveFromCache);
	}

	/**
	* Removes the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and groupKey = &#63; from the database.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @return the group that was removed
	*/
	public static Group removeByC_C_L_GK(long companyId, long classNameId,
		long liveGroupId, java.lang.String groupKey)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .removeByC_C_L_GK(companyId, classNameId, liveGroupId,
			groupKey);
	}

	/**
	* Returns the number of groups where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and groupKey = &#63;.
	*
	* @param companyId the company ID
	* @param classNameId the class name ID
	* @param liveGroupId the live group ID
	* @param groupKey the group key
	* @return the number of matching groups
	*/
	public static int countByC_C_L_GK(long companyId, long classNameId,
		long liveGroupId, java.lang.String groupKey) {
		return getPersistence()
				   .countByC_C_L_GK(companyId, classNameId, liveGroupId,
			groupKey);
	}

	/**
	* Returns all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @return the matching groups
	*/
	public static List<Group> findByC_P_S_I(long companyId, long parentGroupId,
		boolean site, boolean inheritContent) {
		return getPersistence()
				   .findByC_P_S_I(companyId, parentGroupId, site, inheritContent);
	}

	/**
	* Returns a range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of matching groups
	*/
	public static List<Group> findByC_P_S_I(long companyId, long parentGroupId,
		boolean site, boolean inheritContent, int start, int end) {
		return getPersistence()
				   .findByC_P_S_I(companyId, parentGroupId, site,
			inheritContent, start, end);
	}

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByC_P_S_I(long companyId, long parentGroupId,
		boolean site, boolean inheritContent, int start, int end,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .findByC_P_S_I(companyId, parentGroupId, site,
			inheritContent, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching groups
	*/
	public static List<Group> findByC_P_S_I(long companyId, long parentGroupId,
		boolean site, boolean inheritContent, int start, int end,
		OrderByComparator<Group> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_P_S_I(companyId, parentGroupId, site,
			inheritContent, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByC_P_S_I_First(long companyId, long parentGroupId,
		boolean site, boolean inheritContent,
		OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_P_S_I_First(companyId, parentGroupId, site,
			inheritContent, orderByComparator);
	}

	/**
	* Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_P_S_I_First(long companyId,
		long parentGroupId, boolean site, boolean inheritContent,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByC_P_S_I_First(companyId, parentGroupId, site,
			inheritContent, orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group
	* @throws NoSuchGroupException if a matching group could not be found
	*/
	public static Group findByC_P_S_I_Last(long companyId, long parentGroupId,
		boolean site, boolean inheritContent,
		OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_P_S_I_Last(companyId, parentGroupId, site,
			inheritContent, orderByComparator);
	}

	/**
	* Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group, or <code>null</code> if a matching group could not be found
	*/
	public static Group fetchByC_P_S_I_Last(long companyId, long parentGroupId,
		boolean site, boolean inheritContent,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence()
				   .fetchByC_P_S_I_Last(companyId, parentGroupId, site,
			inheritContent, orderByComparator);
	}

	/**
	* Returns the groups before and after the current group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* @param groupId the primary key of the current group
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public static Group[] findByC_P_S_I_PrevAndNext(long groupId,
		long companyId, long parentGroupId, boolean site,
		boolean inheritContent, OrderByComparator<Group> orderByComparator)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence()
				   .findByC_P_S_I_PrevAndNext(groupId, companyId,
			parentGroupId, site, inheritContent, orderByComparator);
	}

	/**
	* Removes all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63; from the database.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	*/
	public static void removeByC_P_S_I(long companyId, long parentGroupId,
		boolean site, boolean inheritContent) {
		getPersistence()
			.removeByC_P_S_I(companyId, parentGroupId, site, inheritContent);
	}

	/**
	* Returns the number of groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; and inheritContent = &#63;.
	*
	* @param companyId the company ID
	* @param parentGroupId the parent group ID
	* @param site the site
	* @param inheritContent the inherit content
	* @return the number of matching groups
	*/
	public static int countByC_P_S_I(long companyId, long parentGroupId,
		boolean site, boolean inheritContent) {
		return getPersistence()
				   .countByC_P_S_I(companyId, parentGroupId, site,
			inheritContent);
	}

	/**
	* Caches the group in the entity cache if it is enabled.
	*
	* @param group the group
	*/
	public static void cacheResult(Group group) {
		getPersistence().cacheResult(group);
	}

	/**
	* Caches the groups in the entity cache if it is enabled.
	*
	* @param groups the groups
	*/
	public static void cacheResult(List<Group> groups) {
		getPersistence().cacheResult(groups);
	}

	/**
	* Creates a new group with the primary key. Does not add the group to the database.
	*
	* @param groupId the primary key for the new group
	* @return the new group
	*/
	public static Group create(long groupId) {
		return getPersistence().create(groupId);
	}

	/**
	* Removes the group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param groupId the primary key of the group
	* @return the group that was removed
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public static Group remove(long groupId)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence().remove(groupId);
	}

	public static Group updateImpl(Group group) {
		return getPersistence().updateImpl(group);
	}

	/**
	* Returns the group with the primary key or throws a {@link NoSuchGroupException} if it could not be found.
	*
	* @param groupId the primary key of the group
	* @return the group
	* @throws NoSuchGroupException if a group with the primary key could not be found
	*/
	public static Group findByPrimaryKey(long groupId)
		throws com.liferay.portal.NoSuchGroupException {
		return getPersistence().findByPrimaryKey(groupId);
	}

	/**
	* Returns the group with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param groupId the primary key of the group
	* @return the group, or <code>null</code> if a group with the primary key could not be found
	*/
	public static Group fetchByPrimaryKey(long groupId) {
		return getPersistence().fetchByPrimaryKey(groupId);
	}

	public static java.util.Map<java.io.Serializable, Group> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the groups.
	*
	* @return the groups
	*/
	public static List<Group> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of groups
	*/
	public static List<Group> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of groups
	*/
	public static List<Group> findAll(int start, int end,
		OrderByComparator<Group> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of groups
	*/
	public static List<Group> findAll(int start, int end,
		OrderByComparator<Group> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the groups from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of groups.
	*
	* @return the number of groups
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	* Returns the primaryKeys of organizations associated with the group.
	*
	* @param pk the primary key of the group
	* @return long[] of the primaryKeys of organizations associated with the group
	*/
	public static long[] getOrganizationPrimaryKeys(long pk) {
		return getPersistence().getOrganizationPrimaryKeys(pk);
	}

	/**
	* Returns all the organizations associated with the group.
	*
	* @param pk the primary key of the group
	* @return the organizations associated with the group
	*/
	public static List<com.liferay.portal.model.Organization> getOrganizations(
		long pk) {
		return getPersistence().getOrganizations(pk);
	}

	/**
	* Returns a range of all the organizations associated with the group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the group
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of organizations associated with the group
	*/
	public static List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end) {
		return getPersistence().getOrganizations(pk, start, end);
	}

	/**
	* Returns an ordered range of all the organizations associated with the group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the group
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of organizations associated with the group
	*/
	public static List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portal.model.Organization> orderByComparator) {
		return getPersistence()
				   .getOrganizations(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of organizations associated with the group.
	*
	* @param pk the primary key of the group
	* @return the number of organizations associated with the group
	*/
	public static int getOrganizationsSize(long pk) {
		return getPersistence().getOrganizationsSize(pk);
	}

	/**
	* Returns <code>true</code> if the organization is associated with the group.
	*
	* @param pk the primary key of the group
	* @param organizationPK the primary key of the organization
	* @return <code>true</code> if the organization is associated with the group; <code>false</code> otherwise
	*/
	public static boolean containsOrganization(long pk, long organizationPK) {
		return getPersistence().containsOrganization(pk, organizationPK);
	}

	/**
	* Returns <code>true</code> if the group has any organizations associated with it.
	*
	* @param pk the primary key of the group to check for associations with organizations
	* @return <code>true</code> if the group has any organizations associated with it; <code>false</code> otherwise
	*/
	public static boolean containsOrganizations(long pk) {
		return getPersistence().containsOrganizations(pk);
	}

	/**
	* Adds an association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organizationPK the primary key of the organization
	*/
	public static void addOrganization(long pk, long organizationPK) {
		getPersistence().addOrganization(pk, organizationPK);
	}

	/**
	* Adds an association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organization the organization
	*/
	public static void addOrganization(long pk,
		com.liferay.portal.model.Organization organization) {
		getPersistence().addOrganization(pk, organization);
	}

	/**
	* Adds an association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organizationPKs the primary keys of the organizations
	*/
	public static void addOrganizations(long pk, long[] organizationPKs) {
		getPersistence().addOrganizations(pk, organizationPKs);
	}

	/**
	* Adds an association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organizations the organizations
	*/
	public static void addOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations) {
		getPersistence().addOrganizations(pk, organizations);
	}

	/**
	* Clears all associations between the group and its organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group to clear the associated organizations from
	*/
	public static void clearOrganizations(long pk) {
		getPersistence().clearOrganizations(pk);
	}

	/**
	* Removes the association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organizationPK the primary key of the organization
	*/
	public static void removeOrganization(long pk, long organizationPK) {
		getPersistence().removeOrganization(pk, organizationPK);
	}

	/**
	* Removes the association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organization the organization
	*/
	public static void removeOrganization(long pk,
		com.liferay.portal.model.Organization organization) {
		getPersistence().removeOrganization(pk, organization);
	}

	/**
	* Removes the association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organizationPKs the primary keys of the organizations
	*/
	public static void removeOrganizations(long pk, long[] organizationPKs) {
		getPersistence().removeOrganizations(pk, organizationPKs);
	}

	/**
	* Removes the association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organizations the organizations
	*/
	public static void removeOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations) {
		getPersistence().removeOrganizations(pk, organizations);
	}

	/**
	* Sets the organizations associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organizationPKs the primary keys of the organizations to be associated with the group
	*/
	public static void setOrganizations(long pk, long[] organizationPKs) {
		getPersistence().setOrganizations(pk, organizationPKs);
	}

	/**
	* Sets the organizations associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param organizations the organizations to be associated with the group
	*/
	public static void setOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations) {
		getPersistence().setOrganizations(pk, organizations);
	}

	/**
	* Returns the primaryKeys of roles associated with the group.
	*
	* @param pk the primary key of the group
	* @return long[] of the primaryKeys of roles associated with the group
	*/
	public static long[] getRolePrimaryKeys(long pk) {
		return getPersistence().getRolePrimaryKeys(pk);
	}

	/**
	* Returns all the roles associated with the group.
	*
	* @param pk the primary key of the group
	* @return the roles associated with the group
	*/
	public static List<com.liferay.portal.model.Role> getRoles(long pk) {
		return getPersistence().getRoles(pk);
	}

	/**
	* Returns a range of all the roles associated with the group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the group
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of roles associated with the group
	*/
	public static List<com.liferay.portal.model.Role> getRoles(long pk,
		int start, int end) {
		return getPersistence().getRoles(pk, start, end);
	}

	/**
	* Returns an ordered range of all the roles associated with the group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the group
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of roles associated with the group
	*/
	public static List<com.liferay.portal.model.Role> getRoles(long pk,
		int start, int end,
		OrderByComparator<com.liferay.portal.model.Role> orderByComparator) {
		return getPersistence().getRoles(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of roles associated with the group.
	*
	* @param pk the primary key of the group
	* @return the number of roles associated with the group
	*/
	public static int getRolesSize(long pk) {
		return getPersistence().getRolesSize(pk);
	}

	/**
	* Returns <code>true</code> if the role is associated with the group.
	*
	* @param pk the primary key of the group
	* @param rolePK the primary key of the role
	* @return <code>true</code> if the role is associated with the group; <code>false</code> otherwise
	*/
	public static boolean containsRole(long pk, long rolePK) {
		return getPersistence().containsRole(pk, rolePK);
	}

	/**
	* Returns <code>true</code> if the group has any roles associated with it.
	*
	* @param pk the primary key of the group to check for associations with roles
	* @return <code>true</code> if the group has any roles associated with it; <code>false</code> otherwise
	*/
	public static boolean containsRoles(long pk) {
		return getPersistence().containsRoles(pk);
	}

	/**
	* Adds an association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param rolePK the primary key of the role
	*/
	public static void addRole(long pk, long rolePK) {
		getPersistence().addRole(pk, rolePK);
	}

	/**
	* Adds an association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param role the role
	*/
	public static void addRole(long pk, com.liferay.portal.model.Role role) {
		getPersistence().addRole(pk, role);
	}

	/**
	* Adds an association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param rolePKs the primary keys of the roles
	*/
	public static void addRoles(long pk, long[] rolePKs) {
		getPersistence().addRoles(pk, rolePKs);
	}

	/**
	* Adds an association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param roles the roles
	*/
	public static void addRoles(long pk,
		List<com.liferay.portal.model.Role> roles) {
		getPersistence().addRoles(pk, roles);
	}

	/**
	* Clears all associations between the group and its roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group to clear the associated roles from
	*/
	public static void clearRoles(long pk) {
		getPersistence().clearRoles(pk);
	}

	/**
	* Removes the association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param rolePK the primary key of the role
	*/
	public static void removeRole(long pk, long rolePK) {
		getPersistence().removeRole(pk, rolePK);
	}

	/**
	* Removes the association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param role the role
	*/
	public static void removeRole(long pk, com.liferay.portal.model.Role role) {
		getPersistence().removeRole(pk, role);
	}

	/**
	* Removes the association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param rolePKs the primary keys of the roles
	*/
	public static void removeRoles(long pk, long[] rolePKs) {
		getPersistence().removeRoles(pk, rolePKs);
	}

	/**
	* Removes the association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param roles the roles
	*/
	public static void removeRoles(long pk,
		List<com.liferay.portal.model.Role> roles) {
		getPersistence().removeRoles(pk, roles);
	}

	/**
	* Sets the roles associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param rolePKs the primary keys of the roles to be associated with the group
	*/
	public static void setRoles(long pk, long[] rolePKs) {
		getPersistence().setRoles(pk, rolePKs);
	}

	/**
	* Sets the roles associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param roles the roles to be associated with the group
	*/
	public static void setRoles(long pk,
		List<com.liferay.portal.model.Role> roles) {
		getPersistence().setRoles(pk, roles);
	}

	/**
	* Returns the primaryKeys of user groups associated with the group.
	*
	* @param pk the primary key of the group
	* @return long[] of the primaryKeys of user groups associated with the group
	*/
	public static long[] getUserGroupPrimaryKeys(long pk) {
		return getPersistence().getUserGroupPrimaryKeys(pk);
	}

	/**
	* Returns all the user groups associated with the group.
	*
	* @param pk the primary key of the group
	* @return the user groups associated with the group
	*/
	public static List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk) {
		return getPersistence().getUserGroups(pk);
	}

	/**
	* Returns a range of all the user groups associated with the group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the group
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of user groups associated with the group
	*/
	public static List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk, int start, int end) {
		return getPersistence().getUserGroups(pk, start, end);
	}

	/**
	* Returns an ordered range of all the user groups associated with the group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the group
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of user groups associated with the group
	*/
	public static List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portal.model.UserGroup> orderByComparator) {
		return getPersistence().getUserGroups(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of user groups associated with the group.
	*
	* @param pk the primary key of the group
	* @return the number of user groups associated with the group
	*/
	public static int getUserGroupsSize(long pk) {
		return getPersistence().getUserGroupsSize(pk);
	}

	/**
	* Returns <code>true</code> if the user group is associated with the group.
	*
	* @param pk the primary key of the group
	* @param userGroupPK the primary key of the user group
	* @return <code>true</code> if the user group is associated with the group; <code>false</code> otherwise
	*/
	public static boolean containsUserGroup(long pk, long userGroupPK) {
		return getPersistence().containsUserGroup(pk, userGroupPK);
	}

	/**
	* Returns <code>true</code> if the group has any user groups associated with it.
	*
	* @param pk the primary key of the group to check for associations with user groups
	* @return <code>true</code> if the group has any user groups associated with it; <code>false</code> otherwise
	*/
	public static boolean containsUserGroups(long pk) {
		return getPersistence().containsUserGroups(pk);
	}

	/**
	* Adds an association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroupPK the primary key of the user group
	*/
	public static void addUserGroup(long pk, long userGroupPK) {
		getPersistence().addUserGroup(pk, userGroupPK);
	}

	/**
	* Adds an association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroup the user group
	*/
	public static void addUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) {
		getPersistence().addUserGroup(pk, userGroup);
	}

	/**
	* Adds an association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroupPKs the primary keys of the user groups
	*/
	public static void addUserGroups(long pk, long[] userGroupPKs) {
		getPersistence().addUserGroups(pk, userGroupPKs);
	}

	/**
	* Adds an association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroups the user groups
	*/
	public static void addUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups) {
		getPersistence().addUserGroups(pk, userGroups);
	}

	/**
	* Clears all associations between the group and its user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group to clear the associated user groups from
	*/
	public static void clearUserGroups(long pk) {
		getPersistence().clearUserGroups(pk);
	}

	/**
	* Removes the association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroupPK the primary key of the user group
	*/
	public static void removeUserGroup(long pk, long userGroupPK) {
		getPersistence().removeUserGroup(pk, userGroupPK);
	}

	/**
	* Removes the association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroup the user group
	*/
	public static void removeUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) {
		getPersistence().removeUserGroup(pk, userGroup);
	}

	/**
	* Removes the association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroupPKs the primary keys of the user groups
	*/
	public static void removeUserGroups(long pk, long[] userGroupPKs) {
		getPersistence().removeUserGroups(pk, userGroupPKs);
	}

	/**
	* Removes the association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroups the user groups
	*/
	public static void removeUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups) {
		getPersistence().removeUserGroups(pk, userGroups);
	}

	/**
	* Sets the user groups associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroupPKs the primary keys of the user groups to be associated with the group
	*/
	public static void setUserGroups(long pk, long[] userGroupPKs) {
		getPersistence().setUserGroups(pk, userGroupPKs);
	}

	/**
	* Sets the user groups associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userGroups the user groups to be associated with the group
	*/
	public static void setUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups) {
		getPersistence().setUserGroups(pk, userGroups);
	}

	/**
	* Returns the primaryKeys of users associated with the group.
	*
	* @param pk the primary key of the group
	* @return long[] of the primaryKeys of users associated with the group
	*/
	public static long[] getUserPrimaryKeys(long pk) {
		return getPersistence().getUserPrimaryKeys(pk);
	}

	/**
	* Returns all the users associated with the group.
	*
	* @param pk the primary key of the group
	* @return the users associated with the group
	*/
	public static List<com.liferay.portal.model.User> getUsers(long pk) {
		return getPersistence().getUsers(pk);
	}

	/**
	* Returns a range of all the users associated with the group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the group
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of users associated with the group
	*/
	public static List<com.liferay.portal.model.User> getUsers(long pk,
		int start, int end) {
		return getPersistence().getUsers(pk, start, end);
	}

	/**
	* Returns an ordered range of all the users associated with the group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the group
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of users associated with the group
	*/
	public static List<com.liferay.portal.model.User> getUsers(long pk,
		int start, int end,
		OrderByComparator<com.liferay.portal.model.User> orderByComparator) {
		return getPersistence().getUsers(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of users associated with the group.
	*
	* @param pk the primary key of the group
	* @return the number of users associated with the group
	*/
	public static int getUsersSize(long pk) {
		return getPersistence().getUsersSize(pk);
	}

	/**
	* Returns <code>true</code> if the user is associated with the group.
	*
	* @param pk the primary key of the group
	* @param userPK the primary key of the user
	* @return <code>true</code> if the user is associated with the group; <code>false</code> otherwise
	*/
	public static boolean containsUser(long pk, long userPK) {
		return getPersistence().containsUser(pk, userPK);
	}

	/**
	* Returns <code>true</code> if the group has any users associated with it.
	*
	* @param pk the primary key of the group to check for associations with users
	* @return <code>true</code> if the group has any users associated with it; <code>false</code> otherwise
	*/
	public static boolean containsUsers(long pk) {
		return getPersistence().containsUsers(pk);
	}

	/**
	* Adds an association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userPK the primary key of the user
	*/
	public static void addUser(long pk, long userPK) {
		getPersistence().addUser(pk, userPK);
	}

	/**
	* Adds an association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param user the user
	*/
	public static void addUser(long pk, com.liferay.portal.model.User user) {
		getPersistence().addUser(pk, user);
	}

	/**
	* Adds an association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userPKs the primary keys of the users
	*/
	public static void addUsers(long pk, long[] userPKs) {
		getPersistence().addUsers(pk, userPKs);
	}

	/**
	* Adds an association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param users the users
	*/
	public static void addUsers(long pk,
		List<com.liferay.portal.model.User> users) {
		getPersistence().addUsers(pk, users);
	}

	/**
	* Clears all associations between the group and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group to clear the associated users from
	*/
	public static void clearUsers(long pk) {
		getPersistence().clearUsers(pk);
	}

	/**
	* Removes the association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userPK the primary key of the user
	*/
	public static void removeUser(long pk, long userPK) {
		getPersistence().removeUser(pk, userPK);
	}

	/**
	* Removes the association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param user the user
	*/
	public static void removeUser(long pk, com.liferay.portal.model.User user) {
		getPersistence().removeUser(pk, user);
	}

	/**
	* Removes the association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userPKs the primary keys of the users
	*/
	public static void removeUsers(long pk, long[] userPKs) {
		getPersistence().removeUsers(pk, userPKs);
	}

	/**
	* Removes the association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param users the users
	*/
	public static void removeUsers(long pk,
		List<com.liferay.portal.model.User> users) {
		getPersistence().removeUsers(pk, users);
	}

	/**
	* Sets the users associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param userPKs the primary keys of the users to be associated with the group
	*/
	public static void setUsers(long pk, long[] userPKs) {
		getPersistence().setUsers(pk, userPKs);
	}

	/**
	* Sets the users associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the group
	* @param users the users to be associated with the group
	*/
	public static void setUsers(long pk,
		List<com.liferay.portal.model.User> users) {
		getPersistence().setUsers(pk, users);
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static GroupPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (GroupPersistence)PortalBeanLocatorUtil.locate(GroupPersistence.class.getName());

			ReferenceRegistry.registerReference(GroupUtil.class, "_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(GroupPersistence persistence) {
	}

	private static GroupPersistence _persistence;
}