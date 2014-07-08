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

import com.liferay.portal.model.Shard;

/**
 * The persistence interface for the shard service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShardPersistenceImpl
 * @see ShardUtil
 * @generated
 */
@ProviderType
public interface ShardPersistence extends BasePersistence<Shard> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ShardUtil} to access the shard persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the shard where name = &#63; or throws a {@link com.liferay.portal.NoSuchShardException} if it could not be found.
	*
	* @param name the name
	* @return the matching shard
	* @throws com.liferay.portal.NoSuchShardException if a matching shard could not be found
	*/
	public com.liferay.portal.model.Shard findByName(java.lang.String name)
		throws com.liferay.portal.NoSuchShardException;

	/**
	* Returns the shard where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param name the name
	* @return the matching shard, or <code>null</code> if a matching shard could not be found
	*/
	public com.liferay.portal.model.Shard fetchByName(java.lang.String name);

	/**
	* Returns the shard where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching shard, or <code>null</code> if a matching shard could not be found
	*/
	public com.liferay.portal.model.Shard fetchByName(java.lang.String name,
		boolean retrieveFromCache);

	/**
	* Removes the shard where name = &#63; from the database.
	*
	* @param name the name
	* @return the shard that was removed
	*/
	public com.liferay.portal.model.Shard removeByName(java.lang.String name)
		throws com.liferay.portal.NoSuchShardException;

	/**
	* Returns the number of shards where name = &#63;.
	*
	* @param name the name
	* @return the number of matching shards
	*/
	public int countByName(java.lang.String name);

	/**
	* Returns the shard where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portal.NoSuchShardException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching shard
	* @throws com.liferay.portal.NoSuchShardException if a matching shard could not be found
	*/
	public com.liferay.portal.model.Shard findByC_C(long classNameId,
		long classPK) throws com.liferay.portal.NoSuchShardException;

	/**
	* Returns the shard where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching shard, or <code>null</code> if a matching shard could not be found
	*/
	public com.liferay.portal.model.Shard fetchByC_C(long classNameId,
		long classPK);

	/**
	* Returns the shard where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching shard, or <code>null</code> if a matching shard could not be found
	*/
	public com.liferay.portal.model.Shard fetchByC_C(long classNameId,
		long classPK, boolean retrieveFromCache);

	/**
	* Removes the shard where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the shard that was removed
	*/
	public com.liferay.portal.model.Shard removeByC_C(long classNameId,
		long classPK) throws com.liferay.portal.NoSuchShardException;

	/**
	* Returns the number of shards where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching shards
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Caches the shard in the entity cache if it is enabled.
	*
	* @param shard the shard
	*/
	public void cacheResult(com.liferay.portal.model.Shard shard);

	/**
	* Caches the shards in the entity cache if it is enabled.
	*
	* @param shards the shards
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.Shard> shards);

	/**
	* Creates a new shard with the primary key. Does not add the shard to the database.
	*
	* @param shardId the primary key for the new shard
	* @return the new shard
	*/
	public com.liferay.portal.model.Shard create(long shardId);

	/**
	* Removes the shard with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param shardId the primary key of the shard
	* @return the shard that was removed
	* @throws com.liferay.portal.NoSuchShardException if a shard with the primary key could not be found
	*/
	public com.liferay.portal.model.Shard remove(long shardId)
		throws com.liferay.portal.NoSuchShardException;

	public com.liferay.portal.model.Shard updateImpl(
		com.liferay.portal.model.Shard shard);

	/**
	* Returns the shard with the primary key or throws a {@link com.liferay.portal.NoSuchShardException} if it could not be found.
	*
	* @param shardId the primary key of the shard
	* @return the shard
	* @throws com.liferay.portal.NoSuchShardException if a shard with the primary key could not be found
	*/
	public com.liferay.portal.model.Shard findByPrimaryKey(long shardId)
		throws com.liferay.portal.NoSuchShardException;

	/**
	* Returns the shard with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param shardId the primary key of the shard
	* @return the shard, or <code>null</code> if a shard with the primary key could not be found
	*/
	public com.liferay.portal.model.Shard fetchByPrimaryKey(long shardId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portal.model.Shard> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the shards.
	*
	* @return the shards
	*/
	public java.util.List<com.liferay.portal.model.Shard> findAll();

	/**
	* Returns a range of all the shards.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ShardModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shards
	* @param end the upper bound of the range of shards (not inclusive)
	* @return the range of shards
	*/
	public java.util.List<com.liferay.portal.model.Shard> findAll(int start,
		int end);

	/**
	* Returns an ordered range of all the shards.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ShardModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shards
	* @param end the upper bound of the range of shards (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of shards
	*/
	public java.util.List<com.liferay.portal.model.Shard> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the shards from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of shards.
	*
	* @return the number of shards
	*/
	public int countAll();
}