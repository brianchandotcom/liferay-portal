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

package com.liferay.commerce.model.impl;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactoryUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPInstanceLocalServiceUtil;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalServiceUtil;
import com.liferay.commerce.product.service.CProductLocalServiceUtil;
import com.liferay.commerce.service.CommerceOrderItemLocalServiceUtil;
import com.liferay.commerce.service.CommerceOrderLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
public class CommerceOrderItemImpl extends CommerceOrderItemBaseImpl {

	@Override
	public CPInstance fetchCPInstance() {
		return CPInstanceLocalServiceUtil.fetchCPInstance(getCPInstanceId());
	}

	@Override
	public CPMeasurementUnit fetchCPMeasurementUnit() {
		return CPMeasurementUnitLocalServiceUtil.fetchCPMeasurementUnit(
			getCPMeasurementUnitId());
	}

	@Override
	public CProduct fetchCProduct() {
		return CProductLocalServiceUtil.fetchCProduct(getCProductId());
	}

	@Override
	public List<CommerceOrderItem> getChildCommerceOrderItems() {
		return CommerceOrderItemLocalServiceUtil.getChildCommerceOrderItems(
			getCommerceOrderItemId());
	}

	@Override
	public CommerceOrder getCommerceOrder() throws PortalException {
		return CommerceOrderLocalServiceUtil.getCommerceOrder(
			getCommerceOrderId());
	}

	@Override
	public CPDefinition getCPDefinition() throws PortalException {
		CPInstance cpInstance = fetchCPInstance();

		if (cpInstance == null) {
			return null;
		}

		return cpInstance.getCPDefinition();
	}

	@Override
	public long getCPDefinitionId() {
		CPInstance cpInstance = fetchCPInstance();

		if (cpInstance == null) {
			return 0;
		}

		return cpInstance.getCPDefinitionId();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CPInstance getCPInstance() throws PortalException {
		return CPInstanceLocalServiceUtil.getCPInstance(getCPInstanceId());
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CProduct getCProduct() throws PortalException {
		return CProductLocalServiceUtil.getCProduct(getCProductId());
	}

	@Override
	public CommerceMoney getDiscountAmountMoney() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		return CommerceMoneyFactoryUtil.create(
			commerceOrder.getCommerceCurrencyId(), getDiscountAmount());
	}

	@Override
	public CommerceMoney getDiscountWithTaxAmountMoney()
		throws PortalException {

		CommerceOrder commerceOrder = getCommerceOrder();

		return CommerceMoneyFactoryUtil.create(
			commerceOrder.getCommerceCurrencyId(), getDiscountWithTaxAmount());
	}

	@Override
	public CommerceMoney getFinalPriceMoney() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		return CommerceMoneyFactoryUtil.create(
			commerceOrder.getCommerceCurrencyId(), getFinalPrice());
	}

	@Override
	public CommerceMoney getFinalPriceWithTaxAmountMoney()
		throws PortalException {

		CommerceOrder commerceOrder = getCommerceOrder();

		return CommerceMoneyFactoryUtil.create(
			commerceOrder.getCommerceCurrencyId(),
			getFinalPriceWithTaxAmount());
	}

	@Override
	public long getParentCommerceOrderItemCPDefinitionId() {
		if (getParentCommerceOrderItemId() == 0) {
			return 0;
		}

		CommerceOrderItem commerceOrderItem =
			CommerceOrderItemLocalServiceUtil.fetchCommerceOrderItem(
				getParentCommerceOrderItemId());

		if (commerceOrderItem == null) {
			return 0;
		}

		return commerceOrderItem.getCPDefinitionId();
	}

	@Override
	public CommerceMoney getPromoPriceMoney() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		return CommerceMoneyFactoryUtil.create(
			commerceOrder.getCommerceCurrencyId(), getPromoPrice());
	}

	@Override
	public CommerceMoney getPromoPriceWithTaxAmountMoney()
		throws PortalException {

		CommerceOrder commerceOrder = getCommerceOrder();

		return CommerceMoneyFactoryUtil.create(
			commerceOrder.getCommerceCurrencyId(),
			getPromoPriceWithTaxAmount());
	}

	@Override
	public CommerceMoney getUnitPriceMoney() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		return CommerceMoneyFactoryUtil.create(
			commerceOrder.getCommerceCurrencyId(), getUnitPrice());
	}

	@Override
	public CommerceMoney getUnitPriceWithTaxAmountMoney()
		throws PortalException {

		CommerceOrder commerceOrder = getCommerceOrder();

		return CommerceMoneyFactoryUtil.create(
			commerceOrder.getCommerceCurrencyId(), getUnitPriceWithTaxAmount());
	}

	@Override
	public boolean hasParentCommerceOrderItem() {
		if (getParentCommerceOrderItemId() == 0) {
			return false;
		}

		return true;
	}

}