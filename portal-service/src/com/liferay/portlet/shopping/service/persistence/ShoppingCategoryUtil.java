/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.shopping.model.ShoppingCategory;

import java.util.List;

/**
 * The persistence utility for the shopping category service. This utility wraps {@link ShoppingCategoryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCategoryPersistence
 * @see ShoppingCategoryPersistenceImpl
 * @generated
 */
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
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ShoppingCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ShoppingCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ShoppingCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ShoppingCategory update(ShoppingCategory shoppingCategory)
		throws SystemException {
		return getPersistence().update(shoppingCategory);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ShoppingCategory update(ShoppingCategory shoppingCategory,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(shoppingCategory, serviceContext);
	}

	/**
	* Returns all the shopping categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching shopping categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the shopping categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @return the range of matching shopping categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the shopping categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first shopping category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping category
	* @throws com.liferay.portlet.shopping.NoSuchCategoryException if a matching shopping category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCategory findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCategoryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first shopping category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping category, or <code>null</code> if a matching shopping category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCategory fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last shopping category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping category
	* @throws com.liferay.portlet.shopping.NoSuchCategoryException if a matching shopping category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCategory findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCategoryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last shopping category in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping category, or <code>null</code> if a matching shopping category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCategory fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the shopping categories before and after the current shopping category in the ordered set where groupId = &#63;.
	*
	* @param categoryId the primary key of the current shopping category
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping category
	* @throws com.liferay.portlet.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCategory[] findByGroupId_PrevAndNext(
		long categoryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCategoryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(categoryId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the shopping categories that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching shopping categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the shopping categories that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @return the range of matching shopping categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the shopping categories that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.portlet.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCategory[] filterFindByGroupId_PrevAndNext(
		long categoryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCategoryException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(categoryId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the shopping categories where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of shopping categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching shopping categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of shopping categories that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching shopping categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the shopping categories where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the matching shopping categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> findByG_P(
		long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, parentCategoryId);
	}

	/**
	* Returns a range of all the shopping categories where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @return the range of matching shopping categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> findByG_P(
		long groupId, long parentCategoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, parentCategoryId, start, end);
	}

	/**
	* Returns an ordered range of all the shopping categories where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> findByG_P(
		long groupId, long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P(groupId, parentCategoryId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first shopping category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping category
	* @throws com.liferay.portlet.shopping.NoSuchCategoryException if a matching shopping category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCategory findByG_P_First(
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCategoryException {
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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCategory fetchByG_P_First(
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.portlet.shopping.NoSuchCategoryException if a matching shopping category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCategory findByG_P_Last(
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCategoryException {
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
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCategory fetchByG_P_Last(
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.portlet.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCategory[] findByG_P_PrevAndNext(
		long categoryId, long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCategoryException {
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
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> filterFindByG_P(
		long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_P(groupId, parentCategoryId);
	}

	/**
	* Returns a range of all the shopping categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @return the range of matching shopping categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> filterFindByG_P(
		long groupId, long parentCategoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P(groupId, parentCategoryId, start, end);
	}

	/**
	* Returns an ordered range of all the shopping categories that the user has permissions to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> filterFindByG_P(
		long groupId, long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws com.liferay.portlet.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCategory[] filterFindByG_P_PrevAndNext(
		long categoryId, long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCategoryException {
		return getPersistence()
				   .filterFindByG_P_PrevAndNext(categoryId, groupId,
			parentCategoryId, orderByComparator);
	}

	/**
	* Removes all the shopping categories where groupId = &#63; and parentCategoryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_P(long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P(groupId, parentCategoryId);
	}

	/**
	* Returns the number of shopping categories where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the number of matching shopping categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_P(long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P(groupId, parentCategoryId);
	}

	/**
	* Returns the number of shopping categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the number of matching shopping categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_P(long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_P(groupId, parentCategoryId);
	}

	/**
	* Caches the shopping category in the entity cache if it is enabled.
	*
	* @param shoppingCategory the shopping category
	*/
	public static void cacheResult(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory) {
		getPersistence().cacheResult(shoppingCategory);
	}

	/**
	* Caches the shopping categories in the entity cache if it is enabled.
	*
	* @param shoppingCategories the shopping categories
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> shoppingCategories) {
		getPersistence().cacheResult(shoppingCategories);
	}

	/**
	* Creates a new shopping category with the primary key. Does not add the shopping category to the database.
	*
	* @param categoryId the primary key for the new shopping category
	* @return the new shopping category
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCategory create(
		long categoryId) {
		return getPersistence().create(categoryId);
	}

	/**
	* Removes the shopping category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryId the primary key of the shopping category
	* @return the shopping category that was removed
	* @throws com.liferay.portlet.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCategory remove(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCategoryException {
		return getPersistence().remove(categoryId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory updateImpl(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(shoppingCategory);
	}

	/**
	* Returns the shopping category with the primary key or throws a {@link com.liferay.portlet.shopping.NoSuchCategoryException} if it could not be found.
	*
	* @param categoryId the primary key of the shopping category
	* @return the shopping category
	* @throws com.liferay.portlet.shopping.NoSuchCategoryException if a shopping category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCategory findByPrimaryKey(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCategoryException {
		return getPersistence().findByPrimaryKey(categoryId);
	}

	/**
	* Returns the shopping category with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param categoryId the primary key of the shopping category
	* @return the shopping category, or <code>null</code> if a shopping category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shopping.model.ShoppingCategory fetchByPrimaryKey(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(categoryId);
	}

	/**
	* Returns all the shopping categories.
	*
	* @return the shopping categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the shopping categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @return the range of shopping categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the shopping categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.shopping.model.impl.ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of shopping categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the shopping categories from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of shopping categories.
	*
	* @return the number of shopping categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ShoppingCategoryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ShoppingCategoryPersistence)PortalBeanLocatorUtil.locate(ShoppingCategoryPersistence.class.getName());

			ReferenceRegistry.registerReference(ShoppingCategoryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(ShoppingCategoryPersistence persistence) {
	}

	private static ShoppingCategoryPersistence _persistence;
}