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

package com.liferay.data.engine.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides a wrapper for {@link DEListViewLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DEListViewLocalService
 * @generated
 */
@ProviderType
public class DEListViewLocalServiceWrapper
	implements DEListViewLocalService, ServiceWrapper<DEListViewLocalService> {

	public DEListViewLocalServiceWrapper(
		DEListViewLocalService deListViewLocalService) {

		_deListViewLocalService = deListViewLocalService;
	}

	/**
	 * Adds the de list view to the database. Also notifies the appropriate model listeners.
	 *
	 * @param deListView the de list view
	 * @return the de list view that was added
	 */
	@Override
	public com.liferay.data.engine.model.DEListView addDEListView(
		com.liferay.data.engine.model.DEListView deListView) {

		return _deListViewLocalService.addDEListView(deListView);
	}

	/**
	 * Creates a new de list view with the primary key. Does not add the de list view to the database.
	 *
	 * @param listViewId the primary key for the new de list view
	 * @return the new de list view
	 */
	@Override
	public com.liferay.data.engine.model.DEListView createDEListView(
		long listViewId) {

		return _deListViewLocalService.createDEListView(listViewId);
	}

	/**
	 * Deletes the de list view from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deListView the de list view
	 * @return the de list view that was removed
	 */
	@Override
	public com.liferay.data.engine.model.DEListView deleteDEListView(
		com.liferay.data.engine.model.DEListView deListView) {

		return _deListViewLocalService.deleteDEListView(deListView);
	}

	/**
	 * Deletes the de list view with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param listViewId the primary key of the de list view
	 * @return the de list view that was removed
	 * @throws PortalException if a de list view with the primary key could not be found
	 */
	@Override
	public com.liferay.data.engine.model.DEListView deleteDEListView(
			long listViewId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deListViewLocalService.deleteDEListView(listViewId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deListViewLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _deListViewLocalService.dynamicQuery();
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

		return _deListViewLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.data.engine.model.impl.DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _deListViewLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.data.engine.model.impl.DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _deListViewLocalService.dynamicQuery(
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

		return _deListViewLocalService.dynamicQueryCount(dynamicQuery);
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

		return _deListViewLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.data.engine.model.DEListView fetchDEListView(
		long listViewId) {

		return _deListViewLocalService.fetchDEListView(listViewId);
	}

	/**
	 * Returns the de list view matching the UUID and group.
	 *
	 * @param uuid the de list view's UUID
	 * @param groupId the primary key of the group
	 * @return the matching de list view, or <code>null</code> if a matching de list view could not be found
	 */
	@Override
	public com.liferay.data.engine.model.DEListView
		fetchDEListViewByUuidAndGroupId(String uuid, long groupId) {

		return _deListViewLocalService.fetchDEListViewByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _deListViewLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the de list view with the primary key.
	 *
	 * @param listViewId the primary key of the de list view
	 * @return the de list view
	 * @throws PortalException if a de list view with the primary key could not be found
	 */
	@Override
	public com.liferay.data.engine.model.DEListView getDEListView(
			long listViewId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deListViewLocalService.getDEListView(listViewId);
	}

	/**
	 * Returns the de list view matching the UUID and group.
	 *
	 * @param uuid the de list view's UUID
	 * @param groupId the primary key of the group
	 * @return the matching de list view
	 * @throws PortalException if a matching de list view could not be found
	 */
	@Override
	public com.liferay.data.engine.model.DEListView
			getDEListViewByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deListViewLocalService.getDEListViewByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the de list views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.data.engine.model.impl.DEListViewModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @return the range of de list views
	 */
	@Override
	public java.util.List<com.liferay.data.engine.model.DEListView>
		getDEListViews(int start, int end) {

		return _deListViewLocalService.getDEListViews(start, end);
	}

	/**
	 * Returns all the de list views matching the UUID and company.
	 *
	 * @param uuid the UUID of the de list views
	 * @param companyId the primary key of the company
	 * @return the matching de list views, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.data.engine.model.DEListView>
		getDEListViewsByUuidAndCompanyId(String uuid, long companyId) {

		return _deListViewLocalService.getDEListViewsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of de list views matching the UUID and company.
	 *
	 * @param uuid the UUID of the de list views
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of de list views
	 * @param end the upper bound of the range of de list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching de list views, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.data.engine.model.DEListView>
		getDEListViewsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.data.engine.model.DEListView> orderByComparator) {

		return _deListViewLocalService.getDEListViewsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of de list views.
	 *
	 * @return the number of de list views
	 */
	@Override
	public int getDEListViewsCount() {
		return _deListViewLocalService.getDEListViewsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _deListViewLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _deListViewLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _deListViewLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deListViewLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the de list view in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param deListView the de list view
	 * @return the de list view that was updated
	 */
	@Override
	public com.liferay.data.engine.model.DEListView updateDEListView(
		com.liferay.data.engine.model.DEListView deListView) {

		return _deListViewLocalService.updateDEListView(deListView);
	}

	@Override
	public DEListViewLocalService getWrappedService() {
		return _deListViewLocalService;
	}

	@Override
	public void setWrappedService(
		DEListViewLocalService deListViewLocalService) {

		_deListViewLocalService = deListViewLocalService;
	}

	private DEListViewLocalService _deListViewLocalService;

}