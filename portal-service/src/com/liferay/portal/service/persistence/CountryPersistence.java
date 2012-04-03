/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.Country;

/**
 * The persistence interface for the country service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CountryPersistenceImpl
 * @see CountryUtil
 * @generated
 */
public interface CountryPersistence extends BasePersistence<Country> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CountryUtil} to access the country persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the country in the entity cache if it is enabled.
	*
	* @param country the country
	*/
	public void cacheResult(com.liferay.portal.model.Country country);

	/**
	* Caches the countries in the entity cache if it is enabled.
	*
	* @param countries the countries
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.Country> countries);

	/**
	* Creates a new country with the primary key. Does not add the country to the database.
	*
	* @param countryId the primary key for the new country
	* @return the new country
	*/
	public com.liferay.portal.model.Country create(long countryId);

	/**
	* Removes the country with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param countryId the primary key of the country
	* @return the country that was removed
	* @throws com.liferay.portal.NoSuchCountryException if a country with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Country remove(long countryId)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Country updateImpl(
		com.liferay.portal.model.Country country, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the country with the primary key or throws a {@link com.liferay.portal.NoSuchCountryException} if it could not be found.
	*
	* @param countryId the primary key of the country
	* @return the country
	* @throws com.liferay.portal.NoSuchCountryException if a country with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Country findByPrimaryKey(long countryId)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the country with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param countryId the primary key of the country
	* @return the country, or <code>null</code> if a country with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Country fetchByPrimaryKey(long countryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the country where name = &#63; or throws a {@link com.liferay.portal.NoSuchCountryException} if it could not be found.
	*
	* @param name the name
	* @return the matching country
	* @throws com.liferay.portal.NoSuchCountryException if a matching country could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Country findByName(java.lang.String name)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the country where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param name the name
	* @return the matching country, or <code>null</code> if a matching country could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Country fetchByName(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the country where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching country, or <code>null</code> if a matching country could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Country fetchByName(java.lang.String name,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the country where a2 = &#63; or throws a {@link com.liferay.portal.NoSuchCountryException} if it could not be found.
	*
	* @param a2 the a2
	* @return the matching country
	* @throws com.liferay.portal.NoSuchCountryException if a matching country could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Country findByA2(java.lang.String a2)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the country where a2 = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param a2 the a2
	* @return the matching country, or <code>null</code> if a matching country could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Country fetchByA2(java.lang.String a2)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the country where a2 = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param a2 the a2
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching country, or <code>null</code> if a matching country could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Country fetchByA2(java.lang.String a2,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the country where a3 = &#63; or throws a {@link com.liferay.portal.NoSuchCountryException} if it could not be found.
	*
	* @param a3 the a3
	* @return the matching country
	* @throws com.liferay.portal.NoSuchCountryException if a matching country could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Country findByA3(java.lang.String a3)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the country where a3 = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param a3 the a3
	* @return the matching country, or <code>null</code> if a matching country could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Country fetchByA3(java.lang.String a3)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the country where a3 = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param a3 the a3
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching country, or <code>null</code> if a matching country could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Country fetchByA3(java.lang.String a3,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the countries where active = &#63;.
	*
	* @param active the active
	* @return the matching countries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Country> findByActive(
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the countries where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of countries
	* @param end the upper bound of the range of countries (not inclusive)
	* @return the range of matching countries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Country> findByActive(
		boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the countries where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of countries
	* @param end the upper bound of the range of countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching countries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Country> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first country in the ordered set where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching country
	* @throws com.liferay.portal.NoSuchCountryException if a matching country could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Country findByActive_First(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last country in the ordered set where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching country
	* @throws com.liferay.portal.NoSuchCountryException if a matching country could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Country findByActive_Last(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the countries before and after the current country in the ordered set where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param countryId the primary key of the current country
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next country
	* @throws com.liferay.portal.NoSuchCountryException if a country with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Country[] findByActive_PrevAndNext(
		long countryId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the countries.
	*
	* @return the countries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Country> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the countries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of countries
	* @param end the upper bound of the range of countries (not inclusive)
	* @return the range of countries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Country> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the countries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of countries
	* @param end the upper bound of the range of countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of countries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Country> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the country where name = &#63; from the database.
	*
	* @param name the name
	* @return the country that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Country removeByName(java.lang.String name)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the country where a2 = &#63; from the database.
	*
	* @param a2 the a2
	* @return the country that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Country removeByA2(java.lang.String a2)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the country where a3 = &#63; from the database.
	*
	* @param a3 the a3
	* @return the country that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Country removeByA3(java.lang.String a3)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the countries where active = &#63; from the database.
	*
	* @param active the active
	* @throws SystemException if a system exception occurred
	*/
	public void removeByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the countries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of countries where name = &#63;.
	*
	* @param name the name
	* @return the number of matching countries
	* @throws SystemException if a system exception occurred
	*/
	public int countByName(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of countries where a2 = &#63;.
	*
	* @param a2 the a2
	* @return the number of matching countries
	* @throws SystemException if a system exception occurred
	*/
	public int countByA2(java.lang.String a2)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of countries where a3 = &#63;.
	*
	* @param a3 the a3
	* @return the number of matching countries
	* @throws SystemException if a system exception occurred
	*/
	public int countByA3(java.lang.String a3)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of countries where active = &#63;.
	*
	* @param active the active
	* @return the number of matching countries
	* @throws SystemException if a system exception occurred
	*/
	public int countByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of countries.
	*
	* @return the number of countries
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}