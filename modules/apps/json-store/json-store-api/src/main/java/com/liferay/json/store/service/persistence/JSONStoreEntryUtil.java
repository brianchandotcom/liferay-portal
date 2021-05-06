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

package com.liferay.json.store.service.persistence;

import com.liferay.json.store.model.JSONStoreEntry;
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
 * The persistence utility for the json store entry service. This utility wraps <code>com.liferay.json.store.service.persistence.impl.JSONStoreEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Preston Crary
 * @see JSONStoreEntryPersistence
 * @generated
 */
public class JSONStoreEntryUtil {

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
	public static void clearCache(JSONStoreEntry jsonStoreEntry) {
		getPersistence().clearCache(jsonStoreEntry);
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
	public static Map<Serializable, JSONStoreEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<JSONStoreEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<JSONStoreEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<JSONStoreEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static JSONStoreEntry update(JSONStoreEntry jsonStoreEntry) {
		return getPersistence().update(jsonStoreEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static JSONStoreEntry update(
		JSONStoreEntry jsonStoreEntry, ServiceContext serviceContext) {

		return getPersistence().update(jsonStoreEntry, serviceContext);
	}

	/**
	 * Returns all the json store entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching json store entries
	 */
	public static List<JSONStoreEntry> findByCN_CPK(
		long classNameId, long classPK) {

		return getPersistence().findByCN_CPK(classNameId, classPK);
	}

	/**
	 * Returns a range of all the json store entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @return the range of matching json store entries
	 */
	public static List<JSONStoreEntry> findByCN_CPK(
		long classNameId, long classPK, int start, int end) {

		return getPersistence().findByCN_CPK(classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the json store entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching json store entries
	 */
	public static List<JSONStoreEntry> findByCN_CPK(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		return getPersistence().findByCN_CPK(
			classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the json store entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching json store entries
	 */
	public static List<JSONStoreEntry> findByCN_CPK(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<JSONStoreEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCN_CPK(
			classNameId, classPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first json store entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	public static JSONStoreEntry findByCN_CPK_First(
			long classNameId, long classPK,
			OrderByComparator<JSONStoreEntry> orderByComparator)
		throws com.liferay.json.store.exception.NoSuchEntryException {

		return getPersistence().findByCN_CPK_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first json store entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	public static JSONStoreEntry fetchByCN_CPK_First(
		long classNameId, long classPK,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		return getPersistence().fetchByCN_CPK_First(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last json store entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	public static JSONStoreEntry findByCN_CPK_Last(
			long classNameId, long classPK,
			OrderByComparator<JSONStoreEntry> orderByComparator)
		throws com.liferay.json.store.exception.NoSuchEntryException {

		return getPersistence().findByCN_CPK_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last json store entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	public static JSONStoreEntry fetchByCN_CPK_Last(
		long classNameId, long classPK,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		return getPersistence().fetchByCN_CPK_Last(
			classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the json store entries before and after the current json store entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param jsonStoreEntryId the primary key of the current json store entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next json store entry
	 * @throws NoSuchEntryException if a json store entry with the primary key could not be found
	 */
	public static JSONStoreEntry[] findByCN_CPK_PrevAndNext(
			long jsonStoreEntryId, long classNameId, long classPK,
			OrderByComparator<JSONStoreEntry> orderByComparator)
		throws com.liferay.json.store.exception.NoSuchEntryException {

		return getPersistence().findByCN_CPK_PrevAndNext(
			jsonStoreEntryId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Removes all the json store entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public static void removeByCN_CPK(long classNameId, long classPK) {
		getPersistence().removeByCN_CPK(classNameId, classPK);
	}

	/**
	 * Returns the number of json store entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching json store entries
	 */
	public static int countByCN_CPK(long classNameId, long classPK) {
		return getPersistence().countByCN_CPK(classNameId, classPK);
	}

	/**
	 * Returns all the json store entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @return the matching json store entries
	 */
	public static List<JSONStoreEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong) {

		return getPersistence().findByC_CN_I_T_VL(
			companyId, classNameId, index, type, valueLong);
	}

	/**
	 * Returns a range of all the json store entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @return the range of matching json store entries
	 */
	public static List<JSONStoreEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong,
		int start, int end) {

		return getPersistence().findByC_CN_I_T_VL(
			companyId, classNameId, index, type, valueLong, start, end);
	}

	/**
	 * Returns an ordered range of all the json store entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching json store entries
	 */
	public static List<JSONStoreEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong,
		int start, int end,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		return getPersistence().findByC_CN_I_T_VL(
			companyId, classNameId, index, type, valueLong, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the json store entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching json store entries
	 */
	public static List<JSONStoreEntry> findByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong,
		int start, int end, OrderByComparator<JSONStoreEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_CN_I_T_VL(
			companyId, classNameId, index, type, valueLong, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	public static JSONStoreEntry findByC_CN_I_T_VL_First(
			long companyId, long classNameId, int index, int type,
			long valueLong, OrderByComparator<JSONStoreEntry> orderByComparator)
		throws com.liferay.json.store.exception.NoSuchEntryException {

		return getPersistence().findByC_CN_I_T_VL_First(
			companyId, classNameId, index, type, valueLong, orderByComparator);
	}

	/**
	 * Returns the first json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	public static JSONStoreEntry fetchByC_CN_I_T_VL_First(
		long companyId, long classNameId, int index, int type, long valueLong,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		return getPersistence().fetchByC_CN_I_T_VL_First(
			companyId, classNameId, index, type, valueLong, orderByComparator);
	}

	/**
	 * Returns the last json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	public static JSONStoreEntry findByC_CN_I_T_VL_Last(
			long companyId, long classNameId, int index, int type,
			long valueLong, OrderByComparator<JSONStoreEntry> orderByComparator)
		throws com.liferay.json.store.exception.NoSuchEntryException {

		return getPersistence().findByC_CN_I_T_VL_Last(
			companyId, classNameId, index, type, valueLong, orderByComparator);
	}

	/**
	 * Returns the last json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	public static JSONStoreEntry fetchByC_CN_I_T_VL_Last(
		long companyId, long classNameId, int index, int type, long valueLong,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		return getPersistence().fetchByC_CN_I_T_VL_Last(
			companyId, classNameId, index, type, valueLong, orderByComparator);
	}

	/**
	 * Returns the json store entries before and after the current json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param jsonStoreEntryId the primary key of the current json store entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next json store entry
	 * @throws NoSuchEntryException if a json store entry with the primary key could not be found
	 */
	public static JSONStoreEntry[] findByC_CN_I_T_VL_PrevAndNext(
			long jsonStoreEntryId, long companyId, long classNameId, int index,
			int type, long valueLong,
			OrderByComparator<JSONStoreEntry> orderByComparator)
		throws com.liferay.json.store.exception.NoSuchEntryException {

		return getPersistence().findByC_CN_I_T_VL_PrevAndNext(
			jsonStoreEntryId, companyId, classNameId, index, type, valueLong,
			orderByComparator);
	}

	/**
	 * Removes all the json store entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 */
	public static void removeByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong) {

		getPersistence().removeByC_CN_I_T_VL(
			companyId, classNameId, index, type, valueLong);
	}

	/**
	 * Returns the number of json store entries where companyId = &#63; and classNameId = &#63; and index = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param index the index
	 * @param type the type
	 * @param valueLong the value long
	 * @return the number of matching json store entries
	 */
	public static int countByC_CN_I_T_VL(
		long companyId, long classNameId, int index, int type, long valueLong) {

		return getPersistence().countByC_CN_I_T_VL(
			companyId, classNameId, index, type, valueLong);
	}

	/**
	 * Returns all the json store entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @return the matching json store entries
	 */
	public static List<JSONStoreEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type,
		long valueLong) {

		return getPersistence().findByC_CN_K_T_VL(
			companyId, classNameId, key, type, valueLong);
	}

	/**
	 * Returns a range of all the json store entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @return the range of matching json store entries
	 */
	public static List<JSONStoreEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong,
		int start, int end) {

		return getPersistence().findByC_CN_K_T_VL(
			companyId, classNameId, key, type, valueLong, start, end);
	}

	/**
	 * Returns an ordered range of all the json store entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching json store entries
	 */
	public static List<JSONStoreEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong,
		int start, int end,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		return getPersistence().findByC_CN_K_T_VL(
			companyId, classNameId, key, type, valueLong, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the json store entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching json store entries
	 */
	public static List<JSONStoreEntry> findByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type, long valueLong,
		int start, int end, OrderByComparator<JSONStoreEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_CN_K_T_VL(
			companyId, classNameId, key, type, valueLong, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	public static JSONStoreEntry findByC_CN_K_T_VL_First(
			long companyId, long classNameId, String key, int type,
			long valueLong, OrderByComparator<JSONStoreEntry> orderByComparator)
		throws com.liferay.json.store.exception.NoSuchEntryException {

		return getPersistence().findByC_CN_K_T_VL_First(
			companyId, classNameId, key, type, valueLong, orderByComparator);
	}

	/**
	 * Returns the first json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	public static JSONStoreEntry fetchByC_CN_K_T_VL_First(
		long companyId, long classNameId, String key, int type, long valueLong,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		return getPersistence().fetchByC_CN_K_T_VL_First(
			companyId, classNameId, key, type, valueLong, orderByComparator);
	}

	/**
	 * Returns the last json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	public static JSONStoreEntry findByC_CN_K_T_VL_Last(
			long companyId, long classNameId, String key, int type,
			long valueLong, OrderByComparator<JSONStoreEntry> orderByComparator)
		throws com.liferay.json.store.exception.NoSuchEntryException {

		return getPersistence().findByC_CN_K_T_VL_Last(
			companyId, classNameId, key, type, valueLong, orderByComparator);
	}

	/**
	 * Returns the last json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	public static JSONStoreEntry fetchByC_CN_K_T_VL_Last(
		long companyId, long classNameId, String key, int type, long valueLong,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		return getPersistence().fetchByC_CN_K_T_VL_Last(
			companyId, classNameId, key, type, valueLong, orderByComparator);
	}

	/**
	 * Returns the json store entries before and after the current json store entry in the ordered set where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param jsonStoreEntryId the primary key of the current json store entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next json store entry
	 * @throws NoSuchEntryException if a json store entry with the primary key could not be found
	 */
	public static JSONStoreEntry[] findByC_CN_K_T_VL_PrevAndNext(
			long jsonStoreEntryId, long companyId, long classNameId, String key,
			int type, long valueLong,
			OrderByComparator<JSONStoreEntry> orderByComparator)
		throws com.liferay.json.store.exception.NoSuchEntryException {

		return getPersistence().findByC_CN_K_T_VL_PrevAndNext(
			jsonStoreEntryId, companyId, classNameId, key, type, valueLong,
			orderByComparator);
	}

	/**
	 * Removes all the json store entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 */
	public static void removeByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type,
		long valueLong) {

		getPersistence().removeByC_CN_K_T_VL(
			companyId, classNameId, key, type, valueLong);
	}

	/**
	 * Returns the number of json store entries where companyId = &#63; and classNameId = &#63; and key = &#63; and type = &#63; and valueLong = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param key the key
	 * @param type the type
	 * @param valueLong the value long
	 * @return the number of matching json store entries
	 */
	public static int countByC_CN_K_T_VL(
		long companyId, long classNameId, String key, int type,
		long valueLong) {

		return getPersistence().countByC_CN_K_T_VL(
			companyId, classNameId, key, type, valueLong);
	}

	/**
	 * Returns the json store entry where classNameId = &#63; and classPK = &#63; and parentJSONStoreEntryId = &#63; and index = &#63; and key = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStoreEntryId the parent json store entry ID
	 * @param index the index
	 * @param key the key
	 * @return the matching json store entry
	 * @throws NoSuchEntryException if a matching json store entry could not be found
	 */
	public static JSONStoreEntry findByCN_CPK_P_I_K(
			long classNameId, long classPK, long parentJSONStoreEntryId,
			int index, String key)
		throws com.liferay.json.store.exception.NoSuchEntryException {

		return getPersistence().findByCN_CPK_P_I_K(
			classNameId, classPK, parentJSONStoreEntryId, index, key);
	}

	/**
	 * Returns the json store entry where classNameId = &#63; and classPK = &#63; and parentJSONStoreEntryId = &#63; and index = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStoreEntryId the parent json store entry ID
	 * @param index the index
	 * @param key the key
	 * @return the matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	public static JSONStoreEntry fetchByCN_CPK_P_I_K(
		long classNameId, long classPK, long parentJSONStoreEntryId, int index,
		String key) {

		return getPersistence().fetchByCN_CPK_P_I_K(
			classNameId, classPK, parentJSONStoreEntryId, index, key);
	}

	/**
	 * Returns the json store entry where classNameId = &#63; and classPK = &#63; and parentJSONStoreEntryId = &#63; and index = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStoreEntryId the parent json store entry ID
	 * @param index the index
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching json store entry, or <code>null</code> if a matching json store entry could not be found
	 */
	public static JSONStoreEntry fetchByCN_CPK_P_I_K(
		long classNameId, long classPK, long parentJSONStoreEntryId, int index,
		String key, boolean useFinderCache) {

		return getPersistence().fetchByCN_CPK_P_I_K(
			classNameId, classPK, parentJSONStoreEntryId, index, key,
			useFinderCache);
	}

	/**
	 * Removes the json store entry where classNameId = &#63; and classPK = &#63; and parentJSONStoreEntryId = &#63; and index = &#63; and key = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStoreEntryId the parent json store entry ID
	 * @param index the index
	 * @param key the key
	 * @return the json store entry that was removed
	 */
	public static JSONStoreEntry removeByCN_CPK_P_I_K(
			long classNameId, long classPK, long parentJSONStoreEntryId,
			int index, String key)
		throws com.liferay.json.store.exception.NoSuchEntryException {

		return getPersistence().removeByCN_CPK_P_I_K(
			classNameId, classPK, parentJSONStoreEntryId, index, key);
	}

	/**
	 * Returns the number of json store entries where classNameId = &#63; and classPK = &#63; and parentJSONStoreEntryId = &#63; and index = &#63; and key = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param parentJSONStoreEntryId the parent json store entry ID
	 * @param index the index
	 * @param key the key
	 * @return the number of matching json store entries
	 */
	public static int countByCN_CPK_P_I_K(
		long classNameId, long classPK, long parentJSONStoreEntryId, int index,
		String key) {

		return getPersistence().countByCN_CPK_P_I_K(
			classNameId, classPK, parentJSONStoreEntryId, index, key);
	}

	/**
	 * Caches the json store entry in the entity cache if it is enabled.
	 *
	 * @param jsonStoreEntry the json store entry
	 */
	public static void cacheResult(JSONStoreEntry jsonStoreEntry) {
		getPersistence().cacheResult(jsonStoreEntry);
	}

	/**
	 * Caches the json store entries in the entity cache if it is enabled.
	 *
	 * @param jsonStoreEntries the json store entries
	 */
	public static void cacheResult(List<JSONStoreEntry> jsonStoreEntries) {
		getPersistence().cacheResult(jsonStoreEntries);
	}

	/**
	 * Creates a new json store entry with the primary key. Does not add the json store entry to the database.
	 *
	 * @param jsonStoreEntryId the primary key for the new json store entry
	 * @return the new json store entry
	 */
	public static JSONStoreEntry create(long jsonStoreEntryId) {
		return getPersistence().create(jsonStoreEntryId);
	}

	/**
	 * Removes the json store entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param jsonStoreEntryId the primary key of the json store entry
	 * @return the json store entry that was removed
	 * @throws NoSuchEntryException if a json store entry with the primary key could not be found
	 */
	public static JSONStoreEntry remove(long jsonStoreEntryId)
		throws com.liferay.json.store.exception.NoSuchEntryException {

		return getPersistence().remove(jsonStoreEntryId);
	}

	public static JSONStoreEntry updateImpl(JSONStoreEntry jsonStoreEntry) {
		return getPersistence().updateImpl(jsonStoreEntry);
	}

	/**
	 * Returns the json store entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param jsonStoreEntryId the primary key of the json store entry
	 * @return the json store entry
	 * @throws NoSuchEntryException if a json store entry with the primary key could not be found
	 */
	public static JSONStoreEntry findByPrimaryKey(long jsonStoreEntryId)
		throws com.liferay.json.store.exception.NoSuchEntryException {

		return getPersistence().findByPrimaryKey(jsonStoreEntryId);
	}

	/**
	 * Returns the json store entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param jsonStoreEntryId the primary key of the json store entry
	 * @return the json store entry, or <code>null</code> if a json store entry with the primary key could not be found
	 */
	public static JSONStoreEntry fetchByPrimaryKey(long jsonStoreEntryId) {
		return getPersistence().fetchByPrimaryKey(jsonStoreEntryId);
	}

	/**
	 * Returns all the json store entries.
	 *
	 * @return the json store entries
	 */
	public static List<JSONStoreEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the json store entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @return the range of json store entries
	 */
	public static List<JSONStoreEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the json store entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of json store entries
	 */
	public static List<JSONStoreEntry> findAll(
		int start, int end,
		OrderByComparator<JSONStoreEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the json store entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JSONStoreEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of json store entries
	 * @param end the upper bound of the range of json store entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of json store entries
	 */
	public static List<JSONStoreEntry> findAll(
		int start, int end, OrderByComparator<JSONStoreEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the json store entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of json store entries.
	 *
	 * @return the number of json store entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static JSONStoreEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<JSONStoreEntryPersistence, JSONStoreEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			JSONStoreEntryPersistence.class);

		ServiceTracker<JSONStoreEntryPersistence, JSONStoreEntryPersistence>
			serviceTracker =
				new ServiceTracker
					<JSONStoreEntryPersistence, JSONStoreEntryPersistence>(
						bundle.getBundleContext(),
						JSONStoreEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}