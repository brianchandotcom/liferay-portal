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

import com.liferay.portal.model.UserIdMapper;

/**
 * The persistence interface for the user ID mapper service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserIdMapperPersistenceImpl
 * @see UserIdMapperUtil
 * @generated
 */
public interface UserIdMapperPersistence extends BasePersistence<UserIdMapper> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserIdMapperUtil} to access the user ID mapper persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the user ID mapper in the entity cache if it is enabled.
	*
	* @param userIdMapper the user ID mapper
	*/
	public void cacheResult(com.liferay.portal.model.UserIdMapper userIdMapper);

	/**
	* Caches the user ID mappers in the entity cache if it is enabled.
	*
	* @param userIdMappers the user ID mappers
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.UserIdMapper> userIdMappers);

	/**
	* Creates a new user ID mapper with the primary key. Does not add the user ID mapper to the database.
	*
	* @param userIdMapperId the primary key for the new user ID mapper
	* @return the new user ID mapper
	*/
	public com.liferay.portal.model.UserIdMapper create(long userIdMapperId);

	/**
	* Removes the user ID mapper with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userIdMapperId the primary key of the user ID mapper
	* @return the user ID mapper that was removed
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a user ID mapper with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper remove(long userIdMapperId)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserIdMapper updateImpl(
		com.liferay.portal.model.UserIdMapper userIdMapper, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user ID mapper with the primary key or throws a {@link com.liferay.portal.NoSuchUserIdMapperException} if it could not be found.
	*
	* @param userIdMapperId the primary key of the user ID mapper
	* @return the user ID mapper
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a user ID mapper with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper findByPrimaryKey(
		long userIdMapperId)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user ID mapper with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param userIdMapperId the primary key of the user ID mapper
	* @return the user ID mapper, or <code>null</code> if a user ID mapper with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper fetchByPrimaryKey(
		long userIdMapperId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the user ID mappers where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching user ID mappers
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserIdMapper> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the user ID mappers where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of user ID mappers
	* @param end the upper bound of the range of user ID mappers (not inclusive)
	* @return the range of matching user ID mappers
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserIdMapper> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the user ID mappers where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of user ID mappers
	* @param end the upper bound of the range of user ID mappers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user ID mappers
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserIdMapper> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first user ID mapper in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user ID mapper
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a matching user ID mapper could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last user ID mapper in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user ID mapper
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a matching user ID mapper could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user ID mappers before and after the current user ID mapper in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userIdMapperId the primary key of the current user ID mapper
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user ID mapper
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a user ID mapper with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper[] findByUserId_PrevAndNext(
		long userIdMapperId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user ID mapper where userId = &#63; and type = &#63; or throws a {@link com.liferay.portal.NoSuchUserIdMapperException} if it could not be found.
	*
	* @param userId the user ID
	* @param type the type
	* @return the matching user ID mapper
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a matching user ID mapper could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper findByU_T(long userId,
		java.lang.String type)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user ID mapper where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param type the type
	* @return the matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper fetchByU_T(long userId,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user ID mapper where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param type the type
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper fetchByU_T(long userId,
		java.lang.String type, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user ID mapper where type = &#63; and externalUserId = &#63; or throws a {@link com.liferay.portal.NoSuchUserIdMapperException} if it could not be found.
	*
	* @param type the type
	* @param externalUserId the external user ID
	* @return the matching user ID mapper
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a matching user ID mapper could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper findByT_E(
		java.lang.String type, java.lang.String externalUserId)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user ID mapper where type = &#63; and externalUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param type the type
	* @param externalUserId the external user ID
	* @return the matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper fetchByT_E(
		java.lang.String type, java.lang.String externalUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user ID mapper where type = &#63; and externalUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param type the type
	* @param externalUserId the external user ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper fetchByT_E(
		java.lang.String type, java.lang.String externalUserId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the user ID mappers.
	*
	* @return the user ID mappers
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserIdMapper> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the user ID mappers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of user ID mappers
	* @param end the upper bound of the range of user ID mappers (not inclusive)
	* @return the range of user ID mappers
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserIdMapper> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the user ID mappers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of user ID mappers
	* @param end the upper bound of the range of user ID mappers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of user ID mappers
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.UserIdMapper> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the user ID mappers where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the user ID mapper where userId = &#63; and type = &#63; from the database.
	*
	* @param userId the user ID
	* @param type the type
	* @return the user ID mapper that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper removeByU_T(long userId,
		java.lang.String type)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the user ID mapper where type = &#63; and externalUserId = &#63; from the database.
	*
	* @param type the type
	* @param externalUserId the external user ID
	* @return the user ID mapper that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.UserIdMapper removeByT_E(
		java.lang.String type, java.lang.String externalUserId)
		throws com.liferay.portal.NoSuchUserIdMapperException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the user ID mappers from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of user ID mappers where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching user ID mappers
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of user ID mappers where userId = &#63; and type = &#63;.
	*
	* @param userId the user ID
	* @param type the type
	* @return the number of matching user ID mappers
	* @throws SystemException if a system exception occurred
	*/
	public int countByU_T(long userId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of user ID mappers where type = &#63; and externalUserId = &#63;.
	*
	* @param type the type
	* @param externalUserId the external user ID
	* @return the number of matching user ID mappers
	* @throws SystemException if a system exception occurred
	*/
	public int countByT_E(java.lang.String type, java.lang.String externalUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of user ID mappers.
	*
	* @return the number of user ID mappers
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}