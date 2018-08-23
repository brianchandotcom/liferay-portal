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

import com.liferay.asset.list.model.AssetListEntryLocalization;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the asset list entry localization service. This utility wraps {@link com.liferay.asset.list.service.persistence.impl.AssetListEntryLocalizationPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryLocalizationPersistence
 * @see com.liferay.asset.list.service.persistence.impl.AssetListEntryLocalizationPersistenceImpl
 * @generated
 */
@ProviderType
public class AssetListEntryLocalizationUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		AssetListEntryLocalization assetListEntryLocalization) {
		getPersistence().clearCache(assetListEntryLocalization);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetListEntryLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetListEntryLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetListEntryLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetListEntryLocalization> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AssetListEntryLocalization update(
		AssetListEntryLocalization assetListEntryLocalization) {
		return getPersistence().update(assetListEntryLocalization);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AssetListEntryLocalization update(
		AssetListEntryLocalization assetListEntryLocalization,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(assetListEntryLocalization, serviceContext);
	}

	/**
	* Returns all the asset list entry localizations where assetListEntryId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @return the matching asset list entry localizations
	*/
	public static List<AssetListEntryLocalization> findByAssetListEntryId(
		long assetListEntryId) {
		return getPersistence().findByAssetListEntryId(assetListEntryId);
	}

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
	public static List<AssetListEntryLocalization> findByAssetListEntryId(
		long assetListEntryId, int start, int end) {
		return getPersistence()
				   .findByAssetListEntryId(assetListEntryId, start, end);
	}

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
	public static List<AssetListEntryLocalization> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetListEntryLocalization> orderByComparator) {
		return getPersistence()
				   .findByAssetListEntryId(assetListEntryId, start, end,
			orderByComparator);
	}

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
	public static List<AssetListEntryLocalization> findByAssetListEntryId(
		long assetListEntryId, int start, int end,
		OrderByComparator<AssetListEntryLocalization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByAssetListEntryId(assetListEntryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset list entry localization in the ordered set where assetListEntryId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list entry localization
	* @throws NoSuchEntryLocalizationException if a matching asset list entry localization could not be found
	*/
	public static AssetListEntryLocalization findByAssetListEntryId_First(
		long assetListEntryId,
		OrderByComparator<AssetListEntryLocalization> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchEntryLocalizationException {
		return getPersistence()
				   .findByAssetListEntryId_First(assetListEntryId,
			orderByComparator);
	}

	/**
	* Returns the first asset list entry localization in the ordered set where assetListEntryId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list entry localization, or <code>null</code> if a matching asset list entry localization could not be found
	*/
	public static AssetListEntryLocalization fetchByAssetListEntryId_First(
		long assetListEntryId,
		OrderByComparator<AssetListEntryLocalization> orderByComparator) {
		return getPersistence()
				   .fetchByAssetListEntryId_First(assetListEntryId,
			orderByComparator);
	}

	/**
	* Returns the last asset list entry localization in the ordered set where assetListEntryId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list entry localization
	* @throws NoSuchEntryLocalizationException if a matching asset list entry localization could not be found
	*/
	public static AssetListEntryLocalization findByAssetListEntryId_Last(
		long assetListEntryId,
		OrderByComparator<AssetListEntryLocalization> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchEntryLocalizationException {
		return getPersistence()
				   .findByAssetListEntryId_Last(assetListEntryId,
			orderByComparator);
	}

	/**
	* Returns the last asset list entry localization in the ordered set where assetListEntryId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list entry localization, or <code>null</code> if a matching asset list entry localization could not be found
	*/
	public static AssetListEntryLocalization fetchByAssetListEntryId_Last(
		long assetListEntryId,
		OrderByComparator<AssetListEntryLocalization> orderByComparator) {
		return getPersistence()
				   .fetchByAssetListEntryId_Last(assetListEntryId,
			orderByComparator);
	}

	/**
	* Returns the asset list entry localizations before and after the current asset list entry localization in the ordered set where assetListEntryId = &#63;.
	*
	* @param assetListEntryLocalizationId the primary key of the current asset list entry localization
	* @param assetListEntryId the asset list entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset list entry localization
	* @throws NoSuchEntryLocalizationException if a asset list entry localization with the primary key could not be found
	*/
	public static AssetListEntryLocalization[] findByAssetListEntryId_PrevAndNext(
		long assetListEntryLocalizationId, long assetListEntryId,
		OrderByComparator<AssetListEntryLocalization> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchEntryLocalizationException {
		return getPersistence()
				   .findByAssetListEntryId_PrevAndNext(assetListEntryLocalizationId,
			assetListEntryId, orderByComparator);
	}

	/**
	* Removes all the asset list entry localizations where assetListEntryId = &#63; from the database.
	*
	* @param assetListEntryId the asset list entry ID
	*/
	public static void removeByAssetListEntryId(long assetListEntryId) {
		getPersistence().removeByAssetListEntryId(assetListEntryId);
	}

	/**
	* Returns the number of asset list entry localizations where assetListEntryId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @return the number of matching asset list entry localizations
	*/
	public static int countByAssetListEntryId(long assetListEntryId) {
		return getPersistence().countByAssetListEntryId(assetListEntryId);
	}

	/**
	* Returns the asset list entry localization where assetListEntryId = &#63; and languageId = &#63; or throws a {@link NoSuchEntryLocalizationException} if it could not be found.
	*
	* @param assetListEntryId the asset list entry ID
	* @param languageId the language ID
	* @return the matching asset list entry localization
	* @throws NoSuchEntryLocalizationException if a matching asset list entry localization could not be found
	*/
	public static AssetListEntryLocalization findByAssetListEntryId_LanguageId(
		long assetListEntryId, String languageId)
		throws com.liferay.asset.list.exception.NoSuchEntryLocalizationException {
		return getPersistence()
				   .findByAssetListEntryId_LanguageId(assetListEntryId,
			languageId);
	}

	/**
	* Returns the asset list entry localization where assetListEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param assetListEntryId the asset list entry ID
	* @param languageId the language ID
	* @return the matching asset list entry localization, or <code>null</code> if a matching asset list entry localization could not be found
	*/
	public static AssetListEntryLocalization fetchByAssetListEntryId_LanguageId(
		long assetListEntryId, String languageId) {
		return getPersistence()
				   .fetchByAssetListEntryId_LanguageId(assetListEntryId,
			languageId);
	}

	/**
	* Returns the asset list entry localization where assetListEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param assetListEntryId the asset list entry ID
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset list entry localization, or <code>null</code> if a matching asset list entry localization could not be found
	*/
	public static AssetListEntryLocalization fetchByAssetListEntryId_LanguageId(
		long assetListEntryId, String languageId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByAssetListEntryId_LanguageId(assetListEntryId,
			languageId, retrieveFromCache);
	}

	/**
	* Removes the asset list entry localization where assetListEntryId = &#63; and languageId = &#63; from the database.
	*
	* @param assetListEntryId the asset list entry ID
	* @param languageId the language ID
	* @return the asset list entry localization that was removed
	*/
	public static AssetListEntryLocalization removeByAssetListEntryId_LanguageId(
		long assetListEntryId, String languageId)
		throws com.liferay.asset.list.exception.NoSuchEntryLocalizationException {
		return getPersistence()
				   .removeByAssetListEntryId_LanguageId(assetListEntryId,
			languageId);
	}

	/**
	* Returns the number of asset list entry localizations where assetListEntryId = &#63; and languageId = &#63;.
	*
	* @param assetListEntryId the asset list entry ID
	* @param languageId the language ID
	* @return the number of matching asset list entry localizations
	*/
	public static int countByAssetListEntryId_LanguageId(
		long assetListEntryId, String languageId) {
		return getPersistence()
				   .countByAssetListEntryId_LanguageId(assetListEntryId,
			languageId);
	}

	/**
	* Returns all the asset list entry localizations where groupId = &#63; and title LIKE &#63;.
	*
	* @param groupId the group ID
	* @param title the title
	* @return the matching asset list entry localizations
	*/
	public static List<AssetListEntryLocalization> findByG_LikeT(long groupId,
		String title) {
		return getPersistence().findByG_LikeT(groupId, title);
	}

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
	public static List<AssetListEntryLocalization> findByG_LikeT(long groupId,
		String title, int start, int end) {
		return getPersistence().findByG_LikeT(groupId, title, start, end);
	}

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
	public static List<AssetListEntryLocalization> findByG_LikeT(long groupId,
		String title, int start, int end,
		OrderByComparator<AssetListEntryLocalization> orderByComparator) {
		return getPersistence()
				   .findByG_LikeT(groupId, title, start, end, orderByComparator);
	}

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
	public static List<AssetListEntryLocalization> findByG_LikeT(long groupId,
		String title, int start, int end,
		OrderByComparator<AssetListEntryLocalization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_LikeT(groupId, title, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset list entry localization in the ordered set where groupId = &#63; and title LIKE &#63;.
	*
	* @param groupId the group ID
	* @param title the title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list entry localization
	* @throws NoSuchEntryLocalizationException if a matching asset list entry localization could not be found
	*/
	public static AssetListEntryLocalization findByG_LikeT_First(long groupId,
		String title,
		OrderByComparator<AssetListEntryLocalization> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchEntryLocalizationException {
		return getPersistence()
				   .findByG_LikeT_First(groupId, title, orderByComparator);
	}

	/**
	* Returns the first asset list entry localization in the ordered set where groupId = &#63; and title LIKE &#63;.
	*
	* @param groupId the group ID
	* @param title the title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list entry localization, or <code>null</code> if a matching asset list entry localization could not be found
	*/
	public static AssetListEntryLocalization fetchByG_LikeT_First(
		long groupId, String title,
		OrderByComparator<AssetListEntryLocalization> orderByComparator) {
		return getPersistence()
				   .fetchByG_LikeT_First(groupId, title, orderByComparator);
	}

	/**
	* Returns the last asset list entry localization in the ordered set where groupId = &#63; and title LIKE &#63;.
	*
	* @param groupId the group ID
	* @param title the title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list entry localization
	* @throws NoSuchEntryLocalizationException if a matching asset list entry localization could not be found
	*/
	public static AssetListEntryLocalization findByG_LikeT_Last(long groupId,
		String title,
		OrderByComparator<AssetListEntryLocalization> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchEntryLocalizationException {
		return getPersistence()
				   .findByG_LikeT_Last(groupId, title, orderByComparator);
	}

	/**
	* Returns the last asset list entry localization in the ordered set where groupId = &#63; and title LIKE &#63;.
	*
	* @param groupId the group ID
	* @param title the title
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list entry localization, or <code>null</code> if a matching asset list entry localization could not be found
	*/
	public static AssetListEntryLocalization fetchByG_LikeT_Last(long groupId,
		String title,
		OrderByComparator<AssetListEntryLocalization> orderByComparator) {
		return getPersistence()
				   .fetchByG_LikeT_Last(groupId, title, orderByComparator);
	}

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
	public static AssetListEntryLocalization[] findByG_LikeT_PrevAndNext(
		long assetListEntryLocalizationId, long groupId, String title,
		OrderByComparator<AssetListEntryLocalization> orderByComparator)
		throws com.liferay.asset.list.exception.NoSuchEntryLocalizationException {
		return getPersistence()
				   .findByG_LikeT_PrevAndNext(assetListEntryLocalizationId,
			groupId, title, orderByComparator);
	}

	/**
	* Removes all the asset list entry localizations where groupId = &#63; and title LIKE &#63; from the database.
	*
	* @param groupId the group ID
	* @param title the title
	*/
	public static void removeByG_LikeT(long groupId, String title) {
		getPersistence().removeByG_LikeT(groupId, title);
	}

	/**
	* Returns the number of asset list entry localizations where groupId = &#63; and title LIKE &#63;.
	*
	* @param groupId the group ID
	* @param title the title
	* @return the number of matching asset list entry localizations
	*/
	public static int countByG_LikeT(long groupId, String title) {
		return getPersistence().countByG_LikeT(groupId, title);
	}

	/**
	* Caches the asset list entry localization in the entity cache if it is enabled.
	*
	* @param assetListEntryLocalization the asset list entry localization
	*/
	public static void cacheResult(
		AssetListEntryLocalization assetListEntryLocalization) {
		getPersistence().cacheResult(assetListEntryLocalization);
	}

	/**
	* Caches the asset list entry localizations in the entity cache if it is enabled.
	*
	* @param assetListEntryLocalizations the asset list entry localizations
	*/
	public static void cacheResult(
		List<AssetListEntryLocalization> assetListEntryLocalizations) {
		getPersistence().cacheResult(assetListEntryLocalizations);
	}

	/**
	* Creates a new asset list entry localization with the primary key. Does not add the asset list entry localization to the database.
	*
	* @param assetListEntryLocalizationId the primary key for the new asset list entry localization
	* @return the new asset list entry localization
	*/
	public static AssetListEntryLocalization create(
		long assetListEntryLocalizationId) {
		return getPersistence().create(assetListEntryLocalizationId);
	}

	/**
	* Removes the asset list entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetListEntryLocalizationId the primary key of the asset list entry localization
	* @return the asset list entry localization that was removed
	* @throws NoSuchEntryLocalizationException if a asset list entry localization with the primary key could not be found
	*/
	public static AssetListEntryLocalization remove(
		long assetListEntryLocalizationId)
		throws com.liferay.asset.list.exception.NoSuchEntryLocalizationException {
		return getPersistence().remove(assetListEntryLocalizationId);
	}

	public static AssetListEntryLocalization updateImpl(
		AssetListEntryLocalization assetListEntryLocalization) {
		return getPersistence().updateImpl(assetListEntryLocalization);
	}

	/**
	* Returns the asset list entry localization with the primary key or throws a {@link NoSuchEntryLocalizationException} if it could not be found.
	*
	* @param assetListEntryLocalizationId the primary key of the asset list entry localization
	* @return the asset list entry localization
	* @throws NoSuchEntryLocalizationException if a asset list entry localization with the primary key could not be found
	*/
	public static AssetListEntryLocalization findByPrimaryKey(
		long assetListEntryLocalizationId)
		throws com.liferay.asset.list.exception.NoSuchEntryLocalizationException {
		return getPersistence().findByPrimaryKey(assetListEntryLocalizationId);
	}

	/**
	* Returns the asset list entry localization with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param assetListEntryLocalizationId the primary key of the asset list entry localization
	* @return the asset list entry localization, or <code>null</code> if a asset list entry localization with the primary key could not be found
	*/
	public static AssetListEntryLocalization fetchByPrimaryKey(
		long assetListEntryLocalizationId) {
		return getPersistence().fetchByPrimaryKey(assetListEntryLocalizationId);
	}

	public static java.util.Map<java.io.Serializable, AssetListEntryLocalization> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the asset list entry localizations.
	*
	* @return the asset list entry localizations
	*/
	public static List<AssetListEntryLocalization> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<AssetListEntryLocalization> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<AssetListEntryLocalization> findAll(int start, int end,
		OrderByComparator<AssetListEntryLocalization> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<AssetListEntryLocalization> findAll(int start, int end,
		OrderByComparator<AssetListEntryLocalization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the asset list entry localizations from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset list entry localizations.
	*
	* @return the number of asset list entry localizations
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AssetListEntryLocalizationPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AssetListEntryLocalizationPersistence, AssetListEntryLocalizationPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AssetListEntryLocalizationPersistence.class);

		ServiceTracker<AssetListEntryLocalizationPersistence, AssetListEntryLocalizationPersistence> serviceTracker =
			new ServiceTracker<AssetListEntryLocalizationPersistence, AssetListEntryLocalizationPersistence>(bundle.getBundleContext(),
				AssetListEntryLocalizationPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}