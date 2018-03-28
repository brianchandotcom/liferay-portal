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

package com.liferay.commerce.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceOrderPayment;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce order payment service. This utility wraps {@link com.liferay.commerce.service.persistence.impl.CommerceOrderPaymentPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderPaymentPersistence
 * @see com.liferay.commerce.service.persistence.impl.CommerceOrderPaymentPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceOrderPaymentUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(CommerceOrderPayment commerceOrderPayment) {
		getPersistence().clearCache(commerceOrderPayment);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceOrderPayment> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceOrderPayment> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceOrderPayment> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceOrderPayment> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceOrderPayment update(
		CommerceOrderPayment commerceOrderPayment) {
		return getPersistence().update(commerceOrderPayment);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceOrderPayment update(
		CommerceOrderPayment commerceOrderPayment, ServiceContext serviceContext) {
		return getPersistence().update(commerceOrderPayment, serviceContext);
	}

	/**
	* Returns all the commerce order payments where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @return the matching commerce order payments
	*/
	public static List<CommerceOrderPayment> findByCommerceOrderId(
		long commerceOrderId) {
		return getPersistence().findByCommerceOrderId(commerceOrderId);
	}

	/**
	* Returns a range of all the commerce order payments where commerceOrderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderPaymentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param start the lower bound of the range of commerce order payments
	* @param end the upper bound of the range of commerce order payments (not inclusive)
	* @return the range of matching commerce order payments
	*/
	public static List<CommerceOrderPayment> findByCommerceOrderId(
		long commerceOrderId, int start, int end) {
		return getPersistence()
				   .findByCommerceOrderId(commerceOrderId, start, end);
	}

	/**
	* Returns an ordered range of all the commerce order payments where commerceOrderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderPaymentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param start the lower bound of the range of commerce order payments
	* @param end the upper bound of the range of commerce order payments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce order payments
	*/
	public static List<CommerceOrderPayment> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderPayment> orderByComparator) {
		return getPersistence()
				   .findByCommerceOrderId(commerceOrderId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce order payments where commerceOrderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderPaymentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceOrderId the commerce order ID
	* @param start the lower bound of the range of commerce order payments
	* @param end the upper bound of the range of commerce order payments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce order payments
	*/
	public static List<CommerceOrderPayment> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		OrderByComparator<CommerceOrderPayment> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceOrderId(commerceOrderId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce order payment in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order payment
	* @throws NoSuchOrderPaymentException if a matching commerce order payment could not be found
	*/
	public static CommerceOrderPayment findByCommerceOrderId_First(
		long commerceOrderId,
		OrderByComparator<CommerceOrderPayment> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderPaymentException {
		return getPersistence()
				   .findByCommerceOrderId_First(commerceOrderId,
			orderByComparator);
	}

	/**
	* Returns the first commerce order payment in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order payment, or <code>null</code> if a matching commerce order payment could not be found
	*/
	public static CommerceOrderPayment fetchByCommerceOrderId_First(
		long commerceOrderId,
		OrderByComparator<CommerceOrderPayment> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceOrderId_First(commerceOrderId,
			orderByComparator);
	}

	/**
	* Returns the last commerce order payment in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order payment
	* @throws NoSuchOrderPaymentException if a matching commerce order payment could not be found
	*/
	public static CommerceOrderPayment findByCommerceOrderId_Last(
		long commerceOrderId,
		OrderByComparator<CommerceOrderPayment> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderPaymentException {
		return getPersistence()
				   .findByCommerceOrderId_Last(commerceOrderId,
			orderByComparator);
	}

	/**
	* Returns the last commerce order payment in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order payment, or <code>null</code> if a matching commerce order payment could not be found
	*/
	public static CommerceOrderPayment fetchByCommerceOrderId_Last(
		long commerceOrderId,
		OrderByComparator<CommerceOrderPayment> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceOrderId_Last(commerceOrderId,
			orderByComparator);
	}

	/**
	* Returns the commerce order payments before and after the current commerce order payment in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderPaymentId the primary key of the current commerce order payment
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce order payment
	* @throws NoSuchOrderPaymentException if a commerce order payment with the primary key could not be found
	*/
	public static CommerceOrderPayment[] findByCommerceOrderId_PrevAndNext(
		long commerceOrderPaymentId, long commerceOrderId,
		OrderByComparator<CommerceOrderPayment> orderByComparator)
		throws com.liferay.commerce.exception.NoSuchOrderPaymentException {
		return getPersistence()
				   .findByCommerceOrderId_PrevAndNext(commerceOrderPaymentId,
			commerceOrderId, orderByComparator);
	}

	/**
	* Removes all the commerce order payments where commerceOrderId = &#63; from the database.
	*
	* @param commerceOrderId the commerce order ID
	*/
	public static void removeByCommerceOrderId(long commerceOrderId) {
		getPersistence().removeByCommerceOrderId(commerceOrderId);
	}

	/**
	* Returns the number of commerce order payments where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @return the number of matching commerce order payments
	*/
	public static int countByCommerceOrderId(long commerceOrderId) {
		return getPersistence().countByCommerceOrderId(commerceOrderId);
	}

	/**
	* Caches the commerce order payment in the entity cache if it is enabled.
	*
	* @param commerceOrderPayment the commerce order payment
	*/
	public static void cacheResult(CommerceOrderPayment commerceOrderPayment) {
		getPersistence().cacheResult(commerceOrderPayment);
	}

	/**
	* Caches the commerce order payments in the entity cache if it is enabled.
	*
	* @param commerceOrderPayments the commerce order payments
	*/
	public static void cacheResult(
		List<CommerceOrderPayment> commerceOrderPayments) {
		getPersistence().cacheResult(commerceOrderPayments);
	}

	/**
	* Creates a new commerce order payment with the primary key. Does not add the commerce order payment to the database.
	*
	* @param commerceOrderPaymentId the primary key for the new commerce order payment
	* @return the new commerce order payment
	*/
	public static CommerceOrderPayment create(long commerceOrderPaymentId) {
		return getPersistence().create(commerceOrderPaymentId);
	}

	/**
	* Removes the commerce order payment with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderPaymentId the primary key of the commerce order payment
	* @return the commerce order payment that was removed
	* @throws NoSuchOrderPaymentException if a commerce order payment with the primary key could not be found
	*/
	public static CommerceOrderPayment remove(long commerceOrderPaymentId)
		throws com.liferay.commerce.exception.NoSuchOrderPaymentException {
		return getPersistence().remove(commerceOrderPaymentId);
	}

	public static CommerceOrderPayment updateImpl(
		CommerceOrderPayment commerceOrderPayment) {
		return getPersistence().updateImpl(commerceOrderPayment);
	}

	/**
	* Returns the commerce order payment with the primary key or throws a {@link NoSuchOrderPaymentException} if it could not be found.
	*
	* @param commerceOrderPaymentId the primary key of the commerce order payment
	* @return the commerce order payment
	* @throws NoSuchOrderPaymentException if a commerce order payment with the primary key could not be found
	*/
	public static CommerceOrderPayment findByPrimaryKey(
		long commerceOrderPaymentId)
		throws com.liferay.commerce.exception.NoSuchOrderPaymentException {
		return getPersistence().findByPrimaryKey(commerceOrderPaymentId);
	}

	/**
	* Returns the commerce order payment with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceOrderPaymentId the primary key of the commerce order payment
	* @return the commerce order payment, or <code>null</code> if a commerce order payment with the primary key could not be found
	*/
	public static CommerceOrderPayment fetchByPrimaryKey(
		long commerceOrderPaymentId) {
		return getPersistence().fetchByPrimaryKey(commerceOrderPaymentId);
	}

	public static java.util.Map<java.io.Serializable, CommerceOrderPayment> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce order payments.
	*
	* @return the commerce order payments
	*/
	public static List<CommerceOrderPayment> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce order payments.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderPaymentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce order payments
	* @param end the upper bound of the range of commerce order payments (not inclusive)
	* @return the range of commerce order payments
	*/
	public static List<CommerceOrderPayment> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce order payments.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderPaymentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce order payments
	* @param end the upper bound of the range of commerce order payments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce order payments
	*/
	public static List<CommerceOrderPayment> findAll(int start, int end,
		OrderByComparator<CommerceOrderPayment> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce order payments.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceOrderPaymentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce order payments
	* @param end the upper bound of the range of commerce order payments (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce order payments
	*/
	public static List<CommerceOrderPayment> findAll(int start, int end,
		OrderByComparator<CommerceOrderPayment> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce order payments from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce order payments.
	*
	* @return the number of commerce order payments
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceOrderPaymentPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceOrderPaymentPersistence, CommerceOrderPaymentPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceOrderPaymentPersistence.class);

		ServiceTracker<CommerceOrderPaymentPersistence, CommerceOrderPaymentPersistence> serviceTracker =
			new ServiceTracker<CommerceOrderPaymentPersistence, CommerceOrderPaymentPersistence>(bundle.getBundleContext(),
				CommerceOrderPaymentPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}