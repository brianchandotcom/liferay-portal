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

package com.liferay.headless.commerce.admin.order.internal.resource.v1_0;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryRelService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Account;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleAccount;
import com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter.AccountDTOConverter;
import com.liferay.headless.commerce.admin.order.resource.v1_0.AccountResource;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/account.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {AccountResource.class, NestedFieldSupport.class}
)
public class AccountResourceImpl
	extends BaseAccountResourceImpl implements NestedFieldSupport {

	@Override
	public Account getOrderByExternalReferenceCodeAccount(
			String externalReferenceCode)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find order with external reference code " +
					externalReferenceCode);
		}

		return _toAccount(commerceOrder.getCommerceAccountId());
	}

	@NestedField(parentClass = Order.class, value = "account")
	@Override
	public Account getOrderIdAccount(Long id) throws Exception {
		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			id);

		return _toAccount(commerceOrder.getCommerceAccountId());
	}

	@NestedField(parentClass = OrderRuleAccount.class, value = "account")
	@Override
	public Account getOrderRuleAccountAccount(Long id) throws Exception {
		COREntryRel corEntryRel = _corEntryRelService.getCOREntryRel(id);

		CommerceAccount commerceAccount =
			_commerceAccountService.getCommerceAccount(
				corEntryRel.getClassPK());

		return _toAccount(commerceAccount.getCommerceAccountId());
	}

	private Account _toAccount(long commerceAccountId) throws Exception {
		return _accountDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAccountId, contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private AccountDTOConverter _accountDTOConverter;

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private COREntryRelService _corEntryRelService;

}