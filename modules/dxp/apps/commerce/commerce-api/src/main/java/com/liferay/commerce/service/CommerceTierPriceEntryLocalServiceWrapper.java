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
 * Provides a wrapper for {@link CommerceTierPriceEntryLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTierPriceEntryLocalService
 * @generated
 */
@ProviderType
public class CommerceTierPriceEntryLocalServiceWrapper
	implements CommerceTierPriceEntryLocalService,
		ServiceWrapper<CommerceTierPriceEntryLocalService> {
	public CommerceTierPriceEntryLocalServiceWrapper(
		CommerceTierPriceEntryLocalService commerceTierPriceEntryLocalService) {
		_commerceTierPriceEntryLocalService = commerceTierPriceEntryLocalService;
	}

	/**
	* Adds the commerce tier price entry to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTierPriceEntry the commerce tier price entry
	* @return the commerce tier price entry that was added
	*/
	@Override
	public com.liferay.commerce.model.CommerceTierPriceEntry addCommerceTierPriceEntry(
		com.liferay.commerce.model.CommerceTierPriceEntry commerceTierPriceEntry) {
		return _commerceTierPriceEntryLocalService.addCommerceTierPriceEntry(commerceTierPriceEntry);
	}

	@Override
	public com.liferay.commerce.model.CommerceTierPriceEntry addCommerceTierPriceEntry(
		long commercePriceEntryId, java.math.BigDecimal price,
		java.math.BigDecimal promoPrice, int minQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTierPriceEntryLocalService.addCommerceTierPriceEntry(commercePriceEntryId,
			price, promoPrice, minQuantity, serviceContext);
	}

	/**
	* Creates a new commerce tier price entry with the primary key. Does not add the commerce tier price entry to the database.
	*
	* @param commerceTierPriceEntryId the primary key for the new commerce tier price entry
	* @return the new commerce tier price entry
	*/
	@Override
	public com.liferay.commerce.model.CommerceTierPriceEntry createCommerceTierPriceEntry(
		long commerceTierPriceEntryId) {
		return _commerceTierPriceEntryLocalService.createCommerceTierPriceEntry(commerceTierPriceEntryId);
	}

	@Override
	public void deleteCommerceTierPriceEntries(long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceTierPriceEntryLocalService.deleteCommerceTierPriceEntries(commercePriceEntryId);
	}

	/**
	* Deletes the commerce tier price entry from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTierPriceEntry the commerce tier price entry
	* @return the commerce tier price entry that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.model.CommerceTierPriceEntry deleteCommerceTierPriceEntry(
		com.liferay.commerce.model.CommerceTierPriceEntry commerceTierPriceEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTierPriceEntryLocalService.deleteCommerceTierPriceEntry(commerceTierPriceEntry);
	}

	/**
	* Deletes the commerce tier price entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTierPriceEntryId the primary key of the commerce tier price entry
	* @return the commerce tier price entry that was removed
	* @throws PortalException if a commerce tier price entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceTierPriceEntry deleteCommerceTierPriceEntry(
		long commerceTierPriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTierPriceEntryLocalService.deleteCommerceTierPriceEntry(commerceTierPriceEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTierPriceEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceTierPriceEntryLocalService.dynamicQuery();
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
		return _commerceTierPriceEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceTierPriceEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceTierPriceEntryLocalService.dynamicQuery(dynamicQuery,
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
		return _commerceTierPriceEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceTierPriceEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceTierPriceEntry fetchCommerceTierPriceEntry(
		long commerceTierPriceEntryId) {
		return _commerceTierPriceEntryLocalService.fetchCommerceTierPriceEntry(commerceTierPriceEntryId);
	}

	/**
	* Returns the commerce tier price entry matching the UUID and group.
	*
	* @param uuid the commerce tier price entry's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceTierPriceEntry fetchCommerceTierPriceEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _commerceTierPriceEntryLocalService.fetchCommerceTierPriceEntryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.commerce.model.CommerceTierPriceEntry findClosestCommerceTierPriceEntry(
		long commercePriceEntryId, int quantity) {
		return _commerceTierPriceEntryLocalService.findClosestCommerceTierPriceEntry(commercePriceEntryId,
			quantity);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceTierPriceEntryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the commerce tier price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @return the range of commerce tier price entries
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTierPriceEntry> getCommerceTierPriceEntries(
		int start, int end) {
		return _commerceTierPriceEntryLocalService.getCommerceTierPriceEntries(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTierPriceEntry> getCommerceTierPriceEntries(
		long commercePriceEntryId, int start, int end) {
		return _commerceTierPriceEntryLocalService.getCommerceTierPriceEntries(commercePriceEntryId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTierPriceEntry> getCommerceTierPriceEntries(
		long commercePriceEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceTierPriceEntry> orderByComparator) {
		return _commerceTierPriceEntryLocalService.getCommerceTierPriceEntries(commercePriceEntryId,
			start, end, orderByComparator);
	}

	/**
	* Returns all the commerce tier price entries matching the UUID and company.
	*
	* @param uuid the UUID of the commerce tier price entries
	* @param companyId the primary key of the company
	* @return the matching commerce tier price entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTierPriceEntry> getCommerceTierPriceEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _commerceTierPriceEntryLocalService.getCommerceTierPriceEntriesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce tier price entries matching the UUID and company.
	*
	* @param uuid the UUID of the commerce tier price entries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce tier price entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTierPriceEntry> getCommerceTierPriceEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceTierPriceEntry> orderByComparator) {
		return _commerceTierPriceEntryLocalService.getCommerceTierPriceEntriesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce tier price entries.
	*
	* @return the number of commerce tier price entries
	*/
	@Override
	public int getCommerceTierPriceEntriesCount() {
		return _commerceTierPriceEntryLocalService.getCommerceTierPriceEntriesCount();
	}

	@Override
	public int getCommerceTierPriceEntriesCount(long commercePriceEntryId) {
		return _commerceTierPriceEntryLocalService.getCommerceTierPriceEntriesCount(commercePriceEntryId);
	}

	/**
	* Returns the commerce tier price entry with the primary key.
	*
	* @param commerceTierPriceEntryId the primary key of the commerce tier price entry
	* @return the commerce tier price entry
	* @throws PortalException if a commerce tier price entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceTierPriceEntry getCommerceTierPriceEntry(
		long commerceTierPriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTierPriceEntryLocalService.getCommerceTierPriceEntry(commerceTierPriceEntryId);
	}

	/**
	* Returns the commerce tier price entry matching the UUID and group.
	*
	* @param uuid the commerce tier price entry's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce tier price entry
	* @throws PortalException if a matching commerce tier price entry could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceTierPriceEntry getCommerceTierPriceEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTierPriceEntryLocalService.getCommerceTierPriceEntryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _commerceTierPriceEntryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceTierPriceEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceTierPriceEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTierPriceEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _commerceTierPriceEntryLocalService.search(searchContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceTierPriceEntry> searchCommerceTierPriceEntries(
		long companyId, long groupId, long commercePriceEntryId,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTierPriceEntryLocalService.searchCommerceTierPriceEntries(companyId,
			groupId, commercePriceEntryId, keywords, start, end, sort);
	}

	/**
	* Updates the commerce tier price entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceTierPriceEntry the commerce tier price entry
	* @return the commerce tier price entry that was updated
	*/
	@Override
	public com.liferay.commerce.model.CommerceTierPriceEntry updateCommerceTierPriceEntry(
		com.liferay.commerce.model.CommerceTierPriceEntry commerceTierPriceEntry) {
		return _commerceTierPriceEntryLocalService.updateCommerceTierPriceEntry(commerceTierPriceEntry);
	}

	@Override
	public com.liferay.commerce.model.CommerceTierPriceEntry updateCommerceTierPriceEntry(
		long commerceTierPriceEntryId, java.math.BigDecimal price,
		java.math.BigDecimal promoPrice, int minQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTierPriceEntryLocalService.updateCommerceTierPriceEntry(commerceTierPriceEntryId,
			price, promoPrice, minQuantity, serviceContext);
	}

	@Override
	public CommerceTierPriceEntryLocalService getWrappedService() {
		return _commerceTierPriceEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceTierPriceEntryLocalService commerceTierPriceEntryLocalService) {
		_commerceTierPriceEntryLocalService = commerceTierPriceEntryLocalService;
	}

	private CommerceTierPriceEntryLocalService _commerceTierPriceEntryLocalService;
}