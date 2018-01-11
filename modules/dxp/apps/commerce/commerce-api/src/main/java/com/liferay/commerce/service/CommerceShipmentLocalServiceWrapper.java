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
 * Provides a wrapper for {@link CommerceShipmentLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentLocalService
 * @generated
 */
@ProviderType
public class CommerceShipmentLocalServiceWrapper
	implements CommerceShipmentLocalService,
		ServiceWrapper<CommerceShipmentLocalService> {
	public CommerceShipmentLocalServiceWrapper(
		CommerceShipmentLocalService commerceShipmentLocalService) {
		_commerceShipmentLocalService = commerceShipmentLocalService;
	}

	/**
	* Adds the commerce shipment to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShipment the commerce shipment
	* @return the commerce shipment that was added
	*/
	@Override
	public com.liferay.commerce.model.CommerceShipment addCommerceShipment(
		com.liferay.commerce.model.CommerceShipment commerceShipment) {
		return _commerceShipmentLocalService.addCommerceShipment(commerceShipment);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment addCommerceShipment(
		long shipmentUserId, long commerceAddressId,
		long commerceShippingMethodId, long commerceWarehouseId,
		java.lang.String carrier, java.lang.String trackingNumber,
		int expectedDuration, int status, int shippingDateMonth,
		int shippingDateDay, int shippingDateYear, int shippingDateHour,
		int shippingDateMinute, int expectedDateMonth, int expectedDateDay,
		int expectedDateYear, int expectedDateHour, int expectedDateMinute,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentLocalService.addCommerceShipment(shipmentUserId,
			commerceAddressId, commerceShippingMethodId, commerceWarehouseId,
			carrier, trackingNumber, expectedDuration, status,
			shippingDateMonth, shippingDateDay, shippingDateYear,
			shippingDateHour, shippingDateMinute, expectedDateMonth,
			expectedDateDay, expectedDateYear, expectedDateHour,
			expectedDateMinute, serviceContext);
	}

	/**
	* Creates a new commerce shipment with the primary key. Does not add the commerce shipment to the database.
	*
	* @param commerceShipmentId the primary key for the new commerce shipment
	* @return the new commerce shipment
	*/
	@Override
	public com.liferay.commerce.model.CommerceShipment createCommerceShipment(
		long commerceShipmentId) {
		return _commerceShipmentLocalService.createCommerceShipment(commerceShipmentId);
	}

	/**
	* Deletes the commerce shipment from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShipment the commerce shipment
	* @return the commerce shipment that was removed
	*/
	@Override
	public com.liferay.commerce.model.CommerceShipment deleteCommerceShipment(
		com.liferay.commerce.model.CommerceShipment commerceShipment) {
		return _commerceShipmentLocalService.deleteCommerceShipment(commerceShipment);
	}

	/**
	* Deletes the commerce shipment with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShipmentId the primary key of the commerce shipment
	* @return the commerce shipment that was removed
	* @throws PortalException if a commerce shipment with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceShipment deleteCommerceShipment(
		long commerceShipmentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentLocalService.deleteCommerceShipment(commerceShipmentId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceShipmentLocalService.dynamicQuery();
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
		return _commerceShipmentLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceShipmentLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceShipmentLocalService.dynamicQuery(dynamicQuery, start,
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
		return _commerceShipmentLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceShipmentLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment fetchCommerceShipment(
		long commerceShipmentId) {
		return _commerceShipmentLocalService.fetchCommerceShipment(commerceShipmentId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceShipmentLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce shipment with the primary key.
	*
	* @param commerceShipmentId the primary key of the commerce shipment
	* @return the commerce shipment
	* @throws PortalException if a commerce shipment with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceShipment getCommerceShipment(
		long commerceShipmentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentLocalService.getCommerceShipment(commerceShipmentId);
	}

	/**
	* Returns a range of all the commerce shipments.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipments
	* @param end the upper bound of the range of commerce shipments (not inclusive)
	* @return the range of commerce shipments
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipment> getCommerceShipments(
		int start, int end) {
		return _commerceShipmentLocalService.getCommerceShipments(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipment> getCommerceShipments(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceShipment> orderByComparator) {
		return _commerceShipmentLocalService.getCommerceShipments(groupId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce shipments.
	*
	* @return the number of commerce shipments
	*/
	@Override
	public int getCommerceShipmentsCount() {
		return _commerceShipmentLocalService.getCommerceShipmentsCount();
	}

	@Override
	public int getCommerceShipmentsCount(long groupId) {
		return _commerceShipmentLocalService.getCommerceShipmentsCount(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceShipmentLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceShipmentLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce shipment in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceShipment the commerce shipment
	* @return the commerce shipment that was updated
	*/
	@Override
	public com.liferay.commerce.model.CommerceShipment updateCommerceShipment(
		com.liferay.commerce.model.CommerceShipment commerceShipment) {
		return _commerceShipmentLocalService.updateCommerceShipment(commerceShipment);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipment updateCommerceShipment(
		long commerceShipmentId, long shipmentUserId, long commerceAddressId,
		long commerceShippingMethodId, java.lang.String carrier,
		java.lang.String trackingNumber, int expectedDuration, int status,
		int shippingDateMonth, int shippingDateDay, int shippingDateYear,
		int shippingDateHour, int shippingDateMinute, int expectedDateMonth,
		int expectedDateDay, int expectedDateYear, int expectedDateHour,
		int expectedDateMinute)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShipmentLocalService.updateCommerceShipment(commerceShipmentId,
			shipmentUserId, commerceAddressId, commerceShippingMethodId,
			carrier, trackingNumber, expectedDuration, status,
			shippingDateMonth, shippingDateDay, shippingDateYear,
			shippingDateHour, shippingDateMinute, expectedDateMonth,
			expectedDateDay, expectedDateYear, expectedDateHour,
			expectedDateMinute);
	}

	@Override
	public CommerceShipmentLocalService getWrappedService() {
		return _commerceShipmentLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceShipmentLocalService commerceShipmentLocalService) {
		_commerceShipmentLocalService = commerceShipmentLocalService;
	}

	private CommerceShipmentLocalService _commerceShipmentLocalService;
}