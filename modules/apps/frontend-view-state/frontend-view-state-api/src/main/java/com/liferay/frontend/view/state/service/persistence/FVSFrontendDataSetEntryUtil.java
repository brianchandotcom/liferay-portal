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

package com.liferay.frontend.view.state.service.persistence;

import com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the fvs frontend data set entry service. This utility wraps <code>com.liferay.frontend.view.state.service.persistence.impl.FVSFrontendDataSetEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FVSFrontendDataSetEntryPersistence
 * @generated
 */
public class FVSFrontendDataSetEntryUtil {

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
		FVSFrontendDataSetEntry fvsFrontendDataSetEntry) {

		getPersistence().clearCache(fvsFrontendDataSetEntry);
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
	public static Map<Serializable, FVSFrontendDataSetEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<FVSFrontendDataSetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<FVSFrontendDataSetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<FVSFrontendDataSetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static FVSFrontendDataSetEntry update(
		FVSFrontendDataSetEntry fvsFrontendDataSetEntry) {

		return getPersistence().update(fvsFrontendDataSetEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static FVSFrontendDataSetEntry update(
		FVSFrontendDataSetEntry fvsFrontendDataSetEntry,
		ServiceContext serviceContext) {

		return getPersistence().update(fvsFrontendDataSetEntry, serviceContext);
	}

	/**
	 * Returns all the fvs frontend data set entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fvs frontend data set entries
	 */
	public static List<FVSFrontendDataSetEntry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the fvs frontend data set entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @return the range of matching fvs frontend data set entries
	 */
	public static List<FVSFrontendDataSetEntry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data set entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs frontend data set entries
	 */
	public static List<FVSFrontendDataSetEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data set entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs frontend data set entries
	 */
	public static List<FVSFrontendDataSetEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first fvs frontend data set entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a matching fvs frontend data set entry could not be found
	 */
	public static FVSFrontendDataSetEntry findByUuid_First(
			String uuid,
			OrderByComparator<FVSFrontendDataSetEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFrontendDataSetEntryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first fvs frontend data set entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set entry, or <code>null</code> if a matching fvs frontend data set entry could not be found
	 */
	public static FVSFrontendDataSetEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last fvs frontend data set entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a matching fvs frontend data set entry could not be found
	 */
	public static FVSFrontendDataSetEntry findByUuid_Last(
			String uuid,
			OrderByComparator<FVSFrontendDataSetEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFrontendDataSetEntryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last fvs frontend data set entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set entry, or <code>null</code> if a matching fvs frontend data set entry could not be found
	 */
	public static FVSFrontendDataSetEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the fvs frontend data set entries before and after the current fvs frontend data set entry in the ordered set where uuid = &#63;.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the current fvs frontend data set entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a fvs frontend data set entry with the primary key could not be found
	 */
	public static FVSFrontendDataSetEntry[] findByUuid_PrevAndNext(
			long fvsFrontendDataSetEntryId, String uuid,
			OrderByComparator<FVSFrontendDataSetEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFrontendDataSetEntryException {

		return getPersistence().findByUuid_PrevAndNext(
			fvsFrontendDataSetEntryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the fvs frontend data set entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of fvs frontend data set entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fvs frontend data set entries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the fvs frontend data set entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fvs frontend data set entries
	 */
	public static List<FVSFrontendDataSetEntry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the fvs frontend data set entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @return the range of matching fvs frontend data set entries
	 */
	public static List<FVSFrontendDataSetEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data set entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs frontend data set entries
	 */
	public static List<FVSFrontendDataSetEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data set entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs frontend data set entries
	 */
	public static List<FVSFrontendDataSetEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first fvs frontend data set entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a matching fvs frontend data set entry could not be found
	 */
	public static FVSFrontendDataSetEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<FVSFrontendDataSetEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFrontendDataSetEntryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first fvs frontend data set entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set entry, or <code>null</code> if a matching fvs frontend data set entry could not be found
	 */
	public static FVSFrontendDataSetEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last fvs frontend data set entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a matching fvs frontend data set entry could not be found
	 */
	public static FVSFrontendDataSetEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<FVSFrontendDataSetEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFrontendDataSetEntryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last fvs frontend data set entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set entry, or <code>null</code> if a matching fvs frontend data set entry could not be found
	 */
	public static FVSFrontendDataSetEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the fvs frontend data set entries before and after the current fvs frontend data set entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the current fvs frontend data set entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a fvs frontend data set entry with the primary key could not be found
	 */
	public static FVSFrontendDataSetEntry[] findByUuid_C_PrevAndNext(
			long fvsFrontendDataSetEntryId, String uuid, long companyId,
			OrderByComparator<FVSFrontendDataSetEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFrontendDataSetEntryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			fvsFrontendDataSetEntryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the fvs frontend data set entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of fvs frontend data set entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fvs frontend data set entries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the fvs frontend data set entries where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching fvs frontend data set entries
	 */
	public static List<FVSFrontendDataSetEntry> findByU_F_P_P(
		long userId, String fdsName, long plid, String portletId) {

		return getPersistence().findByU_F_P_P(userId, fdsName, plid, portletId);
	}

	/**
	 * Returns a range of all the fvs frontend data set entries where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @return the range of matching fvs frontend data set entries
	 */
	public static List<FVSFrontendDataSetEntry> findByU_F_P_P(
		long userId, String fdsName, long plid, String portletId, int start,
		int end) {

		return getPersistence().findByU_F_P_P(
			userId, fdsName, plid, portletId, start, end);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data set entries where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs frontend data set entries
	 */
	public static List<FVSFrontendDataSetEntry> findByU_F_P_P(
		long userId, String fdsName, long plid, String portletId, int start,
		int end, OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		return getPersistence().findByU_F_P_P(
			userId, fdsName, plid, portletId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data set entries where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs frontend data set entries
	 */
	public static List<FVSFrontendDataSetEntry> findByU_F_P_P(
		long userId, String fdsName, long plid, String portletId, int start,
		int end, OrderByComparator<FVSFrontendDataSetEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByU_F_P_P(
			userId, fdsName, plid, portletId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first fvs frontend data set entry in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a matching fvs frontend data set entry could not be found
	 */
	public static FVSFrontendDataSetEntry findByU_F_P_P_First(
			long userId, String fdsName, long plid, String portletId,
			OrderByComparator<FVSFrontendDataSetEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFrontendDataSetEntryException {

		return getPersistence().findByU_F_P_P_First(
			userId, fdsName, plid, portletId, orderByComparator);
	}

	/**
	 * Returns the first fvs frontend data set entry in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set entry, or <code>null</code> if a matching fvs frontend data set entry could not be found
	 */
	public static FVSFrontendDataSetEntry fetchByU_F_P_P_First(
		long userId, String fdsName, long plid, String portletId,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		return getPersistence().fetchByU_F_P_P_First(
			userId, fdsName, plid, portletId, orderByComparator);
	}

	/**
	 * Returns the last fvs frontend data set entry in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a matching fvs frontend data set entry could not be found
	 */
	public static FVSFrontendDataSetEntry findByU_F_P_P_Last(
			long userId, String fdsName, long plid, String portletId,
			OrderByComparator<FVSFrontendDataSetEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFrontendDataSetEntryException {

		return getPersistence().findByU_F_P_P_Last(
			userId, fdsName, plid, portletId, orderByComparator);
	}

	/**
	 * Returns the last fvs frontend data set entry in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set entry, or <code>null</code> if a matching fvs frontend data set entry could not be found
	 */
	public static FVSFrontendDataSetEntry fetchByU_F_P_P_Last(
		long userId, String fdsName, long plid, String portletId,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		return getPersistence().fetchByU_F_P_P_Last(
			userId, fdsName, plid, portletId, orderByComparator);
	}

	/**
	 * Returns the fvs frontend data set entries before and after the current fvs frontend data set entry in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the current fvs frontend data set entry
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a fvs frontend data set entry with the primary key could not be found
	 */
	public static FVSFrontendDataSetEntry[] findByU_F_P_P_PrevAndNext(
			long fvsFrontendDataSetEntryId, long userId, String fdsName,
			long plid, String portletId,
			OrderByComparator<FVSFrontendDataSetEntry> orderByComparator)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFrontendDataSetEntryException {

		return getPersistence().findByU_F_P_P_PrevAndNext(
			fvsFrontendDataSetEntryId, userId, fdsName, plid, portletId,
			orderByComparator);
	}

	/**
	 * Removes all the fvs frontend data set entries where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 */
	public static void removeByU_F_P_P(
		long userId, String fdsName, long plid, String portletId) {

		getPersistence().removeByU_F_P_P(userId, fdsName, plid, portletId);
	}

	/**
	 * Returns the number of fvs frontend data set entries where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching fvs frontend data set entries
	 */
	public static int countByU_F_P_P(
		long userId, String fdsName, long plid, String portletId) {

		return getPersistence().countByU_F_P_P(
			userId, fdsName, plid, portletId);
	}

	/**
	 * Caches the fvs frontend data set entry in the entity cache if it is enabled.
	 *
	 * @param fvsFrontendDataSetEntry the fvs frontend data set entry
	 */
	public static void cacheResult(
		FVSFrontendDataSetEntry fvsFrontendDataSetEntry) {

		getPersistence().cacheResult(fvsFrontendDataSetEntry);
	}

	/**
	 * Caches the fvs frontend data set entries in the entity cache if it is enabled.
	 *
	 * @param fvsFrontendDataSetEntries the fvs frontend data set entries
	 */
	public static void cacheResult(
		List<FVSFrontendDataSetEntry> fvsFrontendDataSetEntries) {

		getPersistence().cacheResult(fvsFrontendDataSetEntries);
	}

	/**
	 * Creates a new fvs frontend data set entry with the primary key. Does not add the fvs frontend data set entry to the database.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key for the new fvs frontend data set entry
	 * @return the new fvs frontend data set entry
	 */
	public static FVSFrontendDataSetEntry create(
		long fvsFrontendDataSetEntryId) {

		return getPersistence().create(fvsFrontendDataSetEntryId);
	}

	/**
	 * Removes the fvs frontend data set entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the fvs frontend data set entry
	 * @return the fvs frontend data set entry that was removed
	 * @throws NoSuchFrontendDataSetEntryException if a fvs frontend data set entry with the primary key could not be found
	 */
	public static FVSFrontendDataSetEntry remove(long fvsFrontendDataSetEntryId)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFrontendDataSetEntryException {

		return getPersistence().remove(fvsFrontendDataSetEntryId);
	}

	public static FVSFrontendDataSetEntry updateImpl(
		FVSFrontendDataSetEntry fvsFrontendDataSetEntry) {

		return getPersistence().updateImpl(fvsFrontendDataSetEntry);
	}

	/**
	 * Returns the fvs frontend data set entry with the primary key or throws a <code>NoSuchFrontendDataSetEntryException</code> if it could not be found.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the fvs frontend data set entry
	 * @return the fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a fvs frontend data set entry with the primary key could not be found
	 */
	public static FVSFrontendDataSetEntry findByPrimaryKey(
			long fvsFrontendDataSetEntryId)
		throws com.liferay.frontend.view.state.exception.
			NoSuchFrontendDataSetEntryException {

		return getPersistence().findByPrimaryKey(fvsFrontendDataSetEntryId);
	}

	/**
	 * Returns the fvs frontend data set entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the fvs frontend data set entry
	 * @return the fvs frontend data set entry, or <code>null</code> if a fvs frontend data set entry with the primary key could not be found
	 */
	public static FVSFrontendDataSetEntry fetchByPrimaryKey(
		long fvsFrontendDataSetEntryId) {

		return getPersistence().fetchByPrimaryKey(fvsFrontendDataSetEntryId);
	}

	/**
	 * Returns all the fvs frontend data set entries.
	 *
	 * @return the fvs frontend data set entries
	 */
	public static List<FVSFrontendDataSetEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the fvs frontend data set entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @return the range of fvs frontend data set entries
	 */
	public static List<FVSFrontendDataSetEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data set entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fvs frontend data set entries
	 */
	public static List<FVSFrontendDataSetEntry> findAll(
		int start, int end,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data set entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fvs frontend data set entries
	 */
	public static List<FVSFrontendDataSetEntry> findAll(
		int start, int end,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the fvs frontend data set entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of fvs frontend data set entries.
	 *
	 * @return the number of fvs frontend data set entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static FVSFrontendDataSetEntryPersistence getPersistence() {
		return _persistence;
	}

	private static volatile FVSFrontendDataSetEntryPersistence _persistence;

}