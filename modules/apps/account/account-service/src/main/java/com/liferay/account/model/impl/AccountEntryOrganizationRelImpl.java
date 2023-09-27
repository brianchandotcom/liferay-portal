/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.model.impl;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class AccountEntryOrganizationRelImpl
	extends AccountEntryOrganizationRelBaseImpl {

	@Override
	public AccountEntry fetchAccountEntry() {
		return AccountEntryLocalServiceUtil.fetchAccountEntry(
			getAccountEntryId());
	}

	@Override
	public Organization fetchOrganization() {
		return OrganizationLocalServiceUtil.fetchOrganization(
			getOrganizationId());
	}

	@Override
	public AccountEntry getAccountEntry() throws PortalException {
		return AccountEntryLocalServiceUtil.getAccountEntry(
			getAccountEntryId());
	}

	@Override
	public Organization getOrganization() throws PortalException {
		return OrganizationLocalServiceUtil.getOrganization(
			getOrganizationId());
	}

}