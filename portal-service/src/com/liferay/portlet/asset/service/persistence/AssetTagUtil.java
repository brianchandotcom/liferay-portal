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

package com.liferay.portlet.asset.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.asset.model.AssetTag;

import java.util.List;

/**
 * The persistence utility for the asset tag service. This utility wraps {@link com.liferay.portlet.asset.service.persistence.impl.AssetTagPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagPersistence
 * @see com.liferay.portlet.asset.service.persistence.impl.AssetTagPersistenceImpl
 * @generated
 */
@ProviderType
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
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetTag> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetTag> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetTag> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetTag> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static AssetTag update(AssetTag assetTag) {
		return getPersistence().update(assetTag);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static AssetTag update(AssetTag assetTag,
		ServiceContext serviceContext) {
		return getPersistence().update(assetTag, serviceContext);
	}

	/**
	* Returns all the asset tags where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching asset tags
	*/
	public static List<AssetTag> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the asset tags where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags
	*/
	public static List<AssetTag> findByUuid(java.lang.String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the asset tags where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags
	*/
	public static List<AssetTag> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<AssetTag> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset tags where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset tags
	*/
	public static List<AssetTag> findByUuid(java.lang.String uuid, int start,
		int end, OrderByComparator<AssetTag> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first asset tag in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag
	* @throws NoSuchTagException if a matching asset tag could not be found
	*/
	public static AssetTag findByUuid_First(java.lang.String uuid,
		OrderByComparator<AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first asset tag in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	public static AssetTag fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<AssetTag> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last asset tag in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag
	* @throws NoSuchTagException if a matching asset tag could not be found
	*/
	public static AssetTag findByUuid_Last(java.lang.String uuid,
		OrderByComparator<AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last asset tag in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	public static AssetTag fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<AssetTag> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the asset tags before and after the current asset tag in the ordered set where uuid = &#63;.
	*
	* @param tagId the primary key of the current asset tag
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag
	* @throws NoSuchTagException if a asset tag with the primary key could not be found
	*/
	public static AssetTag[] findByUuid_PrevAndNext(long tagId,
		java.lang.String uuid, OrderByComparator<AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence()
				   .findByUuid_PrevAndNext(tagId, uuid, orderByComparator);
	}

	/**
	* Removes all the asset tags where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of asset tags where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching asset tags
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the asset tag where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchTagException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching asset tag
	* @throws NoSuchTagException if a matching asset tag could not be found
	*/
	public static AssetTag findByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the asset tag where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	public static AssetTag fetchByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the asset tag where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	public static AssetTag fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the asset tag where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the asset tag that was removed
	*/
	public static AssetTag removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of asset tags where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching asset tags
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the asset tags where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching asset tags
	*/
	public static List<AssetTag> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the asset tags where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags
	*/
	public static List<AssetTag> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the asset tags where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags
	*/
	public static List<AssetTag> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<AssetTag> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset tags where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset tags
	*/
	public static List<AssetTag> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<AssetTag> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset tag in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag
	* @throws NoSuchTagException if a matching asset tag could not be found
	*/
	public static AssetTag findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first asset tag in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	public static AssetTag fetchByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<AssetTag> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last asset tag in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag
	* @throws NoSuchTagException if a matching asset tag could not be found
	*/
	public static AssetTag findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last asset tag in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	public static AssetTag fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<AssetTag> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the asset tags before and after the current asset tag in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param tagId the primary key of the current asset tag
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag
	* @throws NoSuchTagException if a asset tag with the primary key could not be found
	*/
	public static AssetTag[] findByUuid_C_PrevAndNext(long tagId,
		java.lang.String uuid, long companyId,
		OrderByComparator<AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(tagId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the asset tags where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of asset tags where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching asset tags
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the asset tags where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching asset tags
	*/
	public static List<AssetTag> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the asset tags where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags
	*/
	public static List<AssetTag> findByGroupId(long groupId, int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the asset tags where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags
	*/
	public static List<AssetTag> findByGroupId(long groupId, int start,
		int end, OrderByComparator<AssetTag> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset tags where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset tags
	*/
	public static List<AssetTag> findByGroupId(long groupId, int start,
		int end, OrderByComparator<AssetTag> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first asset tag in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag
	* @throws NoSuchTagException if a matching asset tag could not be found
	*/
	public static AssetTag findByGroupId_First(long groupId,
		OrderByComparator<AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first asset tag in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	public static AssetTag fetchByGroupId_First(long groupId,
		OrderByComparator<AssetTag> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last asset tag in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag
	* @throws NoSuchTagException if a matching asset tag could not be found
	*/
	public static AssetTag findByGroupId_Last(long groupId,
		OrderByComparator<AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last asset tag in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	public static AssetTag fetchByGroupId_Last(long groupId,
		OrderByComparator<AssetTag> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the asset tags before and after the current asset tag in the ordered set where groupId = &#63;.
	*
	* @param tagId the primary key of the current asset tag
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag
	* @throws NoSuchTagException if a asset tag with the primary key could not be found
	*/
	public static AssetTag[] findByGroupId_PrevAndNext(long tagId,
		long groupId, OrderByComparator<AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(tagId, groupId, orderByComparator);
	}

	/**
	* Returns all the asset tags that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching asset tags that the user has permission to view
	*/
	public static List<AssetTag> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the asset tags that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags that the user has permission to view
	*/
	public static List<AssetTag> filterFindByGroupId(long groupId, int start,
		int end) {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the asset tags that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags that the user has permission to view
	*/
	public static List<AssetTag> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator<AssetTag> orderByComparator) {
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
	* @throws NoSuchTagException if a asset tag with the primary key could not be found
	*/
	public static AssetTag[] filterFindByGroupId_PrevAndNext(long tagId,
		long groupId, OrderByComparator<AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(tagId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the asset tags that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the matching asset tags that the user has permission to view
	*/
	public static List<AssetTag> filterFindByGroupId(long[] groupIds) {
		return getPersistence().filterFindByGroupId(groupIds);
	}

	/**
	* Returns a range of all the asset tags that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags that the user has permission to view
	*/
	public static List<AssetTag> filterFindByGroupId(long[] groupIds,
		int start, int end) {
		return getPersistence().filterFindByGroupId(groupIds, start, end);
	}

	/**
	* Returns an ordered range of all the asset tags that the user has permission to view where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags that the user has permission to view
	*/
	public static List<AssetTag> filterFindByGroupId(long[] groupIds,
		int start, int end, OrderByComparator<AssetTag> orderByComparator) {
		return getPersistence()
				   .filterFindByGroupId(groupIds, start, end, orderByComparator);
	}

	/**
	* Returns all the asset tags where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @return the matching asset tags
	*/
	public static List<AssetTag> findByGroupId(long[] groupIds) {
		return getPersistence().findByGroupId(groupIds);
	}

	/**
	* Returns a range of all the asset tags where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags
	*/
	public static List<AssetTag> findByGroupId(long[] groupIds, int start,
		int end) {
		return getPersistence().findByGroupId(groupIds, start, end);
	}

	/**
	* Returns an ordered range of all the asset tags where groupId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags
	*/
	public static List<AssetTag> findByGroupId(long[] groupIds, int start,
		int end, OrderByComparator<AssetTag> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupIds, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset tags where groupId = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset tags
	*/
	public static List<AssetTag> findByGroupId(long[] groupIds, int start,
		int end, OrderByComparator<AssetTag> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupIds, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Removes all the asset tags where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of asset tags where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching asset tags
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of asset tags where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching asset tags
	*/
	public static int countByGroupId(long[] groupIds) {
		return getPersistence().countByGroupId(groupIds);
	}

	/**
	* Returns the number of asset tags that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching asset tags that the user has permission to view
	*/
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns the number of asset tags that the user has permission to view where groupId = any &#63;.
	*
	* @param groupIds the group IDs
	* @return the number of matching asset tags that the user has permission to view
	*/
	public static int filterCountByGroupId(long[] groupIds) {
		return getPersistence().filterCountByGroupId(groupIds);
	}

	/**
	* Returns the asset tag where groupId = &#63; and name = &#63; or throws a {@link NoSuchTagException} if it could not be found.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching asset tag
	* @throws NoSuchTagException if a matching asset tag could not be found
	*/
	public static AssetTag findByG_N(long groupId, java.lang.String name)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().findByG_N(groupId, name);
	}

	/**
	* Returns the asset tag where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	public static AssetTag fetchByG_N(long groupId, java.lang.String name) {
		return getPersistence().fetchByG_N(groupId, name);
	}

	/**
	* Returns the asset tag where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param name the name
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	public static AssetTag fetchByG_N(long groupId, java.lang.String name,
		boolean retrieveFromCache) {
		return getPersistence().fetchByG_N(groupId, name, retrieveFromCache);
	}

	/**
	* Removes the asset tag where groupId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the asset tag that was removed
	*/
	public static AssetTag removeByG_N(long groupId, java.lang.String name)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().removeByG_N(groupId, name);
	}

	/**
	* Returns the number of asset tags where groupId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching asset tags
	*/
	public static int countByG_N(long groupId, java.lang.String name) {
		return getPersistence().countByG_N(groupId, name);
	}

	/**
	* Returns all the asset tags where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching asset tags
	*/
	public static List<AssetTag> findByG_LikeN(long groupId,
		java.lang.String name) {
		return getPersistence().findByG_LikeN(groupId, name);
	}

	/**
	* Returns a range of all the asset tags where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags
	*/
	public static List<AssetTag> findByG_LikeN(long groupId,
		java.lang.String name, int start, int end) {
		return getPersistence().findByG_LikeN(groupId, name, start, end);
	}

	/**
	* Returns an ordered range of all the asset tags where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags
	*/
	public static List<AssetTag> findByG_LikeN(long groupId,
		java.lang.String name, int start, int end,
		OrderByComparator<AssetTag> orderByComparator) {
		return getPersistence()
				   .findByG_LikeN(groupId, name, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset tags where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset tags
	*/
	public static List<AssetTag> findByG_LikeN(long groupId,
		java.lang.String name, int start, int end,
		OrderByComparator<AssetTag> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_LikeN(groupId, name, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first asset tag in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag
	* @throws NoSuchTagException if a matching asset tag could not be found
	*/
	public static AssetTag findByG_LikeN_First(long groupId,
		java.lang.String name, OrderByComparator<AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence()
				   .findByG_LikeN_First(groupId, name, orderByComparator);
	}

	/**
	* Returns the first asset tag in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	public static AssetTag fetchByG_LikeN_First(long groupId,
		java.lang.String name, OrderByComparator<AssetTag> orderByComparator) {
		return getPersistence()
				   .fetchByG_LikeN_First(groupId, name, orderByComparator);
	}

	/**
	* Returns the last asset tag in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag
	* @throws NoSuchTagException if a matching asset tag could not be found
	*/
	public static AssetTag findByG_LikeN_Last(long groupId,
		java.lang.String name, OrderByComparator<AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence()
				   .findByG_LikeN_Last(groupId, name, orderByComparator);
	}

	/**
	* Returns the last asset tag in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag, or <code>null</code> if a matching asset tag could not be found
	*/
	public static AssetTag fetchByG_LikeN_Last(long groupId,
		java.lang.String name, OrderByComparator<AssetTag> orderByComparator) {
		return getPersistence()
				   .fetchByG_LikeN_Last(groupId, name, orderByComparator);
	}

	/**
	* Returns the asset tags before and after the current asset tag in the ordered set where groupId = &#63; and name LIKE &#63;.
	*
	* @param tagId the primary key of the current asset tag
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag
	* @throws NoSuchTagException if a asset tag with the primary key could not be found
	*/
	public static AssetTag[] findByG_LikeN_PrevAndNext(long tagId,
		long groupId, java.lang.String name,
		OrderByComparator<AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence()
				   .findByG_LikeN_PrevAndNext(tagId, groupId, name,
			orderByComparator);
	}

	/**
	* Returns all the asset tags that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the matching asset tags that the user has permission to view
	*/
	public static List<AssetTag> filterFindByG_LikeN(long groupId,
		java.lang.String name) {
		return getPersistence().filterFindByG_LikeN(groupId, name);
	}

	/**
	* Returns a range of all the asset tags that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags that the user has permission to view
	*/
	public static List<AssetTag> filterFindByG_LikeN(long groupId,
		java.lang.String name, int start, int end) {
		return getPersistence().filterFindByG_LikeN(groupId, name, start, end);
	}

	/**
	* Returns an ordered range of all the asset tags that the user has permissions to view where groupId = &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags that the user has permission to view
	*/
	public static List<AssetTag> filterFindByG_LikeN(long groupId,
		java.lang.String name, int start, int end,
		OrderByComparator<AssetTag> orderByComparator) {
		return getPersistence()
				   .filterFindByG_LikeN(groupId, name, start, end,
			orderByComparator);
	}

	/**
	* Returns the asset tags before and after the current asset tag in the ordered set of asset tags that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param tagId the primary key of the current asset tag
	* @param groupId the group ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag
	* @throws NoSuchTagException if a asset tag with the primary key could not be found
	*/
	public static AssetTag[] filterFindByG_LikeN_PrevAndNext(long tagId,
		long groupId, java.lang.String name,
		OrderByComparator<AssetTag> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence()
				   .filterFindByG_LikeN_PrevAndNext(tagId, groupId, name,
			orderByComparator);
	}

	/**
	* Returns all the asset tags that the user has permission to view where groupId = any &#63; and name LIKE &#63;.
	*
	* @param groupIds the group IDs
	* @param name the name
	* @return the matching asset tags that the user has permission to view
	*/
	public static List<AssetTag> filterFindByG_LikeN(long[] groupIds,
		java.lang.String name) {
		return getPersistence().filterFindByG_LikeN(groupIds, name);
	}

	/**
	* Returns a range of all the asset tags that the user has permission to view where groupId = any &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param name the name
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags that the user has permission to view
	*/
	public static List<AssetTag> filterFindByG_LikeN(long[] groupIds,
		java.lang.String name, int start, int end) {
		return getPersistence().filterFindByG_LikeN(groupIds, name, start, end);
	}

	/**
	* Returns an ordered range of all the asset tags that the user has permission to view where groupId = any &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param name the name
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags that the user has permission to view
	*/
	public static List<AssetTag> filterFindByG_LikeN(long[] groupIds,
		java.lang.String name, int start, int end,
		OrderByComparator<AssetTag> orderByComparator) {
		return getPersistence()
				   .filterFindByG_LikeN(groupIds, name, start, end,
			orderByComparator);
	}

	/**
	* Returns all the asset tags where groupId = any &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param name the name
	* @return the matching asset tags
	*/
	public static List<AssetTag> findByG_LikeN(long[] groupIds,
		java.lang.String name) {
		return getPersistence().findByG_LikeN(groupIds, name);
	}

	/**
	* Returns a range of all the asset tags where groupId = any &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param name the name
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of matching asset tags
	*/
	public static List<AssetTag> findByG_LikeN(long[] groupIds,
		java.lang.String name, int start, int end) {
		return getPersistence().findByG_LikeN(groupIds, name, start, end);
	}

	/**
	* Returns an ordered range of all the asset tags where groupId = any &#63; and name LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param name the name
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tags
	*/
	public static List<AssetTag> findByG_LikeN(long[] groupIds,
		java.lang.String name, int start, int end,
		OrderByComparator<AssetTag> orderByComparator) {
		return getPersistence()
				   .findByG_LikeN(groupIds, name, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset tags where groupId = &#63; and name LIKE &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param name the name
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset tags
	*/
	public static List<AssetTag> findByG_LikeN(long[] groupIds,
		java.lang.String name, int start, int end,
		OrderByComparator<AssetTag> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_LikeN(groupIds, name, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the asset tags where groupId = &#63; and name LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param name the name
	*/
	public static void removeByG_LikeN(long groupId, java.lang.String name) {
		getPersistence().removeByG_LikeN(groupId, name);
	}

	/**
	* Returns the number of asset tags where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching asset tags
	*/
	public static int countByG_LikeN(long groupId, java.lang.String name) {
		return getPersistence().countByG_LikeN(groupId, name);
	}

	/**
	* Returns the number of asset tags where groupId = any &#63; and name LIKE &#63;.
	*
	* @param groupIds the group IDs
	* @param name the name
	* @return the number of matching asset tags
	*/
	public static int countByG_LikeN(long[] groupIds, java.lang.String name) {
		return getPersistence().countByG_LikeN(groupIds, name);
	}

	/**
	* Returns the number of asset tags that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	*
	* @param groupId the group ID
	* @param name the name
	* @return the number of matching asset tags that the user has permission to view
	*/
	public static int filterCountByG_LikeN(long groupId, java.lang.String name) {
		return getPersistence().filterCountByG_LikeN(groupId, name);
	}

	/**
	* Returns the number of asset tags that the user has permission to view where groupId = any &#63; and name LIKE &#63;.
	*
	* @param groupIds the group IDs
	* @param name the name
	* @return the number of matching asset tags that the user has permission to view
	*/
	public static int filterCountByG_LikeN(long[] groupIds,
		java.lang.String name) {
		return getPersistence().filterCountByG_LikeN(groupIds, name);
	}

	/**
	* Caches the asset tag in the entity cache if it is enabled.
	*
	* @param assetTag the asset tag
	*/
	public static void cacheResult(AssetTag assetTag) {
		getPersistence().cacheResult(assetTag);
	}

	/**
	* Caches the asset tags in the entity cache if it is enabled.
	*
	* @param assetTags the asset tags
	*/
	public static void cacheResult(List<AssetTag> assetTags) {
		getPersistence().cacheResult(assetTags);
	}

	/**
	* Creates a new asset tag with the primary key. Does not add the asset tag to the database.
	*
	* @param tagId the primary key for the new asset tag
	* @return the new asset tag
	*/
	public static AssetTag create(long tagId) {
		return getPersistence().create(tagId);
	}

	/**
	* Removes the asset tag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag that was removed
	* @throws NoSuchTagException if a asset tag with the primary key could not be found
	*/
	public static AssetTag remove(long tagId)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().remove(tagId);
	}

	public static AssetTag updateImpl(AssetTag assetTag) {
		return getPersistence().updateImpl(assetTag);
	}

	/**
	* Returns the asset tag with the primary key or throws a {@link NoSuchTagException} if it could not be found.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag
	* @throws NoSuchTagException if a asset tag with the primary key could not be found
	*/
	public static AssetTag findByPrimaryKey(long tagId)
		throws com.liferay.portlet.asset.NoSuchTagException {
		return getPersistence().findByPrimaryKey(tagId);
	}

	/**
	* Returns the asset tag with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param tagId the primary key of the asset tag
	* @return the asset tag, or <code>null</code> if a asset tag with the primary key could not be found
	*/
	public static AssetTag fetchByPrimaryKey(long tagId) {
		return getPersistence().fetchByPrimaryKey(tagId);
	}

	public static java.util.Map<java.io.Serializable, AssetTag> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the asset tags.
	*
	* @return the asset tags
	*/
	public static List<AssetTag> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the asset tags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of asset tags
	*/
	public static List<AssetTag> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the asset tags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset tags
	*/
	public static List<AssetTag> findAll(int start, int end,
		OrderByComparator<AssetTag> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset tags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset tags
	*/
	public static List<AssetTag> findAll(int start, int end,
		OrderByComparator<AssetTag> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the asset tags from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset tags.
	*
	* @return the number of asset tags
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	* Returns the primaryKeys of asset entries associated with the asset tag.
	*
	* @param pk the primary key of the asset tag
	* @return long[] of the primaryKeys of asset entries associated with the asset tag
	*/
	public static long[] getAssetEntryPrimaryKeys(long pk) {
		return getPersistence().getAssetEntryPrimaryKeys(pk);
	}

	/**
	* Returns all the asset entries associated with the asset tag.
	*
	* @param pk the primary key of the asset tag
	* @return the asset entries associated with the asset tag
	*/
	public static List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk) {
		return getPersistence().getAssetEntries(pk);
	}

	/**
	* Returns a range of all the asset entries associated with the asset tag.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset tag
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @return the range of asset entries associated with the asset tag
	*/
	public static List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end) {
		return getPersistence().getAssetEntries(pk, start, end);
	}

	/**
	* Returns an ordered range of all the asset entries associated with the asset tag.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset tag
	* @param start the lower bound of the range of asset tags
	* @param end the upper bound of the range of asset tags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset entries associated with the asset tag
	*/
	public static List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portlet.asset.model.AssetEntry> orderByComparator) {
		return getPersistence()
				   .getAssetEntries(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of asset entries associated with the asset tag.
	*
	* @param pk the primary key of the asset tag
	* @return the number of asset entries associated with the asset tag
	*/
	public static int getAssetEntriesSize(long pk) {
		return getPersistence().getAssetEntriesSize(pk);
	}

	/**
	* Returns <code>true</code> if the asset entry is associated with the asset tag.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPK the primary key of the asset entry
	* @return <code>true</code> if the asset entry is associated with the asset tag; <code>false</code> otherwise
	*/
	public static boolean containsAssetEntry(long pk, long assetEntryPK) {
		return getPersistence().containsAssetEntry(pk, assetEntryPK);
	}

	/**
	* Returns <code>true</code> if the asset tag has any asset entries associated with it.
	*
	* @param pk the primary key of the asset tag to check for associations with asset entries
	* @return <code>true</code> if the asset tag has any asset entries associated with it; <code>false</code> otherwise
	*/
	public static boolean containsAssetEntries(long pk) {
		return getPersistence().containsAssetEntries(pk);
	}

	/**
	* Adds an association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPK the primary key of the asset entry
	*/
	public static void addAssetEntry(long pk, long assetEntryPK) {
		getPersistence().addAssetEntry(pk, assetEntryPK);
	}

	/**
	* Adds an association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntry the asset entry
	*/
	public static void addAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry) {
		getPersistence().addAssetEntry(pk, assetEntry);
	}

	/**
	* Adds an association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPKs the primary keys of the asset entries
	*/
	public static void addAssetEntries(long pk, long[] assetEntryPKs) {
		getPersistence().addAssetEntries(pk, assetEntryPKs);
	}

	/**
	* Adds an association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntries the asset entries
	*/
	public static void addAssetEntries(long pk,
		List<com.liferay.portlet.asset.model.AssetEntry> assetEntries) {
		getPersistence().addAssetEntries(pk, assetEntries);
	}

	/**
	* Clears all associations between the asset tag and its asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag to clear the associated asset entries from
	*/
	public static void clearAssetEntries(long pk) {
		getPersistence().clearAssetEntries(pk);
	}

	/**
	* Removes the association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPK the primary key of the asset entry
	*/
	public static void removeAssetEntry(long pk, long assetEntryPK) {
		getPersistence().removeAssetEntry(pk, assetEntryPK);
	}

	/**
	* Removes the association between the asset tag and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntry the asset entry
	*/
	public static void removeAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry) {
		getPersistence().removeAssetEntry(pk, assetEntry);
	}

	/**
	* Removes the association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPKs the primary keys of the asset entries
	*/
	public static void removeAssetEntries(long pk, long[] assetEntryPKs) {
		getPersistence().removeAssetEntries(pk, assetEntryPKs);
	}

	/**
	* Removes the association between the asset tag and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntries the asset entries
	*/
	public static void removeAssetEntries(long pk,
		List<com.liferay.portlet.asset.model.AssetEntry> assetEntries) {
		getPersistence().removeAssetEntries(pk, assetEntries);
	}

	/**
	* Sets the asset entries associated with the asset tag, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntryPKs the primary keys of the asset entries to be associated with the asset tag
	*/
	public static void setAssetEntries(long pk, long[] assetEntryPKs) {
		getPersistence().setAssetEntries(pk, assetEntryPKs);
	}

	/**
	* Sets the asset entries associated with the asset tag, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset tag
	* @param assetEntries the asset entries to be associated with the asset tag
	*/
	public static void setAssetEntries(long pk,
		List<com.liferay.portlet.asset.model.AssetEntry> assetEntries) {
		getPersistence().setAssetEntries(pk, assetEntries);
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static AssetTagPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetTagPersistence)PortalBeanLocatorUtil.locate(AssetTagPersistence.class.getName());

			ReferenceRegistry.registerReference(AssetTagUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static AssetTagPersistence _persistence;
}