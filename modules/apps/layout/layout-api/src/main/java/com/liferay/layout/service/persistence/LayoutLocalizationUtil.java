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

package com.liferay.layout.service.persistence;

import com.liferay.layout.model.LayoutLocalization;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the layout localization service. This utility wraps <code>com.liferay.layout.service.persistence.impl.LayoutLocalizationPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutLocalizationPersistence
 * @generated
 */
public class LayoutLocalizationUtil {

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
	public static void clearCache(LayoutLocalization layoutLocalization) {
		getPersistence().clearCache(layoutLocalization);
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
	public static Map<Serializable, LayoutLocalization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LayoutLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LayoutLocalization> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LayoutLocalization update(
		LayoutLocalization layoutLocalization) {

		return getPersistence().update(layoutLocalization);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LayoutLocalization update(
		LayoutLocalization layoutLocalization, ServiceContext serviceContext) {

		return getPersistence().update(layoutLocalization, serviceContext);
	}

	/**
	 * Returns all the layout localizations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout localizations
	 */
	public static List<LayoutLocalization> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the layout localizations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @return the range of matching layout localizations
	 */
	public static List<LayoutLocalization> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the layout localizations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout localizations
	 */
	public static List<LayoutLocalization> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutLocalization> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout localizations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout localizations
	 */
	public static List<LayoutLocalization> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutLocalization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout localization in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout localization
	 * @throws NoSuchLayoutLocalizationException if a matching layout localization could not be found
	 */
	public static LayoutLocalization findByUuid_First(
			String uuid,
			OrderByComparator<LayoutLocalization> orderByComparator)
		throws com.liferay.layout.exception.NoSuchLayoutLocalizationException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first layout localization in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public static LayoutLocalization fetchByUuid_First(
		String uuid, OrderByComparator<LayoutLocalization> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last layout localization in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout localization
	 * @throws NoSuchLayoutLocalizationException if a matching layout localization could not be found
	 */
	public static LayoutLocalization findByUuid_Last(
			String uuid,
			OrderByComparator<LayoutLocalization> orderByComparator)
		throws com.liferay.layout.exception.NoSuchLayoutLocalizationException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last layout localization in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public static LayoutLocalization fetchByUuid_Last(
		String uuid, OrderByComparator<LayoutLocalization> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the layout localizations before and after the current layout localization in the ordered set where uuid = &#63;.
	 *
	 * @param layoutLocalizationId the primary key of the current layout localization
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout localization
	 * @throws NoSuchLayoutLocalizationException if a layout localization with the primary key could not be found
	 */
	public static LayoutLocalization[] findByUuid_PrevAndNext(
			long layoutLocalizationId, String uuid,
			OrderByComparator<LayoutLocalization> orderByComparator)
		throws com.liferay.layout.exception.NoSuchLayoutLocalizationException {

		return getPersistence().findByUuid_PrevAndNext(
			layoutLocalizationId, uuid, orderByComparator);
	}

	/**
	 * Removes all the layout localizations where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of layout localizations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout localizations
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the layout localization where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchLayoutLocalizationException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout localization
	 * @throws NoSuchLayoutLocalizationException if a matching layout localization could not be found
	 */
	public static LayoutLocalization findByUUID_G(String uuid, long groupId)
		throws com.liferay.layout.exception.NoSuchLayoutLocalizationException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the layout localization where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public static LayoutLocalization fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the layout localization where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public static LayoutLocalization fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the layout localization where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout localization that was removed
	 */
	public static LayoutLocalization removeByUUID_G(String uuid, long groupId)
		throws com.liferay.layout.exception.NoSuchLayoutLocalizationException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of layout localizations where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout localizations
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the layout localizations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout localizations
	 */
	public static List<LayoutLocalization> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the layout localizations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @return the range of matching layout localizations
	 */
	public static List<LayoutLocalization> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the layout localizations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout localizations
	 */
	public static List<LayoutLocalization> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutLocalization> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout localizations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout localizations
	 */
	public static List<LayoutLocalization> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutLocalization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout localization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout localization
	 * @throws NoSuchLayoutLocalizationException if a matching layout localization could not be found
	 */
	public static LayoutLocalization findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutLocalization> orderByComparator)
		throws com.liferay.layout.exception.NoSuchLayoutLocalizationException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first layout localization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public static LayoutLocalization fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutLocalization> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last layout localization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout localization
	 * @throws NoSuchLayoutLocalizationException if a matching layout localization could not be found
	 */
	public static LayoutLocalization findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutLocalization> orderByComparator)
		throws com.liferay.layout.exception.NoSuchLayoutLocalizationException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last layout localization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public static LayoutLocalization fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutLocalization> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the layout localizations before and after the current layout localization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutLocalizationId the primary key of the current layout localization
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout localization
	 * @throws NoSuchLayoutLocalizationException if a layout localization with the primary key could not be found
	 */
	public static LayoutLocalization[] findByUuid_C_PrevAndNext(
			long layoutLocalizationId, String uuid, long companyId,
			OrderByComparator<LayoutLocalization> orderByComparator)
		throws com.liferay.layout.exception.NoSuchLayoutLocalizationException {

		return getPersistence().findByUuid_C_PrevAndNext(
			layoutLocalizationId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the layout localizations where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of layout localizations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout localizations
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the layout localizations where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching layout localizations
	 */
	public static List<LayoutLocalization> findByPlid(long plid) {
		return getPersistence().findByPlid(plid);
	}

	/**
	 * Returns a range of all the layout localizations where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @return the range of matching layout localizations
	 */
	public static List<LayoutLocalization> findByPlid(
		long plid, int start, int end) {

		return getPersistence().findByPlid(plid, start, end);
	}

	/**
	 * Returns an ordered range of all the layout localizations where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout localizations
	 */
	public static List<LayoutLocalization> findByPlid(
		long plid, int start, int end,
		OrderByComparator<LayoutLocalization> orderByComparator) {

		return getPersistence().findByPlid(plid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout localizations where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout localizations
	 */
	public static List<LayoutLocalization> findByPlid(
		long plid, int start, int end,
		OrderByComparator<LayoutLocalization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByPlid(
			plid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first layout localization in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout localization
	 * @throws NoSuchLayoutLocalizationException if a matching layout localization could not be found
	 */
	public static LayoutLocalization findByPlid_First(
			long plid, OrderByComparator<LayoutLocalization> orderByComparator)
		throws com.liferay.layout.exception.NoSuchLayoutLocalizationException {

		return getPersistence().findByPlid_First(plid, orderByComparator);
	}

	/**
	 * Returns the first layout localization in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public static LayoutLocalization fetchByPlid_First(
		long plid, OrderByComparator<LayoutLocalization> orderByComparator) {

		return getPersistence().fetchByPlid_First(plid, orderByComparator);
	}

	/**
	 * Returns the last layout localization in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout localization
	 * @throws NoSuchLayoutLocalizationException if a matching layout localization could not be found
	 */
	public static LayoutLocalization findByPlid_Last(
			long plid, OrderByComparator<LayoutLocalization> orderByComparator)
		throws com.liferay.layout.exception.NoSuchLayoutLocalizationException {

		return getPersistence().findByPlid_Last(plid, orderByComparator);
	}

	/**
	 * Returns the last layout localization in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public static LayoutLocalization fetchByPlid_Last(
		long plid, OrderByComparator<LayoutLocalization> orderByComparator) {

		return getPersistence().fetchByPlid_Last(plid, orderByComparator);
	}

	/**
	 * Returns the layout localizations before and after the current layout localization in the ordered set where plid = &#63;.
	 *
	 * @param layoutLocalizationId the primary key of the current layout localization
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout localization
	 * @throws NoSuchLayoutLocalizationException if a layout localization with the primary key could not be found
	 */
	public static LayoutLocalization[] findByPlid_PrevAndNext(
			long layoutLocalizationId, long plid,
			OrderByComparator<LayoutLocalization> orderByComparator)
		throws com.liferay.layout.exception.NoSuchLayoutLocalizationException {

		return getPersistence().findByPlid_PrevAndNext(
			layoutLocalizationId, plid, orderByComparator);
	}

	/**
	 * Removes all the layout localizations where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	public static void removeByPlid(long plid) {
		getPersistence().removeByPlid(plid);
	}

	/**
	 * Returns the number of layout localizations where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching layout localizations
	 */
	public static int countByPlid(long plid) {
		return getPersistence().countByPlid(plid);
	}

	/**
	 * Returns the layout localization where languageId = &#63; and plid = &#63; or throws a <code>NoSuchLayoutLocalizationException</code> if it could not be found.
	 *
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the matching layout localization
	 * @throws NoSuchLayoutLocalizationException if a matching layout localization could not be found
	 */
	public static LayoutLocalization findByL_P(String languageId, long plid)
		throws com.liferay.layout.exception.NoSuchLayoutLocalizationException {

		return getPersistence().findByL_P(languageId, plid);
	}

	/**
	 * Returns the layout localization where languageId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public static LayoutLocalization fetchByL_P(String languageId, long plid) {
		return getPersistence().fetchByL_P(languageId, plid);
	}

	/**
	 * Returns the layout localization where languageId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param languageId the language ID
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public static LayoutLocalization fetchByL_P(
		String languageId, long plid, boolean useFinderCache) {

		return getPersistence().fetchByL_P(languageId, plid, useFinderCache);
	}

	/**
	 * Removes the layout localization where languageId = &#63; and plid = &#63; from the database.
	 *
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the layout localization that was removed
	 */
	public static LayoutLocalization removeByL_P(String languageId, long plid)
		throws com.liferay.layout.exception.NoSuchLayoutLocalizationException {

		return getPersistence().removeByL_P(languageId, plid);
	}

	/**
	 * Returns the number of layout localizations where languageId = &#63; and plid = &#63;.
	 *
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the number of matching layout localizations
	 */
	public static int countByL_P(String languageId, long plid) {
		return getPersistence().countByL_P(languageId, plid);
	}

	/**
	 * Returns the layout localization where groupId = &#63; and languageId = &#63; and plid = &#63; or throws a <code>NoSuchLayoutLocalizationException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the matching layout localization
	 * @throws NoSuchLayoutLocalizationException if a matching layout localization could not be found
	 */
	public static LayoutLocalization findByG_L_P(
			long groupId, String languageId, long plid)
		throws com.liferay.layout.exception.NoSuchLayoutLocalizationException {

		return getPersistence().findByG_L_P(groupId, languageId, plid);
	}

	/**
	 * Returns the layout localization where groupId = &#63; and languageId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public static LayoutLocalization fetchByG_L_P(
		long groupId, String languageId, long plid) {

		return getPersistence().fetchByG_L_P(groupId, languageId, plid);
	}

	/**
	 * Returns the layout localization where groupId = &#63; and languageId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	public static LayoutLocalization fetchByG_L_P(
		long groupId, String languageId, long plid, boolean useFinderCache) {

		return getPersistence().fetchByG_L_P(
			groupId, languageId, plid, useFinderCache);
	}

	/**
	 * Removes the layout localization where groupId = &#63; and languageId = &#63; and plid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the layout localization that was removed
	 */
	public static LayoutLocalization removeByG_L_P(
			long groupId, String languageId, long plid)
		throws com.liferay.layout.exception.NoSuchLayoutLocalizationException {

		return getPersistence().removeByG_L_P(groupId, languageId, plid);
	}

	/**
	 * Returns the number of layout localizations where groupId = &#63; and languageId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the number of matching layout localizations
	 */
	public static int countByG_L_P(long groupId, String languageId, long plid) {
		return getPersistence().countByG_L_P(groupId, languageId, plid);
	}

	/**
	 * Caches the layout localization in the entity cache if it is enabled.
	 *
	 * @param layoutLocalization the layout localization
	 */
	public static void cacheResult(LayoutLocalization layoutLocalization) {
		getPersistence().cacheResult(layoutLocalization);
	}

	/**
	 * Caches the layout localizations in the entity cache if it is enabled.
	 *
	 * @param layoutLocalizations the layout localizations
	 */
	public static void cacheResult(
		List<LayoutLocalization> layoutLocalizations) {

		getPersistence().cacheResult(layoutLocalizations);
	}

	/**
	 * Creates a new layout localization with the primary key. Does not add the layout localization to the database.
	 *
	 * @param layoutLocalizationId the primary key for the new layout localization
	 * @return the new layout localization
	 */
	public static LayoutLocalization create(long layoutLocalizationId) {
		return getPersistence().create(layoutLocalizationId);
	}

	/**
	 * Removes the layout localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutLocalizationId the primary key of the layout localization
	 * @return the layout localization that was removed
	 * @throws NoSuchLayoutLocalizationException if a layout localization with the primary key could not be found
	 */
	public static LayoutLocalization remove(long layoutLocalizationId)
		throws com.liferay.layout.exception.NoSuchLayoutLocalizationException {

		return getPersistence().remove(layoutLocalizationId);
	}

	public static LayoutLocalization updateImpl(
		LayoutLocalization layoutLocalization) {

		return getPersistence().updateImpl(layoutLocalization);
	}

	/**
	 * Returns the layout localization with the primary key or throws a <code>NoSuchLayoutLocalizationException</code> if it could not be found.
	 *
	 * @param layoutLocalizationId the primary key of the layout localization
	 * @return the layout localization
	 * @throws NoSuchLayoutLocalizationException if a layout localization with the primary key could not be found
	 */
	public static LayoutLocalization findByPrimaryKey(long layoutLocalizationId)
		throws com.liferay.layout.exception.NoSuchLayoutLocalizationException {

		return getPersistence().findByPrimaryKey(layoutLocalizationId);
	}

	/**
	 * Returns the layout localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutLocalizationId the primary key of the layout localization
	 * @return the layout localization, or <code>null</code> if a layout localization with the primary key could not be found
	 */
	public static LayoutLocalization fetchByPrimaryKey(
		long layoutLocalizationId) {

		return getPersistence().fetchByPrimaryKey(layoutLocalizationId);
	}

	/**
	 * Returns all the layout localizations.
	 *
	 * @return the layout localizations
	 */
	public static List<LayoutLocalization> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the layout localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @return the range of layout localizations
	 */
	public static List<LayoutLocalization> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the layout localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout localizations
	 */
	public static List<LayoutLocalization> findAll(
		int start, int end,
		OrderByComparator<LayoutLocalization> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the layout localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout localizations
	 */
	public static List<LayoutLocalization> findAll(
		int start, int end,
		OrderByComparator<LayoutLocalization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the layout localizations from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of layout localizations.
	 *
	 * @return the number of layout localizations
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LayoutLocalizationPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		LayoutLocalizationPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile LayoutLocalizationPersistence _persistence;

}