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
 * Provides a wrapper for {@link CommerceTirePriceEntryLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTirePriceEntryLocalService
 * @generated
 */
@ProviderType
public class CommerceTirePriceEntryLocalServiceWrapper
	implements CommerceTirePriceEntryLocalService,
		ServiceWrapper<CommerceTirePriceEntryLocalService> {
	public CommerceTirePriceEntryLocalServiceWrapper(
		CommerceTirePriceEntryLocalService commerceTirePriceEntryLocalService) {
		_commerceTirePriceEntryLocalService = commerceTirePriceEntryLocalService;
	}

	/**
	* Adds the commerce tire price entry to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTirePriceEntry the commerce tire price entry
	* @return the commerce tire price entry that was added
	*/
	@Override
	public com.liferay.commerce.model.CommerceTirePriceEntry addCommerceTirePriceEntry(
		com.liferay.commerce.model.CommerceTirePriceEntry commerceTirePriceEntry) {
		return _commerceTirePriceEntryLocalService.addCommerceTirePriceEntry(commerceTirePriceEntry);
	}

	@Override
	public com.liferay.commerce.model.CommerceTirePriceEntry addCommerceTirePriceEntry(
		long commercePriceEntryId, double price, int minQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTirePriceEntryLocalService.addCommerceTirePriceEntry(commercePriceEntryId,
			price, minQuantity, serviceContext);
	}

	/**
	* Creates a new commerce tire price entry with the primary key. Does not add the commerce tire price entry to the database.
	*
	* @param CommerceTirePriceEntryId the primary key for the new commerce tire price entry
	* @return the new commerce tire price entry
	*/
	@Override
	public com.liferay.commerce.model.CommerceTirePriceEntry createCommerceTirePriceEntry(
		long CommerceTirePriceEntryId) {
		return _commerceTirePriceEntryLocalService.createCommerceTirePriceEntry(CommerceTirePriceEntryId);
	}

	@Override
	public void deleteCommerceTirePriceEntries(long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceTirePriceEntryLocalService.deleteCommerceTirePriceEntries(commercePriceEntryId);
	}

	/**
	* Deletes the commerce tire price entry from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTirePriceEntry the commerce tire price entry
	* @return the commerce tire price entry that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.model.CommerceTirePriceEntry deleteCommerceTirePriceEntry(
		com.liferay.commerce.model.CommerceTirePriceEntry commerceTirePriceEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTirePriceEntryLocalService.deleteCommerceTirePriceEntry(commerceTirePriceEntry);
	}

	/**
	* Deletes the commerce tire price entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CommerceTirePriceEntryId the primary key of the commerce tire price entry
	* @return the commerce tire price entry that was removed
	* @throws PortalException if a commerce tire price entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceTirePriceEntry deleteCommerceTirePriceEntry(
		long CommerceTirePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTirePriceEntryLocalService.deleteCommerceTirePriceEntry(CommerceTirePriceEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTirePriceEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceTirePriceEntryLocalService.dynamicQuery();
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
		return _commerceTirePriceEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceTirePriceEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceTirePriceEntryLocalService.dynamicQuery(dynamicQuery,
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
		return _commerceTirePriceEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceTirePriceEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceTirePriceEntry fetchCommerceTirePriceEntry(
		long CommerceTirePriceEntryId) {
		return _commerceTirePriceEntryLocalService.fetchCommerceTirePriceEntry(CommerceTirePriceEntryId);
	}

	/**
	* Returns the commerce tire price entry matching the UUID and group.
	*
	* @param uuid the commerce tire price entry's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceTirePriceEntry fetchCommerceTirePriceEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _commerceTirePriceEntryLocalService.fetchCommerceTirePriceEntryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceTirePriceEntryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the commerce tire price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @return the range of commerce tire price entries
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTirePriceEntry> getCommerceTirePriceEntries(
		int start, int end) {
		return _commerceTirePriceEntryLocalService.getCommerceTirePriceEntries(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTirePriceEntry> getCommerceTirePriceEntries(
		long commercePriceEntryId, int start, int end) {
		return _commerceTirePriceEntryLocalService.getCommerceTirePriceEntries(commercePriceEntryId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTirePriceEntry> getCommerceTirePriceEntries(
		long commercePriceEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceTirePriceEntry> orderByComparator) {
		return _commerceTirePriceEntryLocalService.getCommerceTirePriceEntries(commercePriceEntryId,
			start, end, orderByComparator);
	}

	/**
	* Returns all the commerce tire price entries matching the UUID and company.
	*
	* @param uuid the UUID of the commerce tire price entries
	* @param companyId the primary key of the company
	* @return the matching commerce tire price entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTirePriceEntry> getCommerceTirePriceEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _commerceTirePriceEntryLocalService.getCommerceTirePriceEntriesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce tire price entries matching the UUID and company.
	*
	* @param uuid the UUID of the commerce tire price entries
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce tire price entries
	* @param end the upper bound of the range of commerce tire price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce tire price entries, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTirePriceEntry> getCommerceTirePriceEntriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceTirePriceEntry> orderByComparator) {
		return _commerceTirePriceEntryLocalService.getCommerceTirePriceEntriesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce tire price entries.
	*
	* @return the number of commerce tire price entries
	*/
	@Override
	public int getCommerceTirePriceEntriesCount() {
		return _commerceTirePriceEntryLocalService.getCommerceTirePriceEntriesCount();
	}

	@Override
	public int getCommerceTirePriceEntriesCount(long commercePriceEntryId) {
		return _commerceTirePriceEntryLocalService.getCommerceTirePriceEntriesCount(commercePriceEntryId);
	}

	/**
	* Returns the commerce tire price entry with the primary key.
	*
	* @param CommerceTirePriceEntryId the primary key of the commerce tire price entry
	* @return the commerce tire price entry
	* @throws PortalException if a commerce tire price entry with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceTirePriceEntry getCommerceTirePriceEntry(
		long CommerceTirePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTirePriceEntryLocalService.getCommerceTirePriceEntry(CommerceTirePriceEntryId);
	}

	/**
	* Returns the commerce tire price entry matching the UUID and group.
	*
	* @param uuid the commerce tire price entry's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce tire price entry
	* @throws PortalException if a matching commerce tire price entry could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceTirePriceEntry getCommerceTirePriceEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTirePriceEntryLocalService.getCommerceTirePriceEntryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _commerceTirePriceEntryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceTirePriceEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceTirePriceEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTirePriceEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _commerceTirePriceEntryLocalService.search(searchContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceTirePriceEntry> searchCommerceTirePriceEntries(
		long companyId, long groupId, long commercePriceEntryId,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTirePriceEntryLocalService.searchCommerceTirePriceEntries(companyId,
			groupId, commercePriceEntryId, keywords, start, end, sort);
	}

	/**
	* Updates the commerce tire price entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceTirePriceEntry the commerce tire price entry
	* @return the commerce tire price entry that was updated
	*/
	@Override
	public com.liferay.commerce.model.CommerceTirePriceEntry updateCommerceTirePriceEntry(
		com.liferay.commerce.model.CommerceTirePriceEntry commerceTirePriceEntry) {
		return _commerceTirePriceEntryLocalService.updateCommerceTirePriceEntry(commerceTirePriceEntry);
	}

	@Override
	public com.liferay.commerce.model.CommerceTirePriceEntry updateCommerceTirePriceEntry(
		long commerceTirePriceEntryId, double price, int minQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTirePriceEntryLocalService.updateCommerceTirePriceEntry(commerceTirePriceEntryId,
			price, minQuantity, serviceContext);
	}

	@Override
	public CommerceTirePriceEntryLocalService getWrappedService() {
		return _commerceTirePriceEntryLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceTirePriceEntryLocalService commerceTirePriceEntryLocalService) {
		_commerceTirePriceEntryLocalService = commerceTirePriceEntryLocalService;
	}

	private CommerceTirePriceEntryLocalService _commerceTirePriceEntryLocalService;
}