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

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the lazy blob entity service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.LazyBlobEntityPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LazyBlobEntityPersistence
 * @generated
 */
public class LazyBlobEntityUtil {

	/**
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
	public static void clearCache(LazyBlobEntity lazyBlobEntity) {
		getPersistence().clearCache(lazyBlobEntity);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, LazyBlobEntity> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LazyBlobEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LazyBlobEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LazyBlobEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LazyBlobEntity> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LazyBlobEntity update(LazyBlobEntity lazyBlobEntity) {
		return getPersistence().update(lazyBlobEntity);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LazyBlobEntity update(
		LazyBlobEntity lazyBlobEntity, ServiceContext serviceContext) {

		return getPersistence().update(lazyBlobEntity, serviceContext);
	}

	/**
	 * Returns all the lazy blob entities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching lazy blob entities
	 */
	public static List<LazyBlobEntity> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the lazy blob entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lazy blob entities
	 * @param end the upper bound of the range of lazy blob entities (not inclusive)
	 * @return the range of matching lazy blob entities
	 */
	public static List<LazyBlobEntity> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the lazy blob entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lazy blob entities
	 * @param end the upper bound of the range of lazy blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lazy blob entities
	 */
	public static List<LazyBlobEntity> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LazyBlobEntity> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the lazy blob entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lazy blob entities
	 * @param end the upper bound of the range of lazy blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lazy blob entities
	 */
	public static List<LazyBlobEntity> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LazyBlobEntity> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first lazy blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lazy blob entity
	 * @throws NoSuchLazyBlobEntityException if a matching lazy blob entity could not be found
	 */
	public static LazyBlobEntity findByUuid_First(
			String uuid, OrderByComparator<LazyBlobEntity> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLazyBlobEntityException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first lazy blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lazy blob entity, or <code>null</code> if a matching lazy blob entity could not be found
	 */
	public static LazyBlobEntity fetchByUuid_First(
		String uuid, OrderByComparator<LazyBlobEntity> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last lazy blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lazy blob entity
	 * @throws NoSuchLazyBlobEntityException if a matching lazy blob entity could not be found
	 */
	public static LazyBlobEntity findByUuid_Last(
			String uuid, OrderByComparator<LazyBlobEntity> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLazyBlobEntityException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last lazy blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lazy blob entity, or <code>null</code> if a matching lazy blob entity could not be found
	 */
	public static LazyBlobEntity fetchByUuid_Last(
		String uuid, OrderByComparator<LazyBlobEntity> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the lazy blob entities before and after the current lazy blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param lazyBlobEntityId the primary key of the current lazy blob entity
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lazy blob entity
	 * @throws NoSuchLazyBlobEntityException if a lazy blob entity with the primary key could not be found
	 */
	public static LazyBlobEntity[] findByUuid_PrevAndNext(
			long lazyBlobEntityId, String uuid,
			OrderByComparator<LazyBlobEntity> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLazyBlobEntityException {

		return getPersistence().findByUuid_PrevAndNext(
			lazyBlobEntityId, uuid, orderByComparator);
	}

	/**
	 * Removes all the lazy blob entities where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of lazy blob entities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching lazy blob entities
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the lazy blob entity where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchLazyBlobEntityException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching lazy blob entity
	 * @throws NoSuchLazyBlobEntityException if a matching lazy blob entity could not be found
	 */
	public static LazyBlobEntity findByUUID_G(String uuid, long groupId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLazyBlobEntityException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the lazy blob entity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching lazy blob entity, or <code>null</code> if a matching lazy blob entity could not be found
	 */
	public static LazyBlobEntity fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the lazy blob entity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lazy blob entity, or <code>null</code> if a matching lazy blob entity could not be found
	 */
	public static LazyBlobEntity fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the lazy blob entity where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the lazy blob entity that was removed
	 */
	public static LazyBlobEntity removeByUUID_G(String uuid, long groupId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLazyBlobEntityException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of lazy blob entities where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching lazy blob entities
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Caches the lazy blob entity in the entity cache if it is enabled.
	 *
	 * @param lazyBlobEntity the lazy blob entity
	 */
	public static void cacheResult(LazyBlobEntity lazyBlobEntity) {
		getPersistence().cacheResult(lazyBlobEntity);
	}

	/**
	 * Caches the lazy blob entities in the entity cache if it is enabled.
	 *
	 * @param lazyBlobEntities the lazy blob entities
	 */
	public static void cacheResult(List<LazyBlobEntity> lazyBlobEntities) {
		getPersistence().cacheResult(lazyBlobEntities);
	}

	/**
	 * Creates a new lazy blob entity with the primary key. Does not add the lazy blob entity to the database.
	 *
	 * @param lazyBlobEntityId the primary key for the new lazy blob entity
	 * @return the new lazy blob entity
	 */
	public static LazyBlobEntity create(long lazyBlobEntityId) {
		return getPersistence().create(lazyBlobEntityId);
	}

	/**
	 * Removes the lazy blob entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lazyBlobEntityId the primary key of the lazy blob entity
	 * @return the lazy blob entity that was removed
	 * @throws NoSuchLazyBlobEntityException if a lazy blob entity with the primary key could not be found
	 */
	public static LazyBlobEntity remove(long lazyBlobEntityId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLazyBlobEntityException {

		return getPersistence().remove(lazyBlobEntityId);
	}

	public static LazyBlobEntity updateImpl(LazyBlobEntity lazyBlobEntity) {
		return getPersistence().updateImpl(lazyBlobEntity);
	}

	/**
	 * Returns the lazy blob entity with the primary key or throws a <code>NoSuchLazyBlobEntityException</code> if it could not be found.
	 *
	 * @param lazyBlobEntityId the primary key of the lazy blob entity
	 * @return the lazy blob entity
	 * @throws NoSuchLazyBlobEntityException if a lazy blob entity with the primary key could not be found
	 */
	public static LazyBlobEntity findByPrimaryKey(long lazyBlobEntityId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLazyBlobEntityException {

		return getPersistence().findByPrimaryKey(lazyBlobEntityId);
	}

	/**
	 * Returns the lazy blob entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param lazyBlobEntityId the primary key of the lazy blob entity
	 * @return the lazy blob entity, or <code>null</code> if a lazy blob entity with the primary key could not be found
	 */
	public static LazyBlobEntity fetchByPrimaryKey(long lazyBlobEntityId) {
		return getPersistence().fetchByPrimaryKey(lazyBlobEntityId);
	}

	/**
	 * Returns all the lazy blob entities.
	 *
	 * @return the lazy blob entities
	 */
	public static List<LazyBlobEntity> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the lazy blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lazy blob entities
	 * @param end the upper bound of the range of lazy blob entities (not inclusive)
	 * @return the range of lazy blob entities
	 */
	public static List<LazyBlobEntity> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the lazy blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lazy blob entities
	 * @param end the upper bound of the range of lazy blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lazy blob entities
	 */
	public static List<LazyBlobEntity> findAll(
		int start, int end,
		OrderByComparator<LazyBlobEntity> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the lazy blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LazyBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lazy blob entities
	 * @param end the upper bound of the range of lazy blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of lazy blob entities
	 */
	public static List<LazyBlobEntity> findAll(
		int start, int end, OrderByComparator<LazyBlobEntity> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the lazy blob entities from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of lazy blob entities.
	 *
	 * @return the number of lazy blob entities
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LazyBlobEntityPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LazyBlobEntityPersistence, LazyBlobEntityPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LazyBlobEntityPersistence.class);

		ServiceTracker<LazyBlobEntityPersistence, LazyBlobEntityPersistence>
			serviceTracker =
				new ServiceTracker
					<LazyBlobEntityPersistence, LazyBlobEntityPersistence>(
						bundle.getBundleContext(),
						LazyBlobEntityPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}