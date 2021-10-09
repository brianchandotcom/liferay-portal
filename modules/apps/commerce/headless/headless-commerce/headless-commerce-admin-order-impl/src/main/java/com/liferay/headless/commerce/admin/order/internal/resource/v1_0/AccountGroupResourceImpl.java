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

import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.CommerceAccountGroupService;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryRelService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.AccountGroup;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleAccountGroup;
import com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter.AccountGroupDTOConverter;
import com.liferay.headless.commerce.admin.order.resource.v1_0.AccountGroupResource;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/account-group.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {AccountGroupResource.class, NestedFieldSupport.class}
)
public class AccountGroupResourceImpl
	extends BaseAccountGroupResourceImpl implements NestedFieldSupport {

	@NestedField(
		parentClass = OrderRuleAccountGroup.class, value = "accountGroup"
	)
	@Override
	public AccountGroup getOrderRuleAccountGroupAccountGroup(Long id)
		throws Exception {

		COREntryRel corEntryRel = _corEntryRelService.getCOREntryRel(id);

		CommerceAccountGroup commerceAccountGroup =
			_commerceAccountGroupService.getCommerceAccountGroup(
				corEntryRel.getClassPK());

		return _toAccountGroup(
			commerceAccountGroup.getCommerceAccountGroupId());
	}

	private AccountGroup _toAccountGroup(long commerceAccountGroupId)
		throws Exception {

		return _accountGroupDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAccountGroupId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private AccountGroupDTOConverter _accountGroupDTOConverter;

	@Reference
	private CommerceAccountGroupService _commerceAccountGroupService;

	@Reference
	private COREntryRelService _corEntryRelService;

}