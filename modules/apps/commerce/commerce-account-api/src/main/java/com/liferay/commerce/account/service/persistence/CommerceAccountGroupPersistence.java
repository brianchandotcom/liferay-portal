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

package com.liferay.commerce.account.service.persistence;

import com.liferay.commerce.account.exception.NoSuchAccountGroupException;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce account group service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceAccountGroupUtil
 * @generated
 */
@ProviderType
public interface CommerceAccountGroupPersistence
	extends BasePersistence<CommerceAccountGroup> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceAccountGroupUtil} to access the commerce account group persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce account groups where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long commerceAccountGroupId);

	/**
	 * Returns a range of all the commerce account groups where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long commerceAccountGroupId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce account groups where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long commerceAccountGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccountGroup>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce account groups where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long commerceAccountGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccountGroup>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce account group in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	public CommerceAccountGroup findByCommerceAccountGroupIds_First(
			long commerceAccountGroupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException;

	/**
	 * Returns the first commerce account group in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	public CommerceAccountGroup fetchByCommerceAccountGroupIds_First(
		long commerceAccountGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccountGroup>
			orderByComparator);

	/**
	 * Returns the last commerce account group in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	public CommerceAccountGroup findByCommerceAccountGroupIds_Last(
			long commerceAccountGroupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException;

	/**
	 * Returns the last commerce account group in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	public CommerceAccountGroup fetchByCommerceAccountGroupIds_Last(
		long commerceAccountGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccountGroup>
			orderByComparator);

	/**
	 * Returns all the commerce account groups that the user has permission to view where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce account groups that the user has permission to view
	 */
	public java.util.List<CommerceAccountGroup>
		filterFindByCommerceAccountGroupIds(long commerceAccountGroupId);

	/**
	 * Returns a range of all the commerce account groups that the user has permission to view where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups that the user has permission to view
	 */
	public java.util.List<CommerceAccountGroup>
		filterFindByCommerceAccountGroupIds(
			long commerceAccountGroupId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce account groups that the user has permissions to view where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups that the user has permission to view
	 */
	public java.util.List<CommerceAccountGroup>
		filterFindByCommerceAccountGroupIds(
			long commerceAccountGroupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroup> orderByComparator);

	/**
	 * Returns all the commerce account groups that the user has permission to view where commerceAccountGroupId = any &#63;.
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @return the matching commerce account groups that the user has permission to view
	 */
	public java.util.List<CommerceAccountGroup>
		filterFindByCommerceAccountGroupIds(long[] commerceAccountGroupIds);

	/**
	 * Returns a range of all the commerce account groups that the user has permission to view where commerceAccountGroupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups that the user has permission to view
	 */
	public java.util.List<CommerceAccountGroup>
		filterFindByCommerceAccountGroupIds(
			long[] commerceAccountGroupIds, int start, int end);

	/**
	 * Returns an ordered range of all the commerce account groups that the user has permission to view where commerceAccountGroupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups that the user has permission to view
	 */
	public java.util.List<CommerceAccountGroup>
		filterFindByCommerceAccountGroupIds(
			long[] commerceAccountGroupIds, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroup> orderByComparator);

	/**
	 * Returns all the commerce account groups where commerceAccountGroupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @return the matching commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long[] commerceAccountGroupIds);

	/**
	 * Returns a range of all the commerce account groups where commerceAccountGroupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long[] commerceAccountGroupIds, int start, int end);

	/**
	 * Returns an ordered range of all the commerce account groups where commerceAccountGroupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long[] commerceAccountGroupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccountGroup>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce account groups where commerceAccountGroupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findByCommerceAccountGroupIds(
		long[] commerceAccountGroupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccountGroup>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce account groups where commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 */
	public void removeByCommerceAccountGroupIds(long commerceAccountGroupId);

	/**
	 * Returns the number of commerce account groups where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce account groups
	 */
	public int countByCommerceAccountGroupIds(long commerceAccountGroupId);

	/**
	 * Returns the number of commerce account groups where commerceAccountGroupId = any &#63;.
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @return the number of matching commerce account groups
	 */
	public int countByCommerceAccountGroupIds(long[] commerceAccountGroupIds);

	/**
	 * Returns the number of commerce account groups that the user has permission to view where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce account groups that the user has permission to view
	 */
	public int filterCountByCommerceAccountGroupIds(
		long commerceAccountGroupId);

	/**
	 * Returns the number of commerce account groups that the user has permission to view where commerceAccountGroupId = any &#63;.
	 *
	 * @param commerceAccountGroupIds the commerce account group IDs
	 * @return the number of matching commerce account groups that the user has permission to view
	 */
	public int filterCountByCommerceAccountGroupIds(
		long[] commerceAccountGroupIds);

	/**
	 * Returns all the commerce account groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the commerce account groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce account groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccountGroup>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce account groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccountGroup>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	public CommerceAccountGroup findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException;

	/**
	 * Returns the first commerce account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	public CommerceAccountGroup fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccountGroup>
			orderByComparator);

	/**
	 * Returns the last commerce account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	public CommerceAccountGroup findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException;

	/**
	 * Returns the last commerce account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	public CommerceAccountGroup fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccountGroup>
			orderByComparator);

	/**
	 * Returns the commerce account groups before and after the current commerce account group in the ordered set where companyId = &#63;.
	 *
	 * @param commerceAccountGroupId the primary key of the current commerce account group
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	public CommerceAccountGroup[] findByCompanyId_PrevAndNext(
			long commerceAccountGroupId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException;

	/**
	 * Returns all the commerce account groups that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce account groups that the user has permission to view
	 */
	public java.util.List<CommerceAccountGroup> filterFindByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the commerce account groups that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups that the user has permission to view
	 */
	public java.util.List<CommerceAccountGroup> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce account groups that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups that the user has permission to view
	 */
	public java.util.List<CommerceAccountGroup> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccountGroup>
			orderByComparator);

	/**
	 * Returns the commerce account groups before and after the current commerce account group in the ordered set of commerce account groups that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceAccountGroupId the primary key of the current commerce account group
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	public CommerceAccountGroup[] filterFindByCompanyId_PrevAndNext(
			long commerceAccountGroupId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException;

	/**
	 * Removes all the commerce account groups where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of commerce account groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce account groups
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of commerce account groups that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce account groups that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Returns all the commerce account groups where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findByC_T(
		long companyId, int type);

	/**
	 * Returns a range of all the commerce account groups where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findByC_T(
		long companyId, int type, int start, int end);

	/**
	 * Returns an ordered range of all the commerce account groups where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findByC_T(
		long companyId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccountGroup>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce account groups where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findByC_T(
		long companyId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccountGroup>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce account group in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	public CommerceAccountGroup findByC_T_First(
			long companyId, int type,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException;

	/**
	 * Returns the first commerce account group in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	public CommerceAccountGroup fetchByC_T_First(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccountGroup>
			orderByComparator);

	/**
	 * Returns the last commerce account group in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	public CommerceAccountGroup findByC_T_Last(
			long companyId, int type,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException;

	/**
	 * Returns the last commerce account group in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	public CommerceAccountGroup fetchByC_T_Last(
		long companyId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccountGroup>
			orderByComparator);

	/**
	 * Returns the commerce account groups before and after the current commerce account group in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param commerceAccountGroupId the primary key of the current commerce account group
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	public CommerceAccountGroup[] findByC_T_PrevAndNext(
			long commerceAccountGroupId, long companyId, int type,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException;

	/**
	 * Returns all the commerce account groups that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce account groups that the user has permission to view
	 */
	public java.util.List<CommerceAccountGroup> filterFindByC_T(
		long companyId, int type);

	/**
	 * Returns a range of all the commerce account groups that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of matching commerce account groups that the user has permission to view
	 */
	public java.util.List<CommerceAccountGroup> filterFindByC_T(
		long companyId, int type, int start, int end);

	/**
	 * Returns an ordered range of all the commerce account groups that the user has permissions to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account groups that the user has permission to view
	 */
	public java.util.List<CommerceAccountGroup> filterFindByC_T(
		long companyId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccountGroup>
			orderByComparator);

	/**
	 * Returns the commerce account groups before and after the current commerce account group in the ordered set of commerce account groups that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param commerceAccountGroupId the primary key of the current commerce account group
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	public CommerceAccountGroup[] filterFindByC_T_PrevAndNext(
			long commerceAccountGroupId, long companyId, int type,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceAccountGroup> orderByComparator)
		throws NoSuchAccountGroupException;

	/**
	 * Removes all the commerce account groups where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public void removeByC_T(long companyId, int type);

	/**
	 * Returns the number of commerce account groups where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce account groups
	 */
	public int countByC_T(long companyId, int type);

	/**
	 * Returns the number of commerce account groups that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce account groups that the user has permission to view
	 */
	public int filterCountByC_T(long companyId, int type);

	/**
	 * Returns the commerce account group where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchAccountGroupException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce account group
	 * @throws NoSuchAccountGroupException if a matching commerce account group could not be found
	 */
	public CommerceAccountGroup findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchAccountGroupException;

	/**
	 * Returns the commerce account group where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	public CommerceAccountGroup fetchByC_ERC(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the commerce account group where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce account group, or <code>null</code> if a matching commerce account group could not be found
	 */
	public CommerceAccountGroup fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the commerce account group where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce account group that was removed
	 */
	public CommerceAccountGroup removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchAccountGroupException;

	/**
	 * Returns the number of commerce account groups where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce account groups
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

	/**
	 * Caches the commerce account group in the entity cache if it is enabled.
	 *
	 * @param commerceAccountGroup the commerce account group
	 */
	public void cacheResult(CommerceAccountGroup commerceAccountGroup);

	/**
	 * Caches the commerce account groups in the entity cache if it is enabled.
	 *
	 * @param commerceAccountGroups the commerce account groups
	 */
	public void cacheResult(
		java.util.List<CommerceAccountGroup> commerceAccountGroups);

	/**
	 * Creates a new commerce account group with the primary key. Does not add the commerce account group to the database.
	 *
	 * @param commerceAccountGroupId the primary key for the new commerce account group
	 * @return the new commerce account group
	 */
	public CommerceAccountGroup create(long commerceAccountGroupId);

	/**
	 * Removes the commerce account group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAccountGroupId the primary key of the commerce account group
	 * @return the commerce account group that was removed
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	public CommerceAccountGroup remove(long commerceAccountGroupId)
		throws NoSuchAccountGroupException;

	public CommerceAccountGroup updateImpl(
		CommerceAccountGroup commerceAccountGroup);

	/**
	 * Returns the commerce account group with the primary key or throws a <code>NoSuchAccountGroupException</code> if it could not be found.
	 *
	 * @param commerceAccountGroupId the primary key of the commerce account group
	 * @return the commerce account group
	 * @throws NoSuchAccountGroupException if a commerce account group with the primary key could not be found
	 */
	public CommerceAccountGroup findByPrimaryKey(long commerceAccountGroupId)
		throws NoSuchAccountGroupException;

	/**
	 * Returns the commerce account group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceAccountGroupId the primary key of the commerce account group
	 * @return the commerce account group, or <code>null</code> if a commerce account group with the primary key could not be found
	 */
	public CommerceAccountGroup fetchByPrimaryKey(long commerceAccountGroupId);

	/**
	 * Returns all the commerce account groups.
	 *
	 * @return the commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findAll();

	/**
	 * Returns a range of all the commerce account groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @return the range of commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce account groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccountGroup>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce account groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account groups
	 * @param end the upper bound of the range of commerce account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce account groups
	 */
	public java.util.List<CommerceAccountGroup> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceAccountGroup>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce account groups from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce account groups.
	 *
	 * @return the number of commerce account groups
	 */
	public int countAll();

}