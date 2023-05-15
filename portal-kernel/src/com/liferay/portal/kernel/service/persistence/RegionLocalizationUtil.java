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

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.RegionLocalization;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the region localization service. This utility wraps <code>com.liferay.portal.service.persistence.impl.RegionLocalizationPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RegionLocalizationPersistence
 * @generated
 */
public class RegionLocalizationUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(RegionLocalization regionLocalization) {
		getPersistence().clearCache(regionLocalization);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, RegionLocalization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RegionLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RegionLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RegionLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RegionLocalization> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static RegionLocalization update(
		RegionLocalization regionLocalization) {

		return getPersistence().update(regionLocalization);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static RegionLocalization update(
		RegionLocalization regionLocalization, ServiceContext serviceContext) {

		return getPersistence().update(regionLocalization, serviceContext);
	}

	/**
	 * Returns all the region localizations where regionId = &#63;.
	 *
	 * @param regionId the region ID
	 * @return the matching region localizations
	 */
	public static List<RegionLocalization> findByRegionId(long regionId) {
		return getPersistence().findByRegionId(regionId);
	}

	/**
	 * Returns a range of all the region localizations where regionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegionLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param regionId the region ID
	 * @param start the lower bound of the range of region localizations
	 * @param end the upper bound of the range of region localizations (not inclusive)
	 * @return the range of matching region localizations
	 */
	public static List<RegionLocalization> findByRegionId(
		long regionId, int start, int end) {

		return getPersistence().findByRegionId(regionId, start, end);
	}

	/**
	 * Returns an ordered range of all the region localizations where regionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegionLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param regionId the region ID
	 * @param start the lower bound of the range of region localizations
	 * @param end the upper bound of the range of region localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching region localizations
	 */
	public static List<RegionLocalization> findByRegionId(
		long regionId, int start, int end,
		OrderByComparator<RegionLocalization> orderByComparator) {

		return getPersistence().findByRegionId(
			regionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the region localizations where regionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegionLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param regionId the region ID
	 * @param start the lower bound of the range of region localizations
	 * @param end the upper bound of the range of region localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching region localizations
	 */
	public static List<RegionLocalization> findByRegionId(
		long regionId, int start, int end,
		OrderByComparator<RegionLocalization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByRegionId(
			regionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first region localization in the ordered set where regionId = &#63;.
	 *
	 * @param regionId the region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching region localization
	 * @throws NoSuchRegionLocalizationException if a matching region localization could not be found
	 */
	public static RegionLocalization findByRegionId_First(
			long regionId,
			OrderByComparator<RegionLocalization> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchRegionLocalizationException {

		return getPersistence().findByRegionId_First(
			regionId, orderByComparator);
	}

	/**
	 * Returns the first region localization in the ordered set where regionId = &#63;.
	 *
	 * @param regionId the region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching region localization, or <code>null</code> if a matching region localization could not be found
	 */
	public static RegionLocalization fetchByRegionId_First(
		long regionId,
		OrderByComparator<RegionLocalization> orderByComparator) {

		return getPersistence().fetchByRegionId_First(
			regionId, orderByComparator);
	}

	/**
	 * Returns the last region localization in the ordered set where regionId = &#63;.
	 *
	 * @param regionId the region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching region localization
	 * @throws NoSuchRegionLocalizationException if a matching region localization could not be found
	 */
	public static RegionLocalization findByRegionId_Last(
			long regionId,
			OrderByComparator<RegionLocalization> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchRegionLocalizationException {

		return getPersistence().findByRegionId_Last(
			regionId, orderByComparator);
	}

	/**
	 * Returns the last region localization in the ordered set where regionId = &#63;.
	 *
	 * @param regionId the region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching region localization, or <code>null</code> if a matching region localization could not be found
	 */
	public static RegionLocalization fetchByRegionId_Last(
		long regionId,
		OrderByComparator<RegionLocalization> orderByComparator) {

		return getPersistence().fetchByRegionId_Last(
			regionId, orderByComparator);
	}

	/**
	 * Returns the region localizations before and after the current region localization in the ordered set where regionId = &#63;.
	 *
	 * @param regionLocalizationId the primary key of the current region localization
	 * @param regionId the region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next region localization
	 * @throws NoSuchRegionLocalizationException if a region localization with the primary key could not be found
	 */
	public static RegionLocalization[] findByRegionId_PrevAndNext(
			long regionLocalizationId, long regionId,
			OrderByComparator<RegionLocalization> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchRegionLocalizationException {

		return getPersistence().findByRegionId_PrevAndNext(
			regionLocalizationId, regionId, orderByComparator);
	}

	/**
	 * Removes all the region localizations where regionId = &#63; from the database.
	 *
	 * @param regionId the region ID
	 */
	public static void removeByRegionId(long regionId) {
		getPersistence().removeByRegionId(regionId);
	}

	/**
	 * Returns the number of region localizations where regionId = &#63;.
	 *
	 * @param regionId the region ID
	 * @return the number of matching region localizations
	 */
	public static int countByRegionId(long regionId) {
		return getPersistence().countByRegionId(regionId);
	}

	/**
	 * Returns the region localization where regionId = &#63; and languageId = &#63; or throws a <code>NoSuchRegionLocalizationException</code> if it could not be found.
	 *
	 * @param regionId the region ID
	 * @param languageId the language ID
	 * @return the matching region localization
	 * @throws NoSuchRegionLocalizationException if a matching region localization could not be found
	 */
	public static RegionLocalization findByRegionId_LanguageId(
			long regionId, String languageId)
		throws com.liferay.portal.kernel.exception.
			NoSuchRegionLocalizationException {

		return getPersistence().findByRegionId_LanguageId(regionId, languageId);
	}

	/**
	 * Returns the region localization where regionId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param regionId the region ID
	 * @param languageId the language ID
	 * @return the matching region localization, or <code>null</code> if a matching region localization could not be found
	 */
	public static RegionLocalization fetchByRegionId_LanguageId(
		long regionId, String languageId) {

		return getPersistence().fetchByRegionId_LanguageId(
			regionId, languageId);
	}

	/**
	 * Returns the region localization where regionId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param regionId the region ID
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching region localization, or <code>null</code> if a matching region localization could not be found
	 */
	public static RegionLocalization fetchByRegionId_LanguageId(
		long regionId, String languageId, boolean useFinderCache) {

		return getPersistence().fetchByRegionId_LanguageId(
			regionId, languageId, useFinderCache);
	}

	/**
	 * Removes the region localization where regionId = &#63; and languageId = &#63; from the database.
	 *
	 * @param regionId the region ID
	 * @param languageId the language ID
	 * @return the region localization that was removed
	 */
	public static RegionLocalization removeByRegionId_LanguageId(
			long regionId, String languageId)
		throws com.liferay.portal.kernel.exception.
			NoSuchRegionLocalizationException {

		return getPersistence().removeByRegionId_LanguageId(
			regionId, languageId);
	}

	/**
	 * Returns the number of region localizations where regionId = &#63; and languageId = &#63;.
	 *
	 * @param regionId the region ID
	 * @param languageId the language ID
	 * @return the number of matching region localizations
	 */
	public static int countByRegionId_LanguageId(
		long regionId, String languageId) {

		return getPersistence().countByRegionId_LanguageId(
			regionId, languageId);
	}

	/**
	 * Caches the region localization in the entity cache if it is enabled.
	 *
	 * @param regionLocalization the region localization
	 */
	public static void cacheResult(RegionLocalization regionLocalization) {
		getPersistence().cacheResult(regionLocalization);
	}

	/**
	 * Caches the region localizations in the entity cache if it is enabled.
	 *
	 * @param regionLocalizations the region localizations
	 */
	public static void cacheResult(
		List<RegionLocalization> regionLocalizations) {

		getPersistence().cacheResult(regionLocalizations);
	}

	/**
	 * Creates a new region localization with the primary key. Does not add the region localization to the database.
	 *
	 * @param regionLocalizationId the primary key for the new region localization
	 * @return the new region localization
	 */
	public static RegionLocalization create(long regionLocalizationId) {
		return getPersistence().create(regionLocalizationId);
	}

	/**
	 * Removes the region localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param regionLocalizationId the primary key of the region localization
	 * @return the region localization that was removed
	 * @throws NoSuchRegionLocalizationException if a region localization with the primary key could not be found
	 */
	public static RegionLocalization remove(long regionLocalizationId)
		throws com.liferay.portal.kernel.exception.
			NoSuchRegionLocalizationException {

		return getPersistence().remove(regionLocalizationId);
	}

	public static RegionLocalization updateImpl(
		RegionLocalization regionLocalization) {

		return getPersistence().updateImpl(regionLocalization);
	}

	/**
	 * Returns the region localization with the primary key or throws a <code>NoSuchRegionLocalizationException</code> if it could not be found.
	 *
	 * @param regionLocalizationId the primary key of the region localization
	 * @return the region localization
	 * @throws NoSuchRegionLocalizationException if a region localization with the primary key could not be found
	 */
	public static RegionLocalization findByPrimaryKey(long regionLocalizationId)
		throws com.liferay.portal.kernel.exception.
			NoSuchRegionLocalizationException {

		return getPersistence().findByPrimaryKey(regionLocalizationId);
	}

	/**
	 * Returns the region localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param regionLocalizationId the primary key of the region localization
	 * @return the region localization, or <code>null</code> if a region localization with the primary key could not be found
	 */
	public static RegionLocalization fetchByPrimaryKey(
		long regionLocalizationId) {

		return getPersistence().fetchByPrimaryKey(regionLocalizationId);
	}

	/**
	 * Returns all the region localizations.
	 *
	 * @return the region localizations
	 */
	public static List<RegionLocalization> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the region localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegionLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of region localizations
	 * @param end the upper bound of the range of region localizations (not inclusive)
	 * @return the range of region localizations
	 */
	public static List<RegionLocalization> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the region localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegionLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of region localizations
	 * @param end the upper bound of the range of region localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of region localizations
	 */
	public static List<RegionLocalization> findAll(
		int start, int end,
		OrderByComparator<RegionLocalization> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the region localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RegionLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of region localizations
	 * @param end the upper bound of the range of region localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of region localizations
	 */
	public static List<RegionLocalization> findAll(
		int start, int end,
		OrderByComparator<RegionLocalization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the region localizations from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of region localizations.
	 *
	 * @return the number of region localizations
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RegionLocalizationPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		RegionLocalizationPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile RegionLocalizationPersistence _persistence;

}