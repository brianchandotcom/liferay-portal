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
 * Provides the local service utility for FriendlyURLEntryLocalization. This utility wraps
 * {@link com.liferay.friendly.url.service.impl.FriendlyURLEntryLocalizationLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryLocalizationLocalService
 * @see com.liferay.friendly.url.service.base.FriendlyURLEntryLocalizationLocalServiceBaseImpl
 * @see com.liferay.friendly.url.service.impl.FriendlyURLEntryLocalizationLocalServiceImpl
 * @generated
 */
@ProviderType
public class FriendlyURLEntryLocalizationLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.friendly.url.service.impl.FriendlyURLEntryLocalizationLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the friendly url entry localization to the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLEntryLocalization the friendly url entry localization
	* @return the friendly url entry localization that was added
	*/
	public static com.liferay.friendly.url.model.FriendlyURLEntryLocalization addFriendlyURLEntryLocalization(
		com.liferay.friendly.url.model.FriendlyURLEntryLocalization friendlyURLEntryLocalization) {
		return getService()
				   .addFriendlyURLEntryLocalization(friendlyURLEntryLocalization);
	}

	public static com.liferay.friendly.url.model.FriendlyURLEntryLocalization addFriendlyURLEntryLocalization(
		long friendlyURLEntryId, java.lang.String urlTitle,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFriendlyURLEntryLocalization(friendlyURLEntryId,
			urlTitle, languageId);
	}

	/**
	* Creates a new friendly url entry localization with the primary key. Does not add the friendly url entry localization to the database.
	*
	* @param friendlyURLEntryLocalizationId the primary key for the new friendly url entry localization
	* @return the new friendly url entry localization
	*/
	public static com.liferay.friendly.url.model.FriendlyURLEntryLocalization createFriendlyURLEntryLocalization(
		long friendlyURLEntryLocalizationId) {
		return getService()
				   .createFriendlyURLEntryLocalization(friendlyURLEntryLocalizationId);
	}

	/**
	* Deletes the friendly url entry localization from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLEntryLocalization the friendly url entry localization
	* @return the friendly url entry localization that was removed
	*/
	public static com.liferay.friendly.url.model.FriendlyURLEntryLocalization deleteFriendlyURLEntryLocalization(
		com.liferay.friendly.url.model.FriendlyURLEntryLocalization friendlyURLEntryLocalization) {
		return getService()
				   .deleteFriendlyURLEntryLocalization(friendlyURLEntryLocalization);
	}

	public static com.liferay.friendly.url.model.FriendlyURLEntryLocalization deleteFriendlyURLEntryLocalization(
		long friendlyURLEntryId, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteFriendlyURLEntryLocalization(friendlyURLEntryId,
			languageId);
	}

	/**
	* Deletes the friendly url entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLEntryLocalizationId the primary key of the friendly url entry localization
	* @return the friendly url entry localization that was removed
	* @throws PortalException if a friendly url entry localization with the primary key could not be found
	*/
	public static com.liferay.friendly.url.model.FriendlyURLEntryLocalization deleteFriendlyURLEntryLocalization(
		long friendlyURLEntryLocalizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteFriendlyURLEntryLocalization(friendlyURLEntryLocalizationId);
	}

	public static com.liferay.friendly.url.model.FriendlyURLEntryLocalization fetchFriendlyURLEntryLocalization(
		long friendlyURLEntryId, java.lang.String languageId) {
		return getService()
				   .fetchFriendlyURLEntryLocalization(friendlyURLEntryId,
			languageId);
	}

	public static com.liferay.friendly.url.model.FriendlyURLEntryLocalization fetchFriendlyURLEntryLocalization(
		long friendlyURLEntryLocalizationId) {
		return getService()
				   .fetchFriendlyURLEntryLocalization(friendlyURLEntryLocalizationId);
	}

	public static com.liferay.friendly.url.model.FriendlyURLEntryLocalization fetchFriendlyURLEntryLocalization(
		long groupId, java.lang.String urlTitle, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .fetchFriendlyURLEntryLocalization(groupId, urlTitle,
			languageId);
	}

	public static com.liferay.friendly.url.model.FriendlyURLEntryLocalization fetchFriendlyURLEntryLocalization(
		long groupId, long classNameId, long classPK,
		java.lang.String languageId) {
		return getService()
				   .fetchFriendlyURLEntryLocalization(groupId, classNameId,
			classPK, languageId);
	}

	/**
	* Returns the friendly url entry localization with the primary key.
	*
	* @param friendlyURLEntryLocalizationId the primary key of the friendly url entry localization
	* @return the friendly url entry localization
	* @throws PortalException if a friendly url entry localization with the primary key could not be found
	*/
	public static com.liferay.friendly.url.model.FriendlyURLEntryLocalization getFriendlyURLEntryLocalization(
		long friendlyURLEntryLocalizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFriendlyURLEntryLocalization(friendlyURLEntryLocalizationId);
	}

	/**
	* Updates the friendly url entry localization in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLEntryLocalization the friendly url entry localization
	* @return the friendly url entry localization that was updated
	*/
	public static com.liferay.friendly.url.model.FriendlyURLEntryLocalization updateFriendlyURLEntryLocalization(
		com.liferay.friendly.url.model.FriendlyURLEntryLocalization friendlyURLEntryLocalization) {
		return getService()
				   .updateFriendlyURLEntryLocalization(friendlyURLEntryLocalization);
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

	/**
	* Returns the number of friendly url entry localizations.
	*
	* @return the number of friendly url entry localizations
	*/
	public static int getFriendlyURLEntryLocalizationsCount() {
		return getService().getFriendlyURLEntryLocalizationsCount();
	}

	public static int getFriendlyURLEntryLocalizationsCount(
		long friendlyURLEntryId) {
		return getService()
				   .getFriendlyURLEntryLocalizationsCount(friendlyURLEntryId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the friendly url entry localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url entry localizations
	* @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	* @return the range of friendly url entry localizations
	*/
	public static java.util.List<com.liferay.friendly.url.model.FriendlyURLEntryLocalization> getFriendlyURLEntryLocalizations(
		int start, int end) {
		return getService().getFriendlyURLEntryLocalizations(start, end);
	}

	public static java.util.List<com.liferay.friendly.url.model.FriendlyURLEntryLocalization> getFriendlyURLEntryLocalizations(
		long friendlyURLEntryId) {
		return getService().getFriendlyURLEntryLocalizations(friendlyURLEntryId);
	}

	public static java.util.List<com.liferay.friendly.url.model.FriendlyURLEntryLocalization> updateFriendlyURLEntryLocalizations(
		long friendlyURLEntryId,
		java.util.Map<java.util.Locale, java.lang.String> urlTitleMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateFriendlyURLEntryLocalizations(friendlyURLEntryId,
			urlTitleMap);
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

	public static void deleteFriendlyURLEntryLocalizations(
		long friendlyURLEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFriendlyURLEntryLocalizations(friendlyURLEntryId);
	}

	public static FriendlyURLEntryLocalizationLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<FriendlyURLEntryLocalizationLocalService, FriendlyURLEntryLocalizationLocalService> _serviceTracker =
		ServiceTrackerFactory.open(FriendlyURLEntryLocalizationLocalService.class);
}