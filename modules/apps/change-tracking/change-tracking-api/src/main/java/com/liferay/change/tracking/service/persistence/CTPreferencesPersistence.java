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

package com.liferay.change.tracking.service.persistence;

import com.liferay.change.tracking.exception.NoSuchPreferencesException;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ct preferences service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTPreferencesUtil
 * @generated
 */
@ProviderType
public interface CTPreferencesPersistence
	extends BasePersistence<CTPreferences> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTPreferencesUtil} to access the ct preferences persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ct preferenceses where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct preferenceses
	 */
	public java.util.List<CTPreferences> findByCtCollectionId(
		long ctCollectionId);

	/**
	 * Returns a range of all the ct preferenceses where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @return the range of matching ct preferenceses
	 */
	public java.util.List<CTPreferences> findByCtCollectionId(
		long ctCollectionId, int start, int end);

	/**
	 * Returns an ordered range of all the ct preferenceses where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct preferenceses
	 */
	public java.util.List<CTPreferences> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTPreferences>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct preferenceses where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct preferenceses
	 */
	public java.util.List<CTPreferences> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTPreferences>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ct preferences in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct preferences
	 * @throws NoSuchPreferencesException if a matching ct preferences could not be found
	 */
	public CTPreferences findByCtCollectionId_First(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTPreferences>
				orderByComparator)
		throws NoSuchPreferencesException;

	/**
	 * Returns the first ct preferences in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct preferences, or <code>null</code> if a matching ct preferences could not be found
	 */
	public CTPreferences fetchByCtCollectionId_First(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTPreferences>
			orderByComparator);

	/**
	 * Returns the last ct preferences in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct preferences
	 * @throws NoSuchPreferencesException if a matching ct preferences could not be found
	 */
	public CTPreferences findByCtCollectionId_Last(
			long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTPreferences>
				orderByComparator)
		throws NoSuchPreferencesException;

	/**
	 * Returns the last ct preferences in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct preferences, or <code>null</code> if a matching ct preferences could not be found
	 */
	public CTPreferences fetchByCtCollectionId_Last(
		long ctCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTPreferences>
			orderByComparator);

	/**
	 * Returns the ct preferenceses before and after the current ct preferences in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctPreferencesId the primary key of the current ct preferences
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct preferences
	 * @throws NoSuchPreferencesException if a ct preferences with the primary key could not be found
	 */
	public CTPreferences[] findByCtCollectionId_PrevAndNext(
			long ctPreferencesId, long ctCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTPreferences>
				orderByComparator)
		throws NoSuchPreferencesException;

	/**
	 * Removes all the ct preferenceses where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public void removeByCtCollectionId(long ctCollectionId);

	/**
	 * Returns the number of ct preferenceses where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct preferenceses
	 */
	public int countByCtCollectionId(long ctCollectionId);

	/**
	 * Returns all the ct preferenceses where previousCtCollectionId = &#63;.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @return the matching ct preferenceses
	 */
	public java.util.List<CTPreferences> findByPreviousCtCollectionId(
		long previousCtCollectionId);

	/**
	 * Returns a range of all the ct preferenceses where previousCtCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @return the range of matching ct preferenceses
	 */
	public java.util.List<CTPreferences> findByPreviousCtCollectionId(
		long previousCtCollectionId, int start, int end);

	/**
	 * Returns an ordered range of all the ct preferenceses where previousCtCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct preferenceses
	 */
	public java.util.List<CTPreferences> findByPreviousCtCollectionId(
		long previousCtCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTPreferences>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct preferenceses where previousCtCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct preferenceses
	 */
	public java.util.List<CTPreferences> findByPreviousCtCollectionId(
		long previousCtCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTPreferences>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ct preferences in the ordered set where previousCtCollectionId = &#63;.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct preferences
	 * @throws NoSuchPreferencesException if a matching ct preferences could not be found
	 */
	public CTPreferences findByPreviousCtCollectionId_First(
			long previousCtCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTPreferences>
				orderByComparator)
		throws NoSuchPreferencesException;

	/**
	 * Returns the first ct preferences in the ordered set where previousCtCollectionId = &#63;.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct preferences, or <code>null</code> if a matching ct preferences could not be found
	 */
	public CTPreferences fetchByPreviousCtCollectionId_First(
		long previousCtCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTPreferences>
			orderByComparator);

	/**
	 * Returns the last ct preferences in the ordered set where previousCtCollectionId = &#63;.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct preferences
	 * @throws NoSuchPreferencesException if a matching ct preferences could not be found
	 */
	public CTPreferences findByPreviousCtCollectionId_Last(
			long previousCtCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTPreferences>
				orderByComparator)
		throws NoSuchPreferencesException;

	/**
	 * Returns the last ct preferences in the ordered set where previousCtCollectionId = &#63;.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct preferences, or <code>null</code> if a matching ct preferences could not be found
	 */
	public CTPreferences fetchByPreviousCtCollectionId_Last(
		long previousCtCollectionId,
		com.liferay.portal.kernel.util.OrderByComparator<CTPreferences>
			orderByComparator);

	/**
	 * Returns the ct preferenceses before and after the current ct preferences in the ordered set where previousCtCollectionId = &#63;.
	 *
	 * @param ctPreferencesId the primary key of the current ct preferences
	 * @param previousCtCollectionId the previous ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct preferences
	 * @throws NoSuchPreferencesException if a ct preferences with the primary key could not be found
	 */
	public CTPreferences[] findByPreviousCtCollectionId_PrevAndNext(
			long ctPreferencesId, long previousCtCollectionId,
			com.liferay.portal.kernel.util.OrderByComparator<CTPreferences>
				orderByComparator)
		throws NoSuchPreferencesException;

	/**
	 * Removes all the ct preferenceses where previousCtCollectionId = &#63; from the database.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 */
	public void removeByPreviousCtCollectionId(long previousCtCollectionId);

	/**
	 * Returns the number of ct preferenceses where previousCtCollectionId = &#63;.
	 *
	 * @param previousCtCollectionId the previous ct collection ID
	 * @return the number of matching ct preferenceses
	 */
	public int countByPreviousCtCollectionId(long previousCtCollectionId);

	/**
	 * Returns the ct preferences where companyId = &#63; and userId = &#63; or throws a <code>NoSuchPreferencesException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching ct preferences
	 * @throws NoSuchPreferencesException if a matching ct preferences could not be found
	 */
	public CTPreferences findByC_U(long companyId, long userId)
		throws NoSuchPreferencesException;

	/**
	 * Returns the ct preferences where companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching ct preferences, or <code>null</code> if a matching ct preferences could not be found
	 */
	public CTPreferences fetchByC_U(long companyId, long userId);

	/**
	 * Returns the ct preferences where companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ct preferences, or <code>null</code> if a matching ct preferences could not be found
	 */
	public CTPreferences fetchByC_U(
		long companyId, long userId, boolean useFinderCache);

	/**
	 * Removes the ct preferences where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the ct preferences that was removed
	 */
	public CTPreferences removeByC_U(long companyId, long userId)
		throws NoSuchPreferencesException;

	/**
	 * Returns the number of ct preferenceses where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching ct preferenceses
	 */
	public int countByC_U(long companyId, long userId);

	/**
	 * Caches the ct preferences in the entity cache if it is enabled.
	 *
	 * @param ctPreferences the ct preferences
	 */
	public void cacheResult(CTPreferences ctPreferences);

	/**
	 * Caches the ct preferenceses in the entity cache if it is enabled.
	 *
	 * @param ctPreferenceses the ct preferenceses
	 */
	public void cacheResult(java.util.List<CTPreferences> ctPreferenceses);

	/**
	 * Creates a new ct preferences with the primary key. Does not add the ct preferences to the database.
	 *
	 * @param ctPreferencesId the primary key for the new ct preferences
	 * @return the new ct preferences
	 */
	public CTPreferences create(long ctPreferencesId);

	/**
	 * Removes the ct preferences with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctPreferencesId the primary key of the ct preferences
	 * @return the ct preferences that was removed
	 * @throws NoSuchPreferencesException if a ct preferences with the primary key could not be found
	 */
	public CTPreferences remove(long ctPreferencesId)
		throws NoSuchPreferencesException;

	public CTPreferences updateImpl(CTPreferences ctPreferences);

	/**
	 * Returns the ct preferences with the primary key or throws a <code>NoSuchPreferencesException</code> if it could not be found.
	 *
	 * @param ctPreferencesId the primary key of the ct preferences
	 * @return the ct preferences
	 * @throws NoSuchPreferencesException if a ct preferences with the primary key could not be found
	 */
	public CTPreferences findByPrimaryKey(long ctPreferencesId)
		throws NoSuchPreferencesException;

	/**
	 * Returns the ct preferences with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctPreferencesId the primary key of the ct preferences
	 * @return the ct preferences, or <code>null</code> if a ct preferences with the primary key could not be found
	 */
	public CTPreferences fetchByPrimaryKey(long ctPreferencesId);

	/**
	 * Returns all the ct preferenceses.
	 *
	 * @return the ct preferenceses
	 */
	public java.util.List<CTPreferences> findAll();

	/**
	 * Returns a range of all the ct preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @return the range of ct preferenceses
	 */
	public java.util.List<CTPreferences> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ct preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct preferenceses
	 */
	public java.util.List<CTPreferences> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTPreferences>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct preferenceses
	 * @param end the upper bound of the range of ct preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct preferenceses
	 */
	public java.util.List<CTPreferences> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTPreferences>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ct preferenceses from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ct preferenceses.
	 *
	 * @return the number of ct preferenceses
	 */
	public int countAll();

}