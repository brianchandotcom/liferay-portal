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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.model.AccountEntryOrganizationRelModel;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.account.service.AccountEntryOrganizationRelLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

	public String getDefaultLogoURL(PortletRequest portletRequest) {
		return PortalUtil.getPathContext(portletRequest) +
			"/account_entries_admin/icons/briefcase.svg";
	}

	public String getDescription() {
		return _description;
	}

	public List<String> getDomains() {
		return _domains;
	}

	public long getLogoId() {
		return _logoId;
	}

	public String getLogoURL(ThemeDisplay themeDisplay) {
		StringBundler sb = new StringBundler(5);

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

	public String getOrganizationNames() {
		return _organizationNames;
	}

	public String getParentAccountEntryName() {
		return _parentAccountEntryName;
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

	private AccountEntryDisplay() {
		_accountEntryId = 0;
		_active = true;
		_description = StringPool.BLANK;
		_domains = Collections.emptyList();
		_logoId = 0;
		_name = StringPool.BLANK;
		_organizationNames = StringPool.BLANK;
		_parentAccountEntryName = StringPool.BLANK;
		_statusLabel = StringPool.BLANK;
		_statusLabelStyle = StringPool.BLANK;
		_taxIdNumber = StringPool.BLANK;
		_type = AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS;
	}

	private AccountEntryDisplay(AccountEntry accountEntry) {
		_accountEntryId = accountEntry.getAccountEntryId();
		_active = _isActive(accountEntry);
		_description = accountEntry.getDescription();
		_domains = _getDomains(accountEntry);
		_logoId = accountEntry.getLogoId();
		_name = accountEntry.getName();
		_organizationNames = _getOrganizationNames(accountEntry);
		_parentAccountEntryName = _getParentAccountEntryName(accountEntry);
		_statusLabel = _getStatusLabel(accountEntry);
		_statusLabelStyle = _getStatusLabelStyle(accountEntry);
		_taxIdNumber = accountEntry.getTaxIdNumber();
		_type = accountEntry.getType();
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

	private String _getParentAccountEntryName(AccountEntry accountEntry) {
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

	private static final AccountEntryDisplay _EMPTY_INSTANCE =
		new AccountEntryDisplay();

	private static final int _ORGANIZATION_NAMES_LIMIT = 5;

	private final long _accountEntryId;
	private final boolean _active;
	private final String _description;
	private final List<String> _domains;
	private final long _logoId;
	private final String _name;
	private final String _organizationNames;
	private final String _parentAccountEntryName;
	private final String _statusLabel;
	private final String _statusLabelStyle;
	private final String _taxIdNumber;
	private final String _type;

}