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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SharingEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SharingEntryLocalService
 * @generated
 */
@ProviderType
public class SharingEntryLocalServiceWrapper implements SharingEntryLocalService,
	ServiceWrapper<SharingEntryLocalService> {
	public SharingEntryLocalServiceWrapper(
		SharingEntryLocalService sharingEntryLocalService) {
		_sharingEntryLocalService = sharingEntryLocalService;
	}

	/**
	* Adds a sharing entry in the database if it does not exist or it updates
	* it if it exists.
	*
	* @param fromUserId the user id sharing the resource
	* @param toUserId the user id whose resource was shared
	* @param classNameId the class name ID of the resource being shared
	* @param classPK the primary key of the resource being shared
	* @param groupId the primary key of the group containing the resource
	being shared
	* @param shareable whether the to user id can share the resource as well
	* @param sharingEntryActions the sharing entry actions
	* @param expirationDate the date when the sharing entry expires
	* @return the sharing entry
	* @param serviceContext the service context to be applied
	* @throws PortalException if sharing entry actions are invalid (it is
	empty, it doesn't contain {@link SharingEntryAction#VIEW,} or
	it contains a <code>null</code> value) or from user id and to
	user id are the same or the expiration date is a value in the
	past.
	*/
	@Override
	public com.liferay.sharing.model.SharingEntry addOrUpdateSharingEntry(
		long fromUserId, long toUserId, long classNameId, long classPK,
		long groupId, boolean shareable,
		java.util.Collection<com.liferay.sharing.security.permission.SharingEntryAction> sharingEntryActions,
		java.util.Date expirationDate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharingEntryLocalService.addOrUpdateSharingEntry(fromUserId,
			toUserId, classNameId, classPK, groupId, shareable,
			sharingEntryActions, expirationDate, serviceContext);
	}

	/**
	* Adds a sharing entry in the database.
	*
	* @param fromUserId the user id sharing the resource
	* @param toUserId the user id whose resource was shared
	* @param classNameId the class name ID of the resource being shared
	* @param classPK the primary key of the resource being shared
	* @param groupId the primary key of the group containing the resource
	being shared
	* @param shareable whether the to user id can share the resource as well
	* @param sharingEntryActions the sharing entry actions
	* @param expirationDate the date when the sharing entry expires
	* @return the sharing entry
	* @param serviceContext the service context to be applied
	* @throws PortalException if there is already a sharing entry for the same
	from user id, to user id and resource or the sharing entry
	actions are invalid (it is empty, it doesn't contain
	{@link SharingEntryAction#VIEW,} or it contains a
	<code>null</code> value) or from user id and to user id are the
	same or the expiration date is a value in the past.
	*/
	@Override
	public com.liferay.sharing.model.SharingEntry addSharingEntry(
		long fromUserId, long toUserId, long classNameId, long classPK,
		long groupId, boolean shareable,
		java.util.Collection<com.liferay.sharing.security.permission.SharingEntryAction> sharingEntryActions,
		java.util.Date expirationDate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharingEntryLocalService.addSharingEntry(fromUserId, toUserId,
			classNameId, classPK, groupId, shareable, sharingEntryActions,
			expirationDate, serviceContext);
	}

	/**
	* Adds the sharing entry to the database. Also notifies the appropriate model listeners.
	*
	* @param sharingEntry the sharing entry
	* @return the sharing entry that was added
	*/
	@Override
	public com.liferay.sharing.model.SharingEntry addSharingEntry(
		com.liferay.sharing.model.SharingEntry sharingEntry) {
		return _sharingEntryLocalService.addSharingEntry(sharingEntry);
	}

	/**
	* Creates a new sharing entry with the primary key. Does not add the sharing entry to the database.
	*
	* @param sharingEntryId the primary key for the new sharing entry
	* @return the new sharing entry
	*/
	@Override
	public com.liferay.sharing.model.SharingEntry createSharingEntry(
		long sharingEntryId) {
		return _sharingEntryLocalService.createSharingEntry(sharingEntryId);
	}

	/**
	* Deletes all sharing entries whose expiration date is before the current
	* date.
	*/
	@Override
	public void deleteExpiredEntries() {
		_sharingEntryLocalService.deleteExpiredEntries();
	}

	/**
	* Deletes all sharing entries that belong to a group.
	*/
	@Override
	public void deleteGroupSharingEntries(long groupId) {
		_sharingEntryLocalService.deleteGroupSharingEntries(groupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharingEntryLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes all sharing entries of a resource.
	*
	* @param classNameId the class name ID of the resource
	* @param classPK the primary key of the resource
	*/
	@Override
	public void deleteSharingEntries(long classNameId, long classPK) {
		_sharingEntryLocalService.deleteSharingEntries(classNameId, classPK);
	}

	/**
	* Deletes the sharing entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sharingEntryId the primary key of the sharing entry
	* @return the sharing entry that was removed
	* @throws PortalException if a sharing entry with the primary key could not be found
	*/
	@Override
	public com.liferay.sharing.model.SharingEntry deleteSharingEntry(
		long sharingEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharingEntryLocalService.deleteSharingEntry(sharingEntryId);
	}

	/**
	* Deletes the sharing entry of a user to another user for a resource.
	*
	* @param classNameId the class name ID of the resource
	* @param classPK the primary key of the resource
	* @return the deleted sharing entry
	*/
	@Override
	public com.liferay.sharing.model.SharingEntry deleteSharingEntry(
		long fromUserId, long toUserId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharingEntryLocalService.deleteSharingEntry(fromUserId,
			toUserId, classNameId, classPK);
	}

	/**
	* Deletes the sharing entry from the database. Also notifies the appropriate model listeners.
	*
	* @param sharingEntry the sharing entry
	* @return the sharing entry that was removed
	*/
	@Override
	public com.liferay.sharing.model.SharingEntry deleteSharingEntry(
		com.liferay.sharing.model.SharingEntry sharingEntry) {
		return _sharingEntryLocalService.deleteSharingEntry(sharingEntry);
	}

	/**
	* Deletes all sharing entries shared to a user.
	*
	* @param toUserId the user id who was shared the resource
	*/
	@Override
	public void deleteToUserSharingEntries(long toUserId) {
		_sharingEntryLocalService.deleteToUserSharingEntries(toUserId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _sharingEntryLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _sharingEntryLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _sharingEntryLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _sharingEntryLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _sharingEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _sharingEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.sharing.model.SharingEntry fetchSharingEntry(
		long sharingEntryId) {
		return _sharingEntryLocalService.fetchSharingEntry(sharingEntryId);
	}

	/**
	* Returns the sharing entry matching the UUID and group.
	*
	* @param uuid the sharing entry's UUID
	* @param groupId the primary key of the group
	* @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	@Override
	public com.liferay.sharing.model.SharingEntry fetchSharingEntryByUuidAndGroupId(
		String uuid, long groupId) {
		return _sharingEntryLocalService.fetchSharingEntryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _sharingEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _sharingEntryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	* Returns a list of all the sharing entries that has been shared by a user.
	*
	* @param fromUserId the user id sharing the resource
	* @return the list of sharing entries
	*/
	@Override
	public java.util.List<com.liferay.sharing.model.SharingEntry> getFromUserSharingEntries(
		long fromUserId) {
		return _sharingEntryLocalService.getFromUserSharingEntries(fromUserId);
	}

	/**
	* Returns a list of all the sharing entries of a resource that has been
	* shared by a user
	*
	* @param fromUserId the user id sharing the resource
	* @param classNameId the class name ID of the resource
	* @param classPK the primary key of the resource
	* @return the list of sharing entries
	*/
	@Override
	public java.util.List<com.liferay.sharing.model.SharingEntry> getFromUserSharingEntries(
		long fromUserId, long classNameId, long classPK) {
		return _sharingEntryLocalService.getFromUserSharingEntries(fromUserId,
			classNameId, classPK);
	}

	/**
	* Returns a range of all the sharing entries of a resource that has been
	* shared by a user
	*
	* @param fromUserId the user id sharing the resource
	* @param classNameId the class name ID of the resource
	* @param classPK the primary key of the resource
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of sharing entries
	*/
	@Override
	public java.util.List<com.liferay.sharing.model.SharingEntry> getFromUserSharingEntries(
		long fromUserId, long classNameId, long classPK, int start, int end) {
		return _sharingEntryLocalService.getFromUserSharingEntries(fromUserId,
			classNameId, classPK, start, end);
	}

	/**
	* Returns the number of sharing entries that have been shared by a user.
	*
	* @param fromUserId the user id sharing the resource
	* @return the number of sharing entries
	*/
	@Override
	public int getFromUserSharingEntriesCount(long fromUserId) {
		return _sharingEntryLocalService.getFromUserSharingEntriesCount(fromUserId);
	}

	/**
	* Returns the number of sharing entries of a resource that have been shared
	* by a user.
	*
	* @param fromUserId the user id sharing the resource
	* @param classNameId the class name ID of the resource
	* @param classPK the primary key of the resource
	* @return the number of sharing entries
	*/
	@Override
	public int getFromUserSharingEntriesCount(long fromUserId,
		long classNameId, long classPK) {
		return _sharingEntryLocalService.getFromUserSharingEntriesCount(fromUserId,
			classNameId, classPK);
	}

	/**
	* Returns a list of all the sharing entries of a group.
	*
	* @param groupId the primary key of the group
	* @return the list of sharing entries
	*/
	@Override
	public java.util.List<com.liferay.sharing.model.SharingEntry> getGroupSharingEntries(
		long groupId) {
		return _sharingEntryLocalService.getGroupSharingEntries(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _sharingEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _sharingEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharingEntryLocalService.getPersistedModel(primaryKeyObj);
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
	@Override
	public java.util.List<com.liferay.sharing.model.SharingEntry> getSharingEntries(
		int start, int end) {
		return _sharingEntryLocalService.getSharingEntries(start, end);
	}

	/**
	* Returns a list of all the sharing entries of a resource.
	*
	* @param classNameId the class name ID of the resource
	* @param classPK the primary key of the resource
	* @return the list of sharing entries
	*/
	@Override
	public java.util.List<com.liferay.sharing.model.SharingEntry> getSharingEntries(
		long classNameId, long classPK) {
		return _sharingEntryLocalService.getSharingEntries(classNameId, classPK);
	}

	/**
	* Returns a list of all the sharing entries of a resource that has been
	* shared to a user.
	*
	* @param toUserId the user id that has been shared the resource
	* @param classNameId the class name ID of the resource
	* @param classPK the primary key of the resource
	* @return the list of sharing entries
	*/
	@Override
	public java.util.List<com.liferay.sharing.model.SharingEntry> getSharingEntries(
		long toUserId, long classNameId, long classPK) {
		return _sharingEntryLocalService.getSharingEntries(toUserId,
			classNameId, classPK);
	}

	/**
	* Returns all the sharing entries matching the UUID and company.
	*
	* @param uuid the UUID of the sharing entries
	* @param companyId the primary key of the company
	* @return the matching sharing entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.sharing.model.SharingEntry> getSharingEntriesByUuidAndCompanyId(
		String uuid, long companyId) {
		return _sharingEntryLocalService.getSharingEntriesByUuidAndCompanyId(uuid,
			companyId);
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
	@Override
	public java.util.List<com.liferay.sharing.model.SharingEntry> getSharingEntriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.sharing.model.SharingEntry> orderByComparator) {
		return _sharingEntryLocalService.getSharingEntriesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of sharing entries.
	*
	* @return the number of sharing entries
	*/
	@Override
	public int getSharingEntriesCount() {
		return _sharingEntryLocalService.getSharingEntriesCount();
	}

	/**
	* Returns the sharing entry with the primary key.
	*
	* @param sharingEntryId the primary key of the sharing entry
	* @return the sharing entry
	* @throws PortalException if a sharing entry with the primary key could not be found
	*/
	@Override
	public com.liferay.sharing.model.SharingEntry getSharingEntry(
		long sharingEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharingEntryLocalService.getSharingEntry(sharingEntryId);
	}

	/**
	* Returns the sharing entry matching the UUID and group.
	*
	* @param uuid the sharing entry's UUID
	* @param groupId the primary key of the group
	* @return the matching sharing entry
	* @throws PortalException if a matching sharing entry could not be found
	*/
	@Override
	public com.liferay.sharing.model.SharingEntry getSharingEntryByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharingEntryLocalService.getSharingEntryByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a list of sharing entries of a specific class name id and class
	* pk that has been shared to a user.
	*
	* @param toUserId the user id that has been shared the resource
	* @param classNameId the class name ID of the shared resource
	* @param classPK the class pk of the shared resource
	* @return the list of sharing entries
	*/
	@Override
	public java.util.List<com.liferay.sharing.model.SharingEntry> getToUserClassPKSharingEntries(
		long toUserId, long classNameId, long classPK) {
		return _sharingEntryLocalService.getToUserClassPKSharingEntries(toUserId,
			classNameId, classPK);
	}

	/**
	* Returns a list of all the sharing entries that has been shared to a user.
	*
	* @param toUserId the user id that has been shared the resource
	* @return the range of sharing entries
	*/
	@Override
	public java.util.List<com.liferay.sharing.model.SharingEntry> getToUserSharingEntries(
		long toUserId) {
		return _sharingEntryLocalService.getToUserSharingEntries(toUserId);
	}

	/**
	* Returns a range of all the sharing entries that has been shared to a
	* user.
	*
	* @param toUserId the user id that has been shared the resource
	* @return the range of sharing entries
	*/
	@Override
	public java.util.List<com.liferay.sharing.model.SharingEntry> getToUserSharingEntries(
		long toUserId, int start, int end) {
		return _sharingEntryLocalService.getToUserSharingEntries(toUserId,
			start, end);
	}

	/**
	* Returns a list of sharing entries of a specific class name id that has
	* been shared to a user.
	*
	* @param toUserId the user id that has been shared the resource
	* @param classNameId the class name ID of the shared resource
	* @return the list of sharing entries
	*/
	@Override
	public java.util.List<com.liferay.sharing.model.SharingEntry> getToUserSharingEntries(
		long toUserId, long classNameId) {
		return _sharingEntryLocalService.getToUserSharingEntries(toUserId,
			classNameId);
	}

	/**
	* Returns the number of sharing entries that have been shared to a user.
	*
	* @param toUserId the user id who was shared the resource
	* @return the number of sharing entries
	*/
	@Override
	public int getToUserSharingEntriesCount(long toUserId) {
		return _sharingEntryLocalService.getToUserSharingEntriesCount(toUserId);
	}

	/**
	* Returns a list of all the sharing entries of a resource that has been
	* shared to a user returning at most one per shared model
	*
	* @param toUserId the user id
	* @param classNameId the classNameId to filter by
	* @param orderByComparator the comparator to order the sharing entries
	* @return the list of sharing entries
	*/
	@Override
	public java.util.List<com.liferay.sharing.model.SharingEntry> getUniqueToUserSharingEntries(
		long toUserId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.sharing.model.SharingEntry> orderByComparator) {
		return _sharingEntryLocalService.getUniqueToUserSharingEntries(toUserId,
			classNameId, start, end, orderByComparator);
	}

	/**
	* Returns the number of sharing entries of a resource that have been shared
	* by to user returning at most one per shared model.
	*
	* @param toUserId the user id
	* @param classNameId the classNameId to filter by
	* @return the number of sharing entries
	*/
	@Override
	public int getUniqueToUserSharingEntriesCount(long toUserId,
		long classNameId) {
		return _sharingEntryLocalService.getUniqueToUserSharingEntriesCount(toUserId,
			classNameId);
	}

	/**
	* Returns <code>true</code> if the to user id has been shared a resource
	* with a sharing entry action and, in addition, he can share the resource
	* as well.
	*
	* @param toUserId the user id that has been shared the resource
	* @param classNameId the class name ID of the shared resource
	* @param classPK the primary key of the shared resource
	* @param sharingEntryAction the sharing entry action
	* @return <code>true</code> if the user has been shared a resource with a
	sharing entry action and he can, in additino, share the resource
	as well; <code>false</code> otherwise
	*/
	@Override
	public boolean hasShareableSharingPermission(long toUserId,
		long classNameId, long classPK,
		com.liferay.sharing.security.permission.SharingEntryAction sharingEntryAction) {
		return _sharingEntryLocalService.hasShareableSharingPermission(toUserId,
			classNameId, classPK, sharingEntryAction);
	}

	/**
	* Returns <code>true</code> if the to user id has been shared a resource
	* with a sharing entry action
	*
	* @param toUserId the user id that has been shared the resource
	* @param classNameId the class name ID of the shared resource
	* @param classPK the primary key of the shared resource
	* @param sharingEntryAction the sharing entry action
	* @return <code>true</code> if the user has been shared a resource with a
	sharing entry action; <code>false</code> otherwise
	*/
	@Override
	public boolean hasSharingPermission(long toUserId, long classNameId,
		long classPK,
		com.liferay.sharing.security.permission.SharingEntryAction sharingEntryAction) {
		return _sharingEntryLocalService.hasSharingPermission(toUserId,
			classNameId, classPK, sharingEntryAction);
	}

	/**
	* Returns <code>true</code> if the sharing entry has certain sharing entry
	* action
	*
	* @param sharingEntry the sharing entry
	* @param sharingEntryAction the sharing entry action
	* @return <code>true</code> if the sharing entry has the sharing entry
	action; <code>false</code> otherwise
	*/
	@Override
	public boolean hasSharingPermission(
		com.liferay.sharing.model.SharingEntry sharingEntry,
		com.liferay.sharing.security.permission.SharingEntryAction sharingEntryAction) {
		return _sharingEntryLocalService.hasSharingPermission(sharingEntry,
			sharingEntryAction);
	}

	/**
	* Updates a sharing entry in the database.
	*
	* @param sharingEntryId the primary key of the sharing entry
	* @param sharingEntryActions the sharing entry actions
	* @param shareable whether the to user id can share the resource as well
	* @param expirationDate the date when the sharing entry expires
	* @return the sharing entry
	* @param serviceContext the service context to be applied
	* @throws PortalException if the sharing entry does not exist or sharing
	entry actions are invalid (it is empty, it doesn't contain
	{@link SharingEntryAction#VIEW,} or it contains a
	<code>null</code> value) or the expiration date is a value in the
	past.
	*/
	@Override
	public com.liferay.sharing.model.SharingEntry updateSharingEntry(
		long sharingEntryId,
		java.util.Collection<com.liferay.sharing.security.permission.SharingEntryAction> sharingEntryActions,
		boolean shareable, java.util.Date expirationDate,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharingEntryLocalService.updateSharingEntry(sharingEntryId,
			sharingEntryActions, shareable, expirationDate, serviceContext);
	}

	/**
	* Updates the sharing entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param sharingEntry the sharing entry
	* @return the sharing entry that was updated
	*/
	@Override
	public com.liferay.sharing.model.SharingEntry updateSharingEntry(
		com.liferay.sharing.model.SharingEntry sharingEntry) {
		return _sharingEntryLocalService.updateSharingEntry(sharingEntry);
	}

	@Override
	public SharingEntryLocalService getWrappedService() {
		return _sharingEntryLocalService;
	}

	@Override
	public void setWrappedService(
		SharingEntryLocalService sharingEntryLocalService) {
		_sharingEntryLocalService = sharingEntryLocalService;
	}

	private SharingEntryLocalService _sharingEntryLocalService;
}