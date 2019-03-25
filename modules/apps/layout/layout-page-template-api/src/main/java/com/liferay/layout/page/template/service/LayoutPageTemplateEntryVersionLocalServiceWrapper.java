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

package com.liferay.layout.page.template.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LayoutPageTemplateEntryVersionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateEntryVersionLocalService
 * @generated
 */
@ProviderType
public class LayoutPageTemplateEntryVersionLocalServiceWrapper
	implements LayoutPageTemplateEntryVersionLocalService,
			   ServiceWrapper<LayoutPageTemplateEntryVersionLocalService> {

	public LayoutPageTemplateEntryVersionLocalServiceWrapper(
		LayoutPageTemplateEntryVersionLocalService
			layoutPageTemplateEntryVersionLocalService) {

		_layoutPageTemplateEntryVersionLocalService =
			layoutPageTemplateEntryVersionLocalService;
	}

	/**
	 * Adds the layout page template entry version to the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateEntryVersion the layout page template entry version
	 * @return the layout page template entry version that was added
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntryVersion
		addLayoutPageTemplateEntryVersion(
			com.liferay.layout.page.template.model.
				LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion) {

		return _layoutPageTemplateEntryVersionLocalService.
			addLayoutPageTemplateEntryVersion(layoutPageTemplateEntryVersion);
	}

	/**
	 * Creates a new layout page template entry version with the primary key. Does not add the layout page template entry version to the database.
	 *
	 * @param layoutPageTemplateEntryVersionId the primary key for the new layout page template entry version
	 * @return the new layout page template entry version
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntryVersion
		createLayoutPageTemplateEntryVersion(
			long layoutPageTemplateEntryVersionId) {

		return _layoutPageTemplateEntryVersionLocalService.
			createLayoutPageTemplateEntryVersion(
				layoutPageTemplateEntryVersionId);
	}

	/**
	 * Deletes the layout page template entry version from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateEntryVersion the layout page template entry version
	 * @return the layout page template entry version that was removed
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntryVersion
		deleteLayoutPageTemplateEntryVersion(
			com.liferay.layout.page.template.model.
				LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion) {

		return _layoutPageTemplateEntryVersionLocalService.
			deleteLayoutPageTemplateEntryVersion(
				layoutPageTemplateEntryVersion);
	}

	/**
	 * Deletes the layout page template entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateEntryVersionId the primary key of the layout page template entry version
	 * @return the layout page template entry version that was removed
	 * @throws PortalException if a layout page template entry version with the primary key could not be found
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntryVersion
			deleteLayoutPageTemplateEntryVersion(
				long layoutPageTemplateEntryVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryVersionLocalService.
			deleteLayoutPageTemplateEntryVersion(
				layoutPageTemplateEntryVersionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryVersionLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _layoutPageTemplateEntryVersionLocalService.dynamicQuery();
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

		return _layoutPageTemplateEntryVersionLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _layoutPageTemplateEntryVersionLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _layoutPageTemplateEntryVersionLocalService.dynamicQuery(
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

		return _layoutPageTemplateEntryVersionLocalService.dynamicQueryCount(
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

		return _layoutPageTemplateEntryVersionLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntryVersion
		fetchLayoutPageTemplateEntryVersion(
			long layoutPageTemplateEntryVersionId) {

		return _layoutPageTemplateEntryVersionLocalService.
			fetchLayoutPageTemplateEntryVersion(
				layoutPageTemplateEntryVersionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _layoutPageTemplateEntryVersionLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _layoutPageTemplateEntryVersionLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the layout page template entry version with the primary key.
	 *
	 * @param layoutPageTemplateEntryVersionId the primary key of the layout page template entry version
	 * @return the layout page template entry version
	 * @throws PortalException if a layout page template entry version with the primary key could not be found
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntryVersion
			getLayoutPageTemplateEntryVersion(
				long layoutPageTemplateEntryVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryVersionLocalService.
			getLayoutPageTemplateEntryVersion(layoutPageTemplateEntryVersionId);
	}

	/**
	 * Returns a range of all the layout page template entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateEntryVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template entry versions
	 * @param end the upper bound of the range of layout page template entry versions (not inclusive)
	 * @return the range of layout page template entry versions
	 */
	@Override
	public java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateEntryVersion>
			getLayoutPageTemplateEntryVersions(int start, int end) {

		return _layoutPageTemplateEntryVersionLocalService.
			getLayoutPageTemplateEntryVersions(start, end);
	}

	/**
	 * Returns the number of layout page template entry versions.
	 *
	 * @return the number of layout page template entry versions
	 */
	@Override
	public int getLayoutPageTemplateEntryVersionsCount() {
		return _layoutPageTemplateEntryVersionLocalService.
			getLayoutPageTemplateEntryVersionsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutPageTemplateEntryVersionLocalService.
			getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateEntryVersionLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the layout page template entry version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateEntryVersion the layout page template entry version
	 * @return the layout page template entry version that was updated
	 */
	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntryVersion
		updateLayoutPageTemplateEntryVersion(
			com.liferay.layout.page.template.model.
				LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion) {

		return _layoutPageTemplateEntryVersionLocalService.
			updateLayoutPageTemplateEntryVersion(
				layoutPageTemplateEntryVersion);
	}

	@Override
	public LayoutPageTemplateEntryVersionLocalService getWrappedService() {
		return _layoutPageTemplateEntryVersionLocalService;
	}

	@Override
	public void setWrappedService(
		LayoutPageTemplateEntryVersionLocalService
			layoutPageTemplateEntryVersionLocalService) {

		_layoutPageTemplateEntryVersionLocalService =
			layoutPageTemplateEntryVersionLocalService;
	}

	private LayoutPageTemplateEntryVersionLocalService
		_layoutPageTemplateEntryVersionLocalService;

}