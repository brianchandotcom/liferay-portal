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

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.currency.util.RoundingType;
import com.liferay.commerce.currency.util.RoundingTypeServicesTracker;
import com.liferay.commerce.price.CommercePriceFormatter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
@Component(immediate = true)
public class CommercePriceFormatterImpl implements CommercePriceFormatter {

	@Override
	public String format(CommerceCurrency commerceCurrency, double price) {
		String roundingTypeName = null;

		if (commerceCurrency != null) {
			roundingTypeName = commerceCurrency.getRoundingType();
		}

		String value = null;

		RoundingType roundingType =
			_roundingTypeServicesTracker.getRoundingType(roundingTypeName);

		if (roundingType != null) {
			value = roundingType.round(price);
		}
		else {
			value = Double.toString(price);
		}

		if (commerceCurrency == null) {
			return value;
		}

		return commerceCurrency.getCode() + StringPool.SPACE + value;
	}

	@Override
	public String format(long groupId, double price) throws PortalException {
		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.fetchPrimaryCommerceCurrency(groupId);

		return format(commerceCurrency, price);
	}

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private Portal _portal;

	@Reference
	private RoundingTypeServicesTracker _roundingTypeServicesTracker;

}