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

import com.liferay.portal.exception.NoSuchUserException;
import com.liferay.portal.model.User;

import java.util.Date;

/**
 * The persistence interface for the user service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.UserPersistenceImpl
 * @see UserUtil
 * @generated
 */
@ProviderType
public interface UserPersistence extends BasePersistence<User> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserUtil} to access the user persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the users where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching users
	*/
	public java.util.List<User> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the users where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	*/
	public java.util.List<User> findByUuid(java.lang.String uuid, int start,
		int end);

	/**
	* Returns an ordered range of all the users where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	*/
	public java.util.List<User> findByUuid(java.lang.String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns an ordered range of all the users where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching users
	*/
	public java.util.List<User> findByUuid(java.lang.String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first user in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Returns the first user in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns the last user in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Returns the last user in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns the users before and after the current user in the ordered set where uuid = &#63;.
	*
	* @param userId the primary key of the current user
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user
	* @throws NoSuchUserException if a user with the primary key could not be found
	*/
	public User[] findByUuid_PrevAndNext(long userId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Removes all the users where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of users where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching users
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns all the users where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching users
	*/
	public java.util.List<User> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the users where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	*/
	public java.util.List<User> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the users where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	*/
	public java.util.List<User> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns an ordered range of all the users where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public java.util.List<User> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first user in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Returns the first user in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns the last user in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Returns the last user in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

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
	public User[] findByUuid_C_PrevAndNext(long userId, java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Removes all the users where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of users where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching users
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the users where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching users
	*/
	public java.util.List<User> findByCompanyId(long companyId);

	/**
	* Returns a range of all the users where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	*/
	public java.util.List<User> findByCompanyId(long companyId, int start,
		int end);

	/**
	* Returns an ordered range of all the users where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	*/
	public java.util.List<User> findByCompanyId(long companyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns an ordered range of all the users where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching users
	*/
	public java.util.List<User> findByCompanyId(long companyId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first user in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Returns the first user in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns the last user in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Returns the last user in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns the users before and after the current user in the ordered set where companyId = &#63;.
	*
	* @param userId the primary key of the current user
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user
	* @throws NoSuchUserException if a user with the primary key could not be found
	*/
	public User[] findByCompanyId_PrevAndNext(long userId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Removes all the users where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of users where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching users
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns the user where contactId = &#63; or throws a {@link NoSuchUserException} if it could not be found.
	*
	* @param contactId the contact ID
	* @return the matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByContactId(long contactId) throws NoSuchUserException;

	/**
	* Returns the user where contactId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param contactId the contact ID
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByContactId(long contactId);

	/**
	* Returns the user where contactId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param contactId the contact ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByContactId(long contactId, boolean retrieveFromCache);

	/**
	* Removes the user where contactId = &#63; from the database.
	*
	* @param contactId the contact ID
	* @return the user that was removed
	*/
	public User removeByContactId(long contactId) throws NoSuchUserException;

	/**
	* Returns the number of users where contactId = &#63;.
	*
	* @param contactId the contact ID
	* @return the number of matching users
	*/
	public int countByContactId(long contactId);

	/**
	* Returns all the users where emailAddress = &#63;.
	*
	* @param emailAddress the email address
	* @return the matching users
	*/
	public java.util.List<User> findByEmailAddress(
		java.lang.String emailAddress);

	/**
	* Returns a range of all the users where emailAddress = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param emailAddress the email address
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	*/
	public java.util.List<User> findByEmailAddress(
		java.lang.String emailAddress, int start, int end);

	/**
	* Returns an ordered range of all the users where emailAddress = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param emailAddress the email address
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	*/
	public java.util.List<User> findByEmailAddress(
		java.lang.String emailAddress, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns an ordered range of all the users where emailAddress = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param emailAddress the email address
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching users
	*/
	public java.util.List<User> findByEmailAddress(
		java.lang.String emailAddress, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first user in the ordered set where emailAddress = &#63;.
	*
	* @param emailAddress the email address
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByEmailAddress_First(java.lang.String emailAddress,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Returns the first user in the ordered set where emailAddress = &#63;.
	*
	* @param emailAddress the email address
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByEmailAddress_First(java.lang.String emailAddress,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns the last user in the ordered set where emailAddress = &#63;.
	*
	* @param emailAddress the email address
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByEmailAddress_Last(java.lang.String emailAddress,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Returns the last user in the ordered set where emailAddress = &#63;.
	*
	* @param emailAddress the email address
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByEmailAddress_Last(java.lang.String emailAddress,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns the users before and after the current user in the ordered set where emailAddress = &#63;.
	*
	* @param userId the primary key of the current user
	* @param emailAddress the email address
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user
	* @throws NoSuchUserException if a user with the primary key could not be found
	*/
	public User[] findByEmailAddress_PrevAndNext(long userId,
		java.lang.String emailAddress,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Removes all the users where emailAddress = &#63; from the database.
	*
	* @param emailAddress the email address
	*/
	public void removeByEmailAddress(java.lang.String emailAddress);

	/**
	* Returns the number of users where emailAddress = &#63;.
	*
	* @param emailAddress the email address
	* @return the number of matching users
	*/
	public int countByEmailAddress(java.lang.String emailAddress);

	/**
	* Returns the user where portraitId = &#63; or throws a {@link NoSuchUserException} if it could not be found.
	*
	* @param portraitId the portrait ID
	* @return the matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByPortraitId(long portraitId) throws NoSuchUserException;

	/**
	* Returns the user where portraitId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param portraitId the portrait ID
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByPortraitId(long portraitId);

	/**
	* Returns the user where portraitId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param portraitId the portrait ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByPortraitId(long portraitId, boolean retrieveFromCache);

	/**
	* Removes the user where portraitId = &#63; from the database.
	*
	* @param portraitId the portrait ID
	* @return the user that was removed
	*/
	public User removeByPortraitId(long portraitId) throws NoSuchUserException;

	/**
	* Returns the number of users where portraitId = &#63;.
	*
	* @param portraitId the portrait ID
	* @return the number of matching users
	*/
	public int countByPortraitId(long portraitId);

	/**
	* Returns the user where companyId = &#63; and userId = &#63; or throws a {@link NoSuchUserException} if it could not be found.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @return the matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByC_U(long companyId, long userId)
		throws NoSuchUserException;

	/**
	* Returns the user where companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_U(long companyId, long userId);

	/**
	* Returns the user where companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_U(long companyId, long userId,
		boolean retrieveFromCache);

	/**
	* Removes the user where companyId = &#63; and userId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @return the user that was removed
	*/
	public User removeByC_U(long companyId, long userId)
		throws NoSuchUserException;

	/**
	* Returns the number of users where companyId = &#63; and userId = &#63;.
	*
	* @param companyId the company ID
	* @param userId the user ID
	* @return the number of matching users
	*/
	public int countByC_U(long companyId, long userId);

	/**
	* Returns all the users where companyId = &#63; and createDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @return the matching users
	*/
	public java.util.List<User> findByC_CD(long companyId, Date createDate);

	/**
	* Returns a range of all the users where companyId = &#63; and createDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	*/
	public java.util.List<User> findByC_CD(long companyId, Date createDate,
		int start, int end);

	/**
	* Returns an ordered range of all the users where companyId = &#63; and createDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	*/
	public java.util.List<User> findByC_CD(long companyId, Date createDate,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns an ordered range of all the users where companyId = &#63; and createDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public java.util.List<User> findByC_CD(long companyId, Date createDate,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first user in the ordered set where companyId = &#63; and createDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByC_CD_First(long companyId, Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Returns the first user in the ordered set where companyId = &#63; and createDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_CD_First(long companyId, Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns the last user in the ordered set where companyId = &#63; and createDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByC_CD_Last(long companyId, Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Returns the last user in the ordered set where companyId = &#63; and createDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_CD_Last(long companyId, Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

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
	public User[] findByC_CD_PrevAndNext(long userId, long companyId,
		Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Removes all the users where companyId = &#63; and createDate = &#63; from the database.
	*
	* @param companyId the company ID
	* @param createDate the create date
	*/
	public void removeByC_CD(long companyId, Date createDate);

	/**
	* Returns the number of users where companyId = &#63; and createDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @return the number of matching users
	*/
	public int countByC_CD(long companyId, Date createDate);

	/**
	* Returns all the users where companyId = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @return the matching users
	*/
	public java.util.List<User> findByC_MD(long companyId, Date modifiedDate);

	/**
	* Returns a range of all the users where companyId = &#63; and modifiedDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	*/
	public java.util.List<User> findByC_MD(long companyId, Date modifiedDate,
		int start, int end);

	/**
	* Returns an ordered range of all the users where companyId = &#63; and modifiedDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	*/
	public java.util.List<User> findByC_MD(long companyId, Date modifiedDate,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns an ordered range of all the users where companyId = &#63; and modifiedDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public java.util.List<User> findByC_MD(long companyId, Date modifiedDate,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByC_MD_First(long companyId, Date modifiedDate,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Returns the first user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_MD_First(long companyId, Date modifiedDate,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns the last user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByC_MD_Last(long companyId, Date modifiedDate,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Returns the last user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_MD_Last(long companyId, Date modifiedDate,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

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
	public User[] findByC_MD_PrevAndNext(long userId, long companyId,
		Date modifiedDate,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Removes all the users where companyId = &#63; and modifiedDate = &#63; from the database.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	*/
	public void removeByC_MD(long companyId, Date modifiedDate);

	/**
	* Returns the number of users where companyId = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param modifiedDate the modified date
	* @return the number of matching users
	*/
	public int countByC_MD(long companyId, Date modifiedDate);

	/**
	* Returns the user where companyId = &#63; and defaultUser = &#63; or throws a {@link NoSuchUserException} if it could not be found.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @return the matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByC_DU(long companyId, boolean defaultUser)
		throws NoSuchUserException;

	/**
	* Returns the user where companyId = &#63; and defaultUser = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_DU(long companyId, boolean defaultUser);

	/**
	* Returns the user where companyId = &#63; and defaultUser = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_DU(long companyId, boolean defaultUser,
		boolean retrieveFromCache);

	/**
	* Removes the user where companyId = &#63; and defaultUser = &#63; from the database.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @return the user that was removed
	*/
	public User removeByC_DU(long companyId, boolean defaultUser)
		throws NoSuchUserException;

	/**
	* Returns the number of users where companyId = &#63; and defaultUser = &#63;.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @return the number of matching users
	*/
	public int countByC_DU(long companyId, boolean defaultUser);

	/**
	* Returns the user where companyId = &#63; and screenName = &#63; or throws a {@link NoSuchUserException} if it could not be found.
	*
	* @param companyId the company ID
	* @param screenName the screen name
	* @return the matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByC_SN(long companyId, java.lang.String screenName)
		throws NoSuchUserException;

	/**
	* Returns the user where companyId = &#63; and screenName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param screenName the screen name
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_SN(long companyId, java.lang.String screenName);

	/**
	* Returns the user where companyId = &#63; and screenName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param screenName the screen name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_SN(long companyId, java.lang.String screenName,
		boolean retrieveFromCache);

	/**
	* Removes the user where companyId = &#63; and screenName = &#63; from the database.
	*
	* @param companyId the company ID
	* @param screenName the screen name
	* @return the user that was removed
	*/
	public User removeByC_SN(long companyId, java.lang.String screenName)
		throws NoSuchUserException;

	/**
	* Returns the number of users where companyId = &#63; and screenName = &#63;.
	*
	* @param companyId the company ID
	* @param screenName the screen name
	* @return the number of matching users
	*/
	public int countByC_SN(long companyId, java.lang.String screenName);

	/**
	* Returns the user where companyId = &#63; and emailAddress = &#63; or throws a {@link NoSuchUserException} if it could not be found.
	*
	* @param companyId the company ID
	* @param emailAddress the email address
	* @return the matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByC_EA(long companyId, java.lang.String emailAddress)
		throws NoSuchUserException;

	/**
	* Returns the user where companyId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param emailAddress the email address
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_EA(long companyId, java.lang.String emailAddress);

	/**
	* Returns the user where companyId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param emailAddress the email address
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_EA(long companyId, java.lang.String emailAddress,
		boolean retrieveFromCache);

	/**
	* Removes the user where companyId = &#63; and emailAddress = &#63; from the database.
	*
	* @param companyId the company ID
	* @param emailAddress the email address
	* @return the user that was removed
	*/
	public User removeByC_EA(long companyId, java.lang.String emailAddress)
		throws NoSuchUserException;

	/**
	* Returns the number of users where companyId = &#63; and emailAddress = &#63;.
	*
	* @param companyId the company ID
	* @param emailAddress the email address
	* @return the number of matching users
	*/
	public int countByC_EA(long companyId, java.lang.String emailAddress);

	/**
	* Returns the user where companyId = &#63; and facebookId = &#63; or throws a {@link NoSuchUserException} if it could not be found.
	*
	* @param companyId the company ID
	* @param facebookId the facebook ID
	* @return the matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByC_FID(long companyId, long facebookId)
		throws NoSuchUserException;

	/**
	* Returns the user where companyId = &#63; and facebookId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param facebookId the facebook ID
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_FID(long companyId, long facebookId);

	/**
	* Returns the user where companyId = &#63; and facebookId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param facebookId the facebook ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_FID(long companyId, long facebookId,
		boolean retrieveFromCache);

	/**
	* Removes the user where companyId = &#63; and facebookId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param facebookId the facebook ID
	* @return the user that was removed
	*/
	public User removeByC_FID(long companyId, long facebookId)
		throws NoSuchUserException;

	/**
	* Returns the number of users where companyId = &#63; and facebookId = &#63;.
	*
	* @param companyId the company ID
	* @param facebookId the facebook ID
	* @return the number of matching users
	*/
	public int countByC_FID(long companyId, long facebookId);

	/**
	* Returns the user where companyId = &#63; and openId = &#63; or throws a {@link NoSuchUserException} if it could not be found.
	*
	* @param companyId the company ID
	* @param openId the open ID
	* @return the matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByC_O(long companyId, java.lang.String openId)
		throws NoSuchUserException;

	/**
	* Returns the user where companyId = &#63; and openId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param openId the open ID
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_O(long companyId, java.lang.String openId);

	/**
	* Returns the user where companyId = &#63; and openId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param openId the open ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_O(long companyId, java.lang.String openId,
		boolean retrieveFromCache);

	/**
	* Removes the user where companyId = &#63; and openId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param openId the open ID
	* @return the user that was removed
	*/
	public User removeByC_O(long companyId, java.lang.String openId)
		throws NoSuchUserException;

	/**
	* Returns the number of users where companyId = &#63; and openId = &#63;.
	*
	* @param companyId the company ID
	* @param openId the open ID
	* @return the number of matching users
	*/
	public int countByC_O(long companyId, java.lang.String openId);

	/**
	* Returns all the users where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching users
	*/
	public java.util.List<User> findByC_S(long companyId, int status);

	/**
	* Returns a range of all the users where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	*/
	public java.util.List<User> findByC_S(long companyId, int status,
		int start, int end);

	/**
	* Returns an ordered range of all the users where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching users
	*/
	public java.util.List<User> findByC_S(long companyId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns an ordered range of all the users where companyId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public java.util.List<User> findByC_S(long companyId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first user in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByC_S_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Returns the first user in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_S_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns the last user in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user
	* @throws NoSuchUserException if a matching user could not be found
	*/
	public User findByC_S_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Returns the last user in the ordered set where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_S_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

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
	public User[] findByC_S_PrevAndNext(long userId, long companyId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Removes all the users where companyId = &#63; and status = &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public void removeByC_S(long companyId, int status);

	/**
	* Returns the number of users where companyId = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching users
	*/
	public int countByC_S(long companyId, int status);

	/**
	* Returns all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param modifiedDate the modified date
	* @return the matching users
	*/
	public java.util.List<User> findByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate);

	/**
	* Returns a range of all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param modifiedDate the modified date
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	*/
	public java.util.List<User> findByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate, int start, int end);

	/**
	* Returns an ordered range of all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public java.util.List<User> findByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns an ordered range of all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public java.util.List<User> findByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache);

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
	public User findByC_CD_MD_First(long companyId, Date createDate,
		Date modifiedDate,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Returns the first user in the ordered set where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_CD_MD_First(long companyId, Date createDate,
		Date modifiedDate,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

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
	public User findByC_CD_MD_Last(long companyId, Date createDate,
		Date modifiedDate,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Returns the last user in the ordered set where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param modifiedDate the modified date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_CD_MD_Last(long companyId, Date createDate,
		Date modifiedDate,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

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
	public User[] findByC_CD_MD_PrevAndNext(long userId, long companyId,
		Date createDate, Date modifiedDate,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Removes all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63; from the database.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param modifiedDate the modified date
	*/
	public void removeByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate);

	/**
	* Returns the number of users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param modifiedDate the modified date
	* @return the number of matching users
	*/
	public int countByC_CD_MD(long companyId, Date createDate, Date modifiedDate);

	/**
	* Returns all the users where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param status the status
	* @return the matching users
	*/
	public java.util.List<User> findByC_DU_S(long companyId,
		boolean defaultUser, int status);

	/**
	* Returns a range of all the users where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param status the status
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of matching users
	*/
	public java.util.List<User> findByC_DU_S(long companyId,
		boolean defaultUser, int status, int start, int end);

	/**
	* Returns an ordered range of all the users where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public java.util.List<User> findByC_DU_S(long companyId,
		boolean defaultUser, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns an ordered range of all the users where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public java.util.List<User> findByC_DU_S(long companyId,
		boolean defaultUser, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache);

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
	public User findByC_DU_S_First(long companyId, boolean defaultUser,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Returns the first user in the ordered set where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_DU_S_First(long companyId, boolean defaultUser,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

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
	public User findByC_DU_S_Last(long companyId, boolean defaultUser,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Returns the last user in the ordered set where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user, or <code>null</code> if a matching user could not be found
	*/
	public User fetchByC_DU_S_Last(long companyId, boolean defaultUser,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

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
	public User[] findByC_DU_S_PrevAndNext(long userId, long companyId,
		boolean defaultUser, int status,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator)
		throws NoSuchUserException;

	/**
	* Removes all the users where companyId = &#63; and defaultUser = &#63; and status = &#63; from the database.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param status the status
	*/
	public void removeByC_DU_S(long companyId, boolean defaultUser, int status);

	/**
	* Returns the number of users where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	*
	* @param companyId the company ID
	* @param defaultUser the default user
	* @param status the status
	* @return the number of matching users
	*/
	public int countByC_DU_S(long companyId, boolean defaultUser, int status);

	/**
	* Caches the user in the entity cache if it is enabled.
	*
	* @param user the user
	*/
	public void cacheResult(User user);

	/**
	* Caches the users in the entity cache if it is enabled.
	*
	* @param users the users
	*/
	public void cacheResult(java.util.List<User> users);

	/**
	* Creates a new user with the primary key. Does not add the user to the database.
	*
	* @param userId the primary key for the new user
	* @return the new user
	*/
	public User create(long userId);

	/**
	* Removes the user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userId the primary key of the user
	* @return the user that was removed
	* @throws NoSuchUserException if a user with the primary key could not be found
	*/
	public User remove(long userId) throws NoSuchUserException;

	public User updateImpl(User user);

	/**
	* Returns the user with the primary key or throws a {@link NoSuchUserException} if it could not be found.
	*
	* @param userId the primary key of the user
	* @return the user
	* @throws NoSuchUserException if a user with the primary key could not be found
	*/
	public User findByPrimaryKey(long userId) throws NoSuchUserException;

	/**
	* Returns the user with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param userId the primary key of the user
	* @return the user, or <code>null</code> if a user with the primary key could not be found
	*/
	public User fetchByPrimaryKey(long userId);

	@Override
	public java.util.Map<java.io.Serializable, User> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the users.
	*
	* @return the users
	*/
	public java.util.List<User> findAll();

	/**
	* Returns a range of all the users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of users
	*/
	public java.util.List<User> findAll(int start, int end);

	/**
	* Returns an ordered range of all the users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of users
	*/
	public java.util.List<User> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator);

	/**
	* Returns an ordered range of all the users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of users
	*/
	public java.util.List<User> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the users from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of users.
	*
	* @return the number of users
	*/
	public int countAll();

	/**
	* Returns the primaryKeys of groups associated with the user.
	*
	* @param pk the primary key of the user
	* @return long[] of the primaryKeys of groups associated with the user
	*/
	public long[] getGroupPrimaryKeys(long pk);

	/**
	* Returns all the groups associated with the user.
	*
	* @param pk the primary key of the user
	* @return the groups associated with the user
	*/
	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk);

	/**
	* Returns a range of all the groups associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of groups associated with the user
	*/
	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk,
		int start, int end);

	/**
	* Returns an ordered range of all the groups associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of groups associated with the user
	*/
	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Group> orderByComparator);

	/**
	* Returns the number of groups associated with the user.
	*
	* @param pk the primary key of the user
	* @return the number of groups associated with the user
	*/
	public int getGroupsSize(long pk);

	/**
	* Returns <code>true</code> if the group is associated with the user.
	*
	* @param pk the primary key of the user
	* @param groupPK the primary key of the group
	* @return <code>true</code> if the group is associated with the user; <code>false</code> otherwise
	*/
	public boolean containsGroup(long pk, long groupPK);

	/**
	* Returns <code>true</code> if the user has any groups associated with it.
	*
	* @param pk the primary key of the user to check for associations with groups
	* @return <code>true</code> if the user has any groups associated with it; <code>false</code> otherwise
	*/
	public boolean containsGroups(long pk);

	/**
	* Adds an association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groupPK the primary key of the group
	*/
	public void addGroup(long pk, long groupPK);

	/**
	* Adds an association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param group the group
	*/
	public void addGroup(long pk, com.liferay.portal.model.Group group);

	/**
	* Adds an association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groupPKs the primary keys of the groups
	*/
	public void addGroups(long pk, long[] groupPKs);

	/**
	* Adds an association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groups the groups
	*/
	public void addGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups);

	/**
	* Clears all associations between the user and its groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user to clear the associated groups from
	*/
	public void clearGroups(long pk);

	/**
	* Removes the association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groupPK the primary key of the group
	*/
	public void removeGroup(long pk, long groupPK);

	/**
	* Removes the association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param group the group
	*/
	public void removeGroup(long pk, com.liferay.portal.model.Group group);

	/**
	* Removes the association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groupPKs the primary keys of the groups
	*/
	public void removeGroups(long pk, long[] groupPKs);

	/**
	* Removes the association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groups the groups
	*/
	public void removeGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups);

	/**
	* Sets the groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groupPKs the primary keys of the groups to be associated with the user
	*/
	public void setGroups(long pk, long[] groupPKs);

	/**
	* Sets the groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param groups the groups to be associated with the user
	*/
	public void setGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups);

	/**
	* Returns the primaryKeys of organizations associated with the user.
	*
	* @param pk the primary key of the user
	* @return long[] of the primaryKeys of organizations associated with the user
	*/
	public long[] getOrganizationPrimaryKeys(long pk);

	/**
	* Returns all the organizations associated with the user.
	*
	* @param pk the primary key of the user
	* @return the organizations associated with the user
	*/
	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long pk);

	/**
	* Returns a range of all the organizations associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of organizations associated with the user
	*/
	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the organizations associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of organizations associated with the user
	*/
	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Organization> orderByComparator);

	/**
	* Returns the number of organizations associated with the user.
	*
	* @param pk the primary key of the user
	* @return the number of organizations associated with the user
	*/
	public int getOrganizationsSize(long pk);

	/**
	* Returns <code>true</code> if the organization is associated with the user.
	*
	* @param pk the primary key of the user
	* @param organizationPK the primary key of the organization
	* @return <code>true</code> if the organization is associated with the user; <code>false</code> otherwise
	*/
	public boolean containsOrganization(long pk, long organizationPK);

	/**
	* Returns <code>true</code> if the user has any organizations associated with it.
	*
	* @param pk the primary key of the user to check for associations with organizations
	* @return <code>true</code> if the user has any organizations associated with it; <code>false</code> otherwise
	*/
	public boolean containsOrganizations(long pk);

	/**
	* Adds an association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizationPK the primary key of the organization
	*/
	public void addOrganization(long pk, long organizationPK);

	/**
	* Adds an association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organization the organization
	*/
	public void addOrganization(long pk,
		com.liferay.portal.model.Organization organization);

	/**
	* Adds an association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizationPKs the primary keys of the organizations
	*/
	public void addOrganizations(long pk, long[] organizationPKs);

	/**
	* Adds an association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizations the organizations
	*/
	public void addOrganizations(long pk,
		java.util.List<com.liferay.portal.model.Organization> organizations);

	/**
	* Clears all associations between the user and its organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user to clear the associated organizations from
	*/
	public void clearOrganizations(long pk);

	/**
	* Removes the association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizationPK the primary key of the organization
	*/
	public void removeOrganization(long pk, long organizationPK);

	/**
	* Removes the association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organization the organization
	*/
	public void removeOrganization(long pk,
		com.liferay.portal.model.Organization organization);

	/**
	* Removes the association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizationPKs the primary keys of the organizations
	*/
	public void removeOrganizations(long pk, long[] organizationPKs);

	/**
	* Removes the association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizations the organizations
	*/
	public void removeOrganizations(long pk,
		java.util.List<com.liferay.portal.model.Organization> organizations);

	/**
	* Sets the organizations associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizationPKs the primary keys of the organizations to be associated with the user
	*/
	public void setOrganizations(long pk, long[] organizationPKs);

	/**
	* Sets the organizations associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param organizations the organizations to be associated with the user
	*/
	public void setOrganizations(long pk,
		java.util.List<com.liferay.portal.model.Organization> organizations);

	/**
	* Returns the primaryKeys of roles associated with the user.
	*
	* @param pk the primary key of the user
	* @return long[] of the primaryKeys of roles associated with the user
	*/
	public long[] getRolePrimaryKeys(long pk);

	/**
	* Returns all the roles associated with the user.
	*
	* @param pk the primary key of the user
	* @return the roles associated with the user
	*/
	public java.util.List<com.liferay.portal.model.Role> getRoles(long pk);

	/**
	* Returns a range of all the roles associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of roles associated with the user
	*/
	public java.util.List<com.liferay.portal.model.Role> getRoles(long pk,
		int start, int end);

	/**
	* Returns an ordered range of all the roles associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of roles associated with the user
	*/
	public java.util.List<com.liferay.portal.model.Role> getRoles(long pk,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Role> orderByComparator);

	/**
	* Returns the number of roles associated with the user.
	*
	* @param pk the primary key of the user
	* @return the number of roles associated with the user
	*/
	public int getRolesSize(long pk);

	/**
	* Returns <code>true</code> if the role is associated with the user.
	*
	* @param pk the primary key of the user
	* @param rolePK the primary key of the role
	* @return <code>true</code> if the role is associated with the user; <code>false</code> otherwise
	*/
	public boolean containsRole(long pk, long rolePK);

	/**
	* Returns <code>true</code> if the user has any roles associated with it.
	*
	* @param pk the primary key of the user to check for associations with roles
	* @return <code>true</code> if the user has any roles associated with it; <code>false</code> otherwise
	*/
	public boolean containsRoles(long pk);

	/**
	* Adds an association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param rolePK the primary key of the role
	*/
	public void addRole(long pk, long rolePK);

	/**
	* Adds an association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param role the role
	*/
	public void addRole(long pk, com.liferay.portal.model.Role role);

	/**
	* Adds an association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param rolePKs the primary keys of the roles
	*/
	public void addRoles(long pk, long[] rolePKs);

	/**
	* Adds an association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param roles the roles
	*/
	public void addRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles);

	/**
	* Clears all associations between the user and its roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user to clear the associated roles from
	*/
	public void clearRoles(long pk);

	/**
	* Removes the association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param rolePK the primary key of the role
	*/
	public void removeRole(long pk, long rolePK);

	/**
	* Removes the association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param role the role
	*/
	public void removeRole(long pk, com.liferay.portal.model.Role role);

	/**
	* Removes the association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param rolePKs the primary keys of the roles
	*/
	public void removeRoles(long pk, long[] rolePKs);

	/**
	* Removes the association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param roles the roles
	*/
	public void removeRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles);

	/**
	* Sets the roles associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param rolePKs the primary keys of the roles to be associated with the user
	*/
	public void setRoles(long pk, long[] rolePKs);

	/**
	* Sets the roles associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param roles the roles to be associated with the user
	*/
	public void setRoles(long pk,
		java.util.List<com.liferay.portal.model.Role> roles);

	/**
	* Returns the primaryKeys of teams associated with the user.
	*
	* @param pk the primary key of the user
	* @return long[] of the primaryKeys of teams associated with the user
	*/
	public long[] getTeamPrimaryKeys(long pk);

	/**
	* Returns all the teams associated with the user.
	*
	* @param pk the primary key of the user
	* @return the teams associated with the user
	*/
	public java.util.List<com.liferay.portal.model.Team> getTeams(long pk);

	/**
	* Returns a range of all the teams associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of teams associated with the user
	*/
	public java.util.List<com.liferay.portal.model.Team> getTeams(long pk,
		int start, int end);

	/**
	* Returns an ordered range of all the teams associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of teams associated with the user
	*/
	public java.util.List<com.liferay.portal.model.Team> getTeams(long pk,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Team> orderByComparator);

	/**
	* Returns the number of teams associated with the user.
	*
	* @param pk the primary key of the user
	* @return the number of teams associated with the user
	*/
	public int getTeamsSize(long pk);

	/**
	* Returns <code>true</code> if the team is associated with the user.
	*
	* @param pk the primary key of the user
	* @param teamPK the primary key of the team
	* @return <code>true</code> if the team is associated with the user; <code>false</code> otherwise
	*/
	public boolean containsTeam(long pk, long teamPK);

	/**
	* Returns <code>true</code> if the user has any teams associated with it.
	*
	* @param pk the primary key of the user to check for associations with teams
	* @return <code>true</code> if the user has any teams associated with it; <code>false</code> otherwise
	*/
	public boolean containsTeams(long pk);

	/**
	* Adds an association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teamPK the primary key of the team
	*/
	public void addTeam(long pk, long teamPK);

	/**
	* Adds an association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param team the team
	*/
	public void addTeam(long pk, com.liferay.portal.model.Team team);

	/**
	* Adds an association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teamPKs the primary keys of the teams
	*/
	public void addTeams(long pk, long[] teamPKs);

	/**
	* Adds an association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teams the teams
	*/
	public void addTeams(long pk,
		java.util.List<com.liferay.portal.model.Team> teams);

	/**
	* Clears all associations between the user and its teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user to clear the associated teams from
	*/
	public void clearTeams(long pk);

	/**
	* Removes the association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teamPK the primary key of the team
	*/
	public void removeTeam(long pk, long teamPK);

	/**
	* Removes the association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param team the team
	*/
	public void removeTeam(long pk, com.liferay.portal.model.Team team);

	/**
	* Removes the association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teamPKs the primary keys of the teams
	*/
	public void removeTeams(long pk, long[] teamPKs);

	/**
	* Removes the association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teams the teams
	*/
	public void removeTeams(long pk,
		java.util.List<com.liferay.portal.model.Team> teams);

	/**
	* Sets the teams associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teamPKs the primary keys of the teams to be associated with the user
	*/
	public void setTeams(long pk, long[] teamPKs);

	/**
	* Sets the teams associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param teams the teams to be associated with the user
	*/
	public void setTeams(long pk,
		java.util.List<com.liferay.portal.model.Team> teams);

	/**
	* Returns the primaryKeys of user groups associated with the user.
	*
	* @param pk the primary key of the user
	* @return long[] of the primaryKeys of user groups associated with the user
	*/
	public long[] getUserGroupPrimaryKeys(long pk);

	/**
	* Returns all the user groups associated with the user.
	*
	* @param pk the primary key of the user
	* @return the user groups associated with the user
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk);

	/**
	* Returns a range of all the user groups associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @return the range of user groups associated with the user
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk, int start, int end);

	/**
	* Returns an ordered range of all the user groups associated with the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user
	* @param start the lower bound of the range of users
	* @param end the upper bound of the range of users (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of user groups associated with the user
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.UserGroup> orderByComparator);

	/**
	* Returns the number of user groups associated with the user.
	*
	* @param pk the primary key of the user
	* @return the number of user groups associated with the user
	*/
	public int getUserGroupsSize(long pk);

	/**
	* Returns <code>true</code> if the user group is associated with the user.
	*
	* @param pk the primary key of the user
	* @param userGroupPK the primary key of the user group
	* @return <code>true</code> if the user group is associated with the user; <code>false</code> otherwise
	*/
	public boolean containsUserGroup(long pk, long userGroupPK);

	/**
	* Returns <code>true</code> if the user has any user groups associated with it.
	*
	* @param pk the primary key of the user to check for associations with user groups
	* @return <code>true</code> if the user has any user groups associated with it; <code>false</code> otherwise
	*/
	public boolean containsUserGroups(long pk);

	/**
	* Adds an association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroupPK the primary key of the user group
	*/
	public void addUserGroup(long pk, long userGroupPK);

	/**
	* Adds an association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroup the user group
	*/
	public void addUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup);

	/**
	* Adds an association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroupPKs the primary keys of the user groups
	*/
	public void addUserGroups(long pk, long[] userGroupPKs);

	/**
	* Adds an association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroups the user groups
	*/
	public void addUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups);

	/**
	* Clears all associations between the user and its user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user to clear the associated user groups from
	*/
	public void clearUserGroups(long pk);

	/**
	* Removes the association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroupPK the primary key of the user group
	*/
	public void removeUserGroup(long pk, long userGroupPK);

	/**
	* Removes the association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroup the user group
	*/
	public void removeUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup);

	/**
	* Removes the association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroupPKs the primary keys of the user groups
	*/
	public void removeUserGroups(long pk, long[] userGroupPKs);

	/**
	* Removes the association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroups the user groups
	*/
	public void removeUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups);

	/**
	* Sets the user groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroupPKs the primary keys of the user groups to be associated with the user
	*/
	public void setUserGroups(long pk, long[] userGroupPKs);

	/**
	* Sets the user groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user
	* @param userGroups the user groups to be associated with the user
	*/
	public void setUserGroups(long pk,
		java.util.List<com.liferay.portal.model.UserGroup> userGroups);

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}