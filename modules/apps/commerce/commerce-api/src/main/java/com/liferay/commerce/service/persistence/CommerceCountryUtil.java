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

package com.liferay.commerce.service.persistence;

import com.liferay.commerce.model.CommerceCountry;
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
 * The persistence utility for the commerce country service. This utility wraps <code>com.liferay.commerce.service.persistence.impl.CommerceCountryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCountryPersistence
 * @generated
 */
public class CommerceCountryUtil {

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
	public static void clearCache(CommerceCountry commerceCountry) {
		getPersistence().clearCache(commerceCountry);
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
	public static Map<Serializable, CommerceCountry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceCountry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceCountry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceCountry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceCountry update(CommerceCountry commerceCountry) {
		return getPersistence().update(commerceCountry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceCountry update(
		CommerceCountry commerceCountry, ServiceContext serviceContext) {

		return getPersistence().update(commerceCountry, serviceContext);
	}

	/**
	 * Returns all the commerce countries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce countries
	 */
	public static List<CommerceCountry> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce countries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @return the range of matching commerce countries
	 */
	public static List<CommerceCountry> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce countries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce countries
	 */
	public static List<CommerceCountry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce countries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce countries
	 */
	public static List<CommerceCountry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public static CommerceCountry findByUuid_First(
			String uuid, OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first commerce country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public static CommerceCountry fetchByUuid_First(
		String uuid, OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public static CommerceCountry findByUuid_Last(
			String uuid, OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public static CommerceCountry fetchByUuid_Last(
		String uuid, OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the commerce countries before and after the current commerce country in the ordered set where uuid = &#63;.
	 *
	 * @param commerceCountryId the primary key of the current commerce country
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce country
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	public static CommerceCountry[] findByUuid_PrevAndNext(
			long commerceCountryId, String uuid,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByUuid_PrevAndNext(
			commerceCountryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the commerce countries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of commerce countries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce countries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the commerce countries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce countries
	 */
	public static List<CommerceCountry> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce countries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @return the range of matching commerce countries
	 */
	public static List<CommerceCountry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce countries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce countries
	 */
	public static List<CommerceCountry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce countries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce countries
	 */
	public static List<CommerceCountry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public static CommerceCountry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public static CommerceCountry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public static CommerceCountry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public static CommerceCountry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the commerce countries before and after the current commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceCountryId the primary key of the current commerce country
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce country
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	public static CommerceCountry[] findByUuid_C_PrevAndNext(
			long commerceCountryId, String uuid, long companyId,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			commerceCountryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce countries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce countries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce countries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the commerce countries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce countries
	 */
	public static List<CommerceCountry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the commerce countries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @return the range of matching commerce countries
	 */
	public static List<CommerceCountry> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce countries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce countries
	 */
	public static List<CommerceCountry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce countries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce countries
	 */
	public static List<CommerceCountry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public static CommerceCountry findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public static CommerceCountry fetchByCompanyId_First(
		long companyId, OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public static CommerceCountry findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public static CommerceCountry fetchByCompanyId_Last(
		long companyId, OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the commerce countries before and after the current commerce country in the ordered set where companyId = &#63;.
	 *
	 * @param commerceCountryId the primary key of the current commerce country
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce country
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	public static CommerceCountry[] findByCompanyId_PrevAndNext(
			long commerceCountryId, long companyId,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByCompanyId_PrevAndNext(
			commerceCountryId, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce countries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of commerce countries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce countries
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns the commerce country where companyId = &#63; and twoLettersISOCode = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param twoLettersISOCode the two letters iso code
	 * @return the matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public static CommerceCountry findByC_Tw(
			long companyId, String twoLettersISOCode)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByC_Tw(companyId, twoLettersISOCode);
	}

	/**
	 * Returns the commerce country where companyId = &#63; and twoLettersISOCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param twoLettersISOCode the two letters iso code
	 * @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public static CommerceCountry fetchByC_Tw(
		long companyId, String twoLettersISOCode) {

		return getPersistence().fetchByC_Tw(companyId, twoLettersISOCode);
	}

	/**
	 * Returns the commerce country where companyId = &#63; and twoLettersISOCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param twoLettersISOCode the two letters iso code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public static CommerceCountry fetchByC_Tw(
		long companyId, String twoLettersISOCode, boolean useFinderCache) {

		return getPersistence().fetchByC_Tw(
			companyId, twoLettersISOCode, useFinderCache);
	}

	/**
	 * Removes the commerce country where companyId = &#63; and twoLettersISOCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param twoLettersISOCode the two letters iso code
	 * @return the commerce country that was removed
	 */
	public static CommerceCountry removeByC_Tw(
			long companyId, String twoLettersISOCode)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().removeByC_Tw(companyId, twoLettersISOCode);
	}

	/**
	 * Returns the number of commerce countries where companyId = &#63; and twoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twoLettersISOCode the two letters iso code
	 * @return the number of matching commerce countries
	 */
	public static int countByC_Tw(long companyId, String twoLettersISOCode) {
		return getPersistence().countByC_Tw(companyId, twoLettersISOCode);
	}

	/**
	 * Returns the commerce country where companyId = &#63; and numericISOCode = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param numericISOCode the numeric iso code
	 * @return the matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public static CommerceCountry findByC_N(long companyId, int numericISOCode)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByC_N(companyId, numericISOCode);
	}

	/**
	 * Returns the commerce country where companyId = &#63; and numericISOCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param numericISOCode the numeric iso code
	 * @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public static CommerceCountry fetchByC_N(
		long companyId, int numericISOCode) {

		return getPersistence().fetchByC_N(companyId, numericISOCode);
	}

	/**
	 * Returns the commerce country where companyId = &#63; and numericISOCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param numericISOCode the numeric iso code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public static CommerceCountry fetchByC_N(
		long companyId, int numericISOCode, boolean useFinderCache) {

		return getPersistence().fetchByC_N(
			companyId, numericISOCode, useFinderCache);
	}

	/**
	 * Removes the commerce country where companyId = &#63; and numericISOCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param numericISOCode the numeric iso code
	 * @return the commerce country that was removed
	 */
	public static CommerceCountry removeByC_N(
			long companyId, int numericISOCode)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().removeByC_N(companyId, numericISOCode);
	}

	/**
	 * Returns the number of commerce countries where companyId = &#63; and numericISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param numericISOCode the numeric iso code
	 * @return the number of matching commerce countries
	 */
	public static int countByC_N(long companyId, int numericISOCode) {
		return getPersistence().countByC_N(companyId, numericISOCode);
	}

	/**
	 * Returns all the commerce countries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce countries
	 */
	public static List<CommerceCountry> findByC_A(
		long companyId, boolean active) {

		return getPersistence().findByC_A(companyId, active);
	}

	/**
	 * Returns a range of all the commerce countries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @return the range of matching commerce countries
	 */
	public static List<CommerceCountry> findByC_A(
		long companyId, boolean active, int start, int end) {

		return getPersistence().findByC_A(companyId, active, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce countries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce countries
	 */
	public static List<CommerceCountry> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().findByC_A(
			companyId, active, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce countries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce countries
	 */
	public static List<CommerceCountry> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_A(
			companyId, active, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public static CommerceCountry findByC_A_First(
			long companyId, boolean active,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByC_A_First(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the first commerce country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public static CommerceCountry fetchByC_A_First(
		long companyId, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().fetchByC_A_First(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public static CommerceCountry findByC_A_Last(
			long companyId, boolean active,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByC_A_Last(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public static CommerceCountry fetchByC_A_Last(
		long companyId, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().fetchByC_A_Last(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the commerce countries before and after the current commerce country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceCountryId the primary key of the current commerce country
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce country
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	public static CommerceCountry[] findByC_A_PrevAndNext(
			long commerceCountryId, long companyId, boolean active,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByC_A_PrevAndNext(
			commerceCountryId, companyId, active, orderByComparator);
	}

	/**
	 * Removes all the commerce countries where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	public static void removeByC_A(long companyId, boolean active) {
		getPersistence().removeByC_A(companyId, active);
	}

	/**
	 * Returns the number of commerce countries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce countries
	 */
	public static int countByC_A(long companyId, boolean active) {
		return getPersistence().countByC_A(companyId, active);
	}

	/**
	 * Returns all the commerce countries where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @return the matching commerce countries
	 */
	public static List<CommerceCountry> findByC_B_A(
		long companyId, boolean billingAllowed, boolean active) {

		return getPersistence().findByC_B_A(companyId, billingAllowed, active);
	}

	/**
	 * Returns a range of all the commerce countries where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @return the range of matching commerce countries
	 */
	public static List<CommerceCountry> findByC_B_A(
		long companyId, boolean billingAllowed, boolean active, int start,
		int end) {

		return getPersistence().findByC_B_A(
			companyId, billingAllowed, active, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce countries where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce countries
	 */
	public static List<CommerceCountry> findByC_B_A(
		long companyId, boolean billingAllowed, boolean active, int start,
		int end, OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().findByC_B_A(
			companyId, billingAllowed, active, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce countries where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce countries
	 */
	public static List<CommerceCountry> findByC_B_A(
		long companyId, boolean billingAllowed, boolean active, int start,
		int end, OrderByComparator<CommerceCountry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_B_A(
			companyId, billingAllowed, active, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce country in the ordered set where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public static CommerceCountry findByC_B_A_First(
			long companyId, boolean billingAllowed, boolean active,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByC_B_A_First(
			companyId, billingAllowed, active, orderByComparator);
	}

	/**
	 * Returns the first commerce country in the ordered set where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public static CommerceCountry fetchByC_B_A_First(
		long companyId, boolean billingAllowed, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().fetchByC_B_A_First(
			companyId, billingAllowed, active, orderByComparator);
	}

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public static CommerceCountry findByC_B_A_Last(
			long companyId, boolean billingAllowed, boolean active,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByC_B_A_Last(
			companyId, billingAllowed, active, orderByComparator);
	}

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public static CommerceCountry fetchByC_B_A_Last(
		long companyId, boolean billingAllowed, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().fetchByC_B_A_Last(
			companyId, billingAllowed, active, orderByComparator);
	}

	/**
	 * Returns the commerce countries before and after the current commerce country in the ordered set where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * @param commerceCountryId the primary key of the current commerce country
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce country
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	public static CommerceCountry[] findByC_B_A_PrevAndNext(
			long commerceCountryId, long companyId, boolean billingAllowed,
			boolean active,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByC_B_A_PrevAndNext(
			commerceCountryId, companyId, billingAllowed, active,
			orderByComparator);
	}

	/**
	 * Removes all the commerce countries where companyId = &#63; and billingAllowed = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 */
	public static void removeByC_B_A(
		long companyId, boolean billingAllowed, boolean active) {

		getPersistence().removeByC_B_A(companyId, billingAllowed, active);
	}

	/**
	 * Returns the number of commerce countries where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @return the number of matching commerce countries
	 */
	public static int countByC_B_A(
		long companyId, boolean billingAllowed, boolean active) {

		return getPersistence().countByC_B_A(companyId, billingAllowed, active);
	}

	/**
	 * Returns all the commerce countries where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @return the matching commerce countries
	 */
	public static List<CommerceCountry> findByC_S_A(
		long companyId, boolean shippingAllowed, boolean active) {

		return getPersistence().findByC_S_A(companyId, shippingAllowed, active);
	}

	/**
	 * Returns a range of all the commerce countries where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @return the range of matching commerce countries
	 */
	public static List<CommerceCountry> findByC_S_A(
		long companyId, boolean shippingAllowed, boolean active, int start,
		int end) {

		return getPersistence().findByC_S_A(
			companyId, shippingAllowed, active, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce countries where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce countries
	 */
	public static List<CommerceCountry> findByC_S_A(
		long companyId, boolean shippingAllowed, boolean active, int start,
		int end, OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().findByC_S_A(
			companyId, shippingAllowed, active, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce countries where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce countries
	 */
	public static List<CommerceCountry> findByC_S_A(
		long companyId, boolean shippingAllowed, boolean active, int start,
		int end, OrderByComparator<CommerceCountry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_S_A(
			companyId, shippingAllowed, active, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce country in the ordered set where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public static CommerceCountry findByC_S_A_First(
			long companyId, boolean shippingAllowed, boolean active,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByC_S_A_First(
			companyId, shippingAllowed, active, orderByComparator);
	}

	/**
	 * Returns the first commerce country in the ordered set where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public static CommerceCountry fetchByC_S_A_First(
		long companyId, boolean shippingAllowed, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().fetchByC_S_A_First(
			companyId, shippingAllowed, active, orderByComparator);
	}

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public static CommerceCountry findByC_S_A_Last(
			long companyId, boolean shippingAllowed, boolean active,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByC_S_A_Last(
			companyId, shippingAllowed, active, orderByComparator);
	}

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public static CommerceCountry fetchByC_S_A_Last(
		long companyId, boolean shippingAllowed, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().fetchByC_S_A_Last(
			companyId, shippingAllowed, active, orderByComparator);
	}

	/**
	 * Returns the commerce countries before and after the current commerce country in the ordered set where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * @param commerceCountryId the primary key of the current commerce country
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce country
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	public static CommerceCountry[] findByC_S_A_PrevAndNext(
			long commerceCountryId, long companyId, boolean shippingAllowed,
			boolean active,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByC_S_A_PrevAndNext(
			commerceCountryId, companyId, shippingAllowed, active,
			orderByComparator);
	}

	/**
	 * Removes all the commerce countries where companyId = &#63; and shippingAllowed = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 */
	public static void removeByC_S_A(
		long companyId, boolean shippingAllowed, boolean active) {

		getPersistence().removeByC_S_A(companyId, shippingAllowed, active);
	}

	/**
	 * Returns the number of commerce countries where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @return the number of matching commerce countries
	 */
	public static int countByC_S_A(
		long companyId, boolean shippingAllowed, boolean active) {

		return getPersistence().countByC_S_A(
			companyId, shippingAllowed, active);
	}

	/**
	 * Caches the commerce country in the entity cache if it is enabled.
	 *
	 * @param commerceCountry the commerce country
	 */
	public static void cacheResult(CommerceCountry commerceCountry) {
		getPersistence().cacheResult(commerceCountry);
	}

	/**
	 * Caches the commerce countries in the entity cache if it is enabled.
	 *
	 * @param commerceCountries the commerce countries
	 */
	public static void cacheResult(List<CommerceCountry> commerceCountries) {
		getPersistence().cacheResult(commerceCountries);
	}

	/**
	 * Creates a new commerce country with the primary key. Does not add the commerce country to the database.
	 *
	 * @param commerceCountryId the primary key for the new commerce country
	 * @return the new commerce country
	 */
	public static CommerceCountry create(long commerceCountryId) {
		return getPersistence().create(commerceCountryId);
	}

	/**
	 * Removes the commerce country with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceCountryId the primary key of the commerce country
	 * @return the commerce country that was removed
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	public static CommerceCountry remove(long commerceCountryId)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().remove(commerceCountryId);
	}

	public static CommerceCountry updateImpl(CommerceCountry commerceCountry) {
		return getPersistence().updateImpl(commerceCountry);
	}

	/**
	 * Returns the commerce country with the primary key or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param commerceCountryId the primary key of the commerce country
	 * @return the commerce country
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	public static CommerceCountry findByPrimaryKey(long commerceCountryId)
		throws com.liferay.commerce.exception.NoSuchCountryException {

		return getPersistence().findByPrimaryKey(commerceCountryId);
	}

	/**
	 * Returns the commerce country with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceCountryId the primary key of the commerce country
	 * @return the commerce country, or <code>null</code> if a commerce country with the primary key could not be found
	 */
	public static CommerceCountry fetchByPrimaryKey(long commerceCountryId) {
		return getPersistence().fetchByPrimaryKey(commerceCountryId);
	}

	/**
	 * Returns all the commerce countries.
	 *
	 * @return the commerce countries
	 */
	public static List<CommerceCountry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce countries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @return the range of commerce countries
	 */
	public static List<CommerceCountry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce countries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce countries
	 */
	public static List<CommerceCountry> findAll(
		int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce countries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce countries
	 */
	public static List<CommerceCountry> findAll(
		int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce countries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce countries.
	 *
	 * @return the number of commerce countries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceCountryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceCountryPersistence, CommerceCountryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceCountryPersistence.class);

		ServiceTracker<CommerceCountryPersistence, CommerceCountryPersistence>
			serviceTracker =
				new ServiceTracker
					<CommerceCountryPersistence, CommerceCountryPersistence>(
						bundle.getBundleContext(),
						CommerceCountryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}