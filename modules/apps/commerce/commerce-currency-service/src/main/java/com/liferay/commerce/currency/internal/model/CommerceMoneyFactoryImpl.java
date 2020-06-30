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

package com.liferay.commerce.currency.internal.model;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.currency.model.CommerceMoneyFactoryUtil;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.currency.util.PriceFormat;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = CommerceMoneyFactory.class)
public class CommerceMoneyFactoryImpl implements CommerceMoneyFactory {

	@Override
	public CommerceMoney create(
		CommerceCurrency commerceCurrency, BigDecimal price) {

		return _createCommerceMoney(
			new CommerceMoneyImpl(_commercePriceFormatter), commerceCurrency,
			price);
	}

	@Override
	public CommerceMoney create(
		CommerceCurrency commerceCurrency, BigDecimal price,
		PriceFormat priceFormat) {

		if (priceFormat == PriceFormat.DEFAULT) {
			return create(commerceCurrency, price);
		}
		else if (priceFormat == PriceFormat.RELATIVE) {
			return _createCommerceMoney(
				new RelativeCommerceMoneyImpl(_commercePriceFormatter),
				commerceCurrency, price);
		}

		throw new IllegalArgumentException(
			"Invalid price format: " + priceFormat);
	}

	@Override
	public CommerceMoney create(long commerceCurrencyId, BigDecimal price)
		throws PortalException {

		return create(
			_commerceCurrencyLocalService.getCommerceCurrency(
				commerceCurrencyId),
			price);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		CommerceMoneyFactoryUtil.setCommerceMoneyFactory(this);
	}

	private CommerceMoney _createCommerceMoney(
		CommerceMoneyImpl commerceMoneyImpl, CommerceCurrency commerceCurrency,
		BigDecimal price) {

		commerceMoneyImpl.setCommerceCurrency(commerceCurrency);
		commerceMoneyImpl.setPrice(price);

		return commerceMoneyImpl;
	}

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

}