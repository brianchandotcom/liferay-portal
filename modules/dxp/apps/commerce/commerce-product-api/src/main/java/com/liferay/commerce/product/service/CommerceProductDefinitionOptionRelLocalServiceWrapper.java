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
 * Provides a wrapper for {@link CommerceProductDefinitionOptionRelLocalService}.
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionRelLocalService
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionRelLocalServiceWrapper
	implements CommerceProductDefinitionOptionRelLocalService,
		ServiceWrapper<CommerceProductDefinitionOptionRelLocalService> {
	public CommerceProductDefinitionOptionRelLocalServiceWrapper(
		CommerceProductDefinitionOptionRelLocalService commerceProductDefinitionOptionRelLocalService) {
		_commerceProductDefinitionOptionRelLocalService = commerceProductDefinitionOptionRelLocalService;
	}

	/**
	* Adds the commerce product definition option rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionRel the commerce product definition option rel
	* @return the commerce product definition option rel that was added
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel addCommerceProductDefinitionOptionRel(
		com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel) {
		return _commerceProductDefinitionOptionRelLocalService.addCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRel);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel addCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionId, long commerceProductOptionId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionRelLocalService.addCommerceProductDefinitionOptionRel(commerceProductDefinitionId,
			commerceProductOptionId, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel addCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionId, long commerceProductOptionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, int priority, boolean facetable,
		boolean skuContributor,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionRelLocalService.addCommerceProductDefinitionOptionRel(commerceProductDefinitionId,
			commerceProductOptionId, nameMap, descriptionMap,
			ddmFormFieldTypeName, priority, facetable, skuContributor,
			serviceContext);
	}

	/**
	* Creates a new commerce product definition option rel with the primary key. Does not add the commerce product definition option rel to the database.
	*
	* @param commerceProductDefinitionOptionRelId the primary key for the new commerce product definition option rel
	* @return the new commerce product definition option rel
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel createCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionOptionRelId) {
		return _commerceProductDefinitionOptionRelLocalService.createCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRelId);
	}

	/**
	* Deletes the commerce product definition option rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionRel the commerce product definition option rel
	* @return the commerce product definition option rel that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel deleteCommerceProductDefinitionOptionRel(
		com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionRelLocalService.deleteCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRel);
	}

	/**
	* Deletes the commerce product definition option rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionRelId the primary key of the commerce product definition option rel
	* @return the commerce product definition option rel that was removed
	* @throws PortalException if a commerce product definition option rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel deleteCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionRelLocalService.deleteCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRelId);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel fetchCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionOptionRelId) {
		return _commerceProductDefinitionOptionRelLocalService.fetchCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRelId);
	}

	/**
	* Returns the commerce product definition option rel matching the UUID and group.
	*
	* @param uuid the commerce product definition option rel's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce product definition option rel, or <code>null</code> if a matching commerce product definition option rel could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel fetchCommerceProductDefinitionOptionRelByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _commerceProductDefinitionOptionRelLocalService.fetchCommerceProductDefinitionOptionRelByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the commerce product definition option rel with the primary key.
	*
	* @param commerceProductDefinitionOptionRelId the primary key of the commerce product definition option rel
	* @return the commerce product definition option rel
	* @throws PortalException if a commerce product definition option rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel getCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionRelLocalService.getCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRelId);
	}

	/**
	* Returns the commerce product definition option rel matching the UUID and group.
	*
	* @param uuid the commerce product definition option rel's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce product definition option rel
	* @throws PortalException if a matching commerce product definition option rel could not be found
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel getCommerceProductDefinitionOptionRelByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionRelLocalService.getCommerceProductDefinitionOptionRelByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Updates the commerce product definition option rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionRel the commerce product definition option rel
	* @return the commerce product definition option rel that was updated
	*/
	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel updateCommerceProductDefinitionOptionRel(
		com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel) {
		return _commerceProductDefinitionOptionRelLocalService.updateCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRel);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel updateCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionOptionRelId,
		long commerceProductOptionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, int priority, boolean facetable,
		boolean skuContributor,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionRelLocalService.updateCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRelId,
			commerceProductOptionId, nameMap, descriptionMap,
			ddmFormFieldTypeName, priority, facetable, skuContributor,
			serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceProductDefinitionOptionRelLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceProductDefinitionOptionRelLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _commerceProductDefinitionOptionRelLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceProductDefinitionOptionRelLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionRelLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionRelLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of commerce product definition option rels.
	*
	* @return the number of commerce product definition option rels
	*/
	@Override
	public int getCommerceProductDefinitionOptionRelsCount() {
		return _commerceProductDefinitionOptionRelLocalService.getCommerceProductDefinitionOptionRelsCount();
	}

	@Override
	public int getCommerceProductDefinitionOptionRelsCount(
		long commerceProductDefinitionId) {
		return _commerceProductDefinitionOptionRelLocalService.getCommerceProductDefinitionOptionRelsCount(commerceProductDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceProductDefinitionOptionRelLocalService.getOSGiServiceIdentifier();
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
		return _commerceProductDefinitionOptionRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceProductDefinitionOptionRelLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceProductDefinitionOptionRelLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns a range of all the commerce product definition option rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @return the range of commerce product definition option rels
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> getCommerceProductDefinitionOptionRels(
		int start, int end) {
		return _commerceProductDefinitionOptionRelLocalService.getCommerceProductDefinitionOptionRels(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> getCommerceProductDefinitionOptionRels(
		long commerceProductDefinitionId, int start, int end) {
		return _commerceProductDefinitionOptionRelLocalService.getCommerceProductDefinitionOptionRels(commerceProductDefinitionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> getCommerceProductDefinitionOptionRels(
		long commerceProductDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> orderByComparator) {
		return _commerceProductDefinitionOptionRelLocalService.getCommerceProductDefinitionOptionRels(commerceProductDefinitionId,
			start, end, orderByComparator);
	}

	/**
	* Returns all the commerce product definition option rels matching the UUID and company.
	*
	* @param uuid the UUID of the commerce product definition option rels
	* @param companyId the primary key of the company
	* @return the matching commerce product definition option rels, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> getCommerceProductDefinitionOptionRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _commerceProductDefinitionOptionRelLocalService.getCommerceProductDefinitionOptionRelsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce product definition option rels matching the UUID and company.
	*
	* @param uuid the UUID of the commerce product definition option rels
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce product definition option rels
	* @param end the upper bound of the range of commerce product definition option rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce product definition option rels, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> getCommerceProductDefinitionOptionRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> orderByComparator) {
		return _commerceProductDefinitionOptionRelLocalService.getCommerceProductDefinitionOptionRelsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> getSkuContributorCommerceProductDefinitionOptionRels(
		long commerceProductDefinitionId) {
		return _commerceProductDefinitionOptionRelLocalService.getSkuContributorCommerceProductDefinitionOptionRels(commerceProductDefinitionId);
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
		return _commerceProductDefinitionOptionRelLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceProductDefinitionOptionRelLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public void deleteCommerceProductDefinitionOptionRels(
		long commerceProductDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceProductDefinitionOptionRelLocalService.deleteCommerceProductDefinitionOptionRels(commerceProductDefinitionId);
	}

	@Override
	public CommerceProductDefinitionOptionRelLocalService getWrappedService() {
		return _commerceProductDefinitionOptionRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceProductDefinitionOptionRelLocalService commerceProductDefinitionOptionRelLocalService) {
		_commerceProductDefinitionOptionRelLocalService = commerceProductDefinitionOptionRelLocalService;
	}

	private CommerceProductDefinitionOptionRelLocalService _commerceProductDefinitionOptionRelLocalService;
}