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

package com.liferay.account.admin.web.internal.display;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Pei-Jung Lan
 */
public class AccountDisplay {

	public static AccountDisplay of(AccountEntry accountEntry) {
		return new AccountDisplay(accountEntry);
	}

	public long getAccountId() {
		return _accountId;
	}

	public String getDescription() {
		return _description;
	}

	public long getLogoId() {
		return _logoId;
	}

	public String getLogoURL(ThemeDisplay themeDisplay) {
		StringBuilder sb = new StringBuilder();

		sb.append(themeDisplay.getPathImage());
		sb.append("/account_entry_logo?img_id=");
		sb.append(getLogoId());
		sb.append("&t=");
		sb.append(WebServerServletTokenUtil.getToken(_logoId));

		return sb.toString();
	}

	public String getName() {
		return _name;
	}

	public String getParentAccountName() {
		return _parentAccountName;
	}

	public String getStatusLabel() {
		return _statusLabel;
	}

	public String getStatusLabelStyle() {
		return _statusLabelStyle;
	}

	public boolean isActive() {
		return _active;
	}

	private AccountDisplay(AccountEntry accountEntry) {
		_accountId = accountEntry.getAccountEntryId();
		_active = _isActive(accountEntry);
		_description = accountEntry.getDescription();
		_logoId = accountEntry.getLogoId();
		_name = accountEntry.getName();
		_parentAccountName = _getParentAccountName(accountEntry);
		_statusLabel = _getStatusLabel(accountEntry);
		_statusLabelStyle = _getStatusLabelStyle(accountEntry);
	}

	private String _getParentAccountName(AccountEntry accountEntry) {
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

	private String _getStatusLabel(AccountEntry accountEntry) {
		int status = accountEntry.getStatus();

		if (status == WorkflowConstants.STATUS_APPROVED) {
			return "active";
		}

		if (status == WorkflowConstants.STATUS_INACTIVE) {
			return "inactive";
		}

		return StringPool.BLANK;
	}

	private String _getStatusLabelStyle(AccountEntry accountEntry) {
		int status = accountEntry.getStatus();

		if (status == WorkflowConstants.STATUS_APPROVED) {
			return "success";
		}

		if (status == WorkflowConstants.STATUS_INACTIVE) {
			return "secondary";
		}

		return StringPool.BLANK;
	}

	private boolean _isActive(AccountEntry accountEntry) {
		int status = accountEntry.getStatus();

		if (status == WorkflowConstants.STATUS_APPROVED) {
			return true;
		}

		return false;
	}

	private final long _accountId;
	private final boolean _active;
	private final String _description;
	private final long _logoId;
	private final String _name;
	private final String _parentAccountName;
	private final String _statusLabel;
	private final String _statusLabelStyle;

}