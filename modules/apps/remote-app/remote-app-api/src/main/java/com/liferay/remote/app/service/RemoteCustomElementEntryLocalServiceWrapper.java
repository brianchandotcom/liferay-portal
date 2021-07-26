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

package com.liferay.remote.app.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link RemoteCustomElementEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see RemoteCustomElementEntryLocalService
 * @generated
 */
public class RemoteCustomElementEntryLocalServiceWrapper
	implements RemoteCustomElementEntryLocalService,
			   ServiceWrapper<RemoteCustomElementEntryLocalService> {

	public RemoteCustomElementEntryLocalServiceWrapper(
		RemoteCustomElementEntryLocalService
			remoteCustomElementEntryLocalService) {

		_remoteCustomElementEntryLocalService =
			remoteCustomElementEntryLocalService;
	}

	@Override
	public com.liferay.remote.app.model.RemoteCustomElementEntry
			addRemoteCustomElementEntry(
				long userId, java.util.Map<java.util.Locale, String> nameMap,
				String tagName, String url,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteCustomElementEntryLocalService.
			addRemoteCustomElementEntry(
				userId, nameMap, tagName, url, serviceContext);
	}

	/**
	 * Adds the remote custom element entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteCustomElementEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param remoteCustomElementEntry the remote custom element entry
	 * @return the remote custom element entry that was added
	 */
	@Override
	public com.liferay.remote.app.model.RemoteCustomElementEntry
		addRemoteCustomElementEntry(
			com.liferay.remote.app.model.RemoteCustomElementEntry
				remoteCustomElementEntry) {

		return _remoteCustomElementEntryLocalService.
			addRemoteCustomElementEntry(remoteCustomElementEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteCustomElementEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new remote custom element entry with the primary key. Does not add the remote custom element entry to the database.
	 *
	 * @param remoteCustomElementEntryId the primary key for the new remote custom element entry
	 * @return the new remote custom element entry
	 */
	@Override
	public com.liferay.remote.app.model.RemoteCustomElementEntry
		createRemoteCustomElementEntry(long remoteCustomElementEntryId) {

		return _remoteCustomElementEntryLocalService.
			createRemoteCustomElementEntry(remoteCustomElementEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteCustomElementEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the remote custom element entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteCustomElementEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param remoteCustomElementEntryId the primary key of the remote custom element entry
	 * @return the remote custom element entry that was removed
	 * @throws PortalException if a remote custom element entry with the primary key could not be found
	 */
	@Override
	public com.liferay.remote.app.model.RemoteCustomElementEntry
			deleteRemoteCustomElementEntry(long remoteCustomElementEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteCustomElementEntryLocalService.
			deleteRemoteCustomElementEntry(remoteCustomElementEntryId);
	}

	/**
	 * Deletes the remote custom element entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteCustomElementEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param remoteCustomElementEntry the remote custom element entry
	 * @return the remote custom element entry that was removed
	 */
	@Override
	public com.liferay.remote.app.model.RemoteCustomElementEntry
		deleteRemoteCustomElementEntry(
			com.liferay.remote.app.model.RemoteCustomElementEntry
				remoteCustomElementEntry) {

		return _remoteCustomElementEntryLocalService.
			deleteRemoteCustomElementEntry(remoteCustomElementEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _remoteCustomElementEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _remoteCustomElementEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _remoteCustomElementEntryLocalService.dynamicQuery();
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

		return _remoteCustomElementEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.app.model.impl.RemoteCustomElementEntryModelImpl</code>.
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

		return _remoteCustomElementEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.app.model.impl.RemoteCustomElementEntryModelImpl</code>.
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

		return _remoteCustomElementEntryLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

		return _remoteCustomElementEntryLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _remoteCustomElementEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.remote.app.model.RemoteCustomElementEntry
		fetchRemoteCustomElementEntry(long remoteCustomElementEntryId) {

		return _remoteCustomElementEntryLocalService.
			fetchRemoteCustomElementEntry(remoteCustomElementEntryId);
	}

	/**
	 * Returns the remote custom element entry with the matching UUID and company.
	 *
	 * @param uuid the remote custom element entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching remote custom element entry, or <code>null</code> if a matching remote custom element entry could not be found
	 */
	@Override
	public com.liferay.remote.app.model.RemoteCustomElementEntry
		fetchRemoteCustomElementEntryByUuidAndCompanyId(
			String uuid, long companyId) {

		return _remoteCustomElementEntryLocalService.
			fetchRemoteCustomElementEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _remoteCustomElementEntryLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _remoteCustomElementEntryLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _remoteCustomElementEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _remoteCustomElementEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteCustomElementEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Returns a range of all the remote custom element entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.app.model.impl.RemoteCustomElementEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote custom element entries
	 * @param end the upper bound of the range of remote custom element entries (not inclusive)
	 * @return the range of remote custom element entries
	 */
	@Override
	public java.util.List<com.liferay.remote.app.model.RemoteCustomElementEntry>
		getRemoteCustomElementEntries(int start, int end) {

		return _remoteCustomElementEntryLocalService.
			getRemoteCustomElementEntries(start, end);
	}

	/**
	 * Returns the number of remote custom element entries.
	 *
	 * @return the number of remote custom element entries
	 */
	@Override
	public int getRemoteCustomElementEntriesCount() {
		return _remoteCustomElementEntryLocalService.
			getRemoteCustomElementEntriesCount();
	}

	/**
	 * Returns the remote custom element entry with the primary key.
	 *
	 * @param remoteCustomElementEntryId the primary key of the remote custom element entry
	 * @return the remote custom element entry
	 * @throws PortalException if a remote custom element entry with the primary key could not be found
	 */
	@Override
	public com.liferay.remote.app.model.RemoteCustomElementEntry
			getRemoteCustomElementEntry(long remoteCustomElementEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteCustomElementEntryLocalService.
			getRemoteCustomElementEntry(remoteCustomElementEntryId);
	}

	/**
	 * Returns the remote custom element entry with the matching UUID and company.
	 *
	 * @param uuid the remote custom element entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching remote custom element entry
	 * @throws PortalException if a matching remote custom element entry could not be found
	 */
	@Override
	public com.liferay.remote.app.model.RemoteCustomElementEntry
			getRemoteCustomElementEntryByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteCustomElementEntryLocalService.
			getRemoteCustomElementEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public java.util.List<com.liferay.remote.app.model.RemoteCustomElementEntry>
			searchRemoteCustomElementEntries(
				long companyId, String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteCustomElementEntryLocalService.
			searchRemoteCustomElementEntries(
				companyId, keywords, start, end, sort);
	}

	@Override
	public int searchRemoteCustomElementEntriesCount(
			long companyId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteCustomElementEntryLocalService.
			searchRemoteCustomElementEntriesCount(companyId, keywords);
	}

	@Override
	public com.liferay.remote.app.model.RemoteCustomElementEntry
			updateRemoteCustomElementEntry(
				long remoteCustomElementEntryId,
				java.util.Map<java.util.Locale, String> nameMap, String tagName,
				String url,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteCustomElementEntryLocalService.
			updateRemoteCustomElementEntry(
				remoteCustomElementEntryId, nameMap, tagName, url,
				serviceContext);
	}

	/**
	 * Updates the remote custom element entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteCustomElementEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param remoteCustomElementEntry the remote custom element entry
	 * @return the remote custom element entry that was updated
	 */
	@Override
	public com.liferay.remote.app.model.RemoteCustomElementEntry
		updateRemoteCustomElementEntry(
			com.liferay.remote.app.model.RemoteCustomElementEntry
				remoteCustomElementEntry) {

		return _remoteCustomElementEntryLocalService.
			updateRemoteCustomElementEntry(remoteCustomElementEntry);
	}

	@Override
	public RemoteCustomElementEntryLocalService getWrappedService() {
		return _remoteCustomElementEntryLocalService;
	}

	@Override
	public void setWrappedService(
		RemoteCustomElementEntryLocalService
			remoteCustomElementEntryLocalService) {

		_remoteCustomElementEntryLocalService =
			remoteCustomElementEntryLocalService;
	}

	private RemoteCustomElementEntryLocalService
		_remoteCustomElementEntryLocalService;

}