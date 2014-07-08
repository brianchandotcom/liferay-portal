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
import com.liferay.portal.model.Shard;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the shard service. This utility wraps {@link ShardPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShardPersistence
 * @see ShardPersistenceImpl
 * @generated
 */
@ProviderType
public class ShardUtil {
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
	public static void clearCache(Shard shard) {
		getPersistence().clearCache(shard);
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
	public static List<Shard> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Shard> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Shard> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static Shard update(Shard shard) {
		return getPersistence().update(shard);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static Shard update(Shard shard, ServiceContext serviceContext) {
		return getPersistence().update(shard, serviceContext);
	}

	/**
	* Returns the shard where name = &#63; or throws a {@link com.liferay.portal.NoSuchShardException} if it could not be found.
	*
	* @param name the name
	* @return the matching shard
	* @throws com.liferay.portal.NoSuchShardException if a matching shard could not be found
	*/
	public static com.liferay.portal.model.Shard findByName(
		java.lang.String name) throws com.liferay.portal.NoSuchShardException {
		return getPersistence().findByName(name);
	}

	/**
	* Returns the shard where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param name the name
	* @return the matching shard, or <code>null</code> if a matching shard could not be found
	*/
	public static com.liferay.portal.model.Shard fetchByName(
		java.lang.String name) {
		return getPersistence().fetchByName(name);
	}

	/**
	* Returns the shard where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching shard, or <code>null</code> if a matching shard could not be found
	*/
	public static com.liferay.portal.model.Shard fetchByName(
		java.lang.String name, boolean retrieveFromCache) {
		return getPersistence().fetchByName(name, retrieveFromCache);
	}

	/**
	* Removes the shard where name = &#63; from the database.
	*
	* @param name the name
	* @return the shard that was removed
	*/
	public static com.liferay.portal.model.Shard removeByName(
		java.lang.String name) throws com.liferay.portal.NoSuchShardException {
		return getPersistence().removeByName(name);
	}

	/**
	* Returns the number of shards where name = &#63;.
	*
	* @param name the name
	* @return the number of matching shards
	*/
	public static int countByName(java.lang.String name) {
		return getPersistence().countByName(name);
	}

	/**
	* Returns the shard where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portal.NoSuchShardException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching shard
	* @throws com.liferay.portal.NoSuchShardException if a matching shard could not be found
	*/
	public static com.liferay.portal.model.Shard findByC_C(long classNameId,
		long classPK) throws com.liferay.portal.NoSuchShardException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns the shard where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching shard, or <code>null</code> if a matching shard could not be found
	*/
	public static com.liferay.portal.model.Shard fetchByC_C(long classNameId,
		long classPK) {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	/**
	* Returns the shard where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching shard, or <code>null</code> if a matching shard could not be found
	*/
	public static com.liferay.portal.model.Shard fetchByC_C(long classNameId,
		long classPK, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_C(classNameId, classPK, retrieveFromCache);
	}

	/**
	* Removes the shard where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the shard that was removed
	*/
	public static com.liferay.portal.model.Shard removeByC_C(long classNameId,
		long classPK) throws com.liferay.portal.NoSuchShardException {
		return getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of shards where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching shards
	*/
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Caches the shard in the entity cache if it is enabled.
	*
	* @param shard the shard
	*/
	public static void cacheResult(com.liferay.portal.model.Shard shard) {
		getPersistence().cacheResult(shard);
	}

	/**
	* Caches the shards in the entity cache if it is enabled.
	*
	* @param shards the shards
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Shard> shards) {
		getPersistence().cacheResult(shards);
	}

	/**
	* Creates a new shard with the primary key. Does not add the shard to the database.
	*
	* @param shardId the primary key for the new shard
	* @return the new shard
	*/
	public static com.liferay.portal.model.Shard create(long shardId) {
		return getPersistence().create(shardId);
	}

	/**
	* Removes the shard with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param shardId the primary key of the shard
	* @return the shard that was removed
	* @throws com.liferay.portal.NoSuchShardException if a shard with the primary key could not be found
	*/
	public static com.liferay.portal.model.Shard remove(long shardId)
		throws com.liferay.portal.NoSuchShardException {
		return getPersistence().remove(shardId);
	}

	public static com.liferay.portal.model.Shard updateImpl(
		com.liferay.portal.model.Shard shard) {
		return getPersistence().updateImpl(shard);
	}

	/**
	* Returns the shard with the primary key or throws a {@link com.liferay.portal.NoSuchShardException} if it could not be found.
	*
	* @param shardId the primary key of the shard
	* @return the shard
	* @throws com.liferay.portal.NoSuchShardException if a shard with the primary key could not be found
	*/
	public static com.liferay.portal.model.Shard findByPrimaryKey(long shardId)
		throws com.liferay.portal.NoSuchShardException {
		return getPersistence().findByPrimaryKey(shardId);
	}

	/**
	* Returns the shard with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param shardId the primary key of the shard
	* @return the shard, or <code>null</code> if a shard with the primary key could not be found
	*/
	public static com.liferay.portal.model.Shard fetchByPrimaryKey(long shardId) {
		return getPersistence().fetchByPrimaryKey(shardId);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.portal.model.Shard> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the shards.
	*
	* @return the shards
	*/
	public static java.util.List<com.liferay.portal.model.Shard> findAll() {
		return getPersistence().findAll();
	}

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
	public static java.util.List<com.liferay.portal.model.Shard> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.liferay.portal.model.Shard> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the shards from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of shards.
	*
	* @return the number of shards
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ShardPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ShardPersistence)PortalBeanLocatorUtil.locate(ShardPersistence.class.getName());

			ReferenceRegistry.registerReference(ShardUtil.class, "_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(ShardPersistence persistence) {
	}

	private static ShardPersistence _persistence;
}