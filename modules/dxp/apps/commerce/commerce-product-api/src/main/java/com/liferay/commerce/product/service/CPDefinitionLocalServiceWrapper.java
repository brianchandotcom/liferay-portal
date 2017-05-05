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
 * Provides a wrapper for {@link CPDefinitionLocalService}.
 *
 * @author Marco Leo
 * @see CPDefinitionLocalService
 * @generated
 */
@ProviderType
public class CPDefinitionLocalServiceWrapper implements CPDefinitionLocalService,
	ServiceWrapper<CPDefinitionLocalService> {
	public CPDefinitionLocalServiceWrapper(
		CPDefinitionLocalService cpDefinitionLocalService) {
		_cpDefinitionLocalService = cpDefinitionLocalService;
	}

	/**
	* Adds the cp definition to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinition the cp definition
	* @return the cp definition that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinition addCPDefinition(
		com.liferay.commerce.product.model.CPDefinition cpDefinition) {
		return _cpDefinitionLocalService.addCPDefinition(cpDefinition);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition addCPDefinition(
		java.lang.String baseSKU,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String productTypeName, java.lang.String ddmStructureKey,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLocalService.addCPDefinition(baseSKU, titleMap,
			descriptionMap, productTypeName, ddmStructureKey, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	/**
	* Creates a new cp definition with the primary key. Does not add the cp definition to the database.
	*
	* @param CPDefinitionId the primary key for the new cp definition
	* @return the new cp definition
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinition createCPDefinition(
		long CPDefinitionId) {
		return _cpDefinitionLocalService.createCPDefinition(CPDefinitionId);
	}

	/**
	* Deletes the cp definition from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinition the cp definition
	* @return the cp definition that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinition deleteCPDefinition(
		com.liferay.commerce.product.model.CPDefinition cpDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLocalService.deleteCPDefinition(cpDefinition);
	}

	/**
	* Deletes the cp definition with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionId the primary key of the cp definition
	* @return the cp definition that was removed
	* @throws PortalException if a cp definition with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinition deleteCPDefinition(
		long CPDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLocalService.deleteCPDefinition(CPDefinitionId);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition fetchCPDefinition(
		long CPDefinitionId) {
		return _cpDefinitionLocalService.fetchCPDefinition(CPDefinitionId);
	}

	/**
	* Returns the cp definition matching the UUID and group.
	*
	* @param uuid the cp definition's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition, or <code>null</code> if a matching cp definition could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinition fetchCPDefinitionByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _cpDefinitionLocalService.fetchCPDefinitionByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the cp definition with the primary key.
	*
	* @param CPDefinitionId the primary key of the cp definition
	* @return the cp definition
	* @throws PortalException if a cp definition with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinition getCPDefinition(
		long CPDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLocalService.getCPDefinition(CPDefinitionId);
	}

	/**
	* Returns the cp definition matching the UUID and group.
	*
	* @param uuid the cp definition's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition
	* @throws PortalException if a matching cp definition could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinition getCPDefinitionByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLocalService.getCPDefinitionByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Moves the commerce product definition to the recycle bin.
	*
	* @param userId the primary key of the user moving the commerce product definition
	* @param cpDefinition the commerce product definition to be moved
	* @return the moved commerce product definition
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinition moveCPDefinitionToTrash(
		long userId,
		com.liferay.commerce.product.model.CPDefinition cpDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLocalService.moveCPDefinitionToTrash(userId,
			cpDefinition);
	}

	/**
	* Moves the commerce product definition with the ID to the recycle bin.
	*
	* @param userId the primary key of the user moving the commerce product definition
	* @param cpDefinitionId the primary key of the commerce product definition to be moved
	* @return the moved commerce product definition
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinition moveCPDefinitionToTrash(
		long userId, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLocalService.moveCPDefinitionToTrash(userId,
			cpDefinitionId);
	}

	/**
	* Restores the commerce product definition with the ID from the recycle bin.
	*
	* @param userId the primary key of the user restoring the commerce product definition
	* @param cpDefinitionId the primary key of the commerce product definition to be restored
	* @return the restored commerce product definition from the recycle bin
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinition restoreCPDefinitionFromTrash(
		long userId, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLocalService.restoreCPDefinitionFromTrash(userId,
			cpDefinitionId);
	}

	/**
	* Updates the cp definition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinition the cp definition
	* @return the cp definition that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CPDefinition updateCPDefinition(
		com.liferay.commerce.product.model.CPDefinition cpDefinition) {
		return _cpDefinitionLocalService.updateCPDefinition(cpDefinition);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition updateCPDefinition(
		long cpDefinitionId, java.lang.String baseSKU,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String productTypeName, java.lang.String ddmStructureKey,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLocalService.updateCPDefinition(cpDefinitionId,
			baseSKU, titleMap, descriptionMap, productTypeName,
			ddmStructureKey, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition updateStatus(
		long userId, long cpDefinitionId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLocalService.updateStatus(userId, cpDefinitionId,
			status, serviceContext, workflowContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionLocalization fetchCPDefinitionLocalization(
		long CPDefinitionId, java.lang.String languageId) {
		return _cpDefinitionLocalService.fetchCPDefinitionLocalization(CPDefinitionId,
			languageId);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionLocalization getCPDefinitionLocalization(
		long CPDefinitionId, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLocalService.getCPDefinitionLocalization(CPDefinitionId,
			languageId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpDefinitionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpDefinitionLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cpDefinitionLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpDefinitionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinition> searchCPDefinitions(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionLocalService.searchCPDefinitions(companyId,
			groupId, keywords, start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _cpDefinitionLocalService.search(searchContext);
	}

	/**
	* Returns the number of cp definitions.
	*
	* @return the number of cp definitions
	*/
	@Override
	public int getCPDefinitionsCount() {
		return _cpDefinitionLocalService.getCPDefinitionsCount();
	}

	@Override
	public int getCPDefinitionsCount(long groupId) {
		return _cpDefinitionLocalService.getCPDefinitionsCount(groupId);
	}

	@Override
	public int getCPDefinitionsCount(long groupId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.commerce.product.model.CPDefinition> queryDefinition) {
		return _cpDefinitionLocalService.getCPDefinitionsCount(groupId,
			queryDefinition);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpDefinitionLocalService.getOSGiServiceIdentifier();
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
		return _cpDefinitionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpDefinitionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpDefinitionLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List<java.lang.String> getCPDefinitionLocalizationLanguageIds(
		long cpDefinitionId) {
		return _cpDefinitionLocalService.getCPDefinitionLocalizationLanguageIds(cpDefinitionId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionLocalization> getCPDefinitionLocalizations(
		long CPDefinitionId) {
		return _cpDefinitionLocalService.getCPDefinitionLocalizations(CPDefinitionId);
	}

	/**
	* Returns a range of all the cp definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @return the range of cp definitions
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinition> getCPDefinitions(
		int start, int end) {
		return _cpDefinitionLocalService.getCPDefinitions(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinition> getCPDefinitions(
		long groupId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.commerce.product.model.CPDefinition> queryDefinition) {
		return _cpDefinitionLocalService.getCPDefinitions(groupId,
			queryDefinition);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinition> getCPDefinitions(
		long groupId, int start, int end) {
		return _cpDefinitionLocalService.getCPDefinitions(groupId, start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinition> getCPDefinitions(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinition> orderByComparator) {
		return _cpDefinitionLocalService.getCPDefinitions(groupId, start, end,
			orderByComparator);
	}

	/**
	* Returns all the cp definitions matching the UUID and company.
	*
	* @param uuid the UUID of the cp definitions
	* @param companyId the primary key of the company
	* @return the matching cp definitions, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinition> getCPDefinitionsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _cpDefinitionLocalService.getCPDefinitionsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp definitions matching the UUID and company.
	*
	* @param uuid the UUID of the cp definitions
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp definitions
	* @param end the upper bound of the range of cp definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp definitions, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinition> getCPDefinitionsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinition> orderByComparator) {
		return _cpDefinitionLocalService.getCPDefinitionsByUuidAndCompanyId(uuid,
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
		return _cpDefinitionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpDefinitionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public void moveCPDefinitionsToTrash(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpDefinitionLocalService.moveCPDefinitionsToTrash(groupId, userId);
	}

	@Override
	public void updateAsset(long userId,
		com.liferay.commerce.product.model.CPDefinition cpDefinition,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds, java.lang.Double priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpDefinitionLocalService.updateAsset(userId, cpDefinition,
			assetCategoryIds, assetTagNames, assetLinkEntryIds, priority);
	}

	@Override
	public CPDefinitionLocalService getWrappedService() {
		return _cpDefinitionLocalService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionLocalService cpDefinitionLocalService) {
		_cpDefinitionLocalService = cpDefinitionLocalService;
	}

	private CPDefinitionLocalService _cpDefinitionLocalService;
}