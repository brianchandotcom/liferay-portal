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

import com.liferay.commerce.exception.NoSuchAddressException;
import com.liferay.commerce.model.CommerceAddress;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce address service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommerceAddressPersistenceImpl
 * @see CommerceAddressUtil
 * @generated
 */
@ProviderType
public interface CommerceAddressPersistence extends BasePersistence<CommerceAddress> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceAddressUtil} to access the commerce address persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce addresses where commerceRegionId = &#63;.
	*
	* @param commerceRegionId the commerce region ID
	* @return the matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByCommerceRegionId(
		long commerceRegionId);

	/**
	* Returns a range of all the commerce addresses where commerceRegionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceRegionId the commerce region ID
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @return the range of matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByCommerceRegionId(
		long commerceRegionId, int start, int end);

	/**
	* Returns an ordered range of all the commerce addresses where commerceRegionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceRegionId the commerce region ID
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByCommerceRegionId(
		long commerceRegionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator);

	/**
	* Returns an ordered range of all the commerce addresses where commerceRegionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceRegionId the commerce region ID
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByCommerceRegionId(
		long commerceRegionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce address in the ordered set where commerceRegionId = &#63;.
	*
	* @param commerceRegionId the commerce region ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public CommerceAddress findByCommerceRegionId_First(long commerceRegionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException;

	/**
	* Returns the first commerce address in the ordered set where commerceRegionId = &#63;.
	*
	* @param commerceRegionId the commerce region ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public CommerceAddress fetchByCommerceRegionId_First(
		long commerceRegionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator);

	/**
	* Returns the last commerce address in the ordered set where commerceRegionId = &#63;.
	*
	* @param commerceRegionId the commerce region ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public CommerceAddress findByCommerceRegionId_Last(long commerceRegionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException;

	/**
	* Returns the last commerce address in the ordered set where commerceRegionId = &#63;.
	*
	* @param commerceRegionId the commerce region ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public CommerceAddress fetchByCommerceRegionId_Last(long commerceRegionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator);

	/**
	* Returns the commerce addresses before and after the current commerce address in the ordered set where commerceRegionId = &#63;.
	*
	* @param commerceAddressId the primary key of the current commerce address
	* @param commerceRegionId the commerce region ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce address
	* @throws NoSuchAddressException if a commerce address with the primary key could not be found
	*/
	public CommerceAddress[] findByCommerceRegionId_PrevAndNext(
		long commerceAddressId, long commerceRegionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException;

	/**
	* Removes all the commerce addresses where commerceRegionId = &#63; from the database.
	*
	* @param commerceRegionId the commerce region ID
	*/
	public void removeByCommerceRegionId(long commerceRegionId);

	/**
	* Returns the number of commerce addresses where commerceRegionId = &#63;.
	*
	* @param commerceRegionId the commerce region ID
	* @return the number of matching commerce addresses
	*/
	public int countByCommerceRegionId(long commerceRegionId);

	/**
	* Returns all the commerce addresses where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @return the matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByCommerceCountryId(
		long commerceCountryId);

	/**
	* Returns a range of all the commerce addresses where commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @return the range of matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByCommerceCountryId(
		long commerceCountryId, int start, int end);

	/**
	* Returns an ordered range of all the commerce addresses where commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator);

	/**
	* Returns an ordered range of all the commerce addresses where commerceCountryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCountryId the commerce country ID
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce address in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public CommerceAddress findByCommerceCountryId_First(
		long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException;

	/**
	* Returns the first commerce address in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public CommerceAddress fetchByCommerceCountryId_First(
		long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator);

	/**
	* Returns the last commerce address in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public CommerceAddress findByCommerceCountryId_Last(
		long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException;

	/**
	* Returns the last commerce address in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public CommerceAddress fetchByCommerceCountryId_Last(
		long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator);

	/**
	* Returns the commerce addresses before and after the current commerce address in the ordered set where commerceCountryId = &#63;.
	*
	* @param commerceAddressId the primary key of the current commerce address
	* @param commerceCountryId the commerce country ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce address
	* @throws NoSuchAddressException if a commerce address with the primary key could not be found
	*/
	public CommerceAddress[] findByCommerceCountryId_PrevAndNext(
		long commerceAddressId, long commerceCountryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException;

	/**
	* Removes all the commerce addresses where commerceCountryId = &#63; from the database.
	*
	* @param commerceCountryId the commerce country ID
	*/
	public void removeByCommerceCountryId(long commerceCountryId);

	/**
	* Returns the number of commerce addresses where commerceCountryId = &#63;.
	*
	* @param commerceCountryId the commerce country ID
	* @return the number of matching commerce addresses
	*/
	public int countByCommerceCountryId(long commerceCountryId);

	/**
	* Returns all the commerce addresses where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByC_C(long classNameId,
		long classPK);

	/**
	* Returns a range of all the commerce addresses where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @return the range of matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByC_C(long classNameId,
		long classPK, int start, int end);

	/**
	* Returns an ordered range of all the commerce addresses where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator);

	/**
	* Returns an ordered range of all the commerce addresses where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce address in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public CommerceAddress findByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException;

	/**
	* Returns the first commerce address in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public CommerceAddress fetchByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator);

	/**
	* Returns the last commerce address in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public CommerceAddress findByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException;

	/**
	* Returns the last commerce address in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public CommerceAddress fetchByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator);

	/**
	* Returns the commerce addresses before and after the current commerce address in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param commerceAddressId the primary key of the current commerce address
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce address
	* @throws NoSuchAddressException if a commerce address with the primary key could not be found
	*/
	public CommerceAddress[] findByC_C_PrevAndNext(long commerceAddressId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException;

	/**
	* Removes all the commerce addresses where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public void removeByC_C(long classNameId, long classPK);

	/**
	* Returns the number of commerce addresses where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching commerce addresses
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Returns all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByG_C_C(long groupId,
		long classNameId, long classPK);

	/**
	* Returns a range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @return the range of matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end);

	/**
	* Returns an ordered range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator);

	/**
	* Returns an ordered range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public CommerceAddress findByG_C_C_First(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException;

	/**
	* Returns the first commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public CommerceAddress fetchByG_C_C_First(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator);

	/**
	* Returns the last commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public CommerceAddress findByG_C_C_Last(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException;

	/**
	* Returns the last commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public CommerceAddress fetchByG_C_C_Last(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator);

	/**
	* Returns the commerce addresses before and after the current commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param commerceAddressId the primary key of the current commerce address
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce address
	* @throws NoSuchAddressException if a commerce address with the primary key could not be found
	*/
	public CommerceAddress[] findByG_C_C_PrevAndNext(long commerceAddressId,
		long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException;

	/**
	* Removes all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public void removeByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Returns the number of commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching commerce addresses
	*/
	public int countByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Returns all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @return the matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByG_C_C_DB(long groupId,
		long classNameId, long classPK, boolean defaultBilling);

	/**
	* Returns a range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @return the range of matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByG_C_C_DB(long groupId,
		long classNameId, long classPK, boolean defaultBilling, int start,
		int end);

	/**
	* Returns an ordered range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByG_C_C_DB(long groupId,
		long classNameId, long classPK, boolean defaultBilling, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator);

	/**
	* Returns an ordered range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByG_C_C_DB(long groupId,
		long classNameId, long classPK, boolean defaultBilling, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public CommerceAddress findByG_C_C_DB_First(long groupId, long classNameId,
		long classPK, boolean defaultBilling,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException;

	/**
	* Returns the first commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public CommerceAddress fetchByG_C_C_DB_First(long groupId,
		long classNameId, long classPK, boolean defaultBilling,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator);

	/**
	* Returns the last commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public CommerceAddress findByG_C_C_DB_Last(long groupId, long classNameId,
		long classPK, boolean defaultBilling,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException;

	/**
	* Returns the last commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public CommerceAddress fetchByG_C_C_DB_Last(long groupId, long classNameId,
		long classPK, boolean defaultBilling,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator);

	/**
	* Returns the commerce addresses before and after the current commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* @param commerceAddressId the primary key of the current commerce address
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce address
	* @throws NoSuchAddressException if a commerce address with the primary key could not be found
	*/
	public CommerceAddress[] findByG_C_C_DB_PrevAndNext(
		long commerceAddressId, long groupId, long classNameId, long classPK,
		boolean defaultBilling,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException;

	/**
	* Removes all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	*/
	public void removeByG_C_C_DB(long groupId, long classNameId, long classPK,
		boolean defaultBilling);

	/**
	* Returns the number of commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultBilling = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultBilling the default billing
	* @return the number of matching commerce addresses
	*/
	public int countByG_C_C_DB(long groupId, long classNameId, long classPK,
		boolean defaultBilling);

	/**
	* Returns all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @return the matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByG_C_C_DS(long groupId,
		long classNameId, long classPK, boolean defaultShipping);

	/**
	* Returns a range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @return the range of matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByG_C_C_DS(long groupId,
		long classNameId, long classPK, boolean defaultShipping, int start,
		int end);

	/**
	* Returns an ordered range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByG_C_C_DS(long groupId,
		long classNameId, long classPK, boolean defaultShipping, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator);

	/**
	* Returns an ordered range of all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce addresses
	*/
	public java.util.List<CommerceAddress> findByG_C_C_DS(long groupId,
		long classNameId, long classPK, boolean defaultShipping, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public CommerceAddress findByG_C_C_DS_First(long groupId, long classNameId,
		long classPK, boolean defaultShipping,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException;

	/**
	* Returns the first commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public CommerceAddress fetchByG_C_C_DS_First(long groupId,
		long classNameId, long classPK, boolean defaultShipping,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator);

	/**
	* Returns the last commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address
	* @throws NoSuchAddressException if a matching commerce address could not be found
	*/
	public CommerceAddress findByG_C_C_DS_Last(long groupId, long classNameId,
		long classPK, boolean defaultShipping,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException;

	/**
	* Returns the last commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	*/
	public CommerceAddress fetchByG_C_C_DS_Last(long groupId, long classNameId,
		long classPK, boolean defaultShipping,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator);

	/**
	* Returns the commerce addresses before and after the current commerce address in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* @param commerceAddressId the primary key of the current commerce address
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce address
	* @throws NoSuchAddressException if a commerce address with the primary key could not be found
	*/
	public CommerceAddress[] findByG_C_C_DS_PrevAndNext(
		long commerceAddressId, long groupId, long classNameId, long classPK,
		boolean defaultShipping,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException;

	/**
	* Removes all the commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	*/
	public void removeByG_C_C_DS(long groupId, long classNameId, long classPK,
		boolean defaultShipping);

	/**
	* Returns the number of commerce addresses where groupId = &#63; and classNameId = &#63; and classPK = &#63; and defaultShipping = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param defaultShipping the default shipping
	* @return the number of matching commerce addresses
	*/
	public int countByG_C_C_DS(long groupId, long classNameId, long classPK,
		boolean defaultShipping);

	/**
	* Caches the commerce address in the entity cache if it is enabled.
	*
	* @param commerceAddress the commerce address
	*/
	public void cacheResult(CommerceAddress commerceAddress);

	/**
	* Caches the commerce addresses in the entity cache if it is enabled.
	*
	* @param commerceAddresses the commerce addresses
	*/
	public void cacheResult(java.util.List<CommerceAddress> commerceAddresses);

	/**
	* Creates a new commerce address with the primary key. Does not add the commerce address to the database.
	*
	* @param commerceAddressId the primary key for the new commerce address
	* @return the new commerce address
	*/
	public CommerceAddress create(long commerceAddressId);

	/**
	* Removes the commerce address with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceAddressId the primary key of the commerce address
	* @return the commerce address that was removed
	* @throws NoSuchAddressException if a commerce address with the primary key could not be found
	*/
	public CommerceAddress remove(long commerceAddressId)
		throws NoSuchAddressException;

	public CommerceAddress updateImpl(CommerceAddress commerceAddress);

	/**
	* Returns the commerce address with the primary key or throws a {@link NoSuchAddressException} if it could not be found.
	*
	* @param commerceAddressId the primary key of the commerce address
	* @return the commerce address
	* @throws NoSuchAddressException if a commerce address with the primary key could not be found
	*/
	public CommerceAddress findByPrimaryKey(long commerceAddressId)
		throws NoSuchAddressException;

	/**
	* Returns the commerce address with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceAddressId the primary key of the commerce address
	* @return the commerce address, or <code>null</code> if a commerce address with the primary key could not be found
	*/
	public CommerceAddress fetchByPrimaryKey(long commerceAddressId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceAddress> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce addresses.
	*
	* @return the commerce addresses
	*/
	public java.util.List<CommerceAddress> findAll();

	/**
	* Returns a range of all the commerce addresses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @return the range of commerce addresses
	*/
	public java.util.List<CommerceAddress> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce addresses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce addresses
	*/
	public java.util.List<CommerceAddress> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator);

	/**
	* Returns an ordered range of all the commerce addresses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce addresses
	* @param end the upper bound of the range of commerce addresses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce addresses
	*/
	public java.util.List<CommerceAddress> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce addresses from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce addresses.
	*
	* @return the number of commerce addresses
	*/
	public int countAll();
}