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

package com.liferay.custom.elements.service.persistence;

import com.liferay.custom.elements.model.CustomElementsSource;
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
 * The persistence utility for the custom elements source service. This utility wraps <code>com.liferay.custom.elements.service.persistence.impl.CustomElementsSourcePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CustomElementsSourcePersistence
 * @generated
 */
public class CustomElementsSourceUtil {

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
	public static void clearCache(CustomElementsSource customElementsSource) {
		getPersistence().clearCache(customElementsSource);
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
	public static Map<Serializable, CustomElementsSource> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CustomElementsSource> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CustomElementsSource> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CustomElementsSource> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CustomElementsSource> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CustomElementsSource update(
		CustomElementsSource customElementsSource) {

		return getPersistence().update(customElementsSource);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CustomElementsSource update(
		CustomElementsSource customElementsSource,
		ServiceContext serviceContext) {

		return getPersistence().update(customElementsSource, serviceContext);
	}

	/**
	 * Returns all the custom elements sources where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching custom elements sources
	 */
	public static List<CustomElementsSource> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the custom elements sources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @return the range of matching custom elements sources
	 */
	public static List<CustomElementsSource> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the custom elements sources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching custom elements sources
	 */
	public static List<CustomElementsSource> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CustomElementsSource> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the custom elements sources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching custom elements sources
	 */
	public static List<CustomElementsSource> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CustomElementsSource> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first custom elements source in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom elements source
	 * @throws NoSuchSourceException if a matching custom elements source could not be found
	 */
	public static CustomElementsSource findByUuid_First(
			String uuid,
			OrderByComparator<CustomElementsSource> orderByComparator)
		throws com.liferay.custom.elements.exception.NoSuchSourceException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first custom elements source in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom elements source, or <code>null</code> if a matching custom elements source could not be found
	 */
	public static CustomElementsSource fetchByUuid_First(
		String uuid,
		OrderByComparator<CustomElementsSource> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last custom elements source in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom elements source
	 * @throws NoSuchSourceException if a matching custom elements source could not be found
	 */
	public static CustomElementsSource findByUuid_Last(
			String uuid,
			OrderByComparator<CustomElementsSource> orderByComparator)
		throws com.liferay.custom.elements.exception.NoSuchSourceException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last custom elements source in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom elements source, or <code>null</code> if a matching custom elements source could not be found
	 */
	public static CustomElementsSource fetchByUuid_Last(
		String uuid,
		OrderByComparator<CustomElementsSource> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the custom elements sources before and after the current custom elements source in the ordered set where uuid = &#63;.
	 *
	 * @param customElementsSourceId the primary key of the current custom elements source
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next custom elements source
	 * @throws NoSuchSourceException if a custom elements source with the primary key could not be found
	 */
	public static CustomElementsSource[] findByUuid_PrevAndNext(
			long customElementsSourceId, String uuid,
			OrderByComparator<CustomElementsSource> orderByComparator)
		throws com.liferay.custom.elements.exception.NoSuchSourceException {

		return getPersistence().findByUuid_PrevAndNext(
			customElementsSourceId, uuid, orderByComparator);
	}

	/**
	 * Removes all the custom elements sources where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of custom elements sources where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching custom elements sources
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the custom elements sources where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching custom elements sources
	 */
	public static List<CustomElementsSource> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the custom elements sources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @return the range of matching custom elements sources
	 */
	public static List<CustomElementsSource> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the custom elements sources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching custom elements sources
	 */
	public static List<CustomElementsSource> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CustomElementsSource> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the custom elements sources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching custom elements sources
	 */
	public static List<CustomElementsSource> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CustomElementsSource> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first custom elements source in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom elements source
	 * @throws NoSuchSourceException if a matching custom elements source could not be found
	 */
	public static CustomElementsSource findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CustomElementsSource> orderByComparator)
		throws com.liferay.custom.elements.exception.NoSuchSourceException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first custom elements source in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom elements source, or <code>null</code> if a matching custom elements source could not be found
	 */
	public static CustomElementsSource fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CustomElementsSource> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last custom elements source in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom elements source
	 * @throws NoSuchSourceException if a matching custom elements source could not be found
	 */
	public static CustomElementsSource findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CustomElementsSource> orderByComparator)
		throws com.liferay.custom.elements.exception.NoSuchSourceException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last custom elements source in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom elements source, or <code>null</code> if a matching custom elements source could not be found
	 */
	public static CustomElementsSource fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CustomElementsSource> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the custom elements sources before and after the current custom elements source in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param customElementsSourceId the primary key of the current custom elements source
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next custom elements source
	 * @throws NoSuchSourceException if a custom elements source with the primary key could not be found
	 */
	public static CustomElementsSource[] findByUuid_C_PrevAndNext(
			long customElementsSourceId, String uuid, long companyId,
			OrderByComparator<CustomElementsSource> orderByComparator)
		throws com.liferay.custom.elements.exception.NoSuchSourceException {

		return getPersistence().findByUuid_C_PrevAndNext(
			customElementsSourceId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the custom elements sources where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of custom elements sources where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching custom elements sources
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the custom elements sources where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching custom elements sources
	 */
	public static List<CustomElementsSource> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the custom elements sources where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @return the range of matching custom elements sources
	 */
	public static List<CustomElementsSource> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the custom elements sources where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching custom elements sources
	 */
	public static List<CustomElementsSource> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CustomElementsSource> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the custom elements sources where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching custom elements sources
	 */
	public static List<CustomElementsSource> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CustomElementsSource> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first custom elements source in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom elements source
	 * @throws NoSuchSourceException if a matching custom elements source could not be found
	 */
	public static CustomElementsSource findByCompanyId_First(
			long companyId,
			OrderByComparator<CustomElementsSource> orderByComparator)
		throws com.liferay.custom.elements.exception.NoSuchSourceException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first custom elements source in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom elements source, or <code>null</code> if a matching custom elements source could not be found
	 */
	public static CustomElementsSource fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CustomElementsSource> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last custom elements source in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom elements source
	 * @throws NoSuchSourceException if a matching custom elements source could not be found
	 */
	public static CustomElementsSource findByCompanyId_Last(
			long companyId,
			OrderByComparator<CustomElementsSource> orderByComparator)
		throws com.liferay.custom.elements.exception.NoSuchSourceException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last custom elements source in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom elements source, or <code>null</code> if a matching custom elements source could not be found
	 */
	public static CustomElementsSource fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CustomElementsSource> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the custom elements sources before and after the current custom elements source in the ordered set where companyId = &#63;.
	 *
	 * @param customElementsSourceId the primary key of the current custom elements source
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next custom elements source
	 * @throws NoSuchSourceException if a custom elements source with the primary key could not be found
	 */
	public static CustomElementsSource[] findByCompanyId_PrevAndNext(
			long customElementsSourceId, long companyId,
			OrderByComparator<CustomElementsSource> orderByComparator)
		throws com.liferay.custom.elements.exception.NoSuchSourceException {

		return getPersistence().findByCompanyId_PrevAndNext(
			customElementsSourceId, companyId, orderByComparator);
	}

	/**
	 * Removes all the custom elements sources where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of custom elements sources where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching custom elements sources
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Caches the custom elements source in the entity cache if it is enabled.
	 *
	 * @param customElementsSource the custom elements source
	 */
	public static void cacheResult(CustomElementsSource customElementsSource) {
		getPersistence().cacheResult(customElementsSource);
	}

	/**
	 * Caches the custom elements sources in the entity cache if it is enabled.
	 *
	 * @param customElementsSources the custom elements sources
	 */
	public static void cacheResult(
		List<CustomElementsSource> customElementsSources) {

		getPersistence().cacheResult(customElementsSources);
	}

	/**
	 * Creates a new custom elements source with the primary key. Does not add the custom elements source to the database.
	 *
	 * @param customElementsSourceId the primary key for the new custom elements source
	 * @return the new custom elements source
	 */
	public static CustomElementsSource create(long customElementsSourceId) {
		return getPersistence().create(customElementsSourceId);
	}

	/**
	 * Removes the custom elements source with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param customElementsSourceId the primary key of the custom elements source
	 * @return the custom elements source that was removed
	 * @throws NoSuchSourceException if a custom elements source with the primary key could not be found
	 */
	public static CustomElementsSource remove(long customElementsSourceId)
		throws com.liferay.custom.elements.exception.NoSuchSourceException {

		return getPersistence().remove(customElementsSourceId);
	}

	public static CustomElementsSource updateImpl(
		CustomElementsSource customElementsSource) {

		return getPersistence().updateImpl(customElementsSource);
	}

	/**
	 * Returns the custom elements source with the primary key or throws a <code>NoSuchSourceException</code> if it could not be found.
	 *
	 * @param customElementsSourceId the primary key of the custom elements source
	 * @return the custom elements source
	 * @throws NoSuchSourceException if a custom elements source with the primary key could not be found
	 */
	public static CustomElementsSource findByPrimaryKey(
			long customElementsSourceId)
		throws com.liferay.custom.elements.exception.NoSuchSourceException {

		return getPersistence().findByPrimaryKey(customElementsSourceId);
	}

	/**
	 * Returns the custom elements source with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param customElementsSourceId the primary key of the custom elements source
	 * @return the custom elements source, or <code>null</code> if a custom elements source with the primary key could not be found
	 */
	public static CustomElementsSource fetchByPrimaryKey(
		long customElementsSourceId) {

		return getPersistence().fetchByPrimaryKey(customElementsSourceId);
	}

	/**
	 * Returns all the custom elements sources.
	 *
	 * @return the custom elements sources
	 */
	public static List<CustomElementsSource> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the custom elements sources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @return the range of custom elements sources
	 */
	public static List<CustomElementsSource> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the custom elements sources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of custom elements sources
	 */
	public static List<CustomElementsSource> findAll(
		int start, int end,
		OrderByComparator<CustomElementsSource> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the custom elements sources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CustomElementsSourceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of custom elements sources
	 * @param end the upper bound of the range of custom elements sources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of custom elements sources
	 */
	public static List<CustomElementsSource> findAll(
		int start, int end,
		OrderByComparator<CustomElementsSource> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the custom elements sources from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of custom elements sources.
	 *
	 * @return the number of custom elements sources
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CustomElementsSourcePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CustomElementsSourcePersistence, CustomElementsSourcePersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CustomElementsSourcePersistence.class);

		ServiceTracker
			<CustomElementsSourcePersistence, CustomElementsSourcePersistence>
				serviceTracker =
					new ServiceTracker
						<CustomElementsSourcePersistence,
						 CustomElementsSourcePersistence>(
							 bundle.getBundleContext(),
							 CustomElementsSourcePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}