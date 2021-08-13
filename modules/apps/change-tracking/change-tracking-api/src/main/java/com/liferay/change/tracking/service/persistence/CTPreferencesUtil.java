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

import com.liferay.change.tracking.model.CTPreferences;
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
 * The persistence utility for the ct preferences service. This utility wraps <code>com.liferay.change.tracking.service.persistence.impl.CTPreferencesPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTPreferencesPersistence
 * @generated
 */
public class CTPreferencesUtil {

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
	public static void clearCache(CTPreferences ctPreferences) {
		getPersistence().clearCache(ctPreferences);
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
	public static Map<Serializable, CTPreferences> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CTPreferences> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CTPreferences> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CTPreferences> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CTPreferences> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CTPreferences update(CTPreferences ctPreferences) {
		return getPersistence().update(ctPreferences);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CTPreferences update(
		CTPreferences ctPreferences, ServiceContext serviceContext) {

		return getPersistence().update(ctPreferences, serviceContext);
	}

	/**
	 * Returns all the ct preferenceses where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct preferenceses
	 */
	public static List<CTPreferences> findByCtCollectionId(
		long ctCollectionId) {

		return getPersistence().findByCtCollectionId(ctCollectionId);
	}

	/**
	 * Returns a range of all the ct preferenceses where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @return the range of matching ct preferenceses
	 */
	public static List<CTPreferences> findByCtCollectionId(
		long ctCollectionId, int start, int end) {

		return getPersistence().findByCtCollectionId(
			ctCollectionId, start, end);
	}

	/**
	 * Returns an ordered range of all the ct preferenceses where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct preferenceses
	 */
	public static List<CTPreferences> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTPreferences> orderByComparator) {

		return getPersistence().findByCtCollectionId(
			ctCollectionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct preferenceses where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct preferenceses
	 */
	public static List<CTPreferences> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTPreferences> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCtCollectionId(
			ctCollectionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ct preferences in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct preferences
	 * @throws NoSuchPreferencesException if a matching ct preferences could not be found
	 */
	public static CTPreferences findByCtCollectionId_First(
			long ctCollectionId,
			OrderByComparator<CTPreferences> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchPreferencesException {

		return getPersistence().findByCtCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the first ct preferences in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct preferences, or <code>null</code> if a matching ct preferences could not be found
	 */
	public static CTPreferences fetchByCtCollectionId_First(
		long ctCollectionId,
		OrderByComparator<CTPreferences> orderByComparator) {

		return getPersistence().fetchByCtCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last ct preferences in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct preferences
	 * @throws NoSuchPreferencesException if a matching ct preferences could not be found
	 */
	public static CTPreferences findByCtCollectionId_Last(
			long ctCollectionId,
			OrderByComparator<CTPreferences> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchPreferencesException {

		return getPersistence().findByCtCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last ct preferences in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct preferences, or <code>null</code> if a matching ct preferences could not be found
	 */
	public static CTPreferences fetchByCtCollectionId_Last(
		long ctCollectionId,
		OrderByComparator<CTPreferences> orderByComparator) {

		return getPersistence().fetchByCtCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the ct preferenceses before and after the current ct preferences in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctPreferencesId the primary key of the current ct preferences
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct preferences
	 * @throws NoSuchPreferencesException if a ct preferences with the primary key could not be found
	 */
	public static CTPreferences[] findByCtCollectionId_PrevAndNext(
			long ctPreferencesId, long ctCollectionId,
			OrderByComparator<CTPreferences> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchPreferencesException {

		return getPersistence().findByCtCollectionId_PrevAndNext(
			ctPreferencesId, ctCollectionId, orderByComparator);
	}

	/**
	 * Removes all the ct preferenceses where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public static void removeByCtCollectionId(long ctCollectionId) {
		getPersistence().removeByCtCollectionId(ctCollectionId);
	}

	/**
	 * Returns the number of ct preferenceses where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct preferenceses
	 */
	public static int countByCtCollectionId(long ctCollectionId) {
		return getPersistence().countByCtCollectionId(ctCollectionId);
	}

	/**
	 * Returns all the ct preferenceses where previousCtCollectionId = &#63;.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @return the matching ct preferenceses
	 */
	public static List<CTPreferences> findByPreviousCtCollectionId(
		long previousCtCollectionId) {

		return getPersistence().findByPreviousCtCollectionId(
			previousCtCollectionId);
	}

	/**
	 * Returns a range of all the ct preferenceses where previousCtCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @return the range of matching ct preferenceses
	 */
	public static List<CTPreferences> findByPreviousCtCollectionId(
		long previousCtCollectionId, int start, int end) {

		return getPersistence().findByPreviousCtCollectionId(
			previousCtCollectionId, start, end);
	}

	/**
	 * Returns an ordered range of all the ct preferenceses where previousCtCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct preferenceses
	 */
	public static List<CTPreferences> findByPreviousCtCollectionId(
		long previousCtCollectionId, int start, int end,
		OrderByComparator<CTPreferences> orderByComparator) {

		return getPersistence().findByPreviousCtCollectionId(
			previousCtCollectionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct preferenceses where previousCtCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct preferenceses
	 */
	public static List<CTPreferences> findByPreviousCtCollectionId(
		long previousCtCollectionId, int start, int end,
		OrderByComparator<CTPreferences> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByPreviousCtCollectionId(
			previousCtCollectionId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first ct preferences in the ordered set where previousCtCollectionId = &#63;.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct preferences
	 * @throws NoSuchPreferencesException if a matching ct preferences could not be found
	 */
	public static CTPreferences findByPreviousCtCollectionId_First(
			long previousCtCollectionId,
			OrderByComparator<CTPreferences> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchPreferencesException {

		return getPersistence().findByPreviousCtCollectionId_First(
			previousCtCollectionId, orderByComparator);
	}

	/**
	 * Returns the first ct preferences in the ordered set where previousCtCollectionId = &#63;.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct preferences, or <code>null</code> if a matching ct preferences could not be found
	 */
	public static CTPreferences fetchByPreviousCtCollectionId_First(
		long previousCtCollectionId,
		OrderByComparator<CTPreferences> orderByComparator) {

		return getPersistence().fetchByPreviousCtCollectionId_First(
			previousCtCollectionId, orderByComparator);
	}

	/**
	 * Returns the last ct preferences in the ordered set where previousCtCollectionId = &#63;.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct preferences
	 * @throws NoSuchPreferencesException if a matching ct preferences could not be found
	 */
	public static CTPreferences findByPreviousCtCollectionId_Last(
			long previousCtCollectionId,
			OrderByComparator<CTPreferences> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchPreferencesException {

		return getPersistence().findByPreviousCtCollectionId_Last(
			previousCtCollectionId, orderByComparator);
	}

	/**
	 * Returns the last ct preferences in the ordered set where previousCtCollectionId = &#63;.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct preferences, or <code>null</code> if a matching ct preferences could not be found
	 */
	public static CTPreferences fetchByPreviousCtCollectionId_Last(
		long previousCtCollectionId,
		OrderByComparator<CTPreferences> orderByComparator) {

		return getPersistence().fetchByPreviousCtCollectionId_Last(
			previousCtCollectionId, orderByComparator);
	}

	/**
	 * Returns the ct preferenceses before and after the current ct preferences in the ordered set where previousCtCollectionId = &#63;.
	 *
	 * @param ctPreferencesId the primary key of the current ct preferences
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct preferences
	 * @throws NoSuchPreferencesException if a ct preferences with the primary key could not be found
	 */
	public static CTPreferences[] findByPreviousCtCollectionId_PrevAndNext(
			long ctPreferencesId, long previousCtCollectionId,
			OrderByComparator<CTPreferences> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchPreferencesException {

		return getPersistence().findByPreviousCtCollectionId_PrevAndNext(
			ctPreferencesId, previousCtCollectionId, orderByComparator);
	}

	/**
	 * Removes all the ct preferenceses where previousCtCollectionId = &#63; from the database.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 */
	public static void removeByPreviousCtCollectionId(
		long previousCtCollectionId) {

		getPersistence().removeByPreviousCtCollectionId(previousCtCollectionId);
	}

	/**
	 * Returns the number of ct preferenceses where previousCtCollectionId = &#63;.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @return the number of matching ct preferenceses
	 */
	public static int countByPreviousCtCollectionId(
		long previousCtCollectionId) {

		return getPersistence().countByPreviousCtCollectionId(
			previousCtCollectionId);
	}

	/**
	 * Returns the ct preferences where companyId = &#63; and userId = &#63; or throws a <code>NoSuchPreferencesException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching ct preferences
	 * @throws NoSuchPreferencesException if a matching ct preferences could not be found
	 */
	public static CTPreferences findByC_U(long companyId, long userId)
		throws com.liferay.change.tracking.exception.
			NoSuchPreferencesException {

		return getPersistence().findByC_U(companyId, userId);
	}

	/**
	 * Returns the ct preferences where companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching ct preferences, or <code>null</code> if a matching ct preferences could not be found
	 */
	public static CTPreferences fetchByC_U(long companyId, long userId) {
		return getPersistence().fetchByC_U(companyId, userId);
	}

	/**
	 * Returns the ct preferences where companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ct preferences, or <code>null</code> if a matching ct preferences could not be found
	 */
	public static CTPreferences fetchByC_U(
		long companyId, long userId, boolean useFinderCache) {

		return getPersistence().fetchByC_U(companyId, userId, useFinderCache);
	}

	/**
	 * Removes the ct preferences where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the ct preferences that was removed
	 */
	public static CTPreferences removeByC_U(long companyId, long userId)
		throws com.liferay.change.tracking.exception.
			NoSuchPreferencesException {

		return getPersistence().removeByC_U(companyId, userId);
	}

	/**
	 * Returns the number of ct preferenceses where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching ct preferenceses
	 */
	public static int countByC_U(long companyId, long userId) {
		return getPersistence().countByC_U(companyId, userId);
	}

	/**
	 * Caches the ct preferences in the entity cache if it is enabled.
	 *
	 * @param ctPreferences the ct preferences
	 */
	public static void cacheResult(CTPreferences ctPreferences) {
		getPersistence().cacheResult(ctPreferences);
	}

	/**
	 * Caches the ct preferenceses in the entity cache if it is enabled.
	 *
	 * @param ctPreferenceses the ct preferenceses
	 */
	public static void cacheResult(List<CTPreferences> ctPreferenceses) {
		getPersistence().cacheResult(ctPreferenceses);
	}

	/**
	 * Creates a new ct preferences with the primary key. Does not add the ct preferences to the database.
	 *
	 * @param ctPreferencesId the primary key for the new ct preferences
	 * @return the new ct preferences
	 */
	public static CTPreferences create(long ctPreferencesId) {
		return getPersistence().create(ctPreferencesId);
	}

	/**
	 * Removes the ct preferences with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctPreferencesId the primary key of the ct preferences
	 * @return the ct preferences that was removed
	 * @throws NoSuchPreferencesException if a ct preferences with the primary key could not be found
	 */
	public static CTPreferences remove(long ctPreferencesId)
		throws com.liferay.change.tracking.exception.
			NoSuchPreferencesException {

		return getPersistence().remove(ctPreferencesId);
	}

	public static CTPreferences updateImpl(CTPreferences ctPreferences) {
		return getPersistence().updateImpl(ctPreferences);
	}

	/**
	 * Returns the ct preferences with the primary key or throws a <code>NoSuchPreferencesException</code> if it could not be found.
	 *
	 * @param ctPreferencesId the primary key of the ct preferences
	 * @return the ct preferences
	 * @throws NoSuchPreferencesException if a ct preferences with the primary key could not be found
	 */
	public static CTPreferences findByPrimaryKey(long ctPreferencesId)
		throws com.liferay.change.tracking.exception.
			NoSuchPreferencesException {

		return getPersistence().findByPrimaryKey(ctPreferencesId);
	}

	/**
	 * Returns the ct preferences with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctPreferencesId the primary key of the ct preferences
	 * @return the ct preferences, or <code>null</code> if a ct preferences with the primary key could not be found
	 */
	public static CTPreferences fetchByPrimaryKey(long ctPreferencesId) {
		return getPersistence().fetchByPrimaryKey(ctPreferencesId);
	}

	/**
	 * Returns all the ct preferenceses.
	 *
	 * @return the ct preferenceses
	 */
	public static List<CTPreferences> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ct preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @return the range of ct preferenceses
	 */
	public static List<CTPreferences> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ct preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct preferenceses
	 */
	public static List<CTPreferences> findAll(
		int start, int end,
		OrderByComparator<CTPreferences> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct preferenceses
	 */
	public static List<CTPreferences> findAll(
		int start, int end, OrderByComparator<CTPreferences> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ct preferenceses from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ct preferenceses.
	 *
	 * @return the number of ct preferenceses
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CTPreferencesPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CTPreferencesPersistence, CTPreferencesPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CTPreferencesPersistence.class);

		ServiceTracker<CTPreferencesPersistence, CTPreferencesPersistence>
			serviceTracker =
				new ServiceTracker
					<CTPreferencesPersistence, CTPreferencesPersistence>(
						bundle.getBundleContext(),
						CTPreferencesPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}