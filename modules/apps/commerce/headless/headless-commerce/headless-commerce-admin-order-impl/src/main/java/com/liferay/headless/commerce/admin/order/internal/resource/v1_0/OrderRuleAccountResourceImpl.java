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

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.commerce.order.rule.exception.NoSuchCOREntryException;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryRelService;
import com.liferay.commerce.order.rule.service.COREntryService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRule;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleAccount;
import com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter.OrderRuleAccountDTOConverter;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderRuleAccountResource;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Marco Leo
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/order-rule-account.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, OrderRuleAccountResource.class}
)
public class OrderRuleAccountResourceImpl
	extends BaseOrderRuleAccountResourceImpl implements NestedFieldSupport {

	@Override
	public void deleteOrderRuleAccount(Long id) throws Exception {
		_corEntryRelService.deleteCOREntryRel(id);
	}

	@Override
	public Page<OrderRuleAccount>
			getOrderRuleByExternalReferenceCodeOrderRuleAccountsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		COREntry corEntry = _corEntryService.fetchByExternalReferenceCode(
			contextCompany.getCompanyId(), externalReferenceCode);

		if (corEntry == null) {
			throw new NoSuchCOREntryException(
				"Unable to find order rule with external reference code " +
					externalReferenceCode);
		}

		return Page.of(
			transform(
				_corEntryRelService.getAccountEntryCOREntryRels(
					corEntry.getCOREntryId(), null,
					pagination.getStartPosition(), pagination.getEndPosition()),
				corEntryRel -> _toOrderRuleAccount(corEntryRel)),
			pagination,
			_corEntryRelService.getAccountEntryCOREntryRelsCount(
				corEntry.getCOREntryId(), null));
	}

	@NestedField(parentClass = OrderRule.class, value = "orderRuleAccounts")
	@Override
	public Page<OrderRuleAccount> getOrderRuleIdOrderRuleAccountsPage(
			Long id, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		COREntry corEntry = _corEntryService.fetchCOREntry(id);

		if (corEntry == null) {
			throw new NoSuchCOREntryException(
				"Unable to find order rule with ID " + id);
		}

		return Page.of(
			transform(
				_corEntryRelService.getAccountEntryCOREntryRels(
					id, search, pagination.getStartPosition(),
					pagination.getEndPosition()),
				corEntryRel -> _toOrderRuleAccount(corEntryRel)),
			pagination,
			_corEntryRelService.getAccountEntryCOREntryRelsCount(id, search));
	}

	@Override
	public OrderRuleAccount
			postOrderRuleByExternalReferenceCodeOrderRuleAccount(
				String externalReferenceCode, OrderRuleAccount orderRuleAccount)
		throws Exception {

		COREntry corEntry = _corEntryService.fetchByExternalReferenceCode(
			contextCompany.getCompanyId(), externalReferenceCode);

		if (corEntry == null) {
			throw new NoSuchCOREntryException(
				"Unable to find order rule with external reference code " +
					externalReferenceCode);
		}

		AccountEntry accountEntry = _getAccountEntry(orderRuleAccount);

		return _toOrderRuleAccount(
			_corEntryRelService.addCOREntryRel(
				AccountEntry.class.getName(), accountEntry.getAccountEntryId(),
				corEntry.getCOREntryId()));
	}

	@Override
	public OrderRuleAccount postOrderRuleIdOrderRuleAccount(
			Long id, OrderRuleAccount orderRuleAccount)
		throws Exception {

		AccountEntry accountEntry = _getAccountEntry(orderRuleAccount);

		return _toOrderRuleAccount(
			_corEntryRelService.addCOREntryRel(
				AccountEntry.class.getName(), accountEntry.getAccountEntryId(),
				id));
	}

	private AccountEntry _getAccountEntry(OrderRuleAccount orderRuleAccount)
		throws Exception {

		AccountEntry accountEntry = null;

		if (orderRuleAccount.getAccountId() > 0) {
			accountEntry = _accountEntryService.getAccountEntry(
				orderRuleAccount.getAccountId());
		}
		else {
			accountEntry =
				_accountEntryService.fetchAccountEntryByExternalReferenceCode(
					contextCompany.getCompanyId(),
					orderRuleAccount.getAccountExternalReferenceCode());
		}

		return accountEntry;
	}

	private Map<String, Map<String, String>> _getActions(
			COREntryRel corEntryRel)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE", corEntryRel.getCOREntryRelId(),
				"deleteOrderRuleAccount", _corEntryRelModelResourcePermission)
		).build();
	}

	private OrderRuleAccount _toOrderRuleAccount(COREntryRel corEntryRel)
		throws Exception {

		return _orderRuleAccountDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(corEntryRel), _dtoConverterRegistry,
				corEntryRel.getCOREntryRelId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private AccountEntryService _accountEntryService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.order.rule.model.COREntryRel)"
	)
	private ModelResourcePermission<COREntryRel>
		_corEntryRelModelResourcePermission;

	@Reference
	private COREntryRelService _corEntryRelService;

	@Reference
	private COREntryService _corEntryService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private OrderRuleAccountDTOConverter _orderRuleAccountDTOConverter;

}