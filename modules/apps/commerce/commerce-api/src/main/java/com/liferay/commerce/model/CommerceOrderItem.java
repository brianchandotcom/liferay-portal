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

package com.liferay.commerce.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceOrderItem service. Represents a row in the &quot;CommerceOrderItem&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderItemModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.model.impl.CommerceOrderItemImpl"
)
@ProviderType
public interface CommerceOrderItem
	extends CommerceOrderItemModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.model.impl.CommerceOrderItemImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceOrderItem, Long>
		COMMERCE_ORDER_ITEM_ID_ACCESSOR =
			new Accessor<CommerceOrderItem, Long>() {

				@Override
				public Long get(CommerceOrderItem commerceOrderItem) {
					return commerceOrderItem.getCommerceOrderItemId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceOrderItem> getTypeClass() {
					return CommerceOrderItem.class;
				}

			};

	public com.liferay.commerce.product.model.CPInstance fetchCPInstance();

	public com.liferay.commerce.product.model.CPMeasurementUnit
		fetchCPMeasurementUnit();

	public com.liferay.commerce.product.model.CProduct fetchCProduct();

	public java.util.List<CommerceOrderItem> getChildCommerceOrderItems();

	public CommerceOrder getCommerceOrder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.product.model.CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getCPDefinitionId();

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public com.liferay.commerce.product.model.CPInstance getCPInstance()
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public com.liferay.commerce.product.model.CProduct getCProduct()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.currency.model.CommerceMoney
			getDiscountAmountMoney()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.currency.model.CommerceMoney
			getDiscountWithTaxAmountMoney()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.currency.model.CommerceMoney
			getFinalPriceMoney()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.currency.model.CommerceMoney
			getFinalPriceWithTaxAmountMoney()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getParentCommerceOrderItemCPDefinitionId();

	public com.liferay.commerce.currency.model.CommerceMoney
			getPromoPriceMoney()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.currency.model.CommerceMoney
			getPromoPriceWithTaxAmountMoney()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.currency.model.CommerceMoney getUnitPriceMoney()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.currency.model.CommerceMoney
			getUnitPriceWithTaxAmountMoney()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean hasParentCommerceOrderItem();

}