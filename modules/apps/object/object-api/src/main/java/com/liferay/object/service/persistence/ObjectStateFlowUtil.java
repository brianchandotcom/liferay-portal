/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence;

import com.liferay.object.model.ObjectStateFlow;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the object state flow service. This utility wraps <code>com.liferay.object.service.persistence.impl.ObjectStateFlowPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectStateFlowPersistence
 * @generated
 */
public class ObjectStateFlowUtil {

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
	public static void clearCache(ObjectStateFlow objectStateFlow) {
		getPersistence().clearCache(objectStateFlow);
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
	public static Map<Serializable, ObjectStateFlow> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ObjectStateFlow> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ObjectStateFlow> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ObjectStateFlow> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ObjectStateFlow> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ObjectStateFlow update(ObjectStateFlow objectStateFlow) {
		return getPersistence().update(objectStateFlow);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ObjectStateFlow update(
		ObjectStateFlow objectStateFlow, ServiceContext serviceContext) {

		return getPersistence().update(objectStateFlow, serviceContext);
	}

	/**
	 * Returns all the object state flows where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object state flows
	 */
	public static List<ObjectStateFlow> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the object state flows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @return the range of matching object state flows
	 */
	public static List<ObjectStateFlow> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the object state flows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state flows
	 */
	public static List<ObjectStateFlow> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectStateFlow> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object state flows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state flows
	 */
	public static List<ObjectStateFlow> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectStateFlow> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object state flow in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state flow
	 * @throws NoSuchObjectStateFlowException if a matching object state flow could not be found
	 */
	public static ObjectStateFlow findByUuid_First(
			String uuid, OrderByComparator<ObjectStateFlow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectStateFlowException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first object state flow in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	public static ObjectStateFlow fetchByUuid_First(
		String uuid, OrderByComparator<ObjectStateFlow> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last object state flow in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state flow
	 * @throws NoSuchObjectStateFlowException if a matching object state flow could not be found
	 */
	public static ObjectStateFlow findByUuid_Last(
			String uuid, OrderByComparator<ObjectStateFlow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectStateFlowException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last object state flow in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	public static ObjectStateFlow fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectStateFlow> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the object state flows before and after the current object state flow in the ordered set where uuid = &#63;.
	 *
	 * @param objectStateFlowId the primary key of the current object state flow
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state flow
	 * @throws NoSuchObjectStateFlowException if a object state flow with the primary key could not be found
	 */
	public static ObjectStateFlow[] findByUuid_PrevAndNext(
			long objectStateFlowId, String uuid,
			OrderByComparator<ObjectStateFlow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectStateFlowException {

		return getPersistence().findByUuid_PrevAndNext(
			objectStateFlowId, uuid, orderByComparator);
	}

	/**
	 * Removes all the object state flows where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of object state flows where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object state flows
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the object state flows where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object state flows
	 */
	public static List<ObjectStateFlow> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the object state flows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @return the range of matching object state flows
	 */
	public static List<ObjectStateFlow> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object state flows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state flows
	 */
	public static List<ObjectStateFlow> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectStateFlow> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object state flows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state flows
	 */
	public static List<ObjectStateFlow> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectStateFlow> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object state flow in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state flow
	 * @throws NoSuchObjectStateFlowException if a matching object state flow could not be found
	 */
	public static ObjectStateFlow findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectStateFlow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectStateFlowException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first object state flow in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	public static ObjectStateFlow fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectStateFlow> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object state flow in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state flow
	 * @throws NoSuchObjectStateFlowException if a matching object state flow could not be found
	 */
	public static ObjectStateFlow findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectStateFlow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectStateFlowException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object state flow in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	public static ObjectStateFlow fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectStateFlow> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the object state flows before and after the current object state flow in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectStateFlowId the primary key of the current object state flow
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state flow
	 * @throws NoSuchObjectStateFlowException if a object state flow with the primary key could not be found
	 */
	public static ObjectStateFlow[] findByUuid_C_PrevAndNext(
			long objectStateFlowId, String uuid, long companyId,
			OrderByComparator<ObjectStateFlow> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectStateFlowException {

		return getPersistence().findByUuid_C_PrevAndNext(
			objectStateFlowId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the object state flows where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of object state flows where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object state flows
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the object state flow where objectFieldId = &#63; or throws a <code>NoSuchObjectStateFlowException</code> if it could not be found.
	 *
	 * @param objectFieldId the object field ID
	 * @return the matching object state flow
	 * @throws NoSuchObjectStateFlowException if a matching object state flow could not be found
	 */
	public static ObjectStateFlow findByObjectFieldId(long objectFieldId)
		throws com.liferay.object.exception.NoSuchObjectStateFlowException {

		return getPersistence().findByObjectFieldId(objectFieldId);
	}

	/**
	 * Returns the object state flow where objectFieldId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param objectFieldId the object field ID
	 * @return the matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	public static ObjectStateFlow fetchByObjectFieldId(long objectFieldId) {
		return getPersistence().fetchByObjectFieldId(objectFieldId);
	}

	/**
	 * Returns the object state flow where objectFieldId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param objectFieldId the object field ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object state flow, or <code>null</code> if a matching object state flow could not be found
	 */
	public static ObjectStateFlow fetchByObjectFieldId(
		long objectFieldId, boolean useFinderCache) {

		return getPersistence().fetchByObjectFieldId(
			objectFieldId, useFinderCache);
	}

	/**
	 * Removes the object state flow where objectFieldId = &#63; from the database.
	 *
	 * @param objectFieldId the object field ID
	 * @return the object state flow that was removed
	 */
	public static ObjectStateFlow removeByObjectFieldId(long objectFieldId)
		throws com.liferay.object.exception.NoSuchObjectStateFlowException {

		return getPersistence().removeByObjectFieldId(objectFieldId);
	}

	/**
	 * Returns the number of object state flows where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @return the number of matching object state flows
	 */
	public static int countByObjectFieldId(long objectFieldId) {
		return getPersistence().countByObjectFieldId(objectFieldId);
	}

	/**
	 * Caches the object state flow in the entity cache if it is enabled.
	 *
	 * @param objectStateFlow the object state flow
	 */
	public static void cacheResult(ObjectStateFlow objectStateFlow) {
		getPersistence().cacheResult(objectStateFlow);
	}

	/**
	 * Caches the object state flows in the entity cache if it is enabled.
	 *
	 * @param objectStateFlows the object state flows
	 */
	public static void cacheResult(List<ObjectStateFlow> objectStateFlows) {
		getPersistence().cacheResult(objectStateFlows);
	}

	/**
	 * Creates a new object state flow with the primary key. Does not add the object state flow to the database.
	 *
	 * @param objectStateFlowId the primary key for the new object state flow
	 * @return the new object state flow
	 */
	public static ObjectStateFlow create(long objectStateFlowId) {
		return getPersistence().create(objectStateFlowId);
	}

	/**
	 * Removes the object state flow with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectStateFlowId the primary key of the object state flow
	 * @return the object state flow that was removed
	 * @throws NoSuchObjectStateFlowException if a object state flow with the primary key could not be found
	 */
	public static ObjectStateFlow remove(long objectStateFlowId)
		throws com.liferay.object.exception.NoSuchObjectStateFlowException {

		return getPersistence().remove(objectStateFlowId);
	}

	public static ObjectStateFlow updateImpl(ObjectStateFlow objectStateFlow) {
		return getPersistence().updateImpl(objectStateFlow);
	}

	/**
	 * Returns the object state flow with the primary key or throws a <code>NoSuchObjectStateFlowException</code> if it could not be found.
	 *
	 * @param objectStateFlowId the primary key of the object state flow
	 * @return the object state flow
	 * @throws NoSuchObjectStateFlowException if a object state flow with the primary key could not be found
	 */
	public static ObjectStateFlow findByPrimaryKey(long objectStateFlowId)
		throws com.liferay.object.exception.NoSuchObjectStateFlowException {

		return getPersistence().findByPrimaryKey(objectStateFlowId);
	}

	/**
	 * Returns the object state flow with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectStateFlowId the primary key of the object state flow
	 * @return the object state flow, or <code>null</code> if a object state flow with the primary key could not be found
	 */
	public static ObjectStateFlow fetchByPrimaryKey(long objectStateFlowId) {
		return getPersistence().fetchByPrimaryKey(objectStateFlowId);
	}

	/**
	 * Returns all the object state flows.
	 *
	 * @return the object state flows
	 */
	public static List<ObjectStateFlow> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the object state flows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @return the range of object state flows
	 */
	public static List<ObjectStateFlow> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the object state flows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object state flows
	 */
	public static List<ObjectStateFlow> findAll(
		int start, int end,
		OrderByComparator<ObjectStateFlow> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object state flows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateFlowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object state flows
	 * @param end the upper bound of the range of object state flows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object state flows
	 */
	public static List<ObjectStateFlow> findAll(
		int start, int end,
		OrderByComparator<ObjectStateFlow> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the object state flows from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of object state flows.
	 *
	 * @return the number of object state flows
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ObjectStateFlowPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(ObjectStateFlowPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile ObjectStateFlowPersistence _persistence;

}