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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPMeasurementUnitLocalService}.
 *
 * @author Marco Leo
 * @see CPMeasurementUnitLocalService
 * @generated
 */
@ProviderType
public class CPMeasurementUnitLocalServiceWrapper
	implements CPMeasurementUnitLocalService,
		ServiceWrapper<CPMeasurementUnitLocalService> {
	public CPMeasurementUnitLocalServiceWrapper(
		CPMeasurementUnitLocalService cpMeasurementUnitLocalService) {
		_cpMeasurementUnitLocalService = cpMeasurementUnitLocalService;
	}

	/**
	* Adds the cp measurement unit to the database. Also notifies the appropriate model listeners.
	*
	* @param cpMeasurementUnit the cp measurement unit
	* @return the cp measurement unit that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPMeasurementUnit addCPMeasurementUnit(
		com.liferay.commerce.product.model.CPMeasurementUnit cpMeasurementUnit) {
		return _cpMeasurementUnitLocalService.addCPMeasurementUnit(cpMeasurementUnit);
	}

	@Override
	public com.liferay.commerce.product.model.CPMeasurementUnit addCPMeasurementUnit(
		java.util.Map<java.util.Locale, String> nameMap, String key,
		double rate, boolean primary, double priority, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMeasurementUnitLocalService.addCPMeasurementUnit(nameMap,
			key, rate, primary, priority, type, serviceContext);
	}

	/**
	* Creates a new cp measurement unit with the primary key. Does not add the cp measurement unit to the database.
	*
	* @param CPMeasurementUnitId the primary key for the new cp measurement unit
	* @return the new cp measurement unit
	*/
	@Override
	public com.liferay.commerce.product.model.CPMeasurementUnit createCPMeasurementUnit(
		long CPMeasurementUnitId) {
		return _cpMeasurementUnitLocalService.createCPMeasurementUnit(CPMeasurementUnitId);
	}

	/**
	* Deletes the cp measurement unit from the database. Also notifies the appropriate model listeners.
	*
	* @param cpMeasurementUnit the cp measurement unit
	* @return the cp measurement unit that was removed
	*/
	@Override
	public com.liferay.commerce.product.model.CPMeasurementUnit deleteCPMeasurementUnit(
		com.liferay.commerce.product.model.CPMeasurementUnit cpMeasurementUnit) {
		return _cpMeasurementUnitLocalService.deleteCPMeasurementUnit(cpMeasurementUnit);
	}

	/**
	* Deletes the cp measurement unit with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPMeasurementUnitId the primary key of the cp measurement unit
	* @return the cp measurement unit that was removed
	* @throws PortalException if a cp measurement unit with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPMeasurementUnit deleteCPMeasurementUnit(
		long CPMeasurementUnitId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMeasurementUnitLocalService.deleteCPMeasurementUnit(CPMeasurementUnitId);
	}

	@Override
	public void deleteCPMeasurementUnits(long groupId) {
		_cpMeasurementUnitLocalService.deleteCPMeasurementUnits(groupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMeasurementUnitLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpMeasurementUnitLocalService.dynamicQuery();
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
		return _cpMeasurementUnitLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpMeasurementUnitLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpMeasurementUnitLocalService.dynamicQuery(dynamicQuery, start,
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
		return _cpMeasurementUnitLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpMeasurementUnitLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.product.model.CPMeasurementUnit fetchCPMeasurementUnit(
		long CPMeasurementUnitId) {
		return _cpMeasurementUnitLocalService.fetchCPMeasurementUnit(CPMeasurementUnitId);
	}

	/**
	* Returns the cp measurement unit matching the UUID and group.
	*
	* @param uuid the cp measurement unit's UUID
	* @param groupId the primary key of the group
	* @return the matching cp measurement unit, or <code>null</code> if a matching cp measurement unit could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPMeasurementUnit fetchCPMeasurementUnitByUuidAndGroupId(
		String uuid, long groupId) {
		return _cpMeasurementUnitLocalService.fetchCPMeasurementUnitByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.commerce.product.model.CPMeasurementUnit fetchPrimaryCPMeasurementUnit(
		long groupId, int type) {
		return _cpMeasurementUnitLocalService.fetchPrimaryCPMeasurementUnit(groupId,
			type);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpMeasurementUnitLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the cp measurement unit with the primary key.
	*
	* @param CPMeasurementUnitId the primary key of the cp measurement unit
	* @return the cp measurement unit
	* @throws PortalException if a cp measurement unit with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPMeasurementUnit getCPMeasurementUnit(
		long CPMeasurementUnitId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMeasurementUnitLocalService.getCPMeasurementUnit(CPMeasurementUnitId);
	}

	/**
	* Returns the cp measurement unit matching the UUID and group.
	*
	* @param uuid the cp measurement unit's UUID
	* @param groupId the primary key of the group
	* @return the matching cp measurement unit
	* @throws PortalException if a matching cp measurement unit could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPMeasurementUnit getCPMeasurementUnitByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMeasurementUnitLocalService.getCPMeasurementUnitByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the cp measurement units.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @return the range of cp measurement units
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPMeasurementUnit> getCPMeasurementUnits(
		int start, int end) {
		return _cpMeasurementUnitLocalService.getCPMeasurementUnits(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPMeasurementUnit> getCPMeasurementUnits(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPMeasurementUnit> orderByComparator) {
		return _cpMeasurementUnitLocalService.getCPMeasurementUnits(groupId,
			type, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPMeasurementUnit> getCPMeasurementUnits(
		long groupId, String[] keys, int type) {
		return _cpMeasurementUnitLocalService.getCPMeasurementUnits(groupId,
			keys, type);
	}

	/**
	* Returns all the cp measurement units matching the UUID and company.
	*
	* @param uuid the UUID of the cp measurement units
	* @param companyId the primary key of the company
	* @return the matching cp measurement units, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPMeasurementUnit> getCPMeasurementUnitsByUuidAndCompanyId(
		String uuid, long companyId) {
		return _cpMeasurementUnitLocalService.getCPMeasurementUnitsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp measurement units matching the UUID and company.
	*
	* @param uuid the UUID of the cp measurement units
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp measurement units
	* @param end the upper bound of the range of cp measurement units (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp measurement units, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPMeasurementUnit> getCPMeasurementUnitsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPMeasurementUnit> orderByComparator) {
		return _cpMeasurementUnitLocalService.getCPMeasurementUnitsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cp measurement units.
	*
	* @return the number of cp measurement units
	*/
	@Override
	public int getCPMeasurementUnitsCount() {
		return _cpMeasurementUnitLocalService.getCPMeasurementUnitsCount();
	}

	@Override
	public int getCPMeasurementUnitsCount(long groupId) {
		return _cpMeasurementUnitLocalService.getCPMeasurementUnitsCount(groupId);
	}

	@Override
	public int getCPMeasurementUnitsCount(long groupId, int type) {
		return _cpMeasurementUnitLocalService.getCPMeasurementUnitsCount(groupId,
			type);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cpMeasurementUnitLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpMeasurementUnitLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpMeasurementUnitLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMeasurementUnitLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public void importDefaultValues(
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpMeasurementUnitLocalService.importDefaultValues(serviceContext);
	}

	/**
	* Updates the cp measurement unit in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpMeasurementUnit the cp measurement unit
	* @return the cp measurement unit that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPMeasurementUnit updateCPMeasurementUnit(
		com.liferay.commerce.product.model.CPMeasurementUnit cpMeasurementUnit) {
		return _cpMeasurementUnitLocalService.updateCPMeasurementUnit(cpMeasurementUnit);
	}

	@Override
	public com.liferay.commerce.product.model.CPMeasurementUnit updateCPMeasurementUnit(
		long cpMeasurementUnitId,
		java.util.Map<java.util.Locale, String> nameMap, String key,
		double rate, boolean primary, double priority, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMeasurementUnitLocalService.updateCPMeasurementUnit(cpMeasurementUnitId,
			nameMap, key, rate, primary, priority, type, serviceContext);
	}

	@Override
	public CPMeasurementUnitLocalService getWrappedService() {
		return _cpMeasurementUnitLocalService;
	}

	@Override
	public void setWrappedService(
		CPMeasurementUnitLocalService cpMeasurementUnitLocalService) {
		_cpMeasurementUnitLocalService = cpMeasurementUnitLocalService;
	}

	private CPMeasurementUnitLocalService _cpMeasurementUnitLocalService;
}