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

package com.liferay.commerce.pricing.internal.modifier;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.modifier.CommercePriceModifierHelper;
import com.liferay.commerce.pricing.service.CommercePriceModifierLocalService;
import com.liferay.commerce.pricing.type.CommercePriceModifierType;
import com.liferay.commerce.pricing.type.CommercePriceModifierTypeRegistry;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(service = CommercePriceModifierHelper.class)
public class CommercePriceModifierHelperImpl
	implements CommercePriceModifierHelper {

	@Override
	public BigDecimal applyCommercePriceModifier(
			long commercePriceListId, long cpDefinitionId,
			CommerceMoney originalCommerceMoney)
		throws PortalException {

		List<CommercePriceModifier> commercePriceModifiers =
			_commercePriceModifierLocalService.
				getQualifiedCommercePriceModifiers(
					commercePriceListId, cpDefinitionId);

		BigDecimal lowestPrice = null;

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.getCommercePriceList(
				commercePriceListId);

		CommerceCurrency priceListCurrency =
			commercePriceList.getCommerceCurrency();

		CommerceCurrency originalCommerceCurrency =
			originalCommerceMoney.getCommerceCurrency();

		BigDecimal originalPrice = originalCommerceMoney.getPrice();

		if (commercePriceList.getCommerceCurrencyId() !=
				originalCommerceCurrency.getCommerceCurrencyId()) {

			originalPrice = originalPrice.divide(
				priceListCurrency.getRate(),
				RoundingMode.valueOf(priceListCurrency.getRoundingMode()));

			originalPrice = originalPrice.multiply(
				originalCommerceCurrency.getRate());
		}

		if ((commercePriceModifiers != null) &&
			!commercePriceModifiers.isEmpty()) {

			for (CommercePriceModifier commercePriceModifier :
					commercePriceModifiers) {

				CommercePriceModifierType commercePriceModifierType =
					_commercePriceModifierTypeRegistry.
						getCommercePriceModifierType(
							commercePriceModifier.getModifierType());

				BigDecimal actualPrice = commercePriceModifierType.evaluate(
					originalPrice, commercePriceModifier);

				if ((lowestPrice == null) ||
					(actualPrice.compareTo(lowestPrice) < 0)) {

					lowestPrice = actualPrice;
				}
			}
		}

		if (lowestPrice == null) {
			return originalCommerceMoney.getPrice();
		}

		if (commercePriceList.getCommerceCurrencyId() !=
				originalCommerceCurrency.getCommerceCurrencyId()) {

			lowestPrice = lowestPrice.divide(
				originalCommerceCurrency.getRate(),
				RoundingMode.valueOf(
					originalCommerceCurrency.getRoundingMode()));

			lowestPrice = lowestPrice.multiply(priceListCurrency.getRate());
		}

		RoundingMode roundingMode = RoundingMode.valueOf(
			originalCommerceCurrency.getRoundingMode());

		return lowestPrice.setScale(_SCALE, roundingMode);
	}

	@Override
	public boolean hasCommercePriceModifiers(
			long commercePriceListId, long cpDefinitionId)
		throws PortalException {

		List<CommercePriceModifier> commercePriceModifiers =
			_commercePriceModifierLocalService.
				getQualifiedCommercePriceModifiers(
					commercePriceListId, cpDefinitionId);

		return !commercePriceModifiers.isEmpty();
	}

	private static final int _SCALE = 10;

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Reference
	private CommercePriceModifierLocalService
		_commercePriceModifierLocalService;

	@Reference
	private CommercePriceModifierTypeRegistry
		_commercePriceModifierTypeRegistry;

}