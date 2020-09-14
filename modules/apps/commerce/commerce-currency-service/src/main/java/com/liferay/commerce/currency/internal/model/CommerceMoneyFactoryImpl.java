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

import java.util.Locale;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(enabled = false, service = CommerceMoneyFactory.class)
public class CommerceMoneyFactoryImpl implements CommerceMoneyFactory {

	@Override
	public CommerceMoney create(
		CommerceCurrency commerceCurrency, BigDecimal price) {

		return new CommerceMoneyImpl(
			commerceCurrency, _commercePriceFormatter, price);
	}

	@Override
	public CommerceMoney create(
		CommerceCurrency commerceCurrency, BigDecimal price,
		PriceFormat priceFormat) {

		if (priceFormat == null) {
			throw new IllegalArgumentException("Price format must not be null");
		}

		CommerceMoney commerceMoney = new CommerceMoneyImpl(
			commerceCurrency, _commercePriceFormatter, price);

		if (priceFormat == PriceFormat.RELATIVE) {
			commerceMoney = new RelativeCommerceMoneyImpl(
				commerceCurrency, _commercePriceFormatter, price);
		}

		return commerceMoney;
	}

	@Override
	public CommerceMoney create(long commerceCurrencyId, BigDecimal price)
		throws PortalException {

		return create(
			_commerceCurrencyLocalService.getCommerceCurrency(
				commerceCurrencyId),
			price);
	}

	@Override
	public CommerceMoney emptyCommerceMoney() {
		if (_emptyCommerceMoney != null) {
			return _emptyCommerceMoney;
		}

		_emptyCommerceMoney = new CommerceMoney() {

			@Override
			public String format(Locale locale) throws PortalException {
				return _commercePriceFormatter.format(BigDecimal.ZERO, locale);
			}

			@Override
			public CommerceCurrency getCommerceCurrency() {
				throw new UnsupportedOperationException();
			}

			@Override
			public BigDecimal getPrice() {
				return BigDecimal.ZERO;
			}

			@Override
			public boolean isEmpty() {
				return true;
			}

		};

		return _emptyCommerceMoney;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		CommerceMoneyFactoryUtil.setCommerceMoneyFactory(this);
	}

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	private CommerceMoney _emptyCommerceMoney;

}