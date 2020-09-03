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

package com.liferay.commerce.internal.price;

import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.discount.CommerceDiscountCalculation;
import com.liferay.commerce.discount.application.strategy.CommerceDiscountApplicationStrategy;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.price.CommerceProductPriceCalculationFactory;
import com.liferay.commerce.price.list.discovery.CommercePriceListDiscovery;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryLocalService;
import com.liferay.commerce.pricing.configuration.CommercePricingConfiguration;
import com.liferay.commerce.pricing.modifier.CommercePriceModifierHelper;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.tax.CommerceTaxCalculation;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Riccardo Alberti
 */
@Component(
	configurationPid = "com.liferay.commerce.pricing.configuration.CommercePricingConfiguration",
	enabled = false, immediate = true,
	service = CommerceProductPriceCalculationFactory.class
)
public class CommerceProductPriceCalculationFactoryImpl
	implements CommerceProductPriceCalculationFactory {

	@Override
	public CommerceProductPriceCalculation
		getCommerceProductPriceCalculation() {

		return new CommerceProductPriceCalculationV2Impl(
			_commerceChannelLocalService, _commerceCurrencyLocalService,
			_commerceDiscountCalculationV2, _commerceMoneyFactory,
			_commercePriceEntryLocalService, _commercePriceListLocalService,
			_commercePriceModifierHelper, _commerceTierPriceEntryLocalService,
			_commerceTaxCalculation, _configurationProvider,
			_cpDefinitionOptionRelLocalService, _cpInstanceLocalService,
			_commerceDiscountApplicationStrategyMap,
			_commercePriceListDiscoveryMap);
	}

	public void unsetCommerceDiscountApplicationStrategy(
		CommerceDiscountApplicationStrategy commerceDiscountApplicationStrategy,
		Map<String, Object> properties) {

		String commerceDiscountApplicationStrategyKey = GetterUtil.getString(
			properties.get("commerce.discount.application.strategy.key"));

		_commerceDiscountApplicationStrategyMap.remove(
			commerceDiscountApplicationStrategyKey);
	}

	public void unsetCommercePriceListDiscovery(
		CommercePriceListDiscovery commercePriceListDiscovery,
		Map<String, Object> properties) {

		String commercePriceListDiscoveryKey = GetterUtil.getString(
			properties.get("commerce.price.list.discovery.key"));

		_commercePriceListDiscoveryMap.remove(commercePriceListDiscoveryKey);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_commercePricingConfiguration = ConfigurableUtil.createConfigurable(
			CommercePricingConfiguration.class, properties);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setCommerceDiscountApplicationStrategy(
		CommerceDiscountApplicationStrategy commerceDiscountApplicationStrategy,
		Map<String, Object> properties) {

		String commerceDiscountApplicationStrategyKey = GetterUtil.getString(
			properties.get("commerce.discount.application.strategy.key"));

		_commerceDiscountApplicationStrategyMap.put(
			commerceDiscountApplicationStrategyKey,
			commerceDiscountApplicationStrategy);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setCommercePriceListDiscovery(
		CommercePriceListDiscovery commercePriceListDiscovery,
		Map<String, Object> properties) {

		String commercePriceListDiscoveryKey = GetterUtil.getString(
			properties.get("commerce.price.list.discovery.key"));

		_commercePriceListDiscoveryMap.put(
			commercePriceListDiscoveryKey, commercePriceListDiscovery);
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	private final Map<String, CommerceDiscountApplicationStrategy>
		_commerceDiscountApplicationStrategyMap = new ConcurrentHashMap<>();

	@Reference(target = "(commerce.discount.calculation.key=v2.0)")
	private CommerceDiscountCalculation _commerceDiscountCalculationV2;

	@Reference
	private CommerceMoneyFactory _commerceMoneyFactory;

	@Reference
	private CommercePriceEntryLocalService _commercePriceEntryLocalService;

	private final Map<String, CommercePriceListDiscovery>
		_commercePriceListDiscoveryMap = new ConcurrentHashMap<>();

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Reference
	private CommercePriceModifierHelper _commercePriceModifierHelper;

	private volatile CommercePricingConfiguration _commercePricingConfiguration;

	@Reference
	private CommerceTaxCalculation _commerceTaxCalculation;

	@Reference
	private CommerceTierPriceEntryLocalService
		_commerceTierPriceEntryLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

}