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

package com.liferay.account.service.impl;

import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountGroupAccountEntryRel;
import com.liferay.account.service.base.AccountGroupLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.account.model.AccountGroup",
	service = AopService.class
)
public class AccountGroupLocalServiceImpl
	extends AccountGroupLocalServiceBaseImpl {

	@Override
	public AccountGroup addAccountGroup(
			long userId, String description, String name)
		throws PortalException {

		long accountGroupId = counterLocalService.increment();

		AccountGroup accountGroup = accountGroupPersistence.create(
			accountGroupId);

		User user = userLocalService.getUser(userId);

		accountGroup.setCompanyId(user.getCompanyId());
		accountGroup.setUserId(user.getUserId());
		accountGroup.setUserName(user.getFullName());

		accountGroup.setDefaultAccountGroup(false);
		accountGroup.setDescription(description);
		accountGroup.setName(name);

		return accountGroupPersistence.update(accountGroup);
	}

	@Override
	public AccountGroup deleteAccountGroup(AccountGroup accountGroup) {
		accountGroupPersistence.remove(accountGroup);

		List<AccountGroupAccountEntryRel> accountGroupAccountEntryRels =
			accountGroupAccountEntryRelPersistence.findByAccountGroupId(
				accountGroup.getAccountGroupId());

		for (AccountGroupAccountEntryRel accountGroupAccountEntryRel :
				accountGroupAccountEntryRels) {

			accountGroupAccountEntryRelPersistence.remove(
				accountGroupAccountEntryRel);
		}

		return accountGroup;
	}

	@Override
	public AccountGroup deleteAccountGroup(long accountGroupId)
		throws PortalException {

		return deleteAccountGroup(
			accountGroupLocalService.getAccountGroup(accountGroupId));
	}

	@Override
	public List<AccountGroup> getAccountGroups(
		long companyId, int start, int end,
		OrderByComparator<AccountGroup> orderByComparator) {

		return accountGroupPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public AccountGroup getDefaultAccountGroup(long companyId) {
		return accountGroupPersistence.fetchByC_D_First(companyId, true, null);
	}

	@Override
	public boolean hasDefaultAccountGroup(long companyId) {
		int count = accountGroupPersistence.countByC_D(companyId, true);

		if (count > 0) {
			return true;
		}

		return false;
	}

	@Override
	public BaseModelSearchResult<AccountGroup> searchAccountGroups(
		long companyId, String keywords, int start, int end,
		OrderByComparator<AccountGroup> orderByComparator) {

		return new BaseModelSearchResult<>(
			accountGroupLocalService.dynamicQuery(
				_getDynamicQuery(companyId, keywords, orderByComparator), start,
				end, orderByComparator),
			(int)accountGroupLocalService.dynamicQueryCount(
				_getDynamicQuery(companyId, keywords, null)));
	}

	@Override
	public AccountGroup updateAccountGroup(
			long accountGroupId, String description, String name)
		throws PortalException {

		AccountGroup accountGroup = accountGroupPersistence.fetchByPrimaryKey(
			accountGroupId);

		accountGroup.setDescription(description);
		accountGroup.setName(name);

		return accountGroupPersistence.update(accountGroup);
	}

	private DynamicQuery _getDynamicQuery(
		long companyId, String keywords,
		OrderByComparator<AccountGroup> orderByComparator) {

		DynamicQuery dynamicQuery = accountGroupLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId", companyId));
		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("defaultAccountGroup", false));

		if (Validator.isNotNull(keywords)) {
			Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

			disjunction.add(
				RestrictionsFactoryUtil.ilike(
					"description",
					StringUtil.quote(keywords, StringPool.PERCENT)));
			disjunction.add(
				RestrictionsFactoryUtil.ilike(
					"name", StringUtil.quote(keywords, StringPool.PERCENT)));

			dynamicQuery.add(disjunction);
		}

		OrderFactoryUtil.addOrderByComparator(dynamicQuery, orderByComparator);

		return dynamicQuery;
	}

}