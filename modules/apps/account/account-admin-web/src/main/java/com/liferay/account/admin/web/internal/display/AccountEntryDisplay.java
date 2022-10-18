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

import com.liferay.account.admin.web.internal.util.CurrentAccountEntryManagerUtil;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

/**
 * @author Drew Brokke
 * @author Pei-Jung Lan
 */
public class AccountEntryDisplay extends AccountEntryWrapper {

	public AccountEntryDisplay(AccountEntry accountEntry) {
		super(accountEntry);
	}

	public String getDefaultLogoURL() {
		return _defaultLogoURL;
	}

	public String getLogoURL() {
		return _logoURL;
	}

	public String getOrganizationNames() {
		return _organizationNames;
	}

	public User getPersonAccountEntryUser() {
		return _personAccountEntryUser;
	}

	public String getStatusLabel() {
		return _statusLabel;
	}

	public String getStatusLabelStyle() {
		return _statusLabelStyle;
	}

	public boolean isEmailAddressDomainValidationEnabled() {
		return _emailAddressDomainValidationEnabled;
	}

	public boolean isSelectedAccountEntry(long groupId, long userId)
		throws PortalException {

		if (isNew()) {
			return false;
		}

		long currentAccountEntryId =
			CurrentAccountEntryManagerUtil.getCurrentAccountEntryId(
				groupId, userId);

		if (currentAccountEntryId == getAccountEntryId()) {
			return true;
		}

		return false;
	}

	public boolean isValidateUserEmailAddress() {
		return _validateUserEmailAddress;
	}

	public void setDefaultLogoURL(String defaultLogoURL) {
		_defaultLogoURL = defaultLogoURL;
	}

	public void setEmailAddressDomainValidationEnabled(
		boolean emailAddressDomainValidationEnabled) {

		_emailAddressDomainValidationEnabled =
			emailAddressDomainValidationEnabled;
	}

	public void setLogoURL(String logoURL) {
		_logoURL = logoURL;
	}

	public void setOrganizationNames(String organizationNames) {
		_organizationNames = organizationNames;
	}

	public void setPersonAccountEntryUser(User personAccountEntryUser) {
		_personAccountEntryUser = personAccountEntryUser;
	}

	public void setStatusLabel(String statusLabel) {
		_statusLabel = statusLabel;
	}

	public void setStatusLabelStyle(String statusLabelStyle) {
		_statusLabelStyle = statusLabelStyle;
	}

	public void setValidateUserEmailAddress(boolean validateUserEmailAddress) {
		_validateUserEmailAddress = validateUserEmailAddress;
	}

	private String _defaultLogoURL;
	private boolean _emailAddressDomainValidationEnabled = true;
	private String _logoURL;
	private String _organizationNames;
	private User _personAccountEntryUser;
	private String _statusLabel;
	private String _statusLabelStyle;
	private boolean _validateUserEmailAddress;

}