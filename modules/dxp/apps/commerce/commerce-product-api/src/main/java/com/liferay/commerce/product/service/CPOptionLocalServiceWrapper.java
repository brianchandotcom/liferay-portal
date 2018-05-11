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
 * Provides a wrapper for {@link CPOptionLocalService}.
 *
 * @author Marco Leo
 * @see CPOptionLocalService
 * @generated
 */
@ProviderType
public class CPOptionLocalServiceWrapper implements CPOptionLocalService,
	ServiceWrapper<CPOptionLocalService> {
	public CPOptionLocalServiceWrapper(
		CPOptionLocalService cpOptionLocalService) {
		_cpOptionLocalService = cpOptionLocalService;
	}

	/**
	* Adds the cp option to the database. Also notifies the appropriate model listeners.
	*
	* @param cpOption the cp option
	* @return the cp option that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPOption addCPOption(
		com.liferay.commerce.product.model.CPOption cpOption) {
		return _cpOptionLocalService.addCPOption(cpOption);
	}

	@Override
	public com.liferay.commerce.product.model.CPOption addCPOption(
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		String ddmFormFieldTypeName, boolean facetable, boolean required,
		boolean skuContributor, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionLocalService.addCPOption(nameMap, descriptionMap,
			ddmFormFieldTypeName, facetable, required, skuContributor, key,
			serviceContext);
	}

	/**
	* Creates a new cp option with the primary key. Does not add the cp option to the database.
	*
	* @param CPOptionId the primary key for the new cp option
	* @return the new cp option
	*/
	@Override
	public com.liferay.commerce.product.model.CPOption createCPOption(
		long CPOptionId) {
		return _cpOptionLocalService.createCPOption(CPOptionId);
	}

	/**
	* Deletes the cp option from the database. Also notifies the appropriate model listeners.
	*
	* @param cpOption the cp option
	* @return the cp option that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.product.model.CPOption deleteCPOption(
		com.liferay.commerce.product.model.CPOption cpOption)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionLocalService.deleteCPOption(cpOption);
	}

	/**
	* Deletes the cp option with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPOptionId the primary key of the cp option
	* @return the cp option that was removed
	* @throws PortalException if a cp option with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPOption deleteCPOption(
		long CPOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionLocalService.deleteCPOption(CPOptionId);
	}

	@Override
	public void deleteCPOptions(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpOptionLocalService.deleteCPOptions(groupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpOptionLocalService.dynamicQuery();
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
		return _cpOptionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpOptionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpOptionLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _cpOptionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpOptionLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.product.model.CPOption fetchCPOption(
		long CPOptionId) {
		return _cpOptionLocalService.fetchCPOption(CPOptionId);
	}

	@Override
	public com.liferay.commerce.product.model.CPOption fetchCPOption(
		long groupId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionLocalService.fetchCPOption(groupId, key);
	}

	/**
	* Returns the cp option matching the UUID and group.
	*
	* @param uuid the cp option's UUID
	* @param groupId the primary key of the group
	* @return the matching cp option, or <code>null</code> if a matching cp option could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPOption fetchCPOptionByUuidAndGroupId(
		String uuid, long groupId) {
		return _cpOptionLocalService.fetchCPOptionByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpOptionLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the cp option with the primary key.
	*
	* @param CPOptionId the primary key of the cp option
	* @return the cp option
	* @throws PortalException if a cp option with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPOption getCPOption(
		long CPOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionLocalService.getCPOption(CPOptionId);
	}

	@Override
	public com.liferay.commerce.product.model.CPOption getCPOption(
		long groupId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionLocalService.getCPOption(groupId, key);
	}

	/**
	* Returns the cp option matching the UUID and group.
	*
	* @param uuid the cp option's UUID
	* @param groupId the primary key of the group
	* @return the matching cp option
	* @throws PortalException if a matching cp option could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPOption getCPOptionByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionLocalService.getCPOptionByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the cp options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @return the range of cp options
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOption> getCPOptions(
		int start, int end) {
		return _cpOptionLocalService.getCPOptions(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOption> getCPOptions(
		long groupId, int start, int end) {
		return _cpOptionLocalService.getCPOptions(groupId, start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOption> getCPOptions(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPOption> orderByComparator) {
		return _cpOptionLocalService.getCPOptions(groupId, start, end,
			orderByComparator);
	}

	/**
	* Returns all the cp options matching the UUID and company.
	*
	* @param uuid the UUID of the cp options
	* @param companyId the primary key of the company
	* @return the matching cp options, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOption> getCPOptionsByUuidAndCompanyId(
		String uuid, long companyId) {
		return _cpOptionLocalService.getCPOptionsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp options matching the UUID and company.
	*
	* @param uuid the UUID of the cp options
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp options
	* @param end the upper bound of the range of cp options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp options, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOption> getCPOptionsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPOption> orderByComparator) {
		return _cpOptionLocalService.getCPOptionsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cp options.
	*
	* @return the number of cp options
	*/
	@Override
	public int getCPOptionsCount() {
		return _cpOptionLocalService.getCPOptionsCount();
	}

	@Override
	public int getCPOptionsCount(long groupId) {
		return _cpOptionLocalService.getCPOptionsCount(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cpOptionLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpOptionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpOptionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _cpOptionLocalService.search(searchContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPOption> searchCPOptions(
		long companyId, long groupId, String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionLocalService.searchCPOptions(companyId, groupId,
			keywords, start, end, sort);
	}

	@Override
	public com.liferay.commerce.product.model.CPOption setFacetable(
		long cpOptionId, boolean facetable)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionLocalService.setFacetable(cpOptionId, facetable);
	}

	@Override
	public com.liferay.commerce.product.model.CPOption setRequired(
		long cpOptionId, boolean required)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionLocalService.setRequired(cpOptionId, required);
	}

	@Override
	public com.liferay.commerce.product.model.CPOption setSkuContributor(
		long cpOptionId, boolean skuContributor)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionLocalService.setSkuContributor(cpOptionId,
			skuContributor);
	}

	/**
	* Updates the cp option in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpOption the cp option
	* @return the cp option that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPOption updateCPOption(
		com.liferay.commerce.product.model.CPOption cpOption) {
		return _cpOptionLocalService.updateCPOption(cpOption);
	}

	@Override
	public com.liferay.commerce.product.model.CPOption updateCPOption(
		long cpOptionId, java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		String ddmFormFieldTypeName, boolean facetable, boolean required,
		boolean skuContributor, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionLocalService.updateCPOption(cpOptionId, nameMap,
			descriptionMap, ddmFormFieldTypeName, facetable, required,
			skuContributor, key, serviceContext);
	}

	@Override
	public CPOptionLocalService getWrappedService() {
		return _cpOptionLocalService;
	}

	@Override
	public void setWrappedService(CPOptionLocalService cpOptionLocalService) {
		_cpOptionLocalService = cpOptionLocalService;
	}

	private CPOptionLocalService _cpOptionLocalService;
}