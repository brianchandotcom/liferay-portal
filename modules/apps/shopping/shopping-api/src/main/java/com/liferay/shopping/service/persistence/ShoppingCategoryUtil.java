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

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import com.liferay.shopping.model.ShoppingCategory;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the shopping category service. This utility wraps {@link com.liferay.shopping.service.persistence.impl.ShoppingCategoryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCategoryPersistence
 * @see com.liferay.shopping.service.persistence.impl.ShoppingCategoryPersistenceImpl
 * @generated
 */
@ProviderType
public class ShoppingCategoryUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(ShoppingCategory shoppingCategory) {
		getPersistence().clearCache(shoppingCategory);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ShoppingCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ShoppingCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ShoppingCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ShoppingCategory> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ShoppingCategory update(ShoppingCategory shoppingCategory) {
		return getPersistence().update(shoppingCategory);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ShoppingCategory update(ShoppingCategory shoppingCategory,
		ServiceContext serviceContext) {
		return getPersistence().update(shoppingCategory, serviceContext);
	}

	/**
	* Returns all the shopping categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching shopping categories
	*/
	public static List<ShoppingCategory> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

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
	public static List<ShoppingCategory> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

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
	public static List<ShoppingCategory> findByGroupId(long groupId, int start,
		int end, OrderByComparator<ShoppingCategory> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

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
	public static List<ShoppingCategory> findByGroupId(long groupId, int start,
		int end, OrderByComparator<ShoppingCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first shopping category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping category
	* @throws com.liferay.shopping.NoSuchCategoryException if a matching shopping category could not be found
	*/
	public static ShoppingCategory findByGroupId_First(long groupId,
		OrderByComparator<ShoppingCategory> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchCategoryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first shopping category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping category, or <code>null</code> if a matching shopping category could not be found
	*/
	public static ShoppingCategory fetchByGroupId_First(long groupId,
		OrderByComparator<ShoppingCategory> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last shopping category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping category
	* @throws com.liferay.shopping.NoSuchCategoryException if a matching shopping category could not be found
	*/
	public static ShoppingCategory findByGroupId_Last(long groupId,
		OrderByComparator<ShoppingCategory> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchCategoryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last shopping category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping category, or <code>null</code> if a matching shopping category could not be found
	*/
	public static ShoppingCategory fetchByGroupId_Last(long groupId,
		OrderByComparator<ShoppingCategory> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the shopping categories before and after the current shopping category in the ordered set where groupId = &#63;.
	*
	* @param categoryId the primary key of the current shopping category
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping category
	* @throws com.liferay.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	*/
	public static ShoppingCategory[] findByGroupId_PrevAndNext(
		long categoryId, long groupId,
		OrderByComparator<ShoppingCategory> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchCategoryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(categoryId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the shopping categories that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching shopping categories that the user has permission to view
	*/
	public static List<ShoppingCategory> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

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
	public static List<ShoppingCategory> filterFindByGroupId(long groupId,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

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
	public static List<ShoppingCategory> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<ShoppingCategory> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the shopping categories before and after the current shopping category in the ordered set of shopping categories that the user has permission to view where groupId = &#63;.
	*
	* @param categoryId the primary key of the current shopping category
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping category
	* @throws com.liferay.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	*/
	public static ShoppingCategory[] filterFindByGroupId_PrevAndNext(
		long categoryId, long groupId,
		OrderByComparator<ShoppingCategory> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchCategoryException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(categoryId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the shopping categories where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of shopping categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching shopping categories
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of shopping categories that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching shopping categories that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the shopping categories where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the matching shopping categories
	*/
	public static List<ShoppingCategory> findByG_P(long groupId,
		long parentCategoryId) {
		return getPersistence().findByG_P(groupId, parentCategoryId);
	}

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
	public static List<ShoppingCategory> findByG_P(long groupId,
		long parentCategoryId, int start, int end) {
		return getPersistence().findByG_P(groupId, parentCategoryId, start, end);
	}

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
	public static List<ShoppingCategory> findByG_P(long groupId,
		long parentCategoryId, int start, int end,
		OrderByComparator<ShoppingCategory> orderByComparator) {
		return getPersistence()
				   .findByG_P(groupId, parentCategoryId, start, end,
			orderByComparator);
	}

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
	public static List<ShoppingCategory> findByG_P(long groupId,
		long parentCategoryId, int start, int end,
		OrderByComparator<ShoppingCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_P(groupId, parentCategoryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first shopping category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping category
	* @throws com.liferay.shopping.NoSuchCategoryException if a matching shopping category could not be found
	*/
	public static ShoppingCategory findByG_P_First(long groupId,
		long parentCategoryId,
		OrderByComparator<ShoppingCategory> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchCategoryException {
		return getPersistence()
				   .findByG_P_First(groupId, parentCategoryId, orderByComparator);
	}

	/**
	* Returns the first shopping category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping category, or <code>null</code> if a matching shopping category could not be found
	*/
	public static ShoppingCategory fetchByG_P_First(long groupId,
		long parentCategoryId,
		OrderByComparator<ShoppingCategory> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_First(groupId, parentCategoryId,
			orderByComparator);
	}

	/**
	* Returns the last shopping category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping category
	* @throws com.liferay.shopping.NoSuchCategoryException if a matching shopping category could not be found
	*/
	public static ShoppingCategory findByG_P_Last(long groupId,
		long parentCategoryId,
		OrderByComparator<ShoppingCategory> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchCategoryException {
		return getPersistence()
				   .findByG_P_Last(groupId, parentCategoryId, orderByComparator);
	}

	/**
	* Returns the last shopping category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping category, or <code>null</code> if a matching shopping category could not be found
	*/
	public static ShoppingCategory fetchByG_P_Last(long groupId,
		long parentCategoryId,
		OrderByComparator<ShoppingCategory> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_Last(groupId, parentCategoryId, orderByComparator);
	}

	/**
	* Returns the shopping categories before and after the current shopping category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param categoryId the primary key of the current shopping category
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping category
	* @throws com.liferay.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	*/
	public static ShoppingCategory[] findByG_P_PrevAndNext(long categoryId,
		long groupId, long parentCategoryId,
		OrderByComparator<ShoppingCategory> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchCategoryException {
		return getPersistence()
				   .findByG_P_PrevAndNext(categoryId, groupId,
			parentCategoryId, orderByComparator);
	}

	/**
	* Returns all the shopping categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the matching shopping categories that the user has permission to view
	*/
	public static List<ShoppingCategory> filterFindByG_P(long groupId,
		long parentCategoryId) {
		return getPersistence().filterFindByG_P(groupId, parentCategoryId);
	}

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
	public static List<ShoppingCategory> filterFindByG_P(long groupId,
		long parentCategoryId, int start, int end) {
		return getPersistence()
				   .filterFindByG_P(groupId, parentCategoryId, start, end);
	}

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
	public static List<ShoppingCategory> filterFindByG_P(long groupId,
		long parentCategoryId, int start, int end,
		OrderByComparator<ShoppingCategory> orderByComparator) {
		return getPersistence()
				   .filterFindByG_P(groupId, parentCategoryId, start, end,
			orderByComparator);
	}

	/**
	* Returns the shopping categories before and after the current shopping category in the ordered set of shopping categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param categoryId the primary key of the current shopping category
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping category
	* @throws com.liferay.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	*/
	public static ShoppingCategory[] filterFindByG_P_PrevAndNext(
		long categoryId, long groupId, long parentCategoryId,
		OrderByComparator<ShoppingCategory> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchCategoryException {
		return getPersistence()
				   .filterFindByG_P_PrevAndNext(categoryId, groupId,
			parentCategoryId, orderByComparator);
	}

	/**
	* Removes all the shopping categories where groupId = &#63; and parentCategoryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	*/
	public static void removeByG_P(long groupId, long parentCategoryId) {
		getPersistence().removeByG_P(groupId, parentCategoryId);
	}

	/**
	* Returns the number of shopping categories where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the number of matching shopping categories
	*/
	public static int countByG_P(long groupId, long parentCategoryId) {
		return getPersistence().countByG_P(groupId, parentCategoryId);
	}

	/**
	* Returns the number of shopping categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the number of matching shopping categories that the user has permission to view
	*/
	public static int filterCountByG_P(long groupId, long parentCategoryId) {
		return getPersistence().filterCountByG_P(groupId, parentCategoryId);
	}

	/**
	* Returns the shopping category where groupId = &#63; and name = &#63; or throws a {@link com.liferay.shopping.NoSuchCategoryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching shopping category
	* @throws com.liferay.shopping.NoSuchCategoryException if a matching shopping category could not be found
	*/
	public static ShoppingCategory findByG_N(long groupId, java.lang.String name)
		throws com.liferay.shopping.exception.NoSuchCategoryException {
		return getPersistence().findByG_N(groupId, name);
	}

	/**
	* Returns the shopping category where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching shopping category, or <code>null</code> if a matching shopping category could not be found
	*/
	public static ShoppingCategory fetchByG_N(long groupId,
		java.lang.String name) {
		return getPersistence().fetchByG_N(groupId, name);
	}

	/**
	* Returns the shopping category where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching shopping category, or <code>null</code> if a matching shopping category could not be found
	*/
	public static ShoppingCategory fetchByG_N(long groupId,
		java.lang.String name, boolean retrieveFromCache) {
		return getPersistence().fetchByG_N(groupId, name, retrieveFromCache);
	}

	/**
	* Removes the shopping category where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the shopping category that was removed
	*/
	public static ShoppingCategory removeByG_N(long groupId,
		java.lang.String name)
		throws com.liferay.shopping.exception.NoSuchCategoryException {
		return getPersistence().removeByG_N(groupId, name);
	}

	/**
	* Returns the number of shopping categories where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching shopping categories
	*/
	public static int countByG_N(long groupId, java.lang.String name) {
		return getPersistence().countByG_N(groupId, name);
	}

	/**
	* Caches the shopping category in the entity cache if it is enabled.
	*
	* @param shoppingCategory the shopping category
	*/
	public static void cacheResult(ShoppingCategory shoppingCategory) {
		getPersistence().cacheResult(shoppingCategory);
	}

	/**
	* Caches the shopping categories in the entity cache if it is enabled.
	*
	* @param shoppingCategories the shopping categories
	*/
	public static void cacheResult(List<ShoppingCategory> shoppingCategories) {
		getPersistence().cacheResult(shoppingCategories);
	}

	/**
	* Creates a new shopping category with the primary key. Does not add the shopping category to the database.
	*
	* @param categoryId the primary key for the new shopping category
	* @return the new shopping category
	*/
	public static ShoppingCategory create(long categoryId) {
		return getPersistence().create(categoryId);
	}

	/**
	* Removes the shopping category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryId the primary key of the shopping category
	* @return the shopping category that was removed
	* @throws com.liferay.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	*/
	public static ShoppingCategory remove(long categoryId)
		throws com.liferay.shopping.exception.NoSuchCategoryException {
		return getPersistence().remove(categoryId);
	}

	public static ShoppingCategory updateImpl(ShoppingCategory shoppingCategory) {
		return getPersistence().updateImpl(shoppingCategory);
	}

	/**
	* Returns the shopping category with the primary key or throws a {@link com.liferay.shopping.NoSuchCategoryException} if it could not be found.
	*
	* @param categoryId the primary key of the shopping category
	* @return the shopping category
	* @throws com.liferay.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	*/
	public static ShoppingCategory findByPrimaryKey(long categoryId)
		throws com.liferay.shopping.exception.NoSuchCategoryException {
		return getPersistence().findByPrimaryKey(categoryId);
	}

	/**
	* Returns the shopping category with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param categoryId the primary key of the shopping category
	* @return the shopping category, or <code>null</code> if a shopping category with the primary key could not be found
	*/
	public static ShoppingCategory fetchByPrimaryKey(long categoryId) {
		return getPersistence().fetchByPrimaryKey(categoryId);
	}

	public static java.util.Map<java.io.Serializable, ShoppingCategory> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the shopping categories.
	*
	* @return the shopping categories
	*/
	public static List<ShoppingCategory> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<ShoppingCategory> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<ShoppingCategory> findAll(int start, int end,
		OrderByComparator<ShoppingCategory> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<ShoppingCategory> findAll(int start, int end,
		OrderByComparator<ShoppingCategory> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the shopping categories from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of shopping categories.
	*
	* @return the number of shopping categories
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ShoppingCategoryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ShoppingCategoryPersistence, ShoppingCategoryPersistence> _serviceTracker =
		ServiceTrackerFactory.open(ShoppingCategoryPersistence.class);
}