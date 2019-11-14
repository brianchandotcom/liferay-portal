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

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLocalizedEntryLocalizationException;
import com.liferay.portal.tools.service.builder.test.model.LocalizedEntryLocalization;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the localized entry localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LocalizedEntryLocalizationUtil
 * @generated
 */
@ProviderType
public interface LocalizedEntryLocalizationPersistence
	extends BasePersistence<LocalizedEntryLocalization> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LocalizedEntryLocalizationUtil} to access the localized entry localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the localized entry localizations where localizedEntryId = &#63;.
	 *
	 * @param localizedEntryId the localized entry ID
	 * @return the matching localized entry localizations
	 */
	public java.util.List<LocalizedEntryLocalization> findByLocalizedEntryId(
		long localizedEntryId);

	/**
	 * Returns a range of all the localized entry localizations where localizedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LocalizedEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param localizedEntryId the localized entry ID
	 * @param start the lower bound of the range of localized entry localizations
	 * @param end the upper bound of the range of localized entry localizations (not inclusive)
	 * @return the range of matching localized entry localizations
	 */
	public java.util.List<LocalizedEntryLocalization> findByLocalizedEntryId(
		long localizedEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the localized entry localizations where localizedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LocalizedEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param localizedEntryId the localized entry ID
	 * @param start the lower bound of the range of localized entry localizations
	 * @param end the upper bound of the range of localized entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching localized entry localizations
	 */
	public java.util.List<LocalizedEntryLocalization> findByLocalizedEntryId(
		long localizedEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LocalizedEntryLocalization> orderByComparator);

	/**
	 * Returns an ordered range of all the localized entry localizations where localizedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LocalizedEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param localizedEntryId the localized entry ID
	 * @param start the lower bound of the range of localized entry localizations
	 * @param end the upper bound of the range of localized entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching localized entry localizations
	 */
	public java.util.List<LocalizedEntryLocalization> findByLocalizedEntryId(
		long localizedEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LocalizedEntryLocalization> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first localized entry localization in the ordered set where localizedEntryId = &#63;.
	 *
	 * @param localizedEntryId the localized entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching localized entry localization
	 * @throws NoSuchLocalizedEntryLocalizationException if a matching localized entry localization could not be found
	 */
	public LocalizedEntryLocalization findByLocalizedEntryId_First(
			long localizedEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LocalizedEntryLocalization> orderByComparator)
		throws NoSuchLocalizedEntryLocalizationException;

	/**
	 * Returns the first localized entry localization in the ordered set where localizedEntryId = &#63;.
	 *
	 * @param localizedEntryId the localized entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching localized entry localization, or <code>null</code> if a matching localized entry localization could not be found
	 */
	public LocalizedEntryLocalization fetchByLocalizedEntryId_First(
		long localizedEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LocalizedEntryLocalization> orderByComparator);

	/**
	 * Returns the last localized entry localization in the ordered set where localizedEntryId = &#63;.
	 *
	 * @param localizedEntryId the localized entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching localized entry localization
	 * @throws NoSuchLocalizedEntryLocalizationException if a matching localized entry localization could not be found
	 */
	public LocalizedEntryLocalization findByLocalizedEntryId_Last(
			long localizedEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LocalizedEntryLocalization> orderByComparator)
		throws NoSuchLocalizedEntryLocalizationException;

	/**
	 * Returns the last localized entry localization in the ordered set where localizedEntryId = &#63;.
	 *
	 * @param localizedEntryId the localized entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching localized entry localization, or <code>null</code> if a matching localized entry localization could not be found
	 */
	public LocalizedEntryLocalization fetchByLocalizedEntryId_Last(
		long localizedEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LocalizedEntryLocalization> orderByComparator);

	/**
	 * Returns the localized entry localizations before and after the current localized entry localization in the ordered set where localizedEntryId = &#63;.
	 *
	 * @param localizedEntryLocalizationId the primary key of the current localized entry localization
	 * @param localizedEntryId the localized entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next localized entry localization
	 * @throws NoSuchLocalizedEntryLocalizationException if a localized entry localization with the primary key could not be found
	 */
	public LocalizedEntryLocalization[] findByLocalizedEntryId_PrevAndNext(
			long localizedEntryLocalizationId, long localizedEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LocalizedEntryLocalization> orderByComparator)
		throws NoSuchLocalizedEntryLocalizationException;

	/**
	 * Removes all the localized entry localizations where localizedEntryId = &#63; from the database.
	 *
	 * @param localizedEntryId the localized entry ID
	 */
	public void removeByLocalizedEntryId(long localizedEntryId);

	/**
	 * Returns the number of localized entry localizations where localizedEntryId = &#63;.
	 *
	 * @param localizedEntryId the localized entry ID
	 * @return the number of matching localized entry localizations
	 */
	public int countByLocalizedEntryId(long localizedEntryId);

	/**
	 * Returns the localized entry localization where localizedEntryId = &#63; and languageId = &#63; or throws a <code>NoSuchLocalizedEntryLocalizationException</code> if it could not be found.
	 *
	 * @param localizedEntryId the localized entry ID
	 * @param languageId the language ID
	 * @return the matching localized entry localization
	 * @throws NoSuchLocalizedEntryLocalizationException if a matching localized entry localization could not be found
	 */
	public LocalizedEntryLocalization findByLocalizedEntryId_LanguageId(
			long localizedEntryId, String languageId)
		throws NoSuchLocalizedEntryLocalizationException;

	/**
	 * Returns the localized entry localization where localizedEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param localizedEntryId the localized entry ID
	 * @param languageId the language ID
	 * @return the matching localized entry localization, or <code>null</code> if a matching localized entry localization could not be found
	 */
	public LocalizedEntryLocalization fetchByLocalizedEntryId_LanguageId(
		long localizedEntryId, String languageId);

	/**
	 * Returns the localized entry localization where localizedEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param localizedEntryId the localized entry ID
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching localized entry localization, or <code>null</code> if a matching localized entry localization could not be found
	 */
	public LocalizedEntryLocalization fetchByLocalizedEntryId_LanguageId(
		long localizedEntryId, String languageId, boolean useFinderCache);

	/**
	 * Removes the localized entry localization where localizedEntryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param localizedEntryId the localized entry ID
	 * @param languageId the language ID
	 * @return the localized entry localization that was removed
	 */
	public LocalizedEntryLocalization removeByLocalizedEntryId_LanguageId(
			long localizedEntryId, String languageId)
		throws NoSuchLocalizedEntryLocalizationException;

	/**
	 * Returns the number of localized entry localizations where localizedEntryId = &#63; and languageId = &#63;.
	 *
	 * @param localizedEntryId the localized entry ID
	 * @param languageId the language ID
	 * @return the number of matching localized entry localizations
	 */
	public int countByLocalizedEntryId_LanguageId(
		long localizedEntryId, String languageId);

	/**
	 * Caches the localized entry localization in the entity cache if it is enabled.
	 *
	 * @param localizedEntryLocalization the localized entry localization
	 */
	public void cacheResult(
		LocalizedEntryLocalization localizedEntryLocalization);

	/**
	 * Caches the localized entry localizations in the entity cache if it is enabled.
	 *
	 * @param localizedEntryLocalizations the localized entry localizations
	 */
	public void cacheResult(
		java.util.List<LocalizedEntryLocalization> localizedEntryLocalizations);

	/**
	 * Creates a new localized entry localization with the primary key. Does not add the localized entry localization to the database.
	 *
	 * @param localizedEntryLocalizationId the primary key for the new localized entry localization
	 * @return the new localized entry localization
	 */
	public LocalizedEntryLocalization create(long localizedEntryLocalizationId);

	/**
	 * Removes the localized entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param localizedEntryLocalizationId the primary key of the localized entry localization
	 * @return the localized entry localization that was removed
	 * @throws NoSuchLocalizedEntryLocalizationException if a localized entry localization with the primary key could not be found
	 */
	public LocalizedEntryLocalization remove(long localizedEntryLocalizationId)
		throws NoSuchLocalizedEntryLocalizationException;

	public LocalizedEntryLocalization updateImpl(
		LocalizedEntryLocalization localizedEntryLocalization);

	/**
	 * Returns the localized entry localization with the primary key or throws a <code>NoSuchLocalizedEntryLocalizationException</code> if it could not be found.
	 *
	 * @param localizedEntryLocalizationId the primary key of the localized entry localization
	 * @return the localized entry localization
	 * @throws NoSuchLocalizedEntryLocalizationException if a localized entry localization with the primary key could not be found
	 */
	public LocalizedEntryLocalization findByPrimaryKey(
			long localizedEntryLocalizationId)
		throws NoSuchLocalizedEntryLocalizationException;

	/**
	 * Returns the localized entry localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param localizedEntryLocalizationId the primary key of the localized entry localization
	 * @return the localized entry localization, or <code>null</code> if a localized entry localization with the primary key could not be found
	 */
	public LocalizedEntryLocalization fetchByPrimaryKey(
		long localizedEntryLocalizationId);

	/**
	 * Returns all the localized entry localizations.
	 *
	 * @return the localized entry localizations
	 */
	public java.util.List<LocalizedEntryLocalization> findAll();

	/**
	 * Returns a range of all the localized entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LocalizedEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of localized entry localizations
	 * @param end the upper bound of the range of localized entry localizations (not inclusive)
	 * @return the range of localized entry localizations
	 */
	public java.util.List<LocalizedEntryLocalization> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the localized entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LocalizedEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of localized entry localizations
	 * @param end the upper bound of the range of localized entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of localized entry localizations
	 */
	public java.util.List<LocalizedEntryLocalization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LocalizedEntryLocalization> orderByComparator);

	/**
	 * Returns an ordered range of all the localized entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LocalizedEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of localized entry localizations
	 * @param end the upper bound of the range of localized entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of localized entry localizations
	 */
	public java.util.List<LocalizedEntryLocalization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LocalizedEntryLocalization> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the localized entry localizations from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of localized entry localizations.
	 *
	 * @return the number of localized entry localizations
	 */
	public int countAll();

}