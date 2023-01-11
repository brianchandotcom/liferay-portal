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

package com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryRelService;
import com.liferay.commerce.order.rule.service.COREntryService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleAccount;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.order.rule.model.COREntryRel-Account",
	service = {DTOConverter.class, OrderRuleAccountDTOConverter.class}
)
public class OrderRuleAccountDTOConverter
	implements DTOConverter<COREntryRel, OrderRuleAccount> {

	@Override
	public String getContentType() {
		return OrderRuleAccount.class.getSimpleName();
	}

	@Override
	public OrderRuleAccount toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		COREntryRel corEntryRel = _corEntryRelService.getCOREntryRel(
			(Long)dtoConverterContext.getId());

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			corEntryRel.getClassPK());
		COREntry corEntry = corEntryRel.getCOREntry();

		return new OrderRuleAccount() {
			{
				accountExternalReferenceCode =
					accountEntry.getExternalReferenceCode();
				accountId = accountEntry.getAccountEntryId();
				actions = dtoConverterContext.getActions();
				orderRuleAccountId = corEntryRel.getCOREntryRelId();
				orderRuleExternalReferenceCode =
					corEntry.getExternalReferenceCode();
				orderRuleId = corEntry.getCOREntryId();
			}
		};
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private COREntryRelService _corEntryRelService;

	@Reference
	private COREntryService _corEntryService;

}