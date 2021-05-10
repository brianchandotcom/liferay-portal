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
 * Provides a wrapper for {@link FrontendViewStateEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see FrontendViewStateEntryLocalService
 * @generated
 */
public class FrontendViewStateEntryLocalServiceWrapper
	implements FrontendViewStateEntryLocalService,
			   ServiceWrapper<FrontendViewStateEntryLocalService> {

	public FrontendViewStateEntryLocalServiceWrapper(
		FrontendViewStateEntryLocalService frontendViewStateEntryLocalService) {

		_frontendViewStateEntryLocalService =
			frontendViewStateEntryLocalService;
	}

	/**
	 * Adds the frontend view state entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FrontendViewStateEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param frontendViewStateEntry the frontend view state entry
	 * @return the frontend view state entry that was added
	 */
	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateEntry
		addFrontendViewStateEntry(
			com.liferay.frontend.view.state.model.FrontendViewStateEntry
				frontendViewStateEntry) {

		return _frontendViewStateEntryLocalService.addFrontendViewStateEntry(
			frontendViewStateEntry);
	}

	/**
	 * Creates a new frontend view state entry with the primary key. Does not add the frontend view state entry to the database.
	 *
	 * @param frontendViewStateEntryId the primary key for the new frontend view state entry
	 * @return the new frontend view state entry
	 */
	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateEntry
		createFrontendViewStateEntry(long frontendViewStateEntryId) {

		return _frontendViewStateEntryLocalService.createFrontendViewStateEntry(
			frontendViewStateEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _frontendViewStateEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the frontend view state entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FrontendViewStateEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param frontendViewStateEntry the frontend view state entry
	 * @return the frontend view state entry that was removed
	 */
	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateEntry
		deleteFrontendViewStateEntry(
			com.liferay.frontend.view.state.model.FrontendViewStateEntry
				frontendViewStateEntry) {

		return _frontendViewStateEntryLocalService.deleteFrontendViewStateEntry(
			frontendViewStateEntry);
	}

	/**
	 * Deletes the frontend view state entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FrontendViewStateEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param frontendViewStateEntryId the primary key of the frontend view state entry
	 * @return the frontend view state entry that was removed
	 * @throws PortalException if a frontend view state entry with the primary key could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateEntry
			deleteFrontendViewStateEntry(long frontendViewStateEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _frontendViewStateEntryLocalService.deleteFrontendViewStateEntry(
			frontendViewStateEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _frontendViewStateEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _frontendViewStateEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _frontendViewStateEntryLocalService.dynamicQuery();
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

		return _frontendViewStateEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FrontendViewStateEntryModelImpl</code>.
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

		return _frontendViewStateEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FrontendViewStateEntryModelImpl</code>.
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

		return _frontendViewStateEntryLocalService.dynamicQuery(
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

		return _frontendViewStateEntryLocalService.dynamicQueryCount(
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

		return _frontendViewStateEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateEntry
		fetchFrontendViewStateEntry(long frontendViewStateEntryId) {

		return _frontendViewStateEntryLocalService.fetchFrontendViewStateEntry(
			frontendViewStateEntryId);
	}

	/**
	 * Returns the frontend view state entry with the matching UUID and company.
	 *
	 * @param uuid the frontend view state entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching frontend view state entry, or <code>null</code> if a matching frontend view state entry could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateEntry
		fetchFrontendViewStateEntryByUuidAndCompanyId(
			String uuid, long companyId) {

		return _frontendViewStateEntryLocalService.
			fetchFrontendViewStateEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _frontendViewStateEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _frontendViewStateEntryLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	 * Returns a range of all the frontend view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @return the range of frontend view state entries
	 */
	@Override
	public java.util.List
		<com.liferay.frontend.view.state.model.FrontendViewStateEntry>
			getFrontendViewStateEntries(int start, int end) {

		return _frontendViewStateEntryLocalService.getFrontendViewStateEntries(
			start, end);
	}

	/**
	 * Returns the number of frontend view state entries.
	 *
	 * @return the number of frontend view state entries
	 */
	@Override
	public int getFrontendViewStateEntriesCount() {
		return _frontendViewStateEntryLocalService.
			getFrontendViewStateEntriesCount();
	}

	/**
	 * Returns the frontend view state entry with the primary key.
	 *
	 * @param frontendViewStateEntryId the primary key of the frontend view state entry
	 * @return the frontend view state entry
	 * @throws PortalException if a frontend view state entry with the primary key could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateEntry
			getFrontendViewStateEntry(long frontendViewStateEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _frontendViewStateEntryLocalService.getFrontendViewStateEntry(
			frontendViewStateEntryId);
	}

	/**
	 * Returns the frontend view state entry with the matching UUID and company.
	 *
	 * @param uuid the frontend view state entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching frontend view state entry
	 * @throws PortalException if a matching frontend view state entry could not be found
	 */
	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateEntry
			getFrontendViewStateEntryByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _frontendViewStateEntryLocalService.
			getFrontendViewStateEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _frontendViewStateEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _frontendViewStateEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _frontendViewStateEntryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the frontend view state entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FrontendViewStateEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param frontendViewStateEntry the frontend view state entry
	 * @return the frontend view state entry that was updated
	 */
	@Override
	public com.liferay.frontend.view.state.model.FrontendViewStateEntry
		updateFrontendViewStateEntry(
			com.liferay.frontend.view.state.model.FrontendViewStateEntry
				frontendViewStateEntry) {

		return _frontendViewStateEntryLocalService.updateFrontendViewStateEntry(
			frontendViewStateEntry);
	}

	@Override
	public FrontendViewStateEntryLocalService getWrappedService() {
		return _frontendViewStateEntryLocalService;
	}

	@Override
	public void setWrappedService(
		FrontendViewStateEntryLocalService frontendViewStateEntryLocalService) {

		_frontendViewStateEntryLocalService =
			frontendViewStateEntryLocalService;
	}

	private FrontendViewStateEntryLocalService
		_frontendViewStateEntryLocalService;

}