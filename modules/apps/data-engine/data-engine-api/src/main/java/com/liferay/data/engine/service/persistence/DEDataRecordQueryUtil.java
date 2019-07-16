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

package com.liferay.data.engine.service.persistence;

import com.liferay.data.engine.model.DEDataRecordQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the de data record query service. This utility wraps <code>com.liferay.data.engine.service.persistence.impl.DEDataRecordQueryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DEDataRecordQueryPersistence
 * @generated
 */
@ProviderType
public class DEDataRecordQueryUtil {

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
	public static void clearCache(DEDataRecordQuery deDataRecordQuery) {
		getPersistence().clearCache(deDataRecordQuery);
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
	public static Map<Serializable, DEDataRecordQuery> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DEDataRecordQuery> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DEDataRecordQuery> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DEDataRecordQuery> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DEDataRecordQuery> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DEDataRecordQuery update(
		DEDataRecordQuery deDataRecordQuery) {

		return getPersistence().update(deDataRecordQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DEDataRecordQuery update(
		DEDataRecordQuery deDataRecordQuery, ServiceContext serviceContext) {

		return getPersistence().update(deDataRecordQuery, serviceContext);
	}

	/**
	 * Returns all the de data record queries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching de data record queries
	 */
	public static List<DEDataRecordQuery> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the de data record queries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEDataRecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data record queries
	 * @param end the upper bound of the range of de data record queries (not inclusive)
	 * @return the range of matching de data record queries
	 */
	public static List<DEDataRecordQuery> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the de data record queries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEDataRecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data record queries
	 * @param end the upper bound of the range of de data record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data record queries
	 */
	public static List<DEDataRecordQuery> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DEDataRecordQuery> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the de data record queries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEDataRecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data record queries
	 * @param end the upper bound of the range of de data record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching de data record queries
	 */
	public static List<DEDataRecordQuery> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DEDataRecordQuery> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first de data record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data record query
	 * @throws NoSuchDataRecordQueryException if a matching de data record query could not be found
	 */
	public static DEDataRecordQuery findByUuid_First(
			String uuid, OrderByComparator<DEDataRecordQuery> orderByComparator)
		throws com.liferay.data.engine.exception.
			NoSuchDataRecordQueryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first de data record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data record query, or <code>null</code> if a matching de data record query could not be found
	 */
	public static DEDataRecordQuery fetchByUuid_First(
		String uuid, OrderByComparator<DEDataRecordQuery> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last de data record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data record query
	 * @throws NoSuchDataRecordQueryException if a matching de data record query could not be found
	 */
	public static DEDataRecordQuery findByUuid_Last(
			String uuid, OrderByComparator<DEDataRecordQuery> orderByComparator)
		throws com.liferay.data.engine.exception.
			NoSuchDataRecordQueryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last de data record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data record query, or <code>null</code> if a matching de data record query could not be found
	 */
	public static DEDataRecordQuery fetchByUuid_Last(
		String uuid, OrderByComparator<DEDataRecordQuery> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the de data record queries before and after the current de data record query in the ordered set where uuid = &#63;.
	 *
	 * @param deDataRecordQueryId the primary key of the current de data record query
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data record query
	 * @throws NoSuchDataRecordQueryException if a de data record query with the primary key could not be found
	 */
	public static DEDataRecordQuery[] findByUuid_PrevAndNext(
			long deDataRecordQueryId, String uuid,
			OrderByComparator<DEDataRecordQuery> orderByComparator)
		throws com.liferay.data.engine.exception.
			NoSuchDataRecordQueryException {

		return getPersistence().findByUuid_PrevAndNext(
			deDataRecordQueryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the de data record queries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of de data record queries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching de data record queries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Caches the de data record query in the entity cache if it is enabled.
	 *
	 * @param deDataRecordQuery the de data record query
	 */
	public static void cacheResult(DEDataRecordQuery deDataRecordQuery) {
		getPersistence().cacheResult(deDataRecordQuery);
	}

	/**
	 * Caches the de data record queries in the entity cache if it is enabled.
	 *
	 * @param deDataRecordQueries the de data record queries
	 */
	public static void cacheResult(
		List<DEDataRecordQuery> deDataRecordQueries) {

		getPersistence().cacheResult(deDataRecordQueries);
	}

	/**
	 * Creates a new de data record query with the primary key. Does not add the de data record query to the database.
	 *
	 * @param deDataRecordQueryId the primary key for the new de data record query
	 * @return the new de data record query
	 */
	public static DEDataRecordQuery create(long deDataRecordQueryId) {
		return getPersistence().create(deDataRecordQueryId);
	}

	/**
	 * Removes the de data record query with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deDataRecordQueryId the primary key of the de data record query
	 * @return the de data record query that was removed
	 * @throws NoSuchDataRecordQueryException if a de data record query with the primary key could not be found
	 */
	public static DEDataRecordQuery remove(long deDataRecordQueryId)
		throws com.liferay.data.engine.exception.
			NoSuchDataRecordQueryException {

		return getPersistence().remove(deDataRecordQueryId);
	}

	public static DEDataRecordQuery updateImpl(
		DEDataRecordQuery deDataRecordQuery) {

		return getPersistence().updateImpl(deDataRecordQuery);
	}

	/**
	 * Returns the de data record query with the primary key or throws a <code>NoSuchDataRecordQueryException</code> if it could not be found.
	 *
	 * @param deDataRecordQueryId the primary key of the de data record query
	 * @return the de data record query
	 * @throws NoSuchDataRecordQueryException if a de data record query with the primary key could not be found
	 */
	public static DEDataRecordQuery findByPrimaryKey(long deDataRecordQueryId)
		throws com.liferay.data.engine.exception.
			NoSuchDataRecordQueryException {

		return getPersistence().findByPrimaryKey(deDataRecordQueryId);
	}

	/**
	 * Returns the de data record query with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param deDataRecordQueryId the primary key of the de data record query
	 * @return the de data record query, or <code>null</code> if a de data record query with the primary key could not be found
	 */
	public static DEDataRecordQuery fetchByPrimaryKey(
		long deDataRecordQueryId) {

		return getPersistence().fetchByPrimaryKey(deDataRecordQueryId);
	}

	/**
	 * Returns all the de data record queries.
	 *
	 * @return the de data record queries
	 */
	public static List<DEDataRecordQuery> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the de data record queries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEDataRecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data record queries
	 * @param end the upper bound of the range of de data record queries (not inclusive)
	 * @return the range of de data record queries
	 */
	public static List<DEDataRecordQuery> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the de data record queries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEDataRecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data record queries
	 * @param end the upper bound of the range of de data record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of de data record queries
	 */
	public static List<DEDataRecordQuery> findAll(
		int start, int end,
		OrderByComparator<DEDataRecordQuery> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the de data record queries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DEDataRecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data record queries
	 * @param end the upper bound of the range of de data record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of de data record queries
	 */
	public static List<DEDataRecordQuery> findAll(
		int start, int end,
		OrderByComparator<DEDataRecordQuery> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Removes all the de data record queries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of de data record queries.
	 *
	 * @return the number of de data record queries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DEDataRecordQueryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DEDataRecordQueryPersistence, DEDataRecordQueryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DEDataRecordQueryPersistence.class);

		ServiceTracker
			<DEDataRecordQueryPersistence, DEDataRecordQueryPersistence>
				serviceTracker =
					new ServiceTracker
						<DEDataRecordQueryPersistence,
						 DEDataRecordQueryPersistence>(
							 bundle.getBundleContext(),
							 DEDataRecordQueryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}