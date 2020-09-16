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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommercePriceList. This utility wraps
 * <code>com.liferay.commerce.price.list.service.impl.CommercePriceListLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListLocalService
 * @generated
 */
public class CommercePriceListLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.price.list.service.impl.CommercePriceListLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.price.list.model.CommercePriceList
			addCatalogBaseCommercePriceList(
				long groupId, long userId, long commerceCurrencyId, String type,
				String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCatalogBaseCommercePriceList(
			groupId, userId, commerceCurrencyId, type, name, serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.commerce.price.list.model.CommercePriceList
			addCommerceCatalogBasePriceList(
				long groupId, long userId, long commerceCurrencyId, String type,
				String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceCatalogBasePriceList(
			groupId, userId, commerceCurrencyId, type, name, serviceContext);
	}

	/**
	 * Adds the commerce price list to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePriceListLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePriceList the commerce price list
	 * @return the commerce price list that was added
	 */
	public static com.liferay.commerce.price.list.model.CommercePriceList
		addCommercePriceList(
			com.liferay.commerce.price.list.model.CommercePriceList
				commercePriceList) {

		return getService().addCommercePriceList(commercePriceList);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			addCommercePriceList(
				long groupId, long userId, long commerceCurrencyId,
				boolean netPrice, long parentCommercePriceListId, String name,
				double priority, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, String externalReferenceCode,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceList(
			groupId, userId, commerceCurrencyId, netPrice,
			parentCommercePriceListId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, externalReferenceCode,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			addCommercePriceList(
				long groupId, long userId, long commerceCurrencyId,
				boolean netPrice, String type, long parentCommercePriceListId,
				boolean catalogBasePriceList, String name, double priority,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, String externalReferenceCode,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceList(
			groupId, userId, commerceCurrencyId, netPrice, type,
			parentCommercePriceListId, catalogBasePriceList, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			externalReferenceCode, neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			addCommercePriceList(
				long groupId, long userId, long commerceCurrencyId,
				long parentCommercePriceListId, String name, double priority,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceList(
			groupId, userId, commerceCurrencyId, parentCommercePriceListId,
			name, priority, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			addCommercePriceList(
				long groupId, long userId, long commerceCurrencyId,
				long parentCommercePriceListId, String name, double priority,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, String externalReferenceCode,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceList(
			groupId, userId, commerceCurrencyId, parentCommercePriceListId,
			name, priority, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, externalReferenceCode, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			addCommercePriceList(
				long groupId, long userId, long commerceCurrencyId, String name,
				double priority, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceList(
			groupId, userId, commerceCurrencyId, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			addCommercePriceList(
				long groupId, long userId, long commerceCurrencyId, String name,
				double priority, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, String externalReferenceCode,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceList(
			groupId, userId, commerceCurrencyId, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			externalReferenceCode, neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			addCommercePriceList(
				long groupId, long userId, long commerceCurrencyId, String type,
				long parentCommercePriceListId, boolean catalogBasePriceList,
				String name, double priority, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				String externalReferenceCode, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceList(
			groupId, userId, commerceCurrencyId, type,
			parentCommercePriceListId, catalogBasePriceList, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			externalReferenceCode, neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			addCommercePriceList(
				long groupId, long userId, long commerceCurrencyId, String type,
				long parentCommercePriceListId, String name, double priority,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceList(
			groupId, userId, commerceCurrencyId, type,
			parentCommercePriceListId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			addCommercePriceList(
				long groupId, long userId, long commerceCurrencyId, String type,
				String name, double priority, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceList(
			groupId, userId, commerceCurrencyId, type, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static void checkCommercePriceLists()
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().checkCommercePriceLists();
	}

	public static void cleanPriceListCache(long companyId) {
		getService().cleanPriceListCache(companyId);
	}

	/**
	 * Creates a new commerce price list with the primary key. Does not add the commerce price list to the database.
	 *
	 * @param commercePriceListId the primary key for the new commerce price list
	 * @return the new commerce price list
	 */
	public static com.liferay.commerce.price.list.model.CommercePriceList
		createCommercePriceList(long commercePriceListId) {

		return getService().createCommercePriceList(commercePriceListId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the commerce price list from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePriceListLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePriceList the commerce price list
	 * @return the commerce price list that was removed
	 * @throws PortalException
	 */
	public static com.liferay.commerce.price.list.model.CommercePriceList
			deleteCommercePriceList(
				com.liferay.commerce.price.list.model.CommercePriceList
					commercePriceList)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommercePriceList(commercePriceList);
	}

	/**
	 * Deletes the commerce price list with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePriceListLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePriceListId the primary key of the commerce price list
	 * @return the commerce price list that was removed
	 * @throws PortalException if a commerce price list with the primary key could not be found
	 */
	public static com.liferay.commerce.price.list.model.CommercePriceList
			deleteCommercePriceList(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommercePriceList(commercePriceListId);
	}

	public static void deleteCommercePriceLists(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommercePriceLists(companyId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.price.list.model.impl.CommercePriceListModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.price.list.model.impl.CommercePriceListModelImpl</code>.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.commerce.price.list.model.CommercePriceList
		fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			fetchCatalogBaseCommercePriceList(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCatalogBaseCommercePriceList(groupId);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			fetchCatalogBaseCommercePriceListByType(long groupId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCatalogBaseCommercePriceListByType(
			groupId, type);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.commerce.price.list.model.CommercePriceList
			fetchCommerceCatalogBasePriceList(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommerceCatalogBasePriceList(groupId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.commerce.price.list.model.CommercePriceList
			fetchCommerceCatalogBasePriceListByType(long groupId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommerceCatalogBasePriceListByType(
			groupId, type);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
		fetchCommercePriceList(long commercePriceListId) {

		return getService().fetchCommercePriceList(commercePriceListId);
	}

	/**
	 * Returns the commerce price list with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce price list's external reference code
	 * @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public static com.liferay.commerce.price.list.model.CommercePriceList
		fetchCommercePriceListByReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchCommercePriceListByReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce price list matching the UUID and group.
	 *
	 * @param uuid the commerce price list's UUID
	 * @param groupId the primary key of the group
	 * @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public static com.liferay.commerce.price.list.model.CommercePriceList
		fetchCommercePriceListByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchCommercePriceListByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			getCatalogBaseCommercePriceList(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCatalogBaseCommercePriceList(groupId);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			getCatalogBaseCommercePriceListByType(long groupId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCatalogBaseCommercePriceListByType(
			groupId, type);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.commerce.price.list.model.CommercePriceList
			getCommerceCatalogBasePriceList(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceCatalogBasePriceList(groupId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.commerce.price.list.model.CommercePriceList
			getCommerceCatalogBasePriceListByType(long groupId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceCatalogBasePriceListByType(
			groupId, type);
	}

	/**
	 * Returns the commerce price list with the primary key.
	 *
	 * @param commercePriceListId the primary key of the commerce price list
	 * @return the commerce price list
	 * @throws PortalException if a commerce price list with the primary key could not be found
	 */
	public static com.liferay.commerce.price.list.model.CommercePriceList
			getCommercePriceList(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceList(commercePriceListId);
	}

	public static java.util.Optional
		<com.liferay.commerce.price.list.model.CommercePriceList>
				getCommercePriceList(
					long companyId, long groupId, long commerceAccountId,
					long[] commerceAccountGroupIds)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceList(
			companyId, groupId, commerceAccountId, commerceAccountGroupIds);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
		getCommercePriceListByAccountAndChannelId(
			long groupId, String type, long commerceAccountId,
			long commerceChannelId) {

		return getService().getCommercePriceListByAccountAndChannelId(
			groupId, type, commerceAccountId, commerceChannelId);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
		getCommercePriceListByAccountGroupIds(
			long groupId, String type, long[] commerceAccountGroupIds) {

		return getService().getCommercePriceListByAccountGroupIds(
			groupId, type, commerceAccountGroupIds);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
		getCommercePriceListByAccountGroupsAndChannelId(
			long groupId, String type, long[] commerceAccountGroupIds,
			long commerceChannelId) {

		return getService().getCommercePriceListByAccountGroupsAndChannelId(
			groupId, type, commerceAccountGroupIds, commerceChannelId);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
		getCommercePriceListByAccountId(
			long groupId, String type, long commerceAccountId) {

		return getService().getCommercePriceListByAccountId(
			groupId, type, commerceAccountId);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
		getCommercePriceListByChannelId(
			long groupId, String type, long commerceChannelId) {

		return getService().getCommercePriceListByChannelId(
			groupId, type, commerceChannelId);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			getCommercePriceListByLowestPrice(
				long groupId, String type, String cPInstanceUuid,
				long commerceAccountId, long[] commerceAccountGroupIds,
				long commerceChannelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceListByLowestPrice(
			groupId, type, cPInstanceUuid, commerceAccountId,
			commerceAccountGroupIds, commerceChannelId);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
		getCommercePriceListByUnqualified(long groupId, String type) {

		return getService().getCommercePriceListByUnqualified(groupId, type);
	}

	/**
	 * Returns the commerce price list matching the UUID and group.
	 *
	 * @param uuid the commerce price list's UUID
	 * @param groupId the primary key of the group
	 * @return the matching commerce price list
	 * @throws PortalException if a matching commerce price list could not be found
	 */
	public static com.liferay.commerce.price.list.model.CommercePriceList
			getCommercePriceListByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceListByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the commerce price lists.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.price.list.model.impl.CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @return the range of commerce price lists
	 */
	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceList>
			getCommercePriceLists(int start, int end) {

		return getService().getCommercePriceLists(start, end);
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceList>
			getCommercePriceLists(long companyId, int start, int end) {

		return getService().getCommercePriceLists(companyId, start, end);
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceList>
			getCommercePriceLists(
				long[] groupIds, long companyId, int start, int end) {

		return getService().getCommercePriceLists(
			groupIds, companyId, start, end);
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceList>
			getCommercePriceLists(
				long[] groupIds, long companyId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.price.list.model.CommercePriceList>
						orderByComparator) {

		return getService().getCommercePriceLists(
			groupIds, companyId, status, start, end, orderByComparator);
	}

	/**
	 * Returns all the commerce price lists matching the UUID and company.
	 *
	 * @param uuid the UUID of the commerce price lists
	 * @param companyId the primary key of the company
	 * @return the matching commerce price lists, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceList>
			getCommercePriceListsByUuidAndCompanyId(
				String uuid, long companyId) {

		return getService().getCommercePriceListsByUuidAndCompanyId(
			uuid, companyId);
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
	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceList>
			getCommercePriceListsByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.price.list.model.CommercePriceList>
						orderByComparator) {

		return getService().getCommercePriceListsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of commerce price lists.
	 *
	 * @return the number of commerce price lists
	 */
	public static int getCommercePriceListsCount() {
		return getService().getCommercePriceListsCount();
	}

	public static int getCommercePriceListsCount(
		long commercePricingClassId, String name) {

		return getService().getCommercePriceListsCount(
			commercePricingClassId, name);
	}

	public static int getCommercePriceListsCount(
		long[] groupIds, long companyId, int status) {

		return getService().getCommercePriceListsCount(
			groupIds, companyId, status);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {

		return getService().search(searchContext);
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceList>
			searchByCommercePricingClassId(
				long commercePricingClassId, String name, int start, int end) {

		return getService().searchByCommercePricingClassId(
			commercePricingClassId, name, start, end);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.price.list.model.CommercePriceList>
				searchCommercePriceLists(
					long companyId, long[] groupIds, String keywords,
					int status, int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommercePriceLists(
			companyId, groupIds, keywords, status, start, end, sort);
	}

	public static int searchCommercePriceListsCount(
			long companyId, long[] groupIds, String keywords, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommercePriceListsCount(
			companyId, groupIds, keywords, status);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			setCatalogBasePriceList(
				long commercePriceListId, boolean catalogBasePriceList)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().setCatalogBasePriceList(
			commercePriceListId, catalogBasePriceList);
	}

	public static void setCatalogBasePriceList(
			long groupId, long commercePriceListId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().setCatalogBasePriceList(
			groupId, commercePriceListId, type);
	}

	/**
	 * Updates the commerce price list in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommercePriceListLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commercePriceList the commerce price list
	 * @return the commerce price list that was updated
	 */
	public static com.liferay.commerce.price.list.model.CommercePriceList
		updateCommercePriceList(
			com.liferay.commerce.price.list.model.CommercePriceList
				commercePriceList) {

		return getService().updateCommercePriceList(commercePriceList);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			updateCommercePriceList(
				long commercePriceListId, long commerceCurrencyId,
				boolean netPrice, long parentCommercePriceListId, String name,
				double priority, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommercePriceList(
			commercePriceListId, commerceCurrencyId, netPrice,
			parentCommercePriceListId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			updateCommercePriceList(
				long commercePriceListId, long commerceCurrencyId,
				boolean netPrice, String type, long parentCommercePriceListId,
				boolean catalogBasePriceList, String name, double priority,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommercePriceList(
			commercePriceListId, commerceCurrencyId, netPrice, type,
			parentCommercePriceListId, catalogBasePriceList, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			updateCommercePriceList(
				long commercePriceListId, long commerceCurrencyId,
				long parentCommercePriceListId, String name, double priority,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommercePriceList(
			commercePriceListId, commerceCurrencyId, parentCommercePriceListId,
			name, priority, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			updateCommercePriceList(
				long commercePriceListId, long commerceCurrencyId, String name,
				double priority, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommercePriceList(
			commercePriceListId, commerceCurrencyId, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			updateCommercePriceList(
				long commercePriceListId, long commerceCurrencyId, String type,
				long parentCommercePriceListId, boolean catalogBasePriceList,
				String name, double priority, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommercePriceList(
			commercePriceListId, commerceCurrencyId, type,
			parentCommercePriceListId, catalogBasePriceList, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static void updateCommercePriceListCurrencies(
			long commerceCurrencyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateCommercePriceListCurrencies(commerceCurrencyId);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			updateExternalReferenceCode(
				com.liferay.commerce.price.list.model.CommercePriceList
					commercePriceList,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateExternalReferenceCode(
			commercePriceList, externalReferenceCode);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			updateStatus(
				long userId, long commercePriceListId, int status,
				com.liferay.portal.kernel.service.ServiceContext serviceContext,
				java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStatus(
			userId, commercePriceListId, status, serviceContext,
			workflowContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			upsertCommercePriceList(
				long groupId, long userId, long commercePriceListId,
				long commerceCurrencyId, boolean netPrice, String type,
				long parentCommercePriceListId, boolean catalogBasePriceList,
				String name, double priority, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				String externalReferenceCode, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommercePriceList(
			groupId, userId, commercePriceListId, commerceCurrencyId, netPrice,
			type, parentCommercePriceListId, catalogBasePriceList, name,
			priority, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, externalReferenceCode, neverExpire,
			serviceContext);
	}

	/**
	 * This method is used to insert a new CommercePriceList or update an
	 * existing one
	 *
	 * @param commercePriceListId - <b>Only</b> used when updating an entity;
	 the matching one will be updated
	 * @param commerceCurrencyId
	 * @param parentCommercePriceListId
	 * @param name
	 * @param priority
	 * @param displayDateMonth
	 * @param displayDateDay
	 * @param displayDateYear
	 * @param displayDateHour
	 * @param displayDateMinute
	 * @param expirationDateMonth
	 * @param expirationDateDay
	 * @param expirationDateYear
	 * @param expirationDateHour
	 * @param expirationDateMinute
	 * @param externalReferenceCode - The external identifier code from a 3rd
	 party system to be able to locate the same entity in the portal
	 <b>Only</b> used when updating an entity; the first entity with a
	 matching reference code one will be updated
	 * @param neverExpire
	 * @param serviceContext
	 * @throws PortalException
	 */
	public static com.liferay.commerce.price.list.model.CommercePriceList
			upsertCommercePriceList(
				long groupId, long userId, long commercePriceListId,
				long commerceCurrencyId, long parentCommercePriceListId,
				String name, double priority, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				String externalReferenceCode, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommercePriceList(
			groupId, userId, commercePriceListId, commerceCurrencyId,
			parentCommercePriceListId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, externalReferenceCode,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			upsertCommercePriceList(
				long groupId, long userId, long commercePriceListId,
				long commerceCurrencyId, String name, double priority,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, String externalReferenceCode,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommercePriceList(
			groupId, userId, commercePriceListId, commerceCurrencyId, name,
			priority, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, externalReferenceCode, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			upsertCommercePriceList(
				long groupId, long userId, long commercePriceListId,
				long commerceCurrencyId, String type,
				long parentCommercePriceListId, boolean catalogBasePriceList,
				String name, double priority, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				String externalReferenceCode, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommercePriceList(
			groupId, userId, commercePriceListId, commerceCurrencyId, type,
			parentCommercePriceListId, catalogBasePriceList, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			externalReferenceCode, neverExpire, serviceContext);
	}

	public static CommercePriceListLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommercePriceListLocalService, CommercePriceListLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommercePriceListLocalService.class);

		ServiceTracker
			<CommercePriceListLocalService, CommercePriceListLocalService>
				serviceTracker =
					new ServiceTracker
						<CommercePriceListLocalService,
						 CommercePriceListLocalService>(
							 bundle.getBundleContext(),
							 CommercePriceListLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}