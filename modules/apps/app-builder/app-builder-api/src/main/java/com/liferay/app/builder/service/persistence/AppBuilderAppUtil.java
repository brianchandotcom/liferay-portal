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

package com.liferay.app.builder.service.persistence;

import com.liferay.app.builder.model.AppBuilderApp;
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
 * The persistence utility for the app builder app service. This utility wraps <code>com.liferay.app.builder.service.persistence.impl.AppBuilderAppPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppPersistence
 * @generated
 */
public class AppBuilderAppUtil {

	/**
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
	public static void clearCache(AppBuilderApp appBuilderApp) {
		getPersistence().clearCache(appBuilderApp);
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
	public static Map<Serializable, AppBuilderApp> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AppBuilderApp> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AppBuilderApp> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AppBuilderApp> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AppBuilderApp update(AppBuilderApp appBuilderApp) {
		return getPersistence().update(appBuilderApp);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AppBuilderApp update(
		AppBuilderApp appBuilderApp, ServiceContext serviceContext) {

		return getPersistence().update(appBuilderApp, serviceContext);
	}

	/**
	 * Returns all the app builder apps where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching app builder apps
	 */
	public static List<AppBuilderApp> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the app builder apps where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	public static List<AppBuilderApp> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the app builder apps where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	public static List<AppBuilderApp> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the app builder apps where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	public static List<AppBuilderApp> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first app builder app in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public static AppBuilderApp findByUuid_First(
			String uuid, OrderByComparator<AppBuilderApp> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first app builder app in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public static AppBuilderApp fetchByUuid_First(
		String uuid, OrderByComparator<AppBuilderApp> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last app builder app in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public static AppBuilderApp findByUuid_Last(
			String uuid, OrderByComparator<AppBuilderApp> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last app builder app in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public static AppBuilderApp fetchByUuid_Last(
		String uuid, OrderByComparator<AppBuilderApp> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where uuid = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	public static AppBuilderApp[] findByUuid_PrevAndNext(
			long appBuilderAppId, String uuid,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppException {

		return getPersistence().findByUuid_PrevAndNext(
			appBuilderAppId, uuid, orderByComparator);
	}

	/**
	 * Removes all the app builder apps where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of app builder apps where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching app builder apps
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the app builder app where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchAppException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public static AppBuilderApp findByUUID_G(String uuid, long groupId)
		throws com.liferay.app.builder.exception.NoSuchAppException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the app builder app where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public static AppBuilderApp fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the app builder app where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public static AppBuilderApp fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the app builder app where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the app builder app that was removed
	 */
	public static AppBuilderApp removeByUUID_G(String uuid, long groupId)
		throws com.liferay.app.builder.exception.NoSuchAppException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of app builder apps where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching app builder apps
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the app builder apps where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching app builder apps
	 */
	public static List<AppBuilderApp> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the app builder apps where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	public static List<AppBuilderApp> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the app builder apps where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	public static List<AppBuilderApp> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the app builder apps where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	public static List<AppBuilderApp> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first app builder app in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public static AppBuilderApp findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first app builder app in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public static AppBuilderApp fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last app builder app in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public static AppBuilderApp findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last app builder app in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public static AppBuilderApp fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	public static AppBuilderApp[] findByUuid_C_PrevAndNext(
			long appBuilderAppId, String uuid, long companyId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppException {

		return getPersistence().findByUuid_C_PrevAndNext(
			appBuilderAppId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the app builder apps where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of app builder apps where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching app builder apps
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the app builder apps where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching app builder apps
	 */
	public static List<AppBuilderApp> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the app builder apps where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	public static List<AppBuilderApp> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the app builder apps where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	public static List<AppBuilderApp> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the app builder apps where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	public static List<AppBuilderApp> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first app builder app in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public static AppBuilderApp findByGroupId_First(
			long groupId, OrderByComparator<AppBuilderApp> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first app builder app in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public static AppBuilderApp fetchByGroupId_First(
		long groupId, OrderByComparator<AppBuilderApp> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last app builder app in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public static AppBuilderApp findByGroupId_Last(
			long groupId, OrderByComparator<AppBuilderApp> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last app builder app in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public static AppBuilderApp fetchByGroupId_Last(
		long groupId, OrderByComparator<AppBuilderApp> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where groupId = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	public static AppBuilderApp[] findByGroupId_PrevAndNext(
			long appBuilderAppId, long groupId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppException {

		return getPersistence().findByGroupId_PrevAndNext(
			appBuilderAppId, groupId, orderByComparator);
	}

	/**
	 * Removes all the app builder apps where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of app builder apps where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching app builder apps
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the app builder apps where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching app builder apps
	 */
	public static List<AppBuilderApp> findByC_S(long companyId, int status) {
		return getPersistence().findByC_S(companyId, status);
	}

	/**
	 * Returns a range of all the app builder apps where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	public static List<AppBuilderApp> findByC_S(
		long companyId, int status, int start, int end) {

		return getPersistence().findByC_S(companyId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the app builder apps where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	public static List<AppBuilderApp> findByC_S(
		long companyId, int status, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return getPersistence().findByC_S(
			companyId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the app builder apps where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	public static List<AppBuilderApp> findByC_S(
		long companyId, int status, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_S(
			companyId, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first app builder app in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public static AppBuilderApp findByC_S_First(
			long companyId, int status,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppException {

		return getPersistence().findByC_S_First(
			companyId, status, orderByComparator);
	}

	/**
	 * Returns the first app builder app in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public static AppBuilderApp fetchByC_S_First(
		long companyId, int status,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return getPersistence().fetchByC_S_First(
			companyId, status, orderByComparator);
	}

	/**
	 * Returns the last app builder app in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public static AppBuilderApp findByC_S_Last(
			long companyId, int status,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppException {

		return getPersistence().findByC_S_Last(
			companyId, status, orderByComparator);
	}

	/**
	 * Returns the last app builder app in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public static AppBuilderApp fetchByC_S_Last(
		long companyId, int status,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return getPersistence().fetchByC_S_Last(
			companyId, status, orderByComparator);
	}

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	public static AppBuilderApp[] findByC_S_PrevAndNext(
			long appBuilderAppId, long companyId, int status,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppException {

		return getPersistence().findByC_S_PrevAndNext(
			appBuilderAppId, companyId, status, orderByComparator);
	}

	/**
	 * Removes all the app builder apps where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	public static void removeByC_S(long companyId, int status) {
		getPersistence().removeByC_S(companyId, status);
	}

	/**
	 * Returns the number of app builder apps where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching app builder apps
	 */
	public static int countByC_S(long companyId, int status) {
		return getPersistence().countByC_S(companyId, status);
	}

	/**
	 * Returns all the app builder apps where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @return the matching app builder apps
	 */
	public static List<AppBuilderApp> findByG_C_D(
		long groupId, long companyId, long ddmStructureId) {

		return getPersistence().findByG_C_D(groupId, companyId, ddmStructureId);
	}

	/**
	 * Returns a range of all the app builder apps where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of matching app builder apps
	 */
	public static List<AppBuilderApp> findByG_C_D(
		long groupId, long companyId, long ddmStructureId, int start, int end) {

		return getPersistence().findByG_C_D(
			groupId, companyId, ddmStructureId, start, end);
	}

	/**
	 * Returns an ordered range of all the app builder apps where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder apps
	 */
	public static List<AppBuilderApp> findByG_C_D(
		long groupId, long companyId, long ddmStructureId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return getPersistence().findByG_C_D(
			groupId, companyId, ddmStructureId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the app builder apps where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder apps
	 */
	public static List<AppBuilderApp> findByG_C_D(
		long groupId, long companyId, long ddmStructureId, int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_C_D(
			groupId, companyId, ddmStructureId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first app builder app in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public static AppBuilderApp findByG_C_D_First(
			long groupId, long companyId, long ddmStructureId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppException {

		return getPersistence().findByG_C_D_First(
			groupId, companyId, ddmStructureId, orderByComparator);
	}

	/**
	 * Returns the first app builder app in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public static AppBuilderApp fetchByG_C_D_First(
		long groupId, long companyId, long ddmStructureId,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return getPersistence().fetchByG_C_D_First(
			groupId, companyId, ddmStructureId, orderByComparator);
	}

	/**
	 * Returns the last app builder app in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app
	 * @throws NoSuchAppException if a matching app builder app could not be found
	 */
	public static AppBuilderApp findByG_C_D_Last(
			long groupId, long companyId, long ddmStructureId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppException {

		return getPersistence().findByG_C_D_Last(
			groupId, companyId, ddmStructureId, orderByComparator);
	}

	/**
	 * Returns the last app builder app in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app, or <code>null</code> if a matching app builder app could not be found
	 */
	public static AppBuilderApp fetchByG_C_D_Last(
		long groupId, long companyId, long ddmStructureId,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return getPersistence().fetchByG_C_D_Last(
			groupId, companyId, ddmStructureId, orderByComparator);
	}

	/**
	 * Returns the app builder apps before and after the current app builder app in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param appBuilderAppId the primary key of the current app builder app
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	public static AppBuilderApp[] findByG_C_D_PrevAndNext(
			long appBuilderAppId, long groupId, long companyId,
			long ddmStructureId,
			OrderByComparator<AppBuilderApp> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppException {

		return getPersistence().findByG_C_D_PrevAndNext(
			appBuilderAppId, groupId, companyId, ddmStructureId,
			orderByComparator);
	}

	/**
	 * Removes all the app builder apps where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 */
	public static void removeByG_C_D(
		long groupId, long companyId, long ddmStructureId) {

		getPersistence().removeByG_C_D(groupId, companyId, ddmStructureId);
	}

	/**
	 * Returns the number of app builder apps where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @return the number of matching app builder apps
	 */
	public static int countByG_C_D(
		long groupId, long companyId, long ddmStructureId) {

		return getPersistence().countByG_C_D(
			groupId, companyId, ddmStructureId);
	}

	/**
	 * Caches the app builder app in the entity cache if it is enabled.
	 *
	 * @param appBuilderApp the app builder app
	 */
	public static void cacheResult(AppBuilderApp appBuilderApp) {
		getPersistence().cacheResult(appBuilderApp);
	}

	/**
	 * Caches the app builder apps in the entity cache if it is enabled.
	 *
	 * @param appBuilderApps the app builder apps
	 */
	public static void cacheResult(List<AppBuilderApp> appBuilderApps) {
		getPersistence().cacheResult(appBuilderApps);
	}

	/**
	 * Creates a new app builder app with the primary key. Does not add the app builder app to the database.
	 *
	 * @param appBuilderAppId the primary key for the new app builder app
	 * @return the new app builder app
	 */
	public static AppBuilderApp create(long appBuilderAppId) {
		return getPersistence().create(appBuilderAppId);
	}

	/**
	 * Removes the app builder app with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppId the primary key of the app builder app
	 * @return the app builder app that was removed
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	public static AppBuilderApp remove(long appBuilderAppId)
		throws com.liferay.app.builder.exception.NoSuchAppException {

		return getPersistence().remove(appBuilderAppId);
	}

	public static AppBuilderApp updateImpl(AppBuilderApp appBuilderApp) {
		return getPersistence().updateImpl(appBuilderApp);
	}

	/**
	 * Returns the app builder app with the primary key or throws a <code>NoSuchAppException</code> if it could not be found.
	 *
	 * @param appBuilderAppId the primary key of the app builder app
	 * @return the app builder app
	 * @throws NoSuchAppException if a app builder app with the primary key could not be found
	 */
	public static AppBuilderApp findByPrimaryKey(long appBuilderAppId)
		throws com.liferay.app.builder.exception.NoSuchAppException {

		return getPersistence().findByPrimaryKey(appBuilderAppId);
	}

	/**
	 * Returns the app builder app with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param appBuilderAppId the primary key of the app builder app
	 * @return the app builder app, or <code>null</code> if a app builder app with the primary key could not be found
	 */
	public static AppBuilderApp fetchByPrimaryKey(long appBuilderAppId) {
		return getPersistence().fetchByPrimaryKey(appBuilderAppId);
	}

	/**
	 * Returns all the app builder apps.
	 *
	 * @return the app builder apps
	 */
	public static List<AppBuilderApp> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the app builder apps.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @return the range of app builder apps
	 */
	public static List<AppBuilderApp> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the app builder apps.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of app builder apps
	 */
	public static List<AppBuilderApp> findAll(
		int start, int end,
		OrderByComparator<AppBuilderApp> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the app builder apps.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder apps
	 * @param end the upper bound of the range of app builder apps (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of app builder apps
	 */
	public static List<AppBuilderApp> findAll(
		int start, int end, OrderByComparator<AppBuilderApp> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the app builder apps from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of app builder apps.
	 *
	 * @return the number of app builder apps
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AppBuilderAppPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AppBuilderAppPersistence, AppBuilderAppPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AppBuilderAppPersistence.class);

		ServiceTracker<AppBuilderAppPersistence, AppBuilderAppPersistence>
			serviceTracker =
				new ServiceTracker
					<AppBuilderAppPersistence, AppBuilderAppPersistence>(
						bundle.getBundleContext(),
						AppBuilderAppPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}