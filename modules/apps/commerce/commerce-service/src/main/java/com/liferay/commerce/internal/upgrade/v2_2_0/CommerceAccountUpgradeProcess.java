/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v2_2_0;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.EmailAddressLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Ethan Bustad
 */
public class CommerceAccountUpgradeProcess extends UpgradeProcess {

	public CommerceAccountUpgradeProcess(
		AccountEntryLocalService accountEntryLocalService,
		AccountEntryOrganizationRelLocalService
			accountEntryOrganizationRelLocalService,
		AccountEntryUserRelLocalService accountEntryUserRelLocalService,
		EmailAddressLocalService emailAddressLocalService,
		OrganizationLocalService organizationLocalService,
		RoleLocalService roleLocalService) {

		_accountEntryLocalService = accountEntryLocalService;
		_accountEntryOrganizationRelLocalService =
			accountEntryOrganizationRelLocalService;
		_accountEntryUserRelLocalService = accountEntryUserRelLocalService;
		_emailAddressLocalService = emailAddressLocalService;
		_organizationLocalService = organizationLocalService;
		_roleLocalService = roleLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		List<Long> queuedOrganizationIds = new ArrayList<>();

		try (Statement s = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = s.executeQuery(
				"select organizationId from Organization_ where type_ = " +
					"'account'")) {

			while (resultSet.next()) {
				long organizationId = resultSet.getLong("organizationId");

				queuedOrganizationIds.add(organizationId);
			}
		}

		while (!queuedOrganizationIds.isEmpty()) {
			List<Long> organizationIds = new ArrayList<>(queuedOrganizationIds);

			queuedOrganizationIds.clear();

			for (long organizationId : organizationIds) {
				Organization organization =
					_organizationLocalService.getOrganization(organizationId);

				long parentCommerceAccountId = _getParentCommerceAccountId(
					organization.getParentOrganizationId());

				if (parentCommerceAccountId < 0) {
					queuedOrganizationIds.add(organizationId);

					continue;
				}

				_addCommerceAccount(organization, parentCommerceAccountId);
			}

			if (queuedOrganizationIds.size() >= organizationIds.size()) {
				_log.error(
					"Organization data is missing, so accounts cannot be " +
						"correctly created. Aborting data transformation.");

				String organizationIdsString = ListUtil.toString(
					organizationIds, StringPool.BLANK,
					StringPool.COMMA_AND_SPACE);

				_log.error(
					"The following organizations are orphaned: " +
						organizationIdsString);

				return;
			}
		}
	}

	private void _addCommerceAccount(
			Organization organization, long parentCommerceAccountId)
		throws Exception {

		String email = _getOrganizationEmailAddress(organization);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(organization.getCompanyId());
		serviceContext.setUserId(organization.getUserId());

		AccountEntry accountEntry =
			AccountEntryLocalServiceUtil.addAccountEntry(
				organization.getUserId(), parentCommerceAccountId,
				organization.getName(), null, null, email, null,
				StringPool.BLANK, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		if (organization.getExternalReferenceCode() != null) {
			accountEntry.setExternalReferenceCode(
				organization.getExternalReferenceCode());

			accountEntry = _accountEntryLocalService.updateAccountEntry(
				accountEntry);
		}

		Role role = _roleLocalService.getRole(
			serviceContext.getCompanyId(),
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR);

		_accountEntryUserRelLocalService.inviteUser(
			accountEntry.getAccountEntryId(), new long[] {role.getRoleId()},
			email, serviceContext.fetchUser(), serviceContext);

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry.getAccountEntryId(), organization.getOrganizationId());
	}

	private String _getOrganizationEmailAddress(Organization organization) {
		List<EmailAddress> emailAddresses =
			_emailAddressLocalService.getEmailAddresses(
				organization.getCompanyId(), Organization.class.getName(),
				organization.getOrganizationId());

		for (EmailAddress emailAddress : emailAddresses) {
			if (emailAddress.isPrimary()) {
				return emailAddress.getAddress();
			}
		}

		if (!emailAddresses.isEmpty()) {
			EmailAddress emailAddress = emailAddresses.get(0);

			return emailAddress.getAddress();
		}

		return StringPool.BLANK;
	}

	private long _getParentCommerceAccountId(long parentOrganizationId)
		throws Exception {

		if (parentOrganizationId == 0) {
			return 0;
		}

		Organization organization = _organizationLocalService.getOrganization(
			parentOrganizationId);

		if (!Objects.equals(organization.getType(), "account")) {
			return 0;
		}

		String sql =
			"select commerceAccountId from CommerceAccountOrganizationRel " +
				"where organizationId = " + parentOrganizationId;

		try (Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(sql)) {

			if (resultSet.next()) {
				return resultSet.getLong("commerceAccountId");
			}
		}

		return -1;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAccountUpgradeProcess.class);

	private final AccountEntryLocalService _accountEntryLocalService;
	private final AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;
	private final AccountEntryUserRelLocalService
		_accountEntryUserRelLocalService;
	private final EmailAddressLocalService _emailAddressLocalService;
	private final OrganizationLocalService _organizationLocalService;
	private final RoleLocalService _roleLocalService;

}