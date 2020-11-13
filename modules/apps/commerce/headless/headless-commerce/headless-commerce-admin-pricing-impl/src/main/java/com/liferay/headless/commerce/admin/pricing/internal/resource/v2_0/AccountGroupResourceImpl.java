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

import com.liferay.commerce.discount.model.CommerceDiscountCommerceAccountGroupRel;
import com.liferay.commerce.discount.service.CommerceDiscountCommerceAccountGroupRelService;
import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel;
import com.liferay.commerce.price.list.service.CommercePriceListCommerceAccountGroupRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.AccountGroup;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountAccountGroup;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListAccountGroup;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.AccountGroupDTOConverter;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.AccountGroupResource;
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
	properties = "OSGI-INF/liferay/rest/v2_0/account-group.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {AccountGroupResource.class, NestedFieldSupport.class}
)
public class AccountGroupResourceImpl
	extends BaseAccountGroupResourceImpl implements NestedFieldSupport {

	@NestedField(
		parentClass = DiscountAccountGroup.class, value = "accountGroup"
	)
	@Override
	public AccountGroup getDiscountAccountGroupAccountGroup(@NotNull Long id)
		throws Exception {

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel =
				_commerceDiscountCommerceAccountGroupRelService.
					getCommerceDiscountCommerceAccountGroupRel(id);

		return _accountGroupDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceDiscountCommerceAccountGroupRel.
					getCommerceAccountGroupId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@NestedField(
		parentClass = PriceListAccountGroup.class, value = "accountGroup"
	)
	@Override
	public AccountGroup getPriceListAccountGroupAccountGroup(@NotNull Long id)
		throws Exception {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel =
				_commercePriceListCommerceAccountGroupRelService.
					getCommercePriceListCommerceAccountGroupRel(id);

		return _accountGroupDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commercePriceListCommerceAccountGroupRel.
					getCommerceAccountGroupId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private AccountGroupDTOConverter _accountGroupDTOConverter;

	@Reference
	private CommerceDiscountCommerceAccountGroupRelService
		_commerceDiscountCommerceAccountGroupRelService;

	@Reference
	private CommercePriceListCommerceAccountGroupRelService
		_commercePriceListCommerceAccountGroupRelService;

}