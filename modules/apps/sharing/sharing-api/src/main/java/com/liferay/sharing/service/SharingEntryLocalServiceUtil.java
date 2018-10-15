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

package com.liferay.sharing.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for SharingEntry. This utility wraps
 * {@link com.liferay.sharing.service.impl.SharingEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SharingEntryLocalService
 * @see com.liferay.sharing.service.base.SharingEntryLocalServiceBaseImpl
 * @see com.liferay.sharing.service.impl.SharingEntryLocalServiceImpl
 * @generated
 */
@ProviderType
public class SharingEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.sharing.service.impl.SharingEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds a new sharing entry in the database or updates an existing one.
	*
	* @param fromUserId the ID of the user sharing the resource
	* @param toUserId the ID of the user the resource is shared with
	* @param classNameId the resource's class name ID
	* @param classPK the class primary key of the resource
	* @param groupId the primary key of the resource's group
	* @param shareable whether the user specified by {@code toUserId} can
	share the resource
	* @param sharingEntryActions the sharing entry actions
	* @param expirationDate the date when the sharing entry expires
	* @param serviceContext the service context
	* @return the sharing entry
	* @throws PortalException if the sharing entry actions are invalid (e.g.,
	empty, don't contain {@code SharingEntryAction#VIEW}, or contain
	a {@code null} value), if the to/from user IDs are the same, or
	if the expiration date is a past value
	*/
	public static com.liferay.sharing.model.SharingEntry addOrUpdateSharingEntry(
		long fromUserId, long toUserId, long classNameId, long classPK,
		long groupId, boolean shareable,
		java.util.Collection<com.liferay.sharing.security.permission.SharingEntryAction> sharingEntryActions,
		java.util.Date expirationDate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addOrUpdateSharingEntry(fromUserId, toUserId, classNameId,
			classPK, groupId, shareable, sharingEntryActions, expirationDate,
			serviceContext);
	}

	/**
	* Adds a new sharing entry in the database.
	*
	* @param fromUserId the ID of the user sharing the resource
	* @param toUserId the ID of the user the resource is shared with
	* @param classNameId the resource's class name ID
	* @param classPK the class primary key of the resource
	* @param groupId the primary key of the resource's group
	* @param shareable whether the user specified by {@code toUserId} can
	share the resource
	* @param sharingEntryActions the sharing entry actions
	* @param expirationDate the date when the sharing entry expires
	* @param serviceContext the service context
	* @return the sharing entry
	* @throws PortalException if a sharing entry already exists for the to/from
	user IDs, if the sharing entry actions are invalid (e.g., empty,
	don't contain {@code SharingEntryAction#VIEW}, or contain a
	{@code null} value), if the to/from user IDs are the same, or if
	the expiration date is a past value
	*/
	public static com.liferay.sharing.model.SharingEntry addSharingEntry(
		long fromUserId, long toUserId, long classNameId, long classPK,
		long groupId, boolean shareable,
		java.util.Collection<com.liferay.sharing.security.permission.SharingEntryAction> sharingEntryActions,
		java.util.Date expirationDate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addSharingEntry(fromUserId, toUserId, classNameId, classPK,
			groupId, shareable, sharingEntryActions, expirationDate,
			serviceContext);
	}

	/**
	* Adds the sharing entry to the database. Also notifies the appropriate model listeners.
	*
	* @param sharingEntry the sharing entry
	* @return the sharing entry that was added
	*/
	public static com.liferay.sharing.model.SharingEntry addSharingEntry(
		com.liferay.sharing.model.SharingEntry sharingEntry) {
		return getService().addSharingEntry(sharingEntry);
	}

	/**
	* Creates a new sharing entry with the primary key. Does not add the sharing entry to the database.
	*
	* @param sharingEntryId the primary key for the new sharing entry
	* @return the new sharing entry
	*/
	public static com.liferay.sharing.model.SharingEntry createSharingEntry(
		long sharingEntryId) {
		return getService().createSharingEntry(sharingEntryId);
	}

	/**
	* Deletes the sharing entries whose expiration date is before the current
	* date.
	*/
	public static void deleteExpiredEntries() {
		getService().deleteExpiredEntries();
	}

	/**
	* Deletes the group's sharing entries.
	*
	* @param groupId the group's ID
	*/
	public static void deleteGroupSharingEntries(long groupId) {
		getService().deleteGroupSharingEntries(groupId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the resource's sharing entries. The class name ID and class
	* primary key identify the resource's type and instance, respectively.
	*
	* @param classNameId the resource's class name ID
	* @param classPK the class primary key of the resource
	*/
	public static void deleteSharingEntries(long classNameId, long classPK) {
		getService().deleteSharingEntries(classNameId, classPK);
	}

	/**
	* Deletes the sharing entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sharingEntryId the primary key of the sharing entry
	* @return the sharing entry that was removed
	* @throws PortalException if a sharing entry with the primary key could not be found
	*/
	public static com.liferay.sharing.model.SharingEntry deleteSharingEntry(
		long sharingEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSharingEntry(sharingEntryId);
	}

	/**
	* Deletes the sharing entry for the resource and users. The class name ID
	* and class primary key identify the resource's type and instance,
	* respectively.
	*
	* @param fromUserId the ID of the user sharing the resource
	* @param toUserId the ID of the user the resource is shared with
	* @param classNameId the resource's class name ID
	* @param classPK the class primary key of the resource
	* @return the deleted sharing entry
	*/
	public static com.liferay.sharing.model.SharingEntry deleteSharingEntry(
		long fromUserId, long toUserId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteSharingEntry(fromUserId, toUserId, classNameId,
			classPK);
	}

	/**
	* Deletes the sharing entry from the database. Also notifies the appropriate model listeners.
	*
	* @param sharingEntry the sharing entry
	* @return the sharing entry that was removed
	*/
	public static com.liferay.sharing.model.SharingEntry deleteSharingEntry(
		com.liferay.sharing.model.SharingEntry sharingEntry) {
		return getService().deleteSharingEntry(sharingEntry);
	}

	/**
	* Deletes the sharing entries for resources shared with the user.
	*
	* @param toUserId the user's ID
	*/
	public static void deleteToUserSharingEntries(long toUserId) {
		getService().deleteToUserSharingEntries(toUserId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.sharing.model.impl.SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.sharing.model.impl.SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.sharing.model.SharingEntry fetchSharingEntry(
		long sharingEntryId) {
		return getService().fetchSharingEntry(sharingEntryId);
	}

	/**
	* Returns the sharing entry matching the UUID and group.
	*
	* @param uuid the sharing entry's UUID
	* @param groupId the primary key of the group
	* @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public static com.liferay.sharing.model.SharingEntry fetchSharingEntryByUuidAndGroupId(
		String uuid, long groupId) {
		return getService().fetchSharingEntryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	* Returns the list of sharing entries for resources shared by the user.
	*
	* @param fromUserId the user's ID
	* @return the list of sharing entries
	*/
	public static java.util.List<com.liferay.sharing.model.SharingEntry> getFromUserSharingEntries(
		long fromUserId) {
		return getService().getFromUserSharingEntries(fromUserId);
	}

	/**
	* Returns the list of sharing entries for the resource shared by the user.
	* The class name ID and class primary key identify the resource's type and
	* instance, respectively.
	*
	* @param fromUserId the user's ID
	* @param classNameId the resource's class name ID
	* @param classPK the primary key of the resource
	* @return the list of sharing entries
	*/
	public static java.util.List<com.liferay.sharing.model.SharingEntry> getFromUserSharingEntries(
		long fromUserId, long classNameId, long classPK) {
		return getService()
				   .getFromUserSharingEntries(fromUserId, classNameId, classPK);
	}

	/**
	* Returns the range of sharing entries for the resource shared by the user.
	* The class name ID and class primary key identify the resource's type and
	* instance, respectively.
	*
	* @param fromUserId the user's ID
	* @param classNameId the resource's class name ID
	* @param classPK the primary key of the resource
	* @param start the range's lower bound
	* @param end the range's upper bound (not inclusive)
	* @return the range of sharing entries
	*/
	public static java.util.List<com.liferay.sharing.model.SharingEntry> getFromUserSharingEntries(
		long fromUserId, long classNameId, long classPK, int start, int end) {
		return getService()
				   .getFromUserSharingEntries(fromUserId, classNameId, classPK,
			start, end);
	}

	/**
	* Returns the number of sharing entries for resources shared by the user.
	*
	* @param fromUserId the user's ID
	* @return the number of sharing entries
	*/
	public static int getFromUserSharingEntriesCount(long fromUserId) {
		return getService().getFromUserSharingEntriesCount(fromUserId);
	}

	/**
	* Returns the number of sharing entries for the resource shared by the
	* user. The class name ID and class primary key identify the resource's
	* type and instance, respectively.
	*
	* @param fromUserId the user's ID
	* @param classNameId the resource's class name ID
	* @param classPK the class primary key of the resource
	* @return the number of sharing entries
	*/
	public static int getFromUserSharingEntriesCount(long fromUserId,
		long classNameId, long classPK) {
		return getService()
				   .getFromUserSharingEntriesCount(fromUserId, classNameId,
			classPK);
	}

	/**
	* Returns the the group's sharing entries.
	*
	* @param groupId the primary key of the group
	* @return the sharing entries
	*/
	public static java.util.List<com.liferay.sharing.model.SharingEntry> getGroupSharingEntries(
		long groupId) {
		return getService().getGroupSharingEntries(groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the sharing entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.sharing.model.impl.SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @return the range of sharing entries
	*/
	public static java.util.List<com.liferay.sharing.model.SharingEntry> getSharingEntries(
		int start, int end) {
		return getService().getSharingEntries(start, end);
	}

	/**
	* Returns the resource's sharing entries. The class name ID and class
	* primary key identify the resource's type and instance, respectively.
	*
	* @param classNameId the resource's class name ID
	* @param classPK the class primary key of the resource
	* @return the sharing entries
	*/
	public static java.util.List<com.liferay.sharing.model.SharingEntry> getSharingEntries(
		long classNameId, long classPK) {
		return getService().getSharingEntries(classNameId, classPK);
	}

	/**
	* Returns the sharing entries for the resource shared with the user. The
	* class name ID and class primary key identify the resource's type and
	* instance, respectively.
	*
	* @param toUserId the user's ID
	* @param classNameId the resource's class name ID
	* @param classPK the class primary key of the resource
	* @return the sharing entries
	*/
	public static java.util.List<com.liferay.sharing.model.SharingEntry> getSharingEntries(
		long toUserId, long classNameId, long classPK) {
		return getService().getSharingEntries(toUserId, classNameId, classPK);
	}

	/**
	* Returns all the sharing entries matching the UUID and company.
	*
	* @param uuid the UUID of the sharing entries
	* @param companyId the primary key of the company
	* @return the matching sharing entries, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.sharing.model.SharingEntry> getSharingEntriesByUuidAndCompanyId(
		String uuid, long companyId) {
		return getService().getSharingEntriesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of sharing entries matching the UUID and company.
	*
	* @param uuid the UUID of the sharing entries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching sharing entries, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.sharing.model.SharingEntry> getSharingEntriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.sharing.model.SharingEntry> orderByComparator) {
		return getService()
				   .getSharingEntriesByUuidAndCompanyId(uuid, companyId, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of sharing entries.
	*
	* @return the number of sharing entries
	*/
	public static int getSharingEntriesCount() {
		return getService().getSharingEntriesCount();
	}

	/**
	* Returns the sharing entry with the primary key.
	*
	* @param sharingEntryId the primary key of the sharing entry
	* @return the sharing entry
	* @throws PortalException if a sharing entry with the primary key could not be found
	*/
	public static com.liferay.sharing.model.SharingEntry getSharingEntry(
		long sharingEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSharingEntry(sharingEntryId);
	}

	/**
	* Returns the sharing entry matching the UUID and group.
	*
	* @param uuid the sharing entry's UUID
	* @param groupId the primary key of the group
	* @return the matching sharing entry
	* @throws PortalException if a matching sharing entry could not be found
	*/
	public static com.liferay.sharing.model.SharingEntry getSharingEntryByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSharingEntryByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the sharing entries for the resource shared with the user. The
	* class name ID and class primary key identify the resource's type and
	* instance, respectively.
	*
	* @param toUserId the user's ID
	* @param classNameId the resource's class name ID
	* @param classPK the class primary key of the resource
	* @return the sharing entries
	*/
	public static java.util.List<com.liferay.sharing.model.SharingEntry> getToUserClassPKSharingEntries(
		long toUserId, long classNameId, long classPK) {
		return getService()
				   .getToUserClassPKSharingEntries(toUserId, classNameId,
			classPK);
	}

	/**
	* Returns the list of sharing entries for resources shared with the user.
	*
	* @param toUserId the user's ID
	* @return the list of sharing entries
	*/
	public static java.util.List<com.liferay.sharing.model.SharingEntry> getToUserSharingEntries(
		long toUserId) {
		return getService().getToUserSharingEntries(toUserId);
	}

	/**
	* Returns the range of sharing entries for resources shared with the user.
	*
	* @param toUserId the user's ID
	* @param start the range's lower bound
	* @param end the range's upper bound (not inclusive)
	* @return the range of sharing entries
	*/
	public static java.util.List<com.liferay.sharing.model.SharingEntry> getToUserSharingEntries(
		long toUserId, int start, int end) {
		return getService().getToUserSharingEntries(toUserId, start, end);
	}

	/**
	* Returns the list of sharing entries for the type of resource shared with
	* the user. The class name ID identifies the resource type.
	*
	* @param toUserId the user's ID
	* @param classNameId the class name ID of the resources
	* @return the list of sharing entries
	*/
	public static java.util.List<com.liferay.sharing.model.SharingEntry> getToUserSharingEntries(
		long toUserId, long classNameId) {
		return getService().getToUserSharingEntries(toUserId, classNameId);
	}

	/**
	* Returns the number of sharing entries for resources shared with the user.
	*
	* @param toUserId the user's ID
	* @return the number of sharing entries
	*/
	public static int getToUserSharingEntriesCount(long toUserId) {
		return getService().getToUserSharingEntriesCount(toUserId);
	}

	/**
	* Returns the ordered range of sharing entries for the type of resource
	* shared with the user. Because it's possible for several users to share
	* the same resource with the user, this method returns only one sharing
	* entry per resource. The class name ID identifies the resource type.
	*
	* @param toUserId the user's ID
	* @param classNameId the class name ID of the resources
	* @param start the ordered range's lower bound
	* @param end the ordered range's upper bound (not inclusive)
	* @param orderByComparator the comparator that orders the sharing entries
	* @return the ordered range of sharing entries
	*/
	public static java.util.List<com.liferay.sharing.model.SharingEntry> getUniqueToUserSharingEntries(
		long toUserId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.sharing.model.SharingEntry> orderByComparator) {
		return getService()
				   .getUniqueToUserSharingEntries(toUserId, classNameId, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of sharing entries for the type of resource shared
	* with the user. Because it's possible for several users to share the same
	* resource with the user, this method counts only one sharing entry per
	* resource. The class name ID identifies the resource type.
	*
	* @param toUserId the user's ID
	* @param classNameId the class name ID of the resources
	* @return the number of sharing entries
	*/
	public static int getUniqueToUserSharingEntriesCount(long toUserId,
		long classNameId) {
		return getService()
				   .getUniqueToUserSharingEntriesCount(toUserId, classNameId);
	}

	/**
	* Returns {@code true} if the resource with the sharing entry action has
	* been shared with a user who can also share that resource. The class name
	* ID and class primary key identify the resource's type and instance,
	* respectively.
	*
	* @param toUserId the user's ID
	* @param classNameId the resource's class name ID
	* @param classPK the class primary key of the shared resource
	* @param sharingEntryAction the sharing entry action
	* @return {@code true} if the resource with the sharing entry action has
	been shared with a user who can also share that resource; {@code
	false} otherwise
	*/
	public static boolean hasShareableSharingPermission(long toUserId,
		long classNameId, long classPK,
		com.liferay.sharing.security.permission.SharingEntryAction sharingEntryAction) {
		return getService()
				   .hasShareableSharingPermission(toUserId, classNameId,
			classPK, sharingEntryAction);
	}

	/**
	* Returns {@code true} if the resource with the sharing entry action has
	* been shared with the user. The class name ID and class primary key
	* identify the resource's type and instance, respectively.
	*
	* @param toUserId the user's ID
	* @param classNameId the resource's class name ID
	* @param classPK the class primary key of the shared resource
	* @param sharingEntryAction the sharing entry action
	* @return {@code true} if the resource with the sharing entry action has
	been shared with the user; {@code false} otherwise
	*/
	public static boolean hasSharingPermission(long toUserId, long classNameId,
		long classPK,
		com.liferay.sharing.security.permission.SharingEntryAction sharingEntryAction) {
		return getService()
				   .hasSharingPermission(toUserId, classNameId, classPK,
			sharingEntryAction);
	}

	/**
	* Returns {@code true} if the sharing entry has the sharing entry action.
	*
	* @param sharingEntry the sharing entry
	* @param sharingEntryAction the sharing entry action
	* @return {@code true} if the sharing entry has the sharing entry action;
	{@code false} otherwise
	*/
	public static boolean hasSharingPermission(
		com.liferay.sharing.model.SharingEntry sharingEntry,
		com.liferay.sharing.security.permission.SharingEntryAction sharingEntryAction) {
		return getService()
				   .hasSharingPermission(sharingEntry, sharingEntryAction);
	}

	/**
	* Updates the sharing entry in the database.
	*
	* @param sharingEntryId the primary key of the sharing entry
	* @param sharingEntryActions the sharing entry actions
	* @param shareable whether the user the resource is shared with can also
	share it
	* @param expirationDate the date when the sharing entry expires
	* @param serviceContext the service context
	* @return the sharing entry
	* @throws PortalException if the sharing entry does not exist, if the
	sharing entry actions are invalid (e.g., empty, don't contain
	{@code SharingEntryAction#VIEW}, or contain a {@code null}
	value), or if the expiration date is a past value
	*/
	public static com.liferay.sharing.model.SharingEntry updateSharingEntry(
		long sharingEntryId,
		java.util.Collection<com.liferay.sharing.security.permission.SharingEntryAction> sharingEntryActions,
		boolean shareable, java.util.Date expirationDate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateSharingEntry(sharingEntryId, sharingEntryActions,
			shareable, expirationDate, serviceContext);
	}

	/**
	* Updates the sharing entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param sharingEntry the sharing entry
	* @return the sharing entry that was updated
	*/
	public static com.liferay.sharing.model.SharingEntry updateSharingEntry(
		com.liferay.sharing.model.SharingEntry sharingEntry) {
		return getService().updateSharingEntry(sharingEntry);
	}

	public static SharingEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SharingEntryLocalService, SharingEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SharingEntryLocalService.class);

		ServiceTracker<SharingEntryLocalService, SharingEntryLocalService> serviceTracker =
			new ServiceTracker<SharingEntryLocalService, SharingEntryLocalService>(bundle.getBundleContext(),
				SharingEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}