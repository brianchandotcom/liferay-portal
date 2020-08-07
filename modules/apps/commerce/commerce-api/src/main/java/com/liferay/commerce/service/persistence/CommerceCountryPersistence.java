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

import com.liferay.commerce.exception.NoSuchCountryException;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce country service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCountryUtil
 * @generated
 */
@ProviderType
public interface CommerceCountryPersistence
	extends BasePersistence<CommerceCountry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceCountryUtil} to access the commerce country persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce countries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce countries
	 */
	public java.util.List<CommerceCountry> findByUuid(String uuid);

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
	public java.util.List<CommerceCountry> findByUuid(
		String uuid, int start, int end);

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
	public java.util.List<CommerceCountry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator);

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
	public java.util.List<CommerceCountry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public CommerceCountry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the first commerce country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public CommerceCountry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator);

	/**
	 * Returns the last commerce country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public CommerceCountry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the last commerce country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public CommerceCountry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator);

	/**
	 * Returns the commerce countries before and after the current commerce country in the ordered set where uuid = &#63;.
	 *
	 * @param commerceCountryId the primary key of the current commerce country
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce country
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	public CommerceCountry[] findByUuid_PrevAndNext(
			long commerceCountryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Removes all the commerce countries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce countries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce countries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the commerce countries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce countries
	 */
	public java.util.List<CommerceCountry> findByUuid_C(
		String uuid, long companyId);

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
	public java.util.List<CommerceCountry> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<CommerceCountry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator);

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
	public java.util.List<CommerceCountry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public CommerceCountry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the first commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public CommerceCountry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator);

	/**
	 * Returns the last commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public CommerceCountry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the last commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public CommerceCountry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator);

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
	public CommerceCountry[] findByUuid_C_PrevAndNext(
			long commerceCountryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Removes all the commerce countries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce countries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce countries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce countries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce countries
	 */
	public java.util.List<CommerceCountry> findByCompanyId(long companyId);

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
	public java.util.List<CommerceCountry> findByCompanyId(
		long companyId, int start, int end);

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
	public java.util.List<CommerceCountry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator);

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
	public java.util.List<CommerceCountry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public CommerceCountry findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the first commerce country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public CommerceCountry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator);

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public CommerceCountry findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public CommerceCountry fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator);

	/**
	 * Returns the commerce countries before and after the current commerce country in the ordered set where companyId = &#63;.
	 *
	 * @param commerceCountryId the primary key of the current commerce country
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce country
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	public CommerceCountry[] findByCompanyId_PrevAndNext(
			long commerceCountryId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Removes all the commerce countries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of commerce countries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce countries
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the commerce country where companyId = &#63; and twoLettersISOCode = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param twoLettersISOCode the two letters iso code
	 * @return the matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public CommerceCountry findByC_Tw(long companyId, String twoLettersISOCode)
		throws NoSuchCountryException;

	/**
	 * Returns the commerce country where companyId = &#63; and twoLettersISOCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param twoLettersISOCode the two letters iso code
	 * @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public CommerceCountry fetchByC_Tw(
		long companyId, String twoLettersISOCode);

	/**
	 * Returns the commerce country where companyId = &#63; and twoLettersISOCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param twoLettersISOCode the two letters iso code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public CommerceCountry fetchByC_Tw(
		long companyId, String twoLettersISOCode, boolean useFinderCache);

	/**
	 * Removes the commerce country where companyId = &#63; and twoLettersISOCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param twoLettersISOCode the two letters iso code
	 * @return the commerce country that was removed
	 */
	public CommerceCountry removeByC_Tw(
			long companyId, String twoLettersISOCode)
		throws NoSuchCountryException;

	/**
	 * Returns the number of commerce countries where companyId = &#63; and twoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twoLettersISOCode the two letters iso code
	 * @return the number of matching commerce countries
	 */
	public int countByC_Tw(long companyId, String twoLettersISOCode);

	/**
	 * Returns the commerce country where companyId = &#63; and numericISOCode = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param numericISOCode the numeric iso code
	 * @return the matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public CommerceCountry findByC_N(long companyId, int numericISOCode)
		throws NoSuchCountryException;

	/**
	 * Returns the commerce country where companyId = &#63; and numericISOCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param numericISOCode the numeric iso code
	 * @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public CommerceCountry fetchByC_N(long companyId, int numericISOCode);

	/**
	 * Returns the commerce country where companyId = &#63; and numericISOCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param numericISOCode the numeric iso code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public CommerceCountry fetchByC_N(
		long companyId, int numericISOCode, boolean useFinderCache);

	/**
	 * Removes the commerce country where companyId = &#63; and numericISOCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param numericISOCode the numeric iso code
	 * @return the commerce country that was removed
	 */
	public CommerceCountry removeByC_N(long companyId, int numericISOCode)
		throws NoSuchCountryException;

	/**
	 * Returns the number of commerce countries where companyId = &#63; and numericISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param numericISOCode the numeric iso code
	 * @return the number of matching commerce countries
	 */
	public int countByC_N(long companyId, int numericISOCode);

	/**
	 * Returns all the commerce countries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce countries
	 */
	public java.util.List<CommerceCountry> findByC_A(
		long companyId, boolean active);

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
	public java.util.List<CommerceCountry> findByC_A(
		long companyId, boolean active, int start, int end);

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
	public java.util.List<CommerceCountry> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator);

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
	public java.util.List<CommerceCountry> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public CommerceCountry findByC_A_First(
			long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the first commerce country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public CommerceCountry fetchByC_A_First(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator);

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	public CommerceCountry findByC_A_Last(
			long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public CommerceCountry fetchByC_A_Last(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator);

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
	public CommerceCountry[] findByC_A_PrevAndNext(
			long commerceCountryId, long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Removes all the commerce countries where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	public void removeByC_A(long companyId, boolean active);

	/**
	 * Returns the number of commerce countries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce countries
	 */
	public int countByC_A(long companyId, boolean active);

	/**
	 * Returns all the commerce countries where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @return the matching commerce countries
	 */
	public java.util.List<CommerceCountry> findByC_B_A(
		long companyId, boolean billingAllowed, boolean active);

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
	public java.util.List<CommerceCountry> findByC_B_A(
		long companyId, boolean billingAllowed, boolean active, int start,
		int end);

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
	public java.util.List<CommerceCountry> findByC_B_A(
		long companyId, boolean billingAllowed, boolean active, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator);

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
	public java.util.List<CommerceCountry> findByC_B_A(
		long companyId, boolean billingAllowed, boolean active, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator,
		boolean useFinderCache);

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
	public CommerceCountry findByC_B_A_First(
			long companyId, boolean billingAllowed, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the first commerce country in the ordered set where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public CommerceCountry fetchByC_B_A_First(
		long companyId, boolean billingAllowed, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator);

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
	public CommerceCountry findByC_B_A_Last(
			long companyId, boolean billingAllowed, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public CommerceCountry fetchByC_B_A_Last(
		long companyId, boolean billingAllowed, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator);

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
	public CommerceCountry[] findByC_B_A_PrevAndNext(
			long commerceCountryId, long companyId, boolean billingAllowed,
			boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Removes all the commerce countries where companyId = &#63; and billingAllowed = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 */
	public void removeByC_B_A(
		long companyId, boolean billingAllowed, boolean active);

	/**
	 * Returns the number of commerce countries where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @return the number of matching commerce countries
	 */
	public int countByC_B_A(
		long companyId, boolean billingAllowed, boolean active);

	/**
	 * Returns all the commerce countries where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @return the matching commerce countries
	 */
	public java.util.List<CommerceCountry> findByC_S_A(
		long companyId, boolean shippingAllowed, boolean active);

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
	public java.util.List<CommerceCountry> findByC_S_A(
		long companyId, boolean shippingAllowed, boolean active, int start,
		int end);

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
	public java.util.List<CommerceCountry> findByC_S_A(
		long companyId, boolean shippingAllowed, boolean active, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator);

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
	public java.util.List<CommerceCountry> findByC_S_A(
		long companyId, boolean shippingAllowed, boolean active, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator,
		boolean useFinderCache);

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
	public CommerceCountry findByC_S_A_First(
			long companyId, boolean shippingAllowed, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the first commerce country in the ordered set where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public CommerceCountry fetchByC_S_A_First(
		long companyId, boolean shippingAllowed, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator);

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
	public CommerceCountry findByC_S_A_Last(
			long companyId, boolean shippingAllowed, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	public CommerceCountry fetchByC_S_A_Last(
		long companyId, boolean shippingAllowed, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator);

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
	public CommerceCountry[] findByC_S_A_PrevAndNext(
			long commerceCountryId, long companyId, boolean shippingAllowed,
			boolean active,
			com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
				orderByComparator)
		throws NoSuchCountryException;

	/**
	 * Removes all the commerce countries where companyId = &#63; and shippingAllowed = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 */
	public void removeByC_S_A(
		long companyId, boolean shippingAllowed, boolean active);

	/**
	 * Returns the number of commerce countries where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @return the number of matching commerce countries
	 */
	public int countByC_S_A(
		long companyId, boolean shippingAllowed, boolean active);

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
	 * Returns the commerce country with the primary key or throws a <code>NoSuchCountryException</code> if it could not be found.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce countries
	 */
	public java.util.List<CommerceCountry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator);

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
	public java.util.List<CommerceCountry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry>
			orderByComparator,
		boolean useFinderCache);

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