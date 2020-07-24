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

package com.liferay.remote.web.app.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link RemoteWebAppEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see RemoteWebAppEntryLocalService
 * @generated
 */
public class RemoteWebAppEntryLocalServiceWrapper
	implements RemoteWebAppEntryLocalService,
			   ServiceWrapper<RemoteWebAppEntryLocalService> {

	public RemoteWebAppEntryLocalServiceWrapper(
		RemoteWebAppEntryLocalService remoteWebAppEntryLocalService) {

		_remoteWebAppEntryLocalService = remoteWebAppEntryLocalService;
	}

	@Override
	public com.liferay.remote.web.app.model.RemoteWebAppEntry addEntry(
			long userId, java.util.Map<java.util.Locale, String> nameMap,
			String url,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteWebAppEntryLocalService.addEntry(
			userId, nameMap, url, serviceContext);
	}

	/**
	 * Adds the remote web app entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteWebAppEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param remoteWebAppEntry the remote web app entry
	 * @return the remote web app entry that was added
	 */
	@Override
	public com.liferay.remote.web.app.model.RemoteWebAppEntry
		addRemoteWebAppEntry(
			com.liferay.remote.web.app.model.RemoteWebAppEntry
				remoteWebAppEntry) {

		return _remoteWebAppEntryLocalService.addRemoteWebAppEntry(
			remoteWebAppEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteWebAppEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new remote web app entry with the primary key. Does not add the remote web app entry to the database.
	 *
	 * @param entryId the primary key for the new remote web app entry
	 * @return the new remote web app entry
	 */
	@Override
	public com.liferay.remote.web.app.model.RemoteWebAppEntry
		createRemoteWebAppEntry(long entryId) {

		return _remoteWebAppEntryLocalService.createRemoteWebAppEntry(entryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteWebAppEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the remote web app entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteWebAppEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param entryId the primary key of the remote web app entry
	 * @return the remote web app entry that was removed
	 * @throws PortalException if a remote web app entry with the primary key could not be found
	 */
	@Override
	public com.liferay.remote.web.app.model.RemoteWebAppEntry
			deleteRemoteWebAppEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteWebAppEntryLocalService.deleteRemoteWebAppEntry(entryId);
	}

	/**
	 * Deletes the remote web app entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteWebAppEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param remoteWebAppEntry the remote web app entry
	 * @return the remote web app entry that was removed
	 */
	@Override
	public com.liferay.remote.web.app.model.RemoteWebAppEntry
		deleteRemoteWebAppEntry(
			com.liferay.remote.web.app.model.RemoteWebAppEntry
				remoteWebAppEntry) {

		return _remoteWebAppEntryLocalService.deleteRemoteWebAppEntry(
			remoteWebAppEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _remoteWebAppEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _remoteWebAppEntryLocalService.dynamicQuery();
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

		return _remoteWebAppEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.web.app.model.impl.RemoteWebAppEntryModelImpl</code>.
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

		return _remoteWebAppEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.web.app.model.impl.RemoteWebAppEntryModelImpl</code>.
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

		return _remoteWebAppEntryLocalService.dynamicQuery(
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

		return _remoteWebAppEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _remoteWebAppEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.remote.web.app.model.RemoteWebAppEntry
		fetchRemoteWebAppEntry(long entryId) {

		return _remoteWebAppEntryLocalService.fetchRemoteWebAppEntry(entryId);
	}

	/**
	 * Returns the remote web app entry with the matching UUID and company.
	 *
	 * @param uuid the remote web app entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching remote web app entry, or <code>null</code> if a matching remote web app entry could not be found
	 */
	@Override
	public com.liferay.remote.web.app.model.RemoteWebAppEntry
		fetchRemoteWebAppEntryByUuidAndCompanyId(String uuid, long companyId) {

		return _remoteWebAppEntryLocalService.
			fetchRemoteWebAppEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _remoteWebAppEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _remoteWebAppEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _remoteWebAppEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _remoteWebAppEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteWebAppEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the remote web app entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.web.app.model.impl.RemoteWebAppEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote web app entries
	 * @param end the upper bound of the range of remote web app entries (not inclusive)
	 * @return the range of remote web app entries
	 */
	@Override
	public java.util.List<com.liferay.remote.web.app.model.RemoteWebAppEntry>
		getRemoteWebAppEntries(int start, int end) {

		return _remoteWebAppEntryLocalService.getRemoteWebAppEntries(
			start, end);
	}

	/**
	 * Returns the number of remote web app entries.
	 *
	 * @return the number of remote web app entries
	 */
	@Override
	public int getRemoteWebAppEntriesCount() {
		return _remoteWebAppEntryLocalService.getRemoteWebAppEntriesCount();
	}

	/**
	 * Returns the remote web app entry with the primary key.
	 *
	 * @param entryId the primary key of the remote web app entry
	 * @return the remote web app entry
	 * @throws PortalException if a remote web app entry with the primary key could not be found
	 */
	@Override
	public com.liferay.remote.web.app.model.RemoteWebAppEntry
			getRemoteWebAppEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteWebAppEntryLocalService.getRemoteWebAppEntry(entryId);
	}

	/**
	 * Returns the remote web app entry with the matching UUID and company.
	 *
	 * @param uuid the remote web app entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching remote web app entry
	 * @throws PortalException if a matching remote web app entry could not be found
	 */
	@Override
	public com.liferay.remote.web.app.model.RemoteWebAppEntry
			getRemoteWebAppEntryByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteWebAppEntryLocalService.
			getRemoteWebAppEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.remote.web.app.model.RemoteWebAppEntry updateEntry(
			long entryId, java.util.Map<java.util.Locale, String> nameMap,
			String url,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteWebAppEntryLocalService.updateEntry(
			entryId, nameMap, url, serviceContext);
	}

	/**
	 * Updates the remote web app entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RemoteWebAppEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param remoteWebAppEntry the remote web app entry
	 * @return the remote web app entry that was updated
	 */
	@Override
	public com.liferay.remote.web.app.model.RemoteWebAppEntry
		updateRemoteWebAppEntry(
			com.liferay.remote.web.app.model.RemoteWebAppEntry
				remoteWebAppEntry) {

		return _remoteWebAppEntryLocalService.updateRemoteWebAppEntry(
			remoteWebAppEntry);
	}

	@Override
	public RemoteWebAppEntryLocalService getWrappedService() {
		return _remoteWebAppEntryLocalService;
	}

	@Override
	public void setWrappedService(
		RemoteWebAppEntryLocalService remoteWebAppEntryLocalService) {

		_remoteWebAppEntryLocalService = remoteWebAppEntryLocalService;
	}

	private RemoteWebAppEntryLocalService _remoteWebAppEntryLocalService;

}