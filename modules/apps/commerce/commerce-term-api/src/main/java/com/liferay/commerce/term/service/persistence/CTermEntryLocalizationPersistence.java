/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.service.persistence;

import com.liferay.commerce.term.exception.NoSuchCTermEntryLocalizationException;
import com.liferay.commerce.term.model.CTermEntryLocalization;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the c term entry localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CTermEntryLocalizationUtil
 * @generated
 */
@ProviderType
public interface CTermEntryLocalizationPersistence
	extends BasePersistence<CTermEntryLocalization> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTermEntryLocalizationUtil} to access the c term entry localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the c term entry localizations where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the matching c term entry localizations
	 */
	public java.util.List<CTermEntryLocalization> findByCommerceTermEntryId(
		long commerceTermEntryId);

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
	public java.util.List<CTermEntryLocalization> findByCommerceTermEntryId(
		long commerceTermEntryId, int start, int end);

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
	public java.util.List<CTermEntryLocalization> findByCommerceTermEntryId(
		long commerceTermEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTermEntryLocalization>
			orderByComparator);

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
	public java.util.List<CTermEntryLocalization> findByCommerceTermEntryId(
		long commerceTermEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTermEntryLocalization>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first c term entry localization in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c term entry localization
	 * @throws NoSuchCTermEntryLocalizationException if a matching c term entry localization could not be found
	 */
	public CTermEntryLocalization findByCommerceTermEntryId_First(
			long commerceTermEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CTermEntryLocalization> orderByComparator)
		throws NoSuchCTermEntryLocalizationException;

	/**
	 * Returns the first c term entry localization in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c term entry localization, or <code>null</code> if a matching c term entry localization could not be found
	 */
	public CTermEntryLocalization fetchByCommerceTermEntryId_First(
		long commerceTermEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CTermEntryLocalization>
			orderByComparator);

	/**
	 * Returns the last c term entry localization in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c term entry localization
	 * @throws NoSuchCTermEntryLocalizationException if a matching c term entry localization could not be found
	 */
	public CTermEntryLocalization findByCommerceTermEntryId_Last(
			long commerceTermEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CTermEntryLocalization> orderByComparator)
		throws NoSuchCTermEntryLocalizationException;

	/**
	 * Returns the last c term entry localization in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c term entry localization, or <code>null</code> if a matching c term entry localization could not be found
	 */
	public CTermEntryLocalization fetchByCommerceTermEntryId_Last(
		long commerceTermEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CTermEntryLocalization>
			orderByComparator);

	/**
	 * Returns the c term entry localizations before and after the current c term entry localization in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param cTermEntryLocalizationId the primary key of the current c term entry localization
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next c term entry localization
	 * @throws NoSuchCTermEntryLocalizationException if a c term entry localization with the primary key could not be found
	 */
	public CTermEntryLocalization[] findByCommerceTermEntryId_PrevAndNext(
			long cTermEntryLocalizationId, long commerceTermEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CTermEntryLocalization> orderByComparator)
		throws NoSuchCTermEntryLocalizationException;

	/**
	 * Removes all the c term entry localizations where commerceTermEntryId = &#63; from the database.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 */
	public void removeByCommerceTermEntryId(long commerceTermEntryId);

	/**
	 * Returns the number of c term entry localizations where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the number of matching c term entry localizations
	 */
	public int countByCommerceTermEntryId(long commerceTermEntryId);

	/**
	 * Returns the c term entry localization where commerceTermEntryId = &#63; and languageId = &#63; or throws a <code>NoSuchCTermEntryLocalizationException</code> if it could not be found.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param languageId the language ID
	 * @return the matching c term entry localization
	 * @throws NoSuchCTermEntryLocalizationException if a matching c term entry localization could not be found
	 */
	public CTermEntryLocalization findByCommerceTermEntryId_LanguageId(
			long commerceTermEntryId, String languageId)
		throws NoSuchCTermEntryLocalizationException;

	/**
	 * Returns the c term entry localization where commerceTermEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param languageId the language ID
	 * @return the matching c term entry localization, or <code>null</code> if a matching c term entry localization could not be found
	 */
	public CTermEntryLocalization fetchByCommerceTermEntryId_LanguageId(
		long commerceTermEntryId, String languageId);

	/**
	 * Returns the c term entry localization where commerceTermEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching c term entry localization, or <code>null</code> if a matching c term entry localization could not be found
	 */
	public CTermEntryLocalization fetchByCommerceTermEntryId_LanguageId(
		long commerceTermEntryId, String languageId, boolean useFinderCache);

	/**
	 * Removes the c term entry localization where commerceTermEntryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param languageId the language ID
	 * @return the c term entry localization that was removed
	 */
	public CTermEntryLocalization removeByCommerceTermEntryId_LanguageId(
			long commerceTermEntryId, String languageId)
		throws NoSuchCTermEntryLocalizationException;

	/**
	 * Returns the number of c term entry localizations where commerceTermEntryId = &#63; and languageId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param languageId the language ID
	 * @return the number of matching c term entry localizations
	 */
	public int countByCommerceTermEntryId_LanguageId(
		long commerceTermEntryId, String languageId);

	/**
	 * Caches the c term entry localization in the entity cache if it is enabled.
	 *
	 * @param cTermEntryLocalization the c term entry localization
	 */
	public void cacheResult(CTermEntryLocalization cTermEntryLocalization);

	/**
	 * Caches the c term entry localizations in the entity cache if it is enabled.
	 *
	 * @param cTermEntryLocalizations the c term entry localizations
	 */
	public void cacheResult(
		java.util.List<CTermEntryLocalization> cTermEntryLocalizations);

	/**
	 * Creates a new c term entry localization with the primary key. Does not add the c term entry localization to the database.
	 *
	 * @param cTermEntryLocalizationId the primary key for the new c term entry localization
	 * @return the new c term entry localization
	 */
	public CTermEntryLocalization create(long cTermEntryLocalizationId);

	/**
	 * Removes the c term entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cTermEntryLocalizationId the primary key of the c term entry localization
	 * @return the c term entry localization that was removed
	 * @throws NoSuchCTermEntryLocalizationException if a c term entry localization with the primary key could not be found
	 */
	public CTermEntryLocalization remove(long cTermEntryLocalizationId)
		throws NoSuchCTermEntryLocalizationException;

	public CTermEntryLocalization updateImpl(
		CTermEntryLocalization cTermEntryLocalization);

	/**
	 * Returns the c term entry localization with the primary key or throws a <code>NoSuchCTermEntryLocalizationException</code> if it could not be found.
	 *
	 * @param cTermEntryLocalizationId the primary key of the c term entry localization
	 * @return the c term entry localization
	 * @throws NoSuchCTermEntryLocalizationException if a c term entry localization with the primary key could not be found
	 */
	public CTermEntryLocalization findByPrimaryKey(
			long cTermEntryLocalizationId)
		throws NoSuchCTermEntryLocalizationException;

	/**
	 * Returns the c term entry localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cTermEntryLocalizationId the primary key of the c term entry localization
	 * @return the c term entry localization, or <code>null</code> if a c term entry localization with the primary key could not be found
	 */
	public CTermEntryLocalization fetchByPrimaryKey(
		long cTermEntryLocalizationId);

	/**
	 * Returns all the c term entry localizations.
	 *
	 * @return the c term entry localizations
	 */
	public java.util.List<CTermEntryLocalization> findAll();

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
	public java.util.List<CTermEntryLocalization> findAll(int start, int end);

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
	public java.util.List<CTermEntryLocalization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTermEntryLocalization>
			orderByComparator);

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
	public java.util.List<CTermEntryLocalization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTermEntryLocalization>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the c term entry localizations from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of c term entry localizations.
	 *
	 * @return the number of c term entry localizations
	 */
	public int countAll();

}