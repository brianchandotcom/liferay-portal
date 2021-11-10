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

package com.liferay.remote.app.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.remote.app.model.RemoteAppEntryLocalization;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the remote app entry localization service. This utility wraps <code>com.liferay.remote.app.service.persistence.impl.RemoteAppEntryLocalizationPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RemoteAppEntryLocalizationPersistence
 * @generated
 */
public class RemoteAppEntryLocalizationUtil {

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
		RemoteAppEntryLocalization remoteAppEntryLocalization) {

		getPersistence().clearCache(remoteAppEntryLocalization);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, RemoteAppEntryLocalization>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<RemoteAppEntryLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<RemoteAppEntryLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<RemoteAppEntryLocalization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<RemoteAppEntryLocalization> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static RemoteAppEntryLocalization update(
		RemoteAppEntryLocalization remoteAppEntryLocalization) {

		return getPersistence().update(remoteAppEntryLocalization);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static RemoteAppEntryLocalization update(
		RemoteAppEntryLocalization remoteAppEntryLocalization,
		ServiceContext serviceContext) {

		return getPersistence().update(
			remoteAppEntryLocalization, serviceContext);
	}

	/**
	 * Returns all the remote app entry localizations where remoteAppEntryId = &#63;.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @return the matching remote app entry localizations
	 */
	public static List<RemoteAppEntryLocalization> findByRemoteAppEntryId(
		long remoteAppEntryId) {

		return getPersistence().findByRemoteAppEntryId(remoteAppEntryId);
	}

	/**
	 * Returns a range of all the remote app entry localizations where remoteAppEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param start the lower bound of the range of remote app entry localizations
	 * @param end the upper bound of the range of remote app entry localizations (not inclusive)
	 * @return the range of matching remote app entry localizations
	 */
	public static List<RemoteAppEntryLocalization> findByRemoteAppEntryId(
		long remoteAppEntryId, int start, int end) {

		return getPersistence().findByRemoteAppEntryId(
			remoteAppEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the remote app entry localizations where remoteAppEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param start the lower bound of the range of remote app entry localizations
	 * @param end the upper bound of the range of remote app entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote app entry localizations
	 */
	public static List<RemoteAppEntryLocalization> findByRemoteAppEntryId(
		long remoteAppEntryId, int start, int end,
		OrderByComparator<RemoteAppEntryLocalization> orderByComparator) {

		return getPersistence().findByRemoteAppEntryId(
			remoteAppEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the remote app entry localizations where remoteAppEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param start the lower bound of the range of remote app entry localizations
	 * @param end the upper bound of the range of remote app entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote app entry localizations
	 */
	public static List<RemoteAppEntryLocalization> findByRemoteAppEntryId(
		long remoteAppEntryId, int start, int end,
		OrderByComparator<RemoteAppEntryLocalization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByRemoteAppEntryId(
			remoteAppEntryId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first remote app entry localization in the ordered set where remoteAppEntryId = &#63;.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote app entry localization
	 * @throws NoSuchRemoteAppEntryLocalizationException if a matching remote app entry localization could not be found
	 */
	public static RemoteAppEntryLocalization findByRemoteAppEntryId_First(
			long remoteAppEntryId,
			OrderByComparator<RemoteAppEntryLocalization> orderByComparator)
		throws com.liferay.remote.app.exception.
			NoSuchRemoteAppEntryLocalizationException {

		return getPersistence().findByRemoteAppEntryId_First(
			remoteAppEntryId, orderByComparator);
	}

	/**
	 * Returns the first remote app entry localization in the ordered set where remoteAppEntryId = &#63;.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote app entry localization, or <code>null</code> if a matching remote app entry localization could not be found
	 */
	public static RemoteAppEntryLocalization fetchByRemoteAppEntryId_First(
		long remoteAppEntryId,
		OrderByComparator<RemoteAppEntryLocalization> orderByComparator) {

		return getPersistence().fetchByRemoteAppEntryId_First(
			remoteAppEntryId, orderByComparator);
	}

	/**
	 * Returns the last remote app entry localization in the ordered set where remoteAppEntryId = &#63;.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote app entry localization
	 * @throws NoSuchRemoteAppEntryLocalizationException if a matching remote app entry localization could not be found
	 */
	public static RemoteAppEntryLocalization findByRemoteAppEntryId_Last(
			long remoteAppEntryId,
			OrderByComparator<RemoteAppEntryLocalization> orderByComparator)
		throws com.liferay.remote.app.exception.
			NoSuchRemoteAppEntryLocalizationException {

		return getPersistence().findByRemoteAppEntryId_Last(
			remoteAppEntryId, orderByComparator);
	}

	/**
	 * Returns the last remote app entry localization in the ordered set where remoteAppEntryId = &#63;.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote app entry localization, or <code>null</code> if a matching remote app entry localization could not be found
	 */
	public static RemoteAppEntryLocalization fetchByRemoteAppEntryId_Last(
		long remoteAppEntryId,
		OrderByComparator<RemoteAppEntryLocalization> orderByComparator) {

		return getPersistence().fetchByRemoteAppEntryId_Last(
			remoteAppEntryId, orderByComparator);
	}

	/**
	 * Returns the remote app entry localizations before and after the current remote app entry localization in the ordered set where remoteAppEntryId = &#63;.
	 *
	 * @param remoteAppEntryLocalizationId the primary key of the current remote app entry localization
	 * @param remoteAppEntryId the remote app entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote app entry localization
	 * @throws NoSuchRemoteAppEntryLocalizationException if a remote app entry localization with the primary key could not be found
	 */
	public static RemoteAppEntryLocalization[]
			findByRemoteAppEntryId_PrevAndNext(
				long remoteAppEntryLocalizationId, long remoteAppEntryId,
				OrderByComparator<RemoteAppEntryLocalization> orderByComparator)
		throws com.liferay.remote.app.exception.
			NoSuchRemoteAppEntryLocalizationException {

		return getPersistence().findByRemoteAppEntryId_PrevAndNext(
			remoteAppEntryLocalizationId, remoteAppEntryId, orderByComparator);
	}

	/**
	 * Removes all the remote app entry localizations where remoteAppEntryId = &#63; from the database.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 */
	public static void removeByRemoteAppEntryId(long remoteAppEntryId) {
		getPersistence().removeByRemoteAppEntryId(remoteAppEntryId);
	}

	/**
	 * Returns the number of remote app entry localizations where remoteAppEntryId = &#63;.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @return the number of matching remote app entry localizations
	 */
	public static int countByRemoteAppEntryId(long remoteAppEntryId) {
		return getPersistence().countByRemoteAppEntryId(remoteAppEntryId);
	}

	/**
	 * Returns the remote app entry localization where remoteAppEntryId = &#63; and languageId = &#63; or throws a <code>NoSuchRemoteAppEntryLocalizationException</code> if it could not be found.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param languageId the language ID
	 * @return the matching remote app entry localization
	 * @throws NoSuchRemoteAppEntryLocalizationException if a matching remote app entry localization could not be found
	 */
	public static RemoteAppEntryLocalization findByRemoteAppEntryId_LanguageId(
			long remoteAppEntryId, String languageId)
		throws com.liferay.remote.app.exception.
			NoSuchRemoteAppEntryLocalizationException {

		return getPersistence().findByRemoteAppEntryId_LanguageId(
			remoteAppEntryId, languageId);
	}

	/**
	 * Returns the remote app entry localization where remoteAppEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param languageId the language ID
	 * @return the matching remote app entry localization, or <code>null</code> if a matching remote app entry localization could not be found
	 */
	public static RemoteAppEntryLocalization fetchByRemoteAppEntryId_LanguageId(
		long remoteAppEntryId, String languageId) {

		return getPersistence().fetchByRemoteAppEntryId_LanguageId(
			remoteAppEntryId, languageId);
	}

	/**
	 * Returns the remote app entry localization where remoteAppEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching remote app entry localization, or <code>null</code> if a matching remote app entry localization could not be found
	 */
	public static RemoteAppEntryLocalization fetchByRemoteAppEntryId_LanguageId(
		long remoteAppEntryId, String languageId, boolean useFinderCache) {

		return getPersistence().fetchByRemoteAppEntryId_LanguageId(
			remoteAppEntryId, languageId, useFinderCache);
	}

	/**
	 * Removes the remote app entry localization where remoteAppEntryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param languageId the language ID
	 * @return the remote app entry localization that was removed
	 */
	public static RemoteAppEntryLocalization
			removeByRemoteAppEntryId_LanguageId(
				long remoteAppEntryId, String languageId)
		throws com.liferay.remote.app.exception.
			NoSuchRemoteAppEntryLocalizationException {

		return getPersistence().removeByRemoteAppEntryId_LanguageId(
			remoteAppEntryId, languageId);
	}

	/**
	 * Returns the number of remote app entry localizations where remoteAppEntryId = &#63; and languageId = &#63;.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param languageId the language ID
	 * @return the number of matching remote app entry localizations
	 */
	public static int countByRemoteAppEntryId_LanguageId(
		long remoteAppEntryId, String languageId) {

		return getPersistence().countByRemoteAppEntryId_LanguageId(
			remoteAppEntryId, languageId);
	}

	/**
	 * Caches the remote app entry localization in the entity cache if it is enabled.
	 *
	 * @param remoteAppEntryLocalization the remote app entry localization
	 */
	public static void cacheResult(
		RemoteAppEntryLocalization remoteAppEntryLocalization) {

		getPersistence().cacheResult(remoteAppEntryLocalization);
	}

	/**
	 * Caches the remote app entry localizations in the entity cache if it is enabled.
	 *
	 * @param remoteAppEntryLocalizations the remote app entry localizations
	 */
	public static void cacheResult(
		List<RemoteAppEntryLocalization> remoteAppEntryLocalizations) {

		getPersistence().cacheResult(remoteAppEntryLocalizations);
	}

	/**
	 * Creates a new remote app entry localization with the primary key. Does not add the remote app entry localization to the database.
	 *
	 * @param remoteAppEntryLocalizationId the primary key for the new remote app entry localization
	 * @return the new remote app entry localization
	 */
	public static RemoteAppEntryLocalization create(
		long remoteAppEntryLocalizationId) {

		return getPersistence().create(remoteAppEntryLocalizationId);
	}

	/**
	 * Removes the remote app entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param remoteAppEntryLocalizationId the primary key of the remote app entry localization
	 * @return the remote app entry localization that was removed
	 * @throws NoSuchRemoteAppEntryLocalizationException if a remote app entry localization with the primary key could not be found
	 */
	public static RemoteAppEntryLocalization remove(
			long remoteAppEntryLocalizationId)
		throws com.liferay.remote.app.exception.
			NoSuchRemoteAppEntryLocalizationException {

		return getPersistence().remove(remoteAppEntryLocalizationId);
	}

	public static RemoteAppEntryLocalization updateImpl(
		RemoteAppEntryLocalization remoteAppEntryLocalization) {

		return getPersistence().updateImpl(remoteAppEntryLocalization);
	}

	/**
	 * Returns the remote app entry localization with the primary key or throws a <code>NoSuchRemoteAppEntryLocalizationException</code> if it could not be found.
	 *
	 * @param remoteAppEntryLocalizationId the primary key of the remote app entry localization
	 * @return the remote app entry localization
	 * @throws NoSuchRemoteAppEntryLocalizationException if a remote app entry localization with the primary key could not be found
	 */
	public static RemoteAppEntryLocalization findByPrimaryKey(
			long remoteAppEntryLocalizationId)
		throws com.liferay.remote.app.exception.
			NoSuchRemoteAppEntryLocalizationException {

		return getPersistence().findByPrimaryKey(remoteAppEntryLocalizationId);
	}

	/**
	 * Returns the remote app entry localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param remoteAppEntryLocalizationId the primary key of the remote app entry localization
	 * @return the remote app entry localization, or <code>null</code> if a remote app entry localization with the primary key could not be found
	 */
	public static RemoteAppEntryLocalization fetchByPrimaryKey(
		long remoteAppEntryLocalizationId) {

		return getPersistence().fetchByPrimaryKey(remoteAppEntryLocalizationId);
	}

	/**
	 * Returns all the remote app entry localizations.
	 *
	 * @return the remote app entry localizations
	 */
	public static List<RemoteAppEntryLocalization> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the remote app entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote app entry localizations
	 * @param end the upper bound of the range of remote app entry localizations (not inclusive)
	 * @return the range of remote app entry localizations
	 */
	public static List<RemoteAppEntryLocalization> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the remote app entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote app entry localizations
	 * @param end the upper bound of the range of remote app entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of remote app entry localizations
	 */
	public static List<RemoteAppEntryLocalization> findAll(
		int start, int end,
		OrderByComparator<RemoteAppEntryLocalization> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the remote app entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote app entry localizations
	 * @param end the upper bound of the range of remote app entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of remote app entry localizations
	 */
	public static List<RemoteAppEntryLocalization> findAll(
		int start, int end,
		OrderByComparator<RemoteAppEntryLocalization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the remote app entry localizations from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of remote app entry localizations.
	 *
	 * @return the number of remote app entry localizations
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static RemoteAppEntryLocalizationPersistence getPersistence() {
		return _persistence;
	}

	private static volatile RemoteAppEntryLocalizationPersistence _persistence;

}