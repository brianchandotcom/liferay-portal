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

package com.liferay.app.builder.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AppBuilderAppDataRecordLinkLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppDataRecordLinkLocalService
 * @generated
 */
public class AppBuilderAppDataRecordLinkLocalServiceWrapper
	implements AppBuilderAppDataRecordLinkLocalService,
			   ServiceWrapper<AppBuilderAppDataRecordLinkLocalService> {

	public AppBuilderAppDataRecordLinkLocalServiceWrapper(
		AppBuilderAppDataRecordLinkLocalService
			appBuilderAppDataRecordLinkLocalService) {

		_appBuilderAppDataRecordLinkLocalService =
			appBuilderAppDataRecordLinkLocalService;
	}

	/**
	 * Adds the app builder app data record link to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppDataRecordLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderAppDataRecordLink the app builder app data record link
	 * @return the app builder app data record link that was added
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppDataRecordLink
		addAppBuilderAppDataRecordLink(
			com.liferay.app.builder.model.AppBuilderAppDataRecordLink
				appBuilderAppDataRecordLink) {

		return _appBuilderAppDataRecordLinkLocalService.
			addAppBuilderAppDataRecordLink(appBuilderAppDataRecordLink);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addAppBuilderAppDataRecordLink(long, long, long, long, long)}
	 */
	@Deprecated
	@Override
	public com.liferay.app.builder.model.AppBuilderAppDataRecordLink
		addAppBuilderAppDataRecordLink(
			long companyId, long appBuilderAppId, long ddlRecordId) {

		return _appBuilderAppDataRecordLinkLocalService.
			addAppBuilderAppDataRecordLink(
				companyId, appBuilderAppId, ddlRecordId);
	}

	@Override
	public com.liferay.app.builder.model.AppBuilderAppDataRecordLink
		addAppBuilderAppDataRecordLink(
			long groupId, long companyId, long appBuilderAppId,
			long appBuilderAppVersionId, long ddlRecordId) {

		return _appBuilderAppDataRecordLinkLocalService.
			addAppBuilderAppDataRecordLink(
				groupId, companyId, appBuilderAppId, appBuilderAppVersionId,
				ddlRecordId);
	}

	/**
	 * Creates a new app builder app data record link with the primary key. Does not add the app builder app data record link to the database.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key for the new app builder app data record link
	 * @return the new app builder app data record link
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppDataRecordLink
		createAppBuilderAppDataRecordLink(long appBuilderAppDataRecordLinkId) {

		return _appBuilderAppDataRecordLinkLocalService.
			createAppBuilderAppDataRecordLink(appBuilderAppDataRecordLinkId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppDataRecordLinkLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the app builder app data record link from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppDataRecordLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderAppDataRecordLink the app builder app data record link
	 * @return the app builder app data record link that was removed
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppDataRecordLink
		deleteAppBuilderAppDataRecordLink(
			com.liferay.app.builder.model.AppBuilderAppDataRecordLink
				appBuilderAppDataRecordLink) {

		return _appBuilderAppDataRecordLinkLocalService.
			deleteAppBuilderAppDataRecordLink(appBuilderAppDataRecordLink);
	}

	/**
	 * Deletes the app builder app data record link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppDataRecordLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key of the app builder app data record link
	 * @return the app builder app data record link that was removed
	 * @throws PortalException if a app builder app data record link with the primary key could not be found
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppDataRecordLink
			deleteAppBuilderAppDataRecordLink(
				long appBuilderAppDataRecordLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppDataRecordLinkLocalService.
			deleteAppBuilderAppDataRecordLink(appBuilderAppDataRecordLinkId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppDataRecordLinkLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _appBuilderAppDataRecordLinkLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _appBuilderAppDataRecordLinkLocalService.dynamicQuery();
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

		return _appBuilderAppDataRecordLinkLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppDataRecordLinkModelImpl</code>.
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

		return _appBuilderAppDataRecordLinkLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppDataRecordLinkModelImpl</code>.
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

		return _appBuilderAppDataRecordLinkLocalService.dynamicQuery(
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

		return _appBuilderAppDataRecordLinkLocalService.dynamicQueryCount(
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

		return _appBuilderAppDataRecordLinkLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.app.builder.model.AppBuilderAppDataRecordLink
		fetchAppBuilderAppDataRecordLink(long appBuilderAppDataRecordLinkId) {

		return _appBuilderAppDataRecordLinkLocalService.
			fetchAppBuilderAppDataRecordLink(appBuilderAppDataRecordLinkId);
	}

	@Override
	public com.liferay.app.builder.model.AppBuilderAppDataRecordLink
		fetchDDLRecordAppBuilderAppDataRecordLink(long ddlRecordId) {

		return _appBuilderAppDataRecordLinkLocalService.
			fetchDDLRecordAppBuilderAppDataRecordLink(ddlRecordId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _appBuilderAppDataRecordLinkLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the app builder app data record link with the primary key.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key of the app builder app data record link
	 * @return the app builder app data record link
	 * @throws PortalException if a app builder app data record link with the primary key could not be found
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppDataRecordLink
			getAppBuilderAppDataRecordLink(long appBuilderAppDataRecordLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppDataRecordLinkLocalService.
			getAppBuilderAppDataRecordLink(appBuilderAppDataRecordLinkId);
	}

	/**
	 * Returns a range of all the app builder app data record links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @return the range of app builder app data record links
	 */
	@Override
	public java.util.List
		<com.liferay.app.builder.model.AppBuilderAppDataRecordLink>
			getAppBuilderAppDataRecordLinks(int start, int end) {

		return _appBuilderAppDataRecordLinkLocalService.
			getAppBuilderAppDataRecordLinks(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.app.builder.model.AppBuilderAppDataRecordLink>
			getAppBuilderAppDataRecordLinks(long appBuilderAppId) {

		return _appBuilderAppDataRecordLinkLocalService.
			getAppBuilderAppDataRecordLinks(appBuilderAppId);
	}

	/**
	 * Returns the number of app builder app data record links.
	 *
	 * @return the number of app builder app data record links
	 */
	@Override
	public int getAppBuilderAppDataRecordLinksCount() {
		return _appBuilderAppDataRecordLinkLocalService.
			getAppBuilderAppDataRecordLinksCount();
	}

	@Override
	public com.liferay.app.builder.model.AppBuilderAppDataRecordLink
			getDDLRecordAppBuilderAppDataRecordLink(long ddlRecordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppDataRecordLinkLocalService.
			getDDLRecordAppBuilderAppDataRecordLink(ddlRecordId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _appBuilderAppDataRecordLinkLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _appBuilderAppDataRecordLinkLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderAppDataRecordLinkLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the app builder app data record link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppDataRecordLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderAppDataRecordLink the app builder app data record link
	 * @return the app builder app data record link that was updated
	 */
	@Override
	public com.liferay.app.builder.model.AppBuilderAppDataRecordLink
		updateAppBuilderAppDataRecordLink(
			com.liferay.app.builder.model.AppBuilderAppDataRecordLink
				appBuilderAppDataRecordLink) {

		return _appBuilderAppDataRecordLinkLocalService.
			updateAppBuilderAppDataRecordLink(appBuilderAppDataRecordLink);
	}

	@Override
	public AppBuilderAppDataRecordLinkLocalService getWrappedService() {
		return _appBuilderAppDataRecordLinkLocalService;
	}

	@Override
	public void setWrappedService(
		AppBuilderAppDataRecordLinkLocalService
			appBuilderAppDataRecordLinkLocalService) {

		_appBuilderAppDataRecordLinkLocalService =
			appBuilderAppDataRecordLinkLocalService;
	}

	private AppBuilderAppDataRecordLinkLocalService
		_appBuilderAppDataRecordLinkLocalService;

}