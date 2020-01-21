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

package com.liferay.change.tracking.service.persistence;

import com.liferay.change.tracking.model.CTAutoResolutionInfo;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the ct auto resolution info service. This utility wraps <code>com.liferay.change.tracking.service.persistence.impl.CTAutoResolutionInfoPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTAutoResolutionInfoPersistence
 * @generated
 */
public class CTAutoResolutionInfoUtil {

	/*
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
	public static void clearCache(CTAutoResolutionInfo ctAutoResolutionInfo) {
		getPersistence().clearCache(ctAutoResolutionInfo);
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
	public static Map<Serializable, CTAutoResolutionInfo> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CTAutoResolutionInfo> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CTAutoResolutionInfo> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CTAutoResolutionInfo> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CTAutoResolutionInfo update(
		CTAutoResolutionInfo ctAutoResolutionInfo) {

		return getPersistence().update(ctAutoResolutionInfo);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CTAutoResolutionInfo update(
		CTAutoResolutionInfo ctAutoResolutionInfo,
		ServiceContext serviceContext) {

		return getPersistence().update(ctAutoResolutionInfo, serviceContext);
	}

	/**
	 * Returns all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct auto resolution infos
	 */
	public static List<CTAutoResolutionInfo> findByCTCollectionId(
		long ctCollectionId) {

		return getPersistence().findByCTCollectionId(ctCollectionId);
	}

	/**
	 * Returns a range of all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @return the range of matching ct auto resolution infos
	 */
	public static List<CTAutoResolutionInfo> findByCTCollectionId(
		long ctCollectionId, int start, int end) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct auto resolution infos
	 */
	public static List<CTAutoResolutionInfo> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct auto resolution infos
	 */
	public static List<CTAutoResolutionInfo> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a matching ct auto resolution info could not be found
	 */
	public static CTAutoResolutionInfo findByCTCollectionId_First(
			long ctCollectionId,
			OrderByComparator<CTAutoResolutionInfo> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchAutoResolutionInfoException {

		return getPersistence().findByCTCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the first ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct auto resolution info, or <code>null</code> if a matching ct auto resolution info could not be found
	 */
	public static CTAutoResolutionInfo fetchByCTCollectionId_First(
		long ctCollectionId,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		return getPersistence().fetchByCTCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a matching ct auto resolution info could not be found
	 */
	public static CTAutoResolutionInfo findByCTCollectionId_Last(
			long ctCollectionId,
			OrderByComparator<CTAutoResolutionInfo> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchAutoResolutionInfoException {

		return getPersistence().findByCTCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct auto resolution info, or <code>null</code> if a matching ct auto resolution info could not be found
	 */
	public static CTAutoResolutionInfo fetchByCTCollectionId_Last(
		long ctCollectionId,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		return getPersistence().fetchByCTCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the ct auto resolution infos before and after the current ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the current ct auto resolution info
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a ct auto resolution info with the primary key could not be found
	 */
	public static CTAutoResolutionInfo[] findByCTCollectionId_PrevAndNext(
			long ctAutoResolutionInfoId, long ctCollectionId,
			OrderByComparator<CTAutoResolutionInfo> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchAutoResolutionInfoException {

		return getPersistence().findByCTCollectionId_PrevAndNext(
			ctAutoResolutionInfoId, ctCollectionId, orderByComparator);
	}

	/**
	 * Removes all the ct auto resolution infos where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public static void removeByCTCollectionId(long ctCollectionId) {
		getPersistence().removeByCTCollectionId(ctCollectionId);
	}

	/**
	 * Returns the number of ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct auto resolution infos
	 */
	public static int countByCTCollectionId(long ctCollectionId) {
		return getPersistence().countByCTCollectionId(ctCollectionId);
	}

	/**
	 * Caches the ct auto resolution info in the entity cache if it is enabled.
	 *
	 * @param ctAutoResolutionInfo the ct auto resolution info
	 */
	public static void cacheResult(CTAutoResolutionInfo ctAutoResolutionInfo) {
		getPersistence().cacheResult(ctAutoResolutionInfo);
	}

	/**
	 * Caches the ct auto resolution infos in the entity cache if it is enabled.
	 *
	 * @param ctAutoResolutionInfos the ct auto resolution infos
	 */
	public static void cacheResult(
		List<CTAutoResolutionInfo> ctAutoResolutionInfos) {

		getPersistence().cacheResult(ctAutoResolutionInfos);
	}

	/**
	 * Creates a new ct auto resolution info with the primary key. Does not add the ct auto resolution info to the database.
	 *
	 * @param ctAutoResolutionInfoId the primary key for the new ct auto resolution info
	 * @return the new ct auto resolution info
	 */
	public static CTAutoResolutionInfo create(long ctAutoResolutionInfoId) {
		return getPersistence().create(ctAutoResolutionInfoId);
	}

	/**
	 * Removes the ct auto resolution info with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the ct auto resolution info
	 * @return the ct auto resolution info that was removed
	 * @throws NoSuchAutoResolutionInfoException if a ct auto resolution info with the primary key could not be found
	 */
	public static CTAutoResolutionInfo remove(long ctAutoResolutionInfoId)
		throws com.liferay.change.tracking.exception.
			NoSuchAutoResolutionInfoException {

		return getPersistence().remove(ctAutoResolutionInfoId);
	}

	public static CTAutoResolutionInfo updateImpl(
		CTAutoResolutionInfo ctAutoResolutionInfo) {

		return getPersistence().updateImpl(ctAutoResolutionInfo);
	}

	/**
	 * Returns the ct auto resolution info with the primary key or throws a <code>NoSuchAutoResolutionInfoException</code> if it could not be found.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the ct auto resolution info
	 * @return the ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a ct auto resolution info with the primary key could not be found
	 */
	public static CTAutoResolutionInfo findByPrimaryKey(
			long ctAutoResolutionInfoId)
		throws com.liferay.change.tracking.exception.
			NoSuchAutoResolutionInfoException {

		return getPersistence().findByPrimaryKey(ctAutoResolutionInfoId);
	}

	/**
	 * Returns the ct auto resolution info with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the ct auto resolution info
	 * @return the ct auto resolution info, or <code>null</code> if a ct auto resolution info with the primary key could not be found
	 */
	public static CTAutoResolutionInfo fetchByPrimaryKey(
		long ctAutoResolutionInfoId) {

		return getPersistence().fetchByPrimaryKey(ctAutoResolutionInfoId);
	}

	/**
	 * Returns all the ct auto resolution infos.
	 *
	 * @return the ct auto resolution infos
	 */
	public static List<CTAutoResolutionInfo> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ct auto resolution infos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @return the range of ct auto resolution infos
	 */
	public static List<CTAutoResolutionInfo> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct auto resolution infos
	 */
	public static List<CTAutoResolutionInfo> findAll(
		int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct auto resolution infos
	 */
	public static List<CTAutoResolutionInfo> findAll(
		int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ct auto resolution infos from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ct auto resolution infos.
	 *
	 * @return the number of ct auto resolution infos
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CTAutoResolutionInfoPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CTAutoResolutionInfoPersistence, CTAutoResolutionInfoPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CTAutoResolutionInfoPersistence.class);

		ServiceTracker
			<CTAutoResolutionInfoPersistence, CTAutoResolutionInfoPersistence>
				serviceTracker =
					new ServiceTracker
						<CTAutoResolutionInfoPersistence,
						 CTAutoResolutionInfoPersistence>(
							 bundle.getBundleContext(),
							 CTAutoResolutionInfoPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}