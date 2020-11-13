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

package com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0;

import com.liferay.commerce.discount.model.CommerceDiscountAccountRel;
import com.liferay.commerce.discount.service.CommerceDiscountAccountRelService;
import com.liferay.commerce.price.list.model.CommercePriceListAccountRel;
import com.liferay.commerce.price.list.service.CommercePriceListAccountRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Account;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountAccount;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListAccount;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.AccountDTOConverter;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.AccountResource;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;

import javax.validation.constraints.NotNull;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltán Takács
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v2_0/account.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {AccountResource.class, NestedFieldSupport.class}
)
public class AccountResourceImpl
	extends BaseAccountResourceImpl implements NestedFieldSupport {

	@NestedField(parentClass = DiscountAccount.class, value = "account")
	@Override
	public Account getDiscountAccountAccount(@NotNull Long id)
		throws Exception {

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			_commerceDiscountAccountRelService.getCommerceDiscountAccountRel(
				id);

		return _accountDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceDiscountAccountRel.getCommerceAccountId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@NestedField(parentClass = PriceListAccount.class, value = "account")
	@Override
	public Account getPriceListAccountAccount(@NotNull Long id)
		throws Exception {

		CommercePriceListAccountRel commercePriceListAccountRel =
			_commercePriceListAccountRelService.getCommercePriceListAccountRel(
				id);

		return _accountDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commercePriceListAccountRel.getCommerceAccountId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private AccountDTOConverter _accountDTOConverter;

	@Reference
	private CommerceDiscountAccountRelService
		_commerceDiscountAccountRelService;

	@Reference
	private CommercePriceListAccountRelService
		_commercePriceListAccountRelService;

}