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
 * Provides the local service utility for FriendlyURL. This utility wraps
 * {@link com.liferay.friendly.url.service.impl.FriendlyURLLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLLocalService
 * @see com.liferay.friendly.url.service.base.FriendlyURLLocalServiceBaseImpl
 * @see com.liferay.friendly.url.service.impl.FriendlyURLLocalServiceImpl
 * @generated
 */
@ProviderType
public class FriendlyURLLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.friendly.url.service.impl.FriendlyURLLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the friendly url to the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURL the friendly url
	* @return the friendly url that was added
	*/
	public static com.liferay.friendly.url.model.FriendlyURL addFriendlyURL(
		com.liferay.friendly.url.model.FriendlyURL friendlyURL) {
		return getService().addFriendlyURL(friendlyURL);
	}

	public static com.liferay.friendly.url.model.FriendlyURL addFriendlyURL(
		long companyId, long groupId, java.lang.Class<?> clazz, long classPK,
		java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFriendlyURL(companyId, groupId, clazz, classPK, urlTitle);
	}

	public static com.liferay.friendly.url.model.FriendlyURL addFriendlyURL(
		long companyId, long groupId, long classNameId, long classPK,
		java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFriendlyURL(companyId, groupId, classNameId, classPK,
			urlTitle);
	}

	/**
	* Creates a new friendly url with the primary key. Does not add the friendly url to the database.
	*
	* @param friendlyURLId the primary key for the new friendly url
	* @return the new friendly url
	*/
	public static com.liferay.friendly.url.model.FriendlyURL createFriendlyURL(
		long friendlyURLId) {
		return getService().createFriendlyURL(friendlyURLId);
	}

	/**
	* Deletes the friendly url from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURL the friendly url
	* @return the friendly url that was removed
	*/
	public static com.liferay.friendly.url.model.FriendlyURL deleteFriendlyURL(
		com.liferay.friendly.url.model.FriendlyURL friendlyURL) {
		return getService().deleteFriendlyURL(friendlyURL);
	}

	/**
	* Deletes the friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLId the primary key of the friendly url
	* @return the friendly url that was removed
	* @throws PortalException if a friendly url with the primary key could not be found
	*/
	public static com.liferay.friendly.url.model.FriendlyURL deleteFriendlyURL(
		long friendlyURLId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteFriendlyURL(friendlyURLId);
	}

	public static com.liferay.friendly.url.model.FriendlyURL fetchFriendlyURL(
		long companyId, long groupId, java.lang.Class<?> clazz,
		java.lang.String urlTitle) {
		return getService().fetchFriendlyURL(companyId, groupId, clazz, urlTitle);
	}

	public static com.liferay.friendly.url.model.FriendlyURL fetchFriendlyURL(
		long companyId, long groupId, long classNameId,
		java.lang.String urlTitle) {
		return getService()
				   .fetchFriendlyURL(companyId, groupId, classNameId, urlTitle);
	}

	public static com.liferay.friendly.url.model.FriendlyURL fetchFriendlyURL(
		long friendlyURLId) {
		return getService().fetchFriendlyURL(friendlyURLId);
	}

	/**
	* Returns the friendly url matching the UUID and group.
	*
	* @param uuid the friendly url's UUID
	* @param groupId the primary key of the group
	* @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public static com.liferay.friendly.url.model.FriendlyURL fetchFriendlyURLByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchFriendlyURLByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the friendly url with the primary key.
	*
	* @param friendlyURLId the primary key of the friendly url
	* @return the friendly url
	* @throws PortalException if a friendly url with the primary key could not be found
	*/
	public static com.liferay.friendly.url.model.FriendlyURL getFriendlyURL(
		long friendlyURLId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFriendlyURL(friendlyURLId);
	}

	/**
	* Returns the friendly url matching the UUID and group.
	*
	* @param uuid the friendly url's UUID
	* @param groupId the primary key of the group
	* @return the matching friendly url
	* @throws PortalException if a matching friendly url could not be found
	*/
	public static com.liferay.friendly.url.model.FriendlyURL getFriendlyURLByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFriendlyURLByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.friendly.url.model.FriendlyURL getMainFriendlyURL(
		long companyId, long groupId, java.lang.Class<?> clazz, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getMainFriendlyURL(companyId, groupId, clazz, classPK);
	}

	public static com.liferay.friendly.url.model.FriendlyURL getMainFriendlyURL(
		long companyId, long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getMainFriendlyURL(companyId, groupId, classNameId, classPK);
	}

	/**
	* Updates the friendly url in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param friendlyURL the friendly url
	* @return the friendly url that was updated
	*/
	public static com.liferay.friendly.url.model.FriendlyURL updateFriendlyURL(
		com.liferay.friendly.url.model.FriendlyURL friendlyURL) {
		return getService().updateFriendlyURL(friendlyURL);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
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
	* Returns the number of friendly urls.
	*
	* @return the number of friendly urls
	*/
	public static int getFriendlyURLsCount() {
		return getService().getFriendlyURLsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.lang.String getUniqueUrlTitle(long companyId,
		long groupId, long classNameId, long classPK, java.lang.String urlTitle) {
		return getService()
				   .getUniqueUrlTitle(companyId, groupId, classNameId, classPK,
			urlTitle);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @return the range of friendly urls
	*/
	public static java.util.List<com.liferay.friendly.url.model.FriendlyURL> getFriendlyURLs(
		int start, int end) {
		return getService().getFriendlyURLs(start, end);
	}

	public static java.util.List<com.liferay.friendly.url.model.FriendlyURL> getFriendlyURLs(
		long companyId, long groupId, long classNameId, long classPK) {
		return getService()
				   .getFriendlyURLs(companyId, groupId, classNameId, classPK);
	}

	/**
	* Returns all the friendly urls matching the UUID and company.
	*
	* @param uuid the UUID of the friendly urls
	* @param companyId the primary key of the company
	* @return the matching friendly urls, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.friendly.url.model.FriendlyURL> getFriendlyURLsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().getFriendlyURLsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of friendly urls matching the UUID and company.
	*
	* @param uuid the UUID of the friendly urls
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching friendly urls, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.friendly.url.model.FriendlyURL> getFriendlyURLsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.friendly.url.model.FriendlyURL> orderByComparator) {
		return getService()
				   .getFriendlyURLsByUuidAndCompanyId(uuid, companyId, start,
			end, orderByComparator);
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

	public static void deleteFriendlyURL(long companyId, long groupId,
		java.lang.Class<?> clazz, long classPK) {
		getService().deleteFriendlyURL(companyId, groupId, clazz, classPK);
	}

	public static void deleteFriendlyURL(long companyId, long groupId,
		java.lang.Class<?> clazz, long classPK, java.lang.String urlTitle)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		getService()
			.deleteFriendlyURL(companyId, groupId, clazz, classPK, urlTitle);
	}

	public static void deleteFriendlyURL(long companyId, long groupId,
		long classNameId, long classPK, java.lang.String urlTitle)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		getService()
			.deleteFriendlyURL(companyId, groupId, classNameId, classPK,
			urlTitle);
	}

	public static void deleteGroupFriendlyURLs(long groupId, long classNameId) {
		getService().deleteGroupFriendlyURLs(groupId, classNameId);
	}

	public static void validate(long companyId, long groupId, long classNameId,
		java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().validate(companyId, groupId, classNameId, urlTitle);
	}

	public static void validate(long companyId, long groupId, long classNameId,
		long classPK, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().validate(companyId, groupId, classNameId, classPK, urlTitle);
	}

	public static FriendlyURLLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<FriendlyURLLocalService, FriendlyURLLocalService> _serviceTracker =
		ServiceTrackerFactory.open(FriendlyURLLocalService.class);
}