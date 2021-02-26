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

package com.liferay.headless.commerce.admin.account.internal.batch.engine.v1_0;

import com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.account.service.CommerceAccountUserRelService;
import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountMember;
import com.liferay.headless.commerce.admin.account.internal.constants.v1_0.AccountMemberBatchEngineTaskItemDelegateConstants;
import com.liferay.headless.commerce.admin.account.internal.util.v1_0.AccountMemberUtil;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.UserLocalService;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	enabled = false, immediate = true,
	property = "batch.engine.task.item.delegate.name=" + AccountMemberBatchEngineTaskItemDelegateConstants.COMMERCE_ACCOUNT_MEMBER,
	service = BatchEngineTaskItemDelegate.class
)
public class AccountMemberBatchEngineTaskItemDelegate
	extends BaseBatchEngineTaskItemDelegate<AccountMember> {

	@Override
	public void createItem(
			AccountMember accountMember, Map<String, Serializable> parameters)
		throws Exception {

		Serializable id = parameters.get("id");

		AccountMemberUtil.addCommerceAccountUserRel(
			_commerceAccountUserRelService, accountMember,
			_commerceAccountService.getCommerceAccount(
				Long.valueOf(id.toString())),
			AccountMemberUtil.getUser(
				_userLocalService, accountMember,
				contextCompany.getCompanyId()),
			_serviceContextHelper.getServiceContext());
	}

	@Override
	public Page<AccountMember> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		return null;
	}

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private CommerceAccountUserRelService _commerceAccountUserRelService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

	@Reference
	private UserLocalService _userLocalService;

}