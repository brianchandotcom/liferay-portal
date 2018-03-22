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
 * Provides a wrapper for {@link CPDefinitionOptionRelLocalService}.
 *
 * @author Marco Leo
 * @see CPDefinitionOptionRelLocalService
 * @generated
 */
@ProviderType
public class CPDefinitionOptionRelLocalServiceWrapper
	implements CPDefinitionOptionRelLocalService,
		ServiceWrapper<CPDefinitionOptionRelLocalService> {
	public CPDefinitionOptionRelLocalServiceWrapper(
		CPDefinitionOptionRelLocalService cpDefinitionOptionRelLocalService) {
		_cpDefinitionOptionRelLocalService = cpDefinitionOptionRelLocalService;
	}

	/**
	* Adds the cp definition option rel to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionOptionRel the cp definition option rel
	* @return the cp definition option rel that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel addCPDefinitionOptionRel(
		com.liferay.commerce.product.model.CPDefinitionOptionRel cpDefinitionOptionRel) {
		return _cpDefinitionOptionRelLocalService.addCPDefinitionOptionRel(cpDefinitionOptionRel);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel addCPDefinitionOptionRel(
		long cpDefinitionId, long cpOptionId, boolean importOptionValue,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelLocalService.addCPDefinitionOptionRel(cpDefinitionId,
			cpOptionId, importOptionValue, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel addCPDefinitionOptionRel(
		long cpDefinitionId, long cpOptionId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, double priority,
		boolean facetable, boolean required, boolean skuContributor,
		boolean importOptionValue,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelLocalService.addCPDefinitionOptionRel(cpDefinitionId,
			cpOptionId, titleMap, descriptionMap, ddmFormFieldTypeName,
			priority, facetable, required, skuContributor, importOptionValue,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel addCPDefinitionOptionRel(
		long cpDefinitionId, long cpOptionId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelLocalService.addCPDefinitionOptionRel(cpDefinitionId,
			cpOptionId, serviceContext);
	}

	/**
	* Creates a new cp definition option rel with the primary key. Does not add the cp definition option rel to the database.
	*
	* @param CPDefinitionOptionRelId the primary key for the new cp definition option rel
	* @return the new cp definition option rel
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel createCPDefinitionOptionRel(
		long CPDefinitionOptionRelId) {
		return _cpDefinitionOptionRelLocalService.createCPDefinitionOptionRel(CPDefinitionOptionRelId);
	}

	/**
	* Deletes the cp definition option rel from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionOptionRel the cp definition option rel
	* @return the cp definition option rel that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel deleteCPDefinitionOptionRel(
		com.liferay.commerce.product.model.CPDefinitionOptionRel cpDefinitionOptionRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelLocalService.deleteCPDefinitionOptionRel(cpDefinitionOptionRel);
	}

	/**
	* Deletes the cp definition option rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionOptionRelId the primary key of the cp definition option rel
	* @return the cp definition option rel that was removed
	* @throws PortalException if a cp definition option rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel deleteCPDefinitionOptionRel(
		long CPDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelLocalService.deleteCPDefinitionOptionRel(CPDefinitionOptionRelId);
	}

	@Override
	public void deleteCPDefinitionOptionRels(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpDefinitionOptionRelLocalService.deleteCPDefinitionOptionRels(cpDefinitionId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpDefinitionOptionRelLocalService.dynamicQuery();
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
		return _cpDefinitionOptionRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpDefinitionOptionRelLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpDefinitionOptionRelLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _cpDefinitionOptionRelLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpDefinitionOptionRelLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel fetchCPDefinitionOptionRel(
		long CPDefinitionOptionRelId) {
		return _cpDefinitionOptionRelLocalService.fetchCPDefinitionOptionRel(CPDefinitionOptionRelId);
	}

	/**
	* Returns the cp definition option rel matching the UUID and group.
	*
	* @param uuid the cp definition option rel's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition option rel, or <code>null</code> if a matching cp definition option rel could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel fetchCPDefinitionOptionRelByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _cpDefinitionOptionRelLocalService.fetchCPDefinitionOptionRelByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpDefinitionOptionRelLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the cp definition option rel with the primary key.
	*
	* @param CPDefinitionOptionRelId the primary key of the cp definition option rel
	* @return the cp definition option rel
	* @throws PortalException if a cp definition option rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel getCPDefinitionOptionRel(
		long CPDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelLocalService.getCPDefinitionOptionRel(CPDefinitionOptionRelId);
	}

	/**
	* Returns the cp definition option rel matching the UUID and group.
	*
	* @param uuid the cp definition option rel's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition option rel
	* @throws PortalException if a matching cp definition option rel could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel getCPDefinitionOptionRelByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelLocalService.getCPDefinitionOptionRelByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the cp definition option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @return the range of cp definition option rels
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRels(
		int start, int end) {
		return _cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId) {
		return _cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(cpDefinitionId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, boolean skuContributor) {
		return _cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(cpDefinitionId,
			skuContributor);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, int start, int end) {
		return _cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(cpDefinitionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionOptionRel> orderByComparator) {
		return _cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(cpDefinitionId,
			start, end, orderByComparator);
	}

	/**
	* Returns all the cp definition option rels matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition option rels
	* @param companyId the primary key of the company
	* @return the matching cp definition option rels, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _cpDefinitionOptionRelLocalService.getCPDefinitionOptionRelsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp definition option rels matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition option rels
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp definition option rels
	* @param end the upper bound of the range of cp definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp definition option rels, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionOptionRel> orderByComparator) {
		return _cpDefinitionOptionRelLocalService.getCPDefinitionOptionRelsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cp definition option rels.
	*
	* @return the number of cp definition option rels
	*/
	@Override
	public int getCPDefinitionOptionRelsCount() {
		return _cpDefinitionOptionRelLocalService.getCPDefinitionOptionRelsCount();
	}

	@Override
	public int getCPDefinitionOptionRelsCount(long cpDefinitionId) {
		return _cpDefinitionOptionRelLocalService.getCPDefinitionOptionRelsCount(cpDefinitionId);
	}

	@Override
	public int getCPDefinitionOptionRelsCount(long cpDefinitionId,
		boolean skuContributor) {
		return _cpDefinitionOptionRelLocalService.getCPDefinitionOptionRelsCount(cpDefinitionId,
			skuContributor);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cpDefinitionOptionRelLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpDefinitionOptionRelLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpDefinitionOptionRelLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _cpDefinitionOptionRelLocalService.search(searchContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinitionOptionRel> searchCPDefinitionOptionRels(
		long companyId, long groupId, long cpDefinitionId,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelLocalService.searchCPDefinitionOptionRels(companyId,
			groupId, cpDefinitionId, keywords, start, end, sort);
	}

	/**
	* Updates the cp definition option rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionOptionRel the cp definition option rel
	* @return the cp definition option rel that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel updateCPDefinitionOptionRel(
		com.liferay.commerce.product.model.CPDefinitionOptionRel cpDefinitionOptionRel) {
		return _cpDefinitionOptionRelLocalService.updateCPDefinitionOptionRel(cpDefinitionOptionRel);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel updateCPDefinitionOptionRel(
		long cpDefinitionOptionRelId, long cpOptionId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, double priority,
		boolean facetable, boolean required, boolean skuContributor,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelLocalService.updateCPDefinitionOptionRel(cpDefinitionOptionRelId,
			cpOptionId, titleMap, descriptionMap, ddmFormFieldTypeName,
			priority, facetable, required, skuContributor, serviceContext);
	}

	@Override
	public CPDefinitionOptionRelLocalService getWrappedService() {
		return _cpDefinitionOptionRelLocalService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionOptionRelLocalService cpDefinitionOptionRelLocalService) {
		_cpDefinitionOptionRelLocalService = cpDefinitionOptionRelLocalService;
	}

	private CPDefinitionOptionRelLocalService _cpDefinitionOptionRelLocalService;
}