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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommercePriceList. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommercePriceListLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListLocalService
 * @see com.liferay.commerce.service.base.CommercePriceListLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommercePriceListLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommercePriceListLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommercePriceListLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce price list to the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceList the commerce price list
	* @return the commerce price list that was added
	*/
	public static com.liferay.commerce.model.CommercePriceList addCommercePriceList(
		com.liferay.commerce.model.CommercePriceList commercePriceList) {
		return getService().addCommercePriceList(commercePriceList);
	}

	public static com.liferay.commerce.model.CommercePriceList addCommercePriceList(
		long commerceCurrencyId, java.lang.String name, double priority,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommercePriceList(commerceCurrencyId, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static void checkCommercePriceLists()
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().checkCommercePriceLists();
	}

	public static void cleanPriceListCache(long groupId) {
		getService().cleanPriceListCache(groupId);
	}

	/**
	* Creates a new commerce price list with the primary key. Does not add the commerce price list to the database.
	*
	* @param commercePriceListId the primary key for the new commerce price list
	* @return the new commerce price list
	*/
	public static com.liferay.commerce.model.CommercePriceList createCommercePriceList(
		long commercePriceListId) {
		return getService().createCommercePriceList(commercePriceListId);
	}

	/**
	* Deletes the commerce price list from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceList the commerce price list
	* @return the commerce price list that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.model.CommercePriceList deleteCommercePriceList(
		com.liferay.commerce.model.CommercePriceList commercePriceList)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommercePriceList(commercePriceList);
	}

	/**
	* Deletes the commerce price list with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListId the primary key of the commerce price list
	* @return the commerce price list that was removed
	* @throws PortalException if a commerce price list with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommercePriceList deleteCommercePriceList(
		long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommercePriceList(commercePriceListId);
	}

	public static void deleteCommercePriceLists(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommercePriceLists(groupId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.commerce.model.CommercePriceList fetchCommercePriceList(
		long commercePriceListId) {
		return getService().fetchCommercePriceList(commercePriceListId);
	}

	/**
	* Returns the commerce price list matching the UUID and group.
	*
	* @param uuid the commerce price list's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public static com.liferay.commerce.model.CommercePriceList fetchCommercePriceListByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchCommercePriceListByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce price list with the primary key.
	*
	* @param commercePriceListId the primary key of the commerce price list
	* @return the commerce price list
	* @throws PortalException if a commerce price list with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommercePriceList getCommercePriceList(
		long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommercePriceList(commercePriceListId);
	}

	/**
	* Returns the commerce price list matching the UUID and group.
	*
	* @param uuid the commerce price list's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce price list
	* @throws PortalException if a matching commerce price list could not be found
	*/
	public static com.liferay.commerce.model.CommercePriceList getCommercePriceListByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommercePriceListByUuidAndGroupId(uuid, groupId);
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
	public static java.util.List<com.liferay.commerce.model.CommercePriceList> getCommercePriceLists(
		int start, int end) {
		return getService().getCommercePriceLists(start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommercePriceList> getCommercePriceLists(
		long groupId, int start, int end) {
		return getService().getCommercePriceLists(groupId, start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommercePriceList> getCommercePriceLists(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommercePriceList> orderByComparator) {
		return getService()
				   .getCommercePriceLists(groupId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns all the commerce price lists matching the UUID and company.
	*
	* @param uuid the UUID of the commerce price lists
	* @param companyId the primary key of the company
	* @return the matching commerce price lists, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.model.CommercePriceList> getCommercePriceListsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getCommercePriceListsByUuidAndCompanyId(uuid, companyId);
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
	public static java.util.List<com.liferay.commerce.model.CommercePriceList> getCommercePriceListsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommercePriceList> orderByComparator) {
		return getService()
				   .getCommercePriceListsByUuidAndCompanyId(uuid, companyId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce price lists.
	*
	* @return the number of commerce price lists
	*/
	public static int getCommercePriceListsCount() {
		return getService().getCommercePriceListsCount();
	}

	public static int getCommercePriceListsCount(long groupId, int status) {
		return getService().getCommercePriceListsCount(groupId, status);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.Optional<com.liferay.commerce.model.CommercePriceList> getUserCommercePriceList(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserCommercePriceList(groupId, userId);
	}

	public static java.util.Optional<com.liferay.commerce.model.CommercePriceList> getUserCommercePriceList(
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserCommercePriceList(serviceContext);
	}

	public static com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return getService().search(searchContext);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommercePriceList> searchCommercePriceLists(
		long companyId, long groupId, java.lang.String keywords, int status,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCommercePriceLists(companyId, groupId, keywords,
			status, start, end, sort);
	}

	/**
	* Updates the commerce price list in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commercePriceList the commerce price list
	* @return the commerce price list that was updated
	*/
	public static com.liferay.commerce.model.CommercePriceList updateCommercePriceList(
		com.liferay.commerce.model.CommercePriceList commercePriceList) {
		return getService().updateCommercePriceList(commercePriceList);
	}

	public static com.liferay.commerce.model.CommercePriceList updateCommercePriceList(
		long commercePriceListId, long commerceCurrencyId,
		java.lang.String name, double priority, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommercePriceList(commercePriceListId,
			commerceCurrencyId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static void updateCommercePriceListCurrencies(
		long commerceCurrencyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().updateCommercePriceListCurrencies(commerceCurrencyId);
	}

	public static com.liferay.commerce.model.CommercePriceList updateStatus(
		long userId, long commercePriceListId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateStatus(userId, commercePriceListId, status,
			serviceContext, workflowContext);
	}

	public static CommercePriceListLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommercePriceListLocalService, CommercePriceListLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommercePriceListLocalService.class);
}