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
 * Provides a wrapper for {@link CommercePriceEntryLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceEntryLocalService
 * @generated
 */
@ProviderType
public class CommercePriceEntryLocalServiceWrapper
	implements CommercePriceEntryLocalService,
		ServiceWrapper<CommercePriceEntryLocalService> {
	public CommercePriceEntryLocalServiceWrapper(
		CommercePriceEntryLocalService commercePriceEntryLocalService) {
		_commercePriceEntryLocalService = commercePriceEntryLocalService;
	}

	/**
	* Adds the commerce price entry to the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceEntry the commerce price entry
	* @return the commerce price entry that was added
	*/
	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry addCommercePriceEntry(
		com.liferay.commerce.price.list.model.CommercePriceEntry commercePriceEntry) {
		return _commercePriceEntryLocalService.addCommercePriceEntry(commercePriceEntry);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry addCommercePriceEntry(
		long cpInstanceId, long commercePriceListId,
		java.math.BigDecimal price, java.math.BigDecimal promoPrice,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryLocalService.addCommercePriceEntry(cpInstanceId,
			commercePriceListId, price, promoPrice, serviceContext);
	}

	/**
	* Creates a new commerce price entry with the primary key. Does not add the commerce price entry to the database.
	*
	* @param commercePriceEntryId the primary key for the new commerce price entry
	* @return the new commerce price entry
	*/
	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry createCommercePriceEntry(
		long commercePriceEntryId) {
		return _commercePriceEntryLocalService.createCommercePriceEntry(commercePriceEntryId);
	}

	@Override
	public void deleteCommercePriceEntries(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commercePriceEntryLocalService.deleteCommercePriceEntries(commercePriceListId);
	}

	@Override
	public void deleteCommercePriceEntriesByCPInstanceId(long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commercePriceEntryLocalService.deleteCommercePriceEntriesByCPInstanceId(cpInstanceId);
	}

	/**
	* Deletes the commerce price entry from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceEntry the commerce price entry
	* @return the commerce price entry that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry deleteCommercePriceEntry(
		com.liferay.commerce.price.list.model.CommercePriceEntry commercePriceEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryLocalService.deleteCommercePriceEntry(commercePriceEntry);
	}

	/**
	* Deletes the commerce price entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceEntryId the primary key of the commerce price entry
	* @return the commerce price entry that was removed
	* @throws PortalException if a commerce price entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry deleteCommercePriceEntry(
		long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryLocalService.deleteCommercePriceEntry(commercePriceEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commercePriceEntryLocalService.dynamicQuery();
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
		return _commercePriceEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.price.list.model.impl.CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commercePriceEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.price.list.model.impl.CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commercePriceEntryLocalService.dynamicQuery(dynamicQuery,
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
		return _commercePriceEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commercePriceEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry fetchCommercePriceEntry(
		long commercePriceEntryId) {
		return _commercePriceEntryLocalService.fetchCommercePriceEntry(commercePriceEntryId);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry fetchCommercePriceEntry(
		long cpInstanceId, long commercePriceListId) {
		return _commercePriceEntryLocalService.fetchCommercePriceEntry(cpInstanceId,
			commercePriceListId);
	}

	/**
	* Returns the commerce price entry matching the UUID and group.
	*
	* @param uuid the commerce price entry's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry fetchCommercePriceEntryByUuidAndGroupId(
		String uuid, long groupId) {
		return _commercePriceEntryLocalService.fetchCommercePriceEntryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commercePriceEntryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the commerce price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.price.list.model.impl.CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @return the range of commerce price entries
	*/
	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> getCommercePriceEntries(
		int start, int end) {
		return _commercePriceEntryLocalService.getCommercePriceEntries(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> getCommercePriceEntries(
		long commercePriceListId, int start, int end) {
		return _commercePriceEntryLocalService.getCommercePriceEntries(commercePriceListId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> getCommercePriceEntries(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.model.CommercePriceEntry> orderByComparator) {
		return _commercePriceEntryLocalService.getCommercePriceEntries(commercePriceListId,
			start, end, orderByComparator);
	}

	/**
	* Returns all the commerce price entries matching the UUID and company.
	*
	* @param uuid the UUID of the commerce price entries
	* @param companyId the primary key of the company
	* @return the matching commerce price entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> getCommercePriceEntriesByUuidAndCompanyId(
		String uuid, long companyId) {
		return _commercePriceEntryLocalService.getCommercePriceEntriesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce price entries matching the UUID and company.
	*
	* @param uuid the UUID of the commerce price entries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce price entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> getCommercePriceEntriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.model.CommercePriceEntry> orderByComparator) {
		return _commercePriceEntryLocalService.getCommercePriceEntriesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce price entries.
	*
	* @return the number of commerce price entries
	*/
	@Override
	public int getCommercePriceEntriesCount() {
		return _commercePriceEntryLocalService.getCommercePriceEntriesCount();
	}

	@Override
	public int getCommercePriceEntriesCount(long commercePriceListId) {
		return _commercePriceEntryLocalService.getCommercePriceEntriesCount(commercePriceListId);
	}

	/**
	* Returns the commerce price entry with the primary key.
	*
	* @param commercePriceEntryId the primary key of the commerce price entry
	* @return the commerce price entry
	* @throws PortalException if a commerce price entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry getCommercePriceEntry(
		long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryLocalService.getCommercePriceEntry(commercePriceEntryId);
	}

	/**
	* Returns the commerce price entry matching the UUID and group.
	*
	* @param uuid the commerce price entry's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price entry
	* @throws PortalException if a matching commerce price entry could not be found
	*/
	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry getCommercePriceEntryByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryLocalService.getCommercePriceEntryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _commercePriceEntryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commercePriceEntryLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end) {
		return _commercePriceEntryLocalService.getInstanceCommercePriceEntries(cpInstanceId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.model.CommercePriceEntry> orderByComparator) {
		return _commercePriceEntryLocalService.getInstanceCommercePriceEntries(cpInstanceId,
			start, end, orderByComparator);
	}

	@Override
	public int getInstanceCommercePriceEntriesCount(long cpInstanceId) {
		return _commercePriceEntryLocalService.getInstanceCommercePriceEntriesCount(cpInstanceId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePriceEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _commercePriceEntryLocalService.search(searchContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.price.list.model.CommercePriceEntry> searchCommercePriceEntries(
		long companyId, long groupId, long commercePriceListId,
		String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryLocalService.searchCommercePriceEntries(companyId,
			groupId, commercePriceListId, keywords, start, end, sort);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry setHasTierPrice(
		long commercePriceEntryId, boolean hasTierPrice)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryLocalService.setHasTierPrice(commercePriceEntryId,
			hasTierPrice);
	}

	/**
	* Updates the commerce price entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commercePriceEntry the commerce price entry
	* @return the commerce price entry that was updated
	*/
	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry updateCommercePriceEntry(
		com.liferay.commerce.price.list.model.CommercePriceEntry commercePriceEntry) {
		return _commercePriceEntryLocalService.updateCommercePriceEntry(commercePriceEntry);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceEntry updateCommercePriceEntry(
		long commercePriceEntryId, java.math.BigDecimal price,
		java.math.BigDecimal promoPrice,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryLocalService.updateCommercePriceEntry(commercePriceEntryId,
			price, promoPrice, serviceContext);
	}

	@Override
	public CommercePriceEntryLocalService getWrappedService() {
		return _commercePriceEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CommercePriceEntryLocalService commercePriceEntryLocalService) {
		_commercePriceEntryLocalService = commercePriceEntryLocalService;
	}

	private CommercePriceEntryLocalService _commercePriceEntryLocalService;
}