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

package com.liferay.account.model.impl;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryOrganizationRelLocalServiceUtil;
import com.liferay.account.service.AccountEntryUserRelLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalServiceUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AccountEntryImpl extends AccountEntryBaseImpl {

	@Override
	public List<Organization> fetchOrganizations() {
		List<Organization> organizations = new ArrayList<>();

		for (AccountEntryOrganizationRel accountEntryOrganizationRel :
				AccountEntryOrganizationRelLocalServiceUtil.
					getAccountEntryOrganizationRels(getAccountEntryId())) {

			try {
				organizations.add(
					accountEntryOrganizationRel.getOrganization());
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}

		return organizations;
	}

	@Override
	public List<User> fetchUsers() {
		List<User> users = new ArrayList<>();

		for (AccountEntryUserRel accountEntryUserRel :
				AccountEntryUserRelLocalServiceUtil.
					getAccountEntryUserRelsByAccountEntryId(
						getAccountEntryId())) {

			try {
				users.add(accountEntryUserRel.getUser());
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}

		return users;
	}

	@Override
	public Group getAccountEntryGroup() {
		return _getAccountEntryGroup();
	}

	@Override
	public long getAccountEntryGroupId() {
		Group group = _getAccountEntryGroup();

		if (group == null) {
			return GroupConstants.DEFAULT_LIVE_GROUP_ID;
		}

		return group.getGroupId();
	}

	@Override
	public Address getDefaultBillingAddress() {
		return AddressLocalServiceUtil.fetchAddress(
			getDefaultBillingAddressId());
	}

	@Override
	public Address getDefaultShippingAddress() {
		return AddressLocalServiceUtil.fetchAddress(
			getDefaultShippingAddressId());
	}

	@Override
	public String[] getDomainsArray() {
		return StringUtil.split(getDomains());
	}

	private Group _getAccountEntryGroup() {
		return GroupLocalServiceUtil.fetchGroup(
			getCompanyId(),
			ClassNameLocalServiceUtil.getClassNameId(AccountEntry.class),
			getAccountEntryId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountEntryImpl.class);

}