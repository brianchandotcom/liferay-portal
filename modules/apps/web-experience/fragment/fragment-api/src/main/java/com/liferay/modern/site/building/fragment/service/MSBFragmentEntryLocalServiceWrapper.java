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

package com.liferay.modern.site.building.fragment.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MSBFragmentEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see MSBFragmentEntryLocalService
 * @generated
 */
@ProviderType
public class MSBFragmentEntryLocalServiceWrapper
	implements MSBFragmentEntryLocalService,
		ServiceWrapper<MSBFragmentEntryLocalService> {
	public MSBFragmentEntryLocalServiceWrapper(
		MSBFragmentEntryLocalService msbFragmentEntryLocalService) {
		_msbFragmentEntryLocalService = msbFragmentEntryLocalService;
	}

	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentEntry addMSBFragmentEntry(
		long groupId, long userId, long msbFragmentCollectionId,
		java.lang.String name, java.lang.String css, java.lang.String html,
		java.lang.String js,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryLocalService.addMSBFragmentEntry(groupId,
			userId, msbFragmentCollectionId, name, css, html, js, serviceContext);
	}

	/**
	* Adds the msb fragment entry to the database. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentEntry the msb fragment entry
	* @return the msb fragment entry that was added
	*/
	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentEntry addMSBFragmentEntry(
		com.liferay.modern.site.building.fragment.model.MSBFragmentEntry msbFragmentEntry) {
		return _msbFragmentEntryLocalService.addMSBFragmentEntry(msbFragmentEntry);
	}

	/**
	* Creates a new msb fragment entry with the primary key. Does not add the msb fragment entry to the database.
	*
	* @param msbFragmentEntryId the primary key for the new msb fragment entry
	* @return the new msb fragment entry
	*/
	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentEntry createMSBFragmentEntry(
		long msbFragmentEntryId) {
		return _msbFragmentEntryLocalService.createMSBFragmentEntry(msbFragmentEntryId);
	}

	/**
	* Deletes the msb fragment entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentEntryId the primary key of the msb fragment entry
	* @return the msb fragment entry that was removed
	* @throws PortalException if a msb fragment entry with the primary key could not be found
	*/
	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentEntry deleteMSBFragmentEntry(
		long msbFragmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryLocalService.deleteMSBFragmentEntry(msbFragmentEntryId);
	}

	/**
	* Deletes the msb fragment entry from the database. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentEntry the msb fragment entry
	* @return the msb fragment entry that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentEntry deleteMSBFragmentEntry(
		com.liferay.modern.site.building.fragment.model.MSBFragmentEntry msbFragmentEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryLocalService.deleteMSBFragmentEntry(msbFragmentEntry);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _msbFragmentEntryLocalService.dynamicQuery();
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
		return _msbFragmentEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.modern.site.building.fragment.model.impl.MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _msbFragmentEntryLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.modern.site.building.fragment.model.impl.MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _msbFragmentEntryLocalService.dynamicQuery(dynamicQuery, start,
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
		return _msbFragmentEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _msbFragmentEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> fetchMSBFragmentEntries(
		long msbFragmentCollectionId) {
		return _msbFragmentEntryLocalService.fetchMSBFragmentEntries(msbFragmentCollectionId);
	}

	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentEntry fetchMSBFragmentEntry(
		long msbFragmentEntryId) {
		return _msbFragmentEntryLocalService.fetchMSBFragmentEntry(msbFragmentEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _msbFragmentEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _msbFragmentEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns a range of all the msb fragment entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.modern.site.building.fragment.model.impl.MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @return the range of msb fragment entries
	*/
	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getMSBFragmentEntries(
		int start, int end) {
		return _msbFragmentEntryLocalService.getMSBFragmentEntries(start, end);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getMSBFragmentEntries(
		long msbFragmentCollectionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryLocalService.getMSBFragmentEntries(msbFragmentCollectionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getMSBFragmentEntries(
		long groupId, long msbFragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryLocalService.getMSBFragmentEntries(groupId,
			msbFragmentCollectionId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getMSBFragmentEntries(
		long groupId, long msbFragmentCollectionId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> orderByComparator) {
		return _msbFragmentEntryLocalService.getMSBFragmentEntries(groupId,
			msbFragmentCollectionId, name, start, end, orderByComparator);
	}

	/**
	* Returns the number of msb fragment entries.
	*
	* @return the number of msb fragment entries
	*/
	@Override
	public int getMSBFragmentEntriesCount() {
		return _msbFragmentEntryLocalService.getMSBFragmentEntriesCount();
	}

	/**
	* Returns the msb fragment entry with the primary key.
	*
	* @param msbFragmentEntryId the primary key of the msb fragment entry
	* @return the msb fragment entry
	* @throws PortalException if a msb fragment entry with the primary key could not be found
	*/
	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentEntry getMSBFragmentEntry(
		long msbFragmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryLocalService.getMSBFragmentEntry(msbFragmentEntryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _msbFragmentEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentEntry updateMSBFragmentEntry(
		long msbFragmentEntryId, java.lang.String name, java.lang.String css,
		java.lang.String html, java.lang.String js)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _msbFragmentEntryLocalService.updateMSBFragmentEntry(msbFragmentEntryId,
			name, css, html, js);
	}

	/**
	* Updates the msb fragment entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentEntry the msb fragment entry
	* @return the msb fragment entry that was updated
	*/
	@Override
	public com.liferay.modern.site.building.fragment.model.MSBFragmentEntry updateMSBFragmentEntry(
		com.liferay.modern.site.building.fragment.model.MSBFragmentEntry msbFragmentEntry) {
		return _msbFragmentEntryLocalService.updateMSBFragmentEntry(msbFragmentEntry);
	}

	@Override
	public MSBFragmentEntryLocalService getWrappedService() {
		return _msbFragmentEntryLocalService;
	}

	@Override
	public void setWrappedService(
		MSBFragmentEntryLocalService msbFragmentEntryLocalService) {
		_msbFragmentEntryLocalService = msbFragmentEntryLocalService;
	}

	private MSBFragmentEntryLocalService _msbFragmentEntryLocalService;
}