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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

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
@ProviderType
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
	public static List<Country> findWithDynamicQuery(DynamicQuery dynamicQuery) {
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
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
	public static Country update(Country country, ServiceContext serviceContext) {
		return getPersistence().update(country, serviceContext);
	}

	/**
	* Returns the country where name = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	*
	* @param name the name
	* @return the matching country
	* @throws NoSuchCountryException if a matching country could not be found
	*/
	public static Country findByName(String name)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {
		return getPersistence().findByName(name);
	}

	/**
	* Returns the country where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param name the name
	* @return the matching country, or <code>null</code> if a matching country could not be found
	*/
	public static Country fetchByName(String name) {
		return getPersistence().fetchByName(name);
	}

	/**
	* Returns the country where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching country, or <code>null</code> if a matching country could not be found
	*/
	public static Country fetchByName(String name, boolean retrieveFromCache) {
		return getPersistence().fetchByName(name, retrieveFromCache);
	}

	/**
	* Removes the country where name = &#63; from the database.
	*
	* @param name the name
	* @return the country that was removed
	*/
	public static Country removeByName(String name)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {
		return getPersistence().removeByName(name);
	}

	/**
	* Returns the number of countries where name = &#63;.
	*
	* @param name the name
	* @return the number of matching countries
	*/
	public static int countByName(String name) {
		return getPersistence().countByName(name);
	}

	/**
	* Returns the country where a2 = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	*
	* @param a2 the a2
	* @return the matching country
	* @throws NoSuchCountryException if a matching country could not be found
	*/
	public static Country findByA2(String a2)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {
		return getPersistence().findByA2(a2);
	}

	/**
	* Returns the country where a2 = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param a2 the a2
	* @return the matching country, or <code>null</code> if a matching country could not be found
	*/
	public static Country fetchByA2(String a2) {
		return getPersistence().fetchByA2(a2);
	}

	/**
	* Returns the country where a2 = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param a2 the a2
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching country, or <code>null</code> if a matching country could not be found
	*/
	public static Country fetchByA2(String a2, boolean retrieveFromCache) {
		return getPersistence().fetchByA2(a2, retrieveFromCache);
	}

	/**
	* Removes the country where a2 = &#63; from the database.
	*
	* @param a2 the a2
	* @return the country that was removed
	*/
	public static Country removeByA2(String a2)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {
		return getPersistence().removeByA2(a2);
	}

	/**
	* Returns the number of countries where a2 = &#63;.
	*
	* @param a2 the a2
	* @return the number of matching countries
	*/
	public static int countByA2(String a2) {
		return getPersistence().countByA2(a2);
	}

	/**
	* Returns the country where a3 = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	*
	* @param a3 the a3
	* @return the matching country
	* @throws NoSuchCountryException if a matching country could not be found
	*/
	public static Country findByA3(String a3)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {
		return getPersistence().findByA3(a3);
	}

	/**
	* Returns the country where a3 = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param a3 the a3
	* @return the matching country, or <code>null</code> if a matching country could not be found
	*/
	public static Country fetchByA3(String a3) {
		return getPersistence().fetchByA3(a3);
	}

	/**
	* Returns the country where a3 = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param a3 the a3
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching country, or <code>null</code> if a matching country could not be found
	*/
	public static Country fetchByA3(String a3, boolean retrieveFromCache) {
		return getPersistence().fetchByA3(a3, retrieveFromCache);
	}

	/**
	* Removes the country where a3 = &#63; from the database.
	*
	* @param a3 the a3
	* @return the country that was removed
	*/
	public static Country removeByA3(String a3)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {
		return getPersistence().removeByA3(a3);
	}

	/**
	* Returns the number of countries where a3 = &#63;.
	*
	* @param a3 the a3
	* @return the number of matching countries
	*/
	public static int countByA3(String a3) {
		return getPersistence().countByA3(a3);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of countries
	* @param end the upper bound of the range of countries (not inclusive)
	* @return the range of matching countries
	*/
	public static List<Country> findByActive(boolean active, int start, int end) {
		return getPersistence().findByActive(active, start, end);
	}

	/**
	* Returns an ordered range of all the countries where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of countries
	* @param end the upper bound of the range of countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching countries
	*/
	public static List<Country> findByActive(boolean active, int start,
		int end, OrderByComparator<Country> orderByComparator) {
		return getPersistence()
				   .findByActive(active, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the countries where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of countries
	* @param end the upper bound of the range of countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching countries
	*/
	public static List<Country> findByActive(boolean active, int start,
		int end, OrderByComparator<Country> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByActive(active, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first country in the ordered set where active = &#63;.
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching country
	* @throws NoSuchCountryException if a matching country could not be found
	*/
	public static Country findByActive_First(boolean active,
		OrderByComparator<Country> orderByComparator)
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
	public static Country fetchByActive_First(boolean active,
		OrderByComparator<Country> orderByComparator) {
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
	public static Country findByActive_Last(boolean active,
		OrderByComparator<Country> orderByComparator)
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
	public static Country fetchByActive_Last(boolean active,
		OrderByComparator<Country> orderByComparator) {
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
	public static Country[] findByActive_PrevAndNext(long countryId,
		boolean active, OrderByComparator<Country> orderByComparator)
		throws com.liferay.portal.kernel.exception.NoSuchCountryException {
		return getPersistence()
				   .findByActive_PrevAndNext(countryId, active,
			orderByComparator);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of countries
	* @param end the upper bound of the range of countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of countries
	*/
	public static List<Country> findAll(int start, int end,
		OrderByComparator<Country> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the countries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CountryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of countries
	* @param end the upper bound of the range of countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of countries
	*/
	public static List<Country> findAll(int start, int end,
		OrderByComparator<Country> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
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
		if (_persistence == null) {
			_persistence = (CountryPersistence)PortalBeanLocatorUtil.locate(CountryPersistence.class.getName());

			ReferenceRegistry.registerReference(CountryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static CountryPersistence _persistence;
}