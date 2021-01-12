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
import com.liferay.portal.tools.service.builder.test.model.ERCGroupEntity;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the erc group entity service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.ERCGroupEntityPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCGroupEntityPersistence
 * @generated
 */
public class ERCGroupEntityUtil {

	/*
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
	public static void clearCache(ERCGroupEntity ercGroupEntity) {
		getPersistence().clearCache(ercGroupEntity);
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
	public static Map<Serializable, ERCGroupEntity> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ERCGroupEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ERCGroupEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ERCGroupEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ERCGroupEntity> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ERCGroupEntity update(ERCGroupEntity ercGroupEntity) {
		return getPersistence().update(ercGroupEntity);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ERCGroupEntity update(
		ERCGroupEntity ercGroupEntity, ServiceContext serviceContext) {

		return getPersistence().update(ercGroupEntity, serviceContext);
	}

	/**
	 * Returns the erc group entity where groupId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchERCGroupEntityException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc group entity
	 * @throws NoSuchERCGroupEntityException if a matching erc group entity could not be found
	 */
	public static ERCGroupEntity findByG_ERC(
			long groupId, String externalReferenceCode)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCGroupEntityException {

		return getPersistence().findByG_ERC(groupId, externalReferenceCode);
	}

	/**
	 * Returns the erc group entity where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching erc group entity, or <code>null</code> if a matching erc group entity could not be found
	 */
	public static ERCGroupEntity fetchByG_ERC(
		long groupId, String externalReferenceCode) {

		return getPersistence().fetchByG_ERC(groupId, externalReferenceCode);
	}

	/**
	 * Returns the erc group entity where groupId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching erc group entity, or <code>null</code> if a matching erc group entity could not be found
	 */
	public static ERCGroupEntity fetchByG_ERC(
		long groupId, String externalReferenceCode, boolean useFinderCache) {

		return getPersistence().fetchByG_ERC(
			groupId, externalReferenceCode, useFinderCache);
	}

	/**
	 * Removes the erc group entity where groupId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the erc group entity that was removed
	 */
	public static ERCGroupEntity removeByG_ERC(
			long groupId, String externalReferenceCode)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCGroupEntityException {

		return getPersistence().removeByG_ERC(groupId, externalReferenceCode);
	}

	/**
	 * Returns the number of erc group entities where groupId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param groupId the group ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching erc group entities
	 */
	public static int countByG_ERC(long groupId, String externalReferenceCode) {
		return getPersistence().countByG_ERC(groupId, externalReferenceCode);
	}

	/**
	 * Caches the erc group entity in the entity cache if it is enabled.
	 *
	 * @param ercGroupEntity the erc group entity
	 */
	public static void cacheResult(ERCGroupEntity ercGroupEntity) {
		getPersistence().cacheResult(ercGroupEntity);
	}

	/**
	 * Caches the erc group entities in the entity cache if it is enabled.
	 *
	 * @param ercGroupEntities the erc group entities
	 */
	public static void cacheResult(List<ERCGroupEntity> ercGroupEntities) {
		getPersistence().cacheResult(ercGroupEntities);
	}

	/**
	 * Creates a new erc group entity with the primary key. Does not add the erc group entity to the database.
	 *
	 * @param ercGroupEntityId the primary key for the new erc group entity
	 * @return the new erc group entity
	 */
	public static ERCGroupEntity create(long ercGroupEntityId) {
		return getPersistence().create(ercGroupEntityId);
	}

	/**
	 * Removes the erc group entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ercGroupEntityId the primary key of the erc group entity
	 * @return the erc group entity that was removed
	 * @throws NoSuchERCGroupEntityException if a erc group entity with the primary key could not be found
	 */
	public static ERCGroupEntity remove(long ercGroupEntityId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCGroupEntityException {

		return getPersistence().remove(ercGroupEntityId);
	}

	public static ERCGroupEntity updateImpl(ERCGroupEntity ercGroupEntity) {
		return getPersistence().updateImpl(ercGroupEntity);
	}

	/**
	 * Returns the erc group entity with the primary key or throws a <code>NoSuchERCGroupEntityException</code> if it could not be found.
	 *
	 * @param ercGroupEntityId the primary key of the erc group entity
	 * @return the erc group entity
	 * @throws NoSuchERCGroupEntityException if a erc group entity with the primary key could not be found
	 */
	public static ERCGroupEntity findByPrimaryKey(long ercGroupEntityId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchERCGroupEntityException {

		return getPersistence().findByPrimaryKey(ercGroupEntityId);
	}

	/**
	 * Returns the erc group entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ercGroupEntityId the primary key of the erc group entity
	 * @return the erc group entity, or <code>null</code> if a erc group entity with the primary key could not be found
	 */
	public static ERCGroupEntity fetchByPrimaryKey(long ercGroupEntityId) {
		return getPersistence().fetchByPrimaryKey(ercGroupEntityId);
	}

	/**
	 * Returns all the erc group entities.
	 *
	 * @return the erc group entities
	 */
	public static List<ERCGroupEntity> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the erc group entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCGroupEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entities
	 * @param end the upper bound of the range of erc group entities (not inclusive)
	 * @return the range of erc group entities
	 */
	public static List<ERCGroupEntity> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the erc group entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCGroupEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entities
	 * @param end the upper bound of the range of erc group entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of erc group entities
	 */
	public static List<ERCGroupEntity> findAll(
		int start, int end,
		OrderByComparator<ERCGroupEntity> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the erc group entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ERCGroupEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entities
	 * @param end the upper bound of the range of erc group entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of erc group entities
	 */
	public static List<ERCGroupEntity> findAll(
		int start, int end, OrderByComparator<ERCGroupEntity> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the erc group entities from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of erc group entities.
	 *
	 * @return the number of erc group entities
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ERCGroupEntityPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ERCGroupEntityPersistence, ERCGroupEntityPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ERCGroupEntityPersistence.class);

		ServiceTracker<ERCGroupEntityPersistence, ERCGroupEntityPersistence>
			serviceTracker =
				new ServiceTracker
					<ERCGroupEntityPersistence, ERCGroupEntityPersistence>(
						bundle.getBundleContext(),
						ERCGroupEntityPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}