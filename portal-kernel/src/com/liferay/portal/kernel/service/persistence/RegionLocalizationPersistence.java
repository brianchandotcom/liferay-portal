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

import com.liferay.portal.kernel.exception.NoSuchRegionLocalizationException;
import com.liferay.portal.kernel.model.RegionLocalization;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the region localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RegionLocalizationUtil
 * @generated
 */
@ProviderType
public interface RegionLocalizationPersistence
	extends BasePersistence<RegionLocalization> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RegionLocalizationUtil} to access the region localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the region localizations where regionId = &#63;.
	 *
	 * @param regionId the region ID
	 * @return the matching region localizations
	 */
	public java.util.List<RegionLocalization> findByRegionId(long regionId);

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
	public java.util.List<RegionLocalization> findByRegionId(
		long regionId, int start, int end);

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
	public java.util.List<RegionLocalization> findByRegionId(
		long regionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegionLocalization>
			orderByComparator);

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
	public java.util.List<RegionLocalization> findByRegionId(
		long regionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegionLocalization>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first region localization in the ordered set where regionId = &#63;.
	 *
	 * @param regionId the region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching region localization
	 * @throws NoSuchRegionLocalizationException if a matching region localization could not be found
	 */
	public RegionLocalization findByRegionId_First(
			long regionId,
			com.liferay.portal.kernel.util.OrderByComparator<RegionLocalization>
				orderByComparator)
		throws NoSuchRegionLocalizationException;

	/**
	 * Returns the first region localization in the ordered set where regionId = &#63;.
	 *
	 * @param regionId the region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching region localization, or <code>null</code> if a matching region localization could not be found
	 */
	public RegionLocalization fetchByRegionId_First(
		long regionId,
		com.liferay.portal.kernel.util.OrderByComparator<RegionLocalization>
			orderByComparator);

	/**
	 * Returns the last region localization in the ordered set where regionId = &#63;.
	 *
	 * @param regionId the region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching region localization
	 * @throws NoSuchRegionLocalizationException if a matching region localization could not be found
	 */
	public RegionLocalization findByRegionId_Last(
			long regionId,
			com.liferay.portal.kernel.util.OrderByComparator<RegionLocalization>
				orderByComparator)
		throws NoSuchRegionLocalizationException;

	/**
	 * Returns the last region localization in the ordered set where regionId = &#63;.
	 *
	 * @param regionId the region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching region localization, or <code>null</code> if a matching region localization could not be found
	 */
	public RegionLocalization fetchByRegionId_Last(
		long regionId,
		com.liferay.portal.kernel.util.OrderByComparator<RegionLocalization>
			orderByComparator);

	/**
	 * Returns the region localizations before and after the current region localization in the ordered set where regionId = &#63;.
	 *
	 * @param regionLocalizationId the primary key of the current region localization
	 * @param regionId the region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next region localization
	 * @throws NoSuchRegionLocalizationException if a region localization with the primary key could not be found
	 */
	public RegionLocalization[] findByRegionId_PrevAndNext(
			long regionLocalizationId, long regionId,
			com.liferay.portal.kernel.util.OrderByComparator<RegionLocalization>
				orderByComparator)
		throws NoSuchRegionLocalizationException;

	/**
	 * Removes all the region localizations where regionId = &#63; from the database.
	 *
	 * @param regionId the region ID
	 */
	public void removeByRegionId(long regionId);

	/**
	 * Returns the number of region localizations where regionId = &#63;.
	 *
	 * @param regionId the region ID
	 * @return the number of matching region localizations
	 */
	public int countByRegionId(long regionId);

	/**
	 * Returns the region localization where regionId = &#63; and languageId = &#63; or throws a <code>NoSuchRegionLocalizationException</code> if it could not be found.
	 *
	 * @param regionId the region ID
	 * @param languageId the language ID
	 * @return the matching region localization
	 * @throws NoSuchRegionLocalizationException if a matching region localization could not be found
	 */
	public RegionLocalization findByRegionId_LanguageId(
			long regionId, String languageId)
		throws NoSuchRegionLocalizationException;

	/**
	 * Returns the region localization where regionId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param regionId the region ID
	 * @param languageId the language ID
	 * @return the matching region localization, or <code>null</code> if a matching region localization could not be found
	 */
	public RegionLocalization fetchByRegionId_LanguageId(
		long regionId, String languageId);

	/**
	 * Returns the region localization where regionId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param regionId the region ID
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching region localization, or <code>null</code> if a matching region localization could not be found
	 */
	public RegionLocalization fetchByRegionId_LanguageId(
		long regionId, String languageId, boolean useFinderCache);

	/**
	 * Removes the region localization where regionId = &#63; and languageId = &#63; from the database.
	 *
	 * @param regionId the region ID
	 * @param languageId the language ID
	 * @return the region localization that was removed
	 */
	public RegionLocalization removeByRegionId_LanguageId(
			long regionId, String languageId)
		throws NoSuchRegionLocalizationException;

	/**
	 * Returns the number of region localizations where regionId = &#63; and languageId = &#63;.
	 *
	 * @param regionId the region ID
	 * @param languageId the language ID
	 * @return the number of matching region localizations
	 */
	public int countByRegionId_LanguageId(long regionId, String languageId);

	/**
	 * Caches the region localization in the entity cache if it is enabled.
	 *
	 * @param regionLocalization the region localization
	 */
	public void cacheResult(RegionLocalization regionLocalization);

	/**
	 * Caches the region localizations in the entity cache if it is enabled.
	 *
	 * @param regionLocalizations the region localizations
	 */
	public void cacheResult(
		java.util.List<RegionLocalization> regionLocalizations);

	/**
	 * Creates a new region localization with the primary key. Does not add the region localization to the database.
	 *
	 * @param regionLocalizationId the primary key for the new region localization
	 * @return the new region localization
	 */
	public RegionLocalization create(long regionLocalizationId);

	/**
	 * Removes the region localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param regionLocalizationId the primary key of the region localization
	 * @return the region localization that was removed
	 * @throws NoSuchRegionLocalizationException if a region localization with the primary key could not be found
	 */
	public RegionLocalization remove(long regionLocalizationId)
		throws NoSuchRegionLocalizationException;

	public RegionLocalization updateImpl(RegionLocalization regionLocalization);

	/**
	 * Returns the region localization with the primary key or throws a <code>NoSuchRegionLocalizationException</code> if it could not be found.
	 *
	 * @param regionLocalizationId the primary key of the region localization
	 * @return the region localization
	 * @throws NoSuchRegionLocalizationException if a region localization with the primary key could not be found
	 */
	public RegionLocalization findByPrimaryKey(long regionLocalizationId)
		throws NoSuchRegionLocalizationException;

	/**
	 * Returns the region localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param regionLocalizationId the primary key of the region localization
	 * @return the region localization, or <code>null</code> if a region localization with the primary key could not be found
	 */
	public RegionLocalization fetchByPrimaryKey(long regionLocalizationId);

	/**
	 * Returns all the region localizations.
	 *
	 * @return the region localizations
	 */
	public java.util.List<RegionLocalization> findAll();

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
	public java.util.List<RegionLocalization> findAll(int start, int end);

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
	public java.util.List<RegionLocalization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegionLocalization>
			orderByComparator);

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
	public java.util.List<RegionLocalization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<RegionLocalization>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the region localizations from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of region localizations.
	 *
	 * @return the number of region localizations
	 */
	public int countAll();

}