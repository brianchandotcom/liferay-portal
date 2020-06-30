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
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPInstanceLocalServiceUtil;
import com.liferay.commerce.product.service.CProductLocalServiceUtil;
import com.liferay.commerce.service.CommerceOrderLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
public class CommerceOrderItemImpl extends CommerceOrderItemBaseImpl {

	public CommerceOrderItemImpl() {
	}

	@Override
	public CPInstance fetchCPInstance() {
		return CPInstanceLocalServiceUtil.fetchCPInstance(getCPInstanceId());
	}

	@Override
	public CProduct fetchCProduct() {
		return CProductLocalServiceUtil.fetchCProduct(getCProductId());
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
	public CommerceMoney getFinalPriceMoney() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		return CommerceMoneyFactoryUtil.create(
			commerceOrder.getCommerceCurrencyId(), getFinalPrice());
	}

	@Override
	public CommerceMoney getPromoPriceMoney() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		return CommerceMoneyFactoryUtil.create(
			commerceOrder.getCommerceCurrencyId(), getPromoPrice());
	}

	@Override
	public CommerceMoney getUnitPriceMoney() throws PortalException {
		CommerceOrder commerceOrder = getCommerceOrder();

		return CommerceMoneyFactoryUtil.create(
			commerceOrder.getCommerceCurrencyId(), getUnitPrice());
	}

}