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

package com.liferay.frontend.view.state.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FrontendViewStateActiveEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see FrontendViewStateActiveEntryLocalService
 * @generated
 */
public class FrontendViewStateActiveEntryLocalServiceWrapper
	implements FrontendViewStateActiveEntryLocalService,
			   ServiceWrapper<FrontendViewStateActiveEntryLocalService> {

	public FrontendViewStateActiveEntryLocalServiceWrapper(
		FrontendViewStateActiveEntryLocalService
			frontendViewStateActiveEntryLocalService) {

		_frontendViewStateActiveEntryLocalService =
			frontendViewStateActiveEntryLocalService;
	}

	/**
	 * Adds the frontend view state active entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FrontendViewStateActiveEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param frontendViewStateActiveEntry the frontend view state active entry
	 * @return the frontend view state active entry that was added
	 */
	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry
		addFrontendViewStateActiveEntry(
			com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry
				frontendViewStateActiveEntry) {

		return _frontendViewStateActiveEntryLocalService.
			addFrontendViewStateActiveEntry(frontendViewStateActiveEntry);
	}

	/**
	 * Creates a new frontend view state active entry with the primary key. Does not add the frontend view state active entry to the database.
	 *
	 * @param frontendViewStateActiveEntryId the primary key for the new frontend view state active entry
	 * @return the new frontend view state active entry
	 */
	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry
		createFrontendViewStateActiveEntry(
			long frontendViewStateActiveEntryId) {

		return _frontendViewStateActiveEntryLocalService.
			createFrontendViewStateActiveEntry(frontendViewStateActiveEntryId);
	}

	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry
		createFrontendViewStateActiveEntry(
			String datasetDisplayId, long frontendViewStateEntryId, long plid,
			String portletId, long userId) {

		return _frontendViewStateActiveEntryLocalService.
			createFrontendViewStateActiveEntry(
				datasetDisplayId, frontendViewStateEntryId, plid, portletId,
				userId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _frontendViewStateActiveEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the frontend view state active entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FrontendViewStateActiveEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param frontendViewStateActiveEntry the frontend view state active entry
	 * @return the frontend view state active entry that was removed
	 */
	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry
		deleteFrontendViewStateActiveEntry(
			com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry
				frontendViewStateActiveEntry) {

		return _frontendViewStateActiveEntryLocalService.
			deleteFrontendViewStateActiveEntry(frontendViewStateActiveEntry);
	}

	/**
	 * Deletes the frontend view state active entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FrontendViewStateActiveEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param frontendViewStateActiveEntryId the primary key of the frontend view state active entry
	 * @return the frontend view state active entry that was removed
	 * @throws PortalException if a frontend view state active entry with the primary key could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry
			deleteFrontendViewStateActiveEntry(
				long frontendViewStateActiveEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _frontendViewStateActiveEntryLocalService.
			deleteFrontendViewStateActiveEntry(frontendViewStateActiveEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _frontendViewStateActiveEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _frontendViewStateActiveEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _frontendViewStateActiveEntryLocalService.dynamicQuery();
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

		return _frontendViewStateActiveEntryLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FrontendViewStateActiveEntryModelImpl</code>.
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

		return _frontendViewStateActiveEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FrontendViewStateActiveEntryModelImpl</code>.
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

		return _frontendViewStateActiveEntryLocalService.dynamicQuery(
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

		return _frontendViewStateActiveEntryLocalService.dynamicQueryCount(
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

		return _frontendViewStateActiveEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry
		fetchFrontendViewStateActiveEntry(long frontendViewStateActiveEntryId) {

		return _frontendViewStateActiveEntryLocalService.
			fetchFrontendViewStateActiveEntry(frontendViewStateActiveEntryId);
	}

	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry
		fetchFrontendViewStateActiveEntry(
			String datasetDisplayId, long plid, String portletId, long userId) {

		return _frontendViewStateActiveEntryLocalService.
			fetchFrontendViewStateActiveEntry(
				datasetDisplayId, plid, portletId, userId);
	}

	/**
	 * Returns the frontend view state active entry with the matching UUID and company.
	 *
	 * @param uuid the frontend view state active entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching frontend view state active entry, or <code>null</code> if a matching frontend view state active entry could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry
		fetchFrontendViewStateActiveEntryByUuidAndCompanyId(
			String uuid, long companyId) {

		return _frontendViewStateActiveEntryLocalService.
			fetchFrontendViewStateActiveEntryByUuidAndCompanyId(
				uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _frontendViewStateActiveEntryLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _frontendViewStateActiveEntryLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	 * Returns a range of all the frontend view state active entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FrontendViewStateActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of frontend view state active entries
	 * @param end the upper bound of the range of frontend view state active entries (not inclusive)
	 * @return the range of frontend view state active entries
	 */
	@Override
	public java.util.List
		<com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry>
			getFrontendViewStateActiveEntries(int start, int end) {

		return _frontendViewStateActiveEntryLocalService.
			getFrontendViewStateActiveEntries(start, end);
	}

	/**
	 * Returns the number of frontend view state active entries.
	 *
	 * @return the number of frontend view state active entries
	 */
	@Override
	public int getFrontendViewStateActiveEntriesCount() {
		return _frontendViewStateActiveEntryLocalService.
			getFrontendViewStateActiveEntriesCount();
	}

	/**
	 * Returns the frontend view state active entry with the primary key.
	 *
	 * @param frontendViewStateActiveEntryId the primary key of the frontend view state active entry
	 * @return the frontend view state active entry
	 * @throws PortalException if a frontend view state active entry with the primary key could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry
			getFrontendViewStateActiveEntry(long frontendViewStateActiveEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _frontendViewStateActiveEntryLocalService.
			getFrontendViewStateActiveEntry(frontendViewStateActiveEntryId);
	}

	/**
	 * Returns the frontend view state active entry with the matching UUID and company.
	 *
	 * @param uuid the frontend view state active entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching frontend view state active entry
	 * @throws PortalException if a matching frontend view state active entry could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry
			getFrontendViewStateActiveEntryByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _frontendViewStateActiveEntryLocalService.
			getFrontendViewStateActiveEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _frontendViewStateActiveEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _frontendViewStateActiveEntryLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _frontendViewStateActiveEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the frontend view state active entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FrontendViewStateActiveEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param frontendViewStateActiveEntry the frontend view state active entry
	 * @return the frontend view state active entry that was updated
	 */
	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry
		updateFrontendViewStateActiveEntry(
			com.liferay.frontend.view.state.model.FrontendViewStateActiveEntry
				frontendViewStateActiveEntry) {

		return _frontendViewStateActiveEntryLocalService.
			updateFrontendViewStateActiveEntry(frontendViewStateActiveEntry);
	}

	@Override
	public FrontendViewStateActiveEntryLocalService getWrappedService() {
		return _frontendViewStateActiveEntryLocalService;
	}

	@Override
	public void setWrappedService(
		FrontendViewStateActiveEntryLocalService
			frontendViewStateActiveEntryLocalService) {

		_frontendViewStateActiveEntryLocalService =
			frontendViewStateActiveEntryLocalService;
	}

	private FrontendViewStateActiveEntryLocalService
		_frontendViewStateActiveEntryLocalService;

}