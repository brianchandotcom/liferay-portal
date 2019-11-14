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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryLocalizationException;
import com.liferay.portal.tools.service.builder.test.model.LVEntryLocalization;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the lv entry localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryLocalizationUtil
 * @generated
 */
@ProviderType
public interface LVEntryLocalizationPersistence
	extends BasePersistence<LVEntryLocalization> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LVEntryLocalizationUtil} to access the lv entry localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the lv entry localizations where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @return the matching lv entry localizations
	 */
	public java.util.List<LVEntryLocalization> findByLvEntryId(long lvEntryId);

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
	public java.util.List<LVEntryLocalization> findByLvEntryId(
		long lvEntryId, int start, int end);

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
	public java.util.List<LVEntryLocalization> findByLvEntryId(
		long lvEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryLocalization>
			orderByComparator);

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
	public java.util.List<LVEntryLocalization> findByLvEntryId(
		long lvEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryLocalization>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first lv entry localization in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a matching lv entry localization could not be found
	 */
	public LVEntryLocalization findByLvEntryId_First(
			long lvEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LVEntryLocalization> orderByComparator)
		throws NoSuchLVEntryLocalizationException;

	/**
	 * Returns the first lv entry localization in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	public LVEntryLocalization fetchByLvEntryId_First(
		long lvEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryLocalization>
			orderByComparator);

	/**
	 * Returns the last lv entry localization in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a matching lv entry localization could not be found
	 */
	public LVEntryLocalization findByLvEntryId_Last(
			long lvEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LVEntryLocalization> orderByComparator)
		throws NoSuchLVEntryLocalizationException;

	/**
	 * Returns the last lv entry localization in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	public LVEntryLocalization fetchByLvEntryId_Last(
		long lvEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryLocalization>
			orderByComparator);

	/**
	 * Returns the lv entry localizations before and after the current lv entry localization in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryLocalizationId the primary key of the current lv entry localization
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a lv entry localization with the primary key could not be found
	 */
	public LVEntryLocalization[] findByLvEntryId_PrevAndNext(
			long lvEntryLocalizationId, long lvEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LVEntryLocalization> orderByComparator)
		throws NoSuchLVEntryLocalizationException;

	/**
	 * Removes all the lv entry localizations where lvEntryId = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 */
	public void removeByLvEntryId(long lvEntryId);

	/**
	 * Returns the number of lv entry localizations where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @return the number of matching lv entry localizations
	 */
	public int countByLvEntryId(long lvEntryId);

	/**
	 * Returns the lv entry localization where lvEntryId = &#63; and languageId = &#63; or throws a <code>NoSuchLVEntryLocalizationException</code> if it could not be found.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @return the matching lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a matching lv entry localization could not be found
	 */
	public LVEntryLocalization findByLvEntryId_LanguageId(
			long lvEntryId, String languageId)
		throws NoSuchLVEntryLocalizationException;

	/**
	 * Returns the lv entry localization where lvEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @return the matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	public LVEntryLocalization fetchByLvEntryId_LanguageId(
		long lvEntryId, String languageId);

	/**
	 * Returns the lv entry localization where lvEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	public LVEntryLocalization fetchByLvEntryId_LanguageId(
		long lvEntryId, String languageId, boolean useFinderCache);

	/**
	 * Removes the lv entry localization where lvEntryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @return the lv entry localization that was removed
	 */
	public LVEntryLocalization removeByLvEntryId_LanguageId(
			long lvEntryId, String languageId)
		throws NoSuchLVEntryLocalizationException;

	/**
	 * Returns the number of lv entry localizations where lvEntryId = &#63; and languageId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param languageId the language ID
	 * @return the number of matching lv entry localizations
	 */
	public int countByLvEntryId_LanguageId(long lvEntryId, String languageId);

	/**
	 * Returns the lv entry localization where headId = &#63; or throws a <code>NoSuchLVEntryLocalizationException</code> if it could not be found.
	 *
	 * @param headId the head ID
	 * @return the matching lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a matching lv entry localization could not be found
	 */
	public LVEntryLocalization findByHeadId(long headId)
		throws NoSuchLVEntryLocalizationException;

	/**
	 * Returns the lv entry localization where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param headId the head ID
	 * @return the matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	public LVEntryLocalization fetchByHeadId(long headId);

	/**
	 * Returns the lv entry localization where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param headId the head ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry localization, or <code>null</code> if a matching lv entry localization could not be found
	 */
	public LVEntryLocalization fetchByHeadId(
		long headId, boolean useFinderCache);

	/**
	 * Removes the lv entry localization where headId = &#63; from the database.
	 *
	 * @param headId the head ID
	 * @return the lv entry localization that was removed
	 */
	public LVEntryLocalization removeByHeadId(long headId)
		throws NoSuchLVEntryLocalizationException;

	/**
	 * Returns the number of lv entry localizations where headId = &#63;.
	 *
	 * @param headId the head ID
	 * @return the number of matching lv entry localizations
	 */
	public int countByHeadId(long headId);

	/**
	 * Caches the lv entry localization in the entity cache if it is enabled.
	 *
	 * @param lvEntryLocalization the lv entry localization
	 */
	public void cacheResult(LVEntryLocalization lvEntryLocalization);

	/**
	 * Caches the lv entry localizations in the entity cache if it is enabled.
	 *
	 * @param lvEntryLocalizations the lv entry localizations
	 */
	public void cacheResult(
		java.util.List<LVEntryLocalization> lvEntryLocalizations);

	/**
	 * Creates a new lv entry localization with the primary key. Does not add the lv entry localization to the database.
	 *
	 * @param lvEntryLocalizationId the primary key for the new lv entry localization
	 * @return the new lv entry localization
	 */
	public LVEntryLocalization create(long lvEntryLocalizationId);

	/**
	 * Removes the lv entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lvEntryLocalizationId the primary key of the lv entry localization
	 * @return the lv entry localization that was removed
	 * @throws NoSuchLVEntryLocalizationException if a lv entry localization with the primary key could not be found
	 */
	public LVEntryLocalization remove(long lvEntryLocalizationId)
		throws NoSuchLVEntryLocalizationException;

	public LVEntryLocalization updateImpl(
		LVEntryLocalization lvEntryLocalization);

	/**
	 * Returns the lv entry localization with the primary key or throws a <code>NoSuchLVEntryLocalizationException</code> if it could not be found.
	 *
	 * @param lvEntryLocalizationId the primary key of the lv entry localization
	 * @return the lv entry localization
	 * @throws NoSuchLVEntryLocalizationException if a lv entry localization with the primary key could not be found
	 */
	public LVEntryLocalization findByPrimaryKey(long lvEntryLocalizationId)
		throws NoSuchLVEntryLocalizationException;

	/**
	 * Returns the lv entry localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param lvEntryLocalizationId the primary key of the lv entry localization
	 * @return the lv entry localization, or <code>null</code> if a lv entry localization with the primary key could not be found
	 */
	public LVEntryLocalization fetchByPrimaryKey(long lvEntryLocalizationId);

	/**
	 * Returns all the lv entry localizations.
	 *
	 * @return the lv entry localizations
	 */
	public java.util.List<LVEntryLocalization> findAll();

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
	public java.util.List<LVEntryLocalization> findAll(int start, int end);

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
	public java.util.List<LVEntryLocalization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryLocalization>
			orderByComparator);

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
	public java.util.List<LVEntryLocalization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<LVEntryLocalization>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the lv entry localizations from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of lv entry localizations.
	 *
	 * @return the number of lv entry localizations
	 */
	public int countAll();

}