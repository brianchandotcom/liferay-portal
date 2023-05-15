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

package com.liferay.commerce.term.service.persistence;

import com.liferay.commerce.term.model.CTermEntryLocalization;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the c term entry localization service. This utility wraps <code>com.liferay.commerce.term.service.persistence.impl.CTermEntryLocalizationPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CTermEntryLocalizationPersistence
 * @generated
 */
public class CTermEntryLocalizationUtil {

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
	public static void clearCache(
		CTermEntryLocalization cTermEntryLocalization) {

		getPersistence().clearCache(cTermEntryLocalization);
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
	public static Map<Serializable, CTermEntryLocalization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CTermEntryLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CTermEntryLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CTermEntryLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CTermEntryLocalization> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CTermEntryLocalization update(
		CTermEntryLocalization cTermEntryLocalization) {

		return getPersistence().update(cTermEntryLocalization);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CTermEntryLocalization update(
		CTermEntryLocalization cTermEntryLocalization,
		ServiceContext serviceContext) {

		return getPersistence().update(cTermEntryLocalization, serviceContext);
	}

	/**
	 * Returns all the c term entry localizations where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the matching c term entry localizations
	 */
	public static List<CTermEntryLocalization> findByCommerceTermEntryId(
		long commerceTermEntryId) {

		return getPersistence().findByCommerceTermEntryId(commerceTermEntryId);
	}

	/**
	 * Returns a range of all the c term entry localizations where commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTermEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of c term entry localizations
	 * @param end the upper bound of the range of c term entry localizations (not inclusive)
	 * @return the range of matching c term entry localizations
	 */
	public static List<CTermEntryLocalization> findByCommerceTermEntryId(
		long commerceTermEntryId, int start, int end) {

		return getPersistence().findByCommerceTermEntryId(
			commerceTermEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the c term entry localizations where commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTermEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of c term entry localizations
	 * @param end the upper bound of the range of c term entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching c term entry localizations
	 */
	public static List<CTermEntryLocalization> findByCommerceTermEntryId(
		long commerceTermEntryId, int start, int end,
		OrderByComparator<CTermEntryLocalization> orderByComparator) {

		return getPersistence().findByCommerceTermEntryId(
			commerceTermEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the c term entry localizations where commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTermEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of c term entry localizations
	 * @param end the upper bound of the range of c term entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching c term entry localizations
	 */
	public static List<CTermEntryLocalization> findByCommerceTermEntryId(
		long commerceTermEntryId, int start, int end,
		OrderByComparator<CTermEntryLocalization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceTermEntryId(
			commerceTermEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first c term entry localization in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c term entry localization
	 * @throws NoSuchCTermEntryLocalizationException if a matching c term entry localization could not be found
	 */
	public static CTermEntryLocalization findByCommerceTermEntryId_First(
			long commerceTermEntryId,
			OrderByComparator<CTermEntryLocalization> orderByComparator)
		throws com.liferay.commerce.term.exception.
			NoSuchCTermEntryLocalizationException {

		return getPersistence().findByCommerceTermEntryId_First(
			commerceTermEntryId, orderByComparator);
	}

	/**
	 * Returns the first c term entry localization in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c term entry localization, or <code>null</code> if a matching c term entry localization could not be found
	 */
	public static CTermEntryLocalization fetchByCommerceTermEntryId_First(
		long commerceTermEntryId,
		OrderByComparator<CTermEntryLocalization> orderByComparator) {

		return getPersistence().fetchByCommerceTermEntryId_First(
			commerceTermEntryId, orderByComparator);
	}

	/**
	 * Returns the last c term entry localization in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c term entry localization
	 * @throws NoSuchCTermEntryLocalizationException if a matching c term entry localization could not be found
	 */
	public static CTermEntryLocalization findByCommerceTermEntryId_Last(
			long commerceTermEntryId,
			OrderByComparator<CTermEntryLocalization> orderByComparator)
		throws com.liferay.commerce.term.exception.
			NoSuchCTermEntryLocalizationException {

		return getPersistence().findByCommerceTermEntryId_Last(
			commerceTermEntryId, orderByComparator);
	}

	/**
	 * Returns the last c term entry localization in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c term entry localization, or <code>null</code> if a matching c term entry localization could not be found
	 */
	public static CTermEntryLocalization fetchByCommerceTermEntryId_Last(
		long commerceTermEntryId,
		OrderByComparator<CTermEntryLocalization> orderByComparator) {

		return getPersistence().fetchByCommerceTermEntryId_Last(
			commerceTermEntryId, orderByComparator);
	}

	/**
	 * Returns the c term entry localizations before and after the current c term entry localization in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param cTermEntryLocalizationId the primary key of the current c term entry localization
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next c term entry localization
	 * @throws NoSuchCTermEntryLocalizationException if a c term entry localization with the primary key could not be found
	 */
	public static CTermEntryLocalization[]
			findByCommerceTermEntryId_PrevAndNext(
				long cTermEntryLocalizationId, long commerceTermEntryId,
				OrderByComparator<CTermEntryLocalization> orderByComparator)
		throws com.liferay.commerce.term.exception.
			NoSuchCTermEntryLocalizationException {

		return getPersistence().findByCommerceTermEntryId_PrevAndNext(
			cTermEntryLocalizationId, commerceTermEntryId, orderByComparator);
	}

	/**
	 * Removes all the c term entry localizations where commerceTermEntryId = &#63; from the database.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 */
	public static void removeByCommerceTermEntryId(long commerceTermEntryId) {
		getPersistence().removeByCommerceTermEntryId(commerceTermEntryId);
	}

	/**
	 * Returns the number of c term entry localizations where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the number of matching c term entry localizations
	 */
	public static int countByCommerceTermEntryId(long commerceTermEntryId) {
		return getPersistence().countByCommerceTermEntryId(commerceTermEntryId);
	}

	/**
	 * Returns the c term entry localization where commerceTermEntryId = &#63; and languageId = &#63; or throws a <code>NoSuchCTermEntryLocalizationException</code> if it could not be found.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param languageId the language ID
	 * @return the matching c term entry localization
	 * @throws NoSuchCTermEntryLocalizationException if a matching c term entry localization could not be found
	 */
	public static CTermEntryLocalization findByCommerceTermEntryId_LanguageId(
			long commerceTermEntryId, String languageId)
		throws com.liferay.commerce.term.exception.
			NoSuchCTermEntryLocalizationException {

		return getPersistence().findByCommerceTermEntryId_LanguageId(
			commerceTermEntryId, languageId);
	}

	/**
	 * Returns the c term entry localization where commerceTermEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param languageId the language ID
	 * @return the matching c term entry localization, or <code>null</code> if a matching c term entry localization could not be found
	 */
	public static CTermEntryLocalization fetchByCommerceTermEntryId_LanguageId(
		long commerceTermEntryId, String languageId) {

		return getPersistence().fetchByCommerceTermEntryId_LanguageId(
			commerceTermEntryId, languageId);
	}

	/**
	 * Returns the c term entry localization where commerceTermEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching c term entry localization, or <code>null</code> if a matching c term entry localization could not be found
	 */
	public static CTermEntryLocalization fetchByCommerceTermEntryId_LanguageId(
		long commerceTermEntryId, String languageId, boolean useFinderCache) {

		return getPersistence().fetchByCommerceTermEntryId_LanguageId(
			commerceTermEntryId, languageId, useFinderCache);
	}

	/**
	 * Removes the c term entry localization where commerceTermEntryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param languageId the language ID
	 * @return the c term entry localization that was removed
	 */
	public static CTermEntryLocalization removeByCommerceTermEntryId_LanguageId(
			long commerceTermEntryId, String languageId)
		throws com.liferay.commerce.term.exception.
			NoSuchCTermEntryLocalizationException {

		return getPersistence().removeByCommerceTermEntryId_LanguageId(
			commerceTermEntryId, languageId);
	}

	/**
	 * Returns the number of c term entry localizations where commerceTermEntryId = &#63; and languageId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param languageId the language ID
	 * @return the number of matching c term entry localizations
	 */
	public static int countByCommerceTermEntryId_LanguageId(
		long commerceTermEntryId, String languageId) {

		return getPersistence().countByCommerceTermEntryId_LanguageId(
			commerceTermEntryId, languageId);
	}

	/**
	 * Caches the c term entry localization in the entity cache if it is enabled.
	 *
	 * @param cTermEntryLocalization the c term entry localization
	 */
	public static void cacheResult(
		CTermEntryLocalization cTermEntryLocalization) {

		getPersistence().cacheResult(cTermEntryLocalization);
	}

	/**
	 * Caches the c term entry localizations in the entity cache if it is enabled.
	 *
	 * @param cTermEntryLocalizations the c term entry localizations
	 */
	public static void cacheResult(
		List<CTermEntryLocalization> cTermEntryLocalizations) {

		getPersistence().cacheResult(cTermEntryLocalizations);
	}

	/**
	 * Creates a new c term entry localization with the primary key. Does not add the c term entry localization to the database.
	 *
	 * @param cTermEntryLocalizationId the primary key for the new c term entry localization
	 * @return the new c term entry localization
	 */
	public static CTermEntryLocalization create(long cTermEntryLocalizationId) {
		return getPersistence().create(cTermEntryLocalizationId);
	}

	/**
	 * Removes the c term entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cTermEntryLocalizationId the primary key of the c term entry localization
	 * @return the c term entry localization that was removed
	 * @throws NoSuchCTermEntryLocalizationException if a c term entry localization with the primary key could not be found
	 */
	public static CTermEntryLocalization remove(long cTermEntryLocalizationId)
		throws com.liferay.commerce.term.exception.
			NoSuchCTermEntryLocalizationException {

		return getPersistence().remove(cTermEntryLocalizationId);
	}

	public static CTermEntryLocalization updateImpl(
		CTermEntryLocalization cTermEntryLocalization) {

		return getPersistence().updateImpl(cTermEntryLocalization);
	}

	/**
	 * Returns the c term entry localization with the primary key or throws a <code>NoSuchCTermEntryLocalizationException</code> if it could not be found.
	 *
	 * @param cTermEntryLocalizationId the primary key of the c term entry localization
	 * @return the c term entry localization
	 * @throws NoSuchCTermEntryLocalizationException if a c term entry localization with the primary key could not be found
	 */
	public static CTermEntryLocalization findByPrimaryKey(
			long cTermEntryLocalizationId)
		throws com.liferay.commerce.term.exception.
			NoSuchCTermEntryLocalizationException {

		return getPersistence().findByPrimaryKey(cTermEntryLocalizationId);
	}

	/**
	 * Returns the c term entry localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cTermEntryLocalizationId the primary key of the c term entry localization
	 * @return the c term entry localization, or <code>null</code> if a c term entry localization with the primary key could not be found
	 */
	public static CTermEntryLocalization fetchByPrimaryKey(
		long cTermEntryLocalizationId) {

		return getPersistence().fetchByPrimaryKey(cTermEntryLocalizationId);
	}

	/**
	 * Returns all the c term entry localizations.
	 *
	 * @return the c term entry localizations
	 */
	public static List<CTermEntryLocalization> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the c term entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTermEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of c term entry localizations
	 * @param end the upper bound of the range of c term entry localizations (not inclusive)
	 * @return the range of c term entry localizations
	 */
	public static List<CTermEntryLocalization> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the c term entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTermEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of c term entry localizations
	 * @param end the upper bound of the range of c term entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of c term entry localizations
	 */
	public static List<CTermEntryLocalization> findAll(
		int start, int end,
		OrderByComparator<CTermEntryLocalization> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the c term entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTermEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of c term entry localizations
	 * @param end the upper bound of the range of c term entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of c term entry localizations
	 */
	public static List<CTermEntryLocalization> findAll(
		int start, int end,
		OrderByComparator<CTermEntryLocalization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the c term entry localizations from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of c term entry localizations.
	 *
	 * @return the number of c term entry localizations
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CTermEntryLocalizationPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CTermEntryLocalizationPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CTermEntryLocalizationPersistence _persistence;

}