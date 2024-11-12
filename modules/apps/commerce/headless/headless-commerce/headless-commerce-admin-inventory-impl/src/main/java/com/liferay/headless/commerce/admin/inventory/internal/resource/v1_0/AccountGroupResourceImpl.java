/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.inventory.internal.resource.v1_0;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseRelService;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.AccountGroup;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.WarehouseAccountGroup;
import com.liferay.headless.commerce.admin.inventory.resource.v1_0.AccountGroupResource;
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
	properties = "OSGI-INF/liferay/rest/v1_0/account-group.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = AccountGroupResource.class
)
public class AccountGroupResourceImpl extends BaseAccountGroupResourceImpl {

	@NestedField(
		parentClass = WarehouseAccountGroup.class, value = "accountGroup"
	)
	@Override
	public AccountGroup getWarehouseAccountGroupAccountGroup(Long id)
		throws Exception {

		CommerceInventoryWarehouseRel commerceInventoryWarehouseRel =
			_commerceInventoryWarehouseRelService.
				getCommerceInventoryWarehouseRel(id);

		return _accountGroupDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceInventoryWarehouseRel.getClassPK(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.inventory.internal.dto.v1_0.converter.AccountGroupDTOConverter)"
	)
	private DTOConverter<com.liferay.account.model.AccountGroup, AccountGroup>
		_accountGroupDTOConverter;

	@Reference
	private CommerceInventoryWarehouseRelService
		_commerceInventoryWarehouseRelService;

}