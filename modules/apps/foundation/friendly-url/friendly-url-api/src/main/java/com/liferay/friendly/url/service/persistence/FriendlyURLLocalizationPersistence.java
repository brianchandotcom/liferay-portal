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

import com.liferay.friendly.url.exception.NoSuchFriendlyURLLocalizationException;
import com.liferay.friendly.url.model.FriendlyURLLocalization;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the friendly url localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.friendly.url.service.persistence.impl.FriendlyURLLocalizationPersistenceImpl
 * @see FriendlyURLLocalizationUtil
 * @generated
 */
@ProviderType
public interface FriendlyURLLocalizationPersistence extends BasePersistence<FriendlyURLLocalization> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FriendlyURLLocalizationUtil} to access the friendly url localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the friendly url localizations where groupId = &#63; and friendlyURLId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @return the matching friendly url localizations
	*/
	public java.util.List<FriendlyURLLocalization> findByG_F(long groupId,
		long friendlyURLId);

	/**
	* Returns a range of all the friendly url localizations where groupId = &#63; and friendlyURLId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param start the lower bound of the range of friendly url localizations
	* @param end the upper bound of the range of friendly url localizations (not inclusive)
	* @return the range of matching friendly url localizations
	*/
	public java.util.List<FriendlyURLLocalization> findByG_F(long groupId,
		long friendlyURLId, int start, int end);

	/**
	* Returns an ordered range of all the friendly url localizations where groupId = &#63; and friendlyURLId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param start the lower bound of the range of friendly url localizations
	* @param end the upper bound of the range of friendly url localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching friendly url localizations
	*/
	public java.util.List<FriendlyURLLocalization> findByG_F(long groupId,
		long friendlyURLId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLLocalization> orderByComparator);

	/**
	* Returns an ordered range of all the friendly url localizations where groupId = &#63; and friendlyURLId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param start the lower bound of the range of friendly url localizations
	* @param end the upper bound of the range of friendly url localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching friendly url localizations
	*/
	public java.util.List<FriendlyURLLocalization> findByG_F(long groupId,
		long friendlyURLId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLLocalization> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first friendly url localization in the ordered set where groupId = &#63; and friendlyURLId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url localization
	* @throws NoSuchFriendlyURLLocalizationException if a matching friendly url localization could not be found
	*/
	public FriendlyURLLocalization findByG_F_First(long groupId,
		long friendlyURLId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLLocalization> orderByComparator)
		throws NoSuchFriendlyURLLocalizationException;

	/**
	* Returns the first friendly url localization in the ordered set where groupId = &#63; and friendlyURLId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url localization, or <code>null</code> if a matching friendly url localization could not be found
	*/
	public FriendlyURLLocalization fetchByG_F_First(long groupId,
		long friendlyURLId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLLocalization> orderByComparator);

	/**
	* Returns the last friendly url localization in the ordered set where groupId = &#63; and friendlyURLId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url localization
	* @throws NoSuchFriendlyURLLocalizationException if a matching friendly url localization could not be found
	*/
	public FriendlyURLLocalization findByG_F_Last(long groupId,
		long friendlyURLId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLLocalization> orderByComparator)
		throws NoSuchFriendlyURLLocalizationException;

	/**
	* Returns the last friendly url localization in the ordered set where groupId = &#63; and friendlyURLId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url localization, or <code>null</code> if a matching friendly url localization could not be found
	*/
	public FriendlyURLLocalization fetchByG_F_Last(long groupId,
		long friendlyURLId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLLocalization> orderByComparator);

	/**
	* Returns the friendly url localizations before and after the current friendly url localization in the ordered set where groupId = &#63; and friendlyURLId = &#63;.
	*
	* @param friendlyURLLocalizationId the primary key of the current friendly url localization
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next friendly url localization
	* @throws NoSuchFriendlyURLLocalizationException if a friendly url localization with the primary key could not be found
	*/
	public FriendlyURLLocalization[] findByG_F_PrevAndNext(
		long friendlyURLLocalizationId, long groupId, long friendlyURLId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLLocalization> orderByComparator)
		throws NoSuchFriendlyURLLocalizationException;

	/**
	* Removes all the friendly url localizations where groupId = &#63; and friendlyURLId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	*/
	public void removeByG_F(long groupId, long friendlyURLId);

	/**
	* Returns the number of friendly url localizations where groupId = &#63; and friendlyURLId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @return the number of matching friendly url localizations
	*/
	public int countByG_F(long groupId, long friendlyURLId);

	/**
	* Returns the friendly url localization where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63; or throws a {@link NoSuchFriendlyURLLocalizationException} if it could not be found.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param languageId the language ID
	* @return the matching friendly url localization
	* @throws NoSuchFriendlyURLLocalizationException if a matching friendly url localization could not be found
	*/
	public FriendlyURLLocalization findByG_F_L(long groupId,
		long friendlyURLId, java.lang.String languageId)
		throws NoSuchFriendlyURLLocalizationException;

	/**
	* Returns the friendly url localization where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param languageId the language ID
	* @return the matching friendly url localization, or <code>null</code> if a matching friendly url localization could not be found
	*/
	public FriendlyURLLocalization fetchByG_F_L(long groupId,
		long friendlyURLId, java.lang.String languageId);

	/**
	* Returns the friendly url localization where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching friendly url localization, or <code>null</code> if a matching friendly url localization could not be found
	*/
	public FriendlyURLLocalization fetchByG_F_L(long groupId,
		long friendlyURLId, java.lang.String languageId,
		boolean retrieveFromCache);

	/**
	* Removes the friendly url localization where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param languageId the language ID
	* @return the friendly url localization that was removed
	*/
	public FriendlyURLLocalization removeByG_F_L(long groupId,
		long friendlyURLId, java.lang.String languageId)
		throws NoSuchFriendlyURLLocalizationException;

	/**
	* Returns the number of friendly url localizations where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param languageId the language ID
	* @return the number of matching friendly url localizations
	*/
	public int countByG_F_L(long groupId, long friendlyURLId,
		java.lang.String languageId);

	/**
	* Returns the friendly url localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or throws a {@link NoSuchFriendlyURLLocalizationException} if it could not be found.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @return the matching friendly url localization
	* @throws NoSuchFriendlyURLLocalizationException if a matching friendly url localization could not be found
	*/
	public FriendlyURLLocalization findByG_U_L(long groupId,
		java.lang.String urlTitle, java.lang.String languageId)
		throws NoSuchFriendlyURLLocalizationException;

	/**
	* Returns the friendly url localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @return the matching friendly url localization, or <code>null</code> if a matching friendly url localization could not be found
	*/
	public FriendlyURLLocalization fetchByG_U_L(long groupId,
		java.lang.String urlTitle, java.lang.String languageId);

	/**
	* Returns the friendly url localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching friendly url localization, or <code>null</code> if a matching friendly url localization could not be found
	*/
	public FriendlyURLLocalization fetchByG_U_L(long groupId,
		java.lang.String urlTitle, java.lang.String languageId,
		boolean retrieveFromCache);

	/**
	* Removes the friendly url localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @return the friendly url localization that was removed
	*/
	public FriendlyURLLocalization removeByG_U_L(long groupId,
		java.lang.String urlTitle, java.lang.String languageId)
		throws NoSuchFriendlyURLLocalizationException;

	/**
	* Returns the number of friendly url localizations where groupId = &#63; and urlTitle = &#63; and languageId = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @return the number of matching friendly url localizations
	*/
	public int countByG_U_L(long groupId, java.lang.String urlTitle,
		java.lang.String languageId);

	/**
	* Caches the friendly url localization in the entity cache if it is enabled.
	*
	* @param friendlyURLLocalization the friendly url localization
	*/
	public void cacheResult(FriendlyURLLocalization friendlyURLLocalization);

	/**
	* Caches the friendly url localizations in the entity cache if it is enabled.
	*
	* @param friendlyURLLocalizations the friendly url localizations
	*/
	public void cacheResult(
		java.util.List<FriendlyURLLocalization> friendlyURLLocalizations);

	/**
	* Creates a new friendly url localization with the primary key. Does not add the friendly url localization to the database.
	*
	* @param friendlyURLLocalizationId the primary key for the new friendly url localization
	* @return the new friendly url localization
	*/
	public FriendlyURLLocalization create(long friendlyURLLocalizationId);

	/**
	* Removes the friendly url localization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLLocalizationId the primary key of the friendly url localization
	* @return the friendly url localization that was removed
	* @throws NoSuchFriendlyURLLocalizationException if a friendly url localization with the primary key could not be found
	*/
	public FriendlyURLLocalization remove(long friendlyURLLocalizationId)
		throws NoSuchFriendlyURLLocalizationException;

	public FriendlyURLLocalization updateImpl(
		FriendlyURLLocalization friendlyURLLocalization);

	/**
	* Returns the friendly url localization with the primary key or throws a {@link NoSuchFriendlyURLLocalizationException} if it could not be found.
	*
	* @param friendlyURLLocalizationId the primary key of the friendly url localization
	* @return the friendly url localization
	* @throws NoSuchFriendlyURLLocalizationException if a friendly url localization with the primary key could not be found
	*/
	public FriendlyURLLocalization findByPrimaryKey(
		long friendlyURLLocalizationId)
		throws NoSuchFriendlyURLLocalizationException;

	/**
	* Returns the friendly url localization with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param friendlyURLLocalizationId the primary key of the friendly url localization
	* @return the friendly url localization, or <code>null</code> if a friendly url localization with the primary key could not be found
	*/
	public FriendlyURLLocalization fetchByPrimaryKey(
		long friendlyURLLocalizationId);

	@Override
	public java.util.Map<java.io.Serializable, FriendlyURLLocalization> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the friendly url localizations.
	*
	* @return the friendly url localizations
	*/
	public java.util.List<FriendlyURLLocalization> findAll();

	/**
	* Returns a range of all the friendly url localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url localizations
	* @param end the upper bound of the range of friendly url localizations (not inclusive)
	* @return the range of friendly url localizations
	*/
	public java.util.List<FriendlyURLLocalization> findAll(int start, int end);

	/**
	* Returns an ordered range of all the friendly url localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url localizations
	* @param end the upper bound of the range of friendly url localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of friendly url localizations
	*/
	public java.util.List<FriendlyURLLocalization> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLLocalization> orderByComparator);

	/**
	* Returns an ordered range of all the friendly url localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url localizations
	* @param end the upper bound of the range of friendly url localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of friendly url localizations
	*/
	public java.util.List<FriendlyURLLocalization> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLLocalization> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the friendly url localizations from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of friendly url localizations.
	*
	* @return the number of friendly url localizations
	*/
	public int countAll();
}