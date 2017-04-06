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

import com.liferay.commerce.product.exception.NoSuchProductOptionException;
import com.liferay.commerce.product.model.CommerceProductOption;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce product option service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CommerceProductOptionPersistenceImpl
 * @see CommerceProductOptionUtil
 * @generated
 */
@ProviderType
public interface CommerceProductOptionPersistence extends BasePersistence<CommerceProductOption> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceProductOptionUtil} to access the commerce product option persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce product options where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce product options
	*/
	public java.util.List<CommerceProductOption> findByGroupId(long groupId);

	/**
	* Returns a range of all the commerce product options where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @return the range of matching commerce product options
	*/
	public java.util.List<CommerceProductOption> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce product options where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product options
	*/
	public java.util.List<CommerceProductOption> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOption> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product options where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product options
	*/
	public java.util.List<CommerceProductOption> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOption> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option
	* @throws NoSuchProductOptionException if a matching commerce product option could not be found
	*/
	public CommerceProductOption findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOption> orderByComparator)
		throws NoSuchProductOptionException;

	/**
	* Returns the first commerce product option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option, or <code>null</code> if a matching commerce product option could not be found
	*/
	public CommerceProductOption fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOption> orderByComparator);

	/**
	* Returns the last commerce product option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option
	* @throws NoSuchProductOptionException if a matching commerce product option could not be found
	*/
	public CommerceProductOption findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOption> orderByComparator)
		throws NoSuchProductOptionException;

	/**
	* Returns the last commerce product option in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option, or <code>null</code> if a matching commerce product option could not be found
	*/
	public CommerceProductOption fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOption> orderByComparator);

	/**
	* Returns the commerce product options before and after the current commerce product option in the ordered set where groupId = &#63;.
	*
	* @param commerceProductOptionId the primary key of the current commerce product option
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product option
	* @throws NoSuchProductOptionException if a commerce product option with the primary key could not be found
	*/
	public CommerceProductOption[] findByGroupId_PrevAndNext(
		long commerceProductOptionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOption> orderByComparator)
		throws NoSuchProductOptionException;

	/**
	* Returns all the commerce product options that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce product options that the user has permission to view
	*/
	public java.util.List<CommerceProductOption> filterFindByGroupId(
		long groupId);

	/**
	* Returns a range of all the commerce product options that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @return the range of matching commerce product options that the user has permission to view
	*/
	public java.util.List<CommerceProductOption> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product options that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product options that the user has permission to view
	*/
	public java.util.List<CommerceProductOption> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOption> orderByComparator);

	/**
	* Returns the commerce product options before and after the current commerce product option in the ordered set of commerce product options that the user has permission to view where groupId = &#63;.
	*
	* @param commerceProductOptionId the primary key of the current commerce product option
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product option
	* @throws NoSuchProductOptionException if a commerce product option with the primary key could not be found
	*/
	public CommerceProductOption[] filterFindByGroupId_PrevAndNext(
		long commerceProductOptionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOption> orderByComparator)
		throws NoSuchProductOptionException;

	/**
	* Removes all the commerce product options where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce product options where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce product options
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of commerce product options that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce product options that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the commerce product options where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce product options
	*/
	public java.util.List<CommerceProductOption> findByCompanyId(long companyId);

	/**
	* Returns a range of all the commerce product options where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @return the range of matching commerce product options
	*/
	public java.util.List<CommerceProductOption> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce product options where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce product options
	*/
	public java.util.List<CommerceProductOption> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOption> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product options where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce product options
	*/
	public java.util.List<CommerceProductOption> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOption> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce product option in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option
	* @throws NoSuchProductOptionException if a matching commerce product option could not be found
	*/
	public CommerceProductOption findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOption> orderByComparator)
		throws NoSuchProductOptionException;

	/**
	* Returns the first commerce product option in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce product option, or <code>null</code> if a matching commerce product option could not be found
	*/
	public CommerceProductOption fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOption> orderByComparator);

	/**
	* Returns the last commerce product option in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option
	* @throws NoSuchProductOptionException if a matching commerce product option could not be found
	*/
	public CommerceProductOption findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOption> orderByComparator)
		throws NoSuchProductOptionException;

	/**
	* Returns the last commerce product option in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce product option, or <code>null</code> if a matching commerce product option could not be found
	*/
	public CommerceProductOption fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOption> orderByComparator);

	/**
	* Returns the commerce product options before and after the current commerce product option in the ordered set where companyId = &#63;.
	*
	* @param commerceProductOptionId the primary key of the current commerce product option
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce product option
	* @throws NoSuchProductOptionException if a commerce product option with the primary key could not be found
	*/
	public CommerceProductOption[] findByCompanyId_PrevAndNext(
		long commerceProductOptionId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOption> orderByComparator)
		throws NoSuchProductOptionException;

	/**
	* Removes all the commerce product options where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of commerce product options where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce product options
	*/
	public int countByCompanyId(long companyId);

	/**
	* Caches the commerce product option in the entity cache if it is enabled.
	*
	* @param commerceProductOption the commerce product option
	*/
	public void cacheResult(CommerceProductOption commerceProductOption);

	/**
	* Caches the commerce product options in the entity cache if it is enabled.
	*
	* @param commerceProductOptions the commerce product options
	*/
	public void cacheResult(
		java.util.List<CommerceProductOption> commerceProductOptions);

	/**
	* Creates a new commerce product option with the primary key. Does not add the commerce product option to the database.
	*
	* @param commerceProductOptionId the primary key for the new commerce product option
	* @return the new commerce product option
	*/
	public CommerceProductOption create(long commerceProductOptionId);

	/**
	* Removes the commerce product option with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductOptionId the primary key of the commerce product option
	* @return the commerce product option that was removed
	* @throws NoSuchProductOptionException if a commerce product option with the primary key could not be found
	*/
	public CommerceProductOption remove(long commerceProductOptionId)
		throws NoSuchProductOptionException;

	public CommerceProductOption updateImpl(
		CommerceProductOption commerceProductOption);

	/**
	* Returns the commerce product option with the primary key or throws a {@link NoSuchProductOptionException} if it could not be found.
	*
	* @param commerceProductOptionId the primary key of the commerce product option
	* @return the commerce product option
	* @throws NoSuchProductOptionException if a commerce product option with the primary key could not be found
	*/
	public CommerceProductOption findByPrimaryKey(long commerceProductOptionId)
		throws NoSuchProductOptionException;

	/**
	* Returns the commerce product option with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceProductOptionId the primary key of the commerce product option
	* @return the commerce product option, or <code>null</code> if a commerce product option with the primary key could not be found
	*/
	public CommerceProductOption fetchByPrimaryKey(long commerceProductOptionId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceProductOption> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce product options.
	*
	* @return the commerce product options
	*/
	public java.util.List<CommerceProductOption> findAll();

	/**
	* Returns a range of all the commerce product options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @return the range of commerce product options
	*/
	public java.util.List<CommerceProductOption> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce product options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce product options
	*/
	public java.util.List<CommerceProductOption> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOption> orderByComparator);

	/**
	* Returns an ordered range of all the commerce product options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product options
	* @param end the upper bound of the range of commerce product options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce product options
	*/
	public java.util.List<CommerceProductOption> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceProductOption> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce product options from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce product options.
	*
	* @return the number of commerce product options
	*/
	public int countAll();
}