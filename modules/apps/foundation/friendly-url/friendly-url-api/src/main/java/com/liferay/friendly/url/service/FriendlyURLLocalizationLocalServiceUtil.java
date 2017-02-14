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

package com.liferay.friendly.url.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for FriendlyURLLocalization. This utility wraps
 * {@link com.liferay.friendly.url.service.impl.FriendlyURLLocalizationLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLLocalizationLocalService
 * @see com.liferay.friendly.url.service.base.FriendlyURLLocalizationLocalServiceBaseImpl
 * @see com.liferay.friendly.url.service.impl.FriendlyURLLocalizationLocalServiceImpl
 * @generated
 */
@ProviderType
public class FriendlyURLLocalizationLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.friendly.url.service.impl.FriendlyURLLocalizationLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.friendly.url.model.FriendlyURLLocalization addFriendlyURLLocalization(
		com.liferay.friendly.url.model.FriendlyURLEntry friendlyURLEntry,
		java.lang.String urlTitle, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFriendlyURLLocalization(friendlyURLEntry, urlTitle,
			languageId);
	}

	/**
	* Adds the friendly url localization to the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLLocalization the friendly url localization
	* @return the friendly url localization that was added
	*/
	public static com.liferay.friendly.url.model.FriendlyURLLocalization addFriendlyURLLocalization(
		com.liferay.friendly.url.model.FriendlyURLLocalization friendlyURLLocalization) {
		return getService().addFriendlyURLLocalization(friendlyURLLocalization);
	}

	/**
	* Creates a new friendly url localization with the primary key. Does not add the friendly url localization to the database.
	*
	* @param friendlyURLLocalizationId the primary key for the new friendly url localization
	* @return the new friendly url localization
	*/
	public static com.liferay.friendly.url.model.FriendlyURLLocalization createFriendlyURLLocalization(
		long friendlyURLLocalizationId) {
		return getService()
				   .createFriendlyURLLocalization(friendlyURLLocalizationId);
	}

	public static com.liferay.friendly.url.model.FriendlyURLLocalization deleteFriendlyURLLocalization(
		com.liferay.friendly.url.model.FriendlyURLEntry friendlyURLEntry,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteFriendlyURLLocalization(friendlyURLEntry, languageId);
	}

	/**
	* Deletes the friendly url localization from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLLocalization the friendly url localization
	* @return the friendly url localization that was removed
	*/
	public static com.liferay.friendly.url.model.FriendlyURLLocalization deleteFriendlyURLLocalization(
		com.liferay.friendly.url.model.FriendlyURLLocalization friendlyURLLocalization) {
		return getService()
				   .deleteFriendlyURLLocalization(friendlyURLLocalization);
	}

	/**
	* Deletes the friendly url localization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLLocalizationId the primary key of the friendly url localization
	* @return the friendly url localization that was removed
	* @throws PortalException if a friendly url localization with the primary key could not be found
	*/
	public static com.liferay.friendly.url.model.FriendlyURLLocalization deleteFriendlyURLLocalization(
		long friendlyURLLocalizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteFriendlyURLLocalization(friendlyURLLocalizationId);
	}

	public static com.liferay.friendly.url.model.FriendlyURLLocalization fetchFriendlyURLLocalization(
		com.liferay.friendly.url.model.FriendlyURLEntry friendlyURLEntry,
		java.lang.String languageId) {
		return getService()
				   .fetchFriendlyURLLocalization(friendlyURLEntry, languageId);
	}

	public static com.liferay.friendly.url.model.FriendlyURLLocalization fetchFriendlyURLLocalization(
		long companyId, long groupId, long classNameId, long classPK,
		java.lang.String languageId) {
		return getService()
				   .fetchFriendlyURLLocalization(companyId, groupId,
			classNameId, classPK, languageId);
	}

	public static com.liferay.friendly.url.model.FriendlyURLLocalization fetchFriendlyURLLocalization(
		long friendlyURLLocalizationId) {
		return getService()
				   .fetchFriendlyURLLocalization(friendlyURLLocalizationId);
	}

	/**
	* Returns the friendly url localization with the primary key.
	*
	* @param friendlyURLLocalizationId the primary key of the friendly url localization
	* @return the friendly url localization
	* @throws PortalException if a friendly url localization with the primary key could not be found
	*/
	public static com.liferay.friendly.url.model.FriendlyURLLocalization getFriendlyURLLocalization(
		long friendlyURLLocalizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFriendlyURLLocalization(friendlyURLLocalizationId);
	}

	/**
	* Updates the friendly url localization in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLLocalization the friendly url localization
	* @return the friendly url localization that was updated
	*/
	public static com.liferay.friendly.url.model.FriendlyURLLocalization updateFriendlyURLLocalization(
		com.liferay.friendly.url.model.FriendlyURLLocalization friendlyURLLocalization) {
		return getService()
				   .updateFriendlyURLLocalization(friendlyURLLocalization);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static int getFriendlyURLLocalizationCount(
		com.liferay.friendly.url.model.FriendlyURLEntry friendlyURLEntry) {
		return getService().getFriendlyURLLocalizationCount(friendlyURLEntry);
	}

	/**
	* Returns the number of friendly url localizations.
	*
	* @return the number of friendly url localizations
	*/
	public static int getFriendlyURLLocalizationsCount() {
		return getService().getFriendlyURLLocalizationsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns a range of all the friendly url localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url localizations
	* @param end the upper bound of the range of friendly url localizations (not inclusive)
	* @return the range of friendly url localizations
	*/
	public static java.util.List<com.liferay.friendly.url.model.FriendlyURLLocalization> getFriendlyURLLocalizations(
		int start, int end) {
		return getService().getFriendlyURLLocalizations(start, end);
	}

	public static java.util.List<com.liferay.friendly.url.model.FriendlyURLLocalization> updateFriendlyURLLocalizations(
		com.liferay.friendly.url.model.FriendlyURLEntry friendlyURLEntry,
		java.util.Map<java.util.Locale, java.lang.String> urlTitleMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateFriendlyURLLocalizations(friendlyURLEntry, urlTitleMap);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static void deleteFriendlyURLLocalizations(
		com.liferay.friendly.url.model.FriendlyURLEntry friendlyURLEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFriendlyURLLocalizations(friendlyURLEntry);
	}

	public static FriendlyURLLocalizationLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<FriendlyURLLocalizationLocalService, FriendlyURLLocalizationLocalService> _serviceTracker =
		ServiceTrackerFactory.open(FriendlyURLLocalizationLocalService.class);
}