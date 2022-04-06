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
import com.liferay.account.configuration.AccountEntryEmailDomainsConfiguration;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.account.service.AccountEntryUserRelLocalServiceUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

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

		AccountUserDisplay accountUserDisplay = (AccountUserDisplay)object;

		User user = UserLocalServiceUtil.fetchUser(
			accountUserDisplay.getUserId());

		try {
			AccountEntryEmailDomainsConfiguration
				accountEntryEmailDomainsConfiguration =
					ConfigurationProviderUtil.getCompanyConfiguration(
						AccountEntryEmailDomainsConfiguration.class,
						user.getCompanyId());

			if (!accountEntryEmailDomainsConfiguration.
					enableEmailDomainValidation()) {

				return false;
			}

			AccountEntry accountEntry =
				AccountEntryLocalServiceUtil.fetchAccountEntry(_accountEntryId);

			if ((accountEntry == null) ||
				ArrayUtil.isEmpty(accountEntry.getDomainsArray())) {

				return false;
			}

			String emailAddress = accountUserDisplay.getEmailAddress();

			emailAddress = StringUtil.toLowerCase(emailAddress);

			int index = emailAddress.indexOf(CharPool.AT);

			String domain = emailAddress.substring(index + 1);

			if (!ArrayUtil.contains(accountEntry.getDomainsArray(), domain)) {
				return true;
			}
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(configurationException);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountUserRowChecker.class);

	private final long _accountEntryId;

}