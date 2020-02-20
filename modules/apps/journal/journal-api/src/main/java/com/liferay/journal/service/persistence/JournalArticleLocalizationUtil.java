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

package com.liferay.journal.service.persistence;

import com.liferay.journal.model.JournalArticleLocalization;
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
 * The persistence utility for the journal article localization service. This utility wraps <code>com.liferay.journal.service.persistence.impl.JournalArticleLocalizationPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleLocalizationPersistence
 * @generated
 */
public class JournalArticleLocalizationUtil {

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
		JournalArticleLocalization journalArticleLocalization) {

		getPersistence().clearCache(journalArticleLocalization);
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
	public static Map<Serializable, JournalArticleLocalization>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<JournalArticleLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<JournalArticleLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<JournalArticleLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<JournalArticleLocalization> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static JournalArticleLocalization update(
		JournalArticleLocalization journalArticleLocalization) {

		return getPersistence().update(journalArticleLocalization);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static JournalArticleLocalization update(
		JournalArticleLocalization journalArticleLocalization,
		ServiceContext serviceContext) {

		return getPersistence().update(
			journalArticleLocalization, serviceContext);
	}

	/**
	 * Returns all the journal article localizations where articlePK = &#63;.
	 *
	 * @param articlePK the article pk
	 * @return the matching journal article localizations
	 */
	public static List<JournalArticleLocalization> findByArticlePK(
		long articlePK) {

		return getPersistence().findByArticlePK(articlePK);
	}

	/**
	 * Returns a range of all the journal article localizations where articlePK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param articlePK the article pk
	 * @param start the lower bound of the range of journal article localizations
	 * @param end the upper bound of the range of journal article localizations (not inclusive)
	 * @return the range of matching journal article localizations
	 */
	public static List<JournalArticleLocalization> findByArticlePK(
		long articlePK, int start, int end) {

		return getPersistence().findByArticlePK(articlePK, start, end);
	}

	/**
	 * Returns an ordered range of all the journal article localizations where articlePK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param articlePK the article pk
	 * @param start the lower bound of the range of journal article localizations
	 * @param end the upper bound of the range of journal article localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal article localizations
	 */
	public static List<JournalArticleLocalization> findByArticlePK(
		long articlePK, int start, int end,
		OrderByComparator<JournalArticleLocalization> orderByComparator) {

		return getPersistence().findByArticlePK(
			articlePK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal article localizations where articlePK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param articlePK the article pk
	 * @param start the lower bound of the range of journal article localizations
	 * @param end the upper bound of the range of journal article localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal article localizations
	 */
	public static List<JournalArticleLocalization> findByArticlePK(
		long articlePK, int start, int end,
		OrderByComparator<JournalArticleLocalization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByArticlePK(
			articlePK, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article localization in the ordered set where articlePK = &#63;.
	 *
	 * @param articlePK the article pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article localization
	 * @throws NoSuchArticleLocalizationException if a matching journal article localization could not be found
	 */
	public static JournalArticleLocalization findByArticlePK_First(
			long articlePK,
			OrderByComparator<JournalArticleLocalization> orderByComparator)
		throws com.liferay.journal.exception.
			NoSuchArticleLocalizationException {

		return getPersistence().findByArticlePK_First(
			articlePK, orderByComparator);
	}

	/**
	 * Returns the first journal article localization in the ordered set where articlePK = &#63;.
	 *
	 * @param articlePK the article pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article localization, or <code>null</code> if a matching journal article localization could not be found
	 */
	public static JournalArticleLocalization fetchByArticlePK_First(
		long articlePK,
		OrderByComparator<JournalArticleLocalization> orderByComparator) {

		return getPersistence().fetchByArticlePK_First(
			articlePK, orderByComparator);
	}

	/**
	 * Returns the last journal article localization in the ordered set where articlePK = &#63;.
	 *
	 * @param articlePK the article pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article localization
	 * @throws NoSuchArticleLocalizationException if a matching journal article localization could not be found
	 */
	public static JournalArticleLocalization findByArticlePK_Last(
			long articlePK,
			OrderByComparator<JournalArticleLocalization> orderByComparator)
		throws com.liferay.journal.exception.
			NoSuchArticleLocalizationException {

		return getPersistence().findByArticlePK_Last(
			articlePK, orderByComparator);
	}

	/**
	 * Returns the last journal article localization in the ordered set where articlePK = &#63;.
	 *
	 * @param articlePK the article pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article localization, or <code>null</code> if a matching journal article localization could not be found
	 */
	public static JournalArticleLocalization fetchByArticlePK_Last(
		long articlePK,
		OrderByComparator<JournalArticleLocalization> orderByComparator) {

		return getPersistence().fetchByArticlePK_Last(
			articlePK, orderByComparator);
	}

	/**
	 * Returns the journal article localizations before and after the current journal article localization in the ordered set where articlePK = &#63;.
	 *
	 * @param articleLocalizationId the primary key of the current journal article localization
	 * @param articlePK the article pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article localization
	 * @throws NoSuchArticleLocalizationException if a journal article localization with the primary key could not be found
	 */
	public static JournalArticleLocalization[] findByArticlePK_PrevAndNext(
			long articleLocalizationId, long articlePK,
			OrderByComparator<JournalArticleLocalization> orderByComparator)
		throws com.liferay.journal.exception.
			NoSuchArticleLocalizationException {

		return getPersistence().findByArticlePK_PrevAndNext(
			articleLocalizationId, articlePK, orderByComparator);
	}

	/**
	 * Removes all the journal article localizations where articlePK = &#63; from the database.
	 *
	 * @param articlePK the article pk
	 */
	public static void removeByArticlePK(long articlePK) {
		getPersistence().removeByArticlePK(articlePK);
	}

	/**
	 * Returns the number of journal article localizations where articlePK = &#63;.
	 *
	 * @param articlePK the article pk
	 * @return the number of matching journal article localizations
	 */
	public static int countByArticlePK(long articlePK) {
		return getPersistence().countByArticlePK(articlePK);
	}

	/**
	 * Returns the journal article localization where articlePK = &#63; and languageId = &#63; or throws a <code>NoSuchArticleLocalizationException</code> if it could not be found.
	 *
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the matching journal article localization
	 * @throws NoSuchArticleLocalizationException if a matching journal article localization could not be found
	 */
	public static JournalArticleLocalization findByA_L(
			long articlePK, String languageId)
		throws com.liferay.journal.exception.
			NoSuchArticleLocalizationException {

		return getPersistence().findByA_L(articlePK, languageId);
	}

	/**
	 * Returns the journal article localization where articlePK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the matching journal article localization, or <code>null</code> if a matching journal article localization could not be found
	 */
	public static JournalArticleLocalization fetchByA_L(
		long articlePK, String languageId) {

		return getPersistence().fetchByA_L(articlePK, languageId);
	}

	/**
	 * Returns the journal article localization where articlePK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal article localization, or <code>null</code> if a matching journal article localization could not be found
	 */
	public static JournalArticleLocalization fetchByA_L(
		long articlePK, String languageId, boolean useFinderCache) {

		return getPersistence().fetchByA_L(
			articlePK, languageId, useFinderCache);
	}

	/**
	 * Removes the journal article localization where articlePK = &#63; and languageId = &#63; from the database.
	 *
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the journal article localization that was removed
	 */
	public static JournalArticleLocalization removeByA_L(
			long articlePK, String languageId)
		throws com.liferay.journal.exception.
			NoSuchArticleLocalizationException {

		return getPersistence().removeByA_L(articlePK, languageId);
	}

	/**
	 * Returns the number of journal article localizations where articlePK = &#63; and languageId = &#63;.
	 *
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the number of matching journal article localizations
	 */
	public static int countByA_L(long articlePK, String languageId) {
		return getPersistence().countByA_L(articlePK, languageId);
	}

	/**
	 * Returns the journal article localization where companyId = &#63; and articlePK = &#63; and languageId = &#63; or throws a <code>NoSuchArticleLocalizationException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the matching journal article localization
	 * @throws NoSuchArticleLocalizationException if a matching journal article localization could not be found
	 */
	public static JournalArticleLocalization findByC_A_L(
			long companyId, long articlePK, String languageId)
		throws com.liferay.journal.exception.
			NoSuchArticleLocalizationException {

		return getPersistence().findByC_A_L(companyId, articlePK, languageId);
	}

	/**
	 * Returns the journal article localization where companyId = &#63; and articlePK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the matching journal article localization, or <code>null</code> if a matching journal article localization could not be found
	 */
	public static JournalArticleLocalization fetchByC_A_L(
		long companyId, long articlePK, String languageId) {

		return getPersistence().fetchByC_A_L(companyId, articlePK, languageId);
	}

	/**
	 * Returns the journal article localization where companyId = &#63; and articlePK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal article localization, or <code>null</code> if a matching journal article localization could not be found
	 */
	public static JournalArticleLocalization fetchByC_A_L(
		long companyId, long articlePK, String languageId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_A_L(
			companyId, articlePK, languageId, useFinderCache);
	}

	/**
	 * Removes the journal article localization where companyId = &#63; and articlePK = &#63; and languageId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the journal article localization that was removed
	 */
	public static JournalArticleLocalization removeByC_A_L(
			long companyId, long articlePK, String languageId)
		throws com.liferay.journal.exception.
			NoSuchArticleLocalizationException {

		return getPersistence().removeByC_A_L(companyId, articlePK, languageId);
	}

	/**
	 * Returns the number of journal article localizations where companyId = &#63; and articlePK = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the number of matching journal article localizations
	 */
	public static int countByC_A_L(
		long companyId, long articlePK, String languageId) {

		return getPersistence().countByC_A_L(companyId, articlePK, languageId);
	}

	/**
	 * Caches the journal article localization in the entity cache if it is enabled.
	 *
	 * @param journalArticleLocalization the journal article localization
	 */
	public static void cacheResult(
		JournalArticleLocalization journalArticleLocalization) {

		getPersistence().cacheResult(journalArticleLocalization);
	}

	/**
	 * Caches the journal article localizations in the entity cache if it is enabled.
	 *
	 * @param journalArticleLocalizations the journal article localizations
	 */
	public static void cacheResult(
		List<JournalArticleLocalization> journalArticleLocalizations) {

		getPersistence().cacheResult(journalArticleLocalizations);
	}

	/**
	 * Creates a new journal article localization with the primary key. Does not add the journal article localization to the database.
	 *
	 * @param articleLocalizationId the primary key for the new journal article localization
	 * @return the new journal article localization
	 */
	public static JournalArticleLocalization create(
		long articleLocalizationId) {

		return getPersistence().create(articleLocalizationId);
	}

	/**
	 * Removes the journal article localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param articleLocalizationId the primary key of the journal article localization
	 * @return the journal article localization that was removed
	 * @throws NoSuchArticleLocalizationException if a journal article localization with the primary key could not be found
	 */
	public static JournalArticleLocalization remove(long articleLocalizationId)
		throws com.liferay.journal.exception.
			NoSuchArticleLocalizationException {

		return getPersistence().remove(articleLocalizationId);
	}

	public static JournalArticleLocalization updateImpl(
		JournalArticleLocalization journalArticleLocalization) {

		return getPersistence().updateImpl(journalArticleLocalization);
	}

	/**
	 * Returns the journal article localization with the primary key or throws a <code>NoSuchArticleLocalizationException</code> if it could not be found.
	 *
	 * @param articleLocalizationId the primary key of the journal article localization
	 * @return the journal article localization
	 * @throws NoSuchArticleLocalizationException if a journal article localization with the primary key could not be found
	 */
	public static JournalArticleLocalization findByPrimaryKey(
			long articleLocalizationId)
		throws com.liferay.journal.exception.
			NoSuchArticleLocalizationException {

		return getPersistence().findByPrimaryKey(articleLocalizationId);
	}

	/**
	 * Returns the journal article localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param articleLocalizationId the primary key of the journal article localization
	 * @return the journal article localization, or <code>null</code> if a journal article localization with the primary key could not be found
	 */
	public static JournalArticleLocalization fetchByPrimaryKey(
		long articleLocalizationId) {

		return getPersistence().fetchByPrimaryKey(articleLocalizationId);
	}

	/**
	 * Returns all the journal article localizations.
	 *
	 * @return the journal article localizations
	 */
	public static List<JournalArticleLocalization> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the journal article localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal article localizations
	 * @param end the upper bound of the range of journal article localizations (not inclusive)
	 * @return the range of journal article localizations
	 */
	public static List<JournalArticleLocalization> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the journal article localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal article localizations
	 * @param end the upper bound of the range of journal article localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of journal article localizations
	 */
	public static List<JournalArticleLocalization> findAll(
		int start, int end,
		OrderByComparator<JournalArticleLocalization> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal article localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal article localizations
	 * @param end the upper bound of the range of journal article localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of journal article localizations
	 */
	public static List<JournalArticleLocalization> findAll(
		int start, int end,
		OrderByComparator<JournalArticleLocalization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the journal article localizations from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of journal article localizations.
	 *
	 * @return the number of journal article localizations
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static JournalArticleLocalizationPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<JournalArticleLocalizationPersistence,
		 JournalArticleLocalizationPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			JournalArticleLocalizationPersistence.class);

		ServiceTracker
			<JournalArticleLocalizationPersistence,
			 JournalArticleLocalizationPersistence> serviceTracker =
				new ServiceTracker
					<JournalArticleLocalizationPersistence,
					 JournalArticleLocalizationPersistence>(
						 bundle.getBundleContext(),
						 JournalArticleLocalizationPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}