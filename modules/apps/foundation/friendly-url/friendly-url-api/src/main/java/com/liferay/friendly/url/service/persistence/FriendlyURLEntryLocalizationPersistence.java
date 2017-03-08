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

package com.liferay.friendly.url.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.friendly.url.exception.NoSuchFriendlyURLEntryLocalizationException;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the friendly url entry localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.friendly.url.service.persistence.impl.FriendlyURLEntryLocalizationPersistenceImpl
 * @see FriendlyURLEntryLocalizationUtil
 * @generated
 */
@ProviderType
public interface FriendlyURLEntryLocalizationPersistence extends BasePersistence<FriendlyURLEntryLocalization> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FriendlyURLEntryLocalizationUtil} to access the friendly url entry localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the friendly url entry localizations where groupId = &#63; and friendlyURLEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLEntryId the friendly url entry ID
	* @return the matching friendly url entry localizations
	*/
	public java.util.List<FriendlyURLEntryLocalization> findByG_F(
		long groupId, long friendlyURLEntryId);

	/**
	* Returns a range of all the friendly url entry localizations where groupId = &#63; and friendlyURLEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param friendlyURLEntryId the friendly url entry ID
	* @param start the lower bound of the range of friendly url entry localizations
	* @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	* @return the range of matching friendly url entry localizations
	*/
	public java.util.List<FriendlyURLEntryLocalization> findByG_F(
		long groupId, long friendlyURLEntryId, int start, int end);

	/**
	* Returns an ordered range of all the friendly url entry localizations where groupId = &#63; and friendlyURLEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param friendlyURLEntryId the friendly url entry ID
	* @param start the lower bound of the range of friendly url entry localizations
	* @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching friendly url entry localizations
	*/
	public java.util.List<FriendlyURLEntryLocalization> findByG_F(
		long groupId, long friendlyURLEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntryLocalization> orderByComparator);

	/**
	* Returns an ordered range of all the friendly url entry localizations where groupId = &#63; and friendlyURLEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param friendlyURLEntryId the friendly url entry ID
	* @param start the lower bound of the range of friendly url entry localizations
	* @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching friendly url entry localizations
	*/
	public java.util.List<FriendlyURLEntryLocalization> findByG_F(
		long groupId, long friendlyURLEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntryLocalization> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first friendly url entry localization in the ordered set where groupId = &#63; and friendlyURLEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLEntryId the friendly url entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url entry localization
	* @throws NoSuchFriendlyURLEntryLocalizationException if a matching friendly url entry localization could not be found
	*/
	public FriendlyURLEntryLocalization findByG_F_First(long groupId,
		long friendlyURLEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntryLocalization> orderByComparator)
		throws NoSuchFriendlyURLEntryLocalizationException;

	/**
	* Returns the first friendly url entry localization in the ordered set where groupId = &#63; and friendlyURLEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLEntryId the friendly url entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	*/
	public FriendlyURLEntryLocalization fetchByG_F_First(long groupId,
		long friendlyURLEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntryLocalization> orderByComparator);

	/**
	* Returns the last friendly url entry localization in the ordered set where groupId = &#63; and friendlyURLEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLEntryId the friendly url entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url entry localization
	* @throws NoSuchFriendlyURLEntryLocalizationException if a matching friendly url entry localization could not be found
	*/
	public FriendlyURLEntryLocalization findByG_F_Last(long groupId,
		long friendlyURLEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntryLocalization> orderByComparator)
		throws NoSuchFriendlyURLEntryLocalizationException;

	/**
	* Returns the last friendly url entry localization in the ordered set where groupId = &#63; and friendlyURLEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLEntryId the friendly url entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	*/
	public FriendlyURLEntryLocalization fetchByG_F_Last(long groupId,
		long friendlyURLEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntryLocalization> orderByComparator);

	/**
	* Returns the friendly url entry localizations before and after the current friendly url entry localization in the ordered set where groupId = &#63; and friendlyURLEntryId = &#63;.
	*
	* @param friendlyURLEntryLocalizationId the primary key of the current friendly url entry localization
	* @param groupId the group ID
	* @param friendlyURLEntryId the friendly url entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next friendly url entry localization
	* @throws NoSuchFriendlyURLEntryLocalizationException if a friendly url entry localization with the primary key could not be found
	*/
	public FriendlyURLEntryLocalization[] findByG_F_PrevAndNext(
		long friendlyURLEntryLocalizationId, long groupId,
		long friendlyURLEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntryLocalization> orderByComparator)
		throws NoSuchFriendlyURLEntryLocalizationException;

	/**
	* Removes all the friendly url entry localizations where groupId = &#63; and friendlyURLEntryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param friendlyURLEntryId the friendly url entry ID
	*/
	public void removeByG_F(long groupId, long friendlyURLEntryId);

	/**
	* Returns the number of friendly url entry localizations where groupId = &#63; and friendlyURLEntryId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLEntryId the friendly url entry ID
	* @return the number of matching friendly url entry localizations
	*/
	public int countByG_F(long groupId, long friendlyURLEntryId);

	/**
	* Returns the friendly url entry localization where groupId = &#63; and friendlyURLEntryId = &#63; and languageId = &#63; or throws a {@link NoSuchFriendlyURLEntryLocalizationException} if it could not be found.
	*
	* @param groupId the group ID
	* @param friendlyURLEntryId the friendly url entry ID
	* @param languageId the language ID
	* @return the matching friendly url entry localization
	* @throws NoSuchFriendlyURLEntryLocalizationException if a matching friendly url entry localization could not be found
	*/
	public FriendlyURLEntryLocalization findByG_F_L(long groupId,
		long friendlyURLEntryId, java.lang.String languageId)
		throws NoSuchFriendlyURLEntryLocalizationException;

	/**
	* Returns the friendly url entry localization where groupId = &#63; and friendlyURLEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param friendlyURLEntryId the friendly url entry ID
	* @param languageId the language ID
	* @return the matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	*/
	public FriendlyURLEntryLocalization fetchByG_F_L(long groupId,
		long friendlyURLEntryId, java.lang.String languageId);

	/**
	* Returns the friendly url entry localization where groupId = &#63; and friendlyURLEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param friendlyURLEntryId the friendly url entry ID
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	*/
	public FriendlyURLEntryLocalization fetchByG_F_L(long groupId,
		long friendlyURLEntryId, java.lang.String languageId,
		boolean retrieveFromCache);

	/**
	* Removes the friendly url entry localization where groupId = &#63; and friendlyURLEntryId = &#63; and languageId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param friendlyURLEntryId the friendly url entry ID
	* @param languageId the language ID
	* @return the friendly url entry localization that was removed
	*/
	public FriendlyURLEntryLocalization removeByG_F_L(long groupId,
		long friendlyURLEntryId, java.lang.String languageId)
		throws NoSuchFriendlyURLEntryLocalizationException;

	/**
	* Returns the number of friendly url entry localizations where groupId = &#63; and friendlyURLEntryId = &#63; and languageId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLEntryId the friendly url entry ID
	* @param languageId the language ID
	* @return the number of matching friendly url entry localizations
	*/
	public int countByG_F_L(long groupId, long friendlyURLEntryId,
		java.lang.String languageId);

	/**
	* Returns the friendly url entry localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or throws a {@link NoSuchFriendlyURLEntryLocalizationException} if it could not be found.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @return the matching friendly url entry localization
	* @throws NoSuchFriendlyURLEntryLocalizationException if a matching friendly url entry localization could not be found
	*/
	public FriendlyURLEntryLocalization findByG_U_L(long groupId,
		java.lang.String urlTitle, java.lang.String languageId)
		throws NoSuchFriendlyURLEntryLocalizationException;

	/**
	* Returns the friendly url entry localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @return the matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	*/
	public FriendlyURLEntryLocalization fetchByG_U_L(long groupId,
		java.lang.String urlTitle, java.lang.String languageId);

	/**
	* Returns the friendly url entry localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	*/
	public FriendlyURLEntryLocalization fetchByG_U_L(long groupId,
		java.lang.String urlTitle, java.lang.String languageId,
		boolean retrieveFromCache);

	/**
	* Removes the friendly url entry localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @return the friendly url entry localization that was removed
	*/
	public FriendlyURLEntryLocalization removeByG_U_L(long groupId,
		java.lang.String urlTitle, java.lang.String languageId)
		throws NoSuchFriendlyURLEntryLocalizationException;

	/**
	* Returns the number of friendly url entry localizations where groupId = &#63; and urlTitle = &#63; and languageId = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @return the number of matching friendly url entry localizations
	*/
	public int countByG_U_L(long groupId, java.lang.String urlTitle,
		java.lang.String languageId);

	/**
	* Caches the friendly url entry localization in the entity cache if it is enabled.
	*
	* @param friendlyURLEntryLocalization the friendly url entry localization
	*/
	public void cacheResult(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization);

	/**
	* Caches the friendly url entry localizations in the entity cache if it is enabled.
	*
	* @param friendlyURLEntryLocalizations the friendly url entry localizations
	*/
	public void cacheResult(
		java.util.List<FriendlyURLEntryLocalization> friendlyURLEntryLocalizations);

	/**
	* Creates a new friendly url entry localization with the primary key. Does not add the friendly url entry localization to the database.
	*
	* @param friendlyURLEntryLocalizationId the primary key for the new friendly url entry localization
	* @return the new friendly url entry localization
	*/
	public FriendlyURLEntryLocalization create(
		long friendlyURLEntryLocalizationId);

	/**
	* Removes the friendly url entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLEntryLocalizationId the primary key of the friendly url entry localization
	* @return the friendly url entry localization that was removed
	* @throws NoSuchFriendlyURLEntryLocalizationException if a friendly url entry localization with the primary key could not be found
	*/
	public FriendlyURLEntryLocalization remove(
		long friendlyURLEntryLocalizationId)
		throws NoSuchFriendlyURLEntryLocalizationException;

	public FriendlyURLEntryLocalization updateImpl(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization);

	/**
	* Returns the friendly url entry localization with the primary key or throws a {@link NoSuchFriendlyURLEntryLocalizationException} if it could not be found.
	*
	* @param friendlyURLEntryLocalizationId the primary key of the friendly url entry localization
	* @return the friendly url entry localization
	* @throws NoSuchFriendlyURLEntryLocalizationException if a friendly url entry localization with the primary key could not be found
	*/
	public FriendlyURLEntryLocalization findByPrimaryKey(
		long friendlyURLEntryLocalizationId)
		throws NoSuchFriendlyURLEntryLocalizationException;

	/**
	* Returns the friendly url entry localization with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param friendlyURLEntryLocalizationId the primary key of the friendly url entry localization
	* @return the friendly url entry localization, or <code>null</code> if a friendly url entry localization with the primary key could not be found
	*/
	public FriendlyURLEntryLocalization fetchByPrimaryKey(
		long friendlyURLEntryLocalizationId);

	@Override
	public java.util.Map<java.io.Serializable, FriendlyURLEntryLocalization> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the friendly url entry localizations.
	*
	* @return the friendly url entry localizations
	*/
	public java.util.List<FriendlyURLEntryLocalization> findAll();

	/**
	* Returns a range of all the friendly url entry localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url entry localizations
	* @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	* @return the range of friendly url entry localizations
	*/
	public java.util.List<FriendlyURLEntryLocalization> findAll(int start,
		int end);

	/**
	* Returns an ordered range of all the friendly url entry localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url entry localizations
	* @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of friendly url entry localizations
	*/
	public java.util.List<FriendlyURLEntryLocalization> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntryLocalization> orderByComparator);

	/**
	* Returns an ordered range of all the friendly url entry localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url entry localizations
	* @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of friendly url entry localizations
	*/
	public java.util.List<FriendlyURLEntryLocalization> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntryLocalization> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the friendly url entry localizations from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of friendly url entry localizations.
	*
	* @return the number of friendly url entry localizations
	*/
	public int countAll();
}