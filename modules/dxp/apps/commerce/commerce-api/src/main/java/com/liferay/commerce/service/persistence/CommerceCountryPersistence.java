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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.exception.NoSuchCountryException;
import com.liferay.commerce.model.CommerceCountry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce country service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommerceCountryPersistenceImpl
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
	* Returns all the commerce countries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the commerce countries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @return the range of matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce countries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce countries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce country in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public CommerceCountry findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Returns the first commerce country in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public CommerceCountry fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator);

	/**
	* Returns the last commerce country in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public CommerceCountry findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Returns the last commerce country in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public CommerceCountry fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator);

	/**
	* Returns the commerce countries before and after the current commerce country in the ordered set where uuid = &#63;.
	*
	* @param commerceCountryId the primary key of the current commerce country
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce country
	* @throws NoSuchCountryException if a commerce country with the primary key could not be found
	*/
	public CommerceCountry[] findByUuid_PrevAndNext(long commerceCountryId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Removes all the commerce countries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of commerce countries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce countries
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the commerce country where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCountryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public CommerceCountry findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCountryException;

	/**
	* Returns the commerce country where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public CommerceCountry fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the commerce country where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public CommerceCountry fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the commerce country where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce country that was removed
	*/
	public CommerceCountry removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCountryException;

	/**
	* Returns the number of commerce countries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce countries
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the commerce countries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the commerce countries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @return the range of matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce countries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce countries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public CommerceCountry findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Returns the first commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public CommerceCountry fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator);

	/**
	* Returns the last commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public CommerceCountry findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Returns the last commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public CommerceCountry fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator);

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
	public CommerceCountry[] findByUuid_C_PrevAndNext(long commerceCountryId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Removes all the commerce countries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of commerce countries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce countries
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the commerce countries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByGroupId(long groupId);

	/**
	* Returns a range of all the commerce countries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @return the range of matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce countries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce countries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce country in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public CommerceCountry findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Returns the first commerce country in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public CommerceCountry fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator);

	/**
	* Returns the last commerce country in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public CommerceCountry findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Returns the last commerce country in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public CommerceCountry fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator);

	/**
	* Returns the commerce countries before and after the current commerce country in the ordered set where groupId = &#63;.
	*
	* @param commerceCountryId the primary key of the current commerce country
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce country
	* @throws NoSuchCountryException if a commerce country with the primary key could not be found
	*/
	public CommerceCountry[] findByGroupId_PrevAndNext(long commerceCountryId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Removes all the commerce countries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce countries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce countries
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the commerce country where groupId = &#63; and numericISOCode = &#63; or throws a {@link NoSuchCountryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param numericISOCode the numeric iso code
	* @return the matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public CommerceCountry findByG_N(long groupId, int numericISOCode)
		throws NoSuchCountryException;

	/**
	* Returns the commerce country where groupId = &#63; and numericISOCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param numericISOCode the numeric iso code
	* @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public CommerceCountry fetchByG_N(long groupId, int numericISOCode);

	/**
	* Returns the commerce country where groupId = &#63; and numericISOCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param numericISOCode the numeric iso code
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public CommerceCountry fetchByG_N(long groupId, int numericISOCode,
		boolean retrieveFromCache);

	/**
	* Removes the commerce country where groupId = &#63; and numericISOCode = &#63; from the database.
	*
	* @param groupId the group ID
	* @param numericISOCode the numeric iso code
	* @return the commerce country that was removed
	*/
	public CommerceCountry removeByG_N(long groupId, int numericISOCode)
		throws NoSuchCountryException;

	/**
	* Returns the number of commerce countries where groupId = &#63; and numericISOCode = &#63;.
	*
	* @param groupId the group ID
	* @param numericISOCode the numeric iso code
	* @return the number of matching commerce countries
	*/
	public int countByG_N(long groupId, int numericISOCode);

	/**
	* Returns all the commerce countries where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByG_A(long groupId,
		boolean active);

	/**
	* Returns a range of all the commerce countries where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @return the range of matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByG_A(long groupId,
		boolean active, int start, int end);

	/**
	* Returns an ordered range of all the commerce countries where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByG_A(long groupId,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce countries where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByG_A(long groupId,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce country in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public CommerceCountry findByG_A_First(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Returns the first commerce country in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public CommerceCountry fetchByG_A_First(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator);

	/**
	* Returns the last commerce country in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public CommerceCountry findByG_A_Last(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Returns the last commerce country in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public CommerceCountry fetchByG_A_Last(long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator);

	/**
	* Returns the commerce countries before and after the current commerce country in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param commerceCountryId the primary key of the current commerce country
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce country
	* @throws NoSuchCountryException if a commerce country with the primary key could not be found
	*/
	public CommerceCountry[] findByG_A_PrevAndNext(long commerceCountryId,
		long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Removes all the commerce countries where groupId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	*/
	public void removeByG_A(long groupId, boolean active);

	/**
	* Returns the number of commerce countries where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching commerce countries
	*/
	public int countByG_A(long groupId, boolean active);

	/**
	* Returns all the commerce countries where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @return the matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByG_B_A(long groupId,
		boolean billingAllowed, boolean active);

	/**
	* Returns a range of all the commerce countries where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @return the range of matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByG_B_A(long groupId,
		boolean billingAllowed, boolean active, int start, int end);

	/**
	* Returns an ordered range of all the commerce countries where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByG_B_A(long groupId,
		boolean billingAllowed, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce countries where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByG_B_A(long groupId,
		boolean billingAllowed, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce country in the ordered set where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public CommerceCountry findByG_B_A_First(long groupId,
		boolean billingAllowed, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Returns the first commerce country in the ordered set where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public CommerceCountry fetchByG_B_A_First(long groupId,
		boolean billingAllowed, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator);

	/**
	* Returns the last commerce country in the ordered set where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public CommerceCountry findByG_B_A_Last(long groupId,
		boolean billingAllowed, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Returns the last commerce country in the ordered set where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public CommerceCountry fetchByG_B_A_Last(long groupId,
		boolean billingAllowed, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator);

	/**
	* Returns the commerce countries before and after the current commerce country in the ordered set where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* @param commerceCountryId the primary key of the current commerce country
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce country
	* @throws NoSuchCountryException if a commerce country with the primary key could not be found
	*/
	public CommerceCountry[] findByG_B_A_PrevAndNext(long commerceCountryId,
		long groupId, boolean billingAllowed, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Removes all the commerce countries where groupId = &#63; and billingAllowed = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	*/
	public void removeByG_B_A(long groupId, boolean billingAllowed,
		boolean active);

	/**
	* Returns the number of commerce countries where groupId = &#63; and billingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param billingAllowed the billing allowed
	* @param active the active
	* @return the number of matching commerce countries
	*/
	public int countByG_B_A(long groupId, boolean billingAllowed, boolean active);

	/**
	* Returns all the commerce countries where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @return the matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByG_S_A(long groupId,
		boolean shippingAllowed, boolean active);

	/**
	* Returns a range of all the commerce countries where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @return the range of matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByG_S_A(long groupId,
		boolean shippingAllowed, boolean active, int start, int end);

	/**
	* Returns an ordered range of all the commerce countries where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByG_S_A(long groupId,
		boolean shippingAllowed, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce countries where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCountryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @param start the lower bound of the range of commerce countries
	* @param end the upper bound of the range of commerce countries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce countries
	*/
	public java.util.List<CommerceCountry> findByG_S_A(long groupId,
		boolean shippingAllowed, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce country in the ordered set where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public CommerceCountry findByG_S_A_First(long groupId,
		boolean shippingAllowed, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Returns the first commerce country in the ordered set where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public CommerceCountry fetchByG_S_A_First(long groupId,
		boolean shippingAllowed, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator);

	/**
	* Returns the last commerce country in the ordered set where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country
	* @throws NoSuchCountryException if a matching commerce country could not be found
	*/
	public CommerceCountry findByG_S_A_Last(long groupId,
		boolean shippingAllowed, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Returns the last commerce country in the ordered set where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	*/
	public CommerceCountry fetchByG_S_A_Last(long groupId,
		boolean shippingAllowed, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator);

	/**
	* Returns the commerce countries before and after the current commerce country in the ordered set where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* @param commerceCountryId the primary key of the current commerce country
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce country
	* @throws NoSuchCountryException if a commerce country with the primary key could not be found
	*/
	public CommerceCountry[] findByG_S_A_PrevAndNext(long commerceCountryId,
		long groupId, boolean shippingAllowed, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException;

	/**
	* Removes all the commerce countries where groupId = &#63; and shippingAllowed = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	*/
	public void removeByG_S_A(long groupId, boolean shippingAllowed,
		boolean active);

	/**
	* Returns the number of commerce countries where groupId = &#63; and shippingAllowed = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param shippingAllowed the shipping allowed
	* @param active the active
	* @return the number of matching commerce countries
	*/
	public int countByG_S_A(long groupId, boolean shippingAllowed,
		boolean active);

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

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}