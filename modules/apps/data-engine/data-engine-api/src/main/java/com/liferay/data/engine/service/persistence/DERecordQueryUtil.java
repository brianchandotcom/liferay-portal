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

import com.liferay.data.engine.model.DERecordQuery;
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
 * The persistence utility for the de record query service. This utility wraps <code>com.liferay.data.engine.service.persistence.impl.DERecordQueryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DERecordQueryPersistence
 * @generated
 */
@ProviderType
public class DERecordQueryUtil {

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
	public static void clearCache(DERecordQuery deRecordQuery) {
		getPersistence().clearCache(deRecordQuery);
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
	public static Map<Serializable, DERecordQuery> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DERecordQuery> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DERecordQuery> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DERecordQuery> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DERecordQuery> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DERecordQuery update(DERecordQuery deRecordQuery) {
		return getPersistence().update(deRecordQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DERecordQuery update(
		DERecordQuery deRecordQuery, ServiceContext serviceContext) {

		return getPersistence().update(deRecordQuery, serviceContext);
	}

	/**
	 * Returns all the de record queries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching de record queries
	 */
	public static List<DERecordQuery> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the de record queries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DERecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de record queries
	 * @param end the upper bound of the range of de record queries (not inclusive)
	 * @return the range of matching de record queries
	 */
	public static List<DERecordQuery> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the de record queries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DERecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de record queries
	 * @param end the upper bound of the range of de record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de record queries
	 */
	public static List<DERecordQuery> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DERecordQuery> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the de record queries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DERecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de record queries
	 * @param end the upper bound of the range of de record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching de record queries
	 */
	public static List<DERecordQuery> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DERecordQuery> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first de record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de record query
	 * @throws NoSuchRecordQueryException if a matching de record query could not be found
	 */
	public static DERecordQuery findByUuid_First(
			String uuid, OrderByComparator<DERecordQuery> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchRecordQueryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first de record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de record query, or <code>null</code> if a matching de record query could not be found
	 */
	public static DERecordQuery fetchByUuid_First(
		String uuid, OrderByComparator<DERecordQuery> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last de record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de record query
	 * @throws NoSuchRecordQueryException if a matching de record query could not be found
	 */
	public static DERecordQuery findByUuid_Last(
			String uuid, OrderByComparator<DERecordQuery> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchRecordQueryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last de record query in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de record query, or <code>null</code> if a matching de record query could not be found
	 */
	public static DERecordQuery fetchByUuid_Last(
		String uuid, OrderByComparator<DERecordQuery> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the de record queries before and after the current de record query in the ordered set where uuid = &#63;.
	 *
	 * @param recordQueryId the primary key of the current de record query
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de record query
	 * @throws NoSuchRecordQueryException if a de record query with the primary key could not be found
	 */
	public static DERecordQuery[] findByUuid_PrevAndNext(
			long recordQueryId, String uuid,
			OrderByComparator<DERecordQuery> orderByComparator)
		throws com.liferay.data.engine.exception.NoSuchRecordQueryException {

		return getPersistence().findByUuid_PrevAndNext(
			recordQueryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the de record queries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of de record queries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching de record queries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Caches the de record query in the entity cache if it is enabled.
	 *
	 * @param deRecordQuery the de record query
	 */
	public static void cacheResult(DERecordQuery deRecordQuery) {
		getPersistence().cacheResult(deRecordQuery);
	}

	/**
	 * Caches the de record queries in the entity cache if it is enabled.
	 *
	 * @param deRecordQueries the de record queries
	 */
	public static void cacheResult(List<DERecordQuery> deRecordQueries) {
		getPersistence().cacheResult(deRecordQueries);
	}

	/**
	 * Creates a new de record query with the primary key. Does not add the de record query to the database.
	 *
	 * @param recordQueryId the primary key for the new de record query
	 * @return the new de record query
	 */
	public static DERecordQuery create(long recordQueryId) {
		return getPersistence().create(recordQueryId);
	}

	/**
	 * Removes the de record query with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recordQueryId the primary key of the de record query
	 * @return the de record query that was removed
	 * @throws NoSuchRecordQueryException if a de record query with the primary key could not be found
	 */
	public static DERecordQuery remove(long recordQueryId)
		throws com.liferay.data.engine.exception.NoSuchRecordQueryException {

		return getPersistence().remove(recordQueryId);
	}

	public static DERecordQuery updateImpl(DERecordQuery deRecordQuery) {
		return getPersistence().updateImpl(deRecordQuery);
	}

	/**
	 * Returns the de record query with the primary key or throws a <code>NoSuchRecordQueryException</code> if it could not be found.
	 *
	 * @param recordQueryId the primary key of the de record query
	 * @return the de record query
	 * @throws NoSuchRecordQueryException if a de record query with the primary key could not be found
	 */
	public static DERecordQuery findByPrimaryKey(long recordQueryId)
		throws com.liferay.data.engine.exception.NoSuchRecordQueryException {

		return getPersistence().findByPrimaryKey(recordQueryId);
	}

	/**
	 * Returns the de record query with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param recordQueryId the primary key of the de record query
	 * @return the de record query, or <code>null</code> if a de record query with the primary key could not be found
	 */
	public static DERecordQuery fetchByPrimaryKey(long recordQueryId) {
		return getPersistence().fetchByPrimaryKey(recordQueryId);
	}

	/**
	 * Returns all the de record queries.
	 *
	 * @return the de record queries
	 */
	public static List<DERecordQuery> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the de record queries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DERecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de record queries
	 * @param end the upper bound of the range of de record queries (not inclusive)
	 * @return the range of de record queries
	 */
	public static List<DERecordQuery> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the de record queries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DERecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de record queries
	 * @param end the upper bound of the range of de record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of de record queries
	 */
	public static List<DERecordQuery> findAll(
		int start, int end,
		OrderByComparator<DERecordQuery> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the de record queries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>DERecordQueryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de record queries
	 * @param end the upper bound of the range of de record queries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of de record queries
	 */
	public static List<DERecordQuery> findAll(
		int start, int end, OrderByComparator<DERecordQuery> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Removes all the de record queries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of de record queries.
	 *
	 * @return the number of de record queries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DERecordQueryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DERecordQueryPersistence, DERecordQueryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DERecordQueryPersistence.class);

		ServiceTracker<DERecordQueryPersistence, DERecordQueryPersistence>
			serviceTracker =
				new ServiceTracker
					<DERecordQueryPersistence, DERecordQueryPersistence>(
						bundle.getBundleContext(),
						DERecordQueryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}