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
 * Provides a wrapper for {@link CPSpecificationOptionLocalService}.
 *
 * @author Marco Leo
 * @see CPSpecificationOptionLocalService
 * @generated
 */
@ProviderType
public class CPSpecificationOptionLocalServiceWrapper
	implements CPSpecificationOptionLocalService,
		ServiceWrapper<CPSpecificationOptionLocalService> {
	public CPSpecificationOptionLocalServiceWrapper(
		CPSpecificationOptionLocalService cpSpecificationOptionLocalService) {
		_cpSpecificationOptionLocalService = cpSpecificationOptionLocalService;
	}

	/**
	* Adds the cp specification option to the database. Also notifies the appropriate model listeners.
	*
	* @param cpSpecificationOption the cp specification option
	* @return the cp specification option that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPSpecificationOption addCPSpecificationOption(
		com.liferay.commerce.product.model.CPSpecificationOption cpSpecificationOption) {
		return _cpSpecificationOptionLocalService.addCPSpecificationOption(cpSpecificationOption);
	}

	@Override
	public com.liferay.commerce.product.model.CPSpecificationOption addCPSpecificationOption(
		long cpOptionCategoryId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		boolean facetable, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpSpecificationOptionLocalService.addCPSpecificationOption(cpOptionCategoryId,
			titleMap, descriptionMap, facetable, key, serviceContext);
	}

	/**
	* Creates a new cp specification option with the primary key. Does not add the cp specification option to the database.
	*
	* @param CPSpecificationOptionId the primary key for the new cp specification option
	* @return the new cp specification option
	*/
	@Override
	public com.liferay.commerce.product.model.CPSpecificationOption createCPSpecificationOption(
		long CPSpecificationOptionId) {
		return _cpSpecificationOptionLocalService.createCPSpecificationOption(CPSpecificationOptionId);
	}

	/**
	* Deletes the cp specification option from the database. Also notifies the appropriate model listeners.
	*
	* @param cpSpecificationOption the cp specification option
	* @return the cp specification option that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.product.model.CPSpecificationOption deleteCPSpecificationOption(
		com.liferay.commerce.product.model.CPSpecificationOption cpSpecificationOption)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpSpecificationOptionLocalService.deleteCPSpecificationOption(cpSpecificationOption);
	}

	/**
	* Deletes the cp specification option with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPSpecificationOptionId the primary key of the cp specification option
	* @return the cp specification option that was removed
	* @throws PortalException if a cp specification option with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPSpecificationOption deleteCPSpecificationOption(
		long CPSpecificationOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpSpecificationOptionLocalService.deleteCPSpecificationOption(CPSpecificationOptionId);
	}

	@Override
	public void deleteCPSpecificationOptions(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpSpecificationOptionLocalService.deleteCPSpecificationOptions(groupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpSpecificationOptionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpSpecificationOptionLocalService.dynamicQuery();
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
		return _cpSpecificationOptionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpSpecificationOptionLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpSpecificationOptionLocalService.dynamicQuery(dynamicQuery,
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
		return _cpSpecificationOptionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpSpecificationOptionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.product.model.CPSpecificationOption fetchCPSpecificationOption(
		long CPSpecificationOptionId) {
		return _cpSpecificationOptionLocalService.fetchCPSpecificationOption(CPSpecificationOptionId);
	}

	@Override
	public com.liferay.commerce.product.model.CPSpecificationOption fetchCPSpecificationOption(
		long groupId, String key) {
		return _cpSpecificationOptionLocalService.fetchCPSpecificationOption(groupId,
			key);
	}

	/**
	* Returns the cp specification option matching the UUID and group.
	*
	* @param uuid the cp specification option's UUID
	* @param groupId the primary key of the group
	* @return the matching cp specification option, or <code>null</code> if a matching cp specification option could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPSpecificationOption fetchCPSpecificationOptionByUuidAndGroupId(
		String uuid, long groupId) {
		return _cpSpecificationOptionLocalService.fetchCPSpecificationOptionByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpSpecificationOptionLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the cp specification option with the primary key.
	*
	* @param CPSpecificationOptionId the primary key of the cp specification option
	* @return the cp specification option
	* @throws PortalException if a cp specification option with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPSpecificationOption getCPSpecificationOption(
		long CPSpecificationOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpSpecificationOptionLocalService.getCPSpecificationOption(CPSpecificationOptionId);
	}

	/**
	* Returns the cp specification option matching the UUID and group.
	*
	* @param uuid the cp specification option's UUID
	* @param groupId the primary key of the group
	* @return the matching cp specification option
	* @throws PortalException if a matching cp specification option could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPSpecificationOption getCPSpecificationOptionByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpSpecificationOptionLocalService.getCPSpecificationOptionByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the cp specification options.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPSpecificationOptionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @return the range of cp specification options
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPSpecificationOption> getCPSpecificationOptions(
		int start, int end) {
		return _cpSpecificationOptionLocalService.getCPSpecificationOptions(start,
			end);
	}

	/**
	* Returns all the cp specification options matching the UUID and company.
	*
	* @param uuid the UUID of the cp specification options
	* @param companyId the primary key of the company
	* @return the matching cp specification options, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPSpecificationOption> getCPSpecificationOptionsByUuidAndCompanyId(
		String uuid, long companyId) {
		return _cpSpecificationOptionLocalService.getCPSpecificationOptionsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp specification options matching the UUID and company.
	*
	* @param uuid the UUID of the cp specification options
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp specification options
	* @param end the upper bound of the range of cp specification options (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp specification options, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPSpecificationOption> getCPSpecificationOptionsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPSpecificationOption> orderByComparator) {
		return _cpSpecificationOptionLocalService.getCPSpecificationOptionsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cp specification options.
	*
	* @return the number of cp specification options
	*/
	@Override
	public int getCPSpecificationOptionsCount() {
		return _cpSpecificationOptionLocalService.getCPSpecificationOptionsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cpSpecificationOptionLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpSpecificationOptionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpSpecificationOptionLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpSpecificationOptionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _cpSpecificationOptionLocalService.search(searchContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPSpecificationOption> searchCPSpecificationOptions(
		long companyId, long groupId, String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpSpecificationOptionLocalService.searchCPSpecificationOptions(companyId,
			groupId, keywords, start, end, sort);
	}

	@Override
	public com.liferay.commerce.product.model.CPSpecificationOption updateCPOptionCategoryId(
		long cpSpecificationOptionId, long cpOptionCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpSpecificationOptionLocalService.updateCPOptionCategoryId(cpSpecificationOptionId,
			cpOptionCategoryId);
	}

	/**
	* Updates the cp specification option in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpSpecificationOption the cp specification option
	* @return the cp specification option that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPSpecificationOption updateCPSpecificationOption(
		com.liferay.commerce.product.model.CPSpecificationOption cpSpecificationOption) {
		return _cpSpecificationOptionLocalService.updateCPSpecificationOption(cpSpecificationOption);
	}

	@Override
	public com.liferay.commerce.product.model.CPSpecificationOption updateCPSpecificationOption(
		long cpSpecificationOptionId, long cpOptionCategoryId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		boolean facetable, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpSpecificationOptionLocalService.updateCPSpecificationOption(cpSpecificationOptionId,
			cpOptionCategoryId, titleMap, descriptionMap, facetable, key,
			serviceContext);
	}

	@Override
	public CPSpecificationOptionLocalService getWrappedService() {
		return _cpSpecificationOptionLocalService;
	}

	@Override
	public void setWrappedService(
		CPSpecificationOptionLocalService cpSpecificationOptionLocalService) {
		_cpSpecificationOptionLocalService = cpSpecificationOptionLocalService;
	}

	private CPSpecificationOptionLocalService _cpSpecificationOptionLocalService;
}