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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceOrderPayment. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceOrderPaymentLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderPaymentLocalService
 * @see com.liferay.commerce.service.base.CommerceOrderPaymentLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceOrderPaymentLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceOrderPaymentLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceOrderPaymentLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce order payment to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderPayment the commerce order payment
	* @return the commerce order payment that was added
	*/
	public static com.liferay.commerce.model.CommerceOrderPayment addCommerceOrderPayment(
		com.liferay.commerce.model.CommerceOrderPayment commerceOrderPayment) {
		return getService().addCommerceOrderPayment(commerceOrderPayment);
	}

	public static com.liferay.commerce.model.CommerceOrderPayment addCommerceOrderPayment(
		long commerceOrderId, int status, String content,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceOrderPayment(commerceOrderId, status, content,
			serviceContext);
	}

	/**
	* Creates a new commerce order payment with the primary key. Does not add the commerce order payment to the database.
	*
	* @param commerceOrderPaymentId the primary key for the new commerce order payment
	* @return the new commerce order payment
	*/
	public static com.liferay.commerce.model.CommerceOrderPayment createCommerceOrderPayment(
		long commerceOrderPaymentId) {
		return getService().createCommerceOrderPayment(commerceOrderPaymentId);
	}

	/**
	* Deletes the commerce order payment from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderPayment the commerce order payment
	* @return the commerce order payment that was removed
	*/
	public static com.liferay.commerce.model.CommerceOrderPayment deleteCommerceOrderPayment(
		com.liferay.commerce.model.CommerceOrderPayment commerceOrderPayment) {
		return getService().deleteCommerceOrderPayment(commerceOrderPayment);
	}

	/**
	* Deletes the commerce order payment with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderPaymentId the primary key of the commerce order payment
	* @return the commerce order payment that was removed
	* @throws PortalException if a commerce order payment with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceOrderPayment deleteCommerceOrderPayment(
		long commerceOrderPaymentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceOrderPayment(commerceOrderPaymentId);
	}

	public static void deleteCommerceOrderPayments(long commerceOrderId) {
		getService().deleteCommerceOrderPayments(commerceOrderId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderPaymentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderPaymentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.model.CommerceOrderPayment fetchCommerceOrderPayment(
		long commerceOrderPaymentId) {
		return getService().fetchCommerceOrderPayment(commerceOrderPaymentId);
	}

	public static com.liferay.commerce.model.CommerceOrderPayment fetchLatestCommerceOrderPayment(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchLatestCommerceOrderPayment(commerceOrderId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce order payment with the primary key.
	*
	* @param commerceOrderPaymentId the primary key of the commerce order payment
	* @return the commerce order payment
	* @throws PortalException if a commerce order payment with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceOrderPayment getCommerceOrderPayment(
		long commerceOrderPaymentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceOrderPayment(commerceOrderPaymentId);
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
	public static java.util.List<com.liferay.commerce.model.CommerceOrderPayment> getCommerceOrderPayments(
		int start, int end) {
		return getService().getCommerceOrderPayments(start, end);
	}

	/**
	* Returns the number of commerce order payments.
	*
	* @return the number of commerce order payments
	*/
	public static int getCommerceOrderPaymentsCount() {
		return getService().getCommerceOrderPaymentsCount();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
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

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce order payment in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderPayment the commerce order payment
	* @return the commerce order payment that was updated
	*/
	public static com.liferay.commerce.model.CommerceOrderPayment updateCommerceOrderPayment(
		com.liferay.commerce.model.CommerceOrderPayment commerceOrderPayment) {
		return getService().updateCommerceOrderPayment(commerceOrderPayment);
	}

	public static CommerceOrderPaymentLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceOrderPaymentLocalService, CommerceOrderPaymentLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceOrderPaymentLocalService.class);

		ServiceTracker<CommerceOrderPaymentLocalService, CommerceOrderPaymentLocalService> serviceTracker =
			new ServiceTracker<CommerceOrderPaymentLocalService, CommerceOrderPaymentLocalService>(bundle.getBundleContext(),
				CommerceOrderPaymentLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}