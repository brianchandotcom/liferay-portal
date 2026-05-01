/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.external.data.source.test.service.persistence;

import com.liferay.external.data.source.test.model.TestEntity;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the test entity service. This utility wraps <code>com.liferay.external.data.source.test.service.persistence.impl.TestEntityPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TestEntityPersistence
 * @generated
 */
public class TestEntityUtil {

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
	public static void clearCache(TestEntity testEntity) {
		getPersistence().clearCache(testEntity);
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
	public static Map<Serializable, TestEntity> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<TestEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<TestEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<TestEntity> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<TestEntity> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static TestEntity update(TestEntity testEntity) {
		return getPersistence().update(testEntity);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static TestEntity update(
		TestEntity testEntity, ServiceContext serviceContext) {

		return getPersistence().update(testEntity, serviceContext);
	}

	/**
	 * Caches the test entity in the entity cache if it is enabled.
	 *
	 * @param testEntity the test entity
	 */
	public static void cacheResult(TestEntity testEntity) {
		getPersistence().cacheResult(testEntity);
	}

	/**
	 * Caches the test entities in the entity cache if it is enabled.
	 *
	 * @param testEntities the test entities
	 */
	public static void cacheResult(List<TestEntity> testEntities) {
		getPersistence().cacheResult(testEntities);
	}

	/**
	 * Creates a new test entity with the primary key. Does not add the test entity to the database.
	 *
	 * @param id the primary key for the new test entity
	 * @return the new test entity
	 */
	public static TestEntity create(long id) {
		return getPersistence().create(id);
	}

	/**
	 * Removes the test entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the test entity
	 * @return the test entity that was removed
	 * @throws NoSuchTestEntityException if a test entity with the primary key could not be found
	 */
	public static TestEntity remove(long id)
		throws com.liferay.external.data.source.test.exception.
			NoSuchTestEntityException {

		return getPersistence().remove(id);
	}

	public static TestEntity updateImpl(TestEntity testEntity) {
		return getPersistence().updateImpl(testEntity);
	}

	/**
	 * Returns the test entity with the primary key or throws a <code>NoSuchTestEntityException</code> if it could not be found.
	 *
	 * @param id the primary key of the test entity
	 * @return the test entity
	 * @throws NoSuchTestEntityException if a test entity with the primary key could not be found
	 */
	public static TestEntity findByPrimaryKey(long id)
		throws com.liferay.external.data.source.test.exception.
			NoSuchTestEntityException {

		return getPersistence().findByPrimaryKey(id);
	}

	/**
	 * Returns the test entity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param id the primary key of the test entity
	 * @return the test entity, or <code>null</code> if a test entity with the primary key could not be found
	 */
	public static TestEntity fetchByPrimaryKey(long id) {
		return getPersistence().fetchByPrimaryKey(id);
	}

	public static TestEntityPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(TestEntityPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile TestEntityPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:933500246