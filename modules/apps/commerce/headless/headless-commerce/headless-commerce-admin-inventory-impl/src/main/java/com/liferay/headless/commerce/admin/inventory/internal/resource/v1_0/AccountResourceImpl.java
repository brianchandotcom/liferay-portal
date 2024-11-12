/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.inventory.internal.resource.v1_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseRelService;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.Account;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.WarehouseAccount;
import com.liferay.headless.commerce.admin.inventory.resource.v1_0.AccountResource;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/account.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = AccountResource.class
)
public class AccountResourceImpl extends BaseAccountResourceImpl {

	@NestedField(parentClass = WarehouseAccount.class, value = "account")
	@Override
	public Account getWarehouseAccountAccount(Long id) throws Exception {
		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel =
			_commerceInventoryWarehouseRelService.
				getCommerceInventoryWarehouseRel(id);

		return _accountDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry,
				commerceInventoryWarehouseRel.getClassPK(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.inventory.internal.dto.v1_0.converter.AccountDTOConverter)"
	)
	private DTOConverter<AccountEntry, Account> _accountDTOConverter;

	@Reference
	private CommerceInventoryWarehouseRelService
		_commerceInventoryWarehouseRelService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

}