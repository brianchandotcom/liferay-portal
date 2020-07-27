/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.app.builder.workflow.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AppBuilderWorkflowTaskLinkLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderWorkflowTaskLinkLocalService
 * @generated
 */
public class AppBuilderWorkflowTaskLinkLocalServiceWrapper
	implements AppBuilderWorkflowTaskLinkLocalService,
			   ServiceWrapper<AppBuilderWorkflowTaskLinkLocalService> {

	public AppBuilderWorkflowTaskLinkLocalServiceWrapper(
		AppBuilderWorkflowTaskLinkLocalService
			appBuilderWorkflowTaskLinkLocalService) {

		_appBuilderWorkflowTaskLinkLocalService =
			appBuilderWorkflowTaskLinkLocalService;
	}

	/**
	 * Adds the app builder workflow task link to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderWorkflowTaskLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderWorkflowTaskLink the app builder workflow task link
	 * @return the app builder workflow task link that was added
	 */
	@Override
	public com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink
		addAppBuilderWorkflowTaskLink(
			com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink
				appBuilderWorkflowTaskLink) {

		return _appBuilderWorkflowTaskLinkLocalService.
			addAppBuilderWorkflowTaskLink(appBuilderWorkflowTaskLink);
	}

	@Override
	public com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink
			addAppBuilderWorkflowTaskLink(
				long companyId, long appBuilderAppId,
				long appBuilderAppVersionId, long ddmStructureLayoutId,
				boolean readOnly, String workflowTaskName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderWorkflowTaskLinkLocalService.
			addAppBuilderWorkflowTaskLink(
				companyId, appBuilderAppId, appBuilderAppVersionId,
				ddmStructureLayoutId, readOnly, workflowTaskName);
	}

	/**
	 * Creates a new app builder workflow task link with the primary key. Does not add the app builder workflow task link to the database.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key for the new app builder workflow task link
	 * @return the new app builder workflow task link
	 */
	@Override
	public com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink
		createAppBuilderWorkflowTaskLink(long appBuilderWorkflowTaskLinkId) {

		return _appBuilderWorkflowTaskLinkLocalService.
			createAppBuilderWorkflowTaskLink(appBuilderWorkflowTaskLinkId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderWorkflowTaskLinkLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the app builder workflow task link from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderWorkflowTaskLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderWorkflowTaskLink the app builder workflow task link
	 * @return the app builder workflow task link that was removed
	 */
	@Override
	public com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink
		deleteAppBuilderWorkflowTaskLink(
			com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink
				appBuilderWorkflowTaskLink) {

		return _appBuilderWorkflowTaskLinkLocalService.
			deleteAppBuilderWorkflowTaskLink(appBuilderWorkflowTaskLink);
	}

	/**
	 * Deletes the app builder workflow task link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderWorkflowTaskLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key of the app builder workflow task link
	 * @return the app builder workflow task link that was removed
	 * @throws PortalException if a app builder workflow task link with the primary key could not be found
	 */
	@Override
	public com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink
			deleteAppBuilderWorkflowTaskLink(long appBuilderWorkflowTaskLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderWorkflowTaskLinkLocalService.
			deleteAppBuilderWorkflowTaskLink(appBuilderWorkflowTaskLinkId);
	}

	@Override
	public void deleteAppBuilderWorkflowTaskLinks(long appBuilderAppId) {
		_appBuilderWorkflowTaskLinkLocalService.
			deleteAppBuilderWorkflowTaskLinks(appBuilderAppId);
	}

	@Override
	public void deleteAppBuilderWorkflowTaskLinks(
		long appBuilderAppId, long appBuilderAppVersionId) {

		_appBuilderWorkflowTaskLinkLocalService.
			deleteAppBuilderWorkflowTaskLinks(
				appBuilderAppId, appBuilderAppVersionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderWorkflowTaskLinkLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _appBuilderWorkflowTaskLinkLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _appBuilderWorkflowTaskLinkLocalService.dynamicQuery();
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

		return _appBuilderWorkflowTaskLinkLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.workflow.model.impl.AppBuilderWorkflowTaskLinkModelImpl</code>.
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

		return _appBuilderWorkflowTaskLinkLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.workflow.model.impl.AppBuilderWorkflowTaskLinkModelImpl</code>.
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

		return _appBuilderWorkflowTaskLinkLocalService.dynamicQuery(
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

		return _appBuilderWorkflowTaskLinkLocalService.dynamicQueryCount(
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

		return _appBuilderWorkflowTaskLinkLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink
		fetchAppBuilderWorkflowTaskLink(long appBuilderWorkflowTaskLinkId) {

		return _appBuilderWorkflowTaskLinkLocalService.
			fetchAppBuilderWorkflowTaskLink(appBuilderWorkflowTaskLinkId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _appBuilderWorkflowTaskLinkLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the app builder workflow task link with the primary key.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key of the app builder workflow task link
	 * @return the app builder workflow task link
	 * @throws PortalException if a app builder workflow task link with the primary key could not be found
	 */
	@Override
	public com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink
			getAppBuilderWorkflowTaskLink(long appBuilderWorkflowTaskLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderWorkflowTaskLinkLocalService.
			getAppBuilderWorkflowTaskLink(appBuilderWorkflowTaskLinkId);
	}

	/**
	 * Returns a range of all the app builder workflow task links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.workflow.model.impl.AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @return the range of app builder workflow task links
	 */
	@Override
	public java.util.List
		<com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink>
			getAppBuilderWorkflowTaskLinks(int start, int end) {

		return _appBuilderWorkflowTaskLinkLocalService.
			getAppBuilderWorkflowTaskLinks(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink>
			getAppBuilderWorkflowTaskLinks(long appBuilderAppId) {

		return _appBuilderWorkflowTaskLinkLocalService.
			getAppBuilderWorkflowTaskLinks(appBuilderAppId);
	}

	@Override
	public java.util.List
		<com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink>
			getAppBuilderWorkflowTaskLinks(
				long appBuilderAppId, long appBuilderAppVersionId) {

		return _appBuilderWorkflowTaskLinkLocalService.
			getAppBuilderWorkflowTaskLinks(
				appBuilderAppId, appBuilderAppVersionId);
	}

	@Override
	public java.util.List
		<com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink>
			getAppBuilderWorkflowTaskLinks(
				long appBuilderAppId, long appBuilderAppVersionId,
				String workflowTaskName) {

		return _appBuilderWorkflowTaskLinkLocalService.
			getAppBuilderWorkflowTaskLinks(
				appBuilderAppId, appBuilderAppVersionId, workflowTaskName);
	}

	/**
	 * Returns the number of app builder workflow task links.
	 *
	 * @return the number of app builder workflow task links
	 */
	@Override
	public int getAppBuilderWorkflowTaskLinksCount() {
		return _appBuilderWorkflowTaskLinkLocalService.
			getAppBuilderWorkflowTaskLinksCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _appBuilderWorkflowTaskLinkLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _appBuilderWorkflowTaskLinkLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _appBuilderWorkflowTaskLinkLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the app builder workflow task link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderWorkflowTaskLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderWorkflowTaskLink the app builder workflow task link
	 * @return the app builder workflow task link that was updated
	 */
	@Override
	public com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink
		updateAppBuilderWorkflowTaskLink(
			com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink
				appBuilderWorkflowTaskLink) {

		return _appBuilderWorkflowTaskLinkLocalService.
			updateAppBuilderWorkflowTaskLink(appBuilderWorkflowTaskLink);
	}

	@Override
	public AppBuilderWorkflowTaskLinkLocalService getWrappedService() {
		return _appBuilderWorkflowTaskLinkLocalService;
	}

	@Override
	public void setWrappedService(
		AppBuilderWorkflowTaskLinkLocalService
			appBuilderWorkflowTaskLinkLocalService) {

		_appBuilderWorkflowTaskLinkLocalService =
			appBuilderWorkflowTaskLinkLocalService;
	}

	private AppBuilderWorkflowTaskLinkLocalService
		_appBuilderWorkflowTaskLinkLocalService;

}