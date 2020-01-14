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

package com.liferay.depot.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DepotAppCustomizationLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DepotAppCustomizationLocalService
 * @generated
 */
public class DepotAppCustomizationLocalServiceWrapper
	implements DepotAppCustomizationLocalService,
			   ServiceWrapper<DepotAppCustomizationLocalService> {

	public DepotAppCustomizationLocalServiceWrapper(
		DepotAppCustomizationLocalService depotAppCustomizationLocalService) {

		_depotAppCustomizationLocalService = depotAppCustomizationLocalService;
	}

	/**
	 * Adds the depot app customization to the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotAppCustomization the depot app customization
	 * @return the depot app customization that was added
	 */
	@Override
	public com.liferay.depot.model.DepotAppCustomization
		addDepotAppCustomization(
			com.liferay.depot.model.DepotAppCustomization
				depotAppCustomization) {

		return _depotAppCustomizationLocalService.addDepotAppCustomization(
			depotAppCustomization);
	}

	@Override
	public com.liferay.depot.model.DepotAppCustomization
		addDepotAppCustomization(
			long depotEntryId, String portletId, boolean enabled) {

		return _depotAppCustomizationLocalService.addDepotAppCustomization(
			depotEntryId, portletId, enabled);
	}

	/**
	 * Creates a new depot app customization with the primary key. Does not add the depot app customization to the database.
	 *
	 * @param depotAppCustomizationId the primary key for the new depot app customization
	 * @return the new depot app customization
	 */
	@Override
	public com.liferay.depot.model.DepotAppCustomization
		createDepotAppCustomization(long depotAppCustomizationId) {

		return _depotAppCustomizationLocalService.createDepotAppCustomization(
			depotAppCustomizationId);
	}

	/**
	 * Deletes the depot app customization from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotAppCustomization the depot app customization
	 * @return the depot app customization that was removed
	 */
	@Override
	public com.liferay.depot.model.DepotAppCustomization
		deleteDepotAppCustomization(
			com.liferay.depot.model.DepotAppCustomization
				depotAppCustomization) {

		return _depotAppCustomizationLocalService.deleteDepotAppCustomization(
			depotAppCustomization);
	}

	/**
	 * Deletes the depot app customization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotAppCustomizationId the primary key of the depot app customization
	 * @return the depot app customization that was removed
	 * @throws PortalException if a depot app customization with the primary key could not be found
	 */
	@Override
	public com.liferay.depot.model.DepotAppCustomization
			deleteDepotAppCustomization(long depotAppCustomizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotAppCustomizationLocalService.deleteDepotAppCustomization(
			depotAppCustomizationId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotAppCustomizationLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _depotAppCustomizationLocalService.dynamicQuery();
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

		return _depotAppCustomizationLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.depot.model.impl.DepotAppCustomizationModelImpl</code>.
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

		return _depotAppCustomizationLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.depot.model.impl.DepotAppCustomizationModelImpl</code>.
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

		return _depotAppCustomizationLocalService.dynamicQuery(
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

		return _depotAppCustomizationLocalService.dynamicQueryCount(
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

		return _depotAppCustomizationLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.depot.model.DepotAppCustomization
		fetchDepotAppCustomization(long depotAppCustomizationId) {

		return _depotAppCustomizationLocalService.fetchDepotAppCustomization(
			depotAppCustomizationId);
	}

	@Override
	public com.liferay.depot.model.DepotAppCustomization
		fetchDepotAppCustomization(long depotEntryId, String portletId) {

		return _depotAppCustomizationLocalService.fetchDepotAppCustomization(
			depotEntryId, portletId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _depotAppCustomizationLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the depot app customization with the primary key.
	 *
	 * @param depotAppCustomizationId the primary key of the depot app customization
	 * @return the depot app customization
	 * @throws PortalException if a depot app customization with the primary key could not be found
	 */
	@Override
	public com.liferay.depot.model.DepotAppCustomization
			getDepotAppCustomization(long depotAppCustomizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotAppCustomizationLocalService.getDepotAppCustomization(
			depotAppCustomizationId);
	}

	/**
	 * Returns a range of all the depot app customizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.depot.model.impl.DepotAppCustomizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot app customizations
	 * @param end the upper bound of the range of depot app customizations (not inclusive)
	 * @return the range of depot app customizations
	 */
	@Override
	public java.util.List<com.liferay.depot.model.DepotAppCustomization>
		getDepotAppCustomizations(int start, int end) {

		return _depotAppCustomizationLocalService.getDepotAppCustomizations(
			start, end);
	}

	/**
	 * Returns the number of depot app customizations.
	 *
	 * @return the number of depot app customizations
	 */
	@Override
	public int getDepotAppCustomizationsCount() {
		return _depotAppCustomizationLocalService.
			getDepotAppCustomizationsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _depotAppCustomizationLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _depotAppCustomizationLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotAppCustomizationLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the depot app customization in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param depotAppCustomization the depot app customization
	 * @return the depot app customization that was updated
	 */
	@Override
	public com.liferay.depot.model.DepotAppCustomization
		updateDepotAppCustomization(
			com.liferay.depot.model.DepotAppCustomization
				depotAppCustomization) {

		return _depotAppCustomizationLocalService.updateDepotAppCustomization(
			depotAppCustomization);
	}

	@Override
	public DepotAppCustomizationLocalService getWrappedService() {
		return _depotAppCustomizationLocalService;
	}

	@Override
	public void setWrappedService(
		DepotAppCustomizationLocalService depotAppCustomizationLocalService) {

		_depotAppCustomizationLocalService = depotAppCustomizationLocalService;
	}

	private DepotAppCustomizationLocalService
		_depotAppCustomizationLocalService;

}