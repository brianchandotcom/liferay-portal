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

package com.liferay.commerce.internal.upgrade.v2_2_0;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.account.service.CommerceAccountOrganizationRelLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.EmailAddressLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ListUtil;

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
		CommerceAccountLocalService commerceAccountLocalService,
		CommerceAccountOrganizationRelLocalService
			commerceAccountOrganizationRelLocalService,
		EmailAddressLocalService emailAddressLocalService,
		OrganizationLocalService organizationLocalService) {

		_commerceAccountLocalService = commerceAccountLocalService;
		_commerceAccountOrganizationRelLocalService =
			commerceAccountOrganizationRelLocalService;
		_emailAddressLocalService = emailAddressLocalService;
		_organizationLocalService = organizationLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		List<Long> queuedOrganizationIds = new ArrayList<>();

		try (Statement s = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = s.executeQuery(
				"select organizationId from Organization_ where type_ = " +
					"'account'")) {

			while (rs.next()) {
				long organizationId = rs.getLong("organizationId");

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

		CommerceAccount commerceAccount =
			_commerceAccountLocalService.addBusinessCommerceAccount(
				organization.getName(), parentCommerceAccountId, email,
				StringPool.BLANK, true, organization.getExternalReferenceCode(),
				new long[0], new String[0], serviceContext);

		_commerceAccountOrganizationRelLocalService.
			addCommerceAccountOrganizationRel(
				commerceAccount.getCommerceAccountId(),
				organization.getOrganizationId(), serviceContext);
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
			ResultSet rs = s.executeQuery(sql)) {

			if (rs.next()) {
				return rs.getLong("commerceAccountId");
			}
		}

		return -1;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAccountUpgradeProcess.class);

	private final CommerceAccountLocalService _commerceAccountLocalService;
	private final CommerceAccountOrganizationRelLocalService
		_commerceAccountOrganizationRelLocalService;
	private final EmailAddressLocalService _emailAddressLocalService;
	private final OrganizationLocalService _organizationLocalService;

}