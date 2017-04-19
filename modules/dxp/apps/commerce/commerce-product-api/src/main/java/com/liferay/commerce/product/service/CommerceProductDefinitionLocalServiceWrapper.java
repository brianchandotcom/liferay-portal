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
 * Provides a wrapper for {@link CommerceProductDefinitionLocalService}.
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionLocalService
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionLocalServiceWrapper
	implements CommerceProductDefinitionLocalService,
		ServiceWrapper<CommerceProductDefinitionLocalService> {
	public CommerceProductDefinitionLocalServiceWrapper(
		CommerceProductDefinitionLocalService commerceProductDefinitionLocalService) {
		_commerceProductDefinitionLocalService = commerceProductDefinitionLocalService;
	}

	/**
	* Adds the commerce product definition to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinition the commerce product definition
	* @return the commerce product definition that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinition addCommerceProductDefinition(
		com.liferay.commerce.product.model.CommerceProductDefinition commerceProductDefinition) {
		return _commerceProductDefinitionLocalService.addCommerceProductDefinition(commerceProductDefinition);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinition addCommerceProductDefinition(
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
		return _commerceProductDefinitionLocalService.addCommerceProductDefinition(baseSKU,
			titleMap, descriptionMap, productTypeName, ddmStructureKey,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	/**
	* Creates a new commerce product definition with the primary key. Does not add the commerce product definition to the database.
	*
	* @param commerceProductDefinitionId the primary key for the new commerce product definition
	* @return the new commerce product definition
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinition createCommerceProductDefinition(
		long commerceProductDefinitionId) {
		return _commerceProductDefinitionLocalService.createCommerceProductDefinition(commerceProductDefinitionId);
	}

	/**
	* Deletes the commerce product definition from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinition the commerce product definition
	* @return the commerce product definition that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinition deleteCommerceProductDefinition(
		com.liferay.commerce.product.model.CommerceProductDefinition commerceProductDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionLocalService.deleteCommerceProductDefinition(commerceProductDefinition);
	}

	/**
	* Deletes the commerce product definition with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionId the primary key of the commerce product definition
	* @return the commerce product definition that was removed
	* @throws PortalException if a commerce product definition with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinition deleteCommerceProductDefinition(
		long commerceProductDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionLocalService.deleteCommerceProductDefinition(commerceProductDefinitionId);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinition fetchCommerceProductDefinition(
		long commerceProductDefinitionId) {
		return _commerceProductDefinitionLocalService.fetchCommerceProductDefinition(commerceProductDefinitionId);
	}

	/**
	* Returns the commerce product definition matching the UUID and group.
	*
	* @param uuid the commerce product definition's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce product definition, or <code>null</code> if a matching commerce product definition could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinition fetchCommerceProductDefinitionByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _commerceProductDefinitionLocalService.fetchCommerceProductDefinitionByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the commerce product definition with the primary key.
	*
	* @param commerceProductDefinitionId the primary key of the commerce product definition
	* @return the commerce product definition
	* @throws PortalException if a commerce product definition with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinition getCommerceProductDefinition(
		long commerceProductDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionLocalService.getCommerceProductDefinition(commerceProductDefinitionId);
	}

	/**
	* Returns the commerce product definition matching the UUID and group.
	*
	* @param uuid the commerce product definition's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce product definition
	* @throws PortalException if a matching commerce product definition could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinition getCommerceProductDefinitionByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionLocalService.getCommerceProductDefinitionByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Updates the commerce product definition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinition the commerce product definition
	* @return the commerce product definition that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinition updateCommerceProductDefinition(
		com.liferay.commerce.product.model.CommerceProductDefinition commerceProductDefinition) {
		return _commerceProductDefinitionLocalService.updateCommerceProductDefinition(commerceProductDefinition);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinition updateCommerceProductDefinition(
		long commerceProductDefinitionId, java.lang.String baseSKU,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String productTypeName, java.lang.String ddmStructureKey,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionLocalService.updateCommerceProductDefinition(commerceProductDefinitionId,
			baseSKU, titleMap, descriptionMap, productTypeName,
			ddmStructureKey, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinition updateStatus(
		long userId, long commerceProductDefinitionId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionLocalService.updateStatus(userId,
			commerceProductDefinitionId, status, serviceContext, workflowContext);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionLocalization fetchCommerceProductDefinitionLocalization(
		long commerceProductDefinitionId, java.lang.String languageId) {
		return _commerceProductDefinitionLocalService.fetchCommerceProductDefinitionLocalization(commerceProductDefinitionId,
			languageId);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionLocalization getCommerceProductDefinitionLocalization(
		long commerceProductDefinitionId, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionLocalService.getCommerceProductDefinitionLocalization(commerceProductDefinitionId,
			languageId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceProductDefinitionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceProductDefinitionLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _commerceProductDefinitionLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceProductDefinitionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of commerce product definitions.
	*
	* @return the number of commerce product definitions
	*/
	@Override
	public int getCommerceProductDefinitionsCount() {
		return _commerceProductDefinitionLocalService.getCommerceProductDefinitionsCount();
	}

	@Override
	public int getCommerceProductDefinitionsCount(long groupId) {
		return _commerceProductDefinitionLocalService.getCommerceProductDefinitionsCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceProductDefinitionLocalService.getOSGiServiceIdentifier();
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
		return _commerceProductDefinitionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceProductDefinitionLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceProductDefinitionLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns a range of all the commerce product definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @return the range of commerce product definitions
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinition> getCommerceProductDefinitions(
		int start, int end) {
		return _commerceProductDefinitionLocalService.getCommerceProductDefinitions(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinition> getCommerceProductDefinitions(
		long groupId, int start, int end) {
		return _commerceProductDefinitionLocalService.getCommerceProductDefinitions(groupId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinition> getCommerceProductDefinitions(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductDefinition> orderByComparator) {
		return _commerceProductDefinitionLocalService.getCommerceProductDefinitions(groupId,
			start, end, orderByComparator);
	}

	/**
	* Returns all the commerce product definitions matching the UUID and company.
	*
	* @param uuid the UUID of the commerce product definitions
	* @param companyId the primary key of the company
	* @return the matching commerce product definitions, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinition> getCommerceProductDefinitionsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _commerceProductDefinitionLocalService.getCommerceProductDefinitionsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce product definitions matching the UUID and company.
	*
	* @param uuid the UUID of the commerce product definitions
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce product definitions
	* @param end the upper bound of the range of commerce product definitions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce product definitions, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinition> getCommerceProductDefinitionsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductDefinition> orderByComparator) {
		return _commerceProductDefinitionLocalService.getCommerceProductDefinitionsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getCommerceProductDefinitionDescriptionMap(
		long commerceProductDefinitionId) {
		return _commerceProductDefinitionLocalService.getCommerceProductDefinitionDescriptionMap(commerceProductDefinitionId);
	}

	@Override
	public java.util.Map<java.util.Locale, java.lang.String> getCommerceProductDefinitionTitleMap(
		long commerceProductDefinitionId) {
		return _commerceProductDefinitionLocalService.getCommerceProductDefinitionTitleMap(commerceProductDefinitionId);
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
		return _commerceProductDefinitionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceProductDefinitionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public void updateAsset(long userId,
		com.liferay.commerce.product.model.CommerceProductDefinition commerceProductDefinition,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds, java.lang.Double priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceProductDefinitionLocalService.updateAsset(userId,
			commerceProductDefinition, assetCategoryIds, assetTagNames,
			assetLinkEntryIds, priority);
	}

	@Override
	public CommerceProductDefinitionLocalService getWrappedService() {
		return _commerceProductDefinitionLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceProductDefinitionLocalService commerceProductDefinitionLocalService) {
		_commerceProductDefinitionLocalService = commerceProductDefinitionLocalService;
	}

	private CommerceProductDefinitionLocalService _commerceProductDefinitionLocalService;
}