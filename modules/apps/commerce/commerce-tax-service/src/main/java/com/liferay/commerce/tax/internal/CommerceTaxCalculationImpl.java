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

package com.liferay.commerce.tax.internal;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.exception.CommerceTaxEngineException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.tax.CommerceTaxCalculateRequest;
import com.liferay.commerce.tax.CommerceTaxCalculation;
import com.liferay.commerce.tax.CommerceTaxEngine;
import com.liferay.commerce.tax.CommerceTaxValue;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.tax.service.CommerceTaxMethodLocalService;
import com.liferay.commerce.util.CommerceTaxEngineRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = CommerceTaxCalculation.class)
public class CommerceTaxCalculationImpl implements CommerceTaxCalculation {

	@Override
	public List<CommerceTaxValue> getCommerceTaxValues(
			CommerceOrder commerceOrder, CommerceContext commerceContext)
		throws PortalException {

		if (commerceOrder == null) {
			return Collections.emptyList();
		}

		Map<String, CommerceTaxValue> taxValueMap = new HashMap<>();

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			List<CommerceTaxValue> commerceTaxValues = getCommerceTaxValues(
				commerceOrder.getGroupId(), commerceOrderItem.getCPInstanceId(),
				commerceOrder.getBillingAddressId(),
				commerceOrder.getShippingAddressId(),
				commerceOrderItem.getFinalPrice(), commerceContext);

			for (CommerceTaxValue commerceTaxValue : commerceTaxValues) {
				CommerceTaxValue aggregatedCommerceTaxValue = null;

				BigDecimal amount = commerceTaxValue.getAmount();

				if (taxValueMap.containsKey(commerceTaxValue.getName())) {
					aggregatedCommerceTaxValue = taxValueMap.get(
						commerceTaxValue.getName());

					amount = amount.add(aggregatedCommerceTaxValue.getAmount());
				}

				aggregatedCommerceTaxValue = new CommerceTaxValue(
					commerceTaxValue.getName(), commerceTaxValue.getLabel(),
					amount);

				taxValueMap.put(
					commerceTaxValue.getName(), aggregatedCommerceTaxValue);
			}
		}

		return new ArrayList<>(taxValueMap.values());
	}

	@Override
	public List<CommerceTaxValue> getCommerceTaxValues(
			long groupId, long cpInstanceId, long commerceBillingAddressId,
			long commerceShippingAddressId, BigDecimal amount,
			CommerceContext commerceContext)
		throws PortalException {

		List<CommerceTaxValue> commerceTaxValues = new ArrayList<>();

		CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
			cpInstanceId);

		if (cpInstance == null) {
			return commerceTaxValues;
		}

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		if (cpDefinition.isTaxExempt() ||
			(cpDefinition.getCPTaxCategoryId() <= 0)) {

			return commerceTaxValues;
		}

		CommerceTaxCalculateRequest commerceTaxCalculateRequest =
			new CommerceTaxCalculateRequest();

		commerceTaxCalculateRequest.setCommerceBillingAddressId(
			commerceBillingAddressId);
		commerceTaxCalculateRequest.setCommerceShippingAddressId(
			commerceShippingAddressId);
		commerceTaxCalculateRequest.setPrice(amount);
		commerceTaxCalculateRequest.setChannelGroupId(groupId);
		commerceTaxCalculateRequest.setTaxCategoryId(
			cpDefinition.getCPTaxCategoryId());

		List<CommerceTaxMethod> commerceTaxMethods =
			_commerceTaxMethodLocalService.getCommerceTaxMethods(groupId, true);

		for (CommerceTaxMethod commerceTaxMethod : commerceTaxMethods) {
			commerceTaxCalculateRequest.setCommerceTaxMethodId(
				commerceTaxMethod.getCommerceTaxMethodId());
			commerceTaxCalculateRequest.setPercentage(
				commerceTaxMethod.isPercentage());

			CommerceTaxEngine commerceTaxEngine =
				_commerceTaxEngineRegistry.getCommerceTaxEngine(
					commerceTaxMethod.getEngineKey());

			try {
				CommerceTaxValue commerceTaxValue =
					commerceTaxEngine.getCommerceTaxValue(
						commerceTaxCalculateRequest);

				if (commerceTaxValue != null) {
					commerceTaxValues.add(commerceTaxValue);
				}
			}
			catch (CommerceTaxEngineException ctee) {
				_log.error(ctee, ctee);
			}
		}

		return commerceTaxValues;
	}

	@Override
	public CommerceMoney getTaxAmount(
			CommerceOrder commerceOrder, CommerceContext commerceContext)
		throws PortalException {

		BigDecimal taxAmount = BigDecimal.ZERO;

		List<CommerceTaxValue> commerceTaxValues = getCommerceTaxValues(
			commerceOrder, commerceContext);

		for (CommerceTaxValue commerceTaxValue : commerceTaxValues) {
			taxAmount = taxAmount.add(commerceTaxValue.getAmount());
		}

		return _commerceMoneyFactory.create(
			commerceContext.getCommerceCurrency(), taxAmount);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceTaxCalculationImpl.class);

	@Reference
	private CommerceMoneyFactory _commerceMoneyFactory;

	@Reference
	private CommerceTaxEngineRegistry _commerceTaxEngineRegistry;

	@Reference
	private CommerceTaxMethodLocalService _commerceTaxMethodLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

}