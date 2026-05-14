/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.persistence;

import com.liferay.commerce.exception.NoSuchOrderPaymentException;
import com.liferay.commerce.model.CommerceOrderPayment;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce order payment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderPaymentUtil
 * @generated
 */
@ProviderType
public interface CommerceOrderPaymentPersistence
	extends BasePersistence<CommerceOrderPayment> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceOrderPaymentUtil} to access the commerce order payment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns an ordered range of all the commerce order payments where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderPaymentModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order payments
	 * @param end the upper bound of the range of commerce order payments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order payments
	 */
	public java.util.List<CommerceOrderPayment> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderPayment>
			orderByComparator,
		boolean useFinderCache);

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
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderPayment> orderByComparator)
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
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderPayment>
			orderByComparator);

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
	 * Returns the commerce order payment with the primary key or throws a <code>NoSuchOrderPaymentException</code> if it could not be found.
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

	/**
	 * Returns all the commerce order payments where commerceOrderId = &#63;.
	 *
	 * @param commerceOrderId the commerce order ID
	 * @return the matching commerce order payments
	 */
	public default java.util.List<CommerceOrderPayment> findByCommerceOrderId(
		long commerceOrderId) {

		return findByCommerceOrderId(
			commerceOrderId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the commerce order payments where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderPaymentModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order payments
	 * @param end the upper bound of the range of commerce order payments (not inclusive)
	 * @return the range of matching commerce order payments
	 */
	public default java.util.List<CommerceOrderPayment> findByCommerceOrderId(
		long commerceOrderId, int start, int end) {

		return findByCommerceOrderId(commerceOrderId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the commerce order payments where commerceOrderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderPaymentModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderId the commerce order ID
	 * @param start the lower bound of the range of commerce order payments
	 * @param end the upper bound of the range of commerce order payments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order payments
	 */
	public default java.util.List<CommerceOrderPayment> findByCommerceOrderId(
		long commerceOrderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderPayment>
			orderByComparator) {

		return findByCommerceOrderId(
			commerceOrderId, start, end, orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1675631387