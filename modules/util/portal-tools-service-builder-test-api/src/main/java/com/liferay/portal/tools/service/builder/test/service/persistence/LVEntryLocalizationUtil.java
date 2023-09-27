/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.LVEntryLocalization;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the lv entry localization service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.LVEntryLocalizationPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryLocalizationPersistence
 * @generated
 */
public class LVEntryLocalizationUtil {

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
	public static void clearCache(LVEntryLocalization lvEntryLocalization) {
		getPersistence().clearCache(lvEntryLocalization);
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
	public static Map<Serializable, LVEntryLocalization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LVEntryLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LVEntryLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LVEntryLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<LVEntryLocalization> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static LVEntryLocalization update(
		LVEntryLocalization lvEntryLocalization) {

		return getPersistence().update(lvEntryLocalization);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static LVEntryLocalization update(
		LVEntryLocalization lvEntryLocalization,
		ServiceContext serviceContext) {

		return getPersistence().update(lvEntryLocalization, serviceContext);
	}

	/**
	 * Returns all the lv entry localizations where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @return the matching lv entry localizations
	 */
	public static List<LVEntryLocalization> findByLvEntryId(long lvEntryId) {
		return getPersistence().findByLvEntryId(lvEntryId);
	}

	/**
	 * Returns a range of all the lv entry localizations where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry localizations
	 * @param end the upper bound of the range of lv entry localizations (not inclusive)
	 * @return the range of matching lv entry localizations
	 */
	public static List<LVEntryLocalization> findByLvEntryId(
		long lvEntryId, int start, int end) {

		return getPersistence().findByLvEntryId(lvEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the lv entry localizations where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry localizations
	 * @param end the upper bound of the range of lv entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry localizations
	 */
	public static List<LVEntryLocalization> findByLvEntryId(
		long lvEntryId, int start, int end,
		OrderByComparator<LVEntryLocalization> orderByComparator) {

		return getPersistence().findByLvEntryId(
			lvEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the lv entry localizations where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry localizations
	 * @param end the upper bound of the range of lv entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entry localizations
	 */
	public static List<LVEntryLocalization> findByLvEntryId(
		long lvEntryId, int start, int end,
		OrderByComparator<LVEntryLocalization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLvEntryId(
			lvEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first lv entry localization in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a matching lv entry localization could not be found
	 */
	public static LVEntryLocalization findByLvEntryId_First(
			long lvEntryId,
			OrderByComparator<LVEntryLocalization> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationException {

		return getPersistence().findByLvEntryId_First(
			lvEntryId, orderByComparator);
	}

	/**
	 * Returns the first lv entry localization in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	public static LVEntryLocalization fetchByLvEntryId_First(
		long lvEntryId,
		OrderByComparator<LVEntryLocalization> orderByComparator) {

		return getPersistence().fetchByLvEntryId_First(
			lvEntryId, orderByComparator);
	}

	/**
	 * Returns the last lv entry localization in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a matching lv entry localization could not be found
	 */
	public static LVEntryLocalization findByLvEntryId_Last(
			long lvEntryId,
			OrderByComparator<LVEntryLocalization> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationException {

		return getPersistence().findByLvEntryId_Last(
			lvEntryId, orderByComparator);
	}

	/**
	 * Returns the last lv entry localization in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	public static LVEntryLocalization fetchByLvEntryId_Last(
		long lvEntryId,
		OrderByComparator<LVEntryLocalization> orderByComparator) {

		return getPersistence().fetchByLvEntryId_Last(
			lvEntryId, orderByComparator);
	}

	/**
	 * Returns the lv entry localizations before and after the current lv entry localization in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryLocalizationId the primary key of the current lv entry localization
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a lv entry localization with the primary key could not be found
	 */
	public static LVEntryLocalization[] findByLvEntryId_PrevAndNext(
			long lvEntryLocalizationId, long lvEntryId,
			OrderByComparator<LVEntryLocalization> orderByComparator)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationException {

		return getPersistence().findByLvEntryId_PrevAndNext(
			lvEntryLocalizationId, lvEntryId, orderByComparator);
	}

	/**
	 * Removes all the lv entry localizations where lvEntryId = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 */
	public static void removeByLvEntryId(long lvEntryId) {
		getPersistence().removeByLvEntryId(lvEntryId);
	}

	/**
	 * Returns the number of lv entry localizations where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @return the number of matching lv entry localizations
	 */
	public static int countByLvEntryId(long lvEntryId) {
		return getPersistence().countByLvEntryId(lvEntryId);
	}

	/**
	 * Returns the lv entry localization where lvEntryId = &#63; and languageId = &#63; or throws a <code>NoSuchLVEntryLocalizationException</code> if it could not be found.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @return the matching lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a matching lv entry localization could not be found
	 */
	public static LVEntryLocalization findByLvEntryId_LanguageId(
			long lvEntryId, String languageId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationException {

		return getPersistence().findByLvEntryId_LanguageId(
			lvEntryId, languageId);
	}

	/**
	 * Returns the lv entry localization where lvEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @return the matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	public static LVEntryLocalization fetchByLvEntryId_LanguageId(
		long lvEntryId, String languageId) {

		return getPersistence().fetchByLvEntryId_LanguageId(
			lvEntryId, languageId);
	}

	/**
	 * Returns the lv entry localization where lvEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	public static LVEntryLocalization fetchByLvEntryId_LanguageId(
		long lvEntryId, String languageId, boolean useFinderCache) {

		return getPersistence().fetchByLvEntryId_LanguageId(
			lvEntryId, languageId, useFinderCache);
	}

	/**
	 * Removes the lv entry localization where lvEntryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @return the lv entry localization that was removed
	 */
	public static LVEntryLocalization removeByLvEntryId_LanguageId(
			long lvEntryId, String languageId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationException {

		return getPersistence().removeByLvEntryId_LanguageId(
			lvEntryId, languageId);
	}

	/**
	 * Returns the number of lv entry localizations where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @return the number of matching lv entry localizations
	 */
	public static int countByLvEntryId_LanguageId(
		long lvEntryId, String languageId) {

		return getPersistence().countByLvEntryId_LanguageId(
			lvEntryId, languageId);
	}

	/**
	 * Returns the lv entry localization where headId = &#63; or throws a <code>NoSuchLVEntryLocalizationException</code> if it could not be found.
	 *
	 * @param headId the head ID
	 * @return the matching lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a matching lv entry localization could not be found
	 */
	public static LVEntryLocalization findByHeadId(long headId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationException {

		return getPersistence().findByHeadId(headId);
	}

	/**
	 * Returns the lv entry localization where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param headId the head ID
	 * @return the matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	public static LVEntryLocalization fetchByHeadId(long headId) {
		return getPersistence().fetchByHeadId(headId);
	}

	/**
	 * Returns the lv entry localization where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param headId the head ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	public static LVEntryLocalization fetchByHeadId(
		long headId, boolean useFinderCache) {

		return getPersistence().fetchByHeadId(headId, useFinderCache);
	}

	/**
	 * Removes the lv entry localization where headId = &#63; from the database.
	 *
	 * @param headId the head ID
	 * @return the lv entry localization that was removed
	 */
	public static LVEntryLocalization removeByHeadId(long headId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationException {

		return getPersistence().removeByHeadId(headId);
	}

	/**
	 * Returns the number of lv entry localizations where headId = &#63;.
	 *
	 * @param headId the head ID
	 * @return the number of matching lv entry localizations
	 */
	public static int countByHeadId(long headId) {
		return getPersistence().countByHeadId(headId);
	}

	/**
	 * Caches the lv entry localization in the entity cache if it is enabled.
	 *
	 * @param lvEntryLocalization the lv entry localization
	 */
	public static void cacheResult(LVEntryLocalization lvEntryLocalization) {
		getPersistence().cacheResult(lvEntryLocalization);
	}

	/**
	 * Caches the lv entry localizations in the entity cache if it is enabled.
	 *
	 * @param lvEntryLocalizations the lv entry localizations
	 */
	public static void cacheResult(
		List<LVEntryLocalization> lvEntryLocalizations) {

		getPersistence().cacheResult(lvEntryLocalizations);
	}

	/**
	 * Creates a new lv entry localization with the primary key. Does not add the lv entry localization to the database.
	 *
	 * @param lvEntryLocalizationId the primary key for the new lv entry localization
	 * @return the new lv entry localization
	 */
	public static LVEntryLocalization create(long lvEntryLocalizationId) {
		return getPersistence().create(lvEntryLocalizationId);
	}

	/**
	 * Removes the lv entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lvEntryLocalizationId the primary key of the lv entry localization
	 * @return the lv entry localization that was removed
	 * @throws NoSuchLVEntryLocalizationException if a lv entry localization with the primary key could not be found
	 */
	public static LVEntryLocalization remove(long lvEntryLocalizationId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationException {

		return getPersistence().remove(lvEntryLocalizationId);
	}

	public static LVEntryLocalization updateImpl(
		LVEntryLocalization lvEntryLocalization) {

		return getPersistence().updateImpl(lvEntryLocalization);
	}

	/**
	 * Returns the lv entry localization with the primary key or throws a <code>NoSuchLVEntryLocalizationException</code> if it could not be found.
	 *
	 * @param lvEntryLocalizationId the primary key of the lv entry localization
	 * @return the lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a lv entry localization with the primary key could not be found
	 */
	public static LVEntryLocalization findByPrimaryKey(
			long lvEntryLocalizationId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchLVEntryLocalizationException {

		return getPersistence().findByPrimaryKey(lvEntryLocalizationId);
	}

	/**
	 * Returns the lv entry localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param lvEntryLocalizationId the primary key of the lv entry localization
	 * @return the lv entry localization, or <code>null</code> if a lv entry localization with the primary key could not be found
	 */
	public static LVEntryLocalization fetchByPrimaryKey(
		long lvEntryLocalizationId) {

		return getPersistence().fetchByPrimaryKey(lvEntryLocalizationId);
	}

	/**
	 * Returns all the lv entry localizations.
	 *
	 * @return the lv entry localizations
	 */
	public static List<LVEntryLocalization> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the lv entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localizations
	 * @param end the upper bound of the range of lv entry localizations (not inclusive)
	 * @return the range of lv entry localizations
	 */
	public static List<LVEntryLocalization> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the lv entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localizations
	 * @param end the upper bound of the range of lv entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lv entry localizations
	 */
	public static List<LVEntryLocalization> findAll(
		int start, int end,
		OrderByComparator<LVEntryLocalization> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the lv entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry localizations
	 * @param end the upper bound of the range of lv entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of lv entry localizations
	 */
	public static List<LVEntryLocalization> findAll(
		int start, int end,
		OrderByComparator<LVEntryLocalization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the lv entry localizations from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of lv entry localizations.
	 *
	 * @return the number of lv entry localizations
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static LVEntryLocalizationPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		LVEntryLocalizationPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile LVEntryLocalizationPersistence _persistence;

}