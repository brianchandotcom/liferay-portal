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

import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderAccountGroup;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.account.model.AccountGroup",
	service = DTOConverter.class
)
public class OrderAccountGroupDTOConverter
	implements DTOConverter<AccountGroup, OrderAccountGroup> {

	@Override
	public String getContentType() {
		return AccountGroup.class.getSimpleName();
	}

	@Override
	public OrderAccountGroup toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		AccountGroup accountGroup = _accountGroupService.getAccountGroup(
			(Long)dtoConverterContext.getId());

		return new OrderAccountGroup() {
			{
				id = accountGroup.getAccountGroupId();
				name = accountGroup.getName();
			}
		};
	}

	@Reference
	private AccountGroupService _accountGroupService;

}