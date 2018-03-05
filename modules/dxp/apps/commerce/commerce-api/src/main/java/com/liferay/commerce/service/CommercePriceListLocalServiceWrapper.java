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
 * Provides a wrapper for {@link CommercePriceListLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListLocalService
 * @generated
 */
@ProviderType
public class CommercePriceListLocalServiceWrapper
	implements CommercePriceListLocalService,
		ServiceWrapper<CommercePriceListLocalService> {
	public CommercePriceListLocalServiceWrapper(
		CommercePriceListLocalService commercePriceListLocalService) {
		_commercePriceListLocalService = commercePriceListLocalService;
	}

	/**
	* Adds the commerce price list to the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceList the commerce price list
	* @return the commerce price list that was added
	*/
	@Override
	public com.liferay.commerce.model.CommercePriceList addCommercePriceList(
		com.liferay.commerce.model.CommercePriceList commercePriceList) {
		return _commercePriceListLocalService.addCommercePriceList(commercePriceList);
	}

	@Override
	public com.liferay.commerce.model.CommercePriceList addCommercePriceList(
		long commerceCurrencyId, java.lang.String name, double priority,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListLocalService.addCommercePriceList(commerceCurrencyId,
			name, priority, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public void checkCommercePriceLists()
		throws com.liferay.portal.kernel.exception.PortalException {
		_commercePriceListLocalService.checkCommercePriceLists();
	}

	@Override
	public void cleanPriceListCache(long groupId) {
		_commercePriceListLocalService.cleanPriceListCache(groupId);
	}

	/**
	* Creates a new commerce price list with the primary key. Does not add the commerce price list to the database.
	*
	* @param commercePriceListId the primary key for the new commerce price list
	* @return the new commerce price list
	*/
	@Override
	public com.liferay.commerce.model.CommercePriceList createCommercePriceList(
		long commercePriceListId) {
		return _commercePriceListLocalService.createCommercePriceList(commercePriceListId);
	}

	/**
	* Deletes the commerce price list from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceList the commerce price list
	* @return the commerce price list that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.model.CommercePriceList deleteCommercePriceList(
		com.liferay.commerce.model.CommercePriceList commercePriceList)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListLocalService.deleteCommercePriceList(commercePriceList);
	}

	/**
	* Deletes the commerce price list with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListId the primary key of the commerce price list
	* @return the commerce price list that was removed
	* @throws PortalException if a commerce price list with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommercePriceList deleteCommercePriceList(
		long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListLocalService.deleteCommercePriceList(commercePriceListId);
	}

	@Override
	public void deleteCommercePriceLists(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commercePriceListLocalService.deleteCommercePriceLists(groupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commercePriceListLocalService.dynamicQuery();
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
		return _commercePriceListLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commercePriceListLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commercePriceListLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _commercePriceListLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commercePriceListLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CommercePriceList fetchCommercePriceList(
		long commercePriceListId) {
		return _commercePriceListLocalService.fetchCommercePriceList(commercePriceListId);
	}

	/**
	* Returns the commerce price list matching the UUID and group.
	*
	* @param uuid the commerce price list's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommercePriceList fetchCommercePriceListByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _commercePriceListLocalService.fetchCommercePriceListByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commercePriceListLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce price list with the primary key.
	*
	* @param commercePriceListId the primary key of the commerce price list
	* @return the commerce price list
	* @throws PortalException if a commerce price list with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommercePriceList getCommercePriceList(
		long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListLocalService.getCommercePriceList(commercePriceListId);
	}

	/**
	* Returns the commerce price list matching the UUID and group.
	*
	* @param uuid the commerce price list's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price list
	* @throws PortalException if a matching commerce price list could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommercePriceList getCommercePriceListByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListLocalService.getCommercePriceListByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the commerce price lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @return the range of commerce price lists
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommercePriceList> getCommercePriceLists(
		int start, int end) {
		return _commercePriceListLocalService.getCommercePriceLists(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommercePriceList> getCommercePriceLists(
		long groupId, int start, int end) {
		return _commercePriceListLocalService.getCommercePriceLists(groupId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommercePriceList> getCommercePriceLists(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommercePriceList> orderByComparator) {
		return _commercePriceListLocalService.getCommercePriceLists(groupId,
			status, start, end, orderByComparator);
	}

	/**
	* Returns all the commerce price lists matching the UUID and company.
	*
	* @param uuid the UUID of the commerce price lists
	* @param companyId the primary key of the company
	* @return the matching commerce price lists, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommercePriceList> getCommercePriceListsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _commercePriceListLocalService.getCommercePriceListsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce price lists matching the UUID and company.
	*
	* @param uuid the UUID of the commerce price lists
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce price lists, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommercePriceList> getCommercePriceListsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommercePriceList> orderByComparator) {
		return _commercePriceListLocalService.getCommercePriceListsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce price lists.
	*
	* @return the number of commerce price lists
	*/
	@Override
	public int getCommercePriceListsCount() {
		return _commercePriceListLocalService.getCommercePriceListsCount();
	}

	@Override
	public int getCommercePriceListsCount(long groupId, int status) {
		return _commercePriceListLocalService.getCommercePriceListsCount(groupId,
			status);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _commercePriceListLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commercePriceListLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commercePriceListLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.Optional<com.liferay.commerce.model.CommercePriceList> getUserCommercePriceList(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListLocalService.getUserCommercePriceList(groupId,
			userId);
	}

	@Override
	public java.util.Optional<com.liferay.commerce.model.CommercePriceList> getUserCommercePriceList(
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListLocalService.getUserCommercePriceList(serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _commercePriceListLocalService.search(searchContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommercePriceList> searchCommercePriceLists(
		long companyId, long groupId, java.lang.String keywords, int status,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListLocalService.searchCommercePriceLists(companyId,
			groupId, keywords, status, start, end, sort);
	}

	/**
	* Updates the commerce price list in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commercePriceList the commerce price list
	* @return the commerce price list that was updated
	*/
	@Override
	public com.liferay.commerce.model.CommercePriceList updateCommercePriceList(
		com.liferay.commerce.model.CommercePriceList commercePriceList) {
		return _commercePriceListLocalService.updateCommercePriceList(commercePriceList);
	}

	@Override
	public com.liferay.commerce.model.CommercePriceList updateCommercePriceList(
		long commercePriceListId, long commerceCurrencyId,
		java.lang.String name, double priority, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListLocalService.updateCommercePriceList(commercePriceListId,
			commerceCurrencyId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public void updateCommercePriceListCurrencies(long commerceCurrencyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commercePriceListLocalService.updateCommercePriceListCurrencies(commerceCurrencyId);
	}

	@Override
	public com.liferay.commerce.model.CommercePriceList updateStatus(
		long userId, long commercePriceListId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListLocalService.updateStatus(userId,
			commercePriceListId, status, serviceContext, workflowContext);
	}

	@Override
	public CommercePriceListLocalService getWrappedService() {
		return _commercePriceListLocalService;
	}

	@Override
	public void setWrappedService(
		CommercePriceListLocalService commercePriceListLocalService) {
		_commercePriceListLocalService = commercePriceListLocalService;
	}

	private CommercePriceListLocalService _commercePriceListLocalService;
}