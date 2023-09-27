/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.CountryLocalization;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the country localization service. This utility wraps <code>com.liferay.portal.service.persistence.impl.CountryLocalizationPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CountryLocalizationPersistence
 * @generated
 */
public class CountryLocalizationUtil {

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
	public static void clearCache(CountryLocalization countryLocalization) {
		getPersistence().clearCache(countryLocalization);
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
	public static Map<Serializable, CountryLocalization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CountryLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CountryLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CountryLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CountryLocalization> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CountryLocalization update(
		CountryLocalization countryLocalization) {

		return getPersistence().update(countryLocalization);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CountryLocalization update(
		CountryLocalization countryLocalization,
		ServiceContext serviceContext) {

		return getPersistence().update(countryLocalization, serviceContext);
	}

	/**
	 * Returns all the country localizations where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @return the matching country localizations
	 */
	public static List<CountryLocalization> findByCountryId(long countryId) {
		return getPersistence().findByCountryId(countryId);
	}

	/**
	 * Returns a range of all the country localizations where countryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param start the lower bound of the range of country localizations
	 * @param end the upper bound of the range of country localizations (not inclusive)
	 * @return the range of matching country localizations
	 */
	public static List<CountryLocalization> findByCountryId(
		long countryId, int start, int end) {

		return getPersistence().findByCountryId(countryId, start, end);
	}

	/**
	 * Returns an ordered range of all the country localizations where countryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param start the lower bound of the range of country localizations
	 * @param end the upper bound of the range of country localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching country localizations
	 */
	public static List<CountryLocalization> findByCountryId(
		long countryId, int start, int end,
		OrderByComparator<CountryLocalization> orderByComparator) {

		return getPersistence().findByCountryId(
			countryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the country localizations where countryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param countryId the country ID
	 * @param start the lower bound of the range of country localizations
	 * @param end the upper bound of the range of country localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching country localizations
	 */
	public static List<CountryLocalization> findByCountryId(
		long countryId, int start, int end,
		OrderByComparator<CountryLocalization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCountryId(
			countryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first country localization in the ordered set where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country localization
	 * @throws NoSuchCountryLocalizationException if a matching country localization could not be found
	 */
	public static CountryLocalization findByCountryId_First(
			long countryId,
			OrderByComparator<CountryLocalization> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchCountryLocalizationException {

		return getPersistence().findByCountryId_First(
			countryId, orderByComparator);
	}

	/**
	 * Returns the first country localization in the ordered set where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country localization, or <code>null</code> if a matching country localization could not be found
	 */
	public static CountryLocalization fetchByCountryId_First(
		long countryId,
		OrderByComparator<CountryLocalization> orderByComparator) {

		return getPersistence().fetchByCountryId_First(
			countryId, orderByComparator);
	}

	/**
	 * Returns the last country localization in the ordered set where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country localization
	 * @throws NoSuchCountryLocalizationException if a matching country localization could not be found
	 */
	public static CountryLocalization findByCountryId_Last(
			long countryId,
			OrderByComparator<CountryLocalization> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchCountryLocalizationException {

		return getPersistence().findByCountryId_Last(
			countryId, orderByComparator);
	}

	/**
	 * Returns the last country localization in the ordered set where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country localization, or <code>null</code> if a matching country localization could not be found
	 */
	public static CountryLocalization fetchByCountryId_Last(
		long countryId,
		OrderByComparator<CountryLocalization> orderByComparator) {

		return getPersistence().fetchByCountryId_Last(
			countryId, orderByComparator);
	}

	/**
	 * Returns the country localizations before and after the current country localization in the ordered set where countryId = &#63;.
	 *
	 * @param countryLocalizationId the primary key of the current country localization
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next country localization
	 * @throws NoSuchCountryLocalizationException if a country localization with the primary key could not be found
	 */
	public static CountryLocalization[] findByCountryId_PrevAndNext(
			long countryLocalizationId, long countryId,
			OrderByComparator<CountryLocalization> orderByComparator)
		throws com.liferay.portal.kernel.exception.
			NoSuchCountryLocalizationException {

		return getPersistence().findByCountryId_PrevAndNext(
			countryLocalizationId, countryId, orderByComparator);
	}

	/**
	 * Removes all the country localizations where countryId = &#63; from the database.
	 *
	 * @param countryId the country ID
	 */
	public static void removeByCountryId(long countryId) {
		getPersistence().removeByCountryId(countryId);
	}

	/**
	 * Returns the number of country localizations where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @return the number of matching country localizations
	 */
	public static int countByCountryId(long countryId) {
		return getPersistence().countByCountryId(countryId);
	}

	/**
	 * Returns the country localization where countryId = &#63; and languageId = &#63; or throws a <code>NoSuchCountryLocalizationException</code> if it could not be found.
	 *
	 * @param countryId the country ID
	 * @param languageId the language ID
	 * @return the matching country localization
	 * @throws NoSuchCountryLocalizationException if a matching country localization could not be found
	 */
	public static CountryLocalization findByCountryId_LanguageId(
			long countryId, String languageId)
		throws com.liferay.portal.kernel.exception.
			NoSuchCountryLocalizationException {

		return getPersistence().findByCountryId_LanguageId(
			countryId, languageId);
	}

	/**
	 * Returns the country localization where countryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param countryId the country ID
	 * @param languageId the language ID
	 * @return the matching country localization, or <code>null</code> if a matching country localization could not be found
	 */
	public static CountryLocalization fetchByCountryId_LanguageId(
		long countryId, String languageId) {

		return getPersistence().fetchByCountryId_LanguageId(
			countryId, languageId);
	}

	/**
	 * Returns the country localization where countryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param countryId the country ID
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching country localization, or <code>null</code> if a matching country localization could not be found
	 */
	public static CountryLocalization fetchByCountryId_LanguageId(
		long countryId, String languageId, boolean useFinderCache) {

		return getPersistence().fetchByCountryId_LanguageId(
			countryId, languageId, useFinderCache);
	}

	/**
	 * Removes the country localization where countryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param countryId the country ID
	 * @param languageId the language ID
	 * @return the country localization that was removed
	 */
	public static CountryLocalization removeByCountryId_LanguageId(
			long countryId, String languageId)
		throws com.liferay.portal.kernel.exception.
			NoSuchCountryLocalizationException {

		return getPersistence().removeByCountryId_LanguageId(
			countryId, languageId);
	}

	/**
	 * Returns the number of country localizations where countryId = &#63; and languageId = &#63;.
	 *
	 * @param countryId the country ID
	 * @param languageId the language ID
	 * @return the number of matching country localizations
	 */
	public static int countByCountryId_LanguageId(
		long countryId, String languageId) {

		return getPersistence().countByCountryId_LanguageId(
			countryId, languageId);
	}

	/**
	 * Caches the country localization in the entity cache if it is enabled.
	 *
	 * @param countryLocalization the country localization
	 */
	public static void cacheResult(CountryLocalization countryLocalization) {
		getPersistence().cacheResult(countryLocalization);
	}

	/**
	 * Caches the country localizations in the entity cache if it is enabled.
	 *
	 * @param countryLocalizations the country localizations
	 */
	public static void cacheResult(
		List<CountryLocalization> countryLocalizations) {

		getPersistence().cacheResult(countryLocalizations);
	}

	/**
	 * Creates a new country localization with the primary key. Does not add the country localization to the database.
	 *
	 * @param countryLocalizationId the primary key for the new country localization
	 * @return the new country localization
	 */
	public static CountryLocalization create(long countryLocalizationId) {
		return getPersistence().create(countryLocalizationId);
	}

	/**
	 * Removes the country localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param countryLocalizationId the primary key of the country localization
	 * @return the country localization that was removed
	 * @throws NoSuchCountryLocalizationException if a country localization with the primary key could not be found
	 */
	public static CountryLocalization remove(long countryLocalizationId)
		throws com.liferay.portal.kernel.exception.
			NoSuchCountryLocalizationException {

		return getPersistence().remove(countryLocalizationId);
	}

	public static CountryLocalization updateImpl(
		CountryLocalization countryLocalization) {

		return getPersistence().updateImpl(countryLocalization);
	}

	/**
	 * Returns the country localization with the primary key or throws a <code>NoSuchCountryLocalizationException</code> if it could not be found.
	 *
	 * @param countryLocalizationId the primary key of the country localization
	 * @return the country localization
	 * @throws NoSuchCountryLocalizationException if a country localization with the primary key could not be found
	 */
	public static CountryLocalization findByPrimaryKey(
			long countryLocalizationId)
		throws com.liferay.portal.kernel.exception.
			NoSuchCountryLocalizationException {

		return getPersistence().findByPrimaryKey(countryLocalizationId);
	}

	/**
	 * Returns the country localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param countryLocalizationId the primary key of the country localization
	 * @return the country localization, or <code>null</code> if a country localization with the primary key could not be found
	 */
	public static CountryLocalization fetchByPrimaryKey(
		long countryLocalizationId) {

		return getPersistence().fetchByPrimaryKey(countryLocalizationId);
	}

	/**
	 * Returns all the country localizations.
	 *
	 * @return the country localizations
	 */
	public static List<CountryLocalization> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the country localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of country localizations
	 * @param end the upper bound of the range of country localizations (not inclusive)
	 * @return the range of country localizations
	 */
	public static List<CountryLocalization> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the country localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of country localizations
	 * @param end the upper bound of the range of country localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of country localizations
	 */
	public static List<CountryLocalization> findAll(
		int start, int end,
		OrderByComparator<CountryLocalization> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the country localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CountryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of country localizations
	 * @param end the upper bound of the range of country localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of country localizations
	 */
	public static List<CountryLocalization> findAll(
		int start, int end,
		OrderByComparator<CountryLocalization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the country localizations from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of country localizations.
	 *
	 * @return the number of country localizations
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CountryLocalizationPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CountryLocalizationPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CountryLocalizationPersistence _persistence;

}