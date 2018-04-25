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

package com.liferay.commerce.price.list.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePriceListUserSegmentEntryRelLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUserSegmentEntryRelLocalService
 * @generated
 */
@ProviderType
public class CommercePriceListUserSegmentEntryRelLocalServiceWrapper
	implements CommercePriceListUserSegmentEntryRelLocalService,
		ServiceWrapper<CommercePriceListUserSegmentEntryRelLocalService> {
	public CommercePriceListUserSegmentEntryRelLocalServiceWrapper(
		CommercePriceListUserSegmentEntryRelLocalService commercePriceListUserSegmentEntryRelLocalService) {
		_commercePriceListUserSegmentEntryRelLocalService = commercePriceListUserSegmentEntryRelLocalService;
	}

	/**
	* Adds the commerce price list user segment entry rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserSegmentEntryRel the commerce price list user segment entry rel
	* @return the commerce price list user segment entry rel that was added
	*/
	@Override
	public com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel addCommercePriceListUserSegmentEntryRel(
		com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel) {
		return _commercePriceListUserSegmentEntryRelLocalService.addCommercePriceListUserSegmentEntryRel(commercePriceListUserSegmentEntryRel);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel addCommercePriceListUserSegmentEntryRel(
		long commercePriceListId, long commerceUserSegmentEntryId, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListUserSegmentEntryRelLocalService.addCommercePriceListUserSegmentEntryRel(commercePriceListId,
			commerceUserSegmentEntryId, order, serviceContext);
	}

	/**
	* Creates a new commerce price list user segment entry rel with the primary key. Does not add the commerce price list user segment entry rel to the database.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key for the new commerce price list user segment entry rel
	* @return the new commerce price list user segment entry rel
	*/
	@Override
	public com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel createCommercePriceListUserSegmentEntryRel(
		long commercePriceListUserSegmentEntryRelId) {
		return _commercePriceListUserSegmentEntryRelLocalService.createCommercePriceListUserSegmentEntryRel(commercePriceListUserSegmentEntryRelId);
	}

	/**
	* Deletes the commerce price list user segment entry rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserSegmentEntryRel the commerce price list user segment entry rel
	* @return the commerce price list user segment entry rel that was removed
	*/
	@Override
	public com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel deleteCommercePriceListUserSegmentEntryRel(
		com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel) {
		return _commercePriceListUserSegmentEntryRelLocalService.deleteCommercePriceListUserSegmentEntryRel(commercePriceListUserSegmentEntryRel);
	}

	/**
	* Deletes the commerce price list user segment entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key of the commerce price list user segment entry rel
	* @return the commerce price list user segment entry rel that was removed
	* @throws PortalException if a commerce price list user segment entry rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel deleteCommercePriceListUserSegmentEntryRel(
		long commercePriceListUserSegmentEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListUserSegmentEntryRelLocalService.deleteCommercePriceListUserSegmentEntryRel(commercePriceListUserSegmentEntryRelId);
	}

	@Override
	public void deleteCommercePriceListUserSegmentEntryRels(
		long commercePriceListId) {
		_commercePriceListUserSegmentEntryRelLocalService.deleteCommercePriceListUserSegmentEntryRels(commercePriceListId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListUserSegmentEntryRelLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commercePriceListUserSegmentEntryRelLocalService.dynamicQuery();
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
		return _commercePriceListUserSegmentEntryRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.price.list.model.impl.CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commercePriceListUserSegmentEntryRelLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.price.list.model.impl.CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commercePriceListUserSegmentEntryRelLocalService.dynamicQuery(dynamicQuery,
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
		return _commercePriceListUserSegmentEntryRelLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commercePriceListUserSegmentEntryRelLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel fetchCommercePriceListUserSegmentEntryRel(
		long commercePriceListUserSegmentEntryRelId) {
		return _commercePriceListUserSegmentEntryRelLocalService.fetchCommercePriceListUserSegmentEntryRel(commercePriceListUserSegmentEntryRelId);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel fetchCommercePriceListUserSegmentEntryRel(
		long commercePriceListId, long commerceUserSegmentEntryId) {
		return _commercePriceListUserSegmentEntryRelLocalService.fetchCommercePriceListUserSegmentEntryRel(commercePriceListId,
			commerceUserSegmentEntryId);
	}

	/**
	* Returns the commerce price list user segment entry rel matching the UUID and group.
	*
	* @param uuid the commerce price list user segment entry rel's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	@Override
	public com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel fetchCommercePriceListUserSegmentEntryRelByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _commercePriceListUserSegmentEntryRelLocalService.fetchCommercePriceListUserSegmentEntryRelByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commercePriceListUserSegmentEntryRelLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce price list user segment entry rel with the primary key.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key of the commerce price list user segment entry rel
	* @return the commerce price list user segment entry rel
	* @throws PortalException if a commerce price list user segment entry rel with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel getCommercePriceListUserSegmentEntryRel(
		long commercePriceListUserSegmentEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListUserSegmentEntryRelLocalService.getCommercePriceListUserSegmentEntryRel(commercePriceListUserSegmentEntryRelId);
	}

	/**
	* Returns the commerce price list user segment entry rel matching the UUID and group.
	*
	* @param uuid the commerce price list user segment entry rel's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price list user segment entry rel
	* @throws PortalException if a matching commerce price list user segment entry rel could not be found
	*/
	@Override
	public com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel getCommercePriceListUserSegmentEntryRelByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListUserSegmentEntryRelLocalService.getCommercePriceListUserSegmentEntryRelByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the commerce price list user segment entry rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.price.list.model.impl.CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @return the range of commerce price list user segment entry rels
	*/
	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRels(
		int start, int end) {
		return _commercePriceListUserSegmentEntryRelLocalService.getCommercePriceListUserSegmentEntryRels(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRels(
		long commercePriceListId) {
		return _commercePriceListUserSegmentEntryRelLocalService.getCommercePriceListUserSegmentEntryRels(commercePriceListId);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRels(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel> orderByComparator) {
		return _commercePriceListUserSegmentEntryRelLocalService.getCommercePriceListUserSegmentEntryRels(commercePriceListId,
			start, end, orderByComparator);
	}

	/**
	* Returns all the commerce price list user segment entry rels matching the UUID and company.
	*
	* @param uuid the UUID of the commerce price list user segment entry rels
	* @param companyId the primary key of the company
	* @return the matching commerce price list user segment entry rels, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _commercePriceListUserSegmentEntryRelLocalService.getCommercePriceListUserSegmentEntryRelsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce price list user segment entry rels matching the UUID and company.
	*
	* @param uuid the UUID of the commerce price list user segment entry rels
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce price list user segment entry rels, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRelsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel> orderByComparator) {
		return _commercePriceListUserSegmentEntryRelLocalService.getCommercePriceListUserSegmentEntryRelsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce price list user segment entry rels.
	*
	* @return the number of commerce price list user segment entry rels
	*/
	@Override
	public int getCommercePriceListUserSegmentEntryRelsCount() {
		return _commercePriceListUserSegmentEntryRelLocalService.getCommercePriceListUserSegmentEntryRelsCount();
	}

	@Override
	public int getCommercePriceListUserSegmentEntryRelsCount(
		long commercePriceListId) {
		return _commercePriceListUserSegmentEntryRelLocalService.getCommercePriceListUserSegmentEntryRelsCount(commercePriceListId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _commercePriceListUserSegmentEntryRelLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commercePriceListUserSegmentEntryRelLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commercePriceListUserSegmentEntryRelLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListUserSegmentEntryRelLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce price list user segment entry rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserSegmentEntryRel the commerce price list user segment entry rel
	* @return the commerce price list user segment entry rel that was updated
	*/
	@Override
	public com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel updateCommercePriceListUserSegmentEntryRel(
		com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel) {
		return _commercePriceListUserSegmentEntryRelLocalService.updateCommercePriceListUserSegmentEntryRel(commercePriceListUserSegmentEntryRel);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel updateCommercePriceListUserSegmentEntryRel(
		long commercePriceListUserSegmentEntryRelId, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListUserSegmentEntryRelLocalService.updateCommercePriceListUserSegmentEntryRel(commercePriceListUserSegmentEntryRelId,
			order, serviceContext);
	}

	@Override
	public CommercePriceListUserSegmentEntryRelLocalService getWrappedService() {
		return _commercePriceListUserSegmentEntryRelLocalService;
	}

	@Override
	public void setWrappedService(
		CommercePriceListUserSegmentEntryRelLocalService commercePriceListUserSegmentEntryRelLocalService) {
		_commercePriceListUserSegmentEntryRelLocalService = commercePriceListUserSegmentEntryRelLocalService;
	}

	private CommercePriceListUserSegmentEntryRelLocalService _commercePriceListUserSegmentEntryRelLocalService;
}