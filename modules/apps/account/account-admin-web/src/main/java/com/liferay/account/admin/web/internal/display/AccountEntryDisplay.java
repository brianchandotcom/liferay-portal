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
import com.liferay.account.configuration.AccountEntryEmailDomainsConfiguration;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.model.AccountEntryOrganizationRelModel;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.account.service.AccountEntryOrganizationRelLocalServiceUtil;
import com.liferay.account.service.AccountEntryUserRelLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;

/**
 * @author Pei-Jung Lan
 */
public class AccountEntryDisplay {

	public static AccountEntryDisplay of(AccountEntry accountEntry) {
		if (accountEntry != null) {
			return new AccountEntryDisplay(accountEntry);
		}

		return _EMPTY_INSTANCE;
	}

	public static AccountEntryDisplay of(long accountEntryId) {
		return of(
			AccountEntryLocalServiceUtil.fetchAccountEntry(accountEntryId));
	}

	public long getAccountEntryId() {
		return _accountEntryId;
	}

	public Address getDefaultBillingAddress() {
		return _defaultBillingAddress;
	}

	public long getDefaultBillingAddressId() {
		if (_defaultBillingAddress == null) {
			return 0L;
		}

		return _defaultBillingAddress.getAddressId();
	}

	public String getDefaultLogoURL(PortletRequest portletRequest) {
		return PortalUtil.getPathContext(portletRequest) +
			"/account_entries_admin/icons/briefcase.svg";
	}

	public Address getDefaultShippingAddress() {
		return _defaultShippingAddress;
	}

	public long getDefaultShippingAddressId() {
		if (_defaultShippingAddress == null) {
			return 0L;
		}

		return _defaultShippingAddress.getAddressId();
	}

	public String getDescription() {
		return _description;
	}

	public List<String> getDomains() {
		return _domains;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public long getLogoId() {
		return _logoId;
	}

	public String getLogoURL(ThemeDisplay themeDisplay) {
		return StringBundler.concat(
			themeDisplay.getPathImage(), "/account_entry_logo?img_id=",
			getLogoId(), "&t=", WebServerServletTokenUtil.getToken(_logoId));
	}

	public String getName() {
		return _name;
	}

	public String getOrganizationNames() {
		return _organizationNames;
	}

	public Optional<User> getPersonAccountEntryUserOptional() {
		return _personAccountEntryUserOptional;
	}

	public String getStatusLabel() {
		return _statusLabel;
	}

	public String getStatusLabelStyle() {
		return _statusLabelStyle;
	}

	public String getTaxIdNumber() {
		return _taxIdNumber;
	}

	public String getType() {
		return _type;
	}

	public boolean isActive() {
		return _active;
	}

	public boolean isEmailDomainValidationEnabled(ThemeDisplay themeDisplay) {
		try {
			AccountEntryEmailDomainsConfiguration
				accountEntryEmailDomainsConfiguration =
					ConfigurationProviderUtil.getCompanyConfiguration(
						AccountEntryEmailDomainsConfiguration.class,
						themeDisplay.getCompanyId());

			if (accountEntryEmailDomainsConfiguration.
					enableEmailDomainValidation()) {

				return true;
			}
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(configurationException, configurationException);
			}
		}

		return false;
	}

	public boolean isSelectedAccountEntry(long groupId, long userId)
		throws PortalException {

		long currentAccountEntryId =
			CurrentAccountEntryManagerUtil.getCurrentAccountEntryId(
				groupId, userId);

		if (currentAccountEntryId == getAccountEntryId()) {
			return true;
		}

		return false;
	}

	public boolean isValidateUserEmailAddress(ThemeDisplay themeDisplay) {
		if (isEmailDomainValidationEnabled(themeDisplay) &&
			ListUtil.isNotEmpty(getDomains())) {

			return true;
		}

		return false;
	}

	private AccountEntryDisplay() {
		_accountEntryId = 0;
		_active = true;
		_defaultBillingAddress = null;
		_defaultShippingAddress = null;
		_description = StringPool.BLANK;
		_domains = Collections.emptyList();
		_externalReferenceCode = StringPool.BLANK;
		_logoId = 0;
		_name = StringPool.BLANK;
		_organizationNames = StringPool.BLANK;
		_personAccountEntryUserOptional = Optional.empty();
		_statusLabel = StringPool.BLANK;
		_statusLabelStyle = StringPool.BLANK;
		_taxIdNumber = StringPool.BLANK;
		_type = AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS;
	}

