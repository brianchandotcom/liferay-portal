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

import com.liferay.portal.kernel.exception.NoSuchCountryException;
import com.liferay.portal.kernel.model.Country;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the country service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CountryUtil
 * @generated
 */
@ProviderType
public interface CountryPersistence extends BasePersistence<Country> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CountryUtil} to access the country persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the countries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching countries
	 */
	public java.util.List<Country> findByUuid(String uuid);

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
	public java.util.List<Country> findByUuid(String uuid, int start, int end);

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
	public java.util.List<Country> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator);

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
	public java.util.List<Country> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public Country findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<Country>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the first country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	public Country fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator);

	/**
	 * Returns the last country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public Country findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<Country>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the last country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country, or <code>null</code> if a matching country could not be found
	 */
	public Country fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator);

	/**
	 * Returns the countries before and after the current country in the ordered set where uuid = &#63;.
	 *
	 * @param countryId the primary key of the current country
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next country
	 * @throws NoSuchCountryException if a country with the primary key could not be found
	 */
	public Country[] findByUuid_PrevAndNext(
			long countryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<Country>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Removes all the countries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of countries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching countries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the countries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching countries
	 */
	public java.util.List<Country> findByUuid_C(String uuid, long companyId);

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
	public java.util.List<Country> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<Country> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator);

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
	public java.util.List<Country> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public Country findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Country>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the first country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	public Country fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator);

	/**
	 * Returns the last country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public Country findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Country>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the last country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country, or <code>null</code> if a matching country could not be found
	 */
	public Country fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator);

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
	public Country[] findByUuid_C_PrevAndNext(
			long countryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Country>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Removes all the countries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of countries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching countries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the countries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching countries
	 */
	public java.util.List<Country> findByCompanyId(long companyId);

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
	public java.util.List<Country> findByCompanyId(
		long companyId, int start, int end);

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
	public java.util.List<Country> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator);

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
	public java.util.List<Country> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public Country findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Country>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the first country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	public Country fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator);

	/**
	 * Returns the last country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public Country findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Country>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the last country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country, or <code>null</code> if a matching country could not be found
	 */
	public Country fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator);

	/**
	 * Returns the countries before and after the current country in the ordered set where companyId = &#63;.
	 *
	 * @param countryId the primary key of the current country
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next country
	 * @throws NoSuchCountryException if a country with the primary key could not be found
	 */
	public Country[] findByCompanyId_PrevAndNext(
			long countryId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<Country>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Removes all the countries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of countries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching countries
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the countries where active = &#63;.
	 *
	 * @param active the active
	 * @return the matching countries
	 */
	public java.util.List<Country> findByActive(boolean active);

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
	public java.util.List<Country> findByActive(
		boolean active, int start, int end);

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
	public java.util.List<Country> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator);

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
	public java.util.List<Country> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first country in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public Country findByActive_First(
			boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<Country>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the first country in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	public Country fetchByActive_First(
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator);

	/**
	 * Returns the last country in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public Country findByActive_Last(
			boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<Country>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the last country in the ordered set where active = &#63;.
	 *
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country, or <code>null</code> if a matching country could not be found
	 */
	public Country fetchByActive_Last(
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator);

	/**
	 * Returns the countries before and after the current country in the ordered set where active = &#63;.
	 *
	 * @param countryId the primary key of the current country
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next country
	 * @throws NoSuchCountryException if a country with the primary key could not be found
	 */
	public Country[] findByActive_PrevAndNext(
			long countryId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<Country>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Removes all the countries where active = &#63; from the database.
	 *
	 * @param active the active
	 */
	public void removeByActive(boolean active);

	/**
	 * Returns the number of countries where active = &#63;.
	 *
	 * @param active the active
	 * @return the number of matching countries
	 */
	public int countByActive(boolean active);

	/**
	 * Returns the country where companyId = &#63; and a2 = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param a2 the a2
	 * @return the matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public Country findByC_A2(long companyId, String a2)
		throws NoSuchCountryException;

	/**
	 * Returns the country where companyId = &#63; and a2 = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param a2 the a2
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	public Country fetchByC_A2(long companyId, String a2);

	/**
	 * Returns the country where companyId = &#63; and a2 = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param a2 the a2
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	public Country fetchByC_A2(
		long companyId, String a2, boolean useFinderCache);

	/**
	 * Removes the country where companyId = &#63; and a2 = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param a2 the a2
	 * @return the country that was removed
	 */
	public Country removeByC_A2(long companyId, String a2)
		throws NoSuchCountryException;

	/**
	 * Returns the number of countries where companyId = &#63; and a2 = &#63;.
	 *
	 * @param companyId the company ID
	 * @param a2 the a2
	 * @return the number of matching countries
	 */
	public int countByC_A2(long companyId, String a2);

	/**
	 * Returns the country where companyId = &#63; and a3 = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param a3 the a3
	 * @return the matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public Country findByC_A3(long companyId, String a3)
		throws NoSuchCountryException;

	/**
	 * Returns the country where companyId = &#63; and a3 = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param a3 the a3
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	public Country fetchByC_A3(long companyId, String a3);

	/**
	 * Returns the country where companyId = &#63; and a3 = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param a3 the a3
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	public Country fetchByC_A3(
		long companyId, String a3, boolean useFinderCache);

	/**
	 * Removes the country where companyId = &#63; and a3 = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param a3 the a3
	 * @return the country that was removed
	 */
	public Country removeByC_A3(long companyId, String a3)
		throws NoSuchCountryException;

	/**
	 * Returns the number of countries where companyId = &#63; and a3 = &#63;.
	 *
	 * @param companyId the company ID
	 * @param a3 the a3
	 * @return the number of matching countries
	 */
	public int countByC_A3(long companyId, String a3);

	/**
	 * Returns all the countries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching countries
	 */
	public java.util.List<Country> findByC_Active(
		long companyId, boolean active);

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
	public java.util.List<Country> findByC_Active(
		long companyId, boolean active, int start, int end);

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
	public java.util.List<Country> findByC_Active(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator);

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
	public java.util.List<Country> findByC_Active(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public Country findByC_Active_First(
			long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<Country>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the first country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country, or <code>null</code> if a matching country could not be found
	 */
	public Country fetchByC_Active_First(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator);

	/**
	 * Returns the last country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public Country findByC_Active_Last(
			long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<Country>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the last country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country, or <code>null</code> if a matching country could not be found
	 */
	public Country fetchByC_Active_Last(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator);

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
	public Country[] findByC_Active_PrevAndNext(
			long countryId, long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<Country>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Removes all the countries where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	public void removeByC_Active(long companyId, boolean active);

	/**
	 * Returns the number of countries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching countries
	 */
	public int countByC_Active(long companyId, boolean active);

	/**
	 * Returns the country where companyId = &#63; and name = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching country
	 * @throws NoSuchCountryException if a matching country could not be found
	 */
	public Country findByC_N(long companyId, String name)
		throws NoSuchCountryException;

	/**
	 * Returns the country where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	public Country fetchByC_N(long companyId, String name);

	/**
	 * Returns the country where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching country, or <code>null</code> if a matching country could not be found
	 */
	public Country fetchByC_N(
		long companyId, String name, boolean useFinderCache);

	/**
	 * Removes the country where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the country that was removed
	 */
	public Country removeByC_N(long companyId, String name)
		throws NoSuchCountryException;

	/**
	 * Returns the number of countries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching countries
	 */
	public int countByC_N(long companyId, String name);

	/**
	 * Caches the country in the entity cache if it is enabled.
	 *
	 * @param country the country
	 */
	public void cacheResult(Country country);

	/**
	 * Caches the countries in the entity cache if it is enabled.
	 *
	 * @param countries the countries
	 */
	public void cacheResult(java.util.List<Country> countries);

	/**
	 * Creates a new country with the primary key. Does not add the country to the database.
	 *
	 * @param countryId the primary key for the new country
	 * @return the new country
	 */
	public Country create(long countryId);

	/**
	 * Removes the country with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param countryId the primary key of the country
	 * @return the country that was removed
	 * @throws NoSuchCountryException if a country with the primary key could not be found
	 */
	public Country remove(long countryId) throws NoSuchCountryException;

	public Country updateImpl(Country country);

	/**
	 * Returns the country with the primary key or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param countryId the primary key of the country
	 * @return the country
	 * @throws NoSuchCountryException if a country with the primary key could not be found
	 */
	public Country findByPrimaryKey(long countryId)
		throws NoSuchCountryException;

	/**
	 * Returns the country with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param countryId the primary key of the country
	 * @return the country, or <code>null</code> if a country with the primary key could not be found
	 */
	public Country fetchByPrimaryKey(long countryId);

	/**
	 * Returns all the countries.
	 *
	 * @return the countries
	 */
	public java.util.List<Country> findAll();

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
	public java.util.List<Country> findAll(int start, int end);

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
	public java.util.List<Country> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator);

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
	public java.util.List<Country> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the countries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of countries.
	 *
	 * @return the number of countries
	 */
	public int countAll();

}