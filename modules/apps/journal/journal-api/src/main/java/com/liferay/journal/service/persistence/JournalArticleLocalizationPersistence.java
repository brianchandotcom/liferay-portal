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

import com.liferay.journal.exception.NoSuchArticleLocalizationException;
import com.liferay.journal.model.JournalArticleLocalization;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the journal article localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleLocalizationUtil
 * @generated
 */
@ProviderType
public interface JournalArticleLocalizationPersistence
	extends BasePersistence<JournalArticleLocalization>,
			CTPersistence<JournalArticleLocalization> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link JournalArticleLocalizationUtil} to access the journal article localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the journal article localizations where articlePK = &#63;.
	 *
	 * @param articlePK the article pk
	 * @return the matching journal article localizations
	 */
	public java.util.List<JournalArticleLocalization> findByArticlePK(
		long articlePK);

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
	public java.util.List<JournalArticleLocalization> findByArticlePK(
		long articlePK, int start, int end);

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
	public java.util.List<JournalArticleLocalization> findByArticlePK(
		long articlePK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<JournalArticleLocalization> orderByComparator);

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
	public java.util.List<JournalArticleLocalization> findByArticlePK(
		long articlePK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<JournalArticleLocalization> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first journal article localization in the ordered set where articlePK = &#63;.
	 *
	 * @param articlePK the article pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article localization
	 * @throws NoSuchArticleLocalizationException if a matching journal article localization could not be found
	 */
	public JournalArticleLocalization findByArticlePK_First(
			long articlePK,
			com.liferay.portal.kernel.util.OrderByComparator
				<JournalArticleLocalization> orderByComparator)
		throws NoSuchArticleLocalizationException;

	/**
	 * Returns the first journal article localization in the ordered set where articlePK = &#63;.
	 *
	 * @param articlePK the article pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article localization, or <code>null</code> if a matching journal article localization could not be found
	 */
	public JournalArticleLocalization fetchByArticlePK_First(
		long articlePK,
		com.liferay.portal.kernel.util.OrderByComparator
			<JournalArticleLocalization> orderByComparator);

	/**
	 * Returns the last journal article localization in the ordered set where articlePK = &#63;.
	 *
	 * @param articlePK the article pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article localization
	 * @throws NoSuchArticleLocalizationException if a matching journal article localization could not be found
	 */
	public JournalArticleLocalization findByArticlePK_Last(
			long articlePK,
			com.liferay.portal.kernel.util.OrderByComparator
				<JournalArticleLocalization> orderByComparator)
		throws NoSuchArticleLocalizationException;

	/**
	 * Returns the last journal article localization in the ordered set where articlePK = &#63;.
	 *
	 * @param articlePK the article pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article localization, or <code>null</code> if a matching journal article localization could not be found
	 */
	public JournalArticleLocalization fetchByArticlePK_Last(
		long articlePK,
		com.liferay.portal.kernel.util.OrderByComparator
			<JournalArticleLocalization> orderByComparator);

	/**
	 * Returns the journal article localizations before and after the current journal article localization in the ordered set where articlePK = &#63;.
	 *
	 * @param articleLocalizationId the primary key of the current journal article localization
	 * @param articlePK the article pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article localization
	 * @throws NoSuchArticleLocalizationException if a journal article localization with the primary key could not be found
	 */
	public JournalArticleLocalization[] findByArticlePK_PrevAndNext(
			long articleLocalizationId, long articlePK,
			com.liferay.portal.kernel.util.OrderByComparator
				<JournalArticleLocalization> orderByComparator)
		throws NoSuchArticleLocalizationException;

	/**
	 * Removes all the journal article localizations where articlePK = &#63; from the database.
	 *
	 * @param articlePK the article pk
	 */
	public void removeByArticlePK(long articlePK);

	/**
	 * Returns the number of journal article localizations where articlePK = &#63;.
	 *
	 * @param articlePK the article pk
	 * @return the number of matching journal article localizations
	 */
	public int countByArticlePK(long articlePK);

	/**
	 * Returns the journal article localization where articlePK = &#63; and languageId = &#63; or throws a <code>NoSuchArticleLocalizationException</code> if it could not be found.
	 *
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the matching journal article localization
	 * @throws NoSuchArticleLocalizationException if a matching journal article localization could not be found
	 */
	public JournalArticleLocalization findByA_L(
			long articlePK, String languageId)
		throws NoSuchArticleLocalizationException;

	/**
	 * Returns the journal article localization where articlePK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the matching journal article localization, or <code>null</code> if a matching journal article localization could not be found
	 */
	public JournalArticleLocalization fetchByA_L(
		long articlePK, String languageId);

	/**
	 * Returns the journal article localization where articlePK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal article localization, or <code>null</code> if a matching journal article localization could not be found
	 */
	public JournalArticleLocalization fetchByA_L(
		long articlePK, String languageId, boolean useFinderCache);

	/**
	 * Removes the journal article localization where articlePK = &#63; and languageId = &#63; from the database.
	 *
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the journal article localization that was removed
	 */
	public JournalArticleLocalization removeByA_L(
			long articlePK, String languageId)
		throws NoSuchArticleLocalizationException;

	/**
	 * Returns the number of journal article localizations where articlePK = &#63; and languageId = &#63;.
	 *
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the number of matching journal article localizations
	 */
	public int countByA_L(long articlePK, String languageId);

	/**
	 * Returns the journal article localization where companyId = &#63; and articlePK = &#63; and languageId = &#63; or throws a <code>NoSuchArticleLocalizationException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the matching journal article localization
	 * @throws NoSuchArticleLocalizationException if a matching journal article localization could not be found
	 */
	public JournalArticleLocalization findByC_A_L(
			long companyId, long articlePK, String languageId)
		throws NoSuchArticleLocalizationException;

	/**
	 * Returns the journal article localization where companyId = &#63; and articlePK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the matching journal article localization, or <code>null</code> if a matching journal article localization could not be found
	 */
	public JournalArticleLocalization fetchByC_A_L(
		long companyId, long articlePK, String languageId);

	/**
	 * Returns the journal article localization where companyId = &#63; and articlePK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal article localization, or <code>null</code> if a matching journal article localization could not be found
	 */
	public JournalArticleLocalization fetchByC_A_L(
		long companyId, long articlePK, String languageId,
		boolean useFinderCache);

	/**
	 * Removes the journal article localization where companyId = &#63; and articlePK = &#63; and languageId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the journal article localization that was removed
	 */
	public JournalArticleLocalization removeByC_A_L(
			long companyId, long articlePK, String languageId)
		throws NoSuchArticleLocalizationException;

	/**
	 * Returns the number of journal article localizations where companyId = &#63; and articlePK = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the number of matching journal article localizations
	 */
	public int countByC_A_L(long companyId, long articlePK, String languageId);

	/**
	 * Caches the journal article localization in the entity cache if it is enabled.
	 *
	 * @param journalArticleLocalization the journal article localization
	 */
	public void cacheResult(
		JournalArticleLocalization journalArticleLocalization);

	/**
	 * Caches the journal article localizations in the entity cache if it is enabled.
	 *
	 * @param journalArticleLocalizations the journal article localizations
	 */
	public void cacheResult(
		java.util.List<JournalArticleLocalization> journalArticleLocalizations);

	/**
	 * Creates a new journal article localization with the primary key. Does not add the journal article localization to the database.
	 *
	 * @param articleLocalizationId the primary key for the new journal article localization
	 * @return the new journal article localization
	 */
	public JournalArticleLocalization create(long articleLocalizationId);

	/**
	 * Removes the journal article localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param articleLocalizationId the primary key of the journal article localization
	 * @return the journal article localization that was removed
	 * @throws NoSuchArticleLocalizationException if a journal article localization with the primary key could not be found
	 */
	public JournalArticleLocalization remove(long articleLocalizationId)
		throws NoSuchArticleLocalizationException;

	public JournalArticleLocalization updateImpl(
		JournalArticleLocalization journalArticleLocalization);

	/**
	 * Returns the journal article localization with the primary key or throws a <code>NoSuchArticleLocalizationException</code> if it could not be found.
	 *
	 * @param articleLocalizationId the primary key of the journal article localization
	 * @return the journal article localization
	 * @throws NoSuchArticleLocalizationException if a journal article localization with the primary key could not be found
	 */
	public JournalArticleLocalization findByPrimaryKey(
			long articleLocalizationId)
		throws NoSuchArticleLocalizationException;

	/**
	 * Returns the journal article localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param articleLocalizationId the primary key of the journal article localization
	 * @return the journal article localization, or <code>null</code> if a journal article localization with the primary key could not be found
	 */
	public JournalArticleLocalization fetchByPrimaryKey(
		long articleLocalizationId);

	/**
	 * Returns all the journal article localizations.
	 *
	 * @return the journal article localizations
	 */
	public java.util.List<JournalArticleLocalization> findAll();

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
	public java.util.List<JournalArticleLocalization> findAll(
		int start, int end);

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
	public java.util.List<JournalArticleLocalization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<JournalArticleLocalization> orderByComparator);

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
	public java.util.List<JournalArticleLocalization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<JournalArticleLocalization> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the journal article localizations from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of journal article localizations.
	 *
	 * @return the number of journal article localizations
	 */
	public int countAll();

}