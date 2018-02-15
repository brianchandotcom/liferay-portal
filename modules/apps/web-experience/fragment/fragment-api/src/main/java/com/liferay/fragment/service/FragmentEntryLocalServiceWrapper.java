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

package com.liferay.fragment.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FragmentEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryLocalService
 * @generated
 */
@ProviderType
public class FragmentEntryLocalServiceWrapper
	implements FragmentEntryLocalService,
		ServiceWrapper<FragmentEntryLocalService> {
	public FragmentEntryLocalServiceWrapper(
		FragmentEntryLocalService fragmentEntryLocalService) {
		_fragmentEntryLocalService = fragmentEntryLocalService;
	}

	/**
	* Adds the fragment entry to the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntry the fragment entry
	* @return the fragment entry that was added
	*/
	@Override
	public com.liferay.fragment.model.FragmentEntry addFragmentEntry(
		com.liferay.fragment.model.FragmentEntry fragmentEntry) {
		return _fragmentEntryLocalService.addFragmentEntry(fragmentEntry);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry addFragmentEntry(
		long userId, long groupId, long fragmentCollectionId,
		java.lang.String name, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLocalService.addFragmentEntry(userId, groupId,
			fragmentCollectionId, name, status, serviceContext);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry addFragmentEntry(
		long userId, long groupId, long fragmentCollectionId,
		java.lang.String fragmentEntryKey, java.lang.String name, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLocalService.addFragmentEntry(userId, groupId,
			fragmentCollectionId, fragmentEntryKey, name, status, serviceContext);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry addFragmentEntry(
		long userId, long groupId, long fragmentCollectionId,
		java.lang.String name, java.lang.String css, java.lang.String html,
		java.lang.String js, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLocalService.addFragmentEntry(userId, groupId,
			fragmentCollectionId, name, css, html, js, status, serviceContext);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry addFragmentEntry(
		long userId, long groupId, long fragmentCollectionId,
		java.lang.String fragmentEntryKey, java.lang.String name,
		java.lang.String css, java.lang.String html, java.lang.String js,
		int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLocalService.addFragmentEntry(userId, groupId,
			fragmentCollectionId, fragmentEntryKey, name, css, html, js,
			status, serviceContext);
	}

	/**
	* Creates a new fragment entry with the primary key. Does not add the fragment entry to the database.
	*
	* @param fragmentEntryId the primary key for the new fragment entry
	* @return the new fragment entry
	*/
	@Override
	public com.liferay.fragment.model.FragmentEntry createFragmentEntry(
		long fragmentEntryId) {
		return _fragmentEntryLocalService.createFragmentEntry(fragmentEntryId);
	}

	/**
	* Deletes the fragment entry from the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntry the fragment entry
	* @return the fragment entry that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.fragment.model.FragmentEntry deleteFragmentEntry(
		com.liferay.fragment.model.FragmentEntry fragmentEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLocalService.deleteFragmentEntry(fragmentEntry);
	}

	/**
	* Deletes the fragment entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntryId the primary key of the fragment entry
	* @return the fragment entry that was removed
	* @throws PortalException if a fragment entry with the primary key could not be found
	*/
	@Override
	public com.liferay.fragment.model.FragmentEntry deleteFragmentEntry(
		long fragmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLocalService.deleteFragmentEntry(fragmentEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _fragmentEntryLocalService.dynamicQuery();
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
		return _fragmentEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.fragment.model.impl.FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _fragmentEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.fragment.model.impl.FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _fragmentEntryLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _fragmentEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _fragmentEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry fetchFragmentEntry(
		long fragmentEntryId) {
		return _fragmentEntryLocalService.fetchFragmentEntry(fragmentEntryId);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry fetchFragmentEntry(
		long groupId, java.lang.String fragmentEntryKey) {
		return _fragmentEntryLocalService.fetchFragmentEntry(groupId,
			fragmentEntryKey);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _fragmentEntryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the fragment entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.fragment.model.impl.FragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of fragment entries
	* @param end the upper bound of the range of fragment entries (not inclusive)
	* @return the range of fragment entries
	*/
	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry> getFragmentEntries(
		int start, int end) {
		return _fragmentEntryLocalService.getFragmentEntries(start, end);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry> getFragmentEntries(
		long fragmentCollectionId) {
		return _fragmentEntryLocalService.getFragmentEntries(fragmentCollectionId);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry> getFragmentEntries(
		long fragmentCollectionId, int status) {
		return _fragmentEntryLocalService.getFragmentEntries(fragmentCollectionId,
			status);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry> getFragmentEntries(
		long fragmentCollectionId, int start, int end) {
		return _fragmentEntryLocalService.getFragmentEntries(fragmentCollectionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.fragment.model.FragmentEntry> orderByComparator) {
		return _fragmentEntryLocalService.getFragmentEntries(groupId,
			fragmentCollectionId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.fragment.model.FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.fragment.model.FragmentEntry> orderByComparator) {
		return _fragmentEntryLocalService.getFragmentEntries(groupId,
			fragmentCollectionId, name, start, end, orderByComparator);
	}

	/**
	* Returns the number of fragment entries.
	*
	* @return the number of fragment entries
	*/
	@Override
	public int getFragmentEntriesCount() {
		return _fragmentEntryLocalService.getFragmentEntriesCount();
	}

	@Override
	public int getFragmentEntriesCount(long fragmentCollectionId) {
		return _fragmentEntryLocalService.getFragmentEntriesCount(fragmentCollectionId);
	}

	/**
	* Returns the fragment entry with the primary key.
	*
	* @param fragmentEntryId the primary key of the fragment entry
	* @return the fragment entry
	* @throws PortalException if a fragment entry with the primary key could not be found
	*/
	@Override
	public com.liferay.fragment.model.FragmentEntry getFragmentEntry(
		long fragmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLocalService.getFragmentEntry(fragmentEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _fragmentEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _fragmentEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.lang.String[] getTempFileNames(long userId, long groupId,
		java.lang.String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLocalService.getTempFileNames(userId, groupId,
			folderName);
	}

	/**
	* Updates the fragment entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param fragmentEntry the fragment entry
	* @return the fragment entry that was updated
	*/
	@Override
	public com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
		com.liferay.fragment.model.FragmentEntry fragmentEntry) {
		return _fragmentEntryLocalService.updateFragmentEntry(fragmentEntry);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
		long userId, long fragmentEntryId, java.lang.String name,
		java.lang.String css, java.lang.String html, java.lang.String js,
		int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLocalService.updateFragmentEntry(userId,
			fragmentEntryId, name, css, html, js, status, serviceContext);
	}

	@Override
	public com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
		long fragmentEntryId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryLocalService.updateFragmentEntry(fragmentEntryId,
			name);
	}

	@Override
	public FragmentEntryLocalService getWrappedService() {
		return _fragmentEntryLocalService;
	}

	@Override
	public void setWrappedService(
		FragmentEntryLocalService fragmentEntryLocalService) {
		_fragmentEntryLocalService = fragmentEntryLocalService;
	}

	private FragmentEntryLocalService _fragmentEntryLocalService;
}