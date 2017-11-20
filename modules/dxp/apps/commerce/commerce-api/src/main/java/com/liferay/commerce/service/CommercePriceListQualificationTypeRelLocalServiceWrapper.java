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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePriceListQualificationTypeRelLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListQualificationTypeRelLocalService
 * @generated
 */
@ProviderType
public class CommercePriceListQualificationTypeRelLocalServiceWrapper
	implements CommercePriceListQualificationTypeRelLocalService,
		ServiceWrapper<CommercePriceListQualificationTypeRelLocalService> {
	public CommercePriceListQualificationTypeRelLocalServiceWrapper(
		CommercePriceListQualificationTypeRelLocalService commercePriceListQualificationTypeRelLocalService) {
		_commercePriceListQualificationTypeRelLocalService = commercePriceListQualificationTypeRelLocalService;
	}

	/**
	* Adds the commerce price list qualification type rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListQualificationTypeRel the commerce price list qualification type rel
	* @return the commerce price list qualification type rel that was added
	*/
	@Override
	public com.liferay.commerce.model.CommercePriceListQualificationTypeRel addCommercePriceListQualificationTypeRel(
		com.liferay.commerce.model.CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel) {
		return _commercePriceListQualificationTypeRelLocalService.addCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRel);
	}

	@Override
	public com.liferay.commerce.model.CommercePriceListQualificationTypeRel addCommercePriceListQualificationTypeRel(
		long commercePriceListId,
		java.lang.String commercePriceListQualificationType, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListQualificationTypeRelLocalService.addCommercePriceListQualificationTypeRel(commercePriceListId,
			commercePriceListQualificationType, order, serviceContext);
	}

	/**
	* Creates a new commerce price list qualification type rel with the primary key. Does not add the commerce price list qualification type rel to the database.
	*
	* @param commercePriceListQualificationTypeRelId the primary key for the new commerce price list qualification type rel
	* @return the new commerce price list qualification type rel
	*/
	@Override
	public com.liferay.commerce.model.CommercePriceListQualificationTypeRel createCommercePriceListQualificationTypeRel(
		long commercePriceListQualificationTypeRelId) {
		return _commercePriceListQualificationTypeRelLocalService.createCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRelId);
	}

	/**
	* Deletes the commerce price list qualification type rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListQualificationTypeRel the commerce price list qualification type rel
	* @return the commerce price list qualification type rel that was removed
	*/
	@Override
	public com.liferay.commerce.model.CommercePriceListQualificationTypeRel deleteCommercePriceListQualificationTypeRel(
		com.liferay.commerce.model.CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel) {
		return _commercePriceListQualificationTypeRelLocalService.deleteCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRel);
	}

	/**
	* Deletes the commerce price list qualification type rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListQualificationTypeRelId the primary key of the commerce price list qualification type rel
	* @return the commerce price list qualification type rel that was removed
	* @throws PortalException if a commerce price list qualification type rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommercePriceListQualificationTypeRel deleteCommercePriceListQualificationTypeRel(
		long commercePriceListQualificationTypeRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListQualificationTypeRelLocalService.deleteCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRelId);
	}

	@Override
	public void deleteCommercePriceListQualificationTypeRels(
		long commercePriceListId) {
		_commercePriceListQualificationTypeRelLocalService.deleteCommercePriceListQualificationTypeRels(commercePriceListId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListQualificationTypeRelLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commercePriceListQualificationTypeRelLocalService.dynamicQuery();
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
		return _commercePriceListQualificationTypeRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commercePriceListQualificationTypeRelLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commercePriceListQualificationTypeRelLocalService.dynamicQuery(dynamicQuery,
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
		return _commercePriceListQualificationTypeRelLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commercePriceListQualificationTypeRelLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CommercePriceListQualificationTypeRel fetchCommercePriceListQualificationTypeRel(
		long commercePriceListQualificationTypeRelId) {
		return _commercePriceListQualificationTypeRelLocalService.fetchCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRelId);
	}

	@Override
	public com.liferay.commerce.model.CommercePriceListQualificationTypeRel fetchCommercePriceListQualificationTypeRel(
		java.lang.String commercePriceListQualificationType,
		long commercePriceListId) {
		return _commercePriceListQualificationTypeRelLocalService.fetchCommercePriceListQualificationTypeRel(commercePriceListQualificationType,
			commercePriceListId);
	}

	/**
	* Returns the commerce price list qualification type rel matching the UUID and group.
	*
	* @param uuid the commerce price list qualification type rel's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommercePriceListQualificationTypeRel fetchCommercePriceListQualificationTypeRelByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _commercePriceListQualificationTypeRelLocalService.fetchCommercePriceListQualificationTypeRelByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commercePriceListQualificationTypeRelLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce price list qualification type rel with the primary key.
	*
	* @param commercePriceListQualificationTypeRelId the primary key of the commerce price list qualification type rel
	* @return the commerce price list qualification type rel
	* @throws PortalException if a commerce price list qualification type rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommercePriceListQualificationTypeRel getCommercePriceListQualificationTypeRel(
		long commercePriceListQualificationTypeRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListQualificationTypeRelLocalService.getCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRelId);
	}

	/**
	* Returns the commerce price list qualification type rel matching the UUID and group.
	*
	* @param uuid the commerce price list qualification type rel's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price list qualification type rel
	* @throws PortalException if a matching commerce price list qualification type rel could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommercePriceListQualificationTypeRel getCommercePriceListQualificationTypeRelByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListQualificationTypeRelLocalService.getCommercePriceListQualificationTypeRelByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the commerce price list qualification type rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @return the range of commerce price list qualification type rels
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> getCommercePriceListQualificationTypeRels(
		int start, int end) {
		return _commercePriceListQualificationTypeRelLocalService.getCommercePriceListQualificationTypeRels(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> getCommercePriceListQualificationTypeRels(
		long commercePriceListId) {
		return _commercePriceListQualificationTypeRelLocalService.getCommercePriceListQualificationTypeRels(commercePriceListId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> getCommercePriceListQualificationTypeRels(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> orderByComparator) {
		return _commercePriceListQualificationTypeRelLocalService.getCommercePriceListQualificationTypeRels(commercePriceListId,
			start, end, orderByComparator);
	}

	/**
	* Returns all the commerce price list qualification type rels matching the UUID and company.
	*
	* @param uuid the UUID of the commerce price list qualification type rels
	* @param companyId the primary key of the company
	* @return the matching commerce price list qualification type rels, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> getCommercePriceListQualificationTypeRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _commercePriceListQualificationTypeRelLocalService.getCommercePriceListQualificationTypeRelsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce price list qualification type rels matching the UUID and company.
	*
	* @param uuid the UUID of the commerce price list qualification type rels
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce price list qualification type rels
	* @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce price list qualification type rels, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> getCommercePriceListQualificationTypeRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> orderByComparator) {
		return _commercePriceListQualificationTypeRelLocalService.getCommercePriceListQualificationTypeRelsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce price list qualification type rels.
	*
	* @return the number of commerce price list qualification type rels
	*/
	@Override
	public int getCommercePriceListQualificationTypeRelsCount() {
		return _commercePriceListQualificationTypeRelLocalService.getCommercePriceListQualificationTypeRelsCount();
	}

	@Override
	public int getCommercePriceListQualificationTypeRelsCount(
		long commercePriceListId) {
		return _commercePriceListQualificationTypeRelLocalService.getCommercePriceListQualificationTypeRelsCount(commercePriceListId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _commercePriceListQualificationTypeRelLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commercePriceListQualificationTypeRelLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commercePriceListQualificationTypeRelLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListQualificationTypeRelLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce price list qualification type rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListQualificationTypeRel the commerce price list qualification type rel
	* @return the commerce price list qualification type rel that was updated
	*/
	@Override
	public com.liferay.commerce.model.CommercePriceListQualificationTypeRel updateCommercePriceListQualificationTypeRel(
		com.liferay.commerce.model.CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel) {
		return _commercePriceListQualificationTypeRelLocalService.updateCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRel);
	}

	@Override
	public com.liferay.commerce.model.CommercePriceListQualificationTypeRel updateCommercePriceListQualificationTypeRel(
		long commercePriceListQualificationTypeRelId, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListQualificationTypeRelLocalService.updateCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRelId,
			order, serviceContext);
	}

	@Override
	public CommercePriceListQualificationTypeRelLocalService getWrappedService() {
		return _commercePriceListQualificationTypeRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommercePriceListQualificationTypeRelLocalService commercePriceListQualificationTypeRelLocalService) {
		_commercePriceListQualificationTypeRelLocalService = commercePriceListQualificationTypeRelLocalService;
	}

	private CommercePriceListQualificationTypeRelLocalService _commercePriceListQualificationTypeRelLocalService;
}