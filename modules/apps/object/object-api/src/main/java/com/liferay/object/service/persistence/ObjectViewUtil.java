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

import com.liferay.object.model.ObjectView;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the object view service. This utility wraps <code>com.liferay.object.service.persistence.impl.ObjectViewPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectViewPersistence
 * @generated
 */
public class ObjectViewUtil {

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
	public static void clearCache(ObjectView objectView) {
		getPersistence().clearCache(objectView);
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
	public static Map<Serializable, ObjectView> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ObjectView> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ObjectView> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ObjectView> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ObjectView> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ObjectView update(ObjectView objectView) {
		return getPersistence().update(objectView);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ObjectView update(
		ObjectView objectView, ServiceContext serviceContext) {

		return getPersistence().update(objectView, serviceContext);
	}

	/**
	 * Returns all the object views where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object views
	 */
	public static List<ObjectView> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the object views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @return the range of matching object views
	 */
	public static List<ObjectView> findByUuid(String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the object views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object views
	 */
	public static List<ObjectView> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectView> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object views
	 */
	public static List<ObjectView> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectView> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view
	 * @throws NoSuchObjectViewException if a matching object view could not be found
	 */
	public static ObjectView findByUuid_First(
			String uuid, OrderByComparator<ObjectView> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first object view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view, or <code>null</code> if a matching object view could not be found
	 */
	public static ObjectView fetchByUuid_First(
		String uuid, OrderByComparator<ObjectView> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last object view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view
	 * @throws NoSuchObjectViewException if a matching object view could not be found
	 */
	public static ObjectView findByUuid_Last(
			String uuid, OrderByComparator<ObjectView> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last object view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view, or <code>null</code> if a matching object view could not be found
	 */
	public static ObjectView fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectView> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the object views before and after the current object view in the ordered set where uuid = &#63;.
	 *
	 * @param objectViewId the primary key of the current object view
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view
	 * @throws NoSuchObjectViewException if a object view with the primary key could not be found
	 */
	public static ObjectView[] findByUuid_PrevAndNext(
			long objectViewId, String uuid,
			OrderByComparator<ObjectView> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewException {

		return getPersistence().findByUuid_PrevAndNext(
			objectViewId, uuid, orderByComparator);
	}

	/**
	 * Removes all the object views where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of object views where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object views
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the object views where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object views
	 */
	public static List<ObjectView> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the object views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @return the range of matching object views
	 */
	public static List<ObjectView> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the object views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object views
	 */
	public static List<ObjectView> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectView> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object views
	 */
	public static List<ObjectView> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectView> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view
	 * @throws NoSuchObjectViewException if a matching object view could not be found
	 */
	public static ObjectView findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectView> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first object view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view, or <code>null</code> if a matching object view could not be found
	 */
	public static ObjectView fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectView> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view
	 * @throws NoSuchObjectViewException if a matching object view could not be found
	 */
	public static ObjectView findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectView> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last object view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view, or <code>null</code> if a matching object view could not be found
	 */
	public static ObjectView fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectView> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the object views before and after the current object view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectViewId the primary key of the current object view
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view
	 * @throws NoSuchObjectViewException if a object view with the primary key could not be found
	 */
	public static ObjectView[] findByUuid_C_PrevAndNext(
			long objectViewId, String uuid, long companyId,
			OrderByComparator<ObjectView> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewException {

		return getPersistence().findByUuid_C_PrevAndNext(
			objectViewId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the object views where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of object views where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object views
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the object views where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @return the matching object views
	 */
	public static List<ObjectView> findByObjectDefinitionId(
		long objectDefinitionId) {

		return getPersistence().findByObjectDefinitionId(objectDefinitionId);
	}

	/**
	 * Returns a range of all the object views where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @return the range of matching object views
	 */
	public static List<ObjectView> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end) {

		return getPersistence().findByObjectDefinitionId(
			objectDefinitionId, start, end);
	}

	/**
	 * Returns an ordered range of all the object views where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object views
	 */
	public static List<ObjectView> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end,
		OrderByComparator<ObjectView> orderByComparator) {

		return getPersistence().findByObjectDefinitionId(
			objectDefinitionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object views where objectDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object views
	 */
	public static List<ObjectView> findByObjectDefinitionId(
		long objectDefinitionId, int start, int end,
		OrderByComparator<ObjectView> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByObjectDefinitionId(
			objectDefinitionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object view in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view
	 * @throws NoSuchObjectViewException if a matching object view could not be found
	 */
	public static ObjectView findByObjectDefinitionId_First(
			long objectDefinitionId,
			OrderByComparator<ObjectView> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewException {

		return getPersistence().findByObjectDefinitionId_First(
			objectDefinitionId, orderByComparator);
	}

	/**
	 * Returns the first object view in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view, or <code>null</code> if a matching object view could not be found
	 */
	public static ObjectView fetchByObjectDefinitionId_First(
		long objectDefinitionId,
		OrderByComparator<ObjectView> orderByComparator) {

		return getPersistence().fetchByObjectDefinitionId_First(
			objectDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last object view in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view
	 * @throws NoSuchObjectViewException if a matching object view could not be found
	 */
	public static ObjectView findByObjectDefinitionId_Last(
			long objectDefinitionId,
			OrderByComparator<ObjectView> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewException {

		return getPersistence().findByObjectDefinitionId_Last(
			objectDefinitionId, orderByComparator);
	}

	/**
	 * Returns the last object view in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view, or <code>null</code> if a matching object view could not be found
	 */
	public static ObjectView fetchByObjectDefinitionId_Last(
		long objectDefinitionId,
		OrderByComparator<ObjectView> orderByComparator) {

		return getPersistence().fetchByObjectDefinitionId_Last(
			objectDefinitionId, orderByComparator);
	}

	/**
	 * Returns the object views before and after the current object view in the ordered set where objectDefinitionId = &#63;.
	 *
	 * @param objectViewId the primary key of the current object view
	 * @param objectDefinitionId the object definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view
	 * @throws NoSuchObjectViewException if a object view with the primary key could not be found
	 */
	public static ObjectView[] findByObjectDefinitionId_PrevAndNext(
			long objectViewId, long objectDefinitionId,
			OrderByComparator<ObjectView> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewException {

		return getPersistence().findByObjectDefinitionId_PrevAndNext(
			objectViewId, objectDefinitionId, orderByComparator);
	}

	/**
	 * Removes all the object views where objectDefinitionId = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 */
	public static void removeByObjectDefinitionId(long objectDefinitionId) {
		getPersistence().removeByObjectDefinitionId(objectDefinitionId);
	}

	/**
	 * Returns the number of object views where objectDefinitionId = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @return the number of matching object views
	 */
	public static int countByObjectDefinitionId(long objectDefinitionId) {
		return getPersistence().countByObjectDefinitionId(objectDefinitionId);
	}

	/**
	 * Returns all the object views where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @return the matching object views
	 */
	public static List<ObjectView> findByODI_DOV(
		long objectDefinitionId, boolean defaultObjectView) {

		return getPersistence().findByODI_DOV(
			objectDefinitionId, defaultObjectView);
	}

	/**
	 * Returns a range of all the object views where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @return the range of matching object views
	 */
	public static List<ObjectView> findByODI_DOV(
		long objectDefinitionId, boolean defaultObjectView, int start,
		int end) {

		return getPersistence().findByODI_DOV(
			objectDefinitionId, defaultObjectView, start, end);
	}

	/**
	 * Returns an ordered range of all the object views where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object views
	 */
	public static List<ObjectView> findByODI_DOV(
		long objectDefinitionId, boolean defaultObjectView, int start, int end,
		OrderByComparator<ObjectView> orderByComparator) {

		return getPersistence().findByODI_DOV(
			objectDefinitionId, defaultObjectView, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object views where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object views
	 */
	public static List<ObjectView> findByODI_DOV(
		long objectDefinitionId, boolean defaultObjectView, int start, int end,
		OrderByComparator<ObjectView> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByODI_DOV(
			objectDefinitionId, defaultObjectView, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first object view in the ordered set where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view
	 * @throws NoSuchObjectViewException if a matching object view could not be found
	 */
	public static ObjectView findByODI_DOV_First(
			long objectDefinitionId, boolean defaultObjectView,
			OrderByComparator<ObjectView> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewException {

		return getPersistence().findByODI_DOV_First(
			objectDefinitionId, defaultObjectView, orderByComparator);
	}

	/**
	 * Returns the first object view in the ordered set where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view, or <code>null</code> if a matching object view could not be found
	 */
	public static ObjectView fetchByODI_DOV_First(
		long objectDefinitionId, boolean defaultObjectView,
		OrderByComparator<ObjectView> orderByComparator) {

		return getPersistence().fetchByODI_DOV_First(
			objectDefinitionId, defaultObjectView, orderByComparator);
	}

	/**
	 * Returns the last object view in the ordered set where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view
	 * @throws NoSuchObjectViewException if a matching object view could not be found
	 */
	public static ObjectView findByODI_DOV_Last(
			long objectDefinitionId, boolean defaultObjectView,
			OrderByComparator<ObjectView> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewException {

		return getPersistence().findByODI_DOV_Last(
			objectDefinitionId, defaultObjectView, orderByComparator);
	}

	/**
	 * Returns the last object view in the ordered set where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view, or <code>null</code> if a matching object view could not be found
	 */
	public static ObjectView fetchByODI_DOV_Last(
		long objectDefinitionId, boolean defaultObjectView,
		OrderByComparator<ObjectView> orderByComparator) {

		return getPersistence().fetchByODI_DOV_Last(
			objectDefinitionId, defaultObjectView, orderByComparator);
	}

	/**
	 * Returns the object views before and after the current object view in the ordered set where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * @param objectViewId the primary key of the current object view
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view
	 * @throws NoSuchObjectViewException if a object view with the primary key could not be found
	 */
	public static ObjectView[] findByODI_DOV_PrevAndNext(
			long objectViewId, long objectDefinitionId,
			boolean defaultObjectView,
			OrderByComparator<ObjectView> orderByComparator)
		throws com.liferay.object.exception.NoSuchObjectViewException {

		return getPersistence().findByODI_DOV_PrevAndNext(
			objectViewId, objectDefinitionId, defaultObjectView,
			orderByComparator);
	}

	/**
	 * Removes all the object views where objectDefinitionId = &#63; and defaultObjectView = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 */
	public static void removeByODI_DOV(
		long objectDefinitionId, boolean defaultObjectView) {

		getPersistence().removeByODI_DOV(objectDefinitionId, defaultObjectView);
	}

	/**
	 * Returns the number of object views where objectDefinitionId = &#63; and defaultObjectView = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param defaultObjectView the default object view
	 * @return the number of matching object views
	 */
	public static int countByODI_DOV(
		long objectDefinitionId, boolean defaultObjectView) {

		return getPersistence().countByODI_DOV(
			objectDefinitionId, defaultObjectView);
	}

	/**
	 * Caches the object view in the entity cache if it is enabled.
	 *
	 * @param objectView the object view
	 */
	public static void cacheResult(ObjectView objectView) {
		getPersistence().cacheResult(objectView);
	}

	/**
	 * Caches the object views in the entity cache if it is enabled.
	 *
	 * @param objectViews the object views
	 */
	public static void cacheResult(List<ObjectView> objectViews) {
		getPersistence().cacheResult(objectViews);
	}

	/**
	 * Creates a new object view with the primary key. Does not add the object view to the database.
	 *
	 * @param objectViewId the primary key for the new object view
	 * @return the new object view
	 */
	public static ObjectView create(long objectViewId) {
		return getPersistence().create(objectViewId);
	}

	/**
	 * Removes the object view with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectViewId the primary key of the object view
	 * @return the object view that was removed
	 * @throws NoSuchObjectViewException if a object view with the primary key could not be found
	 */
	public static ObjectView remove(long objectViewId)
		throws com.liferay.object.exception.NoSuchObjectViewException {

		return getPersistence().remove(objectViewId);
	}

	public static ObjectView updateImpl(ObjectView objectView) {
		return getPersistence().updateImpl(objectView);
	}

	/**
	 * Returns the object view with the primary key or throws a <code>NoSuchObjectViewException</code> if it could not be found.
	 *
	 * @param objectViewId the primary key of the object view
	 * @return the object view
	 * @throws NoSuchObjectViewException if a object view with the primary key could not be found
	 */
	public static ObjectView findByPrimaryKey(long objectViewId)
		throws com.liferay.object.exception.NoSuchObjectViewException {

		return getPersistence().findByPrimaryKey(objectViewId);
	}

	/**
	 * Returns the object view with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectViewId the primary key of the object view
	 * @return the object view, or <code>null</code> if a object view with the primary key could not be found
	 */
	public static ObjectView fetchByPrimaryKey(long objectViewId) {
		return getPersistence().fetchByPrimaryKey(objectViewId);
	}

	/**
	 * Returns all the object views.
	 *
	 * @return the object views
	 */
	public static List<ObjectView> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the object views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @return the range of object views
	 */
	public static List<ObjectView> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the object views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object views
	 */
	public static List<ObjectView> findAll(
		int start, int end, OrderByComparator<ObjectView> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the object views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object views
	 * @param end the upper bound of the range of object views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object views
	 */
	public static List<ObjectView> findAll(
		int start, int end, OrderByComparator<ObjectView> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the object views from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of object views.
	 *
	 * @return the number of object views
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ObjectViewPersistence getPersistence() {
		return _persistence;
	}

	private static volatile ObjectViewPersistence _persistence;

}