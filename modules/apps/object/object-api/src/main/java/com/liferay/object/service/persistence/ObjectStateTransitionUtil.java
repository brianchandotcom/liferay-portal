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

package com.liferay.object.service.persistence;

import com.liferay.object.model.ObjectStateTransition;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the object state transition service. This utility wraps <code>com.liferay.object.service.persistence.impl.ObjectStateTransitionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectStateTransitionPersistence
 * @generated
 */
public class ObjectStateTransitionUtil {

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
	public static void clearCache(ObjectStateTransition objectStateTransition) {
		getPersistence().clearCache(objectStateTransition);
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
	public static Map<Serializable, ObjectStateTransition> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ObjectStateTransition> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ObjectStateTransition> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ObjectStateTransition> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ObjectStateTransition update(
		ObjectStateTransition objectStateTransition) {

		return getPersistence().update(objectStateTransition);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ObjectStateTransition update(
		ObjectStateTransition objectStateTransition,
		ServiceContext serviceContext) {

		return getPersistence().update(objectStateTransition, serviceContext);
	}

	/**
	 * Returns all the object state transitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object state transitions
	 */
	public static List<ObjectStateTransition> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the object state transitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @return the range of matching object state transitions
	 */
	public static List<ObjectStateTransition> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the object state transitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state transitions
	 */
	public static List<ObjectStateTransition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object state transitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state transitions
	 */
	public static List<ObjectStateTransition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object state transition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public static ObjectStateTransition findByUuid_First(
			String uuid,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectStateTransitionException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first object state transition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public static ObjectStateTransition fetchByUuid_First(
		String uuid,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last object state transition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public static ObjectStateTransition findByUuid_Last(
			String uuid,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectStateTransitionException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last object state transition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public static ObjectStateTransition fetchByUuid_Last(
		String uuid,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the object state transitions before and after the current object state transition in the ordered set where uuid = &#63;.
	 *
	 * @param objectStateTransitionId the primary key of the current object state transition
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state transition
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	public static ObjectStateTransition[] findByUuid_PrevAndNext(
			long objectStateTransitionId, String uuid,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectStateTransitionException {

		return getPersistence().findByUuid_PrevAndNext(
			objectStateTransitionId, uuid, orderByComparator);
	}

	/**
	 * Removes all the object state transitions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of object state transitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object state transitions
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the object state transitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object state transitions
	 */
	public static List<ObjectStateTransition> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the object state transitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @return the range of matching object state transitions
	 */
	public static List<ObjectStateTransition> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object state transitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state transitions
	 */
	public static List<ObjectStateTransition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object state transitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state transitions
	 */
	public static List<ObjectStateTransition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object state transition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public static ObjectStateTransition findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectStateTransitionException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first object state transition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public static ObjectStateTransition fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object state transition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public static ObjectStateTransition findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectStateTransitionException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object state transition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public static ObjectStateTransition fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the object state transitions before and after the current object state transition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectStateTransitionId the primary key of the current object state transition
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state transition
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	public static ObjectStateTransition[] findByUuid_C_PrevAndNext(
			long objectStateTransitionId, String uuid, long companyId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectStateTransitionException {

		return getPersistence().findByUuid_C_PrevAndNext(
			objectStateTransitionId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the object state transitions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of object state transitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object state transitions
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the object state transitions where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @return the matching object state transitions
	 */
	public static List<ObjectStateTransition> findByObjectStateFlowId(
		long objectStateFlowId) {

		return getPersistence().findByObjectStateFlowId(objectStateFlowId);
	}

	/**
	 * Returns a range of all the object state transitions where objectStateFlowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @return the range of matching object state transitions
	 */
	public static List<ObjectStateTransition> findByObjectStateFlowId(
		long objectStateFlowId, int start, int end) {

		return getPersistence().findByObjectStateFlowId(
			objectStateFlowId, start, end);
	}

	/**
	 * Returns an ordered range of all the object state transitions where objectStateFlowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state transitions
	 */
	public static List<ObjectStateTransition> findByObjectStateFlowId(
		long objectStateFlowId, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return getPersistence().findByObjectStateFlowId(
			objectStateFlowId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object state transitions where objectStateFlowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state transitions
	 */
	public static List<ObjectStateTransition> findByObjectStateFlowId(
		long objectStateFlowId, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByObjectStateFlowId(
			objectStateFlowId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object state transition in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public static ObjectStateTransition findByObjectStateFlowId_First(
			long objectStateFlowId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectStateTransitionException {

		return getPersistence().findByObjectStateFlowId_First(
			objectStateFlowId, orderByComparator);
	}

	/**
	 * Returns the first object state transition in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public static ObjectStateTransition fetchByObjectStateFlowId_First(
		long objectStateFlowId,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return getPersistence().fetchByObjectStateFlowId_First(
			objectStateFlowId, orderByComparator);
	}

	/**
	 * Returns the last object state transition in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public static ObjectStateTransition findByObjectStateFlowId_Last(
			long objectStateFlowId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectStateTransitionException {

		return getPersistence().findByObjectStateFlowId_Last(
			objectStateFlowId, orderByComparator);
	}

	/**
	 * Returns the last object state transition in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public static ObjectStateTransition fetchByObjectStateFlowId_Last(
		long objectStateFlowId,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return getPersistence().fetchByObjectStateFlowId_Last(
			objectStateFlowId, orderByComparator);
	}

	/**
	 * Returns the object state transitions before and after the current object state transition in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateTransitionId the primary key of the current object state transition
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state transition
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	public static ObjectStateTransition[] findByObjectStateFlowId_PrevAndNext(
			long objectStateTransitionId, long objectStateFlowId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectStateTransitionException {

		return getPersistence().findByObjectStateFlowId_PrevAndNext(
			objectStateTransitionId, objectStateFlowId, orderByComparator);
	}

	/**
	 * Removes all the object state transitions where objectStateFlowId = &#63; from the database.
	 *
	 * @param objectStateFlowId the object state flow ID
	 */
	public static void removeByObjectStateFlowId(long objectStateFlowId) {
		getPersistence().removeByObjectStateFlowId(objectStateFlowId);
	}

	/**
	 * Returns the number of object state transitions where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @return the number of matching object state transitions
	 */
	public static int countByObjectStateFlowId(long objectStateFlowId) {
		return getPersistence().countByObjectStateFlowId(objectStateFlowId);
	}

	/**
	 * Returns all the object state transitions where sourceObjectStateId = &#63;.
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @return the matching object state transitions
	 */
	public static List<ObjectStateTransition> findBySourceObjectStateId(
		long sourceObjectStateId) {

		return getPersistence().findBySourceObjectStateId(sourceObjectStateId);
	}

	/**
	 * Returns a range of all the object state transitions where sourceObjectStateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @return the range of matching object state transitions
	 */
	public static List<ObjectStateTransition> findBySourceObjectStateId(
		long sourceObjectStateId, int start, int end) {

		return getPersistence().findBySourceObjectStateId(
			sourceObjectStateId, start, end);
	}

	/**
	 * Returns an ordered range of all the object state transitions where sourceObjectStateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state transitions
	 */
	public static List<ObjectStateTransition> findBySourceObjectStateId(
		long sourceObjectStateId, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return getPersistence().findBySourceObjectStateId(
			sourceObjectStateId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object state transitions where sourceObjectStateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state transitions
	 */
	public static List<ObjectStateTransition> findBySourceObjectStateId(
		long sourceObjectStateId, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findBySourceObjectStateId(
			sourceObjectStateId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object state transition in the ordered set where sourceObjectStateId = &#63;.
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public static ObjectStateTransition findBySourceObjectStateId_First(
			long sourceObjectStateId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectStateTransitionException {

		return getPersistence().findBySourceObjectStateId_First(
			sourceObjectStateId, orderByComparator);
	}

	/**
	 * Returns the first object state transition in the ordered set where sourceObjectStateId = &#63;.
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public static ObjectStateTransition fetchBySourceObjectStateId_First(
		long sourceObjectStateId,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return getPersistence().fetchBySourceObjectStateId_First(
			sourceObjectStateId, orderByComparator);
	}

	/**
	 * Returns the last object state transition in the ordered set where sourceObjectStateId = &#63;.
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public static ObjectStateTransition findBySourceObjectStateId_Last(
			long sourceObjectStateId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectStateTransitionException {

		return getPersistence().findBySourceObjectStateId_Last(
			sourceObjectStateId, orderByComparator);
	}

	/**
	 * Returns the last object state transition in the ordered set where sourceObjectStateId = &#63;.
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public static ObjectStateTransition fetchBySourceObjectStateId_Last(
		long sourceObjectStateId,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return getPersistence().fetchBySourceObjectStateId_Last(
			sourceObjectStateId, orderByComparator);
	}

	/**
	 * Returns the object state transitions before and after the current object state transition in the ordered set where sourceObjectStateId = &#63;.
	 *
	 * @param objectStateTransitionId the primary key of the current object state transition
	 * @param sourceObjectStateId the source object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state transition
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	public static ObjectStateTransition[] findBySourceObjectStateId_PrevAndNext(
			long objectStateTransitionId, long sourceObjectStateId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectStateTransitionException {

		return getPersistence().findBySourceObjectStateId_PrevAndNext(
			objectStateTransitionId, sourceObjectStateId, orderByComparator);
	}

	/**
	 * Removes all the object state transitions where sourceObjectStateId = &#63; from the database.
	 *
	 * @param sourceObjectStateId the source object state ID
	 */
	public static void removeBySourceObjectStateId(long sourceObjectStateId) {
		getPersistence().removeBySourceObjectStateId(sourceObjectStateId);
	}

	/**
	 * Returns the number of object state transitions where sourceObjectStateId = &#63;.
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @return the number of matching object state transitions
	 */
	public static int countBySourceObjectStateId(long sourceObjectStateId) {
		return getPersistence().countBySourceObjectStateId(sourceObjectStateId);
	}

	/**
	 * Returns all the object state transitions where targetObjectStateId = &#63;.
	 *
	 * @param targetObjectStateId the target object state ID
	 * @return the matching object state transitions
	 */
	public static List<ObjectStateTransition> findByTargetObjectStateId(
		long targetObjectStateId) {

		return getPersistence().findByTargetObjectStateId(targetObjectStateId);
	}

	/**
	 * Returns a range of all the object state transitions where targetObjectStateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @return the range of matching object state transitions
	 */
	public static List<ObjectStateTransition> findByTargetObjectStateId(
		long targetObjectStateId, int start, int end) {

		return getPersistence().findByTargetObjectStateId(
			targetObjectStateId, start, end);
	}

	/**
	 * Returns an ordered range of all the object state transitions where targetObjectStateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state transitions
	 */
	public static List<ObjectStateTransition> findByTargetObjectStateId(
		long targetObjectStateId, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return getPersistence().findByTargetObjectStateId(
			targetObjectStateId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object state transitions where targetObjectStateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state transitions
	 */
	public static List<ObjectStateTransition> findByTargetObjectStateId(
		long targetObjectStateId, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByTargetObjectStateId(
			targetObjectStateId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object state transition in the ordered set where targetObjectStateId = &#63;.
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public static ObjectStateTransition findByTargetObjectStateId_First(
			long targetObjectStateId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectStateTransitionException {

		return getPersistence().findByTargetObjectStateId_First(
			targetObjectStateId, orderByComparator);
	}

	/**
	 * Returns the first object state transition in the ordered set where targetObjectStateId = &#63;.
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public static ObjectStateTransition fetchByTargetObjectStateId_First(
		long targetObjectStateId,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return getPersistence().fetchByTargetObjectStateId_First(
			targetObjectStateId, orderByComparator);
	}

	/**
	 * Returns the last object state transition in the ordered set where targetObjectStateId = &#63;.
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	public static ObjectStateTransition findByTargetObjectStateId_Last(
			long targetObjectStateId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectStateTransitionException {

		return getPersistence().findByTargetObjectStateId_Last(
			targetObjectStateId, orderByComparator);
	}

	/**
	 * Returns the last object state transition in the ordered set where targetObjectStateId = &#63;.
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	public static ObjectStateTransition fetchByTargetObjectStateId_Last(
		long targetObjectStateId,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return getPersistence().fetchByTargetObjectStateId_Last(
			targetObjectStateId, orderByComparator);
	}

	/**
	 * Returns the object state transitions before and after the current object state transition in the ordered set where targetObjectStateId = &#63;.
	 *
	 * @param objectStateTransitionId the primary key of the current object state transition
	 * @param targetObjectStateId the target object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state transition
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	public static ObjectStateTransition[] findByTargetObjectStateId_PrevAndNext(
			long objectStateTransitionId, long targetObjectStateId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws com.liferay.object.exception.
			NoSuchObjectStateTransitionException {

		return getPersistence().findByTargetObjectStateId_PrevAndNext(
			objectStateTransitionId, targetObjectStateId, orderByComparator);
	}

	/**
	 * Removes all the object state transitions where targetObjectStateId = &#63; from the database.
	 *
	 * @param targetObjectStateId the target object state ID
	 */
	public static void removeByTargetObjectStateId(long targetObjectStateId) {
		getPersistence().removeByTargetObjectStateId(targetObjectStateId);
	}

	/**
	 * Returns the number of object state transitions where targetObjectStateId = &#63;.
	 *
	 * @param targetObjectStateId the target object state ID
	 * @return the number of matching object state transitions
	 */
	public static int countByTargetObjectStateId(long targetObjectStateId) {
		return getPersistence().countByTargetObjectStateId(targetObjectStateId);
	}

	/**
	 * Caches the object state transition in the entity cache if it is enabled.
	 *
	 * @param objectStateTransition the object state transition
	 */
	public static void cacheResult(
		ObjectStateTransition objectStateTransition) {

		getPersistence().cacheResult(objectStateTransition);
	}

	/**
	 * Caches the object state transitions in the entity cache if it is enabled.
	 *
	 * @param objectStateTransitions the object state transitions
	 */
	public static void cacheResult(
		List<ObjectStateTransition> objectStateTransitions) {

		getPersistence().cacheResult(objectStateTransitions);
	}

	/**
	 * Creates a new object state transition with the primary key. Does not add the object state transition to the database.
	 *
	 * @param objectStateTransitionId the primary key for the new object state transition
	 * @return the new object state transition
	 */
	public static ObjectStateTransition create(long objectStateTransitionId) {
		return getPersistence().create(objectStateTransitionId);
	}

	/**
	 * Removes the object state transition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectStateTransitionId the primary key of the object state transition
	 * @return the object state transition that was removed
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	public static ObjectStateTransition remove(long objectStateTransitionId)
		throws com.liferay.object.exception.
			NoSuchObjectStateTransitionException {

		return getPersistence().remove(objectStateTransitionId);
	}

	public static ObjectStateTransition updateImpl(
		ObjectStateTransition objectStateTransition) {

		return getPersistence().updateImpl(objectStateTransition);
	}

	/**
	 * Returns the object state transition with the primary key or throws a <code>NoSuchObjectStateTransitionException</code> if it could not be found.
	 *
	 * @param objectStateTransitionId the primary key of the object state transition
	 * @return the object state transition
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	public static ObjectStateTransition findByPrimaryKey(
			long objectStateTransitionId)
		throws com.liferay.object.exception.
			NoSuchObjectStateTransitionException {

		return getPersistence().findByPrimaryKey(objectStateTransitionId);
	}

	/**
	 * Returns the object state transition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectStateTransitionId the primary key of the object state transition
	 * @return the object state transition, or <code>null</code> if a object state transition with the primary key could not be found
	 */
	public static ObjectStateTransition fetchByPrimaryKey(
		long objectStateTransitionId) {

		return getPersistence().fetchByPrimaryKey(objectStateTransitionId);
	}

	/**
	 * Returns all the object state transitions.
	 *
	 * @return the object state transitions
	 */
	public static List<ObjectStateTransition> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the object state transitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @return the range of object state transitions
	 */
	public static List<ObjectStateTransition> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the object state transitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object state transitions
	 */
	public static List<ObjectStateTransition> findAll(
		int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object state transitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object state transitions
	 */
	public static List<ObjectStateTransition> findAll(
		int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the object state transitions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of object state transitions.
	 *
	 * @return the number of object state transitions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ObjectStateTransitionPersistence getPersistence() {
		return _persistence;
	}

	private static volatile ObjectStateTransitionPersistence _persistence;

}