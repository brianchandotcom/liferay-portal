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

package com.liferay.asset.list.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.list.exception.NoSuchEntryLocalizationException;
import com.liferay.asset.list.model.AssetListEntryLocalization;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the asset list entry localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.asset.list.service.persistence.impl.AssetListEntryLocalizationPersistenceImpl
 * @see AssetListEntryLocalizationUtil
 * @generated
 */
@ProviderType
public interface AssetListEntryLocalizationPersistence extends BasePersistence<AssetListEntryLocalization> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetListEntryLocalizationUtil} to access the asset list entry localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the asset list entry localizations where assetListEntryId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @return the matching asset list entry localizations
	*/
	public java.util.List<AssetListEntryLocalization> findByAssetListEntryId(
		long assetListEntryId);

	/**
	* Returns a range of all the asset list entry localizations where assetListEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetListEntryId the asset list entry ID
	* @param start the lower bound of the range of asset list entry localizations
	* @param end the upper bound of the range of asset list entry localizations (not inclusive)
	* @return the range of matching asset list entry localizations
	*/
	public java.util.List<AssetListEntryLocalization> findByAssetListEntryId(
		long assetListEntryId, int start, int end);

	/**
	* Returns an ordered range of all the asset list entry localizations where assetListEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetListEntryId the asset list entry ID
	* @param start the lower bound of the range of asset list entry localizations
	* @param end the upper bound of the range of asset list entry localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset list entry localizations
	*/
	public java.util.List<AssetListEntryLocalization> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryLocalization> orderByComparator);

	/**
	* Returns an ordered range of all the asset list entry localizations where assetListEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param assetListEntryId the asset list entry ID
	* @param start the lower bound of the range of asset list entry localizations
	* @param end the upper bound of the range of asset list entry localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset list entry localizations
	*/
	public java.util.List<AssetListEntryLocalization> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryLocalization> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset list entry localization in the ordered set where assetListEntryId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list entry localization
	* @throws NoSuchEntryLocalizationException if a matching asset list entry localization could not be found
	*/
	public AssetListEntryLocalization findByAssetListEntryId_First(
		long assetListEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryLocalization> orderByComparator)
		throws NoSuchEntryLocalizationException;

	/**
	* Returns the first asset list entry localization in the ordered set where assetListEntryId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list entry localization, or <code>null</code> if a matching asset list entry localization could not be found
	*/
	public AssetListEntryLocalization fetchByAssetListEntryId_First(
		long assetListEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryLocalization> orderByComparator);

	/**
	* Returns the last asset list entry localization in the ordered set where assetListEntryId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list entry localization
	* @throws NoSuchEntryLocalizationException if a matching asset list entry localization could not be found
	*/
	public AssetListEntryLocalization findByAssetListEntryId_Last(
		long assetListEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryLocalization> orderByComparator)
		throws NoSuchEntryLocalizationException;

	/**
	* Returns the last asset list entry localization in the ordered set where assetListEntryId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list entry localization, or <code>null</code> if a matching asset list entry localization could not be found
	*/
	public AssetListEntryLocalization fetchByAssetListEntryId_Last(
		long assetListEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryLocalization> orderByComparator);

	/**
	* Returns the asset list entry localizations before and after the current asset list entry localization in the ordered set where assetListEntryId = &#63;.
	*
	* @param assetListEntryLocalizationId the primary key of the current asset list entry localization
	* @param assetListEntryId the asset list entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset list entry localization
	* @throws NoSuchEntryLocalizationException if a asset list entry localization with the primary key could not be found
	*/
	public AssetListEntryLocalization[] findByAssetListEntryId_PrevAndNext(
		long assetListEntryLocalizationId, long assetListEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryLocalization> orderByComparator)
		throws NoSuchEntryLocalizationException;

	/**
	* Removes all the asset list entry localizations where assetListEntryId = &#63; from the database.
	*
	* @param assetListEntryId the asset list entry ID
	*/
	public void removeByAssetListEntryId(long assetListEntryId);

	/**
	* Returns the number of asset list entry localizations where assetListEntryId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @return the number of matching asset list entry localizations
	*/
	public int countByAssetListEntryId(long assetListEntryId);

	/**
	* Returns the asset list entry localization where assetListEntryId = &#63; and languageId = &#63; or throws a {@link NoSuchEntryLocalizationException} if it could not be found.
	*
	* @param assetListEntryId the asset list entry ID
	* @param languageId the language ID
	* @return the matching asset list entry localization
	* @throws NoSuchEntryLocalizationException if a matching asset list entry localization could not be found
	*/
	public AssetListEntryLocalization findByAssetListEntryId_LanguageId(
		long assetListEntryId, String languageId)
		throws NoSuchEntryLocalizationException;

	/**
	* Returns the asset list entry localization where assetListEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param assetListEntryId the asset list entry ID
	* @param languageId the language ID
	* @return the matching asset list entry localization, or <code>null</code> if a matching asset list entry localization could not be found
	*/
	public AssetListEntryLocalization fetchByAssetListEntryId_LanguageId(
		long assetListEntryId, String languageId);

	/**
	* Returns the asset list entry localization where assetListEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param assetListEntryId the asset list entry ID
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset list entry localization, or <code>null</code> if a matching asset list entry localization could not be found
	*/
	public AssetListEntryLocalization fetchByAssetListEntryId_LanguageId(
		long assetListEntryId, String languageId, boolean retrieveFromCache);

	/**
	* Removes the asset list entry localization where assetListEntryId = &#63; and languageId = &#63; from the database.
	*
	* @param assetListEntryId the asset list entry ID
	* @param languageId the language ID
	* @return the asset list entry localization that was removed
	*/
	public AssetListEntryLocalization removeByAssetListEntryId_LanguageId(
		long assetListEntryId, String languageId)
		throws NoSuchEntryLocalizationException;

	/**
	* Returns the number of asset list entry localizations where assetListEntryId = &#63; and languageId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param languageId the language ID
	* @return the number of matching asset list entry localizations
	*/
	public int countByAssetListEntryId_LanguageId(long assetListEntryId,
		String languageId);

	/**
	* Returns all the asset list entry localizations where groupId = &#63; and title LIKE &#63;.
	*
	* @param groupId the group ID
	* @param title the title
	* @return the matching asset list entry localizations
	*/
	public java.util.List<AssetListEntryLocalization> findByG_LikeT(
		long groupId, String title);

	/**
	* Returns a range of all the asset list entry localizations where groupId = &#63; and title LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param title the title
	* @param start the lower bound of the range of asset list entry localizations
	* @param end the upper bound of the range of asset list entry localizations (not inclusive)
	* @return the range of matching asset list entry localizations
	*/
	public java.util.List<AssetListEntryLocalization> findByG_LikeT(
		long groupId, String title, int start, int end);

	/**
	* Returns an ordered range of all the asset list entry localizations where groupId = &#63; and title LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param title the title
	* @param start the lower bound of the range of asset list entry localizations
	* @param end the upper bound of the range of asset list entry localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset list entry localizations
	*/
	public java.util.List<AssetListEntryLocalization> findByG_LikeT(
		long groupId, String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryLocalization> orderByComparator);

	/**
	* Returns an ordered range of all the asset list entry localizations where groupId = &#63; and title LIKE &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param title the title
	* @param start the lower bound of the range of asset list entry localizations
	* @param end the upper bound of the range of asset list entry localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset list entry localizations
	*/
	public java.util.List<AssetListEntryLocalization> findByG_LikeT(
		long groupId, String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryLocalization> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset list entry localization in the ordered set where groupId = &#63; and title LIKE &#63;.
	*
	* @param groupId the group ID
	* @param title the title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list entry localization
	* @throws NoSuchEntryLocalizationException if a matching asset list entry localization could not be found
	*/
	public AssetListEntryLocalization findByG_LikeT_First(long groupId,
		String title,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryLocalization> orderByComparator)
		throws NoSuchEntryLocalizationException;

	/**
	* Returns the first asset list entry localization in the ordered set where groupId = &#63; and title LIKE &#63;.
	*
	* @param groupId the group ID
	* @param title the title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list entry localization, or <code>null</code> if a matching asset list entry localization could not be found
	*/
	public AssetListEntryLocalization fetchByG_LikeT_First(long groupId,
		String title,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryLocalization> orderByComparator);

	/**
	* Returns the last asset list entry localization in the ordered set where groupId = &#63; and title LIKE &#63;.
	*
	* @param groupId the group ID
	* @param title the title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list entry localization
	* @throws NoSuchEntryLocalizationException if a matching asset list entry localization could not be found
	*/
	public AssetListEntryLocalization findByG_LikeT_Last(long groupId,
		String title,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryLocalization> orderByComparator)
		throws NoSuchEntryLocalizationException;

	/**
	* Returns the last asset list entry localization in the ordered set where groupId = &#63; and title LIKE &#63;.
	*
	* @param groupId the group ID
	* @param title the title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list entry localization, or <code>null</code> if a matching asset list entry localization could not be found
	*/
	public AssetListEntryLocalization fetchByG_LikeT_Last(long groupId,
		String title,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryLocalization> orderByComparator);

	/**
	* Returns the asset list entry localizations before and after the current asset list entry localization in the ordered set where groupId = &#63; and title LIKE &#63;.
	*
	* @param assetListEntryLocalizationId the primary key of the current asset list entry localization
	* @param groupId the group ID
	* @param title the title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset list entry localization
	* @throws NoSuchEntryLocalizationException if a asset list entry localization with the primary key could not be found
	*/
	public AssetListEntryLocalization[] findByG_LikeT_PrevAndNext(
		long assetListEntryLocalizationId, long groupId, String title,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryLocalization> orderByComparator)
		throws NoSuchEntryLocalizationException;

	/**
	* Removes all the asset list entry localizations where groupId = &#63; and title LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param title the title
	*/
	public void removeByG_LikeT(long groupId, String title);

	/**
	* Returns the number of asset list entry localizations where groupId = &#63; and title LIKE &#63;.
	*
	* @param groupId the group ID
	* @param title the title
	* @return the number of matching asset list entry localizations
	*/
	public int countByG_LikeT(long groupId, String title);

	/**
	* Caches the asset list entry localization in the entity cache if it is enabled.
	*
	* @param assetListEntryLocalization the asset list entry localization
	*/
	public void cacheResult(
		AssetListEntryLocalization assetListEntryLocalization);

	/**
	* Caches the asset list entry localizations in the entity cache if it is enabled.
	*
	* @param assetListEntryLocalizations the asset list entry localizations
	*/
	public void cacheResult(
		java.util.List<AssetListEntryLocalization> assetListEntryLocalizations);

	/**
	* Creates a new asset list entry localization with the primary key. Does not add the asset list entry localization to the database.
	*
	* @param assetListEntryLocalizationId the primary key for the new asset list entry localization
	* @return the new asset list entry localization
	*/
	public AssetListEntryLocalization create(long assetListEntryLocalizationId);

	/**
	* Removes the asset list entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetListEntryLocalizationId the primary key of the asset list entry localization
	* @return the asset list entry localization that was removed
	* @throws NoSuchEntryLocalizationException if a asset list entry localization with the primary key could not be found
	*/
	public AssetListEntryLocalization remove(long assetListEntryLocalizationId)
		throws NoSuchEntryLocalizationException;

	public AssetListEntryLocalization updateImpl(
		AssetListEntryLocalization assetListEntryLocalization);

	/**
	* Returns the asset list entry localization with the primary key or throws a {@link NoSuchEntryLocalizationException} if it could not be found.
	*
	* @param assetListEntryLocalizationId the primary key of the asset list entry localization
	* @return the asset list entry localization
	* @throws NoSuchEntryLocalizationException if a asset list entry localization with the primary key could not be found
	*/
	public AssetListEntryLocalization findByPrimaryKey(
		long assetListEntryLocalizationId)
		throws NoSuchEntryLocalizationException;

	/**
	* Returns the asset list entry localization with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param assetListEntryLocalizationId the primary key of the asset list entry localization
	* @return the asset list entry localization, or <code>null</code> if a asset list entry localization with the primary key could not be found
	*/
	public AssetListEntryLocalization fetchByPrimaryKey(
		long assetListEntryLocalizationId);

	@Override
	public java.util.Map<java.io.Serializable, AssetListEntryLocalization> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the asset list entry localizations.
	*
	* @return the asset list entry localizations
	*/
	public java.util.List<AssetListEntryLocalization> findAll();

	/**
	* Returns a range of all the asset list entry localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset list entry localizations
	* @param end the upper bound of the range of asset list entry localizations (not inclusive)
	* @return the range of asset list entry localizations
	*/
	public java.util.List<AssetListEntryLocalization> findAll(int start, int end);

	/**
	* Returns an ordered range of all the asset list entry localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset list entry localizations
	* @param end the upper bound of the range of asset list entry localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset list entry localizations
	*/
	public java.util.List<AssetListEntryLocalization> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryLocalization> orderByComparator);

	/**
	* Returns an ordered range of all the asset list entry localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset list entry localizations
	* @param end the upper bound of the range of asset list entry localizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset list entry localizations
	*/
	public java.util.List<AssetListEntryLocalization> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntryLocalization> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the asset list entry localizations from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of asset list entry localizations.
	*
	* @return the number of asset list entry localizations
	*/
	public int countAll();
}