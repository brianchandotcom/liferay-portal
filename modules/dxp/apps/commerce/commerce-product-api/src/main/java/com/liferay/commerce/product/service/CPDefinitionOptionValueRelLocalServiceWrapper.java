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
 * Provides a wrapper for {@link CPDefinitionOptionValueRelLocalService}.
 *
 * @author Marco Leo
 * @see CPDefinitionOptionValueRelLocalService
 * @generated
 */
@ProviderType
public class CPDefinitionOptionValueRelLocalServiceWrapper
	implements CPDefinitionOptionValueRelLocalService,
		ServiceWrapper<CPDefinitionOptionValueRelLocalService> {
	public CPDefinitionOptionValueRelLocalServiceWrapper(
		CPDefinitionOptionValueRelLocalService cpDefinitionOptionValueRelLocalService) {
		_cpDefinitionOptionValueRelLocalService = cpDefinitionOptionValueRelLocalService;
	}

	/**
	* Adds the cp definition option value rel to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionOptionValueRel the cp definition option value rel
	* @return the cp definition option value rel that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionValueRel addCPDefinitionOptionValueRel(
		com.liferay.commerce.product.model.CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {
		return _cpDefinitionOptionValueRelLocalService.addCPDefinitionOptionValueRel(cpDefinitionOptionValueRel);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionValueRel addCPDefinitionOptionValueRel(
		long cpDefinitionOptionRelId,
		com.liferay.commerce.product.model.CPOptionValue cpOptionValue,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionValueRelLocalService.addCPDefinitionOptionValueRel(cpDefinitionOptionRelId,
			cpOptionValue, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionValueRel addCPDefinitionOptionValueRel(
		long cpDefinitionOptionRelId,
		java.util.Map<java.util.Locale, String> nameMap, double priority,
		String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionValueRelLocalService.addCPDefinitionOptionValueRel(cpDefinitionOptionRelId,
			nameMap, priority, key, serviceContext);
	}

	/**
	* Creates a new cp definition option value rel with the primary key. Does not add the cp definition option value rel to the database.
	*
	* @param CPDefinitionOptionValueRelId the primary key for the new cp definition option value rel
	* @return the new cp definition option value rel
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionValueRel createCPDefinitionOptionValueRel(
		long CPDefinitionOptionValueRelId) {
		return _cpDefinitionOptionValueRelLocalService.createCPDefinitionOptionValueRel(CPDefinitionOptionValueRelId);
	}

	/**
	* Deletes the cp definition option value rel from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionOptionValueRel the cp definition option value rel
	* @return the cp definition option value rel that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionValueRel deleteCPDefinitionOptionValueRel(
		com.liferay.commerce.product.model.CPDefinitionOptionValueRel cpDefinitionOptionValueRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionValueRelLocalService.deleteCPDefinitionOptionValueRel(cpDefinitionOptionValueRel);
	}

	/**
	* Deletes the cp definition option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionOptionValueRelId the primary key of the cp definition option value rel
	* @return the cp definition option value rel that was removed
	* @throws PortalException if a cp definition option value rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionValueRel deleteCPDefinitionOptionValueRel(
		long CPDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionValueRelLocalService.deleteCPDefinitionOptionValueRel(CPDefinitionOptionValueRelId);
	}

	@Override
	public void deleteCPDefinitionOptionValueRels(long cpDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpDefinitionOptionValueRelLocalService.deleteCPDefinitionOptionValueRels(cpDefinitionOptionRelId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionValueRelLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpDefinitionOptionValueRelLocalService.dynamicQuery();
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
		return _cpDefinitionOptionValueRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpDefinitionOptionValueRelLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpDefinitionOptionValueRelLocalService.dynamicQuery(dynamicQuery,
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
		return _cpDefinitionOptionValueRelLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpDefinitionOptionValueRelLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionValueRel fetchCPDefinitionOptionValueRel(
		long CPDefinitionOptionValueRelId) {
		return _cpDefinitionOptionValueRelLocalService.fetchCPDefinitionOptionValueRel(CPDefinitionOptionValueRelId);
	}

	/**
	* Returns the cp definition option value rel matching the UUID and group.
	*
	* @param uuid the cp definition option value rel's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition option value rel, or <code>null</code> if a matching cp definition option value rel could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionValueRel fetchCPDefinitionOptionValueRelByUuidAndGroupId(
		String uuid, long groupId) {
		return _cpDefinitionOptionValueRelLocalService.fetchCPDefinitionOptionValueRelByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpDefinitionOptionValueRelLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the cp definition option value rel with the primary key.
	*
	* @param CPDefinitionOptionValueRelId the primary key of the cp definition option value rel
	* @return the cp definition option value rel
	* @throws PortalException if a cp definition option value rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionValueRel getCPDefinitionOptionValueRel(
		long CPDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionValueRelLocalService.getCPDefinitionOptionValueRel(CPDefinitionOptionValueRelId);
	}

	/**
	* Returns the cp definition option value rel matching the UUID and group.
	*
	* @param uuid the cp definition option value rel's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition option value rel
	* @throws PortalException if a matching cp definition option value rel could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionValueRel getCPDefinitionOptionValueRelByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionValueRelLocalService.getCPDefinitionOptionValueRelByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the cp definition option value rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @return the range of cp definition option value rels
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		int start, int end) {
		return _cpDefinitionOptionValueRelLocalService.getCPDefinitionOptionValueRels(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId) {
		return _cpDefinitionOptionValueRelLocalService.getCPDefinitionOptionValueRels(cpDefinitionOptionRelId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId, int start, int end) {
		return _cpDefinitionOptionValueRelLocalService.getCPDefinitionOptionValueRels(cpDefinitionOptionRelId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> orderByComparator) {
		return _cpDefinitionOptionValueRelLocalService.getCPDefinitionOptionValueRels(cpDefinitionOptionRelId,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long[] cpDefinitionOptionValueRelsId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionValueRelLocalService.getCPDefinitionOptionValueRels(cpDefinitionOptionValueRelsId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		String key, int start, int end) {
		return _cpDefinitionOptionValueRelLocalService.getCPDefinitionOptionValueRels(key,
			start, end);
	}

	/**
	* Returns all the cp definition option value rels matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition option value rels
	* @param companyId the primary key of the company
	* @return the matching cp definition option value rels, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRelsByUuidAndCompanyId(
		String uuid, long companyId) {
		return _cpDefinitionOptionValueRelLocalService.getCPDefinitionOptionValueRelsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp definition option value rels matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition option value rels
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp definition option value rels
	* @param end the upper bound of the range of cp definition option value rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp definition option value rels, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRelsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> orderByComparator) {
		return _cpDefinitionOptionValueRelLocalService.getCPDefinitionOptionValueRelsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cp definition option value rels.
	*
	* @return the number of cp definition option value rels
	*/
	@Override
	public int getCPDefinitionOptionValueRelsCount() {
		return _cpDefinitionOptionValueRelLocalService.getCPDefinitionOptionValueRelsCount();
	}

	@Override
	public int getCPDefinitionOptionValueRelsCount(long cpDefinitionOptionRelId) {
		return _cpDefinitionOptionValueRelLocalService.getCPDefinitionOptionValueRelsCount(cpDefinitionOptionRelId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cpDefinitionOptionValueRelLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpDefinitionOptionValueRelLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionOptionValueRelLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionValueRelLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public void importCPDefinitionOptionRels(long cpDefinitionOptionRelId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpDefinitionOptionValueRelLocalService.importCPDefinitionOptionRels(cpDefinitionOptionRelId,
			serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _cpDefinitionOptionValueRelLocalService.search(searchContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> searchCPDefinitionOptionValueRels(
		long companyId, long groupId, long cpDefinitionOptionRelId,
		String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionValueRelLocalService.searchCPDefinitionOptionValueRels(companyId,
			groupId, cpDefinitionOptionRelId, keywords, start, end, sort);
	}

	/**
	* Updates the cp definition option value rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionOptionValueRel the cp definition option value rel
	* @return the cp definition option value rel that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionValueRel updateCPDefinitionOptionValueRel(
		com.liferay.commerce.product.model.CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {
		return _cpDefinitionOptionValueRelLocalService.updateCPDefinitionOptionValueRel(cpDefinitionOptionValueRel);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionValueRel updateCPDefinitionOptionValueRel(
		long cpDefinitionOptionValueRelId,
		java.util.Map<java.util.Locale, String> nameMap, double priority,
		String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionValueRelLocalService.updateCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId,
			nameMap, priority, key, serviceContext);
	}

	@Override
	public CPDefinitionOptionValueRelLocalService getWrappedService() {
		return _cpDefinitionOptionValueRelLocalService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionOptionValueRelLocalService cpDefinitionOptionValueRelLocalService) {
		_cpDefinitionOptionValueRelLocalService = cpDefinitionOptionValueRelLocalService;
	}

	private CPDefinitionOptionValueRelLocalService _cpDefinitionOptionValueRelLocalService;
}