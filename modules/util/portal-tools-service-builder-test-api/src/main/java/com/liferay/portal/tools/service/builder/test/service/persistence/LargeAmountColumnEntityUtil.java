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
import com.liferay.portal.tools.service.builder.test.model.LargeAmountColumnEntity;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the large amount column entity service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.LargeAmountColumnEntityPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LargeAmountColumnEntityPersistence
 * @generated
 */
public class LargeAmountColumnEntityUtil {

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
	public static void clearCache(
		LargeAmountColumnEntity largeAmountColumnEntity) {

		getPersistence().clearCache(largeAmountColumnEntity);
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
	public static Map<Serializable, LargeAmountColumnEntity> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LargeAmountColumnEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LargeAmountColumnEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LargeAmountColumnEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LargeAmountColumnEntity> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LargeAmountColumnEntity update(
		LargeAmountColumnEntity largeAmountColumnEntity) {

		return getPersistence().update(largeAmountColumnEntity);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LargeAmountColumnEntity update(
		LargeAmountColumnEntity largeAmountColumnEntity,
		ServiceContext serviceContext) {

		return getPersistence().update(largeAmountColumnEntity, serviceContext);
	}

	/**
	 * Caches the large amount column entity in the entity cache if it is enabled.
	 *
	 * @param largeAmountColumnEntity the large amount column entity
	 */
	public static void cacheResult(
		LargeAmountColumnEntity largeAmountColumnEntity) {

		getPersistence().cacheResult(largeAmountColumnEntity);
	}

	/**
	 * Caches the large amount column entities in the entity cache if it is enabled.
	 *
	 * @param largeAmountColumnEntities the large amount column entities
	 */
	public static void cacheResult(
		List<LargeAmountColumnEntity> largeAmountColumnEntities) {

		getPersistence().cacheResult(largeAmountColumnEntities);
	}

	/**
	 * Creates a new large amount column entity with the primary key. Does not add the large amount column entity to the database.
	 *
	 * @param largeAmountColumnEntityId the primary key for the new large amount column entity
	 * @return the new large amount column entity
	 */
	public static LargeAmountColumnEntity create(
		long largeAmountColumnEntityId) {

		return getPersistence().create(largeAmountColumnEntityId);
	}

	/**
	 * Removes the large amount column entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param largeAmountColumnEntityId the primary key of the large amount column entity
	 * @return the large amount column entity that was removed
	 * @throws NoSuchLargeAmountColumnEntityException if a large amount column entity with the primary key could not be found
	 */
	public static LargeAmountColumnEntity remove(long largeAmountColumnEntityId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLargeAmountColumnEntityException {

		return getPersistence().remove(largeAmountColumnEntityId);
	}

	public static LargeAmountColumnEntity updateImpl(
		LargeAmountColumnEntity largeAmountColumnEntity) {

		return getPersistence().updateImpl(largeAmountColumnEntity);
	}

	/**
	 * Returns the large amount column entity with the primary key or throws a <code>NoSuchLargeAmountColumnEntityException</code> if it could not be found.
	 *
	 * @param largeAmountColumnEntityId the primary key of the large amount column entity
	 * @return the large amount column entity
	 * @throws NoSuchLargeAmountColumnEntityException if a large amount column entity with the primary key could not be found
	 */
	public static LargeAmountColumnEntity findByPrimaryKey(
			long largeAmountColumnEntityId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLargeAmountColumnEntityException {

		return getPersistence().findByPrimaryKey(largeAmountColumnEntityId);
	}

	/**
	 * Returns the large amount column entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param largeAmountColumnEntityId the primary key of the large amount column entity
	 * @return the large amount column entity, or <code>null</code> if a large amount column entity with the primary key could not be found
	 */
	public static LargeAmountColumnEntity fetchByPrimaryKey(
		long largeAmountColumnEntityId) {

		return getPersistence().fetchByPrimaryKey(largeAmountColumnEntityId);
	}

	/**
	 * Returns all the large amount column entities.
	 *
	 * @return the large amount column entities
	 */
	public static List<LargeAmountColumnEntity> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the large amount column entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LargeAmountColumnEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of large amount column entities
	 * @param end the upper bound of the range of large amount column entities (not inclusive)
	 * @return the range of large amount column entities
	 */
	public static List<LargeAmountColumnEntity> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the large amount column entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LargeAmountColumnEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of large amount column entities
	 * @param end the upper bound of the range of large amount column entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of large amount column entities
	 */
	public static List<LargeAmountColumnEntity> findAll(
		int start, int end,
		OrderByComparator<LargeAmountColumnEntity> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the large amount column entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LargeAmountColumnEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of large amount column entities
	 * @param end the upper bound of the range of large amount column entities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of large amount column entities
	 */
	public static List<LargeAmountColumnEntity> findAll(
		int start, int end,
		OrderByComparator<LargeAmountColumnEntity> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the large amount column entities from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of large amount column entities.
	 *
	 * @return the number of large amount column entities
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LargeAmountColumnEntityPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LargeAmountColumnEntityPersistence, LargeAmountColumnEntityPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LargeAmountColumnEntityPersistence.class);

		ServiceTracker
			<LargeAmountColumnEntityPersistence,
			 LargeAmountColumnEntityPersistence> serviceTracker =
				new ServiceTracker
					<LargeAmountColumnEntityPersistence,
					 LargeAmountColumnEntityPersistence>(
						 bundle.getBundleContext(),
						 LargeAmountColumnEntityPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}