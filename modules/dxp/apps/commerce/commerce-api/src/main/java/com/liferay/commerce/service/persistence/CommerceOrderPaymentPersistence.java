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

import com.liferay.commerce.exception.NoSuchOrderPaymentException;
import com.liferay.commerce.model.CommerceOrderPayment;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce order payment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommerceOrderPaymentPersistenceImpl
 * @see CommerceOrderPaymentUtil
 * @generated
 */
@ProviderType
public interface CommerceOrderPaymentPersistence extends BasePersistence<CommerceOrderPayment> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceOrderPaymentUtil} to access the commerce order payment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce order payments where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @return the matching commerce order payments
	*/
	public java.util.List<CommerceOrderPayment> findByCommerceOrderId(
		long commerceOrderId);

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
	public java.util.List<CommerceOrderPayment> findByCommerceOrderId(
		long commerceOrderId, int start, int end);

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
	public java.util.List<CommerceOrderPayment> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderPayment> orderByComparator);

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
	public java.util.List<CommerceOrderPayment> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderPayment> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce order payment in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order payment
	* @throws NoSuchOrderPaymentException if a matching commerce order payment could not be found
	*/
	public CommerceOrderPayment findByCommerceOrderId_First(
		long commerceOrderId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderPayment> orderByComparator)
		throws NoSuchOrderPaymentException;

	/**
	* Returns the first commerce order payment in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce order payment, or <code>null</code> if a matching commerce order payment could not be found
	*/
	public CommerceOrderPayment fetchByCommerceOrderId_First(
		long commerceOrderId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderPayment> orderByComparator);

	/**
	* Returns the last commerce order payment in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order payment
	* @throws NoSuchOrderPaymentException if a matching commerce order payment could not be found
	*/
	public CommerceOrderPayment findByCommerceOrderId_Last(
		long commerceOrderId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderPayment> orderByComparator)
		throws NoSuchOrderPaymentException;

	/**
	* Returns the last commerce order payment in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce order payment, or <code>null</code> if a matching commerce order payment could not be found
	*/
	public CommerceOrderPayment fetchByCommerceOrderId_Last(
		long commerceOrderId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderPayment> orderByComparator);

	/**
	* Returns the commerce order payments before and after the current commerce order payment in the ordered set where commerceOrderId = &#63;.
	*
	* @param commerceOrderPaymentId the primary key of the current commerce order payment
	* @param commerceOrderId the commerce order ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce order payment
	* @throws NoSuchOrderPaymentException if a commerce order payment with the primary key could not be found
	*/
	public CommerceOrderPayment[] findByCommerceOrderId_PrevAndNext(
		long commerceOrderPaymentId, long commerceOrderId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderPayment> orderByComparator)
		throws NoSuchOrderPaymentException;

	/**
	* Removes all the commerce order payments where commerceOrderId = &#63; from the database.
	*
	* @param commerceOrderId the commerce order ID
	*/
	public void removeByCommerceOrderId(long commerceOrderId);

	/**
	* Returns the number of commerce order payments where commerceOrderId = &#63;.
	*
	* @param commerceOrderId the commerce order ID
	* @return the number of matching commerce order payments
	*/
	public int countByCommerceOrderId(long commerceOrderId);

	/**
	* Caches the commerce order payment in the entity cache if it is enabled.
	*
	* @param commerceOrderPayment the commerce order payment
	*/
	public void cacheResult(CommerceOrderPayment commerceOrderPayment);

	/**
	* Caches the commerce order payments in the entity cache if it is enabled.
	*
	* @param commerceOrderPayments the commerce order payments
	*/
	public void cacheResult(
		java.util.List<CommerceOrderPayment> commerceOrderPayments);

	/**
	* Creates a new commerce order payment with the primary key. Does not add the commerce order payment to the database.
	*
	* @param commerceOrderPaymentId the primary key for the new commerce order payment
	* @return the new commerce order payment
	*/
	public CommerceOrderPayment create(long commerceOrderPaymentId);

	/**
	* Removes the commerce order payment with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderPaymentId the primary key of the commerce order payment
	* @return the commerce order payment that was removed
	* @throws NoSuchOrderPaymentException if a commerce order payment with the primary key could not be found
	*/
	public CommerceOrderPayment remove(long commerceOrderPaymentId)
		throws NoSuchOrderPaymentException;

	public CommerceOrderPayment updateImpl(
		CommerceOrderPayment commerceOrderPayment);

	/**
	* Returns the commerce order payment with the primary key or throws a {@link NoSuchOrderPaymentException} if it could not be found.
	*
	* @param commerceOrderPaymentId the primary key of the commerce order payment
	* @return the commerce order payment
	* @throws NoSuchOrderPaymentException if a commerce order payment with the primary key could not be found
	*/
	public CommerceOrderPayment findByPrimaryKey(long commerceOrderPaymentId)
		throws NoSuchOrderPaymentException;

	/**
	* Returns the commerce order payment with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceOrderPaymentId the primary key of the commerce order payment
	* @return the commerce order payment, or <code>null</code> if a commerce order payment with the primary key could not be found
	*/
	public CommerceOrderPayment fetchByPrimaryKey(long commerceOrderPaymentId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceOrderPayment> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce order payments.
	*
	* @return the commerce order payments
	*/
	public java.util.List<CommerceOrderPayment> findAll();

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
	public java.util.List<CommerceOrderPayment> findAll(int start, int end);

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
	public java.util.List<CommerceOrderPayment> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderPayment> orderByComparator);

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
	public java.util.List<CommerceOrderPayment> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderPayment> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce order payments from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce order payments.
	*
	* @return the number of commerce order payments
	*/
	public int countAll();
}