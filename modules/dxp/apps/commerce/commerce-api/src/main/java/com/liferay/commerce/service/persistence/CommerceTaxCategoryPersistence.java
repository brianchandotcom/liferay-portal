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

import com.liferay.commerce.exception.NoSuchTaxCategoryException;
import com.liferay.commerce.model.CommerceTaxCategory;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce tax category service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommerceTaxCategoryPersistenceImpl
 * @see CommerceTaxCategoryUtil
 * @generated
 */
@ProviderType
public interface CommerceTaxCategoryPersistence extends BasePersistence<CommerceTaxCategory> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceTaxCategoryUtil} to access the commerce tax category persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce tax categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce tax categories
	*/
	public java.util.List<CommerceTaxCategory> findByGroupId(long groupId);

	/**
	* Returns a range of all the commerce tax categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tax categories
	* @param end the upper bound of the range of commerce tax categories (not inclusive)
	* @return the range of matching commerce tax categories
	*/
	public java.util.List<CommerceTaxCategory> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce tax categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tax categories
	* @param end the upper bound of the range of commerce tax categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tax categories
	*/
	public java.util.List<CommerceTaxCategory> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategory> orderByComparator);

	/**
	* Returns an ordered range of all the commerce tax categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tax categories
	* @param end the upper bound of the range of commerce tax categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tax categories
	*/
	public java.util.List<CommerceTaxCategory> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategory> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce tax category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tax category
	* @throws NoSuchTaxCategoryException if a matching commerce tax category could not be found
	*/
	public CommerceTaxCategory findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategory> orderByComparator)
		throws NoSuchTaxCategoryException;

	/**
	* Returns the first commerce tax category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tax category, or <code>null</code> if a matching commerce tax category could not be found
	*/
	public CommerceTaxCategory fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategory> orderByComparator);

	/**
	* Returns the last commerce tax category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tax category
	* @throws NoSuchTaxCategoryException if a matching commerce tax category could not be found
	*/
	public CommerceTaxCategory findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategory> orderByComparator)
		throws NoSuchTaxCategoryException;

	/**
	* Returns the last commerce tax category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tax category, or <code>null</code> if a matching commerce tax category could not be found
	*/
	public CommerceTaxCategory fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategory> orderByComparator);

	/**
	* Returns the commerce tax categories before and after the current commerce tax category in the ordered set where groupId = &#63;.
	*
	* @param commerceTaxCategoryId the primary key of the current commerce tax category
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tax category
	* @throws NoSuchTaxCategoryException if a commerce tax category with the primary key could not be found
	*/
	public CommerceTaxCategory[] findByGroupId_PrevAndNext(
		long commerceTaxCategoryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategory> orderByComparator)
		throws NoSuchTaxCategoryException;

	/**
	* Removes all the commerce tax categories where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce tax categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce tax categories
	*/
	public int countByGroupId(long groupId);

	/**
	* Caches the commerce tax category in the entity cache if it is enabled.
	*
	* @param commerceTaxCategory the commerce tax category
	*/
	public void cacheResult(CommerceTaxCategory commerceTaxCategory);

	/**
	* Caches the commerce tax categories in the entity cache if it is enabled.
	*
	* @param commerceTaxCategories the commerce tax categories
	*/
	public void cacheResult(
		java.util.List<CommerceTaxCategory> commerceTaxCategories);

	/**
	* Creates a new commerce tax category with the primary key. Does not add the commerce tax category to the database.
	*
	* @param commerceTaxCategoryId the primary key for the new commerce tax category
	* @return the new commerce tax category
	*/
	public CommerceTaxCategory create(long commerceTaxCategoryId);

	/**
	* Removes the commerce tax category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategoryId the primary key of the commerce tax category
	* @return the commerce tax category that was removed
	* @throws NoSuchTaxCategoryException if a commerce tax category with the primary key could not be found
	*/
	public CommerceTaxCategory remove(long commerceTaxCategoryId)
		throws NoSuchTaxCategoryException;

	public CommerceTaxCategory updateImpl(
		CommerceTaxCategory commerceTaxCategory);

	/**
	* Returns the commerce tax category with the primary key or throws a {@link NoSuchTaxCategoryException} if it could not be found.
	*
	* @param commerceTaxCategoryId the primary key of the commerce tax category
	* @return the commerce tax category
	* @throws NoSuchTaxCategoryException if a commerce tax category with the primary key could not be found
	*/
	public CommerceTaxCategory findByPrimaryKey(long commerceTaxCategoryId)
		throws NoSuchTaxCategoryException;

	/**
	* Returns the commerce tax category with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceTaxCategoryId the primary key of the commerce tax category
	* @return the commerce tax category, or <code>null</code> if a commerce tax category with the primary key could not be found
	*/
	public CommerceTaxCategory fetchByPrimaryKey(long commerceTaxCategoryId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceTaxCategory> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce tax categories.
	*
	* @return the commerce tax categories
	*/
	public java.util.List<CommerceTaxCategory> findAll();

	/**
	* Returns a range of all the commerce tax categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax categories
	* @param end the upper bound of the range of commerce tax categories (not inclusive)
	* @return the range of commerce tax categories
	*/
	public java.util.List<CommerceTaxCategory> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce tax categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax categories
	* @param end the upper bound of the range of commerce tax categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce tax categories
	*/
	public java.util.List<CommerceTaxCategory> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategory> orderByComparator);

	/**
	* Returns an ordered range of all the commerce tax categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTaxCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax categories
	* @param end the upper bound of the range of commerce tax categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce tax categories
	*/
	public java.util.List<CommerceTaxCategory> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTaxCategory> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce tax categories from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce tax categories.
	*
	* @return the number of commerce tax categories
	*/
	public int countAll();
}