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
 * Provides a wrapper for {@link CPMediaTypeLocalService}.
 *
 * @author Marco Leo
 * @see CPMediaTypeLocalService
 * @generated
 */
@ProviderType
public class CPMediaTypeLocalServiceWrapper implements CPMediaTypeLocalService,
	ServiceWrapper<CPMediaTypeLocalService> {
	public CPMediaTypeLocalServiceWrapper(
		CPMediaTypeLocalService cpMediaTypeLocalService) {
		_cpMediaTypeLocalService = cpMediaTypeLocalService;
	}

	/**
	* Adds the cp media type to the database. Also notifies the appropriate model listeners.
	*
	* @param cpMediaType the cp media type
	* @return the cp media type that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPMediaType addCPMediaType(
		com.liferay.commerce.product.model.CPMediaType cpMediaType) {
		return _cpMediaTypeLocalService.addCPMediaType(cpMediaType);
	}

	/**
	* Creates a new cp media type with the primary key. Does not add the cp media type to the database.
	*
	* @param CPMediaTypeId the primary key for the new cp media type
	* @return the new cp media type
	*/
	@Override
	public com.liferay.commerce.product.model.CPMediaType createCPMediaType(
		long CPMediaTypeId) {
		return _cpMediaTypeLocalService.createCPMediaType(CPMediaTypeId);
	}

	/**
	* Deletes the cp media type from the database. Also notifies the appropriate model listeners.
	*
	* @param cpMediaType the cp media type
	* @return the cp media type that was removed
	*/
	@Override
	public com.liferay.commerce.product.model.CPMediaType deleteCPMediaType(
		com.liferay.commerce.product.model.CPMediaType cpMediaType) {
		return _cpMediaTypeLocalService.deleteCPMediaType(cpMediaType);
	}

	/**
	* Deletes the cp media type with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPMediaTypeId the primary key of the cp media type
	* @return the cp media type that was removed
	* @throws PortalException if a cp media type with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPMediaType deleteCPMediaType(
		long CPMediaTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMediaTypeLocalService.deleteCPMediaType(CPMediaTypeId);
	}

	@Override
	public com.liferay.commerce.product.model.CPMediaType fetchCPMediaType(
		long CPMediaTypeId) {
		return _cpMediaTypeLocalService.fetchCPMediaType(CPMediaTypeId);
	}

	/**
	* Returns the cp media type matching the UUID and group.
	*
	* @param uuid the cp media type's UUID
	* @param groupId the primary key of the group
	* @return the matching cp media type, or <code>null</code> if a matching cp media type could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPMediaType fetchCPMediaTypeByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _cpMediaTypeLocalService.fetchCPMediaTypeByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the cp media type with the primary key.
	*
	* @param CPMediaTypeId the primary key of the cp media type
	* @return the cp media type
	* @throws PortalException if a cp media type with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPMediaType getCPMediaType(
		long CPMediaTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMediaTypeLocalService.getCPMediaType(CPMediaTypeId);
	}

	/**
	* Returns the cp media type matching the UUID and group.
	*
	* @param uuid the cp media type's UUID
	* @param groupId the primary key of the group
	* @return the matching cp media type
	* @throws PortalException if a matching cp media type could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPMediaType getCPMediaTypeByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMediaTypeLocalService.getCPMediaTypeByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Updates the cp media type in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpMediaType the cp media type
	* @return the cp media type that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPMediaType updateCPMediaType(
		com.liferay.commerce.product.model.CPMediaType cpMediaType) {
		return _cpMediaTypeLocalService.updateCPMediaType(cpMediaType);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpMediaTypeLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpMediaTypeLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cpMediaTypeLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpMediaTypeLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMediaTypeLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMediaTypeLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of cp media types.
	*
	* @return the number of cp media types
	*/
	@Override
	public int getCPMediaTypesCount() {
		return _cpMediaTypeLocalService.getCPMediaTypesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpMediaTypeLocalService.getOSGiServiceIdentifier();
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
		return _cpMediaTypeLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpMediaTypeLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpMediaTypeLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns a range of all the cp media types.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPMediaTypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @return the range of cp media types
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPMediaType> getCPMediaTypes(
		int start, int end) {
		return _cpMediaTypeLocalService.getCPMediaTypes(start, end);
	}

	/**
	* Returns all the cp media types matching the UUID and company.
	*
	* @param uuid the UUID of the cp media types
	* @param companyId the primary key of the company
	* @return the matching cp media types, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPMediaType> getCPMediaTypesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _cpMediaTypeLocalService.getCPMediaTypesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp media types matching the UUID and company.
	*
	* @param uuid the UUID of the cp media types
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp media types
	* @param end the upper bound of the range of cp media types (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp media types, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPMediaType> getCPMediaTypesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPMediaType> orderByComparator) {
		return _cpMediaTypeLocalService.getCPMediaTypesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
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
		return _cpMediaTypeLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpMediaTypeLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public CPMediaTypeLocalService getWrappedService() {
		return _cpMediaTypeLocalService;
	}

	@Override
	public void setWrappedService(
		CPMediaTypeLocalService cpMediaTypeLocalService) {
		_cpMediaTypeLocalService = cpMediaTypeLocalService;
	}

	private CPMediaTypeLocalService _cpMediaTypeLocalService;
}