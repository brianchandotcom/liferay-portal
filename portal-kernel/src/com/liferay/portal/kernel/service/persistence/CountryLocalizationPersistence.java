/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.exception.NoSuchCountryLocalizationException;
import com.liferay.portal.kernel.model.CountryLocalization;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the country localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CountryLocalizationUtil
 * @generated
 */
@ProviderType
public interface CountryLocalizationPersistence
	extends BasePersistence<CountryLocalization> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CountryLocalizationUtil} to access the country localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the country localizations where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @return the matching country localizations
	 */
	public java.util.List<CountryLocalization> findByCountryId(long countryId);

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
	public java.util.List<CountryLocalization> findByCountryId(
		long countryId, int start, int end);

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
	public java.util.List<CountryLocalization> findByCountryId(
		long countryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CountryLocalization>
			orderByComparator);

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
	public java.util.List<CountryLocalization> findByCountryId(
		long countryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CountryLocalization>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first country localization in the ordered set where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country localization
	 * @throws NoSuchCountryLocalizationException if a matching country localization could not be found
	 */
	public CountryLocalization findByCountryId_First(
			long countryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CountryLocalization> orderByComparator)
		throws NoSuchCountryLocalizationException;

	/**
	 * Returns the first country localization in the ordered set where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching country localization, or <code>null</code> if a matching country localization could not be found
	 */
	public CountryLocalization fetchByCountryId_First(
		long countryId,
		com.liferay.portal.kernel.util.OrderByComparator<CountryLocalization>
			orderByComparator);

	/**
	 * Returns the last country localization in the ordered set where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country localization
	 * @throws NoSuchCountryLocalizationException if a matching country localization could not be found
	 */
	public CountryLocalization findByCountryId_Last(
			long countryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CountryLocalization> orderByComparator)
		throws NoSuchCountryLocalizationException;

	/**
	 * Returns the last country localization in the ordered set where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching country localization, or <code>null</code> if a matching country localization could not be found
	 */
	public CountryLocalization fetchByCountryId_Last(
		long countryId,
		com.liferay.portal.kernel.util.OrderByComparator<CountryLocalization>
			orderByComparator);

	/**
	 * Returns the country localizations before and after the current country localization in the ordered set where countryId = &#63;.
	 *
	 * @param countryLocalizationId the primary key of the current country localization
	 * @param countryId the country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next country localization
	 * @throws NoSuchCountryLocalizationException if a country localization with the primary key could not be found
	 */
	public CountryLocalization[] findByCountryId_PrevAndNext(
			long countryLocalizationId, long countryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CountryLocalization> orderByComparator)
		throws NoSuchCountryLocalizationException;

	/**
	 * Removes all the country localizations where countryId = &#63; from the database.
	 *
	 * @param countryId the country ID
	 */
	public void removeByCountryId(long countryId);

	/**
	 * Returns the number of country localizations where countryId = &#63;.
	 *
	 * @param countryId the country ID
	 * @return the number of matching country localizations
	 */
	public int countByCountryId(long countryId);

	/**
	 * Returns the country localization where countryId = &#63; and languageId = &#63; or throws a <code>NoSuchCountryLocalizationException</code> if it could not be found.
	 *
	 * @param countryId the country ID
	 * @param languageId the language ID
	 * @return the matching country localization
	 * @throws NoSuchCountryLocalizationException if a matching country localization could not be found
	 */
	public CountryLocalization findByCountryId_LanguageId(
			long countryId, String languageId)
		throws NoSuchCountryLocalizationException;

	/**
	 * Returns the country localization where countryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param countryId the country ID
	 * @param languageId the language ID
	 * @return the matching country localization, or <code>null</code> if a matching country localization could not be found
	 */
	public CountryLocalization fetchByCountryId_LanguageId(
		long countryId, String languageId);

	/**
	 * Returns the country localization where countryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param countryId the country ID
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching country localization, or <code>null</code> if a matching country localization could not be found
	 */
	public CountryLocalization fetchByCountryId_LanguageId(
		long countryId, String languageId, boolean useFinderCache);

	/**
	 * Removes the country localization where countryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param countryId the country ID
	 * @param languageId the language ID
	 * @return the country localization that was removed
	 */
	public CountryLocalization removeByCountryId_LanguageId(
			long countryId, String languageId)
		throws NoSuchCountryLocalizationException;

	/**
	 * Returns the number of country localizations where countryId = &#63; and languageId = &#63;.
	 *
	 * @param countryId the country ID
	 * @param languageId the language ID
	 * @return the number of matching country localizations
	 */
	public int countByCountryId_LanguageId(long countryId, String languageId);

	/**
	 * Caches the country localization in the entity cache if it is enabled.
	 *
	 * @param countryLocalization the country localization
	 */
	public void cacheResult(CountryLocalization countryLocalization);

	/**
	 * Caches the country localizations in the entity cache if it is enabled.
	 *
	 * @param countryLocalizations the country localizations
	 */
	public void cacheResult(
		java.util.List<CountryLocalization> countryLocalizations);

	/**
	 * Creates a new country localization with the primary key. Does not add the country localization to the database.
	 *
	 * @param countryLocalizationId the primary key for the new country localization
	 * @return the new country localization
	 */
	public CountryLocalization create(long countryLocalizationId);

	/**
	 * Removes the country localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param countryLocalizationId the primary key of the country localization
	 * @return the country localization that was removed
	 * @throws NoSuchCountryLocalizationException if a country localization with the primary key could not be found
	 */
	public CountryLocalization remove(long countryLocalizationId)
		throws NoSuchCountryLocalizationException;

	public CountryLocalization updateImpl(
		CountryLocalization countryLocalization);

	/**
	 * Returns the country localization with the primary key or throws a <code>NoSuchCountryLocalizationException</code> if it could not be found.
	 *
	 * @param countryLocalizationId the primary key of the country localization
	 * @return the country localization
	 * @throws NoSuchCountryLocalizationException if a country localization with the primary key could not be found
	 */
	public CountryLocalization findByPrimaryKey(long countryLocalizationId)
		throws NoSuchCountryLocalizationException;

	/**
	 * Returns the country localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param countryLocalizationId the primary key of the country localization
	 * @return the country localization, or <code>null</code> if a country localization with the primary key could not be found
	 */
	public CountryLocalization fetchByPrimaryKey(long countryLocalizationId);

	/**
	 * Returns all the country localizations.
	 *
	 * @return the country localizations
	 */
	public java.util.List<CountryLocalization> findAll();

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
	public java.util.List<CountryLocalization> findAll(int start, int end);

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
	public java.util.List<CountryLocalization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CountryLocalization>
			orderByComparator);

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
	public java.util.List<CountryLocalization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CountryLocalization>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the country localizations from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of country localizations.
	 *
	 * @return the number of country localizations
	 */
	public int countAll();

}