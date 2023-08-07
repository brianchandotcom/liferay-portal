/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.internal.resource.v1_0;

import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupService;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryRelService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderAccountGroup;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleAccountGroup;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderAccountGroupResource;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/order-account-group.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = OrderAccountGroupResource.class
)
public class OrderAccountGroupResourceImpl
	extends BaseOrderAccountGroupResourceImpl {

	@NestedField(
		parentClass = OrderRuleAccountGroup.class, value = "accountGroup"
	)
	@Override
	public OrderAccountGroup getOrderRuleAccountGroupAccountGroup(Long id)
		throws Exception {

		COREntryRel corEntryRel = _corEntryRelService.getCOREntryRel(id);

		AccountGroup accountGroup = _accountGroupService.getAccountGroup(
			corEntryRel.getClassPK());

		return _toAccountGroup(accountGroup.getAccountGroupId());
	}

	private OrderAccountGroup _toAccountGroup(long accountGroupId)
		throws Exception {

		return _orderAccountGroupDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				accountGroupId, contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private AccountGroupService _accountGroupService;

	@Reference
	private COREntryRelService _corEntryRelService;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter.OrderAccountGroupDTOConverter)"
	)
	private DTOConverter<AccountGroup, OrderAccountGroup>
		_orderAccountGroupDTOConverter;

}