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

import com.liferay.object.model.ObjectLayoutBox;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the object layout box service. This utility wraps <code>com.liferay.object.service.persistence.impl.ObjectLayoutBoxPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectLayoutBoxPersistence
 * @generated
 */
public class ObjectLayoutBoxUtil {

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
	public static void clearCache(ObjectLayoutBox objectLayoutBox) {
		getPersistence().clearCache(objectLayoutBox);
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
	public static Map<Serializable, ObjectLayoutBox> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ObjectLayoutBox> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ObjectLayoutBox> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ObjectLayoutBox> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ObjectLayoutBox> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ObjectLayoutBox update(ObjectLayoutBox objectLayoutBox) {
		return getPersistence().update(objectLayoutBox);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ObjectLayoutBox update(
		ObjectLayoutBox objectLayoutBox, ServiceContext serviceContext) {

		return getPersistence().update(objectLayoutBox, serviceContext);
	}

	/**
	 * Returns all the object layout boxes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object layout boxes
	 */
	public static List<ObjectLayoutBox> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the object layout boxes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @return the range of matching object layout boxes
	 */
	public static List<ObjectLayoutBox> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the object layout boxes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout boxes
	 */
	public static List<ObjectLayoutBox> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutBox> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout boxes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout boxes
	 */
	public static List<ObjectLayoutBox> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutBox> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object layout box in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	public static ObjectLayoutBox findByUuid_First(
			String uuid, OrderByComparator<ObjectLayoutBox> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutBoxException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first object layout box in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	public static ObjectLayoutBox fetchByUuid_First(
		String uuid, OrderByComparator<ObjectLayoutBox> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last object layout box in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	public static ObjectLayoutBox findByUuid_Last(
			String uuid, OrderByComparator<ObjectLayoutBox> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutBoxException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last object layout box in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	public static ObjectLayoutBox fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectLayoutBox> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the object layout boxes before and after the current object layout box in the ordered set where uuid = &#63;.
	 *
	 * @param objectLayoutBoxId the primary key of the current object layout box
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout box
	 * @throws NoSuchObjectLayoutBoxException if a object layout box with the primary key could not be found
	 */
	public static ObjectLayoutBox[] findByUuid_PrevAndNext(
			long objectLayoutBoxId, String uuid,
			OrderByComparator<ObjectLayoutBox> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutBoxException {

		return getPersistence().findByUuid_PrevAndNext(
			objectLayoutBoxId, uuid, orderByComparator);
	}

	/**
	 * Removes all the object layout boxes where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of object layout boxes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object layout boxes
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object layout boxes
	 */
	public static List<ObjectLayoutBox> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @return the range of matching object layout boxes
	 */
	public static List<ObjectLayoutBox> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout boxes
	 */
	public static List<ObjectLayoutBox> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutBox> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout boxes
	 */
	public static List<ObjectLayoutBox> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutBox> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object layout box in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	public static ObjectLayoutBox findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutBox> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutBoxException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first object layout box in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	public static ObjectLayoutBox fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutBox> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object layout box in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	public static ObjectLayoutBox findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutBox> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutBoxException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object layout box in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	public static ObjectLayoutBox fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutBox> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the object layout boxes before and after the current object layout box in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectLayoutBoxId the primary key of the current object layout box
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout box
	 * @throws NoSuchObjectLayoutBoxException if a object layout box with the primary key could not be found
	 */
	public static ObjectLayoutBox[] findByUuid_C_PrevAndNext(
			long objectLayoutBoxId, String uuid, long companyId,
			OrderByComparator<ObjectLayoutBox> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutBoxException {

		return getPersistence().findByUuid_C_PrevAndNext(
			objectLayoutBoxId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the object layout boxes where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object layout boxes
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @return the matching object layout boxes
	 */
	public static List<ObjectLayoutBox> findByObjectLayoutTabId(
		long objectLayoutTabId) {

		return getPersistence().findByObjectLayoutTabId(objectLayoutTabId);
	}

	/**
	 * Returns a range of all the object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @return the range of matching object layout boxes
	 */
	public static List<ObjectLayoutBox> findByObjectLayoutTabId(
		long objectLayoutTabId, int start, int end) {

		return getPersistence().findByObjectLayoutTabId(
			objectLayoutTabId, start, end);
	}

	/**
	 * Returns an ordered range of all the object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout boxes
	 */
	public static List<ObjectLayoutBox> findByObjectLayoutTabId(
		long objectLayoutTabId, int start, int end,
		OrderByComparator<ObjectLayoutBox> orderByComparator) {

		return getPersistence().findByObjectLayoutTabId(
			objectLayoutTabId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout boxes
	 */
	public static List<ObjectLayoutBox> findByObjectLayoutTabId(
		long objectLayoutTabId, int start, int end,
		OrderByComparator<ObjectLayoutBox> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByObjectLayoutTabId(
			objectLayoutTabId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object layout box in the ordered set where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	public static ObjectLayoutBox findByObjectLayoutTabId_First(
			long objectLayoutTabId,
			OrderByComparator<ObjectLayoutBox> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutBoxException {

		return getPersistence().findByObjectLayoutTabId_First(
			objectLayoutTabId, orderByComparator);
	}

	/**
	 * Returns the first object layout box in the ordered set where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	public static ObjectLayoutBox fetchByObjectLayoutTabId_First(
		long objectLayoutTabId,
		OrderByComparator<ObjectLayoutBox> orderByComparator) {

		return getPersistence().fetchByObjectLayoutTabId_First(
			objectLayoutTabId, orderByComparator);
	}

	/**
	 * Returns the last object layout box in the ordered set where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	public static ObjectLayoutBox findByObjectLayoutTabId_Last(
			long objectLayoutTabId,
			OrderByComparator<ObjectLayoutBox> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutBoxException {

		return getPersistence().findByObjectLayoutTabId_Last(
			objectLayoutTabId, orderByComparator);
	}

	/**
	 * Returns the last object layout box in the ordered set where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	public static ObjectLayoutBox fetchByObjectLayoutTabId_Last(
		long objectLayoutTabId,
		OrderByComparator<ObjectLayoutBox> orderByComparator) {

		return getPersistence().fetchByObjectLayoutTabId_Last(
			objectLayoutTabId, orderByComparator);
	}

	/**
	 * Returns the object layout boxes before and after the current object layout box in the ordered set where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutBoxId the primary key of the current object layout box
	 * @param objectLayoutTabId the object layout tab ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout box
	 * @throws NoSuchObjectLayoutBoxException if a object layout box with the primary key could not be found
	 */
	public static ObjectLayoutBox[] findByObjectLayoutTabId_PrevAndNext(
			long objectLayoutBoxId, long objectLayoutTabId,
			OrderByComparator<ObjectLayoutBox> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectLayoutBoxException {

		return getPersistence().findByObjectLayoutTabId_PrevAndNext(
			objectLayoutBoxId, objectLayoutTabId, orderByComparator);
	}

	/**
	 * Removes all the object layout boxes where objectLayoutTabId = &#63; from the database.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 */
	public static void removeByObjectLayoutTabId(long objectLayoutTabId) {
		getPersistence().removeByObjectLayoutTabId(objectLayoutTabId);
	}

	/**
	 * Returns the number of object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @return the number of matching object layout boxes
	 */
	public static int countByObjectLayoutTabId(long objectLayoutTabId) {
		return getPersistence().countByObjectLayoutTabId(objectLayoutTabId);
	}

	/**
	 * Caches the object layout box in the entity cache if it is enabled.
	 *
	 * @param objectLayoutBox the object layout box
	 */
	public static void cacheResult(ObjectLayoutBox objectLayoutBox) {
		getPersistence().cacheResult(objectLayoutBox);
	}

	/**
	 * Caches the object layout boxes in the entity cache if it is enabled.
	 *
	 * @param objectLayoutBoxes the object layout boxes
	 */
	public static void cacheResult(List<ObjectLayoutBox> objectLayoutBoxes) {
		getPersistence().cacheResult(objectLayoutBoxes);
	}

	/**
	 * Creates a new object layout box with the primary key. Does not add the object layout box to the database.
	 *
	 * @param objectLayoutBoxId the primary key for the new object layout box
	 * @return the new object layout box
	 */
	public static ObjectLayoutBox create(long objectLayoutBoxId) {
		return getPersistence().create(objectLayoutBoxId);
	}

	/**
	 * Removes the object layout box with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectLayoutBoxId the primary key of the object layout box
	 * @return the object layout box that was removed
	 * @throws NoSuchObjectLayoutBoxException if a object layout box with the primary key could not be found
	 */
	public static ObjectLayoutBox remove(long objectLayoutBoxId)
		throws com.liferay.object.exception.NoSuchObjectLayoutBoxException {

		return getPersistence().remove(objectLayoutBoxId);
	}

	public static ObjectLayoutBox updateImpl(ObjectLayoutBox objectLayoutBox) {
		return getPersistence().updateImpl(objectLayoutBox);
	}

	/**
	 * Returns the object layout box with the primary key or throws a <code>NoSuchObjectLayoutBoxException</code> if it could not be found.
	 *
	 * @param objectLayoutBoxId the primary key of the object layout box
	 * @return the object layout box
	 * @throws NoSuchObjectLayoutBoxException if a object layout box with the primary key could not be found
	 */
	public static ObjectLayoutBox findByPrimaryKey(long objectLayoutBoxId)
		throws com.liferay.object.exception.NoSuchObjectLayoutBoxException {

		return getPersistence().findByPrimaryKey(objectLayoutBoxId);
	}

	/**
	 * Returns the object layout box with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectLayoutBoxId the primary key of the object layout box
	 * @return the object layout box, or <code>null</code> if a object layout box with the primary key could not be found
	 */
	public static ObjectLayoutBox fetchByPrimaryKey(long objectLayoutBoxId) {
		return getPersistence().fetchByPrimaryKey(objectLayoutBoxId);
	}

	/**
	 * Returns all the object layout boxes.
	 *
	 * @return the object layout boxes
	 */
	public static List<ObjectLayoutBox> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the object layout boxes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @return the range of object layout boxes
	 */
	public static List<ObjectLayoutBox> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the object layout boxes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object layout boxes
	 */
	public static List<ObjectLayoutBox> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutBox> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object layout boxes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object layout boxes
	 */
	public static List<ObjectLayoutBox> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutBox> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the object layout boxes from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of object layout boxes.
	 *
	 * @return the number of object layout boxes
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ObjectLayoutBoxPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ObjectLayoutBoxPersistence, ObjectLayoutBoxPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ObjectLayoutBoxPersistence.class);

		ServiceTracker<ObjectLayoutBoxPersistence, ObjectLayoutBoxPersistence>
			serviceTracker =
				new ServiceTracker
					<ObjectLayoutBoxPersistence, ObjectLayoutBoxPersistence>(
						bundle.getBundleContext(),
						ObjectLayoutBoxPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}