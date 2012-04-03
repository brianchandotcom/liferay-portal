/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.messageboards.model.MBCategory;

import java.util.List;

/**
 * The persistence utility for the message boards category service. This utility wraps {@link MBCategoryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBCategoryPersistence
 * @see MBCategoryPersistenceImpl
 * @generated
 */
public class MBCategoryUtil {
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
	public static void clearCache(MBCategory mbCategory) {
		getPersistence().clearCache(mbCategory);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<MBCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MBCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MBCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static MBCategory update(MBCategory mbCategory, boolean merge)
		throws SystemException {
		return getPersistence().update(mbCategory, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static MBCategory update(MBCategory mbCategory, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(mbCategory, merge, serviceContext);
	}

	/**
	* Caches the message boards category in the entity cache if it is enabled.
	*
	* @param mbCategory the message boards category
	*/
	public static void cacheResult(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory) {
		getPersistence().cacheResult(mbCategory);
	}

	/**
	* Caches the message boards categories in the entity cache if it is enabled.
	*
	* @param mbCategories the message boards categories
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBCategory> mbCategories) {
		getPersistence().cacheResult(mbCategories);
	}

	/**
	* Creates a new message boards category with the primary key. Does not add the message boards category to the database.
	*
	* @param categoryId the primary key for the new message boards category
	* @return the new message boards category
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory create(
		long categoryId) {
		return getPersistence().create(categoryId);
	}

	/**
	* Removes the message boards category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryId the primary key of the message boards category
	* @return the message boards category that was removed
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory remove(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().remove(categoryId);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory updateImpl(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(mbCategory, merge);
	}

	/**
	* Returns the message boards category with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchCategoryException} if it could not be found.
	*
	* @param categoryId the primary key of the message boards category
	* @return the message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory findByPrimaryKey(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByPrimaryKey(categoryId);
	}

	/**
	* Returns the message boards category with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param categoryId the primary key of the message boards category
	* @return the message boards category, or <code>null</code> if a message boards category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory fetchByPrimaryKey(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(categoryId);
	}

	/**
	* Returns all the message boards categories where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the message boards categories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the message boards categories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first message boards category in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last message boards category in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the message boards categories before and after the current message boards category in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the primary key of the current message boards category
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory[] findByUuid_PrevAndNext(
		long categoryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(categoryId, uuid, orderByComparator);
	}

	/**
	* Returns the message boards category where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchCategoryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the message boards category where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching message boards category, or <code>null</code> if a matching message boards category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the message boards category where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching message boards category, or <code>null</code> if a matching message boards category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Returns all the message boards categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the message boards categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first message boards category in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last message boards category in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the message boards categories before and after the current message boards category in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the primary key of the current message boards category
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory[] findByGroupId_PrevAndNext(
		long categoryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(categoryId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the message boards categories that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching message boards categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the message boards categories that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards categories that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the message boards categories before and after the current message boards category in the ordered set of message boards categories that the user has permission to view where groupId = &#63;.
	*
	* @param categoryId the primary key of the current message boards category
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory[] filterFindByGroupId_PrevAndNext(
		long categoryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(categoryId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the message boards categories where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the message boards categories where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards categories where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first message boards category in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last message boards category in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the message boards categories before and after the current message boards category in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the primary key of the current message boards category
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory[] findByCompanyId_PrevAndNext(
		long categoryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(categoryId, companyId,
			orderByComparator);
	}

	/**
	* Returns all the message boards categories where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P(
		long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, parentCategoryId);
	}

	/**
	* Returns a range of all the message boards categories where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P(
		long groupId, long parentCategoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, parentCategoryId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards categories where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P(
		long groupId, long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P(groupId, parentCategoryId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first message boards category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory findByG_P_First(
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence()
				   .findByG_P_First(groupId, parentCategoryId, orderByComparator);
	}

	/**
	* Returns the last message boards category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a matching message boards category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory findByG_P_Last(
		long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence()
				   .findByG_P_Last(groupId, parentCategoryId, orderByComparator);
	}

	/**
	* Returns the message boards categories before and after the current message boards category in the ordered set where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the primary key of the current message boards category
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory[] findByG_P_PrevAndNext(
		long categoryId, long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence()
				   .findByG_P_PrevAndNext(categoryId, groupId,
			parentCategoryId, orderByComparator);
	}

	/**
	* Returns all the message boards categories where groupId = &#63; and parentCategoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @return the matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P(
		long groupId, long[] parentCategoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, parentCategoryIds);
	}

	/**
	* Returns a range of all the message boards categories where groupId = &#63; and parentCategoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P(
		long groupId, long[] parentCategoryIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, parentCategoryIds, start, end);
	}

	/**
	* Returns an ordered range of all the message boards categories where groupId = &#63; and parentCategoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findByG_P(
		long groupId, long[] parentCategoryIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P(groupId, parentCategoryIds, start, end,
			orderByComparator);
	}

	/**
	* Returns all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the matching message boards categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_P(
		long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_P(groupId, parentCategoryId);
	}

	/**
	* Returns a range of all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_P(
		long groupId, long parentCategoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P(groupId, parentCategoryId, start, end);
	}

	/**
	* Returns an ordered range of all the message boards categories that the user has permissions to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_P(
		long groupId, long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P(groupId, parentCategoryId, start, end,
			orderByComparator);
	}

	/**
	* Returns the message boards categories before and after the current message boards category in the ordered set of message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param categoryId the primary key of the current message boards category
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards category
	* @throws com.liferay.portlet.messageboards.NoSuchCategoryException if a message boards category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory[] filterFindByG_P_PrevAndNext(
		long categoryId, long groupId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence()
				   .filterFindByG_P_PrevAndNext(categoryId, groupId,
			parentCategoryId, orderByComparator);
	}

	/**
	* Returns all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = any &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @return the matching message boards categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_P(
		long groupId, long[] parentCategoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_P(groupId, parentCategoryIds);
	}

	/**
	* Returns a range of all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of matching message boards categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_P(
		long groupId, long[] parentCategoryIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P(groupId, parentCategoryIds, start, end);
	}

	/**
	* Returns an ordered range of all the message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> filterFindByG_P(
		long groupId, long[] parentCategoryIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P(groupId, parentCategoryIds, start, end,
			orderByComparator);
	}

	/**
	* Returns all the message boards categories.
	*
	* @return the message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the message boards categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @return the range of message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the message boards categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards categories
	* @param end the upper bound of the range of message boards categories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the message boards categories where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the message boards category where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the message boards category that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBCategory removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchCategoryException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Removes all the message boards categories where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes all the message boards categories where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Removes all the message boards categories where groupId = &#63; and parentCategoryId = &#63; from the database.
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
	* Removes all the message boards categories from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of message boards categories where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of message boards categories where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of message boards categories where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of message boards categories that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching message boards categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the number of message boards categories where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the number of message boards categories where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the number of matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_P(long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P(groupId, parentCategoryId);
	}

	/**
	* Returns the number of message boards categories where groupId = &#63; and parentCategoryId = any &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @return the number of matching message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_P(long groupId, long[] parentCategoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P(groupId, parentCategoryIds);
	}

	/**
	* Returns the number of message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryId the parent category ID
	* @return the number of matching message boards categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_P(long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_P(groupId, parentCategoryId);
	}

	/**
	* Returns the number of message boards categories that the user has permission to view where groupId = &#63; and parentCategoryId = any &#63;.
	*
	* @param groupId the group ID
	* @param parentCategoryIds the parent category IDs
	* @return the number of matching message boards categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_P(long groupId, long[] parentCategoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_P(groupId, parentCategoryIds);
	}

	/**
	* Returns the number of message boards categories.
	*
	* @return the number of message boards categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static MBCategoryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MBCategoryPersistence)PortalBeanLocatorUtil.locate(MBCategoryPersistence.class.getName());

			ReferenceRegistry.registerReference(MBCategoryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(MBCategoryPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(MBCategoryUtil.class, "_persistence");
	}

	private static MBCategoryPersistence _persistence;
}