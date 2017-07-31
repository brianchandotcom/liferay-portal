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

package com.liferay.commerce.address.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.address.exception.NoSuchCountryException;
import com.liferay.commerce.address.model.CommerceCountry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce country service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.address.service.persistence.impl.CommerceCountryPersistenceImpl
 * @see CommerceCountryUtil
 * @generated
 */
@ProviderType
public interface CommerceCountryPersistence extends BasePersistence<CommerceCountry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceCountryUtil} to access the commerce country persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the commerce country in the entity cache if it is enabled.
	*
	* @param commerceCountry the commerce country
	*/
	public void cacheResult(CommerceCountry commerceCountry);

	/**
	* Caches the commerce countries in the entity cache if it is enabled.
	*
	* @param commerceCountries the commerce countries
	*/
	public void cacheResult(java.util.List<CommerceCountry> commerceCountries);

	/**
	* Creates a new commerce country with the primary key. Does not add the commerce country to the database.
	*
	* @param commerceCountryId the primary key for the new commerce country
	* @return the new commerce country
	*/
	public CommerceCountry create(long commerceCountryId);

	/**
	* Removes the commerce country with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCountryId the primary key of the commerce country
	* @return the commerce country that was removed
	* @throws NoSuchCountryException if a commerce country with the primary key could not be found
	*/
	public CommerceCountry remove(long commerceCountryId)
		throws NoSuchCountryException;

	public CommerceCountry updateImpl(CommerceCountry commerceCountry);

	/**
	* Returns the commerce country with the primary key or throws a {@link NoSuchCountryException} if it could not be found.
	*
	* @param commerceCountryId the primary key of the commerce country
	* @return the commerce country
	* @throws NoSuchCountryException if a commerce country with the primary key could not be found
	*/
	public CommerceCountry findByPrimaryKey(long commerceCountryId)
		throws NoSuchCountryException;

	/**
	* Returns the commerce country with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceCountryId the primary key of the commerce country
	* @return the commerce country, or <code>null</code> if a commerce country with the primary key could not be found
	*/
	public CommerceCountry fetchByPrimaryKey(long commerceCountryId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceCountry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce countries.
	*
	* @return the commerce countries
	*/
	public java.util.List<CommerceCountry> findAll();

	/**
	* Returns a range of all the commerce countries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @return the range of commerce countries
	*/
	public java.util.List<CommerceCountry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce countries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce countries
	*/
	public java.util.List<CommerceCountry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce countries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce countries
	*/
	public java.util.List<CommerceCountry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce countries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce countries.
	*
	* @return the number of commerce countries
	*/
	public int countAll();
}