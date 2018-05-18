/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.currency.internal.model;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.currency.model.CommerceMoneyFactoryUtil;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component
public class CommerceMoneyFactoryImpl implements CommerceMoneyFactory {

	@Override
	public CommerceMoney create(
		CommerceCurrency commerceCurrency, BigDecimal price) {

		CommerceMoneyImpl commerceMoney = new CommerceMoneyImpl(
			_commercePriceFormatter);

		commerceMoney.setCommerceCurrency(commerceCurrency);
		commerceMoney.setPrice(price);

		return commerceMoney;
	}

	@Override
	public CommerceMoney create(long commerceCurrencyId, BigDecimal price)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.getCommerceCurrency(commerceCurrencyId);

		return create(commerceCurrency, price);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		CommerceMoneyFactoryUtil.setCommerceMoneyFactory(this);
	}

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

}