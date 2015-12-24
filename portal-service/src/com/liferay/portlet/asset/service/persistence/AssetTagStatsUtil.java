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

import com.liferay.portlet.asset.model.AssetTagStats;

import java.util.List;

/**
 * The persistence utility for the asset tag stats service. This utility wraps {@link com.liferay.portlet.asset.service.persistence.impl.AssetTagStatsPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagStatsPersistence
 * @see com.liferay.portlet.asset.service.persistence.impl.AssetTagStatsPersistenceImpl
 * @generated
 */
@ProviderType
public class AssetTagStatsUtil {
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
	public static void clearCache(AssetTagStats assetTagStats) {
		getPersistence().clearCache(assetTagStats);
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
	public static List<AssetTagStats> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetTagStats> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetTagStats> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetTagStats> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static AssetTagStats update(AssetTagStats assetTagStats) {
		return getPersistence().update(assetTagStats);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static AssetTagStats update(AssetTagStats assetTagStats,
		ServiceContext serviceContext) {
		return getPersistence().update(assetTagStats, serviceContext);
	}

	/**
	* Returns all the asset tag statses where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @return the matching asset tag statses
	*/
	public static List<AssetTagStats> findByTagId(long tagId) {
		return getPersistence().findByTagId(tagId);
	}

	/**
	* Returns a range of all the asset tag statses where tagId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tagId the tag ID
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @return the range of matching asset tag statses
	*/
	public static List<AssetTagStats> findByTagId(long tagId, int start, int end) {
		return getPersistence().findByTagId(tagId, start, end);
	}

	/**
	* Returns an ordered range of all the asset tag statses where tagId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tagId the tag ID
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tag statses
	*/
	public static List<AssetTagStats> findByTagId(long tagId, int start,
		int end, OrderByComparator<AssetTagStats> orderByComparator) {
		return getPersistence().findByTagId(tagId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset tag statses where tagId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tagId the tag ID
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset tag statses
	*/
	public static List<AssetTagStats> findByTagId(long tagId, int start,
		int end, OrderByComparator<AssetTagStats> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByTagId(tagId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first asset tag stats in the ordered set where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag stats
	* @throws NoSuchTagStatsException if a matching asset tag stats could not be found
	*/
	public static AssetTagStats findByTagId_First(long tagId,
		OrderByComparator<AssetTagStats> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().findByTagId_First(tagId, orderByComparator);
	}

	/**
	* Returns the first asset tag stats in the ordered set where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag stats, or <code>null</code> if a matching asset tag stats could not be found
	*/
	public static AssetTagStats fetchByTagId_First(long tagId,
		OrderByComparator<AssetTagStats> orderByComparator) {
		return getPersistence().fetchByTagId_First(tagId, orderByComparator);
	}

	/**
	* Returns the last asset tag stats in the ordered set where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag stats
	* @throws NoSuchTagStatsException if a matching asset tag stats could not be found
	*/
	public static AssetTagStats findByTagId_Last(long tagId,
		OrderByComparator<AssetTagStats> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().findByTagId_Last(tagId, orderByComparator);
	}

	/**
	* Returns the last asset tag stats in the ordered set where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag stats, or <code>null</code> if a matching asset tag stats could not be found
	*/
	public static AssetTagStats fetchByTagId_Last(long tagId,
		OrderByComparator<AssetTagStats> orderByComparator) {
		return getPersistence().fetchByTagId_Last(tagId, orderByComparator);
	}

	/**
	* Returns the asset tag statses before and after the current asset tag stats in the ordered set where tagId = &#63;.
	*
	* @param tagStatsId the primary key of the current asset tag stats
	* @param tagId the tag ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag stats
	* @throws NoSuchTagStatsException if a asset tag stats with the primary key could not be found
	*/
	public static AssetTagStats[] findByTagId_PrevAndNext(long tagStatsId,
		long tagId, OrderByComparator<AssetTagStats> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence()
				   .findByTagId_PrevAndNext(tagStatsId, tagId, orderByComparator);
	}

	/**
	* Removes all the asset tag statses where tagId = &#63; from the database.
	*
	* @param tagId the tag ID
	*/
	public static void removeByTagId(long tagId) {
		getPersistence().removeByTagId(tagId);
	}

	/**
	* Returns the number of asset tag statses where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @return the number of matching asset tag statses
	*/
	public static int countByTagId(long tagId) {
		return getPersistence().countByTagId(tagId);
	}

	/**
	* Returns all the asset tag statses where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the matching asset tag statses
	*/
	public static List<AssetTagStats> findByClassNameId(long classNameId) {
		return getPersistence().findByClassNameId(classNameId);
	}

	/**
	* Returns a range of all the asset tag statses where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @return the range of matching asset tag statses
	*/
	public static List<AssetTagStats> findByClassNameId(long classNameId,
		int start, int end) {
		return getPersistence().findByClassNameId(classNameId, start, end);
	}

	/**
	* Returns an ordered range of all the asset tag statses where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tag statses
	*/
	public static List<AssetTagStats> findByClassNameId(long classNameId,
		int start, int end, OrderByComparator<AssetTagStats> orderByComparator) {
		return getPersistence()
				   .findByClassNameId(classNameId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset tag statses where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset tag statses
	*/
	public static List<AssetTagStats> findByClassNameId(long classNameId,
		int start, int end, OrderByComparator<AssetTagStats> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByClassNameId(classNameId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset tag stats in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag stats
	* @throws NoSuchTagStatsException if a matching asset tag stats could not be found
	*/
	public static AssetTagStats findByClassNameId_First(long classNameId,
		OrderByComparator<AssetTagStats> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence()
				   .findByClassNameId_First(classNameId, orderByComparator);
	}

	/**
	* Returns the first asset tag stats in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag stats, or <code>null</code> if a matching asset tag stats could not be found
	*/
	public static AssetTagStats fetchByClassNameId_First(long classNameId,
		OrderByComparator<AssetTagStats> orderByComparator) {
		return getPersistence()
				   .fetchByClassNameId_First(classNameId, orderByComparator);
	}

	/**
	* Returns the last asset tag stats in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag stats
	* @throws NoSuchTagStatsException if a matching asset tag stats could not be found
	*/
	public static AssetTagStats findByClassNameId_Last(long classNameId,
		OrderByComparator<AssetTagStats> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence()
				   .findByClassNameId_Last(classNameId, orderByComparator);
	}

	/**
	* Returns the last asset tag stats in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag stats, or <code>null</code> if a matching asset tag stats could not be found
	*/
	public static AssetTagStats fetchByClassNameId_Last(long classNameId,
		OrderByComparator<AssetTagStats> orderByComparator) {
		return getPersistence()
				   .fetchByClassNameId_Last(classNameId, orderByComparator);
	}

	/**
	* Returns the asset tag statses before and after the current asset tag stats in the ordered set where classNameId = &#63;.
	*
	* @param tagStatsId the primary key of the current asset tag stats
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag stats
	* @throws NoSuchTagStatsException if a asset tag stats with the primary key could not be found
	*/
	public static AssetTagStats[] findByClassNameId_PrevAndNext(
		long tagStatsId, long classNameId,
		OrderByComparator<AssetTagStats> orderByComparator)
		throws com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence()
				   .findByClassNameId_PrevAndNext(tagStatsId, classNameId,
			orderByComparator);
	}

	/**
	* Removes all the asset tag statses where classNameId = &#63; from the database.
	*
	* @param classNameId the class name ID
	*/
	public static void removeByClassNameId(long classNameId) {
		getPersistence().removeByClassNameId(classNameId);
	}

	/**
	* Returns the number of asset tag statses where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the number of matching asset tag statses
	*/
	public static int countByClassNameId(long classNameId) {
		return getPersistence().countByClassNameId(classNameId);
	}

	/**
	* Returns the asset tag stats where tagId = &#63; and classNameId = &#63; or throws a {@link NoSuchTagStatsException} if it could not be found.
	*
	* @param tagId the tag ID
	* @param classNameId the class name ID
	* @return the matching asset tag stats
	* @throws NoSuchTagStatsException if a matching asset tag stats could not be found
	*/
	public static AssetTagStats findByT_C(long tagId, long classNameId)
		throws com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().findByT_C(tagId, classNameId);
	}

	/**
	* Returns the asset tag stats where tagId = &#63; and classNameId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param tagId the tag ID
	* @param classNameId the class name ID
	* @return the matching asset tag stats, or <code>null</code> if a matching asset tag stats could not be found
	*/
	public static AssetTagStats fetchByT_C(long tagId, long classNameId) {
		return getPersistence().fetchByT_C(tagId, classNameId);
	}

	/**
	* Returns the asset tag stats where tagId = &#63; and classNameId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param tagId the tag ID
	* @param classNameId the class name ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset tag stats, or <code>null</code> if a matching asset tag stats could not be found
	*/
	public static AssetTagStats fetchByT_C(long tagId, long classNameId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByT_C(tagId, classNameId, retrieveFromCache);
	}

	/**
	* Removes the asset tag stats where tagId = &#63; and classNameId = &#63; from the database.
	*
	* @param tagId the tag ID
	* @param classNameId the class name ID
	* @return the asset tag stats that was removed
	*/
	public static AssetTagStats removeByT_C(long tagId, long classNameId)
		throws com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().removeByT_C(tagId, classNameId);
	}

	/**
	* Returns the number of asset tag statses where tagId = &#63; and classNameId = &#63;.
	*
	* @param tagId the tag ID
	* @param classNameId the class name ID
	* @return the number of matching asset tag statses
	*/
	public static int countByT_C(long tagId, long classNameId) {
		return getPersistence().countByT_C(tagId, classNameId);
	}

	/**
	* Caches the asset tag stats in the entity cache if it is enabled.
	*
	* @param assetTagStats the asset tag stats
	*/
	public static void cacheResult(AssetTagStats assetTagStats) {
		getPersistence().cacheResult(assetTagStats);
	}

	/**
	* Caches the asset tag statses in the entity cache if it is enabled.
	*
	* @param assetTagStatses the asset tag statses
	*/
	public static void cacheResult(List<AssetTagStats> assetTagStatses) {
		getPersistence().cacheResult(assetTagStatses);
	}

	/**
	* Creates a new asset tag stats with the primary key. Does not add the asset tag stats to the database.
	*
	* @param tagStatsId the primary key for the new asset tag stats
	* @return the new asset tag stats
	*/
	public static AssetTagStats create(long tagStatsId) {
		return getPersistence().create(tagStatsId);
	}

	/**
	* Removes the asset tag stats with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tagStatsId the primary key of the asset tag stats
	* @return the asset tag stats that was removed
	* @throws NoSuchTagStatsException if a asset tag stats with the primary key could not be found
	*/
	public static AssetTagStats remove(long tagStatsId)
		throws com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().remove(tagStatsId);
	}

	public static AssetTagStats updateImpl(AssetTagStats assetTagStats) {
		return getPersistence().updateImpl(assetTagStats);
	}

	/**
	* Returns the asset tag stats with the primary key or throws a {@link NoSuchTagStatsException} if it could not be found.
	*
	* @param tagStatsId the primary key of the asset tag stats
	* @return the asset tag stats
	* @throws NoSuchTagStatsException if a asset tag stats with the primary key could not be found
	*/
	public static AssetTagStats findByPrimaryKey(long tagStatsId)
		throws com.liferay.portlet.asset.NoSuchTagStatsException {
		return getPersistence().findByPrimaryKey(tagStatsId);
	}

	/**
	* Returns the asset tag stats with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param tagStatsId the primary key of the asset tag stats
	* @return the asset tag stats, or <code>null</code> if a asset tag stats with the primary key could not be found
	*/
	public static AssetTagStats fetchByPrimaryKey(long tagStatsId) {
		return getPersistence().fetchByPrimaryKey(tagStatsId);
	}

	public static java.util.Map<java.io.Serializable, AssetTagStats> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the asset tag statses.
	*
	* @return the asset tag statses
	*/
	public static List<AssetTagStats> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the asset tag statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @return the range of asset tag statses
	*/
	public static List<AssetTagStats> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the asset tag statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset tag statses
	*/
	public static List<AssetTagStats> findAll(int start, int end,
		OrderByComparator<AssetTagStats> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset tag statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset tag statses
	* @param end the upper bound of the range of asset tag statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset tag statses
	*/
	public static List<AssetTagStats> findAll(int start, int end,
		OrderByComparator<AssetTagStats> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the asset tag statses from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset tag statses.
	*
	* @return the number of asset tag statses
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AssetTagStatsPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetTagStatsPersistence)PortalBeanLocatorUtil.locate(AssetTagStatsPersistence.class.getName());

			ReferenceRegistry.registerReference(AssetTagStatsUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static AssetTagStatsPersistence _persistence;
}