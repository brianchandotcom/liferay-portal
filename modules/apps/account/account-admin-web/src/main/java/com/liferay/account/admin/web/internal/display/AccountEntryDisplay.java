/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.display;

import com.liferay.account.manager.CurrentAccountEntryManager;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryWrapper;
import com.liferay.osgi.util.service.Snapshot;
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

		long currentAccountEntryId = 0L;

		CurrentAccountEntryManager currentAccountEntryManager =
			_currentAccountEntryManagerSnapshot.get();

		AccountEntry accountEntry =
			currentAccountEntryManager.getCurrentAccountEntry(groupId, userId);

		if (accountEntry != null) {
			currentAccountEntryId = accountEntry.getAccountEntryId();
		}

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

	private static final Snapshot<CurrentAccountEntryManager>
		_currentAccountEntryManagerSnapshot = new Snapshot<>(
			AccountEntryDisplay.class, CurrentAccountEntryManager.class);

	private String _defaultLogoURL;
	private boolean _emailAddressDomainValidationEnabled = true;
	private String _logoURL;
	private String _organizationNames;
	private User _personAccountEntryUser;
	private String _statusLabel;
	private String _statusLabelStyle;
	private boolean _validateUserEmailAddress;

}