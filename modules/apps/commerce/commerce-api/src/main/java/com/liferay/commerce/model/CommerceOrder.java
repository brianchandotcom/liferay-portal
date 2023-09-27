/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceOrder service. Represents a row in the &quot;CommerceOrder&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderModel
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.model.impl.CommerceOrderImpl")
@ProviderType
public interface CommerceOrder extends CommerceOrderModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.model.impl.CommerceOrderImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceOrder, Long>
		COMMERCE_ORDER_ID_ACCESSOR = new Accessor<CommerceOrder, Long>() {

			@Override
			public Long get(CommerceOrder commerceOrder) {
				return commerceOrder.getCommerceOrderId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceOrder> getTypeClass() {
				return CommerceOrder.class;
			}

		};

	public com.liferay.account.model.AccountEntry getAccountEntry()
		throws com.liferay.portal.kernel.exception.PortalException;

	public CommerceAddress getBillingAddress()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getCommerceAccountName()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.currency.model.CommerceCurrency
			getCommerceCurrency()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<CommerceOrderItem> getCommerceOrderItems();

	public java.util.List<CommerceOrderItem> getCommerceOrderItems(
		long cpInstanceId);

	public int getCommerceOrderItemsCount(long cpInstanceId);

	public CommerceShippingMethod getCommerceShippingMethod()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<Long> getCustomerCommerceOrderIds();

	public int getCustomerCommerceOrderIdsCount();

	public long getScopeGroupId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public CommerceAddress getShippingAddress()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.currency.model.CommerceMoney getShippingMoney()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.currency.model.CommerceMoney
			getShippingWithTaxAmountMoney()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.currency.model.CommerceMoney getSubtotalMoney()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.currency.model.CommerceMoney
			getSubtotalWithTaxAmountMoney()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<Long> getSupplierCommerceOrderIds();

	public int getSupplierCommerceOrderIdsCount();

	public com.liferay.commerce.currency.model.CommerceMoney getTotalMoney()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.currency.model.CommerceMoney
			getTotalWithTaxAmountMoney()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isB2B()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isEmpty();

	public boolean isGuestOrder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isOpen();

	public boolean isQuote();

	public boolean isSubscription();

	public boolean isSubscriptionOrder();

	public void setShippingDiscounts(
		com.liferay.commerce.discount.CommerceDiscountValue
			commerceDiscountValue);

	public void setSubtotalDiscounts(
		com.liferay.commerce.discount.CommerceDiscountValue
			commerceDiscountValue);

	public void setTotalDiscounts(
		com.liferay.commerce.discount.CommerceDiscountValue
			commerceDiscountValue);

}