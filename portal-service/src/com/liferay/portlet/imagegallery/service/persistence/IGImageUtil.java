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

import com.liferay.portlet.imagegallery.model.IGImage;

import java.util.List;

/**
 * The persistence utility for the i g image service. This utility wraps {@link IGImagePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see IGImagePersistence
 * @see IGImagePersistenceImpl
 * @generated
 */
public class IGImageUtil {
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
	public static void clearCache(IGImage igImage) {
		getPersistence().clearCache(igImage);
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
	public static List<IGImage> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<IGImage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<IGImage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static IGImage remove(IGImage igImage) throws SystemException {
		return getPersistence().remove(igImage);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static IGImage update(IGImage igImage, boolean merge)
		throws SystemException {
		return getPersistence().update(igImage, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static IGImage update(IGImage igImage, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(igImage, merge, serviceContext);
	}

	/**
	* Caches the i g image in the entity cache if it is enabled.
	*
	* @param igImage the i g image
	*/
	public static void cacheResult(
		com.liferay.portlet.imagegallery.model.IGImage igImage) {
		getPersistence().cacheResult(igImage);
	}

	/**
	* Caches the i g images in the entity cache if it is enabled.
	*
	* @param igImages the i g images
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.imagegallery.model.IGImage> igImages) {
		getPersistence().cacheResult(igImages);
	}

	/**
	* Creates a new i g image with the primary key. Does not add the i g image to the database.
	*
	* @param imageId the primary key for the new i g image
	* @return the new i g image
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage create(
		long imageId) {
		return getPersistence().create(imageId);
	}

	/**
	* Removes the i g image with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param imageId the primary key of the i g image
	* @return the i g image that was removed
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage remove(
		long imageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().remove(imageId);
	}

	public static com.liferay.portlet.imagegallery.model.IGImage updateImpl(
		com.liferay.portlet.imagegallery.model.IGImage igImage, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(igImage, merge);
	}

	/**
	* Returns the i g image with the primary key or throws a {@link com.liferay.portlet.imagegallery.NoSuchImageException} if it could not be found.
	*
	* @param imageId the primary key of the i g image
	* @return the i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage findByPrimaryKey(
		long imageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByPrimaryKey(imageId);
	}

	/**
	* Returns the i g image with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param imageId the primary key of the i g image
	* @return the i g image, or <code>null</code> if a i g image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage fetchByPrimaryKey(
		long imageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(imageId);
	}

	/**
	* Returns all the i g images where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the i g images where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @return the range of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the i g images where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first i g image in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last i g image in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the i g images before and after the current i g image in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param imageId the primary key of the current i g image
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage[] findByUuid_PrevAndNext(
		long imageId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence()
				   .findByUuid_PrevAndNext(imageId, uuid, orderByComparator);
	}

	/**
	* Returns the i g image where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.imagegallery.NoSuchImageException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the i g image where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the i g image where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Returns all the i g images where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the i g images where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @return the range of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the i g images where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first i g image in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last i g image in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the i g images before and after the current i g image in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param imageId the primary key of the current i g image
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage[] findByGroupId_PrevAndNext(
		long imageId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(imageId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the i g images that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the i g images that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @return the range of matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the i g images that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the i g images before and after the current i g image in the ordered set of i g images that the user has permission to view where groupId = &#63;.
	*
	* @param imageId the primary key of the current i g image
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage[] filterFindByGroupId_PrevAndNext(
		long imageId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(imageId, groupId,
			orderByComparator);
	}

	/**
	* Returns the i g image where smallImageId = &#63; or throws a {@link com.liferay.portlet.imagegallery.NoSuchImageException} if it could not be found.
	*
	* @param smallImageId the small image ID
	* @return the matching i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage findBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findBySmallImageId(smallImageId);
	}

	/**
	* Returns the i g image where smallImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param smallImageId the small image ID
	* @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage fetchBySmallImageId(
		long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchBySmallImageId(smallImageId);
	}

	/**
	* Returns the i g image where smallImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param smallImageId the small image ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage fetchBySmallImageId(
		long smallImageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchBySmallImageId(smallImageId, retrieveFromCache);
	}

	/**
	* Returns the i g image where largeImageId = &#63; or throws a {@link com.liferay.portlet.imagegallery.NoSuchImageException} if it could not be found.
	*
	* @param largeImageId the large image ID
	* @return the matching i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage findByLargeImageId(
		long largeImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByLargeImageId(largeImageId);
	}

	/**
	* Returns the i g image where largeImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param largeImageId the large image ID
	* @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage fetchByLargeImageId(
		long largeImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByLargeImageId(largeImageId);
	}

	/**
	* Returns the i g image where largeImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param largeImageId the large image ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage fetchByLargeImageId(
		long largeImageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByLargeImageId(largeImageId, retrieveFromCache);
	}

	/**
	* Returns the i g image where custom1ImageId = &#63; or throws a {@link com.liferay.portlet.imagegallery.NoSuchImageException} if it could not be found.
	*
	* @param custom1ImageId the custom1 image ID
	* @return the matching i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage findByCustom1ImageId(
		long custom1ImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByCustom1ImageId(custom1ImageId);
	}

	/**
	* Returns the i g image where custom1ImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param custom1ImageId the custom1 image ID
	* @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage fetchByCustom1ImageId(
		long custom1ImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByCustom1ImageId(custom1ImageId);
	}

	/**
	* Returns the i g image where custom1ImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param custom1ImageId the custom1 image ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage fetchByCustom1ImageId(
		long custom1ImageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCustom1ImageId(custom1ImageId, retrieveFromCache);
	}

	/**
	* Returns the i g image where custom2ImageId = &#63; or throws a {@link com.liferay.portlet.imagegallery.NoSuchImageException} if it could not be found.
	*
	* @param custom2ImageId the custom2 image ID
	* @return the matching i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage findByCustom2ImageId(
		long custom2ImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence().findByCustom2ImageId(custom2ImageId);
	}

	/**
	* Returns the i g image where custom2ImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param custom2ImageId the custom2 image ID
	* @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage fetchByCustom2ImageId(
		long custom2ImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByCustom2ImageId(custom2ImageId);
	}

	/**
	* Returns the i g image where custom2ImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param custom2ImageId the custom2 image ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching i g image, or <code>null</code> if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage fetchByCustom2ImageId(
		long custom2ImageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCustom2ImageId(custom2ImageId, retrieveFromCache);
	}

	/**
	* Returns all the i g images where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U(groupId, userId);
	}

	/**
	* Returns a range of all the i g images where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @return the range of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U(groupId, userId, start, end);
	}

	/**
	* Returns an ordered range of all the i g images where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_U(groupId, userId, start, end, orderByComparator);
	}

	/**
	* Returns the first i g image in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence()
				   .findByG_U_First(groupId, userId, orderByComparator);
	}

	/**
	* Returns the last i g image in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence()
				   .findByG_U_Last(groupId, userId, orderByComparator);
	}

	/**
	* Returns the i g images before and after the current i g image in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param imageId the primary key of the current i g image
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage[] findByG_U_PrevAndNext(
		long imageId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence()
				   .findByG_U_PrevAndNext(imageId, groupId, userId,
			orderByComparator);
	}

	/**
	* Returns all the i g images that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> filterFindByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_U(groupId, userId);
	}

	/**
	* Returns a range of all the i g images that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @return the range of matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> filterFindByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_U(groupId, userId, start, end);
	}

	/**
	* Returns an ordered range of all the i g images that the user has permissions to view where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> filterFindByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_U(groupId, userId, start, end,
			orderByComparator);
	}

	/**
	* Returns the i g images before and after the current i g image in the ordered set of i g images that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param imageId the primary key of the current i g image
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage[] filterFindByG_U_PrevAndNext(
		long imageId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence()
				   .filterFindByG_U_PrevAndNext(imageId, groupId, userId,
			orderByComparator);
	}

	/**
	* Returns all the i g images where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_F(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F(groupId, folderId);
	}

	/**
	* Returns a range of all the i g images where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @return the range of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_F(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F(groupId, folderId, start, end);
	}

	/**
	* Returns an ordered range of all the i g images where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_F(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_F(groupId, folderId, start, end, orderByComparator);
	}

	/**
	* Returns the first i g image in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage findByG_F_First(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence()
				   .findByG_F_First(groupId, folderId, orderByComparator);
	}

	/**
	* Returns the last i g image in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage findByG_F_Last(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence()
				   .findByG_F_Last(groupId, folderId, orderByComparator);
	}

	/**
	* Returns the i g images before and after the current i g image in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param imageId the primary key of the current i g image
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage[] findByG_F_PrevAndNext(
		long imageId, long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence()
				   .findByG_F_PrevAndNext(imageId, groupId, folderId,
			orderByComparator);
	}

	/**
	* Returns all the i g images where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_F(
		long groupId, long[] folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F(groupId, folderIds);
	}

	/**
	* Returns a range of all the i g images where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @return the range of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_F(
		long groupId, long[] folderIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F(groupId, folderIds, start, end);
	}

	/**
	* Returns an ordered range of all the i g images where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_F(
		long groupId, long[] folderIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_F(groupId, folderIds, start, end, orderByComparator);
	}

	/**
	* Returns all the i g images that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> filterFindByG_F(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_F(groupId, folderId);
	}

	/**
	* Returns a range of all the i g images that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @return the range of matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> filterFindByG_F(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_F(groupId, folderId, start, end);
	}

	/**
	* Returns an ordered range of all the i g images that the user has permissions to view where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> filterFindByG_F(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_F(groupId, folderId, start, end,
			orderByComparator);
	}

	/**
	* Returns the i g images before and after the current i g image in the ordered set of i g images that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param imageId the primary key of the current i g image
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage[] filterFindByG_F_PrevAndNext(
		long imageId, long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence()
				   .filterFindByG_F_PrevAndNext(imageId, groupId, folderId,
			orderByComparator);
	}

	/**
	* Returns all the i g images that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> filterFindByG_F(
		long groupId, long[] folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_F(groupId, folderIds);
	}

	/**
	* Returns a range of all the i g images that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @return the range of matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> filterFindByG_F(
		long groupId, long[] folderIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_F(groupId, folderIds, start, end);
	}

	/**
	* Returns an ordered range of all the i g images that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> filterFindByG_F(
		long groupId, long[] folderIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_F(groupId, folderIds, start, end,
			orderByComparator);
	}

	/**
	* Returns all the i g images where groupId = &#63; and folderId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param name the name
	* @return the matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_F_N(
		long groupId, long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F_N(groupId, folderId, name);
	}

	/**
	* Returns a range of all the i g images where groupId = &#63; and folderId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param name the name
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @return the range of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_F_N(
		long groupId, long folderId, java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F_N(groupId, folderId, name, start, end);
	}

	/**
	* Returns an ordered range of all the i g images where groupId = &#63; and folderId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param name the name
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findByG_F_N(
		long groupId, long folderId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_F_N(groupId, folderId, name, start, end,
			orderByComparator);
	}

	/**
	* Returns the first i g image in the ordered set where groupId = &#63; and folderId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage findByG_F_N_First(
		long groupId, long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence()
				   .findByG_F_N_First(groupId, folderId, name, orderByComparator);
	}

	/**
	* Returns the last i g image in the ordered set where groupId = &#63; and folderId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a matching i g image could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage findByG_F_N_Last(
		long groupId, long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence()
				   .findByG_F_N_Last(groupId, folderId, name, orderByComparator);
	}

	/**
	* Returns the i g images before and after the current i g image in the ordered set where groupId = &#63; and folderId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param imageId the primary key of the current i g image
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage[] findByG_F_N_PrevAndNext(
		long imageId, long groupId, long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence()
				   .findByG_F_N_PrevAndNext(imageId, groupId, folderId, name,
			orderByComparator);
	}

	/**
	* Returns all the i g images that the user has permission to view where groupId = &#63; and folderId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param name the name
	* @return the matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> filterFindByG_F_N(
		long groupId, long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_F_N(groupId, folderId, name);
	}

	/**
	* Returns a range of all the i g images that the user has permission to view where groupId = &#63; and folderId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param name the name
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @return the range of matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> filterFindByG_F_N(
		long groupId, long folderId, java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_F_N(groupId, folderId, name, start, end);
	}

	/**
	* Returns an ordered range of all the i g images that the user has permissions to view where groupId = &#63; and folderId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param name the name
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> filterFindByG_F_N(
		long groupId, long folderId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_F_N(groupId, folderId, name, start, end,
			orderByComparator);
	}

	/**
	* Returns the i g images before and after the current i g image in the ordered set of i g images that the user has permission to view where groupId = &#63; and folderId = &#63; and name = &#63;.
	*
	* @param imageId the primary key of the current i g image
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next i g image
	* @throws com.liferay.portlet.imagegallery.NoSuchImageException if a i g image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.imagegallery.model.IGImage[] filterFindByG_F_N_PrevAndNext(
		long imageId, long groupId, long folderId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		return getPersistence()
				   .filterFindByG_F_N_PrevAndNext(imageId, groupId, folderId,
			name, orderByComparator);
	}

	/**
	* Returns all the i g images.
	*
	* @return the i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the i g images.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @return the range of i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the i g images.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of i g images
	* @param end the upper bound of the range of i g images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of i g images
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.imagegallery.model.IGImage> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the i g images where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the i g image where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Removes all the i g images where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes the i g image where smallImageId = &#63; from the database.
	*
	* @param smallImageId the small image ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeBySmallImageId(long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		getPersistence().removeBySmallImageId(smallImageId);
	}

	/**
	* Removes the i g image where largeImageId = &#63; from the database.
	*
	* @param largeImageId the large image ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByLargeImageId(long largeImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		getPersistence().removeByLargeImageId(largeImageId);
	}

	/**
	* Removes the i g image where custom1ImageId = &#63; from the database.
	*
	* @param custom1ImageId the custom1 image ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCustom1ImageId(long custom1ImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		getPersistence().removeByCustom1ImageId(custom1ImageId);
	}

	/**
	* Removes the i g image where custom2ImageId = &#63; from the database.
	*
	* @param custom2ImageId the custom2 image ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCustom2ImageId(long custom2ImageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.imagegallery.NoSuchImageException {
		getPersistence().removeByCustom2ImageId(custom2ImageId);
	}

	/**
	* Removes all the i g images where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_U(groupId, userId);
	}

	/**
	* Removes all the i g images where groupId = &#63; and folderId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_F(groupId, folderId);
	}

	/**
	* Removes all the i g images where groupId = &#63; and folderId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param name the name
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_F_N(long groupId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_F_N(groupId, folderId, name);
	}

	/**
	* Removes all the i g images from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of i g images where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of i g images where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of i g images where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of i g images that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the number of i g images where smallImageId = &#63;.
	*
	* @param smallImageId the small image ID
	* @return the number of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static int countBySmallImageId(long smallImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countBySmallImageId(smallImageId);
	}

	/**
	* Returns the number of i g images where largeImageId = &#63;.
	*
	* @param largeImageId the large image ID
	* @return the number of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static int countByLargeImageId(long largeImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByLargeImageId(largeImageId);
	}

	/**
	* Returns the number of i g images where custom1ImageId = &#63;.
	*
	* @param custom1ImageId the custom1 image ID
	* @return the number of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCustom1ImageId(long custom1ImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCustom1ImageId(custom1ImageId);
	}

	/**
	* Returns the number of i g images where custom2ImageId = &#63;.
	*
	* @param custom2ImageId the custom2 image ID
	* @return the number of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCustom2ImageId(long custom2ImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCustom2ImageId(custom2ImageId);
	}

	/**
	* Returns the number of i g images where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_U(groupId, userId);
	}

	/**
	* Returns the number of i g images that the user has permission to view where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_U(groupId, userId);
	}

	/**
	* Returns the number of i g images where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the number of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_F(groupId, folderId);
	}

	/**
	* Returns the number of i g images where groupId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the number of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_F(long groupId, long[] folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_F(groupId, folderIds);
	}

	/**
	* Returns the number of i g images that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the number of matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_F(groupId, folderId);
	}

	/**
	* Returns the number of i g images that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group ID
	* @param folderIds the folder IDs
	* @return the number of matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_F(long groupId, long[] folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_F(groupId, folderIds);
	}

	/**
	* Returns the number of i g images where groupId = &#63; and folderId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param name the name
	* @return the number of matching i g images
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_F_N(long groupId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_F_N(groupId, folderId, name);
	}

	/**
	* Returns the number of i g images that the user has permission to view where groupId = &#63; and folderId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param name the name
	* @return the number of matching i g images that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_F_N(long groupId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_F_N(groupId, folderId, name);
	}

	/**
	* Returns the number of i g images.
	*
	* @return the number of i g images
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static IGImagePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (IGImagePersistence)PortalBeanLocatorUtil.locate(IGImagePersistence.class.getName());

			ReferenceRegistry.registerReference(IGImageUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(IGImagePersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(IGImageUtil.class, "_persistence");
	}

	private static IGImagePersistence _persistence;
}