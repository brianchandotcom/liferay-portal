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

package com.liferay.commerce.account.internal.service;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryTable;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryLocalServiceWrapper;
import com.liferay.commerce.account.internal.util.AccountEntryUtil;
import com.liferay.commerce.context.CommerceContextThreadLocal;
import com.liferay.commerce.context.CommerceGroupThreadLocal;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRelTable;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceWrapper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(service = ServiceWrapper.class)
public class CommerceChannelAccountEntryRelAccountEntryLocalServiceWrapper
	extends AccountEntryLocalServiceWrapper {

	@Override
	public AccountEntry fetchUserAccountEntry(
		long userId, long accountEntryId) {

		AccountEntry accountEntry = super.fetchUserAccountEntry(
			userId, accountEntryId);

		if (accountEntry != null) {
			return accountEntry;
		}

		try {
			long commerceChannelId = AccountEntryUtil.getCommerceChannelId(
				CommerceContextThreadLocal.get(),
				CommerceGroupThreadLocal.get());

			JoinStep joinStep = DSLQueryFactoryUtil.selectDistinct(
				AccountEntryTable.INSTANCE
			).from(
				CommerceChannelAccountEntryRelTable.INSTANCE
			).leftJoinOn(
				AccountEntryTable.INSTANCE,
				AccountEntryTable.INSTANCE.accountEntryId.eq(
					CommerceChannelAccountEntryRelTable.INSTANCE.accountEntryId)
			);

			DSLQuery dslQuery = joinStep.where(
				CommerceChannelAccountEntryRelTable.INSTANCE.accountEntryId.eq(
					accountEntryId
				).and(
					CommerceChannelAccountEntryRelTable.INSTANCE.
						commerceChannelId.eq(commerceChannelId)
				).and(
					CommerceChannelAccountEntryRelTable.INSTANCE.classNameId.eq(
						_classNameLocalService.getClassNameId(
							User.class.getName()))
				).and(
					CommerceChannelAccountEntryRelTable.INSTANCE.classPK.eq(
						userId)
				).and(
					CommerceChannelAccountEntryRelTable.INSTANCE.type.eq(
						CommerceChannelAccountEntryRelConstants.TYPE_USER)
				)
			).limit(
				0, 1
			);

			List<AccountEntry> accountEntries = dslQuery(dslQuery);

			if (accountEntries.isEmpty()) {
				return null;
			}

			return accountEntries.get(0);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return null;
	}

	@Override
	public List<AccountEntry> getUserAccountEntries(
			long userId, Long parentAccountEntryId, String keywords,
			String[] types, Integer status, int start, int end)
		throws PortalException {

		List<AccountEntry> userAccountEntries = new ArrayList<>(
			super.getUserAccountEntries(
				userId, parentAccountEntryId, keywords, types, status, start,
				end));

		long commerceChannelId = AccountEntryUtil.getCommerceChannelId(
			CommerceContextThreadLocal.get(), CommerceGroupThreadLocal.get());

		JoinStep joinStep = DSLQueryFactoryUtil.selectDistinct(
			AccountEntryTable.INSTANCE
		).from(
			CommerceChannelAccountEntryRelTable.INSTANCE
		).leftJoinOn(
			AccountEntryTable.INSTANCE,
			AccountEntryTable.INSTANCE.accountEntryId.eq(
				CommerceChannelAccountEntryRelTable.INSTANCE.accountEntryId)
		);

		DSLQuery dslQuery = joinStep.where(
			CommerceChannelAccountEntryRelTable.INSTANCE.commerceChannelId.eq(
				commerceChannelId
			).and(
				CommerceChannelAccountEntryRelTable.INSTANCE.classNameId.eq(
					_classNameLocalService.getClassNameId(User.class.getName()))
			).and(
				CommerceChannelAccountEntryRelTable.INSTANCE.classPK.eq(userId)
			).and(
				CommerceChannelAccountEntryRelTable.INSTANCE.type.eq(
					CommerceChannelAccountEntryRelConstants.TYPE_USER)
			)
		).limit(
			start, end
		);

		List<AccountEntry> accountEntries = new ArrayList<>(dslQuery(dslQuery));

		if (userAccountEntries.isEmpty()) {
			return accountEntries;
		}

		userAccountEntries.addAll(accountEntries);

		return new ArrayList<>(new LinkedHashSet<>(userAccountEntries));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceChannelAccountEntryRelAccountEntryLocalServiceWrapper.class);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

}