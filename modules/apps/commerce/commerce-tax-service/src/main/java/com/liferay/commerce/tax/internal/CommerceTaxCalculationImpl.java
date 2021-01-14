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

import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
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
import com.liferay.commerce.tax.configuration.CommerceShippingTaxConfiguration;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.tax.service.CommerceTaxMethodLocalService;
import com.liferay.commerce.util.CommerceTaxEngineRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;

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
@Component(enabled = false, service = CommerceTaxCalculation.class)
public class CommerceTaxCalculationImpl implements CommerceTaxCalculation {

	@Override
	public List<CommerceTaxValue> getCommerceTaxValues(
			CommerceOrder commerceOrder)
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
				commerceOrderItem.getFinalPrice(), false);

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

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public List<CommerceTaxValue> getCommerceTaxValues(
			CommerceOrder commerceOrder, CommerceContext commerceContext)
		throws PortalException {

		return getCommerceTaxValues(commerceOrder);
	}

	@Override
	public List<CommerceTaxValue> getCommerceTaxValues(
			long groupId, long cpInstanceId, long commerceBillingAddressId,
			long commerceShippingAddressId, BigDecimal amount,
			boolean includeTax)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
			cpInstanceId);

		if (cpInstance == null) {
			return Collections.emptyList();
		}

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		if (cpDefinition.isTaxExempt() ||
			(cpDefinition.getCPTaxCategoryId() <= 0)) {

			return Collections.emptyList();
		}

		return _getCommerceTaxValues(
			groupId, commerceBillingAddressId, commerceShippingAddressId,
			amount, includeTax, cpDefinition.getCPTaxCategoryId());
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public List<CommerceTaxValue> getCommerceTaxValues(
			long groupId, long cpInstanceId, long commerceBillingAddressId,
			long commerceShippingAddressId, BigDecimal amount,
			CommerceContext commerceContext)
		throws PortalException {

		return getCommerceTaxValues(
			groupId, cpInstanceId, commerceBillingAddressId,
			commerceShippingAddressId, amount, false);
	}

	@Override
	public CommerceMoney getShippingTaxValue(
			CommerceOrder commerceOrder, CommerceCurrency commerceCurrency)
		throws PortalException {

		CommerceShippingTaxConfiguration commerceShippingTaxConfiguration =
			_configurationProvider.getConfiguration(
				CommerceShippingTaxConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceOrder.getGroupId(),
					CommerceConstants.SERVICE_NAME_TAX));

		List<CommerceTaxValue> commerceTaxValues = _getCommerceTaxValues(
			commerceOrder.getGroupId(), commerceOrder.getBillingAddressId(),
			commerceOrder.getShippingAddressId(),
			commerceOrder.getShippingAmount(), false,
			commerceShippingTaxConfiguration.taxCategoryId());

		BigDecimal taxAmount = BigDecimal.ZERO;

		if (commerceTaxValues != null) {
			for (CommerceTaxValue commerceTaxValue : commerceTaxValues) {
				taxAmount = taxAmount.add(commerceTaxValue.getAmount());
			}
		}

		return _commerceMoneyFactory.create(commerceCurrency, taxAmount);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public CommerceMoney getTaxAmount(
			CommerceOrder commerceOrder, CommerceContext commerceContext)
		throws PortalException {

		return getTaxAmount(
			commerceOrder, commerceContext.getCommerceCurrency());
	}

	@Override
	public CommerceMoney getTaxAmount(
			CommerceOrder commerceOrder, CommerceCurrency commerceCurrency)
		throws PortalException {

		BigDecimal taxAmount = BigDecimal.ZERO;

		List<CommerceTaxValue> commerceTaxValues = getCommerceTaxValues(
			commerceOrder);

		for (CommerceTaxValue commerceTaxValue : commerceTaxValues) {
			taxAmount = taxAmount.add(commerceTaxValue.getAmount());
		}

		return _commerceMoneyFactory.create(commerceCurrency, taxAmount);
	}

	private List<CommerceTaxValue> _getCommerceTaxValues(
		long groupId, long commerceBillingAddressId,
		long commerceShippingAddressId, BigDecimal amount, boolean includeTax,
		long taxCategoryId) {

		List<CommerceTaxValue> commerceTaxValues = new ArrayList<>();

		CommerceTaxCalculateRequest commerceTaxCalculateRequest =
			new CommerceTaxCalculateRequest();

		commerceTaxCalculateRequest.setCommerceBillingAddressId(
			commerceBillingAddressId);
		commerceTaxCalculateRequest.setCommerceShippingAddressId(
			commerceShippingAddressId);
		commerceTaxCalculateRequest.setPrice(amount);
		commerceTaxCalculateRequest.setIncludeTax(includeTax);
		commerceTaxCalculateRequest.setChannelGroupId(groupId);
		commerceTaxCalculateRequest.setTaxCategoryId(taxCategoryId);

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
			catch (CommerceTaxEngineException commerceTaxEngineException) {
				_log.error(
					commerceTaxEngineException, commerceTaxEngineException);
			}
		}

		return commerceTaxValues;
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
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

}