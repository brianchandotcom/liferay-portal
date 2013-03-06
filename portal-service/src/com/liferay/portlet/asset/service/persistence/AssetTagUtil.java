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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.asset.model.AssetTag;

import java.util.List;

/**
 * The persistence utility for the asset tag service. This utility wraps {@link AssetTagPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagPersistence
 * @see AssetTagPersistenceImpl
 * @generated
 */
public class AssetTagUtil {
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
	public static void clearCache(AssetTag assetTag) {
		getPersistence().clearCache(assetTag);
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
	public static List<AssetTag> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetTag> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetTag> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static AssetTag update(AssetTag assetTag) throws SystemException {
		return getPersistence().update(assetTag);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static AssetTag update(AssetTag assetTag,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(assetTag, serviceContext);
	}

	/**
	* Returns all the asset tags where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching asset tags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the asset tags where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the asset tags where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first asset tag in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a matching asset tag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTag findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first asset tag in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag, or <code>null</code> if a matching asset tag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTag fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last asset tag in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a matching asset tag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTag findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last asset tag in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag, or <code>null</code> if a matching asset tag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTag fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the asset tags before and after the current asset tag in the ordered set where groupId = &#63;.
	*
	* @param tagId the primary key of the current asset tag
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a asset tag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTag[] findByGroupId_PrevAndNext(
		long tagId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(tagId, groupId, orderByComparator);
	}

	/**
	* Returns all the asset tags that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching asset tags that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the asset tags that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the asset tags that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the asset tags before and after the current asset tag in the ordered set of asset tags that the user has permission to view where groupId = &#63;.
	*
	* @param tagId the primary key of the current asset tag
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a asset tag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTag[] filterFindByGroupId_PrevAndNext(
		long tagId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(tagId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the asset tags where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of asset tags where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching asset tags
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of asset tags that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching asset tags that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the asset tag where groupId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchTagException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a matching asset tag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTag findByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().findByG_N(groupId, name);
	}

	/**
	* Returns the asset tag where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching asset tag, or <code>null</code> if a matching asset tag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTag fetchByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_N(groupId, name);
	}

	/**
	* Returns the asset tag where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching asset tag, or <code>null</code> if a matching asset tag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTag fetchByG_N(
		long groupId, java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_N(groupId, name, retrieveFromCache);
	}

	/**
	* Removes the asset tag where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the asset tag that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTag removeByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().removeByG_N(groupId, name);
	}

	/**
	* Returns the number of asset tags where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching asset tags
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_N(groupId, name);
	}

	/**
	* Caches the asset tag in the entity cache if it is enabled.
	*
	* @param assetTag the asset tag
	*/
	public static void cacheResult(
		com.liferay.portlet.asset.model.AssetTag assetTag) {
		getPersistence().cacheResult(assetTag);
	}

	/**
	* Caches the asset tags in the entity cache if it is enabled.
	*
	* @param assetTags the asset tags
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags) {
		getPersistence().cacheResult(assetTags);
	}

	/**
	* Creates a new asset tag with the primary key. Does not add the asset tag to the database.
	*
	* @param tagId the primary key for the new asset tag
	* @return the new asset tag
	*/
	public static com.liferay.portlet.asset.model.AssetTag create(long tagId) {
		return getPersistence().create(tagId);
	}

	/**
	* Removes the asset tag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag that was removed
	* @throws com.liferay.portlet.asset.NoSuchTagException if a asset tag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTag remove(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().remove(tagId);
	}

	public static com.liferay.portlet.asset.model.AssetTag updateImpl(
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(assetTag);
	}

	/**
	* Returns the asset tag with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchTagException} if it could not be found.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag
	* @throws com.liferay.portlet.asset.NoSuchTagException if a asset tag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTag findByPrimaryKey(
		long tagId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().findByPrimaryKey(tagId);
	}

	/**
	* Returns the asset tag with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag, or <code>null</code> if a asset tag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTag fetchByPrimaryKey(
		long tagId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(tagId);
	}

	/**
	* Returns all the asset tags.
	*
	* @return the asset tags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the asset tags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of asset tags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the asset tags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset tags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the asset tags from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset tags.
	*
	* @return the number of asset tags
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	/**
	* Returns all the asset entries associated with the asset tag.
	*
	* @param pk the primary key of the asset tag
	* @return the asset entries associated with the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetEntries(pk);
	}

	/**
	* Returns a range of all the asset entries associated with the asset tag.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset tag
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of asset entries associated with the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetEntries(pk, start, end);
	}

	/**
	* Returns an ordered range of all the asset entries associated with the asset tag.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset tag
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset entries associated with the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .getAssetEntries(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of asset entries associated with the asset tag.
	*
	* @param pk the primary key of the asset tag
	* @return the number of asset entries associated with the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public static int getAssetEntriesSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetEntriesSize(pk);
	}

	/**
	* Returns <code>true</code> if the asset entry is associated with the asset tag.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPK the primary key of the asset entry
	* @return <code>true</code> if the asset entry is associated with the asset tag; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsAssetEntry(long pk, long assetEntryPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsAssetEntry(pk, assetEntryPK);
	}

	/**
	* Returns <code>true</code> if the asset tag has any asset entries associated with it.
	*
	* @param pk the primary key of the asset tag to check for associations with asset entries
	* @return <code>true</code> if the asset tag has any asset entries associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsAssetEntries(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsAssetEntries(pk);
	}

	/**
	* Adds an association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPK the primary key of the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetEntry(long pk, long assetEntryPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetEntry(pk, assetEntryPK);
	}

	/**
	* Adds an association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntry the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetEntry(pk, assetEntry);
	}

	/**
	* Adds an association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPKs the primary keys of the asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetEntries(long pk, long[] assetEntryPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetEntries(pk, assetEntryPKs);
	}

	/**
	* Adds an association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntries the asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetEntries(pk, assetEntries);
	}

	/**
	* Clears all associations between the asset tag and its asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag to clear the associated asset entries from
	* @throws SystemException if a system exception occurred
	*/
	public static void clearAssetEntries(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearAssetEntries(pk);
	}

	/**
	* Removes the association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPK the primary key of the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetEntry(long pk, long assetEntryPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetEntry(pk, assetEntryPK);
	}

	/**
	* Removes the association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntry the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetEntry(pk, assetEntry);
	}

	/**
	* Removes the association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPKs the primary keys of the asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetEntries(long pk, long[] assetEntryPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetEntries(pk, assetEntryPKs);
	}

	/**
	* Removes the association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntries the asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetEntries(pk, assetEntries);
	}

	/**
	* Sets the asset entries associated with the asset tag, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPKs the primary keys of the asset entries to be associated with the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public static void setAssetEntries(long pk, long[] assetEntryPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setAssetEntries(pk, assetEntryPKs);
	}

	/**
	* Sets the asset entries associated with the asset tag, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntries the asset entries to be associated with the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public static void setAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setAssetEntries(pk, assetEntries);
	}

	public static AssetTagPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetTagPersistence)PortalBeanLocatorUtil.locate(AssetTagPersistence.class.getName());

			ReferenceRegistry.registerReference(AssetTagUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(AssetTagPersistence persistence) {
	}

	private static AssetTagPersistence _persistence;
}