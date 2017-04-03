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

package com.liferay.commerce.product.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.exception.NoSuchProductOptionValueException;
import com.liferay.commerce.product.model.CommerceProductOptionValue;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce product option value service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CommerceProductOptionValuePersistenceImpl
 * @see CommerceProductOptionValueUtil
 * @generated
 */
@ProviderType
public interface CommerceProductOptionValuePersistence extends BasePersistence<CommerceProductOptionValue> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceProductOptionValueUtil} to access the commerce product option value persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce product option values where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce product option values
	*/
	public java.util.List<CommerceProductOptionValue> findByGroupId(
		long groupId);

	/**
	* Returns a range of all the commerce product option values where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @return the range of matching commerce product option values
	*/
	public java.util.List<CommerceProductOptionValue> findByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product option values where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product option values
	*/
	public java.util.List<CommerceProductOptionValue> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product option values where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product option values
	*/
	public java.util.List<CommerceProductOptionValue> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option value
	* @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	*/
	public CommerceProductOptionValue findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException;

	/**
	* Returns the first commerce product option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	*/
	public CommerceProductOptionValue fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator);

	/**
	* Returns the last commerce product option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option value
	* @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	*/
	public CommerceProductOptionValue findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException;

	/**
	* Returns the last commerce product option value in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	*/
	public CommerceProductOptionValue fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator);

	/**
	* Returns the commerce product option values before and after the current commerce product option value in the ordered set where groupId = &#63;.
	*
	* @param commerceProductOptionValueId the primary key of the current commerce product option value
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product option value
	* @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	*/
	public CommerceProductOptionValue[] findByGroupId_PrevAndNext(
		long commerceProductOptionValueId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException;

	/**
	* Removes all the commerce product option values where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce product option values where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce product option values
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the commerce product option values where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce product option values
	*/
	public java.util.List<CommerceProductOptionValue> findByCompanyId(
		long companyId);

	/**
	* Returns a range of all the commerce product option values where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @return the range of matching commerce product option values
	*/
	public java.util.List<CommerceProductOptionValue> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product option values where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product option values
	*/
	public java.util.List<CommerceProductOptionValue> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product option values where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product option values
	*/
	public java.util.List<CommerceProductOptionValue> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product option value in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option value
	* @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	*/
	public CommerceProductOptionValue findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException;

	/**
	* Returns the first commerce product option value in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	*/
	public CommerceProductOptionValue fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator);

	/**
	* Returns the last commerce product option value in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option value
	* @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	*/
	public CommerceProductOptionValue findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException;

	/**
	* Returns the last commerce product option value in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	*/
	public CommerceProductOptionValue fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator);

	/**
	* Returns the commerce product option values before and after the current commerce product option value in the ordered set where companyId = &#63;.
	*
	* @param commerceProductOptionValueId the primary key of the current commerce product option value
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product option value
	* @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	*/
	public CommerceProductOptionValue[] findByCompanyId_PrevAndNext(
		long commerceProductOptionValueId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException;

	/**
	* Removes all the commerce product option values where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of commerce product option values where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce product option values
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the commerce product option values where commerceProductOptionId = &#63;.
	*
	* @param commerceProductOptionId the commerce product option ID
	* @return the matching commerce product option values
	*/
	public java.util.List<CommerceProductOptionValue> findByCommerceProductOptionId(
		long commerceProductOptionId);

	/**
	* Returns a range of all the commerce product option values where commerceProductOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductOptionId the commerce product option ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @return the range of matching commerce product option values
	*/
	public java.util.List<CommerceProductOptionValue> findByCommerceProductOptionId(
		long commerceProductOptionId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product option values where commerceProductOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductOptionId the commerce product option ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product option values
	*/
	public java.util.List<CommerceProductOptionValue> findByCommerceProductOptionId(
		long commerceProductOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product option values where commerceProductOptionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceProductOptionId the commerce product option ID
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product option values
	*/
	public java.util.List<CommerceProductOptionValue> findByCommerceProductOptionId(
		long commerceProductOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product option value in the ordered set where commerceProductOptionId = &#63;.
	*
	* @param commerceProductOptionId the commerce product option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option value
	* @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	*/
	public CommerceProductOptionValue findByCommerceProductOptionId_First(
		long commerceProductOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException;

	/**
	* Returns the first commerce product option value in the ordered set where commerceProductOptionId = &#63;.
	*
	* @param commerceProductOptionId the commerce product option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	*/
	public CommerceProductOptionValue fetchByCommerceProductOptionId_First(
		long commerceProductOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator);

	/**
	* Returns the last commerce product option value in the ordered set where commerceProductOptionId = &#63;.
	*
	* @param commerceProductOptionId the commerce product option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option value
	* @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	*/
	public CommerceProductOptionValue findByCommerceProductOptionId_Last(
		long commerceProductOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException;

	/**
	* Returns the last commerce product option value in the ordered set where commerceProductOptionId = &#63;.
	*
	* @param commerceProductOptionId the commerce product option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	*/
	public CommerceProductOptionValue fetchByCommerceProductOptionId_Last(
		long commerceProductOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator);

	/**
	* Returns the commerce product option values before and after the current commerce product option value in the ordered set where commerceProductOptionId = &#63;.
	*
	* @param commerceProductOptionValueId the primary key of the current commerce product option value
	* @param commerceProductOptionId the commerce product option ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product option value
	* @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	*/
	public CommerceProductOptionValue[] findByCommerceProductOptionId_PrevAndNext(
		long commerceProductOptionValueId, long commerceProductOptionId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException;

	/**
	* Removes all the commerce product option values where commerceProductOptionId = &#63; from the database.
	*
	* @param commerceProductOptionId the commerce product option ID
	*/
	public void removeByCommerceProductOptionId(long commerceProductOptionId);

	/**
	* Returns the number of commerce product option values where commerceProductOptionId = &#63;.
	*
	* @param commerceProductOptionId the commerce product option ID
	* @return the number of matching commerce product option values
	*/
	public int countByCommerceProductOptionId(long commerceProductOptionId);

	/**
	* Caches the commerce product option value in the entity cache if it is enabled.
	*
	* @param commerceProductOptionValue the commerce product option value
	*/
	public void cacheResult(
		CommerceProductOptionValue commerceProductOptionValue);

	/**
	* Caches the commerce product option values in the entity cache if it is enabled.
	*
	* @param commerceProductOptionValues the commerce product option values
	*/
	public void cacheResult(
		java.util.List<CommerceProductOptionValue> commerceProductOptionValues);

	/**
	* Creates a new commerce product option value with the primary key. Does not add the commerce product option value to the database.
	*
	* @param commerceProductOptionValueId the primary key for the new commerce product option value
	* @return the new commerce product option value
	*/
	public CommerceProductOptionValue create(long commerceProductOptionValueId);

	/**
	* Removes the commerce product option value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductOptionValueId the primary key of the commerce product option value
	* @return the commerce product option value that was removed
	* @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	*/
	public CommerceProductOptionValue remove(long commerceProductOptionValueId)
		throws NoSuchProductOptionValueException;

	public CommerceProductOptionValue updateImpl(
		CommerceProductOptionValue commerceProductOptionValue);

	/**
	* Returns the commerce product option value with the primary key or throws a {@link NoSuchProductOptionValueException} if it could not be found.
	*
	* @param commerceProductOptionValueId the primary key of the commerce product option value
	* @return the commerce product option value
	* @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	*/
	public CommerceProductOptionValue findByPrimaryKey(
		long commerceProductOptionValueId)
		throws NoSuchProductOptionValueException;

	/**
	* Returns the commerce product option value with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceProductOptionValueId the primary key of the commerce product option value
	* @return the commerce product option value, or <code>null</code> if a commerce product option value with the primary key could not be found
	*/
	public CommerceProductOptionValue fetchByPrimaryKey(
		long commerceProductOptionValueId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceProductOptionValue> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce product option values.
	*
	* @return the commerce product option values
	*/
	public java.util.List<CommerceProductOptionValue> findAll();

	/**
	* Returns a range of all the commerce product option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @return the range of commerce product option values
	*/
	public java.util.List<CommerceProductOptionValue> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce product option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce product option values
	*/
	public java.util.List<CommerceProductOptionValue> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product option values
	* @param end the upper bound of the range of commerce product option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce product option values
	*/
	public java.util.List<CommerceProductOptionValue> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOptionValue> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce product option values from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce product option values.
	*
	* @return the number of commerce product option values
	*/
	public int countAll();
}