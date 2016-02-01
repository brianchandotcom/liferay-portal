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

package com.liferay.shopping.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.shopping.exception.NoSuchCategoryException;
import com.liferay.shopping.model.ShoppingCategory;

/**
 * The persistence interface for the shopping category service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.shopping.service.persistence.impl.ShoppingCategoryPersistenceImpl
 * @see ShoppingCategoryUtil
 * @generated
 */
@ProviderType
public interface ShoppingCategoryPersistence extends BasePersistence<ShoppingCategory> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ShoppingCategoryUtil} to access the shopping category persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the shopping categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching shopping categories
	*/
	public java.util.List<ShoppingCategory> findByGroupId(long groupId);

	/**
	* Returns a range of all the shopping categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @return the range of matching shopping categories
	*/
	public java.util.List<ShoppingCategory> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the shopping categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping categories
	*/
	public java.util.List<ShoppingCategory> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator);

	/**
	* Returns an ordered range of all the shopping categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching shopping categories
	*/
	public java.util.List<ShoppingCategory> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first shopping category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping category
	* @throws NoSuchCategoryException if a matching shopping category could not be found
	*/
	public ShoppingCategory findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator)
		throws NoSuchCategoryException;

	/**
	* Returns the first shopping category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping category, or <code>null</code> if a matching shopping category could not be found
	*/
	public ShoppingCategory fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator);

	/**
	* Returns the last shopping category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping category
	* @throws NoSuchCategoryException if a matching shopping category could not be found
	*/
	public ShoppingCategory findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator)
		throws NoSuchCategoryException;

	/**
	* Returns the last shopping category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping category, or <code>null</code> if a matching shopping category could not be found
	*/
	public ShoppingCategory fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator);

	/**
	* Returns the shopping categories before and after the current shopping category in the ordered set where groupId = &#63;.
	*
	* @param categoryId the primary key of the current shopping category
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping category
	* @throws NoSuchCategoryException if a shopping category with the primary key could not be found
	*/
	public ShoppingCategory[] findByGroupId_PrevAndNext(long categoryId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator)
		throws NoSuchCategoryException;

	/**
	* Returns all the shopping categories that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching shopping categories that the user has permission to view
	*/
	public java.util.List<ShoppingCategory> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the shopping categories that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @return the range of matching shopping categories that the user has permission to view
	*/
	public java.util.List<ShoppingCategory> filterFindByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the shopping categories that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping categories that the user has permission to view
	*/
	public java.util.List<ShoppingCategory> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator);

	/**
	* Returns the shopping categories before and after the current shopping category in the ordered set of shopping categories that the user has permission to view where groupId = &#63;.
	*
	* @param categoryId the primary key of the current shopping category
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping category
	* @throws NoSuchCategoryException if a shopping category with the primary key could not be found
	*/
	public ShoppingCategory[] filterFindByGroupId_PrevAndNext(long categoryId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator)
		throws NoSuchCategoryException;

	/**
	* Removes all the shopping categories where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of shopping categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching shopping categories
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of shopping categories that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching shopping categories that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the shopping categories where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the matching shopping categories
	*/
	public java.util.List<ShoppingCategory> findByG_P(long groupId,
		long parentCategoryId);

	/**
	* Returns a range of all the shopping categories where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @return the range of matching shopping categories
	*/
	public java.util.List<ShoppingCategory> findByG_P(long groupId,
		long parentCategoryId, int start, int end);

	/**
	* Returns an ordered range of all the shopping categories where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping categories
	*/
	public java.util.List<ShoppingCategory> findByG_P(long groupId,
		long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator);

	/**
	* Returns an ordered range of all the shopping categories where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching shopping categories
	*/
	public java.util.List<ShoppingCategory> findByG_P(long groupId,
		long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first shopping category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping category
	* @throws NoSuchCategoryException if a matching shopping category could not be found
	*/
	public ShoppingCategory findByG_P_First(long groupId,
		long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator)
		throws NoSuchCategoryException;

	/**
	* Returns the first shopping category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping category, or <code>null</code> if a matching shopping category could not be found
	*/
	public ShoppingCategory fetchByG_P_First(long groupId,
		long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator);

	/**
	* Returns the last shopping category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping category
	* @throws NoSuchCategoryException if a matching shopping category could not be found
	*/
	public ShoppingCategory findByG_P_Last(long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator)
		throws NoSuchCategoryException;

	/**
	* Returns the last shopping category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping category, or <code>null</code> if a matching shopping category could not be found
	*/
	public ShoppingCategory fetchByG_P_Last(long groupId,
		long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator);

	/**
	* Returns the shopping categories before and after the current shopping category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param categoryId the primary key of the current shopping category
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping category
	* @throws NoSuchCategoryException if a shopping category with the primary key could not be found
	*/
	public ShoppingCategory[] findByG_P_PrevAndNext(long categoryId,
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator)
		throws NoSuchCategoryException;

	/**
	* Returns all the shopping categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the matching shopping categories that the user has permission to view
	*/
	public java.util.List<ShoppingCategory> filterFindByG_P(long groupId,
		long parentCategoryId);

	/**
	* Returns a range of all the shopping categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @return the range of matching shopping categories that the user has permission to view
	*/
	public java.util.List<ShoppingCategory> filterFindByG_P(long groupId,
		long parentCategoryId, int start, int end);

	/**
	* Returns an ordered range of all the shopping categories that the user has permissions to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping categories that the user has permission to view
	*/
	public java.util.List<ShoppingCategory> filterFindByG_P(long groupId,
		long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator);

	/**
	* Returns the shopping categories before and after the current shopping category in the ordered set of shopping categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param categoryId the primary key of the current shopping category
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping category
	* @throws NoSuchCategoryException if a shopping category with the primary key could not be found
	*/
	public ShoppingCategory[] filterFindByG_P_PrevAndNext(long categoryId,
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator)
		throws NoSuchCategoryException;

	/**
	* Removes all the shopping categories where groupId = &#63; and parentCategoryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	*/
	public void removeByG_P(long groupId, long parentCategoryId);

	/**
	* Returns the number of shopping categories where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the number of matching shopping categories
	*/
	public int countByG_P(long groupId, long parentCategoryId);

	/**
	* Returns the number of shopping categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the number of matching shopping categories that the user has permission to view
	*/
	public int filterCountByG_P(long groupId, long parentCategoryId);

	/**
	* Returns the shopping category where groupId = &#63; and name = &#63; or throws a {@link NoSuchCategoryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching shopping category
	* @throws NoSuchCategoryException if a matching shopping category could not be found
	*/
	public ShoppingCategory findByG_N(long groupId, java.lang.String name)
		throws NoSuchCategoryException;

	/**
	* Returns the shopping category where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching shopping category, or <code>null</code> if a matching shopping category could not be found
	*/
	public ShoppingCategory fetchByG_N(long groupId, java.lang.String name);

	/**
	* Returns the shopping category where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching shopping category, or <code>null</code> if a matching shopping category could not be found
	*/
	public ShoppingCategory fetchByG_N(long groupId, java.lang.String name,
		boolean retrieveFromCache);

	/**
	* Removes the shopping category where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the shopping category that was removed
	*/
	public ShoppingCategory removeByG_N(long groupId, java.lang.String name)
		throws NoSuchCategoryException;

	/**
	* Returns the number of shopping categories where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching shopping categories
	*/
	public int countByG_N(long groupId, java.lang.String name);

	/**
	* Caches the shopping category in the entity cache if it is enabled.
	*
	* @param shoppingCategory the shopping category
	*/
	public void cacheResult(ShoppingCategory shoppingCategory);

	/**
	* Caches the shopping categories in the entity cache if it is enabled.
	*
	* @param shoppingCategories the shopping categories
	*/
	public void cacheResult(java.util.List<ShoppingCategory> shoppingCategories);

	/**
	* Creates a new shopping category with the primary key. Does not add the shopping category to the database.
	*
	* @param categoryId the primary key for the new shopping category
	* @return the new shopping category
	*/
	public ShoppingCategory create(long categoryId);

	/**
	* Removes the shopping category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryId the primary key of the shopping category
	* @return the shopping category that was removed
	* @throws NoSuchCategoryException if a shopping category with the primary key could not be found
	*/
	public ShoppingCategory remove(long categoryId)
		throws NoSuchCategoryException;

	public ShoppingCategory updateImpl(ShoppingCategory shoppingCategory);

	/**
	* Returns the shopping category with the primary key or throws a {@link NoSuchCategoryException} if it could not be found.
	*
	* @param categoryId the primary key of the shopping category
	* @return the shopping category
	* @throws NoSuchCategoryException if a shopping category with the primary key could not be found
	*/
	public ShoppingCategory findByPrimaryKey(long categoryId)
		throws NoSuchCategoryException;

	/**
	* Returns the shopping category with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param categoryId the primary key of the shopping category
	* @return the shopping category, or <code>null</code> if a shopping category with the primary key could not be found
	*/
	public ShoppingCategory fetchByPrimaryKey(long categoryId);

	@Override
	public java.util.Map<java.io.Serializable, ShoppingCategory> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the shopping categories.
	*
	* @return the shopping categories
	*/
	public java.util.List<ShoppingCategory> findAll();

	/**
	* Returns a range of all the shopping categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @return the range of shopping categories
	*/
	public java.util.List<ShoppingCategory> findAll(int start, int end);

	/**
	* Returns an ordered range of all the shopping categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of shopping categories
	*/
	public java.util.List<ShoppingCategory> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator);

	/**
	* Returns an ordered range of all the shopping categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of shopping categories
	*/
	public java.util.List<ShoppingCategory> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCategory> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the shopping categories from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of shopping categories.
	*
	* @return the number of shopping categories
	*/
	public int countAll();
}