	private AccountEntryDisplay(AccountEntry accountEntry) {
		_accountEntryId = accountEntry.getAccountEntryId();
		_active = _isActive(accountEntry);
		_defaultBillingAddress = accountEntry.getDefaultBillingAddress();
		_defaultShippingAddress = accountEntry.getDefaultShippingAddress();
		_description = accountEntry.getDescription();
		_domains = _getDomains(accountEntry);
		_externalReferenceCode = accountEntry.getExternalReferenceCode();
		_logoId = accountEntry.getLogoId();
		_name = accountEntry.getName();
		_organizationNames = _getOrganizationNames(accountEntry);
		_personAccountEntryUserOptional = _getPersonAccountEntryUserOptional(
			accountEntry);
		_statusLabel = _getStatusLabel(accountEntry);
		_statusLabelStyle = _getStatusLabelStyle(accountEntry);
		_taxIdNumber = accountEntry.getTaxIdNumber();
		_type = accountEntry.getType();
	}

	private List<User> _getAccountEntryUsers(AccountEntry accountEntry) {
		return Stream.of(
			AccountEntryUserRelLocalServiceUtil.
				getAccountEntryUserRelsByAccountEntryId(
					accountEntry.getAccountEntryId())
		).flatMap(
			List::stream
		).map(
			AccountEntryUserRel::getAccountUserId
		).map(
			UserLocalServiceUtil::fetchUser
		).filter(
			Objects::nonNull
		).collect(
			Collectors.toList()
		);
	}

	private List<String> _getDomains(AccountEntry accountEntry) {
		return StringUtil.split(accountEntry.getDomains());
	}

	private String _getOrganizationNames(AccountEntry accountEntry) {
		StringBundler sb = new StringBundler(4);

		List<AccountEntryOrganizationRel> accountEntryOrganizationRels =
			AccountEntryOrganizationRelLocalServiceUtil.
				getAccountEntryOrganizationRels(
					accountEntry.getAccountEntryId());

		int size = accountEntryOrganizationRels.size();

		sb.append(
			Stream.of(
				accountEntryOrganizationRels
			).flatMap(
				List::stream
			).map(
				AccountEntryOrganizationRelModel::getOrganizationId
			).map(
				OrganizationLocalServiceUtil::fetchOrganization
			).filter(
				Objects::nonNull
			).limit(
				Math.min(_ORGANIZATION_NAMES_LIMIT, size)
			).map(
				Organization::getName
			).collect(
				Collectors.joining(StringPool.COMMA_AND_SPACE)
			));

		if (size > _ORGANIZATION_NAMES_LIMIT) {
			sb.append(StringPool.COMMA_AND_SPACE);
			sb.append(
				LanguageUtil.format(
					LocaleThreadLocal.getThemeDisplayLocale(), "and-x-more",
					size - _ORGANIZATION_NAMES_LIMIT));
			sb.append(StringPool.TRIPLE_PERIOD);
		}

		return sb.toString();
	}

	private Optional<User> _getPersonAccountEntryUserOptional(
		AccountEntry accountEntry) {

		if (!Objects.equals(
				AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON,
				accountEntry.getType())) {

			return Optional.empty();
		}

		List<User> users = _getAccountEntryUsers(accountEntry);

		if (ListUtil.isNotEmpty(users)) {
			return Optional.of(users.get(0));
		}

		return Optional.empty();
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

	private static final AccountEntryDisplay _EMPTY_INSTANCE =
		new AccountEntryDisplay();

	private static final int _ORGANIZATION_NAMES_LIMIT = 5;

	private static final Log _log = LogFactoryUtil.getLog(
		AccountEntryDisplay.class);

	private final long _accountEntryId;
	private final boolean _active;
	private final Address _defaultBillingAddress;
	private final Address _defaultShippingAddress;
	private final String _description;
	private final List<String> _domains;
	private final String _externalReferenceCode;
	private final long _logoId;
	private final String _name;
	private final String _organizationNames;
	private final Optional<User> _personAccountEntryUserOptional;
	private final String _statusLabel;
	private final String _statusLabelStyle;
	private final String _taxIdNumber;
	private final String _type;

}