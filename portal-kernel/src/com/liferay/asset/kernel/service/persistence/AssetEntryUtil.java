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

package com.liferay.asset.kernel.service.persistence;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the asset entry service. This utility wraps <code>com.liferay.portlet.asset.service.persistence.impl.AssetEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryPersistence
 * @generated
 */
public class AssetEntryUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(AssetEntry assetEntry) {
		getPersistence().clearCache(assetEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, AssetEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AssetEntry update(AssetEntry assetEntry) {
		return getPersistence().update(assetEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AssetEntry update(
		AssetEntry assetEntry, ServiceContext serviceContext) {

		return getPersistence().update(assetEntry, serviceContext);
	}

	/**
	 * Returns all the asset entries where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching asset entries
	 */
	public static List<AssetEntry> findByCTCollectionId(long ctCollectionId) {
		return getPersistence().findByCTCollectionId(ctCollectionId);
	}

	/**
	 * Returns a range of all the asset entries where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 */
	public static List<AssetEntry> findByCTCollectionId(
		long ctCollectionId, int start, int end) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset entries where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 */
	public static List<AssetEntry> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset entries where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entries
	 */
	public static List<AssetEntry> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	public static AssetEntry findByCTCollectionId_First(
			long ctCollectionId,
			OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByCTCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the first asset entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByCTCollectionId_First(
		long ctCollectionId, OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().fetchByCTCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last asset entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	public static AssetEntry findByCTCollectionId_Last(
			long ctCollectionId,
			OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByCTCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last asset entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByCTCollectionId_Last(
		long ctCollectionId, OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().fetchByCTCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the asset entries before and after the current asset entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param entryId the primary key of the current asset entry
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry
	 * @throws NoSuchEntryException if a asset entry with the primary key could not be found
	 */
	public static AssetEntry[] findByCTCollectionId_PrevAndNext(
			long entryId, long ctCollectionId,
			OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByCTCollectionId_PrevAndNext(
			entryId, ctCollectionId, orderByComparator);
	}

	/**
	 * Removes all the asset entries where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public static void removeByCTCollectionId(long ctCollectionId) {
		getPersistence().removeByCTCollectionId(ctCollectionId);
	}

	/**
	 * Returns the number of asset entries where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching asset entries
	 */
	public static int countByCTCollectionId(long ctCollectionId) {
		return getPersistence().countByCTCollectionId(ctCollectionId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 */
	public static List<AssetEntry> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 */
	public static List<AssetEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entries
	 */
	public static List<AssetEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	public static AssetEntry findByGroupId_First(
			long groupId, OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first asset entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByGroupId_First(
		long groupId, OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last asset entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	public static AssetEntry findByGroupId_Last(
			long groupId, OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last asset entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByGroupId_Last(
		long groupId, OrderByComparator<AssetEntry> orderByComparator) {

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
	public static AssetEntry[] findByGroupId_PrevAndNext(
			long entryId, long groupId,
			OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByGroupId_PrevAndNext(
			entryId, groupId, orderByComparator);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 */
	public static List<AssetEntry> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 */
	public static List<AssetEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entries
	 */
	public static List<AssetEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	public static AssetEntry findByCompanyId_First(
			long companyId, OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first asset entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last asset entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	public static AssetEntry findByCompanyId_Last(
			long companyId, OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last asset entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByCompanyId_Last(
		long companyId, OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
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
	public static AssetEntry[] findByCompanyId_PrevAndNext(
			long entryId, long companyId,
			OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByCompanyId_PrevAndNext(
			entryId, companyId, orderByComparator);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param visible the visible
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 */
	public static List<AssetEntry> findByVisible(
		boolean visible, int start, int end) {

		return getPersistence().findByVisible(visible, start, end);
	}

	/**
	 * Returns an ordered range of all the asset entries where visible = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param visible the visible
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 */
	public static List<AssetEntry> findByVisible(
		boolean visible, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().findByVisible(
			visible, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset entries where visible = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param visible the visible
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entries
	 */
	public static List<AssetEntry> findByVisible(
		boolean visible, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByVisible(
			visible, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset entry in the ordered set where visible = &#63;.
	 *
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	public static AssetEntry findByVisible_First(
			boolean visible, OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByVisible_First(visible, orderByComparator);
	}

	/**
	 * Returns the first asset entry in the ordered set where visible = &#63;.
	 *
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByVisible_First(
		boolean visible, OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().fetchByVisible_First(
			visible, orderByComparator);
	}

	/**
	 * Returns the last asset entry in the ordered set where visible = &#63;.
	 *
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	public static AssetEntry findByVisible_Last(
			boolean visible, OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByVisible_Last(visible, orderByComparator);
	}

	/**
	 * Returns the last asset entry in the ordered set where visible = &#63;.
	 *
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByVisible_Last(
		boolean visible, OrderByComparator<AssetEntry> orderByComparator) {

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
	public static AssetEntry[] findByVisible_PrevAndNext(
			long entryId, boolean visible,
			OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByVisible_PrevAndNext(
			entryId, visible, orderByComparator);
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
	public static List<AssetEntry> findByPublishDate(Date publishDate) {
		return getPersistence().findByPublishDate(publishDate);
	}

	/**
	 * Returns a range of all the asset entries where publishDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param publishDate the publish date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 */
	public static List<AssetEntry> findByPublishDate(
		Date publishDate, int start, int end) {

		return getPersistence().findByPublishDate(publishDate, start, end);
	}

	/**
	 * Returns an ordered range of all the asset entries where publishDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param publishDate the publish date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 */
	public static List<AssetEntry> findByPublishDate(
		Date publishDate, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().findByPublishDate(
			publishDate, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset entries where publishDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param publishDate the publish date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entries
	 */
	public static List<AssetEntry> findByPublishDate(
		Date publishDate, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByPublishDate(
			publishDate, start, end, orderByComparator, useFinderCache);
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
			Date publishDate, OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByPublishDate_First(
			publishDate, orderByComparator);
	}

	/**
	 * Returns the first asset entry in the ordered set where publishDate = &#63;.
	 *
	 * @param publishDate the publish date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByPublishDate_First(
		Date publishDate, OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().fetchByPublishDate_First(
			publishDate, orderByComparator);
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
			Date publishDate, OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByPublishDate_Last(
			publishDate, orderByComparator);
	}

	/**
	 * Returns the last asset entry in the ordered set where publishDate = &#63;.
	 *
	 * @param publishDate the publish date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByPublishDate_Last(
		Date publishDate, OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().fetchByPublishDate_Last(
			publishDate, orderByComparator);
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
	public static AssetEntry[] findByPublishDate_PrevAndNext(
			long entryId, Date publishDate,
			OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByPublishDate_PrevAndNext(
			entryId, publishDate, orderByComparator);
	}

	/**
	 * Removes all the asset entries where publishDate = &#63; from the database.
	 *
	 * @param publishDate the publish date
	 */
	public static void removeByPublishDate(Date publishDate) {
		getPersistence().removeByPublishDate(publishDate);
	}

	/**
	 * Returns the number of asset entries where publishDate = &#63;.
	 *
	 * @param publishDate the publish date
	 * @return the number of matching asset entries
	 */
	public static int countByPublishDate(Date publishDate) {
		return getPersistence().countByPublishDate(publishDate);
	}

	/**
	 * Returns all the asset entries where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the matching asset entries
	 */
	public static List<AssetEntry> findByExpirationDate(Date expirationDate) {
		return getPersistence().findByExpirationDate(expirationDate);
	}

	/**
	 * Returns a range of all the asset entries where expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 */
	public static List<AssetEntry> findByExpirationDate(
		Date expirationDate, int start, int end) {

		return getPersistence().findByExpirationDate(
			expirationDate, start, end);
	}

	/**
	 * Returns an ordered range of all the asset entries where expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 */
	public static List<AssetEntry> findByExpirationDate(
		Date expirationDate, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().findByExpirationDate(
			expirationDate, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset entries where expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entries
	 */
	public static List<AssetEntry> findByExpirationDate(
		Date expirationDate, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByExpirationDate(
			expirationDate, start, end, orderByComparator, useFinderCache);
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
			Date expirationDate,
			OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByExpirationDate_First(
			expirationDate, orderByComparator);
	}

	/**
	 * Returns the first asset entry in the ordered set where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByExpirationDate_First(
		Date expirationDate, OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().fetchByExpirationDate_First(
			expirationDate, orderByComparator);
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
			Date expirationDate,
			OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByExpirationDate_Last(
			expirationDate, orderByComparator);
	}

	/**
	 * Returns the last asset entry in the ordered set where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByExpirationDate_Last(
		Date expirationDate, OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().fetchByExpirationDate_Last(
			expirationDate, orderByComparator);
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
	public static AssetEntry[] findByExpirationDate_PrevAndNext(
			long entryId, Date expirationDate,
			OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByExpirationDate_PrevAndNext(
			entryId, expirationDate, orderByComparator);
	}

	/**
	 * Removes all the asset entries where expirationDate = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 */
	public static void removeByExpirationDate(Date expirationDate) {
		getPersistence().removeByExpirationDate(expirationDate);
	}

	/**
	 * Returns the number of asset entries where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the number of matching asset entries
	 */
	public static int countByExpirationDate(Date expirationDate) {
		return getPersistence().countByExpirationDate(expirationDate);
	}

	/**
	 * Returns all the asset entries where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @return the matching asset entries
	 */
	public static List<AssetEntry> findByLayoutUuid(String layoutUuid) {
		return getPersistence().findByLayoutUuid(layoutUuid);
	}

	/**
	 * Returns a range of all the asset entries where layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 */
	public static List<AssetEntry> findByLayoutUuid(
		String layoutUuid, int start, int end) {

		return getPersistence().findByLayoutUuid(layoutUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the asset entries where layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 */
	public static List<AssetEntry> findByLayoutUuid(
		String layoutUuid, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().findByLayoutUuid(
			layoutUuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset entries where layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entries
	 */
	public static List<AssetEntry> findByLayoutUuid(
		String layoutUuid, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLayoutUuid(
			layoutUuid, start, end, orderByComparator, useFinderCache);
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
			String layoutUuid, OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByLayoutUuid_First(
			layoutUuid, orderByComparator);
	}

	/**
	 * Returns the first asset entry in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByLayoutUuid_First(
		String layoutUuid, OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().fetchByLayoutUuid_First(
			layoutUuid, orderByComparator);
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
			String layoutUuid, OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByLayoutUuid_Last(
			layoutUuid, orderByComparator);
	}

	/**
	 * Returns the last asset entry in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByLayoutUuid_Last(
		String layoutUuid, OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().fetchByLayoutUuid_Last(
			layoutUuid, orderByComparator);
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
	public static AssetEntry[] findByLayoutUuid_PrevAndNext(
			long entryId, String layoutUuid,
			OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByLayoutUuid_PrevAndNext(
			entryId, layoutUuid, orderByComparator);
	}

	/**
	 * Removes all the asset entries where layoutUuid = &#63; from the database.
	 *
	 * @param layoutUuid the layout uuid
	 */
	public static void removeByLayoutUuid(String layoutUuid) {
		getPersistence().removeByLayoutUuid(layoutUuid);
	}

	/**
	 * Returns the number of asset entries where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @return the number of matching asset entries
	 */
	public static int countByLayoutUuid(String layoutUuid) {
		return getPersistence().countByLayoutUuid(layoutUuid);
	}

	/**
	 * Returns the asset entry where groupId = &#63; and classUuid = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classUuid the class uuid
	 * @return the matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	public static AssetEntry findByG_CU(long groupId, String classUuid)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByG_CU(groupId, classUuid);
	}

	/**
	 * Returns the asset entry where groupId = &#63; and classUuid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classUuid the class uuid
	 * @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByG_CU(long groupId, String classUuid) {
		return getPersistence().fetchByG_CU(groupId, classUuid);
	}

	/**
	 * Returns the asset entry where groupId = &#63; and classUuid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classUuid the class uuid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByG_CU(
		long groupId, String classUuid, boolean useFinderCache) {

		return getPersistence().fetchByG_CU(groupId, classUuid, useFinderCache);
	}

	/**
	 * Removes the asset entry where groupId = &#63; and classUuid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classUuid the class uuid
	 * @return the asset entry that was removed
	 */
	public static AssetEntry removeByG_CU(long groupId, String classUuid)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().removeByG_CU(groupId, classUuid);
	}

	/**
	 * Returns the number of asset entries where groupId = &#63; and classUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classUuid the class uuid
	 * @return the number of matching asset entries
	 */
	public static int countByG_CU(long groupId, String classUuid) {
		return getPersistence().countByG_CU(groupId, classUuid);
	}

	/**
	 * Returns the asset entry where classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	public static AssetEntry findByC_C(long classNameId, long classPK)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	 * Returns the asset entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByC_C(long classNameId, long classPK) {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	/**
	 * Returns the asset entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByC_C(
		long classNameId, long classPK, boolean useFinderCache) {

		return getPersistence().fetchByC_C(
			classNameId, classPK, useFinderCache);
	}

	/**
	 * Removes the asset entry where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the asset entry that was removed
	 */
	public static AssetEntry removeByC_C(long classNameId, long classPK)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	 * Returns the number of asset entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching asset entries
	 */
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	 * Returns all the asset entries where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @return the matching asset entries
	 */
	public static List<AssetEntry> findByG_C_V(
		long groupId, long classNameId, boolean visible) {

		return getPersistence().findByG_C_V(groupId, classNameId, visible);
	}

	/**
	 * Returns a range of all the asset entries where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 */
	public static List<AssetEntry> findByG_C_V(
		long groupId, long classNameId, boolean visible, int start, int end) {

		return getPersistence().findByG_C_V(
			groupId, classNameId, visible, start, end);
	}

	/**
	 * Returns an ordered range of all the asset entries where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 */
	public static List<AssetEntry> findByG_C_V(
		long groupId, long classNameId, boolean visible, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().findByG_C_V(
			groupId, classNameId, visible, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset entries where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entries
	 */
	public static List<AssetEntry> findByG_C_V(
		long groupId, long classNameId, boolean visible, int start, int end,
		OrderByComparator<AssetEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_C_V(
			groupId, classNameId, visible, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	public static AssetEntry findByG_C_V_First(
			long groupId, long classNameId, boolean visible,
			OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByG_C_V_First(
			groupId, classNameId, visible, orderByComparator);
	}

	/**
	 * Returns the first asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByG_C_V_First(
		long groupId, long classNameId, boolean visible,
		OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().fetchByG_C_V_First(
			groupId, classNameId, visible, orderByComparator);
	}

	/**
	 * Returns the last asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	public static AssetEntry findByG_C_V_Last(
			long groupId, long classNameId, boolean visible,
			OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByG_C_V_Last(
			groupId, classNameId, visible, orderByComparator);
	}

	/**
	 * Returns the last asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByG_C_V_Last(
		long groupId, long classNameId, boolean visible,
		OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().fetchByG_C_V_Last(
			groupId, classNameId, visible, orderByComparator);
	}

	/**
	 * Returns the asset entries before and after the current asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * @param entryId the primary key of the current asset entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry
	 * @throws NoSuchEntryException if a asset entry with the primary key could not be found
	 */
	public static AssetEntry[] findByG_C_V_PrevAndNext(
			long entryId, long groupId, long classNameId, boolean visible,
			OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByG_C_V_PrevAndNext(
			entryId, groupId, classNameId, visible, orderByComparator);
	}

	/**
	 * Removes all the asset entries where groupId = &#63; and classNameId = &#63; and visible = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 */
	public static void removeByG_C_V(
		long groupId, long classNameId, boolean visible) {

		getPersistence().removeByG_C_V(groupId, classNameId, visible);
	}

	/**
	 * Returns the number of asset entries where groupId = &#63; and classNameId = &#63; and visible = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param visible the visible
	 * @return the number of matching asset entries
	 */
	public static int countByG_C_V(
		long groupId, long classNameId, boolean visible) {

		return getPersistence().countByG_C_V(groupId, classNameId, visible);
	}

	/**
	 * Returns all the asset entries where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @return the matching asset entries
	 */
	public static List<AssetEntry> findByG_C_P_E(
		long groupId, long classNameId, Date publishDate, Date expirationDate) {

		return getPersistence().findByG_C_P_E(
			groupId, classNameId, publishDate, expirationDate);
	}

	/**
	 * Returns a range of all the asset entries where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 */
	public static List<AssetEntry> findByG_C_P_E(
		long groupId, long classNameId, Date publishDate, Date expirationDate,
		int start, int end) {

		return getPersistence().findByG_C_P_E(
			groupId, classNameId, publishDate, expirationDate, start, end);
	}

	/**
	 * Returns an ordered range of all the asset entries where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 */
	public static List<AssetEntry> findByG_C_P_E(
		long groupId, long classNameId, Date publishDate, Date expirationDate,
		int start, int end, OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().findByG_C_P_E(
			groupId, classNameId, publishDate, expirationDate, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset entries where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset entries
	 */
	public static List<AssetEntry> findByG_C_P_E(
		long groupId, long classNameId, Date publishDate, Date expirationDate,
		int start, int end, OrderByComparator<AssetEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_C_P_E(
			groupId, classNameId, publishDate, expirationDate, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	public static AssetEntry findByG_C_P_E_First(
			long groupId, long classNameId, Date publishDate,
			Date expirationDate,
			OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByG_C_P_E_First(
			groupId, classNameId, publishDate, expirationDate,
			orderByComparator);
	}

	/**
	 * Returns the first asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByG_C_P_E_First(
		long groupId, long classNameId, Date publishDate, Date expirationDate,
		OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().fetchByG_C_P_E_First(
			groupId, classNameId, publishDate, expirationDate,
			orderByComparator);
	}

	/**
	 * Returns the last asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry
	 * @throws NoSuchEntryException if a matching asset entry could not be found
	 */
	public static AssetEntry findByG_C_P_E_Last(
			long groupId, long classNameId, Date publishDate,
			Date expirationDate,
			OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByG_C_P_E_Last(
			groupId, classNameId, publishDate, expirationDate,
			orderByComparator);
	}

	/**
	 * Returns the last asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 */
	public static AssetEntry fetchByG_C_P_E_Last(
		long groupId, long classNameId, Date publishDate, Date expirationDate,
		OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().fetchByG_C_P_E_Last(
			groupId, classNameId, publishDate, expirationDate,
			orderByComparator);
	}

	/**
	 * Returns the asset entries before and after the current asset entry in the ordered set where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * @param entryId the primary key of the current asset entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry
	 * @throws NoSuchEntryException if a asset entry with the primary key could not be found
	 */
	public static AssetEntry[] findByG_C_P_E_PrevAndNext(
			long entryId, long groupId, long classNameId, Date publishDate,
			Date expirationDate,
			OrderByComparator<AssetEntry> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().findByG_C_P_E_PrevAndNext(
			entryId, groupId, classNameId, publishDate, expirationDate,
			orderByComparator);
	}

	/**
	 * Removes all the asset entries where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 */
	public static void removeByG_C_P_E(
		long groupId, long classNameId, Date publishDate, Date expirationDate) {

		getPersistence().removeByG_C_P_E(
			groupId, classNameId, publishDate, expirationDate);
	}

	/**
	 * Returns the number of asset entries where groupId = &#63; and classNameId = &#63; and publishDate = &#63; and expirationDate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param publishDate the publish date
	 * @param expirationDate the expiration date
	 * @return the number of matching asset entries
	 */
	public static int countByG_C_P_E(
		long groupId, long classNameId, Date publishDate, Date expirationDate) {

		return getPersistence().countByG_C_P_E(
			groupId, classNameId, publishDate, expirationDate);
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
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

		return getPersistence().remove(entryId);
	}

	public static AssetEntry updateImpl(AssetEntry assetEntry) {
		return getPersistence().updateImpl(assetEntry);
	}

	/**
	 * Returns the asset entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param entryId the primary key of the asset entry
	 * @return the asset entry
	 * @throws NoSuchEntryException if a asset entry with the primary key could not be found
	 */
	public static AssetEntry findByPrimaryKey(long entryId)
		throws com.liferay.asset.kernel.exception.NoSuchEntryException {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset entries
	 */
	public static List<AssetEntry> findAll(
		int start, int end, OrderByComparator<AssetEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset entries
	 */
	public static List<AssetEntry> findAll(
		int start, int end, OrderByComparator<AssetEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
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
	public static List<com.liferay.asset.kernel.model.AssetCategory>
		getAssetCategories(long pk) {

		return getPersistence().getAssetCategories(pk);
	}

	/**
	 * Returns a range of all the asset categories associated with the asset entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the asset entry
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of asset categories associated with the asset entry
	 */
	public static List<com.liferay.asset.kernel.model.AssetCategory>
		getAssetCategories(long pk, int start, int end) {

		return getPersistence().getAssetCategories(pk, start, end);
	}

	/**
	 * Returns an ordered range of all the asset categories associated with the asset entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the asset entry
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset categories associated with the asset entry
	 */
	public static List<com.liferay.asset.kernel.model.AssetCategory>
		getAssetCategories(
			long pk, int start, int end,
			OrderByComparator<com.liferay.asset.kernel.model.AssetCategory>
				orderByComparator) {

		return getPersistence().getAssetCategories(
			pk, start, end, orderByComparator);
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
	public static void addAssetCategory(
		long pk, com.liferay.asset.kernel.model.AssetCategory assetCategory) {

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
	public static void addAssetCategories(
		long pk,
		List<com.liferay.asset.kernel.model.AssetCategory> assetCategories) {

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
	public static void removeAssetCategory(
		long pk, com.liferay.asset.kernel.model.AssetCategory assetCategory) {

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
	public static void removeAssetCategories(
		long pk,
		List<com.liferay.asset.kernel.model.AssetCategory> assetCategories) {

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
	public static void setAssetCategories(
		long pk,
		List<com.liferay.asset.kernel.model.AssetCategory> assetCategories) {

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
	public static List<com.liferay.asset.kernel.model.AssetTag> getAssetTags(
		long pk) {

		return getPersistence().getAssetTags(pk);
	}

	/**
	 * Returns a range of all the asset tags associated with the asset entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the asset entry
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of asset tags associated with the asset entry
	 */
	public static List<com.liferay.asset.kernel.model.AssetTag> getAssetTags(
		long pk, int start, int end) {

		return getPersistence().getAssetTags(pk, start, end);
	}

	/**
	 * Returns an ordered range of all the asset tags associated with the asset entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the asset entry
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset tags associated with the asset entry
	 */
	public static List<com.liferay.asset.kernel.model.AssetTag> getAssetTags(
		long pk, int start, int end,
		OrderByComparator<com.liferay.asset.kernel.model.AssetTag>
			orderByComparator) {

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
	public static void addAssetTag(
		long pk, com.liferay.asset.kernel.model.AssetTag assetTag) {

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
	public static void addAssetTags(
		long pk, List<com.liferay.asset.kernel.model.AssetTag> assetTags) {

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
	public static void removeAssetTag(
		long pk, com.liferay.asset.kernel.model.AssetTag assetTag) {

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
	public static void removeAssetTags(
		long pk, List<com.liferay.asset.kernel.model.AssetTag> assetTags) {

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
	public static void setAssetTags(
		long pk, List<com.liferay.asset.kernel.model.AssetTag> assetTags) {

		getPersistence().setAssetTags(pk, assetTags);
	}

	public static AssetEntryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetEntryPersistence)PortalBeanLocatorUtil.locate(
				AssetEntryPersistence.class.getName());
		}

		return _persistence;
	}

	private static AssetEntryPersistence _persistence;

}