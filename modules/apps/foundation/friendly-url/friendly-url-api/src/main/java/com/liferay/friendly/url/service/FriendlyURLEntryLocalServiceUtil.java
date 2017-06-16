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
 * Provides the local service utility for FriendlyURLEntry. This utility wraps
 * {@link com.liferay.friendly.url.service.impl.FriendlyURLEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryLocalService
 * @see com.liferay.friendly.url.service.base.FriendlyURLEntryLocalServiceBaseImpl
 * @see com.liferay.friendly.url.service.impl.FriendlyURLEntryLocalServiceImpl
 * @generated
 */
@ProviderType
public class FriendlyURLEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.friendly.url.service.impl.FriendlyURLEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the friendly url entry to the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLEntry the friendly url entry
	* @return the friendly url entry that was added
	*/
	public static com.liferay.friendly.url.model.FriendlyURLEntry addFriendlyURLEntry(
		com.liferay.friendly.url.model.FriendlyURLEntry friendlyURLEntry) {
		return getService().addFriendlyURLEntry(friendlyURLEntry);
	}

	public static com.liferay.friendly.url.model.FriendlyURLEntry addFriendlyURLEntry(
		long groupId, java.lang.Class<?> clazz, long classPK,
		java.lang.String urlTitle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFriendlyURLEntry(groupId, clazz, classPK, urlTitle,
			serviceContext);
	}

	public static com.liferay.friendly.url.model.FriendlyURLEntry addFriendlyURLEntry(
		long groupId, long classNameId, long classPK,
		java.lang.String urlTitle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFriendlyURLEntry(groupId, classNameId, classPK,
			urlTitle, serviceContext);
	}

	/**
	* Creates a new friendly url entry with the primary key. Does not add the friendly url entry to the database.
	*
	* @param friendlyURLEntryId the primary key for the new friendly url entry
	* @return the new friendly url entry
	*/
	public static com.liferay.friendly.url.model.FriendlyURLEntry createFriendlyURLEntry(
		long friendlyURLEntryId) {
		return getService().createFriendlyURLEntry(friendlyURLEntryId);
	}

	/**
	* Deletes the friendly url entry from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLEntry the friendly url entry
	* @return the friendly url entry that was removed
	*/
	public static com.liferay.friendly.url.model.FriendlyURLEntry deleteFriendlyURLEntry(
		com.liferay.friendly.url.model.FriendlyURLEntry friendlyURLEntry) {
		return getService().deleteFriendlyURLEntry(friendlyURLEntry);
	}

	/**
	* Deletes the friendly url entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLEntryId the primary key of the friendly url entry
	* @return the friendly url entry that was removed
	* @throws PortalException if a friendly url entry with the primary key could not be found
	*/
	public static com.liferay.friendly.url.model.FriendlyURLEntry deleteFriendlyURLEntry(
		long friendlyURLEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteFriendlyURLEntry(friendlyURLEntryId);
	}

	public static com.liferay.friendly.url.model.FriendlyURLEntry fetchFriendlyURLEntry(
		long friendlyURLEntryId) {
		return getService().fetchFriendlyURLEntry(friendlyURLEntryId);
	}

	public static com.liferay.friendly.url.model.FriendlyURLEntry fetchFriendlyURLEntry(
		long groupId, java.lang.Class<?> clazz, java.lang.String urlTitle) {
		return getService().fetchFriendlyURLEntry(groupId, clazz, urlTitle);
	}

	public static com.liferay.friendly.url.model.FriendlyURLEntry fetchFriendlyURLEntry(
		long groupId, long classNameId, java.lang.String urlTitle) {
		return getService().fetchFriendlyURLEntry(groupId, classNameId, urlTitle);
	}

	/**
	* Returns the friendly url entry matching the UUID and group.
	*
	* @param uuid the friendly url entry's UUID
	* @param groupId the primary key of the group
	* @return the matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	*/
	public static com.liferay.friendly.url.model.FriendlyURLEntry fetchFriendlyURLEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchFriendlyURLEntryByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the friendly url entry with the primary key.
	*
	* @param friendlyURLEntryId the primary key of the friendly url entry
	* @return the friendly url entry
	* @throws PortalException if a friendly url entry with the primary key could not be found
	*/
	public static com.liferay.friendly.url.model.FriendlyURLEntry getFriendlyURLEntry(
		long friendlyURLEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFriendlyURLEntry(friendlyURLEntryId);
	}

	/**
	* Returns the friendly url entry matching the UUID and group.
	*
	* @param uuid the friendly url entry's UUID
	* @param groupId the primary key of the group
	* @return the matching friendly url entry
	* @throws PortalException if a matching friendly url entry could not be found
	*/
	public static com.liferay.friendly.url.model.FriendlyURLEntry getFriendlyURLEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFriendlyURLEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.friendly.url.model.FriendlyURLEntry getMainFriendlyURLEntry(
		long groupId, java.lang.Class<?> clazz, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMainFriendlyURLEntry(groupId, clazz, classPK);
	}

	public static com.liferay.friendly.url.model.FriendlyURLEntry getMainFriendlyURLEntry(
		long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getMainFriendlyURLEntry(groupId, classNameId, classPK);
	}

	/**
	* Updates the friendly url entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLEntry the friendly url entry
	* @return the friendly url entry that was updated
	*/
	public static com.liferay.friendly.url.model.FriendlyURLEntry updateFriendlyURLEntry(
		com.liferay.friendly.url.model.FriendlyURLEntry friendlyURLEntry) {
		return getService().updateFriendlyURLEntry(friendlyURLEntry);
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
	* Returns the number of friendly url entries.
	*
	* @return the number of friendly url entries
	*/
	public static int getFriendlyURLEntriesCount() {
		return getService().getFriendlyURLEntriesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.lang.String getUniqueUrlTitle(long groupId,
		long classNameId, long classPK, java.lang.String urlTitle) {
		return getService()
				   .getUniqueUrlTitle(groupId, classNameId, classPK, urlTitle);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the friendly url entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url entries
	* @param end the upper bound of the range of friendly url entries (not inclusive)
	* @return the range of friendly url entries
	*/
	public static java.util.List<com.liferay.friendly.url.model.FriendlyURLEntry> getFriendlyURLEntries(
		int start, int end) {
		return getService().getFriendlyURLEntries(start, end);
	}

	public static java.util.List<com.liferay.friendly.url.model.FriendlyURLEntry> getFriendlyURLEntries(
		long groupId, long classNameId, long classPK) {
		return getService().getFriendlyURLEntries(groupId, classNameId, classPK);
	}

	/**
	* Returns all the friendly url entries matching the UUID and company.
	*
	* @param uuid the UUID of the friendly url entries
	* @param companyId the primary key of the company
	* @return the matching friendly url entries, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.friendly.url.model.FriendlyURLEntry> getFriendlyURLEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getFriendlyURLEntriesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of friendly url entries matching the UUID and company.
	*
	* @param uuid the UUID of the friendly url entries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of friendly url entries
	* @param end the upper bound of the range of friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching friendly url entries, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.friendly.url.model.FriendlyURLEntry> getFriendlyURLEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.friendly.url.model.FriendlyURLEntry> orderByComparator) {
		return getService()
				   .getFriendlyURLEntriesByUuidAndCompanyId(uuid, companyId,
			start, end, orderByComparator);
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

	public static void deleteFriendlyURLEntry(long groupId,
		java.lang.Class<?> clazz, long classPK) {
		getService().deleteFriendlyURLEntry(groupId, clazz, classPK);
	}

	public static void deleteFriendlyURLEntry(long groupId,
		java.lang.Class<?> clazz, long classPK, java.lang.String urlTitle)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLEntryException {
		getService().deleteFriendlyURLEntry(groupId, clazz, classPK, urlTitle);
	}

	public static void deleteFriendlyURLEntry(long groupId, long classNameId,
		long classPK, java.lang.String urlTitle)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLEntryException {
		getService()
			.deleteFriendlyURLEntry(groupId, classNameId, classPK, urlTitle);
	}

	public static void deleteGroupFriendlyURLEntries(long groupId,
		long classNameId) {
		getService().deleteGroupFriendlyURLEntries(groupId, classNameId);
	}

	public static void validate(long groupId, long classNameId,
		java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().validate(groupId, classNameId, urlTitle);
	}

	public static void validate(long groupId, long classNameId, long classPK,
		java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().validate(groupId, classNameId, classPK, urlTitle);
	}

	public static FriendlyURLEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<FriendlyURLEntryLocalService, FriendlyURLEntryLocalService> _serviceTracker =
		ServiceTrackerFactory.open(FriendlyURLEntryLocalService.class);
}