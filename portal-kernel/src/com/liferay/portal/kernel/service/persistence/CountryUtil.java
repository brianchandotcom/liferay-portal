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

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the country service. This utility wraps <code>com.liferay.portal.service.persistence.impl.CountryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CountryPersistence
 * @generated
 */
public class CountryUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(Country country) {
		getPersistence().clearCache(country);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, Country> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Country> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Country> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Country> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Country update(Country country) {
		return getPersistence().update(country);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Country update(
		Country country, ServiceContext serviceContext) {

		return getPersistence().update(country, serviceContext);
	}

	/**
	 * Returns all the countries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching countries
	 */
	public static List<Country> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the countries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries
	 */
	public static List<Country> findByUuid(String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the countries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries
	 */
	public static List<Country> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the countries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching countries
	 */
	public static List<Country> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<Country> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public static Country findByUuid_First(
			String uuid, OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByUuid_First(
		String uuid, OrderByComparator<Country> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public static Country findByUuid_Last(
			String uuid, OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByUuid_Last(
		String uuid, OrderByComparator<Country> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the countries before and after the current country in the ordered set where uuid = &#63;.
	 *
	 * @param countryId the primary key of the current country
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next country
	 * @throws NoSuchCountryException if a country with the primary key could not be found
	 */
	public static Country[] findByUuid_PrevAndNext(
			long countryId, String uuid,
			OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByUuid_PrevAndNext(
			countryId, uuid, orderByComparator);
	}

	/**
	 * Removes all the countries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of countries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching countries
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns all the countries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching countries
	 */
	public static List<Country> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the countries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries
	 */
	public static List<Country> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the countries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries
	 */
	public static List<Country> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the countries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching countries
	 */
	public static List<Country> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Country> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public static Country findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<Country> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public static Country findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<Country> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the countries before and after the current country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param countryId the primary key of the current country
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next country
	 * @throws NoSuchCountryException if a country with the primary key could not be found
	 */
	public static Country[] findByUuid_C_PrevAndNext(
			long countryId, String uuid, long companyId,
			OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByUuid_C_PrevAndNext(
			countryId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the countries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of countries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching countries
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the countries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching countries
	 */
	public static List<Country> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the countries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries
	 */
	public static List<Country> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the countries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries
	 */
	public static List<Country> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the countries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching countries
	 */
	public static List<Country> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Country> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public static Country findByCompanyId_First(
			long companyId, OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByCompanyId_First(
		long companyId, OrderByComparator<Country> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public static Country findByCompanyId_Last(
			long companyId, OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByCompanyId_Last(
		long companyId, OrderByComparator<Country> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the countries before and after the current country in the ordered set where companyId = &#63;.
	 *
	 * @param countryId the primary key of the current country
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next country
	 * @throws NoSuchCountryException if a country with the primary key could not be found
	 */
	public static Country[] findByCompanyId_PrevAndNext(
			long countryId, long companyId,
			OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByCompanyId_PrevAndNext(
			countryId, companyId, orderByComparator);
	}

	/**
	 * Removes all the countries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of countries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching countries
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the countries where active = &#63;.
	 *
	 * @param active the active
	 * @return the matching countries
	 */
	public static List<Country> findByActive(boolean active) {
		return getPersistence().findByActive(active);
	}

	/**
	 * Returns a range of all the countries where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries
	 */
	public static List<Country> findByActive(
		boolean active, int start, int end) {

		return getPersistence().findByActive(active, start, end);
	}

	/**
	 * Returns an ordered range of all the countries where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries
	 */
	public static List<Country> findByActive(
		boolean active, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return getPersistence().findByActive(
			active, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the countries where active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param active the active
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching countries
	 */
	public static List<Country> findByActive(
		boolean active, int start, int end,
		OrderByComparator<Country> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByActive(
			active, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first country in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public static Country findByActive_First(
			boolean active, OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByActive_First(active, orderByComparator);
	}

	/**
	 * Returns the first country in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByActive_First(
		boolean active, OrderByComparator<Country> orderByComparator) {

		return getPersistence().fetchByActive_First(active, orderByComparator);
	}

	/**
	 * Returns the last country in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public static Country findByActive_Last(
			boolean active, OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByActive_Last(active, orderByComparator);
	}

	/**
	 * Returns the last country in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByActive_Last(
		boolean active, OrderByComparator<Country> orderByComparator) {

		return getPersistence().fetchByActive_Last(active, orderByComparator);
	}

	/**
	 * Returns the countries before and after the current country in the ordered set where active = &#63;.
	 *
	 * @param countryId the primary key of the current country
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next country
	 * @throws NoSuchCountryException if a country with the primary key could not be found
	 */
	public static Country[] findByActive_PrevAndNext(
			long countryId, boolean active,
			OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByActive_PrevAndNext(
			countryId, active, orderByComparator);
	}

	/**
	 * Removes all the countries where active = &#63; from the database.
	 *
	 * @param active the active
	 */
	public static void removeByActive(boolean active) {
		getPersistence().removeByActive(active);
	}

	/**
	 * Returns the number of countries where active = &#63;.
	 *
	 * @param active the active
	 * @return the number of matching countries
	 */
	public static int countByActive(boolean active) {
		return getPersistence().countByActive(active);
	}

	/**
	 * Returns the country where companyId = &#63; and a2 = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param a2 the a2
	 * @return the matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public static Country findByC_A2(long companyId, String a2)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByC_A2(companyId, a2);
	}

	/**
	 * Returns the country where companyId = &#63; and a2 = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param a2 the a2
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByC_A2(long companyId, String a2) {
		return getPersistence().fetchByC_A2(companyId, a2);
	}

	/**
	 * Returns the country where companyId = &#63; and a2 = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param a2 the a2
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByC_A2(
		long companyId, String a2, boolean useFinderCache) {

		return getPersistence().fetchByC_A2(companyId, a2, useFinderCache);
	}

	/**
	 * Removes the country where companyId = &#63; and a2 = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param a2 the a2
	 * @return the country that was removed
	 */
	public static Country removeByC_A2(long companyId, String a2)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().removeByC_A2(companyId, a2);
	}

	/**
	 * Returns the number of countries where companyId = &#63; and a2 = &#63;.
	 *
	 * @param companyId the company ID
	 * @param a2 the a2
	 * @return the number of matching countries
	 */
	public static int countByC_A2(long companyId, String a2) {
		return getPersistence().countByC_A2(companyId, a2);
	}

	/**
	 * Returns the country where companyId = &#63; and a3 = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param a3 the a3
	 * @return the matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public static Country findByC_A3(long companyId, String a3)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByC_A3(companyId, a3);
	}

	/**
	 * Returns the country where companyId = &#63; and a3 = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param a3 the a3
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByC_A3(long companyId, String a3) {
		return getPersistence().fetchByC_A3(companyId, a3);
	}

	/**
	 * Returns the country where companyId = &#63; and a3 = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param a3 the a3
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByC_A3(
		long companyId, String a3, boolean useFinderCache) {

		return getPersistence().fetchByC_A3(companyId, a3, useFinderCache);
	}

	/**
	 * Removes the country where companyId = &#63; and a3 = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param a3 the a3
	 * @return the country that was removed
	 */
	public static Country removeByC_A3(long companyId, String a3)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().removeByC_A3(companyId, a3);
	}

	/**
	 * Returns the number of countries where companyId = &#63; and a3 = &#63;.
	 *
	 * @param companyId the company ID
	 * @param a3 the a3
	 * @return the number of matching countries
	 */
	public static int countByC_A3(long companyId, String a3) {
		return getPersistence().countByC_A3(companyId, a3);
	}

	/**
	 * Returns all the countries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching countries
	 */
	public static List<Country> findByC_Active(long companyId, boolean active) {
		return getPersistence().findByC_Active(companyId, active);
	}

	/**
	 * Returns a range of all the countries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries
	 */
	public static List<Country> findByC_Active(
		long companyId, boolean active, int start, int end) {

		return getPersistence().findByC_Active(companyId, active, start, end);
	}

	/**
	 * Returns an ordered range of all the countries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries
	 */
	public static List<Country> findByC_Active(
		long companyId, boolean active, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return getPersistence().findByC_Active(
			companyId, active, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the countries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching countries
	 */
	public static List<Country> findByC_Active(
		long companyId, boolean active, int start, int end,
		OrderByComparator<Country> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByC_Active(
			companyId, active, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public static Country findByC_Active_First(
			long companyId, boolean active,
			OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByC_Active_First(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the first country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByC_Active_First(
		long companyId, boolean active,
		OrderByComparator<Country> orderByComparator) {

		return getPersistence().fetchByC_Active_First(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the last country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public static Country findByC_Active_Last(
			long companyId, boolean active,
			OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByC_Active_Last(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the last country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByC_Active_Last(
		long companyId, boolean active,
		OrderByComparator<Country> orderByComparator) {

		return getPersistence().fetchByC_Active_Last(
			companyId, active, orderByComparator);
	}

	/**
	 * Returns the countries before and after the current country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param countryId the primary key of the current country
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next country
	 * @throws NoSuchCountryException if a country with the primary key could not be found
	 */
	public static Country[] findByC_Active_PrevAndNext(
			long countryId, long companyId, boolean active,
			OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByC_Active_PrevAndNext(
			countryId, companyId, active, orderByComparator);
	}

	/**
	 * Removes all the countries where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	public static void removeByC_Active(long companyId, boolean active) {
		getPersistence().removeByC_Active(companyId, active);
	}

	/**
	 * Returns the number of countries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching countries
	 */
	public static int countByC_Active(long companyId, boolean active) {
		return getPersistence().countByC_Active(companyId, active);
	}

	/**
	 * Returns the country where companyId = &#63; and name = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public static Country findByC_Name(long companyId, String name)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByC_Name(companyId, name);
	}

	/**
	 * Returns the country where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByC_Name(long companyId, String name) {
		return getPersistence().fetchByC_Name(companyId, name);
	}

	/**
	 * Returns the country where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByC_Name(
		long companyId, String name, boolean useFinderCache) {

		return getPersistence().fetchByC_Name(companyId, name, useFinderCache);
	}

	/**
	 * Removes the country where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the country that was removed
	 */
	public static Country removeByC_Name(long companyId, String name)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().removeByC_Name(companyId, name);
	}

	/**
	 * Returns the number of countries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching countries
	 */
	public static int countByC_Name(long companyId, String name) {
		return getPersistence().countByC_Name(companyId, name);
	}

	/**
	 * Returns the country where companyId = &#63; and number = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param number the number
	 * @return the matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public static Country findByC_Number(long companyId, String number)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByC_Number(companyId, number);
	}

	/**
	 * Returns the country where companyId = &#63; and number = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param number the number
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByC_Number(long companyId, String number) {
		return getPersistence().fetchByC_Number(companyId, number);
	}

	/**
	 * Returns the country where companyId = &#63; and number = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param number the number
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByC_Number(
		long companyId, String number, boolean useFinderCache) {

		return getPersistence().fetchByC_Number(
			companyId, number, useFinderCache);
	}

	/**
	 * Removes the country where companyId = &#63; and number = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param number the number
	 * @return the country that was removed
	 */
	public static Country removeByC_Number(long companyId, String number)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().removeByC_Number(companyId, number);
	}

	/**
	 * Returns the number of countries where companyId = &#63; and number = &#63;.
	 *
	 * @param companyId the company ID
	 * @param number the number
	 * @return the number of matching countries
	 */
	public static int countByC_Number(long companyId, String number) {
		return getPersistence().countByC_Number(companyId, number);
	}

	/**
	 * Returns all the countries where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @return the matching countries
	 */
	public static List<Country> findByC_A_B(
		long companyId, boolean active, boolean billingAllowed) {

		return getPersistence().findByC_A_B(companyId, active, billingAllowed);
	}

	/**
	 * Returns a range of all the countries where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries
	 */
	public static List<Country> findByC_A_B(
		long companyId, boolean active, boolean billingAllowed, int start,
		int end) {

		return getPersistence().findByC_A_B(
			companyId, active, billingAllowed, start, end);
	}

	/**
	 * Returns an ordered range of all the countries where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries
	 */
	public static List<Country> findByC_A_B(
		long companyId, boolean active, boolean billingAllowed, int start,
		int end, OrderByComparator<Country> orderByComparator) {

		return getPersistence().findByC_A_B(
			companyId, active, billingAllowed, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the countries where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching countries
	 */
	public static List<Country> findByC_A_B(
		long companyId, boolean active, boolean billingAllowed, int start,
		int end, OrderByComparator<Country> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_A_B(
			companyId, active, billingAllowed, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first country in the ordered set where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public static Country findByC_A_B_First(
			long companyId, boolean active, boolean billingAllowed,
			OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByC_A_B_First(
			companyId, active, billingAllowed, orderByComparator);
	}

	/**
	 * Returns the first country in the ordered set where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByC_A_B_First(
		long companyId, boolean active, boolean billingAllowed,
		OrderByComparator<Country> orderByComparator) {

		return getPersistence().fetchByC_A_B_First(
			companyId, active, billingAllowed, orderByComparator);
	}

	/**
	 * Returns the last country in the ordered set where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public static Country findByC_A_B_Last(
			long companyId, boolean active, boolean billingAllowed,
			OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByC_A_B_Last(
			companyId, active, billingAllowed, orderByComparator);
	}

	/**
	 * Returns the last country in the ordered set where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByC_A_B_Last(
		long companyId, boolean active, boolean billingAllowed,
		OrderByComparator<Country> orderByComparator) {

		return getPersistence().fetchByC_A_B_Last(
			companyId, active, billingAllowed, orderByComparator);
	}

	/**
	 * Returns the countries before and after the current country in the ordered set where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * @param countryId the primary key of the current country
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next country
	 * @throws NoSuchCountryException if a country with the primary key could not be found
	 */
	public static Country[] findByC_A_B_PrevAndNext(
			long countryId, long companyId, boolean active,
			boolean billingAllowed,
			OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByC_A_B_PrevAndNext(
			countryId, companyId, active, billingAllowed, orderByComparator);
	}

	/**
	 * Removes all the countries where companyId = &#63; and active = &#63; and billingAllowed = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 */
	public static void removeByC_A_B(
		long companyId, boolean active, boolean billingAllowed) {

		getPersistence().removeByC_A_B(companyId, active, billingAllowed);
	}

	/**
	 * Returns the number of countries where companyId = &#63; and active = &#63; and billingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param billingAllowed the billing allowed
	 * @return the number of matching countries
	 */
	public static int countByC_A_B(
		long companyId, boolean active, boolean billingAllowed) {

		return getPersistence().countByC_A_B(companyId, active, billingAllowed);
	}

	/**
	 * Returns all the countries where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @return the matching countries
	 */
	public static List<Country> findByC_A_S(
		long companyId, boolean active, boolean shippingAllowed) {

		return getPersistence().findByC_A_S(companyId, active, shippingAllowed);
	}

	/**
	 * Returns a range of all the countries where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of matching countries
	 */
	public static List<Country> findByC_A_S(
		long companyId, boolean active, boolean shippingAllowed, int start,
		int end) {

		return getPersistence().findByC_A_S(
			companyId, active, shippingAllowed, start, end);
	}

	/**
	 * Returns an ordered range of all the countries where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching countries
	 */
	public static List<Country> findByC_A_S(
		long companyId, boolean active, boolean shippingAllowed, int start,
		int end, OrderByComparator<Country> orderByComparator) {

		return getPersistence().findByC_A_S(
			companyId, active, shippingAllowed, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the countries where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching countries
	 */
	public static List<Country> findByC_A_S(
		long companyId, boolean active, boolean shippingAllowed, int start,
		int end, OrderByComparator<Country> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_A_S(
			companyId, active, shippingAllowed, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first country in the ordered set where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public static Country findByC_A_S_First(
			long companyId, boolean active, boolean shippingAllowed,
			OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByC_A_S_First(
			companyId, active, shippingAllowed, orderByComparator);
	}

	/**
	 * Returns the first country in the ordered set where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByC_A_S_First(
		long companyId, boolean active, boolean shippingAllowed,
		OrderByComparator<Country> orderByComparator) {

		return getPersistence().fetchByC_A_S_First(
			companyId, active, shippingAllowed, orderByComparator);
	}

	/**
	 * Returns the last country in the ordered set where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public static Country findByC_A_S_Last(
			long companyId, boolean active, boolean shippingAllowed,
			OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByC_A_S_Last(
			companyId, active, shippingAllowed, orderByComparator);
	}

	/**
	 * Returns the last country in the ordered set where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country, or <code>null</code> if a matching country could not be found
	 */
	public static Country fetchByC_A_S_Last(
		long companyId, boolean active, boolean shippingAllowed,
		OrderByComparator<Country> orderByComparator) {

		return getPersistence().fetchByC_A_S_Last(
			companyId, active, shippingAllowed, orderByComparator);
	}

	/**
	 * Returns the countries before and after the current country in the ordered set where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * @param countryId the primary key of the current country
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next country
	 * @throws NoSuchCountryException if a country with the primary key could not be found
	 */
	public static Country[] findByC_A_S_PrevAndNext(
			long countryId, long companyId, boolean active,
			boolean shippingAllowed,
			OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByC_A_S_PrevAndNext(
			countryId, companyId, active, shippingAllowed, orderByComparator);
	}

	/**
	 * Removes all the countries where companyId = &#63; and active = &#63; and shippingAllowed = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 */
	public static void removeByC_A_S(
		long companyId, boolean active, boolean shippingAllowed) {

		getPersistence().removeByC_A_S(companyId, active, shippingAllowed);
	}

	/**
	 * Returns the number of countries where companyId = &#63; and active = &#63; and shippingAllowed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param shippingAllowed the shipping allowed
	 * @return the number of matching countries
	 */
	public static int countByC_A_S(
		long companyId, boolean active, boolean shippingAllowed) {

		return getPersistence().countByC_A_S(
			companyId, active, shippingAllowed);
	}

	/**
	 * Caches the country in the entity cache if it is enabled.
	 *
	 * @param country the country
	 */
	public static void cacheResult(Country country) {
		getPersistence().cacheResult(country);
	}

	/**
	 * Caches the countries in the entity cache if it is enabled.
	 *
	 * @param countries the countries
	 */
	public static void cacheResult(List<Country> countries) {
		getPersistence().cacheResult(countries);
	}

	/**
	 * Creates a new country with the primary key. Does not add the country to the database.
	 *
	 * @param countryId the primary key for the new country
	 * @return the new country
	 */
	public static Country create(long countryId) {
		return getPersistence().create(countryId);
	}

	/**
	 * Removes the country with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param countryId the primary key of the country
	 * @return the country that was removed
	 * @throws NoSuchCountryException if a country with the primary key could not be found
	 */
	public static Country remove(long countryId)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().remove(countryId);
	}

	public static Country updateImpl(Country country) {
		return getPersistence().updateImpl(country);
	}

	/**
	 * Returns the country with the primary key or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param countryId the primary key of the country
	 * @return the country
	 * @throws NoSuchCountryException if a country with the primary key could not be found
	 */
	public static Country findByPrimaryKey(long countryId)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {

		return getPersistence().findByPrimaryKey(countryId);
	}

	/**
	 * Returns the country with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param countryId the primary key of the country
	 * @return the country, or <code>null</code> if a country with the primary key could not be found
	 */
	public static Country fetchByPrimaryKey(long countryId) {
		return getPersistence().fetchByPrimaryKey(countryId);
	}

	/**
	 * Returns all the countries.
	 *
	 * @return the countries
	 */
	public static List<Country> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the countries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @return the range of countries
	 */
	public static List<Country> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the countries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of countries
	 */
	public static List<Country> findAll(
		int start, int end, OrderByComparator<Country> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the countries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of countries
	 * @param end the upper bound of the range of countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of countries
	 */
	public static List<Country> findAll(
		int start, int end, OrderByComparator<Country> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the countries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of countries.
	 *
	 * @return the number of countries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CountryPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(CountryPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile CountryPersistence _persistence;

}