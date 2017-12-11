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
 * Provides a wrapper for {@link CommerceOrderPaymentLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderPaymentLocalService
 * @generated
 */
@ProviderType
public class CommerceOrderPaymentLocalServiceWrapper
	implements CommerceOrderPaymentLocalService,
		ServiceWrapper<CommerceOrderPaymentLocalService> {
	public CommerceOrderPaymentLocalServiceWrapper(
		CommerceOrderPaymentLocalService commerceOrderPaymentLocalService) {
		_commerceOrderPaymentLocalService = commerceOrderPaymentLocalService;
	}

	/**
	* Adds the commerce order payment to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderPayment the commerce order payment
	* @return the commerce order payment that was added
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrderPayment addCommerceOrderPayment(
		com.liferay.commerce.model.CommerceOrderPayment commerceOrderPayment) {
		return _commerceOrderPaymentLocalService.addCommerceOrderPayment(commerceOrderPayment);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderPayment addCommerceOrderPayment(
		long commerceOrderId, int status, java.lang.String content,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderPaymentLocalService.addCommerceOrderPayment(commerceOrderId,
			status, content, serviceContext);
	}

	/**
	* Creates a new commerce order payment with the primary key. Does not add the commerce order payment to the database.
	*
	* @param commerceOrderPaymentId the primary key for the new commerce order payment
	* @return the new commerce order payment
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrderPayment createCommerceOrderPayment(
		long commerceOrderPaymentId) {
		return _commerceOrderPaymentLocalService.createCommerceOrderPayment(commerceOrderPaymentId);
	}

	/**
	* Deletes the commerce order payment from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderPayment the commerce order payment
	* @return the commerce order payment that was removed
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrderPayment deleteCommerceOrderPayment(
		com.liferay.commerce.model.CommerceOrderPayment commerceOrderPayment) {
		return _commerceOrderPaymentLocalService.deleteCommerceOrderPayment(commerceOrderPayment);
	}

	/**
	* Deletes the commerce order payment with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderPaymentId the primary key of the commerce order payment
	* @return the commerce order payment that was removed
	* @throws PortalException if a commerce order payment with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrderPayment deleteCommerceOrderPayment(
		long commerceOrderPaymentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderPaymentLocalService.deleteCommerceOrderPayment(commerceOrderPaymentId);
	}

	@Override
	public void deleteCommerceOrderPayments(long commerceOrderId) {
		_commerceOrderPaymentLocalService.deleteCommerceOrderPayments(commerceOrderId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderPaymentLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceOrderPaymentLocalService.dynamicQuery();
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
		return _commerceOrderPaymentLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderPaymentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceOrderPaymentLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderPaymentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceOrderPaymentLocalService.dynamicQuery(dynamicQuery,
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
		return _commerceOrderPaymentLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceOrderPaymentLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderPayment fetchCommerceOrderPayment(
		long commerceOrderPaymentId) {
		return _commerceOrderPaymentLocalService.fetchCommerceOrderPayment(commerceOrderPaymentId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceOrderPaymentLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce order payment with the primary key.
	*
	* @param commerceOrderPaymentId the primary key of the commerce order payment
	* @return the commerce order payment
	* @throws PortalException if a commerce order payment with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrderPayment getCommerceOrderPayment(
		long commerceOrderPaymentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderPaymentLocalService.getCommerceOrderPayment(commerceOrderPaymentId);
	}

	/**
	* Returns a range of all the commerce order payments.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderPaymentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce order payments
	* @param end the upper bound of the range of commerce order payments (not inclusive)
	* @return the range of commerce order payments
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderPayment> getCommerceOrderPayments(
		int start, int end) {
		return _commerceOrderPaymentLocalService.getCommerceOrderPayments(start,
			end);
	}

	/**
	* Returns the number of commerce order payments.
	*
	* @return the number of commerce order payments
	*/
	@Override
	public int getCommerceOrderPaymentsCount() {
		return _commerceOrderPaymentLocalService.getCommerceOrderPaymentsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceOrderPaymentLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderPayment getLatestCommerceOrderPayment(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderPaymentLocalService.getLatestCommerceOrderPayment(commerceOrderId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceOrderPaymentLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderPaymentLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce order payment in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderPayment the commerce order payment
	* @return the commerce order payment that was updated
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrderPayment updateCommerceOrderPayment(
		com.liferay.commerce.model.CommerceOrderPayment commerceOrderPayment) {
		return _commerceOrderPaymentLocalService.updateCommerceOrderPayment(commerceOrderPayment);
	}

	@Override
	public CommerceOrderPaymentLocalService getWrappedService() {
		return _commerceOrderPaymentLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceOrderPaymentLocalService commerceOrderPaymentLocalService) {
		_commerceOrderPaymentLocalService = commerceOrderPaymentLocalService;
	}

	private CommerceOrderPaymentLocalService _commerceOrderPaymentLocalService;
}