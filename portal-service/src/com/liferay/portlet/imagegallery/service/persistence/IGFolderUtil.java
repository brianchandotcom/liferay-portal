/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallery.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.imagegallery.model.IGFolder;

import java.util.List;

/**
 * The persistence utility for the i g folder service. This utility wraps {@link IGFolderPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see IGFolderPersistence
 * @see IGFolderPersistenceImpl
 * @generated
 */
public class IGFolderUtil {
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
	public static void clearCache(IGFolder igFolder) {
		getPersistence().clearCache(igFolder);
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
	public static List<IGFolder> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<IGFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<IGFolder> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static IGFolder remove(IGFolder igFolder) throws SystemException {
		return getPersistence().remove(igFolder);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static IGFolder update(IGFolder igFolder, boolean merge)
		throws SystemException {
		return getPersistence().update(igFolder, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static IGFolder update(IGFolder igFolder, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(igFolder, merge, serviceContext);
	}

	/**
	* Caches the i g folder in the entity cache if it is enabled.
	*
	* @param igFolder the i g folder
	*/
	public static void cacheResult(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder) {
		getPersistence().cacheResult(igFolder);
	}

	/**
	* Caches the i g folders in the entity cache if it is enabled.
	*
	* @param igFolders the i g folders
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> igFolders) {
		getPersistence().cacheResult(igFolders);
	}

	/**
	* Creates a new i g folder with the primary key. Does not add the i g folder to the database.
	*
	* @param folderId the primary key for the new i g folder
	* @return the new i g folder
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder create(
		long folderId) {
		return getPersistence().create(folderId);
	}

	/**
	* Removes the i g folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param folderId the primary key of the i g folder
	* @return the i g folder that was removed
	* @throws com.liferay.portlet.imagegallery.NoSuchFolderException if a i g folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder remove(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		return getPersistence().remove(folderId);
	}

	public static com.liferay.portlet.imagegallery.model.IGFolder updateImpl(
		com.liferay.portlet.imagegallery.model.IGFolder igFolder, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(igFolder, merge);
	}

	/**
	* Returns the i g folder with the primary key or throws a {@link com.liferay.portlet.imagegallery.NoSuchFolderException} if it could not be found.
	*
	* @param folderId the primary key of the i g folder
	* @return the i g folder
	* @throws com.liferay.portlet.imagegallery.NoSuchFolderException if a i g folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder findByPrimaryKey(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		return getPersistence().findByPrimaryKey(folderId);
	}

	/**
	* Returns the i g folder with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param folderId the primary key of the i g folder
	* @return the i g folder, or <code>null</code> if a i g folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder fetchByPrimaryKey(
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(folderId);
	}

	/**
	* Returns all the i g folders where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the i g folders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of i g folders
	* @param end the upper bound of the range of i g folders (not inclusive)
	* @return the range of matching i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the i g folders where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of i g folders
	* @param end the upper bound of the range of i g folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first i g folder in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching i g folder
	* @throws com.liferay.portlet.imagegallery.NoSuchFolderException if a matching i g folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last i g folder in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching i g folder
	* @throws com.liferay.portlet.imagegallery.NoSuchFolderException if a matching i g folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the i g folders before and after the current i g folder in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param folderId the primary key of the current i g folder
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next i g folder
	* @throws com.liferay.portlet.imagegallery.NoSuchFolderException if a i g folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder[] findByUuid_PrevAndNext(
		long folderId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		return getPersistence()
				   .findByUuid_PrevAndNext(folderId, uuid, orderByComparator);
	}

	/**
	* Returns the i g folder where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.imagegallery.NoSuchFolderException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching i g folder
	* @throws com.liferay.portlet.imagegallery.NoSuchFolderException if a matching i g folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the i g folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching i g folder, or <code>null</code> if a matching i g folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the i g folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching i g folder, or <code>null</code> if a matching i g folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Returns all the i g folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the i g folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of i g folders
	* @param end the upper bound of the range of i g folders (not inclusive)
	* @return the range of matching i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the i g folders where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of i g folders
	* @param end the upper bound of the range of i g folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first i g folder in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching i g folder
	* @throws com.liferay.portlet.imagegallery.NoSuchFolderException if a matching i g folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last i g folder in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching i g folder
	* @throws com.liferay.portlet.imagegallery.NoSuchFolderException if a matching i g folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the i g folders before and after the current i g folder in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param folderId the primary key of the current i g folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next i g folder
	* @throws com.liferay.portlet.imagegallery.NoSuchFolderException if a i g folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder[] findByGroupId_PrevAndNext(
		long folderId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(folderId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the i g folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching i g folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the i g folders that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of i g folders
	* @param end the upper bound of the range of i g folders (not inclusive)
	* @return the range of matching i g folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the i g folders that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of i g folders
	* @param end the upper bound of the range of i g folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching i g folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the i g folders before and after the current i g folder in the ordered set of i g folders that the user has permission to view where groupId = &#63;.
	*
	* @param folderId the primary key of the current i g folder
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next i g folder
	* @throws com.liferay.portlet.imagegallery.NoSuchFolderException if a i g folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder[] filterFindByGroupId_PrevAndNext(
		long folderId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(folderId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the i g folders where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the i g folders where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of i g folders
	* @param end the upper bound of the range of i g folders (not inclusive)
	* @return the range of matching i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the i g folders where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of i g folders
	* @param end the upper bound of the range of i g folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first i g folder in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching i g folder
	* @throws com.liferay.portlet.imagegallery.NoSuchFolderException if a matching i g folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last i g folder in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching i g folder
	* @throws com.liferay.portlet.imagegallery.NoSuchFolderException if a matching i g folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the i g folders before and after the current i g folder in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param folderId the primary key of the current i g folder
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next i g folder
	* @throws com.liferay.portlet.imagegallery.NoSuchFolderException if a i g folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder[] findByCompanyId_PrevAndNext(
		long folderId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(folderId, companyId,
			orderByComparator);
	}

	/**
	* Returns all the i g folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the matching i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByG_P(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, parentFolderId);
	}

	/**
	* Returns a range of all the i g folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of i g folders
	* @param end the upper bound of the range of i g folders (not inclusive)
	* @return the range of matching i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_P(groupId, parentFolderId, start, end);
	}

	/**
	* Returns an ordered range of all the i g folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of i g folders
	* @param end the upper bound of the range of i g folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findByG_P(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_P(groupId, parentFolderId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first i g folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching i g folder
	* @throws com.liferay.portlet.imagegallery.NoSuchFolderException if a matching i g folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder findByG_P_First(
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		return getPersistence()
				   .findByG_P_First(groupId, parentFolderId, orderByComparator);
	}

	/**
	* Returns the last i g folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching i g folder
	* @throws com.liferay.portlet.imagegallery.NoSuchFolderException if a matching i g folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder findByG_P_Last(
		long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		return getPersistence()
				   .findByG_P_Last(groupId, parentFolderId, orderByComparator);
	}

	/**
	* Returns the i g folders before and after the current i g folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param folderId the primary key of the current i g folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next i g folder
	* @throws com.liferay.portlet.imagegallery.NoSuchFolderException if a i g folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder[] findByG_P_PrevAndNext(
		long folderId, long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		return getPersistence()
				   .findByG_P_PrevAndNext(folderId, groupId, parentFolderId,
			orderByComparator);
	}

	/**
	* Returns all the i g folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the matching i g folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> filterFindByG_P(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_P(groupId, parentFolderId);
	}

	/**
	* Returns a range of all the i g folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of i g folders
	* @param end the upper bound of the range of i g folders (not inclusive)
	* @return the range of matching i g folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> filterFindByG_P(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P(groupId, parentFolderId, start, end);
	}

	/**
	* Returns an ordered range of all the i g folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param start the lower bound of the range of i g folders
	* @param end the upper bound of the range of i g folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching i g folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> filterFindByG_P(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_P(groupId, parentFolderId, start, end,
			orderByComparator);
	}

	/**
	* Returns the i g folders before and after the current i g folder in the ordered set of i g folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param folderId the primary key of the current i g folder
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next i g folder
	* @throws com.liferay.portlet.imagegallery.NoSuchFolderException if a i g folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder[] filterFindByG_P_PrevAndNext(
		long folderId, long groupId, long parentFolderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		return getPersistence()
				   .filterFindByG_P_PrevAndNext(folderId, groupId,
			parentFolderId, orderByComparator);
	}

	/**
	* Returns the i g folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.imagegallery.NoSuchFolderException} if it could not be found.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the matching i g folder
	* @throws com.liferay.portlet.imagegallery.NoSuchFolderException if a matching i g folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder findByG_P_N(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		return getPersistence().findByG_P_N(groupId, parentFolderId, name);
	}

	/**
	* Returns the i g folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the matching i g folder, or <code>null</code> if a matching i g folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder fetchByG_P_N(
		long groupId, long parentFolderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_P_N(groupId, parentFolderId, name);
	}

	/**
	* Returns the i g folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching i g folder, or <code>null</code> if a matching i g folder could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGFolder fetchByG_P_N(
		long groupId, long parentFolderId, java.lang.String name,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_P_N(groupId, parentFolderId, name,
			retrieveFromCache);
	}

	/**
	* Returns all the i g folders.
	*
	* @return the i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the i g folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of i g folders
	* @param end the upper bound of the range of i g folders (not inclusive)
	* @return the range of i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the i g folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of i g folders
	* @param end the upper bound of the range of i g folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGFolder> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the i g folders where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the i g folder where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Removes all the i g folders where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes all the i g folders where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Removes all the i g folders where groupId = &#63; and parentFolderId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_P(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_P(groupId, parentFolderId);
	}

	/**
	* Removes the i g folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_P_N(long groupId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchFolderException {
		getPersistence().removeByG_P_N(groupId, parentFolderId, name);
	}

	/**
	* Removes all the i g folders from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of i g folders where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of i g folders where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of i g folders where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of i g folders that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching i g folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the number of i g folders where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the number of i g folders where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_P(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P(groupId, parentFolderId);
	}

	/**
	* Returns the number of i g folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @return the number of matching i g folders that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_P(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_P(groupId, parentFolderId);
	}

	/**
	* Returns the number of i g folders where groupId = &#63; and parentFolderId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param parentFolderId the parent folder ID
	* @param name the name
	* @return the number of matching i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_P_N(long groupId, long parentFolderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_P_N(groupId, parentFolderId, name);
	}

	/**
	* Returns the number of i g folders.
	*
	* @return the number of i g folders
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static IGFolderPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (IGFolderPersistence)PortalBeanLocatorUtil.locate(IGFolderPersistence.class.getName());

			ReferenceRegistry.registerReference(IGFolderUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(IGFolderPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(IGFolderUtil.class, "_persistence");
	}

	private static IGFolderPersistence _persistence;
}