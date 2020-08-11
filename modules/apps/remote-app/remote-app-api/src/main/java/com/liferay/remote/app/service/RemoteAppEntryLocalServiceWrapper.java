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
 * Provides a wrapper for {@link RemoteAppEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see RemoteAppEntryLocalService
 * @generated
 */
public class RemoteAppEntryLocalServiceWrapper
	implements RemoteAppEntryLocalService,
			   ServiceWrapper<RemoteAppEntryLocalService> {

	public RemoteAppEntryLocalServiceWrapper(
		RemoteAppEntryLocalService remoteAppEntryLocalService) {

		_remoteAppEntryLocalService = remoteAppEntryLocalService;
	}

	@Override
	public com.liferay.remote.app.model.RemoteAppEntry addRemoteAppEntry(
			long userId, java.util.Map<java.util.Locale, String> nameMap,
			String url,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteAppEntryLocalService.addRemoteAppEntry(
			userId, nameMap, url, serviceContext);
	}

	/**
	 * Adds the remote app entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteAppEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param remoteAppEntry the remote app entry
	 * @return the remote app entry that was added
	 */
	@Override
	public com.liferay.remote.app.model.RemoteAppEntry addRemoteAppEntry(
		com.liferay.remote.app.model.RemoteAppEntry remoteAppEntry) {

		return _remoteAppEntryLocalService.addRemoteAppEntry(remoteAppEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteAppEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new remote app entry with the primary key. Does not add the remote app entry to the database.
	 *
	 * @param entryId the primary key for the new remote app entry
	 * @return the new remote app entry
	 */
	@Override
	public com.liferay.remote.app.model.RemoteAppEntry createRemoteAppEntry(
		long entryId) {

		return _remoteAppEntryLocalService.createRemoteAppEntry(entryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteAppEntryLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the remote app entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteAppEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param entryId the primary key of the remote app entry
	 * @return the remote app entry that was removed
	 * @throws PortalException if a remote app entry with the primary key could not be found
	 */
	@Override
	public com.liferay.remote.app.model.RemoteAppEntry deleteRemoteAppEntry(
			long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteAppEntryLocalService.deleteRemoteAppEntry(entryId);
	}

	/**
	 * Deletes the remote app entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteAppEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param remoteAppEntry the remote app entry
	 * @return the remote app entry that was removed
	 */
	@Override
	public com.liferay.remote.app.model.RemoteAppEntry deleteRemoteAppEntry(
		com.liferay.remote.app.model.RemoteAppEntry remoteAppEntry) {

		return _remoteAppEntryLocalService.deleteRemoteAppEntry(remoteAppEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _remoteAppEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _remoteAppEntryLocalService.dynamicQuery();
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

		return _remoteAppEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.app.model.impl.RemoteAppEntryModelImpl</code>.
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

		return _remoteAppEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.app.model.impl.RemoteAppEntryModelImpl</code>.
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

		return _remoteAppEntryLocalService.dynamicQuery(
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

		return _remoteAppEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _remoteAppEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.remote.app.model.RemoteAppEntry fetchRemoteAppEntry(
		long entryId) {

		return _remoteAppEntryLocalService.fetchRemoteAppEntry(entryId);
	}

	/**
	 * Returns the remote app entry with the matching UUID and company.
	 *
	 * @param uuid the remote app entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching remote app entry, or <code>null</code> if a matching remote app entry could not be found
	 */
	@Override
	public com.liferay.remote.app.model.RemoteAppEntry
		fetchRemoteAppEntryByUuidAndCompanyId(String uuid, long companyId) {

		return _remoteAppEntryLocalService.
			fetchRemoteAppEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _remoteAppEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _remoteAppEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _remoteAppEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _remoteAppEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteAppEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the remote app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.app.model.impl.RemoteAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote app entries
	 * @param end the upper bound of the range of remote app entries (not inclusive)
	 * @return the range of remote app entries
	 */
	@Override
	public java.util.List<com.liferay.remote.app.model.RemoteAppEntry>
		getRemoteAppEntries(int start, int end) {

		return _remoteAppEntryLocalService.getRemoteAppEntries(start, end);
	}

	/**
	 * Returns the number of remote app entries.
	 *
	 * @return the number of remote app entries
	 */
	@Override
	public int getRemoteAppEntriesCount() {
		return _remoteAppEntryLocalService.getRemoteAppEntriesCount();
	}

	/**
	 * Returns the remote app entry with the primary key.
	 *
	 * @param entryId the primary key of the remote app entry
	 * @return the remote app entry
	 * @throws PortalException if a remote app entry with the primary key could not be found
	 */
	@Override
	public com.liferay.remote.app.model.RemoteAppEntry getRemoteAppEntry(
			long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteAppEntryLocalService.getRemoteAppEntry(entryId);
	}

	/**
	 * Returns the remote app entry with the matching UUID and company.
	 *
	 * @param uuid the remote app entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching remote app entry
	 * @throws PortalException if a matching remote app entry could not be found
	 */
	@Override
	public com.liferay.remote.app.model.RemoteAppEntry
			getRemoteAppEntryByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteAppEntryLocalService.getRemoteAppEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.remote.app.model.RemoteAppEntry updateRemoteAppEntry(
			long remoteAppEntryId,
			java.util.Map<java.util.Locale, String> nameMap, String url,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteAppEntryLocalService.updateRemoteAppEntry(
			remoteAppEntryId, nameMap, url, serviceContext);
	}

	/**
	 * Updates the remote app entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteAppEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param remoteAppEntry the remote app entry
	 * @return the remote app entry that was updated
	 */
	@Override
	public com.liferay.remote.app.model.RemoteAppEntry updateRemoteAppEntry(
		com.liferay.remote.app.model.RemoteAppEntry remoteAppEntry) {

		return _remoteAppEntryLocalService.updateRemoteAppEntry(remoteAppEntry);
	}

	@Override
	public RemoteAppEntryLocalService getWrappedService() {
		return _remoteAppEntryLocalService;
	}

	@Override
	public void setWrappedService(
		RemoteAppEntryLocalService remoteAppEntryLocalService) {

		_remoteAppEntryLocalService = remoteAppEntryLocalService;
	}

	private RemoteAppEntryLocalService _remoteAppEntryLocalService;

}