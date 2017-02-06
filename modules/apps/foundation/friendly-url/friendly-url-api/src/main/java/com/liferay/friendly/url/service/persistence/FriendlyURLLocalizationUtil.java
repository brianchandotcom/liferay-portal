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

import com.liferay.friendly.url.model.FriendlyURLLocalization;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the friendly url localization service. This utility wraps {@link com.liferay.friendly.url.service.persistence.impl.FriendlyURLLocalizationPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLLocalizationPersistence
 * @see com.liferay.friendly.url.service.persistence.impl.FriendlyURLLocalizationPersistenceImpl
 * @generated
 */
@ProviderType
public class FriendlyURLLocalizationUtil {
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
		FriendlyURLLocalization friendlyURLLocalization) {
		getPersistence().clearCache(friendlyURLLocalization);
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
	public static List<FriendlyURLLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<FriendlyURLLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<FriendlyURLLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<FriendlyURLLocalization> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static FriendlyURLLocalization update(
		FriendlyURLLocalization friendlyURLLocalization) {
		return getPersistence().update(friendlyURLLocalization);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static FriendlyURLLocalization update(
		FriendlyURLLocalization friendlyURLLocalization,
		ServiceContext serviceContext) {
		return getPersistence().update(friendlyURLLocalization, serviceContext);
	}

	/**
	* Returns all the friendly url localizations where groupId = &#63; and friendlyURLId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @return the matching friendly url localizations
	*/
	public static List<FriendlyURLLocalization> findByG_F(long groupId,
		long friendlyURLId) {
		return getPersistence().findByG_F(groupId, friendlyURLId);
	}

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
	public static List<FriendlyURLLocalization> findByG_F(long groupId,
		long friendlyURLId, int start, int end) {
		return getPersistence().findByG_F(groupId, friendlyURLId, start, end);
	}

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
	public static List<FriendlyURLLocalization> findByG_F(long groupId,
		long friendlyURLId, int start, int end,
		OrderByComparator<FriendlyURLLocalization> orderByComparator) {
		return getPersistence()
				   .findByG_F(groupId, friendlyURLId, start, end,
			orderByComparator);
	}

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
	public static List<FriendlyURLLocalization> findByG_F(long groupId,
		long friendlyURLId, int start, int end,
		OrderByComparator<FriendlyURLLocalization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_F(groupId, friendlyURLId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first friendly url localization in the ordered set where groupId = &#63; and friendlyURLId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url localization
	* @throws NoSuchFriendlyURLLocalizationException if a matching friendly url localization could not be found
	*/
	public static FriendlyURLLocalization findByG_F_First(long groupId,
		long friendlyURLId,
		OrderByComparator<FriendlyURLLocalization> orderByComparator)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLLocalizationException {
		return getPersistence()
				   .findByG_F_First(groupId, friendlyURLId, orderByComparator);
	}

	/**
	* Returns the first friendly url localization in the ordered set where groupId = &#63; and friendlyURLId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url localization, or <code>null</code> if a matching friendly url localization could not be found
	*/
	public static FriendlyURLLocalization fetchByG_F_First(long groupId,
		long friendlyURLId,
		OrderByComparator<FriendlyURLLocalization> orderByComparator) {
		return getPersistence()
				   .fetchByG_F_First(groupId, friendlyURLId, orderByComparator);
	}

	/**
	* Returns the last friendly url localization in the ordered set where groupId = &#63; and friendlyURLId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url localization
	* @throws NoSuchFriendlyURLLocalizationException if a matching friendly url localization could not be found
	*/
	public static FriendlyURLLocalization findByG_F_Last(long groupId,
		long friendlyURLId,
		OrderByComparator<FriendlyURLLocalization> orderByComparator)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLLocalizationException {
		return getPersistence()
				   .findByG_F_Last(groupId, friendlyURLId, orderByComparator);
	}

	/**
	* Returns the last friendly url localization in the ordered set where groupId = &#63; and friendlyURLId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url localization, or <code>null</code> if a matching friendly url localization could not be found
	*/
	public static FriendlyURLLocalization fetchByG_F_Last(long groupId,
		long friendlyURLId,
		OrderByComparator<FriendlyURLLocalization> orderByComparator) {
		return getPersistence()
				   .fetchByG_F_Last(groupId, friendlyURLId, orderByComparator);
	}

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
	public static FriendlyURLLocalization[] findByG_F_PrevAndNext(
		long friendlyURLLocalizationId, long groupId, long friendlyURLId,
		OrderByComparator<FriendlyURLLocalization> orderByComparator)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLLocalizationException {
		return getPersistence()
				   .findByG_F_PrevAndNext(friendlyURLLocalizationId, groupId,
			friendlyURLId, orderByComparator);
	}

	/**
	* Removes all the friendly url localizations where groupId = &#63; and friendlyURLId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	*/
	public static void removeByG_F(long groupId, long friendlyURLId) {
		getPersistence().removeByG_F(groupId, friendlyURLId);
	}

	/**
	* Returns the number of friendly url localizations where groupId = &#63; and friendlyURLId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @return the number of matching friendly url localizations
	*/
	public static int countByG_F(long groupId, long friendlyURLId) {
		return getPersistence().countByG_F(groupId, friendlyURLId);
	}

	/**
	* Returns the friendly url localization where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63; or throws a {@link NoSuchFriendlyURLLocalizationException} if it could not be found.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param languageId the language ID
	* @return the matching friendly url localization
	* @throws NoSuchFriendlyURLLocalizationException if a matching friendly url localization could not be found
	*/
	public static FriendlyURLLocalization findByG_F_L(long groupId,
		long friendlyURLId, java.lang.String languageId)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLLocalizationException {
		return getPersistence().findByG_F_L(groupId, friendlyURLId, languageId);
	}

	/**
	* Returns the friendly url localization where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param languageId the language ID
	* @return the matching friendly url localization, or <code>null</code> if a matching friendly url localization could not be found
	*/
	public static FriendlyURLLocalization fetchByG_F_L(long groupId,
		long friendlyURLId, java.lang.String languageId) {
		return getPersistence().fetchByG_F_L(groupId, friendlyURLId, languageId);
	}

	/**
	* Returns the friendly url localization where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching friendly url localization, or <code>null</code> if a matching friendly url localization could not be found
	*/
	public static FriendlyURLLocalization fetchByG_F_L(long groupId,
		long friendlyURLId, java.lang.String languageId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_F_L(groupId, friendlyURLId, languageId,
			retrieveFromCache);
	}

	/**
	* Removes the friendly url localization where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param languageId the language ID
	* @return the friendly url localization that was removed
	*/
	public static FriendlyURLLocalization removeByG_F_L(long groupId,
		long friendlyURLId, java.lang.String languageId)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLLocalizationException {
		return getPersistence().removeByG_F_L(groupId, friendlyURLId, languageId);
	}

	/**
	* Returns the number of friendly url localizations where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63;.
	*
	* @param groupId the group ID
	* @param friendlyURLId the friendly url ID
	* @param languageId the language ID
	* @return the number of matching friendly url localizations
	*/
	public static int countByG_F_L(long groupId, long friendlyURLId,
		java.lang.String languageId) {
		return getPersistence().countByG_F_L(groupId, friendlyURLId, languageId);
	}

	/**
	* Returns the friendly url localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or throws a {@link NoSuchFriendlyURLLocalizationException} if it could not be found.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @return the matching friendly url localization
	* @throws NoSuchFriendlyURLLocalizationException if a matching friendly url localization could not be found
	*/
	public static FriendlyURLLocalization findByG_U_L(long groupId,
		java.lang.String urlTitle, java.lang.String languageId)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLLocalizationException {
		return getPersistence().findByG_U_L(groupId, urlTitle, languageId);
	}

	/**
	* Returns the friendly url localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @return the matching friendly url localization, or <code>null</code> if a matching friendly url localization could not be found
	*/
	public static FriendlyURLLocalization fetchByG_U_L(long groupId,
		java.lang.String urlTitle, java.lang.String languageId) {
		return getPersistence().fetchByG_U_L(groupId, urlTitle, languageId);
	}

	/**
	* Returns the friendly url localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching friendly url localization, or <code>null</code> if a matching friendly url localization could not be found
	*/
	public static FriendlyURLLocalization fetchByG_U_L(long groupId,
		java.lang.String urlTitle, java.lang.String languageId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_U_L(groupId, urlTitle, languageId,
			retrieveFromCache);
	}

	/**
	* Removes the friendly url localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @return the friendly url localization that was removed
	*/
	public static FriendlyURLLocalization removeByG_U_L(long groupId,
		java.lang.String urlTitle, java.lang.String languageId)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLLocalizationException {
		return getPersistence().removeByG_U_L(groupId, urlTitle, languageId);
	}

	/**
	* Returns the number of friendly url localizations where groupId = &#63; and urlTitle = &#63; and languageId = &#63;.
	*
	* @param groupId the group ID
	* @param urlTitle the url title
	* @param languageId the language ID
	* @return the number of matching friendly url localizations
	*/
	public static int countByG_U_L(long groupId, java.lang.String urlTitle,
		java.lang.String languageId) {
		return getPersistence().countByG_U_L(groupId, urlTitle, languageId);
	}

	/**
	* Caches the friendly url localization in the entity cache if it is enabled.
	*
	* @param friendlyURLLocalization the friendly url localization
	*/
	public static void cacheResult(
		FriendlyURLLocalization friendlyURLLocalization) {
		getPersistence().cacheResult(friendlyURLLocalization);
	}

	/**
	* Caches the friendly url localizations in the entity cache if it is enabled.
	*
	* @param friendlyURLLocalizations the friendly url localizations
	*/
	public static void cacheResult(
		List<FriendlyURLLocalization> friendlyURLLocalizations) {
		getPersistence().cacheResult(friendlyURLLocalizations);
	}

	/**
	* Creates a new friendly url localization with the primary key. Does not add the friendly url localization to the database.
	*
	* @param friendlyURLLocalizationId the primary key for the new friendly url localization
	* @return the new friendly url localization
	*/
	public static FriendlyURLLocalization create(long friendlyURLLocalizationId) {
		return getPersistence().create(friendlyURLLocalizationId);
	}

	/**
	* Removes the friendly url localization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLLocalizationId the primary key of the friendly url localization
	* @return the friendly url localization that was removed
	* @throws NoSuchFriendlyURLLocalizationException if a friendly url localization with the primary key could not be found
	*/
	public static FriendlyURLLocalization remove(long friendlyURLLocalizationId)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLLocalizationException {
		return getPersistence().remove(friendlyURLLocalizationId);
	}

	public static FriendlyURLLocalization updateImpl(
		FriendlyURLLocalization friendlyURLLocalization) {
		return getPersistence().updateImpl(friendlyURLLocalization);
	}

	/**
	* Returns the friendly url localization with the primary key or throws a {@link NoSuchFriendlyURLLocalizationException} if it could not be found.
	*
	* @param friendlyURLLocalizationId the primary key of the friendly url localization
	* @return the friendly url localization
	* @throws NoSuchFriendlyURLLocalizationException if a friendly url localization with the primary key could not be found
	*/
	public static FriendlyURLLocalization findByPrimaryKey(
		long friendlyURLLocalizationId)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLLocalizationException {
		return getPersistence().findByPrimaryKey(friendlyURLLocalizationId);
	}

	/**
	* Returns the friendly url localization with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param friendlyURLLocalizationId the primary key of the friendly url localization
	* @return the friendly url localization, or <code>null</code> if a friendly url localization with the primary key could not be found
	*/
	public static FriendlyURLLocalization fetchByPrimaryKey(
		long friendlyURLLocalizationId) {
		return getPersistence().fetchByPrimaryKey(friendlyURLLocalizationId);
	}

	public static java.util.Map<java.io.Serializable, FriendlyURLLocalization> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the friendly url localizations.
	*
	* @return the friendly url localizations
	*/
	public static List<FriendlyURLLocalization> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<FriendlyURLLocalization> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<FriendlyURLLocalization> findAll(int start, int end,
		OrderByComparator<FriendlyURLLocalization> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<FriendlyURLLocalization> findAll(int start, int end,
		OrderByComparator<FriendlyURLLocalization> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the friendly url localizations from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of friendly url localizations.
	*
	* @return the number of friendly url localizations
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static FriendlyURLLocalizationPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<FriendlyURLLocalizationPersistence, FriendlyURLLocalizationPersistence> _serviceTracker =
		ServiceTrackerFactory.open(FriendlyURLLocalizationPersistence.class);
}