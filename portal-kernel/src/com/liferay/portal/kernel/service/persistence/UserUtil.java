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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the user service. This utility wraps <code>com.liferay.portal.service.persistence.impl.UserPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserPersistence
 * @generated
 */
@ProviderType
public class UserUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(User user) {
		getPersistence().clearCache(user);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, User> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<User> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<User> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<User> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static User update(User user) {
		return getPersistence().update(user);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static User update(User user, ServiceContext serviceContext) {
		return getPersistence().update(user, serviceContext);
	}

	/**
	* Returns all the users where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching users
	*/
	public static List<User> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the users where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	*/
	public static List<User> findByUuid(String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the users where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	*/
	public static List<User> findByUuid(String uuid, int start, int end,
		OrderByComparator<User> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the users where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching users
	*/
	public static List<User> findByUuid(String uuid, int start, int end,
		OrderByComparator<User> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first user in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByUuid_First(String uuid,
		OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first user in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByUuid_First(String uuid,
		OrderByComparator<User> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByUuid_Last(String uuid,
		OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByUuid_Last(String uuid,
		OrderByComparator<User> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the users before and after the current user in the ordered set where uuid = &#63;.
	*
	* @param userId the primary key of the current user
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user
	* @throws NoSuchUserException if a user with the primary key could not be found
	*/
	public static User[] findByUuid_PrevAndNext(long userId, String uuid,
		OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByUuid_PrevAndNext(userId, uuid, orderByComparator);
	}

	/**
	* Removes all the users where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of users where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching users
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns all the users where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching users
	*/
	public static List<User> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the users where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	*/
	public static List<User> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the users where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	*/
	public static List<User> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the users where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching users
	*/
	public static List<User> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first user in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first user in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the users before and after the current user in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param userId the primary key of the current user
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user
	* @throws NoSuchUserException if a user with the primary key could not be found
	*/
	public static User[] findByUuid_C_PrevAndNext(long userId, String uuid,
		long companyId, OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(userId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the users where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of users where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching users
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the users where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching users
	*/
	public static List<User> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the users where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	*/
	public static List<User> findByCompanyId(long companyId, int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the users where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	*/
	public static List<User> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the users where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching users
	*/
	public static List<User> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first user in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByCompanyId_First(long companyId,
		OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first user in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByCompanyId_First(long companyId,
		OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByCompanyId_Last(long companyId,
		OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByCompanyId_Last(long companyId,
		OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the users before and after the current user in the ordered set where companyId = &#63;.
	*
	* @param userId the primary key of the current user
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user
	* @throws NoSuchUserException if a user with the primary key could not be found
	*/
	public static User[] findByCompanyId_PrevAndNext(long userId,
		long companyId, OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(userId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the users where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of users where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching users
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the user where contactId = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	*
	* @param contactId the contact ID
	* @return the matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByContactId(long contactId)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().findByContactId(contactId);
	}

	/**
	* Returns the user where contactId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param contactId the contact ID
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByContactId(long contactId) {
		return getPersistence().fetchByContactId(contactId);
	}

	/**
	* Returns the user where contactId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param contactId the contact ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByContactId(long contactId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByContactId(contactId, retrieveFromCache);
	}

	/**
	* Removes the user where contactId = &#63; from the database.
	*
	* @param contactId the contact ID
	* @return the user that was removed
	*/
	public static User removeByContactId(long contactId)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().removeByContactId(contactId);
	}

	/**
	* Returns the number of users where contactId = &#63;.
	*
	* @param contactId the contact ID
	* @return the number of matching users
	*/
	public static int countByContactId(long contactId) {
		return getPersistence().countByContactId(contactId);
	}

	/**
	* Returns all the users where emailAddress = &#63;.
	*
	* @param emailAddress the email address
	* @return the matching users
	*/
	public static List<User> findByEmailAddress(String emailAddress) {
		return getPersistence().findByEmailAddress(emailAddress);
	}

	/**
	* Returns a range of all the users where emailAddress = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param emailAddress the email address
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	*/
	public static List<User> findByEmailAddress(String emailAddress, int start,
		int end) {
		return getPersistence().findByEmailAddress(emailAddress, start, end);
	}

	/**
	* Returns an ordered range of all the users where emailAddress = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param emailAddress the email address
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	*/
	public static List<User> findByEmailAddress(String emailAddress, int start,
		int end, OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .findByEmailAddress(emailAddress, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the users where emailAddress = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param emailAddress the email address
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching users
	*/
	public static List<User> findByEmailAddress(String emailAddress, int start,
		int end, OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByEmailAddress(emailAddress, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first user in the ordered set where emailAddress = &#63;.
	*
	* @param emailAddress the email address
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByEmailAddress_First(String emailAddress,
		OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByEmailAddress_First(emailAddress, orderByComparator);
	}

	/**
	* Returns the first user in the ordered set where emailAddress = &#63;.
	*
	* @param emailAddress the email address
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByEmailAddress_First(String emailAddress,
		OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .fetchByEmailAddress_First(emailAddress, orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where emailAddress = &#63;.
	*
	* @param emailAddress the email address
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByEmailAddress_Last(String emailAddress,
		OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByEmailAddress_Last(emailAddress, orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where emailAddress = &#63;.
	*
	* @param emailAddress the email address
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByEmailAddress_Last(String emailAddress,
		OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .fetchByEmailAddress_Last(emailAddress, orderByComparator);
	}

	/**
	* Returns the users before and after the current user in the ordered set where emailAddress = &#63;.
	*
	* @param userId the primary key of the current user
	* @param emailAddress the email address
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user
	* @throws NoSuchUserException if a user with the primary key could not be found
	*/
	public static User[] findByEmailAddress_PrevAndNext(long userId,
		String emailAddress, OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByEmailAddress_PrevAndNext(userId, emailAddress,
			orderByComparator);
	}

	/**
	* Removes all the users where emailAddress = &#63; from the database.
	*
	* @param emailAddress the email address
	*/
	public static void removeByEmailAddress(String emailAddress) {
		getPersistence().removeByEmailAddress(emailAddress);
	}

	/**
	* Returns the number of users where emailAddress = &#63;.
	*
	* @param emailAddress the email address
	* @return the number of matching users
	*/
	public static int countByEmailAddress(String emailAddress) {
		return getPersistence().countByEmailAddress(emailAddress);
	}

	/**
	* Returns the user where portraitId = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	*
	* @param portraitId the portrait ID
	* @return the matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByPortraitId(long portraitId)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().findByPortraitId(portraitId);
	}

	/**
	* Returns the user where portraitId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param portraitId the portrait ID
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByPortraitId(long portraitId) {
		return getPersistence().fetchByPortraitId(portraitId);
	}

	/**
	* Returns the user where portraitId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param portraitId the portrait ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByPortraitId(long portraitId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByPortraitId(portraitId, retrieveFromCache);
	}

	/**
	* Removes the user where portraitId = &#63; from the database.
	*
	* @param portraitId the portrait ID
	* @return the user that was removed
	*/
	public static User removeByPortraitId(long portraitId)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().removeByPortraitId(portraitId);
	}

	/**
	* Returns the number of users where portraitId = &#63;.
	*
	* @param portraitId the portrait ID
	* @return the number of matching users
	*/
	public static int countByPortraitId(long portraitId) {
		return getPersistence().countByPortraitId(portraitId);
	}

	/**
	* Returns all the users where userId &gt; &#63; and companyId = &#63;.
	*
	* @param userId the user ID
	* @param companyId the company ID
	* @return the matching users
	*/
	public static List<User> findByU_C(long userId, long companyId) {
		return getPersistence().findByU_C(userId, companyId);
	}

	/**
	* Returns a range of all the users where userId &gt; &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param companyId the company ID
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	*/
	public static List<User> findByU_C(long userId, long companyId, int start,
		int end) {
		return getPersistence().findByU_C(userId, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the users where userId &gt; &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param companyId the company ID
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	*/
	public static List<User> findByU_C(long userId, long companyId, int start,
		int end, OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .findByU_C(userId, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the users where userId &gt; &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param companyId the company ID
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching users
	*/
	public static List<User> findByU_C(long userId, long companyId, int start,
		int end, OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByU_C(userId, companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first user in the ordered set where userId &gt; &#63; and companyId = &#63;.
	*
	* @param userId the user ID
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByU_C_First(long userId, long companyId,
		OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByU_C_First(userId, companyId, orderByComparator);
	}

	/**
	* Returns the first user in the ordered set where userId &gt; &#63; and companyId = &#63;.
	*
	* @param userId the user ID
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByU_C_First(long userId, long companyId,
		OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .fetchByU_C_First(userId, companyId, orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where userId &gt; &#63; and companyId = &#63;.
	*
	* @param userId the user ID
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByU_C_Last(long userId, long companyId,
		OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByU_C_Last(userId, companyId, orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where userId &gt; &#63; and companyId = &#63;.
	*
	* @param userId the user ID
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByU_C_Last(long userId, long companyId,
		OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .fetchByU_C_Last(userId, companyId, orderByComparator);
	}

	/**
	* Removes all the users where userId &gt; &#63; and companyId = &#63; from the database.
	*
	* @param userId the user ID
	* @param companyId the company ID
	*/
	public static void removeByU_C(long userId, long companyId) {
		getPersistence().removeByU_C(userId, companyId);
	}

	/**
	* Returns the number of users where userId &gt; &#63; and companyId = &#63;.
	*
	* @param userId the user ID
	* @param companyId the company ID
	* @return the number of matching users
	*/
	public static int countByU_C(long userId, long companyId) {
		return getPersistence().countByU_C(userId, companyId);
	}

	/**
	* Returns the user where companyId = &#63; and userId = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @return the matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByC_U(long companyId, long userId)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().findByC_U(companyId, userId);
	}

	/**
	* Returns the user where companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_U(long companyId, long userId) {
		return getPersistence().fetchByC_U(companyId, userId);
	}

	/**
	* Returns the user where companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_U(long companyId, long userId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByC_U(companyId, userId, retrieveFromCache);
	}

	/**
	* Removes the user where companyId = &#63; and userId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @return the user that was removed
	*/
	public static User removeByC_U(long companyId, long userId)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().removeByC_U(companyId, userId);
	}

	/**
	* Returns the number of users where companyId = &#63; and userId = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @return the number of matching users
	*/
	public static int countByC_U(long companyId, long userId) {
		return getPersistence().countByC_U(companyId, userId);
	}

	/**
	* Returns all the users where companyId = &#63; and createDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @return the matching users
	*/
	public static List<User> findByC_CD(long companyId, Date createDate) {
		return getPersistence().findByC_CD(companyId, createDate);
	}

	/**
	* Returns a range of all the users where companyId = &#63; and createDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	*/
	public static List<User> findByC_CD(long companyId, Date createDate,
		int start, int end) {
		return getPersistence().findByC_CD(companyId, createDate, start, end);
	}

	/**
	* Returns an ordered range of all the users where companyId = &#63; and createDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	*/
	public static List<User> findByC_CD(long companyId, Date createDate,
		int start, int end, OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .findByC_CD(companyId, createDate, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the users where companyId = &#63; and createDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching users
	*/
	public static List<User> findByC_CD(long companyId, Date createDate,
		int start, int end, OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_CD(companyId, createDate, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first user in the ordered set where companyId = &#63; and createDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByC_CD_First(long companyId, Date createDate,
		OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByC_CD_First(companyId, createDate, orderByComparator);
	}

	/**
	* Returns the first user in the ordered set where companyId = &#63; and createDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_CD_First(long companyId, Date createDate,
		OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .fetchByC_CD_First(companyId, createDate, orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where companyId = &#63; and createDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByC_CD_Last(long companyId, Date createDate,
		OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByC_CD_Last(companyId, createDate, orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where companyId = &#63; and createDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_CD_Last(long companyId, Date createDate,
		OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .fetchByC_CD_Last(companyId, createDate, orderByComparator);
	}

	/**
	* Returns the users before and after the current user in the ordered set where companyId = &#63; and createDate = &#63;.
	*
	* @param userId the primary key of the current user
	* @param companyId the company ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user
	* @throws NoSuchUserException if a user with the primary key could not be found
	*/
	public static User[] findByC_CD_PrevAndNext(long userId, long companyId,
		Date createDate, OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByC_CD_PrevAndNext(userId, companyId, createDate,
			orderByComparator);
	}

	/**
	* Removes all the users where companyId = &#63; and createDate = &#63; from the database.
	*
	* @param companyId the company ID
	* @param createDate the create date
	*/
	public static void removeByC_CD(long companyId, Date createDate) {
		getPersistence().removeByC_CD(companyId, createDate);
	}

	/**
	* Returns the number of users where companyId = &#63; and createDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @return the number of matching users
	*/
	public static int countByC_CD(long companyId, Date createDate) {
		return getPersistence().countByC_CD(companyId, createDate);
	}

	/**
	* Returns all the users where companyId = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @return the matching users
	*/
	public static List<User> findByC_MD(long companyId, Date modifiedDate) {
		return getPersistence().findByC_MD(companyId, modifiedDate);
	}

	/**
	* Returns a range of all the users where companyId = &#63; and modifiedDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	*/
	public static List<User> findByC_MD(long companyId, Date modifiedDate,
		int start, int end) {
		return getPersistence().findByC_MD(companyId, modifiedDate, start, end);
	}

	/**
	* Returns an ordered range of all the users where companyId = &#63; and modifiedDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	*/
	public static List<User> findByC_MD(long companyId, Date modifiedDate,
		int start, int end, OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .findByC_MD(companyId, modifiedDate, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the users where companyId = &#63; and modifiedDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching users
	*/
	public static List<User> findByC_MD(long companyId, Date modifiedDate,
		int start, int end, OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_MD(companyId, modifiedDate, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByC_MD_First(long companyId, Date modifiedDate,
		OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByC_MD_First(companyId, modifiedDate, orderByComparator);
	}

	/**
	* Returns the first user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_MD_First(long companyId, Date modifiedDate,
		OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .fetchByC_MD_First(companyId, modifiedDate, orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByC_MD_Last(long companyId, Date modifiedDate,
		OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByC_MD_Last(companyId, modifiedDate, orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_MD_Last(long companyId, Date modifiedDate,
		OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .fetchByC_MD_Last(companyId, modifiedDate, orderByComparator);
	}

	/**
	* Returns the users before and after the current user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	*
	* @param userId the primary key of the current user
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user
	* @throws NoSuchUserException if a user with the primary key could not be found
	*/
	public static User[] findByC_MD_PrevAndNext(long userId, long companyId,
		Date modifiedDate, OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByC_MD_PrevAndNext(userId, companyId, modifiedDate,
			orderByComparator);
	}

	/**
	* Removes all the users where companyId = &#63; and modifiedDate = &#63; from the database.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	*/
	public static void removeByC_MD(long companyId, Date modifiedDate) {
		getPersistence().removeByC_MD(companyId, modifiedDate);
	}

	/**
	* Returns the number of users where companyId = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @return the number of matching users
	*/
	public static int countByC_MD(long companyId, Date modifiedDate) {
		return getPersistence().countByC_MD(companyId, modifiedDate);
	}

	/**
	* Returns the user where companyId = &#63; and defaultUser = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @return the matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByC_DU(long companyId, boolean defaultUser)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().findByC_DU(companyId, defaultUser);
	}

	/**
	* Returns the user where companyId = &#63; and defaultUser = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_DU(long companyId, boolean defaultUser) {
		return getPersistence().fetchByC_DU(companyId, defaultUser);
	}

	/**
	* Returns the user where companyId = &#63; and defaultUser = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_DU(long companyId, boolean defaultUser,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_DU(companyId, defaultUser, retrieveFromCache);
	}

	/**
	* Removes the user where companyId = &#63; and defaultUser = &#63; from the database.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @return the user that was removed
	*/
	public static User removeByC_DU(long companyId, boolean defaultUser)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().removeByC_DU(companyId, defaultUser);
	}

	/**
	* Returns the number of users where companyId = &#63; and defaultUser = &#63;.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @return the number of matching users
	*/
	public static int countByC_DU(long companyId, boolean defaultUser) {
		return getPersistence().countByC_DU(companyId, defaultUser);
	}

	/**
	* Returns the user where companyId = &#63; and screenName = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	*
	* @param companyId the company ID
	* @param screenName the screen name
	* @return the matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByC_SN(long companyId, String screenName)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().findByC_SN(companyId, screenName);
	}

	/**
	* Returns the user where companyId = &#63; and screenName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param screenName the screen name
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_SN(long companyId, String screenName) {
		return getPersistence().fetchByC_SN(companyId, screenName);
	}

	/**
	* Returns the user where companyId = &#63; and screenName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param screenName the screen name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_SN(long companyId, String screenName,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_SN(companyId, screenName, retrieveFromCache);
	}

	/**
	* Removes the user where companyId = &#63; and screenName = &#63; from the database.
	*
	* @param companyId the company ID
	* @param screenName the screen name
	* @return the user that was removed
	*/
	public static User removeByC_SN(long companyId, String screenName)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().removeByC_SN(companyId, screenName);
	}

	/**
	* Returns the number of users where companyId = &#63; and screenName = &#63;.
	*
	* @param companyId the company ID
	* @param screenName the screen name
	* @return the number of matching users
	*/
	public static int countByC_SN(long companyId, String screenName) {
		return getPersistence().countByC_SN(companyId, screenName);
	}

	/**
	* Returns the user where companyId = &#63; and emailAddress = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	*
	* @param companyId the company ID
	* @param emailAddress the email address
	* @return the matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByC_EA(long companyId, String emailAddress)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().findByC_EA(companyId, emailAddress);
	}

	/**
	* Returns the user where companyId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param emailAddress the email address
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_EA(long companyId, String emailAddress) {
		return getPersistence().fetchByC_EA(companyId, emailAddress);
	}

	/**
	* Returns the user where companyId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param emailAddress the email address
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_EA(long companyId, String emailAddress,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_EA(companyId, emailAddress, retrieveFromCache);
	}

	/**
	* Removes the user where companyId = &#63; and emailAddress = &#63; from the database.
	*
	* @param companyId the company ID
	* @param emailAddress the email address
	* @return the user that was removed
	*/
	public static User removeByC_EA(long companyId, String emailAddress)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().removeByC_EA(companyId, emailAddress);
	}

	/**
	* Returns the number of users where companyId = &#63; and emailAddress = &#63;.
	*
	* @param companyId the company ID
	* @param emailAddress the email address
	* @return the number of matching users
	*/
	public static int countByC_EA(long companyId, String emailAddress) {
		return getPersistence().countByC_EA(companyId, emailAddress);
	}

	/**
	* Returns the user where companyId = &#63; and facebookId = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	*
	* @param companyId the company ID
	* @param facebookId the facebook ID
	* @return the matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByC_FID(long companyId, long facebookId)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().findByC_FID(companyId, facebookId);
	}

	/**
	* Returns the user where companyId = &#63; and facebookId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param facebookId the facebook ID
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_FID(long companyId, long facebookId) {
		return getPersistence().fetchByC_FID(companyId, facebookId);
	}

	/**
	* Returns the user where companyId = &#63; and facebookId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param facebookId the facebook ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_FID(long companyId, long facebookId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_FID(companyId, facebookId, retrieveFromCache);
	}

	/**
	* Removes the user where companyId = &#63; and facebookId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param facebookId the facebook ID
	* @return the user that was removed
	*/
	public static User removeByC_FID(long companyId, long facebookId)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().removeByC_FID(companyId, facebookId);
	}

	/**
	* Returns the number of users where companyId = &#63; and facebookId = &#63;.
	*
	* @param companyId the company ID
	* @param facebookId the facebook ID
	* @return the number of matching users
	*/
	public static int countByC_FID(long companyId, long facebookId) {
		return getPersistence().countByC_FID(companyId, facebookId);
	}

	/**
	* Returns the user where companyId = &#63; and googleUserId = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	*
	* @param companyId the company ID
	* @param googleUserId the google user ID
	* @return the matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByC_GUID(long companyId, String googleUserId)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().findByC_GUID(companyId, googleUserId);
	}

	/**
	* Returns the user where companyId = &#63; and googleUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param googleUserId the google user ID
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_GUID(long companyId, String googleUserId) {
		return getPersistence().fetchByC_GUID(companyId, googleUserId);
	}

	/**
	* Returns the user where companyId = &#63; and googleUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param googleUserId the google user ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_GUID(long companyId, String googleUserId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_GUID(companyId, googleUserId, retrieveFromCache);
	}

	/**
	* Removes the user where companyId = &#63; and googleUserId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param googleUserId the google user ID
	* @return the user that was removed
	*/
	public static User removeByC_GUID(long companyId, String googleUserId)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().removeByC_GUID(companyId, googleUserId);
	}

	/**
	* Returns the number of users where companyId = &#63; and googleUserId = &#63;.
	*
	* @param companyId the company ID
	* @param googleUserId the google user ID
	* @return the number of matching users
	*/
	public static int countByC_GUID(long companyId, String googleUserId) {
		return getPersistence().countByC_GUID(companyId, googleUserId);
	}

	/**
	* Returns the user where companyId = &#63; and openId = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	*
	* @param companyId the company ID
	* @param openId the open ID
	* @return the matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByC_O(long companyId, String openId)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().findByC_O(companyId, openId);
	}

	/**
	* Returns the user where companyId = &#63; and openId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param openId the open ID
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_O(long companyId, String openId) {
		return getPersistence().fetchByC_O(companyId, openId);
	}

	/**
	* Returns the user where companyId = &#63; and openId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param openId the open ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_O(long companyId, String openId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByC_O(companyId, openId, retrieveFromCache);
	}

	/**
	* Removes the user where companyId = &#63; and openId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param openId the open ID
	* @return the user that was removed
	*/
	public static User removeByC_O(long companyId, String openId)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().removeByC_O(companyId, openId);
	}

	/**
	* Returns the number of users where companyId = &#63; and openId = &#63;.
	*
	* @param companyId the company ID
	* @param openId the open ID
	* @return the number of matching users
	*/
	public static int countByC_O(long companyId, String openId) {
		return getPersistence().countByC_O(companyId, openId);
	}

	/**
	* Returns all the users where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching users
	*/
	public static List<User> findByC_S(long companyId, int status) {
		return getPersistence().findByC_S(companyId, status);
	}

	/**
	* Returns a range of all the users where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	*/
	public static List<User> findByC_S(long companyId, int status, int start,
		int end) {
		return getPersistence().findByC_S(companyId, status, start, end);
	}

	/**
	* Returns an ordered range of all the users where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	*/
	public static List<User> findByC_S(long companyId, int status, int start,
		int end, OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .findByC_S(companyId, status, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the users where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching users
	*/
	public static List<User> findByC_S(long companyId, int status, int start,
		int end, OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_S(companyId, status, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first user in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByC_S_First(long companyId, int status,
		OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByC_S_First(companyId, status, orderByComparator);
	}

	/**
	* Returns the first user in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_S_First(long companyId, int status,
		OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .fetchByC_S_First(companyId, status, orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByC_S_Last(long companyId, int status,
		OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByC_S_Last(companyId, status, orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_S_Last(long companyId, int status,
		OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .fetchByC_S_Last(companyId, status, orderByComparator);
	}

	/**
	* Returns the users before and after the current user in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param userId the primary key of the current user
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user
	* @throws NoSuchUserException if a user with the primary key could not be found
	*/
	public static User[] findByC_S_PrevAndNext(long userId, long companyId,
		int status, OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByC_S_PrevAndNext(userId, companyId, status,
			orderByComparator);
	}

	/**
	* Removes all the users where companyId = &#63; and status = &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public static void removeByC_S(long companyId, int status) {
		getPersistence().removeByC_S(companyId, status);
	}

	/**
	* Returns the number of users where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching users
	*/
	public static int countByC_S(long companyId, int status) {
		return getPersistence().countByC_S(companyId, status);
	}

	/**
	* Returns all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param modifiedDate the modified date
	* @return the matching users
	*/
	public static List<User> findByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate) {
		return getPersistence()
				   .findByC_CD_MD(companyId, createDate, modifiedDate);
	}

	/**
	* Returns a range of all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param modifiedDate the modified date
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	*/
	public static List<User> findByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate, int start, int end) {
		return getPersistence()
				   .findByC_CD_MD(companyId, createDate, modifiedDate, start,
			end);
	}

	/**
	* Returns an ordered range of all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param modifiedDate the modified date
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	*/
	public static List<User> findByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate, int start, int end,
		OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .findByC_CD_MD(companyId, createDate, modifiedDate, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param modifiedDate the modified date
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching users
	*/
	public static List<User> findByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate, int start, int end,
		OrderByComparator<User> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_CD_MD(companyId, createDate, modifiedDate, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first user in the ordered set where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByC_CD_MD_First(long companyId, Date createDate,
		Date modifiedDate, OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByC_CD_MD_First(companyId, createDate, modifiedDate,
			orderByComparator);
	}

	/**
	* Returns the first user in the ordered set where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_CD_MD_First(long companyId, Date createDate,
		Date modifiedDate, OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .fetchByC_CD_MD_First(companyId, createDate, modifiedDate,
			orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByC_CD_MD_Last(long companyId, Date createDate,
		Date modifiedDate, OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByC_CD_MD_Last(companyId, createDate, modifiedDate,
			orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_CD_MD_Last(long companyId, Date createDate,
		Date modifiedDate, OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .fetchByC_CD_MD_Last(companyId, createDate, modifiedDate,
			orderByComparator);
	}

	/**
	* Returns the users before and after the current user in the ordered set where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	*
	* @param userId the primary key of the current user
	* @param companyId the company ID
	* @param createDate the create date
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user
	* @throws NoSuchUserException if a user with the primary key could not be found
	*/
	public static User[] findByC_CD_MD_PrevAndNext(long userId, long companyId,
		Date createDate, Date modifiedDate,
		OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByC_CD_MD_PrevAndNext(userId, companyId, createDate,
			modifiedDate, orderByComparator);
	}

	/**
	* Removes all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63; from the database.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param modifiedDate the modified date
	*/
	public static void removeByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate) {
		getPersistence().removeByC_CD_MD(companyId, createDate, modifiedDate);
	}

	/**
	* Returns the number of users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param modifiedDate the modified date
	* @return the number of matching users
	*/
	public static int countByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate) {
		return getPersistence()
				   .countByC_CD_MD(companyId, createDate, modifiedDate);
	}

	/**
	* Returns all the users where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param status the status
	* @return the matching users
	*/
	public static List<User> findByC_DU_S(long companyId, boolean defaultUser,
		int status) {
		return getPersistence().findByC_DU_S(companyId, defaultUser, status);
	}

	/**
	* Returns a range of all the users where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param status the status
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	*/
	public static List<User> findByC_DU_S(long companyId, boolean defaultUser,
		int status, int start, int end) {
		return getPersistence()
				   .findByC_DU_S(companyId, defaultUser, status, start, end);
	}

	/**
	* Returns an ordered range of all the users where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param status the status
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	*/
	public static List<User> findByC_DU_S(long companyId, boolean defaultUser,
		int status, int start, int end,
		OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .findByC_DU_S(companyId, defaultUser, status, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the users where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param status the status
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching users
	*/
	public static List<User> findByC_DU_S(long companyId, boolean defaultUser,
		int status, int start, int end,
		OrderByComparator<User> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_DU_S(companyId, defaultUser, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first user in the ordered set where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByC_DU_S_First(long companyId, boolean defaultUser,
		int status, OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByC_DU_S_First(companyId, defaultUser, status,
			orderByComparator);
	}

	/**
	* Returns the first user in the ordered set where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_DU_S_First(long companyId, boolean defaultUser,
		int status, OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .fetchByC_DU_S_First(companyId, defaultUser, status,
			orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByC_DU_S_Last(long companyId, boolean defaultUser,
		int status, OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByC_DU_S_Last(companyId, defaultUser, status,
			orderByComparator);
	}

	/**
	* Returns the last user in the ordered set where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_DU_S_Last(long companyId, boolean defaultUser,
		int status, OrderByComparator<User> orderByComparator) {
		return getPersistence()
				   .fetchByC_DU_S_Last(companyId, defaultUser, status,
			orderByComparator);
	}

	/**
	* Returns the users before and after the current user in the ordered set where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	*
	* @param userId the primary key of the current user
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user
	* @throws NoSuchUserException if a user with the primary key could not be found
	*/
	public static User[] findByC_DU_S_PrevAndNext(long userId, long companyId,
		boolean defaultUser, int status,
		OrderByComparator<User> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence()
				   .findByC_DU_S_PrevAndNext(userId, companyId, defaultUser,
			status, orderByComparator);
	}

	/**
	* Removes all the users where companyId = &#63; and defaultUser = &#63; and status = &#63; from the database.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param status the status
	*/
	public static void removeByC_DU_S(long companyId, boolean defaultUser,
		int status) {
		getPersistence().removeByC_DU_S(companyId, defaultUser, status);
	}

	/**
	* Returns the number of users where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param status the status
	* @return the number of matching users
	*/
	public static int countByC_DU_S(long companyId, boolean defaultUser,
		int status) {
		return getPersistence().countByC_DU_S(companyId, defaultUser, status);
	}

	/**
	* Returns the user where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	*
	* @param companyId the company ID
	* @param externalReferenceCode the external reference code
	* @return the matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public static User findByC_ERC(long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().findByC_ERC(companyId, externalReferenceCode);
	}

	/**
	* Returns the user where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param externalReferenceCode the external reference code
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_ERC(long companyId, String externalReferenceCode) {
		return getPersistence().fetchByC_ERC(companyId, externalReferenceCode);
	}

	/**
	* Returns the user where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param externalReferenceCode the external reference code
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public static User fetchByC_ERC(long companyId,
		String externalReferenceCode, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_ERC(companyId, externalReferenceCode,
			retrieveFromCache);
	}

	/**
	* Removes the user where companyId = &#63; and externalReferenceCode = &#63; from the database.
	*
	* @param companyId the company ID
	* @param externalReferenceCode the external reference code
	* @return the user that was removed
	*/
	public static User removeByC_ERC(long companyId,
		String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().removeByC_ERC(companyId, externalReferenceCode);
	}

	/**
	* Returns the number of users where companyId = &#63; and externalReferenceCode = &#63;.
	*
	* @param companyId the company ID
	* @param externalReferenceCode the external reference code
	* @return the number of matching users
	*/
	public static int countByC_ERC(long companyId, String externalReferenceCode) {
		return getPersistence().countByC_ERC(companyId, externalReferenceCode);
	}

	/**
	* Caches the user in the entity cache if it is enabled.
	*
	* @param user the user
	*/
	public static void cacheResult(User user) {
		getPersistence().cacheResult(user);
	}

	/**
	* Caches the users in the entity cache if it is enabled.
	*
	* @param users the users
	*/
	public static void cacheResult(List<User> users) {
		getPersistence().cacheResult(users);
	}

	/**
	* Creates a new user with the primary key. Does not add the user to the database.
	*
	* @param userId the primary key for the new user
	* @return the new user
	*/
	public static User create(long userId) {
		return getPersistence().create(userId);
	}

	/**
	* Removes the user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userId the primary key of the user
	* @return the user that was removed
	* @throws NoSuchUserException if a user with the primary key could not be found
	*/
	public static User remove(long userId)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().remove(userId);
	}

	public static User updateImpl(User user) {
		return getPersistence().updateImpl(user);
	}

	/**
	* Returns the user with the primary key or throws a <code>NoSuchUserException</code> if it could not be found.
	*
	* @param userId the primary key of the user
	* @return the user
	* @throws NoSuchUserException if a user with the primary key could not be found
	*/
	public static User findByPrimaryKey(long userId)
		throws com.liferay.portal.kernel.exception.NoSuchUserException {
		return getPersistence().findByPrimaryKey(userId);
	}

	/**
	* Returns the user with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param userId the primary key of the user
	* @return the user, or <code>null</code> if a user with the primary key could not be found
	*/
	public static User fetchByPrimaryKey(long userId) {
		return getPersistence().fetchByPrimaryKey(userId);
	}

	/**
	* Returns all the users.
	*
	* @return the users
	*/
	public static List<User> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of users
	*/
	public static List<User> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of users
	*/
	public static List<User> findAll(int start, int end,
		OrderByComparator<User> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of users
	*/
	public static List<User> findAll(int start, int end,
		OrderByComparator<User> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the users from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of users.
	*
	* @return the number of users
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	* Returns the primaryKeys of groups associated with the user.
	*
	* @param pk the primary key of the user
	* @return long[] of the primaryKeys of groups associated with the user
	*/
	public static long[] getGroupPrimaryKeys(long pk) {
		return getPersistence().getGroupPrimaryKeys(pk);
	}

	/**
	* Returns all the groups associated with the user.
	*
	* @param pk the primary key of the user
	* @return the groups associated with the user
	*/
	public static List<com.liferay.portal.kernel.model.Group> getGroups(long pk) {
		return getPersistence().getGroups(pk);
	}

	/**
	* Returns a range of all the groups associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of groups associated with the user
	*/
	public static List<com.liferay.portal.kernel.model.Group> getGroups(
		long pk, int start, int end) {
		return getPersistence().getGroups(pk, start, end);
	}

	/**
	* Returns an ordered range of all the groups associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of groups associated with the user
	*/
	public static List<com.liferay.portal.kernel.model.Group> getGroups(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portal.kernel.model.Group> orderByComparator) {
		return getPersistence().getGroups(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of groups associated with the user.
	*
	* @param pk the primary key of the user
	* @return the number of groups associated with the user
	*/
	public static int getGroupsSize(long pk) {
		return getPersistence().getGroupsSize(pk);
	}

	/**
	* Returns <code>true</code> if the group is associated with the user.
	*
	* @param pk the primary key of the user
	* @param groupPK the primary key of the group
	* @return <code>true</code> if the group is associated with the user; <code>false</code> otherwise
	*/
	public static boolean containsGroup(long pk, long groupPK) {
		return getPersistence().containsGroup(pk, groupPK);
	}

	/**
	* Returns <code>true</code> if the user has any groups associated with it.
	*
	* @param pk the primary key of the user to check for associations with groups
	* @return <code>true</code> if the user has any groups associated with it; <code>false</code> otherwise
	*/
	public static boolean containsGroups(long pk) {
		return getPersistence().containsGroups(pk);
	}

	/**
	* Adds an association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groupPK the primary key of the group
	*/
	public static void addGroup(long pk, long groupPK) {
		getPersistence().addGroup(pk, groupPK);
	}

	/**
	* Adds an association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param group the group
	*/
	public static void addGroup(long pk,
		com.liferay.portal.kernel.model.Group group) {
		getPersistence().addGroup(pk, group);
	}

	/**
	* Adds an association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groupPKs the primary keys of the groups
	*/
	public static void addGroups(long pk, long[] groupPKs) {
		getPersistence().addGroups(pk, groupPKs);
	}

	/**
	* Adds an association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groups the groups
	*/
	public static void addGroups(long pk,
		List<com.liferay.portal.kernel.model.Group> groups) {
		getPersistence().addGroups(pk, groups);
	}

	/**
	* Clears all associations between the user and its groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user to clear the associated groups from
	*/
	public static void clearGroups(long pk) {
		getPersistence().clearGroups(pk);
	}

	/**
	* Removes the association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groupPK the primary key of the group
	*/
	public static void removeGroup(long pk, long groupPK) {
		getPersistence().removeGroup(pk, groupPK);
	}

	/**
	* Removes the association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param group the group
	*/
	public static void removeGroup(long pk,
		com.liferay.portal.kernel.model.Group group) {
		getPersistence().removeGroup(pk, group);
	}

	/**
	* Removes the association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groupPKs the primary keys of the groups
	*/
	public static void removeGroups(long pk, long[] groupPKs) {
		getPersistence().removeGroups(pk, groupPKs);
	}

	/**
	* Removes the association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groups the groups
	*/
	public static void removeGroups(long pk,
		List<com.liferay.portal.kernel.model.Group> groups) {
		getPersistence().removeGroups(pk, groups);
	}

	/**
	* Sets the groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groupPKs the primary keys of the groups to be associated with the user
	*/
	public static void setGroups(long pk, long[] groupPKs) {
		getPersistence().setGroups(pk, groupPKs);
	}

	/**
	* Sets the groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groups the groups to be associated with the user
	*/
	public static void setGroups(long pk,
		List<com.liferay.portal.kernel.model.Group> groups) {
		getPersistence().setGroups(pk, groups);
	}

	/**
	* Returns the primaryKeys of organizations associated with the user.
	*
	* @param pk the primary key of the user
	* @return long[] of the primaryKeys of organizations associated with the user
	*/
	public static long[] getOrganizationPrimaryKeys(long pk) {
		return getPersistence().getOrganizationPrimaryKeys(pk);
	}

	/**
	* Returns all the organizations associated with the user.
	*
	* @param pk the primary key of the user
	* @return the organizations associated with the user
	*/
	public static List<com.liferay.portal.kernel.model.Organization> getOrganizations(
		long pk) {
		return getPersistence().getOrganizations(pk);
	}

	/**
	* Returns a range of all the organizations associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of organizations associated with the user
	*/
	public static List<com.liferay.portal.kernel.model.Organization> getOrganizations(
		long pk, int start, int end) {
		return getPersistence().getOrganizations(pk, start, end);
	}

	/**
	* Returns an ordered range of all the organizations associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of organizations associated with the user
	*/
	public static List<com.liferay.portal.kernel.model.Organization> getOrganizations(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portal.kernel.model.Organization> orderByComparator) {
		return getPersistence()
				   .getOrganizations(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of organizations associated with the user.
	*
	* @param pk the primary key of the user
	* @return the number of organizations associated with the user
	*/
	public static int getOrganizationsSize(long pk) {
		return getPersistence().getOrganizationsSize(pk);
	}

	/**
	* Returns <code>true</code> if the organization is associated with the user.
	*
	* @param pk the primary key of the user
	* @param organizationPK the primary key of the organization
	* @return <code>true</code> if the organization is associated with the user; <code>false</code> otherwise
	*/
	public static boolean containsOrganization(long pk, long organizationPK) {
		return getPersistence().containsOrganization(pk, organizationPK);
	}

	/**
	* Returns <code>true</code> if the user has any organizations associated with it.
	*
	* @param pk the primary key of the user to check for associations with organizations
	* @return <code>true</code> if the user has any organizations associated with it; <code>false</code> otherwise
	*/
	public static boolean containsOrganizations(long pk) {
		return getPersistence().containsOrganizations(pk);
	}

	/**
	* Adds an association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizationPK the primary key of the organization
	*/
	public static void addOrganization(long pk, long organizationPK) {
		getPersistence().addOrganization(pk, organizationPK);
	}

	/**
	* Adds an association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organization the organization
	*/
	public static void addOrganization(long pk,
		com.liferay.portal.kernel.model.Organization organization) {
		getPersistence().addOrganization(pk, organization);
	}

	/**
	* Adds an association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizationPKs the primary keys of the organizations
	*/
	public static void addOrganizations(long pk, long[] organizationPKs) {
		getPersistence().addOrganizations(pk, organizationPKs);
	}

	/**
	* Adds an association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizations the organizations
	*/
	public static void addOrganizations(long pk,
		List<com.liferay.portal.kernel.model.Organization> organizations) {
		getPersistence().addOrganizations(pk, organizations);
	}

	/**
	* Clears all associations between the user and its organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user to clear the associated organizations from
	*/
	public static void clearOrganizations(long pk) {
		getPersistence().clearOrganizations(pk);
	}

	/**
	* Removes the association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizationPK the primary key of the organization
	*/
	public static void removeOrganization(long pk, long organizationPK) {
		getPersistence().removeOrganization(pk, organizationPK);
	}

	/**
	* Removes the association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organization the organization
	*/
	public static void removeOrganization(long pk,
		com.liferay.portal.kernel.model.Organization organization) {
		getPersistence().removeOrganization(pk, organization);
	}

	/**
	* Removes the association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizationPKs the primary keys of the organizations
	*/
	public static void removeOrganizations(long pk, long[] organizationPKs) {
		getPersistence().removeOrganizations(pk, organizationPKs);
	}

	/**
	* Removes the association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizations the organizations
	*/
	public static void removeOrganizations(long pk,
		List<com.liferay.portal.kernel.model.Organization> organizations) {
		getPersistence().removeOrganizations(pk, organizations);
	}

	/**
	* Sets the organizations associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizationPKs the primary keys of the organizations to be associated with the user
	*/
	public static void setOrganizations(long pk, long[] organizationPKs) {
		getPersistence().setOrganizations(pk, organizationPKs);
	}

	/**
	* Sets the organizations associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizations the organizations to be associated with the user
	*/
	public static void setOrganizations(long pk,
		List<com.liferay.portal.kernel.model.Organization> organizations) {
		getPersistence().setOrganizations(pk, organizations);
	}

	/**
	* Returns the primaryKeys of roles associated with the user.
	*
	* @param pk the primary key of the user
	* @return long[] of the primaryKeys of roles associated with the user
	*/
	public static long[] getRolePrimaryKeys(long pk) {
		return getPersistence().getRolePrimaryKeys(pk);
	}

	/**
	* Returns all the roles associated with the user.
	*
	* @param pk the primary key of the user
	* @return the roles associated with the user
	*/
	public static List<com.liferay.portal.kernel.model.Role> getRoles(long pk) {
		return getPersistence().getRoles(pk);
	}

	/**
	* Returns a range of all the roles associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of roles associated with the user
	*/
	public static List<com.liferay.portal.kernel.model.Role> getRoles(long pk,
		int start, int end) {
		return getPersistence().getRoles(pk, start, end);
	}

	/**
	* Returns an ordered range of all the roles associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of roles associated with the user
	*/
	public static List<com.liferay.portal.kernel.model.Role> getRoles(long pk,
		int start, int end,
		OrderByComparator<com.liferay.portal.kernel.model.Role> orderByComparator) {
		return getPersistence().getRoles(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of roles associated with the user.
	*
	* @param pk the primary key of the user
	* @return the number of roles associated with the user
	*/
	public static int getRolesSize(long pk) {
		return getPersistence().getRolesSize(pk);
	}

	/**
	* Returns <code>true</code> if the role is associated with the user.
	*
	* @param pk the primary key of the user
	* @param rolePK the primary key of the role
	* @return <code>true</code> if the role is associated with the user; <code>false</code> otherwise
	*/
	public static boolean containsRole(long pk, long rolePK) {
		return getPersistence().containsRole(pk, rolePK);
	}

	/**
	* Returns <code>true</code> if the user has any roles associated with it.
	*
	* @param pk the primary key of the user to check for associations with roles
	* @return <code>true</code> if the user has any roles associated with it; <code>false</code> otherwise
	*/
	public static boolean containsRoles(long pk) {
		return getPersistence().containsRoles(pk);
	}

	/**
	* Adds an association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param rolePK the primary key of the role
	*/
	public static void addRole(long pk, long rolePK) {
		getPersistence().addRole(pk, rolePK);
	}

	/**
	* Adds an association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param role the role
	*/
	public static void addRole(long pk,
		com.liferay.portal.kernel.model.Role role) {
		getPersistence().addRole(pk, role);
	}

	/**
	* Adds an association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param rolePKs the primary keys of the roles
	*/
	public static void addRoles(long pk, long[] rolePKs) {
		getPersistence().addRoles(pk, rolePKs);
	}

	/**
	* Adds an association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param roles the roles
	*/
	public static void addRoles(long pk,
		List<com.liferay.portal.kernel.model.Role> roles) {
		getPersistence().addRoles(pk, roles);
	}

	/**
	* Clears all associations between the user and its roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user to clear the associated roles from
	*/
	public static void clearRoles(long pk) {
		getPersistence().clearRoles(pk);
	}

	/**
	* Removes the association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param rolePK the primary key of the role
	*/
	public static void removeRole(long pk, long rolePK) {
		getPersistence().removeRole(pk, rolePK);
	}

	/**
	* Removes the association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param role the role
	*/
	public static void removeRole(long pk,
		com.liferay.portal.kernel.model.Role role) {
		getPersistence().removeRole(pk, role);
	}

	/**
	* Removes the association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param rolePKs the primary keys of the roles
	*/
	public static void removeRoles(long pk, long[] rolePKs) {
		getPersistence().removeRoles(pk, rolePKs);
	}

	/**
	* Removes the association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param roles the roles
	*/
	public static void removeRoles(long pk,
		List<com.liferay.portal.kernel.model.Role> roles) {
		getPersistence().removeRoles(pk, roles);
	}

	/**
	* Sets the roles associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param rolePKs the primary keys of the roles to be associated with the user
	*/
	public static void setRoles(long pk, long[] rolePKs) {
		getPersistence().setRoles(pk, rolePKs);
	}

	/**
	* Sets the roles associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param roles the roles to be associated with the user
	*/
	public static void setRoles(long pk,
		List<com.liferay.portal.kernel.model.Role> roles) {
		getPersistence().setRoles(pk, roles);
	}

	/**
	* Returns the primaryKeys of teams associated with the user.
	*
	* @param pk the primary key of the user
	* @return long[] of the primaryKeys of teams associated with the user
	*/
	public static long[] getTeamPrimaryKeys(long pk) {
		return getPersistence().getTeamPrimaryKeys(pk);
	}

	/**
	* Returns all the teams associated with the user.
	*
	* @param pk the primary key of the user
	* @return the teams associated with the user
	*/
	public static List<com.liferay.portal.kernel.model.Team> getTeams(long pk) {
		return getPersistence().getTeams(pk);
	}

	/**
	* Returns a range of all the teams associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of teams associated with the user
	*/
	public static List<com.liferay.portal.kernel.model.Team> getTeams(long pk,
		int start, int end) {
		return getPersistence().getTeams(pk, start, end);
	}

	/**
	* Returns an ordered range of all the teams associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of teams associated with the user
	*/
	public static List<com.liferay.portal.kernel.model.Team> getTeams(long pk,
		int start, int end,
		OrderByComparator<com.liferay.portal.kernel.model.Team> orderByComparator) {
		return getPersistence().getTeams(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of teams associated with the user.
	*
	* @param pk the primary key of the user
	* @return the number of teams associated with the user
	*/
	public static int getTeamsSize(long pk) {
		return getPersistence().getTeamsSize(pk);
	}

	/**
	* Returns <code>true</code> if the team is associated with the user.
	*
	* @param pk the primary key of the user
	* @param teamPK the primary key of the team
	* @return <code>true</code> if the team is associated with the user; <code>false</code> otherwise
	*/
	public static boolean containsTeam(long pk, long teamPK) {
		return getPersistence().containsTeam(pk, teamPK);
	}

	/**
	* Returns <code>true</code> if the user has any teams associated with it.
	*
	* @param pk the primary key of the user to check for associations with teams
	* @return <code>true</code> if the user has any teams associated with it; <code>false</code> otherwise
	*/
	public static boolean containsTeams(long pk) {
		return getPersistence().containsTeams(pk);
	}

	/**
	* Adds an association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teamPK the primary key of the team
	*/
	public static void addTeam(long pk, long teamPK) {
		getPersistence().addTeam(pk, teamPK);
	}

	/**
	* Adds an association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param team the team
	*/
	public static void addTeam(long pk,
		com.liferay.portal.kernel.model.Team team) {
		getPersistence().addTeam(pk, team);
	}

	/**
	* Adds an association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teamPKs the primary keys of the teams
	*/
	public static void addTeams(long pk, long[] teamPKs) {
		getPersistence().addTeams(pk, teamPKs);
	}

	/**
	* Adds an association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teams the teams
	*/
	public static void addTeams(long pk,
		List<com.liferay.portal.kernel.model.Team> teams) {
		getPersistence().addTeams(pk, teams);
	}

	/**
	* Clears all associations between the user and its teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user to clear the associated teams from
	*/
	public static void clearTeams(long pk) {
		getPersistence().clearTeams(pk);
	}

	/**
	* Removes the association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teamPK the primary key of the team
	*/
	public static void removeTeam(long pk, long teamPK) {
		getPersistence().removeTeam(pk, teamPK);
	}

	/**
	* Removes the association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param team the team
	*/
	public static void removeTeam(long pk,
		com.liferay.portal.kernel.model.Team team) {
		getPersistence().removeTeam(pk, team);
	}

	/**
	* Removes the association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teamPKs the primary keys of the teams
	*/
	public static void removeTeams(long pk, long[] teamPKs) {
		getPersistence().removeTeams(pk, teamPKs);
	}

	/**
	* Removes the association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teams the teams
	*/
	public static void removeTeams(long pk,
		List<com.liferay.portal.kernel.model.Team> teams) {
		getPersistence().removeTeams(pk, teams);
	}

	/**
	* Sets the teams associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teamPKs the primary keys of the teams to be associated with the user
	*/
	public static void setTeams(long pk, long[] teamPKs) {
		getPersistence().setTeams(pk, teamPKs);
	}

	/**
	* Sets the teams associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teams the teams to be associated with the user
	*/
	public static void setTeams(long pk,
		List<com.liferay.portal.kernel.model.Team> teams) {
		getPersistence().setTeams(pk, teams);
	}

	/**
	* Returns the primaryKeys of user groups associated with the user.
	*
	* @param pk the primary key of the user
	* @return long[] of the primaryKeys of user groups associated with the user
	*/
	public static long[] getUserGroupPrimaryKeys(long pk) {
		return getPersistence().getUserGroupPrimaryKeys(pk);
	}

	/**
	* Returns all the user groups associated with the user.
	*
	* @param pk the primary key of the user
	* @return the user groups associated with the user
	*/
	public static List<com.liferay.portal.kernel.model.UserGroup> getUserGroups(
		long pk) {
		return getPersistence().getUserGroups(pk);
	}

	/**
	* Returns a range of all the user groups associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of user groups associated with the user
	*/
	public static List<com.liferay.portal.kernel.model.UserGroup> getUserGroups(
		long pk, int start, int end) {
		return getPersistence().getUserGroups(pk, start, end);
	}

	/**
	* Returns an ordered range of all the user groups associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>UserModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of user groups associated with the user
	*/
	public static List<com.liferay.portal.kernel.model.UserGroup> getUserGroups(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portal.kernel.model.UserGroup> orderByComparator) {
		return getPersistence().getUserGroups(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of user groups associated with the user.
	*
	* @param pk the primary key of the user
	* @return the number of user groups associated with the user
	*/
	public static int getUserGroupsSize(long pk) {
		return getPersistence().getUserGroupsSize(pk);
	}

	/**
	* Returns <code>true</code> if the user group is associated with the user.
	*
	* @param pk the primary key of the user
	* @param userGroupPK the primary key of the user group
	* @return <code>true</code> if the user group is associated with the user; <code>false</code> otherwise
	*/
	public static boolean containsUserGroup(long pk, long userGroupPK) {
		return getPersistence().containsUserGroup(pk, userGroupPK);
	}

	/**
	* Returns <code>true</code> if the user has any user groups associated with it.
	*
	* @param pk the primary key of the user to check for associations with user groups
	* @return <code>true</code> if the user has any user groups associated with it; <code>false</code> otherwise
	*/
	public static boolean containsUserGroups(long pk) {
		return getPersistence().containsUserGroups(pk);
	}

	/**
	* Adds an association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroupPK the primary key of the user group
	*/
	public static void addUserGroup(long pk, long userGroupPK) {
		getPersistence().addUserGroup(pk, userGroupPK);
	}

	/**
	* Adds an association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroup the user group
	*/
	public static void addUserGroup(long pk,
		com.liferay.portal.kernel.model.UserGroup userGroup) {
		getPersistence().addUserGroup(pk, userGroup);
	}

	/**
	* Adds an association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroupPKs the primary keys of the user groups
	*/
	public static void addUserGroups(long pk, long[] userGroupPKs) {
		getPersistence().addUserGroups(pk, userGroupPKs);
	}

	/**
	* Adds an association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroups the user groups
	*/
	public static void addUserGroups(long pk,
		List<com.liferay.portal.kernel.model.UserGroup> userGroups) {
		getPersistence().addUserGroups(pk, userGroups);
	}

	/**
	* Clears all associations between the user and its user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user to clear the associated user groups from
	*/
	public static void clearUserGroups(long pk) {
		getPersistence().clearUserGroups(pk);
	}

	/**
	* Removes the association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroupPK the primary key of the user group
	*/
	public static void removeUserGroup(long pk, long userGroupPK) {
		getPersistence().removeUserGroup(pk, userGroupPK);
	}

	/**
	* Removes the association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroup the user group
	*/
	public static void removeUserGroup(long pk,
		com.liferay.portal.kernel.model.UserGroup userGroup) {
		getPersistence().removeUserGroup(pk, userGroup);
	}

	/**
	* Removes the association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroupPKs the primary keys of the user groups
	*/
	public static void removeUserGroups(long pk, long[] userGroupPKs) {
		getPersistence().removeUserGroups(pk, userGroupPKs);
	}

	/**
	* Removes the association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroups the user groups
	*/
	public static void removeUserGroups(long pk,
		List<com.liferay.portal.kernel.model.UserGroup> userGroups) {
		getPersistence().removeUserGroups(pk, userGroups);
	}

	/**
	* Sets the user groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroupPKs the primary keys of the user groups to be associated with the user
	*/
	public static void setUserGroups(long pk, long[] userGroupPKs) {
		getPersistence().setUserGroups(pk, userGroupPKs);
	}

	/**
	* Sets the user groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroups the user groups to be associated with the user
	*/
	public static void setUserGroups(long pk,
		List<com.liferay.portal.kernel.model.UserGroup> userGroups) {
		getPersistence().setUserGroups(pk, userGroups);
	}

	public static UserPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (UserPersistence)PortalBeanLocatorUtil.locate(UserPersistence.class.getName());

			ReferenceRegistry.registerReference(UserUtil.class, "_persistence");
		}

		return _persistence;
	}

	private static UserPersistence _persistence;
}