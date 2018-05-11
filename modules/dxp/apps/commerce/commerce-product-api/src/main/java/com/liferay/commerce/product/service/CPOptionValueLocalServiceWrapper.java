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
 * Provides a wrapper for {@link CPOptionValueLocalService}.
 *
 * @author Marco Leo
 * @see CPOptionValueLocalService
 * @generated
 */
@ProviderType
public class CPOptionValueLocalServiceWrapper
	implements CPOptionValueLocalService,
		ServiceWrapper<CPOptionValueLocalService> {
	public CPOptionValueLocalServiceWrapper(
		CPOptionValueLocalService cpOptionValueLocalService) {
		_cpOptionValueLocalService = cpOptionValueLocalService;
	}

	/**
	* Adds the cp option value to the database. Also notifies the appropriate model listeners.
	*
	* @param cpOptionValue the cp option value
	* @return the cp option value that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPOptionValue addCPOptionValue(
		com.liferay.commerce.product.model.CPOptionValue cpOptionValue) {
		return _cpOptionValueLocalService.addCPOptionValue(cpOptionValue);
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionValue addCPOptionValue(
		long cpOptionId, java.util.Map<java.util.Locale, String> nameMap,
		double priority, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValueLocalService.addCPOptionValue(cpOptionId, nameMap,
			priority, key, serviceContext);
	}

	/**
	* Creates a new cp option value with the primary key. Does not add the cp option value to the database.
	*
	* @param CPOptionValueId the primary key for the new cp option value
	* @return the new cp option value
	*/
	@Override
	public com.liferay.commerce.product.model.CPOptionValue createCPOptionValue(
		long CPOptionValueId) {
		return _cpOptionValueLocalService.createCPOptionValue(CPOptionValueId);
	}

	/**
	* Deletes the cp option value from the database. Also notifies the appropriate model listeners.
	*
	* @param cpOptionValue the cp option value
	* @return the cp option value that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.product.model.CPOptionValue deleteCPOptionValue(
		com.liferay.commerce.product.model.CPOptionValue cpOptionValue)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValueLocalService.deleteCPOptionValue(cpOptionValue);
	}

	/**
	* Deletes the cp option value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPOptionValueId the primary key of the cp option value
	* @return the cp option value that was removed
	* @throws PortalException if a cp option value with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPOptionValue deleteCPOptionValue(
		long CPOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValueLocalService.deleteCPOptionValue(CPOptionValueId);
	}

	@Override
	public void deleteCPOptionValues(long cpOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpOptionValueLocalService.deleteCPOptionValues(cpOptionId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValueLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpOptionValueLocalService.dynamicQuery();
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
		return _cpOptionValueLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpOptionValueLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpOptionValueLocalService.dynamicQuery(dynamicQuery, start,
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
		return _cpOptionValueLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpOptionValueLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionValue fetchCPOptionValue(
		long CPOptionValueId) {
		return _cpOptionValueLocalService.fetchCPOptionValue(CPOptionValueId);
	}

	/**
	* Returns the cp option value matching the UUID and group.
	*
	* @param uuid the cp option value's UUID
	* @param groupId the primary key of the group
	* @return the matching cp option value, or <code>null</code> if a matching cp option value could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPOptionValue fetchCPOptionValueByUuidAndGroupId(
		String uuid, long groupId) {
		return _cpOptionValueLocalService.fetchCPOptionValueByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpOptionValueLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the cp option value with the primary key.
	*
	* @param CPOptionValueId the primary key of the cp option value
	* @return the cp option value
	* @throws PortalException if a cp option value with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPOptionValue getCPOptionValue(
		long CPOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValueLocalService.getCPOptionValue(CPOptionValueId);
	}

	/**
	* Returns the cp option value matching the UUID and group.
	*
	* @param uuid the cp option value's UUID
	* @param groupId the primary key of the group
	* @return the matching cp option value
	* @throws PortalException if a matching cp option value could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPOptionValue getCPOptionValueByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValueLocalService.getCPOptionValueByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the cp option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @return the range of cp option values
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOptionValue> getCPOptionValues(
		int start, int end) {
		return _cpOptionValueLocalService.getCPOptionValues(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOptionValue> getCPOptionValues(
		long cpOptionId, int start, int end) {
		return _cpOptionValueLocalService.getCPOptionValues(cpOptionId, start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOptionValue> getCPOptionValues(
		long cpOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPOptionValue> orderByComparator) {
		return _cpOptionValueLocalService.getCPOptionValues(cpOptionId, start,
			end, orderByComparator);
	}

	/**
	* Returns all the cp option values matching the UUID and company.
	*
	* @param uuid the UUID of the cp option values
	* @param companyId the primary key of the company
	* @return the matching cp option values, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOptionValue> getCPOptionValuesByUuidAndCompanyId(
		String uuid, long companyId) {
		return _cpOptionValueLocalService.getCPOptionValuesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp option values matching the UUID and company.
	*
	* @param uuid the UUID of the cp option values
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp option values
	* @param end the upper bound of the range of cp option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp option values, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOptionValue> getCPOptionValuesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPOptionValue> orderByComparator) {
		return _cpOptionValueLocalService.getCPOptionValuesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cp option values.
	*
	* @return the number of cp option values
	*/
	@Override
	public int getCPOptionValuesCount() {
		return _cpOptionValueLocalService.getCPOptionValuesCount();
	}

	@Override
	public int getCPOptionValuesCount(long cpOptionId) {
		return _cpOptionValueLocalService.getCPOptionValuesCount(cpOptionId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cpOptionValueLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpOptionValueLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpOptionValueLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValueLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _cpOptionValueLocalService.search(searchContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPOptionValue> searchCPOptionValues(
		long companyId, long groupId, long cpOptionId, String keywords,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValueLocalService.searchCPOptionValues(companyId,
			groupId, cpOptionId, keywords, start, end, sort);
	}

	/**
	* Updates the cp option value in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpOptionValue the cp option value
	* @return the cp option value that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPOptionValue updateCPOptionValue(
		com.liferay.commerce.product.model.CPOptionValue cpOptionValue) {
		return _cpOptionValueLocalService.updateCPOptionValue(cpOptionValue);
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionValue updateCPOptionValue(
		long cpOptionValueId, java.util.Map<java.util.Locale, String> nameMap,
		double priority, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValueLocalService.updateCPOptionValue(cpOptionValueId,
			nameMap, priority, key, serviceContext);
	}

	@Override
	public CPOptionValueLocalService getWrappedService() {
		return _cpOptionValueLocalService;
	}

	@Override
	public void setWrappedService(
		CPOptionValueLocalService cpOptionValueLocalService) {
		_cpOptionValueLocalService = cpOptionValueLocalService;
	}

	private CPOptionValueLocalService _cpOptionValueLocalService;
}