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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.exception.NoSuchCountryException;
import com.liferay.portal.model.Country;

/**
 * The persistence interface for the country service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.persistence.impl.CountryPersistenceImpl
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
	* Returns the country where name = &#63; or throws a {@link NoSuchCountryException} if it could not be found.
	*
	* @param name the name
	* @return the matching country
	* @throws NoSuchCountryException if a matching country could not be found
	*/
	public Country findByName(java.lang.String name)
		throws NoSuchCountryException;

	/**
	* Returns the country where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param name the name
	* @return the matching country, or <code>null</code> if a matching country could not be found
	*/
	public Country fetchByName(java.lang.String name);

	/**
	* Returns the country where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching country, or <code>null</code> if a matching country could not be found
	*/
	public Country fetchByName(java.lang.String name, boolean retrieveFromCache);

	/**
	* Removes the country where name = &#63; from the database.
	*
	* @param name the name
	* @return the country that was removed
	*/
	public Country removeByName(java.lang.String name)
		throws NoSuchCountryException;

	/**
	* Returns the number of countries where name = &#63;.
	*
	* @param name the name
	* @return the number of matching countries
	*/
	public int countByName(java.lang.String name);

	/**
	* Returns the country where a2 = &#63; or throws a {@link NoSuchCountryException} if it could not be found.
	*
	* @param a2 the a2
	* @return the matching country
	* @throws NoSuchCountryException if a matching country could not be found
	*/
	public Country findByA2(java.lang.String a2) throws NoSuchCountryException;

	/**
	* Returns the country where a2 = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param a2 the a2
	* @return the matching country, or <code>null</code> if a matching country could not be found
	*/
	public Country fetchByA2(java.lang.String a2);

	/**
	* Returns the country where a2 = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param a2 the a2
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching country, or <code>null</code> if a matching country could not be found
	*/
	public Country fetchByA2(java.lang.String a2, boolean retrieveFromCache);

	/**
	* Removes the country where a2 = &#63; from the database.
	*
	* @param a2 the a2
	* @return the country that was removed
	*/
	public Country removeByA2(java.lang.String a2)
		throws NoSuchCountryException;

	/**
	* Returns the number of countries where a2 = &#63;.
	*
	* @param a2 the a2
	* @return the number of matching countries
	*/
	public int countByA2(java.lang.String a2);

	/**
	* Returns the country where a3 = &#63; or throws a {@link NoSuchCountryException} if it could not be found.
	*
	* @param a3 the a3
	* @return the matching country
	* @throws NoSuchCountryException if a matching country could not be found
	*/
	public Country findByA3(java.lang.String a3) throws NoSuchCountryException;

	/**
	* Returns the country where a3 = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param a3 the a3
	* @return the matching country, or <code>null</code> if a matching country could not be found
	*/
	public Country fetchByA3(java.lang.String a3);

	/**
	* Returns the country where a3 = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param a3 the a3
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching country, or <code>null</code> if a matching country could not be found
	*/
	public Country fetchByA3(java.lang.String a3, boolean retrieveFromCache);

	/**
	* Removes the country where a3 = &#63; from the database.
	*
	* @param a3 the a3
	* @return the country that was removed
	*/
	public Country removeByA3(java.lang.String a3)
		throws NoSuchCountryException;

	/**
	* Returns the number of countries where a3 = &#63;.
	*
	* @param a3 the a3
	* @return the number of matching countries
	*/
	public int countByA3(java.lang.String a3);

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of countries
	* @param end the upper bound of the range of countries (not inclusive)
	* @return the range of matching countries
	*/
	public java.util.List<Country> findByActive(boolean active, int start,
		int end);

	/**
	* Returns an ordered range of all the countries where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of countries
	* @param end the upper bound of the range of countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching countries
	*/
	public java.util.List<Country> findByActive(boolean active, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Country> orderByComparator);

	/**
	* Returns an ordered range of all the countries where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of countries
	* @param end the upper bound of the range of countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching countries
	*/
	public java.util.List<Country> findByActive(boolean active, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<Country> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first country in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching country
	* @throws NoSuchCountryException if a matching country could not be found
	*/
	public Country findByActive_First(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<Country> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Returns the first country in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching country, or <code>null</code> if a matching country could not be found
	*/
	public Country fetchByActive_First(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<Country> orderByComparator);

	/**
	* Returns the last country in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching country
	* @throws NoSuchCountryException if a matching country could not be found
	*/
	public Country findByActive_Last(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<Country> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Returns the last country in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching country, or <code>null</code> if a matching country could not be found
	*/
	public Country fetchByActive_Last(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<Country> orderByComparator);

	/**
	* Returns the countries before and after the current country in the ordered set where active = &#63;.
	*
	* @param countryId the primary key of the current country
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next country
	* @throws NoSuchCountryException if a country with the primary key could not be found
	*/
	public Country[] findByActive_PrevAndNext(long countryId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<Country> orderByComparator)
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
	* Returns the country with the primary key or throws a {@link NoSuchCountryException} if it could not be found.
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

	@Override
	public java.util.Map<java.io.Serializable, Country> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of countries
	* @param end the upper bound of the range of countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of countries
	*/
	public java.util.List<Country> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Country> orderByComparator);

	/**
	* Returns an ordered range of all the countries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of countries
	* @param end the upper bound of the range of countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of countries
	*/
	public java.util.List<Country> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Country> orderByComparator,
		boolean retrieveFromCache);

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

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}