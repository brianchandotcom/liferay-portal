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

import com.liferay.portlet.asset.model.AssetEntry;

import java.util.List;

/**
 * The persistence utility for the asset entry service. This utility wraps {@link com.liferay.portlet.asset.service.persistence.impl.AssetEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryPersistence
 * @see com.liferay.portlet.asset.service.persistence.impl.AssetEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class AssetEntryUtil {
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
	public static void clearCache(AssetEntry assetEntry) {
		getPersistence().clearCache(assetEntry);
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
	public static List<AssetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static AssetEntry update(AssetEntry assetEntry) {
		return getPersistence().update(assetEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static AssetEntry update(AssetEntry assetEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(assetEntry, serviceContext);
	}

	/**
	* Returns all the asset entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching asset entries
	*/
	public static List<AssetEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the asset entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	*/
	public static List<AssetEntry> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the asset entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	*/
	public static List<AssetEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entries
	*/
	public static List<AssetEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<AssetEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first asset entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public static AssetEntry findByGroupId_First(long groupId,
		OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first asset entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public static AssetEntry fetchByGroupId_First(long groupId,
		OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public static AssetEntry findByGroupId_Last(long groupId,
		OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public static AssetEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where groupId = &#63;.
	*
	* @param entryId the primary key of the current asset entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws NoSuchEntryException if a asset entry with the primary key could not be found
	*/
	public static AssetEntry[] findByGroupId_PrevAndNext(long entryId,
		long groupId, OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(entryId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the asset entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of asset entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching asset entries
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the asset entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching asset entries
	*/
	public static List<AssetEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the asset entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	*/
	public static List<AssetEntry> findByCompanyId(long companyId, int start,
		int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the asset entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	*/
	public static List<AssetEntry> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entries
	*/
	public static List<AssetEntry> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<AssetEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first asset entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public static AssetEntry findByCompanyId_First(long companyId,
		OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first asset entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public static AssetEntry fetchByCompanyId_First(long companyId,
		OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public static AssetEntry findByCompanyId_Last(long companyId,
		OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public static AssetEntry fetchByCompanyId_Last(long companyId,
		OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where companyId = &#63;.
	*
	* @param entryId the primary key of the current asset entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws NoSuchEntryException if a asset entry with the primary key could not be found
	*/
	public static AssetEntry[] findByCompanyId_PrevAndNext(long entryId,
		long companyId, OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(entryId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the asset entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of asset entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching asset entries
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the asset entries where visible = &#63;.
	*
	* @param visible the visible
	* @return the matching asset entries
	*/
	public static List<AssetEntry> findByVisible(boolean visible) {
		return getPersistence().findByVisible(visible);
	}

	/**
	* Returns a range of all the asset entries where visible = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param visible the visible
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	*/
	public static List<AssetEntry> findByVisible(boolean visible, int start,
		int end) {
		return getPersistence().findByVisible(visible, start, end);
	}

	/**
	* Returns an ordered range of all the asset entries where visible = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param visible the visible
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	*/
	public static List<AssetEntry> findByVisible(boolean visible, int start,
		int end, OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence()
				   .findByVisible(visible, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset entries where visible = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param visible the visible
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entries
	*/
	public static List<AssetEntry> findByVisible(boolean visible, int start,
		int end, OrderByComparator<AssetEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByVisible(visible, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first asset entry in the ordered set where visible = &#63;.
	*
	* @param visible the visible
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public static AssetEntry findByVisible_First(boolean visible,
		OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().findByVisible_First(visible, orderByComparator);
	}

	/**
	* Returns the first asset entry in the ordered set where visible = &#63;.
	*
	* @param visible the visible
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public static AssetEntry fetchByVisible_First(boolean visible,
		OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence().fetchByVisible_First(visible, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where visible = &#63;.
	*
	* @param visible the visible
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public static AssetEntry findByVisible_Last(boolean visible,
		OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().findByVisible_Last(visible, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where visible = &#63;.
	*
	* @param visible the visible
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public static AssetEntry fetchByVisible_Last(boolean visible,
		OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence().fetchByVisible_Last(visible, orderByComparator);
	}

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where visible = &#63;.
	*
	* @param entryId the primary key of the current asset entry
	* @param visible the visible
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws NoSuchEntryException if a asset entry with the primary key could not be found
	*/
	public static AssetEntry[] findByVisible_PrevAndNext(long entryId,
		boolean visible, OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByVisible_PrevAndNext(entryId, visible,
			orderByComparator);
	}

	/**
	* Removes all the asset entries where visible = &#63; from the database.
	*
	* @param visible the visible
	*/
	public static void removeByVisible(boolean visible) {
		getPersistence().removeByVisible(visible);
	}

	/**
	* Returns the number of asset entries where visible = &#63;.
	*
	* @param visible the visible
	* @return the number of matching asset entries
	*/
	public static int countByVisible(boolean visible) {
		return getPersistence().countByVisible(visible);
	}

	/**
	* Returns all the asset entries where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @return the matching asset entries
	*/
	public static List<AssetEntry> findByPublishDate(java.util.Date publishDate) {
		return getPersistence().findByPublishDate(publishDate);
	}

	/**
	* Returns a range of all the asset entries where publishDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param publishDate the publish date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	*/
	public static List<AssetEntry> findByPublishDate(
		java.util.Date publishDate, int start, int end) {
		return getPersistence().findByPublishDate(publishDate, start, end);
	}

	/**
	* Returns an ordered range of all the asset entries where publishDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param publishDate the publish date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	*/
	public static List<AssetEntry> findByPublishDate(
		java.util.Date publishDate, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence()
				   .findByPublishDate(publishDate, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset entries where publishDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param publishDate the publish date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entries
	*/
	public static List<AssetEntry> findByPublishDate(
		java.util.Date publishDate, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByPublishDate(publishDate, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset entry in the ordered set where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public static AssetEntry findByPublishDate_First(
		java.util.Date publishDate,
		OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByPublishDate_First(publishDate, orderByComparator);
	}

	/**
	* Returns the first asset entry in the ordered set where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public static AssetEntry fetchByPublishDate_First(
		java.util.Date publishDate,
		OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence()
				   .fetchByPublishDate_First(publishDate, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public static AssetEntry findByPublishDate_Last(
		java.util.Date publishDate,
		OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByPublishDate_Last(publishDate, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public static AssetEntry fetchByPublishDate_Last(
		java.util.Date publishDate,
		OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence()
				   .fetchByPublishDate_Last(publishDate, orderByComparator);
	}

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where publishDate = &#63;.
	*
	* @param entryId the primary key of the current asset entry
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws NoSuchEntryException if a asset entry with the primary key could not be found
	*/
	public static AssetEntry[] findByPublishDate_PrevAndNext(long entryId,
		java.util.Date publishDate,
		OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByPublishDate_PrevAndNext(entryId, publishDate,
			orderByComparator);
	}

	/**
	* Removes all the asset entries where publishDate = &#63; from the database.
	*
	* @param publishDate the publish date
	*/
	public static void removeByPublishDate(java.util.Date publishDate) {
		getPersistence().removeByPublishDate(publishDate);
	}

	/**
	* Returns the number of asset entries where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @return the number of matching asset entries
	*/
	public static int countByPublishDate(java.util.Date publishDate) {
		return getPersistence().countByPublishDate(publishDate);
	}

	/**
	* Returns all the asset entries where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @return the matching asset entries
	*/
	public static List<AssetEntry> findByExpirationDate(
		java.util.Date expirationDate) {
		return getPersistence().findByExpirationDate(expirationDate);
	}

	/**
	* Returns a range of all the asset entries where expirationDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	*/
	public static List<AssetEntry> findByExpirationDate(
		java.util.Date expirationDate, int start, int end) {
		return getPersistence().findByExpirationDate(expirationDate, start, end);
	}

	/**
	* Returns an ordered range of all the asset entries where expirationDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	*/
	public static List<AssetEntry> findByExpirationDate(
		java.util.Date expirationDate, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence()
				   .findByExpirationDate(expirationDate, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset entries where expirationDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entries
	*/
	public static List<AssetEntry> findByExpirationDate(
		java.util.Date expirationDate, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByExpirationDate(expirationDate, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset entry in the ordered set where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public static AssetEntry findByExpirationDate_First(
		java.util.Date expirationDate,
		OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByExpirationDate_First(expirationDate, orderByComparator);
	}

	/**
	* Returns the first asset entry in the ordered set where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public static AssetEntry fetchByExpirationDate_First(
		java.util.Date expirationDate,
		OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence()
				   .fetchByExpirationDate_First(expirationDate,
			orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public static AssetEntry findByExpirationDate_Last(
		java.util.Date expirationDate,
		OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByExpirationDate_Last(expirationDate, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public static AssetEntry fetchByExpirationDate_Last(
		java.util.Date expirationDate,
		OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence()
				   .fetchByExpirationDate_Last(expirationDate, orderByComparator);
	}

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where expirationDate = &#63;.
	*
	* @param entryId the primary key of the current asset entry
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws NoSuchEntryException if a asset entry with the primary key could not be found
	*/
	public static AssetEntry[] findByExpirationDate_PrevAndNext(long entryId,
		java.util.Date expirationDate,
		OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByExpirationDate_PrevAndNext(entryId, expirationDate,
			orderByComparator);
	}

	/**
	* Removes all the asset entries where expirationDate = &#63; from the database.
	*
	* @param expirationDate the expiration date
	*/
	public static void removeByExpirationDate(java.util.Date expirationDate) {
		getPersistence().removeByExpirationDate(expirationDate);
	}

	/**
	* Returns the number of asset entries where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @return the number of matching asset entries
	*/
	public static int countByExpirationDate(java.util.Date expirationDate) {
		return getPersistence().countByExpirationDate(expirationDate);
	}

	/**
	* Returns all the asset entries where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @return the matching asset entries
	*/
	public static List<AssetEntry> findByLayoutUuid(java.lang.String layoutUuid) {
		return getPersistence().findByLayoutUuid(layoutUuid);
	}

	/**
	* Returns a range of all the asset entries where layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	*/
	public static List<AssetEntry> findByLayoutUuid(
		java.lang.String layoutUuid, int start, int end) {
		return getPersistence().findByLayoutUuid(layoutUuid, start, end);
	}

	/**
	* Returns an ordered range of all the asset entries where layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	*/
	public static List<AssetEntry> findByLayoutUuid(
		java.lang.String layoutUuid, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence()
				   .findByLayoutUuid(layoutUuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset entries where layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entries
	*/
	public static List<AssetEntry> findByLayoutUuid(
		java.lang.String layoutUuid, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByLayoutUuid(layoutUuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first asset entry in the ordered set where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public static AssetEntry findByLayoutUuid_First(
		java.lang.String layoutUuid,
		OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByLayoutUuid_First(layoutUuid, orderByComparator);
	}

	/**
	* Returns the first asset entry in the ordered set where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public static AssetEntry fetchByLayoutUuid_First(
		java.lang.String layoutUuid,
		OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutUuid_First(layoutUuid, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public static AssetEntry findByLayoutUuid_Last(
		java.lang.String layoutUuid,
		OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByLayoutUuid_Last(layoutUuid, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public static AssetEntry fetchByLayoutUuid_Last(
		java.lang.String layoutUuid,
		OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence()
				   .fetchByLayoutUuid_Last(layoutUuid, orderByComparator);
	}

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where layoutUuid = &#63;.
	*
	* @param entryId the primary key of the current asset entry
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws NoSuchEntryException if a asset entry with the primary key could not be found
	*/
	public static AssetEntry[] findByLayoutUuid_PrevAndNext(long entryId,
		java.lang.String layoutUuid,
		OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByLayoutUuid_PrevAndNext(entryId, layoutUuid,
			orderByComparator);
	}

	/**
	* Removes all the asset entries where layoutUuid = &#63; from the database.
	*
	* @param layoutUuid the layout uuid
	*/
	public static void removeByLayoutUuid(java.lang.String layoutUuid) {
		getPersistence().removeByLayoutUuid(layoutUuid);
	}

	/**
	* Returns the number of asset entries where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @return the number of matching asset entries
	*/
	public static int countByLayoutUuid(java.lang.String layoutUuid) {
		return getPersistence().countByLayoutUuid(layoutUuid);
	}

	/**
	* Returns the asset entry where groupId = &#63; and classUuid = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @return the matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public static AssetEntry findByG_CU(long groupId, java.lang.String classUuid)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().findByG_CU(groupId, classUuid);
	}

	/**
	* Returns the asset entry where groupId = &#63; and classUuid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public static AssetEntry fetchByG_CU(long groupId,
		java.lang.String classUuid) {
		return getPersistence().fetchByG_CU(groupId, classUuid);
	}

	/**
	* Returns the asset entry where groupId = &#63; and classUuid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public static AssetEntry fetchByG_CU(long groupId,
		java.lang.String classUuid, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_CU(groupId, classUuid, retrieveFromCache);
	}

	/**
	* Removes the asset entry where groupId = &#63; and classUuid = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @return the asset entry that was removed
	*/
	public static AssetEntry removeByG_CU(long groupId,
		java.lang.String classUuid)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().removeByG_CU(groupId, classUuid);
	}

	/**
	* Returns the number of asset entries where groupId = &#63; and classUuid = &#63;.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @return the number of matching asset entries
	*/
	public static int countByG_CU(long groupId, java.lang.String classUuid) {
		return getPersistence().countByG_CU(groupId, classUuid);
	}

	/**
	* Returns the asset entry where classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching asset entry
	* @throws NoSuchEntryException if a matching asset entry could not be found
	*/
	public static AssetEntry findByC_C(long classNameId, long classPK)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns the asset entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public static AssetEntry fetchByC_C(long classNameId, long classPK) {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	/**
	* Returns the asset entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	*/
	public static AssetEntry fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_C(classNameId, classPK, retrieveFromCache);
	}

	/**
	* Removes the asset entry where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the asset entry that was removed
	*/
	public static AssetEntry removeByC_C(long classNameId, long classPK)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of asset entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching asset entries
	*/
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Caches the asset entry in the entity cache if it is enabled.
	*
	* @param assetEntry the asset entry
	*/
	public static void cacheResult(AssetEntry assetEntry) {
		getPersistence().cacheResult(assetEntry);
	}

	/**
	* Caches the asset entries in the entity cache if it is enabled.
	*
	* @param assetEntries the asset entries
	*/
	public static void cacheResult(List<AssetEntry> assetEntries) {
		getPersistence().cacheResult(assetEntries);
	}

	/**
	* Creates a new asset entry with the primary key. Does not add the asset entry to the database.
	*
	* @param entryId the primary key for the new asset entry
	* @return the new asset entry
	*/
	public static AssetEntry create(long entryId) {
		return getPersistence().create(entryId);
	}

	/**
	* Removes the asset entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset entry that was removed
	* @throws NoSuchEntryException if a asset entry with the primary key could not be found
	*/
	public static AssetEntry remove(long entryId)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().remove(entryId);
	}

	public static AssetEntry updateImpl(AssetEntry assetEntry) {
		return getPersistence().updateImpl(assetEntry);
	}

	/**
	* Returns the asset entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset entry
	* @throws NoSuchEntryException if a asset entry with the primary key could not be found
	*/
	public static AssetEntry findByPrimaryKey(long entryId)
		throws com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	/**
	* Returns the asset entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset entry, or <code>null</code> if a asset entry with the primary key could not be found
	*/
	public static AssetEntry fetchByPrimaryKey(long entryId) {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	public static java.util.Map<java.io.Serializable, AssetEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the asset entries.
	*
	* @return the asset entries
	*/
	public static List<AssetEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the asset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of asset entries
	*/
	public static List<AssetEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the asset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset entries
	*/
	public static List<AssetEntry> findAll(int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset entries
	*/
	public static List<AssetEntry> findAll(int start, int end,
		OrderByComparator<AssetEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the asset entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset entries.
	*
	* @return the number of asset entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	* Returns the primaryKeys of asset categories associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return long[] of the primaryKeys of asset categories associated with the asset entry
	*/
	public static long[] getAssetCategoryPrimaryKeys(long pk) {
		return getPersistence().getAssetCategoryPrimaryKeys(pk);
	}

	/**
	* Returns all the asset categories associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return the asset categories associated with the asset entry
	*/
	public static List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk) {
		return getPersistence().getAssetCategories(pk);
	}

	/**
	* Returns a range of all the asset categories associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset entry
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of asset categories associated with the asset entry
	*/
	public static List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end) {
		return getPersistence().getAssetCategories(pk, start, end);
	}

	/**
	* Returns an ordered range of all the asset categories associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset entry
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset categories associated with the asset entry
	*/
	public static List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portlet.asset.model.AssetCategory> orderByComparator) {
		return getPersistence()
				   .getAssetCategories(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of asset categories associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return the number of asset categories associated with the asset entry
	*/
	public static int getAssetCategoriesSize(long pk) {
		return getPersistence().getAssetCategoriesSize(pk);
	}

	/**
	* Returns <code>true</code> if the asset category is associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPK the primary key of the asset category
	* @return <code>true</code> if the asset category is associated with the asset entry; <code>false</code> otherwise
	*/
	public static boolean containsAssetCategory(long pk, long assetCategoryPK) {
		return getPersistence().containsAssetCategory(pk, assetCategoryPK);
	}

	/**
	* Returns <code>true</code> if the asset entry has any asset categories associated with it.
	*
	* @param pk the primary key of the asset entry to check for associations with asset categories
	* @return <code>true</code> if the asset entry has any asset categories associated with it; <code>false</code> otherwise
	*/
	public static boolean containsAssetCategories(long pk) {
		return getPersistence().containsAssetCategories(pk);
	}

	/**
	* Adds an association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPK the primary key of the asset category
	*/
	public static void addAssetCategory(long pk, long assetCategoryPK) {
		getPersistence().addAssetCategory(pk, assetCategoryPK);
	}

	/**
	* Adds an association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategory the asset category
	*/
	public static void addAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory) {
		getPersistence().addAssetCategory(pk, assetCategory);
	}

	/**
	* Adds an association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPKs the primary keys of the asset categories
	*/
	public static void addAssetCategories(long pk, long[] assetCategoryPKs) {
		getPersistence().addAssetCategories(pk, assetCategoryPKs);
	}

	/**
	* Adds an association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategories the asset categories
	*/
	public static void addAssetCategories(long pk,
		List<com.liferay.portlet.asset.model.AssetCategory> assetCategories) {
		getPersistence().addAssetCategories(pk, assetCategories);
	}

	/**
	* Clears all associations between the asset entry and its asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry to clear the associated asset categories from
	*/
	public static void clearAssetCategories(long pk) {
		getPersistence().clearAssetCategories(pk);
	}

	/**
	* Removes the association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPK the primary key of the asset category
	*/
	public static void removeAssetCategory(long pk, long assetCategoryPK) {
		getPersistence().removeAssetCategory(pk, assetCategoryPK);
	}

	/**
	* Removes the association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategory the asset category
	*/
	public static void removeAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory) {
		getPersistence().removeAssetCategory(pk, assetCategory);
	}

	/**
	* Removes the association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPKs the primary keys of the asset categories
	*/
	public static void removeAssetCategories(long pk, long[] assetCategoryPKs) {
		getPersistence().removeAssetCategories(pk, assetCategoryPKs);
	}

	/**
	* Removes the association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategories the asset categories
	*/
	public static void removeAssetCategories(long pk,
		List<com.liferay.portlet.asset.model.AssetCategory> assetCategories) {
		getPersistence().removeAssetCategories(pk, assetCategories);
	}

	/**
	* Sets the asset categories associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPKs the primary keys of the asset categories to be associated with the asset entry
	*/
	public static void setAssetCategories(long pk, long[] assetCategoryPKs) {
		getPersistence().setAssetCategories(pk, assetCategoryPKs);
	}

	/**
	* Sets the asset categories associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategories the asset categories to be associated with the asset entry
	*/
	public static void setAssetCategories(long pk,
		List<com.liferay.portlet.asset.model.AssetCategory> assetCategories) {
		getPersistence().setAssetCategories(pk, assetCategories);
	}

	/**
	* Returns the primaryKeys of asset tags associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return long[] of the primaryKeys of asset tags associated with the asset entry
	*/
	public static long[] getAssetTagPrimaryKeys(long pk) {
		return getPersistence().getAssetTagPrimaryKeys(pk);
	}

	/**
	* Returns all the asset tags associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return the asset tags associated with the asset entry
	*/
	public static List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk) {
		return getPersistence().getAssetTags(pk);
	}

	/**
	* Returns a range of all the asset tags associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset entry
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of asset tags associated with the asset entry
	*/
	public static List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end) {
		return getPersistence().getAssetTags(pk, start, end);
	}

	/**
	* Returns an ordered range of all the asset tags associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset entry
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset tags associated with the asset entry
	*/
	public static List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portlet.asset.model.AssetTag> orderByComparator) {
		return getPersistence().getAssetTags(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of asset tags associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return the number of asset tags associated with the asset entry
	*/
	public static int getAssetTagsSize(long pk) {
		return getPersistence().getAssetTagsSize(pk);
	}

	/**
	* Returns <code>true</code> if the asset tag is associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPK the primary key of the asset tag
	* @return <code>true</code> if the asset tag is associated with the asset entry; <code>false</code> otherwise
	*/
	public static boolean containsAssetTag(long pk, long assetTagPK) {
		return getPersistence().containsAssetTag(pk, assetTagPK);
	}

	/**
	* Returns <code>true</code> if the asset entry has any asset tags associated with it.
	*
	* @param pk the primary key of the asset entry to check for associations with asset tags
	* @return <code>true</code> if the asset entry has any asset tags associated with it; <code>false</code> otherwise
	*/
	public static boolean containsAssetTags(long pk) {
		return getPersistence().containsAssetTags(pk);
	}

	/**
	* Adds an association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPK the primary key of the asset tag
	*/
	public static void addAssetTag(long pk, long assetTagPK) {
		getPersistence().addAssetTag(pk, assetTagPK);
	}

	/**
	* Adds an association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTag the asset tag
	*/
	public static void addAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag) {
		getPersistence().addAssetTag(pk, assetTag);
	}

	/**
	* Adds an association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPKs the primary keys of the asset tags
	*/
	public static void addAssetTags(long pk, long[] assetTagPKs) {
		getPersistence().addAssetTags(pk, assetTagPKs);
	}

	/**
	* Adds an association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTags the asset tags
	*/
	public static void addAssetTags(long pk,
		List<com.liferay.portlet.asset.model.AssetTag> assetTags) {
		getPersistence().addAssetTags(pk, assetTags);
	}

	/**
	* Clears all associations between the asset entry and its asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry to clear the associated asset tags from
	*/
	public static void clearAssetTags(long pk) {
		getPersistence().clearAssetTags(pk);
	}

	/**
	* Removes the association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPK the primary key of the asset tag
	*/
	public static void removeAssetTag(long pk, long assetTagPK) {
		getPersistence().removeAssetTag(pk, assetTagPK);
	}

	/**
	* Removes the association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTag the asset tag
	*/
	public static void removeAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag) {
		getPersistence().removeAssetTag(pk, assetTag);
	}

	/**
	* Removes the association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPKs the primary keys of the asset tags
	*/
	public static void removeAssetTags(long pk, long[] assetTagPKs) {
		getPersistence().removeAssetTags(pk, assetTagPKs);
	}

	/**
	* Removes the association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTags the asset tags
	*/
	public static void removeAssetTags(long pk,
		List<com.liferay.portlet.asset.model.AssetTag> assetTags) {
		getPersistence().removeAssetTags(pk, assetTags);
	}

	/**
	* Sets the asset tags associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPKs the primary keys of the asset tags to be associated with the asset entry
	*/
	public static void setAssetTags(long pk, long[] assetTagPKs) {
		getPersistence().setAssetTags(pk, assetTagPKs);
	}

	/**
	* Sets the asset tags associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTags the asset tags to be associated with the asset entry
	*/
	public static void setAssetTags(long pk,
		List<com.liferay.portlet.asset.model.AssetTag> assetTags) {
		getPersistence().setAssetTags(pk, assetTags);
	}

	public static AssetEntryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetEntryPersistence)PortalBeanLocatorUtil.locate(AssetEntryPersistence.class.getName());

			ReferenceRegistry.registerReference(AssetEntryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static AssetEntryPersistence _persistence;
}