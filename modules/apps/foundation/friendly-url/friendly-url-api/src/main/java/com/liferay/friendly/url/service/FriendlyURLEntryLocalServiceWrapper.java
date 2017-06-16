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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FriendlyURLEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryLocalService
 * @generated
 */
@ProviderType
public class FriendlyURLEntryLocalServiceWrapper
	implements FriendlyURLEntryLocalService,
		ServiceWrapper<FriendlyURLEntryLocalService> {
	public FriendlyURLEntryLocalServiceWrapper(
		FriendlyURLEntryLocalService friendlyURLEntryLocalService) {
		_friendlyURLEntryLocalService = friendlyURLEntryLocalService;
	}

	/**
	* Adds the friendly url entry to the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLEntry the friendly url entry
	* @return the friendly url entry that was added
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURLEntry addFriendlyURLEntry(
		com.liferay.friendly.url.model.FriendlyURLEntry friendlyURLEntry) {
		return _friendlyURLEntryLocalService.addFriendlyURLEntry(friendlyURLEntry);
	}

	@Override
	public com.liferay.friendly.url.model.FriendlyURLEntry addFriendlyURLEntry(
		long groupId, java.lang.Class<?> clazz, long classPK,
		java.lang.String urlTitle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLEntryLocalService.addFriendlyURLEntry(groupId,
			clazz, classPK, urlTitle, serviceContext);
	}

	@Override
	public com.liferay.friendly.url.model.FriendlyURLEntry addFriendlyURLEntry(
		long groupId, long classNameId, long classPK,
		java.lang.String urlTitle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLEntryLocalService.addFriendlyURLEntry(groupId,
			classNameId, classPK, urlTitle, serviceContext);
	}

	/**
	* Creates a new friendly url entry with the primary key. Does not add the friendly url entry to the database.
	*
	* @param friendlyURLEntryId the primary key for the new friendly url entry
	* @return the new friendly url entry
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURLEntry createFriendlyURLEntry(
		long friendlyURLEntryId) {
		return _friendlyURLEntryLocalService.createFriendlyURLEntry(friendlyURLEntryId);
	}

	/**
	* Deletes the friendly url entry from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLEntry the friendly url entry
	* @return the friendly url entry that was removed
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURLEntry deleteFriendlyURLEntry(
		com.liferay.friendly.url.model.FriendlyURLEntry friendlyURLEntry) {
		return _friendlyURLEntryLocalService.deleteFriendlyURLEntry(friendlyURLEntry);
	}

	/**
	* Deletes the friendly url entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLEntryId the primary key of the friendly url entry
	* @return the friendly url entry that was removed
	* @throws PortalException if a friendly url entry with the primary key could not be found
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURLEntry deleteFriendlyURLEntry(
		long friendlyURLEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLEntryLocalService.deleteFriendlyURLEntry(friendlyURLEntryId);
	}

	@Override
	public com.liferay.friendly.url.model.FriendlyURLEntry fetchFriendlyURLEntry(
		long friendlyURLEntryId) {
		return _friendlyURLEntryLocalService.fetchFriendlyURLEntry(friendlyURLEntryId);
	}

	@Override
	public com.liferay.friendly.url.model.FriendlyURLEntry fetchFriendlyURLEntry(
		long groupId, java.lang.Class<?> clazz, java.lang.String urlTitle) {
		return _friendlyURLEntryLocalService.fetchFriendlyURLEntry(groupId,
			clazz, urlTitle);
	}

	@Override
	public com.liferay.friendly.url.model.FriendlyURLEntry fetchFriendlyURLEntry(
		long groupId, long classNameId, java.lang.String urlTitle) {
		return _friendlyURLEntryLocalService.fetchFriendlyURLEntry(groupId,
			classNameId, urlTitle);
	}

	/**
	* Returns the friendly url entry matching the UUID and group.
	*
	* @param uuid the friendly url entry's UUID
	* @param groupId the primary key of the group
	* @return the matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURLEntry fetchFriendlyURLEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _friendlyURLEntryLocalService.fetchFriendlyURLEntryByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the friendly url entry with the primary key.
	*
	* @param friendlyURLEntryId the primary key of the friendly url entry
	* @return the friendly url entry
	* @throws PortalException if a friendly url entry with the primary key could not be found
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURLEntry getFriendlyURLEntry(
		long friendlyURLEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLEntryLocalService.getFriendlyURLEntry(friendlyURLEntryId);
	}

	/**
	* Returns the friendly url entry matching the UUID and group.
	*
	* @param uuid the friendly url entry's UUID
	* @param groupId the primary key of the group
	* @return the matching friendly url entry
	* @throws PortalException if a matching friendly url entry could not be found
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURLEntry getFriendlyURLEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLEntryLocalService.getFriendlyURLEntryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.friendly.url.model.FriendlyURLEntry getMainFriendlyURLEntry(
		long groupId, java.lang.Class<?> clazz, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLEntryLocalService.getMainFriendlyURLEntry(groupId,
			clazz, classPK);
	}

	@Override
	public com.liferay.friendly.url.model.FriendlyURLEntry getMainFriendlyURLEntry(
		long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLEntryLocalService.getMainFriendlyURLEntry(groupId,
			classNameId, classPK);
	}

	/**
	* Updates the friendly url entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLEntry the friendly url entry
	* @return the friendly url entry that was updated
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURLEntry updateFriendlyURLEntry(
		com.liferay.friendly.url.model.FriendlyURLEntry friendlyURLEntry) {
		return _friendlyURLEntryLocalService.updateFriendlyURLEntry(friendlyURLEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _friendlyURLEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _friendlyURLEntryLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _friendlyURLEntryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _friendlyURLEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of friendly url entries.
	*
	* @return the number of friendly url entries
	*/
	@Override
	public int getFriendlyURLEntriesCount() {
		return _friendlyURLEntryLocalService.getFriendlyURLEntriesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _friendlyURLEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.lang.String getUniqueUrlTitle(long groupId, long classNameId,
		long classPK, java.lang.String urlTitle) {
		return _friendlyURLEntryLocalService.getUniqueUrlTitle(groupId,
			classNameId, classPK, urlTitle);
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
		return _friendlyURLEntryLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _friendlyURLEntryLocalService.dynamicQuery(dynamicQuery, start,
			end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _friendlyURLEntryLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
	@Override
	public java.util.List<com.liferay.friendly.url.model.FriendlyURLEntry> getFriendlyURLEntries(
		int start, int end) {
		return _friendlyURLEntryLocalService.getFriendlyURLEntries(start, end);
	}

	@Override
	public java.util.List<com.liferay.friendly.url.model.FriendlyURLEntry> getFriendlyURLEntries(
		long groupId, long classNameId, long classPK) {
		return _friendlyURLEntryLocalService.getFriendlyURLEntries(groupId,
			classNameId, classPK);
	}

	/**
	* Returns all the friendly url entries matching the UUID and company.
	*
	* @param uuid the UUID of the friendly url entries
	* @param companyId the primary key of the company
	* @return the matching friendly url entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.friendly.url.model.FriendlyURLEntry> getFriendlyURLEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _friendlyURLEntryLocalService.getFriendlyURLEntriesByUuidAndCompanyId(uuid,
			companyId);
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
	@Override
	public java.util.List<com.liferay.friendly.url.model.FriendlyURLEntry> getFriendlyURLEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.friendly.url.model.FriendlyURLEntry> orderByComparator) {
		return _friendlyURLEntryLocalService.getFriendlyURLEntriesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
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
		return _friendlyURLEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _friendlyURLEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public void deleteFriendlyURLEntry(long groupId, java.lang.Class<?> clazz,
		long classPK) {
		_friendlyURLEntryLocalService.deleteFriendlyURLEntry(groupId, clazz,
			classPK);
	}

	@Override
	public void deleteFriendlyURLEntry(long groupId, java.lang.Class<?> clazz,
		long classPK, java.lang.String urlTitle)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLEntryException {
		_friendlyURLEntryLocalService.deleteFriendlyURLEntry(groupId, clazz,
			classPK, urlTitle);
	}

	@Override
	public void deleteFriendlyURLEntry(long groupId, long classNameId,
		long classPK, java.lang.String urlTitle)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLEntryException {
		_friendlyURLEntryLocalService.deleteFriendlyURLEntry(groupId,
			classNameId, classPK, urlTitle);
	}

	@Override
	public void deleteGroupFriendlyURLEntries(long groupId, long classNameId) {
		_friendlyURLEntryLocalService.deleteGroupFriendlyURLEntries(groupId,
			classNameId);
	}

	@Override
	public void validate(long groupId, long classNameId,
		java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		_friendlyURLEntryLocalService.validate(groupId, classNameId, urlTitle);
	}

	@Override
	public void validate(long groupId, long classNameId, long classPK,
		java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		_friendlyURLEntryLocalService.validate(groupId, classNameId, classPK,
			urlTitle);
	}

	@Override
	public FriendlyURLEntryLocalService getWrappedService() {
		return _friendlyURLEntryLocalService;
	}

	@Override
	public void setWrappedService(
		FriendlyURLEntryLocalService friendlyURLEntryLocalService) {
		_friendlyURLEntryLocalService = friendlyURLEntryLocalService;
	}

	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;
}