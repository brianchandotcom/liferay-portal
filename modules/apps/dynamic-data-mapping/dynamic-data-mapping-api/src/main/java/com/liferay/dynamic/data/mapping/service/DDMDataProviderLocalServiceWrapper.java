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

package com.liferay.dynamic.data.mapping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMDataProviderLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderLocalService
 * @generated
 */
@ProviderType
public class DDMDataProviderLocalServiceWrapper
	implements DDMDataProviderLocalService,
		ServiceWrapper<DDMDataProviderLocalService> {
	public DDMDataProviderLocalServiceWrapper(
		DDMDataProviderLocalService ddmDataProviderLocalService) {
		_ddmDataProviderLocalService = ddmDataProviderLocalService;
	}

	/**
	* Adds the d d m data provider to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmDataProvider the d d m data provider
	* @return the d d m data provider that was added
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider addDDMDataProvider(
		com.liferay.dynamic.data.mapping.model.DDMDataProvider ddmDataProvider) {
		return _ddmDataProviderLocalService.addDDMDataProvider(ddmDataProvider);
	}

	/**
	* Creates a new d d m data provider with the primary key. Does not add the d d m data provider to the database.
	*
	* @param dataProviderId the primary key for the new d d m data provider
	* @return the new d d m data provider
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider createDDMDataProvider(
		long dataProviderId) {
		return _ddmDataProviderLocalService.createDDMDataProvider(dataProviderId);
	}

	/**
	* Deletes the d d m data provider with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param dataProviderId the primary key of the d d m data provider
	* @return the d d m data provider that was removed
	* @throws PortalException if a d d m data provider with the primary key could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider deleteDDMDataProvider(
		long dataProviderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderLocalService.deleteDDMDataProvider(dataProviderId);
	}

	/**
	* Deletes the d d m data provider from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmDataProvider the d d m data provider
	* @return the d d m data provider that was removed
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider deleteDDMDataProvider(
		com.liferay.dynamic.data.mapping.model.DDMDataProvider ddmDataProvider) {
		return _ddmDataProviderLocalService.deleteDDMDataProvider(ddmDataProvider);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ddmDataProviderLocalService.dynamicQuery();
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
		return _ddmDataProviderLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ddmDataProviderLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ddmDataProviderLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _ddmDataProviderLocalService.dynamicQueryCount(dynamicQuery);
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
		return _ddmDataProviderLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider fetchDDMDataProvider(
		long dataProviderId) {
		return _ddmDataProviderLocalService.fetchDDMDataProvider(dataProviderId);
	}

	/**
	* Returns the d d m data provider matching the UUID and group.
	*
	* @param uuid the d d m data provider's UUID
	* @param groupId the primary key of the group
	* @return the matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider fetchDDMDataProviderByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _ddmDataProviderLocalService.fetchDDMDataProviderByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _ddmDataProviderLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the d d m data provider with the primary key.
	*
	* @param dataProviderId the primary key of the d d m data provider
	* @return the d d m data provider
	* @throws PortalException if a d d m data provider with the primary key could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider getDDMDataProvider(
		long dataProviderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderLocalService.getDDMDataProvider(dataProviderId);
	}

	/**
	* Returns the d d m data provider matching the UUID and group.
	*
	* @param uuid the d d m data provider's UUID
	* @param groupId the primary key of the group
	* @return the matching d d m data provider
	* @throws PortalException if a matching d d m data provider could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider getDDMDataProviderByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderLocalService.getDDMDataProviderByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the d d m data providers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @return the range of d d m data providers
	*/
	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProvider> getDDMDataProviders(
		int start, int end) {
		return _ddmDataProviderLocalService.getDDMDataProviders(start, end);
	}

	/**
	* Returns all the d d m data providers matching the UUID and company.
	*
	* @param uuid the UUID of the d d m data providers
	* @param companyId the primary key of the company
	* @return the matching d d m data providers, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProvider> getDDMDataProvidersByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _ddmDataProviderLocalService.getDDMDataProvidersByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of d d m data providers matching the UUID and company.
	*
	* @param uuid the UUID of the d d m data providers
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching d d m data providers, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProvider> getDDMDataProvidersByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMDataProvider> orderByComparator) {
		return _ddmDataProviderLocalService.getDDMDataProvidersByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of d d m data providers.
	*
	* @return the number of d d m data providers
	*/
	@Override
	public int getDDMDataProvidersCount() {
		return _ddmDataProviderLocalService.getDDMDataProvidersCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return _ddmDataProviderLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _ddmDataProviderLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the d d m data provider in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmDataProvider the d d m data provider
	* @return the d d m data provider that was updated
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProvider updateDDMDataProvider(
		com.liferay.dynamic.data.mapping.model.DDMDataProvider ddmDataProvider) {
		return _ddmDataProviderLocalService.updateDDMDataProvider(ddmDataProvider);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public DDMDataProviderLocalService getWrappedDDMDataProviderLocalService() {
		return _ddmDataProviderLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedDDMDataProviderLocalService(
		DDMDataProviderLocalService ddmDataProviderLocalService) {
		_ddmDataProviderLocalService = ddmDataProviderLocalService;
	}

	@Override
	public DDMDataProviderLocalService getWrappedService() {
		return _ddmDataProviderLocalService;
	}

	@Override
	public void setWrappedService(
		DDMDataProviderLocalService ddmDataProviderLocalService) {
		_ddmDataProviderLocalService = ddmDataProviderLocalService;
	}

	private DDMDataProviderLocalService _ddmDataProviderLocalService;
}