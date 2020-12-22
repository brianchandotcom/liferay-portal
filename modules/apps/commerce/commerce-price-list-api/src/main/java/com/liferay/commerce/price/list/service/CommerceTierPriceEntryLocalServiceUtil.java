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
 * Provides the local service utility for CommerceTierPriceEntry. This utility wraps
 * <code>com.liferay.commerce.price.list.service.impl.CommerceTierPriceEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTierPriceEntryLocalService
 * @generated
 */
public class CommerceTierPriceEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.price.list.service.impl.CommerceTierPriceEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the commerce tier price entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceTierPriceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceTierPriceEntry the commerce tier price entry
	 * @return the commerce tier price entry that was added
	 */
	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
		addCommerceTierPriceEntry(
			com.liferay.commerce.price.list.model.CommerceTierPriceEntry
				commerceTierPriceEntry) {

		return getService().addCommerceTierPriceEntry(commerceTierPriceEntry);
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			addCommerceTierPriceEntry(
				long commercePriceEntryId, java.math.BigDecimal price,
				java.math.BigDecimal promoPrice, boolean bulkPricing,
				int minQuantity,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceTierPriceEntry(
			commercePriceEntryId, price, promoPrice, bulkPricing, minQuantity,
			serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			addCommerceTierPriceEntry(
				long commercePriceEntryId, java.math.BigDecimal price,
				java.math.BigDecimal promoPrice, int minQuantity,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceTierPriceEntry(
			commercePriceEntryId, price, promoPrice, minQuantity,
			serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			addCommerceTierPriceEntry(
				long commercePriceEntryId, String externalReferenceCode,
				java.math.BigDecimal price, java.math.BigDecimal promoPrice,
				boolean bulkPricing, int minQuantity,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceTierPriceEntry(
			commercePriceEntryId, externalReferenceCode, price, promoPrice,
			bulkPricing, minQuantity, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			addCommerceTierPriceEntry(
				long commercePriceEntryId, String externalReferenceCode,
				java.math.BigDecimal price, java.math.BigDecimal promoPrice,
				int minQuantity, boolean bulkPricing, boolean discountDiscovery,
				java.math.BigDecimal discountLevel1,
				java.math.BigDecimal discountLevel2,
				java.math.BigDecimal discountLevel3,
				java.math.BigDecimal discountLevel4, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceTierPriceEntry(
			commercePriceEntryId, externalReferenceCode, price, promoPrice,
			minQuantity, bulkPricing, discountDiscovery, discountLevel1,
			discountLevel2, discountLevel3, discountLevel4, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			addCommerceTierPriceEntry(
				long commercePriceEntryId, String externalReferenceCode,
				java.math.BigDecimal price, java.math.BigDecimal promoPrice,
				int minQuantity,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceTierPriceEntry(
			commercePriceEntryId, externalReferenceCode, price, promoPrice,
			minQuantity, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			addCommerceTierPriceEntry(
				long commercePriceEntryId, String externalReferenceCode,
				java.math.BigDecimal price, int minQuantity,
				boolean bulkPricing, boolean discountDiscovery,
				java.math.BigDecimal discountLevel1,
				java.math.BigDecimal discountLevel2,
				java.math.BigDecimal discountLevel3,
				java.math.BigDecimal discountLevel4, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceTierPriceEntry(
			commercePriceEntryId, externalReferenceCode, price, minQuantity,
			bulkPricing, discountDiscovery, discountLevel1, discountLevel2,
			discountLevel3, discountLevel4, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static void checkCommerceTierPriceEntries()
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().checkCommerceTierPriceEntries();
	}

	/**
	 * Creates a new commerce tier price entry with the primary key. Does not add the commerce tier price entry to the database.
	 *
	 * @param commerceTierPriceEntryId the primary key for the new commerce tier price entry
	 * @return the new commerce tier price entry
	 */
	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
		createCommerceTierPriceEntry(long commerceTierPriceEntryId) {

		return getService().createCommerceTierPriceEntry(
			commerceTierPriceEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	public static void deleteCommerceTierPriceEntries(long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceTierPriceEntries(commercePriceEntryId);
	}

	/**
	 * Deletes the commerce tier price entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceTierPriceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceTierPriceEntry the commerce tier price entry
	 * @return the commerce tier price entry that was removed
	 * @throws PortalException
	 */
	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			deleteCommerceTierPriceEntry(
				com.liferay.commerce.price.list.model.CommerceTierPriceEntry
					commerceTierPriceEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceTierPriceEntry(
			commerceTierPriceEntry);
	}

	/**
	 * Deletes the commerce tier price entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceTierPriceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceTierPriceEntryId the primary key of the commerce tier price entry
	 * @return the commerce tier price entry that was removed
	 * @throws PortalException if a commerce tier price entry with the primary key could not be found
	 */
	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			deleteCommerceTierPriceEntry(long commerceTierPriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceTierPriceEntry(
			commerceTierPriceEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.price.list.model.impl.CommerceTierPriceEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.price.list.model.impl.CommerceTierPriceEntryModelImpl</code>.
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

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
		fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>
			fetchCommerceTierPriceEntries(long companyId, int start, int end) {

		return getService().fetchCommerceTierPriceEntries(
			companyId, start, end);
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
		fetchCommerceTierPriceEntry(long commerceTierPriceEntryId) {

		return getService().fetchCommerceTierPriceEntry(
			commerceTierPriceEntryId);
	}

	/**
	 * Returns the commerce tier price entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce tier price entry's external reference code
	 * @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
		fetchCommerceTierPriceEntryByReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchCommerceTierPriceEntryByReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce tier price entry with the matching UUID and company.
	 *
	 * @param uuid the commerce tier price entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
		fetchCommerceTierPriceEntryByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().fetchCommerceTierPriceEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
		findClosestCommerceTierPriceEntry(
			long commercePriceEntryId, int quantity) {

		return getService().findClosestCommerceTierPriceEntry(
			commercePriceEntryId, quantity);
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>
			findCommerceTierPriceEntries(
				long commercePriceEntryId, int quantity) {

		return getService().findCommerceTierPriceEntries(
			commercePriceEntryId, quantity);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the commerce tier price entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.price.list.model.impl.CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of commerce tier price entries
	 */
	public static java.util.List
		<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>
			getCommerceTierPriceEntries(int start, int end) {

		return getService().getCommerceTierPriceEntries(start, end);
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>
			getCommerceTierPriceEntries(
				long commercePriceEntryId, int start, int end) {

		return getService().getCommerceTierPriceEntries(
			commercePriceEntryId, start, end);
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>
			getCommerceTierPriceEntries(
				long commercePriceEntryId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.price.list.model.
						CommerceTierPriceEntry> orderByComparator) {

		return getService().getCommerceTierPriceEntries(
			commercePriceEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of commerce tier price entries.
	 *
	 * @return the number of commerce tier price entries
	 */
	public static int getCommerceTierPriceEntriesCount() {
		return getService().getCommerceTierPriceEntriesCount();
	}

	public static int getCommerceTierPriceEntriesCount(
		long commercePriceEntryId) {

		return getService().getCommerceTierPriceEntriesCount(
			commercePriceEntryId);
	}

	public static int getCommerceTierPriceEntriesCountByCompanyId(
		long companyId) {

		return getService().getCommerceTierPriceEntriesCountByCompanyId(
			companyId);
	}

	/**
	 * Returns the commerce tier price entry with the primary key.
	 *
	 * @param commerceTierPriceEntryId the primary key of the commerce tier price entry
	 * @return the commerce tier price entry
	 * @throws PortalException if a commerce tier price entry with the primary key could not be found
	 */
	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			getCommerceTierPriceEntry(long commerceTierPriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceTierPriceEntry(commerceTierPriceEntryId);
	}

	/**
	 * Returns the commerce tier price entry with the matching UUID and company.
	 *
	 * @param uuid the commerce tier price entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching commerce tier price entry
	 * @throws PortalException if a matching commerce tier price entry could not be found
	 */
	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			getCommerceTierPriceEntryByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceTierPriceEntryByUuidAndCompanyId(
			uuid, companyId);
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

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.price.list.model.CommerceTierPriceEntry>
				searchCommerceTierPriceEntries(
					long companyId, long commercePriceEntryId, String keywords,
					int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommerceTierPriceEntries(
			companyId, commercePriceEntryId, keywords, start, end, sort);
	}

	public static int searchCommerceTierPriceEntriesCount(
			long companyId, long commercePriceEntryId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommerceTierPriceEntriesCount(
			companyId, commercePriceEntryId, keywords);
	}

	/**
	 * Updates the commerce tier price entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceTierPriceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceTierPriceEntry the commerce tier price entry
	 * @return the commerce tier price entry that was updated
	 */
	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
		updateCommerceTierPriceEntry(
			com.liferay.commerce.price.list.model.CommerceTierPriceEntry
				commerceTierPriceEntry) {

		return getService().updateCommerceTierPriceEntry(
			commerceTierPriceEntry);
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			updateCommerceTierPriceEntry(
				long commerceTierPriceEntryId, java.math.BigDecimal price,
				java.math.BigDecimal promoPrice, int minQuantity,
				boolean bulkPricing, boolean discountDiscovery,
				java.math.BigDecimal discountLevel1,
				java.math.BigDecimal discountLevel2,
				java.math.BigDecimal discountLevel3,
				java.math.BigDecimal discountLevel4, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceTierPriceEntry(
			commerceTierPriceEntryId, price, promoPrice, minQuantity,
			bulkPricing, discountDiscovery, discountLevel1, discountLevel2,
			discountLevel3, discountLevel4, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			updateCommerceTierPriceEntry(
				long commerceTierPriceEntryId, java.math.BigDecimal price,
				java.math.BigDecimal promoPrice, int minQuantity,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceTierPriceEntry(
			commerceTierPriceEntryId, price, promoPrice, minQuantity,
			serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			updateCommerceTierPriceEntry(
				long commerceTierPriceEntryId, java.math.BigDecimal price,
				int minQuantity, boolean bulkPricing, boolean discountDiscovery,
				java.math.BigDecimal discountLevel1,
				java.math.BigDecimal discountLevel2,
				java.math.BigDecimal discountLevel3,
				java.math.BigDecimal discountLevel4, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceTierPriceEntry(
			commerceTierPriceEntryId, price, minQuantity, bulkPricing,
			discountDiscovery, discountLevel1, discountLevel2, discountLevel3,
			discountLevel4, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			updateExternalReferenceCode(
				com.liferay.commerce.price.list.model.CommerceTierPriceEntry
					commerceTierPriceEntry,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateExternalReferenceCode(
			commerceTierPriceEntry, externalReferenceCode);
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			updateStatus(
				long userId, long commerceTierPriceEntryId, int status,
				com.liferay.portal.kernel.service.ServiceContext serviceContext,
				java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateStatus(
			userId, commerceTierPriceEntryId, status, serviceContext,
			workflowContext);
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			upsertCommerceTierPriceEntry(
				long commerceTierPriceEntryId, long commercePriceEntryId,
				String externalReferenceCode, java.math.BigDecimal price,
				java.math.BigDecimal promoPrice, int minQuantity,
				boolean bulkPricing, boolean discountDiscovery,
				java.math.BigDecimal discountLevel1,
				java.math.BigDecimal discountLevel2,
				java.math.BigDecimal discountLevel3,
				java.math.BigDecimal discountLevel4, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire, String priceEntryExternalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommerceTierPriceEntry(
			commerceTierPriceEntryId, commercePriceEntryId,
			externalReferenceCode, price, promoPrice, minQuantity, bulkPricing,
			discountDiscovery, discountLevel1, discountLevel2, discountLevel3,
			discountLevel4, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, priceEntryExternalReferenceCode,
			serviceContext);
	}

	/**
	 * This method is used to insert a new CommerceTierPriceEntry or update an
	 * existing one
	 *
	 * @param commerceTierPriceEntryId - <b>Only</b> used when updating an
	 entity; the matching one will be updated
	 * @param commercePriceEntryId - <b>Only</b> used when adding a new entity
	 * @param externalReferenceCode - The external identifier code from a 3rd
	 party system to be able to locate the same entity in the portal
	 <b>Only</b> used when updating an entity; the first entity with a
	 matching reference code one will be updated
	 * @param price
	 * @param promoPrice
	 * @param minQuantity
	 * @param priceEntryExternalReferenceCode - <b>Only</b> used when adding a
	 new entity, similar as <code>commercePriceEntryId</code> but the
	 external identifier code from a 3rd party system. If
	 commercePriceEntryId is used, it doesn't have any effect,
	 otherwise it tries to fetch the CommercePriceEntry against the
	 external code reference
	 * @param serviceContext
	 * @return CommerceTierPriceEntry
	 * @throws PortalException
	 */
	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			upsertCommerceTierPriceEntry(
				long commerceTierPriceEntryId, long commercePriceEntryId,
				String externalReferenceCode, java.math.BigDecimal price,
				java.math.BigDecimal promoPrice, int minQuantity,
				String priceEntryExternalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommerceTierPriceEntry(
			commerceTierPriceEntryId, commercePriceEntryId,
			externalReferenceCode, price, promoPrice, minQuantity,
			priceEntryExternalReferenceCode, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommerceTierPriceEntry
			upsertCommerceTierPriceEntry(
				long commerceTierPriceEntryId, long commercePriceEntryId,
				String externalReferenceCode, java.math.BigDecimal price,
				int minQuantity, boolean bulkPricing, boolean discountDiscovery,
				java.math.BigDecimal discountLevel1,
				java.math.BigDecimal discountLevel2,
				java.math.BigDecimal discountLevel3,
				java.math.BigDecimal discountLevel4, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire, String priceEntryExternalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommerceTierPriceEntry(
			commerceTierPriceEntryId, commercePriceEntryId,
			externalReferenceCode, price, minQuantity, bulkPricing,
			discountDiscovery, discountLevel1, discountLevel2, discountLevel3,
			discountLevel4, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, priceEntryExternalReferenceCode,
			serviceContext);
	}

	public static CommerceTierPriceEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceTierPriceEntryLocalService, CommerceTierPriceEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceTierPriceEntryLocalService.class);

		ServiceTracker
			<CommerceTierPriceEntryLocalService,
			 CommerceTierPriceEntryLocalService> serviceTracker =
				new ServiceTracker
					<CommerceTierPriceEntryLocalService,
					 CommerceTierPriceEntryLocalService>(
						 bundle.getBundleContext(),
						 CommerceTierPriceEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}