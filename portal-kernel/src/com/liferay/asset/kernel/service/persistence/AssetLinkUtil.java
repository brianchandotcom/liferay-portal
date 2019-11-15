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

import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the asset link service. This utility wraps <code>com.liferay.portlet.asset.service.persistence.impl.AssetLinkPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetLinkPersistence
 * @generated
 */
public class AssetLinkUtil {

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
	public static void clearCache(AssetLink assetLink) {
		getPersistence().clearCache(assetLink);
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
	public static Map<Serializable, AssetLink> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AssetLink update(AssetLink assetLink) {
		return getPersistence().update(assetLink);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AssetLink update(
		AssetLink assetLink, ServiceContext serviceContext) {

		return getPersistence().update(assetLink, serviceContext);
	}

	/**
	 * Returns all the asset links where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching asset links
	 */
	public static List<AssetLink> findByCTCollectionId(long ctCollectionId) {
		return getPersistence().findByCTCollectionId(ctCollectionId);
	}

	/**
	 * Returns a range of all the asset links where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of matching asset links
	 */
	public static List<AssetLink> findByCTCollectionId(
		long ctCollectionId, int start, int end) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset links where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset links
	 */
	public static List<AssetLink> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset links where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset links
	 */
	public static List<AssetLink> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<AssetLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset link in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	public static AssetLink findByCTCollectionId_First(
			long ctCollectionId, OrderByComparator<AssetLink> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByCTCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the first asset link in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	public static AssetLink fetchByCTCollectionId_First(
		long ctCollectionId, OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().fetchByCTCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last asset link in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	public static AssetLink findByCTCollectionId_Last(
			long ctCollectionId, OrderByComparator<AssetLink> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByCTCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last asset link in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	public static AssetLink fetchByCTCollectionId_Last(
		long ctCollectionId, OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().fetchByCTCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the asset links before and after the current asset link in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param linkId the primary key of the current asset link
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset link
	 * @throws NoSuchLinkException if a asset link with the primary key could not be found
	 */
	public static AssetLink[] findByCTCollectionId_PrevAndNext(
			long linkId, long ctCollectionId,
			OrderByComparator<AssetLink> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByCTCollectionId_PrevAndNext(
			linkId, ctCollectionId, orderByComparator);
	}

	/**
	 * Removes all the asset links where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public static void removeByCTCollectionId(long ctCollectionId) {
		getPersistence().removeByCTCollectionId(ctCollectionId);
	}

	/**
	 * Returns the number of asset links where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching asset links
	 */
	public static int countByCTCollectionId(long ctCollectionId) {
		return getPersistence().countByCTCollectionId(ctCollectionId);
	}

	/**
	 * Returns all the asset links where entryId1 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @return the matching asset links
	 */
	public static List<AssetLink> findByE1(long entryId1) {
		return getPersistence().findByE1(entryId1);
	}

	/**
	 * Returns a range of all the asset links where entryId1 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of matching asset links
	 */
	public static List<AssetLink> findByE1(long entryId1, int start, int end) {
		return getPersistence().findByE1(entryId1, start, end);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId1 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset links
	 */
	public static List<AssetLink> findByE1(
		long entryId1, int start, int end,
		OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().findByE1(
			entryId1, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId1 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset links
	 */
	public static List<AssetLink> findByE1(
		long entryId1, int start, int end,
		OrderByComparator<AssetLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByE1(
			entryId1, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset link in the ordered set where entryId1 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	public static AssetLink findByE1_First(
			long entryId1, OrderByComparator<AssetLink> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByE1_First(entryId1, orderByComparator);
	}

	/**
	 * Returns the first asset link in the ordered set where entryId1 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	public static AssetLink fetchByE1_First(
		long entryId1, OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().fetchByE1_First(entryId1, orderByComparator);
	}

	/**
	 * Returns the last asset link in the ordered set where entryId1 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	public static AssetLink findByE1_Last(
			long entryId1, OrderByComparator<AssetLink> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByE1_Last(entryId1, orderByComparator);
	}

	/**
	 * Returns the last asset link in the ordered set where entryId1 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	public static AssetLink fetchByE1_Last(
		long entryId1, OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().fetchByE1_Last(entryId1, orderByComparator);
	}

	/**
	 * Returns the asset links before and after the current asset link in the ordered set where entryId1 = &#63;.
	 *
	 * @param linkId the primary key of the current asset link
	 * @param entryId1 the entry id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset link
	 * @throws NoSuchLinkException if a asset link with the primary key could not be found
	 */
	public static AssetLink[] findByE1_PrevAndNext(
			long linkId, long entryId1,
			OrderByComparator<AssetLink> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByE1_PrevAndNext(
			linkId, entryId1, orderByComparator);
	}

	/**
	 * Removes all the asset links where entryId1 = &#63; from the database.
	 *
	 * @param entryId1 the entry id1
	 */
	public static void removeByE1(long entryId1) {
		getPersistence().removeByE1(entryId1);
	}

	/**
	 * Returns the number of asset links where entryId1 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @return the number of matching asset links
	 */
	public static int countByE1(long entryId1) {
		return getPersistence().countByE1(entryId1);
	}

	/**
	 * Returns all the asset links where entryId2 = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @return the matching asset links
	 */
	public static List<AssetLink> findByE2(long entryId2) {
		return getPersistence().findByE2(entryId2);
	}

	/**
	 * Returns a range of all the asset links where entryId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param entryId2 the entry id2
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of matching asset links
	 */
	public static List<AssetLink> findByE2(long entryId2, int start, int end) {
		return getPersistence().findByE2(entryId2, start, end);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param entryId2 the entry id2
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset links
	 */
	public static List<AssetLink> findByE2(
		long entryId2, int start, int end,
		OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().findByE2(
			entryId2, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param entryId2 the entry id2
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset links
	 */
	public static List<AssetLink> findByE2(
		long entryId2, int start, int end,
		OrderByComparator<AssetLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByE2(
			entryId2, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset link in the ordered set where entryId2 = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	public static AssetLink findByE2_First(
			long entryId2, OrderByComparator<AssetLink> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByE2_First(entryId2, orderByComparator);
	}

	/**
	 * Returns the first asset link in the ordered set where entryId2 = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	public static AssetLink fetchByE2_First(
		long entryId2, OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().fetchByE2_First(entryId2, orderByComparator);
	}

	/**
	 * Returns the last asset link in the ordered set where entryId2 = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	public static AssetLink findByE2_Last(
			long entryId2, OrderByComparator<AssetLink> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByE2_Last(entryId2, orderByComparator);
	}

	/**
	 * Returns the last asset link in the ordered set where entryId2 = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	public static AssetLink fetchByE2_Last(
		long entryId2, OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().fetchByE2_Last(entryId2, orderByComparator);
	}

	/**
	 * Returns the asset links before and after the current asset link in the ordered set where entryId2 = &#63;.
	 *
	 * @param linkId the primary key of the current asset link
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset link
	 * @throws NoSuchLinkException if a asset link with the primary key could not be found
	 */
	public static AssetLink[] findByE2_PrevAndNext(
			long linkId, long entryId2,
			OrderByComparator<AssetLink> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByE2_PrevAndNext(
			linkId, entryId2, orderByComparator);
	}

	/**
	 * Removes all the asset links where entryId2 = &#63; from the database.
	 *
	 * @param entryId2 the entry id2
	 */
	public static void removeByE2(long entryId2) {
		getPersistence().removeByE2(entryId2);
	}

	/**
	 * Returns the number of asset links where entryId2 = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @return the number of matching asset links
	 */
	public static int countByE2(long entryId2) {
		return getPersistence().countByE2(entryId2);
	}

	/**
	 * Returns all the asset links where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @return the matching asset links
	 */
	public static List<AssetLink> findByE_E(long entryId1, long entryId2) {
		return getPersistence().findByE_E(entryId1, entryId2);
	}

	/**
	 * Returns a range of all the asset links where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of matching asset links
	 */
	public static List<AssetLink> findByE_E(
		long entryId1, long entryId2, int start, int end) {

		return getPersistence().findByE_E(entryId1, entryId2, start, end);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset links
	 */
	public static List<AssetLink> findByE_E(
		long entryId1, long entryId2, int start, int end,
		OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().findByE_E(
			entryId1, entryId2, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset links
	 */
	public static List<AssetLink> findByE_E(
		long entryId1, long entryId2, int start, int end,
		OrderByComparator<AssetLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByE_E(
			entryId1, entryId2, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	public static AssetLink findByE_E_First(
			long entryId1, long entryId2,
			OrderByComparator<AssetLink> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByE_E_First(
			entryId1, entryId2, orderByComparator);
	}

	/**
	 * Returns the first asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	public static AssetLink fetchByE_E_First(
		long entryId1, long entryId2,
		OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().fetchByE_E_First(
			entryId1, entryId2, orderByComparator);
	}

	/**
	 * Returns the last asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	public static AssetLink findByE_E_Last(
			long entryId1, long entryId2,
			OrderByComparator<AssetLink> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByE_E_Last(
			entryId1, entryId2, orderByComparator);
	}

	/**
	 * Returns the last asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	public static AssetLink fetchByE_E_Last(
		long entryId1, long entryId2,
		OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().fetchByE_E_Last(
			entryId1, entryId2, orderByComparator);
	}

	/**
	 * Returns the asset links before and after the current asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param linkId the primary key of the current asset link
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset link
	 * @throws NoSuchLinkException if a asset link with the primary key could not be found
	 */
	public static AssetLink[] findByE_E_PrevAndNext(
			long linkId, long entryId1, long entryId2,
			OrderByComparator<AssetLink> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByE_E_PrevAndNext(
			linkId, entryId1, entryId2, orderByComparator);
	}

	/**
	 * Removes all the asset links where entryId1 = &#63; and entryId2 = &#63; from the database.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 */
	public static void removeByE_E(long entryId1, long entryId2) {
		getPersistence().removeByE_E(entryId1, entryId2);
	}

	/**
	 * Returns the number of asset links where entryId1 = &#63; and entryId2 = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @return the number of matching asset links
	 */
	public static int countByE_E(long entryId1, long entryId2) {
		return getPersistence().countByE_E(entryId1, entryId2);
	}

	/**
	 * Returns all the asset links where entryId1 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @return the matching asset links
	 */
	public static List<AssetLink> findByE1_T(long entryId1, int type) {
		return getPersistence().findByE1_T(entryId1, type);
	}

	/**
	 * Returns a range of all the asset links where entryId1 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of matching asset links
	 */
	public static List<AssetLink> findByE1_T(
		long entryId1, int type, int start, int end) {

		return getPersistence().findByE1_T(entryId1, type, start, end);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId1 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset links
	 */
	public static List<AssetLink> findByE1_T(
		long entryId1, int type, int start, int end,
		OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().findByE1_T(
			entryId1, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId1 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset links
	 */
	public static List<AssetLink> findByE1_T(
		long entryId1, int type, int start, int end,
		OrderByComparator<AssetLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByE1_T(
			entryId1, type, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	public static AssetLink findByE1_T_First(
			long entryId1, int type,
			OrderByComparator<AssetLink> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByE1_T_First(
			entryId1, type, orderByComparator);
	}

	/**
	 * Returns the first asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	public static AssetLink fetchByE1_T_First(
		long entryId1, int type,
		OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().fetchByE1_T_First(
			entryId1, type, orderByComparator);
	}

	/**
	 * Returns the last asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	public static AssetLink findByE1_T_Last(
			long entryId1, int type,
			OrderByComparator<AssetLink> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByE1_T_Last(
			entryId1, type, orderByComparator);
	}

	/**
	 * Returns the last asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	public static AssetLink fetchByE1_T_Last(
		long entryId1, int type,
		OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().fetchByE1_T_Last(
			entryId1, type, orderByComparator);
	}

	/**
	 * Returns the asset links before and after the current asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	 *
	 * @param linkId the primary key of the current asset link
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset link
	 * @throws NoSuchLinkException if a asset link with the primary key could not be found
	 */
	public static AssetLink[] findByE1_T_PrevAndNext(
			long linkId, long entryId1, int type,
			OrderByComparator<AssetLink> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByE1_T_PrevAndNext(
			linkId, entryId1, type, orderByComparator);
	}

	/**
	 * Removes all the asset links where entryId1 = &#63; and type = &#63; from the database.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 */
	public static void removeByE1_T(long entryId1, int type) {
		getPersistence().removeByE1_T(entryId1, type);
	}

	/**
	 * Returns the number of asset links where entryId1 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param type the type
	 * @return the number of matching asset links
	 */
	public static int countByE1_T(long entryId1, int type) {
		return getPersistence().countByE1_T(entryId1, type);
	}

	/**
	 * Returns all the asset links where entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @return the matching asset links
	 */
	public static List<AssetLink> findByE2_T(long entryId2, int type) {
		return getPersistence().findByE2_T(entryId2, type);
	}

	/**
	 * Returns a range of all the asset links where entryId2 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of matching asset links
	 */
	public static List<AssetLink> findByE2_T(
		long entryId2, int type, int start, int end) {

		return getPersistence().findByE2_T(entryId2, type, start, end);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId2 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset links
	 */
	public static List<AssetLink> findByE2_T(
		long entryId2, int type, int start, int end,
		OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().findByE2_T(
			entryId2, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset links where entryId2 = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset links
	 */
	public static List<AssetLink> findByE2_T(
		long entryId2, int type, int start, int end,
		OrderByComparator<AssetLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByE2_T(
			entryId2, type, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	public static AssetLink findByE2_T_First(
			long entryId2, int type,
			OrderByComparator<AssetLink> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByE2_T_First(
			entryId2, type, orderByComparator);
	}

	/**
	 * Returns the first asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	public static AssetLink fetchByE2_T_First(
		long entryId2, int type,
		OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().fetchByE2_T_First(
			entryId2, type, orderByComparator);
	}

	/**
	 * Returns the last asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	public static AssetLink findByE2_T_Last(
			long entryId2, int type,
			OrderByComparator<AssetLink> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByE2_T_Last(
			entryId2, type, orderByComparator);
	}

	/**
	 * Returns the last asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	public static AssetLink fetchByE2_T_Last(
		long entryId2, int type,
		OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().fetchByE2_T_Last(
			entryId2, type, orderByComparator);
	}

	/**
	 * Returns the asset links before and after the current asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	 *
	 * @param linkId the primary key of the current asset link
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset link
	 * @throws NoSuchLinkException if a asset link with the primary key could not be found
	 */
	public static AssetLink[] findByE2_T_PrevAndNext(
			long linkId, long entryId2, int type,
			OrderByComparator<AssetLink> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByE2_T_PrevAndNext(
			linkId, entryId2, type, orderByComparator);
	}

	/**
	 * Removes all the asset links where entryId2 = &#63; and type = &#63; from the database.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 */
	public static void removeByE2_T(long entryId2, int type) {
		getPersistence().removeByE2_T(entryId2, type);
	}

	/**
	 * Returns the number of asset links where entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @return the number of matching asset links
	 */
	public static int countByE2_T(long entryId2, int type) {
		return getPersistence().countByE2_T(entryId2, type);
	}

	/**
	 * Returns the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; or throws a <code>NoSuchLinkException</code> if it could not be found.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @return the matching asset link
	 * @throws NoSuchLinkException if a matching asset link could not be found
	 */
	public static AssetLink findByE_E_T(long entryId1, long entryId2, int type)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByE_E_T(entryId1, entryId2, type);
	}

	/**
	 * Returns the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @return the matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	public static AssetLink fetchByE_E_T(
		long entryId1, long entryId2, int type) {

		return getPersistence().fetchByE_E_T(entryId1, entryId2, type);
	}

	/**
	 * Returns the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset link, or <code>null</code> if a matching asset link could not be found
	 */
	public static AssetLink fetchByE_E_T(
		long entryId1, long entryId2, int type, boolean useFinderCache) {

		return getPersistence().fetchByE_E_T(
			entryId1, entryId2, type, useFinderCache);
	}

	/**
	 * Removes the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; from the database.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @return the asset link that was removed
	 */
	public static AssetLink removeByE_E_T(
			long entryId1, long entryId2, int type)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().removeByE_E_T(entryId1, entryId2, type);
	}

	/**
	 * Returns the number of asset links where entryId1 = &#63; and entryId2 = &#63; and type = &#63;.
	 *
	 * @param entryId1 the entry id1
	 * @param entryId2 the entry id2
	 * @param type the type
	 * @return the number of matching asset links
	 */
	public static int countByE_E_T(long entryId1, long entryId2, int type) {
		return getPersistence().countByE_E_T(entryId1, entryId2, type);
	}

	/**
	 * Caches the asset link in the entity cache if it is enabled.
	 *
	 * @param assetLink the asset link
	 */
	public static void cacheResult(AssetLink assetLink) {
		getPersistence().cacheResult(assetLink);
	}

	/**
	 * Caches the asset links in the entity cache if it is enabled.
	 *
	 * @param assetLinks the asset links
	 */
	public static void cacheResult(List<AssetLink> assetLinks) {
		getPersistence().cacheResult(assetLinks);
	}

	/**
	 * Creates a new asset link with the primary key. Does not add the asset link to the database.
	 *
	 * @param linkId the primary key for the new asset link
	 * @return the new asset link
	 */
	public static AssetLink create(long linkId) {
		return getPersistence().create(linkId);
	}

	/**
	 * Removes the asset link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param linkId the primary key of the asset link
	 * @return the asset link that was removed
	 * @throws NoSuchLinkException if a asset link with the primary key could not be found
	 */
	public static AssetLink remove(long linkId)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().remove(linkId);
	}

	public static AssetLink updateImpl(AssetLink assetLink) {
		return getPersistence().updateImpl(assetLink);
	}

	/**
	 * Returns the asset link with the primary key or throws a <code>NoSuchLinkException</code> if it could not be found.
	 *
	 * @param linkId the primary key of the asset link
	 * @return the asset link
	 * @throws NoSuchLinkException if a asset link with the primary key could not be found
	 */
	public static AssetLink findByPrimaryKey(long linkId)
		throws com.liferay.asset.kernel.exception.NoSuchLinkException {

		return getPersistence().findByPrimaryKey(linkId);
	}

	/**
	 * Returns the asset link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param linkId the primary key of the asset link
	 * @return the asset link, or <code>null</code> if a asset link with the primary key could not be found
	 */
	public static AssetLink fetchByPrimaryKey(long linkId) {
		return getPersistence().fetchByPrimaryKey(linkId);
	}

	/**
	 * Returns all the asset links.
	 *
	 * @return the asset links
	 */
	public static List<AssetLink> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the asset links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @return the range of asset links
	 */
	public static List<AssetLink> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the asset links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset links
	 */
	public static List<AssetLink> findAll(
		int start, int end, OrderByComparator<AssetLink> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset links
	 * @param end the upper bound of the range of asset links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset links
	 */
	public static List<AssetLink> findAll(
		int start, int end, OrderByComparator<AssetLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the asset links from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of asset links.
	 *
	 * @return the number of asset links
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AssetLinkPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetLinkPersistence)PortalBeanLocatorUtil.locate(
				AssetLinkPersistence.class.getName());
		}

		return _persistence;
	}

	private static AssetLinkPersistence _persistence;

}