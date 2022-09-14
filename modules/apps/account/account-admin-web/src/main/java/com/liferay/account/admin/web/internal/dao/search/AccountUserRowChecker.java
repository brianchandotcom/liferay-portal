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

package com.liferay.account.admin.web.internal.dao.search;

import com.liferay.account.admin.web.internal.display.AccountUserDisplay;
import com.liferay.account.admin.web.internal.util.AccountEntryEmailAddressValidatorFactoryUtil;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.account.service.AccountEntryUserRelLocalServiceUtil;
import com.liferay.account.validator.AccountEntryEmailAddressValidator;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;

import javax.portlet.PortletResponse;

/**
 * @author Erick Monteiro
 */
public class AccountUserRowChecker extends EmptyOnClickRowChecker {

	public AccountUserRowChecker(
		long accountEntryId, PortletResponse portletResponse) {

		super(portletResponse);

		_accountEntryId = accountEntryId;
	}

	@Override
	public boolean isChecked(Object object) {
		AccountUserDisplay accountUserDisplay = (AccountUserDisplay)object;

		return AccountEntryUserRelLocalServiceUtil.hasAccountEntryUserRel(
			_accountEntryId, accountUserDisplay.getUserId());
	}

	@Override
	public boolean isDisabled(Object object) {
		if (isChecked(object)) {
			return true;
		}

		AccountEntry accountEntry =
			AccountEntryLocalServiceUtil.fetchAccountEntry(_accountEntryId);

		if (accountEntry == null) {
			return false;
		}

		AccountEntryEmailAddressValidator accountEntryEmailAddressValidator =
			AccountEntryEmailAddressValidatorFactoryUtil.create(
				accountEntry.getCompanyId(), accountEntry.getDomainsArray());
		AccountUserDisplay accountUserDisplay = (AccountUserDisplay)object;

		if (accountEntryEmailAddressValidator.isValidDomain(
				accountUserDisplay.getEmailAddress())) {

			return false;
		}

		return true;
	}

	private final long _accountEntryId;

}