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

import com.liferay.app.builder.model.AppBuilderAppVersion;
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
 * The persistence utility for the app builder app version service. This utility wraps <code>com.liferay.app.builder.service.persistence.impl.AppBuilderAppVersionPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppVersionPersistence
 * @generated
 */
public class AppBuilderAppVersionUtil {

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
	public static void clearCache(AppBuilderAppVersion appBuilderAppVersion) {
		getPersistence().clearCache(appBuilderAppVersion);
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
	public static Map<Serializable, AppBuilderAppVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AppBuilderAppVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AppBuilderAppVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AppBuilderAppVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AppBuilderAppVersion update(
		AppBuilderAppVersion appBuilderAppVersion) {

		return getPersistence().update(appBuilderAppVersion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AppBuilderAppVersion update(
		AppBuilderAppVersion appBuilderAppVersion,
		ServiceContext serviceContext) {

		return getPersistence().update(appBuilderAppVersion, serviceContext);
	}

	/**
	 * Returns all the app builder app versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the app builder app versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @return the range of matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first app builder app version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion findByUuid_First(
			String uuid,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first app builder app version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion fetchByUuid_First(
		String uuid,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last app builder app version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion findByUuid_Last(
			String uuid,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last app builder app version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion fetchByUuid_Last(
		String uuid,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the app builder app versions before and after the current app builder app version in the ordered set where uuid = &#63;.
	 *
	 * @param appBuilderAppVersionId the primary key of the current app builder app version
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app version
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	public static AppBuilderAppVersion[] findByUuid_PrevAndNext(
			long appBuilderAppVersionId, String uuid,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().findByUuid_PrevAndNext(
			appBuilderAppVersionId, uuid, orderByComparator);
	}

	/**
	 * Removes all the app builder app versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of app builder app versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching app builder app versions
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the app builder app version where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchAppVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion findByUUID_G(String uuid, long groupId)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the app builder app version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the app builder app version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the app builder app version where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the app builder app version that was removed
	 */
	public static AppBuilderAppVersion removeByUUID_G(String uuid, long groupId)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of app builder app versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching app builder app versions
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the app builder app versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the app builder app versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @return the range of matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first app builder app version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first app builder app version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last app builder app version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last app builder app version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the app builder app versions before and after the current app builder app version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param appBuilderAppVersionId the primary key of the current app builder app version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app version
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	public static AppBuilderAppVersion[] findByUuid_C_PrevAndNext(
			long appBuilderAppVersionId, String uuid, long companyId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().findByUuid_C_PrevAndNext(
			appBuilderAppVersionId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the app builder app versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of app builder app versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching app builder app versions
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the app builder app versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the app builder app versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @return the range of matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first app builder app version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion findByGroupId_First(
			long groupId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first app builder app version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion fetchByGroupId_First(
		long groupId,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last app builder app version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion findByGroupId_Last(
			long groupId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last app builder app version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion fetchByGroupId_Last(
		long groupId,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the app builder app versions before and after the current app builder app version in the ordered set where groupId = &#63;.
	 *
	 * @param appBuilderAppVersionId the primary key of the current app builder app version
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app version
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	public static AppBuilderAppVersion[] findByGroupId_PrevAndNext(
			long appBuilderAppVersionId, long groupId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().findByGroupId_PrevAndNext(
			appBuilderAppVersionId, groupId, orderByComparator);
	}

	/**
	 * Removes all the app builder app versions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of app builder app versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching app builder app versions
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the app builder app versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the app builder app versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @return the range of matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first app builder app version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion findByCompanyId_First(
			long companyId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first app builder app version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion fetchByCompanyId_First(
		long companyId,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last app builder app version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion findByCompanyId_Last(
			long companyId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last app builder app version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the app builder app versions before and after the current app builder app version in the ordered set where companyId = &#63;.
	 *
	 * @param appBuilderAppVersionId the primary key of the current app builder app version
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app version
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	public static AppBuilderAppVersion[] findByCompanyId_PrevAndNext(
			long appBuilderAppVersionId, long companyId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().findByCompanyId_PrevAndNext(
			appBuilderAppVersionId, companyId, orderByComparator);
	}

	/**
	 * Removes all the app builder app versions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of app builder app versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching app builder app versions
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the app builder app versions where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByAppBuilderAppId(
		long appBuilderAppId) {

		return getPersistence().findByAppBuilderAppId(appBuilderAppId);
	}

	/**
	 * Returns a range of all the app builder app versions where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @return the range of matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end) {

		return getPersistence().findByAppBuilderAppId(
			appBuilderAppId, start, end);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return getPersistence().findByAppBuilderAppId(
			appBuilderAppId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the app builder app versions where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app versions
	 */
	public static List<AppBuilderAppVersion> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAppBuilderAppId(
			appBuilderAppId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first app builder app version in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion findByAppBuilderAppId_First(
			long appBuilderAppId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().findByAppBuilderAppId_First(
			appBuilderAppId, orderByComparator);
	}

	/**
	 * Returns the first app builder app version in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion fetchByAppBuilderAppId_First(
		long appBuilderAppId,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return getPersistence().fetchByAppBuilderAppId_First(
			appBuilderAppId, orderByComparator);
	}

	/**
	 * Returns the last app builder app version in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion findByAppBuilderAppId_Last(
			long appBuilderAppId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().findByAppBuilderAppId_Last(
			appBuilderAppId, orderByComparator);
	}

	/**
	 * Returns the last app builder app version in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion fetchByAppBuilderAppId_Last(
		long appBuilderAppId,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return getPersistence().fetchByAppBuilderAppId_Last(
			appBuilderAppId, orderByComparator);
	}

	/**
	 * Returns the app builder app versions before and after the current app builder app version in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppVersionId the primary key of the current app builder app version
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app version
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	public static AppBuilderAppVersion[] findByAppBuilderAppId_PrevAndNext(
			long appBuilderAppVersionId, long appBuilderAppId,
			OrderByComparator<AppBuilderAppVersion> orderByComparator)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().findByAppBuilderAppId_PrevAndNext(
			appBuilderAppVersionId, appBuilderAppId, orderByComparator);
	}

	/**
	 * Removes all the app builder app versions where appBuilderAppId = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 */
	public static void removeByAppBuilderAppId(long appBuilderAppId) {
		getPersistence().removeByAppBuilderAppId(appBuilderAppId);
	}

	/**
	 * Returns the number of app builder app versions where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the number of matching app builder app versions
	 */
	public static int countByAppBuilderAppId(long appBuilderAppId) {
		return getPersistence().countByAppBuilderAppId(appBuilderAppId);
	}

	/**
	 * Returns the app builder app version where appBuilderAppId = &#63; and version = &#63; or throws a <code>NoSuchAppVersionException</code> if it could not be found.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param version the version
	 * @return the matching app builder app version
	 * @throws NoSuchAppVersionException if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion findByA_V(
			long appBuilderAppId, String version)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().findByA_V(appBuilderAppId, version);
	}

	/**
	 * Returns the app builder app version where appBuilderAppId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param version the version
	 * @return the matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion fetchByA_V(
		long appBuilderAppId, String version) {

		return getPersistence().fetchByA_V(appBuilderAppId, version);
	}

	/**
	 * Returns the app builder app version where appBuilderAppId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching app builder app version, or <code>null</code> if a matching app builder app version could not be found
	 */
	public static AppBuilderAppVersion fetchByA_V(
		long appBuilderAppId, String version, boolean useFinderCache) {

		return getPersistence().fetchByA_V(
			appBuilderAppId, version, useFinderCache);
	}

	/**
	 * Removes the app builder app version where appBuilderAppId = &#63; and version = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param version the version
	 * @return the app builder app version that was removed
	 */
	public static AppBuilderAppVersion removeByA_V(
			long appBuilderAppId, String version)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().removeByA_V(appBuilderAppId, version);
	}

	/**
	 * Returns the number of app builder app versions where appBuilderAppId = &#63; and version = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param version the version
	 * @return the number of matching app builder app versions
	 */
	public static int countByA_V(long appBuilderAppId, String version) {
		return getPersistence().countByA_V(appBuilderAppId, version);
	}

	/**
	 * Caches the app builder app version in the entity cache if it is enabled.
	 *
	 * @param appBuilderAppVersion the app builder app version
	 */
	public static void cacheResult(AppBuilderAppVersion appBuilderAppVersion) {
		getPersistence().cacheResult(appBuilderAppVersion);
	}

	/**
	 * Caches the app builder app versions in the entity cache if it is enabled.
	 *
	 * @param appBuilderAppVersions the app builder app versions
	 */
	public static void cacheResult(
		List<AppBuilderAppVersion> appBuilderAppVersions) {

		getPersistence().cacheResult(appBuilderAppVersions);
	}

	/**
	 * Creates a new app builder app version with the primary key. Does not add the app builder app version to the database.
	 *
	 * @param appBuilderAppVersionId the primary key for the new app builder app version
	 * @return the new app builder app version
	 */
	public static AppBuilderAppVersion create(long appBuilderAppVersionId) {
		return getPersistence().create(appBuilderAppVersionId);
	}

	/**
	 * Removes the app builder app version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppVersionId the primary key of the app builder app version
	 * @return the app builder app version that was removed
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	public static AppBuilderAppVersion remove(long appBuilderAppVersionId)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().remove(appBuilderAppVersionId);
	}

	public static AppBuilderAppVersion updateImpl(
		AppBuilderAppVersion appBuilderAppVersion) {

		return getPersistence().updateImpl(appBuilderAppVersion);
	}

	/**
	 * Returns the app builder app version with the primary key or throws a <code>NoSuchAppVersionException</code> if it could not be found.
	 *
	 * @param appBuilderAppVersionId the primary key of the app builder app version
	 * @return the app builder app version
	 * @throws NoSuchAppVersionException if a app builder app version with the primary key could not be found
	 */
	public static AppBuilderAppVersion findByPrimaryKey(
			long appBuilderAppVersionId)
		throws com.liferay.app.builder.exception.NoSuchAppVersionException {

		return getPersistence().findByPrimaryKey(appBuilderAppVersionId);
	}

	/**
	 * Returns the app builder app version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param appBuilderAppVersionId the primary key of the app builder app version
	 * @return the app builder app version, or <code>null</code> if a app builder app version with the primary key could not be found
	 */
	public static AppBuilderAppVersion fetchByPrimaryKey(
		long appBuilderAppVersionId) {

		return getPersistence().fetchByPrimaryKey(appBuilderAppVersionId);
	}

	/**
	 * Returns all the app builder app versions.
	 *
	 * @return the app builder app versions
	 */
	public static List<AppBuilderAppVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the app builder app versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @return the range of app builder app versions
	 */
	public static List<AppBuilderAppVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the app builder app versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of app builder app versions
	 */
	public static List<AppBuilderAppVersion> findAll(
		int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the app builder app versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app versions
	 * @param end the upper bound of the range of app builder app versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of app builder app versions
	 */
	public static List<AppBuilderAppVersion> findAll(
		int start, int end,
		OrderByComparator<AppBuilderAppVersion> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the app builder app versions from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of app builder app versions.
	 *
	 * @return the number of app builder app versions
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AppBuilderAppVersionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AppBuilderAppVersionPersistence, AppBuilderAppVersionPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AppBuilderAppVersionPersistence.class);

		ServiceTracker
			<AppBuilderAppVersionPersistence, AppBuilderAppVersionPersistence>
				serviceTracker =
					new ServiceTracker
						<AppBuilderAppVersionPersistence,
						 AppBuilderAppVersionPersistence>(
							 bundle.getBundleContext(),
							 AppBuilderAppVersionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}