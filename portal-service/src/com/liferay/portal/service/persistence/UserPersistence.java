/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.User;

/**
 * The persistence interface for the user service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserPersistenceImpl
 * @see UserUtil
 * @generated
 */
public interface UserPersistence extends BasePersistence<User> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserUtil} to access the user persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the user in the entity cache if it is enabled.
	*
	* @param user the user
	*/
	public void cacheResult(com.liferay.portal.model.User user);

	/**
	* Caches the users in the entity cache if it is enabled.
	*
	* @param users the users
	*/
	public void cacheResult(java.util.List<com.liferay.portal.model.User> users);

	/**
	* Creates a new user with the primary key. Does not add the user to the database.
	*
	* @param userId the primary key for the new user
	* @return the new user
	*/
	public com.liferay.portal.model.User create(long userId);

	/**
	* Removes the user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userId the primary key of the user
	* @return the user that was removed
	* @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User remove(long userId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.User updateImpl(
		com.liferay.portal.model.User user, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user with the primary key or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	*
	* @param userId the primary key of the user
	* @return the user
	* @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User findByPrimaryKey(long userId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param userId the primary key of the user
	* @return the user, or <code>null</code> if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchByPrimaryKey(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the users where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the users where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the users where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first user in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last user in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the users before and after the current user in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the primary key of the current user
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user
	* @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User[] findByUuid_PrevAndNext(long userId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the users where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the users where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the users where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first user in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last user in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the users before and after the current user in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the primary key of the current user
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user
	* @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User[] findByCompanyId_PrevAndNext(
		long userId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where contactId = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	*
	* @param contactId the contact ID
	* @return the matching user
	* @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User findByContactId(long contactId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where contactId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param contactId the contact ID
	* @return the matching user, or <code>null</code> if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchByContactId(long contactId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where contactId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param contactId the contact ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchByContactId(long contactId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the users where emailAddress = &#63;.
	*
	* @param emailAddress the email address
	* @return the matching users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> findByEmailAddress(
		java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the users where emailAddress = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param emailAddress the email address
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> findByEmailAddress(
		java.lang.String emailAddress, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the users where emailAddress = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param emailAddress the email address
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> findByEmailAddress(
		java.lang.String emailAddress, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first user in the ordered set where emailAddress = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param emailAddress the email address
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User findByEmailAddress_First(
		java.lang.String emailAddress,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last user in the ordered set where emailAddress = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param emailAddress the email address
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User findByEmailAddress_Last(
		java.lang.String emailAddress,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the users before and after the current user in the ordered set where emailAddress = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the primary key of the current user
	* @param emailAddress the email address
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user
	* @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User[] findByEmailAddress_PrevAndNext(
		long userId, java.lang.String emailAddress,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where portraitId = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	*
	* @param portraitId the portrait ID
	* @return the matching user
	* @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User findByPortraitId(long portraitId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where portraitId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param portraitId the portrait ID
	* @return the matching user, or <code>null</code> if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchByPortraitId(long portraitId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where portraitId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param portraitId the portrait ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchByPortraitId(long portraitId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where companyId = &#63; and userId = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @return the matching user
	* @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User findByC_U(long companyId, long userId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @return the matching user, or <code>null</code> if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchByC_U(long companyId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchByC_U(long companyId,
		long userId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where companyId = &#63; and defaultUser = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @return the matching user
	* @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User findByC_DU(long companyId,
		boolean defaultUser)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where companyId = &#63; and defaultUser = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @return the matching user, or <code>null</code> if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchByC_DU(long companyId,
		boolean defaultUser)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where companyId = &#63; and defaultUser = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchByC_DU(long companyId,
		boolean defaultUser, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where companyId = &#63; and screenName = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	*
	* @param companyId the company ID
	* @param screenName the screen name
	* @return the matching user
	* @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User findByC_SN(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where companyId = &#63; and screenName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param screenName the screen name
	* @return the matching user, or <code>null</code> if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchByC_SN(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where companyId = &#63; and screenName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param screenName the screen name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchByC_SN(long companyId,
		java.lang.String screenName, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where companyId = &#63; and emailAddress = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	*
	* @param companyId the company ID
	* @param emailAddress the email address
	* @return the matching user
	* @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User findByC_EA(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where companyId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param emailAddress the email address
	* @return the matching user, or <code>null</code> if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchByC_EA(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where companyId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param emailAddress the email address
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchByC_EA(long companyId,
		java.lang.String emailAddress, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where companyId = &#63; and facebookId = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	*
	* @param companyId the company ID
	* @param facebookId the facebook ID
	* @return the matching user
	* @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User findByC_FID(long companyId,
		long facebookId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where companyId = &#63; and facebookId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param facebookId the facebook ID
	* @return the matching user, or <code>null</code> if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchByC_FID(long companyId,
		long facebookId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where companyId = &#63; and facebookId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param facebookId the facebook ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchByC_FID(long companyId,
		long facebookId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where companyId = &#63; and openId = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	*
	* @param companyId the company ID
	* @param openId the open ID
	* @return the matching user
	* @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User findByC_O(long companyId,
		java.lang.String openId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where companyId = &#63; and openId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param openId the open ID
	* @return the matching user, or <code>null</code> if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchByC_O(long companyId,
		java.lang.String openId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user where companyId = &#63; and openId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param openId the open ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User fetchByC_O(long companyId,
		java.lang.String openId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the users where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> findByC_S(
		long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the users where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> findByC_S(
		long companyId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the users where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> findByC_S(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first user in the ordered set where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User findByC_S_First(long companyId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last user in the ordered set where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User findByC_S_Last(long companyId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the users before and after the current user in the ordered set where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the primary key of the current user
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user
	* @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.User[] findByC_S_PrevAndNext(long userId,
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the users.
	*
	* @return the users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.User> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the users where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the users where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the user where contactId = &#63; from the database.
	*
	* @param contactId the contact ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByContactId(long contactId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the users where emailAddress = &#63; from the database.
	*
	* @param emailAddress the email address
	* @throws SystemException if a system exception occurred
	*/
	public void removeByEmailAddress(java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the user where portraitId = &#63; from the database.
	*
	* @param portraitId the portrait ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByPortraitId(long portraitId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the user where companyId = &#63; and userId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_U(long companyId, long userId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the user where companyId = &#63; and defaultUser = &#63; from the database.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_DU(long companyId, boolean defaultUser)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the user where companyId = &#63; and screenName = &#63; from the database.
	*
	* @param companyId the company ID
	* @param screenName the screen name
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_SN(long companyId, java.lang.String screenName)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the user where companyId = &#63; and emailAddress = &#63; from the database.
	*
	* @param companyId the company ID
	* @param emailAddress the email address
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_EA(long companyId, java.lang.String emailAddress)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the user where companyId = &#63; and facebookId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param facebookId the facebook ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_FID(long companyId, long facebookId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the user where companyId = &#63; and openId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param openId the open ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_O(long companyId, java.lang.String openId)
		throws com.liferay.portal.NoSuchUserException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the users where companyId = &#63; and status = &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_S(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the users from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of users where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching users
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of users where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching users
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of users where contactId = &#63;.
	*
	* @param contactId the contact ID
	* @return the number of matching users
	* @throws SystemException if a system exception occurred
	*/
	public int countByContactId(long contactId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of users where emailAddress = &#63;.
	*
	* @param emailAddress the email address
	* @return the number of matching users
	* @throws SystemException if a system exception occurred
	*/
	public int countByEmailAddress(java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of users where portraitId = &#63;.
	*
	* @param portraitId the portrait ID
	* @return the number of matching users
	* @throws SystemException if a system exception occurred
	*/
	public int countByPortraitId(long portraitId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of users where companyId = &#63; and userId = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @return the number of matching users
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_U(long companyId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of users where companyId = &#63; and defaultUser = &#63;.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @return the number of matching users
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_DU(long companyId, boolean defaultUser)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of users where companyId = &#63; and screenName = &#63;.
	*
	* @param companyId the company ID
	* @param screenName the screen name
	* @return the number of matching users
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_SN(long companyId, java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of users where companyId = &#63; and emailAddress = &#63;.
	*
	* @param companyId the company ID
	* @param emailAddress the email address
	* @return the number of matching users
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_EA(long companyId, java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of users where companyId = &#63; and facebookId = &#63;.
	*
	* @param companyId the company ID
	* @param facebookId the facebook ID
	* @return the number of matching users
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_FID(long companyId, long facebookId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of users where companyId = &#63; and openId = &#63;.
	*
	* @param companyId the company ID
	* @param openId the open ID
	* @return the number of matching users
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_O(long companyId, java.lang.String openId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of users where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching users
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_S(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of users.
	*
	* @return the number of users
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the groups associated with the user.
	*
	* @param pk the primary key of the user
	* @return the groups associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the groups associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of groups associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the groups associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of groups associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of groups associated with the user.
	*
	* @param pk the primary key of the user
	* @return the number of groups associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public int getGroupsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the group is associated with the user.
	*
	* @param pk the primary key of the user
	* @param groupPK the primary key of the group
	* @return <code>true</code> if the group is associated with the user; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the user has any groups associated with it.
	*
	* @param pk the primary key of the user to check for associations with groups
	* @return <code>true</code> if the user has any groups associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groupPK the primary key of the group
	* @throws SystemException if a system exception occurred
	*/
	public void addGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param group the group
	* @throws SystemException if a system exception occurred
	*/
	public void addGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groupPKs the primary keys of the groups
	* @throws SystemException if a system exception occurred
	*/
	public void addGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groups the groups
	* @throws SystemException if a system exception occurred
	*/
	public void addGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Clears all associations between the user and its groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user to clear the associated groups from
	* @throws SystemException if a system exception occurred
	*/
	public void clearGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groupPK the primary key of the group
	* @throws SystemException if a system exception occurred
	*/
	public void removeGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param group the group
	* @throws SystemException if a system exception occurred
	*/
	public void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groupPKs the primary keys of the groups
	* @throws SystemException if a system exception occurred
	*/
	public void removeGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groups the groups
	* @throws SystemException if a system exception occurred
	*/
	public void removeGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groupPKs the primary keys of the groups to be associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public void setGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groups the groups to be associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public void setGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the organizations associated with the user.
	*
	* @param pk the primary key of the user
	* @return the organizations associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long pk) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the organizations associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of organizations associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the organizations associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of organizations associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of organizations associated with the user.
	*
	* @param pk the primary key of the user
	* @return the number of organizations associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public int getOrganizationsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the organization is associated with the user.
	*
	* @param pk the primary key of the user
	* @param organizationPK the primary key of the organization
	* @return <code>true</code> if the organization is associated with the user; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsOrganization(long pk, long organizationPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the user has any organizations associated with it.
	*
	* @param pk the primary key of the user to check for associations with organizations
	* @return <code>true</code> if the user has any organizations associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsOrganizations(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizationPK the primary key of the organization
	* @throws SystemException if a system exception occurred
	*/
	public void addOrganization(long pk, long organizationPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organization the organization
	* @throws SystemException if a system exception occurred
	*/
	public void addOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizationPKs the primary keys of the organizations
	* @throws SystemException if a system exception occurred
	*/
	public void addOrganizations(long pk, long[] organizationPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizations the organizations
	* @throws SystemException if a system exception occurred
	*/
	public void addOrganizations(long pk,
		java.util.List<com.liferay.portal.model.Organization> organizations)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Clears all associations between the user and its organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user to clear the associated organizations from
	* @throws SystemException if a system exception occurred
	*/
	public void clearOrganizations(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizationPK the primary key of the organization
	* @throws SystemException if a system exception occurred
	*/
	public void removeOrganization(long pk, long organizationPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organization the organization
	* @throws SystemException if a system exception occurred
	*/
	public void removeOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizationPKs the primary keys of the organizations
	* @throws SystemException if a system exception occurred
	*/
	public void removeOrganizations(long pk, long[] organizationPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizations the organizations
	* @throws SystemException if a system exception occurred
	*/
	public void removeOrganizations(long pk,
		java.util.List<com.liferay.portal.model.Organization> organizations)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the organizations associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizationPKs the primary keys of the organizations to be associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public void setOrganizations(long pk, long[] organizationPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the organizations associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizations the organizations to be associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public void setOrganizations(long pk,
		java.util.List<com.liferay.portal.model.Organization> organizations)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the roles associated with the user.
	*
	* @param pk the primary key of the user
	* @return the roles associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Role> getRoles(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the roles associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of roles associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Role> getRoles(long pk,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the roles associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of roles associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Role> getRoles(long pk,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of roles associated with the user.
	*
	* @param pk the primary key of the user
	* @return the number of roles associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public int getRolesSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the role is associated with the user.
	*
	* @param pk the primary key of the user
	* @param rolePK the primary key of the role
	* @return <code>true</code> if the role is associated with the user; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsRole(long pk, long rolePK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the user has any roles associated with it.
	*
	* @param pk the primary key of the user to check for associations with roles
	* @return <code>true</code> if the user has any roles associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsRoles(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param rolePK the primary key of the role
	* @throws SystemException if a system exception occurred
	*/
	public void addRole(long pk, long rolePK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param role the role
	* @throws SystemException if a system exception occurred
	*/
	public void addRole(long pk, com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param rolePKs the primary keys of the roles
	* @throws SystemException if a system exception occurred
	*/
	public void addRoles(long pk, long[] rolePKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param roles the roles
	* @throws SystemException if a system exception occurred
	*/
	public void addRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Clears all associations between the user and its roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user to clear the associated roles from
	* @throws SystemException if a system exception occurred
	*/
	public void clearRoles(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param rolePK the primary key of the role
	* @throws SystemException if a system exception occurred
	*/
	public void removeRole(long pk, long rolePK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param role the role
	* @throws SystemException if a system exception occurred
	*/
	public void removeRole(long pk, com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param rolePKs the primary keys of the roles
	* @throws SystemException if a system exception occurred
	*/
	public void removeRoles(long pk, long[] rolePKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param roles the roles
	* @throws SystemException if a system exception occurred
	*/
	public void removeRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the roles associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param rolePKs the primary keys of the roles to be associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public void setRoles(long pk, long[] rolePKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the roles associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param roles the roles to be associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public void setRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the teams associated with the user.
	*
	* @param pk the primary key of the user
	* @return the teams associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Team> getTeams(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the teams associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of teams associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Team> getTeams(long pk,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the teams associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of teams associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Team> getTeams(long pk,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of teams associated with the user.
	*
	* @param pk the primary key of the user
	* @return the number of teams associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public int getTeamsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the team is associated with the user.
	*
	* @param pk the primary key of the user
	* @param teamPK the primary key of the team
	* @return <code>true</code> if the team is associated with the user; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsTeam(long pk, long teamPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the user has any teams associated with it.
	*
	* @param pk the primary key of the user to check for associations with teams
	* @return <code>true</code> if the user has any teams associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsTeams(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teamPK the primary key of the team
	* @throws SystemException if a system exception occurred
	*/
	public void addTeam(long pk, long teamPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param team the team
	* @throws SystemException if a system exception occurred
	*/
	public void addTeam(long pk, com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teamPKs the primary keys of the teams
	* @throws SystemException if a system exception occurred
	*/
	public void addTeams(long pk, long[] teamPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teams the teams
	* @throws SystemException if a system exception occurred
	*/
	public void addTeams(long pk,
		java.util.List<com.liferay.portal.model.Team> teams)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Clears all associations between the user and its teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user to clear the associated teams from
	* @throws SystemException if a system exception occurred
	*/
	public void clearTeams(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teamPK the primary key of the team
	* @throws SystemException if a system exception occurred
	*/
	public void removeTeam(long pk, long teamPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param team the team
	* @throws SystemException if a system exception occurred
	*/
	public void removeTeam(long pk, com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teamPKs the primary keys of the teams
	* @throws SystemException if a system exception occurred
	*/
	public void removeTeams(long pk, long[] teamPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teams the teams
	* @throws SystemException if a system exception occurred
	*/
	public void removeTeams(long pk,
		java.util.List<com.liferay.portal.model.Team> teams)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the teams associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teamPKs the primary keys of the teams to be associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public void setTeams(long pk, long[] teamPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the teams associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teams the teams to be associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public void setTeams(long pk,
		java.util.List<com.liferay.portal.model.Team> teams)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the user groups associated with the user.
	*
	* @param pk the primary key of the user
	* @return the user groups associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the user groups associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of user groups associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the user groups associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of user groups associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of user groups associated with the user.
	*
	* @param pk the primary key of the user
	* @return the number of user groups associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public int getUserGroupsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the user group is associated with the user.
	*
	* @param pk the primary key of the user
	* @param userGroupPK the primary key of the user group
	* @return <code>true</code> if the user group is associated with the user; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsUserGroup(long pk, long userGroupPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns <code>true</code> if the user has any user groups associated with it.
	*
	* @param pk the primary key of the user to check for associations with user groups
	* @return <code>true</code> if the user has any user groups associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean containsUserGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroupPK the primary key of the user group
	* @throws SystemException if a system exception occurred
	*/
	public void addUserGroup(long pk, long userGroupPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroup the user group
	* @throws SystemException if a system exception occurred
	*/
	public void addUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroupPKs the primary keys of the user groups
	* @throws SystemException if a system exception occurred
	*/
	public void addUserGroups(long pk, long[] userGroupPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Adds an association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroups the user groups
	* @throws SystemException if a system exception occurred
	*/
	public void addUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Clears all associations between the user and its user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user to clear the associated user groups from
	* @throws SystemException if a system exception occurred
	*/
	public void clearUserGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroupPK the primary key of the user group
	* @throws SystemException if a system exception occurred
	*/
	public void removeUserGroup(long pk, long userGroupPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroup the user group
	* @throws SystemException if a system exception occurred
	*/
	public void removeUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroupPKs the primary keys of the user groups
	* @throws SystemException if a system exception occurred
	*/
	public void removeUserGroups(long pk, long[] userGroupPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroups the user groups
	* @throws SystemException if a system exception occurred
	*/
	public void removeUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the user groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroupPKs the primary keys of the user groups to be associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public void setUserGroups(long pk, long[] userGroupPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the user groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroups the user groups to be associated with the user
	* @throws SystemException if a system exception occurred
	*/
	public void setUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups)
		throws com.liferay.portal.kernel.exception.SystemException;
}