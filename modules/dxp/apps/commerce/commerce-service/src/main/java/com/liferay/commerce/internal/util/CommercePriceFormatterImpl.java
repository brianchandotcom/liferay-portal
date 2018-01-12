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

package com.liferay.commerce.internal.util;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.currency.util.RoundingType;
import com.liferay.commerce.currency.util.RoundingTypeServicesTracker;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
@Component(immediate = true)
public class CommercePriceFormatterImpl implements CommercePriceFormatter {

	@Override
	public String format(HttpServletRequest httpServletRequest, double price)
		throws PortalException {

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		String roundingTypeName = null;

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.fetchPrimaryCommerceCurrency(groupId);

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

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private Portal _portal;

	@Reference
	private RoundingTypeServicesTracker _roundingTypeServicesTracker;

}