/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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