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
import com.liferay.portal.tools.service.builder.test.model.EagerBlobEntity;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the eager blob entity service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.EagerBlobEntityPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see EagerBlobEntityPersistence
 * @generated
 */
public class EagerBlobEntityUtil {

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
	public static void clearCache(EagerBlobEntity eagerBlobEntity) {
		getPersistence().clearCache(eagerBlobEntity);
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
	public static Map<Serializable, EagerBlobEntity> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<EagerBlobEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<EagerBlobEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<EagerBlobEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<EagerBlobEntity> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static EagerBlobEntity update(EagerBlobEntity eagerBlobEntity) {
		return getPersistence().update(eagerBlobEntity);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static EagerBlobEntity update(
		EagerBlobEntity eagerBlobEntity, ServiceContext serviceContext) {

		return getPersistence().update(eagerBlobEntity, serviceContext);
	}

	/**
	 * Returns all the eager blob entities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching eager blob entities
	 */
	public static List<EagerBlobEntity> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the eager blob entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @return the range of matching eager blob entities
	 */
	public static List<EagerBlobEntity> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the eager blob entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching eager blob entities
	 */
	public static List<EagerBlobEntity> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<EagerBlobEntity> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the eager blob entities where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching eager blob entities
	 */
	public static List<EagerBlobEntity> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<EagerBlobEntity> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first eager blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching eager blob entity
	 * @throws NoSuchEagerBlobEntityException if a matching eager blob entity could not be found
	 */
	public static EagerBlobEntity findByUuid_First(
			String uuid, OrderByComparator<EagerBlobEntity> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchEagerBlobEntityException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first eager blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching eager blob entity, or <code>null</code> if a matching eager blob entity could not be found
	 */
	public static EagerBlobEntity fetchByUuid_First(
		String uuid, OrderByComparator<EagerBlobEntity> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last eager blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching eager blob entity
	 * @throws NoSuchEagerBlobEntityException if a matching eager blob entity could not be found
	 */
	public static EagerBlobEntity findByUuid_Last(
			String uuid, OrderByComparator<EagerBlobEntity> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchEagerBlobEntityException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last eager blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching eager blob entity, or <code>null</code> if a matching eager blob entity could not be found
	 */
	public static EagerBlobEntity fetchByUuid_Last(
		String uuid, OrderByComparator<EagerBlobEntity> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the eager blob entities before and after the current eager blob entity in the ordered set where uuid = &#63;.
	 *
	 * @param eagerBlobEntityId the primary key of the current eager blob entity
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next eager blob entity
	 * @throws NoSuchEagerBlobEntityException if a eager blob entity with the primary key could not be found
	 */
	public static EagerBlobEntity[] findByUuid_PrevAndNext(
			long eagerBlobEntityId, String uuid,
			OrderByComparator<EagerBlobEntity> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchEagerBlobEntityException {

		return getPersistence().findByUuid_PrevAndNext(
			eagerBlobEntityId, uuid, orderByComparator);
	}

	/**
	 * Removes all the eager blob entities where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of eager blob entities where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching eager blob entities
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the eager blob entity where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEagerBlobEntityException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching eager blob entity
	 * @throws NoSuchEagerBlobEntityException if a matching eager blob entity could not be found
	 */
	public static EagerBlobEntity findByUUID_G(String uuid, long groupId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchEagerBlobEntityException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the eager blob entity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching eager blob entity, or <code>null</code> if a matching eager blob entity could not be found
	 */
	public static EagerBlobEntity fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the eager blob entity where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching eager blob entity, or <code>null</code> if a matching eager blob entity could not be found
	 */
	public static EagerBlobEntity fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the eager blob entity where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the eager blob entity that was removed
	 */
	public static EagerBlobEntity removeByUUID_G(String uuid, long groupId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchEagerBlobEntityException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of eager blob entities where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching eager blob entities
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Caches the eager blob entity in the entity cache if it is enabled.
	 *
	 * @param eagerBlobEntity the eager blob entity
	 */
	public static void cacheResult(EagerBlobEntity eagerBlobEntity) {
		getPersistence().cacheResult(eagerBlobEntity);
	}

	/**
	 * Caches the eager blob entities in the entity cache if it is enabled.
	 *
	 * @param eagerBlobEntities the eager blob entities
	 */
	public static void cacheResult(List<EagerBlobEntity> eagerBlobEntities) {
		getPersistence().cacheResult(eagerBlobEntities);
	}

	/**
	 * Creates a new eager blob entity with the primary key. Does not add the eager blob entity to the database.
	 *
	 * @param eagerBlobEntityId the primary key for the new eager blob entity
	 * @return the new eager blob entity
	 */
	public static EagerBlobEntity create(long eagerBlobEntityId) {
		return getPersistence().create(eagerBlobEntityId);
	}

	/**
	 * Removes the eager blob entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param eagerBlobEntityId the primary key of the eager blob entity
	 * @return the eager blob entity that was removed
	 * @throws NoSuchEagerBlobEntityException if a eager blob entity with the primary key could not be found
	 */
	public static EagerBlobEntity remove(long eagerBlobEntityId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchEagerBlobEntityException {

		return getPersistence().remove(eagerBlobEntityId);
	}

	public static EagerBlobEntity updateImpl(EagerBlobEntity eagerBlobEntity) {
		return getPersistence().updateImpl(eagerBlobEntity);
	}

	/**
	 * Returns the eager blob entity with the primary key or throws a <code>NoSuchEagerBlobEntityException</code> if it could not be found.
	 *
	 * @param eagerBlobEntityId the primary key of the eager blob entity
	 * @return the eager blob entity
	 * @throws NoSuchEagerBlobEntityException if a eager blob entity with the primary key could not be found
	 */
	public static EagerBlobEntity findByPrimaryKey(long eagerBlobEntityId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchEagerBlobEntityException {

		return getPersistence().findByPrimaryKey(eagerBlobEntityId);
	}

	/**
	 * Returns the eager blob entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param eagerBlobEntityId the primary key of the eager blob entity
	 * @return the eager blob entity, or <code>null</code> if a eager blob entity with the primary key could not be found
	 */
	public static EagerBlobEntity fetchByPrimaryKey(long eagerBlobEntityId) {
		return getPersistence().fetchByPrimaryKey(eagerBlobEntityId);
	}

	/**
	 * Returns all the eager blob entities.
	 *
	 * @return the eager blob entities
	 */
	public static List<EagerBlobEntity> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the eager blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @return the range of eager blob entities
	 */
	public static List<EagerBlobEntity> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the eager blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of eager blob entities
	 */
	public static List<EagerBlobEntity> findAll(
		int start, int end,
		OrderByComparator<EagerBlobEntity> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the eager blob entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EagerBlobEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of eager blob entities
	 * @param end the upper bound of the range of eager blob entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of eager blob entities
	 */
	public static List<EagerBlobEntity> findAll(
		int start, int end,
		OrderByComparator<EagerBlobEntity> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the eager blob entities from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of eager blob entities.
	 *
	 * @return the number of eager blob entities
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static EagerBlobEntityPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<EagerBlobEntityPersistence, EagerBlobEntityPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			EagerBlobEntityPersistence.class);

		ServiceTracker<EagerBlobEntityPersistence, EagerBlobEntityPersistence>
			serviceTracker =
				new ServiceTracker
					<EagerBlobEntityPersistence, EagerBlobEntityPersistence>(
						bundle.getBundleContext(),
						EagerBlobEntityPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}