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

import com.liferay.commerce.model.CommerceShipment;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for CommerceShipment. This utility wraps
 * <code>com.liferay.commerce.service.impl.CommerceShipmentLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentLocalService
 * @generated
 */
public class CommerceShipmentLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceShipmentLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #addDeliverySubscriptionCommerceShipment(long, long)}
	 */
	@Deprecated
	public static CommerceShipment addCommerceDeliverySubscriptionShipment(
			long userId, long commerceOrderId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber)
		throws PortalException {

		return getService().addCommerceDeliverySubscriptionShipment(
			userId, commerceOrderId, name, description, street1, street2,
			street3, city, zip, regionId, countryId, phoneNumber);
	}

	/**
	 * Adds the commerce shipment to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceShipmentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceShipment the commerce shipment
	 * @return the commerce shipment that was added
	 */
	public static CommerceShipment addCommerceShipment(
		CommerceShipment commerceShipment) {

		return getService().addCommerceShipment(commerceShipment);
	}

	public static CommerceShipment addCommerceShipment(
			long commerceOrderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceShipment(
			commerceOrderId, serviceContext);
	}

	public static CommerceShipment addCommerceShipment(
			String externalReferenceCode, long groupId, long commerceAccountId,
			long commerceAddressId, long commerceShippingMethodId,
			String commerceShippingOptionName,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceShipment(
			externalReferenceCode, groupId, commerceAccountId,
			commerceAddressId, commerceShippingMethodId,
			commerceShippingOptionName, serviceContext);
	}

	public static CommerceShipment addDeliverySubscriptionCommerceShipment(
			long userId, long commerceOrderItemId)
		throws PortalException {

		return getService().addDeliverySubscriptionCommerceShipment(
			userId, commerceOrderItemId);
	}

	/**
	 * Creates a new commerce shipment with the primary key. Does not add the commerce shipment to the database.
	 *
	 * @param commerceShipmentId the primary key for the new commerce shipment
	 * @return the new commerce shipment
	 */
	public static CommerceShipment createCommerceShipment(
		long commerceShipmentId) {

		return getService().createCommerceShipment(commerceShipmentId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the commerce shipment from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceShipmentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceShipment the commerce shipment
	 * @return the commerce shipment that was removed
	 */
	public static CommerceShipment deleteCommerceShipment(
		CommerceShipment commerceShipment) {

		return getService().deleteCommerceShipment(commerceShipment);
	}

	public static CommerceShipment deleteCommerceShipment(
			CommerceShipment commerceShipment, boolean restoreStockQuantity)
		throws PortalException {

		return getService().deleteCommerceShipment(
			commerceShipment, restoreStockQuantity);
	}

	/**
	 * Deletes the commerce shipment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceShipmentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceShipmentId the primary key of the commerce shipment
	 * @return the commerce shipment that was removed
	 * @throws PortalException if a commerce shipment with the primary key could not be found
	 */
	public static CommerceShipment deleteCommerceShipment(
			long commerceShipmentId)
		throws PortalException {

		return getService().deleteCommerceShipment(commerceShipmentId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static CommerceShipment fetchCommerceShipment(
		long commerceShipmentId) {

		return getService().fetchCommerceShipment(commerceShipmentId);
	}

	/**
	 * Returns the commerce shipment with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce shipment's external reference code
	 * @return the matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	public static CommerceShipment fetchCommerceShipmentByExternalReferenceCode(
		long companyId, String externalReferenceCode) {

		return getService().fetchCommerceShipmentByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchCommerceShipmentByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	public static CommerceShipment fetchCommerceShipmentByReferenceCode(
		long companyId, String externalReferenceCode) {

		return getService().fetchCommerceShipmentByReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce shipment with the primary key.
	 *
	 * @param commerceShipmentId the primary key of the commerce shipment
	 * @return the commerce shipment
	 * @throws PortalException if a commerce shipment with the primary key could not be found
	 */
	public static CommerceShipment getCommerceShipment(long commerceShipmentId)
		throws PortalException {

		return getService().getCommerceShipment(commerceShipmentId);
	}

	/**
	 * Returns the commerce shipment with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce shipment's external reference code
	 * @return the matching commerce shipment
	 * @throws PortalException if a matching commerce shipment could not be found
	 */
	public static CommerceShipment getCommerceShipmentByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		return getService().getCommerceShipmentByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns a range of all the commerce shipments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceShipmentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @return the range of commerce shipments
	 */
	public static List<CommerceShipment> getCommerceShipments(
		int start, int end) {

		return getService().getCommerceShipments(start, end);
	}

	public static List<CommerceShipment> getCommerceShipments(
		long commerceOrderId, int start, int end) {

		return getService().getCommerceShipments(commerceOrderId, start, end);
	}

	public static List<CommerceShipment> getCommerceShipments(
			long companyId, long[] groupIds, long[] commerceAccountIds,
			String keywords, int[] shipmentStatuses,
			boolean excludeShipmentStatus, int start, int end)
		throws PortalException {

		return getService().getCommerceShipments(
			companyId, groupIds, commerceAccountIds, keywords, shipmentStatuses,
			excludeShipmentStatus, start, end);
	}

	public static List<CommerceShipment> getCommerceShipments(
		long[] groupIds, int status, int start, int end,
		OrderByComparator<CommerceShipment> orderByComparator) {

		return getService().getCommerceShipments(
			groupIds, status, start, end, orderByComparator);
	}

	public static List<CommerceShipment> getCommerceShipments(
		long[] groupIds, int start, int end,
		OrderByComparator<CommerceShipment> orderByComparator) {

		return getService().getCommerceShipments(
			groupIds, start, end, orderByComparator);
	}

	public static List<CommerceShipment> getCommerceShipments(
		long[] groupIds, long commerceAddressId, int start, int end,
		OrderByComparator<CommerceShipment> orderByComparator) {

		return getService().getCommerceShipments(
			groupIds, commerceAddressId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of commerce shipments.
	 *
	 * @return the number of commerce shipments
	 */
	public static int getCommerceShipmentsCount() {
		return getService().getCommerceShipmentsCount();
	}

	public static int getCommerceShipmentsCount(long commerceOrderId) {
		return getService().getCommerceShipmentsCount(commerceOrderId);
	}

	public static int getCommerceShipmentsCount(
			long companyId, long[] groupIds, long[] commerceAccountIds,
			String keywords, int[] shipmentStatuses,
			boolean excludeShipmentStatus)
		throws PortalException {

		return getService().getCommerceShipmentsCount(
			companyId, groupIds, commerceAccountIds, keywords, shipmentStatuses,
			excludeShipmentStatus);
	}

	public static int getCommerceShipmentsCount(long[] groupIds) {
		return getService().getCommerceShipmentsCount(groupIds);
	}

	public static int getCommerceShipmentsCount(long[] groupIds, int status) {
		return getService().getCommerceShipmentsCount(groupIds, status);
	}

	public static int getCommerceShipmentsCount(
		long[] groupIds, long commerceAddressId) {

		return getService().getCommerceShipmentsCount(
			groupIds, commerceAddressId);
	}

	public static int[] getCommerceShipmentStatusesByCommerceOrderId(
		long commerceOrderId) {

		return getService().getCommerceShipmentStatusesByCommerceOrderId(
			commerceOrderId);
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
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static CommerceShipment reprocessCommerceShipment(
			long commerceShipmentId)
		throws PortalException {

		return getService().reprocessCommerceShipment(commerceShipmentId);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<CommerceShipment> searchCommerceShipments(
				com.liferay.portal.kernel.search.SearchContext searchContext)
			throws PortalException {

		return getService().searchCommerceShipments(searchContext);
	}

	public static long searchCommerceShipmentsCount(
			com.liferay.portal.kernel.search.SearchContext searchContext)
		throws PortalException {

		return getService().searchCommerceShipmentsCount(searchContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #updateAddress(long, String, String, String, String, String, String,
	 String, long, long, String, ServiceContext)}
	 */
	@Deprecated
	public static CommerceShipment updateAddress(
			long commerceShipmentId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber)
		throws PortalException {

		return getService().updateAddress(
			commerceShipmentId, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber);
	}

	public static CommerceShipment updateAddress(
			long commerceShipmentId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateAddress(
			commerceShipmentId, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, serviceContext);
	}

	public static CommerceShipment updateCarrierDetails(
			long commerceShipmentId, String carrier, String trackingNumber)
		throws PortalException {

		return getService().updateCarrierDetails(
			commerceShipmentId, carrier, trackingNumber);
	}

	/**
	 * Updates the commerce shipment in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceShipmentLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceShipment the commerce shipment
	 * @return the commerce shipment that was updated
	 */
	public static CommerceShipment updateCommerceShipment(
		CommerceShipment commerceShipment) {

		return getService().updateCommerceShipment(commerceShipment);
	}

	public static CommerceShipment updateCommerceShipment(
			long commerceShipmentId, String carrier, String trackingNumber,
			int status, int shippingDateMonth, int shippingDateDay,
			int shippingDateYear, int shippingDateHour, int shippingDateMinute,
			int expectedDateMonth, int expectedDateDay, int expectedDateYear,
			int expectedDateHour, int expectedDateMinute)
		throws PortalException {

		return getService().updateCommerceShipment(
			commerceShipmentId, carrier, trackingNumber, status,
			shippingDateMonth, shippingDateDay, shippingDateYear,
			shippingDateHour, shippingDateMinute, expectedDateMonth,
			expectedDateDay, expectedDateYear, expectedDateHour,
			expectedDateMinute);
	}

	public static CommerceShipment updateCommerceShipment(
			long commerceShipmentId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			String carrier, String trackingNumber, int status,
			int shippingDateMonth, int shippingDateDay, int shippingDateYear,
			int shippingDateHour, int shippingDateMinute, int expectedDateMonth,
			int expectedDateDay, int expectedDateYear, int expectedDateHour,
			int expectedDateMinute)
		throws PortalException {

		return getService().updateCommerceShipment(
			commerceShipmentId, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, carrier,
			trackingNumber, status, shippingDateMonth, shippingDateDay,
			shippingDateYear, shippingDateHour, shippingDateMinute,
			expectedDateMonth, expectedDateDay, expectedDateYear,
			expectedDateHour, expectedDateMinute);
	}

	public static CommerceShipment updateCommerceShipmentExternalReferenceCode(
			String externalReferenceCode, long commerceShipmentId)
		throws PortalException {

		return getService().updateCommerceShipmentExternalReferenceCode(
			externalReferenceCode, commerceShipmentId);
	}

	public static CommerceShipment updateExpectedDate(
			long commerceShipmentId, int expectedDateMonth, int expectedDateDay,
			int expectedDateYear, int expectedDateHour, int expectedDateMinute)
		throws PortalException {

		return getService().updateExpectedDate(
			commerceShipmentId, expectedDateMonth, expectedDateDay,
			expectedDateYear, expectedDateHour, expectedDateMinute);
	}

	public static CommerceShipment updateShippingDate(
			long commerceShipmentId, int shippingDateMonth, int shippingDateDay,
			int shippingDateYear, int shippingDateHour, int shippingDateMinute)
		throws PortalException {

		return getService().updateShippingDate(
			commerceShipmentId, shippingDateMonth, shippingDateDay,
			shippingDateYear, shippingDateHour, shippingDateMinute);
	}

	public static CommerceShipment updateStatus(
			long commerceShipmentId, int status)
		throws PortalException {

		return getService().updateStatus(commerceShipmentId, status);
	}

	public static CommerceShipmentLocalService getService() {
		return _service;
	}

	private static volatile CommerceShipmentLocalService _service;

}