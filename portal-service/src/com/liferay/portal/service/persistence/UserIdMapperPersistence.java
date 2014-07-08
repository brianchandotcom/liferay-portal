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
@ProviderType
public interface UserIdMapperPersistence extends BasePersistence<UserIdMapper> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserIdMapperUtil} to access the user ID mapper persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the user ID mappers where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching user ID mappers
	*/
	public java.util.List<com.liferay.portal.model.UserIdMapper> findByUserId(
		long userId);

	/**
	* Returns a range of all the user ID mappers where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserIdMapperModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of user ID mappers
	* @param end the upper bound of the range of user ID mappers (not inclusive)
	* @return the range of matching user ID mappers
	*/
	public java.util.List<com.liferay.portal.model.UserIdMapper> findByUserId(
		long userId, int start, int end);

	/**
	* Returns an ordered range of all the user ID mappers where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserIdMapperModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of user ID mappers
	* @param end the upper bound of the range of user ID mappers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user ID mappers
	*/
	public java.util.List<com.liferay.portal.model.UserIdMapper> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first user ID mapper in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user ID mapper
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a matching user ID mapper could not be found
	*/
	public com.liferay.portal.model.UserIdMapper findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserIdMapperException;

	/**
	* Returns the first user ID mapper in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	*/
	public com.liferay.portal.model.UserIdMapper fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last user ID mapper in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user ID mapper
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a matching user ID mapper could not be found
	*/
	public com.liferay.portal.model.UserIdMapper findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserIdMapperException;

	/**
	* Returns the last user ID mapper in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	*/
	public com.liferay.portal.model.UserIdMapper fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the user ID mappers before and after the current user ID mapper in the ordered set where userId = &#63;.
	*
	* @param userIdMapperId the primary key of the current user ID mapper
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user ID mapper
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a user ID mapper with the primary key could not be found
	*/
	public com.liferay.portal.model.UserIdMapper[] findByUserId_PrevAndNext(
		long userIdMapperId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserIdMapperException;

	/**
	* Removes all the user ID mappers where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public void removeByUserId(long userId);

	/**
	* Returns the number of user ID mappers where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching user ID mappers
	*/
	public int countByUserId(long userId);

	/**
	* Returns the user ID mapper where userId = &#63; and type = &#63; or throws a {@link com.liferay.portal.NoSuchUserIdMapperException} if it could not be found.
	*
	* @param userId the user ID
	* @param type the type
	* @return the matching user ID mapper
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a matching user ID mapper could not be found
	*/
	public com.liferay.portal.model.UserIdMapper findByU_T(long userId,
		java.lang.String type)
		throws com.liferay.portal.NoSuchUserIdMapperException;

	/**
	* Returns the user ID mapper where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param type the type
	* @return the matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	*/
	public com.liferay.portal.model.UserIdMapper fetchByU_T(long userId,
		java.lang.String type);

	/**
	* Returns the user ID mapper where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param type the type
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	*/
	public com.liferay.portal.model.UserIdMapper fetchByU_T(long userId,
		java.lang.String type, boolean retrieveFromCache);

	/**
	* Removes the user ID mapper where userId = &#63; and type = &#63; from the database.
	*
	* @param userId the user ID
	* @param type the type
	* @return the user ID mapper that was removed
	*/
	public com.liferay.portal.model.UserIdMapper removeByU_T(long userId,
		java.lang.String type)
		throws com.liferay.portal.NoSuchUserIdMapperException;

	/**
	* Returns the number of user ID mappers where userId = &#63; and type = &#63;.
	*
	* @param userId the user ID
	* @param type the type
	* @return the number of matching user ID mappers
	*/
	public int countByU_T(long userId, java.lang.String type);

	/**
	* Returns the user ID mapper where type = &#63; and externalUserId = &#63; or throws a {@link com.liferay.portal.NoSuchUserIdMapperException} if it could not be found.
	*
	* @param type the type
	* @param externalUserId the external user ID
	* @return the matching user ID mapper
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a matching user ID mapper could not be found
	*/
	public com.liferay.portal.model.UserIdMapper findByT_E(
		java.lang.String type, java.lang.String externalUserId)
		throws com.liferay.portal.NoSuchUserIdMapperException;

	/**
	* Returns the user ID mapper where type = &#63; and externalUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param type the type
	* @param externalUserId the external user ID
	* @return the matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	*/
	public com.liferay.portal.model.UserIdMapper fetchByT_E(
		java.lang.String type, java.lang.String externalUserId);

	/**
	* Returns the user ID mapper where type = &#63; and externalUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param type the type
	* @param externalUserId the external user ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	*/
	public com.liferay.portal.model.UserIdMapper fetchByT_E(
		java.lang.String type, java.lang.String externalUserId,
		boolean retrieveFromCache);

	/**
	* Removes the user ID mapper where type = &#63; and externalUserId = &#63; from the database.
	*
	* @param type the type
	* @param externalUserId the external user ID
	* @return the user ID mapper that was removed
	*/
	public com.liferay.portal.model.UserIdMapper removeByT_E(
		java.lang.String type, java.lang.String externalUserId)
		throws com.liferay.portal.NoSuchUserIdMapperException;

	/**
	* Returns the number of user ID mappers where type = &#63; and externalUserId = &#63;.
	*
	* @param type the type
	* @param externalUserId the external user ID
	* @return the number of matching user ID mappers
	*/
	public int countByT_E(java.lang.String type, java.lang.String externalUserId);

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
	*/
	public com.liferay.portal.model.UserIdMapper remove(long userIdMapperId)
		throws com.liferay.portal.NoSuchUserIdMapperException;

	public com.liferay.portal.model.UserIdMapper updateImpl(
		com.liferay.portal.model.UserIdMapper userIdMapper);

	/**
	* Returns the user ID mapper with the primary key or throws a {@link com.liferay.portal.NoSuchUserIdMapperException} if it could not be found.
	*
	* @param userIdMapperId the primary key of the user ID mapper
	* @return the user ID mapper
	* @throws com.liferay.portal.NoSuchUserIdMapperException if a user ID mapper with the primary key could not be found
	*/
	public com.liferay.portal.model.UserIdMapper findByPrimaryKey(
		long userIdMapperId)
		throws com.liferay.portal.NoSuchUserIdMapperException;

	/**
	* Returns the user ID mapper with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param userIdMapperId the primary key of the user ID mapper
	* @return the user ID mapper, or <code>null</code> if a user ID mapper with the primary key could not be found
	*/
	public com.liferay.portal.model.UserIdMapper fetchByPrimaryKey(
		long userIdMapperId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portal.model.UserIdMapper> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the user ID mappers.
	*
	* @return the user ID mappers
	*/
	public java.util.List<com.liferay.portal.model.UserIdMapper> findAll();

	/**
	* Returns a range of all the user ID mappers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserIdMapperModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user ID mappers
	* @param end the upper bound of the range of user ID mappers (not inclusive)
	* @return the range of user ID mappers
	*/
	public java.util.List<com.liferay.portal.model.UserIdMapper> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the user ID mappers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserIdMapperModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user ID mappers
	* @param end the upper bound of the range of user ID mappers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of user ID mappers
	*/
	public java.util.List<com.liferay.portal.model.UserIdMapper> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the user ID mappers from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of user ID mappers.
	*
	* @return the number of user ID mappers
	*/
	public int countAll();
}