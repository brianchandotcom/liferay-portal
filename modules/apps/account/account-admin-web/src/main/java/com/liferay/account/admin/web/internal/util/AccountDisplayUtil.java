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

package com.liferay.account.admin.web.internal.util;

import com.liferay.account.admin.web.internal.display.AccountDisplay;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.service.WebsiteLocalServiceUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

/**
 * @author Pei-Jung Lan
 */
public class AccountDisplayUtil {

	public static AccountDisplay getAccountDisplay(AccountEntry accountEntry) {
		AccountDisplay.Builder builder = new AccountDisplay.Builder();

		return builder.accountId(
			accountEntry.getAccountEntryId()
		).description(
			accountEntry.getDescription()
		).name(
			accountEntry.getName()
		).parentAccountName(
			_getParentAccountName(accountEntry)
		).statusLabel(
			_getStatusLabel(accountEntry)
		).website(
			_getWebsite(accountEntry)
		).build();
	}

	private static String _getParentAccountName(AccountEntry accountEntry) {
		long parentAccountEntryId = accountEntry.getParentAccountEntryId();

		if (parentAccountEntryId == 0) {
			return StringPool.BLANK;
		}

		AccountEntry parentAccountEntry =
			AccountEntryLocalServiceUtil.fetchAccountEntry(
				parentAccountEntryId);

		if (parentAccountEntry != null) {
			return parentAccountEntry.getName();
		}

		return StringPool.BLANK;
	}

	private static String _getStatusLabel(AccountEntry accountEntry) {
		int status = accountEntry.getStatus();

		if (status == WorkflowConstants.STATUS_APPROVED) {
			return "active";
		}

		if (status == WorkflowConstants.STATUS_INACTIVE) {
			return "inactive";
		}

		return StringPool.BLANK;
	}

	private static String _getWebsite(AccountEntry accountEntry) {
		List<Website> websites = WebsiteLocalServiceUtil.getWebsites(
			accountEntry.getCompanyId(), AccountEntry.class.getName(),
			accountEntry.getAccountEntryId());

		if (websites.isEmpty()) {
			return StringPool.BLANK;
		}

		Website website = websites.get(0);

		return website.getUrl();
	}

}