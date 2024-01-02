/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.internal.resource.v1_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelService;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.Account;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.ChannelAccount;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.AccountResource;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Danny Situ
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/account.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = AccountResource.class
)
public class AccountResourceImpl extends BaseAccountResourceImpl {

	@NestedField(parentClass = ChannelAccount.class, value = "account")
	@Override
	public Account getChannelAccountAccount(Long id) throws Exception {
		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_commerceChannelAccountEntryRelService.
				getCommerceChannelAccountEntryRel(id);

		return _accountDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceChannelAccountEntryRel.getAccountEntryId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.channel.internal.dto.v1_0.converter.AccountDTOConverter)"
	)
	private DTOConverter<AccountEntry, Account> _accountDTOConverter;

	@Reference
	private CommerceChannelAccountEntryRelService
		_commerceChannelAccountEntryRelService;

}