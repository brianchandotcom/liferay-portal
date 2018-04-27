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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceRegionLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceRegionLocalService
 * @generated
 */
@ProviderType
public class CommerceRegionLocalServiceWrapper
	implements CommerceRegionLocalService,
		ServiceWrapper<CommerceRegionLocalService> {
	public CommerceRegionLocalServiceWrapper(
		CommerceRegionLocalService commerceRegionLocalService) {
		_commerceRegionLocalService = commerceRegionLocalService;
	}

	/**
	* Adds the commerce region to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceRegion the commerce region
	* @return the commerce region that was added
	*/
	@Override
	public com.liferay.commerce.model.CommerceRegion addCommerceRegion(
		com.liferay.commerce.model.CommerceRegion commerceRegion) {
		return _commerceRegionLocalService.addCommerceRegion(commerceRegion);
	}

	@Override
	public com.liferay.commerce.model.CommerceRegion addCommerceRegion(
		long commerceCountryId, String name, String code, double priority,
		boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceRegionLocalService.addCommerceRegion(commerceCountryId,
			name, code, priority, active, serviceContext);
	}

	/**
	* Creates a new commerce region with the primary key. Does not add the commerce region to the database.
	*
	* @param commerceRegionId the primary key for the new commerce region
	* @return the new commerce region
	*/
	@Override
	public com.liferay.commerce.model.CommerceRegion createCommerceRegion(
		long commerceRegionId) {
		return _commerceRegionLocalService.createCommerceRegion(commerceRegionId);
	}

	/**
	* Deletes the commerce region from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceRegion the commerce region
	* @return the commerce region that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.model.CommerceRegion deleteCommerceRegion(
		com.liferay.commerce.model.CommerceRegion commerceRegion)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceRegionLocalService.deleteCommerceRegion(commerceRegion);
	}

	/**
	* Deletes the commerce region with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceRegionId the primary key of the commerce region
	* @return the commerce region that was removed
	* @throws PortalException if a commerce region with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceRegion deleteCommerceRegion(
		long commerceRegionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceRegionLocalService.deleteCommerceRegion(commerceRegionId);
	}

	@Override
	public void deleteCommerceRegions(long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceRegionLocalService.deleteCommerceRegions(commerceCountryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceRegionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceRegionLocalService.dynamicQuery();
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
		return _commerceRegionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceRegionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceRegionLocalService.dynamicQuery(dynamicQuery, start,
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
		return _commerceRegionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceRegionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceRegion fetchCommerceRegion(
		long commerceRegionId) {
		return _commerceRegionLocalService.fetchCommerceRegion(commerceRegionId);
	}

	/**
	* Returns the commerce region matching the UUID and group.
	*
	* @param uuid the commerce region's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce region, or <code>null</code> if a matching commerce region could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceRegion fetchCommerceRegionByUuidAndGroupId(
		String uuid, long groupId) {
		return _commerceRegionLocalService.fetchCommerceRegionByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceRegionLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce region with the primary key.
	*
	* @param commerceRegionId the primary key of the commerce region
	* @return the commerce region
	* @throws PortalException if a commerce region with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceRegion getCommerceRegion(
		long commerceRegionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceRegionLocalService.getCommerceRegion(commerceRegionId);
	}

	/**
	* Returns the commerce region matching the UUID and group.
	*
	* @param uuid the commerce region's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce region
	* @throws PortalException if a matching commerce region could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceRegion getCommerceRegionByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceRegionLocalService.getCommerceRegionByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the commerce regions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @return the range of commerce regions
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceRegion> getCommerceRegions(
		int start, int end) {
		return _commerceRegionLocalService.getCommerceRegions(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceRegion> getCommerceRegions(
		long commerceCountryId, boolean active) {
		return _commerceRegionLocalService.getCommerceRegions(commerceCountryId,
			active);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceRegion> getCommerceRegions(
		long commerceCountryId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceRegion> orderByComparator) {
		return _commerceRegionLocalService.getCommerceRegions(commerceCountryId,
			active, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceRegion> getCommerceRegions(
		long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceRegion> orderByComparator) {
		return _commerceRegionLocalService.getCommerceRegions(commerceCountryId,
			start, end, orderByComparator);
	}

	/**
	* Returns all the commerce regions matching the UUID and company.
	*
	* @param uuid the UUID of the commerce regions
	* @param companyId the primary key of the company
	* @return the matching commerce regions, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceRegion> getCommerceRegionsByUuidAndCompanyId(
		String uuid, long companyId) {
		return _commerceRegionLocalService.getCommerceRegionsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce regions matching the UUID and company.
	*
	* @param uuid the UUID of the commerce regions
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce regions, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceRegion> getCommerceRegionsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceRegion> orderByComparator) {
		return _commerceRegionLocalService.getCommerceRegionsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce regions.
	*
	* @return the number of commerce regions
	*/
	@Override
	public int getCommerceRegionsCount() {
		return _commerceRegionLocalService.getCommerceRegionsCount();
	}

	@Override
	public int getCommerceRegionsCount(long commerceCountryId) {
		return _commerceRegionLocalService.getCommerceRegionsCount(commerceCountryId);
	}

	@Override
	public int getCommerceRegionsCount(long commerceCountryId, boolean active) {
		return _commerceRegionLocalService.getCommerceRegionsCount(commerceCountryId,
			active);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _commerceRegionLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceRegionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceRegionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceRegionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce region in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceRegion the commerce region
	* @return the commerce region that was updated
	*/
	@Override
	public com.liferay.commerce.model.CommerceRegion updateCommerceRegion(
		com.liferay.commerce.model.CommerceRegion commerceRegion) {
		return _commerceRegionLocalService.updateCommerceRegion(commerceRegion);
	}

	@Override
	public com.liferay.commerce.model.CommerceRegion updateCommerceRegion(
		long commerceRegionId, String name, String code, double priority,
		boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceRegionLocalService.updateCommerceRegion(commerceRegionId,
			name, code, priority, active, serviceContext);
	}

	@Override
	public CommerceRegionLocalService getWrappedService() {
		return _commerceRegionLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceRegionLocalService commerceRegionLocalService) {
		_commerceRegionLocalService = commerceRegionLocalService;
	}

	private CommerceRegionLocalService _commerceRegionLocalService;
}